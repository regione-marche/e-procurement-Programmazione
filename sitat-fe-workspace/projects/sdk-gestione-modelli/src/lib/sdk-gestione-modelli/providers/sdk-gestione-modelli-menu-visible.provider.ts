import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';
import { SdkGestioneModelliConstants } from '../sdk-gestione-modelli.constants';

@Injectable()
export class SdkGestioneModelliMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {

        let userProfile: UserProfile = args.userProfile;

        return userProfile && (
            userProfile?.abilitazioni?.includes(SdkGestioneModelliConstants.OU_GESTIONE_MODELLI_COMPLETA) ||
            userProfile?.abilitazioni?.includes(SdkGestioneModelliConstants.OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI)
        );

    }
}