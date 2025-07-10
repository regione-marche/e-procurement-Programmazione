import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';

import { Constants } from '../../app-commons.constants';

@Injectable()
export class StazioneAppaltanteUtentiVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            const abilitazioni: Array<string> = userProfile.abilitazioni;
            if (abilitazioni != null &&
                abilitazioni.includes(Constants.OU_GESTIONE_UTENTI_COMPLETA) &&
                !abilitazioni.includes(Constants.OU_GESTIONE_UTENTI_OU12)) {
                return true;
            }
        }
        return false;
    }
}