import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkSessionStorageService } from '@maggioli/sdk-commons';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { Constants } from '../../../app.constants';
import { AuthenticationTokenInfo } from '../../models/authentication/authentication.model';


@Injectable()
export class AuthenticationInterceptor extends SdkBaseService implements HttpInterceptor {

    constructor(inj: Injector) {
        super(inj);
    }

    public intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let authTokenInfo: AuthenticationTokenInfo = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
        if (authTokenInfo != null) {
            let headers: IDictionary<any> = {
                'Authorization': `Bearer ${authTokenInfo.token}`
            };

            if (environment.LOGIN_MODE_EXCLUDED_BASE_URL != null && environment.LOGIN_MODE_EXCLUDED_BASE_URL.length > 0) {
                const excludedUrls: Array<string> = environment.LOGIN_MODE_EXCLUDED_BASE_URL;
                let found: boolean = false;
                each(excludedUrls, (exUrl: string) => {
                    if (req.url.includes(exUrl)) {
                        found = true;
                        return false;
                    }
                });

                if (!found) {
                    headers = {
                        ...headers,
                        'X-loginMode': environment.LOGIN_MODE.toString()
                    };
                }
            } else {
                headers = {
                    ...headers,
                    'X-loginMode': environment.LOGIN_MODE.toString()
                };
            }

            req = req.clone(
                {
                    setHeaders: headers
                }
            );
        }
        return next.handle(req);
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    // #endregion
}
