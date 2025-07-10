import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    SdkAbstractComponent,
    SdkProviderService,
    SdkStoreService,
    SdkValidatorConfig,
    SdkValidatorService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkRadioConfig,
    SdkRadioInput,
    SdkRadioOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, has, isEmpty, isObject, set } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `pubblica-tutto-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PubblicaTuttoModalConfig extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild('warningPanelMessage') _warningPanelMessage: ElementRef;

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;

    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    private radioValue = '1';
    private selectedItem: SdkAutocompleteItem;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public radioConfig: Observable<SdkRadioConfig>;

    public radioData: Observable<SdkRadioInput> = of({
        data: {
            code: '1',
            label: 'LABEL-RADIO-PUBBLICA-TUTTO.SOLO-NON-PUBBLICATI',
            checked: true
        }
    } as SdkRadioInput);

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr)
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
    }

    // #region Hooks

    protected onInit(): void {
        this.loadButtons();      
    }

    protected onAfterViewInit(): void {
        this.elaborateConfig();
    }

    protected onDestroy(): void { }


    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                if(this.config.isPubblicato){
                    this.radioConfig = of({
                        code: 'id-radio',
                        label: 'LABEL-RADIO-PUBBLICA-TUTTO.TITLE',
                        choices: [                       
                            {
                                code: '1',
                                label: 'LABEL-RADIO-PUBBLICA-TUTTO.SOLO-NON-PUBBLICATI',
                                checked: true
                            },
                            {
                                code: '2',
                                label: 'LABEL-RADIO-PUBBLICA-TUTTO.TUTTO'
                            },
                        ]
                    } as SdkRadioConfig);
                } else{
                    this.radioConfig = of({
                        code: 'id-radio',
                        label: 'LABEL-RADIO-PUBBLICA-TUTTO.TITLE',
                        choices: [ 
                            {
                                code: '1',
                                label: 'LABEL-RADIO-PUBBLICA-TUTTO.TUTTO',
                                checked: true
                            }
                        ]
                    } as SdkRadioConfig);
                }
                
                
                this.isReady = true;
            });
        }
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }
    // #endregion

    // #region Private


    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        let ofTop: number = messagesPanel.offsetTop > 100 ? messagesPanel.offsetTop : 100;
        window.scrollTo({
            top: ofTop - 100,
            left: 0,
            behavior: 'auto'
        });
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'fields')
                };
                each(formConfig.fields, (one: SdkFormBuilderField) => {
                    if (one.type === 'FORM-SECTION') {
                        one = this.elaborateSection(one);
                    } else if (one.type === 'FORM-GROUP') {
                        one = this.elaborateGroup(one);
                    } else {
                        one = this.elaborateProvider(one);
                    }
                });

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

   

    private elaborateProvider(field: SdkFormBuilderField): SdkFormBuilderField {
        if (!isEmpty(field.itemsProviderCode)) {
            field.itemsProvider = this.provider.run(field.itemsProviderCode);
        }

        if (!isEmpty(field.validators)) {
            each(field.validators, (one: any) => {
                if (isObject(one.config)) {
                    let config: SdkValidatorConfig = one.config;
                    if (get(config, 'required') === true) {
                        one.validator = this.validator.run('MANDATORY', one.messages);
                    } else if (!isEmpty(get(config, 'regexp'))) {
                        one.validator = this.validator.run('REGEXP', one.messages);
                    } else {
                        one.validator = this.validator.run(one.validator, one.messages);
                    }
                }
            });
        }
        if (isObject(this.selectedItem)) {
            if (field.type === 'AUTOCOMPLETE') {
                let restItem: string = get(this.selectedItem, field.mappingInput);
                let item: SdkAutocompleteItem = {
                    _key: restItem,
                    text: restItem
                };
                set(field, 'data', item);
            } else {
                set(field, 'data', get(this.selectedItem, field.mappingInput));
            }
        }

        return field;
    }

    private elaborateSection(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateSection(one);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateGroup(one);
            } else {
                one = this.elaborateProvider(one);
            }
        });
        return field;
    }

    private elaborateGroup(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateSection(one);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateGroup(one);
                } else {
                    one = this.elaborateProvider(one);
                }
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }



    public onOutput(item: SdkRadioOutput): void {
        if (item != null &&!isEmpty(item.data)) {
            this.radioValue = item.data.code;
        }
    }


    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            console.log(obj);
            if (has(obj, 'reload') && get(obj, 'reload') === true) {
                this.emitOutput(obj);
            }  else if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(obj);
            }else {
                this.emitOutput(undefined);
            }

        }
    }

    private get warningPanelMessage(): HTMLElement {
        return isObject(this._warningPanelMessage) ? this._warningPanelMessage.nativeElement : undefined;
    }

   

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        let pubblicatoTutto = false;
        if(!this.config.isPubblicato){
            pubblicatoTutto = true;
        } else{
            pubblicatoTutto = this.radioValue === '1' ? false : true
        }
        
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider,{data:{code: button.code},... {
                type: 'BUTTON',                                
                ...this.config.data,
                buttonCode: button.code,
                pubblicatoTutto                
            }}).subscribe(this.manageExecutionProvider,()=>{
                this.emitOutput({close: true});
            });
            
        }
    
        
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }


    public manageOutputField(field: SdkFormBuilderField): void { }


   

    // #endregion
}
