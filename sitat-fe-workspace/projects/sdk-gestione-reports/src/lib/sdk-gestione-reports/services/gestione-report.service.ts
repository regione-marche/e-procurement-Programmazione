import { HttpErrorResponse, HttpResponse } from "@angular/common/http";
import { Inject, Injectable, Injector } from "@angular/core";
import {
    IHttpParams,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkRestHelperService
} from "@maggioli/sdk-commons";
import { isEmpty, toString } from "lodash-es";
import { Observable, catchError, tap, throwError } from "rxjs";
import { DefinizioneReportEditDTO, DynamicFormValueDTO, GenericDynamicFormValueDTO, JsonResponseDTO, ProfiliUtenteEditDTO, ProfiloDTO, ResponseDTO, ResponseListaDTO, ResultQueryExecutionFormDTO, ResultReportQueryParamsDTO, UfficioIntestatarioDTO, UfficioIntestatarioEditDTO } from "../model/lib.model";
import { WRicParamDTO } from "../model/lista-params.model";
import { WRicercheDTO } from "../model/lista-report.model";

@Injectable()
export class GestioneReportService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    //#region Public
    
    //#region Get

    public getListaReport(): Observable<ResponseDTO<Array<WRicercheDTO>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        return this.restHelper.get<ResponseDTO<Array<WRicercheDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getListaReports`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDetailReport(idRicerca: number): Observable<ResponseDTO<WRicercheDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<WRicercheDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getDetailReport/${idRicerca}` )
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaProfili(): Observable<ResponseDTO<Array<ProfiloDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<ProfiloDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + '/profili')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaUfficiIntestatari(): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<UfficioIntestatarioDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + '/getListaUfficiIntestatari')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaUfficiIntestatariReport(idRicerca: number): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<UfficioIntestatarioDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getUfficiIntestatariReport/${idRicerca}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaProfiliReport(idRicerca: number): Observable<ResponseDTO<Array<ProfiloDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<ProfiloDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getProfiliReport/${idRicerca}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getAbilitazioneUffInt(): Observable<ResponseListaDTO<number>>{
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<ProfiloDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getAbilitazioniUffInt`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    //#endregion

    //#region Put

    public updateDefSqlReport(form: DefinizioneReportEditDTO, syscon: number, idProfilo: string): Observable<ResponseDTO<DefinizioneReportEditDTO>>{
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<DefinizioneReportEditDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/updateDefSqlReport/${syscon}/${idProfilo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateDatiGeneraliReport(form: WRicercheDTO, syscon: number, idProfilo: string) {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<DefinizioneReportEditDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/updateDatiGeneraliReport/${syscon}/${idProfilo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public setListaProfiliReport(idRicerca: number, syscon: number, idProfilo: string, listaProfili: Array<string>): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        const body: ProfiliUtenteEditDTO = {
            listaProfili
        };

        return this.restHelper.put<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/setProfiliReport/${idRicerca}/${syscon}/${idProfilo}/profili`, body)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public setListaUfficiIntestatariReport(idRicerca: number, syscon: number, idProfilo: string, listaUfficiIntestatari: Array<string>): Observable<ResponseListaDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        const body: UfficioIntestatarioEditDTO = {
            listaUfficiIntestatari
        };

        return this.restHelper.put<ResponseListaDTO<boolean>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/setUfficiIntestatariReport/${idRicerca}/${syscon}/${idProfilo}`, body)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public executeReportWithParams(idRicerca: number, syscon: number, idProfilo:string, form: Array<WRicParamDTO>) {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let values: DynamicFormValueDTO = {};
        let params: GenericDynamicFormValueDTO = {};

        //Per ogni parametro devo ricevere un valore da parte dell'utente.
        //Per inviarlo al backend popolo un'interfaccia con campo values (any) dinamicamente.
        //Il risultato è un oggetto contenente tutti i parametri a partire da value1 (il primo parametro con progressivo = 1)
        //fino alla fine, valueN, ordinati per PROGRESSIVO in ordine crescente.
        /*form.forEach((item, index) => {
            const key = `${item.tipo}-${index + 1}`;
            values[key] = {
                value: item.value
            }  
        })*/

        form.forEach((item) => {
            const key = item.codice;
            values[key] = {
                [item.tipo]: item.value
            }  
        })

        params = {
            dynamicFormValueDTO: {...values},
            allParams: form
        };

        return this.restHelper.put<ResponseListaDTO<ResultQueryExecutionFormDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/executeReportWithParams/${idRicerca}/${syscon}/${idProfilo}`, params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public exportListaReportCsv(request: ResultReportQueryParamsDTO, idProfilo: string): Observable<ResponseListaDTO<HttpResponse<any>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseListaDTO<HttpResponse<any>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/exportRisultatoReportCsv/${idProfilo}`, request)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public exportListaReportRtf(request: ResultReportQueryParamsDTO, idProfilo: string): Observable<ResponseListaDTO<HttpResponse<any>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseListaDTO<HttpResponse<any>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/exportRisultatoReportRtf/${idProfilo}`, request)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public exportListaReportDocx(request: ResultReportQueryParamsDTO, idProfilo: string): Observable<ResponseListaDTO<HttpResponse<any>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseListaDTO<HttpResponse<any>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/exportRisultatoReportDocx/${idProfilo}`, request)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public exportListaReportPdf(request: ResultReportQueryParamsDTO, idProfilo: string): Observable<ResponseListaDTO<HttpResponse<any>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseListaDTO<HttpResponse<any>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/exportRisultatoReportPdf/${idProfilo}`, request)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public exportListaReportJson(request: ResultReportQueryParamsDTO, idProfilo: string): Observable<ResponseListaDTO<Array<JsonResponseDTO>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseListaDTO<Array<JsonResponseDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/exportRisultatoReportJson/${idProfilo}`, request)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public exportListaReportXlsx(request: ResultReportQueryParamsDTO, idProfilo: string): Observable<ResponseListaDTO<HttpResponse<any>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseListaDTO<HttpResponse<any>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/exportRisultatoReportXlsx/${idProfilo}`, request)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public moveRowUpParam(idRicerca: number, codice: string): Observable<ResponseDTO<WRicParamDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<WRicParamDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/moveParamRowUp/${idRicerca}/${codice}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public moveRowDownParam(idRicerca: number, codice: string): Observable<ResponseDTO<WRicParamDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<WRicParamDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/moveParamRowDown/${idRicerca}/${codice}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public rimuoviReportPreferito(idRicerca: number, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            idRicerca: toString(idRicerca),
            syscon: toString(syscon),
            idProfilo
        };

        return this.restHelper.delete<ResponseDTO<WRicercheDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/rimuoviReportPreferito`, null, params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public aggiungiReportPreferito(idRicerca: number, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            idRicerca: toString(idRicerca),
            syscon: toString(syscon),
            idProfilo
        };

        return this.restHelper.post<ResponseDTO<WRicercheDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/aggiungiReportPreferito`, null, params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    //#endregion

    //#region Post

    public copyRowReport(idRicerca: number, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<WRicercheDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/copyRowReport/${idRicerca}/${syscon}/${idProfilo}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertNewReport(form: WRicercheDTO, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<WRicercheDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/insertNewReport/${syscon}/${idProfilo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    //#endregion

    //#region Delete

    public deleteRowReport(idRicerca: number, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.delete<ResponseDTO<WRicercheDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/deleteRowReport/${idRicerca}/${syscon}/${idProfilo}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public deleteParamRowReport(idRicerca: number, codice: string, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicParamDTO>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.delete<ResponseDTO<WRicParamDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/deleteParamRowReport/${idRicerca}/${codice}/${syscon}/${idProfilo}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    //#endregion

    //#region Utility

    public userHasDifferentUfficiIntestatariThanSessionUser(ufficiIntestatariUser: Array<string>, ufficiIntestatariSessionUser: Array<string>): boolean {
        if (ufficiIntestatariUser == null || ufficiIntestatariUser.length == 0 || ufficiIntestatariSessionUser == null || ufficiIntestatariSessionUser.length == 0)
            return true;

        let result: Array<string> = ufficiIntestatariUser.filter(val => !ufficiIntestatariSessionUser.includes(val));
        return result != null && result.length > 0;
    }

    //#endregion 

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    // #endregion
}