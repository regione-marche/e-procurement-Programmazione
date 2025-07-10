import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';
import { indexOf, isArray, isEmpty } from 'lodash-es';

import { Constants } from '../../app-commons.constants';

@Injectable()
export class AbilitazioniUtilsService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    /**
     * Metodo che verifica la presenza del ruolo amministratore nelle abilitazioni
     * @param abilitazioni Lista di abilitazioni
     * @returns Booleano che indica se l'utente collegato e' amministratore
     */
    public isAmministratore(abilitazioni: Array<string>): boolean {
        if (isArray(abilitazioni) && !isEmpty(abilitazioni)) {
            return indexOf(abilitazioni, Constants.ABILITAZIONE_AMMINISTRATORE) > -1
        }
        return false;
    }

}