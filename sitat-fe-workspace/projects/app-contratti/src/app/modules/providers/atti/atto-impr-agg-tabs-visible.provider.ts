import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';


@Injectable({ providedIn: 'root' })
export class AttoImpreseAggiudicatarieTabsVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        this.logger.debug('AttoImpreseAggiudicatarieTabsVisibleProvider', args);
        let tipoDocumento: string = get(args, 'tipoDocumento');
        return tipoDocumento != null && (tipoDocumento === '20' || tipoDocumento === '14' || tipoDocumento === '19');
    }

}
