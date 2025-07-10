import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { AbilitazioniUtilsService } from '../../services/utils/abilitazioni-utils.service';

@Injectable()
export class MessaggiAvvisiAdminVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        let userProfile: UserProfile = get(args, 'userProfile');
        if (userProfile != null) {
            let isAmministratore: boolean = this.abilitazioniUtilsService.isAmministratore(userProfile.abilitazioni);
            let messaggisticaInternaAttiva: boolean = !!userProfile.messaggisticaInternaAttiva;
            return messaggisticaInternaAttiva && isAmministratore === true;
        }
        return false;
    }

    // #region Getters

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    // #endregion
}