import { Injectable, Injector } from '@angular/core';
import {
    HttpRequestHelper,
    StazioneAppaltanteInfo,
    TabellatiService,
    Ufficio,
    UfficioInsertForm,
} from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { NuovoAvvisoValidationUtilsService } from '../../services/utils/nuovo-avviso-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class UfficiNewModalProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }


    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        let data: IDictionary<any> = get(args, 'data');
        if (isObject(data)) {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = get(data, 'defaultFormBuilderConfig');
            let formBuilderConfig: SdkFormBuilderConfiguration = get(data, 'formBuilderConfig');
            let stazioneAppaltante: StazioneAppaltanteInfo = get(data, 'stazioneAppaltante');
            let messagesPanel: HTMLElement = get(data, 'messagesPanel');
            let selectedItem: Ufficio = get(data, 'selectedItem');

            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                let valid: boolean = this.nuovoAvvisoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: UfficioInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);
                    if (isObject(selectedItem)) {
                        request.id = selectedItem.id;
                    }
                    let inserisciUfficioFactory = this.inserisciUfficioFactory(request);
                    return this.requestHelper.begin(inserisciUfficioFactory, messagesPanel).pipe(
                        map((result: string) => {
                            if (!isEmpty(result)) {
                                request.id = result;
                            }
                            return {
                                data: request,
                                close: true
                            }
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
        return of(undefined);
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: StazioneAppaltanteInfo): UfficioInsertForm {
        let request: UfficioInsertForm = this.getDefaultUfficioInsertForm(stazioneAppaltante);
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

    private elaborateOne(field: SdkFormBuilderField, request: UfficioInsertForm): UfficioInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput) && !isUndefined(field.data)) {
            set(request, field.mappingOutput, field.data);
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: UfficioInsertForm): UfficioInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: UfficioInsertForm): UfficioInsertForm {
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


    private getDefaultUfficioInsertForm(stazioneAppaltante: StazioneAppaltanteInfo): UfficioInsertForm {
        return {
            stazioneAppaltante: stazioneAppaltante.codice,
        };
    }

    private inserisciUfficioFactory(insertForm: UfficioInsertForm) {
        return () => {
            return this.tabellatiService.creaUfficio(environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAvvisoValidationUtilsService(): NuovoAvvisoValidationUtilsService { return this.injectable(NuovoAvvisoValidationUtilsService) }

    // #endregion
}