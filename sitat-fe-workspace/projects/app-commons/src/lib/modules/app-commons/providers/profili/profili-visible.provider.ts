import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';

@Injectable()
export class ProfiliVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        let stazioneAppaltanteInfo: StazioneAppaltanteInfo = get(args, 'stazioneAppaltanteInfo');
        if (stazioneAppaltanteInfo != null) {
            return stazioneAppaltanteInfo.profili != null && stazioneAppaltanteInfo.profili.length > 1;
        }
        return false;
    }
}