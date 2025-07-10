import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioProgrammaStoreService,
} from '../../layout/components/business/dettaglio-programma/dettaglio-programma-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioProgrammaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioProgrammaParamsProvider', args);
        let idProgramma: string = this.dettaglioProgrammaStore.idProgramma;
        let tipologia: string = this.dettaglioProgrammaStore.tipologia;
        return {
            idProgramma,
            tipologia
        };
    }

    // #region Getters

    private get dettaglioProgrammaStore(): DettaglioProgrammaStoreService { return this.injectable(DettaglioProgrammaStoreService) }

    // #endregion

}
