import { Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SdkBaseService,
    SdkDateHelper,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkProvider,
    SdkRouterService,
    SdkSessionStorageService,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkCheckboxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { AppHomeIndexPanel } from '@maggioli/sdk-widgets';
import { TranslateService } from '@ngx-translate/core';
import { each, filter, get, has, isEmpty, isEqual, isFunction, isObject, isUndefined, map as mapArray, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../app.constants';
import { UserAccountForm, UserRegistrationResponse } from '../models/authentication/authentication.model';
import { DynamicValue } from '../models/common/common.model';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { FileUtilsService } from '../services/utils/file-utils.service';
import {
    RegistrazioneUtentePdfValidationUtilsService,
} from '../services/utils/registrazione-utente-pdf-validation-utils.service';
import { RegistrazioneUtenteValidationUtilsService } from '../services/utils/registrazione-utente-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class RegistrazioneUtenteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<any> {
        this.logger.debug('RegistrazioneUtenteProvider >>>', args);

        if (args.buttonCode === 'back-to-home-page') {
            const setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            this.routerService.navigateToPage('home-page');

        } else if (args.buttonCode === 'save-utente') {

            this.sdkMessagePanelService.clear(args.messagesPanel);
            const setUpdateState: Function = args.setUpdateState;
            const request: UserAccountForm = this.getRequest(args);
            if (request != null) {
                const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
                const panelCode: string = moduloSelezionato.panelCode;

                if (panelCode === 'W9-PT' || panelCode === 'W9-AEC' || panelCode === '229') {
                    request.applicativiSelezionati = [
                        panelCode
                    ];
                }

                let registrationBaseUrl: string = args.registrationBaseUrl;
                const registrazionePubblica: boolean = args.registrazionePubblica;
                if (!!registrazionePubblica == true) {
                    registrationBaseUrl = registrationBaseUrl.replace('/protected/', '/public/');
                }
                delete request['trattamentoDatiPersonali'];
                this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
                return this.authenticationService.userRegistration(registrationBaseUrl, request).pipe(
                    map((result: UserRegistrationResponse) => {
                        if (isFunction(setUpdateState)) {
                            setUpdateState(false);
                        }
                        this.sdkHttpLoaderService.hideLoader();
                        this.routerService.navigateToPage('registrazione-utente-completata-page', { stato: '1' });
                        return {
                            ...result
                        };
                    }),
                    catchError((error: any, caught: Observable<any>) => {
                        this.sdkHttpLoaderService.hideLoader();
                        args.captchaResetSubject$.next(true);
                        this.manageError(error, args.messagesPanel);
                        this.scrollToMessagePanel(args.messagesPanel);
                        return throwError(() => error);
                    })
                );
            }
        } else if (args.buttonCode === 'get-request-for-pdf') {
            const request: UserAccountForm = this.getRequest(args, true);
            return new Observable((ob: Observer<UserAccountForm>) => {
                ob.next(request);
                ob.complete();
            });
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private getRequest(args: IDictionary<any>, pdf: boolean = false): UserAccountForm {
        const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        const messagesPanel: HTMLElement = args.messagesPanel;
        const registrazionePubblica: boolean = args.registrazionePubblica;
        let serverCaptchaEnabled: boolean = args.serverCaptchaEnabled;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            let captchaSolution: string = args.captchaSolution;
            if (serverCaptchaEnabled && isEmpty(captchaSolution)) {
                this.sdkMessagePanelService.showError(args.messagesPanel, [
                    {
                        message: 'CAPTCHA.ERROR'
                    }
                ]);
                this.scrollToMessagePanel(args.messagesPanel);
            } else {

                // controllo la validita' del modello
                let valid: boolean = pdf === true ? this.registrazioneUtentePdfValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel) : this.registrazioneUtenteValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    const request: UserAccountForm = this.populateRequest(formBuilderConfig, captchaSolution);
                    const codiceFiscale: string = registrazionePubblica == true ? request.codiceFiscaleLogin : request.codiceFiscale;
                    request.fileName = Constants.REGISTRAZIONE_UTENTE_FILE_NAME(codiceFiscale, request.fileExt);
                    delete request.fileExt;
                    return request;
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
        return null;
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, captchaSolution: string): UserAccountForm {
        let request: UserAccountForm = this.getDefaultForm(captchaSolution);
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

    private getDefaultForm(captchaSolution: string): UserAccountForm {
        let request: UserAccountForm = {
            captchaSolution
        };

        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: UserAccountForm, dynamicMappingOutput?: string): UserAccountForm {
        if (isObject(field)) {
            if (!isEmpty(dynamicMappingOutput)) {
                let list: Array<DynamicValue> = get(request, dynamicMappingOutput);
                if (isUndefined(list)) {
                    list = new Array();
                }
                if (field.type === 'COMBOBOX') {
                    if (field.data != null) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data.key ? field.data.key : null
                        };
                        list.push(dynamicValue);
                    }
                } else {
                    if (field.data != null) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data
                        };
                        list.push(dynamicValue);
                    }
                }
                set(request, dynamicMappingOutput, list);
            } else if (!isEmpty(field.mappingOutput)) {
                if (field.type === 'COMBOBOX') {
                    if (field.data != null) {
                        set(request, field.mappingOutput, field.data.key);
                    }
                } else if (field.type === 'DOCUMENT') {
                    if (field.file != null) {
                        set(request, field.mappingOutput, field.file);
                        const ext: string = this.fileUtilsService.getExtFromFileName(field.fileName)
                        set(request, 'fileExt', ext);
                    }
                } else if (field.type === 'CHECKBOX') {
                    if (field.data != null) {
                        const data: Array<SdkCheckboxItem> = field.data;
                        let checkeds: Array<string> = mapArray(filter(data, one => one.checked === true), one => one.code);
                        set(request, field.mappingOutput, checkeds);
                    }
                } else if (field.type === 'DATEPICKER') {
                    if (field.data != null) {
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    }
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else {
                    if (field.data != null) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: UserAccountForm): UserAccountForm {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request);
            } else {
                let dynamicMappingOutput: string = field.mappingOutput && one.dynamicField === true ? field.mappingOutput : undefined;
                request = this.elaborateOne(one, request, dynamicMappingOutput);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: UserAccountForm): UserAccountForm {
        if (field.visible !== false) {
            let newRestObjects: Array<any>;
            if (!isEmpty(field.mappingOutput)) {
                newRestObjects = get(request, field.mappingOutput);
                if (isUndefined(newRestObjects)) {
                    newRestObjects = new Array();
                }
            }

            if (isObject(newRestObjects)) {
                each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration) => {
                    let singleIterationObject: any = {};
                    each(group.fields, (one: SdkFormBuilderField) => {
                        if (one.type === 'FORM-SECTION') {
                            singleIterationObject = this.elaborateSection(one, singleIterationObject);
                        } else if (one.type === 'FORM-GROUP') {
                            singleIterationObject = this.elaborateGroup(one, singleIterationObject);
                        } else {
                            singleIterationObject = this.elaborateOne(one, singleIterationObject);
                        }
                    });
                    newRestObjects.push(singleIterationObject);
                });
                set(request, field.mappingOutput, newRestObjects);
            } else {
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
            }
        }
        return request;
    }

    private manageError(error: any, messagesPanel: HTMLElement): void {
        let httpError: UserRegistrationResponse = error.error as UserRegistrationResponse;

        let errorData: string = 'UNEXPECTED-ERROR';
        let errorDataParameters: any;
        let passwordErrors: Array<string>;

        if (httpError != null) {

            if (httpError.errorData != null) {
                errorData = get(httpError, 'errorData');
            }

            if (has(httpError, 'errorDataParameters')) {
                errorDataParameters = get(httpError, 'errorDataParameters');
            }

            if (httpError.passwordErrors != null) {
                passwordErrors = httpError.passwordErrors;
            }

        }

        let message: string = `ERRORS.${errorData}`;
        if (!isEmpty(errorDataParameters)) {
            let translatedData: string = this.translateService.instant(errorData, errorDataParameters);
            let message = translatedData
            this.sdkMessagePanelService.showError(messagesPanel, [
                {
                    message
                }
            ]);
        } else {

            if (passwordErrors != null) {
                let pwdErrors: Array<any> = passwordErrors.map(e => {
                    return {
                        message: this.translateService.instant(`REGISTRAZIONE-UTENTE.ERRORS.${e}`)
                    };
                });
                this.sdkMessagePanelService.showError(messagesPanel, pwdErrors);
            } else {
                let translatedData: string = this.translateService.instant(message);
                if (translatedData === message) {
                    message = errorData;
                }

                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message
                    }
                ]);
            }
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get registrazioneUtenteValidationUtilsService(): RegistrazioneUtenteValidationUtilsService { return this.injectable(RegistrazioneUtenteValidationUtilsService) }

    private get registrazioneUtentePdfValidationUtilsService(): RegistrazioneUtentePdfValidationUtilsService { return this.injectable(RegistrazioneUtentePdfValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    private get fileUtilsService(): FileUtilsService { return this.injectable(FileUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }


    // #endregion
}
