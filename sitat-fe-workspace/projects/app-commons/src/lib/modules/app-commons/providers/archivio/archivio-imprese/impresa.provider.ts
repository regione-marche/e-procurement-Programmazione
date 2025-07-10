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
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isNull, isObject, isUndefined, set, toNumber } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app-commons.constants';
import { ImpresaInsertForm } from '../../../models/archivio/archivio-imprese.models';
import { ArchivioImpreseService } from '../../../services/archivio/archivio-imprese.service';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { CommonValidationUtilsService } from '../../../services/utils/common-validation-utils.service';

@Injectable()
export class ImpresaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('ImpresaProvider >>>', args);

        if (args.buttonCode === 'save-impresa') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let currentCodice: string = args.currentCodice;
            let stazioneAppaltante: string = args.stazioneAppaltante;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello

                let nazioneCode = '1';
                formBuilderConfig.fields.forEach(sezione => {
                    if (sezione.code == 'indirizzo-data') {                    
                        sezione.fieldSections.forEach(field => {
                             if (field.code == 'nazione'  && isObject(field.data)){
                                    nazioneCode = get(field.data, 'key');                               
                            }
                        });
                        if (!isEmpty(nazioneCode) && nazioneCode != '1') {
                            sezione.fieldSections.forEach(field => {
                                if (field.code == 'comune') {
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




                let valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: ImpresaInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante);
                    request.nazione = toNumber(nazioneCode);
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

                            setUpdateState(false);

                            if (update === false) {
                                this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_IMPRESE_DISPATCHER, undefined));
                            }

                            let params: IDictionary<any> = {
                                codiceImpresa: update === true ? request.codiceImpresa : result
                            };
                            this.routerService.navigateToPage('dettaglio-impresa-page', params);
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
        if (isObject(field) && (!isEmpty(field.mappingOutput) && field.code !== 'ambito-territoriale') && field.data != null) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else if (field.code === 'ambito-territoriale') {
                if(isObject(field.data) && get(field.data, 'key') === '1')
                set(request, 'nazione', '1');              
            } else if (field.code === 'comune') {
                set(request, field.mappingOutput, field.data.descrizione);
            } else if (field.code === 'cf-estero') {
                set(request, 'partitaIva', field.data);
                set(request, 'codiceFiscale', field.data);
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
            return this.archivioImpreseService.creaImpresa(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    private updateImpresaFactory(insertForm: ImpresaInsertForm) {
        return () => {
            return this.archivioImpreseService.updateImpresa(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get commonValidationUtilsService(): CommonValidationUtilsService { return this.injectable(CommonValidationUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
