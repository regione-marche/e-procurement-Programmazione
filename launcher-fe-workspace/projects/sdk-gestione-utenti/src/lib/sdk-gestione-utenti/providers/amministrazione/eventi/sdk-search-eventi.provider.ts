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

import { RicercaEventiFormDTO } from '../../../model/amministrazione.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';


export interface SdkGestioneEventiSearchProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    homeSlug?: string;
}

@Injectable()
export class SdkGestioneEventiSearchProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkGestioneEventiSearchProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkGestioneEventiSearchProviderArgs >>>', args);

        if (args.buttonCode === 'back-button') {
            this.store.dispatch(new SdkStoreAction(SdkGestioneUtentiConstants.SEARCH_FORM_GESTIONE_EVENTI_DISPATCHER, undefined));
            let homeSlug = args.homeSlug != null ? args.homeSlug : 'home-page';
            this.routerService.navigateToPage(homeSlug);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(SdkGestioneUtentiConstants.SEARCH_FORM_GESTIONE_EVENTI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (args.buttonCode === 'search-button') {
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let form: RicercaEventiFormDTO = this.populateRequest(formBuilderConfig);
            this.store.dispatch(new SdkStoreAction(SdkGestioneUtentiConstants.SEARCH_FORM_GESTIONE_EVENTI_DISPATCHER, form));
            this.routerService.navigateToPage('lista-eventi-page');
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): RicercaEventiFormDTO {

        let request: RicercaEventiFormDTO = this.getDefaultForm();
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

    private getDefaultForm(): RicercaEventiFormDTO {
        let item: RicercaEventiFormDTO = {};
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
                    let finalDate: string = this.dateHelper.format(<Date>field.data, 'yyyy/MM/dd HH:mm:ss');
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
