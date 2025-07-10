import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkDettaglioCodificaAutomaticaStoreService,
} from '../../../components/amministrazione/codifica-automatica/sdk-dettaglio-codifica-automatica/sdk-dettaglio-codifica-automatica-store.service';


@Injectable()
export class SdkDettaglioCodificaAutomaticaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('SdkDettaglioCodificaAutomaticaParamsProvider', args);
        let nomEnt: string = this.sdkDettaglioCodificaAutomaticaStoreService.nomEnt;
        let nomCam: string = this.sdkDettaglioCodificaAutomaticaStoreService.nomCam;
        let tipCam: number = this.sdkDettaglioCodificaAutomaticaStoreService.tipCam;
        return {
            nomEnt,
            nomCam,
            tipCam
        };
    }

    // #region Getters

    private get sdkDettaglioCodificaAutomaticaStoreService(): SdkDettaglioCodificaAutomaticaStoreService { return this.injectable(SdkDettaglioCodificaAutomaticaStoreService) }

    // #endregion

}
