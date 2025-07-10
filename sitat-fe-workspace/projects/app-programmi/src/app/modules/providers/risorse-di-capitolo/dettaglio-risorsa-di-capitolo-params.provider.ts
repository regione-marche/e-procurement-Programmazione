import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioRisorsaDiCapitoloStore,
} from '../../layout/components/business/risorse-di-capitolo/dettaglio-risorsa-di-capitolo-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioRisorsaDiCapitoloParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioRisorsaDiCapitoloParamsProvider', args);
        let idProgramma: string = this.dettaglioRisorsaDiCapitoloStore.idProgramma;
        let tipologia: string = this.dettaglioRisorsaDiCapitoloStore.tipologia;
        let idIntervento: string = this.dettaglioRisorsaDiCapitoloStore.idIntervento;
        let idRisorsaDiCapitolo: string = this.dettaglioRisorsaDiCapitoloStore.idRisorsaDiCapitolo;
        return {
            idProgramma,
            tipologia,
            idIntervento,
            idRisorsaDiCapitolo
        };
    }

    // #region Getters

    private get dettaglioRisorsaDiCapitoloStore(): DettaglioRisorsaDiCapitoloStore { return this.injectable(DettaglioRisorsaDiCapitoloStore) }

    // #endregion

}
