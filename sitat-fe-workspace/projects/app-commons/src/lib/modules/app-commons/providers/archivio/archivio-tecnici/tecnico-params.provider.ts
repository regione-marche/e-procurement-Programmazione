import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioTecnicoStoreService } from '../../../components/archivio/archivio-tecnici/dettaglio-tecnico-store.service';

@Injectable()
export class TecnicoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('TecnicoParamsProvider', args);
        let codice: string = this.dettaglioTecnicoStore.codice;
        return {
            codice
        };
    }

    // #region Getters

    private get dettaglioTecnicoStore(): DettaglioTecnicoStoreService { return this.injectable(DettaglioTecnicoStoreService) }

    // #endregion

}
