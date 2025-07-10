import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

@Injectable()
export class SdkSearchFormTabellatiReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<any>): SdkStateMap {
        try {
            return { ...state, searchFormGestioneTabellatiS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}