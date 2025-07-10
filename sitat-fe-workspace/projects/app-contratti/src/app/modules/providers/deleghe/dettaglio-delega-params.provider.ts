import { DettaglioDelegaStoreService } from './../../layout/components/business/deleghe/dettaglio-delega-store.service';
import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioDelegaParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioDelegaParamsProvider', args);
        let id: string = this.dettaglioDelegaStore.id;
        return {
          id
        };
    }

    // #region Getters

    private get dettaglioDelegaStore(): DettaglioDelegaStoreService { return this.injectable(DettaglioDelegaStoreService) }

    // #endregion

}
