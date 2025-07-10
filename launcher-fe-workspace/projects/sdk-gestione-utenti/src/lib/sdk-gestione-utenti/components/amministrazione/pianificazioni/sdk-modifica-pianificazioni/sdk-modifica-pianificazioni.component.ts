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
  SdkDialogConfig,
  SdkFormBuilderConfiguration,
  SdkFormBuilderField,
  SdkFormBuilderInput,
  SdkMessagePanelService,
  SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, find, get, isEmpty, isEqual, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { WQuartzDTO } from '../../../../model/amministrazione.model';
import { CustomParamsFunction, ResponseDTO } from '../../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GestionePianificazioniService } from '../../../../services/gestione-pianificazioni.service';
import { FormBuilderUtilsService } from '../../../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-modifica-pianificazioni.component.html',
  styleUrls: ['./sdk-modifica-pianificazioni.component.scss']
})
export class SdkModificaPianificazioniComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-modifica-pianificazioni-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
  public dialogConfigObs: Observable<SdkDialogConfig>;
  public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

  private buttons: SdkButtonGroupInput;
  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  private userProfile: UserProfile;
  private codApp: string;
  private beanId: string;
  private dettaglio: WQuartzDTO;
  private dialogConfig: SdkDialogConfig;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    // set update state
    this.setUpdateState(true);

    this.addSubscription(this.store.select<UserProfile>(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
    this.loadQueryParams();
    this.initDialog();
  }

  protected onAfterViewInit(): void {
    this.loadDettaglio().pipe(
      map(this.elaborateDettaglio),
      map(() => this.checkInfoBox()),
      map(() => this.loadForm()),
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

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

  // #endregion

  // #region Public

  public manageFormOutput(config: SdkFormBuilderConfiguration): void {
    this.formBuilderConfig = config;
  }

  public manageOutputField(field: SdkFormBuilderField): void {
    if (field != null) {
      if (field.code === 'secondi' || field.code === 'minuti' || field.code === 'ore' || field.code === 'giorni-del-mese' || field.code === 'mesi' || field.code === 'giorni-della-settimana' || field.code === 'anni') {
        this.setCronExpression(field);
      }
    }
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

      if (button.code === 'back-to-dettaglio-pianificazione' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
        this.back(button, data);
      } else {
        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
      }
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
    this.buttonsSubj = new BehaviorSubject(this.buttons);
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

  private initDialog(): void {
    this.dialogConfig = {
      header: this.translateService.instant('DIALOG.BACK-TITLE'),
      message: this.translateService.instant('DIALOG.BACK-TEXT'),
      acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
      rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
      open: new Subject()
    };
    this.dialogConfigObs = of(this.dialogConfig);
  }

  private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
    let func = this.backConfirm(button, data);
    this.dialogConfig.open.next(func);
  }

  private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
    return () => {
      this.provider.run(button.provider, data).subscribe();
    }
  }

  private setCronExpression(currentField: SdkFormBuilderField): void {
    let seconds: string, minutes: string, hours: string, dayOfMonth: string, month: string, dayOfWeek: string, year: string;

    let section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'general-data');
    each(section.fieldSections, (one: SdkFormBuilderField) => {
      if (one.code === 'secondi') {
        seconds = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          seconds = currentField.data != null ? currentField.data.trim() : '';
        }
      } else if (one.code === 'minuti') {
        minutes = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          minutes = currentField.data != null ? currentField.data.trim() : '';
        }
      } else if (one.code === 'ore') {
        hours = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          hours = currentField.data != null ? currentField.data.trim() : '';
        }
      } else if (one.code === 'giorni-del-mese') {
        dayOfMonth = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          dayOfMonth = currentField.data != null ? currentField.data.trim() : '';
        }
      } else if (one.code === 'mesi') {
        month = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          month = currentField.data != null ? currentField.data.trim() : '';
        }
      } else if (one.code === 'giorni-della-settimana') {
        dayOfWeek = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          dayOfWeek = currentField.data != null ? currentField.data.trim() : '';
        }
      } else if (one.code === 'anni') {
        year = one.data != null ? one.data.trim() : '';
        if (currentField.code === one.code) {
          year = currentField.data != null ? currentField.data.trim() : '';
        }
      }
    });

    let expression: string = '';
    expression += seconds;
    expression += ' ' + minutes;
    expression += ' ' + hours;
    expression += ' ' + dayOfMonth;
    expression += ' ' + month;
    expression += ' ' + dayOfWeek;
    expression += ' ' + year;
    expression = expression.trim();

    this.formBuilderDataSubject.next({
      code: 'pianificazione',
      data: expression
    });
  }

  // #endregion

}
