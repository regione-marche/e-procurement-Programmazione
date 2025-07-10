import { HttpErrorResponse } from '@angular/common/http';
import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {
  IDictionary,
  SdkBusinessAbstractWidget,
  SdkRouterService,
  SdkSessionStorageService,
  SdkStoreService,
  UserProfile,
} from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { isObject } from 'lodash-es';

import { ChangePasswordUserDTO } from '../../model/authentication.model';
import { ResponseDTO } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { AuthenticationService } from '../../services/authentication.service';

/**
 * Componente di cambio password utente autenticato (figura nel menu' a tendina)
 */
@Component({
  selector: 'sdk-change-password-utente-form',
  templateUrl: './sdk-change-password-utente-form.component.html',
  styleUrls: ['./sdk-change-password-utente-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SdkChangePasswordUtenteFormComponent extends SdkBusinessAbstractWidget<any> implements OnInit, OnDestroy, AfterViewInit {

  // #region Variables

  @ViewChild('messages') _messagesPanel: ElementRef;

  private userProfile: UserProfile;
  private homeSlug: string;
  private config: IDictionary<any>;

  public cambioPasswordForm: FormGroup<{
    oldPassword: FormControl<string | null>,
    newPassword: FormControl<string | null>,
    confirmNewPassword: FormControl<string | null>
  }>;

  // #endregion

  constructor(inj: Injector, private cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {
    this.cambioPasswordForm = this.formBuilder.group({
      oldPassword: ['', Validators.required],
      newPassword: ['', Validators.required],
      confirmNewPassword: ['', Validators.required]
    });

    this.cambioPasswordForm.disable();

    this.addSubscription(this.store.select(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));
  }

  protected onAfterViewInit(): void {
    this.checkInternalAuthentication();
  }

  protected onDestroy(): void { }

  protected onConfig(config: IDictionary<any>): void {
    if (config != null) {
      this.config = { ...config };
      this.homeSlug = this.config.homeSlug;
    }
  }

  protected onUpdateState(_state: boolean): void { }

  // #endregion

  // #region Getters

  private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

  private get formBuilder(): FormBuilder { return this.injectable(FormBuilder) }

  private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

  private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

  // #endregion

  // #region Public

  public onSubmit(): void {
    if (this.cambioPasswordForm.invalid) {
      return;
    }

    this.sdkMessagePanelService.clear(this.messagesPanel);

    const oldPassword: string = this.f.oldPassword.value;
    const newPassword: string = this.f.newPassword.value;
    const confirmNewPassword: string = this.f.confirmNewPassword.value;

    if (newPassword !== confirmNewPassword) {

      this.sdkMessagePanelService.showError(this.messagesPanel, [
        {
          message: 'SDK-GESTIONE-UTENTI.CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH'
        }
      ]);

      return;
    }

    const body: ChangePasswordUserDTO = {
      oldPassword,
      newPassword,
      confirmNewPassword
    };

    this.authenticationService.executeChangeUserPassword(body).subscribe(
      {
        next: (response: ResponseDTO<any>) => {
          this.logger.log('Response >>>', response);
          // cambio password avvenuto con successo
          const params: IDictionary<string> = {
            successMessage: 'CHANGE_PASSWORD_SUCCESS'
          };
          this.sdkRouterService.navigateToPage(this.homeSlug, params);
        },
        error: (error: HttpErrorResponse) => {
          const bodyErrore: ResponseDTO<any> = error.error;
          this.logger.error('Errore >>>', bodyErrore);
          const messaggiErrore: Array<SdkMessagePanelTranslate> = bodyErrore.messages.map((m: string) => {
            return { message: `SDK-GESTIONE-UTENTI.${m}` };
          });
          this.sdkMessagePanelService.showError(this.messagesPanel, messaggiErrore);
        }
      }
    );
  }

  public goBack(): void {
    this.sdkRouterService.navigateToPage(this.homeSlug);
  }

  // #endregion

  // #region Private

  public get f(): any {
    return this.cambioPasswordForm.controls;
  }

  private get messagesPanel(): HTMLElement {
    return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
  }

  private checkInternalAuthentication(): void {
    if (this.userProfile != null) {
      if (this.userProfile.internal === true) {
        this.cambioPasswordForm.enable();
      } else {
        this.sdkMessagePanelService.showError(this.messagesPanel, [
          {
            message: `SDK-GESTIONE-UTENTI.CHANGE_PASSWORD_NOT_ALLOWED_SSO_USER`
          }
        ]);
      }
    } else {
      throw new Error('User Profile non valorizzato');
    }
  }

  // #endregion

}
