import { Injectable, Injector } from '@angular/core';
import { RicercaAvanzataArchivioUfficiForm } from '@maggioli/app-commons';
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

@Injectable()
export class SearchFormArchivioUfficiReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaAvanzataArchivioUfficiForm>): SdkStateMap {
        try {
            return { ...state, searchFormAUFFICIS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}