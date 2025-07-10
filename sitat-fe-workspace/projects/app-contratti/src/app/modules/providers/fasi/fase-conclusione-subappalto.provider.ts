import { Injectable, Injector } from '@angular/core';
import { DynamicValue, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty, isFunction, isObject, isString, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { DettaglioFaseStoreService } from '../../layout/components/business/fasi/dettaglio-fase-store.service';
import { FaseSubappaltoInsertForm } from '../../models/fasi/fase-subappalto.model';
import { FaseSubappaltoService } from '../../services/fasi/fase-subappalto.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';
import { FaseConclusioneSubappaltoService } from '../../services/fasi/fase-conclusione-subappalto.service';

@Injectable({ providedIn: 'root' })
export class FaseConclusioneSubappaltoProvider extends SdkBaseService implements SdkProvider {

    constructor(injector: Injector) {
        super(injector);
    }    
    
    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('FaseConclusioneSubappaltoProvider >>>', args);

        if (args.buttonCode === 'back-to-dettaglio-fase') {
            let codGara: string = args.codGara;
            let codLotto: string = args.codLotto;
            let codiceFase: string = args.codiceFase;
            let numeroProgressivo: string = args.numeroProgressivo;
            let setUpdateState: Function = args.setUpdateState;
            this.dettaglioFase(codGara, codLotto, codiceFase, numeroProgressivo, setUpdateState);
        } else if (args.buttonCode === 'go-to-update-fase') {
            let codGara: string = args.codGara;
            let codLotto: string = args.codLotto;
            let codiceFase: string = args.codiceFase;
            let numeroProgressivo: string = args.numeroProgressivo;
            this.modificaFase(codGara, codLotto, codiceFase, numeroProgressivo);
        } else if (args.buttonCode === 'save-fase') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const codGara: string = args.codGara;
            const codLotto: string = args.codLotto;
            const codiceFase: string = args.codiceFase;
            const numeroProgressivo: string = args.numeroProgressivo;
            const isUpdatePhase: boolean = args.updateFase;

            // controllo la validita' del modello
            let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                const request: FaseSubappaltoInsertForm = this.populateRequest(formBuilderConfig, codGara, codLotto);
                let emptyArray = true;
                if(request.mandanti != null){
                    for (let index = 0; index < request.mandanti.length; index++) {
                        const element = request.mandanti[index];
                        if(!isEmpty(element)){
                            emptyArray = false;
                        }         
                    }
                }
                let checkMandataria = false;
                if(request.mandanti != null){
                    for (let index = 0; index < request.mandanti.length; index++) {
                        const element = request.mandanti[index];
                        if(!isEmpty(element) && element.nomeMandante === request.codImpresaAgg){
                            checkMandataria = true;
                        }         
                    }
                }                            
                if((request.mandanti == null || request.mandanti.length == 0 || emptyArray) && (request.tipoImpresa == '2' || request.tipoImpresa == this.translateService.instant('COMBOBOX.MANDATARIA'))){
                    let error = {
                        errorMandante : true
                    };
                    return of(error);                  
                } else if(checkMandataria){
                    let error = {
                        errorMandataria : true
                    };
                    return of(error); 
                } else{
                    let faseFactory: Function;
                    
                    if (isUpdatePhase === true) {
                        request.num = Number(numeroProgressivo);
                        faseFactory = this.updateFaseFactory(request);
                    } else {
                        faseFactory = this.insertFaseFactory(request);
                    }
    
                    return this.requestHelper.begin(faseFactory, messagesPanel).pipe(
                        map((result: any) => {
    
                            if (isFunction(setUpdateState)) {
                                setUpdateState(false);
                            }
    
                            this.dettaglioFaseStoreService.clear();
                            this.dettaglioFaseStoreService.codGara = codGara;
                            this.dettaglioFaseStoreService.codLotto = codLotto;
                            this.dettaglioFaseStoreService.codiceFase = codiceFase;
                            this.dettaglioFaseStoreService.numeroProgressivo = numeroProgressivo || result.data;
    
                            let params: IDictionary<any> = {
                                codGara,
                                codLotto,
                                codiceFase,
                                numeroProgressivo: numeroProgressivo || result.data
                            };
    
                            this.routerService.navigateToPage('dettaglio-fase-conclusione-subappalto-page', params);
                            return {
                                ...result,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            };
                        }),
                        catchError((error: any, caught: Observable<any>) => {
                            this.scrollToMessagePanel(messagesPanel);
                            return throwError(error);
                        })
                    );
                }

            } else {
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

    private dettaglioFase(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string, setUpdateState: Function): Observable<IDictionary<any>> {

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara,
            codLotto,
            codiceFase,
            numeroProgressivo
        };
        this.routerService.navigateToPage('dettaglio-fase-conclusione-subappalto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private modificaFase(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara,
            codLotto,
            codiceFase,
            numeroProgressivo
        };
        this.routerService.navigateToPage('modifica-fase-conclusione-subappalto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private insertFaseFactory(insertForm: FaseSubappaltoInsertForm) {
        return () => {
            return this.faseConclusioneSubappaltoService.insertFase(insertForm);
        }
    }

    private updateFaseFactory(insertForm: FaseSubappaltoInsertForm) {
        return () => {
            return this.faseConclusioneSubappaltoService.updateFase(insertForm);
        }
    }

    private getDefaultFaseForm(codGara: string, codLotto: string): FaseSubappaltoInsertForm {
        const request: FaseSubappaltoInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: FaseSubappaltoInsertForm, dynamicMappingOutput?: string): FaseSubappaltoInsertForm {
        if (isObject(field)) {
            if (!isEmpty(dynamicMappingOutput)) {
                let list: Array<DynamicValue> = get(request, dynamicMappingOutput);
                if (isUndefined(list)) {
                    list = new Array();
                }
                if (field.type === 'COMBOBOX') {
                    if (field.data != null) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data.key
                        };
                        list.push(dynamicValue);
                    }
                } else {
                    if (field.data != null) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data
                        };
                        list.push(dynamicValue);
                    }
                }
                set(request, dynamicMappingOutput, list);
            } else if (!isEmpty(field.mappingOutput)) {

                let path: string = field.mappingOutput;

                if (field.type === 'COMBOBOX') {
                    if (field.data != null) {
                        set(request, path, field.data.key);
                    }
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.type === 'DATEPICKER') {
                    if (field.data != null) {
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, path, formatted);
                        }
                    }
                } else {
                    let first: boolean = field.data != null;
                    let second: boolean = !isString(field.data) || (isString(field.data) && !isEmpty(field.data));
                    if (first && second) {
                        set(request, path, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: FaseSubappaltoInsertForm): FaseSubappaltoInsertForm {
        let dynamicMappingOutput: string = field.mappingOutput ? field.mappingOutput : undefined;
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request);
            } else {
                request = this.elaborateOne(one, request, dynamicMappingOutput);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: FaseSubappaltoInsertForm): FaseSubappaltoInsertForm {
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, codLotto: string): FaseSubappaltoInsertForm {
        let request: FaseSubappaltoInsertForm = this.getDefaultFaseForm(codGara, codLotto);
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

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get faseConclusioneSubappaltoService(): FaseConclusioneSubappaltoService { return this.injectable(FaseConclusioneSubappaltoService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion
}
