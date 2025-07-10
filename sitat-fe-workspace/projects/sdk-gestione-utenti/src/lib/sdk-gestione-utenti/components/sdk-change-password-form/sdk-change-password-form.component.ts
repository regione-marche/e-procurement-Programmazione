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
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { IDictionary, SdkAbstractWidget, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { isEmpty, isObject } from 'lodash-es';

import { ChangePasswordDTO } from '../../model/authentication.model';
import { ResponseDTO } from '../../model/lib.model';
import { AuthenticationService } from '../../services/authentication.service';

/**
 * Componente di cambio password utente non autenticato
 */
@Component({
  selector: 'sdk-change-password-form',
  templateUrl: './sdk-change-password-form.component.html',
  styleUrls: ['./sdk-change-password-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SdkChangePasswordFormComponent extends SdkAbstractWidget<any> implements OnInit, OnDestroy, AfterViewInit {

  // #region Variables

  @ViewChild('messages') _messagesPanel: ElementRef;

  private _username: string;

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
  }

  protected onAfterViewInit(): void {
    this.addSubscription(this.activatedRoute.queryParamMap.subscribe((paramMap: ParamMap) => {
      const username: string = paramMap.get('username');

      const errorMessage: string = paramMap.get('errorMessage');
      if (!isEmpty(errorMessage)) {
        this.sdkMessagePanelService.showError(this.messagesPanel, [
          {
            message: `SDK-GESTIONE-UTENTI.${errorMessage}`
          }
        ])
      }

      this.username = username;
    }));
  }

  protected onDestroy(): void { }

  protected onConfig(_config: IDictionary<any>): void { }

  protected onUpdateState(_state: boolean): void { }

  // #endregion

  // #region Getters

  private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

  private get formBuilder(): FormBuilder { return this.injectable(FormBuilder) }

  private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

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

    const body: ChangePasswordDTO = {
      username: this.username,
      oldPassword,
      newPassword,
      confirmNewPassword
    };

    this.authenticationService.executeChangePassword(body).subscribe(
      {
        next: (response: ResponseDTO<any>) => {
          this.logger.log('Response >>>', response);
          // cambio password avvenuto con successo
          const params: IDictionary<string> = {
            successMessage: 'CHANGE_PASSWORD_SUCCESS'
          };
          this.sdkRouterService.navigateToPage('internal-login-page', params);
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
    this.sdkRouterService.navigateToPage('internal-login-page');
  }

  // #endregion

  // #region Private

  public get f(): any {
    return this.cambioPasswordForm.controls;
  }

  private get messagesPanel(): HTMLElement {
    return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
  }

  private get username(): string {
    return this._username;
  }

  private set username(value: string) {

    if (isEmpty(value)) {
      this.cambioPasswordForm.disable();
    } else {
      this.cambioPasswordForm.enable();
    }

    this._username = value;

  }

  // #endregion

}
