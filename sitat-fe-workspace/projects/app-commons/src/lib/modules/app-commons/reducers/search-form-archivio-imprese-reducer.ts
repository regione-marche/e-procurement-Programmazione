import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaAvanzataArchivioImpreseForm } from '../models/archivio/archivio-imprese.models';

@Injectable()
export class SearchFormArchivioImpreseReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAvanzataArchivioImpreseForm>): SdkStateMap {
        try {
            return { ...state, searchFormAIS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}