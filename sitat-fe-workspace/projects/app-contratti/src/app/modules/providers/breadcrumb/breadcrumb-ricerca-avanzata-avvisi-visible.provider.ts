import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkStateMap } from '@maggioli/sdk-commons';
import { get, isEmpty } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class BreadcrumbRicercaAvanzataAvvisiVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('BreadcrumbRicercaAvanzataAvvisiVisibleProvider >>>', args);
        const state: SdkStateMap = get(args, 'state');
        const visible: boolean = state != null && state.searchFormS != null && !isEmpty(state.searchFormS);
        return visible;
    }

}
