import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkProvider,
    SdkRedirectService,
    SdkRouterService,
} from '@maggioli/sdk-commons';
import { SdkAdvancedSearchItemResult } from '@maggioli/sdk-controls';
import { map as mapArray } from 'lodash-es';
import { map } from 'rxjs/operators';

import { Ufficio } from '../../models/uffici/uffici.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiService } from '../../services/tabellati/tabellati.service';

@Injectable()
export class AdvancedSearchUfficiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        if (args != null) {
            if (args.code === 'SEARCH') {
                return (searchTerm: string) => {
                    const ufficiSearchFactory = this.getUfficiSearchFactory(args.stazioneAppaltante.codice, searchTerm);
                    return this.requestHelper.begin(ufficiSearchFactory, null, false)
                        .pipe(
                            map((items: Array<Ufficio>) => {
                                const searchItems = items != null && items.length > 0 ? mapArray(items, (item: Ufficio) => {
                                    const searchItem: SdkAdvancedSearchItemResult = {
                                        original: item,
                                        label: `${item.denominazione}`
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
                            codice: selectedItem.original.id
                        };
                        let parsedParams: string = this.redirectService.buildQueryParams(params);
                        let redirectParams: IDictionary<any> = {
                            redirectUrl: 'dettaglio-ufficio-page',
                            redirectParams: parsedParams != null && parsedParams.length > 0 ? parsedParams.substring(1) : parsedParams
                        };
                        this.routerService.navigateToPage('redirect-page', redirectParams);
                    }
                }
            }
        }
        return null;
    }

    private getUfficiSearchFactory(stazioneAppaltante: string, searchTerm: string) {
        return () => {
            return this.tabellatiService.getListaUfficiNonPaginata(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, stazioneAppaltante, searchTerm);
        }
    }

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion

}