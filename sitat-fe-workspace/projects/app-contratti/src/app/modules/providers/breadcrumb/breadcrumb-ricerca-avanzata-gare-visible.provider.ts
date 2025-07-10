import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkStateMap } from '@maggioli/sdk-commons';
import { get, isEmpty } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class BreadcrumbRicercaAvanzataGareVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('BreadcrumbRicercaAvanzataGareVisibleProvider >>>', args);
        const state: SdkStateMap = get(args, 'state');
        const visible: boolean = state != null && state.searchFormGS != null && !isEmpty(state.searchFormGS);
        return visible;
    }

}
