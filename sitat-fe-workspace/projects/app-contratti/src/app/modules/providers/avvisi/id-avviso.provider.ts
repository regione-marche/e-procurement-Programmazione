import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioAvvisoStoreService,
} from '../../layout/components/business/avvisi/dettaglio-avviso/dettaglio-avviso-store.service';


@Injectable({ providedIn: 'root' })
export class IdAvvisoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('IdAvvisoProvider', args);
        let idAvviso: string = this.dettaglioAvvisoStoreService.idAvviso;
        let stazioneAppaltante: string = this.dettaglioAvvisoStoreService.stazioneAppaltante;
        return { idAvviso,stazioneAppaltante };
    }

    // #region Getters

    private get dettaglioAvvisoStoreService(): DettaglioAvvisoStoreService { return this.injectable(DettaglioAvvisoStoreService) }

    // #endregion

}
