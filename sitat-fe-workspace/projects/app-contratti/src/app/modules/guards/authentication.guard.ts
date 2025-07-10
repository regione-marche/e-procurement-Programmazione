import { Injectable, Injector } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { IDictionary, SdkBaseService, SdkRedirectService, SdkSessionStorageService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Constants } from '../../app.constants';

@Injectable({ providedIn: 'root' })
export class AuthenticationGuard extends SdkBaseService {

    private publicRoutes: Array<string> = [
        'internal-login-page',
        'spid-login-page',
        'recupero-password-page',
        'recupero-password-inviata-page',
        'cambio-password-page',
        'esegui-recupero-password-page',
        'esegui-recupero-password-success-page',
        'oauth-login-page'
    ];

    constructor(inj: Injector) {
        super(inj);
    }

    public canActivate(
        next: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean> | Promise<boolean> | boolean {
        return this.can(next);
    }

    private can(route?: ActivatedRouteSnapshot): Observable<boolean> | Promise<boolean> | boolean {
        let canActivate: boolean = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX) != null;
        
        if (route != null) {
            let slug = route.paramMap.get('slug');
            // se vado alla pagina di login o recupero password allora ignoro la guardia
            if (slug != null && this.publicRoutes.includes(slug)) {
                canActivate = true;
            }
        }
        
        if (canActivate === false) {
            // redirect
            let redirectUrl: string = environment.APP_LAUNCHER_CONTEXT_URL;
            let params: IDictionary<string> = {
                logout: 'true'
            };
            this.sdkRedirectService.redirect(redirectUrl, params);
        }
        return canActivate;
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion
}
