import { Injector } from '@angular/core';
import { IDictionary, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    ListaStazioniAppaltantiRequest,
    ResponseListaStazioneAppaltante,
    RicercaAvanzataArchivioStazioniAppaltantiForm,
    StazioneAppaltanteListaEntry,
} from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../../services/tabellati/tabellati.service';


export class ListaArchivioStazioniAppaltantiDatasource extends SdkTableDatasource {

    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    /**
     * Implementazione metodo di interrogazione
     * 
     * @param state: SdkTableState
     */
    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {

        this.logger.debug('SdkTableState >>>', state);

        let ob = this.getListaStazioniAppaltanti(state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return true;
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaStazioneAppaltante): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getListaStazioniAppaltanti(state: SdkTableState): Observable<ResponseListaStazioneAppaltante> {
        let requestParams: ListaStazioniAppaltantiRequest = {};

        if (this.typedParams.searchForm != null) {
            requestParams = {
                citta: this.typedParams.searchForm.citta,
                codFisc: this.typedParams.searchForm.codiceFiscale,
                codiceAnagrafico: this.typedParams.searchForm.codiceAnagrafico,
                denominazione: this.typedParams.searchForm.denominazione,
                indirizzo: this.typedParams.searchForm.indirizzo,
                provincia: this.typedParams.searchForm.provincia,
                codAusa: this.typedParams.searchForm.codAusa
            };
        }

        const getListaStazioniAppaltanti = this.getListaStazioniAppaltantiFactory(requestParams, state);
        return this.requestHelper.begin(getListaStazioniAppaltanti, this.typedParams.messagesPanel);
    }

    private getListaStazioniAppaltantiFactory(requestParams: ListaStazioniAppaltantiRequest, state: SdkTableState) {
        return () => {
            return this.tabellatiService.getListaStazioniAppaltanti(this.typedParams.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, requestParams, state);
        }
    }

    private adaptServerDataItem = (item: StazioneAppaltanteListaEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: StazioneAppaltanteListaEntry): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        searchForm: RicercaAvanzataArchivioStazioniAppaltantiForm,
        messagesPanel: HTMLElement,
        appConfig: SdkAppEnvConfig
    } {
        return this.params as {
            searchForm: RicercaAvanzataArchivioStazioniAppaltantiForm,
            messagesPanel: HTMLElement,
            appConfig: SdkAppEnvConfig
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    // #endregion

}