import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkDettaglioTabellatoStoreService,
} from '../../../components/amministrazione/tabellati/sdk-dettaglio-tabellato/sdk-dettaglio-tabellato-store.service';


@Injectable()
export class SdkDettaglioTabellatoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('SdkDettaglioTabellatoParamsProvider', args);
        let provenienza: string = this.sdkDettaglioTabellatoStoreService.provenienza;
        let codiceTabellato: string = this.sdkDettaglioTabellatoStoreService.codiceTabellato;
        let identificativo: string = this.sdkDettaglioTabellatoStoreService.identificativo;
        return {
            provenienza,
            codiceTabellato,
            identificativo
        };
    }

    // #region Getters

    private get sdkDettaglioTabellatoStoreService(): SdkDettaglioTabellatoStoreService { return this.injectable(SdkDettaglioTabellatoStoreService) }

    // #endregion

}
