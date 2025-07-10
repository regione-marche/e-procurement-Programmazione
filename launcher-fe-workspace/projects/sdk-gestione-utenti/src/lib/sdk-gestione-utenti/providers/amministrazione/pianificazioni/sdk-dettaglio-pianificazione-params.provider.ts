import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import {
    SdkDettaglioPianificazioneStoreService,
} from '../../../components/amministrazione/pianificazioni/sdk-dettaglio-pianificazione/sdk-dettaglio-pianificazione-store.service';


@Injectable()
export class SdkDettaglioPianificazioneParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('SdkDettaglioPianificazioneParamsProvider', args);
        let codApp: string = this.sdkDettaglioPianificazioneStoreService.codApp;
        let beanId: string = this.sdkDettaglioPianificazioneStoreService.beanId;
        return {
            codApp,
            beanId
        };
    }

    // #region Getters

    private get sdkDettaglioPianificazioneStoreService(): SdkDettaglioPianificazioneStoreService { return this.injectable(SdkDettaglioPianificazioneStoreService) }

    // #endregion

}
