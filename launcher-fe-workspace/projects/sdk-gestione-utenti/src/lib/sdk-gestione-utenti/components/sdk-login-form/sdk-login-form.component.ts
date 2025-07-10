import { HttpErrorResponse } from '@angular/common/http';
import {
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Inject,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { IDictionary, SdkAbstractWidget, SdkAppEnvConfig, SdkProviderService, SdkRedirectService, SdkRouterService, SDK_APP_CONFIG } from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isEmpty, isObject } from 'lodash-es';

import { AuthenticationDTO, CheckMTokenDTO, LoginSuccessDTO } from '../../model/authentication.model';
import { ResponseDTO } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'sdk-login-form',
  templateUrl: './sdk-login-form.component.html',
  styleUrls: ['./sdk-login-form.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SdkLoginFormComponent extends SdkAbstractWidget<any> implements OnInit, OnDestroy, AfterViewInit {

  // #region Variables

  @ViewChild('messages') public _messagesPanel: ElementRef;

  public loginForm: FormGroup<{
    username: FormControl<string | null>,
    password: FormControl<string | null>
  }>;

  public mTokenForm: FormGroup<{
    certificatoFile: FormControl<File | null>,
    certificatoText: FormControl<string | null>,
    motivazione: FormControl<string | null>,
  }>;

  public registrazioneUtenteAttiva: boolean = false;
  public step: number = 1; // 1 = login, 2 = m-token

  private loginBody: AuthenticationDTO = null;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.mTokenForm = this.formBuilder.group({
      certificatoFile: this.formBuilder.control(null),
      certificatoText: this.formBuilder.control(null),
      motivazione: this.formBuilder.control(null, [Validators.required])
    });
  }

  protected onAfterViewInit(): void {
    this.addSubscription(this.activatedRoute.queryParamMap.subscribe((paramMap: ParamMap) => {
      const successMessage: string = paramMap.get('successMessage');
      if (!isEmpty(successMessage)) {
        this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
          {
            message: `SDK-GESTIONE-UTENTI.${successMessage}`
          }
        ])
      }
    }));
    this.authenticationService.isRegistrazioneAttiva().subscribe((active: boolean) => {
      if (active != null) {
        this.markForCheck(() => {
          this.registrazioneUtenteAttiva = !!active;
        });
      }
    });
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

  private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  // #endregion

  // #region Public

  public onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.sdkMessagePanelService.clear(this.messagesPanel);

    const username: string = this.f.username.value;
    const password: string = this.f.password.value;

    const body: AuthenticationDTO = {
      username,
      password
    };

    this.loginBody = body;

    this.executeCheckMToken(username);
  }

  public async onMTokenSubmit(): Promise<void> {
    if (this.mTokenForm.invalid) {
      return;
    }

    this.sdkMessagePanelService.clear(this.messagesPanel);

    const certificatoFile: File = this.fm.certificatoFile.value;
    const certificatoText: string = this.fm.certificatoText.value;
    const motivazione: string = this.fm.motivazione.value;

    if (certificatoFile == null && isEmpty(certificatoText)) {
      this.sdkMessagePanelService.showError(this.messagesPanel, [
        {
          message: `SDK-GESTIONE-UTENTI.LOGIN_MTOKEN_CERT_NOT_FOUND`
        }
      ]);
    }

    let certificato: string;
    if (certificatoFile != null) {
      certificato = await new Promise<string>((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = function (event) {
          resolve(reader.result as string);
        }
        reader.readAsText(certificatoFile);
      });
    } else {
      certificato = certificatoText;
    }

    this.loginBody.certificato = certificato;
    this.loginBody.motivazione = motivazione;

    this.executeLogin();
  }

  public goBack(): void {
    this.sdkRedirectService.redirect(this.appConfig.environment.APP_LAUNCHER_CONTEXT_URL);
  }

  public goMTokenBack(): void {
    this.mTokenForm.reset();
    this.loginForm.reset();
    this.markForCheck(() => this.step = 1);
  }

  public navigatePasswordRecovery(): void {
    this.sdkRouterService.navigateToPage('recupero-password-page');
  }

  public navigateUserRegistration(): void {
    this.sdkRedirectService.redirect(this.appConfig.environment.APP_LAUNCHER_CONTEXT_URL, { panelCodeRegistrazione: this.appConfig.environment.APP_CODE });
  }

  public onFileChange(event: Event): void {
    const file = (event.target as HTMLInputElement).files[0];
    this.mTokenForm.patchValue({ certificatoFile: file });
  }

  // #endregion

  // #region Private

  public get f(): any {
    return this.loginForm.controls;
  }

  public get fm(): any {
    return this.mTokenForm.controls;
  }

  private get messagesPanel(): HTMLElement {
    return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
  }

  private executeLogin(): void {
    this.authenticationService.executeLogin(this.loginBody).subscribe(
      {
        next: (response: ResponseDTO<LoginSuccessDTO>) => {
          const args: IDictionary<any> = {
            token: response.response.token,
            refreshToken: response.response.refreshToken
          };
          this.provider.run('SDK_INTERNAL_LOGIN', args).subscribe();
        },
        error: (error: HttpErrorResponse) => {
          const bodyErrore: ResponseDTO<LoginSuccessDTO> = error.error;
          this.logger.error('Errore >>>', bodyErrore);
          const messaggioErrore: string = bodyErrore.messages[0];
          if (!isEmpty(messaggioErrore)) {

            if (!messaggioErrore.includes('MTOKEN'))
              this.markForCheck(() => this.step = 1);

            this.sdkMessagePanelService.showError(this.messagesPanel, [
              {
                message: `SDK-GESTIONE-UTENTI.${messaggioErrore}`
              }
            ]);

            if (messaggioErrore === SdkGestioneUtentiConstants.LOGIN_FIRST_ACCESS ||
              messaggioErrore === SdkGestioneUtentiConstants.LOGIN_PASSWORD_EXPIRED) {
              // cambio password
              const params: IDictionary<string> = {
                username: this.loginBody.username,
                errorMessage: messaggioErrore
              };
              this.sdkRouterService.navigateToPage('cambio-password-page', params);
            }

          }
        }
      }
    );
  }

  private executeCheckMToken(username: string): void {
    let body: CheckMTokenDTO = {
      username
    };
    this.authenticationService.executeCheckMToken(body).subscribe(
      {
        next: (response: ResponseDTO<boolean>) => {
          if (response != null) {
            if (response.response == true) {
              this.markForCheck(() => this.step = 2);
            } else {
              this.executeLogin();
            }
          }
        },
        error: (error: HttpErrorResponse) => {
          const bodyErrore: ResponseDTO<boolean> = error.error;
          this.logger.error('Errore >>>', bodyErrore);
          const messaggioErrore: string = bodyErrore.messages[0];
          if (!isEmpty(messaggioErrore)) {

            this.sdkMessagePanelService.showError(this.messagesPanel, [
              {
                message: `SDK-GESTIONE-UTENTI.${messaggioErrore}`
              }
            ]);
          }
        }
      }
    );
  }

  // #endregion

}
