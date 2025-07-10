import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaAvanzataArchivioTecniciForm } from '../models/archivio/archivio-tecnici.models';

@Injectable()
export class SearchFormArchivioTecniciReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAvanzataArchivioTecniciForm>): SdkStateMap {
        try {
            return { ...state, searchFormATECNICIS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}