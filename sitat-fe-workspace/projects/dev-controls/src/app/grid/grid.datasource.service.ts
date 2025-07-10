import { HttpClient } from '@angular/common/http';
import { Injector } from '@angular/core';
import { IDictionary, IHttpHeaders, IHttpOptions, IHttpParams } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { get, head, isObject, map as mapArray, merge, pickBy, toString } from 'lodash-es';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';


export class GridDatasourceService extends SdkTableDatasource {

    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    /**
     * Implementazione metodo di interrogazione
     * 
     * @param state: SdkTableState
     */
    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {

        // this.logger.debug('SdkTableState >>>', state);

        let ob = this.getLista(state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams);
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: any): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getLista(state: SdkTableState): Observable<any> {
        let requestParams: any = {
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            numero: state.filter != null ? get(state.filter, 'numero') : null
        };

        let headers: any = {
            'Cache-Control': 'no-cache',
            'Pragma': 'no-cache',
            'Expires': 'Sat, 01 Jan 2000 00:00:00 GMT'
        }

        let options = this.options(pickBy(requestParams), headers);

        return this.httpClient.get<any>('http://localhost:3000/api/lista', options).pipe(
            catchError((err: Error) => {
                return throwError(() => err);
            })
        );
    }

    private adaptServerDataItem = (item: any): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: any): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
    } {
        return this.params as {
        }
    }

    private options(params?: IHttpParams, headers?: IHttpHeaders, body?: any, observe: 'body' = 'body'): IHttpOptions {
        return { params, observe, body, headers: merge(this.headers(), headers), responseType: 'json', reportProgress: false };
    }

    private headers(): IHttpHeaders {
        return { 'Content-Type': 'application/json; charset=utf-8' };
    }

    private get httpClient(): HttpClient { return this.inj.get(HttpClient) }

}