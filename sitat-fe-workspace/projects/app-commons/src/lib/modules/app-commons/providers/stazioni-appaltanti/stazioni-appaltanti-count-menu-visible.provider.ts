import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';


@Injectable()
export class StazioniAppaltantiCountMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        let stazioniAppaltantiCount: number = get(args, 'stazioniAppaltantiCount');
        return stazioniAppaltantiCount != null && stazioniAppaltantiCount > 1;
    }
}