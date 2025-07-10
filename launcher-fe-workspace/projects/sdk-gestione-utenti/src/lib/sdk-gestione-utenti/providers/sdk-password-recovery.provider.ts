import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, SdkAppEnvConfig, SdkBaseService, SdkProvider, SdkRedirectService, SdkRouterService, SDK_APP_CONFIG } from '@maggioli/sdk-commons';
import {
    SdkCheckboxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { each, filter, get, isEmpty, isEqual, isObject, isUndefined, map as mapArray, set } from 'lodash-es';
import { Observable, Observer, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { PasswordRecoveryExecutionDTO, PasswordRecoveryRequestDTO } from '../model/authentication.model';
import { ResponseDTO } from '../model/lib.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';
import { AuthenticationService } from '../services/authentication.service';
import { GestioneUtentiValidationUtilsService } from '../utils/gestione-utenti-validation-utils.service';

export interface SdkPasswordRecoveryProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    currentUrl?: string;
    setUpdateState?: Function;
    token?: string;
    captchaSolution?: string;
    serverCaptchaEnabled?: boolean;
    captchaResetSubject$?: Subject<boolean>
}

@Injectable()
export class SdkPasswordRecoveryProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: SdkPasswordRecoveryProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkPasswordRecoveryProvider >>>', args);

        if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'back-to-home-page') {
            return this.backHome();
        } else if (args.buttonCode === 'richiedi-recupero-password') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let currentUrl: string = args.currentUrl;
            let serverCaptchaEnabled: boolean = args.serverCaptchaEnabled;
            let captchaSolution: string = args.captchaSolution;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                if (serverCaptchaEnabled && isEmpty(captchaSolution)) {
                    this.sdkMessagePanelService.showError(messagesPanel, [
                        {
                            message: 'CAPTCHA.ERROR'
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                } else {
                    // controllo la validita' del modello
                    let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                    if (valid === true) {

                        // genero l'oggetto di richiesta
                        let form: PasswordRecoveryRequestDTO = this.populateRequest(formBuilderConfig) as PasswordRecoveryRequestDTO;

                        form.currentUrl = currentUrl;
                        form.captchaSolution = captchaSolution;

                        return this.authenticationService.requestPasswordRecovery(form).pipe(
                            map((result: ResponseDTO<string>) => {

                                if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {

                                    args.captchaResetSubject$.next(true);

                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-RECUPERO-PASSWORD.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);
                                } else {

                                    if (result.response != null) {

                                        let params: IDictionary<string> = {
                                            email: result.response
                                        };

                                        this.routerService.navigateToPage('recupero-password-inviata-page', params);
                                    } else {
                                        this.routerService.navigateToPage('recupero-password-inviata-page');
                                    }

                                }

                                return {
                                    ...result,
                                    defaultFormBuilderConfig,
                                    formBuilderConfig
                                };
                            }),
                            catchError((error: any, caught: Observable<any>) => {

                                args.captchaResetSubject$.next(true);

                                if (error != null && error.error != null) {
                                    if (error.error.error != null) {
                                        let message: SdkMessagePanelTranslate = {
                                            message: 'SDK-RECUPERO-PASSWORD.VALIDATORS.ERRORE-GENERICO',
                                            messageParams: {
                                                value: error.error.error
                                            }
                                        };
                                        this.sdkMessagePanelService.showError(messagesPanel, [
                                            message
                                        ]);
                                    } else {
                                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                            let message: SdkMessagePanelTranslate = {
                                                message: one
                                            };
                                            return message;
                                        })
                                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    }
                                }
                                this.scrollToMessagePanel(messagesPanel);
                                return throwError(() => error);
                            })
                        );
                    } else {
                        this.scrollToMessagePanel(messagesPanel);
                    }
                }
            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-NON-COMPILATA'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            }
        } else if (args.buttonCode === 'esegui-recupero-password') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let token: string = args.token;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    // genero l'oggetto di richiesta
                    let form: PasswordRecoveryExecutionDTO = this.populateRequest(formBuilderConfig) as PasswordRecoveryExecutionDTO;
                    form.token = token;

                    return this.authenticationService.executePasswordRecovery(form).pipe(
                        map((result: ResponseDTO<boolean>) => {

                            if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-RECUPERO-PASSWORD.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                this.routerService.navigateToPage('esegui-recupero-password-success-page');
                            }

                            return {
                                ...result,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            };
                        }),
                        catchError((error: any, caught: Observable<any>) => {
                            if (error != null && error.error != null) {
                                if (error.error.error != null) {
                                    let message: SdkMessagePanelTranslate = {
                                        message: 'SDK-RECUPERO-PASSWORD.VALIDATORS.ERRORE-GENERICO',
                                        messageParams: {
                                            value: error.error.error
                                        }
                                    };
                                    this.sdkMessagePanelService.showError(messagesPanel, [
                                        message
                                    ]);
                                } else {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: one
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                }
                            }
                            this.scrollToMessagePanel(messagesPanel);
                            return throwError(() => error);
                        })
                    );
                } else {
                    this.scrollToMessagePanel(messagesPanel);
                }
            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-NON-COMPILATA'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            }
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('internal-login-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backHome(): Observable<IDictionary<any>> {
        this.sdkRedirectService.redirect(this.appConfig.environment.APP_LAUNCHER_CONTEXT_URL);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): IDictionary<any> {
        let request: IDictionary<any> = this.getDefaultForm();
        each(formBuilderConfig.fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION') {
                request = this.elaborateSection(field, request);
            } else if (field.type === 'FORM-GROUP') {
                request = this.elaborateGroup(field, request);
            } else {
                request = this.elaborateOne(field, request);
            }
        });
        return request;
    }

    private getDefaultForm(): IDictionary<any> {
        return {};
    }

    private elaborateOne(field: SdkFormBuilderField, request: IDictionary<any>): IDictionary<any> {
        if (isObject(field) && !isEmpty(field.mappingOutput) && !isUndefined(field.data)) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else if (field.type === 'DATEPICKER') {
                if (field.data != null) {
                    let data: Date = get(field, 'data');
                    if (isObject(data)) {
                        set(request, field.mappingOutput, data.getTime());
                    }
                }
            } else if (field.type === 'CHECKBOX') {
                if (field.data != null) {
                    const data: Array<SdkCheckboxItem> = field.data;
                    let checkeds: Array<string> = mapArray(filter(data, one => one.checked === true), one => one.code);
                    set(request, field.mappingOutput, checkeds);
                }
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: IDictionary<any>): IDictionary<any> {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request);
            } else {
                request = this.elaborateOne(one, request);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: IDictionary<any>): IDictionary<any> {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    request = this.elaborateSection(one, request);
                } else if (one.type === 'FORM-GROUP') {
                    request = this.elaborateGroup(one, request);
                } else {
                    request = this.elaborateOne(one, request);
                }
            });
            field.fieldGroups[index] = group;
        });
        return request;
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    // #endregion

}
