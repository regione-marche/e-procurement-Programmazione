import { Injectable, Injector } from '@angular/core';
import { ArchivioCentriDiCostoService, CentroDiCostoInsertForm, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { CentriCostoEntry } from '../../models/gare/gare.model';
import { NuovoAvvisoValidationUtilsService } from '../../services/utils/nuovo-avviso-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class CdcModalProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('CdcModalProvider >>>', args);

        let data: IDictionary<any> = get(args, 'data');

        if (isObject(data)) {
            if (get(data, 'code') === 'save-cdc') {
                let defaultFormBuilderConfig: SdkFormBuilderConfiguration = get(data, 'defaultFormBuilderConfig');
                let formBuilderConfig: SdkFormBuilderConfiguration = get(data, 'formBuilderConfig');
                let messagesPanel: HTMLElement = get(data, 'messagesPanel');
                let centroDiCosto: CentriCostoEntry = get(data, 'centroDiCosto');
                let stazioneAppaltante: string = get(data, 'stazioneAppaltante');
                // controllo che il modello sia cambiato dal default
                if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                    // controllo la validita' del modello
                    let valid: boolean = this.nuovoAvvisoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                    if (valid === true) {
                        // genero l'oggetto di richiesta
                        let request: CentroDiCostoInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                        let cdcFactory: Function = this.updateCdcFactory(request);
                        request.id = centroDiCosto.id;

                        return this.requestHelper.begin(cdcFactory, messagesPanel).pipe(
                            map((result: any) => {
                                return {
                                    ...result,
                                    data: {
                                        id: request.id,
                                        codiceCDC: request.codiceCentro,
                                        nominativoCDC: request.denominazione,
                                        note: request.note,
                                        tipologia: request.tipologia
                                    },
                                    close: true
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string): CentroDiCostoInsertForm {
        let request: CentroDiCostoInsertForm = this.getDefaultCdcInsertForm(stazioneAppaltante);
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

    private getDefaultCdcInsertForm(stazioneAppaltante: string): CentroDiCostoInsertForm {
        return {
            stazioneAppaltante
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: CentroDiCostoInsertForm): CentroDiCostoInsertForm {
        if (isObject(field) && !isEmpty(field.mappingOutput) && !isUndefined(field.data)) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: CentroDiCostoInsertForm): CentroDiCostoInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: CentroDiCostoInsertForm): CentroDiCostoInsertForm {
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

    private updateCdcFactory(insertForm: CentroDiCostoInsertForm) {
        return () => {
            return this.archivioCdcService.updateCdc(environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioCdcService(): ArchivioCentriDiCostoService { return this.injectable(ArchivioCentriDiCostoService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAvvisoValidationUtilsService(): NuovoAvvisoValidationUtilsService { return this.injectable(NuovoAvvisoValidationUtilsService) }

    // #endregion

}
