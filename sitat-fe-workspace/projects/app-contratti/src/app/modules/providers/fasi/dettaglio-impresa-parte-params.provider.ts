import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioImpresaInvitataPartecipanteStoreService,
} from '../../layout/components/business/fasi/elenco-impr-inv-parte/dettaglio-impr-inv-parte-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioImpresaParteParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioImpresaParteParamsProvider', args);
        let codGara: string = this.dettaglioImprInvParteStoreService.codGara;
        let codLotto: string = this.dettaglioImprInvParteStoreService.codLotto;
        let codiceFase: string = this.dettaglioImprInvParteStoreService.codiceFase;
        let numeroProgressivo: string = this.dettaglioImprInvParteStoreService.numeroProgressivo;
        let num: string = this.dettaglioImprInvParteStoreService.num;
        let numRagg: string = this.dettaglioImprInvParteStoreService.numRagg;
        let fromLS: string = this.dettaglioImprInvParteStoreService.fromLS;
        let params: IDictionary<string> = {
            codGara,
            codLotto,
            codiceFase,
            numeroProgressivo,
            num,
            numRagg,
            fromLS
        };
        return params;
    }

    // #region Getters

    private get dettaglioImprInvParteStoreService(): DettaglioImpresaInvitataPartecipanteStoreService { return this.injectable(DettaglioImpresaInvitataPartecipanteStoreService) }

    // #endregion

}
