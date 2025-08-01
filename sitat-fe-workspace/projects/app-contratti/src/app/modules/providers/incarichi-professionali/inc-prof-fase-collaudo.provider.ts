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
import {
    IncarichiProfessionaliInsertForm,
    IncaricoProfessionaleInsertForm,
} from '../../models/incarichi-professionali/incarichi-professionali.model';
import { IncarichiProfessionaliService } from '../../services/incarichi-professionali/incarichi-professionali.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';

export interface IncarichiProfessionaliFaseCollaudoProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    codiceFase?: string;
    numeroProgressivo?: string;
    setUpdateState?: Function;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
}

@Injectable({ providedIn: 'root' })
export class IncarichiProfessionaliFaseCollaudoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IncarichiProfessionaliFaseCollaudoProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('IncarichiProfessionaliFaseCollaudoProviderArgs >>>', args);
        if (args.buttonCode === 'back-to-dettaglio-incarichi-professionali') {
            return this.dettaglioIncarichiProfessionali(args);
        } else if (args.buttonCode === 'go-to-update-incarichi') {
            return this.goToUpdateIncarichiProfessionali(args);
        } else if (args.buttonCode === 'save-incarichi') {
            return this.saveIncarichi(args);
        }
        return of(args);
    }

    // #region Private

    private dettaglioIncarichiProfessionali(args: IncarichiProfessionaliFaseCollaudoProviderArgs): Observable<IDictionary<any>> {

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
        this.routerService.navigateToPage('dettaglio-inc-prof-fase-collaudo-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToUpdateIncarichiProfessionali(args: IncarichiProfessionaliFaseCollaudoProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo
        };
        this.routerService.navigateToPage('modifica-inc-prof-fase-collaudo-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateIncarichiFactory(form: IncarichiProfessionaliInsertForm) {
        return () => {
            return this.incarichiProfessionaliService.updateIncarichiProfessionali(form);
        }
    }

    private saveIncarichi(args: IncarichiProfessionaliFaseCollaudoProviderArgs): Observable<any> {

        const codGara: string = args.codGara;
        const codLotto: string = args.codLotto;
        const codiceFase: string = args.codiceFase;
        const numeroProgressivo: string = args.numeroProgressivo;
        const defaultFormBuilderConfig: SdkFormFieldGroupConfiguration = args.defaultFormBuilderConfig;
        const formBuilderConfig: SdkFormFieldGroupConfiguration = args.formBuilderConfig;
        const setUpdateState: Function = args.setUpdateState;
        const messagesPanel: HTMLElement = args.messagesPanel;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

            // controllo la validita' del modello
            const valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                const request: IncarichiProfessionaliInsertForm = this.populateRequest(formBuilderConfig, codGara, codLotto, codiceFase, numeroProgressivo);

                each(request.incarichi, (one: IncaricoProfessionaleInsertForm) => {
                    one.sezione = 'CO';
                });

                const factory: Function = this.updateIncarichiFactory(request);

                return this.requestHelper.begin(factory, messagesPanel).pipe(
                    map((result: any) => {

                        setUpdateState(false);

                        const params: IDictionary<any> = {
                            codGara,
                            codLotto,
                            codiceFase,
                            numeroProgressivo
                        };

                        this.routerService.navigateToPage('dettaglio-inc-prof-fase-collaudo-page', params);
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): IncarichiProfessionaliInsertForm {
        let request: IncarichiProfessionaliInsertForm = this.getDefaultIncarichiProfessionaliForm(codGara, codLotto, codiceFase, numeroProgressivo);
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

    private getDefaultIncarichiProfessionaliForm(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): IncarichiProfessionaliInsertForm {
        const request: IncarichiProfessionaliInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto,
            codFase: +codiceFase,
            numAppa: +numeroProgressivo,
            incarichi: []
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: IncarichiProfessionaliInsertForm): IncarichiProfessionaliInsertForm {
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

    private elaborateSection(field: SdkFormBuilderField, request: IncarichiProfessionaliInsertForm): IncarichiProfessionaliInsertForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: IncarichiProfessionaliInsertForm): IncarichiProfessionaliInsertForm {
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

    private get routerService(): SdkRouterService {
        return this.injectable(SdkRouterService);
    }

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get incarichiProfessionaliService(): IncarichiProfessionaliService {
        return this.injectable(IncarichiProfessionaliService);
    }

    private get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService {
        return this.injectable(NuovoAttoValidationUtilsService);
    }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

}
