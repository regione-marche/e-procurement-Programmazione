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
  SdkMenuTab,
  SdkMessagePanelService,
  SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { isEmpty, map as mapArray, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { InitRicercaUtentiDTO, ProfiloDTO, UserDTO } from '../../model/gestione-utenti.model';
import { ResponseDTO } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-profili-utente.component.html',
  styleUrls: ['./sdk-profili-utente.component.scss']
})
export class SdkProfiliUtenteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-profili-utente-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
  public profiliUtente: Array<ProfiloDTO>;

  private buttons: SdkButtonGroupInput;
  private buttonsReadonly: SdkButtonGroupInput;
  private userProfile: UserProfile;
  private syscon: number;
  private menuTabs: Array<SdkMenuTab>;
  private utente: UserDTO;
  private initRicercaUtenti: InitRicercaUtentiDTO;

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
    this.checkInfoBox();
    this.loadUtente()
      .pipe(
        map(this.elaborateUtente),
        mergeMap(this.loadInitRicercaUtenti),
        map(this.elaborateInitRicercaUtenti),
        map(() => this.refreshTabs()),
        mergeMap(this.loadProfiliUtente),
        map(this.elaborateProfiliUtente),
        map(this.elaborateButtons),
        catchError(this.handleError)
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

  private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

  // #endregion

  // #region Public

  public onButtonClick(button: SdkButtonGroupOutput): void {
    if (button != null && isEmpty(button.provider) === false) {
      let data: IDictionary<any> = {
        buttonCode: button.code,
        messagesPanel: this.messagesPanel,
        syscon: this.syscon
      };

      this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
    }
  }

  public trackByCode(index: number, profilo: ProfiloDTO): string {
    return profilo != null ? profilo.codice : toString(index);
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

    this.buttonsReadonly = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsReadonly, this.userProfile.configurations)
    };
  }

  private manageExecutionProvider = (obj: any) => {
    if (obj != null && obj.cleanSearch === true) {
    }
  }

  private loadQueryParams(): void {
    let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    this.syscon = +paramsMap.get('syscon');
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
          utente: this.utente,
          initRicercaUtenti: this.initRicercaUtenti
        });
        return visible === false;
      }
      return false;
    });
    this.sdkLayoutMenuTabs.emit(this.menuTabs);
  }

  private loadUtente = (): Observable<ResponseDTO<UserDTO>> => {
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

  private elaborateUtente = (result: ResponseDTO<UserDTO>) => {
    this.utente = result.response;
  }

  private loadProfiliUtente = (): Observable<ResponseDTO<Array<ProfiloDTO>>> => {
    return this.gestioneUtentiService.getListaProfiliUtente(this.syscon)
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

  private elaborateProfiliUtente = (result: ResponseDTO<Array<ProfiloDTO>>) => {
    this.profiliUtente = result.response;
  }

  private elaborateButtons = () => {
    let hasDifferentUfficiIntestatari: boolean = this.initRicercaUtenti.utenteDelegatoGestioneUtenti == true && this.gestioneUtentiService.userHasDifferentUfficiIntestatariThanSessionUser(this.utente.ufficiIntestatari, this.initRicercaUtenti.stazioniAppaltantiAssociate);
    if (this.isGestioneCompletaUtenteEnabled() && !hasDifferentUfficiIntestatari) {
      this.buttonsSubj.next(this.buttons);
    } else {
      this.buttonsSubj.next(this.buttonsReadonly);
    }
  }

  private isGestioneCompletaUtenteEnabled(): boolean {
    if (this.userProfile.abilitazioni != null && this.userProfile.abilitazioni.length > 0) {
      return this.userProfile.abilitazioni.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_COMPLETA) &&
        !this.userProfile.abilitazioni.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_OU12);
    }
    return false;
  }

  private handleError = (err: any) => {
    this.buttonsSubj.next(this.buttonsReadonly);
    return throwError(() => err);
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
