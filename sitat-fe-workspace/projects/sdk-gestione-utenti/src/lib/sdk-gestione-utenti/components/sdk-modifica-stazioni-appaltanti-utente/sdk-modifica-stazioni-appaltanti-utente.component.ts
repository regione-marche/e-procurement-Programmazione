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
  FilterObject,
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
  SdkMessagePanelService,
  SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, find, findIndex, isEmpty, map as mapArray } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { UfficioIntestatarioCheckDTO, UfficioIntestatarioDTO } from '../../model/gestione-utenti.model';
import { ResponseDTO } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-modifica-stazioni-appaltanti-utente.component.html',
  styleUrls: ['./sdk-modifica-stazioni-appaltanti-utente.component.scss']
})
export class SdkModificaStazioniAppaltantiUtenteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-modifica-stazioni-appaltanti-utente-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public dialogConfigObs: Observable<SdkDialogConfig>;
  public listaStazioniAppaltantiEdit: Array<UfficioIntestatarioCheckDTO>;

  private buttons: SdkButtonGroupInput;
  private userProfile: UserProfile;
  private syscon: number;
  private dialogConfig: SdkDialogConfig;
  private listaStazioniAppaltanti: Array<UfficioIntestatarioDTO>;
  private listaStazioniAppaltantiUtente: Array<UfficioIntestatarioDTO>;
  private currentFilter: FilterObject<UfficioIntestatarioCheckDTO>;

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
    this.checkInfoBox();
    this.loadListaStazioniAppaltanti()
      .pipe(
        map(this.elaborateListaStazioniAppaltanti),
        mergeMap(this.loadListaStazioniAppaltantiUtente),
        map(this.elaborateListaStazioniAppaltantiUtente),
        map(this.elaborateListaStazioniAppaltantiEdit)
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

  private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

  // #endregion

  // #region Public

  public onButtonClick(button: SdkButtonGroupOutput): void {
    if (button != null && isEmpty(button.provider) === false) {
      let data: IDictionary<any> = {
        buttonCode: button.code,
        messagesPanel: this.messagesPanel,
        syscon: this.syscon,
        setUpdateState: this.setUpdateState,
        listaStazioniAppaltanti: this.listaStazioniAppaltantiEdit,
        currentFilter: this.currentFilter
      };

      if (button.code === 'back-to-stazioni-appaltanti-utente') {
        this.back(button, data);
      } else {
        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
      }
    }
  }

  public manageCheckboxes(codiceSA: string, event: any): void {
    let index: number = findIndex(this.listaStazioniAppaltantiEdit, (one: UfficioIntestatarioCheckDTO) => one.codice === codiceSA);
    if (index != null && index > -1) {
      this.listaStazioniAppaltantiEdit[index].checked = event.target.checked === true;
    }
  }

  public onFilter(event: FilterObject<UfficioIntestatarioCheckDTO>): void {
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
    if (obj != null && obj.listaStazioniAppaltantiEdit != null) {
      this.listaStazioniAppaltantiEdit = obj.listaStazioniAppaltantiEdit;
    }
  }

  private loadQueryParams(): void {
    let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    this.syscon = +paramsMap.get('syscon');
  }

  private loadListaStazioniAppaltanti = (): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> => {
    return this.gestioneUtentiService.getListaUfficiIntestatari()
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

  private elaborateListaStazioniAppaltanti = (result: ResponseDTO<Array<UfficioIntestatarioDTO>>) => {
    this.listaStazioniAppaltanti = result.response;
  }

  private loadListaStazioniAppaltantiUtente = (): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> => {
    return this.gestioneUtentiService.getListaUfficiIntestatariUtente(this.syscon)
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

  private elaborateListaStazioniAppaltantiUtente = (result: ResponseDTO<Array<UfficioIntestatarioDTO>>) => {
    this.listaStazioniAppaltantiUtente = result.response;
  }

  private elaborateListaStazioniAppaltantiEdit = () => {
    if (this.listaStazioniAppaltanti != null && this.listaStazioniAppaltanti.length > 0) {
      // carico la lista di tutte le stazioni appaltanti
      this.listaStazioniAppaltantiEdit = mapArray(this.listaStazioniAppaltanti, (one: UfficioIntestatarioDTO) => {
        return {
          ...one,
          checked: false
        };
      });
    }

    if (this.listaStazioniAppaltantiUtente != null && this.listaStazioniAppaltantiUtente.length > 0) {
      // applico il checked a quelle gia' assegnate all'utente
      each(this.listaStazioniAppaltantiEdit, (one: UfficioIntestatarioCheckDTO) => {
        let saPresente: UfficioIntestatarioDTO = find(this.listaStazioniAppaltantiUtente, (item: UfficioIntestatarioDTO) => one.codice === item.codice);
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
