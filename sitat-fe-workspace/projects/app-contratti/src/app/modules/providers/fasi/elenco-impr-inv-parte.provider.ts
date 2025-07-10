import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, ImpresaEntry } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set, toString } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import {
    DettaglioImpresaInvitataPartecipanteStoreService,
} from '../../layout/components/business/fasi/elenco-impr-inv-parte/dettaglio-impr-inv-parte-store.service';
import { FaseImpresaEntry, FaseImpresaInsertForm, RuoloImpresa } from '../../models/fasi/elenco-impr-inv-parte.model';
import { ElencoImpreseInvitatePartecipantiService } from '../../services/fasi/elenco-impr-inv-parte.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

export interface ElencoImpreseInvitatePartecipantiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    codiceFase?: string;
    numeroProgressivo?: string;
    num?: string;
    numRagg?: string;
    impresa?: FaseImpresaEntry;
    setUpdateState?: Function;
    defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    formBuilderConfig: SdkFormBuilderConfiguration;
    updateFase?: boolean;
    smartCig?: boolean;
    fromLS?: string;
}

@Injectable({ providedIn: 'root' })
export class ElencoImpreseInvitatePartecipantiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ElencoImpreseInvitatePartecipantiProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteImpresa(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL' || args.buttonCode === 'back-to-dettaglio-impr-inv-parte') {
            return this.dettaglioImpresaInvitataPartecipante(args);
        } else if (args.action === 'DETAIL-LS') {
            return this.dettaglioImpresaInvitataPartecipanteLS(args);
        } else if (args.buttonCode === 'back-to-lista-impr-inv-parte') {
            return this.listaImrpeseInvitatePartecipanti(args);
        } else if (args.buttonCode === 'back-to-lista-impr-inv-parte-LS') {
            return this.listaImrpeseInvitatePartecipantiLS(args);
        } else if (args.buttonCode === 'go-to-update-impr-inv-parte') {
            return this.goToUpdateImpresa(args);
        } else if (args.buttonCode === 'go-to-nuova-impresa') {
            return this.goToNuovaImpresa(args);
        } else if (args.buttonCode === 'save-impresa') {
            return this.saveImpresa(args);
        }
        return of(args);
    }

    // #region Private

    private deleteImpresa(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {
        if (args.impresa) {
            let factory = this.deleteFactory(args.codGara, args.codLotto, toString(args.impresa.num), toString(args.impresa.numRagg), args.updateDaexport);
            return this.requestHelper.begin(factory, args.messagesPanel);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioImpresaInvitataPartecipante(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            this.dettaglioImprInvParteStoreService.clear();
            this.dettaglioImprInvParteStoreService.codGara = args.codGara;
            this.dettaglioImprInvParteStoreService.codLotto = args.codLotto;
            this.dettaglioImprInvParteStoreService.codiceFase = args.codiceFase;
            this.dettaglioImprInvParteStoreService.numeroProgressivo = args.numeroProgressivo;
            this.dettaglioImprInvParteStoreService.num = args.num;
            this.dettaglioImprInvParteStoreService.numRagg = args.numRagg;
            this.dettaglioImprInvParteStoreService.impresa = args.impresa;

            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase,
                numeroProgressivo: args.numeroProgressivo,
                num: args.num
            };
            if (!isEmpty(args.numRagg)) {
                set(params, 'numRagg', args.numRagg);
            }
            this.routerService.navigateToPage('dettaglio-impr-inv-parte-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioImpresaInvitataPartecipanteLS(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            this.dettaglioImprInvParteStoreService.clear();
            this.dettaglioImprInvParteStoreService.codGara = args.codGara;
            this.dettaglioImprInvParteStoreService.codLotto = args.codLotto;
            this.dettaglioImprInvParteStoreService.codiceFase = args.codiceFase;
            this.dettaglioImprInvParteStoreService.numeroProgressivo = args.numeroProgressivo;
            this.dettaglioImprInvParteStoreService.num = args.num;
            this.dettaglioImprInvParteStoreService.numRagg = args.numRagg;
            this.dettaglioImprInvParteStoreService.impresa = args.impresa;
            this.dettaglioImprInvParteStoreService.fromLS = 'LS';

            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase,
                numeroProgressivo: args.numeroProgressivo,
                num: args.num,
                fromLS: 'LS'
            };
            if (!isEmpty(args.numRagg)) {
                set(params, 'numRagg', args.numRagg);
            }
            this.routerService.navigateToPage('dettaglio-impr-inv-parte-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToUpdateImpresa(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.impresa)) {

            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase,
                numeroProgressivo: args.numeroProgressivo,
                num: args.num
            };
            if (!isEmpty(args.numRagg)) {
                set(params, 'numRagg', args.numRagg);
            }
            this.routerService.navigateToPage('modifica-impr-inv-parte-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToNuovaImpresa(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };

        this.routerService.navigateToPage('nuova-impr-inv-parte-page', params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaImrpeseInvitatePartecipanti(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {

        this.dettaglioImprInvParteStoreService.clear();

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
        if(args.smartCig === true){
            this.routerService.navigateToPage('lista-elenco-impr-inv-parte-smartcig-page', params);
        } else {
            this.routerService.navigateToPage('lista-elenco-impr-inv-parte-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaImrpeseInvitatePartecipantiLS(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {

        this.dettaglioImprInvParteStoreService.clear();

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

        if(args.smartCig === true){
            this.routerService.navigateToPage('lista-elenco-impr-inv-parte-smartcig-page', params);
        } else {
            this.routerService.navigateToPage('lista-elenco-impr-inv-parte-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteFactory(codGara: string, codLotto: string, num: string, numRagg: string, updateDaexport:boolean) {
        return () => {
            return this.elencoImprInvParteService.deleteSingolaImpresa(codGara, codLotto, num, numRagg, updateDaexport);
        }
    }

    private saveImpresa(args: ElencoImpreseInvitatePartecipantiProviderArgs): Observable<IDictionary<any>> {
        let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        let messagesPanel: HTMLElement = args.messagesPanel;
        let setUpdateState: Function = args.setUpdateState;
        let codGara: string = args.codGara;
        let codLotto: string = args.codLotto;
        let codiceFase: string = args.codiceFase;
        let numeroProgressivo: string = args.numeroProgressivo;
        let num: string = args.num;
        let numRagg: string = args.numRagg;
        let updateFase: boolean = args.updateFase;
        let isImpresaAgg: boolean = args.isImpresaAgg;
        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: FaseImpresaInsertForm = this.populateRequest(formBuilderConfig, codGara, codLotto);
                request.updateDaexport = args.updateDaexport;
                let codimpArray: String[] = [];

                if(isImpresaAgg){
                    request.partecipante = 1;
                }

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

                    if (updateFase === true) {
                        request.num = +num;
                        if (!isEmpty(numRagg) && numRagg !== '0') {
                            request.numRagg = +numRagg;
                        }
                        factory = this.updateImpresaFactory(request);
                    } else {
                        factory = this.insertImpresaFactory(request);
                    }

                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            this.dettaglioImprInvParteStoreService.clear();

                            let params: IDictionary<any> = {
                                codGara,
                                codLotto,
                                codiceFase,
                                numeroProgressivo
                            };
                            if(args.smartCig === true){
                                this.routerService.navigateToPage('lista-elenco-impr-inv-parte-smartcig-page', params);
                            } else {
                                this.routerService.navigateToPage('lista-elenco-impr-inv-parte-page', params);
                            }
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, codLotto: string): FaseImpresaInsertForm {
        let request: FaseImpresaInsertForm = this.getDefaultImpresaAggiudicatariaForm(codGara, codLotto);
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

    private getDefaultImpresaAggiudicatariaForm(codGara: string, codLotto: string): FaseImpresaInsertForm {
        let request: FaseImpresaInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto,
            imprese: []
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: FaseImpresaInsertForm, dynamicMappingOutput?: string): FaseImpresaInsertForm {
        if (isObject(field)) {
            if (!isEmpty(field.mappingOutput)) {
                if (field.type === 'COMBOBOX') {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data.key);
                    }
                } else if (field.type === 'DATEPICKER') {
                    if (!isUndefined(field.data)) {
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    }
                } else if (field.code === 'denominazione-impresa' || field.code === 'denominazione-impresa2') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.code === 'denominazione-impresa-singola' && field.visible === true) {
                    let impresa: ImpresaEntry = field.data;
                    let ruoloImpresa: RuoloImpresa = {
                        codImpresa: impresa.codiceImpresa
                    };
                    request.imprese.push(ruoloImpresa);
                } else {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: FaseImpresaInsertForm): FaseImpresaInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: FaseImpresaInsertForm): FaseImpresaInsertForm {
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

    private updateImpresaFactory(insertForm: FaseImpresaInsertForm) {
        return () => {
            return this.elencoImprInvParteService.updateImpresa(insertForm);
        }
    }

    private insertImpresaFactory(insertForm: FaseImpresaInsertForm) {
        return () => {
            return this.elencoImprInvParteService.insertImpresa(insertForm);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioImprInvParteStoreService(): DettaglioImpresaInvitataPartecipanteStoreService { return this.injectable(DettaglioImpresaInvitataPartecipanteStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get elencoImprInvParteService(): ElencoImpreseInvitatePartecipantiService { return this.injectable(ElencoImpreseInvitatePartecipantiService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
