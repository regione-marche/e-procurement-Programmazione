import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkDettaglioModelloStoreService } from '../components/sdk-dettaglio-modello/sdk-dettaglio-modello-store.service';


@Injectable()
export class SdkDettaglioModelloParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioUtenteParamsProvider', args);
        let idModello: number = this.sdkDettaglioModelloStoreService.idModello;
        return {
            idModello
        };
    }

    // #region Getters

    private get sdkDettaglioModelloStoreService(): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

    // #endregion

}
