import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { LottoEntry } from '../../models/gare/gare.model';


@Injectable({ providedIn: 'root' })
export class SchedaSicurezzaTabsVisible extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('SchedaSicurezzaTabsVisible', args);
        let lotto: LottoEntry = get(args, 'lotto');
        return lotto.manodopera === '1';
    }

    // #region Getters

    // #endregion

}
