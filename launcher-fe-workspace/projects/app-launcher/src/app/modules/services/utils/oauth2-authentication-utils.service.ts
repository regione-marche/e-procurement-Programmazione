import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkRedirectService } from '@maggioli/sdk-commons';
import { OAuthService } from 'angular-oauth2-oidc';

import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class OAuth2AuthenticationUtilsService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    public loadToscanaAuthentication(): IDictionary<string> {
        const href: string = window.location.href;
        if (environment.LOGIN_MODE === 0 && href.indexOf('?') != -1) {
            const params: IDictionary<string> = this.redirectService.parseQueryParams(href.substring(href.indexOf('?')));
            return params != null ? params : null;
        }
        return null;
    }

    public logoutOauth(): void {
        // se sono in Toscana eseguo il logout di oauth
        if (environment.LOGIN_MODE == 0) {
            try {
                this.oauthService.revokeTokenAndLogout();
            } catch (e) {
                this.logger.error(e);
            }
        }
    }

    // #region Getters

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get oauthService(): OAuthService { return this.injectable(OAuthService) }

    // #endregion
}