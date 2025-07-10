import { Injectable, Injector } from '@angular/core';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    ImpresaBaseEntry,
    ImpresaEntry,
    ImpresaInsertForm,
    ListaArchivioImpreseParams,
    ResponseListaImprese,
} from '../../models/archivio/archivio-imprese.models';
import { ResponseResult } from '../../models/common/common.model';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';


@Injectable()
export class ArchivioImpreseService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getListaImprese(baseUrl: string, request: ListaArchivioImpreseParams, state: SdkTableState): Observable<ResponseListaImprese> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            codiceFiscale: request.codiceFiscale,
            comune: request.comune,
            email: request.email,
            legale: request.legale,
            partitaIva: request.partitaIva,
            pec: request.pec,
            provincia: request.provincia,
            ragioneSociale: request.ragioneSociale
        };

        return this.restHelper.get<ResponseListaImprese>(`${baseUrl}/getListaImprese`, params);
    }

    public deleteImpresa(baseUrl: string, codiceImpresa: string): Observable<any> {

        let params: IHttpParams = {
            codiceImpresa
        };

        return this.restHelper.delete<ResponseResult<any>>(`${baseUrl}/deleteImpresa`, null, params);
    }

    public getImpresa(baseUrl: string, codiceImpresa: string): Observable<ImpresaEntry> {
        let params: IHttpParams = {
            codiceImpresa
        };

        return this.restHelper.get<ResponseResult<ImpresaEntry>>(`${baseUrl}/getImpresa`, params)
            .pipe(
                map((result: ResponseResult<ImpresaEntry>) => {
                    return result.data;
                })
            );
    }

    public creaImpresa(baseUrl: string, insertForm: ImpresaInsertForm): Observable<string> {
        return this.restHelper.post<ResponseResult<string>>(`${baseUrl}/InsertImpresa`, insertForm)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public updateImpresa(baseUrl: string, insertForm: ImpresaInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${baseUrl}/updateImpresa`, insertForm);
    }

    public getImpreseOptions(baseUrl: string, stazioneAppaltante: StazioneAppaltanteInfo, desc: string): Observable<Array<ImpresaEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante: stazioneAppaltante.codice,
            desc
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaEntry>>>(`${baseUrl}/GetImpreseOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<ImpresaEntry>>) => {
                    return result.data;
                })
            );
    }

    public getImpreseOptionsVaraggi(baseUrl: string, stazioneAppaltante: StazioneAppaltanteInfo, codGara: string, codLotto: string, desc: string): Observable<Array<ImpresaEntry>> {


        let params: IHttpParams = {
            stazioneAppaltante: stazioneAppaltante.codice,
            codGara,
            codLotto,
            desc
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaEntry>>>(`${baseUrl}/getImpreseOptionsVaraggi`, params)
            .pipe(
                map((result: ResponseResult<Array<ImpresaEntry>>) => {
                    return result.data;
                })
            );
    }

    public getImpreseOptionsAggiSuba(baseUrl: string, stazioneAppaltante: StazioneAppaltanteInfo, codGara: string, codLotto: string, desc: string): Observable<Array<ImpresaEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante: stazioneAppaltante.codice,
            codGara,
            codLotto,
            desc
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaEntry>>>(`${baseUrl}/getImpreseOptionsAggiSuba`, params)
            .pipe(
                map((result: ResponseResult<Array<ImpresaEntry>>) => {
                    return result.data;
                })
            );
    }

    public getImpreseOptionsAggi(baseUrl: string, stazioneAppaltante: StazioneAppaltanteInfo, codGara: string, codLotto: string, desc: string): Observable<Array<ImpresaEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante: stazioneAppaltante.codice,
            codGara,
            codLotto,
            desc
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaEntry>>>(`${baseUrl}/getImpreseOptionsAggi`, params)
            .pipe(
                map((result: ResponseResult<Array<ImpresaEntry>>) => {
                    return result.data;
                })
            );
    }

    public getListaImpreseNonPaginata(baseUrl: string, stazioneAppaltante: string, searchString: string): Observable<Array<ImpresaBaseEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaBaseEntry>>>(`${baseUrl}/getListaImpreseNonPaginata`, params)
            .pipe(
                map((result: ResponseResult<Array<ImpresaBaseEntry>>) => {
                    return result.data;
                })
            );
    }

    // #region Getter

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}