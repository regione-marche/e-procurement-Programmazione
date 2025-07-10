import { Injectable, Injector } from '@angular/core';
import { FilterObject, IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';

import { HttpResponse } from '@angular/common/http';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { SdkCheckboxItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration, SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { each, filter, findIndex, get, isEmpty, isEqual, isFunction, isObject, isUndefined, map as mapArray, set } from 'lodash-es';
import { Observable, Observer, catchError, map, throwError } from 'rxjs';
import { DefinizioneReportEditDTO, JsonResponseDTO, ProfiloCheckDTO, ResponseDTO, ResponseListaDTO, ResultReportQueryParamsDTO, UfficioIntestatarioCheckDTO } from '../model/lib.model';
import { WRicParamDTO } from '../model/lista-params.model';
import { WRicercheDTO } from '../model/lista-report.model';
import { SdkGestioneReportConstants } from '../sdk-gestione-report.constants';
import { SdkDettaglioReportStoreService } from '../sdk-gestione-report.exports';
import { GestioneParametriService } from '../services/gestione-parametri.service';
import { GestioneReportService } from '../services/gestione-report.service';
import { ArrayUtils } from '../utils/array-utils.service';
import { GestioneReportsValidationUtilsService } from '../utils/gestione-reports-validation-utils.service';

export interface SdkReportProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    idRicerca?: number;
    fromCreazione?: string;
    schedula?: string;
    maxNumRecordConfig?: number;
    totalCount?: number;
}

@Injectable({providedIn: 'root'})
export class SdkGestioneReportProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    //Provider per insert e update

    public run(args: any): Observable<IDictionary<any>> {
        this.logger.debug('SdkReportsProviderArgs >>>', args);

        //#region definizione-report
        if (args.buttonCode === 'conferma-definizione-report') {

            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let idRicerca: number = args.idRicerca;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            let defSql: string = args.defSql;
            let isFormatted: string = args.isFormatted;

            this.sdkMessagePanelService.clear(messagesPanel);

            // genero l'oggetto di richiesta
            let form: DefinizioneReportEditDTO = {};//this.populateRequest(formBuilderConfig, isUpdate);

            if(isEmpty(defSql)){
                //Aggiungo messaggio d'errore
                let message: SdkMessagePanelTranslate = {
                    message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.CAMPO-DEFSQL-OBBLIGATORIO'
                };
                this.sdkMessagePanelService.showError(messagesPanel, [message]);
            }

            //Nel caso in cui la definizione SQL del form non rispetti le specifiche, restituisco errore.
            if(!this.gestioneReportsValidationUtilsService.checkModifyingOperations(form.defSql)){
                //Aggiungo messaggio d'errore
                let message: SdkMessagePanelTranslate = {
                    message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.GESTIONE_REPORTS_DEFSQL_OPERATION_NOT_ALLOWED'
                };
                this.sdkMessagePanelService.showError(messagesPanel, [message]);
            }

            if (defSql) {

                form.idRicerca = idRicerca;
                form.defSql = defSql;

                let servizio: Observable<ResponseDTO<DefinizioneReportEditDTO>> = this.gestioneReportService.updateDefSqlReport(form, syscon, idProfilo);

                return servizio.pipe(
                    map((result: ResponseDTO<DefinizioneReportEditDTO>) => {

                        let reload: boolean = true;

                        if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                            let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                let message: SdkMessagePanelTranslate = {
                                    message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                };
                                return message;
                            })
                            this.sdkMessagePanelService.showError(messagesPanel, messages);
                            this.scrollToMessagePanel(messagesPanel);

                            reload = false;
                        }
                        else {
                            setUpdateState(false);

                            let messages: Array<string> = result.messages;

                            this.sdkDettaglioReportStoreService.clear();
                            this.sdkDettaglioReportStoreService.idRicerca = result.response.idRicerca;

                            let params: IDictionary<any> = {
                                idRicerca: result.response.idRicerca
                            };

                            if (messages != null) {
                                if (messages.includes('GESTIONE_REPORTS_RESPONSE_NOT_DONE')) {
                                    // warning di response nulla
                                    params = {
                                        ...params,
                                        warning: 'GESTIONE_REPORTS_RESPONSE_NOT_DONE'
                                    };
                                }

                                if (messages.includes('GESTIONE_REPORTS_RESPONSE_DEFSQL_NOT_FOUND')) {
                                    // warning di definizione SQL non trovato nel report cercato.
                                    params = {
                                        ...params,
                                        warning: 'GESTIONE_REPORTS_RESPONSE_DEFSQL_NOT_FOUND'
                                    }
                                }
                            }

                            sessionStorage.setItem('isFormatted', isFormatted);
                            this.routerService.navigateToPage('definizione-report-page', params);
                        }

                        return {
                            ...result.response,
                            defaultFormBuilderConfig,
                            formBuilderConfig,
                            reload
                        };
                    }),
                    catchError((error: any, caught: Observable<any>) => {
                        if (error != null && error.error != null) {
                            if (error.error.error != null) {
                                let message: SdkMessagePanelTranslate = {
                                    message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
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
                                        message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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
        //#endregion
        //#region save-dati-generali
        else if (args.buttonCode === 'save-dati-generali'){
            
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.sdkGestioneReportsValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let isUpdate = true;

                    //Sistemare passaggio form completo da qui.
                    // genero l'oggetto di richiesta con update = true perché sto salvando la modifica di dati generali
                    let form: WRicercheDTO = this.populateRequest(formBuilderConfig, isUpdate);

                    let servizio: Observable<ResponseDTO<WRicercheDTO>>;
                    servizio = this.gestioneReportService.updateDatiGeneraliReport(form as WRicercheDTO, syscon, idProfilo);
                    
                    return servizio.pipe(
                        map((result: ResponseDTO<WRicercheDTO>) => {

                            if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                let messages: Array<string> = result.messages;

                                this.sdkDettaglioReportStoreService.clear();
                                this.sdkDettaglioReportStoreService.idRicerca = result.response.idRicerca;

                                let params: IDictionary<any> = {
                                    idRicerca: result.response.idRicerca
                                };

                                if (messages != null) {
                                    if (messages.includes('GESTIONE_REPORTS_RESPONSE_NOT_DONE')) {
                                        // warning di response nulla
                                        params = {
                                            ...params,
                                            warning: 'GESTIONE_REPORTS_RESPONSE_NOT_DONE'
                                        };
                                    }

                                    if (messages.includes('GESITONE_REPORTS_UPDATE_FALSE')) {
                                        // warning di update fallito
                                        params = {
                                            ...params,
                                            warning: 'GESITONE_REPORTS_UPDATE_FALSE'
                                        }
                                    }
                                }

                                this.routerService.navigateToPage('dati-generali-report-page', params);
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
                                        message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
                                        messageParams: {
                                            value: error.error.error
                                        }
                                    };
                                    this.sdkMessagePanelService.showError(messagesPanel, [
                                        message
                                    ]);
                                } else if (error.error.done === SdkGestioneReportConstants.RESPONSE_N && error.error.messages != null && error.error.messages.length > 0) {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);
                                } else {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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
        //#endregion
        //#region save-nuovo-param
        else if (args.buttonCode === 'save-nuovo-parametro'){
            
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validità del modello
                let valid: boolean = this.sdkGestioneReportsValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let isUpdate = true;

                    // genero l'oggetto di richiesta con update = true
                    let form: WRicParamDTO = this.populateRequest(formBuilderConfig, isUpdate);
                    form.idRicerca = this.sdkDettaglioReportStoreService.idRicerca;

                    if(!isEmpty(form) && form.tipo === 'M' && !this.checkFormatMenuField(form.menuField)){
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.MENU-FORMATO-ERRATO'
                            }
                        ]);
                    }
                    else {

                        let servizio: Observable<ResponseDTO<WRicParamDTO>>;
                        servizio = this.gestioneParametriService.addNuovoParametro(form as WRicParamDTO, syscon, idProfilo);
                        
                        return servizio.pipe(
                            map((result: ResponseDTO<WRicParamDTO>) => {

                                if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);
                                } else {
                                    setUpdateState(false);

                                    let messages: Array<string> = result.messages;

                                    this.sdkDettaglioReportStoreService.clear();
                                    this.sdkDettaglioReportStoreService.idRicerca = result.response.idRicerca;
                                    this.sdkDettaglioReportStoreService.codice = result.response.codice;

                                    let params: IDictionary<any> = {
                                        idRicerca: result.response.idRicerca,
                                        codice: result.response.codice,
                                        menuField: form.menuField
                                    };

                                    if (messages != null) {
                                        if (messages.includes('GESTIONE_REPORTS_RESPONSE_NOT_DONE')) {
                                            // warning di response nulla
                                            params = {
                                                ...params,
                                                warning: 'GESTIONE_REPORTS_RESPONSE_NOT_DONE'
                                            };
                                        }

                                        if (messages.includes('GESITONE_REPORTS_UPDATE_FALSE')) {
                                            // warning di update fallito
                                            params = {
                                                ...params,
                                                warning: 'GESITONE_REPORTS_UPDATE_FALSE'
                                            }
                                        }
                                    }

                                    this.routerService.navigateToPage('parametri-report-page', params);
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
                                            message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
                                            messageParams: {
                                                value: error.error.error
                                            }
                                        };
                                        this.sdkMessagePanelService.showError(messagesPanel, [
                                            message
                                        ]);
                                    } else if (error.error.done === SdkGestioneReportConstants.RESPONSE_N && error.error.messages != null && error.error.messages.length > 0) {
                                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                            let message: SdkMessagePanelTranslate = {
                                                message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                            };
                                            return message;
                                        })
                                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                                        this.scrollToMessagePanel(messagesPanel);
                                    } else {
                                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                            let message: SdkMessagePanelTranslate = {
                                                message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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
                    }
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
        //#endregion
        //#region save-dett-param
        else if (args.buttonCode === 'save-dettaglio-parametro'){

            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let idRicerca: number = args.idRicerca;
            let codice: string = args.codice;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.sdkGestioneReportsValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let isUpdate = true;

                    // genero l'oggetto di richiesta con update = true perché sto salvando la modifica di dati generali
                    let form: WRicParamDTO = this.populateRequest(formBuilderConfig, isUpdate);
                    form.idRicerca = idRicerca;
                    form.codice = codice;

                    if(!isEmpty(form) && form.tipo === 'M' && !this.checkFormatMenuField(form.menuField)){
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.MENU-FORMATO-ERRATO'
                            }
                        ]);
                    }
                    else {

                        let servizio: Observable<ResponseDTO<WRicParamDTO>>;
                        servizio = this.gestioneParametriService.updateDettaglioParametroReport(form as WRicParamDTO, syscon, idProfilo);
                        
                        return servizio.pipe(
                            map((result: ResponseDTO<WRicParamDTO>) => {

                                if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);
                                } else {
                                    setUpdateState(false);

                                    let messages: Array<string> = result.messages;

                                    this.sdkDettaglioReportStoreService.clear();
                                    this.sdkDettaglioReportStoreService.idRicerca = result.response.idRicerca;
                                    this.sdkDettaglioReportStoreService.codice = result.response.codice;

                                    let params: IDictionary<any> = {
                                        idRicerca: result.response.idRicerca,
                                        codice: result.response.codice
                                    };

                                    if (messages != null) {
                                        if (messages.includes('GESTIONE_REPORTS_RESPONSE_NOT_DONE')) {
                                            // warning di response nulla
                                            params = {
                                                ...params,
                                                warning: 'GESTIONE_REPORTS_RESPONSE_NOT_DONE'
                                            };
                                        }

                                        if (messages.includes('GESITONE_REPORTS_UPDATE_FALSE')) {
                                            // warning di update fallito
                                            params = {
                                                ...params,
                                                warning: 'GESITONE_REPORTS_UPDATE_FALSE'
                                            }
                                        }
                                    }

                                    this.routerService.navigateToPage('dettaglio-parametro-page', params);
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
                                            message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
                                            messageParams: {
                                                value: error.error.error
                                            }
                                        };
                                        this.sdkMessagePanelService.showError(messagesPanel, [
                                            message
                                        ]);
                                    } else if (error.error.done === SdkGestioneReportConstants.RESPONSE_N && error.error.messages != null && error.error.messages.length > 0) {
                                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                            let message: SdkMessagePanelTranslate = {
                                                message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                            };
                                            return message;
                                        })
                                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                                        this.scrollToMessagePanel(messagesPanel);
                                    } else {
                                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                            let message: SdkMessagePanelTranslate = {
                                                message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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
                    }
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
        else if (args.buttonCode === 'save-profili-report') {
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let idRicerca: number = args.idRicerca;
            let listaProfili: Array<ProfiloCheckDTO> = args.listaProfili;
            let listaProfiliToSave: Array<string> = mapArray(filter(listaProfili, (one: ProfiloCheckDTO) => one.checked === true), (profiloToSave: ProfiloCheckDTO) => profiloToSave.codice);
            let idProfilo: string = args.idProfilo;

            return this.gestioneReportService.setListaProfiliReport(idRicerca, syscon, idProfilo, listaProfiliToSave).pipe(
                map((result: ResponseDTO<boolean>) => {

                    if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                            };
                            return message;
                        })
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                        this.scrollToMessagePanel(messagesPanel);
                    } else {
                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            idRicerca: this.sdkDettaglioReportStoreService.idRicerca,
                            syscon: syscon
                        };
                        this.routerService.navigateToPage('profili-report-page', params);
                    }

                    return {
                        response: result.response
                    };
                }),
                catchError((error: any, caught: Observable<any>) => {
                    if (error != null && error.error != null) {
                        if (error.error.error != null) {
                            let message: SdkMessagePanelTranslate = {
                                message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
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
                                    message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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

        }
        //#endregion
        //#region save-nuovo-report
        else if (args.buttonCode === 'save-nuovo-report'){
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            let fromCreazione: string = args.fromCreazione;
            let schedula: string = args.schedula;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.sdkGestioneReportsValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let isUpdate = true;

                    // genero l'oggetto di richiesta con update = true perché sto salvando il nuovo report
                    let form: WRicercheDTO = this.populateRequest(formBuilderConfig, isUpdate);

                    let servizio: Observable<ResponseDTO<WRicercheDTO>>;
                    servizio = this.gestioneReportService.insertNewReport(form as WRicercheDTO, syscon, idProfilo);
                    
                    return servizio.pipe(
                        map((result: ResponseDTO<WRicercheDTO>) => {

                            if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                let messages: Array<string> = result.messages;

                                this.sdkDettaglioReportStoreService.clear();
                                this.sdkDettaglioReportStoreService.idRicerca = result.response.idRicerca;

                                let params: IDictionary<any> = {
                                    idRicerca: result.response.idRicerca,
                                    fromCreazione: fromCreazione,
                                    schedula: schedula
                                };

                                if (messages != null) {
                                    if (messages.includes('GESTIONE_REPORTS_RESPONSE_NOT_DONE')) {
                                        // warning di response nulla
                                        params = {
                                            ...params,
                                            warning: 'GESTIONE_REPORTS_RESPONSE_NOT_DONE'
                                        };
                                    }

                                    if (messages.includes('GESITONE_REPORTS_UPDATE_FALSE')) {
                                        // warning di update fallito
                                        params = {
                                            ...params,
                                            warning: 'GESITONE_REPORTS_UPDATE_FALSE'
                                        }
                                    }
                                }

                                this.routerService.navigateToPage('dati-generali-report-page', params);
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
                                        message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
                                        messageParams: {
                                            value: error.error.error
                                        }
                                    };
                                    this.sdkMessagePanelService.showError(messagesPanel, [
                                        message
                                    ]);
                                } else if (error.error.done === SdkGestioneReportConstants.RESPONSE_N && error.error.messages != null && error.error.messages.length > 0) {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);
                                } else {
                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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
        //#endregion
        //#region save-uffint-report
        else if (args.buttonCode === 'save-uffici-intestatari-report'){
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let idRicerca: number = args.idRicerca;
            let listaUffInt: Array<UfficioIntestatarioCheckDTO> = args.listaUffInt;
            let idProfilo: string = args.idProfilo;

            let listaUffIntToSave: Array<string> = mapArray(
                filter(listaUffInt, (one: UfficioIntestatarioCheckDTO) => one.checked === true),
                (saToSave: UfficioIntestatarioCheckDTO) => saToSave.codice
            );

            return this.gestioneReportService.setListaUfficiIntestatariReport(idRicerca, syscon, idProfilo, listaUffIntToSave).pipe(
                map((result: ResponseListaDTO<boolean>) => {

                    if (result.done === SdkGestioneReportConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                            };
                            return message;
                        })
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                        this.scrollToMessagePanel(messagesPanel);
                    } else {
                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            syscon,
                            idRicerca: idRicerca
                        };
                        this.routerService.navigateToPage('uffici-intestatari-report-page', params);
                    }

                    return {
                        response: result.response
                    };
                }),
                catchError((error: any, caught: Observable<any>) => {
                    if (error != null && error.error != null) {
                        if (error.error.error != null) {
                            let message: SdkMessagePanelTranslate = {
                                message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.ERRORE-GENERICO',
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
                                    message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
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
        }
        //#endregion
        //#region sel-curr-uffint
        else if (args.buttonCode === 'select-current-filtered-uffici-intestatari') {

            let listaUffInt: Array<UfficioIntestatarioCheckDTO> = args.listaUffInt;
            let currentFilter: FilterObject<UfficioIntestatarioCheckDTO> = args.currentFilter;

            if (listaUffInt != null && currentFilter != null && currentFilter.filteredValue != null && currentFilter.filteredValue.length > 0) {
                each(currentFilter.filteredValue, (one: UfficioIntestatarioCheckDTO) => {
                    let saIndex: number = findIndex(listaUffInt, (k: UfficioIntestatarioCheckDTO) => k.codice === one.codice);
                    if (saIndex != null && saIndex > -1) {
                        listaUffInt[saIndex].checked = true;
                    }
                });
            }

            let returnObj: IDictionary<any> = {
                ...args,
                listaUffIntEdit: listaUffInt
            };

            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next(returnObj);
                ob.complete();
            });
        }
        //#endregion
        //#region unsel-curr-uffint
        else if (args.buttonCode === 'unselect-current-filtered-uffici-intestatari') {
            let listaStazioniUffInt: Array<UfficioIntestatarioCheckDTO> = args.listaUffInt;
            let currentFilter: FilterObject<UfficioIntestatarioCheckDTO> = args.currentFilter;

            if (listaStazioniUffInt != null && currentFilter != null && currentFilter.filteredValue != null && currentFilter.filteredValue.length > 0) {
                each(currentFilter.filteredValue, (one: UfficioIntestatarioCheckDTO) => {
                    let saIndex: number = findIndex(listaStazioniUffInt, (k: UfficioIntestatarioCheckDTO) => k.codice === one.codice);
                    if (saIndex != null && saIndex > -1) {
                        listaStazioniUffInt[saIndex].checked = false;
                    }
                });
            }
            let returnObj: IDictionary<any> = {
                ...args,
                listaStazioniUffIntEdit: listaStazioniUffInt
            };

            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next(returnObj);
                ob.complete();
            });
        }
        //#endregion
        //#region export-report-docx
        else if(args.buttonCode === 'export-report-docx'){
            let factory: Function;
            let setUpdateState: Function = args.setUpdateState;
            let columnNames: Array<string> = args.columnNames;
            let values: Array<any> = args.values;
            let idRicerca: number = args.idRicerca;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            let maxNumRecordConfig: number = args.maxNumRecordConfig;
            let totalCount: number = args.totalCount;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            factory = this.exportListaReportDocx(columnNames, values, idRicerca, syscon, idProfilo);
            
            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result :ResponseListaDTO<HttpResponse<any>>)=>{ 

                if(totalCount > 0){
                    if(totalCount > maxNumRecordConfig){
                        this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                            {
                                message: 'LISTA-RISULTATO-REPORT.MAX-ROW'
                            }
                        ])
                        this.scrollToMessagePanel(args.messagesPanel);
                
                    }

                    let fileName: string = 'Risultato_report_'+ idRicerca.toString() +'.docx';
                    let body: any = result.response.body;

                    ArrayUtils.download(fileName, body, 'vnd.openxmlformats-officedocument.wordprocessingml.document');

                } else{
                    this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                        {
                            message: 'LISTA-RISULTATO-REPORT.NO-ELEM'
                        }
                    ])
                    this.scrollToMessagePanel(args.messagesPanel);
                }
                
            });
        }
        //#endregion
        //#region export-report-csv
        else if (args.buttonCode === 'export-report-csv'){
            
            let factory: Function;
            let setUpdateState: Function = args.setUpdateState;
            let columnNames: Array<string> = args.columnNames;
            let values: Array<any> = args.values;
            let idRicerca: number = args.idRicerca;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            let maxNumRecordConfig: number = args.maxNumRecordConfig;
            let totalCount: number = args.totalCount;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            factory = this.exportListaReportCsv(columnNames, values, idRicerca, syscon, idProfilo);
            
            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result :ResponseListaDTO<HttpResponse<any>>)=>{   
                if(totalCount > 0){
                    if(totalCount > maxNumRecordConfig){
                        this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                            {
                                message: 'LISTA-RISULTATO-REPORT.MAX-ROW'
                            }
                        ])
                        this.scrollToMessagePanel(args.messagesPanel);
                    }

                    let csvFile = document.createElement('a');
        
                    csvFile.href = 'data:text/csv;charset=utf-8,' + encodeURI(result.response.body);
                    csvFile.target = '_blank';
                    
                    csvFile.download = 'Risultato_report_'+ idRicerca.toString() +'.csv';
                    
                    csvFile.click();
                } else{
                    this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                        {
                            message: 'LISTA-RISULTATO-REPORT.NO-ELEM'
                        }
                    ])
                    this.scrollToMessagePanel(args.messagesPanel);
                }
                
            });
        }
        //#endregion
        //#region export-report-pdf
        else if (args.buttonCode === 'export-report-pdf'){
            
            let factory: Function;
            let setUpdateState: Function = args.setUpdateState;
            let columnNames: Array<string> = args.columnNames;
            let values: Array<any> = args.values;
            let idRicerca: number = args.idRicerca;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            let maxNumRecordConfig: number = args.maxNumRecordConfig;
            let totalCount: number = args.totalCount;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            factory = this.exportListaReportPdf(columnNames, values, idRicerca, syscon, idProfilo);
            
            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result :ResponseListaDTO<HttpResponse<any>>)=>{ 

                if(totalCount > 0){
                    if(totalCount > maxNumRecordConfig){
                        this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                            {
                                message: 'LISTA-RISULTATO-REPORT.MAX-ROW'
                            }
                        ])
                        this.scrollToMessagePanel(args.messagesPanel);
                
                    }

                    let fileName: string = 'Risultato_report_'+ idRicerca.toString() +'.pdf';
                    let body: any = result.response.body;

                    ArrayUtils.download(fileName, body, 'pdf');

                } else{
                    this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                        {
                            message: 'LISTA-RISULTATO-REPORT.NO-ELEM'
                        }
                    ])
                    this.scrollToMessagePanel(args.messagesPanel);
                }
                
            });
        }
        //#region export-report-json
        else if (args.buttonCode === 'export-report-json'){

            let factory: Function;
            let setUpdateState: Function = args.setUpdateState;
            let columnNames: Array<string> = args.columnNames;
            let values: Array<any> = args.values;
            let idRicerca: number = args.idRicerca;
            let idProfilo: string = args.idProfilo;
            let syscon: number = args.syscon;
            let maxNumRecordConfig: number = args.maxNumRecordConfig;
            let totalCount: number = args.totalCount;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            factory = this.exportRisultatoReportJson(columnNames, values, idRicerca, syscon, idProfilo);
            
            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result :ResponseListaDTO<Array<JsonResponseDTO>>)=>{ 

                if(totalCount > 0){
                    if(totalCount > maxNumRecordConfig){
                        this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                            {
                                message: 'LISTA-RISULTATO-REPORT.MAX-ROW'
                            }
                        ])
                        this.scrollToMessagePanel(args.messagesPanel);
                
                    }

                    let fileName: string = 'Risultato_report_'+ idRicerca.toString() +'.json';
                    let body: any = result.response;

                    //ArrayUtils.download(fileName, body, 'json');
                    const jsonString = JSON.stringify(body, null, 2);
                    const blob = new Blob([jsonString], { type: 'application/json' });
                    const url = window.URL.createObjectURL(blob);

                    const a = document.createElement('a');
                    a.href = url;
                    a.download = fileName;
                    a.click();
                    window.URL.revokeObjectURL(url);

                } else{
                    this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                        {
                            message: 'LISTA-RISULTATO-REPORT.NO-ELEM'
                        }
                    ])
                    this.scrollToMessagePanel(args.messagesPanel);
                }
                
            });
        }
        //#endregion
        //#region export-report-xlsx
        else if (args.buttonCode === 'export-report-xlsx'){
            
            let factory: Function;
            let setUpdateState: Function = args.setUpdateState;
            let columnNames: Array<string> = args.columnNames;
            let values: Array<any> = args.values;
            let idRicerca: number = args.idRicerca;
            let syscon: number = args.syscon;
            let idProfilo: string = args.idProfilo;
            let maxNumRecordConfig: number = args.maxNumRecordConfig;
            let totalCount: number = args.totalCount;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            factory = this.exportRisultatoReportXlsx(columnNames, values, idRicerca, syscon, idProfilo);
            
            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result :ResponseListaDTO<HttpResponse<any>>)=>{ 

                if(totalCount > 0){
                    if(totalCount > maxNumRecordConfig){
                        this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                            {
                                message: 'LISTA-RISULTATO-REPORT.MAX-ROW'
                            }
                        ])
                        this.scrollToMessagePanel(args.messagesPanel);
                
                    }

                    let fileName: string = 'Risultato_report_'+ idRicerca.toString() +'.xlsx';
                    let body: any = result.response;

                    ArrayUtils.download(fileName, body, 'xlsx');

                } else{
                    this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                        {
                            message: 'LISTA-RISULTATO-REPORT.NO-ELEM'
                        }
                    ])
                    this.scrollToMessagePanel(args.messagesPanel);
                }
                
            });
        }


        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    //#region Private 

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, update: boolean) {
        let request: any = this.getDefaultForm(update);
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

    private getDefaultForm(update: boolean) {
        return {};
    }

    private elaborateOne(field: SdkFormBuilderField, request) {
        if (isObject(field) && !isEmpty(field.mappingOutput) && (!isUndefined(field.data) || !isUndefined(field.file))) {
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
                    set(request, field.mappingOutput, data);
                }
            } else if (field.type === 'DOCUMENT') {
                if (field.file != null) {
                    set(request, field.mappingOutput, field.file);
                    set(request, 'nomefile', field.fileName);                   
                }
            } else if (field.type === 'TEXTMODAL') {
                
                    set(request, field.mappingOutput, field.data);              
                
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request) {
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

    private elaborateGroup(field: SdkFormBuilderField, request) {
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

    private exportListaReportCsv(columnNames: Array<string>, values:Array<any>, idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            let params: ResultReportQueryParamsDTO = {
                columnNames: columnNames,
                values: values,
                idRicerca: idRicerca,
                syscon: +syscon
            }

            return this.gestioneReportService.exportListaReportCsv(params, idProfilo);
        }
    }

    private exportListaReportRtf(columnNames: Array<string>, values:Array<any>, idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            let params: ResultReportQueryParamsDTO = {
                columnNames: columnNames,
                values: values,
                idRicerca: idRicerca,
                syscon: +syscon
            }

            return this.gestioneReportService.exportListaReportRtf(params, idProfilo);
        }
    }

    private exportListaReportDocx(columnNames: Array<string>, values:Array<any>, idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            let params: ResultReportQueryParamsDTO = {
                columnNames: columnNames,
                values: values,
                idRicerca: idRicerca,
                syscon: +syscon
            }

            return this.gestioneReportService.exportListaReportDocx(params, idProfilo);
        }
    }

    private exportListaReportPdf(columnNames: Array<string>, values:Array<any>, idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            let params: ResultReportQueryParamsDTO = {
                columnNames: columnNames,
                values: values,
                idRicerca: idRicerca,
                syscon: +syscon
            }

            return this.gestioneReportService.exportListaReportPdf(params, idProfilo);
        }
    }

    private exportRisultatoReportJson(columnNames: Array<string>, values:Array<any>, idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            let params: ResultReportQueryParamsDTO = {
                columnNames: columnNames,
                values: values,
                idRicerca: idRicerca,
                syscon: syscon
            }

            return this.gestioneReportService.exportListaReportJson(params, idProfilo);
        }
    }

    private exportRisultatoReportXlsx(columnNames: Array<string>, values:Array<any>, idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            let params: ResultReportQueryParamsDTO = {
                columnNames: columnNames,
                values: values,
                idRicerca: idRicerca,
                syscon: syscon
            }

            return this.gestioneReportService.exportListaReportXlsx(params, idProfilo);
        }
    }

    private checkFormatMenuField(menuField: string): boolean {

    // Divido la stringa in parti usando il separatore '|'
    const parti = menuField.split('|');

    // Verifico che ci sia almeno una parte e che nessuna sia vuota
    if (parti.length === 0 || parti.some(p => p.trim() === '')) {
        return false;
    }

    // Regex per verificare il formato di ogni parte: numero,descrizione
    const formatoCorretto = /^\d+,.+$/;

    // Controllo ogni parte
    for (const parte of parti) {
        if (!formatoCorretto.test(parte)) {
            return false;
        }
    }

    // Tutte le parti sono nel formato corretto e posso ritornare true
    return true;
}

    //#endregion
   

    // #region Getters / Setters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }
     
    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneReportsValidationUtilsService(): GestioneReportsValidationUtilsService { return this.injectable(GestioneReportsValidationUtilsService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get gestioneParametriService(): GestioneParametriService { return this.injectable(GestioneParametriService) }

    private get sdkGestioneReportsValidationUtilsService(): GestioneReportsValidationUtilsService { return this.injectable(GestioneReportsValidationUtilsService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}




