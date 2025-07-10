import { Injectable, Injector } from '@angular/core';
import { DynamicValue, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkDateHelper, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration } from '@maggioli/sdk-controls';
import { each, get, isEmpty, isFunction, isObject, isString, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { DettaglioFaseStoreService } from '../../layout/components/business/fasi/dettaglio-fase-store.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';
import { IncarichiProfessionaliInsertForm, IncaricoProfessionaleInsertForm } from '../../models/incarichi-professionali/incarichi-professionali.model';
import { FaseIncarichiProfessionaliService } from '../../services/fasi/fase-incarichi-professionali.service';


@Injectable({ providedIn: 'root' })
export class FaseIncarichiProfessionaliProvider extends SdkBaseService implements SdkProvider {

    constructor(injector: Injector) {
        super(injector);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('FaseIncarichiProfessionaliProvider >>>', args);

        if (args.buttonCode === 'back-to-dettaglio-fase') {
            let codGara: string = args.codGara;
            let codLotto: string = args.codLotto;
            let codiceFase: string = args.codiceFase;
            let numeroProgressivo: string = args.numeroProgressivo;
            let setUpdateState: Function = args.setUpdateState;
            this.dettaglioFase(codGara, codLotto, codiceFase, numeroProgressivo, setUpdateState);
        } else if (args.buttonCode === 'go-to-update-fase') {
            let codGara: string = args.codGara;
            let codLotto: string = args.codLotto;
            let codiceFase: string = args.codiceFase;
            let numeroProgressivo: string = args.numeroProgressivo;
            this.modificaFase(codGara, codLotto, codiceFase, numeroProgressivo);
        } else if (args.buttonCode === 'save-fase') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            const setUpdateState: Function = args.setUpdateState;
            const codGara: string = args.codGara;
            const codLotto: string = args.codLotto;
            const codiceFase: string = args.codiceFase;
            const numeroProgressivo: string = args.numeroProgressivo;
            const isUpdatePhase: boolean = args.updateFase;

            // controllo la validita' del modello
            let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                // genero l'oggetto di richiesta
                let request: IncarichiProfessionaliInsertForm = this.populateRequest(formBuilderConfig, codGara, codLotto,codiceFase, numeroProgressivo);

                each(request.incarichi, (one: IncaricoProfessionaleInsertForm) => {
                    one.sezione = 'PCP';
                });

                let faseFactory: Function;

                if (isUpdatePhase === true) {
                    request.num = +numeroProgressivo;
                    faseFactory = this.updateFaseFactory(request);
                } else {
                    faseFactory = this.insertFaseFactory(request);
                }

                return this.requestHelper.begin(faseFactory, messagesPanel).pipe(
                    map((result: any) => {

                        if (isFunction(setUpdateState)) {
                            setUpdateState(false);
                        }

                        this.dettaglioFaseStoreService.clear();
                        this.dettaglioFaseStoreService.codGara = codGara;
                        this.dettaglioFaseStoreService.codLotto = codLotto;
                        this.dettaglioFaseStoreService.codiceFase = codiceFase;
                        this.dettaglioFaseStoreService.numeroProgressivo = numeroProgressivo || result.data;

                        let params: IDictionary<any> = {
                            codGara,
                            codLotto,
                            codiceFase,
                            numeroProgressivo: numeroProgressivo || result.data
                        };

                        this.routerService.navigateToPage('dettaglio-fase-incarichi-professionali-page', params);
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
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private dettaglioFase(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string, setUpdateState: Function): Observable<IDictionary<any>> {

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara,
            codLotto,
            codiceFase,
            numeroProgressivo
        };
        this.routerService.navigateToPage('dettaglio-fase-incarichi-professionali-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private modificaFase(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara,
            codLotto,
            codiceFase,
            numeroProgressivo
        };
        this.routerService.navigateToPage('modifica-fase-incarichi-professionali-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private insertFaseFactory(insertForm: IncarichiProfessionaliInsertForm) {
        return () => {
            return this.faseIncarichiProfessionaliService.insertFase(insertForm);
        }
    }

    private updateFaseFactory(insertForm: IncarichiProfessionaliInsertForm) {
        return () => {
            return this.faseIncarichiProfessionaliService.updateFase(insertForm);
        }
    }

    private getDefaultFaseForm(codGara: string, codLotto: string): IncarichiProfessionaliInsertForm {
        const request: IncarichiProfessionaliInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto
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
        let request: IncarichiProfessionaliInsertForm = {
            codGara: +codGara,
            codLotto: +codLotto,
            codFase: +codiceFase,
            numAppa: +numeroProgressivo,
            incarichi: []
        };
        return request;
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get faseIncarichiProfessionaliService(): FaseIncarichiProfessionaliService { return this.injectable(FaseIncarichiProfessionaliService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion
}
