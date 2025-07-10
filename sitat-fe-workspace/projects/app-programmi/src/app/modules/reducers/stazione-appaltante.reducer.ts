import { Injectable, Injector } from '@angular/core';
import { StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class StazioneAppaltanteInfoReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<StazioneAppaltanteInfo>): SdkStateMap {
        try {
            return { ...state, saInfo: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}