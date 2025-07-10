import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { AttoGeneraleInsertForm, AttoGeneraleUpdateForm, ListaAttiGeneraliParams, ResponseDettaglioAttoGenerale, ResponseListaAttiGenerali } from '../../models/atti-generali/atti-generali.model';


@Injectable({ providedIn: 'root' })
export class AttiGeneraliService extends SdkBaseService {    
    

    constructor(inj: Injector) { super(inj); }

    public dettaglioAttoGenerale(request: ListaAttiGeneraliParams): Observable<ResponseDettaglioAttoGenerale> {

        let params: IHttpParams = {
            skip: null,
            take: null,
            sort: null,
            sortDirection: null,
            stazioneAppaltante: request.stazioneAppaltante,
            idAtto: request.idAtto,
            tipologia: toString(request.tipologia),
            descrizione: request.descrizione,
            rup: request.rup,
            dataPubbSistema: "",
            syscon: request.syscon
        };

        return this.restHelper.get<ResponseDettaglioAttoGenerale>(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/getDettaglioAttoGenerale`, params)
    }

    public getListaAttiGenerali(request: ListaAttiGeneraliParams, state: SdkTableState): Observable<ResponseListaAttiGenerali> {

        let params: IHttpParams = {
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            stazioneAppaltante: request.stazioneAppaltante,
            idAtto: request.idAtto,
            tipologia: toString(request.tipologia),
            descrizione: request.descrizione,
            rup: request.rup,
            dataPubbSistema: toString(request.dataPubbSistema),
            dataPubbSistemaDa: toString(request.dataPubbSistemaDa),
            dataPubbSistemaA: toString(request.dataPubbSistemaA),
            syscon: request.syscon
        };

        return this.restHelper.get<ResponseListaAttiGenerali>(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/getListaAttiGenerali`, params);
    }

    public deleteAttoGeneraleSingolo(idAtto: string, codProfilo: string): Observable<any> {
        let params: IHttpParams = {
            idAtto,
            codProfilo
        };

        return this.restHelper.delete(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/deleteAttoGenerale`, null, params);
    }

    public annullaPubblicazione(idAtto: string, codProfilo: string): Observable<any> {
        let params: IHttpParams = {
            idAtto,
            codProfilo
        };

        return this.restHelper.put(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/annullaPubblicazione`, null, params);
    }

    public annullaPubblicazioneMotivazione(idAtto:string, codProfilo: string, motivoCanc: string): Observable<any> {
        let params: IHttpParams = {
            idAtto,
            codProfilo,
            motivoCanc
        };

        return this.restHelper.put(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/annullaPubblicazioneMotivazione`, null, params);
    }

    public modificaAttoGenerale(updateForm: AttoGeneraleUpdateForm): Observable<string> {
        return this.restHelper.put(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/updateDettaglioAttoGenerale`, updateForm);
    }

    public downloadDocumentoAttoGenerale(idAtto: number, idAllegato: number): Observable<string> {
        let params: IHttpParams = {
            idAtto: toString(idAtto),
            idAllegato: toString(idAllegato)
        };
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/downloadDocumentoAttoGenerale`, params)
            .pipe(map((result: ResponseResult<string>) => {
                return result.data;
            }));
    }

    public creaNuovoAttoGenerale(request: AttoGeneraleInsertForm): Observable<string> {

        return this.restHelper.post<ResponseResult<string>>(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/creaNuovoAttoGenerale`, request)
            .pipe(map((result: ResponseResult<string>) => {
                return result.data;
            }));
    }

    public pubblicaAttoGenerale(idAtto: number): Observable<any> {
        let params: IHttpParams = {
            idAtto: toString(idAtto)
        };

        return this.restHelper.put(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/pubblicaAttoGenerale`, null, params);
    }

    public pubblicaAttoGeneraleDataFutura(idAtto: number, dataProgrammazione: Date): Observable<string> {
        let params: IHttpParams = {
            idAtto: toString(idAtto),
            dataProgrammazione: toString(dataProgrammazione)
        };

        return this.restHelper.put(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/pubblicaAttoGeneraleFuturo`, null, params);
    } 

    public generaUrlAnac(): Observable<ResponseResult<string>> {
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_ATTI_GENERALI_BASE_URL}/generaUrlAnac`);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService); };

    // #endregion
}