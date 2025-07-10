import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkRedirectService } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ShibbolethUtilsService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    public logoutTrento(logoutUrl: string = null): void {
        // se sono a Trento chiamo l'url di logout
        if (environment.LOGIN_MODE == 1) {
            const homePath: string = get(environment, 'HOME_PATH');
            const logoutPath: string = logoutUrl != null ? logoutUrl : get(environment, 'LOGOUT_PATH');
            const returnUrl: string = `${location.protocol}//${location.hostname}${homePath}`;
            const logoutParams: IDictionary<string> = {
                return: returnUrl
            }
            this.redirectService.redirect(logoutPath, logoutParams);
        }
    }

    // #region Getters

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion
}