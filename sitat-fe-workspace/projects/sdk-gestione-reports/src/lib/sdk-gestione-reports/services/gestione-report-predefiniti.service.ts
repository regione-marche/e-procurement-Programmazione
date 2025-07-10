import { HttpErrorResponse } from "@angular/common/http";
import { Inject, Injectable, Injector } from "@angular/core";
import {
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkRestHelperService
} from "@maggioli/sdk-commons";
import { isEmpty } from "lodash-es";
import { Observable, catchError, tap, throwError } from "rxjs";
import { ResponseDTO, ResponseListaDTO } from "../model/lib.model";
import { WRicercheDTO } from "../model/lista-report.model";

@Injectable()
export class GestioneReportPredefinitiService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    //#region Public

    public getListaReportPredefiniti(syscon: string, idProfilo: string, uffInt: string): Observable<ResponseListaDTO<Array<WRicercheDTO>>> {

        if (isEmpty(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL))
            throw new Error('GESTIONE_REPORTS_LISTA_URL empty');


        uffInt = uffInt === '*' ? 'Tutte le Stazioni Appaltanti' : uffInt;

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        return this.restHelper.get<ResponseListaDTO<Array<WRicercheDTO>>>(this.appConfig.environment.GESTIONE_REPORTS_LISTA_URL + `/getListaReportPredefiniti/${syscon}/${idProfilo}/${uffInt}`)
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