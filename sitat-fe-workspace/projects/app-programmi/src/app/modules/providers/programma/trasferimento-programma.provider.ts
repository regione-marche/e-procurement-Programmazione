import { Injectable, Injector } from '@angular/core';
import { DynamicValue, HttpRequestHelper, ResponseResult, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isString, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { CambioSysconForm } from '../../models/programmi/programmi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import { NuovoProgrammaValidationUtilsService } from '../../services/utils/nuovo-programma-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class TrasferimentoProgrammaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('TrasferimentoProgrammaProvider >>>', args);
        const data = args.data;
        if (data.code === 'conferma') {
            return this.conferma();
        } else if (data.code === 'trasferisci-programma') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = data.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = data.formBuilderConfig;
            const messagesPanel: HTMLElement = data.messagesPanel;
            const idProgramma: number = data.idProgramma;
            const stazioneAppaltante: StazioneAppaltanteInfo = data.stazioneAppaltante;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoProgrammaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: CambioSysconForm = this.populateRequest(formBuilderConfig, idProgramma, stazioneAppaltante);

                    let factory: Function = this.cambioSysconFactory(request);

                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: ResponseResult<boolean>) => {
                            return {
                                update: result.esito
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, idProgramma: number, stazioneAppaltante: StazioneAppaltanteInfo): CambioSysconForm {
        let request: CambioSysconForm = this.getDefaultForm(idProgramma, stazioneAppaltante);
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

    private getDefaultForm(idProgramma: number, stazioneAppaltante: StazioneAppaltanteInfo): CambioSysconForm {
        let request: CambioSysconForm = {
            contri: idProgramma,
            stazioneAppaltante: stazioneAppaltante.codice
        };

        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: CambioSysconForm, dynamicMappingOutput?: string): CambioSysconForm {
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
                            value: field.data.key
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

                let path: string = field.mappingOutput;

                if (field.type === 'COMBOBOX') {
                    if (!isUndefined(field.data)) {
                        set(request, path, field.data.key);
                    }
                } else if (field.type === 'DATEPICKER') {
                    if (!isUndefined(field.data)) {
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, path, formatted);
                        }
                    }
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else {
                    let first: boolean = !isUndefined(field.data);
                    let second: boolean = !isString(field.data) || (isString(field.data) && !isEmpty(field.data));
                    if (first && second) {
                        set(request, path, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: CambioSysconForm): CambioSysconForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: CambioSysconForm): CambioSysconForm {
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

    private cambioSysconFactory(request: CambioSysconForm) {
        return () => {
            return this.programmiService.cambiaSyscon(request);
        }
    }

    private conferma(): Observable<IDictionary<any>> {
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next({
                conferma: true
            });
            ob.complete();
        });
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get nuovoProgrammaValidationUtilsService(): NuovoProgrammaValidationUtilsService { return this.injectable(NuovoProgrammaValidationUtilsService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion
}
