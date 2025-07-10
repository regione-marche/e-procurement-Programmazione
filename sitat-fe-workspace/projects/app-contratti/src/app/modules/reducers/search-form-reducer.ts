import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaAvvisoForm } from '../models/avviso/avviso.model';

@Injectable({ providedIn: 'root' })
export class SearchFormReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAvvisoForm>): SdkStateMap {
        try {
            return { ...state, searchFormS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}