import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../app-commons.constants';
import { RicercaAvanzataArchivioImpreseForm } from '../../../../models/archivio/archivio-imprese.models';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';

@Component({
    templateUrl: `ricerca-avanzata-archivio-imprese-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaAvanzataArchivioImpreseSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `ricerca-avanzata-archivio-imprese-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private buttons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private form: RicercaAvanzataArchivioImpreseForm;
    private userProfile: UserProfile;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SEARCH_FORM_ARCHIVIO_IMPRESE_SELECT).subscribe((form: RicercaAvanzataArchivioImpreseForm) => {
            this.form = form;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig
            }).subscribe(this.manageExecutionProvider);
        }
    }

    // #endregion

    // #region Private

    private loadForm = () => {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = dynamicField === true ? field.data : toString(keyAny);

                    if (field.code === 'stazione-appaltante' && field.type === 'AUTOCOMPLETE' &&  restObject != null && restObject.stazioneAppaltanteData != null) {
                        let item: SdkAutocompleteItem = {
                            ...restObject.stazioneAppaltanteData,
                            text: `${restObject.stazioneAppaltanteData.nomein} (${restObject.stazioneAppaltanteData.cfein})`,
                            _key: restObject.stazioneAppaltanteData.codein
                        };
                        set(field, 'data', item);
                        mapping = false;
                    }

                    if (field.code === 'stazione-appaltante' && field.type === 'AUTOCOMPLETE' && this.stazioneAppaltanteInfo.codice !== '*'){
                        field.visible = false;
                        mapping = false;
                    }
                    
                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
                    }

                    return {
                        mapping,
                        field
                    };
                }



                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    advancedSearch: true
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.form, providerArgs);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;

                this.formBuilderConfigObs.next(formConfig);
            });

        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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
        return this.tabellatiCacheService.getValoriTabellati(Constants.IMPRESA_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    // #endregion


}
