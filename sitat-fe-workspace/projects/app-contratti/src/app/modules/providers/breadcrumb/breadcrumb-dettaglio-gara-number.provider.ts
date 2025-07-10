import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { DettaglioIdGaraStoreService } from '../../layout/components/business/gare/dettaglio-id-gara-store.service';
import { GaraEntry } from '../../models/gare/gare.model';
import { SmartCigEntry } from '../../models/smartcig/smartcig.model';

@Injectable({ providedIn: 'root' })
export class BreadcrumbDettaglioGaraNumberProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(_args: IDictionary<any>): string {
        const gara: GaraEntry | SmartCigEntry = this.dettaglioIdGaraStoreService.gara;
        this.logger.debug('BreadcrumbDettaglioGaraNumberProvider >>>', gara);
        if (gara != null) {
            const idGara: string = get<any, any>(gara, 'identificativoGara') != null ? get<any, any>(gara, 'identificativoGara') : get<any, any>(gara, 'smartCig');
            return idGara;
        }
        return null;
    }

    // #region Getters

    private get dettaglioIdGaraStoreService(): DettaglioIdGaraStoreService { return this.injectable(DettaglioIdGaraStoreService) }

    // #endregion

}
