import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { Observable, Observer, of } from 'rxjs';

export interface SdkListaMnemoniciProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    homeSlug?: string;
}

@Injectable()
export class SdkListaMnemoniciProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaMnemoniciProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaMnemoniciProviderArgs >>>', args);
        if (args.buttonCode === 'back') {
            return this.back(args);
        }
        return of(args);
    }

    private back(args: SdkListaMnemoniciProviderArgs): Observable<IDictionary<any>> {
        let homeSlug = args.homeSlug != null ? args.homeSlug : 'home-page';
            this.routerService.navigateToPage(homeSlug);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

}
