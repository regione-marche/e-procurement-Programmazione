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
import { SdkAutocompleteItem, SdkComboBoxItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration } from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isObject, isUndefined, reduce, set } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';

import { Constants } from '../../../app.constants';
import { RicercaAvvisoForm } from '../../models/avviso/avviso.model';


export interface AdvancedSearchAvvisiProviderArgs extends IDictionary<any> {
    type: 'BUTTON' | 'COMBOBOX';
    data?: IDictionary<any>;
}

@Injectable({ providedIn: 'root' })
export class AdvancedSearchAvvisiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: AdvancedSearchAvvisiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('AdvancedSearchAvvisiProvider', args);

        if (args.type === 'BUTTON') {
            return this.handleButtons(args.data);
        } else if (args.type === 'COMBOBOX') {
            return this.handleCombobox(args.data);
        }
        return of(args);
    }

    private handleButtons(data: IDictionary<any>): Observable<IDictionary<any>> {
        if (data.code === 'back-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_AVVISI_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        } else if (data.code === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_AVVISI_DISPATCHER, undefined));

            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({                    
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (data.code === 'search-button') {
            let formBuilderConfig: SdkFormBuilderConfiguration = data.formBuilderConfig;
            let params: IDictionary<any> = this.populateRequest(formBuilderConfig);
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_AVVISI_DISPATCHER, params));
            this.routerService.navigateToPage('lista-avvisi-page');
        }
        return of(undefined);
    }

    private handleCombobox(data: IDictionary<any>): Observable<IDictionary<any>> {
        return undefined;
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) };

    // #endregion

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): RicercaAvvisoForm {

        let request: RicercaAvvisoForm = this.getDefaultForm();
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

    private getDefaultForm(): RicercaAvvisoForm {
        let item: RicercaAvvisoForm = {};
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
                }  else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                        if(field.code === 'stazione-appaltante'){
                            set(request, 'stazioneAppaltanteData', item);
                        }
                    }
                }  else if (field.type === 'DATEPICKER' && field.data) {
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

}
