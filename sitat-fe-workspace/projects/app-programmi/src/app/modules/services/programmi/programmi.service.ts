import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpHeaders, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { get, head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { ImportInterventiForm, InterventiDaRiproporre } from '../../models/interventi/interventi-import.model';
import {
    InterventoNonRipropostoEntry,
    InterventoNonRipropostoInsertForm,
    InterventoNonRipropostoUpdateForm,
    ResponseListaInterventiNR,
} from '../../models/interventi/interventi-non-riproposti.model';
import {
    CuiSearchForm,
    ExportInterventiAquistiForm,
    ExportInterventiAquistiNonRipropostiForm,
    InterventoEntry,
    InterventoInsertForm,
    ResponseListaInterventi,
} from '../../models/interventi/interventi.model';
import {
    DettaglioCupForm,
    OperaIncompiutaEntry,
    OperaIncompiutaInsertForm,
    ResponseListaOpereIncompiute,
} from '../../models/opere/opere-incompiute.model';
import {
    CambioSysconForm,
    InizNuovoProgramma,
    InterventiDaConfrontoDTO,
    ListaInterventiFilter,
    ListaProgrammiRequest,
    ProgrammaBaseEntry,
    ProgrammaEntry,
    ProgrammaInsertForm,
    ProgrammaPubblicatoForm,
    ProgrammaUpdateForm,
    ResponseListaProgrammi,
} from '../../models/programmi/programmi.model';
import {
    FlussiProgrammi,
    PubblicaProgrammaFornitureServiziEntry,
    PubblicaProgrammaLavoriEntry,
    PubblicazioneResult,
} from '../../models/pubblicazione/pubblicazione.model';
import {
    ResponseListaRisorsaCapitolo,
    RisorsaCapitoloEntry,
    RisorsaDiCapitoloInsertForm,
} from '../../models/risorse-capitolo/risorsa-capitolo.model';

@Injectable({ providedIn: 'root' })
export class ProgrammiService extends SdkBaseService {   
    constructor(inj: Injector) { super(inj); }

    public getListaProgrammi(request: ListaProgrammiRequest, state: SdkTableState): Observable<ResponseListaProgrammi> {

        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            tipologia: request.tipologia,
            searchString: request.searchString,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            syscon: request.syscon
        };

        return this.restHelper.get<ResponseListaProgrammi>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getListaProgrammi`, params);
    }

    public createProgramma(insertForm: ProgrammaInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/insertProgramma`, insertForm);

    }


    public modificaProgramma(updateForm: ProgrammaUpdateForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/updateProgramma`, updateForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );

    }


    public dettaglioProgramma(id: string): Observable<ProgrammaEntry> {

        let params: IHttpParams = {
            id
        };

        return this.restHelper.get<ResponseResult<ProgrammaEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getProgramma`, params)
            .pipe(
                map((result: ResponseResult<ProgrammaEntry>) => {
                    return result.data;
                })
            );
    }


    public getListaInterventi(idProgramma: string, state: SdkTableState, filter: ListaInterventiFilter): Observable<ResponseListaInterventi> {
        let params: IHttpParams = {
            idProgramma,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        if (filter != null) {
            params = {
                ...params,
                ...filter
            }
        }

        return this.restHelper.get<ResponseListaInterventi>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getListaInterventi`, params);
    }

    public getListaInterventiNRImport(idProgramma: string): Observable<Array<InterventiDaRiproporre>> {

        return this.restHelper.get<ResponseResult<Array<InterventiDaRiproporre>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getInterventiNRDaImportare?idProgramma=${idProgramma}`)
            .pipe(
                map((result: ResponseResult<Array<InterventiDaRiproporre>>) => {
                    return result.data;
                })
            );
    }

    public getListaInterventiImport(idProgramma: string): Observable<Array<InterventiDaRiproporre>> {

        return this.restHelper.get<ResponseResult<Array<InterventiDaRiproporre>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getInterventiDaImportare?idProgramma=${idProgramma}`)
            .pipe(
                map((result: ResponseResult<Array<InterventiDaRiproporre>>) => {
                    return result.data;
                })
            );
    }


    public dettaglioIntervento(idProgramma: string, idIntervento: string): Observable<InterventoEntry> {

        let params: IHttpParams = {
            idProgramma,
            idIntervento
        };

        return this.restHelper.get<ResponseResult<InterventoEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getIntervento`, params)
            .pipe(
                map((result: ResponseResult<InterventoEntry>) => {
                    return result.data;
                })
            );
    }


    public createIntervento(insertForm: InterventoInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/insertIntervento`, insertForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );

    }


    public modificaIntervento(updateForm: InterventoInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/updateIntervento`, updateForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }


    public getListaInterventiNonRiproposti(idProgramma: string, state: SdkTableState): Observable<ResponseListaInterventiNR> {

        let params: IHttpParams = {
            idProgramma,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaInterventiNR>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getListaInterventiNonRiproposti`, params);
    }


    public dettaglioInterventoNonRiproposto(idProgramma: string, idIntervento: string): Observable<Array<InterventoEntry>> {

        let params: IHttpParams = {
            idProgramma,
            idIntervento
        };

        return this.restHelper.get<ResponseResult<Array<InterventoNonRipropostoEntry>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getInterventoNonRiproposto`, params)
            .pipe(
                map((result: ResponseResult<Array<InterventoNonRipropostoEntry>>) => {
                    return result.data;
                })
            );
    }

    public createInterventoNonRiproposto(insertForm: InterventoNonRipropostoInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/insertInterventoNonRiproposto`, insertForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public modificaInterventoNonRiproposto(updateForm: InterventoNonRipropostoUpdateForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/updateInterventoNonRiproposto`, updateForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }


    public importaInterventiNonRip(form: ImportInterventiForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/importaInterventiNonRip`, form)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public importaInterventi(form: ImportInterventiForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/importaInterventi`, form)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }


    public getListaOpereIncompiute(idProgramma: string, state: SdkTableState): Observable<ResponseListaOpereIncompiute> {

        let params: IHttpParams = {
            idProgramma,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaOpereIncompiute>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getListaOpereIncompiute`, params);
    }

    public dettaglioCup(form: DettaglioCupForm) {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getDettaglioCup`, form);
    }

    public dettaglioOperaIncompiuta(idProgramma: string, id: string): Observable<OperaIncompiutaEntry> {

        let params: IHttpParams = {
            idProgramma,
            id
        };

        return this.restHelper.get<ResponseResult<OperaIncompiutaEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getOperaIncompiuta`, params)
            .pipe(
                map((result: ResponseResult<OperaIncompiutaEntry>) => {
                    return result.data;
                })
            );
    }

    public createOperaIncompiuta(insertForm: OperaIncompiutaInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/insertOperaIncompiuta`, insertForm);
    }

    public modificaOperaIncompiuta(updateForm: OperaIncompiutaInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/updateOperaIncompiuta`, updateForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public deleteProgramma(id: string): Observable<any> {
        let params: IHttpParams = {
            id
        };

        return this.restHelper.delete(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/deleteProgramma`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public deleteIntervento(idProgramma: string, idIntervento: string): Observable<any> {
        let params: IHttpParams = {
            idProgramma,
            idIntervento
        };

        return this.restHelper.delete(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/deleteIntervento`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public getReportSoggetto(idProgramma: string): Observable<ResponseResult<any>> {

        let params: IHttpParams = {
            idProgramma,
        };

        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/reportSoggAggregatori`, params);
    }

    public deleteInterventoNonRiproposto(idProgramma: string, idIntervento: string): Observable<any> {
        let params: IHttpParams = {
            idProgramma,
            idIntervento
        };

        return this.restHelper.delete(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/deleteInterventoNonRiproposto`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public deleteOperaIncompiuta(idProgramma: string, idOpera: string): Observable<any> {
        let params: IHttpParams = {
            idProgramma,
            idOpera
        };

        return this.restHelper.delete(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/deleteOperaIncompiuta`, null, params)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public getListaRisorseDiCapitolo(idProgramma: string, idIntervento: string, state: SdkTableState): Observable<ResponseListaRisorsaCapitolo> {

        let params: IHttpParams = {
            idProgramma,
            idIntervento,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaRisorsaCapitolo>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getRisorseDiCapitolo`, params);
    }


    public dettaglioRisorsaDiCapitolo(idProgramma: string, idIntervento: string, id: string): Observable<RisorsaCapitoloEntry> {

        let params: IHttpParams = {
            idProgramma,
            idIntervento,
            id
        };

        return this.restHelper.get<ResponseResult<RisorsaCapitoloEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getRisorsaDiCapitolo`, params)
            .pipe(
                map((result: ResponseResult<RisorsaCapitoloEntry>) => {
                    return result.data;
                })
            );
    }


    public createRisorsaDiCapitolo(insertForm: RisorsaDiCapitoloInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/insertRisorsaDiCapitolo`, insertForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }


    public modificaRisorsaDiCapitolo(updateForm: RisorsaDiCapitoloInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/updateRisorsaDiCapitolo`, updateForm)
            .pipe(
                map((result: any) => {
                    return result;
                })
            );
    }

    public deleteRisorsaDiCapitolo(idProgramma: string, idIntervento: string, idRisorsa: string): Observable<string> {
        let params: IHttpParams = {
            idProgramma,
            idIntervento,
            idRisorsa
        };

        return this.restHelper.delete<any>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/deleteRisorsaDiCapitolo`, null, params);
    }


    public getPubblicazioni(idProgramma: string, stazioneAppaltante: string): Observable<Array<FlussiProgrammi>> {
        let params: IHttpParams = {
            idProgramma,
            stazioneAppaltante
        };

        return this.restHelper.get<ResponseResult<Array<FlussiProgrammi>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getPubblicazioni`, params).pipe(
            map((result: ResponseResult<Array<FlussiProgrammi>>) => {
                return result.data;
            })
        );
    }


    public getRequestPubLavori(idProgramma: string): Observable<PubblicaProgrammaLavoriEntry> {
        let params: IHttpParams = {
            idProgramma
        };

        return this.restHelper.get<ResponseResult<PubblicaProgrammaLavoriEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getRequestPubblicazioneLavori`, params).pipe(
            map((result: ResponseResult<PubblicaProgrammaLavoriEntry>) => {
                return result.data;
            })
        );
    }


    public getRequestPubServizi(idProgramma: string): Observable<PubblicaProgrammaFornitureServiziEntry> {
        let params: IHttpParams = {
            idProgramma
        };

        return this.restHelper.get<ResponseResult<PubblicaProgrammaFornitureServiziEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getRequestPubblicazioneForniture`, params).pipe(
            map((result: ResponseResult<PubblicaProgrammaFornitureServiziEntry>) => {
                return result.data;
            })
        );
    }

    public allineaPubblicazione(form: ProgrammaPubblicatoForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/allineaPubblicazione`, form);
    }

    public getPdf(idProgramma: string, tipologia: string): Observable<any> {
        let params: IHttpParams = {
            idProgramma,
            tipoProg: tipologia
        };
        let url = environment.GESTIONE_PDF_BASE_URL;
        if(url == null || '' == url){
            url = environment.GESTIONE_PROGRAMMI_BASE_URL;
        }

        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getPdf`, params);
    }

    public copiaPerAggiornamento(idProgramma: string): Observable<number> {
        let params: IHttpParams = {
            id: idProgramma
        };
        return this.restHelper.post<ResponseResult<number>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/copiaProgramma`, null, params)
            .pipe(
                map((result: ResponseResult<number>) => {
                    return result.data;
                })
            );
    }

    public cambiaSyscon(form: CambioSysconForm): Observable<ResponseResult<any>> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/cambiaSyscon`, form);
    }

    public listaOpzioniCui(form: CuiSearchForm): Observable<Array<string>> {
        let params: IHttpParams = {
            stazioneAppaltante: form.stazioneAppaltante,
            desc: form.desc
        };
        return this.restHelper.get<ResponseResult<Array<string>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getCuiOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<string>>) => {
                    return result.data;
                })
            );
    }

    public getListaProgrammiNonPaginata(stazioneAppaltante: string, syscon: number, searchString: string): Observable<Array<ProgrammaBaseEntry>> {

        const params: IHttpParams = {
            stazioneAppaltante,
            syscon: toString(syscon),
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<ProgrammaBaseEntry>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getListaProgrammiNonPaginata`, params)
            .pipe(
                map((result: ResponseResult<Array<ProgrammaBaseEntry>>) => {
                    return result.data;
                })
            );
    }

    public getInizNuovoProgramma(stazioneAppaltante: string, syscon: string): Observable<InizNuovoProgramma> {
        const params: IHttpParams = {
            syscon,
            stazioneAppaltante
        };

        return this.restHelper.get<ResponseResult<InizNuovoProgramma>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getInizNuovoProgramma`, params)
            .pipe(
                map((response: ResponseResult<InizNuovoProgramma>) => {
                    return response.data;
                })
            );
    }

    public getInizDettaglioCup(syscon: string): Observable<any> {
        const params: IHttpParams = {
            syscon
        };

        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getInizDettaglioCup`, params)
            .pipe(
                map((response: ResponseResult<any>) => {
                    return response.data;
                })
            );
    }

    public showSoggReport(idProgramma: string): Observable<any> {
        let params: IHttpParams = {
            idProgramma
        };
        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getShowSoggReport`, params);
    }

    public deleteFlussoProgramma(idProgramma: string): Observable<any> {
        const params: IHttpParams = {
            idProgramma
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/deleteFlussoProgramma`, null, params);
    }

    public getLinkPubbblicazione(idRicevuto: string): Observable<ResponseResult<string>> {
        const params: IHttpParams = {
            idRicevuto
        };

        return this.restHelper.get<ResponseResult<string>>(`${environment.PUBBLICAZIONE_PROGRAMMI_BASE_URL}/getPubblicazioneLink`, params)
    }

    public exportInterventiAcquisti(form: ExportInterventiAquistiForm): Observable<string> {
        return this.restHelper.post<ResponseResult<string>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/exportInterventiAcquisti`, form)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public exportInterventiAcquistiNr(form: ExportInterventiAquistiNonRipropostiForm): Observable<string> {
        return this.restHelper.post<ResponseResult<string>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/exportInterventiAcquistiNonRiproposti`, form)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public getListaProgrammiDaConfronto(contri: string): Observable<Array<ProgrammaBaseEntry>> {
        const params: IHttpParams = {
            contri
        };

        return this.restHelper.get<ResponseResult<Array<ProgrammaBaseEntry>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/listaProgrammiDaConfronto`, params)
            .pipe(
                map((response: ResponseResult<Array<ProgrammaBaseEntry>>) => {
                    return response.data;
                })
            );
    }

    public getListaInterventiDaConfronto(contri: string, contriDaConfrontare: string): Observable<Array<InterventiDaConfrontoDTO>> {
        const params: IHttpParams = {
            contri,
            contriDaConfrontare
        };

        return this.restHelper.get<ResponseResult<Array<InterventiDaConfrontoDTO>>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/listaInterventiDaConfronto`, params)
            .pipe(
                map((response: ResponseResult<Array<InterventiDaConfrontoDTO>>) => {
                    return response.data;
                })
            );
    }

    public downloadListaInterventiDaConfronto(contri: string, contriDaConfrontare: string): Observable<string> {

        const params: IHttpParams = {
            contri,
            contriDaConfrontare
        };

        return this.restHelper.post<ResponseResult<string>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/downloadListaInterventiDaConfronto`, null, params)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return result.data;
                })
            );
    }

    public getTipologiaInstallazione(): Observable<any> {
        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getTipoInstallazione`)
        .pipe(
            map((response: ResponseResult<any>) => {
                return response.data;
            })
        );
    }

    public dettaglioInterventoNr(idProgramma: string, idIntervento: string): Observable<InterventoNonRipropostoEntry> {
        const params: IHttpParams = {
            idProgramma,
            idIntervento
        };
        return this.restHelper.get<ResponseResult<InterventoNonRipropostoEntry>>(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/getDettaglioInterventoNr`, params)
        .pipe(
            map((response: ResponseResult<InterventoNonRipropostoEntry>) => {
                return response.data;
            })
        );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}