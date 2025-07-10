import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider, SdkDateHelper, SdkRouterService, SdkStoreService, SdkSessionStorageService } from "@maggioli/sdk-commons";
import { SdkCheckboxItem, SdkFormBuilderConfiguration, SdkMessagePanelService, SdkAutocompleteItem, SdkFormBuilderField, SdkFormFieldGroupConfiguration } from "@maggioli/sdk-controls";
import { Observable, of } from "rxjs";
import { HttpRequestHelper } from "../services/http/http-request-helper.service";
import { CommonValidationUtilsService } from "../services/utils/common-validation-utils.service";
import { each, filter, get, isEmpty, isFunction, isObject, isUndefined, map as mapArray, set } from "lodash-es";

import { DynamicValue } from "../model/sdk-base.model";
import { SdkAppaltiecontrattiBaseConstants } from "../sdk-appaltiecontratti-base.constants";
import { StazioneAppaltanteBaseInfo } from "../model/stazione-appaltante/stazione-appaltante.model";


@Injectable({ providedIn: "root" })
export abstract class SdkBaseProvider extends SdkBaseService implements SdkProvider {
    constructor(inj: Injector) {
        super(inj);
    }

    abstract run(args: any);

    protected abstract getDefaultFormItem(args: any, stazioneAppaltante: StazioneAppaltanteBaseInfo, syscon: number): any;

    protected populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, args: any, stazioneAppaltante: StazioneAppaltanteBaseInfo, syscon: number, request: any = null): any {
        request = request ?? this.getDefaultFormItem(args, stazioneAppaltante, syscon);
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

    protected elaborateOne(field: SdkFormBuilderField, request: any, dynamicMappingOutput?: string): any {
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
                if (field.type === "DOCUMENT") {
                    set(request, field.mappingOutput, field.file);
                    if (!isUndefined(field.mappingOutputFileName) && !isEmpty(field.mappingOutputFileName)) {
                        set(request, field.mappingOutputFileName, field.fileName);
                    }
                    if (!isUndefined(field.mappingOutputFileType) && !isEmpty(field.mappingOutputFileType)) {
                        set(request, field.mappingOutputFileType, field.tipoFile);
                    }
                    if (!isUndefined(field.mappingOutputTitle) && !isEmpty(field.mappingOutputTitle)) {
                        set(request, field.mappingOutputTitle, field.title);
                    }
                } else if (field.type === "COMBOBOX") {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data.key);
                    }
                } else if (field.type === "DATEPICKER") {
                    if (field.data != null) {
                        let data: Date = get(field, "data");
                        if (data != null) {
                            let formatted: string = this.sdkDateHelper.format(data, SdkAppaltiecontrattiBaseConstants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    } else {
                        set(request, field.mappingOutput, null);
                    }
                } else if (field.type === "AUTOCOMPLETE") {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, "data");
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.type === "CHECKBOX") {
                    if (field.data != null) {
                        const data: Array<SdkCheckboxItem> = field.data;
                        let checkeds: Array<string> = mapArray(
                            filter(data, (one) => one.checked === true),
                            (one) => one.code
                        );
                        set(request, field.mappingOutput, checkeds.length > 0);
                    } else {
                        set(request, field.mappingOutput, false);
                    }
                } else if (field.type === "NUMERIC-TEXTBOX") {
                    set(request, field.mappingOutput, field.data);
                } else {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            }
        }
        return request;
    }

    protected elaborateSection(field: SdkFormBuilderField, request: any): any {
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

    protected elaborateGroup(field: SdkFormBuilderField, request: any): any {
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

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    protected back(args: any): Observable<IDictionary<any>> {
        let setUpdateState = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage("home-page");
        return of(undefined);
    }

    // #region Getters

    protected get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    protected get routerService(): SdkRouterService {
        return this.injectable(SdkRouterService);
    }

    protected get store(): SdkStoreService {
        return this.injectable(SdkStoreService);
    }

    protected get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    protected get commonValidationUtilsService(): CommonValidationUtilsService {
        return this.injectable(CommonValidationUtilsService);
    }

    private get sdkDateHelper(): SdkDateHelper {
        return this.injectable(SdkDateHelper);
    }

    protected get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }


    // #endregion
}
