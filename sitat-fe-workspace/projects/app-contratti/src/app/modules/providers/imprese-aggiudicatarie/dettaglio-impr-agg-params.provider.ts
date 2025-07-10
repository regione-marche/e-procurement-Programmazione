import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    DettaglioImpresaAggiudicatariaStoreService,
} from '../../layout/components/business/fasi/imprese-aggiudicatarie/dettaglio-impresa-aggiudicataria-store.service';
import { ImpresaAggiudicatariaEntry } from '../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';


@Injectable({ providedIn: 'root' })
export class DettaglioImpresaAggiudicatariaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioImpresaAggiudicatariaParamsProvider', args);
        const codGara: string = this.dettaglioImpresaAggiudicatariaStoreService.codGara;
        const codLotto: string = this.dettaglioImpresaAggiudicatariaStoreService.codLotto;
        const codiceFase: string = this.dettaglioImpresaAggiudicatariaStoreService.codiceFase;
        const numeroProgressivo: string = this.dettaglioImpresaAggiudicatariaStoreService.numeroProgressivo;
        const impresa: ImpresaAggiudicatariaEntry = this.dettaglioImpresaAggiudicatariaStoreService.impresa;

        // Atti
        const tipoDocumento: string = this.dettaglioImpresaAggiudicatariaStoreService.tipoDocumento;
        const numPubb: string = this.dettaglioImpresaAggiudicatariaStoreService.numPubb;
        const campiVisibili: string = this.dettaglioImpresaAggiudicatariaStoreService.campiVisibili;
        const campiObbligatori: string = this.dettaglioImpresaAggiudicatariaStoreService.campiObbligatori;

        //Schede trasmesse a PCP
        const fromLS: string = this.dettaglioImpresaAggiudicatariaStoreService.fromLS;

        const params: IDictionary<string> = {};

        params.codGara = codGara;
        if (codLotto != null) params.codLotto = codLotto;
        if (codiceFase != null) params.codiceFase = codiceFase;
        if (numeroProgressivo != null) params.numeroProgressivo = numeroProgressivo;
        if (impresa != null) params.codiceImpresa = impresa.codImpresa;
        if (tipoDocumento != null) params.tipoDocumento = tipoDocumento;
        if (numPubb != null) params.numPubb = numPubb;
        if (campiVisibili != null) params.campiVisibili = campiVisibili;
        if (campiObbligatori != null) params.campiObbligatori = campiObbligatori;
        if (fromLS != null) params.fromLS = fromLS;

        return params;
    }

    // #region Getters

    private get dettaglioImpresaAggiudicatariaStoreService(): DettaglioImpresaAggiudicatariaStoreService { return this.injectable(DettaglioImpresaAggiudicatariaStoreService) }

    // #endregion

}
