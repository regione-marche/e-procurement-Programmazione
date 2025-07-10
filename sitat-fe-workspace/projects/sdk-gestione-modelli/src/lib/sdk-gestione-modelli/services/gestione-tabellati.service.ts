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
    ListaDettaglioTabellatoFormDTO,
    ResponseListaDTO,
    RicercaTabellatiFormDTO,
    Tab0DTO,
    Tab1DTO,
    Tab2DTO,
    Tab3DTO,
    Tab5DTO,
    TabXDTO,
    VTab4Tab6DTO,
} from '../model/amministrazione.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class GestioneTabellatiService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public getListaTabellati(form: RicercaTabellatiFormDTO, state: SdkTableState): Observable<ResponseListaDTO<Array<VTab4Tab6DTO>>> {
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
                codiceTabellato: form.codiceTabellato,
                descrizioneTabellato: form.descrizioneTabellato
            };
        }

        return this.restHelper.get<ResponseListaDTO<Array<VTab4Tab6DTO>>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneTabellati/lista', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaDettaglioTabellato(request: ListaDettaglioTabellatoFormDTO): Observable<ResponseListaDTO<Array<TabXDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            skip: toString(request.skip),
            take: toString(request.take),
            provenienza: request.provenienza,
            codiceTabellato: request.codiceTabellato
        };

        return this.restHelper.get<ResponseListaDTO<Array<TabXDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneTabellati/listaDettaglioTabellato', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDettaglioTabellato(provenienza: string, codiceTabellato: string, identificativo: string): Observable<ResponseDTO<TabXDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            provenienza,
            codiceTabellato,
            identificativo
        };

        return this.restHelper.get<ResponseDTO<TabXDTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneTabellati/dettaglioTabellato', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertTab0(codiceTabellato: string, form: Tab0DTO): Observable<ResponseDTO<Tab0DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<Tab0DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/insertTab0/${codiceTabellato}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertTab1(codiceTabellato: string, form: Tab1DTO): Observable<ResponseDTO<Tab1DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<Tab1DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/insertTab1/${codiceTabellato}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertTab2(codiceTabellato: string, form: Tab2DTO): Observable<ResponseDTO<Tab2DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<Tab2DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/insertTab2/${codiceTabellato}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertTab3(codiceTabellato: string, form: Tab3DTO): Observable<ResponseDTO<Tab3DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<Tab3DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/insertTab3/${codiceTabellato}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertTab5(codiceTabellato: string, form: Tab5DTO): Observable<ResponseDTO<Tab5DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<Tab5DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/insertTab5/${codiceTabellato}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateTab0(codiceTabellato: string, identificativo: string, form: Tab0DTO): Observable<ResponseDTO<Tab0DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<Tab0DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/updateTab0/${codiceTabellato}/${identificativo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateTab1(codiceTabellato: string, identificativo: string, form: Tab1DTO): Observable<ResponseDTO<Tab1DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<Tab1DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/updateTab1/${codiceTabellato}/${identificativo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateTab2(codiceTabellato: string, identificativo: string, form: Tab2DTO): Observable<ResponseDTO<Tab2DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<Tab2DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/updateTab2/${codiceTabellato}/${identificativo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateTab3(codiceTabellato: string, identificativo: string, form: Tab3DTO): Observable<ResponseDTO<Tab3DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<Tab3DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/updateTab3/${codiceTabellato}/${identificativo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateTab5(codiceTabellato: string, identificativo: string, form: Tab5DTO): Observable<ResponseDTO<Tab5DTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<Tab5DTO>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + `/gestioneTabellati/updateTab5/${codiceTabellato}/${identificativo}`, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public deleteTabellato(provenienza: string, codiceTabellato: string, identificativo: string): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL))
            throw new Error('GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL empty');

        let params: IHttpParams = {
            provenienza,
            codiceTabellato,
            identificativo
        };

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.delete<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL + '/gestioneTabellati/deleteTabellato', null, params)
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