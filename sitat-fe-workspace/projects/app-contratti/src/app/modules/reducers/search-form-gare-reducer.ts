import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

import { RicercaGareForm } from '../models/gare/gare.model';

@Injectable({ providedIn: 'root' })
export class SearchFormGareReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaGareForm>): SdkStateMap {
        try {
            return { ...state, searchFormGS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}