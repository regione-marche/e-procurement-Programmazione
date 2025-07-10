import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioOperaIncompiutaStoreService,
} from '../../layout/components/business/opera-incompiuta/dettaglio-opera-incompiuta-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioOperaIncompiutaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioOperaIncompiutaParamsProvider', args);
        let idProgramma: string = this.dettaglioOperaIncompiutaStore.idProgramma;
        let tipologia: string = this.dettaglioOperaIncompiutaStore.tipologia;
        let idOperaIncompiuta: string = this.dettaglioOperaIncompiutaStore.idOperaIncompiuta;
        let params: IDictionary<any> = {
            idProgramma,
            tipologia,
            idOperaIncompiuta
        };

        return params;
    }

    // #region Getters

    private get dettaglioOperaIncompiutaStore(): DettaglioOperaIncompiutaStoreService { return this.injectable(DettaglioOperaIncompiutaStoreService) }

    // #endregion

}
