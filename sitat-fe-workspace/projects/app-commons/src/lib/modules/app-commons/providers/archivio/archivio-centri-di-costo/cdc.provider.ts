import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app-commons.constants';
import { CentroDiCostoInsertForm } from '../../../models/archivio/archivio-centri-di-costo.models';
import { ArchivioCentriDiCostoService } from '../../../services/archivio/archivio-centri-di-costo.service';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { CommonValidationUtilsService } from '../../../services/utils/common-validation-utils.service';

@Injectable()
export class CdcProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('CdcProvider >>>', args);

        if (args.buttonCode === 'save-cdc') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let currentCodice: string = args.currentCodice;
            let stazioneAppaltante: string = args.stazioneAppaltante;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: CentroDiCostoInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                    let cdcFactory: Function = undefined;

                    let update: boolean = false;

                    if (!isEmpty(currentCodice)) {
                        // Update
                        request.id = +currentCodice;
                        cdcFactory = this.updateCdcFactory(request);
                        update = true;
                    } else {
                        cdcFactory = this.inserisciCdcFactory(request);
                    }

                    return this.requestHelper.begin(cdcFactory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            if (update === false) {
                                this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_CDC_DISPATCHER, undefined));
                            }

                            let params: IDictionary<any> = {
                                idCentro: update === true ? request.id : result
                            };
                            this.routerService.navigateToPage('dettaglio-cdc-page', params);
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

    private inserisciCdcFactory(insertForm: CentroDiCostoInsertForm) {
        return () => {
            return this.archivioCdcService.creaCdc(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    private updateCdcFactory(insertForm: CentroDiCostoInsertForm) {
        return () => {
            return this.archivioCdcService.updateCdc(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioCdcService(): ArchivioCentriDiCostoService { return this.injectable(ArchivioCentriDiCostoService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get commonValidationUtilsService(): CommonValidationUtilsService { return this.injectable(CommonValidationUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
