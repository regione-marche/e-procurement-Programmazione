import { Type } from '@angular/core';
import { IDictionary, SdkReducer } from '@maggioli/sdk-commons';
import { SdkUserReducer } from '@maggioli/sdk-widgets';

export function reducers(): IDictionary<Type<SdkReducer>> {
    return {
        USER: SdkUserReducer
    };
}
