import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkCheckboxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { each, filter, get, isEmpty, isEqual, isObject, isUndefined, map as mapArray, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../app-commons.constants';
import {
    UtenteStazioneAppaltanteStoreService,
} from '../../components/archivio/archivio-stazioni-appaltanti/utenti-stazione-appaltante/utente-stazione-appaltante-store.service';
import { UserDTO, UserEditDTO } from '../../models/user/user.model';
import { ResponseDTO } from '../../models/utils/utils.model';
import { UserService } from '../../services/user/user.service';
import { CommonValidationUtilsService } from '../../services/utils/common-validation-utils.service';

export interface UtenteStazioneAppaltanteProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    syscon?: number;
    codice?: string;
}

@Injectable()
export class UtenteStazioneAppaltanteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: UtenteStazioneAppaltanteProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('UtenteStazioneAppaltanteProvider >>>', args);

        if (args.buttonCode === 'save-utente') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let codice: string = args.codice;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    // genero l'oggetto di richiesta
                    let form: UserEditDTO = this.populateRequest(formBuilderConfig);

                    let servizio: Observable<ResponseDTO<UserDTO>> = this.userService.updateUser(syscon, form);

                    return servizio.pipe(
                        map((result: ResponseDTO<UserDTO>) => {

                            if (result.done === Constants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-UTENTE.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                this.utenteStazioneAppaltanteStoreService.clear();
                                this.utenteStazioneAppaltanteStoreService.syscon = result.response.syscon;
                                this.utenteStazioneAppaltanteStoreService.codice = codice;

                                let params: IDictionary<any> = {
                                    syscon: result.response.syscon,
                                    codice
                                };
                                this.routerService.navigateToPage('dettaglio-utente-stazione-appaltante-page', params);
                            }

                            return {
                                ...result.response,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            };
                        }),
                        catchError((error: any, caught: Observable<any>) => {
                            if (error != null && error.error != null) {
                                if (error.error.error != null) {
                                    let message: SdkMessagePanelTranslate = {
                                        message: 'SDK-UTENTE.VALIDATORS.ERRORE-GENERICO',
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

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): UserEditDTO {
        let request: UserEditDTO = this.getDefaultForm();
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

    private getDefaultForm(): UserEditDTO {
        return {
            denominazione: null,
            username: null
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: UserEditDTO): UserEditDTO {
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

    private elaborateSection(field: SdkFormBuilderField, request: UserEditDTO): UserEditDTO {
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

    private elaborateGroup(field: SdkFormBuilderField, request: UserEditDTO): UserEditDTO {
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

    private get userService(): UserService { return this.injectable(UserService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get commonValidationUtilsService(): CommonValidationUtilsService { return this.injectable(CommonValidationUtilsService) }

    private get utenteStazioneAppaltanteStoreService(): UtenteStazioneAppaltanteStoreService { return this.injectable(UtenteStazioneAppaltanteStoreService) }

    // #endregion

}
