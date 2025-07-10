import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkCheckboxItem,
    SdkComboBoxItem,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty, isObject, isUndefined, set } from 'lodash-es';
import { Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../app.constants';
import {
    DettaglioProgrammaStoreService,
} from '../../layout/components/business/dettaglio-programma/dettaglio-programma-store.service';
import { ProgrammaInsertForm } from '../../models/programmi/programmi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import { NuovoProgrammaValidationUtilsService } from '../../services/utils/nuovo-programma-validation-utils.service';



@Injectable({ providedIn: 'root' })
export abstract class AbstractProgrammaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        if (args.type === 'BUTTON') {
            return this.handleButtons(args.data);
        }
        return of(args);
    }


    protected abstract handleButtons(data: IDictionary<any>): Observable<IDictionary<any>>;

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }


    protected populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, stazioneAppaltante: string, syscon: number, tipologia: number): ProgrammaInsertForm {

        let request: ProgrammaInsertForm = this.getDefaultForm(stazioneAppaltante, syscon, tipologia);
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

    protected abstract getDefaultForm(stazioneappaltante: string, syscon: number, tipologia: number): ProgrammaInsertForm;

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'AUTOCOMPLETE') {
                if (field.data != null) {
                    let item: SdkAutocompleteItem = get(field, 'data');
                    set(request, field.mappingOutput, item._key);
                }
            } else if (field.type === 'COMBOBOX') {
                let item: SdkComboBoxItem = get(field, 'data');
                if (isObject(item)) {
                    set(request, field.mappingOutput, item.key);
                }
            }  else if (field.type === 'CHECKBOX') {
                let item: SdkCheckboxItem = get(field, 'data');
                if (isObject(item)) {
                    set(request, field.mappingOutput, item[0].checked);
                }
            } else if (field.type === 'DATEPICKER') {
                let data: Date = get(field, 'data');
                if (isObject(data)) {
                    let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                    set(request, field.mappingOutput, formatted);
                }
            } else {
                if (!isUndefined(field.data)) {
                    set(request, field.mappingOutput, field.data);
                }
            }
        }
        return request;
    }



    private elaborateSection(field: SdkFormBuilderField, request: any): any {
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

    private elaborateGroup(field: SdkFormBuilderField, request: any): any {
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
    // #region Getters

    protected get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    protected get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    protected get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    protected get nuovoProgrammaValidationUtilsService(): NuovoProgrammaValidationUtilsService { return this.injectable(NuovoProgrammaValidationUtilsService) }

    protected get dettaglioProgrammaStore(): DettaglioProgrammaStoreService { return this.injectable(DettaglioProgrammaStoreService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}
