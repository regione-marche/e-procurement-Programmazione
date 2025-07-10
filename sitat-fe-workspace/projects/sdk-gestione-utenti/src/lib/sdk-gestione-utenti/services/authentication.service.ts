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
import { catchError, map, tap } from 'rxjs/operators';

import {
    AuthenticationDTO,
    ChangePasswordDTO,
    ChangePasswordUserDTO,
    CheckMTokenDTO,
    LoginSuccessDTO,
    PasswordRecoveryExecutionDTO,
    PasswordRecoveryRequestDTO,
} from '../model/authentication.model';
import { ResponseDTO } from '../model/lib.model';

@Injectable()
export class AuthenticationService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    // #region Public

    public executeLogin(form: AuthenticationDTO): Observable<ResponseDTO<LoginSuccessDTO>> {
        if (isEmpty(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL))
            throw new Error('INTERNAL_LOGIN_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<LoginSuccessDTO>>(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL + '/authorize', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public executeChangePassword(form: ChangePasswordDTO): Observable<ResponseDTO<any>> {
        if (isEmpty(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL))
            throw new Error('INTERNAL_LOGIN_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<any>>(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL + '/changePassword', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public requestPasswordRecovery(form: PasswordRecoveryRequestDTO): Observable<ResponseDTO<string>> {
        if (isEmpty(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL))
            throw new Error('INTERNAL_LOGIN_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<string>>(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL + '/requestPasswordRecovery', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public checkPasswordRecoveryToken(token: string): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL))
            throw new Error('INTERNAL_LOGIN_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        let params: IHttpParams = {
            token
        };

        return this.restHelper.get<ResponseDTO<boolean>>(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL + '/checkPasswordRecoveryToken', params)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public executePasswordRecovery(form: PasswordRecoveryExecutionDTO): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL))
            throw new Error('INTERNAL_LOGIN_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<boolean>>(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL + '/executePasswordRecovery', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public executeChangeUserPassword(form: ChangePasswordUserDTO): Observable<ResponseDTO<any>> {
        if (isEmpty(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL))
            throw new Error('GESTIONE_UTENTI_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<any>>(this.appConfig.environment.GESTIONE_UTENTI_BASE_URL + '/changeUserPassword', form)
            .pipe(
                tap(_response => this.sdkHttpLoaderService.hideLoader()),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public isRegistrazioneAttiva(): Observable<boolean> {
        return this.restHelper.get<any>(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL + '/isUserRegistrationActive')
            .pipe(
                map((response: any) => {
                    this.sdkHttpLoaderService.hideLoader()
                    return response.data == '1';
                }),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    this.sdkHttpLoaderService.hideLoader();
                    return throwError(() => error);
                })
            );
    }

    public executeCheckMToken(form: CheckMTokenDTO): Observable<ResponseDTO<boolean>> {
        if (isEmpty(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL))
            throw new Error('INTERNAL_LOGIN_BASE_URL empty');

        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

        return this.restHelper.post<ResponseDTO<boolean>>(this.appConfig.environment.INTERNAL_LOGIN_BASE_URL + '/checkMToken', form)
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