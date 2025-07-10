import { Inject, Injectable, Injector } from '@angular/core';
import { SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each, get } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';


import { TabellatiService } from '../services/tabellati/tabellati.service';


@Injectable()
export class SdkRicercaModelliAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: any): Function {
       return undefined 
    }


    // #region Getters


    // #endregion

}