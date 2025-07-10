import { Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SdkBaseService,
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
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isObject, set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { Constants } from '../../../app-commons.constants';
import { RicercaAvanzataArchivioStazioniAppaltantiForm } from '../../../models/stazione-appaltante/stazione-appaltante.model';


export interface AdvancedSearchArchivioStazioniAppaltantiProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
}

@Injectable()
export class AdvancedSearchArchivioStazioniAppaltantiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: AdvancedSearchArchivioStazioniAppaltantiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('AdvancedSearchArchivioStazioniAppaltantiProviderArgs', args);
        if (args.buttonCode === 'back') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (args.buttonCode === 'search-button') {
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let form: RicercaAvanzataArchivioStazioniAppaltantiForm = this.populateRequest(formBuilderConfig);
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER, form));
            this.routerService.navigateToPage('lista-archivio-stazioni-appaltanti-page');
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): RicercaAvanzataArchivioStazioniAppaltantiForm {

        let request: RicercaAvanzataArchivioStazioniAppaltantiForm = this.getDefaultForm();
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

    private getDefaultForm(): RicercaAvanzataArchivioStazioniAppaltantiForm {
        let item: RicercaAvanzataArchivioStazioniAppaltantiForm = {};
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
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.data != null) {
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

    // #endregion

}
