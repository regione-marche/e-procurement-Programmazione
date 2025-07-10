import { Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SdkBaseService,
    SdkProvider,
    SdkRedirectService,
    SdkSessionStorageService,
    SdkStoreAction,
    SdkStoreService,
    SdkTestataProfileMessageService,
} from '@maggioli/sdk-commons';
import { get, isEmpty } from 'lodash-es';
import { Observable, of } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Constants } from '../../app.constants';
import { OAuth2AuthenticationUtilsService } from '../services/utils/oauth2-authentication-utils.service';

@Injectable({ providedIn: 'root' })
export class LogoutProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('LogoutProvider', args);

        const logoutUrl: string = this.sdkSessionStorageService.getItem(Constants.LOGOUT_PATH, Constants.LOCAL_STORAGE_PREFIX);

        this.sdkSessionStorageService.clear(Constants.LOCAL_STORAGE_PREFIX);
        this.sdkStoreService.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, undefined));

        this.testataProfile.emit(false);

        this.oauth2AuthenticationUtilsService.logoutOauth();

        const homePath: string = get(environment, 'HOME_PATH');
        const returnUrl: string = `${location.protocol}//${location.hostname}${homePath}`;

        if (environment.LOGIN_MODE == 3) {
            // logout scp
            const logoutPath: string = get(environment, 'LOGOUT_PATH');
            this.sdkRedirectService.redirect(logoutPath);
        } else if (environment.LOGIN_MODE == 1) {
            const logoutPath: string = logoutUrl != null ? logoutUrl : get(environment, 'LOGOUT_PATH');
            if (!isEmpty(logoutPath)) {
                this.sdkRedirectService.redirect(logoutPath, null, true);
                return of({});
            }
        }


        this.sdkRedirectService.redirect(returnUrl);

        return of({ logout: true });
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkStoreService(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get testataProfile(): SdkTestataProfileMessageService { return this.injectable(SdkTestataProfileMessageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get oauth2AuthenticationUtilsService(): OAuth2AuthenticationUtilsService { return this.injectable(OAuth2AuthenticationUtilsService) }

    // #endregion

}
