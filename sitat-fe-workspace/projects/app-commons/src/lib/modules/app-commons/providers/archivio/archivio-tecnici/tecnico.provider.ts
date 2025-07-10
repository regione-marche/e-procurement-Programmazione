import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { RupInsertForm } from '../../../models/tecnico/tecnico.model';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../services/tabellati/tabellati.service';
import { CommonValidationUtilsService } from '../../../services/utils/common-validation-utils.service';

@Injectable()
export class TecnicoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('TecnicoProvider >>>', args);

        if (args.buttonCode === 'save-tecnico') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let stazioneAppaltante: string = args.stazioneAppaltante;
            let setUpdateState: Function = args.setUpdateState;
            let currentCodice: string = args.currentCodice;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: RupInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);

                    if (!isEmpty(currentCodice)) {
                        // Update
                        request.codice = currentCodice;
                    }

                    let inserisciTecnicoFactory = this.inserisciTecnicoFactory(request);
                    return this.requestHelper.begin(inserisciTecnicoFactory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            let params: IDictionary<any> = {
                                codice: result
                            };
                            this.routerService.navigateToPage('dettaglio-tecnico-page', params);
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string): RupInsertForm {
        let request: RupInsertForm = this.getDefaultTecnicoInsertForm(stazioneAppaltante);
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

    private getDefaultTecnicoInsertForm(stazioneAppaltante: string): RupInsertForm {
        return {
            stazioneAppaltante
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: RupInsertForm): RupInsertForm {
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

    private elaborateSection(field: SdkFormBuilderField, request: RupInsertForm): RupInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: RupInsertForm): RupInsertForm {
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

    private inserisciTecnicoFactory(insertForm: RupInsertForm) {
        return () => {
            return this.tabellatiService.creaTecnico(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get commonValidationUtilsService(): CommonValidationUtilsService { return this.injectable(CommonValidationUtilsService) }

    // #endregion

}
