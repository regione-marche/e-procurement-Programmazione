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
    SdkDettaglioServerPostaStoreService,
} from '../../../components/amministrazione/server-posta/sdk-dettaglio-server-posta/sdk-dettaglio-server-posta-store.service';
import { WMailDTO, WMailEditDTO } from '../../../model/amministrazione.model';
import { ResponseDTO } from '../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';
import { GestioneServerPostaService } from '../../../services/gestione-server-posta.service';
import { GestioneUtentiValidationUtilsService } from '../../../utils/gestione-utenti-validation-utils.service';

export interface SdkServerPostaProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    item?: WMailDTO
}

@Injectable()
export class SdkServerPostaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkServerPostaProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkServerPostaProviderArgs >>>', args);

        if (args.buttonCode === 'save-server-posta') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let item: WMailDTO = args.item;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let update: boolean = item != null;

                    // genero l'oggetto di richiesta
                    let form: WMailEditDTO = this.populateRequest(formBuilderConfig, item);

                    let servizio: Observable<ResponseDTO<WMailDTO>>;
                    if (update == true) {
                        servizio = this.gestioneServerPostaService.updateServerPosta(form);
                    } else {
                        servizio = this.gestioneServerPostaService.insertServerPosta(form);
                    }

                    return servizio.pipe(
                        map((result: ResponseDTO<WMailDTO>) => {

                            if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-SERVER-POSTA.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                this.sdkDettaglioServerPostaStoreService.clear();
                                this.sdkDettaglioServerPostaStoreService.idCfg = result.response.idCfg;

                                let params: IDictionary<any> = {
                                    idCfg: result.response.idCfg
                                };
                                this.routerService.navigateToPage('dettaglio-server-posta-page', params);
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
                                        message: 'SDK-SERVER-POSTA.VALIDATORS.ERRORE-GENERICO',
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
                                            message: `SDK-SERVER-POSTA.VALIDATORS.${one}`
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, item: WMailDTO): WMailEditDTO {
        let request: WMailEditDTO = this.getDefaultForm(item);
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

    private getDefaultForm(item: WMailDTO): WMailEditDTO {
        if (item != null) {
            return {
                idCfg: item.idCfg,
                mailMittente: null,
                server: null
            };
        } else {
            return {
                idCfg: null,
                mailMittente: null,
                server: null
            };
        }
    }

    private elaborateOne(field: SdkFormBuilderField, request: WMailEditDTO): WMailEditDTO {
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

    private elaborateSection(field: SdkFormBuilderField, request: WMailEditDTO): WMailEditDTO {
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

    private elaborateGroup(field: SdkFormBuilderField, request: WMailEditDTO): WMailEditDTO {
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

    private get gestioneServerPostaService(): GestioneServerPostaService { return this.injectable(GestioneServerPostaService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    private get sdkDettaglioServerPostaStoreService(): SdkDettaglioServerPostaStoreService { return this.injectable(SdkDettaglioServerPostaStoreService) }

    // #endregion

}
