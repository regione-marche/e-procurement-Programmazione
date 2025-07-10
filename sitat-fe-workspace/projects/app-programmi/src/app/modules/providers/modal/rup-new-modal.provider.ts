import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, StazioneAppaltanteInfo, TabellatiService, Tecnico } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, set } from 'lodash-es';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { NuovoProgrammaValidationUtilsService } from '../../services/utils/nuovo-programma-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class RupNewModalProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        let data: IDictionary<any> = get(args, 'data');
        if (data != null) {
            if (data.code === 'update-button') {
                return of({ update: true });
            } else if (data.code === 'save-button') {
                let defaultFormBuilderConfig: SdkFormBuilderConfiguration = get(data, 'defaultFormBuilderConfig');
                let formBuilderConfig: SdkFormBuilderConfiguration = get(data, 'formBuilderConfig');
                let stazioneAppaltante: StazioneAppaltanteInfo = get(data, 'stazioneAppaltante');
                let messagesPanel: HTMLElement = get(data, 'messagesPanel');
                let selectedItem: Tecnico = get(data, 'selectedItem');

                if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                    let valid: boolean = this.nuovoProgrammaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                    if (valid === true) {
                        // genero l'oggetto di richiesta
                        let request: Tecnico = this.populateRequest(formBuilderConfig, stazioneAppaltante);
                        if (isObject(selectedItem)) {
                            // Update
                            request.codice = selectedItem.codice;
                        }
                        let inserisciTecnicoFactory = this.inserisciTecnicoFactory(request);
                        return this.requestHelper.begin(inserisciTecnicoFactory, messagesPanel).pipe(
                            map((result: any) => {
                                if (!isEmpty(result)) {
                                    request.codice = result;
                                }
                                return {
                                    data: request,
                                    close: true
                                }
                            }),
                            catchError((error: any, caught: Observable<any>) => {
                                messagesPanel.scrollIntoView();
                                return throwError(() => error);
                            })
                        );
                    } else {
                        messagesPanel.scrollIntoView();
                    }
                } else {
                    this.sdkMessagePanelService.showError(messagesPanel, [
                        {
                            message: 'VALIDATORS.FORM-NON-COMPILATA'
                        }
                    ]);
                    messagesPanel.scrollIntoView();
                }
            }
        }
        return of(undefined);
    }

    // #region Private

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: StazioneAppaltanteInfo): Tecnico {
        let request: Tecnico = this.getDefaultTecnicoInsertForm(stazioneAppaltante);
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

    private getDefaultTecnicoInsertForm(stazioneAppaltante: StazioneAppaltanteInfo): Tecnico {
        return {
            stazioneAppaltante: stazioneAppaltante.codice,
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: Tecnico): Tecnico {
        if (isObject(field) && !isEmpty(field.mappingOutput) && field.data != null) {
            if (field.type === 'AUTOCOMPLETE') {
                let item: SdkAutocompleteItem = get(field, 'data');
                set(request, field.mappingOutput, item._key);
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: Tecnico): Tecnico {
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

    private elaborateGroup(field: SdkFormBuilderField, request: Tecnico): Tecnico {
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

    private inserisciTecnicoFactory(insertForm: Tecnico) {
        return () => {
            return this.tabellatiService.creaTecnico(environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }


    // #endregion

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoProgrammaValidationUtilsService(): NuovoProgrammaValidationUtilsService { return this.injectable(NuovoProgrammaValidationUtilsService) }


    // #endregion

}