import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { DettaglioAttoEntry } from '../../models/gare/gare.model';


@Injectable({ providedIn: 'root' })
export class AttoLottiTabsVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('AttoLottiTabsVisibleProvider', args);
        const atto: DettaglioAttoEntry = get(args, 'atto');
        return atto != null && atto.countLotti != null && atto.countLotti > 1;
    }

}
