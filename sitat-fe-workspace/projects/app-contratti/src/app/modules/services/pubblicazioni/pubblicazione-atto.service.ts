import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, ResponseResult } from '@maggioli/app-commons';
import { IHttpHeaders, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FlussiAtto, PubblicaAttoEntry } from '../../models/pubblicazioni/pubblicazione-atto.model';
import { PubblicazioneAttoResult } from '../../models/pubblicazioni/pubblicazioni.model';

@Injectable({ providedIn: 'root' })
export class PubblicazioneAttoService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getListaPubblicazioniAtto(codGara: string, num: string): Observable<Array<FlussiAtto>> {
        let params: IHttpParams = {
            codGara,
            num
        };

        return this.restHelper.get<ResponseResult<Array<FlussiAtto>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaPubblicazioniAtto`, params)
            .pipe(
                map((result: ResponseResult<Array<FlussiAtto>>) => {
                    return result.data;
                })
            );
    }

    public getRequestPubblicazioneAtto(codGara: string, numPubb: string, tipoDocumento: string): Observable<PubblicaAttoEntry> {
        let params: IHttpParams = {
            codGara,
            numPubb,
            tipoDocumento
        };

        return this.restHelper.get<ResponseResult<PubblicaAttoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getRequestPubblicazioneAtto`, params)
            .pipe(
                map((result: ResponseResult<PubblicaAttoEntry>) => {
                    return result.data;
                })
            );
    }

    public checkPubblicazioneAtto(atto: PubblicaAttoEntry, token: string): Observable<PubblicazioneAttoResult> {
        let params: IHttpParams = {
            modalitaInvio: '1',
            pubblicaGara: 'false'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post<PubblicazioneAttoResult>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/PubblicaAtto`, atto, params, headers);
    }

    public pubblicaAtto(atto: PubblicaAttoEntry, token: string): Observable<PubblicazioneAttoResult> {
        let params: IHttpParams = {
            modalitaInvio: '2',
            pubblicaGara: 'false'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post<PubblicazioneAttoResult>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/PubblicaAtto`, atto, params, headers);
    }

    public allineaPubblicazioneAtto(codGara: string, numPubb: string, tipoDocumento: string, idGara: string, idRicevuto: string, tipoInvio: string, syscon: string, payload:string): Observable<ResponseResult<any>> {
        let params: IHttpParams = {
            codGara,
            idGara,
            idRicevuto,
            numPubb,
            tipoDocumento,
            tipoInvio,
            syscon
            
        };
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/allineaPubblicazioneAtto`, payload, params);
    }

    public serviceAccessPubblica(chiaviAccessoOrt: ChiaviAccessoOrt): Observable<any> {

        let params: IHttpParams = {
            ...chiaviAccessoOrt
        };
        return this.restHelper.get<any>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/serviceAccessPubblica`, params);
    }

    public pubblicaAtti(codgara: string, syscon: string,pubblicatoTutto: boolean): Observable<any> {
        let params: IHttpParams = {
            codgara,
            syscon,
            pubblicatoTutto: pubblicatoTutto.toString()
        };

       
        return this.restHelper.get<any>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/pubblicaAtti`,params) .pipe(
            map((result: ResponseResult<any>) => {
                return result.data;
            })
        );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}