import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { DettaglioGaraStoreService } from '../../layout/components/business/gare/dettaglio-gara-store.service';
import { DettaglioSmartCigStoreService } from '../../layout/components/business/smartcig/dettaglio-smartcig-store.service';
import { GaraInsertForm, GaraUpdateForm } from '../../models/gare/gare.model';
import { GareService } from '../../services/gare/gare.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class GaraProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('GaraProvider >>>', args);

        if (args.buttonCode === 'save-gara') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const codGara: string = args.codGara;
            const isTest: boolean = args.isTest === true;
            const stazioneAppaltante: StazioneAppaltanteInfo = args.stazioneAppaltante;
            const syscon: string = args.syscon;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta

                    let garaFactory: Function;

                    if (codGara == null && isTest === true) {
                        let request: GaraInsertForm = this.populateRequest(formBuilderConfig);
                        request.syscon = +syscon;
                        request.codiceStazioneAppaltante = stazioneAppaltante.codice;
                        garaFactory = this.insertGaraFactory(request);
                    } else if (codGara != null) {
                        let request: GaraUpdateForm = this.populateRequest(formBuilderConfig, codGara) as GaraUpdateForm;
                        if(request.dataLetteraInvito){

                        }
                        garaFactory = this.updateGaraFactory(request);
                    } else {
                        return new Observable((ob: Observer<IDictionary<any>>) => {
                            ob.next(args);
                            ob.complete();
                        });
                    }

                    return this.requestHelper.begin(garaFactory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            let codGaraResult = codGara || result.data;

                            this.dettaglioSmartCigStoreService.clear();
                            this.dettaglioGaraStore.clear();
                            this.dettaglioGaraStore.codGara = codGaraResult;

                            let params: IDictionary<any> = {
                                codGara: codGaraResult
                            };

                            this.routerService.navigateToPage('dettaglio-gara-page', params);
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara?: string): GaraInsertForm | GaraUpdateForm {
        let request: GaraInsertForm | GaraUpdateForm = this.getDefaultGaraForm(codGara);
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

    private getDefaultGaraForm(codGara?: string): GaraInsertForm | GaraUpdateForm {
        if (codGara != null) {
            let request: GaraUpdateForm = {
                codGara: +codGara
            };
            return request;
        } else {
            let request: GaraInsertForm = {};
            return request;
        }
    }

    private elaborateOne(field: SdkFormBuilderField, request: GaraInsertForm | GaraUpdateForm): GaraInsertForm | GaraUpdateForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'COMBOBOX') {
                if (!isUndefined(field.data)) {
                    set(request, field.mappingOutput, field.data.key);
                }
            } else if (field.type === 'DATEPICKER') {
                if (!isUndefined(field.data)) {
                    if(field.code === 'data-lettera-invito'){
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        } else if(!isEmpty(field.data)){
                            const [day, month, year] = field.data.split('/');
                            let data = new Date(+year, +month - 1, +day);
                            if (isObject(data)) {
                                let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                                set(request, field.mappingOutput, formatted);
                            }
                        }
                    } else{
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    }
                    
                }
            } else if (field.type === 'AUTOCOMPLETE') {
                if (field.data != null) {
                    let item: SdkAutocompleteItem = get(field, 'data');
                    set(request, field.mappingOutput, item._key);
                }
            } else {
                if(field.code === 'data-lettera-invito' && !isUndefined(field.data) && field.data != null){
                    const [day, month, year] = field.data.split('/');
                    let data = new Date(+year, +month - 1, +day);
                    if (isObject(data)) {
                        let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                        set(request, field.mappingOutput, formatted);
                    }
                } else{
                    if (!isUndefined(field.data) && field.data != null) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
                
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: GaraInsertForm | GaraUpdateForm): GaraInsertForm | GaraUpdateForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: GaraInsertForm | GaraUpdateForm): GaraInsertForm | GaraUpdateForm {
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

    private updateGaraFactory(insertForm: GaraUpdateForm) {
        return () => {
            return this.gareService.updateGara(insertForm);
        }
    }

    private insertGaraFactory(insertForm: GaraInsertForm) {
        return () => {
            return this.gareService.insertGara(insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get dettaglioGaraStore(): DettaglioGaraStoreService { return this.injectable(DettaglioGaraStoreService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get dettaglioSmartCigStoreService(): DettaglioSmartCigStoreService { return this.injectable(DettaglioSmartCigStoreService) }

    // #endregion
}
