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
    StazioneAppaltanteBaseEntry,
    StazioneAppaltanteEntry,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    TabellatiService,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkButtonGroupInput, SdkButtonGroupOutput, SdkComboBoxItem, SdkFormBuilderConfiguration, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, has, isEmpty, isObject, map, set } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, Observer, Subject } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { RicercaGareForm } from '../../../../../models/gare/gare.model';

@Component({
    templateUrl: `ricerca-avanzata-gare-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaAvanzataGareSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `ricerca-avanzata-gare-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private form: RicercaGareForm;
    private userProfile: UserProfile;
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SEARCH_FORM_GARE_SELECT).subscribe((form: RicercaGareForm) => {
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

        this.tabellatiCacheService.getValoriTabellati(Constants.RICERCA_AVANZATA_GARE_TABELLATI).subscribe(this.manageFormData);
                            
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

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    // #region Public

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig
                }
            }).subscribe(this.manageExecutionProvider);
        }
    }

    // #endregion

    // #region Private

    private manageFormData = () => {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    advancedSearch: true
                }
                

                let customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {
                    let mapping: boolean = true;
            

                    if (
                        field.code === 'stazione-appaltante' && restObject != null && restObject.stazioneAppaltanteData != null
                    ) {
                        mapping = false;                       
                        set(field, 'data', restObject.stazioneAppaltanteData);
                    }

                    if (field.code === 'stazione-appaltante' && field.type === 'MULTIPLE-AUTOCOMPLETE' && this.stazioneAppaltanteInfo.codice !== '*'){
                        field.visible = false;
                        mapping = false
                    }
                    
                    if (field.code === 'gare-archiviate' && restObject == null) {
                        mapping = false;
                        let comboItem: SdkComboBoxItem = {
                            key: '2',
                            value: ''
                        }
                        set(field, 'data', comboItem);
                    }
                    if (
                        field.code === 'rup-gara' && restObject != null && restObject.rupData != null
                    ) {
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


   
    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.manageFormData();
            
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion


}
