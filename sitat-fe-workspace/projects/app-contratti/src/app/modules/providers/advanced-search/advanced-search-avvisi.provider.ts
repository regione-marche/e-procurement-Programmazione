import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRedirectService, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkAdvancedSearchItemResult } from '@maggioli/sdk-controls';
import { map as mapArray } from 'lodash-es';
import { map } from 'rxjs/operators';

import { AvvisoEntry } from '../../models/avviso/avviso.model';
import { AvvisiService } from '../../services/avvisi/avvisi.service';


@Injectable({ providedIn: 'root' })
export class HeaderAdvancedSearchAvvisiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        if (args != null) {
            if (args.code === 'SEARCH') {
                return (searchString: string) => {
                    const avvisiSearchFactory = this.getAvvisiSearchFactory(args.stazioneAppaltante.codice, args.userProfile.syscon, searchString);
                    return this.requestHelper.begin(avvisiSearchFactory, null, false)
                        .pipe(
                            map((items: Array<AvvisoEntry>) => {
                                const searchItems = items != null && items.length > 0 ? mapArray(items, (item: AvvisoEntry) => {
                                    const searchItem: SdkAdvancedSearchItemResult = {
                                        original: item,
                                        label: `${item.numeroAvviso} ${item.descrizione}`
                                    };
                                    return searchItem;
                                }) : [];
                                return searchItems;
                            })
                        );
                }
            } else if (args.code === 'SELECT') {
                return (selectedItem: SdkAdvancedSearchItemResult) => {
                    if (selectedItem != null && selectedItem.original != null) {
                        let params: IDictionary<any> = {
                            idAvviso: selectedItem.original.numeroAvviso
                        };
                        let parsedParams: string = this.redirectService.buildQueryParams(params);
                        let redirectParams: IDictionary<any> = {
                            redirectUrl: 'dettaglio-avviso-page',
                            redirectParams: parsedParams != null && parsedParams.length > 0 ? parsedParams.substring(1) : parsedParams
                        };
                        this.routerService.navigateToPage('redirect-page', redirectParams);
                    }
                }
            }
        }
        return null;
    }

    private getAvvisiSearchFactory(stazioneAppaltante: string, syscon: number, searchString: string) {
        return () => {
            return this.avvisiService.getListaAvvisiNonPaginata(stazioneAppaltante, syscon, searchString);
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get avvisiService(): AvvisiService { return this.injectable(AvvisiService) }

    // #endregion

}