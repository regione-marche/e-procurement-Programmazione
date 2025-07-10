import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioCdcStoreService } from '../../../components/archivio/archivio-centri-di-costo/dettaglio-cdc-store.service';


@Injectable()
export class CdcParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('CdcParamsProvider', args);
        let idCentro: string = this.dettaglioCdcStore.idCentro;
        return {
            idCentro
        };
    }

    // #region Getters

    private get dettaglioCdcStore(): DettaglioCdcStoreService { return this.injectable(DettaglioCdcStoreService) }

    // #endregion

}
