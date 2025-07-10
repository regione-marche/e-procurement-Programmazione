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
  IDictionary,
} from '@maggioli/sdk-commons';
import {
  SdkAutocompleteItem,
  SdkButtonGroupInput,
  SdkButtonGroupOutput,
  SdkCheckboxConfig,
  SdkCheckboxInput,
  SdkCheckboxOutput,
  SdkFormBuilderConfiguration,
  SdkFormBuilderField,
  SdkFormBuilderInput,
  SdkFormFieldGroupConfiguration,
  SdkMessagePanelService,
  SdkRadioConfig,
  SdkRadioInput,
  SdkRadioOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, set, reduce } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';
import { ProfiloConfigurationItem, ProfiloConfiguration } from '../../app-commons.exports';
import { TranslateService } from '@ngx-translate/core';
import * as JSZip from 'jszip';

@Component({
  templateUrl: `report-indicatori-modal.widget.html`,
  encapsulation: ViewEncapsulation.None,
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ReportIndicatoriModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

  // #region Variables

  public config: any;
  public data: void;
  private buttons: SdkButtonGroupInput;
  private buttonsRo: SdkButtonGroupInput;
  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
  private userProfile: UserProfile;

  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
  public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
  private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
  public visibleCheckCig: boolean = true;
  public modifyCheckCig: string= '';
  public noCigUpdate: boolean=true;
  public radioValue: string;
  public tipoReport: string = 'preliminare';
  public showWarning: boolean = false;
  private text: string='';
  public righeCaricate: number = 0;
  public righeAggiornate: number = 0;
  public righeIgnorate: number =  0;
  public righeScartate: number =  0;
  public chiamataEseguita: boolean = false;

  @ViewChild('messages') _messagesPanel: ElementRef;

  public radioConfig: Observable<SdkRadioConfig> = of({
    code: 'id-radio',
    label: 'LABEL-RADIO-REPORT-INDICATORI.TITLE',
    choices: [      
      {
        code: 'preliminare',
        label: 'LABEL-RADIO-REPORT-INDICATORI.PRELIMINARE',
        checked: true        
      },
      {
        code: 'anomalia',
        label: 'LABEL-RADIO-REPORT-INDICATORI.ANOMALIA'
      }    
    ]
  } as SdkRadioConfig);

  public radioData: Observable<SdkRadioInput> = of({
    data: {
      code: 'preliminare',
      label: 'LABEL-CHECK-REPORT-INDICATORI.PRELIMINARE'
    }
  } as SdkRadioInput);


  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef,pus:ProtectionUtilsService, private http: HttpClient) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {
    this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
      this.stazioneAppaltanteInfo = saInfo;
    }));

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

  protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

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



  private manageExecutionProvider = (obj: any) => {
    this.sdkMessagePanelService.clear(this.messagesPanel);
    if (!isEmpty(obj)) {
      if (has(obj, 'close') && get(obj, 'close') === true) {
        this.emitOutput(obj);
      }      
    }
  }



  // #endregion

  // #region Public

  public onOutputRadio(item: SdkRadioOutput): void {
    this.tipoReport = item.data.code;   
  }

  public onButtonClick(button: SdkButtonGroupOutput): void {
    let cupProgetto = null;
    if(this.config.progetto != null){
      cupProgetto = this.config.progetto.cup;
    }
    
    if (isObject(button) && isEmpty(button.provider) === false) {
      if(button.code === 'modal-close-button'){
        let data ={ close: true };
        this.emitOutput(data);
      } else {
        this.provider.run(button.provider,         
          {
            type: 'BUTTON',
            buttonCode: button.code,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            messagesPanel: this.messagesPanel,
            stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
            codGara: this.config.codGara,
            codLotto: this.config.codLotto,
            cig: this.config.cig,
            syscon: this.userProfile.syscon,         
            tipoReport: this.tipoReport,
            cupProgetto: cupProgetto
          }
        ).subscribe(this.manageExecutionProvider)
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
    this.buttonsRo = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttonsRo, this.userProfile.configurations)
    };
    this.buttonsSubj.next(this.buttons)
  }


  public manageOutputField(field: SdkFormBuilderField): void {}




  // #endregion
}
