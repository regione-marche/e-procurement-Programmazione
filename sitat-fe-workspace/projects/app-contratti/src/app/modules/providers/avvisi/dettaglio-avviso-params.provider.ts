import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioAvvisoStoreService,
} from '../../layout/components/business/avvisi/dettaglio-avviso/dettaglio-avviso-store.service';

@Injectable({ providedIn: 'root' })
export class DettaglioAvvisoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioAvvisoParamsProvider', args);
        const idAvviso: string = this.dettaglioAvvisoStoreService.idAvviso;
        const stazioneAppaltante: string = this.dettaglioAvvisoStoreService.stazioneAppaltante;
        const params: IDictionary<string> = {
            idAvviso,
            stazioneAppaltante
        };
        return params;
    }

    // #region Getters

    private get dettaglioAvvisoStoreService(): DettaglioAvvisoStoreService {
        return this.injectable(DettaglioAvvisoStoreService);
    }

    // #endregion

}
