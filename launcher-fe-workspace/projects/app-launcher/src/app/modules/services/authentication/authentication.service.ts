import { Injectable, Injector } from '@angular/core';
import { IHttpParams, SdkBaseService, SdkHttpLoaderService, SdkHttpLoaderType, SdkRestHelperService, SdkRouterService, SdkSessionStorageService } from '@maggioli/sdk-commons';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { HttpErrorResponse } from '@angular/common/http';
import { AppHomeIndexPanel } from '@maggioli/sdk-widgets';
import { environment } from '../../../../environments/environment';
import { ApplicationLoginConfiguration } from '../../models/application.model';
import { SSOTokenInfo, UserAccountForm, UserExistsInfo, UserRegistrationResponse } from '../../models/authentication/authentication.model';
import { ResponseResult } from '../../models/common/common.model';
import { get } from 'lodash-es';
import { Constants } from '../../../app.constants';

@Injectable({ providedIn: 'root' })
export class AuthenticationService extends SdkBaseService {

    constructor(inj: Injector) { super(inj); }

    public checkExistingUser(baseUrl: string, username: string): Observable<UserExistsInfo> {
        const params: IHttpParams = {
            username
        };
        return this.restHelper.get<ResponseResult<UserExistsInfo>>(`${baseUrl}/checkUserExists`, params)
            .pipe(map((result: ResponseResult<UserExistsInfo>) => result.data));
    }

    public userRegistration(baseUrl: string, form: UserAccountForm): Observable<UserRegistrationResponse> {
        return this.restHelper.post<UserRegistrationResponse>(`${baseUrl}/userRegistration`, form)
    }

    public getUrlSPID(baseUrl: string, idProvider: string, callbackUrl: string): Observable<any> {
        const params: IHttpParams = {
            idProvider,
            callbackUrl
        };
        return this.restHelper.get<any>(`${baseUrl}/getInfoSpid`, params);
    }

    public getUrlCIE(baseUrl: string, callbackUrl: string): Observable<any> {
        const params: IHttpParams = {
            callbackUrl
        };
        return this.restHelper.get<any>(`${baseUrl}/getInfoCie`, params);
    }

    public getUrlCNS(baseUrl: string, callbackUrl: string): Observable<any> {
        const params: IHttpParams = {
            callbackUrl
        };
        return this.restHelper.get<any>(`${baseUrl}/getInfoCns`, params);
    }

    public getUserSPID(baseUrl: string, authId: string): Observable<any> {
        const params: IHttpParams = {
            authId,
            codein: '-',
            provider: 'CIE'
        };
        return this.restHelper.get<any>(`${baseUrl}/getSpidUser`, params);
    }

    public isRichiestaAssistenzaAttiva(baseUrl: string): Observable<boolean> {
        return this.restHelper.get<any>(`${baseUrl}/isRichiestaAssistenzaAttiva`)
            .pipe(
                map((result: any) => {
                    return result.response;
                })
            );
    }

    public retrieveAuthenticationToken(baseUrl: string, authorizationCode: string): Observable<ResponseResult<SSOTokenInfo>> {
        const params: IHttpParams = {
            authorizationCode
        };
        return this.restHelper.get<ResponseResult<SSOTokenInfo>>(`${baseUrl}/retrieveAuthenticationToken`, params);
    }

    public isRegistrazioneAttiva(baseUrl: string): Observable<boolean> {
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        return this.restHelper.get<any>(`${baseUrl}/isUserRegistrationActive`)
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

    public getApplicationLoginConfiguration(moduloSelezionato: AppHomeIndexPanel): ApplicationLoginConfiguration {

        const panelCode: string = moduloSelezionato.panelCode;

        const appLoginConfig: ApplicationLoginConfiguration = get(environment.PANELS_CONFIGURATION, panelCode);

        if (appLoginConfig == null) {
            this.sdkRouterService.navigateToPage('home-page');
        }

        return appLoginConfig;
    }

    public clearAuthInfo(): void {
        this.sdkSessionStorageService.removeItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
        this.sdkSessionStorageService.removeItem(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    // #endregion

}