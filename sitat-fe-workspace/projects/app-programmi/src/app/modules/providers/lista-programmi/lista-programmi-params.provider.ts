import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

import { ListaProgrammiStoreService } from '../../layout/components/business/lista-programmi/lista-programmi-store.service';


@Injectable({ providedIn: 'root' })
export class ListaProgrammiParamsProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('ListaProgrammiParamsProvider', args);
        let searchString: string = this.listaProgrammiStore.searchString;
        let tipologia: string = this.listaProgrammiStore.tipologia;
        return {
            searchString,
            tipologia
        };
    }

    // #region Getters

    private get listaProgrammiStore(): ListaProgrammiStoreService { return this.injectable(ListaProgrammiStoreService) }

    // #endregion

}
