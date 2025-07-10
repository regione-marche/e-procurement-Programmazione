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
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app-commons.constants';
import {
    DettaglioStazioneAppaltanteStoreService,
} from '../../../components/archivio/archivio-stazioni-appaltanti/dettaglio-stazione-appaltante-store.service';
import {
    UtenteStazioneAppaltanteStoreService,
} from '../../../components/archivio/archivio-stazioni-appaltanti/utenti-stazione-appaltante/utente-stazione-appaltante-store.service';
import {
    StazioneAppaltanteInsertForm,
    StazioneAppaltanteUpdateForm,
} from '../../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../services/tabellati/tabellati.service';
import { CommonValidationUtilsService } from '../../../services/utils/common-validation-utils.service';

export interface ArchivioStazioniAppaltantiProviderArgs extends IDictionary<any> {
    codice?: string;
    syscon?: number;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
}

@Injectable()
export class ArchivioStazioniAppaltantiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ArchivioStazioniAppaltantiProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteSa(args.codice, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailSa(args);
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-archivio-stazioni-appaltanti') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-stazione-appaltante') {
            return this.goUpdate(args.codice);
        } else if (args.buttonCode === 'back-to-dettaglio-stazione-appaltante') {
            return this.detailSa(args);
        } else if (args.buttonCode === 'save-stazione-appaltante') {
            return this.saveStazioneAppaltante(args);
        } else if (args.buttonCode === 'back-to-search') {
            return this.backRicerca();
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (args.buttonCode === 'go-to-update-utenti-stazione-appaltante') {
            return this.updateUtentiSA(args);
        } else if (args.buttonCode === 'back-to-utenti-stazione-appaltante') {
            return this.detailUtentiSA(args);
        } else if (args.buttonCode === 'go-to-update-utente-stazione-appaltante') {
            return this.updateUtenteSA(args)
        } else if (args.buttonCode === 'back-to-dettaglio-utente-stazione-appaltante') {
            return this.detailUtenteSA(args)
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private deleteSa(codice: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getdeleteSaFactory(codice);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getdeleteSaFactory(codice: string) {
        return () => {
            return this.tabellatiService.deleteStazioneAppaltante(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codice);
        }
    }

    private detailSa(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioStazioneAppaltanteStoreService.clear();
        this.dettaglioStazioneAppaltanteStoreService.codice = args.codice;
        let params: IDictionary<any> = {
            codice: args.codice
        };
        this.routerService.navigateToPage('dettaglio-stazione-appaltante-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuova-stazione-appaltante-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-archivio-stazioni-appaltanti-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(codice: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codice
        };
        this.routerService.navigateToPage('modifica-stazione-appaltante-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backRicerca(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-archivio-stazioni-appaltanti-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private saveStazioneAppaltante(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {
        const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        const messagesPanel: HTMLElement = args.messagesPanel;
        const setUpdateState: Function = args.setUpdateState;
        const codice: string = args.codice;
        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            let valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: StazioneAppaltanteInsertForm = this.populateRequest(formBuilderConfig);

                let impresaFactory: Function = undefined;

                let update: boolean = false;

                if (!isEmpty(codice)) {
                    // Update
                    request.codein = codice;
                    impresaFactory = this.updateStazioneAppaltanteFactory(request);
                    update = true;
                } else {
                    impresaFactory = this.insertStazioneAppaltanteFactory(request);
                }

                return this.requestHelper.begin(impresaFactory, messagesPanel).pipe(
                    map((result: any) => {

                        setUpdateState(false);

                        const updatedCodice: string = update === true ? request.codein : result.data;

                        if (update === false) {
                            // pulisco la ricerca perche' l'utente puo' aver inserito campi non presenti nei risultati del filtro
                            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER, undefined));
                        }

                        this.dettaglioStazioneAppaltanteStoreService.clear();
                        this.dettaglioStazioneAppaltanteStoreService.codice = updatedCodice;

                        let params: IDictionary<any> = {
                            codice: updatedCodice
                        };
                        this.routerService.navigateToPage('dettaglio-stazione-appaltante-page', params);
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
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): StazioneAppaltanteInsertForm {
        let request: StazioneAppaltanteInsertForm = this.getDefaultStazioneAppaltanteForm()
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

    private getDefaultStazioneAppaltanteForm(): StazioneAppaltanteInsertForm {
        return {};
    }

    private elaborateOne(field: SdkFormBuilderField, request: StazioneAppaltanteInsertForm): StazioneAppaltanteInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput) && field.data != null) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else if (field.type === 'AUTOCOMPLETE') {
                if (field.data != null) {
                    let item: SdkAutocompleteItem = get(field, 'data');
                    set(request, field.mappingOutput, item._key);
                }
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: StazioneAppaltanteInsertForm): StazioneAppaltanteInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: StazioneAppaltanteInsertForm): StazioneAppaltanteInsertForm {
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

    private insertStazioneAppaltanteFactory(insertForm: StazioneAppaltanteInsertForm) {
        return () => {
            return this.tabellatiService.insertStazioneAppaltante(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    private updateStazioneAppaltanteFactory(insertForm: StazioneAppaltanteUpdateForm) {
        return () => {
            return this.tabellatiService.updateStazioneAppaltante(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    private updateUtentiSA(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codice: args.codice
        };
        this.routerService.navigateToPage('modifica-utenti-stazione-appaltante-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private detailUtentiSA(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codice: args.codice
        };
        this.routerService.navigateToPage('utenti-stazione-appaltante-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateUtenteSA(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            syscon: args.syscon,
            codice: args.codice
        };
        this.routerService.navigateToPage('modifica-utente-stazione-appaltante-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private detailUtenteSA(args: ArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.utenteStazioneAppaltanteStoreService.clear();
        this.utenteStazioneAppaltanteStoreService.syscon = args.syscon;
        this.utenteStazioneAppaltanteStoreService.codice = args.codice;

        let params: IDictionary<any> = {
            syscon: args.syscon,
            codice: args.codice
        };
        this.routerService.navigateToPage('dettaglio-utente-stazione-appaltante-page', params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get dettaglioStazioneAppaltanteStoreService(): DettaglioStazioneAppaltanteStoreService { return this.injectable(DettaglioStazioneAppaltanteStoreService) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get commonValidationUtilsService(): CommonValidationUtilsService { return this.injectable(CommonValidationUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get utenteStazioneAppaltanteStoreService(): UtenteStazioneAppaltanteStoreService { return this.injectable(UtenteStazioneAppaltanteStoreService) }

    // #endregion

}
