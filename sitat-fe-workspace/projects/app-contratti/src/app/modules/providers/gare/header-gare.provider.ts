import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkSimpleSearchOutput } from '@maggioli/sdk-controls';
import { get } from 'lodash-es';
import { Observable, of } from 'rxjs';


@Injectable({ providedIn: 'root' })
export class HeaderGareProvider extends SdkBaseService implements SdkProvider {

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {

        let item: SdkSimpleSearchOutput = get(args, 'item');

        let params: IDictionary<string> = {
            descrizione: item.data
        };

        if (item.filterCode != null) {
            if (item.filterCode === '1') {
                this.routerService.navigateToPage('lista-gare-page', params);
            } else if (item.filterCode === '2') {
                this.routerService.navigateToPage('lista-avvisi-page', params);
            }
        }
        return of(undefined);
    }

    constructor(inj: Injector) {
        super(inj);
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

}