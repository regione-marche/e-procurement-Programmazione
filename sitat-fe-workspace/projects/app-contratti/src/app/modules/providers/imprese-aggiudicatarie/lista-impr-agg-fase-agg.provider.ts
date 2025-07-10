import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, ImpresaEntry, ResponseResult } from '@maggioli/app-commons';
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
    ImportImpresaAgiudicatariaForm,
    ImpresaAggiudicatariaInsertForm,
    ImpresaAggiudicatariaRaggEntry,
    ImpreseAggiudicatarieInsertForm,
    ImpreseAggiudicatarieUpdateForm,
} from '../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';
import { ElencoImpreseInvitatePartecipantiService } from '../../services/fasi/elenco-impr-inv-parte.service';
import { ImpreseAggiudicatarieService } from '../../services/imprese-aggiudicatarie/imprese-aggiudicatarie.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

export interface ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    codiceFase?: string;
    numeroProgressivo?: string;
    idGruppo?: number;
    impresa?: ImpresaAggiudicatariaRaggEntry;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    selectedImpresa?: FaseImpresaEntry;
    update?: boolean;
    numAppa?: number;
    impreseDaCancellare?: Array<number>;
    fromLS?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaImpreseAggiudicatarieFaseAggiudicazioneProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteImpresaAggiudicataria(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL' || args.buttonCode === 'back-to-dettaglio-impresa-aggiudicataria') {
            return this.dettaglioImpresaAggiudicataria(args);
        } else if (args.buttonCode === 'back-to-lista-imprese-aggiudicatarie') {
            return this.listaImpreseAggiudicatarie(args);
        } else if (args.buttonCode === 'back-to-lista-impr-agg-schede-trasmesse') {
            return this.backListaImprAggiudicatarieSchedeTrasmesse(args);
        } else if (args.buttonCode === 'go-to-update-impresa-aggiudicataria') {
            return this.updateImpresa(args);
        } else if (args.buttonCode === 'save-impresa') {
            return this.saveImpresa(args);
        } else if (args.buttonCode === 'nuova-impresa') {
            return this.nuovaImpresa(args);
        } else if (args.buttonCode === 'import-impresa') {
            return this.importImpresa(args);
        }
        return of(args);
    }

    // #region Private

    private deleteImpresaAggiudicataria(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {
        if (isObject(args.impresa)) {
            let factory = this.deleteFactory(args.codGara, args.codLotto, args.impresa);
            return this.requestHelper.begin(factory, args.messagesPanel);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioImpresaAggiudicataria(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            let impresa: ImpresaAggiudicatariaRaggEntry = args.impresa;

            this.dettaglioImpresaAggiudicatariaStoreService.clear();
            this.dettaglioImpresaAggiudicatariaStoreService.codGara = args.codGara;
            this.dettaglioImpresaAggiudicatariaStoreService.codLotto = args.codLotto;
            this.dettaglioImpresaAggiudicatariaStoreService.codiceFase = args.codiceFase;
            this.dettaglioImpresaAggiudicatariaStoreService.numeroProgressivo = args.numeroProgressivo;
            this.dettaglioImpresaAggiudicatariaStoreService.impresa = args.impresa;
            this.dettaglioImpresaAggiudicatariaStoreService.fromLS = args.fromLS;

            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase,
                numeroProgressivo: args.numeroProgressivo,
                idGruppo: impresa.idGruppo,
                fromLS: args.fromLS
            };
            this.routerService.navigateToPage('dettaglio-impr-agg-fase-agg-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateImpresa(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let impresa: ImpresaAggiudicatariaRaggEntry = args.impresa;

            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase,
                numeroProgressivo: args.numeroProgressivo,
                idGruppo: impresa.idGruppo
            };
            this.routerService.navigateToPage('modifica-impr-agg-fase-agg-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaImpreseAggiudicatarie(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('lista-impr-agg-fase-agg-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaImprAggiudicatarieSchedeTrasmesse(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo,
            fromLS: 'LS'
        };
        this.dettaglioImpresaAggiudicatariaStoreService.clearFromLS();
        this.routerService.navigateToPage('lista-impr-agg-fase-agg-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovaImpresa(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };

        let factory = this.dettaglioListaImpresePartecipantiFactory(args.codGara, args.codLotto);
        return this.requestHelper.begin(factory, args.messagesPanel)
            .pipe(
                map((result: Array<FaseImpresaEntry>) => {

                    if (size(result) > 0) {
                        this.routerService.navigateToPage('import-impr-agg-fase-agg-page', params);
                    } else {
                        this.routerService.navigateToPage('nuova-impr-agg-fase-agg-page', params);
                    }

                    return result;
                })
            );
    }

    private importImpresa(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.selectedImpresa)) {

            let impresa: FaseImpresaEntry = args.selectedImpresa;

            let request: ImportImpresaAgiudicatariaForm = {
                codGara: +args.codGara,
                codLotto: +args.codLotto,
                num: impresa.num,
                numRagg: impresa.numRagg
            };

            let factory = this.importImpresaAggiudicatariaFactory(request);
            return this.requestHelper.begin(factory, args.messagesPanel)
                .pipe(
                    map((result: ResponseResult<any>) => {

                        let params: IDictionary<any> = {
                            codGara: args.codGara,
                            codLotto: args.codLotto,
                            codiceFase: args.codiceFase,
                            numeroProgressivo: args.numeroProgressivo
                        };
                        this.routerService.navigateToPage('lista-impr-agg-fase-agg-page', params);

                        return result;
                    })
                );
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }


    private deleteFactory(codGara: string, codLotto: string, impresa: ImpresaAggiudicatariaRaggEntry) {
        return () => {
            return this.impreseAggiudicatarieService.deleteImpresaAggiudicataria(codGara, codLotto, get(head(impresa.imprese), 'numAggi'), get(head(impresa.imprese), 'numAppa'));
        }
    }

    private saveImpresa(args: ListaImpreseAggiudicatarieFaseAggiudicazioneProviderArgs): Observable<IDictionary<any>> {
        const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        const messagesPanel: HTMLElement = args.messagesPanel;
        const setUpdateState: Function = args.setUpdateState;
        const codGara: string = args.codGara;
        const codLotto: string = args.codLotto;
        const codiceFase: string = args.codiceFase;
        const numeroProgressivo: string = args.numeroProgressivo;
        const update: boolean = args.update;
        const numAppa: number = args.numAppa;
        const impreseDaCancellare: Array<number> = args.impreseDaCancellare;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: ImpreseAggiudicatarieInsertForm = this.populateRequest(formBuilderConfig, codGara, codLotto);
                let codimpArray: String[] = [];
                if (isObject(request) && isObject(request.imprese)) {
                    each(request.imprese, (imp) => {
                        codimpArray.push(imp.codImpresa);
                    });

                }
                if (new Set(codimpArray).size !== request.imprese.length) {
                    this.sdkMessagePanelService.showError(messagesPanel, [
                        {
                            message: 'IMPRESA-IMPR-INV-PARTE.VALIDATORS.CODIMP-DUPLICATO'
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                } else {


                    let factory: Function;

                    if (update === true) {
                        const updateRequest: ImpreseAggiudicatarieUpdateForm = {
                            ...request,
                            numAppa,
                            impreseDaCancellare: [...impreseDaCancellare]
                        };
                        factory = this.updateImpresaAggiudicatariaFactory(updateRequest);
                    } else {
                        factory = this.insertImpresaAggiudicatariaFactory(request);
                    }

                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            this.dettaglioImpresaAggiudicatariaStoreService.clear();

                            let params: IDictionary<any> = {
                                codGara,
                                codLotto,
                                codiceFase,
                                numeroProgressivo
                            };

                            this.routerService.navigateToPage('lista-impr-agg-fase-agg-page', params);
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

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, codLotto: string): ImpreseAggiudicatarieInsertForm {
        let request: ImpreseAggiudicatarieInsertForm = this.getDefaultImpresaAggiudicatariaForm(codGara, codLotto);
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

    private getDefaultImpresaAggiudicatariaForm(codGara: string, codLotto: string): ImpreseAggiudicatarieInsertForm {
        let request: ImpreseAggiudicatarieInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto,
            imprese: []
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: ImpreseAggiudicatarieInsertForm, dynamicMappingOutput?: string): ImpreseAggiudicatarieInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'COMBOBOX' && field.code !== 'flag-avvalimento-singolo') {
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
            } else if (field.code === 'nomest' || field.code === 'nomest-ausiliaria') {
                if (field.data != null) {
                    let impresa: ImpresaEntry = field.data;
                    set(request, field.mappingOutput, impresa.codiceImpresa);
                }
            } else if ((field.code === 'nomest-singola' || field.code === 'nomest-ausiliaria-singola') && field.visible === true) {
                if (field.data != null) {
                    let singolaImpresa: ImpresaAggiudicatariaInsertForm = size(request.imprese) > 0 ? head(request.imprese) : {};
                    let impresa: ImpresaEntry = field.data;
                    set(singolaImpresa, field.mappingOutput, impresa.codiceImpresa);
                    request.imprese = [singolaImpresa];
                }
            } else if (field.code === 'flag-avvalimento-singolo' && field.visible === true) {
                if (field.data != null) {
                    let singolaImpresa: ImpresaAggiudicatariaInsertForm = size(request.imprese) > 0 ? head(request.imprese) : {};
                    set(singolaImpresa, field.mappingOutput, field.data.key);
                    request.imprese = [singolaImpresa];
                }
            } else if ((field.code === 'importo-aggiudicazione-singola' || field.code === 'ribasso-aggiudicazione-singola' || field.code === 'offerta-aumento-singola') && field.visible === true) {
                if (field.data != null) {
                    let singolaImpresa: ImpresaAggiudicatariaInsertForm = size(request.imprese) > 0 ? head(request.imprese) : {};
                    set(singolaImpresa, field.mappingOutput, field.data);
                    request.imprese = [singolaImpresa];
                }
            }  else if ((field.code === 'nome-legale-rappr-singola' || field.code === 'cognome-legale-rappr-singola' || field.code === 'cf-legale-rappr-singola') && field.visible === true) {
                if (field.data != null) {
                    let singolaImpresa: ImpresaAggiudicatariaInsertForm = size(request.imprese) > 0 ? head(request.imprese) : {};
                    set(singolaImpresa, field.mappingOutput, field.data);
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

    private elaborateSection(field: SdkFormBuilderField, request: ImpreseAggiudicatarieInsertForm): ImpreseAggiudicatarieInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: ImpreseAggiudicatarieInsertForm): ImpreseAggiudicatarieInsertForm {
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

    private insertImpresaAggiudicatariaFactory(form: ImpreseAggiudicatarieInsertForm) {
        return () => {
            return this.impreseAggiudicatarieService.insertImpresaAggiudicataria(form);
        }
    }

    private updateImpresaAggiudicatariaFactory(form: ImpreseAggiudicatarieUpdateForm) {
        return () => {
            return this.impreseAggiudicatarieService.updateImpresaAggiudicataria(form);
        }
    }

    private importImpresaAggiudicatariaFactory(form: ImportImpresaAgiudicatariaForm) {
        return () => {
            return this.impreseAggiudicatarieService.importImpresaAggiudicataria(form);
        }
    }

    private dettaglioListaImpresePartecipantiFactory(codGara: string, codLotto: string): () => Observable<Array<FaseImpresaEntry>> {
        return () => {
            return this.elencoImpreseInvitatePartecipantiService.getImpresePartecipanti(codGara, codLotto);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioImpresaAggiudicatariaStoreService(): DettaglioImpresaAggiudicatariaStoreService { return this.injectable(DettaglioImpresaAggiudicatariaStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get impreseAggiudicatarieService(): ImpreseAggiudicatarieService { return this.injectable(ImpreseAggiudicatarieService) }

    private get elencoImpreseInvitatePartecipantiService(): ElencoImpreseInvitatePartecipantiService { return this.injectable(ElencoImpreseInvitatePartecipantiService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
