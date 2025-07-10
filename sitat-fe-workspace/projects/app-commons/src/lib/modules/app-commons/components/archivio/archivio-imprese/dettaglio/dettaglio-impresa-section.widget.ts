import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { Constants } from '../../../../app-commons.constants';
import { ImpresaEntry } from '../../../../models/archivio/archivio-imprese.models';
import { ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { ArchivioImpreseService } from '../../../../services/archivio/archivio-imprese.service';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';
import { DettaglioImpresaStoreService } from '../dettaglio-impresa-store.service';


@Component({
    templateUrl: `dettaglio-impresa-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioImpresaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-impresa-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private codiceImpresa: string;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private impresa: ImpresaEntry;
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private estera: Boolean = false;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();

        this.tabellatiCacheService.getValoriTabellati(Constants.IMPRESA_TABELLATI).subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
            this.valoriTabellati = result;

            this.loadQueryParams();
            this.checkInfoBox();

            let factory = this.dettaglioImpresaFactory(this.codiceImpresa);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ImpresaEntry) => {
                this.impresa = result;
                this.estera = this.impresa.nazione != null && this.impresa.nazione !== 1;
                this.elaborateConfig();
            });
        });
    }

    protected onAfterViewInit(): void { }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get dettaglioImpresaStore(): DettaglioImpresaStoreService { return this.injectable(DettaglioImpresaStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codiceImpresa: this.codiceImpresa,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig
            }).subscribe();
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codiceImpresa = paramsMap.get('codiceImpresa');
        this.dettaglioImpresaStore.codiceImpresa = this.codiceImpresa;
    }

    private dettaglioImpresaFactory(codiceImpresa: string): () => Observable<ImpresaEntry> {
        return () => {
            return this.archivioImpreseService.getImpresa(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codiceImpresa)
        }
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {

                let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

                let formConfig: SdkFormBuilderConfiguration = {
                    fields
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: ImpresaEntry) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
                    }

                    if(field.code === 'codice-fiscale-impresa' && this.estera === true){
                        field.visible = false;
                        mapping = false;
                    }

                    if(field.code === 'partita-iva' && this.estera === true){
                        field.visible = false;
                        mapping = false;
                    }

                    if(field.code === 'cf-estero' && this.estera === false){
                        field.visible = false;
                        mapping = false;
                    }

                    if (field.code === 'cognome-legale' || field.code === 'nome-legale' || field.code === 'codice-fiscale-legale') {
                        if (isObject(restObject.rappresentante)) {
                            let data = restObject.rappresentante;
                            set(field, 'data', get(data, field.mappingInput));
                            mapping = false;
                        }
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.impresa);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
