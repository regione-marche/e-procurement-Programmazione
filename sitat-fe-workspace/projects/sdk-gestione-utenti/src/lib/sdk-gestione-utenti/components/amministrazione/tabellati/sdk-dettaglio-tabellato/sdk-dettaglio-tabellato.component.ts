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

import { TabXDTO } from '../../../../model/amministrazione.model';
import { CustomParamsFunction, ResponseDTO } from '../../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GestioneTabellatiService } from '../../../../services/gestione-tabellati.service';
import { FormBuilderUtilsService } from '../../../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-dettaglio-tabellato.component.html'
})
export class SdkDettaglioTabellatoComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-dettaglio-tabellato-section`;

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
  private provenienza: string;
  private codiceTabellato: string;
  private identificativo: string;
  private dettaglio: TabXDTO;

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
      catchError(this.handleError)
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

  private get gestioneTabellatiService(): GestioneTabellatiService { return this.injectable(GestioneTabellatiService) }

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
        provenienza: this.provenienza,
        codiceTabellato: this.codiceTabellato,
        identificativo: this.identificativo
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
          // caricamento campi in base alla provenienza
          fields: cloneDeep(get(this.config, `body.${this.provenienza}_fields`))
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
    this.provenienza = paramsMap.get('provenienza');
    this.codiceTabellato = paramsMap.get('codiceTabellato');
    this.identificativo = paramsMap.get('identificativo');
  }

  private loadDettaglio = (): Observable<ResponseDTO<TabXDTO>> => {
    return this.gestioneTabellatiService.getDettaglioTabellato(this.provenienza, this.codiceTabellato, this.identificativo)
      .pipe(
        catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
          let err: ResponseDTO<any> = error.error;
          if (err != null && err.messages != null && err.messages.length > 0) {
            let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
              let message: SdkMessagePanelTranslate = {
                message: `SDK-TABELLATO.VALIDATORS.${one}`
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

  private elaborateDettaglio = (result: ResponseDTO<TabXDTO>) => {
    this.dettaglio = result.response;
  }

  private elaborateButtons = () => {
    if (this.dettaglio.bloccato === '1') {
      this.buttonsSubj.next(this.buttonsReadonly)
    } else {
      this.buttonsSubj.next(this.buttons);
    }
  }

  private handleError = (err: any) => {
    this.buttonsSubj.next(this.buttonsReadonly);
    return throwError(() => err);
  }

  // #endregion

}
