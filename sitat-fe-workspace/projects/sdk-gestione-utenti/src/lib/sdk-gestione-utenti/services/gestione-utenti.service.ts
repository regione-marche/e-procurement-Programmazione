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
    InitRicercaUtentiDTO,
    ProfiliUtenteEditDTO,
    ProfiloDTO,
    ResponseListaUtentiDTO,
    RicercaUtentiFormDTOInternal,
    RichiestaAssistenzaFormDTO,
    RichiestaAssistenzaInitDTO,
    UfficioIntestatarioDTO,
    UfficioIntestatarioEditDTO,
    UserChangePasswordAdminDTO,
    UserConnectedDTO,
    UserConnectedEditDTO,
    UserDTO,
    UserEditDTO,
    UserInsertDTO,
} from '../model/gestione-utenti.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class GestioneUtentiService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public getListaUtenti(form: RicercaUtentiFormDTOInternal, state: SdkTableState): Observable<ResponseListaUtentiDTO<Array<UserDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

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
                denominazione: form.denominazione,
                username: form.username,
                usernameCF: form.usernameCF,
                abilitato: form.abilitato,
                codiceFiscale: form.codiceFiscale,
                email: form.email,
                ufficioIntestatarioKey: form.ufficioIntestatarioKey,
                gestioneUtenti: form.gestioneUtenti,
                amministratore: form.amministratore,
                profiloKey: form.profiloKey
            }
        }

        return this.restHelper.get<ResponseListaUtentiDTO<Array<UserDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/lista', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public insertUser(form: UserInsertDTO): Observable<ResponseDTO<UserDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<UserDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDettaglioUtente(syscon: number): Observable<ResponseDTO<UserDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<UserDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getUfficioIntestatario(codice: string): Observable<ResponseDTO<UfficioIntestatarioDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        return this.restHelper.get<ResponseDTO<UfficioIntestatarioDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/ufficioIntestatario/' + codice);
    }

    public listaOpzioniUfficiIntestatari(searchData: string): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        let params: IHttpParams = {
            searchData
        };

        return this.restHelper.get<ResponseDTO<Array<UfficioIntestatarioDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/ufficiIntestatari/options', params);
    }

    public getProfilo(codice: string): Observable<ResponseDTO<ProfiloDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        return this.restHelper.get<ResponseDTO<ProfiloDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/profilo/' + codice);
    }

    public listaOpzioniProfili(searchData: string): Observable<ResponseDTO<Array<ProfiloDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        let params: IHttpParams = {
            searchData
        };

        return this.restHelper.get<ResponseDTO<Array<ProfiloDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/profili/options', params);
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

    public enableUser(syscon: number): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/abilita')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public disableUser(syscon: number): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/disabilita')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public deleteUser(syscon: number): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.delete<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaProfiliUtente(syscon: number): Observable<ResponseDTO<Array<ProfiloDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<ProfiloDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/profili')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaProfili(): Observable<ResponseDTO<Array<ProfiloDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<ProfiloDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/profili')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public setListaProfiliUtente(syscon: number, listaProfili: Array<string>): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        const body: ProfiliUtenteEditDTO = {
            listaProfili
        };

        return this.restHelper.put<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/profili', body)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaUfficiIntestatariUtente(syscon: number): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<UfficioIntestatarioDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/ufficiIntestatari')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getListaUfficiIntestatari(): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<Array<UfficioIntestatarioDTO>>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/ufficiIntestatari')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public setListaUfficiIntestatariUtente(syscon: number, listaUfficiIntestatari: Array<string>): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        const body: UfficioIntestatarioEditDTO = {
            listaUfficiIntestatari
        };

        return this.restHelper.put<ResponseDTO<boolean>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/ufficiIntestatari', body)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public changePasswordAdminUtente(syscon: number, form: UserChangePasswordAdminDTO): Observable<ResponseDTO<UserDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<UserDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utente/' + syscon + '/changePassword', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getInizRichiestaAssistenza(): Observable<ResponseDTO<RichiestaAssistenzaInitDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_PUBLIC_BASE_URL))
            throw new Error('GESTIONE_UTENTI_PUBLIC_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<RichiestaAssistenzaInitDTO>>(this.appConfig.environment.GESTIONE_UTENTI_PUBLIC_BASE_URL + '/richiestaAssistenza')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public richiestaAssistenza(form: RichiestaAssistenzaFormDTO): Observable<ResponseDTO<any>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_PUBLIC_BASE_URL))
            throw new Error('GESTIONE_UTENTI_PUBLIC_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<any>>(this.appConfig.environment.GESTIONE_UTENTI_PUBLIC_BASE_URL + '/richiestaAssistenza', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public getDetailUserConnected(): Observable<ResponseDTO<UserConnectedDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<UserConnectedDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utenteConnesso')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public updateUserConnected(form: UserConnectedEditDTO): Observable<ResponseDTO<UserConnectedDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.put<ResponseDTO<UserConnectedEditDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/utenteConnesso', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }


    public getDownloadUtentiCsv(form: RicercaUtentiFormDTOInternal): Observable<ResponseDTO<any>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);


        let params: IHttpParams = {
            skip: '0',
            take: '0',
            sort: 'null',
            sortDirection: 'asc'
        };

        if (form != null) {
            params = {
                ...params,
                denominazione: form.denominazione,
                username: form.username,
                abilitato: form.abilitato,
                codiceFiscale: form.codiceFiscale,
                email: form.email,
                ufficioIntestatarioKey: form.ufficioIntestatarioKey,
                gestioneUtenti: form.gestioneUtenti,
                amministratore: form.amministratore,
                profiloKey: form.profiloKey
            }
        }

        return this.restHelper.get<ResponseDTO<any>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/csvUtenti', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public initRicercaUtenti(): Observable<ResponseDTO<InitRicercaUtentiDTO>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.get<ResponseDTO<InitRicercaUtentiDTO>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/initRicercaUtenti')
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public userHasDifferentUfficiIntestatariThanSessionUser(ufficiIntestatariUser: Array<string>, ufficiIntestatariSessionUser: Array<string>): boolean {
        if (ufficiIntestatariUser == null || ufficiIntestatariUser.length == 0 || ufficiIntestatariSessionUser == null || ufficiIntestatariSessionUser.length == 0)
            return true;

        let result: Array<string> = ufficiIntestatariUser.filter(val => !ufficiIntestatariSessionUser.includes(val));
        return result != null && result.length > 0;
    }

    // #endregion

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    // #endregion
}