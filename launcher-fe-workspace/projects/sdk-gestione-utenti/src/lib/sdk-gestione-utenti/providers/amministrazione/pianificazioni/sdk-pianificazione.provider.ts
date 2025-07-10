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

import {
    SdkDettaglioPianificazioneStoreService,
} from '../../../components/amministrazione/pianificazioni/sdk-dettaglio-pianificazione/sdk-dettaglio-pianificazione-store.service';
import { WQuartzDTO, WQuartzEditDTO } from '../../../model/amministrazione.model';
import { ResponseDTO } from '../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';
import { GestionePianificazioniService } from '../../../services/gestione-pianificazioni.service';
import { GestioneUtentiValidationUtilsService } from '../../../utils/gestione-utenti-validation-utils.service';

export interface SdkPianificazioneProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    item?: WQuartzDTO
}

@Injectable()
export class SdkPianificazioneProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkPianificazioneProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkPianificazioneProviderArgs >>>', args);

        if (args.buttonCode === 'save-pianificazione') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let item: WQuartzDTO = args.item;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    // genero l'oggetto di richiesta
                    let form: WQuartzEditDTO = this.populateRequest(formBuilderConfig, item);

                    return this.gestionePianificazioniService.updatePianificazione(form).pipe(
                        map((result: ResponseDTO<WQuartzDTO>) => {

                            if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-PIANIFICAZIONE.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                this.sdkDettaglioPianificazioneStoreService.clear();
                                this.sdkDettaglioPianificazioneStoreService.codApp = item.codApp;
                                this.sdkDettaglioPianificazioneStoreService.beanId = item.beanId;

                                let params: IDictionary<any> = {
                                    codApp: item.codApp,
                                    beanId: item.beanId,
                                    done: 'Y'
                                };
                                this.routerService.navigateToPage('dettaglio-pianificazione-page', params);
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
                                        message: 'SDK-PIANIFICAZIONE.VALIDATORS.ERRORE-GENERICO',
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, item: WQuartzDTO): WQuartzEditDTO {
        let request: WQuartzEditDTO = this.getDefaultForm(item);
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

    private getDefaultForm(item: WQuartzDTO): WQuartzEditDTO {
        return {
            codApp: item.codApp,
            beanId: item.beanId
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: WQuartzEditDTO): WQuartzEditDTO {
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

    private elaborateSection(field: SdkFormBuilderField, request: WQuartzEditDTO): WQuartzEditDTO {
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

    private elaborateGroup(field: SdkFormBuilderField, request: WQuartzEditDTO): WQuartzEditDTO {
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

    private get gestionePianificazioniService(): GestionePianificazioniService { return this.injectable(GestionePianificazioniService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    private get sdkDettaglioPianificazioneStoreService(): SdkDettaglioPianificazioneStoreService { return this.injectable(SdkDettaglioPianificazioneStoreService) }

    // #endregion

}
