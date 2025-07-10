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
  IDictionary,
  SDK_APP_CONFIG,
  SdkAppEnvConfig,
  SdkBusinessAbstractWidget,
  SdkProviderService,
  SdkStoreService,
  UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMenuTab, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isEmpty, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../app-commons.constants';
import { UsrSysconEntry } from '../../../../models/user/user.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { UserService } from '../../../../services/user/user.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';

@Component({
  templateUrl: './utenti-stazione-appaltante.component.html',
  styleUrls: ['./utenti-stazione-appaltante.component.scss']
})
export class UtentiStazioneAppaltanteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `utenti-stazione-appaltante-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public utentiStazioneAppaltante: Array<UsrSysconEntry>;

  private buttons: SdkButtonGroupInput;
  private userProfile: UserProfile;
  private codice: string;
  private menuTabs: Array<SdkMenuTab>;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
    this.loadQueryParams();
  }

  protected onAfterViewInit(): void {
    this.refreshTabs();
    this.checkInfoBox();
    this.loadUtentiStazioneAppaltante()
      .pipe(
        map(this.elaborateUtentiStazioneAppaltante)
      ).subscribe();
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

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

  private get userService(): UserService { return this.injectable(UserService) }

  // #endregion

  // #region Public

  public onButtonClick(button: SdkButtonGroupOutput): void {
    if (button != null && isEmpty(button.provider) === false) {
      let data: IDictionary<any> = {
        buttonCode: button.code,
        messagesPanel: this.messagesPanel,
        codice: this.codice
      };

      this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
    }
  }

  public trackByCode(index: number, utente: UsrSysconEntry): string {
    return utente != null ? toString(utente.syscon) : toString(index);
  }

  public loadUtente(ut: UsrSysconEntry): void {
    if (ut != null) {

      let data: IDictionary<any> = {
        buttonCode: 'back-to-dettaglio-utente-stazione-appaltante',
        messagesPanel: this.messagesPanel,
        codice: this.codice,
        syscon: ut.syscon
      };

      this.provider.run('APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI', data).subscribe(this.manageExecutionProvider);
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

  private loadButtons(): void {
    this.buttons = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
    };
    this.buttonsSubj = new BehaviorSubject(this.buttons);
  }

  private manageExecutionProvider = (obj: any) => {
    if (obj != null && obj.cleanSearch === true) {
    }
  }

  private loadQueryParams(): void {
    let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    this.codice = paramsMap.get('codice');
  }

  private refreshTabs(): void {
    remove(this.menuTabs, (one: SdkMenuTab) => {
      if (!isEmpty(one.oggettoProtezione)) {
        let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
        if (visible !== true) {
          return true;
        }
      }
      if (!isEmpty(one.visible)) {
        let visible: boolean = this.provider.run(one.visible, {
          userProfile: this.userProfile
        });
        return visible === false;
      }
      return false;
    });
    this.sdkLayoutMenuTabs.emit(this.menuTabs);
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
    this.utentiStazioneAppaltante = result;
  }

  // #endregion

}
