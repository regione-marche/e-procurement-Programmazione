import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkDettaglioEventoStoreService,
} from '../../../components/amministrazione/eventi/sdk-dettaglio-evento/sdk-dettaglio-evento-store.service';


@Injectable()
export class SdkDettaglioEventoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioEventoParamsProvider', args);
        let idEvento: number = this.sdkDettaglioEventoStoreService.idEvento;
        return {
            idEvento
        };
    }

    // #region Getters

    private get sdkDettaglioEventoStoreService(): SdkDettaglioEventoStoreService { return this.injectable(SdkDettaglioEventoStoreService) }

    // #endregion

}
