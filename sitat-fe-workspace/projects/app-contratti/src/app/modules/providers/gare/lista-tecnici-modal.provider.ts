import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';

import { NuovoAvvisoValidationUtilsService } from '../../services/utils/nuovo-avviso-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class ListaTecniciModalProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('ListaTecniciModalProvider >>>', args);

        let data: IDictionary<any> = get(args, 'data');

        if (isObject(data)) {
            if (get(data, 'code') === 'choose-tecnico') {
                let defaultFormBuilderConfig: SdkFormBuilderConfiguration = get(data, 'defaultFormBuilderConfig');
                let formBuilderConfig: SdkFormBuilderConfiguration = get(data, 'formBuilderConfig');
                let messagesPanel: HTMLElement = get(data, 'messagesPanel');
                // controllo che il modello sia cambiato dal default
                if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                    // controllo la validita' del modello
                    let valid: boolean = this.nuovoAvvisoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                    if (valid === true) {
                        // genero l'oggetto di richiesta
                        let request: IDictionary<any> = this.populateRequest(formBuilderConfig);
                        return new Observable((ob: Observer<IDictionary<any>>) => {
                            ob.next({
                                data: request.tecnico,
                                close: true
                            });
                            ob.complete();
                        });
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

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): IDictionary<any> {
        let request: IDictionary<any> = this.getDefaultForm();
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

    private getDefaultForm(): IDictionary<any> {
        return {};
    }

    private elaborateOne(field: SdkFormBuilderField, request: IDictionary<any>): IDictionary<any> {
        if (isObject(field) && !isEmpty(field.mappingOutput) && !isUndefined(field.data)) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: IDictionary<any>): IDictionary<any> {
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

    private elaborateGroup(field: SdkFormBuilderField, request: IDictionary<any>): IDictionary<any> {
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

    // #endregion

    // #region Getters

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAvvisoValidationUtilsService(): NuovoAvvisoValidationUtilsService { return this.injectable(NuovoAvvisoValidationUtilsService) }

    // #endregion

}
