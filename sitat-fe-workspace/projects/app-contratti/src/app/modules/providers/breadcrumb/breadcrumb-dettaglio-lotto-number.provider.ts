import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioCigLottoStoreService } from '../../layout/components/business/lotti/dettaglio-cig-lotto-store.service';
import { LottoEntry } from '../../models/gare/gare.model';

@Injectable({ providedIn: 'root' })
export class BreadcrumbDettaglioLottoNumberProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(_args: IDictionary<any>): string {
        const lotto: LottoEntry = this.dettaglioCigLottoStoreService.lotto;
        this.logger.debug('BreadcrumbDettaglioLottoNumberProvider >>>', lotto);
        return lotto ? (lotto.cig ?? lotto.codLotto ?? null) : null;
    }

    // #region Getters

    private get dettaglioCigLottoStoreService(): DettaglioCigLottoStoreService { return this.injectable(DettaglioCigLottoStoreService) }

    // #endregion

}
