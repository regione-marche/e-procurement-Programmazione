import { Injectable, Injector } from '@angular/core';
import { ActivatedRouteSnapshot, ParamMap, RouterStateSnapshot } from '@angular/router';
import {
    IDictionary,
    SdkBaseService,
    SdkRedirectService,
    SdkRouterService,
    SdkSessionStorageService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { each, get, has, isEmpty, set } from 'lodash-es';

import { environment } from '../../../environments/environment';
import { Constants } from '../../app.constants';
import { AuthenticationTokenInfo } from '../models/authentication/authentication.model';
import { OAuth2AuthenticationUtilsService } from '../services/utils/oauth2-authentication-utils.service';

@Injectable({ providedIn: 'root' })
export class AuthenticationResolver extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    public resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {

        const toscanaParams: IDictionary<string> = this.oauth2AuthenticationUtilsService.loadToscanaAuthentication();

        let paramMap: ParamMap = route.queryParamMap;
        let logout = paramMap.get('logout');
        let auth = paramMap.get('auth');
        let logoutUrl: string = paramMap.get('logoutUrl');
        let panelCodeRegistrazione: string = paramMap.get('panelCodeRegistrazione');
        let userProfileToLauncher: string = paramMap.get('userProfileToLauncher');

        let paramsDictionary: IDictionary<any> = {};
        if (toscanaParams != null) {
            paramsDictionary = {
                ...toscanaParams
            };
        }

        let authorizationCode: string = null;
        let externalParamsDictionary: IDictionary<string> = this.loadExternalQueryParams();
        if (externalParamsDictionary != null) {
            if (has(externalParamsDictionary, 'authorizationCode')) {
                authorizationCode = get(externalParamsDictionary, 'authorizationCode');
            }
        }

        each(paramMap.keys, (key: string) => {
            if (key !== 'auth' && key !== 'authenticationAppCode') {
                set(paramsDictionary, key, paramMap.get(key))
            }
        });

        if (authorizationCode != null) {
            this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_CODE, authorizationCode, Constants.LOCAL_STORAGE_PREFIX);
        }

        if (logout === 'true') {
            this.sdkSessionStorageService.clear();

            if (environment.LOGIN_MODE == 3) {
                // logout scp
                const logoutPath: string = get(environment, 'LOGOUT_PATH');
                this.sdkRedirectService.redirect(logoutPath);
            } else if (environment.LOGIN_MODE == 0) {
                this.oauth2AuthenticationUtilsService.logoutOauth();
            } else if (environment.LOGIN_MODE == 1) {
                let logoutPath: string = logoutUrl != null ? logoutUrl : get(environment, 'LOGOUT_PATH');
                if (logoutPath == '')
                    logoutPath = '/';
                if (logoutPath != null) {
                    this.sdkRedirectService.redirect(logoutPath, null, true);
                    return;
                }
            }
        }

        if (!isEmpty(auth)) {
            const parsed: AuthenticationTokenInfo = JSON.parse(auth);
            this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, parsed, Constants.LOCAL_STORAGE_PREFIX);

            if (userProfileToLauncher != null && !isEmpty(userProfileToLauncher)) {
                let userProfileToLauncherObj: UserProfile = JSON.parse(userProfileToLauncher);
                this.sdkSessionStorageService.setItem(Constants.USER_PROFILE_TO_LAUNCHER, userProfileToLauncherObj, Constants.LOCAL_STORAGE_PREFIX);
            }
        }

        if (!isEmpty(panelCodeRegistrazione)) {
            this.sdkSessionStorageService.setItem(Constants.PANEL_CODE_SELEZIONATO, panelCodeRegistrazione, Constants.LOCAL_STORAGE_PREFIX);
            this.routerService.navigateToPage('registrazione-utente-page');
            return;
        }

        this.routerService.navigateToPage('home-page');
    }

    private loadExternalQueryParams(): IDictionary<string> {
        const href: string = window.location.href;
        if (environment.LOGIN_MODE !== 0 && href.indexOf('?') != -1) {
            const params: IDictionary<string> = this.sdkRedirectService.parseQueryParams(href.substring(href.indexOf('?')));
            return params != null ? params : null;
        }
        return null;
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get oauth2AuthenticationUtilsService(): OAuth2AuthenticationUtilsService { return this.injectable(OAuth2AuthenticationUtilsService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion
}
