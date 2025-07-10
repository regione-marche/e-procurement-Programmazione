import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app-commons.constants';
import { DettaglioUfficioStoreService } from '../../../components/archivio/archivio-uffici/dettaglio-ufficio-store.service';
import { UfficioInsertForm } from '../../../models/uffici/uffici.model';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../services/tabellati/tabellati.service';
import { CommonValidationUtilsService } from '../../../services/utils/common-validation-utils.service';

export interface ListaUfficiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    codice?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
}

@Injectable()
export class UfficioProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: ListaUfficiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('UfficioProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteUfficio(args.codice, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailUfficio(args);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-archivio-uffici') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-ufficio') {
            return this.goUpdate(args.codice);
        } else if (args.buttonCode === 'back-to-dettaglio-ufficio') {
            return this.detailUfficio(args);
        } else if (args.buttonCode === 'save-ufficio') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const stazioneAppaltante: string = args.stazioneAppaltante;
            const setUpdateState: Function = args.setUpdateState;
            const codice: string = args.codice;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                const valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    const request: UfficioInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                    if (!isEmpty(codice)) {
                        // Update
                        request.id = codice;
                    }

                    const inserisciUfficioFactory = this.inserisciUfficioFactory(request);
                    return this.requestHelper.begin(inserisciUfficioFactory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            this.dettaglioUfficioStoreService.clear();
                            this.dettaglioUfficioStoreService.codice = codice || result;

                            const params: IDictionary<any> = {
                                codice: codice || result
                            };
                            this.routerService.navigateToPage('dettaglio-ufficio-page', params);
                            return {
                                ...result,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            };
                        }),
                        catchError((error: any, caught: Observable<any>) => {
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
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_UFFICI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private deleteUfficio(codice: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteUfficioFactory(codice);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteUfficioFactory(codice: string) {
        return () => {
            return this.tabellatiService.deleteUfficio(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codice);
        }
    }

    private detailUfficio(args: ListaUfficiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioUfficioStoreService.clear();
        this.dettaglioUfficioStoreService.codice = args.codice;
        let params: IDictionary<any> = {
            codice: args.codice
        };
        this.routerService.navigateToPage('dettaglio-ufficio-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-archivio-uffici-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuovo-ufficio-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: ListaUfficiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-archivio-uffici-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(codice: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codice
        };
        this.routerService.navigateToPage('modifica-ufficio-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string): UfficioInsertForm {
        let request: UfficioInsertForm = this.getDefaultTecnicoInsertForm(stazioneAppaltante);
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

    private getDefaultTecnicoInsertForm(stazioneAppaltante: string): UfficioInsertForm {
        return {
            stazioneAppaltante
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: UfficioInsertForm): UfficioInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput) && !isUndefined(field.data)) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: UfficioInsertForm): UfficioInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: UfficioInsertForm): UfficioInsertForm {
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

    private inserisciUfficioFactory(insertForm: UfficioInsertForm) {
        return () => {
            return this.tabellatiService.creaUfficio(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get commonValidationUtilsService(): CommonValidationUtilsService { return this.injectable(CommonValidationUtilsService) }

    private get dettaglioUfficioStoreService(): DettaglioUfficioStoreService { return this.injectable(DettaglioUfficioStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
