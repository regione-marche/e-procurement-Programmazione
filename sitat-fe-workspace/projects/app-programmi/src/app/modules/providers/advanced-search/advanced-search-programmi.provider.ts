import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRedirectService, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkAdvancedSearchItemResult } from '@maggioli/sdk-controls';
import { map as mapArray } from 'lodash-es';
import { map } from 'rxjs/operators';

import {
    DettaglioProgrammaStoreService,
} from '../../layout/components/business/dettaglio-programma/dettaglio-programma-store.service';
import { ProgrammaBaseEntry } from '../../models/programmi/programmi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';


@Injectable({ providedIn: 'root' })
export class HeaderAdvancedSearchProgrammiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        if (args != null) {
            if (args.code === 'SEARCH') {
                return (searchString: string) => {
                    const programmiSearchFactory = this.getProgrammiSearchFactory(args.stazioneAppaltante.codice, args.userProfile.syscon, searchString);
                    return this.requestHelper.begin(programmiSearchFactory, null, false)
                        .pipe(
                            map((items: Array<ProgrammaBaseEntry>) => {
                                const searchItems = items != null && items.length > 0 ? mapArray(items, (item: ProgrammaBaseEntry) => {
                                    const searchItem: SdkAdvancedSearchItemResult = {
                                        original: item,
                                        label: `${item.idProgramma} - ${item.descrizioneBreve}`
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
                            idProgramma: selectedItem.original.id,
                            tipologia: selectedItem.original.tipoProg
                        };
                        let parsedParams: string = this.redirectService.buildQueryParams(params);
                        let redirectParams: IDictionary<any> = {
                            redirectUrl: 'dett-prog-dati-generali-page',
                            redirectParams: parsedParams != null && parsedParams.length > 0 ? parsedParams.substring(1) : parsedParams
                        };

                        this.dettaglioProgrammaStore.clear();
                        this.dettaglioProgrammaStore.idProgramma = selectedItem.original.id;
                        this.dettaglioProgrammaStore.tipologia = selectedItem.original.tipoProg;

                        this.routerService.navigateToPage('redirect-page', redirectParams);
                    }
                }
            }
        }
        return null;
    }

    private getProgrammiSearchFactory(stazioneAppaltante: string, syscon: number, searchString: string) {
        return () => {
            return this.programmiService.getListaProgrammiNonPaginata(stazioneAppaltante, syscon, searchString);
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get dettaglioProgrammaStore(): DettaglioProgrammaStoreService { return this.injectable(DettaglioProgrammaStoreService) }

    // #endregion

}