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
  SdkMessagePanelService,
  SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { WQuartzDTO } from '../../../../model/amministrazione.model';
import { CustomParamsFunction, ResponseDTO } from '../../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GestionePianificazioniService } from '../../../../services/gestione-pianificazioni.service';
import { FormBuilderUtilsService } from '../../../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-dettaglio-pianificazione.component.html',
  styleUrls: ['./sdk-dettaglio-pianificazione.component.scss']
})
export class SdkDettaglioPianificazioneComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-dettaglio-pianificazione-section`;

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
  private codApp: string;
  private beanId: string;
  private dettaglio: WQuartzDTO;
  private done: string;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    this.addSubscription(this.store.select<UserProfile>(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
    this.loadQueryParams();
  }

  protected onAfterViewInit(): void {
    this.loadDettaglio().pipe(
      map(this.elaborateDettaglio),
      map(() => this.checkInfoBox()),
      map(() => this.loadForm()),
      map(this.elaborateButtons),
      catchError(this.handleError),
      map(() => {
        if (this.done != null && this.done === 'Y') {
          this.sdkMessagePanelService.showInfo(this.messagesPanel, [
            {
              message: 'SDK-PIANIFICAZIONE.UPDATE-AFTER-RESTART'
            }
          ]);
        }
      })
    ).subscribe();
  }

  protected onDestroy(): void { }

  protected onConfig(config: any): void {
    if (config != null) {
      this.markForCheck(() => {
        this.config = { ...config };
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

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get gestionePianificazioniService(): GestionePianificazioniService { return this.injectable(GestionePianificazioniService) }

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
        item: this.dettaglio
      };

      this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
    }
  }

  // #endregion

  // #region Private

  private get messagesPanel(): HTMLElement {
    return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
  }

  private get infoBox(): HTMLElement {
    return this._infoBox != null ? this._infoBox.nativeElement : undefined;
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

        // inizializzazione di default
        let form: IDictionary<any> = {
          ...this.dettaglio
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
          let mapping: boolean = true;

          let keyAny: any = get(restObject, field.mappingInput);
          let key: string = dynamicField === true ? field.data : toString(keyAny);

          if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
          }

          return {
            mapping,
            field
          };
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, form);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
      });

    }
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
    this.codApp = paramsMap.get('codApp');
    this.beanId = paramsMap.get('beanId');
    this.done = paramsMap.get('done');
  }

  private loadDettaglio = (): Observable<ResponseDTO<WQuartzDTO>> => {
    return this.gestionePianificazioniService.getDettaglioPianificazione(this.codApp, this.beanId)
      .pipe(
        catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
          let err: ResponseDTO<any> = error.error;
          if (err != null && err.messages != null && err.messages.length > 0) {
            let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
              let message: SdkMessagePanelTranslate = {
                message: `SDK-PIANIFICAZIONE.VALIDATORS.${one}`
              };
              return message;
            });
            this.sdkMessagePanelService.showError(this.messagesPanel, messages);
          } else {
            this.sdkMessagePanelService.showError(this.messagesPanel, [
              {
                message: `ERRORS.UNEXPECTED-ERROR`
              }
            ]);
          }
          return throwError(() => error);
        })
      );
  }

  private elaborateDettaglio = (result: ResponseDTO<WQuartzDTO>) => {
    this.dettaglio = result.response;
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
