import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from "@maggioli/sdk-commons";
import { Observable, of } from "rxjs";

@Injectable()
export class UltimiAccessiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('UltimiAccessiProvider', args);
        this.routerService.navigateToPage('ultimi-accessi-page');
        return of({});
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

}