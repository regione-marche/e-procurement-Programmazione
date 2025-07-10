import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isEmpty, isObject, set } from 'lodash-es';
import { of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class HeaderProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any) {
        let params: IDictionary<string> = {};
        if (isObject(args.item) && !isEmpty(args.item.data)) {
            set(params, 'searchString', args.item.data);
        }
        this.routerService.navigateToPage('lista-programmi-page', params);
        return of(undefined);
    }


    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

}