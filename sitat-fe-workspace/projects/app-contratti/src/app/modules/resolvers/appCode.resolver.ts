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
export class AppCodeResolverService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    public resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): any {
        
        let appCode = this.getHashQueryParam('appCode');
        if(!isEmpty(appCode)){
            this.sdkSessionStorageService.setItem(Constants.APP_CODE, appCode, Constants.LOCAL_STORAGE_PREFIX);
            environment.APP_CODE = appCode;
        }        
        
    }

    getHashQueryParam(param: string): string | null {

        const url = window.location.href;
    
        const hash = url.split('#')[1]; 
    
        if (!hash) {
            return null;
        }
    
        const queryString = hash.split('?')[1];
    
        if (!queryString) {
            return null;
        }
        const urlParams = new URLSearchParams(queryString);
    
        return urlParams.get(param);
    }
    


    // #region Getters


    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion
}
