import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { DettaglioAttoStoreService } from '../../layout/components/business/atti/dettaglio-atto-store.service';
import { AllegatoEntry, AllegatoMotivazioneEntry } from '../../models/atti-generali/atti-generali.model';
import { AttoInsertForm, AttoUpdateForm } from '../../models/gare/gare.model';
import { GareService } from '../../services/gare/gare.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class AttoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('AttoProvider >>>', args);

        if (args.buttonCode === 'save-atto') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let codGara: string = args.codGara;
            let tipoDocumento: string = args.tipoDocumento;
            let numPubb: string = args.numPubb;
            let campiVisibili: string = args.campiVisibili;
            let campiObbligatori: string = args.campiObbligatori;
            let daAnnullare: Array<AllegatoMotivazioneEntry> = args.daAnnullare;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: AttoInsertForm | AttoUpdateForm = this.populateRequest(formBuilderConfig, codGara, tipoDocumento);

                    request = {
                        ...request,
                        allegatiDaAnnullare: daAnnullare
                    }

                    let factory: Function = undefined;

                    let update: boolean = false;

                    if(isEmpty(request.updateDocuments) && isEmpty(request.documents)) {
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'VALIDATORS.FORM-MODIFICA-DOCUMENTI-NON-PRESENTI'
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    }
                    else {
                        if (!isEmpty(numPubb)) {
                            // Update
                            request.numPubb = +numPubb;
                            factory = this.updateAttoFactory(request);
                            update = true;
                        } else {
                            factory = this.inserisciAttoFactory(request);
                        }

                        return this.requestHelper.begin(factory, messagesPanel).pipe(
                            map((result: any) => {

                                setUpdateState(false);

                                this.dettaglioAttoStore.clear();
                                this.dettaglioAttoStore.codGara = args.codGara;
                                this.dettaglioAttoStore.tipoDocumento = args.tipoDocumento;
                                this.dettaglioAttoStore.numPubb = update === true ? request.numPubb : result;
                                this.dettaglioAttoStore.campiObbligatori = args.campiObbligatori;
                                this.dettaglioAttoStore.campiVisibili = args.campiVisibili;
                                this.dettaglioAttoStore.attiMap = args.attiMap;
                                let params: IDictionary<any> = {
                                    codGara: args.codGara,
                                    tipoDocumento: args.tipoDocumento,
                                    numPubb: update === true ? request.numPubb : result,
                                    campiVisibili: args.campiVisibili,
                                    campiObbligatori: args.campiObbligatori
                                };
                                if (args.codLotto != null) {
                                    this.dettaglioAttoStore.codLotto = args.codLotto;
                                    params.codLotto = args.codLotto;
                                }
                                this.routerService.navigateToPage('dettaglio-atto-page', params);
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

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, tipoDocumento: string): AttoInsertForm | AttoUpdateForm {
        let request: AttoInsertForm | AttoUpdateForm = this.getDefaultAttoInsertForm(codGara, tipoDocumento);
        each(formBuilderConfig.fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION') {
                request = this.elaborateSection(field, request, codGara);
            } else if (field.type === 'FORM-GROUP') {
                request = this.elaborateGroup(field, request, codGara);
            } else {
                request = this.elaborateOne(field, request, codGara);
            }
        });
        return request;
    }

    private getDefaultAttoInsertForm(codGara: string, tipoDocumento: string): AttoInsertForm | AttoUpdateForm {
        let request: AttoInsertForm | AttoUpdateForm = {
            codGara: +codGara,
            tipDoc: +tipoDocumento,
            documents: []
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: AttoInsertForm | AttoUpdateForm, codGara: string): AttoInsertForm | AttoUpdateForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'COMBOBOX') {
                if (!isUndefined(field.data)) {
                    set(request, field.mappingOutput, field.data.key);
                }
            } else if (field.type === 'DOCUMENT') {
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
            } else if (field.type === 'DATEPICKER') {
                if (!isUndefined(field.data)) {
                    let data: Date = get(field, 'data');
                    if (isObject(data)) {
                        let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                        set(request, field.mappingOutput, formatted);
                    }
                }
            } else {
                if (!isUndefined(field.data)) {
                    set(request, field.mappingOutput, field.data);
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: AttoInsertForm | AttoUpdateForm, codGara: string): AttoInsertForm | AttoUpdateForm {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request, codGara);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request, codGara);
            } else {
                request = this.elaborateOne(one, request, codGara);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: AttoInsertForm | AttoUpdateForm, codGara: string): AttoInsertForm | AttoUpdateForm {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    request = this.elaborateSection(one, request, codGara);
                } else if (one.type === 'FORM-GROUP') {
                    request = this.elaborateGroup(one, request, codGara);
                } else {
                    request = this.elaborateOne(one, request, codGara);
                }
            });
            field.fieldGroups[index] = group;
        });
        return request;
    }

    private inserisciAttoFactory(insertForm: AttoInsertForm) {
        return () => {
            return this.gareService.insertAtto(insertForm);
        }
    }

    private updateAttoFactory(insertForm: AttoUpdateForm) {
        return () => {
            return this.gareService.updateAtto(insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get dettaglioAttoStore(): DettaglioAttoStoreService { return this.injectable(DettaglioAttoStoreService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion
}
