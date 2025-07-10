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
import { SdkTableState } from '@maggioli/sdk-table';
import { head, isEmpty, toString } from 'lodash-es';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import {
    ResponseListaDTO,
    RicercaConfigurazioniFormDTO,
    RicercaConfigurazioniInizDTO,
    WConfigDTO,
    WConfigEditDTO,
} from '../model/amministrazione.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class GestioneConfigurazioniService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public getInizRicercaConfigurazioni(): Observable<ResponseDTO<RicercaConfigurazioniInizDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<RicercaConfigurazioniInizDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneConfigurazioni/getInizRicercaConfigurazioni')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }


    public getListaConfigurazioni(form: RicercaConfigurazioniFormDTO, state: SdkTableState): Observable<ResponseListaDTO<Array<WConfigDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);


        let params: IHttpParams = {
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
        };

        if (form != null) {
            params = {
                ...params,
                sezione: form.sezione,
                chiave: form.chiave,
                valore: form.valore,
                descrizione: form.descrizione
            }
        }

        return this.restHelper.get<ResponseListaDTO<Array<WConfigDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneConfigurazioni/lista', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDettaglioConfigurazione(codApp: string, chiave: string): Observable<ResponseDTO<WConfigDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            codApp,
            chiave
        };

        return this.restHelper.get<ResponseDTO<WConfigDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneConfigurazioni/configurazione', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateConfigurazione(form: WConfigEditDTO): Observable<ResponseDTO<WConfigDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<WConfigDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneConfigurazioni/configurazione', form)
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