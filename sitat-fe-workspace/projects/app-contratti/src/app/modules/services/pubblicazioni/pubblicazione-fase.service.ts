import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, GaraEntry, ResponseResult } from '@maggioli/app-commons';
import { IHttpHeaders, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { LottoEntry } from '../../models/gare/gare.model';
import {
    FlussiFase,
    ModalitaInvio,
    PubblicazioneFaseEntry,
    ResponseListaPubblicazioneFase,
    ResponsePubblicaFase,
    ResponseRequestFase,
} from '../../models/pubblicazioni/pubblicazione-fase.model';
import { PubblicazioneResult } from '../../models/pubblicazioni/pubblicazioni.model';
import { GareService } from '../gare/gare.service';

@Injectable({ providedIn: 'root' })
export class PubblicazioneFaseService extends SdkBaseService {    

    constructor(inj: Injector) { super(inj) }

    public getListaPubblicazioniFase(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string): Observable<Array<FlussiFase>> {
        const params: IHttpParams = {
            codGara,
            codLotto,
            numFase: codFase,
            num: numeroProgressivo
        };

        return this.restHelper.get<ResponseListaPubblicazioneFase>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaPubblicazioniFase`, params)
            .pipe(
                map((result: ResponseListaPubblicazioneFase) => {
                    return result.data;
                })
            );
    }

    public verificaPubblicazioneFase(fasePubblicazione: PubblicazioneFaseEntry, token: string): Observable<ResponsePubblicaFase> {
        return this.execute(
            `${environment.PUBBLICAZIONE_CONTRATTI_BASE_URL}/pubblicaScheda`,
            ModalitaInvio.VERIFICA,
            fasePubblicazione,
            token
        );
    }

    public pubblicaFase(fasePubblicazione: PubblicazioneFaseEntry, token: string): Observable<PubblicazioneResult> {
        return this.execute(
            `${environment.PUBBLICAZIONE_CONTRATTI_BASE_URL}/pubblicaScheda`,
            ModalitaInvio.PUBBLICA,
            fasePubblicazione,
            token
        );
    }

    public deleteLogicaFase(token: string, cig: string, codFase: number, idAvGara: string, num: number, stazioneAppaltante: string): Observable<ResponseResult<any>> {

        let headers: IHttpHeaders = {
            applicationToken: token
        };

        let params: IHttpParams = {
            cig,
            codFase: toString(codFase),
            idAvGara,
            num: toString(num),
            stazioneAppaltante
        };
        return this.restHelper.post<ResponseResult<any>>(`${environment.PUBBLICAZIONE_CONTRATTI_BASE_URL}/deleteFase`, null, params, headers);
    }

    public allineaPubblicazioneFase(pubbFase: PubblicazioneFaseEntry): Observable<ResponseResult<any>> {
        let params: IHttpParams = {
            codGara: pubbFase.codGara,
            codLotto: pubbFase.codLotto,
            codiceFiscaleSA: pubbFase.cfStazioneAppaltante,
            num: pubbFase.numeroProgressivo,
            numFase: pubbFase.codFase,
            tipoInvio: `${pubbFase.tipoInvio}`,
            syscon: pubbFase.syscon
        };

        if (pubbFase.motivazione != null) {
            params = {
                ...params,
                motivazione: pubbFase.motivazione
            };
        }

        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/allineaPubblicazioneFase`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService {
        return this.injectable(SdkRestHelperService);
    }

    private get gareService(): GareService {
        return this.injectable(GareService);
    }

    // #endregion

    // #region Private

    public pubblicazioneFasePcp(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string, codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string): Observable<any> {
        const params: IHttpParams = {
            codGara,
            codFase,
            codLotto,
            num: numeroProgressivo,
            codProfilo,
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            codein
        };

        return this.restHelper.get<ResponseRequestFase>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/pubblicaSchedaPcp`, params);
    }

    public verificaFasePcp(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string): Observable<any> {
        const params: IHttpParams = {
            codGara,
            codFase,
            codLotto,
            num: numeroProgressivo
        };

        return this.restHelper.get<ResponseRequestFase>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/verificaSchedaPcp`, params);
    }

    private getRequestPubblicazioneFase(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string): Observable<string> {
        const params: IHttpParams = {
            codGara,
            codFase,
            codLotto,
            num: numeroProgressivo
        };

        return this.restHelper.get<ResponseRequestFase>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getRequestPubblicazioneFase`, params)
            .pipe(
                map((result: ResponseRequestFase) => {
                    return result.data;
                })
            );
    }

    private execute(url: string, modalitaInvio: ModalitaInvio, pubbFase: PubblicazioneFaseEntry, token: string): Observable<ResponsePubblicaFase> {

        let headers: IHttpHeaders = {
            applicationToken: token
        };

        const pubblicaSchedaInsertForm: any = { ...pubbFase };
        pubblicaSchedaInsertForm.modalitaInvio = modalitaInvio;

        return this.getRequestPubblicazioneFase(pubbFase.codGara, pubbFase.codFase, pubbFase.codLotto, pubbFase.numeroProgressivo)
            .pipe(
                mergeMap((oggettoScheda: string) => {
                    pubblicaSchedaInsertForm.oggettoScheda = JSON.stringify(oggettoScheda);
                    return this.gareService.dettaglioLotto(pubbFase.codGara, pubbFase.codLotto)
                }),
                mergeMap((lotto: LottoEntry) => {
                    pubblicaSchedaInsertForm.cig = lotto.cig;
                    pubblicaSchedaInsertForm.lotto = lotto;
                    return this.gareService.dettaglioGara(pubbFase.codGara);
                }),
                mergeMap((gara: GaraEntry) => {
                    pubblicaSchedaInsertForm.gara = gara;
                    return this.restHelper.post<ResponsePubblicaFase>(url, pubblicaSchedaInsertForm, null, headers);
                })

            );
    }

    public serviceAccessPubblica(chiaviAccessoOrt: ChiaviAccessoOrt): Observable<any> {

        let params: IHttpParams = {
            ...chiaviAccessoOrt
        };
        return this.restHelper.get<any>(`${environment.PUBBLICAZIONE_CONTRATTI_BASE_URL}/serviceAccessPubblica`, params);
    }

    public deleteFlussoLotto(codGara: string, codLotto: string) : Observable<any> {
        const params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFlussoLotto`, null, params);
    }

    public verificaStatoScheda(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string, codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string): Observable<any> {
        const params: IHttpParams = {
            codGara,
            codFase,
            codLotto,
            num: numeroProgressivo,
            codProfilo,
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            codein
        };

        return this.restHelper.get<ResponseListaPubblicazioneFase>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/verificaStatoScheda`, params);
    }

    public cancellaSchedaPcp(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string, codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, motivazione: string, codein: string): Observable<any> {
        const params: IHttpParams = {
            codGara,
            codFase,
            codLotto,
            num: numeroProgressivo,
            codProfilo,
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            motivazione,
            codein
        };

        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/cancellaSchedaPcp`, null, params,);
    }


    // #endregion

}