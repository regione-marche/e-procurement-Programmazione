import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkDettaglioServerPostaStoreService,
} from '../../../components/amministrazione/server-posta/sdk-dettaglio-server-posta/sdk-dettaglio-server-posta-store.service';


@Injectable()
export class SdkDettaglioServerPostaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('SdkDettaglioServerPostaParamsProvider', args);
        let idCfg: string = this.sdkDettaglioServerPostaStoreService.idCfg;
        return {
            idCfg
        };
    }

    // #region Getters

    private get sdkDettaglioServerPostaStoreService(): SdkDettaglioServerPostaStoreService { return this.injectable(SdkDettaglioServerPostaStoreService) }

    // #endregion

}
