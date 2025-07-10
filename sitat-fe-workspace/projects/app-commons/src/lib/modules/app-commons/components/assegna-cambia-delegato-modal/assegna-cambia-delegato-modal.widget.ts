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
    SdkCheckboxOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkRadioConfig,
    SdkRadioInput,
    SdkRadioOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { AbilitazioniUtilsService } from '../../services/utils/abilitazioni-utils.service';
import { Tecnico } from '../../models/tecnico/tecnico.model';

@Component({
    templateUrl: `assegna-cambia-delegato-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AssegnaCambiaDelegatoModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;

    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    n
    public formBuilderConfigObs: BehaviorSubject<SdkFormBuilderConfiguration> = new BehaviorSubject<SdkFormBuilderConfiguration>(null);
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private delegato:string;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private modalConfig: any;
    public insert: boolean = true;
    public hiddenRadio: boolean = true;

    public radioConfig: Observable<SdkRadioConfig> = of({
        code: 'delegato-radio',
        label: '',
        choices: [
          {
            code: 'aggiungi',
            label: 'LABEL-RADIO-DELEGATO.AGGIUNGI',
            checked: true
          },
          {
            code: 'cancella',
            label: 'LABEL-RADIO-DELEGATO.CANCELLA'
            
          }      
        ]
      } as SdkRadioConfig);
    
      public radioData: Observable<SdkRadioInput> = of({
        data: {
          code: 'aggiungi',
          label: 'LABEL-RADIO-DELEGATO.AGGIUNGI'
        }
      } as SdkRadioInput);

    @ViewChild('messages') _messagesPanel: ElementRef;


    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.initModal();
        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        let factory: Function = this.getDelegatiFactory(this.config.codGara, this.userProfile.cfImpersonato ,this.userProfile.loaImpersonato ,this.userProfile.idpImpersonato, this.stazioneAppaltanteInfo.codice );
        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
            {
                message: 'GARA.GENERAL-DATA.WARNING-DELEGA'
            }
        ]);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
            this.delegato = result.delegato;
            this.loadForm();
            if(isEmpty(this.config.gara.drp)){
                this.hiddenRadio = true;
                this.insert = true;
            } else{                
                this.hiddenRadio = false;
            }
        });

    }

    
    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
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

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    public onOutputRadio(item: SdkRadioOutput): void {        
        if (item.data.code === 'aggiungi') {            
          this.insert = true;
          this.loadForm();
        } else{
            this.insert = false;
        }        
    }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'drp') {
                this.modalConfig.componentConfig = {
                    gara: this.config.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.config.gara.delegato
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    private initModal() {
        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = toString(keyAny);

            if (field.code === 'drp') {
                if (isObject(restObject.delegato)) {
                    let data: Tecnico = get(restObject, field.mappingInput);
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${data.nominativo} (${data.cf})`,
                        _key: data.codice
                    };
                    set(field, 'data', item);
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

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, {delegato:this.delegato}, {stazioneAppaltante: this.config.stazioneAppaltante}, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
    }

    private getDelegatiFactory(codGara: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string) {
        return () => {
            return this.gareService.getDelegati(codGara, cfImpersonato, loaImpersonato, idpImpersonato, codein);
        }
    }

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





    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'reload') && get(obj, 'reload') === true) {
                this.emitOutput(obj);
            } else {
                obj = {reload:true};
                this.emitOutput(obj);
            }

        }
    }



    // #endregion

    // #region Public
  

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    messagesPanel: this.messagesPanel,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,                                  
                    syscon: this.userProfile.syscon,
                    codGara: this.config.codGara,
                    cfImpersonato: this.userProfile.cfImpersonato,
                    loaImpersonato: this.userProfile.loaImpersonato,
                    idpImpersonato: this.userProfile.idpImpersonato,
                    codein: this.stazioneAppaltanteInfo.codice,
                    inserisciDelegato: this.insert
                }
            }).subscribe(this.manageExecutionProvider)
            
            
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


    public manageOutputField(field: SdkFormBuilderField): void {


    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'modal') {
            if (isObject(_item.data)) {
               
            }
        }
    }

    // #endregion
}
