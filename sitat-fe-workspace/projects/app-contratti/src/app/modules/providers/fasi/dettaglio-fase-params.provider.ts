import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkSessionStorageService } from '@maggioli/sdk-commons';

import { DettaglioFaseStoreService } from '../../layout/components/business/fasi/dettaglio-fase-store.service';

@Injectable({ providedIn: 'root' })
export class DettaglioFaseParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioFaseParamsProvider', this.dettaglioFaseStoreService);
        let codGara: string = this.dettaglioFaseStoreService.codGara;
        let codLotto: string = this.dettaglioFaseStoreService.codLotto;
        let codiceFase: string = this.dettaglioFaseStoreService.codiceFase;
        let numeroProgressivo: string = this.dettaglioFaseStoreService.numeroProgressivo;

        let fromLS: string = this.dettaglioFaseStoreService.fromLS;

        let params: IDictionary<string>;
        if (numeroProgressivo != null) {
            params = {
                codGara,
                codLotto,
                codiceFase,
                numeroProgressivo,
                fromLS
            };
        } else {
            params = {
                codGara,
                codLotto,
                codiceFase,
                fromLS
            };
        }
        return params;
    }

    // #region Getters

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    // #endregion

}
