import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';
import { get } from 'lodash-es';

@Injectable()
export class MessaggiAvvisiVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        let userProfile: UserProfile = get(args, 'userProfile');
        if (userProfile != null) {
            let messaggisticaInternaAttiva: boolean = !!userProfile.messaggisticaInternaAttiva;
            return messaggisticaInternaAttiva;
        }
        return false;
    }
}