import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioFaseStoreService } from '../../layout/components/business/fasi/dettaglio-fase-store.service';

@Injectable({ providedIn: 'root' })
export class FinanziamentiTabsVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('FinanziamentiTabsVisibleProvider', args);
        const riaggiudicazione: boolean = this.dettaglioFaseStoreService.riaggiudicazione;
        return riaggiudicazione == null || riaggiudicazione === false;
    }

    // #region Getters

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    // #endregion
}