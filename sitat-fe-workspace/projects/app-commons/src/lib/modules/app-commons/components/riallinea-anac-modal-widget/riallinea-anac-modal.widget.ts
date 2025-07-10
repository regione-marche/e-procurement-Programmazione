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
    SdkValidatorService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkRadioConfig,
    SdkRadioInput,
    SdkRadioOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { AbilitazioniUtilsService } from '../../services/utils/abilitazioni-utils.service';

@Component({
    templateUrl: `riallinea-anac-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RiallineaAnacModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;

    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;    
    public formBuilderConfigObs: BehaviorSubject<SdkFormBuilderConfiguration> = new BehaviorSubject<SdkFormBuilderConfiguration>(null);
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    public option: string = '1';

    public radioConfig: Observable<SdkRadioConfig> = of({
        code: 'riallinea-anac-radio',
        label: 'LABEL-RADIO-RIALINEA-ANAC.TITLE',
        choices: [
          {
            code: 'ricarica-pre-ese',
            label: 'LABEL-RADIO-RIALINEA-ANAC.PRE-ESE',
            checked: true
          },
          {
            code: 'ricarica-previa-cancellazione',
            label: 'LABEL-RADIO-RIALINEA-ANAC.CANCELLA'
            
          }      
        ]
      } as SdkRadioConfig);
    
      public radioData: Observable<SdkRadioInput> = of({
        data: {
            code: 'ricarica-pre-ese',
            label: 'LABEL-RADIO-RIALINEA-ANAC.PRE-ESE'
        }
      } as SdkRadioInput);

    @ViewChild('messages') _messagesPanel: ElementRef;


    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
       
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.loadButtons();
    }

    protected onAfterViewInit(): void {        
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

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    public onOutputRadio(item: SdkRadioOutput): void {        
        if (item.data.code === 'ricarica-pre-ese') {            
          this.option = '1';         
        } else{
            this.option = '2';
        }        
    }

    public manageFormClick(config: SdkTextOutput): void {       
    }



    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'reload') && get(obj, 'reload') === true) {
                this.emitOutput(obj);
            } else {                
                this.emitOutput(obj);
            }

        }
    }



    // #endregion

    // #region Public
  

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            if(button.code === 'modal-close-button'){
                of({ close: true, option: this.option }).subscribe(this.manageExecutionProvider)   
            } else if(button.code === 'conferma-riallinea'){
                of({ riallinea: true, option: this.option }).subscribe(this.manageExecutionProvider)   
            }
                      
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

    public manageOutputField(field: SdkFormBuilderField): void {}

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'modal') {
            if (isObject(_item.data)) {
               
            }
        }
    }

    // #endregion
}
