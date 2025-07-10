import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkComboBoxItem,
    SdkDocumentItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { AvvisoDocumentInsert, AvvisoInsertForm, AvvisoUpdateForm, DocumentoAvviso } from '../../models/avviso/avviso.model';
import { AvvisiService } from '../../services/avvisi/avvisi.service';
import { ModificaAvvisoValidationUtilsService } from '../../services/utils/modifica-avviso-validation-utils.service';


@Injectable({ providedIn: 'root' })
export class ModificaAvvisoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('ModificaAvvisoProvider', args);

        if (args.type === 'BUTTON') {
            return this.handleButtons(args.data);
        }
        return of(args);
    }

    private handleButtons(data: IDictionary<any>): Observable<IDictionary<any>> {
        if (data.code === 'back-button') {

            let setUpdateState: Function = data.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            let params: IDictionary<any> = {
                idAvviso: data.idAvviso,
                stazioneAppaltante: data.stazioneAppaltante
            }
            this.routerService.navigateToPage('dettaglio-avviso-page', params);
        } else if (data.code === 'clean-button') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = data.defaultFormBuilderConfig;
            let messagesPanel: HTMLElement = data.messagesPanel;
            this.sdkMessagePanelService.clear(messagesPanel);
            return of({
                formBuilderConfig: cloneDeep(defaultFormBuilderConfig)
            });
        } else if (data.code === 'save-button') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = data.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = data.formBuilderConfig;
            let stazioneAppaltante: string = data.stazioneAppaltante;
            let syscon: number = data.syscon;
            let messagesPanel: HTMLElement = data.messagesPanel;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                // controllo la validita' del modello
                let valid: boolean = this.modificaAvvisoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: AvvisoUpdateForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon);
                    if (isEmpty(request.existingDocuments) && isEmpty(request.newDocuments)) {
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'VALIDATORS.FORM-MODIFICA-DOCUMENTI-NON-PRESENTI'
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    } else {
                        let modificaAvvisoFactory = this.modificaAvvisoFactory(request);
                        return this.requestHelper.begin(modificaAvvisoFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {
                            let idAvviso: number = request.numeroAvviso;

                            let setUpdateState: Function = data.setUpdateState;
                            if (isFunction(setUpdateState)) {
                                setUpdateState(false);
                            }

                            let params: IDictionary<any> = {
                                idAvviso,
                                stazioneAppaltante
                            }
                            this.routerService.navigateToPage('dettaglio-avviso-page', params);
                            return {
                                ...result,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            }
                        }));
                    }
                } else {
                    this.scrollToMessagePanel(messagesPanel);
                }
            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-MODIFICA-NON-COMPILATA'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            }
        }
        return of(undefined);
    }

    private modificaAvvisoFactory(updateForm: AvvisoInsertForm) {
        return () => {
            return this.avvisiService.modificaAvviso(updateForm)
        }
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string, syscon: number): AvvisoInsertForm {
        let request: AvvisoInsertForm = this.getDefaultAvvisoInsertForm(stazioneAppaltante, syscon);
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

    private getDefaultAvvisoInsertForm(stazioneAppaltante: string, syscon: number): AvvisoInsertForm {
        return {
            codSistema: 1,
            stazioneAppaltante,
            syscon
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: AvvisoInsertForm): AvvisoInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'DOCUMENT') {
                if (field.url || field.file) {
                    let documents = get(request, field.mappingOutput);
                    if (isUndefined(documents)) {
                        documents = new Array();
                    }
                    if (!isEmpty(field.title)) {
                        let document: AvvisoDocumentInsert = {
                            binary: field.fileSwitch === true ? field.file : '',
                            url: isUndefined(field.fileSwitch) || field.fileSwitch === false ? field.url : undefined,
                            titolo: field.title,
                            tipoFile: field.tipoFile
                        };
                        documents.push(document);
                    }
                    set(request, field.mappingOutput, documents);
                }
            } else if (field.type === 'DOCUMENTS-LIST') {
                let documents = get(request, field.mappingOutput);
                if (isUndefined(documents)) {
                    documents = new Array();
                }
                each(field.documents, (one: SdkDocumentItem) => {
                    let document: DocumentoAvviso = {
                        ...one
                    };
                    set(document, 'code', undefined);
                    documents.push(document);
                });
                set(request, field.mappingOutput, documents);
            } else if (field.type === 'AUTOCOMPLETE') {
                if (field.data != null) {
                    let item: SdkAutocompleteItem = get(field, 'data');
                    set(request, field.mappingOutput, item._key);
                }
            } else if (field.type === 'COMBOBOX') {
                let item: SdkComboBoxItem = get(field, 'data');
                if (isObject(item)) {
                    set(request, field.mappingOutput, item.key);
                }
            } else if (field.type === 'DATEPICKER') {
                let data: Date = get(field, 'data');
                if (isObject(data)) {
                    let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                    set(request, field.mappingOutput, formatted);
                }
            } else {
                if (!isUndefined(field.data)) {
                    set(request, field.mappingOutput, field.data);
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: AvvisoInsertForm): AvvisoInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: AvvisoInsertForm): AvvisoInsertForm {
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

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get avvisiService(): AvvisiService { return this.injectable(AvvisiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get modificaAvvisoValidationUtilsService(): ModificaAvvisoValidationUtilsService { return this.injectable(ModificaAvvisoValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }
    // #endregion

}
