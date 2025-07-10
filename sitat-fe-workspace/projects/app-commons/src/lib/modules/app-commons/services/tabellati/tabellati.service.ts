import { Injectable, Injector } from '@angular/core';
import { IDictionary, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { CentroDiCostoEntry } from '../../models/archivio/archivio-centri-di-costo.models';
import { ResponseResult } from '../../models/common/common.model';
import { TabellatoCpv } from '../../models/cpv/cpv.model';
import {
    ListaStazioniAppaltantiRequest,
    ResponseListaStazioneAppaltante,
    StazioneAppaltanteBaseEntry,
    StazioneAppaltanteEntry,
    StazioneAppaltanteInsertForm,
    StazioneAppaltanteUpdateForm,
} from '../../models/stazione-appaltante/stazione-appaltante.model';
import {
    InfoCampo,
    ListaTecniciRequest,
    MessageEntry,
    MessageForm,
    MessageReceiverEntry,
    ResponseListaRupPaginata,
    RupEntry,
    SAEntry,
    ValoreTabellato,
} from '../../models/tabellati/tabellato.model';
import { RupInsertForm, Tecnico } from '../../models/tecnico/tecnico.model';
import {
    ListaUfficiRequest,
    ResponseListaUfficiPaginata,
    Ufficio,
    UfficioInsertForm,
} from '../../models/uffici/uffici.model';
import { UsrSysconEntry } from '../../models/user/user.model';
import { ResponseDTO } from 'projects/sdk-gestione-reports/src/lib/sdk-gestione-reports/model/lib.model';

@Injectable()
export class TabellatiService extends SdkBaseService {

    constructor(inj: Injector) { super(inj); }

    public listaValori(baseUrl: string, cod: string): Observable<Array<ValoreTabellato>> {

        let params: IHttpParams = {
            cod
        };

        return this.restHelper.get<ResponseResult<Array<ValoreTabellato>>>(`${baseUrl}/Valori`, params)
            .pipe(
                map((result: ResponseResult<Array<ValoreTabellato>>) => {
                    return result.data;
                })
            );
    }

    public getMultipleListaValori(baseUrl: string, codici: Array<string>): Observable<IDictionary<Array<ValoreTabellato>>> {

        let params: IHttpParams = {
            cods: codici
        };

        return this.restHelper.get<ResponseResult<IDictionary<Array<ValoreTabellato>>>>(`${baseUrl}/listaTabellati`, params)
            .pipe(
                map((result: ResponseResult<IDictionary<Array<ValoreTabellato>>>) => {
                    return result.data;
                })
            );
    }

    public defaultTecnico(baseUrl: string, stazioneAppaltante: string, syscon: string): Observable<Array<Tecnico>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            syscon
        };

        return this.restHelper.get<ResponseResult<Array<Tecnico>>>(`${baseUrl}/GetSuggestedRUP`, params)
            .pipe(
                map((result: ResponseResult<Array<Tecnico>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniTecnici(baseUrl: string, stazioneAppaltante: string, rup: string): Observable<Array<Tecnico>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            rup
        };

        return this.restHelper.get<ResponseResult<Array<Tecnico>>>(`${baseUrl}/GetRupOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<Tecnico>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniCig(baseUrl: string, stazioneAppaltante: string, cig: string): Observable<Array<string>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            cig
        };

        return this.restHelper.get<ResponseResult<Array<string>>>(`${baseUrl}/GetCigOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<string>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniUtenti(baseUrl: string, stazioneAppaltante: string, rup: string): Observable<Array<Tecnico>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            rup
        };

        return this.restHelper.get<ResponseResult<Array<Tecnico>>>(`${baseUrl}/getUtentiOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<Tecnico>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniComuni(baseUrl: string, data: string): Observable<Array<ValoreTabellato>> {

        let params: IHttpParams = {
            search: data
        };

        return this.restHelper.get<ResponseResult<Array<ValoreTabellato>>>(`${baseUrl}/getComuniOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<ValoreTabellato>>) => {
                    return result.data;
                })
            );
    }


    public creaTecnico(baseUrl: string, tecnico: Tecnico | RupInsertForm): Observable<string> {

        return this.restHelper.post<ResponseResult<string>>(`${baseUrl}/InsertRUP`, tecnico)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }


    public listaOpzioniUffici(baseUrl: string, stazioneAppaltante: string, desc: string): Observable<Array<Ufficio>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            desc
        };

        return this.restHelper.get<ResponseResult<Array<Ufficio>>>(`${baseUrl}/getUffOptionsProgrammazione`, params)
            .pipe(
                map((result: ResponseResult<Array<Ufficio>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniUfficiAvvisi(baseUrl: string, stazioneAppaltante: string, desc: string): Observable<Array<Ufficio>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            desc
        };

        return this.restHelper.get<ResponseResult<Array<Ufficio>>>(`${baseUrl}/getUffOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<Ufficio>>) => {
                    return result.data;
                })
            );
    }

    public creaUfficio(baseUrl: string, ufficio: UfficioInsertForm): Observable<string> {

        return this.restHelper.post<ResponseResult<string>>(`${baseUrl}/insertUff`, ufficio)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public getListaUffici(baseUrl: string, request: ListaUfficiRequest, state: SdkTableState): Observable<ResponseListaUfficiPaginata> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            searchString: request.searchString,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaUfficiPaginata>(`${baseUrl}/getListaUffici`, params);
    }

    public getUfficio(baseUrl: string, id: string): Observable<Ufficio> {

        let params: IHttpParams = {
            id
        };

        return this.restHelper.get<ResponseResult<Ufficio>>(`${baseUrl}/getUfficio`, params)
            .pipe(
                map((result: ResponseResult<Ufficio>) => {
                    return result.data;
                })
            );
    }

    public nutsData(baseUrl: string): Observable<any> {

        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getTabNuts`)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result.data;
                })
            );
    }

    public cpv0(baseUrl: string): Observable<any> {

        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getCpv0`)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result.data;
                })
            );
    }

    public cpv1(baseUrl: string, cpv0: string): Observable<any> {

        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getCpv1?cpv0=${cpv0}`)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result.data;
                })
            );
    }


    public cpv2(baseUrl: string, cpv0: string, cpv1: string): Observable<any> {

        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getCpv2?cpv0=${cpv0}&cpv1=${cpv1}`)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result.data;
                })
            );
    }

    public cpv3(baseUrl: string, cpv0: string, cpv1: string, cpv2: string): Observable<any> {

        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getCpv3?cpv0=${cpv0}&cpv1=${cpv1}&cpv2=${cpv2}`)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result.data;
                })
            );
    }

    public allCpv(baseUrl: string): Observable<Array<TabellatoCpv>> {

        return this.restHelper.get<ResponseResult<Array<TabellatoCpv>>>(`${baseUrl}/getAllCpv`)
            .pipe(
                map((result: ResponseResult<Array<TabellatoCpv>>) => {
                    return result.data;
                })
            );
    }

    public getListaTecnici(baseUrl: string, request: ListaTecniciRequest, state: SdkTableState): Observable<ResponseListaRupPaginata> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            searchString: request.searchString,
            codiceFiscale: request.codiceFiscale,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaRupPaginata>(`${baseUrl}/getListaTecnici`, params);
    }

    public getTecnico(baseUrl: string, id: string): Observable<RupEntry> {

        let params: IHttpParams = {
            id
        };

        return this.restHelper.get<ResponseResult<RupEntry>>(`${baseUrl}/getTecnico`, params)
            .pipe(
                map((result: ResponseResult<RupEntry>) => {
                    return result.data;
                })
            );
    }

    public deleteTecnico(baseUrl: string, id: string): Observable<any> {

        let params: IHttpParams = {
            id
        };

        return this.restHelper.delete<ResponseResult<any>>(`${baseUrl}/deleteTecnico`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public getInfoCampo(baseUrl: string, mnemonico: string): Observable<InfoCampo> {
        let params: IHttpParams = {
            mnemonico
        };

        return this.restHelper.get<ResponseResult<InfoCampo>>(`${baseUrl}/getInfoCampo`, params)
            .pipe(
                map((result: ResponseResult<InfoCampo>) => {
                    return result.data;
                })
            );
    }

    public getMessageReceiver(baseUrl: string, searchString: string): Observable<Array<MessageReceiverEntry>> {
        let params: IHttpParams = {
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<MessageReceiverEntry>>>(`${baseUrl}/getMessageReceiver`, params)
            .pipe(
                map((result: ResponseResult<Array<MessageReceiverEntry>>) => {
                    return result.data;
                })
            );
    }

    public getMessages(baseUrl: string, syscon: string, origin: string): Observable<Array<MessageEntry>> {
        let params: IHttpParams = {
            syscon,
            origin
        };

        return this.restHelper.get<ResponseResult<Array<MessageEntry>>>(`${baseUrl}/getMessages`, params)
            .pipe(
                map((result: ResponseResult<Array<MessageEntry>>) => {
                    return result.data;
                })
            );
    }

    public sendMessage(baseUrl: string, form: MessageForm): Observable<any> {
        return this.restHelper.post<ResponseResult<string>>(`${baseUrl}/sendMessage`, form);
    }

    public deleteMessage(baseUrl: string, messageId: number, origin: string): Observable<any> {

        let params: IHttpParams = {
            messageId: toString(messageId),
            origin
        };

        return this.restHelper.delete<ResponseResult<string>>(`${baseUrl}/deleteMessage`, undefined, params);
    }

    public setMessageSeen(baseUrl: string, messageId: number, letto: number): Observable<any> {

        let params: IHttpParams = {
            messageId: toString(messageId),
            letto: toString(letto)
        };

        return this.restHelper.put<ResponseResult<string>>(`${baseUrl}/setMessageSeen`, undefined, params);
    }

    public listaOpzioniCentriDiCosto(baseUrl: string, stazioneAppaltante: string, cdc: string): Observable<Array<CentroDiCostoEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            desc: cdc
        };

        return this.restHelper.get<ResponseResult<Array<CentroDiCostoEntry>>>(`${baseUrl}/getCdcOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<CentroDiCostoEntry>>) => {
                    return result.data;
                })
            );
    }

    public getStazioniAppaltantiOptions(baseUrl: string, desc: string): Observable<Array<SAEntry>> {

        let params: IHttpParams = {
            desc
        };

        return this.restHelper.get<ResponseResult<Array<SAEntry>>>(`${baseUrl}/getStazioniAppaltantiOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<SAEntry>>) => {
                    return result.data;
                })
            );
    }

    public getApplicationTitle(baseUrl: string): Observable<string> {
        return this.restHelper.get<ResponseResult<string>>(`${baseUrl}/title`)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public getUserOptions(baseUrl: string, stazioneAppaltante: string, user: string): Observable<Array<UsrSysconEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            search: user
        };

        return this.restHelper.get<ResponseResult<Array<UsrSysconEntry>>>(`${baseUrl}/getUserOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<UsrSysconEntry>>) => {
                    return result.data;
                })
            );
    }

    public getListaStazioniAppaltanti(baseUrl: string, request: ListaStazioniAppaltantiRequest, state: SdkTableState): Observable<ResponseListaStazioneAppaltante> {

        let params: IHttpParams = {
            ...request,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaStazioneAppaltante>(`${baseUrl}/getListaSa`, params);
    }

    public getStazioneAppaltante(baseUrl: string, codiceSA: string): Observable<StazioneAppaltanteEntry> {

        const params: IHttpParams = {
            codiceSA
        };

        return this.restHelper.get<ResponseResult<StazioneAppaltanteEntry>>(`${baseUrl}/getSa`, params)
            .pipe(
                map((result: ResponseResult<StazioneAppaltanteEntry>) => {
                    return result.data;
                })
            );
    }

    public deleteStazioneAppaltante(baseUrl: string, codiceSA: string): Observable<any> {

        let params: IHttpParams = {
            codiceSA
        };

        return this.restHelper.delete<ResponseResult<any>>(`${baseUrl}/deleteSa`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public insertStazioneAppaltante(baseUrl: string, form: StazioneAppaltanteInsertForm): Observable<ResponseResult<any>> {
        return this.restHelper.post(`${baseUrl}/insertSa`, form);
    }

    public updateStazioneAppaltante(baseUrl: string, form: StazioneAppaltanteUpdateForm): Observable<ResponseResult<any>> {
        return this.restHelper.put(`${baseUrl}/updateSa`, form);
    }

    public getStazioneAppaltanteByCodiceFiscale(baseUrl: string, codiceFiscale: string): Observable<StazioneAppaltanteBaseEntry> {

        const params: IHttpParams = {
            codiceFiscale
        };

        return this.restHelper.get<ResponseResult<StazioneAppaltanteBaseEntry>>(`${baseUrl}/getSaByCodiceFiscale`, params)
            .pipe(
                map((result: ResponseResult<StazioneAppaltanteBaseEntry>) => {
                    return result.data;
                })
            );
    }

    public getLogoutUrl(baseUrl: string): Observable<ResponseResult<string>> {
        return this.restHelper.get<ResponseResult<string>>(`${baseUrl}/getLogoutUrl`);
    }

    public getListaReportPredefinitiUrl(baseUrl: string, idProfilo: string, uffInt: string, syscon: number): Observable<ResponseDTO<number>> {

        let params: IHttpParams = {
            idProfilo,
            uffInt,
            syscon: toString(syscon)
        }

        return this.restHelper.get<ResponseDTO<number>>(`${baseUrl}/getCountListaReportPredefiniti`, params);
    }

    public getListaTecniciNonPaginata(baseUrl: string, stazioneAppaltante: string, searchString: string): Observable<Array<RupEntry>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<RupEntry>>>(`${baseUrl}/getListaTecniciNonPaginata`, params)
            .pipe(
                map((result: ResponseResult<Array<RupEntry>>) => {
                    return result.data;
                })
            );
    }

    public getListaUfficiNonPaginata(baseUrl: string, stazioneAppaltante: string, searchString: string): Observable<Array<Ufficio>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<Ufficio>>>(`${baseUrl}/getListaUfficiNonPaginata`, params)
            .pipe(
                map((result: ResponseResult<Array<Ufficio>>) => {
                    return result.data;
                })
            );
    }

    public deleteUfficio(baseUrl: string, id: string): Observable<any> {

        let params: IHttpParams = {
            id
        };

        return this.restHelper.delete<ResponseResult<any>>(`${baseUrl}/deleteUfficio`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public getCurrentMessagesStatus(baseUrl: string): Observable<number> {
        return this.restHelper.get<ResponseResult<number>>(`${baseUrl}/getCurrentMessagesStatus`)
            .pipe(
                map((result: ResponseResult<number>) => {
                    return result.data;
                })
            );
    }

    public deleteCurrentMessage(baseUrl: string, messageId: number): Observable<ResponseResult<boolean>> {
        return this.restHelper.delete<ResponseResult<boolean>>(`${baseUrl}/deleteCurrentMessage/${messageId}`);
    }

    // #region gettetr

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}