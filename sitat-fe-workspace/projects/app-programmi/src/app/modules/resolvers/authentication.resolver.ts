import { Injectable, Injector } from '@angular/core';
import { ActivatedRouteSnapshot, ParamMap, RouterStateSnapshot } from '@angular/router';
import { AuthenticationTokenInfo } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBaseService,
    SdkRedirectService,
    SdkRouterService,
    SdkSessionStorageService,
} from '@maggioli/sdk-commons';
import { isEmpty } from 'lodash-es';

import { environment } from '../../../environments/environment';
import { Constants } from '../../app.constants';

@Injectable({ providedIn: 'root' })
export class AuthenticationResolverService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    public resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
        let paramMap: ParamMap = route.queryParamMap;
        let auth = paramMap.get('auth');
        let appCode = paramMap.get('appCode');
        let authenticationAppCode = paramMap.get('authenticationAppCode');
        let loa = paramMap.get('loa');

        if (environment.LOGIN_MODE === 0) {
            try {
                let authTokenInfo: AuthenticationTokenInfo = JSON.parse(auth) as AuthenticationTokenInfo;
                if (authTokenInfo != null && authTokenInfo.token != null) {
                    this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, authTokenInfo, Constants.LOCAL_STORAGE_PREFIX);
                    this.sdkSessionStorageService.setItem(Constants.APP_CODE, appCode, Constants.LOCAL_STORAGE_PREFIX);
                    this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_APP_CODE, authenticationAppCode, Constants.LOCAL_STORAGE_PREFIX);
                    this.routerService.navigateToPage('choose-ente-page');
                } else {
                    this.routerService.navigateToPage('oauth-login-page');
                }
            } catch (e) {
                this.routerService.navigateToPage('oauth-login-page');
            }
        }

        else if (!isEmpty(auth) && !isEmpty(appCode)) {
            let authTokenInfo: AuthenticationTokenInfo = JSON.parse(auth) as AuthenticationTokenInfo;
            this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, authTokenInfo, Constants.LOCAL_STORAGE_PREFIX);
            this.sdkSessionStorageService.setItem(Constants.APP_CODE, appCode, Constants.LOCAL_STORAGE_PREFIX);
            this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_APP_CODE, authenticationAppCode, Constants.LOCAL_STORAGE_PREFIX);
            this.routerService.navigateToPage('choose-ente-page');
        } else {
            let redirectUrl: string = environment.APP_LAUNCHER_CONTEXT_URL;

            let params: IDictionary<string> = {
                error: Constants.SESSION_EXPIRED
            };
            this.sdkRedirectService.redirect(redirectUrl, params);
        }
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion
}
