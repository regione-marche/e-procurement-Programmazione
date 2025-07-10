import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { InterventoNonRipropostoUpdateForm } from '../../models/interventi/interventi-non-riproposti.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';
import { NuovoProgrammaValidationUtilsService } from '../../services/utils/nuovo-programma-validation-utils.service';

export interface ListaInterventiNonRipropostiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    idIntervento?: string,
    idProgramma?: string,
    tipologia?: string,
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    motivo: string;
    defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    formBuilderConfig: SdkFormBuilderConfiguration;
}

@Injectable({ providedIn: 'root' })
export class ListaInterventiNonRipropostiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaInterventiNonRipropostiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaInterventiNonRipropostiProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteInterventoNonRiproposto(args.idProgramma, args.idIntervento, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailInterventiNr(args.idProgramma, args.idIntervento, args.tipologia);
        } else if (args.buttonCode === 'importa') {
            return this.importInterventiNonRiproposti(args.idProgramma, args.tipologia);
        } else if (args.buttonCode === 'update') {
            return this.update(args);
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo(args);
        } else if (args.buttonCode === 'update-form') {
            return this.updateForm(args.idProgramma, args.idIntervento, args.tipologia);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private deleteInterventoNonRiproposto(idProgramma, idIntervento: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteInterventoFactory(idProgramma, idIntervento);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteInterventoFactory(idProgramma: string, idIntervento: string) {
        return () => {
            return this.programmiService.deleteInterventoNonRiproposto(idProgramma, idIntervento);
        }
    }

    private importInterventiNonRiproposti(idProgramma: string, tipologia: string): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        };
        this.routerService.navigateToPage('import-interventi-non-riproposti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private update(args: ListaInterventiNonRipropostiProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args)) {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = get(args, 'defaultFormBuilderConfig');
            let formBuilderConfig: SdkFormBuilderConfiguration = get(args, 'formBuilderConfig');
            let messagesPanel: HTMLElement = get(args, 'messagesPanel');
            let idProgramma = get(args, 'idProgramma');
            let idIntervento = get(args, 'idIntervento');
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                let valid: boolean = this.nuovoProgrammaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: InterventoNonRipropostoUpdateForm = this.populateRequest(formBuilderConfig, args.idProgramma, args.idIntervento);
                    let factory = this.modificaInterventoNoRipFactory(request);
                    return this.requestHelper.begin(factory, messagesPanel).pipe(
                        map((result: string) => {
                            return {
                                reload: true
                            }
                        })
                    );
                } else {
                    messagesPanel.scrollIntoView();
                }
            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-NON-COMPILATA'
                    }
                ]);
                messagesPanel.scrollIntoView();
            }
        }
        return of(undefined);
    }

    private modificaInterventoNoRipFactory(form: InterventoNonRipropostoUpdateForm) {
        return () => {
            return this.programmiService.modificaInterventoNonRiproposto(form);
        }
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, idProgramma: string, idIntervento: string): InterventoNonRipropostoUpdateForm {
        let request: InterventoNonRipropostoUpdateForm = this.getDefaultInterventoUpdateForm(idProgramma, idIntervento);
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

    private getDefaultInterventoUpdateForm(idProgramma: string, idIntervento: string) {
        let defaultForm: InterventoNonRipropostoUpdateForm = {
            idIntervento: Number(idIntervento),
            idProgramma: Number(idProgramma)
        }
        return defaultForm;
    }


    private elaborateOne(field: SdkFormBuilderField, request: InterventoNonRipropostoUpdateForm): InterventoNonRipropostoUpdateForm {
        if (isObject(field) && !isEmpty(field.mappingOutput) && !isUndefined(field.data)) {
            set(request, field.mappingOutput, field.data);
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: InterventoNonRipropostoUpdateForm): InterventoNonRipropostoUpdateForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: InterventoNonRipropostoUpdateForm): InterventoNonRipropostoUpdateForm {
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

    private nuovo(args: ListaInterventiNonRipropostiProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma: args.idProgramma,
            tipologia: args.tipologia
        };
        this.routerService.navigateToPage('nuovo-intervento-non-riproposto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private detailInterventiNr(idProgramma: string, idIntervento: string, tipologia: string): Observable<IDictionary<any>> {
       
        let params: IDictionary<any> = {
            idProgramma,
            idIntervento,
            tipologia
        };

        
        this.routerService.navigateToPage('dettaglio-intervento-non-riproposto-page', params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateForm(idProgramma: string, idIntervento: string, tipologia: string): Observable<IDictionary<any>> {
       
        let params: IDictionary<any> = {
            idProgramma,
            idIntervento,
            tipologia
        };

        
        this.routerService.navigateToPage('modifica-intervento-non-riproposto-page', params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }


    // #region public

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoProgrammaValidationUtilsService(): NuovoProgrammaValidationUtilsService { return this.injectable(NuovoProgrammaValidationUtilsService) }

    // #endregion

}
