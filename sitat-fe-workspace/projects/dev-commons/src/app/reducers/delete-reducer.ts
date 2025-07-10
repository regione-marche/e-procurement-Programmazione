import { Injectable } from '@angular/core';
import { SdkReducer, SdkStateMap, SdkStoreAction } from '@maggioli/sdk-commons';
import { omit } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class DeleteReducer implements SdkReducer {

  public run(state: SdkStateMap, action: SdkStoreAction<any>): SdkStateMap {
    try {
      let newState: any = { ...state };
      newState = omit(newState, action.payload)
      return newState;
    } catch (e) {
      return { ...state };
    }
  }

}
