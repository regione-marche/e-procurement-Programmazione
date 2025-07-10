import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    Injector,
    OnDestroy,
    OnInit,
    ViewEncapsulation,
} from '@angular/core';
import { IDictionary, SdkBusinessAbstractWidget, SdkLocalStorageService, SdkRedirectService, SdkRouterService, SdkSessionStorageService, SdkStoreAction, SdkStoreService, decodeJwt } from '@maggioli/sdk-commons';
import { SdkButtonGroupInput } from '@maggioli/sdk-controls';
import { get, isEmpty, isObject, shuffle } from 'lodash-es';
import { environment } from '../../../../../../environments/environment';
import { Observable, map, of } from 'rxjs';

import { Constants } from '../../../../../app.constants';
import { AuthenticationService } from "../../../../services/authentication/authentication.service";
import { AppHomeIndexPanel } from '@maggioli/sdk-widgets';
import { RichiestaTokenResponse, TokenContent, UserExistsInfo } from '../../../../models/authentication/authentication.model';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ApplicationLoginConfiguration } from '../../../../models/application.model';

@Component({
    templateUrl: `spid-login-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class SpidLoginSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    public config: any = {};
    public providers: any = {};
    public buttons: Observable<SdkButtonGroupInput>;
    public visibleMenu: boolean = false;
    public showSpidButton: boolean;
    private provider: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
        let authId: string = this.sdkLocalStorageService.getItem(Constants.SPID_AUTH_ID, Constants.LOCAL_STORAGE_PREFIX);
        if (!isEmpty(authId)) {
            this.sdkLocalStorageService.removeItem(Constants.SPID_AUTH_ID, Constants.LOCAL_STORAGE_PREFIX);
            this.authenticationService.getUserSPID(environment.SPID_AUTHENTICATION_BASE_URL, authId).subscribe((responseData: any) => {
                this.clearAuthInfo();


                let response: RichiestaTokenResponse = {
                    token: responseData.data.token,
                    dataCreazione: '',
                    refreshToken: responseData.data.refreshToken,
                };

                // load logout url spid
                if (response.token != null) {
                    let userInfo: TokenContent = decodeJwt(response.token);
                    if (userInfo.SSO_LOGOUT_URL != null)
                        this.sdkSessionStorageService.setItem(Constants.LOGOUT_PATH, userInfo.SSO_LOGOUT_URL, Constants.LOCAL_STORAGE_PREFIX);
                }

                let redirectUrl: string = get(environment, moduloSelezionato.targetUrl);
                let params: IDictionary<string> = {
                    auth: JSON.stringify(response),
                    appCode: moduloSelezionato.panelCode
                }
                const baseUrl: string = this.getApplicationLoginConfiguration(moduloSelezionato).AUTHENTICATION_CHECK_USER_BASE_URL;
                let userInfo: any = decodeJwt(response.token);
                let userProfile: any = {};
                if (environment.LOGIN_MODE == 0) {
                    userProfile = {
                        nome: userInfo.given_name,
                        cognome: userInfo.family_name,
                        codiceFiscale: userInfo.cf
                    };
                } else {
                    userProfile = {
                        nome: userInfo.USER_NAME,
                        cognome: userInfo.USER_SURNAME,
                        codiceFiscale: userInfo.USER_CF,
                        login: userInfo.USER_LOGIN,
                    };
                }

                this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, userProfile));
                this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, response, Constants.LOCAL_STORAGE_PREFIX);

                this.getUserInfo(baseUrl, userInfo.USER_LOGIN).pipe(
                    map((result: UserExistsInfo) => {
                        if (result.exist === 'OK_USER') {
                            this.sdkRedirectService.redirect(redirectUrl, params);
                        } else if (result.exist === 'NO_USER' && result.abilitaRegistrazione) {
                            this.sdkSessionStorageService.setItem(Constants.CURRENT_MODULE, moduloSelezionato, Constants.LOCAL_STORAGE_PREFIX);
                            this.sdkRouterService.navigateToPage('registrazione-utente-page');
                        } else if (result.exist === 'DISABLED_USER') {
                            // Caso di utente registrato ma disabilitato. Forward a pagina di cortesia
                            this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '2' });
                        } else if (result.exist === 'EXPIRED_USER') {
                            // Caso di utente registrato ma con ultimo accesso più vecchio di dutrata Account.
                            this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '4' });
                        } else {
                            this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '3' });
                        }
                    })
                ).subscribe();



            });
        } else {
            this.sdkLocalStorageService.setItem(Constants.CURRENT_MODULE, moduloSelezionato);
            let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
            this.provider = paramsMap.get('provider');
            if (this.provider === 'CIE') {
                this.showSpidButton = false;
                let callbackUrl: string = this.getCallbackUrl();
                this.authenticationService.getUrlCIE(environment.SPID_AUTHENTICATION_BASE_URL, callbackUrl).subscribe((response: any) => {
                    let authId = response.data.authId;
                    this.sdkLocalStorageService.setItem(Constants.SPID_AUTH_ID, authId, Constants.LOCAL_STORAGE_PREFIX);
                    window.location.href = response.data.url;
                });

            } else if (this.provider === 'CNS') {
                this.showSpidButton = false;
                let callbackUrl: string = this.getCallbackUrl();
                this.authenticationService.getUrlCNS(environment.SPID_AUTHENTICATION_BASE_URL, callbackUrl).subscribe((response: any) => {
                    let authId = response.data.authId;
                    this.sdkLocalStorageService.setItem(Constants.SPID_AUTH_ID, authId, Constants.LOCAL_STORAGE_PREFIX);
                    window.location.href = response.data.url;
                });
            } else if (this.provider === 'SPID') {
                this.showSpidButton = true;
            }

        }
    }

    private clearAuthInfo(): void {
        this.sdkSessionStorageService.removeItem(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
    }

    private getUserInfo(baseUrl: string, username: string): Observable<UserExistsInfo> {
        return this.authenticationService.checkExistingUser(baseUrl, username);
    }

    protected onAfterViewInit(): void {
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.buttons = of({
                    buttons: this.config.body.buttons
                });
                this.providers = shuffle(this.config.body.providers);
                this.isReady = true
            })
        }
    }

    private getApplicationLoginConfiguration(moduloSelezionato: AppHomeIndexPanel): ApplicationLoginConfiguration {

        const panelCode: string = moduloSelezionato.panelCode;

        const appLoginConfig: ApplicationLoginConfiguration = get(environment.PANELS_CONFIGURATION, panelCode);

        if (appLoginConfig == null) {
            this.sdkRouterService.navigateToPage('home-page');
        }

        return appLoginConfig;
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public getSpidUrl(idProvider: string) {
        let callbackUrl: string = this.getCallbackUrl();
        this.authenticationService.getUrlSPID(environment.SPID_AUTHENTICATION_BASE_URL, idProvider, callbackUrl).subscribe((response: any) => {
            let authId = response.data.authId;
            this.sdkLocalStorageService.setItem(Constants.SPID_AUTH_ID, authId, Constants.LOCAL_STORAGE_PREFIX);
            window.location.href = response.data.url;
        });
    }

    public showMenu() {
        this.visibleMenu = !this.visibleMenu;
    }

    private getCallbackUrl(): string {
        let callbackUrl: string = [location.protocol, '//', location.host, location.pathname].join('');
        callbackUrl = callbackUrl + '%23/page/spid-login-page';
        return callbackUrl
    }

    // #endregion

    // #region Getters

    private get sdkLocalStorageService(): SdkLocalStorageService { return this.injectable(SdkLocalStorageService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

    // #region Private

    public goBack() {
        this.sdkRouterService.navigateToPage('login-page');
    }

    // #endregion
}
