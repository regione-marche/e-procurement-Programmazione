import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostBinding,
  Inject,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
  FilterObject,
  IDictionary,
  SDK_APP_CONFIG,
  SdkAppEnvConfig,
  SdkBusinessAbstractWidget,
  SdkProviderService,
  SdkStoreService,
  UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, find, findIndex, isEmpty, map as mapArray } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../app-commons.constants';
import { UsrSysconCheckEntry, UsrSysconEntry } from '../../../../models/user/user.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { UserService } from '../../../../services/user/user.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';

@Component({
  templateUrl: './modifica-utenti-stazione-appaltante.component.html'
})
export class ModificaUtentiStazioneAppaltanteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `modifica-utenti-stazione-appaltante-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public dialogConfigObs: Observable<SdkDialogConfig>;
  public listaUtentiEdit: Array<UsrSysconCheckEntry>;

  private buttons: SdkButtonGroupInput;
  private userProfile: UserProfile;
  private codice: string;
  private dialogConfig: SdkDialogConfig;
  private listaUtenti: Array<UsrSysconEntry>;
  private listaUtentiStazioneAppaltante: Array<UsrSysconEntry>;
  private currentFilter: FilterObject<UsrSysconCheckEntry>;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    // set update state
    this.setUpdateState(true);

    this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
    this.loadQueryParams();
    this.initDialog();
  }

  protected onAfterViewInit(): void {
    this.checkInfoBox();
    this.loadListaUtenti()
      .pipe(
        map(this.elaborateListaUtenti),
        mergeMap(this.loadUtentiStazioneAppaltante),
        map(this.elaborateUtentiStazioneAppaltante),
        map(this.elaborateListaUtentiEdit)
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

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

  private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

  private get userService(): UserService { return this.injectable(UserService) }

  // #endregion

  // #region Public

  public onButtonClick(button: SdkButtonGroupOutput): void {
    if (button != null && isEmpty(button.provider) === false) {
      let data: IDictionary<any> = {
        buttonCode: button.code,
        messagesPanel: this.messagesPanel,
        codice: this.codice,
        setUpdateState: this.setUpdateState,
        listaUtenti: this.listaUtentiEdit,
        currentFilter: this.currentFilter
      };

      if (button.code === 'back-to-utenti-stazione-appaltante') {
        this.back(button, data);
      } else {
        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
      }
    }
  }

  public manageCheckboxes(syscon: number, event: any): void {
    let index: number = findIndex(this.listaUtentiEdit, (one: UsrSysconCheckEntry) => one.syscon === syscon);
    if (index != null && index > -1) {
      this.listaUtentiEdit[index].checked = event.target.checked === true;
    }
  }

  public onFilter(event: FilterObject<UsrSysconCheckEntry>): void {
    this.currentFilter = event;
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

  private loadButtons(): void {
    this.buttons = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
    };
    this.buttonsSubj = new BehaviorSubject(this.buttons);
  }

  private manageExecutionProvider = (obj: any) => {
    if (obj != null && obj.listaUtentiEdit != null) {
      this.listaUtentiEdit = obj.listaUtentiEdit;
    }
  }

  private loadQueryParams(): void {
    let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    this.codice = paramsMap.get('codice');
  }

  private loadListaUtentiFactory() {
    return () => {
      return this.userService.getAllUtenti(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL);
    };
  }

  private loadListaUtenti = (): Observable<Array<UsrSysconEntry>> => {
    let factory = this.loadListaUtentiFactory();
    return this.requestHelper.begin(factory, this.messagesPanel);
  }

  private elaborateListaUtenti = (result: Array<UsrSysconEntry>) => {
    this.listaUtenti = result;
  }

  private loadUtentiStazioneAppaltanteFactory(codice: string) {
    return () => {
      return this.userService.getUtentiStazioneAppaltante(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codice);
    };
  }

  private loadUtentiStazioneAppaltante = (): Observable<Array<UsrSysconEntry>> => {
    let factory = this.loadUtentiStazioneAppaltanteFactory(this.codice);
    return this.requestHelper.begin(factory, this.messagesPanel);
  }

  private elaborateUtentiStazioneAppaltante = (result: Array<UsrSysconEntry>) => {
    this.listaUtentiStazioneAppaltante = result;
  }

  private elaborateListaUtentiEdit = () => {
    if (this.listaUtenti != null && this.listaUtenti.length > 0) {
      // carico la lista di tutti gli utenti
      this.listaUtentiEdit = mapArray(this.listaUtenti, (one: UsrSysconEntry) => {
        return {
          ...one,
          checked: false
        };
      });
    }

    if (this.listaUtentiStazioneAppaltante != null && this.listaUtentiStazioneAppaltante.length > 0) {
      // applico il checked a quelle gia' assegnate alla stazione appaltante
      each(this.listaUtentiEdit, (one: UsrSysconCheckEntry) => {
        let saPresente: UsrSysconEntry = find(this.listaUtentiStazioneAppaltante, (item: UsrSysconEntry) => one.syscon === item.syscon);
        if (saPresente != null) {
          one.checked = true;
        }
      });
    }
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

  // #endregion

}
