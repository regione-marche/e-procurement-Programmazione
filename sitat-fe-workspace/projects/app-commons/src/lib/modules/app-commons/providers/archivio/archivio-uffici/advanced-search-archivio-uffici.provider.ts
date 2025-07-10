import { Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SdkBaseService,
    SdkDateHelper,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkComboBoxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkTextboxMatrixCellConfig,
    SdkTextboxMatrixRowConfig,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';

import { Constants } from '../../../app-commons.constants';
import { RicercaAvanzataArchivioImpreseForm } from '../../../models/archivio/archivio-imprese.models';


export interface AdvancedSearchArchivioUfficiProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
}

@Injectable()
export class AdvancedSearchArchivioUfficiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: AdvancedSearchArchivioUfficiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('AdvancedSearchArchivioUfficiProvider', args);

        if (args.buttonCode === 'back-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_UFFICI_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_UFFICI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (args.buttonCode === 'search-button') {
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let form: RicercaAvanzataArchivioImpreseForm = this.populateRequest(formBuilderConfig);
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_UFFICI_DISPATCHER, form));
            this.routerService.navigateToPage('lista-archivio-uffici-page');
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): RicercaAvanzataArchivioImpreseForm {

        let request: RicercaAvanzataArchivioImpreseForm = this.getDefaultForm();
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

    private getDefaultForm(): RicercaAvanzataArchivioImpreseForm {
        let item: RicercaAvanzataArchivioImpreseForm = {};
        return item;
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field)) {
            if (field.visible !== false) {
                if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item) && !isEmpty(field.mappingOutput)) {
                        const key = item.key;
                        if (!isEmpty(key)) {
                            set(request, field.mappingOutput, item.key);
                        }
                    }
                } else if (field.type === 'TEXTBOX-MATRIX') {
                    each(field.rows, (row: SdkTextboxMatrixRowConfig) => {
                        each(row.cells, (cell: SdkTextboxMatrixCellConfig) => {
                            if (!isEmpty(cell.mappingOutput)) {
                                set(request, cell.mappingOutput, cell.value);
                            }
                        });
                    })
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.type === 'DATEPICKER' && field.data) {
                    let finalDate: string = this.dateHelper.format(<Date>field.data, 'yyyy/MM/dd');
                    set(request, field.mappingOutput, finalDate);
                } else if (!isUndefined(field.data)) {
                    if (!isEmpty(field.mappingOutput)) {
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

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
