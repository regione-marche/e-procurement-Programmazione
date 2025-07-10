import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaAvanzataArchivioCdcForm } from '../models/archivio/archivio-centri-di-costo.models';

@Injectable()
export class SearchFormArchivioCdcReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAvanzataArchivioCdcForm>): SdkStateMap {
        try {
            return { ...state, searchFormACDCS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}