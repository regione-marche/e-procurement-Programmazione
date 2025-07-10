import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { GareService } from '../../../../../../modules/services/gare/gare.service';
import { GaraBaseEntry, ListaGareParams, ResponseListaGare, RicercaGareForm } from '../../../../../models/gare/gare.model';


export class ListaGareDatasource extends SdkTableDatasource {

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

        let ob = this.getGare(state); // no cache

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
        return (res: ResponseListaGare): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getGare(state: SdkTableState): Observable<ResponseListaGare> {
        let getListaGare = this.getGareFactory(this.typedParams.searchForm, this.typedParams.stazioneAppaltante, this.typedParams.syscon, state);
        return this.requestHelper.begin(getListaGare, this.typedParams.messagesPanel);
    }

    private getGareFactory(searchForm: RicercaGareForm, stazioneAppaltante: string, syscon: string, state: SdkTableState) {
        return () => {
            let params: ListaGareParams = {
                ...searchForm,
                stazioneAppaltante,
                syscon: +syscon
            }
            return this.gareService.getListaGare(params, state);
        }
    }

    private adaptServerDataItem = (item: GaraBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: GaraBaseEntry): any => {
        let situazioneCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'Situazione');
        let situazioneItem: ValoreTabellato = find(situazioneCombo, (one: ValoreTabellato) => one.codice === toString(item.situazione));
        return {
            ...item,
            situazione: situazioneItem ? situazioneItem.descrizione : item.situazione,
            identificativoGara: item.smartCig === true ? '-' : item.identificativoGara // metto trattino se la gara e' smartCig
        };
    }

    private get typedParams(): {
        searchForm: RicercaGareForm,
        stazioneAppaltante: string,
        syscon: string,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaGareForm,
            stazioneAppaltante: string,
            syscon: string,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    // #endregion

}