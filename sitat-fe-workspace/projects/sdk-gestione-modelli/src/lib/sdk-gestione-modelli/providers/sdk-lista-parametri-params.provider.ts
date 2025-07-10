import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { SdkDettaglioModelloStoreService } from '../components/sdk-dettaglio-modello/sdk-dettaglio-modello-store.service';

@Injectable()
export class SdkListaParametriParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('SdkListaParametriParamsProvider', args);
        let idModello: number = this.sdkDettaglioModello.idModello;
        return {
            idModello
        };
    }

    // #region Getters

    private get sdkDettaglioModello(): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

    // #endregion

}
