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
import { cloneDeep, each, get, isEmpty, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';

import { Constants } from '../../../app.constants';
import { RicercaGareForm } from '../../models/gare/gare.model';


export interface AdvancedSearchGareProviderArgs extends IDictionary<any> {
    type: 'BUTTON';
    data?: IDictionary<any>;
}

@Injectable({ providedIn: 'root' })
export class AdvancedSearchGareProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: AdvancedSearchGareProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('AdvancedSearchGareProvider', args);

        if (args.type === 'BUTTON') {
            return this.handleButtons(args.data);
        }
        return of(args);
    }

    private handleButtons(data: IDictionary<any>): Observable<IDictionary<any>> {
        if (data.code === 'back-button') {
            //this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_GARE_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        } else if (data.code === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_GARE_DISPATCHER, undefined));
            
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({                    
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (data.code === 'search-button') {
            let formBuilderConfig: SdkFormBuilderConfiguration = data.formBuilderConfig;
            let form: RicercaGareForm = this.populateRequest(formBuilderConfig);

            const params: IDictionary<any> = {
                ...form
            };
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_GARE_DISPATCHER, params));
            this.routerService.navigateToPage('lista-gare-page');
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): RicercaGareForm {

        let request: RicercaGareForm = this.getDefaultForm();
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

    private getDefaultForm(): RicercaGareForm {
        let item: RicercaGareForm = {};
        return item;
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field)) {
            if (field.visible !== false) {
                if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item) && !isEmpty(field.mappingOutput)) {
                        set(request, field.mappingOutput, item.key);
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
                        if(field.code === 'stazione-appaltante'){
                            set(request, 'stazioneAppaltanteData', item);
                        }
                    }
                }  else if (field.type === 'MULTIPLE-AUTOCOMPLETE') {
                    if(field.code === 'stazione-appaltante'){
                        if (field.data != null) {
                            let items: Array<SdkAutocompleteItem> = get(field, 'data');
                            let keys: Array<String> = [];
                            each(items, (item: SdkAutocompleteItem) => {
                                if (!isEmpty(item._key)) {
                                    keys.push(item._key)
                                }
                            });
                            set(request, field.mappingOutput, keys);
                            set(request, 'stazioneAppaltanteData', items);
                        }
                    } else if(field.code === 'rup-gara'){
                        if (field.data != null) {
                            let items: Array<SdkAutocompleteItem> = get(field, 'data');
                            let keys: Array<String> = [];
                            each(items, (item: SdkAutocompleteItem) => {
                                if (!isEmpty(item._key)) {
                                    keys.push(item._key)
                                }
                            });
                            set(request, field.mappingOutput, keys);
                            set(request, 'rupData', items);
                        }
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

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) };

    // #endregion

}
