import { HttpErrorResponse } from '@angular/common/http';
import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    IHttpParams,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkRestHelperService,
} from '@maggioli/sdk-commons';
import { isArray, isEmpty, sortBy, toString } from 'lodash-es';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { ResponseResult } from '../../models/common/common.model';
import { ProfiloConfiguration } from '../../models/profilo/profilo.model';
import {
    StazioneAppaltanteBaseInfo,
    StazioneAppaltanteInfo,
} from '../../models/stazione-appaltante/stazione-appaltante.model';
import { OpzioniUtenteProdotto, UserAccountInfo, UserDTO, UserEditDTO, UsrSysconEntry } from '../../models/user/user.model';
import { ResponseDTO } from '../../models/utils/utils.model';

@Injectable()
export class UserService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj); }

    public getUserInfo(baseUrl: string, searchSA: string): Observable<UserAccountInfo> {

        let params: IHttpParams = {};

        if (searchSA != null) {
            params.searchUfficioIntestatario = searchSA;
        }

        return this.restHelper.get<ResponseResult<UserAccountInfo>>(`${baseUrl}/AccountInfo`, params)
            .pipe(
                map((result: ResponseResult<UserAccountInfo>) => {
                    const data: UserAccountInfo = result.data;
                    if (data.stazioniAppaltanti != null && isArray(data.stazioniAppaltanti) && data.stazioniAppaltanti.length > 1) {
                        data.stazioniAppaltanti = sortBy(data.stazioniAppaltanti, (one: StazioneAppaltanteBaseInfo) => one.nome);
                    }
                    return data;
                })
            );
    }

    public getSAInfo(baseUrl: string, codiceSA: string, codiceApplicazione: string, syscon: string, selezioneSA?: boolean): Observable<StazioneAppaltanteInfo> {

        let params: IHttpParams = {
            stazioneAppaltante: codiceSA,
            codapp: codiceApplicazione,
            syscon,
            selezioneSA: toString(selezioneSA ?? false)
        };

        return this.restHelper.get<ResponseResult<StazioneAppaltanteInfo>>(`${baseUrl}/GetSAInfo`, params)
            .pipe(map((result: ResponseResult<StazioneAppaltanteInfo>) => {
                return result.data;
            }));
    }


    public getConfigProfilo(baseUrl: string, codProfilo: string) {
        let params: IHttpParams = {
            codProfilo
        };
        return this.restHelper.get<ResponseResult<ProfiloConfiguration>>(`${baseUrl}/GetProfilo`, params)
            .pipe(map((result: ResponseResult<ProfiloConfiguration>) => {
                return result.data;
            })
            );

    }

    public getInizSpidLogin(baseUrl: string): Observable<any> {

        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getInizSpid`)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result.data;
                })
            );
    }

    public getSpidSa(baseUrl: string, searchUfficioIntestatario: string): Observable<any> {
        const params: IHttpParams = {
            searchUfficioIntestatario
        };
        return this.restHelper.get<ResponseResult<any>>(`${baseUrl}/getSpidSa`, params);
    }

    public getUrlSPID(baseUrl: string, idProvider: string, callbackUrl: string, codein: string): Observable<any> {
        const params: IHttpParams = {
            idProvider,
            callbackUrl,
            codein
        };
        return this.restHelper.get<any>(`${baseUrl}/getInfoSpid`, params);
    }

    public getUserSPID(baseUrl: string, authId: string, codein: string, provider: string): Observable<any> {
        const params: IHttpParams = {
            authId,
            codein,
            provider
        };
        return this.restHelper.get<any>(`${baseUrl}/getSpidUser`, params);
    }

    public getAbilitazioni(baseUrl: string): Observable<OpzioniUtenteProdotto> {
        return this.restHelper.get<ResponseResult<OpzioniUtenteProdotto>>(`${baseUrl}/GetAbilitazioni`)
            .pipe(map((result: ResponseResult<OpzioniUtenteProdotto>) => {
                return result.data;
            }));
    }

    public getUtentiStazioneAppaltante(baseUrl: string, codiceSA: string): Observable<Array<UsrSysconEntry>> {

        const params: IHttpParams = {
            codiceSA
        };

        return this.restHelper.get<ResponseResult<Array<UsrSysconEntry>>>(`${baseUrl}/getUtentiStazioneAppaltante`, params)
            .pipe(
                map((result: ResponseResult<Array<UsrSysconEntry>>) => {
                    return result.data;
                })
            );
    }

    public getAllUtenti(baseUrl: string): Observable<Array<UsrSysconEntry>> {

        return this.restHelper.get<ResponseResult<Array<UsrSysconEntry>>>(`${baseUrl}/getAllUtenti`)
            .pipe(
                map((result: ResponseResult<Array<UsrSysconEntry>>) => {
                    return result.data;
                })
            );
    }

    public setUtentiStazioneAppaltante(baseUrl: string, codice: string, listaUtentiStazioneAppaltante: Array<number>): Observable<boolean> {

        const body: IDictionary<any> = {
            codice,
            listaUtentiStazioneAppaltante
        };

        return this.restHelper.put<ResponseResult<boolean>>(`${baseUrl}/setUtentiStazioneAppaltante`, body)
            .pipe(
                map((result: ResponseResult<boolean>) => {
                    return result.data;
                })
            );
    }

    public isRichiestaAssistenzaAttiva(): Observable<boolean> {
        return this.restHelper.get<any>(`${this.appConfig.environment.GESTIONE_UTENTI_PUBLIC_BASE_URL}/isRichiestaAssistenzaAttiva`)
            .pipe(
                map((result: any) => {
                    return result.response;
                })
            );
    }

    public updateUser(syscon: number, form: UserEditDTO): Observable<ResponseDTO<UserDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<UserDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon, form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService); };

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    // #endregion
}