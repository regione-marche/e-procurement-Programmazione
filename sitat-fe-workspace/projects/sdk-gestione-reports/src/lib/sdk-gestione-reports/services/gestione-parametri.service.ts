import { HttpErrorResponse } from "@angular/common/http";
import { Inject, Injectable, Injector } from "@angular/core";
import { SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkHttpLoaderService, SdkHttpLoaderType, SdkRestHelperService } from "@maggioli/sdk-commons";
import { isEmpty } from "lodash-es";
import { Observable, catchError, tap, throwError } from "rxjs";
import { ResponseDTO, ResponseListaDTO } from "../model/lib.model";
import { WRicParamDTO } from '../model/lista-params.model';

@Injectable()
export class GestioneParametriService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    //#region Public

    //#region Get
    
    public getListaParametriReport(idRicerca: number, syscon: number): Observable<ResponseListaDTO<Array<WRicParamDTO>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL)){
            throw new Error('GESTIONE_REPORT_LISTA_PARAMS_URL empty');
        }
            
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        return this.restHelper.get<ResponseListaDTO<Array<WRicParamDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getListaParametri/${idRicerca}/${syscon}`)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDetailParamReport(idRicerca: number, codice: string): Observable<ResponseDTO<WRicParamDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<WRicParamDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getDetailParamReport/${idRicerca}/${codice}`)
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

    public addNuovoParametro(form: WRicParamDTO, syscon: number, idProfilo: string) {
        if(isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL)){
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');
        }

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<WRicParamDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/insertNewParamReport/${syscon}/${idProfilo}`, form)
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

    public updateDettaglioParametroReport(form: WRicParamDTO, syscon: number, idProfilo: string){
        if(isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL)){
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');
        }

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<WRicParamDTO>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/updateDettaglioParametroReport/${syscon}/${idProfilo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    //#endregion    

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    // #endregion

}