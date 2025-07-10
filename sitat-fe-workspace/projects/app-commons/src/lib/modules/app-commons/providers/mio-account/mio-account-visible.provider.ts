import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Injectable()
export class MioAccountVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        let userProfile: UserProfile = get(args, 'userProfile');
        if (userProfile != null && userProfile.configurations != null) {
            return this.protectionUtilsService.isSubmenuVisible('UTILITA.Mio-account', userProfile.configurations);
        }
        return false;
    }

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion
}