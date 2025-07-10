import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { SdkDettaglioUtenteStoreService } from '../components/sdk-dettaglio-utente/sdk-dettaglio-utente-store.service';


@Injectable()
export class SdkDettaglioUtenteParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioUtenteParamsProvider', args);
        let syscon: number = this.sdkDettaglioUtenteStoreService.syscon;
        return {
            syscon
        };
    }

    // #region Getters

    private get sdkDettaglioUtenteStoreService(): SdkDettaglioUtenteStoreService { return this.injectable(SdkDettaglioUtenteStoreService) }

    // #endregion

}
