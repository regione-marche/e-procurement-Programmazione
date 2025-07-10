import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, ResponseResult } from '@maggioli/app-commons';
import { IHttpHeaders, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FlussiGara, PubblicaGaraEntry } from '../../models/pubblicazioni/pubblicazione-gara.model';
import { PubblicazioneResult } from '../../models/pubblicazioni/pubblicazioni.model';

@Injectable({ providedIn: 'root' })
export class PubblicazioneGaraService extends SdkBaseService {    

    constructor(inj: Injector) { super(inj) }

    public getListaPubblicazioniGara(codGara: string): Observable<Array<FlussiGara>> {
        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<Array<FlussiGara>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaPubblicazioniGara`, params)
            .pipe(
                map((result: ResponseResult<Array<FlussiGara>>) => {
                    return result.data;
                })
            );
    }

    public getTracciatoFlusso(idFlusso: number): Observable<string> {
        let params: IHttpParams = {
            idFlusso: idFlusso +""
        };

        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getTracciatoFlusso`, params)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public getRequestPubblicazioneGara(codGara: string): Observable<PubblicaGaraEntry> {
        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<PubblicaGaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getRequestPubblicazioneGara`, params)
            .pipe(
                map((result: ResponseResult<PubblicaGaraEntry>) => {
                    return result.data;
                })
            );
    }

    public getRequestPubblicazioneSmartCig(codGara: string): Observable<PubblicaGaraEntry> {
        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<PubblicaGaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getRequestPubblicazioneSmartCig`, params)
            .pipe(
                map((result: ResponseResult<PubblicaGaraEntry>) => {
                    return result.data;
                })
            );
    }
    


    public checkPubblicazioneGara(gara: PubblicaGaraEntry, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '1'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post<PubblicazioneResult>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/PubblicaGaraLotti`, gara, params, headers);
    }

    public checkPubblicazioneSmartCig(gara: any, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '1'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post<PubblicazioneResult>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/PubblicaSmartCig`, gara, params, headers);
    }
    
    public pubblicaGara(gara: PubblicaGaraEntry, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '2'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post<PubblicazioneResult>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/PubblicaGaraLotti`, gara, params, headers);
    }

    public pubblicaSmartCig(gara: any, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '2'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post<PubblicazioneResult>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/PubblicaSmartCig`, gara, params, headers);
    }


    public allineaPubblicazioneGara(codGara: string, codiceFiscaleSA: string, idRicevuto: string, tipoInvio: string, syscon : string, payload: string): Observable<ResponseResult<any>> {
        let params: IHttpParams = {
            codGara,
            codiceFiscaleSA,
            idRicevuto,
            tipoInvio,
            syscon
        };
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/allineaPubblicazioneGara`, payload, params);
    }

    public serviceAccessPubblica(chiaviAccessoOrt: ChiaviAccessoOrt): Observable<any> {

        let params: IHttpParams = {
            ...chiaviAccessoOrt
        };
        return this.restHelper.get<any>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/serviceAccessPubblica`, params);
    }

    public deleteFlussoGara(codGara: string): Observable<any> {
        const params: IHttpParams = {
            codGara
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFlussoGara`, null, params);
    }

    public deleteFlussoAtto(codGara: string, numPubb: string) : Observable<any> {
        const params: IHttpParams = {
            codGara,
            numPubb
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFlussoAtto`, null, params);
    }

  


    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}