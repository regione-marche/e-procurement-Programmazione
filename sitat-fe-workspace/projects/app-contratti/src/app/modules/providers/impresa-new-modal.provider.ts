import { Injectable, Injector } from '@angular/core';
import { ArchivioImpreseService, HttpRequestHelper, ImpresaInsertForm } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkStoreService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isNull, isObject, isUndefined, set } from 'lodash-es';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { NuovoAvvisoValidationUtilsService } from '../services/utils/nuovo-avviso-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class ImpresaNewModalProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        let data: IDictionary<any> = get(args, 'data');
        if (data != null) {
            if (data.code === 'save-button') {
                let defaultFormBuilderConfig: SdkFormBuilderConfiguration = data.defaultFormBuilderConfig;
                let formBuilderConfig: SdkFormBuilderConfiguration = data.formBuilderConfig;
                let messagesPanel: HTMLElement = data.messagesPanel;
                let currentCodice: string = data.currentCodice;
                let stazioneAppaltante: string = data.stazioneAppaltante;
                // controllo che il modello sia cambiato dal default
                if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                    formBuilderConfig.fields.forEach(sezione => {
                        if(sezione.code == 'indirizzo-data'){
                            let nazioneCode = undefined;
                            sezione.fieldSections.forEach(field => {
                                if(field.code == 'nazione' && isObject(field.data)){
                                    nazioneCode = get(field.data, 'key')
                                }
                            });
                            if(!isEmpty(nazioneCode) && nazioneCode != '1'){
                                sezione.fieldSections.forEach(field => {
                                    if(field.code == 'comune'){
                                        let item: SdkAutocompleteItem = {
                                            _key: '-',
                                            text: '-'
                                        };
                                        field.data = item;
                                    }
                                });
                            }
                        }
                    });
                    // controllo la validita' del modello
                    let valid: boolean = this.nuovoAvvisoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                    if (valid === true) {
                        // genero l'oggetto di richiesta
                        let request: ImpresaInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                        let impresaFactory: Function = undefined;

                        let update: boolean = false;

                        if (!isEmpty(currentCodice)) {
                            // Update
                            request.codiceImpresa = currentCodice;
                            impresaFactory = this.updateImpresaFactory(request);
                            update = true;
                        } else {
                            impresaFactory = this.inserisciImpresaFactory(request);
                        }

                        return this.requestHelper.begin(impresaFactory, messagesPanel).pipe(
                            map((result: any) => {

                                if (!isEmpty(result)) {
                                    request.codiceImpresa = update === true ? request.codiceImpresa : result;
                                }
                                return {
                                    data: request,
                                    close: true
                                }
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
        }
        return of(undefined);
    }

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        // siamo nel modale
        try {
            let elem: Element = document.getElementsByClassName('sdk-modal-container')[0];
            if (elem != null) {
                let ofTop: number = messagesPanel.offsetTop > 100 ? messagesPanel.offsetTop : 100;
                elem.scrollTo({
                    top: ofTop - 100,
                    left: 0,
                    behavior: 'auto'
                });
            }
        } catch (e) {
            messagesPanel.scrollIntoView();
        }
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string): ImpresaInsertForm {
        let request: ImpresaInsertForm = this.getDefaultImpresaInsertForm(stazioneAppaltante);
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

    private getDefaultImpresaInsertForm(stazioneAppaltante: string): ImpresaInsertForm {
        return {
            stazioneAppaltante
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: ImpresaInsertForm): ImpresaInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput) && field.data != null) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else if (field.code === 'comune') {
                set(request, field.mappingOutput, field.data.descrizione);
            } else if (field.code === 'cognome-legale' || field.code === 'nome-legale' || field.code === 'codice-fiscale-legale') {
                if (isUndefined(request.rappresentante) || isNull(request.rappresentante)) {
                    request.rappresentante = {};
                }
                let path: string = `rappresentante.${field.mappingOutput}`;
                set(request, path, field.data);
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: ImpresaInsertForm): ImpresaInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: ImpresaInsertForm): ImpresaInsertForm {
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

    private inserisciImpresaFactory(insertForm: ImpresaInsertForm) {
        return () => {
            return this.archivioImpreseService.creaImpresa(environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    private updateImpresaFactory(insertForm: ImpresaInsertForm) {
        return () => {
            return this.archivioImpreseService.updateImpresa(environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAvvisoValidationUtilsService(): NuovoAvvisoValidationUtilsService { return this.injectable(NuovoAvvisoValidationUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}