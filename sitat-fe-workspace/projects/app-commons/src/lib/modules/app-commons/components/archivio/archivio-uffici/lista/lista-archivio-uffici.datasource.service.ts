import { Injector } from '@angular/core';
import { IDictionary, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { RicercaAvanzataArchivioUfficiForm } from '../../../../models/archivio/archivio-uffici.models';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ListaUfficiRequest, ResponseListaUfficiPaginata, Ufficio } from '../../../../models/uffici/uffici.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../../services/tabellati/tabellati.service';


export class ListaArchivioUfficiDatasource extends SdkTableDatasource {

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

        let ob = this.getUffici(state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.stazioneAppaltante)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaUfficiPaginata): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getUffici(state: SdkTableState): Observable<ResponseListaUfficiPaginata> {
        const requestParams: ListaUfficiRequest = {
            searchString: this.typedParams.searchForm ? this.typedParams.searchForm.descrizione : null,
            stazioneAppaltante: this.typedParams.stazioneAppaltante.codice
        }
        const factory = this.getFactory(requestParams, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getFactory(requestParams: ListaUfficiRequest, state: SdkTableState) {
        return () => {
            return this.tabellatiService.getListaUffici(this.typedParams.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, requestParams, state);
        }
    }

    private adaptServerDataItem = (item: Ufficio): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: Ufficio): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        searchForm: RicercaAvanzataArchivioUfficiForm,
        stazioneAppaltante: StazioneAppaltanteInfo,
        messagesPanel: HTMLElement,
        appConfig: SdkAppEnvConfig
    } {
        return this.params as {
            searchForm: RicercaAvanzataArchivioUfficiForm,
            stazioneAppaltante: StazioneAppaltanteInfo,
            messagesPanel: HTMLElement,
            appConfig: SdkAppEnvConfig
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    // #endregion

}