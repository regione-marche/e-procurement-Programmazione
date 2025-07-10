import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { InitRicercaUtentiDTO } from '../model/gestione-utenti.model';

@Injectable()
export class SdkGestioneUtentiGestoreStazioniAppaltantiVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const initRicercaUtenti: InitRicercaUtentiDTO = args.initRicercaUtenti;
        if (initRicercaUtenti != null) {
            // Sono un utente admin oppure sono un gestore con piu' di un ufficio intestatario associato
            return initRicercaUtenti.utenteDelegatoGestioneUtenti == false ||
                (
                    initRicercaUtenti.utenteDelegatoGestioneUtenti == true && initRicercaUtenti.stazioniAppaltantiAssociate != null && initRicercaUtenti.stazioniAppaltantiAssociate.length > 1
                );
        }
        return false;
    }
}