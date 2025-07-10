import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkSessionStorageService } from '@maggioli/sdk-commons';

import { DettaglioLottoStoreService } from '../../layout/components/business/lotti/dettaglio-lotto-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioLottoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioLottoParamsProvider', args);
        let codGara: string = this.dettaglioLottoStore.codGara;
        let codLotto: string = this.dettaglioLottoStore.codLotto;
        let fromLS: string = this.dettaglioLottoStore.fromLS;

        return {
            codGara,
            codLotto,
            fromLS
        };
    }

    // #region Getters

    private get dettaglioLottoStore(): DettaglioLottoStoreService { return this.injectable(DettaglioLottoStoreService) }

    // #endregion

}
