import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { SchedaSicurezzaInsertForm } from '../../models/scheda-sicurezza/scheda-sicurezza.model';
import { SchedaSicurezzaService } from '../../services/scheda-sicurezza/scheda-sicurezza.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

export interface SchedaSicurezzaFaseInizialeProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    codiceFase?: string;
    numeroProgressivo?: string;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    update?: boolean;
}

@Injectable({ providedIn: 'root' })
export class SchedaSicurezzaFaseInizialeProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SchedaSicurezzaFaseInizialeProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SchedaSicurezzaFaseInizialeProviderArgs >>>', args);
        if (args.buttonCode === 'back-to-dettaglio-scheda-sicurezza') {
            return this.dettaglioSchedaSicurezza(args);
        } else if (args.buttonCode === 'go-to-update-scheda-sicurezza') {
            return this.goToUpdateSchedaSicurezza(args);
        } else if (args.buttonCode === 'go-to-nuova-scheda-sicurezza') {
            return this.goToNuovaSchedaSicurezza(args);
        } else if (args.buttonCode === 'save-scheda-sicurezza') {
            return this.saveSchedaSicurezza(args);
        } else if (args.buttonCode === 'delete-scheda-sicurezza') {
            return this.deleteSchedaSicurezza(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        }
        return of(args);
    }

    // #region Private

    private deleteSchedaSicurezza(args: SchedaSicurezzaFaseInizialeProviderArgs): Observable<IDictionary<any>> {
        let factory = this.deleteFactory(args.codGara, args.codLotto, args.numeroProgressivo);
        return this.requestHelper.begin(factory, args.messagesPanel);
    }

    private deleteFactory(codGara: string, codLotto: string, numeroProgressivo: string) {
        return () => {
            return this.schedaSicurezzaService.deleteSchedaSicurezza(codGara, codLotto, numeroProgressivo);
        }
    }

    private dettaglioSchedaSicurezza(args: SchedaSicurezzaFaseInizialeProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('dettaglio-scheda-sicurezza-fase-ini-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToUpdateSchedaSicurezza(args: SchedaSicurezzaFaseInizialeProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('modifica-scheda-sicurezza-fase-ini-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToNuovaSchedaSicurezza(args: SchedaSicurezzaFaseInizialeProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('nuova-scheda-sicurezza-fase-ini-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateSchedaSicurezzaFactory(form: SchedaSicurezzaInsertForm) {
        return () => {
            return this.schedaSicurezzaService.updateSchedaSicurezza(form);
        }
    }

    private insertSchedaSicurezzaFactory(form: SchedaSicurezzaInsertForm) {
        return () => {
            return this.schedaSicurezzaService.insertSchedaSicurezza(form);
        }
    }

    private saveSchedaSicurezza(args: SchedaSicurezzaFaseInizialeProviderArgs): Observable<any> {

        let codGara: string = args.codGara;
        let codLotto: string = args.codLotto;
        let codiceFase: string = args.codiceFase;
        let numeroProgressivo: string = args.numeroProgressivo;
        let defaultFormBuilderConfig: SdkFormFieldGroupConfiguration = args.defaultFormBuilderConfig;
        let formBuilderConfig: SdkFormFieldGroupConfiguration = args.formBuilderConfig;
        let setUpdateState: Function = args.setUpdateState;
        let messagesPanel: HTMLElement = args.messagesPanel;
        let update: boolean = args.update;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: SchedaSicurezzaInsertForm = this.populateRequest(formBuilderConfig, codGara, codLotto, numeroProgressivo);

                let factory: Function;
                if (update === true) {
                    factory = this.updateSchedaSicurezzaFactory(request);
                } else {
                    factory = this.insertSchedaSicurezzaFactory(request);
                }

                return this.requestHelper.begin(factory, messagesPanel).pipe(
                    map((result: any) => {

                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            codGara,
                            codLotto,
                            codiceFase,
                            numeroProgressivo
                        };

                        this.routerService.navigateToPage('dettaglio-scheda-sicurezza-fase-ini-page', params);
                        return {
                            ...result,
                            defaultFormBuilderConfig,
                            formBuilderConfig
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
                    message: 'VALIDATORS.FORM-NON-COMPILATA'
                }
            ]);
            this.scrollToMessagePanel(messagesPanel);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, codLotto: string, numeroProgressivo: string): SchedaSicurezzaInsertForm {
        let request: SchedaSicurezzaInsertForm = this.getDefaultIncarichiProfessionaliForm(codGara, codLotto, numeroProgressivo);
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

    private getDefaultIncarichiProfessionaliForm(codGara: string, codLotto: string, numeroProgressivo: string): SchedaSicurezzaInsertForm {
        let request: SchedaSicurezzaInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto,
            numIniz: +numeroProgressivo
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: SchedaSicurezzaInsertForm): SchedaSicurezzaInsertForm {
        if (isObject(field)) {
            if (!isEmpty(field.mappingOutput)) {
                if (field.type === 'COMBOBOX') {
                    if (!isUndefined(field.data)) {
                        set(request, field.mappingOutput, field.data.key);
                    }
                } else if (field.type === 'DATEPICKER') {
                    if (!isUndefined(field.data)) {
                        let data: Date = get(field, 'data');
                        if (isObject(data)) {
                            let formatted: string = this.sdkDateHelper.format(data, Constants.SERVER_DATE_FORMAT);
                            set(request, field.mappingOutput, formatted);
                        }
                    }
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
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

    private elaborateSection(field: SdkFormBuilderField, request: SchedaSicurezzaInsertForm): SchedaSicurezzaInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: SchedaSicurezzaInsertForm): SchedaSicurezzaInsertForm {
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

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get schedaSicurezzaService(): SchedaSicurezzaService { return this.injectable(SchedaSicurezzaService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
