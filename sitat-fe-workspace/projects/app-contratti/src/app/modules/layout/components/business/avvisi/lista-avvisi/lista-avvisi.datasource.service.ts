import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, isEmpty, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AvvisoEntry, ResponseListaAvvisi, RicercaAvvisoForm } from '../../../../../models/avviso/avviso.model';
import { AvvisiService } from '../../../../../services/avvisi/avvisi.service';


export class ListaAvvisiDatasource extends SdkTableDatasource {

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

        let ob = this.getAvvisi(state); // no cache

        // let ob = this.cacheService.cacheObservable({ _: 'products_test_1_datasource', state }, () => {
        //     return this.inj.get(HttpClient).get(this.endpoint(state))
        // }) // con cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && isObject(this.typedParams.searchForm)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaAvvisi): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getAvvisi(state: SdkTableState): Observable<ResponseListaAvvisi> {
        let getListaAvvisi = this.getAvvisiFactory(this.typedParams.searchForm, this.typedParams.stazioneAppaltante, this.typedParams.syscon, state);
        return this.requestHelper.begin(getListaAvvisi, this.typedParams.messagesPanel);
    }

    private getAvvisiFactory(searchForm: RicercaAvvisoForm, stazioneAppaltante: string, syscon: string, state: SdkTableState) {
        return () => {
            const params: RicercaAvvisoForm = {
                ...searchForm,
                stazioneAppaltante,
                syscon
            };
            return this.avvisiService.getListaAvvisi(params, state);
        }
    }

    private adaptServerDataItem = (item: AvvisoEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: AvvisoEntry): any => {
        let tipoAvviso: ValoreTabellato = find(this.typedParams.tipiAvviso, (one: ValoreTabellato) => one.codice === toString(item.tipoAvviso));
        return {
            ...item,
            id: toString(item.numeroAvviso),
            pubblicato: item.idRicevuto != null && item.idRicevuto != undefined,
            descrizioneTipoAvviso: isObject(tipoAvviso) ? tipoAvviso.descrizione : item.tipoAvviso
        };
    }

    private get typedParams(): {
        searchForm: RicercaAvvisoForm,
        tipiAvviso: Array<ValoreTabellato>,
        stazioneAppaltante: string,
        syscon: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaAvvisoForm,
            tipiAvviso: Array<ValoreTabellato>,
            stazioneAppaltante: string,
            syscon: string,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get avvisiService(): AvvisiService { return this.injectable(AvvisiService) }

    // #endregion

}