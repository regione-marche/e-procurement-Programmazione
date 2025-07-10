import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, SdkStoreService } from '@maggioli/sdk-commons';

import { Observable, Observer, catchError, map, throwError } from 'rxjs';
import { GestioneModelliService } from '../services/gestione-modelli.service';
import { SdkCheckboxItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration, SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { each, get, isEqual, isObject, isUndefined, set, isEmpty, map as mapArray, isFunction } from 'lodash-es';
import { GestioneModelliValidationUtilsService } from '../utils/gestione-modelli-validation-utils.service';
import { SdkDettaglioParametroStoreService } from '../components/sdk-dettaglio-parametro/sdk-dettaglio-parametro-store.service';



@Injectable()
export class SdkParametroProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        this.logger.debug('SdkModelloProvider', args);
        if (args.action === 'DELETE') {
            return this.deleteParametro(args);
        }
        else if (args.action === 'MOVE') {
            return this.moveParametro(args);
        }
        else if (args.buttonCode === 'go-to-update-parametro') {

            this.sdkDettaglioParametroStoreService.clear()
            this.sdkDettaglioParametroStoreService.idModello = args.idModello;
            this.sdkDettaglioParametroStoreService.idParametro = args.idParametro;
            let params: IDictionary<any> = {
                idModello: args.idModello,
                idParametro: args.idParametro
            };

            this.routerService.navigateToPage('modifica-parametro-page', params);
        } else if (args.buttonCode === 'save-parametro') {
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
                    form.idModello = args.idModello;
                    let servizio: Observable<any>;
                    if (isUpdate) {
                        form.idParametro = args.idParametro;
                        servizio = this.gestioneModelliService.updateParametroModello(form);
                    } else {
                        servizio = this.gestioneModelliService.insertParametroModello(form);
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
                                let idModello = args.idModello;
                                let idParametro = result.data ? result.data : args.idParametro;
                                this.sdkDettaglioParametroStoreService.clear();
                                this.sdkDettaglioParametroStoreService.idModello = idModello;
                                this.sdkDettaglioParametroStoreService.idParametro = idParametro;

                                let params: IDictionary<any> = {
                                    idModello,
                                    idParametro
                                };

                                this.routerService.navigateToPage('dettaglio-parametro-page', params);
                            }

                            return {
                                ...result.response,
                                defaultFormBuilderConfig,
                                formBuilderConfig
                            };
                        }),
                        catchError((error: any, caught: Observable<any>) => {
                            let errorData = `ERRORS.UNEXPECTED-ERROR`;
                            let errorParam = ''
                            if (isObject(error) && isObject(get(error, 'error'))) {
                                let errorObject: any = get(error, 'error')
                                errorData = 'ERRORS.' + errorObject.errorData ;
                                errorParam = this.getErrorTipo(errorObject.errorDataParameters) || ''
                            }

                            this.sdkMessagePanelService.showError(messagesPanel, [
                                errorParam ? {
                                    message: errorData,
                                    messageParams: { tipo: errorParam }
                                }
                                : {
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

    private deleteParametro(args): Observable<any> {
        let setUpdateState: Function = args.setUpdateState;
        let idModello = args.idModello;
        let idParametro = args.idParametro;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        return this.gestioneModelliService.deleteParametro(idModello, idParametro).pipe(
            map((result) => { return { reload: true } })
        );
    }
    private moveParametro(args): Observable<any> {
        let setUpdateState: Function = args.setUpdateState;
        let idModello = args.idModello;
        let idParametro = args.idParametro;
        let direction = args.direction;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        return this.gestioneModelliService.moveParametro(idModello, idParametro, direction).pipe(
            map((result) => { return { reload: true } })
        );
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

private getErrorTipo(errorParams: string[] | null): string {
    if (!errorParams || !Array.isArray(errorParams) || errorParams.length === 0) {
        return '';
    }
    
    const key = errorParams[0];
    switch (key) {
        case 'U': return 'Identificativo utente'
        case 'UI': return 'Ufficio intestatario'
        default: return ''
    }
}

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkDettaglioParametroStoreService(): SdkDettaglioParametroStoreService { return this.injectable(SdkDettaglioParametroStoreService) }

    private get gestioneModelliService(): GestioneModelliService { return this.injectable(GestioneModelliService) }

    private get gestioneModelliValidationService(): GestioneModelliValidationUtilsService { return this.injectable(GestioneModelliValidationUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }


    // #endregion

}




