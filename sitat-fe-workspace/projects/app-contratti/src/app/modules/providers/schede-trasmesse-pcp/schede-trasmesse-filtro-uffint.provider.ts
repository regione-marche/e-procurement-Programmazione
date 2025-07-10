import { Injectable, Injector } from "@angular/core";
import { HttpRequestHelper, TabellatiService, ValoreTabellato } from "@maggioli/app-commons";
import { SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";
import { SdkAutocompleteItem } from "@maggioli/sdk-controls";
import { each } from "lodash-es";
import { environment } from "projects/app-contratti/src/environments/environment";
import { map, Observable } from "rxjs";

@Injectable({ providedIn: 'root' })
export class SchedeTrasmesseFiltroUffIntProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = null;
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}