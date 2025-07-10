import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { DettaglioAttoStoreService } from '../../layout/components/business/atti/dettaglio-atto-store.service';


@Injectable({ providedIn: 'root' })
export class DettaglioAttoParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioAttoParamsProvider', args);
        let codGara: string = this.dettaglioAttoStore.codGara;
        let codLotto: string = this.dettaglioAttoStore.codLotto;
        let tipoDocumento: string = this.dettaglioAttoStore.tipoDocumento;
        let numPubb: string = this.dettaglioAttoStore.numPubb;
        let campiVisibili: string = this.dettaglioAttoStore.campiVisibili;
        let campiObbligatori: string = this.dettaglioAttoStore.campiObbligatori;
        let params: IDictionary<any> = {
            codGara,
            tipoDocumento,
            numPubb,
            campiVisibili,
            campiObbligatori
        };

        if (codLotto != null) {
            params.codLotto = codLotto;
        }

        return params;
    }

    // #region Getters

    private get dettaglioAttoStore(): DettaglioAttoStoreService { return this.injectable(DettaglioAttoStoreService) }

    // #endregion

}
