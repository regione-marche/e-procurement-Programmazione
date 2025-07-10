import { Injectable, Injector } from '@angular/core';
import { DynamicValue, HttpRequestHelper, StazioneAppaltanteBaseInfo, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { DettaglioGaraStoreService } from '../../layout/components/business/gare/dettaglio-gara-store.service';
import { DettaglioSmartCigStoreService } from '../../layout/components/business/smartcig/dettaglio-smartcig-store.service';
import { ImportaSmartcigForm } from '../../models/gare/importa-gara.model';
import { SmartCigInsertForm, SmartCigUpdateForm } from '../../models/smartcig/smartcig.model';
import { SmartCigService } from '../../services/smartcig/smartcig.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';
import { environment } from '../../../../environments/environment';

export interface SmartCigProviderArgs extends IDictionary<any> {
    stazioneAppaltante?: StazioneAppaltanteBaseInfo;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    setUpdateState?: Function;
    radioData?: string;
}

@Injectable({ providedIn: 'root' })
export class SmartCigProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SmartCigProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SmartCigProviderArgs >>>', args);
        if (args.buttonCode === 'back-to-home-page') {
            return this.backToHomePage(args);
        } else if (args.buttonCode === 'back-to-gestione-smartcig') {
            return this.backToGestioneSmartcig(args);
        } else if (args.buttonCode === 'back-to-lista-gare') {
            return this.backListaGare(args);
        } else if (args.buttonCode === 'go-to-update-smartcig') {
            return this.goUpdate(args.codGara);
           
        } else if (args.buttonCode === 'go-to-new-smartcig') {
            return this.goNew(args.radioData);
        }else if (args.buttonCode === 'back-to-dettaglio') {
            return this.backDettaglio(args);
        } else if (args.buttonCode === 'importa-smartcig') {
            return this.importaSmartcig(args);
        }  else if (args.buttonCode === 'save-and-publish-smartcig') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const stazioneAppaltante: StazioneAppaltanteInfo = args.stazioneAppaltante;
            const syscon: number = args.syscon;
            let codGara = args.codGara;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                const valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let factory: Function;
                    if (codGara == null) {
                        const request: SmartCigInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon);
                        if (request != null && request.smartCig != null) {
                            request.smartCig = request.smartCig.toUpperCase();
                        }
                        if (request != null && request.smartCig != null) {
                            request.smartCig = request.smartCig.toUpperCase();
                        }
                        factory = this.insertAndPublishSmartCigFactory(request);
                    } else {
                        const request: SmartCigUpdateForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon, codGara);
                        if (request != null && request.smartCig != null) {
                            request.smartCig = request.smartCig.toUpperCase();
                        }
                        if (request != null && request.smartCig != null) {
                            request.smartCig = request.smartCig.toUpperCase();
                        }
                        factory = this.updateAndPublishSmartCigFactory(request);
                    }
                   
                   
   
                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            const codGaraResult = codGara == null? result.data.codGara : codGara;

                            this.dettaglioGaraStoreService.clear();
                            this.dettaglioSmartCigStoreService.clear();
                            this.dettaglioSmartCigStoreService.codGara = codGaraResult;

                            const params: IDictionary<any> = {
                                codGara: codGaraResult
                            };

                            this.routerService.navigateToPage('dettaglio-smartcig-page', params);
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
            }
        } else if (args.buttonCode === 'save-smartcig') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const codGara: string = args.codGara;
            const stazioneAppaltante: StazioneAppaltanteInfo = args.stazioneAppaltante;
            const syscon: number = args.syscon;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                const valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta

                    let factory: Function

                    if (codGara == null) {
                        const request: SmartCigInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon);
                        if (request != null && request.smartCig != null) {
                            request.smartCig = request.smartCig.toUpperCase();
                        }
                        factory = this.insertSmartCigFactory(request);
                    } else {
                        const request: SmartCigUpdateForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon, codGara);
                        if (request != null && request.smartCig != null) {
                            request.smartCig = request.smartCig.toUpperCase();
                        }
                        factory = this.updateSmartCigFactory(request);
                    }


                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: any) => {

                            setUpdateState(false);

                            const codGaraResult = codGara || result.data;

                            this.dettaglioGaraStoreService.clear();
                            this.dettaglioSmartCigStoreService.clear();
                            this.dettaglioSmartCigStoreService.codGara = codGaraResult;

                            const params: IDictionary<any> = {
                                codGara: codGaraResult
                            };

                            this.routerService.navigateToPage('dettaglio-smartcig-page', params);
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
        return of(args);
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private backToHomePage(args: SmartCigProviderArgs): Observable<IDictionary<any>> {
        const setUpdateState = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('home-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToGestioneSmartcig(args: SmartCigProviderArgs): Observable<IDictionary<any>> {
        const setUpdateState = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        if(environment.LOGIN_MODE === 3){
            this.routerService.navigateToPage('home-page');
        } else {
            this.routerService.navigateToPage('gestione-smartcig-page');
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaGare(args: SmartCigProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-gare-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(codGara: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara
        };
        this.routerService.navigateToPage('modifica-smartcig-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goNew(radioData: string): Observable<IDictionary<any>> {
        if(radioData === '1'){
            this.routerService.navigateToPage('nuovo-smartcig-page');
        }else{
            this.routerService.navigateToPage('importa-smartcig-page');
        }
        //this.routerService.navigateToPage('modifica-smartcig-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }


    private importaSmartcig(args){

        const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        const messagesPanel: HTMLElement = args.messagesPanel;
        const setUpdateState: Function = args.setUpdateState;

        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            const valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                if (isFunction(setUpdateState)) {
                    setUpdateState(true);
                }
                let request: ImportaSmartcigForm = {};
                request.codiceStazioneAppaltante = args.stazioneAppaltante.codice;
                let sectionCig = formBuilderConfig.fields[0];
                let fieldSections = sectionCig.fieldSections;
                request.syscon = args.syscon;
                each(fieldSections, (one: SdkFormBuilderField) => {
                    if(one.code === 'codice-smartcig'){
                        request.cig = one.data;
                    }
                });

                let sectionUser = formBuilderConfig.fields[1];
                let fieldSectionsUser = sectionUser.fieldSections;
                request.syscon = args.syscon;
                each(fieldSectionsUser, (one: SdkFormBuilderField) => {                  

                    if(one.code === 'username'){
                        request.username = one.data;
                    }
                    if(one.code === 'password'){
                        request.password = one.data;
                    }
                    if(one.code === 'salva-credenziali'){
                        if(isObject(one.data) && isObject(one.data[0]) && get(one.data[0], 'checked') == true){
                            request.saveCredentials = true;
                        } else {
                            request.saveCredentials = false;
                        }                        
                    }
                });

                let factory = this.importaSmartCigFactory(request);
                return this.requestHelper.begin(factory, messagesPanel).pipe(
                    map((result: any) => {
                        console.log(result);
                        setUpdateState(false);
                        return new Observable((ob: Observer<IDictionary<any>>) => {
                            ob.next({
                                codGara : result.data
                            });

                        });
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
        return of(args);
    }

    private backDettaglio(args: SmartCigProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };

        this.routerService.navigateToPage('dettaglio-smartcig-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: StazioneAppaltanteInfo, syscon: number, codGara?: string): SmartCigInsertForm | SmartCigUpdateForm {
        let request: SmartCigInsertForm | SmartCigUpdateForm = this.getDefaultForm(stazioneAppaltante, syscon, codGara);
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

    private getDefaultForm(stazioneAppaltante: StazioneAppaltanteInfo, syscon: number, codGara?: string): SmartCigInsertForm | SmartCigUpdateForm {
        let request: SmartCigInsertForm | SmartCigUpdateForm = {
            stazioneAppaltante: stazioneAppaltante.codice
        };

        if (codGara == null) {
            // nuovo smart cig
            (request as SmartCigInsertForm).syscon = +syscon;
        } else {
            // modifica smart cig
            (request as SmartCigUpdateForm).codGara = +codGara;
            
        }

        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: SmartCigInsertForm | SmartCigUpdateForm, dynamicMappingOutput?: string): SmartCigInsertForm | SmartCigUpdateForm {
        if (isObject(field)) {
            if (!isEmpty(dynamicMappingOutput)) {
                let list: Array<DynamicValue> = get(request, dynamicMappingOutput);
                if (isUndefined(list)) {
                    list = new Array();
                }
                if (field.type === 'COMBOBOX') {
                    if (!isUndefined(field.data)) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data.key ? field.data.key : null
                        };
                        list.push(dynamicValue);
                    }
                } else {
                    if (!isUndefined(field.data)) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data
                        };
                        list.push(dynamicValue);
                    }
                }
                set(request, dynamicMappingOutput, list);
            } else if (!isEmpty(field.mappingOutput)) {
                if (field.type === 'COMBOBOX') {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data.key);
                    }
                } else if (field.type === 'DATEPICKER') {
                    if (!isUndefined(field.data)) {
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    }
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: SmartCigInsertForm | SmartCigUpdateForm): SmartCigInsertForm | SmartCigUpdateForm {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request);
            } else {
                let dynamicMappingOutput: string = field.mappingOutput && one.dynamicField === true ? field.mappingOutput : undefined;
                request = this.elaborateOne(one, request, dynamicMappingOutput);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: SmartCigInsertForm | SmartCigUpdateForm): SmartCigInsertForm | SmartCigUpdateForm {
        if (field.visible !== false) {
            let newRestObjects: Array<any>;
            if (!isEmpty(field.mappingOutput)) {
                newRestObjects = get(request, field.mappingOutput);
                if (isUndefined(newRestObjects)) {
                    newRestObjects = new Array();
                }
            }

            if (isObject(newRestObjects)) {
                each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration) => {
                    let singleIterationObject: any = {};
                    each(group.fields, (one: SdkFormBuilderField) => {
                        if (one.type === 'FORM-SECTION') {
                            singleIterationObject = this.elaborateSection(one, singleIterationObject);
                        } else if (one.type === 'FORM-GROUP') {
                            singleIterationObject = this.elaborateGroup(one, singleIterationObject);
                        } else {
                            singleIterationObject = this.elaborateOne(one, singleIterationObject);
                        }
                    });
                    newRestObjects.push(singleIterationObject);
                });
                set(request, field.mappingOutput, newRestObjects);
            } else {
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
            }
        }
        return request;
    }

    private importaSmartCigFactory(form: ImportaSmartcigForm) {
        return () => {
            return this.smartCigService.importaSmartCig(form);
        }
    }



    private insertSmartCigFactory(insertForm: SmartCigInsertForm) {
        return () => {
            return this.smartCigService.insertSmartCig(insertForm);
        }
    }

    private insertAndPublishSmartCigFactory(insertForm: SmartCigInsertForm) {
        return () => {
            return this.smartCigService.insertPubblicaSmartCig(insertForm);
        }
    }

    private updateAndPublishSmartCigFactory(updateForm: SmartCigUpdateForm) {
        return () => {
            return this.smartCigService.updateAndPublishSmartCigFactory(updateForm);
        }
    }

    private updateSmartCigFactory(insertForm: SmartCigUpdateForm) {
        return () => {
            return this.smartCigService.updateSmartCig(insertForm);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get dettaglioSmartCigStoreService(): DettaglioSmartCigStoreService { return this.injectable(DettaglioSmartCigStoreService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get dettaglioGaraStoreService(): DettaglioGaraStoreService { return this.injectable(DettaglioGaraStoreService) }

    // #endregion

}
