import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRedirectService, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkAdvancedSearchItemResult } from '@maggioli/sdk-controls';
import { map as mapArray } from 'lodash-es';
import { map } from 'rxjs/operators';

import { DettaglioGaraStoreService } from '../../layout/components/business/gare/dettaglio-gara-store.service';
import { DettaglioSmartCigStoreService } from '../../layout/components/business/smartcig/dettaglio-smartcig-store.service';
import { GaraBaseEntry } from '../../models/gare/gare.model';
import { GareService } from '../../services/gare/gare.service';
import { DettaglioImpresaAggiudicatariaStoreService } from '../../layout/components/business/fasi/imprese-aggiudicatarie/dettaglio-impresa-aggiudicataria-store.service';
import { DettaglioFaseStoreService } from '../../layout/components/business/fasi/dettaglio-fase-store.service';
import { DettaglioLottoStoreService } from '../../layout/components/business/lotti/dettaglio-lotto-store.service';
import { DettaglioImpresaInvitataPartecipanteStoreService } from '../../layout/components/business/fasi/elenco-impr-inv-parte/dettaglio-impr-inv-parte-store.service';


@Injectable({ providedIn: 'root' })
export class HeaderAdvancedSearchGareProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        if (args != null) {
            if (args.code === 'SEARCH') {
                return (searchString: string) => {
                    const gareSearchFactory = this.getGareSearchFactory(args.stazioneAppaltante.codice, args.userProfile.syscon, searchString);
                    return this.requestHelper.begin(gareSearchFactory, null, false)
                        .pipe(
                            map((items: Array<GaraBaseEntry>) => {
                                const searchItems = items != null && items.length > 0 ? mapArray(items, (item: GaraBaseEntry) => {
                                    
                                    let searchItem: SdkAdvancedSearchItemResult = {
                                        original: item,
                                        label: `${item.identificativoGara} - ${item.oggetto}`
                                    };
                                    if(item.smartCig === true){
                                        searchItem = {
                                            original: item,
                                            label: `${item.cigLotti} - ${item.oggetto}`
                                        };
                                    } else {
                                        searchItem = {
                                            original: item,
                                            label: `${item.identificativoGara} - ${item.oggetto}`
                                        };
                                    }


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
                            codGara: selectedItem.original.codgara
                        };
                        let parsedParams: string = this.redirectService.buildQueryParams(params);
                        let redirectParams: IDictionary<any> = {
                            redirectParams: parsedParams != null && parsedParams.length > 0 ? parsedParams.substring(1) : parsedParams
                        };

                        this.dettaglioSmartCigStoreService.clear();
                        this.dettaglioGaraStoreService.clear();
                        //Pulizia schede trasmesse a PCP
                        this.dettaglioFaseStoreService.clearFromLS();
                        this.dettaglioLottoStoreService.clearFromLS();
                        this.dettaglioImpresaAggiudicatariaStoreService.clearFromLS();
                        this.dettaglioImprInvParteStoreService.clearFromLS();

                        if (selectedItem.original.smartCig === true) {
                            this.dettaglioSmartCigStoreService.codGara = selectedItem.original.codgara;
                            redirectParams = {
                                ...redirectParams,
                                redirectUrl: 'dettaglio-smartcig-page'
                            };
                        } else {
                            this.dettaglioGaraStoreService.codGara = selectedItem.original.codgara;
                            redirectParams = {
                                ...redirectParams,
                                redirectUrl: 'dettaglio-gara-page'
                            };
                        }


                        this.routerService.navigateToPage('redirect-page', redirectParams);
                    }
                }
            }
        }
        return null;
    }

    private getGareSearchFactory(stazioneAppaltante: string, syscon: number, searchString: string) {
        return () => {
            return this.gareService.getListaGareNonPaginata(stazioneAppaltante, syscon, searchString);
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get dettaglioGaraStoreService(): DettaglioGaraStoreService { return this.injectable(DettaglioGaraStoreService) }

    private get dettaglioSmartCigStoreService(): DettaglioSmartCigStoreService { return this.injectable(DettaglioSmartCigStoreService) }

    private get dettaglioImpresaAggiudicatariaStoreService(): DettaglioImpresaAggiudicatariaStoreService { return this.injectable(DettaglioImpresaAggiudicatariaStoreService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    private get dettaglioLottoStoreService(): DettaglioLottoStoreService { return this.injectable(DettaglioLottoStoreService) }

    private get dettaglioImprInvParteStoreService(): DettaglioImpresaInvitataPartecipanteStoreService { return this.injectable(DettaglioImpresaInvitataPartecipanteStoreService) }

    // #endregion

}