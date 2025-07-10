import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';


@Injectable({ providedIn: 'root' })
export class DettProgFornitureTabsVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('DettProgFornitureTabsVisible', args);
        let tipologia: string = get(args, 'tipologia');
        return tipologia === '2';
    }

    // #region Getters

    // #endregion

}
