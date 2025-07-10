import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, ImpresaEntry } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, head, isEmpty, isEqual, isFunction, isObject, isUndefined, set, size } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import {
    DettaglioImpresaAggiudicatariaStoreService,
} from '../../layout/components/business/fasi/imprese-aggiudicatarie/dettaglio-impresa-aggiudicataria-store.service';
import { FaseImpresaEntry } from '../../models/fasi/elenco-impr-inv-parte.model';
import {
    ImpresaAggiudicatariaInsertForm,
    ImpresaAggiudicatariaRaggEntry,
    ImpreseAggiudicatarieAttoInsertForm,
} from '../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';
import { ImpreseAggiudicatarieService } from '../../services/imprese-aggiudicatarie/imprese-aggiudicatarie.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

export interface ListaImpreseAggiudicatarieAttoProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    tipoDocumento: string;
    numPubb: string;
    campiVisibili: string;
    campiObbligatori: string;
    idGruppo?: number;
    impresa?: ImpresaAggiudicatariaRaggEntry;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    selectedImpresa?: FaseImpresaEntry;
    update?: boolean;
}

@Injectable({ providedIn: 'root' })
export class ListaImpreseAggiudicatarieAttoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaImpreseAggiudicatarieAttoProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteImpresaAggiudicataria(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL' || args.buttonCode === 'back-to-dettaglio-impresa-aggiudicataria') {
            return this.dettaglioImpresaAggiudicataria(args);
        } else if (args.buttonCode === 'back-to-lista-imprese-aggiudicatarie') {
            return this.listaImpreseAggiudicatarie(args);
        } else if (args.buttonCode === 'go-to-update-impresa-aggiudicataria') {
            return this.updateImpresa(args);
        } else if (args.buttonCode === 'save-impresa') {
            return this.saveImpresa(args);
        } else if (args.buttonCode === 'nuova-impresa') {
            return this.nuovaImpresa(args);
        }
        return of(args);
    }

    // #region Private

    private deleteImpresaAggiudicataria(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {
        if (isObject(args.impresa)) {
            let factory = this.deleteFactory(args.codGara, args.numPubb);
            return this.requestHelper.begin(factory, args.messagesPanel);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioImpresaAggiudicataria(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            let impresa: ImpresaAggiudicatariaRaggEntry = args.impresa;

            this.dettaglioImpresaAggiudicatariaStoreService.clear();
            this.dettaglioImpresaAggiudicatariaStoreService.codGara = args.codGara;
            this.dettaglioImpresaAggiudicatariaStoreService.impresa = args.impresa;
            this.dettaglioImpresaAggiudicatariaStoreService.tipoDocumento = args.tipoDocumento;
            this.dettaglioImpresaAggiudicatariaStoreService.numPubb = args.numPubb;
            this.dettaglioImpresaAggiudicatariaStoreService.campiVisibili = args.campiVisibili;
            this.dettaglioImpresaAggiudicatariaStoreService.campiObbligatori = args.campiObbligatori;

            let params: IDictionary<any> = {
                codGara: args.codGara,
                tipoDocumento: args.tipoDocumento,
                numPubb: args.numPubb,
                campiVisibili: args.campiVisibili,
                campiObbligatori: args.campiObbligatori,
                idGruppo: impresa.idGruppo
            };
            this.routerService.navigateToPage('dettaglio-impr-agg-atto-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateImpresa(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let impresa: ImpresaAggiudicatariaRaggEntry = args.impresa;

            let params: IDictionary<any> = {
                codGara: args.codGara,
                tipoDocumento: args.tipoDocumento,
                numPubb: args.numPubb,
                campiVisibili: args.campiVisibili,
                campiObbligatori: args.campiObbligatori,
                idGruppo: impresa.idGruppo
            };
            this.routerService.navigateToPage('modifica-impr-agg-atto-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaImpreseAggiudicatarie(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            tipoDocumento: args.tipoDocumento,
            numPubb: args.numPubb,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('lista-impr-agg-atto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovaImpresa(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara: args.codGara,
            tipoDocumento: args.tipoDocumento,
            numPubb: args.numPubb,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('nuova-impr-agg-atto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteFactory(codGara: string, numPubb: string) {
        return () => {
            return this.impreseAggiudicatarieService.deleteImpresaAggiudicatariaAtto(codGara, numPubb);
        }
    }

    private saveImpresa(args: ListaImpreseAggiudicatarieAttoProviderArgs): Observable<IDictionary<any>> {
        let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        let messagesPanel: HTMLElement = args.messagesPanel;
        let setUpdateState: Function = args.setUpdateState;
        let codGara: string = args.codGara;
        let tipoDocumento: string = args.tipoDocumento;
        let numPubb: string = args.numPubb;
        let campiVisibili: string = args.campiVisibili;
        let campiObbligatori: string = args.campiObbligatori;
        let update: boolean = args.update;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: ImpreseAggiudicatarieAttoInsertForm = this.populateRequest(formBuilderConfig, codGara, tipoDocumento, numPubb);

                let factory: Function;

                if (update === true) {
                    factory = this.updateImpresaAggiudicatariaFactory(request);
                } else {
                    factory = this.insertImpresaAggiudicatariaFactory(request);
                }

                return this.requestHelper.begin(factory, messagesPanel).pipe(
                    map((result: any) => {

                        setUpdateState(false);

                        this.dettaglioImpresaAggiudicatariaStoreService.clear();

                        let params: IDictionary<any> = {
                            codGara,
                            tipoDocumento,
                            numPubb,
                            campiVisibili,
                            campiObbligatori
                        };

                        this.routerService.navigateToPage('lista-impr-agg-atto-page', params);
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

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, tipoDocumento: string, numPubb: string): ImpreseAggiudicatarieAttoInsertForm {
        let request: ImpreseAggiudicatarieAttoInsertForm = this.getDefaultImpresaAggiudicatariaForm(codGara, tipoDocumento, numPubb);
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

    private getDefaultImpresaAggiudicatariaForm(codGara: string, tipoDocumento: string, numPubb: string): ImpreseAggiudicatarieAttoInsertForm {
        let request: any = {
            codGara: +codGara,
            tipoDocumento,
            numPubb,
            imprese: []
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: ImpreseAggiudicatarieAttoInsertForm, dynamicMappingOutput?: string): ImpreseAggiudicatarieAttoInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'COMBOBOX') {
                if (field.data != null) {
                    set(request, field.mappingOutput, field.data.key);
                }
            } else if (field.type === 'DATEPICKER') {
                if (field.data != null) {
                    let data: Date = get(field, 'data');
                    if (isObject(data)) {
                        let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                        set(request, field.mappingOutput, formatted);
                    }
                }
            } else if (field.code === 'nomest') {
                if (field.data != null) {
                    let impresa: ImpresaEntry = field.data;
                    set(request, field.mappingOutput, impresa.codiceImpresa);
                }
            } else if ((field.code === 'nomest-singola') && field.visible === true) {
                if (field.data != null) {
                    let singolaImpresa: ImpresaAggiudicatariaInsertForm = size(request.imprese) > 0 ? head(request.imprese) : {};
                    let impresa: ImpresaEntry = field.data;
                    set(singolaImpresa, field.mappingOutput, impresa.codiceImpresa);
                    request.imprese = [singolaImpresa];
                }
            } else {
                if (field.data != null) {
                    set(request, field.mappingOutput, field.data);
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: ImpreseAggiudicatarieAttoInsertForm): ImpreseAggiudicatarieAttoInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: ImpreseAggiudicatarieAttoInsertForm): ImpreseAggiudicatarieAttoInsertForm {
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

    private insertImpresaAggiudicatariaFactory(form: ImpreseAggiudicatarieAttoInsertForm) {
        return () => {
            return this.impreseAggiudicatarieService.insertImpresaAggiudicatariaAtto(form);
        }
    }

    private updateImpresaAggiudicatariaFactory(form: ImpreseAggiudicatarieAttoInsertForm) {
        return () => {
            return this.impreseAggiudicatarieService.updateImpresaAggiudicatariaAtto(form);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioImpresaAggiudicatariaStoreService(): DettaglioImpresaAggiudicatariaStoreService { return this.injectable(DettaglioImpresaAggiudicatariaStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get impreseAggiudicatarieService(): ImpreseAggiudicatarieService { return this.injectable(ImpreseAggiudicatarieService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
