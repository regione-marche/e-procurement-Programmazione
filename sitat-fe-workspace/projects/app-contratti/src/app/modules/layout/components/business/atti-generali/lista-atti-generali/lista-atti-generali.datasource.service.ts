import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AttoGeneraleEntry, ResponseListaAttiGenerali, RicercaAttiGeneraliForm } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { AttiGeneraliService } from 'projects/app-contratti/src/app/modules/services/atti-generali/atti-generali.service';


export class ListaAttiGeneraliDatasource extends SdkTableDatasource {

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

        let ob = this.getAttiGenerali(state); // no cache

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
        return (res: ResponseListaAttiGenerali): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getAttiGenerali(state: SdkTableState): Observable<ResponseListaAttiGenerali> {
        let getListaAttiGenerali = this.getAttiGeneraliFactory(this.typedParams.searchForm, this.typedParams.stazioneAppaltante, this.typedParams.syscon, state);
        return this.requestHelper.begin(getListaAttiGenerali, this.typedParams.messagesPanel);
    }

    private getAttiGeneraliFactory(searchForm: RicercaAttiGeneraliForm, stazioneAppaltante: string, syscon: string, state: SdkTableState) {
        return () => {
            const params: RicercaAttiGeneraliForm = {
                ...searchForm,
                stazioneAppaltante,
                syscon
            };
            return this.attiGeneraliService.getListaAttiGenerali(params, state);
        }
    }

    private adaptServerDataItem = (item: AttoGeneraleEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: AttoGeneraleEntry): any => {
        //Sicuramente da adattare per il valore tabellati corretto.
        let tipologieAtto: ValoreTabellato = find(this.typedParams.tipologieAtto, (one: ValoreTabellato) => one.codice === toString(item.tipologia));
        return {
            ...item,
            idAtto: toString(item.idAtto),
            tipologia: tipologieAtto ? tipologieAtto.descrizione : item.tipologia
        };
    }

    private get typedParams(): {
        searchForm: RicercaAttiGeneraliForm,
        tipologieAtto: Array<ValoreTabellato>,
        stazioneAppaltante: string,
        syscon: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaAttiGeneraliForm,
            tipologieAtto: Array<ValoreTabellato>,
            stazioneAppaltante: string,
            syscon: string,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get attiGeneraliService(): AttiGeneraliService { return this.injectable(AttiGeneraliService) }

    // #endregion

}