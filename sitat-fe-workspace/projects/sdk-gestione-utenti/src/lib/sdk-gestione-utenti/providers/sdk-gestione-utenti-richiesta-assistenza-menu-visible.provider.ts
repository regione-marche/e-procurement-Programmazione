import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';

@Injectable()
export class SdkGestioneUtentiRichiestaAssistenzaMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            const richiestaAssistenzaAttiva: boolean = userProfile.richiestaAssistenzaAttiva;
            return richiestaAssistenzaAttiva;
        }
        return false;
    }
}