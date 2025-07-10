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
import { each, filter, get, isEmpty, isEqual, isObject, isUndefined, map as mapArray, set, toString } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import {
    SdkDettaglioTabellatoStoreService,
} from '../../../components/amministrazione/tabellati/sdk-dettaglio-tabellato/sdk-dettaglio-tabellato-store.service';
import { Tab0DTO, Tab1DTO, Tab2DTO, Tab3DTO, Tab5DTO, TabXDTO } from '../../../model/amministrazione.model';
import { ResponseDTO } from '../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';
import { GestioneTabellatiService } from '../../../services/gestione-tabellati.service';
import { GestioneUtentiValidationUtilsService } from '../../../utils/gestione-utenti-validation-utils.service';

export interface SdkTabellatoProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    provenienza?: string;
    codiceTabellato?: string;
    identificativo?: string;
}

@Injectable()
export class SdkTabellatoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkTabellatoProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkTabellatoProviderArgs >>>', args);

        if (args.buttonCode === 'save-tabellato') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let provenienza: string = args.provenienza;
            let codiceTabellato: string = args.codiceTabellato;
            let identificativo: string = args.identificativo;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    let update: boolean = identificativo != null;

                    // genero l'oggetto di richiesta
                    let form: IDictionary<any> = this.populateRequest(formBuilderConfig);

                    let servizio: Observable<ResponseDTO<TabXDTO>>;
                    if (update == true) {
                        if (provenienza == 'TAB0') {
                            let updateForm: Tab0DTO = {
                                ...form,
                                tab0cod: codiceTabellato,
                                tab0tip: identificativo
                            };
                            servizio = this.gestioneTabellatiService.updateTab0(codiceTabellato, identificativo, updateForm);
                        } else if (provenienza == 'TAB1') {
                            let updateForm: Tab1DTO = {
                                ...form,
                                tab1cod: codiceTabellato,
                                tab1tip: +identificativo
                            };
                            servizio = this.gestioneTabellatiService.updateTab1(codiceTabellato, identificativo, updateForm);
                        } else if (provenienza == 'TAB2') {
                            let updateForm: Tab2DTO = {
                                ...form,
                                tab2cod: codiceTabellato,
                                tab2tip: identificativo
                            };
                            servizio = this.gestioneTabellatiService.updateTab2(codiceTabellato, identificativo, updateForm);
                        } else if (provenienza == 'TAB3') {
                            let updateForm: Tab3DTO = {
                                ...form,
                                tab3cod: codiceTabellato,
                                tab3tip: identificativo
                            };
                            servizio = this.gestioneTabellatiService.updateTab3(codiceTabellato, identificativo, updateForm);
                        } else if (provenienza == 'TAB5') {
                            let updateForm: Tab5DTO = {
                                ...form,
                                tab5cod: codiceTabellato,
                                tab5tip: identificativo
                            };
                            servizio = this.gestioneTabellatiService.updateTab5(codiceTabellato, identificativo, updateForm);
                        }
                    } else {
                        if (provenienza == 'TAB0') {
                            let insertForm: Tab0DTO = {
                                tab0cod: codiceTabellato,
                                tab0tip: '',
                                ...form
                            };
                            servizio = this.gestioneTabellatiService.insertTab0(codiceTabellato, insertForm);
                        } else if (provenienza == 'TAB1') {
                            let insertForm: Tab1DTO = {
                                tab1cod: codiceTabellato,
                                tab1tip: null,
                                ...form,
                            };
                            servizio = this.gestioneTabellatiService.insertTab1(codiceTabellato, insertForm);
                        } else if (provenienza == 'TAB2') {
                            let insertForm: Tab2DTO = {
                                tab2cod: codiceTabellato,
                                tab2tip: '',
                                ...form
                            };
                            servizio = this.gestioneTabellatiService.insertTab2(codiceTabellato, insertForm);
                        } else if (provenienza == 'TAB3') {
                            let insertForm: Tab3DTO = {
                                tab3cod: codiceTabellato,
                                tab3tip: '',
                                ...form
                            };
                            servizio = this.gestioneTabellatiService.insertTab3(codiceTabellato, insertForm);
                        } else if (provenienza == 'TAB5') {
                            let insertForm: Tab5DTO = {
                                tab5cod: codiceTabellato,
                                tab5tip: '',
                                ...form
                            };
                            servizio = this.gestioneTabellatiService.insertTab5(codiceTabellato, insertForm);
                        }
                    }

                    return servizio.pipe(
                        map((result: ResponseDTO<TabXDTO>) => {

                            if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                                let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                    let message: SdkMessagePanelTranslate = {
                                        message: `SDK-TABELLATO.VALIDATORS.${one}`
                                    };
                                    return message;
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                this.sdkDettaglioTabellatoStoreService.clear();
                                this.sdkDettaglioTabellatoStoreService.provenienza = provenienza;
                                this.sdkDettaglioTabellatoStoreService.codiceTabellato = codiceTabellato;
                                let identificativoUpdated: string;
                                if (provenienza == 'TAB0') {
                                    let dto: Tab0DTO = result.response as Tab0DTO;
                                    identificativoUpdated = dto.tab0tip;
                                } else if (provenienza == 'TAB1') {
                                    let dto: Tab1DTO = result.response as Tab1DTO;
                                    identificativoUpdated = toString(dto.tab1tip);
                                } else if (provenienza == 'TAB2') {
                                    let dto: Tab2DTO = result.response as Tab2DTO;
                                    identificativoUpdated = dto.tab2tip;
                                } else if (provenienza == 'TAB3') {
                                    let dto: Tab3DTO = result.response as Tab3DTO;
                                    identificativoUpdated = dto.tab3tip;
                                } else if (provenienza == 'TAB5') {
                                    let dto: Tab5DTO = result.response as Tab5DTO;
                                    identificativoUpdated = dto.tab5tip;
                                }

                                this.sdkDettaglioTabellatoStoreService.identificativo = identificativoUpdated;

                                let params: IDictionary<any> = {
                                    provenienza,
                                    codiceTabellato,
                                    identificativo: identificativoUpdated
                                };
                                this.routerService.navigateToPage('dettaglio-tabellato-page', params);
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
                                        message: 'SDK-TABELLATO.VALIDATORS.ERRORE-GENERICO',
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
                                            message: `SDK-TABELLATO.VALIDATORS.${one}`
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
        return {
        };
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

    private get gestioneTabellatiService(): GestioneTabellatiService { return this.injectable(GestioneTabellatiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    private get sdkDettaglioTabellatoStoreService(): SdkDettaglioTabellatoStoreService { return this.injectable(SdkDettaglioTabellatoStoreService) }

    // #endregion

}
