import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class NumAppaFasiLottoReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<number>): SdkStateMap {
        try {
            return { ...state, currentNumAppaFasiLottoS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}