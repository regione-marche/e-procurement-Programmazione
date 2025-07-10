import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRedirectService, SdkSessionStorageService } from '@maggioli/sdk-commons';
import { isEmpty, set } from 'lodash-es';
import { map, Observable, of } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Constants } from '../../app.constants';
import { OAuthService } from 'angular-oauth2-oidc';
import { EventService, ResponseDTO } from '@maggioli/app-commons';

@Injectable({ providedIn: 'root' })
export class LogoutProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('LogoutProvider', args);

        const logoutUrl: string = this.sdkSessionStorageService.getItem(Constants.LOGOUT_PATH, Constants.LOCAL_STORAGE_PREFIX);

        return this.eventService.writeLogoutEvent(environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL).pipe(
            map((response: ResponseDTO<string>) => {
                this.sdkSessionStorageService.clear(Constants.LOCAL_STORAGE_PREFIX);
                let redirectUrl: string = environment.APP_LAUNCHER_CONTEXT_URL;
        
                let params: IDictionary<string> = {
                    logout: 'true'
                };
        
                let returnParams: IDictionary<any> = {
                    logout: true
                };
        
                if (logoutUrl != null && !isEmpty(logoutUrl)) {
                    set(params, 'logoutUrl', logoutUrl);
                    set(returnParams, 'logoutUrl', logoutUrl);
                }
                if (environment.LOGIN_MODE === 0) {
                    this.oauthService.postLogoutRedirectUri = redirectUrl;
                    this.oauthService.revokeTokenAndLogout();
                }
                this.sdkRedirectService.redirect(redirectUrl, params);
                return of(returnParams);
            })
        )
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) };

    private get oauthService(): OAuthService { return this.injectable(OAuthService) }

    private get eventService(): EventService { return this.injectable(EventService) }

    // #endregion

}
