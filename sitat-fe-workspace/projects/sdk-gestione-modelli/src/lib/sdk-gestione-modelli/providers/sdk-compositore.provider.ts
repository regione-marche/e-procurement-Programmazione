import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, SdkStoreService } from '@maggioli/sdk-commons';
import { Observable, Observer, of } from 'rxjs';


import { SdkDettaglioModelloStoreService } from '../components/sdk-dettaglio-modello/sdk-dettaglio-modello-store.service';
import { SdkDettaglioParametroStoreService } from '../components/sdk-dettaglio-parametro/sdk-dettaglio-parametro-store.service';
import { GestioneModelliService } from '../services/gestione-modelli.service';


@Injectable()
export class SdkCompositoreProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        this.logger.debug('SdkRicercaModelliProvider', args);
        if (args.data.code === 'modal-close-button') {
            return of({ close: true });
        }
        if (args.code === 'set-parameters') {

        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkDettaglioModelloStoreService(): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

    private get sdkDettaglioParametroStoreService(): SdkDettaglioParametroStoreService { return this.injectable(SdkDettaglioParametroStoreService) }


    private get gestioneModelliService(): GestioneModelliService { return this.injectable(GestioneModelliService) }

    // #endregion

}
