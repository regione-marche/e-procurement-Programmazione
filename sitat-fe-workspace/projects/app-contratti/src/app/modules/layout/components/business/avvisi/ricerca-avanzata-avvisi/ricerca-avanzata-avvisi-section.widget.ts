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
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteEntry,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    TabellatiService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkButtonGroupInput, SdkButtonGroupOutput, SdkFormBuilderConfiguration, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, has, isEmpty, isObject, set } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, Observer, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { RicercaAvvisoForm } from '../../../../../models/avviso/avviso.model';

@Component({
    templateUrl: `ricerca-avanzata-avvisi-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaAvanzataAvvisiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `ricerca-avanzata-avvisi-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private form: RicercaAvvisoForm;    
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.SEARCH_FORM_AVVISI_SELECT).subscribe((form: RicercaAvvisoForm) => {
            this.form = form;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {

        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_AVVISO).subscribe(this.manageformData);
        
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }
    
    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    // #region Public

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.addSubscription(
                this.provider.run(button.provider, {
                    type: 'BUTTON',
                    data: {
                        code: button.code,
                        defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                        formBuilderConfig: this.formBuilderConfig
                    }
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }

    // #endregion

    // #region Private

    private manageformData = () => {        
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let providerArgs: IDictionary<any> = {
                    advancedSearch: true
                }

                let customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {
                    let mapping: boolean = true;
            

                    if (field.code === 'stazione-appaltante' && field.type === 'AUTOCOMPLETE' && restObject != null && restObject.stazioneAppaltanteData != null) {
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
                    
                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
                if (isObject(this.form)) {
                    formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction, undefined, providerArgs);
                    this.defaultFormBuilderConfig = cloneDeep(formConfig);
                    formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.form, providerArgs);
                } else {
                    formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction, undefined, providerArgs);
                    this.defaultFormBuilderConfig = cloneDeep(formConfig);
                }

                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });

        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.manageformData();
            
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
