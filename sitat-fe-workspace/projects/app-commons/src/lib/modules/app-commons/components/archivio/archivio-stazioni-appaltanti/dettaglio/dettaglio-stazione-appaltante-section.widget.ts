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
    SdkMenuTab,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, findIndex, get, has, isEmpty, isObject, isString, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../app-commons.constants';
import { StazioneAppaltanteEntry } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { TabellatiService } from '../../../../services/tabellati/tabellati.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';


@Component({
    templateUrl: `dettaglio-stazione-appaltante-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioStazioneAppaltanteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-stazione-appaltante-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private codice: string;
    private buttons: SdkButtonGroupInput;
    private buttonsWithoutPermission: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private stazioneAppaltante: StazioneAppaltanteEntry;
    private provinceMap: IDictionary<ValoreTabellato> = {};
    private comuniMap: IDictionary<ValoreTabellato> = {};
    private menuTabs: Array<SdkMenuTab>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.refreshTabs();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            mergeMap(this.loadDettaglio),
            map(this.elaborateDettaglio),
            map(() => this.checkInfoBox()),
            map(() => this.elaborateConfig()),
            map(this.elaborateButtons),
            catchError(this.handleError)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
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

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codice: this.codice,
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
        this.codice = paramsMap.get('codice');
    }

    private dettaglioStazioneAppaltanteFactory(codice: string): () => Observable<StazioneAppaltanteEntry> {
        return () => {
            return this.tabellatiService.getStazioneAppaltante(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codice);
        }
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {

            let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

            let formConfig: SdkFormBuilderConfiguration = {
                fields
            };

            let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
                let mapping: boolean = true;

                let keyAny: any = get(restObject, field.mappingInput);
                let key: string = dynamicField === true ? field.data : toString(keyAny);

                if (field.code === 'cod-istat') {
                    if (has(this.comuniMap, key)) {
                        const istatComune: string = key;
                        const istatProvincia: string = `0${istatComune.substring(3, 6)}`;
                        field.data = `${this.comuniMap[istatComune].descrizione} - ${this.provinceMap[istatProvincia].descrizione} (${istatComune})`
                        mapping = false;
                    }
                }

                if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                    [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
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
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.stazioneAppaltante);

            this.defaultFormBuilderConfig = cloneDeep(formConfig);
            this.formBuilderConfig = formConfig;
            this.formBuilderConfigObs.next(formConfig);
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

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.ARCHIVIO_STAZIONI_APPALTANTI_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadDettaglio = (): Observable<StazioneAppaltanteEntry> => {
        const factory = this.dettaglioStazioneAppaltanteFactory(this.codice);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDettaglio = (result: StazioneAppaltanteEntry) => {
        this.stazioneAppaltante = result;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsWithoutPermission = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsWithoutPermission, this.userProfile.configurations)
        };
    }

    private isUtenteAbilitatoSA(): boolean {
        return findIndex(this.userProfile.abilitazioni, (one: string) => one === Constants.OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI) == -1;
    }

    private loadProvince = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE);
    }

    private elaborateProvince = (province: Array<ValoreTabellato>) => {
        each(province, (provincia: ValoreTabellato) => {
            this.provinceMap[provincia.codistat] = provincia;
        });
    }

    private loadComuni = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI);
    }

    private elaborateComuni = (comuni: Array<ValoreTabellato>) => {
        each(comuni, (comune: ValoreTabellato) => {
            this.comuniMap[comune.codistat] = comune;
        });
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, {
                    userProfile: this.userProfile
                });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private elaborateButtons = () => {
        if (this.isUtenteAbilitatoSA() && this.stazioneAppaltante.codFisc != Constants.UFFINT_RISERVATA) {
            this.buttonsSubj.next(this.buttons);
        } else {
            this.buttonsSubj.next(this.buttonsWithoutPermission);
        }
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsWithoutPermission);
        return throwError(() => err);
    }

    // #endregion
}
