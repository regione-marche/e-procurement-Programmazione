import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, SdkStoreService } from '@maggioli/sdk-commons';

import { Observable, Observer, catchError, map, throwError } from 'rxjs';
import { SdkDettaglioModelloStoreService } from '../components/sdk-dettaglio-modello/sdk-dettaglio-modello-store.service';
import { GestioneModelliService } from '../services/gestione-modelli.service';
import { SdkCheckboxItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration, SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { each, get, isEqual, isObject, isUndefined, set, isEmpty, map as mapArray } from 'lodash-es';
import { GestioneModelliValidationUtilsService } from '../utils/gestione-modelli-validation-utils.service';



@Injectable()
export class SdkModelloProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        this.logger.debug('SdkModelloProvider', args);
        if (args.buttonCode === 'go-to-update-modello') {

            this.sdkDettaglioModelloStoreService.clear();
            this.sdkDettaglioModelloStoreService.idModello = args.idModello;

            let params: IDictionary<any> = {
                idModello: args.idModello
            };

            this.routerService.navigateToPage('modifica-modello-page', params);
        }
        if (args.buttonCode === 'save-modello') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                // controllo la validita' del modello
                let valid: boolean = this.gestioneModelliValidationService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {

                    
                    let isUpdate = args.isUpdate;

                    // genero l'oggetto di richiesta

                    let form = this.populateRequest(formBuilderConfig, isUpdate);
                    form.syscon = args.syscon;
                    form.idProfilo = args.idProfilo;
                    let servizio: Observable<any>;
                    if (isUpdate) {
                        let fieldExistingDoc = formBuilderConfig.fields[0].fieldSections[8];
                        if (form.file == null && fieldExistingDoc.visible === false) {
                            let messages: Array<any> = [];
                            messages.push({
                                message: `SDK-MODELLO.VALIDATORS.DOCUMENTS-MODELLO-MANDATORY`
                            })
                            this.sdkMessagePanelService.showError(messagesPanel, messages);
                            this.scrollToMessagePanel(messagesPanel);
                            return new Observable((ob: Observer<IDictionary<any>>) => {
                                ob.next(args);
                                ob.complete();
                            });
                        } 
                        if(form.file == null){

                        }


                        form.idModello = args.idModello;
                        servizio = this.gestioneModelliService.updateModello(form);
                    } else {
                        servizio = this.gestioneModelliService.insertModello(form);
                    }

                    return servizio.pipe(
                        map((result: any) => {

                            if (result.esito === false) {
                                let messages: Array<any> = [];
                                messages.push({
                                    message: result.errorData
                                })
                                this.sdkMessagePanelService.showError(messagesPanel, messages);
                                this.scrollToMessagePanel(messagesPanel);
                            } else {
                                setUpdateState(false);

                                this.sdkDettaglioModelloStoreService.clear();
                                this.sdkDettaglioModelloStoreService.idModello = result.data;

                                let params: IDictionary<any> = {
                                    idModello: result.data
                                };

                                this.routerService.navigateToPage('dettaglio-modello-page', params);
                            }

                            return {
                                ...result.response,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            };
                        }),
                        catchError((error: any, caught: Observable<any>) => {
                            let errorData = `ERRORS.UNEXPECTED-ERROR`;
                            if (isObject(error) && isObject(get(error, 'error'))) {
                                let errorObject: any = get(error, 'error')
                                errorData = 'ERRORS.' + errorObject.errorData;
                            }
                            this.sdkMessagePanelService.showError(messagesPanel, [
                                {
                                    message: errorData
                                }
                            ]);
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


    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, update: boolean) {
        let request: any = this.getDefaultForm(update);
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

    private getDefaultForm(update: boolean) {
        return {};
    }

    private elaborateOne(field: SdkFormBuilderField, request) {
        if (isObject(field) && !isEmpty(field.mappingOutput) && (!isUndefined(field.data) || !isUndefined(field.file))) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else if (field.type === 'DATEPICKER') {
                if (field.data != null) {
                    let data: Date = get(field, 'data');
                    if (isObject(data)) {
                        set(request, field.mappingOutput, data.getTime());
                    }
                }
            } else if (field.type === 'CHECKBOX') {
                if (field.data != null) {
                    const data: Array<SdkCheckboxItem> = field.data;
                    set(request, field.mappingOutput, data);
                }
            } else if (field.type === 'DOCUMENT') {
                if (field.file != null) {
                    set(request, field.mappingOutput, field.file);
                    set(request, 'nomefile', field.fileName);
                }
            } else if (field.type === 'TEXTMODAL') {

                set(request, field.mappingOutput, field.data);

            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request) {
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

    private elaborateGroup(field: SdkFormBuilderField, request) {
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


    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkDettaglioModelloStoreService(): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

    private get gestioneModelliService(): GestioneModelliService { return this.injectable(GestioneModelliService) }

    private get gestioneModelliValidationService(): GestioneModelliValidationUtilsService { return this.injectable(GestioneModelliValidationUtilsService) }

    GestioneModelliValidationUtilsService

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }


    // #endregion

}




