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

import { SdkDettaglioUtenteStoreService } from '../components/sdk-dettaglio-utente/sdk-dettaglio-utente-store.service';
import { UserDTO, UserEditDTO, UserInsertDTO } from '../model/gestione-utenti.model';
import { ResponseDTO } from '../model/lib.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../services/gestione-utenti.service';
import { GestioneUtentiValidationUtilsService } from '../utils/gestione-utenti-validation-utils.service';

export interface SdkUtenteProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    syscon?: number;
}

@Injectable()
export class SdkUtenteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkUtenteProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkUtenteProvider >>>', args);

        if (args.buttonCode === 'save-utente') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let isUpdate = syscon != null;

                    // genero l'oggetto di richiesta
                    let form: UserInsertDTO | UserEditDTO = this.populateRequest(formBuilderConfig, isUpdate);

                    let servizio: Observable<ResponseDTO<UserDTO>>;
                    if (isUpdate) {
                        servizio = this.gestioneUtentiService.updateUser(syscon, form as UserEditDTO);
                    } else {
                        servizio = this.gestioneUtentiService.insertUser(form as UserInsertDTO);
                    }

                    return servizio.pipe(
                        map((result: ResponseDTO<UserDTO>) => {

                            if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
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

                                let messages: Array<string> = result.messages;

                                this.sdkDettaglioUtenteStoreService.clear();
                                this.sdkDettaglioUtenteStoreService.syscon = result.response.syscon;

                                let params: IDictionary<any> = {
                                    syscon: result.response.syscon
                                };

                                if (messages != null) {
                                    if (messages.includes('GESTIONE_UTENTE_USER_EDIT_AMMINISTRAZIONI_WITHOUT_GESTIONE_COMPLETA')) {
                                        // warning di modifica SA senza gestione utenti completa
                                        params = {
                                            ...params,
                                            warning: 'GESTIONE_UTENTE_USER_EDIT_AMMINISTRAZIONI_WITHOUT_GESTIONE_COMPLETA'
                                        };
                                    }

                                    if (messages.includes('GESTIONE_UTENTE_USER_DELEGATO_ALL_SA')) {
                                        // warning utente inserito da delegato con tutte le stazioni appaltanti associate
                                        params = {
                                            ...params,
                                            warning: 'GESTIONE_UTENTE_USER_DELEGATO_ALL_SA'
                                        }
                                    }
                                }

                                this.routerService.navigateToPage('dettaglio-utente-page', params);
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
                                } else if (error.error.done === SdkGestioneUtentiConstants.RESPONSE_N && error.error.messages != null && error.error.messages.length > 0) {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-UTENTE.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, update: boolean): UserInsertDTO | UserEditDTO {
        let request: UserInsertDTO | UserEditDTO = this.getDefaultForm(update);
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

    private getDefaultForm(update: boolean): UserInsertDTO | UserEditDTO {
        if (update) {
            return {
                denominazione: null,
                username: null
            }
        } else {
            return {
                denominazione: null,
                username: null,
                password: null,
                confermaPassword: null
            }
        }
    }

    private elaborateOne(field: SdkFormBuilderField, request: UserInsertDTO | UserEditDTO): UserInsertDTO | UserEditDTO {
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

    private elaborateSection(field: SdkFormBuilderField, request: UserInsertDTO | UserEditDTO): UserInsertDTO | UserEditDTO {
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

    private elaborateGroup(field: SdkFormBuilderField, request: UserInsertDTO | UserEditDTO): UserInsertDTO | UserEditDTO {
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

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    private get sdkDettaglioUtenteStoreService(): SdkDettaglioUtenteStoreService { return this.injectable(SdkDettaglioUtenteStoreService) }

    // #endregion

}
