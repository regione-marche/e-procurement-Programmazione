import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkDocumentItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import {
    DocumentoFaseInsertForm,
    ExistingDocumentoFaseEntry,
    MisureAggiuntivesicurezzaInsertForm,
    MisureAggiuntivesicurezzaUpdateForm,
} from '../../models/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza.models';
import {
    MisureAggiuntiveSicurezzaService,
} from '../../services/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza.service';
import {
    NuoveMisureAggiuntiveSicurezzaValidationUtilsService,
} from '../../services/utils/nuove.mis-agg-sic-validation-utils.service';

export interface MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    codiceFase?: string;
    numeroProgressivo?: string;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    update?: boolean;
}

@Injectable({ providedIn: 'root' })
export class MisureAggiuntiveSicurezzaFaseInizialeSempProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs >>>', args);
        if (args.buttonCode === 'back-to-dettaglio-misure-aggiuntive-sicurezza') {
            return this.dettaglioMisureAggiuntiveSicurezza(args);
        } else if (args.buttonCode === 'go-to-update-misure-aggiuntive-sicurezza') {
            return this.goToUpdateMisureAggiuntiveSicurezza(args);
        } else if (args.buttonCode === 'go-to-nuove-misure-aggiuntive-sicurezza') {
            return this.goToNuoveMisureAggiuntiveSicurezza(args);
        } else if (args.buttonCode === 'save-misure-aggiuntive-sicurezza') {
            return this.saveMisureAggiuntiveSicurezza(args);
        } else if (args.buttonCode === 'delete-misure-aggiuntive-sicurezza') {
            return this.deleteMisureAggiuntiveSicurezza(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        }
        return of(args);
    }

    // #region Private

    private deleteMisureAggiuntiveSicurezza(args: MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs): Observable<IDictionary<any>> {
        let factory = this.deleteFactory(args.codGara, args.codLotto, args.codiceFase, args.numeroProgressivo);
        return this.requestHelper.begin(factory, args.messagesPanel);
    }

    private deleteFactory(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string) {
        return () => {
            return this.misureAggiuntiveSicurezzaService.deleteMisureAggiuntiveSicurezza(codGara, codLotto, codiceFase, numeroProgressivo);
        }
    }

    private dettaglioMisureAggiuntiveSicurezza(args: MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs): Observable<IDictionary<any>> {

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
        this.routerService.navigateToPage('dettaglio-mis-agg-sic-fase-ini-semp-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToUpdateMisureAggiuntiveSicurezza(args: MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('modifica-mis-agg-sic-fase-ini-semp-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToNuoveMisureAggiuntiveSicurezza(args: MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('nuove-mis-agg-sic-fase-ini-semp-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateMisureAggiuntiveSicurezzaFactory(form: MisureAggiuntivesicurezzaUpdateForm) {
        return () => {
            return this.misureAggiuntiveSicurezzaService.updateMisureAggiuntiveSicurezza(form);
        }
    }

    private insertMisureAggiuntiveSicurezzaFactory(form: MisureAggiuntivesicurezzaInsertForm) {
        return () => {
            return this.misureAggiuntiveSicurezzaService.isnertMisureAggiuntiveSicurezza(form);
        }
    }

    private saveMisureAggiuntiveSicurezza(args: MisureAggiuntiveSicurezzaFaseInizialeSempProviderArgs): Observable<any> {

        let codGara: string = args.codGara;
        let codLotto: string = args.codLotto;
        let codiceFase: string = args.codiceFase;
        let numeroProgressivo: string = args.numeroProgressivo;
        let defaultFormBuilderConfig: SdkFormFieldGroupConfiguration = args.defaultFormBuilderConfig;
        let formBuilderConfig: SdkFormFieldGroupConfiguration = args.formBuilderConfig;
        let setUpdateState: Function = args.setUpdateState;
        let messagesPanel: HTMLElement = args.messagesPanel;
        let update: boolean = args.update;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            let valid: boolean = this.nuoveMisureAggiuntiveSicurezzaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm = this.populateRequest(formBuilderConfig, codGara, codLotto, codiceFase, numeroProgressivo);

                let factory: Function;
                if (update === true) {
                    factory = this.updateMisureAggiuntiveSicurezzaFactory(request);
                } else {
                    factory = this.insertMisureAggiuntiveSicurezzaFactory(request);
                }

                return this.requestHelper.begin(factory, messagesPanel).pipe(
                    map((result: any) => {

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            codGara,
                            codLotto,
                            codiceFase,
                            numeroProgressivo
                        };

                        this.routerService.navigateToPage('dettaglio-mis-agg-sic-fase-ini-semp-page', params);
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

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm {
        let request: MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm = this.getDefaultForm(codGara, codLotto, codiceFase, numeroProgressivo);
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

    private getDefaultForm(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm {
        let request: MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm = {
            codGara: +codGara,
            codLotto: +codLotto,
            codFase: +codiceFase,
            numIniz: +numeroProgressivo
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm): MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm {
        if (isObject(field)) {
            if (!isEmpty(field.mappingOutput)) {
                if (field.type === 'COMBOBOX') {
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
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.type === 'DOCUMENT') {
                    if (field.file) {
                        let documents = get(request, field.mappingOutput);
                        if (isUndefined(documents)) {
                            documents = new Array();
                        }
                        let document: DocumentoFaseInsertForm = {
                            binary: field.file,
                            titolo: field.title,
                            tipoFile: field.tipoFile
                        };
                        documents.push(document);
                        set(request, field.mappingOutput, documents);
                    }
                } else if (field.type === 'DOCUMENTS-LIST') {
                    let documents = get(request, field.mappingOutput);
                    if (isUndefined(documents)) {
                        documents = new Array();
                    }
                    each(field.documents, (one: SdkDocumentItem) => {
                        let document: ExistingDocumentoFaseEntry = {
                            ...one,
                            progressivoDocumento: +one.code,
                            titolo: one.titolo,
                            codGara: request.codGara,
                            codLotto: request.codLotto
                        };
                        set(document, 'code', undefined);
                        documents.push(document);
                    });
                    set(request, field.mappingOutput, documents);
                } else {
                    if (field.data != null) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm): MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm): MisureAggiuntivesicurezzaInsertForm | MisureAggiuntivesicurezzaUpdateForm {
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

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get misureAggiuntiveSicurezzaService(): MisureAggiuntiveSicurezzaService { return this.injectable(MisureAggiuntiveSicurezzaService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuoveMisureAggiuntiveSicurezzaValidationUtilsService(): NuoveMisureAggiuntiveSicurezzaValidationUtilsService { return this.injectable(NuoveMisureAggiuntiveSicurezzaValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
