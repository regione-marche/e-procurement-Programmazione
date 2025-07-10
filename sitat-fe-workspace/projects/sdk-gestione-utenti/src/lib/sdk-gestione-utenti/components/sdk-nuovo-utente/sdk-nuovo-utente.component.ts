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
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { InitRicercaUtentiDTO } from '../../model/gestione-utenti.model';
import { CustomParamsFunction, ResponseDTO, ValoreTabellato } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-nuovo-utente.component.html'
})
export class SdkNuovoUtenteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-nuovo-utente-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
  public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
  public dialogConfigObs: Observable<SdkDialogConfig>;

  private buttons: SdkButtonGroupInput;
  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  private userProfile: UserProfile;
  private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
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
    this.initDialog();
  }

  protected onAfterViewInit(): void {
    this.loadTabellati().pipe(
      map(this.elaborateTabellati),
      mergeMap(this.loadInitRicercaUtenti),
      map(this.elaborateInitRicercaUtenti),
      map(() => this.checkInfoBox()),
      map(() => this.loadForm())
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

  private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

  private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

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
        setUpdateState: this.setUpdateState
      };

      if (button.code === 'back-to-lista-utenti' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
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
          isAmministratore: this.isCurrentUserAmministratore(),
          amministratoreReadonly: '2',
          scadenzaAccount: '1',
          gestioneUtenti: '1',
          controlliGdpr: '1',
          abilitaModificaUfficiIntestatari: '2',
          abilitaTuttiUfficiIntestatari: '2',
          utenteDelegato,
          registrazioneLoginCF,
          gestioneModelli: '1'
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
          let mapping: boolean = true;

          let keyAny: any = get(restObject, field.mappingInput);
          let key: string = dynamicField === true ? field.data : toString(keyAny);

          if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
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

  private isCurrentUserAmministratore(): string {
    let amministratore: string = null;

    if (this.userProfile != null && this.userProfile.abilitazioni != null) {
      let found: boolean = this.userProfile.abilitazioni.includes(SdkGestioneUtentiConstants.OU_89_AMMINISTRATORE);
      amministratore = (found != null) ? '1' : null;
    }

    return amministratore;
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
