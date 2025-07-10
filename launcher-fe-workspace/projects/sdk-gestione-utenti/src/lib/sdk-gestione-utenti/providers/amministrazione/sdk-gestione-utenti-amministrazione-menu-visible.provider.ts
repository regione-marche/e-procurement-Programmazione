import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';

import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';

@Injectable()
export class SdkGestioneUtentiAmministrazioneMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            const abilitazioni: Array<string> = userProfile.abilitazioni;
            return abilitazioni != null && abilitazioni.includes(SdkGestioneUtentiConstants.OU_89_AMMINISTRATORE);
        }
        return false;
    }
}