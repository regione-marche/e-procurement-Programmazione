import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioUfficioStoreService } from '../../../components/archivio/archivio-uffici/dettaglio-ufficio-store.service';

@Injectable()
export class UfficioParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('UfficioParamsProvider', args);
        let codice: string = this.dettaglioUfficioStoreService.codice;
        return {
            codice
        };
    }

    // #region Getters

    private get dettaglioUfficioStoreService(): DettaglioUfficioStoreService { return this.injectable(DettaglioUfficioStoreService) }

    // #endregion

}
