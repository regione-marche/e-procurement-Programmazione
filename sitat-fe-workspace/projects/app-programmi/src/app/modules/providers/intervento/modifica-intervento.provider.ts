import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkComboBoxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkTextboxMatrixCellConfig,
    SdkTextboxMatrixRowConfig,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { ImmobileInsertForm } from '../../models/immobili/immobile.model';
import { InterventoBaseEntry, InterventoInsertForm } from '../../models/interventi/interventi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import { NuovoInterventoValidationUtilsService } from '../../services/utils/nuovo-intervento-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class ModificaInterventoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        if (args.buttonCode === 'back-to-dett-prog') {
            let setUpdateState: Function = args.setUpdateState;
            setUpdateState(false);
            let params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia,
                idIntervento: args.idIntervento
            }
            this.routerService.navigateToPage('dett-intervento-page', params);
        } else if (args.buttonCode === 'save-intervento') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let idProgramma: string = args.idProgramma;
            let tipologia: string = args.tipologia;
            let idIntervento: number = args.idIntervento;
            let tab: string = args.tab;
            let setUpdateState: Function = args.setUpdateState;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoInterventoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let request: InterventoInsertForm = this.populateRequest(formBuilderConfig, idProgramma, tipologia, undefined, tab);
                    request.id = idIntervento;
                    let modificaInterventoFactory = this.modificaInterventoFactory(request);
                    return this.requestHelper.begin(modificaInterventoFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            idProgramma,
                            tipologia,
                            idIntervento
                        };
                        this.routerService.navigateToPage('dett-intervento-page', params);
                        return {
                            ...result,
                            defaultFormBuilderConfig,
                            formBuilderConfig
                        };
                    }), catchError((err: any) => {
                        this.scrollToMessagePanel(messagesPanel);
                        return of(err);
                    }));
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

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, idProgramma: string, tipologia: string, baseObj: InterventoBaseEntry, tab: string): InterventoInsertForm {
        let request: InterventoInsertForm;
        if (isObject(baseObj)) {
            request = cloneDeep(baseObj);
            if (tab && tab === '1') {
                delete request.immobili;
            }
        } else {
            request = this.getDefaultForm(idProgramma, tipologia);
        }
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

    protected getDefaultForm(idProgramma: string, tipologia: string): InterventoInsertForm {
        let item: InterventoInsertForm = {
            idProgramma: +idProgramma,
            tipologiaIntervento: +tipologia
        };
        if (tipologia === '1') {
            item.settore = 'L'
        }
        return item;
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field)) {
            if (field.visible !== false) {
                if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item) && !isEmpty(field.mappingOutput)) {
                        set(request, field.mappingOutput, item.key);
                    }
                } else if (field.type === 'TEXTBOX-MATRIX') {
                    each(field.rows, (row: SdkTextboxMatrixRowConfig) => {
                        each(row.cells, (cell: SdkTextboxMatrixCellConfig) => {
                            if (!isEmpty(cell.mappingOutput)) {
                                set(request, cell.mappingOutput, cell.value);
                            }
                        });
                    })
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (!isUndefined(field.data)) {
                    if (!isEmpty(field.mappingOutput)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            } else {
                set(request, field.mappingOutput, undefined);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: any): any {
        if (field.visible !== false) {
            each(field.fieldSections, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    request = this.elaborateSection(one, request);
                } else if (one.type === 'FORM-GROUP') {
                    request = this.elaborateGroup(one, request);
                } else {
                    request = this.elaborateOne(one, request);
                }
            });
        }
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: any): any {
        if (field.visible !== false) {
            let newRestObjects: Array<ImmobileInsertForm>;
            if (!isEmpty(field.mappingOutput)) {
                newRestObjects = get(request, field.mappingOutput);
                if (isUndefined(newRestObjects)) {
                    newRestObjects = new Array();
                }
            }

            if (isObject(newRestObjects)) {
                each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration) => {
                    let singleIterationObject: ImmobileInsertForm = {};
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

    private modificaInterventoFactory(insertForm: InterventoInsertForm) {
        return () => {
            return this.programmiService.modificaIntervento(insertForm)
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get nuovoInterventoValidationUtilsService(): NuovoInterventoValidationUtilsService { return this.injectable(NuovoInterventoValidationUtilsService) }

    // #endregion

}
