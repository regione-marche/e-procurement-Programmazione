import { Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isObject, map as mapArray } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';


export class EsempioDatasource extends SdkTableDatasource {

    data = [{
        dato_1: 'stringa 1',
        dato_2: 'stringa 2',
        dato_3: 1,
        dato_4: 2
    }, {
        dato_1: 'stringa 3',
        dato_2: 'stringa 4',
        dato_3: 3,
        dato_4: 4
    }]


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

        let ob = this.getGare(); // no cache

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
        return isObject(this.typedParams)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {

        return (res: any): SdkTableDataResult => {

            let result = {
                data: mapArray(this.data, this.adaptServerDataItem),
                total: 10,
                gridState: state
            };
            return result;
        }
    }

    private getGare(): Observable<any> {

        //return this.requestHelper.begin(getListaGare);
        return new Observable((ob: Observer<any>) => {
            ob.next({
                data: this.data
            });
        });
    }

    private getGareFactory() {
        return () => {

            return this.data;
        }
    }

    private adaptServerDataItem = (item: any): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: any): any => {

        return {
            ...item,

        };
    }





    private get typedParams(): {

    } {
        return this.params as {


        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }


    // #endregion

}