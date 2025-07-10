import { Injectable, Injector } from '@angular/core';
import { AuthenticationTokenInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, SdkSessionStorageService } from '@maggioli/sdk-commons';
import { Observable, of } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { Constants } from '../../../app.constants';

@Injectable({ providedIn: 'root' })
export class SdkInternalLoginProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('SdkInternalLoginProvider >>>', args);

        const token: string = args.token;
        const refreshToken: string = args.refreshToken;
        let authTokenInfo: AuthenticationTokenInfo = {
            token,
            dataCreazione: '',
            refreshToken
        };
        
        this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, authTokenInfo, Constants.LOCAL_STORAGE_PREFIX);
        this.sdkSessionStorageService.setItem(Constants.APP_CODE, environment.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
        this.sdkRouterService.navigateToPage('choose-ente-page');

        return of(args);
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion
}