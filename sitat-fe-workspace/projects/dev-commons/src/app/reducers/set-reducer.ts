import { Injectable } from '@angular/core';
import { SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class SetReducer implements SdkReducer {

  public run(state: SdkStateMap, action: SdkStoreAction<any>): SdkStateMap {
    try {
      return { ...action.payload };
    } catch (e) {
      return { ...state };
    }
  }

}
