import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';

import { Constants } from '../../../app-commons.constants';

@Injectable()
export class ArchivioStazioniAppaltantiMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            const abilitazioni: Array<string> = userProfile.abilitazioni;
            return abilitazioni != null && !abilitazioni.includes(Constants.OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI);
        }
        return false;
    }
}