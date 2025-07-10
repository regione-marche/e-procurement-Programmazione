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

import { ResponseListaDTO, RicercaEventiFormDTO, RicercaEventiInizDTO, WLogEventiDTO } from '../model/amministrazione.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class GestioneEventiService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public getInizRicercaEventi(): Observable<ResponseDTO<RicercaEventiInizDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<RicercaEventiInizDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneEventi/getInizRicercaEventi')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }


    public getListaEventi(form: RicercaEventiFormDTO, state: SdkTableState): Observable<ResponseListaDTO<Array<WLogEventiDTO>>> {
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
                dataOraDa: form.dataOraDa,
                dataOraA: form.dataOraA,
                descrizioneUtente: form.descrizioneUtente,
                livelloEvento: form.livelloEvento,
                codiceEvento: form.codiceEvento,
                oggettoEvento: form.oggettoEvento,
                descrizione: form.descrizione
            };

            if (form.idEvento != null) {
                params.idEvento = '' + form.idEvento;
            }
        }

        return this.restHelper.get<ResponseListaDTO<Array<WLogEventiDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneEventi/lista', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDettaglioEvento(idEvento: number): Observable<ResponseDTO<WLogEventiDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<WLogEventiDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneEventi/evento/${idEvento}`)
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