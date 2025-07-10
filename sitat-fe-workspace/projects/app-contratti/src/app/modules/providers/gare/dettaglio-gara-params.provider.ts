import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioGaraStoreService } from '../../layout/components/business/gare/dettaglio-gara-store.service';
import { DettaglioSmartCigStoreService } from '../../layout/components/business/smartcig/dettaglio-smartcig-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioGaraParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioGaraParamsProvider', args);
        let codGara: string = this.dettaglioGaraStoreService.codGara;
        if (codGara == null) {
            codGara = this.dettaglioSmartCigStoreService.codGara;
        }
        return {
            codGara
        };
    }

    // #region Getters

    private get dettaglioGaraStoreService(): DettaglioGaraStoreService { return this.injectable(DettaglioGaraStoreService) }

    private get dettaglioSmartCigStoreService(): DettaglioSmartCigStoreService { return this.injectable(DettaglioSmartCigStoreService) }

    // #endregion

}
