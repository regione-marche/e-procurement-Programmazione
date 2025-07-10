import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkListaDettaglioTabellatoStoreService,
} from '../../../components/amministrazione/tabellati/sdk-lista-dettaglio-tabellato/sdk-lista-dettaglio-tabellato-store.service';


@Injectable()
export class SdkListaDettaglioTabellatoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('SdkListaDettaglioTabellatoParamsProvider', args);
        let provenienza: string = this.sdkListaDettaglioTabellatoStoreService.provenienza;
        let codiceTabellato: string = this.sdkListaDettaglioTabellatoStoreService.codiceTabellato;
        return {
            provenienza,
            codiceTabellato
        };
    }

    // #region Getters

    private get sdkListaDettaglioTabellatoStoreService(): SdkListaDettaglioTabellatoStoreService { return this.injectable(SdkListaDettaglioTabellatoStoreService) }

    // #endregion

}
