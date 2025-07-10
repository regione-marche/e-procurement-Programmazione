import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FasiInfo, TabellatoFaseEntry } from '../../models/fasi/fasi.model';
import {
    AccorpaMultilottoEntry,
    AccorpaMultilottoForm,
    AttoEntry,
    AttoInsertForm,
    AttoLottoEntry,
    AttoUpdateForm,
    DatiAccorpamentoEntry,
    DettaglioAttoEntry,
    FullFlussiFase,
    GaraBaseEntry,
    GaraEntry,
    GaraInsertForm,
    GaraUpdateForm,
    ListaGareParams,
    LottoAccorpabileEntry,
    LottoBaseEntry,
    LottoEntry,
    LottoInsertForm,
    MigrazioneGaraForm,
    PubblicitaGaraEntry,
    PubblicitaGaraInsertForm,
    PulisciAccorpamentiMultilottoForm,
    ResponseAttiAssociatiLotto,
    ResponseDatiContabilita,
    ResponseExportCsv,
    ResponseInizInsertLotto,
    ResponseListaGare,
    ResponseListaInviiFasi,
    RupGaraUpdateForm,
    SysconGaraUpdateForm,
} from '../../models/gare/gare.model';


@Injectable({ providedIn: 'root' })
export class GareService extends SdkBaseService {
    
    constructor(inj: Injector) { super(inj) }

