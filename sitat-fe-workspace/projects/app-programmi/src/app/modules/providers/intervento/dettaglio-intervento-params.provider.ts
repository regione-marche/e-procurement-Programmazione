import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioInterventoStore } from '../../layout/components/business/interventi/dettaglio-intervento-store.service';

export interface DettaglioInterventoProviderArgs extends IDictionary<any> {
    idProgramma?: string;
    tipologia?: string;
    idIntervento?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class DettaglioInterventoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioInterventoParamsProvider', args);
        let idProgramma: string = this.dettaglioInterventoStore.idProgramma;
        let tipologia: string = this.dettaglioInterventoStore.tipologia;
        let idIntervento: string = this.dettaglioInterventoStore.idIntervento;
        return {
            idProgramma,
            tipologia,
            idIntervento
        };
    }


    // #region Getters

    private get dettaglioInterventoStore(): DettaglioInterventoStore { return this.injectable(DettaglioInterventoStore) }

    // #endregion

}
