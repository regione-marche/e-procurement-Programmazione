import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkDettaglioConfigurazioneStoreService,
} from '../../../components/amministrazione/configurazioni/sdk-dettaglio-configurazione/sdk-dettaglio-configurazione-store.service';


@Injectable()
export class SdkDettaglioConfigurazioneParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioConfigurazioneParamsProvider', args);
        let codApp: string = this.sdkDettaglioConfigurazioneStoreService.codApp;
        let chiave: string = this.sdkDettaglioConfigurazioneStoreService.chiave;
        return {
            codApp,
            chiave
        };
    }

    // #region Getters

    private get sdkDettaglioConfigurazioneStoreService(): SdkDettaglioConfigurazioneStoreService { return this.injectable(SdkDettaglioConfigurazioneStoreService) }

    // #endregion

}
