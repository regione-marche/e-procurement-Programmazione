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
    SdkDettaglioConfigurazioneStoreService,
} from '../../../components/amministrazione/configurazioni/sdk-dettaglio-configurazione/sdk-dettaglio-configurazione-store.service';
import { WConfigDTO, WConfigEditDTO } from '../../../model/amministrazione.model';
import { ResponseDTO } from '../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';
import { GestioneConfigurazioniService } from '../../../services/gestione-configurazioni.service';
import { GestioneUtentiValidationUtilsService } from '../../../utils/gestione-utenti-validation-utils.service';

export interface SdkConfigurazioneProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    item?: WConfigDTO
}

@Injectable()
export class SdkConfigurazioneProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkConfigurazioneProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkConfigurazioneProviderArgs >>>', args);

        if (args.buttonCode === 'save-configurazione') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let item: WConfigDTO = args.item;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    // genero l'oggetto di richiesta
                    let form: WConfigEditDTO = this.populateRequest(formBuilderConfig, item);

                    return this.gestioneConfigurazioniService.updateConfigurazione(form).pipe(
                        map((result: ResponseDTO<WConfigDTO>) => {

                            if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-CONFIGURAZIONE.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                this.sdkDettaglioConfigurazioneStoreService.clear();
                                this.sdkDettaglioConfigurazioneStoreService.codApp = item.codApp;
                                this.sdkDettaglioConfigurazioneStoreService.chiave = item.chiave;

                                let params: IDictionary<any> = {
                                    codApp: item.codApp,
                                    chiave: item.chiave
                                };
                                this.routerService.navigateToPage('dettaglio-configurazione-page', params);
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
                                        message: 'SDK-CONFIGURAZIONE.VALIDATORS.ERRORE-GENERICO',
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, item: WConfigDTO): WConfigEditDTO {
        let request: WConfigEditDTO = this.getDefaultForm(item);
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

    private getDefaultForm(item: WConfigDTO): WConfigEditDTO {
        return {
            codApp: item.codApp,
            chiave: item.chiave
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: WConfigEditDTO): WConfigEditDTO {
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

    private elaborateSection(field: SdkFormBuilderField, request: WConfigEditDTO): WConfigEditDTO {
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

    private elaborateGroup(field: SdkFormBuilderField, request: WConfigEditDTO): WConfigEditDTO {
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

    private get gestioneConfigurazioniService(): GestioneConfigurazioniService { return this.injectable(GestioneConfigurazioniService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    private get sdkDettaglioConfigurazioneStoreService(): SdkDettaglioConfigurazioneStoreService { return this.injectable(SdkDettaglioConfigurazioneStoreService) }

    // #endregion

}
