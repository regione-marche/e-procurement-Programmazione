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
  SdkMessagePanelService,
  SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, find, findIndex, isEmpty, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { ProfiloDTO, ProfiloCheckDTO } from '../../model/gestione-utenti.model';
import { ResponseDTO } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-modifica-profili-utente.component.html',
  styleUrls: ['./sdk-modifica-profili-utente.component.scss']
})
export class SdkModificaProfiliUtenteComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-modifica-profili-utente-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public dialogConfigObs: Observable<SdkDialogConfig>;
  public listaProfiliEdit: Array<ProfiloCheckDTO>;

  private buttons: SdkButtonGroupInput;
  private userProfile: UserProfile;
  private syscon: number;
  private dialogConfig: SdkDialogConfig;
  private listaProfili: Array<ProfiloDTO>;
  private listaProfiliUtente: Array<ProfiloDTO>;

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
    this.loadListaProfili()
      .pipe(
        map(this.elaborateListaProfili),
        mergeMap(this.loadProfiliUtente),
        map(this.elaborateProfiliUtente),
        map(this.elaborateListaProfiliEdit)
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
        listaProfili: this.listaProfiliEdit
      };

      if (button.code === 'back-to-profili-utente') {
        this.back(button, data);
      } else {
        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
      }
    }
  }

  public trackByCode(index: number, profilo: ProfiloDTO): string {
    return profilo != null ? profilo.codice : toString(index);
  }

  public manageCheckboxes(codiceProfilo: string, event: any): void {
    let index: number = findIndex(this.listaProfiliEdit, (one: ProfiloCheckDTO) => one.codice === codiceProfilo);
    if (index != null && index > -1) {
      this.listaProfiliEdit[index].checked = event.target.checked === true;
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
    this.syscon = +paramsMap.get('syscon');
  }

  private loadListaProfili = (): Observable<ResponseDTO<Array<ProfiloDTO>>> => {
    return this.gestioneUtentiService.getListaProfili()
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

  private elaborateListaProfili = (result: ResponseDTO<Array<ProfiloDTO>>) => {
    this.listaProfili = result.response;
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
    this.listaProfiliUtente = result.response;
  }

  private elaborateListaProfiliEdit = () => {
    if (this.listaProfili != null && this.listaProfili.length > 0) {
      // carico la lista di tutti i profili
      this.listaProfiliEdit = mapArray(this.listaProfili, (one: ProfiloDTO) => {
        return {
          ...one,
          checked: false
        };
      });

      if (this.listaProfiliUtente != null && this.listaProfiliUtente.length > 0) {
        // applico il checked a quelli gia' assegnati all'utente
        each(this.listaProfiliEdit, (one: ProfiloCheckDTO) => {
          let profiloPresente: ProfiloDTO = find(this.listaProfiliUtente, (item: ProfiloDTO) => one.codice === item.codice);
          if (profiloPresente != null) {
            one.checked = true;
          }
        });
      }

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
