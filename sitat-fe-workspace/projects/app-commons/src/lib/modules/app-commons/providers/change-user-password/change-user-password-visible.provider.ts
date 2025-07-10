import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkSessionStorageService, decodeJwt } from '@maggioli/sdk-commons';

import { Constants } from '../../app-commons.constants';
import { AuthenticationTokenInfo, TokenContent } from '../../models/authentication/authentication.model';

@Injectable()
export class ChangeUserPasswordVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {

        let authTokenInfo = this.sdkSessionStorageService.getItem<AuthenticationTokenInfo>(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
        if (authTokenInfo != null) {
            let userInfo: TokenContent = decodeJwt(authTokenInfo.token);
            if (userInfo != null) {
                return userInfo.internal === true;
            }
        }

        return false;
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    // #endregion
}