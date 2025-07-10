import { Injectable, Injector } from '@angular/core';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    CentroDiCostoEntry,
    CentroDiCostoInsertForm,
    ListaArchivioCdcParams,
    ResponseListaCentriDiCosto,
} from '../../models/archivio/archivio-centri-di-costo.models';
import { ResponseResult } from '../../models/common/common.model';


@Injectable()
export class ArchivioCentriDiCostoService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getListaCdc(baseUrl: string, request: ListaArchivioCdcParams, state: SdkTableState): Observable<ResponseListaCentriDiCosto> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            codiceCentro: request.codiceCentro
        };

        return this.restHelper.get<ResponseListaCentriDiCosto>(`${baseUrl}/getListaCdc`, params);
    }

    public deleteCdc(baseUrl: string, idCentro: string): Observable<any> {

        let params: IHttpParams = {
            codiceCdc: idCentro
        };

        return this.restHelper.delete<ResponseResult<any>>(`${baseUrl}/deleteCdc`, null, params);
    }

    public getCdc(baseUrl: string, idCentro: string): Observable<CentroDiCostoEntry> {
        let params: IHttpParams = {
            codiceCdc: idCentro
        };

        return this.restHelper.get<ResponseResult<CentroDiCostoEntry>>(`${baseUrl}/getCdc`, params)
            .pipe(
                map((result: ResponseResult<CentroDiCostoEntry>) => {
                    return result.data;
                })
            );
    }

    public creaCdc(baseUrl: string, insertForm: CentroDiCostoInsertForm): Observable<string> {
        return this.restHelper.post<ResponseResult<string>>(`${baseUrl}/insertCdc`, insertForm)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public updateCdc(baseUrl: string, insertForm: CentroDiCostoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${baseUrl}/updateCdc`, insertForm);
    }

    public getListaCdcNonPaginata(baseUrl: string, stazioneAppaltante: string, searchString: string): Observable<Array<CentroDiCostoEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<CentroDiCostoEntry>>>(`${baseUrl}/getListaCdcNonPaginata`, params)
            .pipe(
                map((result: ResponseResult<Array<CentroDiCostoEntry>>) => {
                    return result.data;
                })
            );
    }

    // #region Getter

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}