import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaAvanzataArchivioStazioniAppaltantiForm } from '../models/stazione-appaltante/stazione-appaltante.model';

@Injectable()
export class SearchFormArchivioStazioniAppaltantiReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAvanzataArchivioStazioniAppaltantiForm>): SdkStateMap {
        try {
            return { ...state, searchFormASAS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}