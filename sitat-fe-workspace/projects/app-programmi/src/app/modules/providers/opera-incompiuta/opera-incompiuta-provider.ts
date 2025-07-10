import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkComboBoxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isEqual, isObject, isUndefined, set, toString } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import {
    DettaglioOperaIncompiutaStoreService,
} from '../../layout/components/business/opera-incompiuta/dettaglio-opera-incompiuta-store.service';
import { ImmobileInsertForm } from '../../models/immobili/immobile.model';
import { DettaglioCupForm, OperaIncompiutaEntry, OperaIncompiutaInsertForm } from '../../models/opere/opere-incompiute.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import {
    NuovaOperaIncompiutaValidationUtilsService,
} from '../../services/utils/nuova-opera-incompiuta-validation-utils.service';

export interface OperaIncompiutaProviderArgs extends IDictionary<any> {
    action?: string;
    idProgramma?: string;
    tipologia?: string;
    idOperaIncompiuta?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    altriDati?: boolean;
}

@Injectable({ providedIn: 'root' })
export class OperaIncompiutaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('OperaIncompiutaProvider', args);
        if (args.buttonCode === 'dettaglio-cup') {
            return this.dettaglioCup(args.data.cup, args.data.username, args.data.password, args.data.syscon, args.data.messagesPanel);            
        }

        if (args.buttonCode === 'update-opera-incompiuta') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let idProgramma: string = args.idProgramma;
            let tipologia: string = args.tipologia;
            let setUpdateState: Function = args.setUpdateState;
            let altriDati: boolean = !!args.altriDati;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovaOperaIncompiutaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let operaIncompiuta: OperaIncompiutaEntry = args.operaIncompiuta;
                    let request: OperaIncompiutaInsertForm = this.populateRequest(formBuilderConfig, idProgramma, operaIncompiuta, altriDati);

                    request.id = operaIncompiuta.id;
                    let modificaOperaFactory = this.modificaOperaFactory(request);
                    return this.requestHelper.begin(modificaOperaFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {
                        let idOperaIncompiuta: number = request.id;

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            idProgramma,
                            tipologia,
                            idOperaIncompiuta
                        };

                        this.dettaglioOperaIncompiutaStore.clear();
                        this.dettaglioOperaIncompiutaStore.idProgramma = args.idProgramma;
                        this.dettaglioOperaIncompiutaStore.tipologia = args.tipologia;
                        this.dettaglioOperaIncompiutaStore.idOperaIncompiuta = args.idOperaIncompiuta;

                        let page: string = altriDati === true ? 'dett-altri-dati-opera-incompiuta-page' : 'dett-generale-opera-incompiuta-page';

                        this.routerService.navigateToPage(page, params);
                        return {
                            ...result,
                            defaultFormBuilderConfig,
                            formBuilderConfig
                        };
                    }), catchError((err: any) => {
                        this.scrollToMessagePanel(messagesPanel);
                        return of(err);
                    }));
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

        if (args.buttonCode === 'save-opera-incompiuta') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let idProgramma: string = args.idProgramma;
            let tipologia: string = args.tipologia;
            let setUpdateState: Function = args.setUpdateState;
            let additionalInfo: IDictionary<String> = args.additionalInfo;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovaOperaIncompiutaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let request: OperaIncompiutaInsertForm = this.populateRequest(formBuilderConfig, idProgramma, null, false);
                    request.additionalInfo = additionalInfo;
                    let inserisciOperaFactory = this.inserisciOperaFactory(request);
                    return this.requestHelper.begin(inserisciOperaFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {
                        let idOperaIncompiuta: number = result.data;

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            idProgramma,
                            tipologia,
                            idOperaIncompiuta
                        };

                        this.dettaglioOperaIncompiutaStore.clear();
                        this.dettaglioOperaIncompiutaStore.idProgramma = idProgramma;
                        this.dettaglioOperaIncompiutaStore.tipologia = tipologia;
                        this.dettaglioOperaIncompiutaStore.idOperaIncompiuta = toString(idOperaIncompiuta);

                        this.routerService.navigateToPage('dett-generale-opera-incompiuta-page', params);
                        return {
                            ...result,
                            defaultFormBuilderConfig,
                            formBuilderConfig
                        };
                    }), catchError((err: any) => {
                        this.scrollToMessagePanel(messagesPanel);
                        return of(err);
                    }));
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

    private dettaglioCup(cup: string, username: string, password: string, syscon: string, messagesPanel: HTMLElement): Observable<IDictionary<any>> {
        let form: DettaglioCupForm ={
            cup,
            username,
            password,
            syscon
        }
        let factory = this.getDettaglioCUPFactory(form);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((response) => {
                return {
                    response
                };
            })
        )
    }


    private getDettaglioCUPFactory(form: DettaglioCupForm) {
        return () => {
            return this.programmiService.dettaglioCup(form);
        }
    }



    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, idProgramma: string, baseObj: OperaIncompiutaEntry, altriDati: boolean): OperaIncompiutaInsertForm {
        let request: OperaIncompiutaInsertForm;
        if (isObject(baseObj)) {
            request = cloneDeep(baseObj);
            if (altriDati === false) {
                delete request.immobili;
            }
        } else {
            request = this.getDefaultForm(idProgramma);
        }
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

    protected getDefaultForm(idProgramma: string): OperaIncompiutaInsertForm {
        return {
            idProgramma: +idProgramma
        };
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.visible !== false) {
                if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item)) {
                        set(request, field.mappingOutput, item.key);
                    }
                } else {
                    if (field.data != null) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            } else {
                set(request, field.mappingOutput, undefined);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: any): any {
        if (field.visible !== false) {
            each(field.fieldSections, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    request = this.elaborateSection(one, request);
                } else if (one.type === 'FORM-GROUP') {
                    request = this.elaborateGroup(one, request);
                } else {
                    request = this.elaborateOne(one, request);
                }
            });
        }
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: any): any {
        if (field.visible !== false) {
            let newRestObjects: Array<ImmobileInsertForm>;
            if (!isEmpty(field.mappingOutput)) {
                newRestObjects = get(request, field.mappingOutput);
                if (isUndefined(newRestObjects)) {
                    newRestObjects = new Array();
                }
            }

            if (isObject(newRestObjects)) {
                each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration) => {
                    let singleIterationObject: ImmobileInsertForm = {};
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

    private inserisciOperaFactory(insertForm: OperaIncompiutaInsertForm) {
        return () => {
            return this.programmiService.createOperaIncompiuta(insertForm)
        }
    }

    private modificaOperaFactory(updateForm: OperaIncompiutaInsertForm) {
        return () => {
            return this.programmiService.modificaOperaIncompiuta(updateForm);
        }
    }


    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovaOperaIncompiutaValidationUtilsService(): NuovaOperaIncompiutaValidationUtilsService { return this.injectable(NuovaOperaIncompiutaValidationUtilsService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioOperaIncompiutaStore(): DettaglioOperaIncompiutaStoreService { return this.injectable(DettaglioOperaIncompiutaStoreService) }

    // #endregion

}
