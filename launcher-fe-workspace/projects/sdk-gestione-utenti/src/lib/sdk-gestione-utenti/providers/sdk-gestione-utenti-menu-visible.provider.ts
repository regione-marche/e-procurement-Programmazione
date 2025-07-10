import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';

import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';

@Injectable()
export class SdkGestioneUtentiMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            const abilitazioni: Array<string> = userProfile.abilitazioni;
            const opzioniProdotto: Array<string> = userProfile.opzioniProdotto;
            if (abilitazioni != null && opzioniProdotto != null &&
                abilitazioni.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_COMPLETA)) {
                return true;
            }
        }
        return false;
    }
}