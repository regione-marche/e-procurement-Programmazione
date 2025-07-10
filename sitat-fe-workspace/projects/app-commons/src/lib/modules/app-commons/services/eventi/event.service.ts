import { Inject, Injectable, Injector } from "@angular/core";
import { AuthenticationTokenInfo, ResponseDTO } from "@maggioli/app-commons";
import { IHttpHeaders, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkRestHelperService } from "@maggioli/sdk-commons";
import { Observable } from "rxjs";
@Injectable({
    providedIn: "root"
})
export class EventService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj); }

    public writeLogoutEvent(baseUrl: string): Observable<ResponseDTO<string>> {

        return this.restHelper.post<ResponseDTO<string>>(baseUrl + `/gestioneEventi/logout`);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService); };

    // #endregion
}
