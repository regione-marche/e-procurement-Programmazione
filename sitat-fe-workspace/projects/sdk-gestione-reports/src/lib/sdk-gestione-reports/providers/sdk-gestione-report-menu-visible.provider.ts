import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from "@maggioli/sdk-commons";
import { SdkGestioneReportConstants } from '../sdk-gestione-report.constants';

@Injectable({providedIn: "root"})
export class SdkGestioneReportMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj)
    }

    //#region Public

    public run(args: IDictionary<any>): boolean {
        const userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            const abilitazioni: Array<string> = userProfile.abilitazioni;
            if (abilitazioni != null && abilitazioni.includes(SdkGestioneReportConstants.ABILITAZIONE_AMMINISTRATORE)) {
                return true;
            }
        }
        return false;
    }

    //#endregion
}