import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioImpresaStoreService } from '../../../components/archivio/archivio-imprese/dettaglio-impresa-store.service';


@Injectable()
export class ImpresaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('ImpresaParamsProvider', args);
        let codiceImpresa: string = this.dettaglioImpresaStore.codiceImpresa;
        return {
            codiceImpresa
        };
    }

    // #region Getters

    private get dettaglioImpresaStore(): DettaglioImpresaStoreService { return this.injectable(DettaglioImpresaStoreService) }

    // #endregion

}
