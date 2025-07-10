import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class SmartCigTabsVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('SmartCigTabsVisibleProvider', args);
        const smartCig: boolean = get(args, 'smartCig');
        return smartCig === true;
    }

}