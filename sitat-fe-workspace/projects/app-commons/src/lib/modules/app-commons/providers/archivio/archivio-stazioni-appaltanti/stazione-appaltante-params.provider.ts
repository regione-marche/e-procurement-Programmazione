import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioStazioneAppaltanteStoreService,
} from '../../../components/archivio/archivio-stazioni-appaltanti/dettaglio-stazione-appaltante-store.service';

@Injectable()
export class StazioneAppaltanteParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('StazioneAppaltanteParamsProvider', args);
        let codice: string = this.dettaglioStazioneAppaltanteStoreService.codice;
        return {
            codice
        };
    }

    // #region Getters

    private get dettaglioStazioneAppaltanteStoreService(): DettaglioStazioneAppaltanteStoreService { return this.injectable(DettaglioStazioneAppaltanteStoreService) }

    // #endregion

}
