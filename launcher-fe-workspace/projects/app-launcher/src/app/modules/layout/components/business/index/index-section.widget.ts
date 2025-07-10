import { HttpErrorResponse } from '@angular/common/http';
import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkRedirectService,
    SdkRouterService,
    SdkSessionStorageService,
    SdkStoreAction,
    SdkStoreService,
    SdkTestataProfileMessageService,
    UserProfile,
    decodeJwt,
} from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { AppHomeIndexPanel } from '@maggioli/sdk-widgets';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty, join, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../../../../../environments/environment';
import { Constants } from '../../../../../app.constants';
import {
    AuthenticationTokenInfo,
    RichiestaTokenResponse,
    SSOTokenInfo,
    TokenContent,
    UserExistsInfo
} from '../../../../models/authentication/authentication.model';
import { ResponseResult } from '../../../../models/common/common.model';
import { AuthenticationService } from '../../../../services/authentication/authentication.service';

@Component({
    templateUrl: `index-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class IndexSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `index-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};

    public isError: boolean = false;
    public error: string = '';
    public showInternalAuthentication: boolean = false;
    public showRichiestaAssistenza: boolean = false;

    private userProfile: UserProfile;
    private userProfileToscana: any;

    constructor(inj: Injector, cdr: ChangeDetectorRef, private route: ActivatedRoute) { super(inj, cdr) }

    // #region Hooks


    protected onInit(): void {

        let error: string = this.checkError();
        if (!isEmpty(error)) {
            this.error = join(['ERRORS', error], '.');
        } else {
            // check login
            let auth: RichiestaTokenResponse = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
            if (auth != null) {
                this.storeUserAuthInfo(auth);
            }

            if (auth == null) {
                // load authorization code
                let authorizationCode: string = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_CODE, Constants.LOCAL_STORAGE_PREFIX);
                if (!isEmpty(authorizationCode)) {
                    const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);

                    const baseUrl: string = this.authenticationService.getApplicationLoginConfiguration(moduloSelezionato).AUTHENTICATION_GET_TOKEN_BASE_URL;
                    this.authenticationService.retrieveAuthenticationToken(baseUrl, authorizationCode).subscribe(
                        {
                            next: (result: ResponseResult<SSOTokenInfo>) => {
                                const response: RichiestaTokenResponse = {
                                    token: result.data.jwtToken,
                                    dataCreazione: this.sdkDateHelper.format(result.data.createdAt, Constants.SERVER_DATE_FORMAT),
                                    refreshToken: result.data.refreshToken
                                };
                                this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, response, Constants.LOCAL_STORAGE_PREFIX);
                                this.sdkSessionStorageService.removeItem(Constants.AUTHENTICATION_CODE, Constants.LOCAL_STORAGE_PREFIX);

                                if (response != null) {
                                    this.storeUserAuthInfo(response);
                                }
                                this.navigateInternal(response, moduloSelezionato, result.data.loa);
                            },
                            error: (err: HttpErrorResponse) => {
                                this.logger.error('Errore retrieveAuthenticationToken', err);
                                this.sdkSessionStorageService.removeItem(Constants.AUTHENTICATION_CODE, Constants.LOCAL_STORAGE_PREFIX);
                            }
                        }
                    );
                } else {
                    // non ho authorization code quindi eseguo il check di singola applicazione
                    this.checkSingleApp();
                }
            } else {
                // si renderizza normalmente quindi verifico la presenza di una singola applicazione per fare
                // redirect alla pagina di login
                this.checkSingleApp();
            }
        }
    }

    protected onAfterViewInit(): void {
        if (!isEmpty(this.error)) {
            this.sdkMessagePanelService.showError(this.messagesPanel, [
                {
                    message: this.error
                }
            ]);
        }

        this.loadRichiestaAssistenza();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.elaboratePanels();
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public track(index: number, _panel: AppHomeIndexPanel): string { return toString(index) }

    public navigate(panel: AppHomeIndexPanel): void {
        if (panel != null) {
            let auth: RichiestaTokenResponse = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
            if (auth != null) {
                this.navigateInternal(auth, panel, null);
            } else if(environment.EXTERNAL_LOGIN_PAGE) {
                let panel: AppHomeIndexPanel = this.config.panels.items[0];
                this.sdkSessionStorageService.setItem(Constants.CURRENT_MODULE, panel, Constants.LOCAL_STORAGE_PREFIX);
                let url: string = environment.PANELS_CONFIGURATION[panel.panelCode]['SSO'][0]['AUTHENTICATION_SSO_LOGIN_URL'];
                this.sdkRedirectService.redirect(url);
            } else {
                this.gotoLoginPage(panel);
            }
        }
    }

    public navigateRichiestaAssistenza(): void {
        this.sdkRouterService.navigateToPage('richiesta-assistenza-page');
    }

    public getLinkAriaLabel(label1: string, title: string): string {
        return this.translateService.instant(label1) + ': ' + this.translateService.instant(title);
    }

    // #endregion

    // #region Getters

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get testataProfile(): SdkTestataProfileMessageService { return this.injectable(SdkTestataProfileMessageService) }

    // #endregion

    // #region Private

    private checkError(): string {
        let paramMap: ParamMap = this.route.snapshot.queryParamMap;
        let error: string = paramMap.get('error');
        return error;
    }

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private navigateInternal(response: RichiestaTokenResponse, moduloSelezionato: AppHomeIndexPanel, loa: string): void {

        const login: string = this.userProfile.login;

        const baseUrl: string = this.authenticationService.getApplicationLoginConfiguration(moduloSelezionato).AUTHENTICATION_CHECK_USER_BASE_URL;

        this.getUserInfo(baseUrl, login).pipe(
            map((result: UserExistsInfo) => {
                if (result.exist === 'OK_USER') {
                    this.authenticationService.clearAuthInfo();
                    let redirectUrl: string = get(environment, moduloSelezionato.targetUrl);
                    let params: IDictionary<string> = {
                        auth: JSON.stringify(response),
                        appCode: moduloSelezionato.panelCode,
                        loa: loa
                    }
                    this.sdkRedirectService.redirect(redirectUrl, params);
                } else if (result.exist === 'NO_USER' && result.abilitaRegistrazione) {
                    this.sdkSessionStorageService.setItem(Constants.CURRENT_MODULE, moduloSelezionato, Constants.LOCAL_STORAGE_PREFIX);
                    this.sdkRouterService.navigateToPage('registrazione-utente-page');
                } else if (result.exist === 'DISABLED_USER') {
                    // Caso di utente registrato ma disabilitato. Forward a pagina di cortesia
                    this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '2', cf: login });
                } else if (result.exist === 'EXPIRED_USER') {
                    // Caso di utente registrato ma con ultimo accesso più vecchio di dutrata Account.
                    this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '4', cf: login });
                } else {
                    this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '3', cf: login });
                }
            })
        ).subscribe({
            error: (error: HttpErrorResponse) => {
                if (environment.LOGIN_MODE != 0 && error.status == 403) {
                    this.sdkMessagePanelService.showError(this.messagesPanel, [
                        {
                            message: 'Si e verificato un errore, si chiede di eseguire l\'autenticazione nel modulo selezionato'
                        }
                    ]);
                    this.sdkSessionStorageService.clear(Constants.LOCAL_STORAGE_PREFIX);
                    this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, undefined));
                    this.testataProfile.emit(false);
                }
            }
        });

    }

    private storeUserAuthInfo(auth: AuthenticationTokenInfo): void {
        let userInfo: any = decodeJwt(auth.token);

        if (environment.LOGIN_MODE == 0) {

            let userProfileToLauncher: UserProfile = this.sdkSessionStorageService.getItem(Constants.USER_PROFILE_TO_LAUNCHER, Constants.LOCAL_STORAGE_PREFIX);

            let cf: string = userInfo.preferred_username;
            if (cf.startsWith('tinit-'))
                cf = cf.replace('tinit-', '');
            this.userProfile = {
                nome: userInfo.given_name,
                cognome: userInfo.family_name,
                codiceFiscale: cf,
                login: cf
            };
            // vado a leggere il nome e cognome da modulo esterno perche' non presenti nel token Toscana
            if (userProfileToLauncher != null && isEmpty(this.userProfile.nome) && isEmpty(this.userProfile.cognome)) {
                this.userProfile.nome = userProfileToLauncher.nome;
                this.userProfile.cognome = userProfileToLauncher.cognome;
            }
        } else {

            // load logout url spid
            if (auth.token != null) {
                let userInfo: TokenContent = decodeJwt(auth.token);
                if (userInfo.SSO_LOGOUT_URL != null)
                    this.sdkSessionStorageService.setItem(Constants.LOGOUT_PATH, userInfo.SSO_LOGOUT_URL, Constants.LOCAL_STORAGE_PREFIX);
            }

            this.userProfile = {
                nome: userInfo.USER_NAME,
                cognome: userInfo.USER_SURNAME,
                codiceFiscale: userInfo.USER_CF,
                login: userInfo.USER_LOGIN,
            };
        }

        this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
    }

    private gotoLoginPage(panel: AppHomeIndexPanel): void {
        if (panel != null) {
            this.sdkSessionStorageService.setItem(Constants.CURRENT_MODULE, panel, Constants.LOCAL_STORAGE_PREFIX);
            this.sdkRouterService.navigateToPage('login-page');
        }
    }

    private getUserInfo(baseUrl: string, codiceFiscaleUtente: string): Observable<UserExistsInfo> {
        return this.authenticationService.checkExistingUser(baseUrl, codiceFiscaleUtente);
    }



    private loadRichiestaAssistenza(): void {
        this.authenticationService.isRichiestaAssistenzaAttiva(environment.GESTIONE_UTENTI_PUBLIC_BASE_URL).subscribe((attiva: boolean) => {
            this.markForCheck(() => this.showRichiestaAssistenza = attiva);
        });
    }

    private checkSingleApp(): void {
        if (this.config.panels.items != null && this.config.panels.items.length == 1 && environment.SINGLE_APP === true) {
            let panel: AppHomeIndexPanel = this.config.panels.items[0];
            this.gotoLoginPage(panel);
        }
    }

    private elaboratePanels(): void {
        if (this.config != null && this.config.panels != null && this.config.panels.items != null) {
            each(this.config.panels.items, (one: AppHomeIndexPanel) => {
                if (environment.PANELS_CONFIGURATION[one.panelCode] != null && environment.PANELS_CONFIGURATION[one.panelCode].disabled === true) {
                    one.disabled = true;
                }
            });
        }
    }

    // #endregion
}
