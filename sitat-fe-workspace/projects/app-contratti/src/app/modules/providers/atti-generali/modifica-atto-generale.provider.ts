import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, ResponseResult, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkComboBoxItem,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { BehaviorSubject, catchError, map, Observable, Observer, of, throwError } from 'rxjs';

import { Constants } from '../../../app.constants';
import { AllegatoEntry, AllegatoMotivazioneEntry, AttoGeneraleEntry, AttoGeneraleInsertForm, AttoGeneraleUpdateForm, RicercaAttiGeneraliForm } from '../../models/atti-generali/atti-generali.model';
import { AttiGeneraliService } from '../../services/atti-generali/atti-generali.service';
import { ModificaAttoGeneraleValidationUtilsService } from '../../services/utils/modifica-atto-generale-validation-utils.service';

export interface ListaAttiGeneraliProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    idAtto?: string;
    stazioneAppaltante?: StazioneAppaltanteInfo;
    searchForm?: RicercaAttiGeneraliForm;
    setUpdateState?: Function;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    syscon?: number;
    dialogConfigSubj?: BehaviorSubject<SdkDialogConfig>;
    nomein?: string;
    daAnnullare?: Array<AllegatoMotivazioneEntry>;
}

@Injectable({ providedIn: 'root' })
export class ModificaAttoGeneraleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ModificaAttoGeneraleProvider', args);

        if(!isEmpty(args.buttonCode)){
            return this.handleButtons(args);
        }
        
        return of(args);
    }

    private handleButtons(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {
        if (args.buttonCode === 'salva-atto') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let stazioneAppaltante: string = args.nomein;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let idAtto: string = args.idAtto;
            let daAnnullare: Array<AllegatoMotivazioneEntry> = args.daAnnullare;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                // controllo la validita' del modello
                let valid: boolean = this.modificaAttoGeneraleValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: AttoGeneraleUpdateForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                    request = {
                        ...request,
                        allegatiDaAnnullare: daAnnullare
                    }

                    if (isEmpty(request.updateDocuments) && isEmpty(request.documents)) {
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'VALIDATORS.FORM-MODIFICA-DOCUMENTI-NON-PRESENTI'
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    } else {

                        request = {
                            ...request,
                            idAtto: idAtto
                        }
                        let modificaAttoGeneraleFactory = this.modificaAttoGeneraleFactory(request);
                        return this.requestHelper.begin(modificaAttoGeneraleFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {

                            let setUpdateState: Function = args.setUpdateState;
                            if (isFunction(setUpdateState)) {
                                setUpdateState(false);
                            }

                            let params: IDictionary<any> = {
                                idAtto,
                                modifica: 'S',
                                stazioneAppaltante
                            }
                            this.routerService.navigateToPage('dettaglio-atto-generale-page', params);
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
        else if(args.buttonCode === 'salva-nuovo-atto') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let stazioneAppaltante: string = args.stazioneAppaltante.codice;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.modificaAttoGeneraleValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: AttoGeneraleInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                    if (isEmpty(request.updateDocuments)) {
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'VALIDATORS.FORM-INSERIMENTO-NUOVO-ALLEGATO'
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    } else {

                        let factory: Function = undefined;

                        factory = this.inserisciAttoGeneraleFactory(request);

                        return this.requestHelper.begin(factory, messagesPanel).pipe(
                            map((result: ResponseResult<AttoGeneraleEntry>) => {

                                setUpdateState(false);

                                //Da testare
                                let params: IDictionary<any> = {
                                    idAtto: +result,
                                    modifica: 'S',
                                    stazioneAppaltante
                                };
                                
                                this.routerService.navigateToPage('dettaglio-atto-generale-page', params);
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
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private modificaAttoGeneraleFactory(updateForm: AttoGeneraleUpdateForm) {
        return () => {
            return this.attiGeneraliService.modificaAttoGenerale(updateForm)
        }
    }

    private inserisciAttoGeneraleFactory(insertForm: AttoGeneraleInsertForm) {
        return () => {
            return this.attiGeneraliService.creaNuovoAttoGenerale(insertForm)
        }
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string): AttoGeneraleUpdateForm {
        let request: AttoGeneraleUpdateForm = this.getDefaultAttoGeneraleUpdateForm(stazioneAppaltante);
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

    private getDefaultAttoGeneraleUpdateForm(stazioneAppaltante: string): AttoGeneraleUpdateForm {
        return {
            stazioneAppaltante
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: AttoGeneraleUpdateForm): AttoGeneraleUpdateForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'DOCUMENT') {
                if (field.url || field.file) {
                    let documents = get(request, field.mappingOutput);
                    if (isUndefined(documents)) {
                        documents = new Array();
                    }
                    if (!isEmpty(field.title)) {
                        let document: AllegatoEntry = {
                            binary: field.file ?? undefined,
                            url: field.url ?? undefined,
                            descrizione: field.title,
                            tipoFile: field.tipoFile
                        };
                        documents.push(document);
                    }
                    set(request, field.mappingOutput, documents);
                }
            } else if (field.type === 'DOCUMENTS-LIST') {
                let documents = get(field, field.mappingOutput);
                if (isUndefined(documents)) {
                    documents = new Array();
                }
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

    private elaborateSection(field: SdkFormBuilderField, request: AttoGeneraleUpdateForm): AttoGeneraleUpdateForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: AttoGeneraleUpdateForm): AttoGeneraleUpdateForm {
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get modificaAttoGeneraleValidationUtilsService(): ModificaAttoGeneraleValidationUtilsService { return this.injectable(ModificaAttoGeneraleValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get attiGeneraliService(): AttiGeneraliService { return this.injectable(AttiGeneraliService) }

    // #endregion

}
