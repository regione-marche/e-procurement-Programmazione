import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostBinding,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
  IDictionary,
  SdkBase64Helper,
  SdkBusinessAbstractWidget,
  SdkDateHelper,
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
  SdkMessagePanelTranslate,
  SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, map as mapArray, remove, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { UserDTO } from '../../model/gestione-utenti.model';
import { CustomParamsFunction, ResponseDTO, ValoreTabellato } from '../../model/lib.model';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';
import { SdkGestioneModelliConstants } from '../../sdk-gestione-modelli.constants';
import { GestioneModelliService } from '../../services/gestione-modelli.service';
import { SdkDettaglioModelloStoreService } from '@maggioli/sdk-gestione-modelli';

@Component({
  templateUrl: './sdk-dettaglio-parametro.component.html'
})
export class SdkDettaglioParametroComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-dettaglio-parametro-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

  private buttons: SdkButtonGroupInput;
  private buttonsReadonly: SdkButtonGroupInput;
  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  private userProfile: UserProfile;
  private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
  private idModello: number;
  private idParametro: number;  
  private dettaglio: any;
  private menuTabs: Array<SdkMenuTab>;
  private schema: string;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    this.addSubscription(this.store.select<UserProfile>(SdkGestioneModelliConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
    this.loadQueryParams();
  }

  protected onAfterViewInit(): void {
    this.schema = this.sdkModelloStore.entita
    setTimeout(() => {
      this.loadTabellati().pipe(
        map(this.elaborateTabellati),
        mergeMap(this.loadDettaglio),
        map(this.elaborateDettaglio),
        map(() => this.checkInfoBox()),
        map(() => this.loadForm()),
        map(this.elaborateButtons),
        catchError(this.handleError)
      ).subscribe();
    });
  }

  protected onDestroy(): void { }

  protected onConfig(config: any): void {
    if (config != null) {
      this.markForCheck(() => {
        this.config = { ...config };
        this.menuTabs = this.config.menuTabs;        
        this.isReady = true
      });
    }
  }

  protected onUpdateState(_state: boolean): void { }

  // #endregion

  // #region Getters

  private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

  private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get gestioneModelliService(): GestioneModelliService { return this.injectable(GestioneModelliService) }

  private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

  private get sdkModelloStore (): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

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
        idModello: this.idModello,
        idParametro: this.idParametro,
        schema: this.schema
      };

      this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
    }
  }

  // #endregion

  // #region Private

  private get messagesPanel(): HTMLElement {
    return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
  }

  private get sdkBase64Helper(): SdkBase64Helper { return this.injectable(SdkBase64Helper) }

  private get modelliService(): GestioneModelliService { return this.injectable(GestioneModelliService); }

  private get infoBox(): HTMLElement {
    return this._infoBox != null ? this._infoBox.nativeElement : undefined;
  }

  private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
    return this.tabellatiCacheService.getValoriTabellati(SdkGestioneModelliConstants.DETTAGLIO_PARAMETRO_TABELLATI);
  }

  private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
    this.valoriTabellati = result;
  }

  

  private checkInfoBox(): void {
    if (!isEmpty(this.config.infoBox)) {
      this.sdkMessagePanelService.clear(this.infoBox);
      this.sdkMessagePanelService.showInfoBox(this.infoBox, {
        message: this.config.infoBox
      });
    }
  }

  private loadForm = () => {
    if (this.config != null) {
      this.markForCheck(() => {
        let formConfig: SdkFormBuilderConfiguration = {
          fields: cloneDeep(get(this.config, 'body.fields'))
        };

       

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
          let mapping: boolean = true;

          let keyAny: any = get(restObject, field.mappingInput);
          let key: string = dynamicField === true ? field.data : toString(keyAny);
          if(field.code === 'tabellato'){
            const descr = this.dettaglio.tabDesc 
            set(field, 'data', descr);
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

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.dettaglio);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
      });

    }
  }

  public manageFormClick(config: SdkTextOutput): void {
   
  }


  private loadButtons(): void {
    this.buttons = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
    };

    this.buttonsReadonly = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsReadonly, this.userProfile.configurations)
    };
  }

  private manageExecutionProvider = (obj: any) => {
    if (obj != null && obj.cleanSearch === true) {
      this.loadForm();
    }
  }

  private loadQueryParams(): void {
    let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    this.idModello = +paramsMap.get('idModello');
    this.idParametro = +paramsMap.get('idParametro');
  }

  private loadDettaglio = (): Observable<ResponseDTO<UserDTO>> => {
    return this.gestioneModelliService.getDettaglioParametro(this.idModello, this.idParametro)
      .pipe(
        catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
          let err: ResponseDTO<any> = error.error;
          if (err != null && err.messages != null && err.messages.length > 0) {
            let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
              let message: SdkMessagePanelTranslate = {
                message: `SDK-UTENTE.VALIDATORS.${one}`
              };
              return message;
            });
            this.sdkMessagePanelService.showError(this.messagesPanel, messages);
          }
          return throwError(() => error);
        })
      );
  }

  private elaborateDettaglio = (result: any) => {
    this.dettaglio = result.data;
  }

  private elaborateButtons = () => {   
      this.buttonsSubj.next(this.buttons);    
  }


  private handleError = (err: any) => {
    this.buttonsSubj.next(this.buttonsReadonly);
    return throwError(() => err);
  } 

  // #endregion

}
