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
import { isEmpty } from 'lodash-es';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { WQuartzDTO, WQuartzEditDTO } from '../model/amministrazione.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class GestionePianificazioniService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public getListaPianificazioni(): Observable<ResponseDTO<Array<WQuartzDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<WQuartzDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestionePianificazioni/lista')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDettaglioPianificazione(codApp: string, beanId: string): Observable<ResponseDTO<WQuartzDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            codApp,
            beanId
        };

        return this.restHelper.get<ResponseDTO<WQuartzDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestionePianificazioni/pianificazione', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updatePianificazione(form: WQuartzEditDTO): Observable<ResponseDTO<WQuartzDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<WQuartzDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestionePianificazioni/pianificazione', form)
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