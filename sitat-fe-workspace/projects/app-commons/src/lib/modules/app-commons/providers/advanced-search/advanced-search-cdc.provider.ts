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

import { CentroDiCostoEntry } from '../../models/archivio/archivio-centri-di-costo.models';
import { ArchivioCentriDiCostoService } from '../../services/archivio/archivio-centri-di-costo.service';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';

@Injectable()
export class AdvancedSearchCdcProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        if (args != null) {
            if (args.code === 'SEARCH') {
                return (searchTerm: string) => {
                    const cdcSearchFactory = this.getCdcSearchFactory(args.stazioneAppaltante.codice, searchTerm);
                    return this.requestHelper.begin(cdcSearchFactory, null, false)
                        .pipe(
                            map((items: Array<CentroDiCostoEntry>) => {
                                const searchItems = items != null && items.length > 0 ? mapArray(items, (item: CentroDiCostoEntry) => {
                                    const searchItem: SdkAdvancedSearchItemResult = {
                                        original: item,
                                        label: `${item.denominazione} (${item.codiceCentro})`
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
                            idCentro: selectedItem.original.id
                        };
                        let parsedParams: string = this.redirectService.buildQueryParams(params);
                        let redirectParams: IDictionary<any> = {
                            redirectUrl: 'dettaglio-cdc-page',
                            redirectParams: parsedParams != null && parsedParams.length > 0 ? parsedParams.substring(1) : parsedParams
                        };
                        this.routerService.navigateToPage('redirect-page', redirectParams);
                    }
                }
            }
        }
        return null;
    }

    private getCdcSearchFactory(stazioneAppaltante: string, searchTerm: string) {
        return () => {
            return this.archivioCentriDiCostoService.getListaCdcNonPaginata(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, stazioneAppaltante, searchTerm);
        }
    }

    // #region Getters

    private get archivioCentriDiCostoService(): ArchivioCentriDiCostoService { return this.injectable(ArchivioCentriDiCostoService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion

}