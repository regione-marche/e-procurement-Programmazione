import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ChooseEnteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('ChangeEnteProvider', args);
        this.routerService.navigateToPage('choose-ente-page');
        return of({ changeEnte: true });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

}
