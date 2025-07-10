import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaAvvisoForm } from '../models/avviso/avviso.model';
import { RicercaAttiGeneraliForm } from '../models/atti-generali/atti-generali.model';

@Injectable({ providedIn: 'root' })
export class SearchFormAttiGeneraliReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAttiGeneraliForm>): SdkStateMap {
        try {
            return { ...state, searchFormAGS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}