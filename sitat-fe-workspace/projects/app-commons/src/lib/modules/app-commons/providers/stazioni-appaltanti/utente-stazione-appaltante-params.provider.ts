import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    UtenteStazioneAppaltanteStoreService,
} from '../../components/archivio/archivio-stazioni-appaltanti/utenti-stazione-appaltante/utente-stazione-appaltante-store.service';


@Injectable()
export class UtenteStazioneAppaltanteParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('UtenteStazioneAppaltanteParamsProvider', args);
        let syscon: number = this.utenteStazioneAppaltanteStoreService.syscon;
        let codice: string = this.utenteStazioneAppaltanteStoreService.codice;
        return {
            syscon,
            codice
        };
    }

    // #region Getters

    private get utenteStazioneAppaltanteStoreService(): UtenteStazioneAppaltanteStoreService { return this.injectable(UtenteStazioneAppaltanteStoreService) }

    // #endregion

}
