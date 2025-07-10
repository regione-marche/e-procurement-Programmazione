import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    CentroDiCostoEntry,
    ListaArchivioCdcParams,
    ResponseListaCentriDiCosto,
    RicercaAvanzataArchivioCdcForm,
} from '../../../../models/archivio/archivio-centri-di-costo.models';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ResponseListaRupPaginata } from '../../../../models/tabellati/tabellato.model';
import { ArchivioCentriDiCostoService } from '../../../../services/archivio/archivio-centri-di-costo.service';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';


export class ListaArchivioCdcDatasource extends SdkTableDatasource {

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

        let ob = this.getCdc(this.typedParams.searchForm, state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaCentriDiCosto): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getCdc(searchForm: RicercaAvanzataArchivioCdcForm, state: SdkTableState): Observable<ResponseListaRupPaginata> {
        let params: ListaArchivioCdcParams = {
            ...searchForm           
        };
        if(isEmpty(searchForm.stazioneAppaltante)){
            params = {
                ...searchForm,
                stazioneAppaltante: this.typedParams.stazioneAppaltante.codice
            };
        }

        let getListaCdc = this.getListaCdcFactory(params, state);
        return this.requestHelper.begin(getListaCdc, this.typedParams.messagesPanel);
    }

    private getListaCdcFactory(params: ListaArchivioCdcParams, state: SdkTableState) {
        return () => {
            return this.archivioCdcService.getListaCdc(this.typedParams.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, params, state);
        }
    }

    private adaptServerDataItem = (item: CentroDiCostoEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: CentroDiCostoEntry): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        searchForm: RicercaAvanzataArchivioCdcForm,
        stazioneAppaltante: StazioneAppaltanteInfo,
        messagesPanel: HTMLElement,
        appConfig: SdkAppEnvConfig
    } {
        return this.params as {
            searchForm: RicercaAvanzataArchivioCdcForm,
            stazioneAppaltante: StazioneAppaltanteInfo,
            messagesPanel: HTMLElement,
            appConfig: SdkAppEnvConfig
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioCdcService(): ArchivioCentriDiCostoService { return this.injectable(ArchivioCentriDiCostoService) }

    // #endregion

}