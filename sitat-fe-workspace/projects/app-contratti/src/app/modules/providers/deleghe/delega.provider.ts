import { DelegaInsertForm } from "./../../models/deleghe/deleghe.model";
import { Injectable, Injector } from "@angular/core";
import { DynamicValue, HttpRequestHelper, StazioneAppaltanteInfo } from "@maggioli/app-commons";
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService, UserProfile } from "@maggioli/sdk-commons";
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from "@maggioli/sdk-controls";
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from "lodash-es";
import { Observable, Observer, throwError } from "rxjs";
import { catchError, map } from "rxjs/operators";

import { Constants } from "../../../app.constants";
import { DettaglioDelegaStoreService } from "../../layout/components/business/deleghe/dettaglio-delega-store.service";
import { GenericValidationUtilsService } from "../../services/utils/generic-validation-utils.service";
import { DelegheService } from "../../services/deleghe/deleghe.service";

@Injectable({ providedIn: "root" })
export class DelegaProvider extends SdkBaseService implements SdkProvider {
    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug("DelegaProvider >>>", args);

        if (args.buttonCode === "save-delega") {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const id: string = args.id;
            const stazioneAppaltante: StazioneAppaltanteInfo = args.stazioneAppaltante;
            const userProfile: UserProfile = args.userProfile;
            const syscon: number = args.syscon;
            const idProfilo: string = args.idProfilo;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                // controllo la validita' del modello
                let valid: boolean = this.genericValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let delegaFactory: Function;

                    if (id == null) {
                        let request: DelegaInsertForm = this.populateRequest(formBuilderConfig);
                        request.syscon = syscon;
                        request.idProfilo = idProfilo;
                        request.stazioneAppaltante = stazioneAppaltante.codice;
                        delegaFactory = this.insertDelegaFactory(request);
                    } else if (id != null) {
                        let request: DelegaInsertForm = this.populateRequest(formBuilderConfig, id);
                        request.syscon = syscon;
                        request.idProfilo = idProfilo;
                        request.stazioneAppaltante = stazioneAppaltante.codice;
                        delegaFactory = this.updateDelegaFactory(request);
                    } else {
                        return new Observable((ob: Observer<IDictionary<any>>) => {
                            ob.next(args);
                            ob.complete();
                        });
                    }

                    return this.requestHelper.begin(delegaFactory, messagesPanel).pipe(
                        map((result: any) => {
                            setUpdateState(false);

                            let idResult = id || result.data;

                            this.dettaglioDelegaStore.clear();
                            this.dettaglioDelegaStore.id = idResult;

                            let params: IDictionary<any> = {
                                id: idResult,
                            };

                            this.routerService.navigateToPage("dettaglio-delega-page", params);
                            return {
                                ...result,
                                defaultFormBuilderConfig,
                                formBuilderConfig,
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
                        message: "VALIDATORS.FORM-NON-COMPILATA",
                    },
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, id?: string): DelegaInsertForm {
        let request: DelegaInsertForm = this.getDefaultDelegaForm(id);
        each(formBuilderConfig.fields, (field: SdkFormBuilderField) => {
            if (field.type === "FORM-SECTION") {
                request = this.elaborateSection(field, request);
            } else if (field.type === "FORM-GROUP") {
                request = this.elaborateGroup(field, request);
            } else {
                request = this.elaborateOne(field, request);
            }
        });
        return request;
    }

    private getDefaultDelegaForm(id?: string): DelegaInsertForm {
        let request: DelegaInsertForm = {};

        if (id != null) {
            request.id = +id;
        }

        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: DelegaInsertForm, dynamicMappingOutput?: string): DelegaInsertForm {
        if (isObject(field)) {
            if (!isEmpty(dynamicMappingOutput)) {
                let list: Array<DynamicValue> = get(request, dynamicMappingOutput);
                if (isUndefined(list)) {
                    list = new Array();
                }
                if (field.type === "COMBOBOX") {
                    if (!isUndefined(field.data)) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data.key ? field.data.key : null,
                        };
                        list.push(dynamicValue);
                    }
                } else {
                    if (!isUndefined(field.data)) {
                        let dynamicValue: DynamicValue = {
                            codice: field.code,
                            descrizione: field.label,
                            value: field.data,
                        };
                        list.push(dynamicValue);
                    }
                }
                set(request, dynamicMappingOutput, list);
            } else if (!isEmpty(field.mappingOutput)) {
                if (field.type === "COMBOBOX") {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data.key);
                    }
                } else if (field.type === "DATEPICKER") {
                    if (!isUndefined(field.data)) {
                        let data: Date = get(field, "data");
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    }
                } else if (field.type === "AUTOCOMPLETE") {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, "data");
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.type === "DYNAMIC-FORM-SECTION") {
                    if (field.dynamicFieldValues != null) {
                        set(request, field.mappingOutput, field.dynamicFieldValues);
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

    private elaborateSection(field: SdkFormBuilderField, request: DelegaInsertForm): DelegaInsertForm {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === "FORM-SECTION") {
                request = this.elaborateSection(one, request);
            } else if (one.type === "FORM-GROUP") {
                request = this.elaborateGroup(one, request);
            } else {
                let dynamicMappingOutput: string = field.mappingOutput && one.dynamicField === true ? field.mappingOutput : undefined;
                request = this.elaborateOne(one, request, dynamicMappingOutput);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: DelegaInsertForm): DelegaInsertForm {
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
                        if (one.type === "FORM-SECTION") {
                            singleIterationObject = this.elaborateSection(one, singleIterationObject);
                        } else if (one.type === "FORM-GROUP") {
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
                        if (one.type === "FORM-SECTION") {
                            request = this.elaborateSection(one, request);
                        } else if (one.type === "FORM-GROUP") {
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

    private insertDelegaFactory(insertForm: DelegaInsertForm) {
        return () => {
            return this.delegheService.insertDelega(insertForm);
        };
    }

    private updateDelegaFactory(insertForm: DelegaInsertForm) {
        return () => {
            return this.delegheService.updateDelega(insertForm);
        };
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService {
        return this.injectable(SdkRouterService);
    }

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get delegheService(): DelegheService {
        return this.injectable(DelegheService);
    }

    private get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get genericValidationUtilsService(): GenericValidationUtilsService {
        return this.injectable(GenericValidationUtilsService);
    }

    private get dettaglioDelegaStore(): DettaglioDelegaStoreService {
        return this.injectable(DettaglioDelegaStoreService);
    }

    private get sdkDateHelper(): SdkDateHelper {
        return this.injectable(SdkDateHelper);
    }

    // #endregion
}
