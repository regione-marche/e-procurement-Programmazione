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

import { ImpresaBaseEntry } from '../../models/archivio/archivio-imprese.models';
import { ArchivioImpreseService } from '../../services/archivio/archivio-imprese.service';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';

@Injectable()
export class AdvancedSearchImpreseProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        if (args != null) {
            if (args.code === 'SEARCH') {
                return (searchTerm: string) => {
                    const impreseSearchFactory = this.getImpreseSearchFactory(args.stazioneAppaltante.codice, searchTerm);
                    return this.requestHelper.begin(impreseSearchFactory, null, false)
                        .pipe(
                            map((items: Array<ImpresaBaseEntry>) => {
                                const searchItems = items != null && items.length > 0 ? mapArray(items, (item: ImpresaBaseEntry) => {
                                    const searchItem: SdkAdvancedSearchItemResult = {
                                        original: item,
                                        label: `${item.ragioneSociale} (${item.codiceFiscale})`
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
                            codiceImpresa: selectedItem.original.codiceImpresa
                        };
                        let parsedParams: string = this.redirectService.buildQueryParams(params);
                        let redirectParams: IDictionary<any> = {
                            redirectUrl: 'dettaglio-impresa-page',
                            redirectParams: parsedParams != null && parsedParams.length > 0 ? parsedParams.substring(1) : parsedParams
                        };
                        this.routerService.navigateToPage('redirect-page', redirectParams);
                    }
                }
            }
        }
        return null;
    }

    private getImpreseSearchFactory(stazioneAppaltante: string, searchTerm: string) {
        return () => {
            return this.archivioImpreseService.getListaImpreseNonPaginata(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, stazioneAppaltante, searchTerm);
        }
    }

    // #region Getters

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion

}