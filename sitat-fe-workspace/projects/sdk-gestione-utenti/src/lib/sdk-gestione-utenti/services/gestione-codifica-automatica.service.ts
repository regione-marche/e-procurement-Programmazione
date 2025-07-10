import { HttpErrorResponse } from '@angular/common/http';
import { Inject, Injectable, Injector } from '@angular/core';
import {
    IHttpParams,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkRestHelperService,
} from '@maggioli/sdk-commons';
import { isEmpty, toString } from 'lodash-es';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { GConfCodDTO, GConfCodEditDTO, ListaCodificaAutomaticaInizDTO } from '../model/amministrazione.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class GestioneCodificaAutomaticaService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public getInizLista(): Observable<ResponseDTO<ListaCodificaAutomaticaInizDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<ListaCodificaAutomaticaInizDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneCodificaAutomatica/getInizLista')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaCodificaAutomatica(codApp: string): Observable<ResponseDTO<Array<GConfCodDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);


        let params: IHttpParams = {
            codApp
        };

        return this.restHelper.get<ResponseDTO<Array<GConfCodDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneCodificaAutomatica/lista', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDettaglioCodificaAutomatica(nomEnt: string, nomCam: string, tipCam: number): Observable<ResponseDTO<GConfCodDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);


        let params: IHttpParams = {
            nomEnt,
            nomCam,
            tipCam: toString(tipCam)
        };

        return this.restHelper.get<ResponseDTO<GConfCodDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneCodificaAutomatica/codifica-automatica', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateCodificaAutomatica(form: GConfCodEditDTO): Observable<ResponseDTO<GConfCodDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<GConfCodDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneCodificaAutomatica/codifica-automatica', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    // #endregion

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    // #endregion
}