    public getListaGare(request: ListaGareParams, state: SdkTableState): Observable<ResponseListaGare> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            archiviate: toString(request.archiviate),
            cigLotto: request.cigLotto,
            codiceTecnico: request.codiceTecnico,
            rup: request.rup,
            cupLotto: request.cupLotto,
            modalitaRealizzazione: toString(request.modalitaRealizzazione),
            numGara: request.numGara,
            oggetto: request.oggetto,
            oggettoLotto: request.oggettoLotto,
            proceduraContraenteLotto: toString(request.proceduraContraenteLotto),
            searchString: request.descrizione,
            situazioneGara: toString(request.situazioneGara),
            tipoAppaltoLotto: toString(request.tipoAppaltoLotto),
            syscon: toString(request.syscon),
            dataDa: toString(request.dataDa),
            dataA: toString(request.dataA),
            importoGaraDa:toString(request.importoGaraDa),
            importoGaraA:toString(request.importoGaraA),
            importoLottoDa:toString(request.importoLottoDa),
            importoLottoA:toString(request.importoLottoA),
            cfTecnico:toString(request.cfTecnico)
        };

        return this.restHelper.get<ResponseListaGare>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaGare`, params);
    }

    public dettaglioGara(codGara: string): Observable<GaraEntry> {

        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<GaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDettaglioGara`, params)
            .pipe(
                map((response: ResponseResult<GaraEntry>) => {
                    return response.data;
                })
            );
    }

    public matriceAtti(codGara: string): Observable<any> {

        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/matriceAtti`, params)
            .pipe(
                map((response: ResponseResult<any>) => {
                    return response.data;
                })
            );
    }

    public archiviGara(codGara: string): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/archiviaGara`, codGara);
    }

    public annullaArchiviGara(codGara: string): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/annullaArchiviaGara`, codGara);
    }

    public updateGara(request: GaraUpdateForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateGara`, request);
    }

    public deleteGara(codGara: string): Observable<any> {

        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteGara`, null, params);
    }

    public deleteAppaltoPcp(codGara: string): Observable<any> {

        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteAppaltoPcp`, null, params);
    }

    public getListaLotti(codGara: string, state: SdkTableState): Observable<ResponseListaGare> {

        let params: IHttpParams = {
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            codGara
        };

        return this.restHelper.get<ResponseListaGare>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaLotti`, params);
    }

    public getListaAttiLotto(codGara: string, codLotto: string): Observable<ResponseAttiAssociatiLotto> {
        const params: IHttpParams = {
            codGara,
            codLotto
        }

        return this.restHelper.get<ResponseAttiAssociatiLotto>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getAttiAssociatiLotto`, params);
    }

    public getListaAtti(codGara: string): Observable<Array<AttoEntry>> {

        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<Array<AttoEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaAtti`, params)
            .pipe(
                map((response: ResponseResult<Array<AttoEntry>>) => {
                    return response.data;
                })
            );
    }

    public insertAtto(request: AttoInsertForm): Observable<string> {

        return this.restHelper.post<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertAtto`, request)
            .pipe(
                map((response: ResponseResult<string>) => {
                    return response.data;
                })
            );
    }

    public updateAtto(request: AttoUpdateForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateAtto`, request);
    }

    public dettaglioAtto(codGara: string, tipoDocumento: string, numPubb: string): Observable<DettaglioAttoEntry> {

        let params: IHttpParams = {
            codGara,
            tipoDocumento,
            numPubb
        };

        return this.restHelper.get<ResponseResult<DettaglioAttoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDettaglioAtto`, params)
            .pipe(
                map((response: ResponseResult<DettaglioAttoEntry>) => {
                    return response.data;
                })
            );
    }

    public deleteAtto(codGara: string, numPubb: string): Observable<any> {

        let params: IHttpParams = {
            codGara,
            numPubb
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteAtto`, null, params);
    }

    public pubblicaAtto(codGara: number, numPubb: number): Observable<any> {
        let params: IHttpParams = {
            codGara: toString(codGara), 
            numPubb: toString(numPubb)
        }

        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/pubblicaAtto`, null, params);
    }

    public programmaPubbAtto(codGara: number, numPubb: number, dataProgrammazione: Date): Observable<any> {
        let params: IHttpParams = {
            codGara: toString(codGara), 
            numPubb: toString(numPubb),
            dataProgrammazione: toString(dataProgrammazione)
        }

        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/programmaPubbAtto`, null, params);
    }

    public deleteAttoSingolo(codGara: number, numPubb: number): Observable<any> {
        let params: IHttpParams = {
            codGara: toString(codGara), 
            numPubb: toString(numPubb)
        }

        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteAttoSingolo`, null, params);
    }

    public annullaPubblicazione(codGara: number, numPubb: number): Observable<any> {
        let params: IHttpParams = {
            codGara: toString(codGara), 
            numPubb: toString(numPubb)
        }

        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/annullaPubblicazione`, null, params);
    }

    public annullaPubblicazioneMotivazione(codGara: number, numPubb: number, motivazione: string): Observable<any> {
        let params: IHttpParams = {
            codGara: toString(codGara), 
            numPubb: toString(numPubb),
            motivoCanc: motivazione
        }

        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/annullaPubblicazioneMotivazione`, null, params);
    }

    public dettaglioLotto(codGara: string, codLotto: string): Observable<LottoEntry> {

        let params: IHttpParams = {
            codGara,
            numLotto: codLotto
        };

        return this.restHelper.get<ResponseResult<LottoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDettaglioLotto`, params)
            .pipe(
                map((response: ResponseResult<LottoEntry>) => {
                    return response.data;
                })
            );
    }

    public getLottiAtto(codGara: string, numPubb: string): Observable<Array<AttoLottoEntry>> {

        let params: IHttpParams = {
            codGara,
            numPubb
        };

        return this.restHelper.get<ResponseResult<Array<AttoLottoEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getLottiAtto`, params)
            .pipe(
                map((response: ResponseResult<Array<AttoLottoEntry>>) => {
                    return response.data;
                })
            );
    }

    public associaLottiAtto(codGara: string, numPubb: string, idLotti: Array<string>): Observable<any> {

        let params: IHttpParams = {
            codGara,
            numPubb,
            idLotti
        };

        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/associaLottiAtto`, null, params);
    }

    public pubblicitaGara(codGara: string): Observable<ResponseResult<PubblicitaGaraEntry>> {

        let params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<PubblicitaGaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getPubblicitaGara`, params);
    }

    public updatePubblicitaGara(form: PubblicitaGaraInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertPubblicitaGara`, form);
    }

    public insertLotto(form: LottoInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertLotto`, form);
    }

    public updateLotto(form: LottoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateLotto`, form);
    }

    public deleteLotto(codGara: string, codLotto: string): Observable<any> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteLotto`, null, params);
    }

    public getFasiLotto(codGara: string, codLotto: string, numAppa?: number): Observable<FasiInfo> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        if (numAppa != null) {
            params = {
                ...params,
                numAppa: toString(numAppa)
            }
        }

        return this.restHelper.get<ResponseResult<FasiInfo>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFasiLotto`, params)
            .pipe(
                map((response: ResponseResult<FasiInfo>) => {
                    return response.data;
                })
            );
    }

    public getListaFasiVisibili(codGara: string, numLotto: string): Observable<Array<TabellatoFaseEntry>> {

        let params: IHttpParams = {
            codGara,
            numLotto
        };

        return this.restHelper.get<ResponseResult<Array<TabellatoFaseEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/listaFasiVisibili`, params)
            .pipe(
                map((response: ResponseResult<Array<TabellatoFaseEntry>>) => {
                    return response.data;
                })
            );
    }

    public insertGara(request: GaraInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertGara`, request);
    }

    public getInizInsertLotto(): Observable<ResponseInizInsertLotto> {
        return this.restHelper.get<ResponseResult<ResponseInizInsertLotto>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizInsertLotto`);
    }

    public getListaInviiFasi(codGara: string, codLotto: string, state: SdkTableState): Observable<ResponseListaInviiFasi> {
        let params: IHttpParams = {
            codGara,
            codLotto,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaInviiFasi>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaInviiFasi`, params);
    }

    public updateRupGara(form: RupGaraUpdateForm): Observable<boolean> {
        return this.restHelper.put<ResponseResult<boolean>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateRupGara`, form)
            .pipe(
                map((response: ResponseResult<boolean>) => {
                    return response.data;
                })
            );
    }

    public updateSysconGara(form: SysconGaraUpdateForm): Observable<ResponseResult<any>> {
       
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/trasferisciReferenteGara`, form);           
    }

    public executeMigraGara(form: MigrazioneGaraForm): Observable<ResponseResult<any>> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/migraGara`, form);
    }

    public isAccordoConvenzione(gara: GaraEntry): boolean {
        return gara.tipoApp === 9 || gara.tipoApp === 17 || gara.tipoApp === 18;
    }

    public isAnagraficaGaraPubblicata(codGara: string, checkSmartCig: string): Observable<boolean> {

        let params: IHttpParams = {
            codGara,
            checkSmartCig
        };

        return this.restHelper.get<ResponseResult<boolean>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/isAnagraficaGaraPubblicata`, params)
            .pipe(
                map((response: ResponseResult<boolean>) => {
                    return response.data;
                })
            );
    }

    public checkLottiAccorpatiGara(codGara: string): Observable<LottoAccorpabileEntry> {

        const params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<LottoAccorpabileEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/checkLottiAccorpatiGara`, params)
            .pipe(
                map((response: ResponseResult<LottoAccorpabileEntry>) => {
                    return response.data;
                })
            );
    }

    public getMultiLottoOptions(codGara: string, searchString: string): Observable<Array<LottoBaseEntry>> {
        const params: IHttpParams = {
            codGara,
            occurrence: searchString
        };

        return this.restHelper.get<ResponseResult<Array<LottoBaseEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getMultiLottoOptions`, params)
            .pipe(
                map((response: ResponseResult<Array<LottoBaseEntry>>) => {
                    return response.data;
                })
            );
    }

    public getLottiAccorpabili(codGara: string, codLottoMaster: string): Observable<Array<LottoBaseEntry>> {
        const params: IHttpParams = {
            codGara,
            codLotto: codLottoMaster
        };

        return this.restHelper.get<ResponseResult<Array<LottoBaseEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getLottiAccorpabili`, params)
            .pipe(
                map((response: ResponseResult<Array<LottoBaseEntry>>) => {
                    return response.data;
                })
            );
    }

    public pulisciAccorpamentiMultilotto(codGara: string): Observable<ResponseResult<any>> {
        const body: PulisciAccorpamentiMultilottoForm = {
            codGara: +codGara
        };

        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/pulisciAccorpamentiMultilotto`, body);
    }

    public getRiepilogoAccorpamenti(codGara: string): Observable<DatiAccorpamentoEntry> {
        const params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<DatiAccorpamentoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getRiepilogoAccorpamenti`, params)
            .pipe(
                map((response: ResponseResult<DatiAccorpamentoEntry>) => {
                    return response.data;
                })
            );
    }

    public accorpaMultilotto(body: AccorpaMultilottoForm): Observable<AccorpaMultilottoEntry> {

        return this.restHelper.post<ResponseResult<AccorpaMultilottoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/accorpaMultilotto`, body)
            .pipe(
                map((response: ResponseResult<AccorpaMultilottoEntry>) => {
                    return response.data;
                })
            );
    }

    public downloadDocumentoAtto(codGara: number, idAllegato: number): Observable<string> {
        const params: IHttpParams = {
            codGara: toString(codGara),
            idAllegato: toString(idAllegato)
        };
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/downloadDocumentoAtto`, params)
            .pipe(
                map((response: ResponseResult<string>) => {
                    return response.data;
                })
            );
    }

    public downloadDocumentoAttoGenerale(codGara: number, numPubb: number, idAllegato: number): Observable<string> {
        const params: IHttpParams = {
            codGara: toString(codGara),
            numbPubb: toString(numPubb),
            idAllegato: toString(idAllegato)
        };
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/downloadDocumentoAttoGenerale`, params)
            .pipe(
                map((response: ResponseResult<string>) => {
                    return response.data;
                })
            );
    }

    public getListaGareNonPaginata(stazioneAppaltante: string, syscon: number, searchString: string): Observable<Array<GaraBaseEntry>> {
        const params: IHttpParams = {
            stazioneAppaltante,
            syscon: toString(syscon),
            searchString
        };
        return this.restHelper.get<ResponseResult<Array<GaraBaseEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaGareNonPaginata`, params)
            .pipe(
                map((response: ResponseResult<Array<GaraBaseEntry>>) => {
                    return response.data;
                })
            );
    }

    public exportListaGare(request: ListaGareParams): Observable<ResponseExportCsv> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            archiviate: toString(request.archiviate),
            cigLotto: request.cigLotto,
            codiceTecnico: request.codiceTecnico,
            rup: request.rup,
            cupLotto: request.cupLotto,
            modalitaRealizzazione: toString(request.modalitaRealizzazione),
            numGara: request.numGara,
            oggetto: request.oggetto,
            oggettoLotto: request.oggettoLotto,
            proceduraContraenteLotto: toString(request.proceduraContraenteLotto),
            searchString: request.descrizione,
            situazioneGara: toString(request.situazioneGara),
            tipoAppaltoLotto: toString(request.tipoAppaltoLotto),
            syscon: toString(request.syscon),
            dataDa: toString(request.dataDa),
            dataA: toString(request.dataA),
            importoGaraDa:toString(request.importoGaraDa),
            importoGaraA:toString(request.importoGaraA),
            importoLottoDa:toString(request.importoLottoDa),
            importoLottoA:toString(request.importoLottoA),
            cfTecnico:toString(request.cfTecnico)
        };

        return this.restHelper.get<ResponseExportCsv>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/exportListaGareCsv`, params);
    }

    public getLinkPubbblicazione(idRicevuto: string,stazioneAppaltante: string,entita: string): Observable<ResponseResult<string>> {
        const params: IHttpParams = {
            idRicevuto,
            stazioneAppaltante,
            entita
        };
        
        return this.restHelper.get<ResponseResult<string>>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/getPubblicazioneLink`,params)           
    }

    public getReportIndicatori(codGara: string, codLotto: string, stazioneAppaltante: string, tipoReport: string, cig: string) {
        const params: IHttpParams = {
            codGara,
            stazioneAppaltante,
            codLotto,
            tipoReport,
            cig
        };
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getReportIndicatori`,params)    
    }

    public isAttivoIndicatoriLotto() {        
        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/isAttivoIndicatoriLotto`)    
    }

    public existSimogEndpoint(): Observable<any> {
        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/existSimogEndpoint`) 
    } 

    public getDelegati(codGara: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string): Observable<any> {
        const params: IHttpParams = {
            codGara,
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            codein
        };
        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDelegatoPcp`,params) 
    }

    public confermaDelegato(codGara: string, codtec: string,cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string, inserisciDelegato: boolean): Observable<any> {
        const params: IHttpParams = {
            codGara,
            codtec,
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            codein,
            inserisciDelegato: toString(inserisciDelegato)
        };
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/saveDelegatoPcp`,undefined, params) 
    }

    public presaCarico(codGara: string,cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string): Observable<any> {
        const params: IHttpParams = {
            codGara,
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            codein,
        };
        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/presaCarico`, params) 
    }

    
    public getDatiContabilita(codGara: string, cig: string): Observable<ResponseDatiContabilita> {
        let params: IHttpParams = {
            codGara,
            cig
        };

        return this.restHelper.get<ResponseDatiContabilita>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDatiContabilita`, params);
    }
    public getIdSchedaLocale(item: FullFlussiFase): Observable<string> {
        const params: IHttpParams = {
            codGara: toString(item.codGara),
            codLotto: toString(item.codLotto),
            num: toString(item.num),
            numFase: toString(item.numFase)
        }

        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getIdSchedaLocale`, params)
        .pipe(
            map((response: ResponseResult<string>) => {
                return response.data;
            })
        );
    }


    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}