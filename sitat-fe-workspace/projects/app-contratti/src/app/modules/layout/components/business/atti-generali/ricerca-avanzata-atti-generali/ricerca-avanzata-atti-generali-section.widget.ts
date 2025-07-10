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
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    TabellatiService
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkFormBuilderConfiguration, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, set } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { RicercaAttiGeneraliForm } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { Constants } from '../../../../../../app.constants';

@Component({
    templateUrl: `ricerca-avanzata-atti-generali-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaAvanzataAttiGeneraliSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `ricerca-atti-generali-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private form: RicercaAttiGeneraliForm;    
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

        this.addSubscription(this.store.select(Constants.SEARCH_FORM_ATTI_GENERALI_SELECT).subscribe((form: RicercaAttiGeneraliForm) => {
            this.form = form;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {

        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_ATTO_GENERALE).subscribe(this.manageformData);
        
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
        if (button != null && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                idProfilo: this.userProfile?.configurations?.idProfilo
            };

            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
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
                    stazioneAppaltante: this.stazioneAppaltanteInfo
                }

                let customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {
                    let mapping: boolean = true;
            

                    if ( field.code === 'stazione-appaltante' && restObject != null && restObject.stazioneAppaltanteData != null ) {
                        mapping = false;                       
                        set(field, 'data', restObject.stazioneAppaltanteData);
                    }

                    if (field.code === 'stazione-appaltante' && field.type === 'MULTIPLE-AUTOCOMPLETE' && this.stazioneAppaltanteInfo.codice !== '*'){
                        field.visible = false;
                        mapping = false;
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    if ( field.code === 'rup' && restObject != null && restObject.rupData != null ) {
                        mapping = false;                       
                        set(field, 'data', restObject.rupData);
                    }

                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.form, providerArgs);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
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
