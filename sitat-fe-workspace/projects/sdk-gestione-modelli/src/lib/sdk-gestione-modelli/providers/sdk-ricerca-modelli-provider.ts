import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService, SdkStoreAction, SdkStoreService } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkComboBoxItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration } from '@maggioli/sdk-controls';
import { each, get, isEmpty, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { SdkGestioneModelliConstants } from '../sdk-gestione-modelli.constants';
import { RicercaModelliForm } from '../model/gestione-utenti.model';

export interface SdkRicercaModelliProviderProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    codiceProfilo: string;
    syscon: string;
}

@Injectable()
export class SdkRicercaModelliProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkRicercaModelliProviderProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkRicercaModelliProvider', args);

        if (args.buttonCode === 'back-button') {
            this.store.dispatch(new SdkStoreAction(SdkGestioneModelliConstants.SEARCH_FORM_MODELLI_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        } else if (args.buttonCode === 'clean-button') {
            let form: RicercaModelliForm = {};
            form.codProfiloAttivo = args.idProfilo;
            form.syscon = args.syscon;
            this.store.dispatch(new SdkStoreAction(SdkGestioneModelliConstants.SEARCH_FORM_MODELLI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (args.buttonCode === 'search-button') {
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let codiceProfilo: string = args.codiceProfilo;
            let syscon: string = args.syscon;
            let form: RicercaModelliForm = this.populateRequest(formBuilderConfig, codiceProfilo, syscon);
            this.store.dispatch(new SdkStoreAction(SdkGestioneModelliConstants.SEARCH_FORM_MODELLI_DISPATCHER, form));
            this.routerService.navigateToPage('lista-modelli-page');
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codiceProfilo: string, syscon: string): RicercaModelliForm {

        let request: RicercaModelliForm = this.getDefaultForm();
        request.syscon = syscon;
        request.codProfiloAttivo = codiceProfilo;
        each(formBuilderConfig.fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION') {
                this.elaborateSection(field, request);
            } else if (field.type === 'FORM-GROUP') {
                this.elaborateGroup(field, request);
            } else {
                this.elaborateOne(field, request);
            }
        });
        return request;
    }

    private getDefaultForm(): RicercaModelliForm {
        let item: RicercaModelliForm = {};
        return item;
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field)) {
            if (field.visible !== false) {
                if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item) && !isEmpty(field.mappingOutput)) {
                        const key = item.key;
                        if (key != null || key != undefined) {
                            set(request, field.mappingOutput, item.key);
                        }
                    }
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
                this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                this.elaborateGroup(one, request);
            } else {
                this.elaborateOne(one, request);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: any): any {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    this.elaborateSection(one, request);
                } else if (one.type === 'FORM-GROUP') {
                    this.elaborateGroup(one, request);
                } else {
                    this.elaborateOne(one, request);
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
