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
  SdkBusinessAbstractWidget,
  SdkProviderService,
  SdkRouterService,
  SdkStoreService,
  UserProfile,
} from '@maggioli/sdk-commons';
import {
  SdkButtonGroupInput,
  SdkButtonGroupOutput,
  SdkMessagePanelService,
  SdkRadioConfig,
  SdkRadioItem,
  SdkRadioOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, find, get, isEmpty } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-check-nuovo-utente.component.html'
})
export class SdkCheckNuovoUtenteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-check-nuovo-utente-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public selezioneUtenteConfigSubject: BehaviorSubject<SdkRadioConfig> = new BehaviorSubject(null);

  private buttons: SdkButtonGroupInput;
  private userProfile: UserProfile;
  private selezioneUtenteConfig: SdkRadioConfig;
  private selezioneUtenteData: SdkRadioItem;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    this.addSubscription(this.store.select<UserProfile>(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
  }

  protected onAfterViewInit(): void {
    this.checkLDAP();
    this.checkInfoBox();
    this.loadForm();
  }

  protected onDestroy(): void { }

  protected onConfig(config: any): void {
    if (config != null) {
      this.markForCheck(() => {
        this.config = { ...config };
      });
    }
  }

  protected onUpdateState(_state: boolean): void { }

  // #endregion

  // #region Getters

  private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

  // #endregion

  // #region Public

  public manageRadioOutput(config: SdkRadioOutput) {
    if (config != null) {
      this.selezioneUtenteData = config.data;
    }
  }

  public onButtonClick(button: SdkButtonGroupOutput): void {
    if (button != null && isEmpty(button.provider) === false) {
      this.addSubscription(
        this.provider.run(button.provider, {
          buttonCode: button.code,
          selezioneUtenteData: this.selezioneUtenteData
        }).subscribe(this.manageExecutionProvider)
      );
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

  private loadForm(): void {
    const selezioneUtenteConfig: SdkRadioConfig = cloneDeep(get(this.config, 'body.selezioneUtente'));
    if (selezioneUtenteConfig != null) {
      this.selezioneUtenteConfig = selezioneUtenteConfig;

      each(this.selezioneUtenteConfig.choices, (choice: SdkRadioItem) => {
        if (choice.checked === true) {
          this.selezioneUtenteData = choice;
        }
      });

      this.selezioneUtenteConfigSubject.next(this.selezioneUtenteConfig);
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

  private checkLDAP(): void {
    if (this.userProfile.opzioniProdotto != null) {
      let found: string = find(this.userProfile.opzioniProdotto, (opzioneProdotto: string) => {
        return opzioneProdotto === SdkGestioneUtentiConstants.OP_100_LDAP;
      });

      if (found == null) {
        this.routerService.navigateToPage('nuovo-utente-page');
      } else {
        this.isReady = true;
      }
    }
  }

  // #endregion

}
