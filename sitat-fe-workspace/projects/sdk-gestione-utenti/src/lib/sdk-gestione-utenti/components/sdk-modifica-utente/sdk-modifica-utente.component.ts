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
  SdkDateHelper,
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
  SdkMessagePanelService,
  SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, of, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { InitRicercaUtentiDTO, UserDTO } from '../../model/gestione-utenti.model';
import { CustomParamsFunction, ResponseDTO, ValoreTabellato } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-modifica-utente.component.html'
})
export class SdkModificaUtenteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-modifica-utente-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
  public dialogConfigObs: Observable<SdkDialogConfig>;

  private buttons: SdkButtonGroupInput;
  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  private userProfile: UserProfile;
  private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
  private syscon: number;
  private codice: string; // codice SA se provengo dalla lista SA
  private dettaglio: UserDTO;
  private dialogConfig: SdkDialogConfig;
  private initRicercaUtenti: InitRicercaUtentiDTO;

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
    this.loadTabellati().pipe(
      map(this.elaborateTabellati),
      mergeMap(this.loadInitRicercaUtenti),
      map(this.elaborateInitRicercaUtenti),
      mergeMap(this.loadDettaglio),
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

  private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

  private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

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
        syscon: this.syscon,
        codice: this.codice
      };

      if (button.code === 'back-to-dettaglio-utente' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
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

  private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
    return this.tabellatiCacheService.getValoriTabellati(SdkGestioneUtentiConstants.RICERCA_UTENTI_TABELLATI);
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

        let utenteDelegato: string = this.initRicercaUtenti != null && this.initRicercaUtenti.utenteDelegatoGestioneUtenti ? '1' : '2';
        let registrazioneLoginCF: string = this.initRicercaUtenti != null && this.initRicercaUtenti.registrazioneLoginCF ? '1' : '2';

        // inizializzazione di default
        let form: IDictionary<any> = {
          ...this.dettaglio,
          gestioneUtenti: this.gestioneUtenti(),
          scadenzaAccount: this.dettaglio.dataScadenzaAccount != null ? '2' : '1',
          controlliGdpr: this.controlliGdpr(),
          abilitaModificaUfficiIntestatari: this.abilitaModificaUfficiIntestatari(),
          isCurrentUserAmministratore: this.isCurrentUserAmministratore(),
          amministratore: this.isAmministratore(),
          abilitaModificaUsername: this.abilitaModificaUsername(),
          abilitaTuttiUfficiIntestatari: this.abilitaTuttiUfficiIntestatari(),
          utenteDelegato,
          registrazioneLoginCF,
          gestioneModelli: this.gestioneModelli()
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
          let mapping: boolean = true;

          let keyAny: any = get(restObject, field.mappingInput);
          let key: string = dynamicField === true ? field.data : toString(keyAny);

          if (field.code === 'data-scadenza-account' && field.type === 'TEXT') {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
              field.data = this.dateHelper.format(new Date(parseInt(value, 10)), this.config.locale.dateFormat);
              mapping = false;
            }
          } else if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
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
    this.syscon = +paramsMap.get('syscon');
    this.codice = paramsMap.get('codice');
  }

  private loadDettaglio = (): Observable<ResponseDTO<UserDTO>> => {
    return this.gestioneUtentiService.getDettaglioUtente(this.syscon)
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

  private elaborateDettaglio = (result: ResponseDTO<UserDTO>) => {
    this.dettaglio = result.response;
  }

  private gestioneUtenti(): string {

    if (this.dettaglio != null && this.dettaglio.opzioniUtente) {
      if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_COMPLETA)
        && this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_OU12)) {
        return '2';
      } else if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_COMPLETA)
        && !this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_OU12)) {
        return '3'
      } else if (!this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_COMPLETA)
        && !this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_OU12)) {
        return '1';
      }
    }

    return null;
  }

  private abilitaModificaUfficiIntestatari(): string {
    if (this.dettaglio != null && this.dettaglio.opzioniUtente != null) {
      if (!this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI)) {
        return '1';
      }
    }
    return '2';
  }

  private abilitaTuttiUfficiIntestatari(): string {
    if (this.dettaglio != null && this.dettaglio.opzioniUtente != null) {
      if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI)) {
        return '1';
      }
    }
    return '2';
  }

  private controlliGdpr(): string {
    if (this.dettaglio != null && this.dettaglio.opzioniUtente != null) {
      if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_CONTROLLI_GDPR)) {
        return '1';
      }
    }
    return '2';
  }

  private isCurrentUserAmministratore(): string {
    let amministratore: string = null;

    if (this.userProfile != null && this.userProfile.abilitazioni != null) {
      let found: boolean = this.userProfile.abilitazioni.includes(SdkGestioneUtentiConstants.OU_89_AMMINISTRATORE);
      amministratore = (found != null) ? '1' : null;
    }

    return amministratore;
  }

  private isAmministratore(): string {
    if (this.dettaglio != null && this.dettaglio.opzioniUtente != null) {
      if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_89_AMMINISTRATORE)) {
        return '1';
      }
    }
    return '2';
  }

  private gestioneModelli(): string {
    if (this.dettaglio != null && this.dettaglio.opzioniUtente != null) {
      if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI)) {
        return '2';
      } else if (this.dettaglio.opzioniUtente.includes(SdkGestioneUtentiConstants.OU_GESTIONE_MODELLI_COMPLETA)) {
        return '3';
      }
    }
    return '1';
  }

  private abilitaModificaUsername(): string {
    if (
      this.dettaglio != null &&
      this.dettaglio.dataUltimoAccesso == null &&
      this.dettaglio.syscon != 47 &&
      this.dettaglio.syscon != 48 &&
      this.dettaglio.syscon != 49 &&
      this.dettaglio.syscon != 50
    ) {
      return '1';
    }
    return null;
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

  private loadInitRicercaUtenti = (): Observable<InitRicercaUtentiDTO> => {
    return this.gestioneUtentiService.initRicercaUtenti()
      .pipe(
        map((result: ResponseDTO<InitRicercaUtentiDTO>) => result.response)
      );
  }

  private elaborateInitRicercaUtenti = (result: InitRicercaUtentiDTO): void => {
    this.initRicercaUtenti = result;
  }

  // #endregion

}
