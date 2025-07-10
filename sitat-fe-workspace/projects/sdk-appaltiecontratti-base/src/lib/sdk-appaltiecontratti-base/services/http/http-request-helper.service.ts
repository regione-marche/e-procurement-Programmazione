import { HttpErrorResponse } from '@angular/common/http';
import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkRedirectService,
    SdkRestHelperService,
    SdkSessionStorageService,
} from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get, isEmpty, isObject } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { AuthenticationTokenInfo, RefreshTokenRequest } from '../../model/authentication.model';
import { SdkAppaltiecontrattiBaseConstants } from '../../sdk-appaltiecontratti-base.constants';


@Injectable({ providedIn: 'root' })
export class HttpRequestHelper extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public begin(serviceFunction: Function, errorPanel?: HTMLElement, loading: boolean = true, scrollView: boolean = true): Observable<any> {
        return new Observable((ob: Observer<any>) => {
            if (loading === true) {
                this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
            }
            serviceFunction().subscribe((response: any) => {
                if (response != null) {
                    this._completed(ob, response, loading);
                }
            }, (err: HttpErrorResponse) => {
                if (err.status == 401 && err.error.errorData == '2') {
                    // token scaduto
                    this._refreshToken(serviceFunction, ob, loading, scrollView, errorPanel);
                } else if (err.status == 401 && err.error.errorData == '1') {
                    // token non valido
                    this._unsuccessfulRefresh(ob, err, 'Invalid token', loading);
                } else {
                    // errore della chiamata (no token)
                    this._unsuccessful(ob, err, errorPanel, loading, scrollView);
                }
            });
        });
    }

    // #region Private

    private _completed = (ob: Observer<any>, response: any, loading: boolean) => {
        if (loading === true) {
            this.sdkHttpLoaderService.hideLoader();
        }
        ob.next(response);
        ob.complete();
    }

    private _unsuccessful = (ob: Observer<any>, error: HttpErrorResponse, errorPanel: HTMLElement, loading: boolean, scrollView: boolean) => {
        if (loading === true) {
            this.sdkHttpLoaderService.hideLoader();
        }

        setTimeout(() => {

            let httpError: any = error.error as any;

            let errorData: string = 'UNEXPECTED-ERROR';
            if (isObject(httpError) && !isEmpty(get(httpError, 'errorData'))) {
                errorData = get(httpError, 'errorData');
            }
            let errorDataParameters: any;
            if (isObject(httpError) && !isEmpty(get(httpError, 'errorDataParameters'))) {
                errorDataParameters = get(httpError, 'errorDataParameters');
            }
            let message: string = `ERRORS.${errorData}`;
            if (!isEmpty(errorDataParameters)) {
                let translatedData: string = this.translateService.instant(errorData, errorDataParameters);
                let message = translatedData
                this.sdkMessagePanelService.showError(errorPanel, [
                    {
                        message
                    }
                ], scrollView);
                ob.error(error);
                ob.complete();
            } else {
                let translatedData: string = this.translateService.instant(message);
                if (translatedData === message) {
                    message = errorData;
                }

                this.sdkMessagePanelService.showError(errorPanel, [
                    {
                        message
                    }
                ], scrollView);
                ob.error(error);
                ob.complete();
            }
        });

    }

    private _unsuccessfulRefresh = (ob: Observer<any>, error: Error, message: any, loading: boolean) => {
        if (loading === true) {
            this.sdkHttpLoaderService.hideLoader();
        }
        setTimeout(() => {
            this.logger.error(message);
            ob.error(error);
            ob.complete();

            let redirectUrl: string = this.appConfig.environment.APP_HOME_CONTEXT_URL;

            let params: IDictionary<string> = {
                error: SdkAppaltiecontrattiBaseConstants.SESSION_EXPIRED
            };
            this.sdkRedirectService.redirect(redirectUrl, params);
        });
    }

    private _refreshToken = (serviceFunction: Function, ob: Observer<any>, loading: boolean, scrollView: boolean, errorPanel?: HTMLElement) => {
        setTimeout(() => {
            let appCode: string = this.sdkSessionStorageService.getItem<string>(SdkAppaltiecontrattiBaseConstants.AUTHENTICATION_APP_CODE, SdkAppaltiecontrattiBaseConstants.LOCAL_STORAGE_PREFIX);
            let authTokenInfo: AuthenticationTokenInfo = this.sdkSessionStorageService.getItem<AuthenticationTokenInfo>(SdkAppaltiecontrattiBaseConstants.AUTHENTICATION_TOKEN_INFO, SdkAppaltiecontrattiBaseConstants.LOCAL_STORAGE_PREFIX);
            let body: RefreshTokenRequest = {
                appSecret: this.appConfig.environment.APP_SECRET,
                appCode,
                refreshToken: authTokenInfo.refreshToken
            };
            this.restHelper.post<AuthenticationTokenInfo>(`${this.appConfig.environment.AUTHENTICATION_BASE_URL}/refreshToken`, body).subscribe(
                (response: any) => {
                    let authData: any;
                    if (this.appConfig.environment.LOGIN_MODE === 0) {
                        authData = response;
                    } else {
                        if (isObject(response.data)) {
                            authData = response.data;
                        }
                    }

                    this.sdkSessionStorageService.setItem(SdkAppaltiecontrattiBaseConstants.AUTHENTICATION_TOKEN_INFO, authData, SdkAppaltiecontrattiBaseConstants.LOCAL_STORAGE_PREFIX);
                    this._retry(serviceFunction, ob, loading, scrollView, errorPanel);
                }, (err: any) => {
                    this._unsuccessfulRefresh(ob, err, 'Refresh Token failed', loading);
                }
            );
        });
    }

    private _retry = (serviceFunction: Function, ob: Observer<any>, loading: boolean, scrollView: boolean, errorPanel?: HTMLElement) => {
        serviceFunction().subscribe((response: any) => {
            if (response != null) {
                this._completed(ob, response, loading);
            }
        }, (err: any) => {
            this._unsuccessful(ob, err, errorPanel, loading, scrollView);
        });
    }


    // #endregion

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}