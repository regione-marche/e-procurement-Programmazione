import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkComboBoxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkTextboxMatrixCellConfig,
    SdkTextboxMatrixRowConfig,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { RisorsaCapitoloBaseEntry, RisorsaDiCapitoloInsertForm } from '../../models/risorse-capitolo/risorsa-capitolo.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import { NuovaRisorsaValidationUtilsService } from '../../services/utils/nuova-risorsa-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class RisorsaDiCapitoloProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('RisorsaDiCapitoloProvider >>>', args);

        if (args.buttonCode === 'save-risorsa-di-capitolo') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let idProgramma: string = args.idProgramma;
            let tipologia: string = args.tipologia;
            let idIntervento: string = args.idIntervento;
            let setUpdateState: Function = args.setUpdateState;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovaRisorsaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let request: RisorsaDiCapitoloInsertForm = this.populateRequest(formBuilderConfig, idProgramma, idIntervento, undefined);
                    let inserisciRisorsaFactory = this.inserisciRisorsaFactory(request);
                    return this.requestHelper.begin(inserisciRisorsaFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {
                        let idRisorsa: number = result.data;

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            idProgramma,
                            tipologia,
                            idIntervento
                        };
                        this.routerService.navigateToPage('risorse-di-capitolo-page', params);
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

        if (args.buttonCode === 'update-risorsa-di-capitolo') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let idProgramma: string = args.idProgramma;
            let tipologia: string = args.tipologia;
            let idIntervento: string = args.idIntervento;
            let idRisorsaDiCapitolo: string = args.idRisorsaDiCapitolo;
            let setUpdateState: Function = args.setUpdateState;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovaRisorsaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let request: RisorsaDiCapitoloInsertForm = this.populateRequest(formBuilderConfig, idProgramma, idIntervento, undefined);
                    request.id = Number(idRisorsaDiCapitolo);
                    let modificaRisorsaFactory = this.modificaRisorsaFactory(request);
                    return this.requestHelper.begin(modificaRisorsaFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            idProgramma,
                            tipologia,
                            idIntervento,
                            idRisorsaDiCapitolo
                        };
                        this.routerService.navigateToPage('dett-risorsa-di-capitolo-page', params);
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, idProgramma: string, idIntervento: string, baseObj: RisorsaCapitoloBaseEntry): RisorsaDiCapitoloInsertForm {
        let request: RisorsaDiCapitoloInsertForm = this.getDefaultForm(idProgramma, idIntervento);
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

    protected getDefaultForm(idProgramma: string, idIntervento: string): RisorsaDiCapitoloInsertForm {
        return {
            idProgramma: +idProgramma,
            idIntervento: +idIntervento
        } as RisorsaDiCapitoloInsertForm;
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

    private inserisciRisorsaFactory(insertForm: RisorsaDiCapitoloInsertForm) {
        return () => {
            return this.programmiService.createRisorsaDiCapitolo(insertForm)
        }
    }

    private modificaRisorsaFactory(form: RisorsaDiCapitoloInsertForm) {
        return () => {
            return this.programmiService.modificaRisorsaDiCapitolo(form)
        }
    }


    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovaRisorsaValidationUtilsService(): NuovaRisorsaValidationUtilsService { return this.injectable(NuovaRisorsaValidationUtilsService) }

    // #endregion

}
