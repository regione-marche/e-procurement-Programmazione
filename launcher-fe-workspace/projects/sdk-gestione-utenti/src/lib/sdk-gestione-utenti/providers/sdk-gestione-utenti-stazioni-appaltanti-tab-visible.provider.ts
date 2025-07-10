import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { UserDTO } from '../model/gestione-utenti.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';

@Injectable()
export class SdkGestioneUtentiStazioniAppaltantiVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const utente: UserDTO = args.utente;
        if (utente != null) {
            const opzioniUtente: Array<string> = utente.opzioniUtente;
            if (opzioniUtente != null &&
                opzioniUtente.includes(SdkGestioneUtentiConstants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI)) {
                return false;
            }
        }
        return true;
    }
}