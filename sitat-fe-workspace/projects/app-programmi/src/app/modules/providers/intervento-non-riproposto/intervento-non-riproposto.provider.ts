import { Injectable, Injector } from '@angular/core';
import { DynamicValue, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { InterventoNonRipropostoInsertForm, InterventoNonRipropostoUpdateForm } from '../../models/interventi/interventi-non-riproposti.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import { NuovoProgrammaValidationUtilsService } from '../../services/utils/nuovo-programma-validation-utils.service';


export interface InterventoNonRipropostoProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    idProgramma?: string;
    idIntervento?: string;
    tipologia?: string;
}

@Injectable({ providedIn: 'root' })
export class InterventoNonRipropostoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: InterventoNonRipropostoProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('InterventoNonRipropostoProviderArgs', args);
        if (args.buttonCode === 'back-to-lista') {

            const setUpdateState = args.setUpdateState;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            const params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia
            }

            this.routerService.navigateToPage('dett-prog-interventi-non-riproposti-page', params);
        } else if (args.buttonCode === 'back-to-dettaglio') {

            const setUpdateState = args.setUpdateState;

            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            const params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                idIntervento: args.idIntervento,
                tipologia: args.tipologia
            }

            this.routerService.navigateToPage('dettaglio-intervento-non-riproposto-page', params);
        } else if (args.buttonCode === 'save-intervento-nr') {

            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const idProgramma: string = args.idProgramma;
            const idIntervento: string = args.idIntervento;
            const tipologia: string = args.tipologia;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoProgrammaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta

                    
                    let factory: Function;
                    if(idIntervento != null){
                        let request: InterventoNonRipropostoUpdateForm = this.populateRequest(formBuilderConfig, idProgramma);
                        request.idIntervento = Number(idIntervento);
                        factory = this.updateFactory(request);
                    } else{
                        let request: InterventoNonRipropostoInsertForm = this.populateRequest(formBuilderConfig, idProgramma);
                        factory = this.insertFactory(request);
                    }
                    
                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);
                            
                            let idInt;
                            if(idIntervento != null){
                                idInt = idIntervento
                            } else{
                                idInt = result.data
                            }
                            const params: IDictionary<any> = {
                                idProgramma: idProgramma,
                                idIntervento: idInt,
                                tipologia: tipologia
                            }

                            this.routerService.navigateToPage('dettaglio-intervento-non-riproposto-page', params);
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
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, idProgramma: string): InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm {
        let request: InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm = this.getDefaultForm(idProgramma);
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

    private getDefaultForm(idProgramma: string): InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm {
        let request: InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm = {
            idProgramma: +idProgramma
        };

        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm, dynamicMappingOutput?: string): InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm {
        if (isObject(field)) {
            if (!isEmpty(dynamicMappingOutput)) {
                let list: Array<DynamicValue> = get(request, dynamicMappingOutput);
                if (isUndefined(list)) {
                    list = new Array();
                }
                if (field.type === 'COMBOBOX') {
                    if (!isUndefined(field.data)) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data.key ? field.data.key : null
                        };
                        list.push(dynamicValue);
                    }
                } else {
                    if (!isUndefined(field.data)) {
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
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm): InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm): InterventoNonRipropostoInsertForm | InterventoNonRipropostoUpdateForm {
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

    private insertFactory(insertForm: InterventoNonRipropostoInsertForm) {
        return () => {
            return this.programmiService.createInterventoNonRiproposto(insertForm);
        }
    }

    private updateFactory(insertForm: InterventoNonRipropostoUpdateForm) {
        return () => {
            return this.programmiService.modificaInterventoNonRiproposto(insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoProgrammaValidationUtilsService(): NuovoProgrammaValidationUtilsService { return this.injectable(NuovoProgrammaValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
