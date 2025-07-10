import { Injectable, Injector } from '@angular/core';
import { AuthenticationTokenInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRedirectService, SdkSessionStorageService, UserProfile } from '@maggioli/sdk-commons';
import { Observable, of } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Constants } from '../../app.constants';

@Injectable({ providedIn: 'root' })
export class CambioModuloProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('CambioModuloProvider', args);

        let authTokenInfo: AuthenticationTokenInfo = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
        let authAppCode: string = this.sdkSessionStorageService.getItem<string>(Constants.AUTHENTICATION_APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
        let userProfileToLauncher: UserProfile = this.sdkSessionStorageService.getItem<UserProfile>(Constants.USER_PROFILE_TO_LAUNCHER, Constants.LOCAL_STORAGE_PREFIX);

        this.sdkSessionStorageService.clear(Constants.LOCAL_STORAGE_PREFIX);
        let redirectUrl: string = environment.APP_LAUNCHER_CONTEXT_URL;

        let params: IDictionary<string> = {
            authenticationAppCode: authAppCode,
            auth: JSON.stringify(authTokenInfo)
        };

        if (userProfileToLauncher != null) {
            params.userProfileToLauncher = JSON.stringify(userProfileToLauncher);
        }

        this.sdkRedirectService.redirect(redirectUrl, params);

        return of({ redirect: true });
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) };

    // #endregion

}
