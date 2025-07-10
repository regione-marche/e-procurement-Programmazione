import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';



@Injectable({ providedIn: 'root' })
export class ListaDelegheVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {            
            if (userProfile.codiceFiscale != null) {
                return true;
            }
        }
        return false;
    }
}