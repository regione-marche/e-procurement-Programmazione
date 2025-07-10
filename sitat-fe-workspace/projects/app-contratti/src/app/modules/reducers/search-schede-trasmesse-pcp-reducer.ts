import { Injectable, Injector } from "@angular/core";
import { SdkBaseService, SdkReducer, SdkStateMap, SdkStoreAction } from "@maggioli/sdk-commons";
import { RicercaSchedePcp } from "../models/schede-trasmesse-pcp/schede-trasmesse-pcp.model";

@Injectable({ providedIn: 'root' })
export class SearchSchedeTrasmessePcpReducer extends SdkBaseService implements SdkReducer {

    constructor(inj: Injector) { super(inj) }

    public run(state: SdkStateMap, action: SdkStoreAction<RicercaSchedePcp>): SdkStateMap {
        try {
            return { ...state, searchFormRPCPS: action.payload }
        } catch (e) {
            return { ...state };
        }
    }

}