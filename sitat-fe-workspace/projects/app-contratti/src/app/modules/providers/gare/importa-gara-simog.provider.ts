import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, ResponseResult, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBaseService,
    SdkDateHelper,
    SdkProvider,
    SdkRouterService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelConfig,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isArray, isEmpty, isEqual, isFunction, isObject, isUndefined, set, size } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { Constants, TEnvs } from '../../../app.constants';
import { DettaglioGaraStoreService } from '../../layout/components/business/gare/dettaglio-gara-store.service';
import { MigrazioneGaraForm } from '../../models/gare/gare.model';
import {
    ConsultaGaraBean,
    ConsultaGaraEntry,
    ConsultaGaraOperations,
    ImportaGaraForm,
    PresaInCaricoGaraDelegataForm,
    ResponsePresaInCaricoGaraDelegata,
} from '../../models/gare/importa-gara.model';
import { GareService } from '../../services/gare/gare.service';
import { ImportaGaraService } from '../../services/gare/importa-gara.service';
import { NuovoAttoValidationUtilsService } from '../../services/utils/nuovo-atto-validation-utils.service';
import { validate as isValidUUID } from 'uuid';

export interface ImportaGaraSimogProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    formBuilderConfig: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    stazioneAppaltante?: StazioneAppaltanteInfo;
    userProfile?: UserProfile;
    codGara?: string;
    migraGaraForm?: MigrazioneGaraForm;
    presaInCaricoGaraDelegataForm?: PresaInCaricoGaraDelegataForm;
}

@Injectable({ providedIn: 'root' })
export class ImportaGaraSimogProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ImportaGaraSimogProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ImportaGaraSimogProvider >>>', args);

        if (args.buttonCode === 'back-to-home-page-gare') {
            let setUpdateState: Function = args.setUpdateState;
            this.homePageGare(setUpdateState);
        } else if (args.buttonCode === 'go-to-dettaglio-gara') {
            let codGara: string = args.codGara;
            let setUpdateState: Function = args.setUpdateState;
            this.dettaglioGara(codGara, setUpdateState);
        } else if (args.buttonCode === 'importa-gara') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let cfImpersonato: string = args.userProfile.cfImpersonato;
            let loaImpersonato: string = args.userProfile.loaImpersonato;
            let idpImpersonato: string = args.userProfile.idpImpersonato;
            let syscon: string = args.userProfile.syscon;
            let cfRup: string = args.userProfile.codiceFiscale;
            let cfSA: string = args.stazioneAppaltante.codFiscale;
            let codProfilo: string = args.userProfile.configurations?.idProfilo;
            let codein: string = args.stazioneAppaltante.codice;
            let presaCarico: string = args.presaCarico;
            const INVIO_MAIL_RICHIESTA_SIMOG: boolean = environment.INVIO_MAIL_RICHIESTA_SIMOG;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoAttoValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                
                if (valid === true) {
                    // genero l'oggetto di richiesta
                    let request: ImportaGaraForm = this.populateRequest(formBuilderConfig, syscon, cfRup, cfSA);
                    
                    valid = this.validateCigIdAvgaraIdAppaltoFormat(request.cigIdAvGara);

                    if (valid === true) {
                        request.sendMail = INVIO_MAIL_RICHIESTA_SIMOG;                        
                        request.cfImpersonato = cfImpersonato;
                        request.loaImpersonato = loaImpersonato;
                        request.idpImpersonato = idpImpersonato;
                        request.codProfilo = codProfilo;
                        request.codein = codein;
                        let garaFactory: Function = null;
                        if(presaCarico){
                            garaFactory = this.presaCaricoImportaGaraFactory(request);
                        } else{
                            garaFactory = this.importaGaraDaSimogFactory(request);
                        }
                        
    
                        return this.requestHelper.begin(garaFactory, messagesPanel).pipe(
                            map((response: ConsultaGaraEntry) => {
    
                                if (response.operation === ConsultaGaraOperations.OP_IMPORT_ANAC) {
                                    let codGara: string = response.codGara;
                                    let setUpdateState: Function = args.setUpdateState;
                                    if(response.anacErrors != null){
                                        let anacErrorsArray = [];
                                        response.anacErrors.forEach(element => {
                                            anacErrorsArray.push({message : element});
                                        });
                                        this.sdkMessagePanelService.showError(messagesPanel, anacErrorsArray);
                                    }
                                    if(response.internalErrors != null){
                                        let internalErrorsArray = [];
                                        response.internalErrors.forEach(element => {
                                            internalErrorsArray.push({message : element});
                                        });
                                        this.sdkMessagePanelService.showError(messagesPanel, internalErrorsArray);
                                    }
                                    if(response.validationErrors != null){
                                        
                                    }
                                    
                                    if(response.inserito == true && response.presaCarico == false){
                                        this.dettaglioGara(codGara, setUpdateState);
                                    }
                                    
                                } else if (response.operation === ConsultaGaraOperations.OP_CONSULTA_GARA) {
                                    if (isArray(response.listaLotti) && size(response.listaLotti) > 0) {
                                        let listaMessaggiPositivi: Array<SdkMessagePanelTranslate> = new Array();
                                        let listaMessaggiNegativi: Array<SdkMessagePanelTranslate> = new Array();
    
                                        each(response.listaLotti, (one: ConsultaGaraBean) => {
                                            if (one.caricato === true) {
                                                let message: string = this.translateService.instant('IMPORTA-GARA.CIG', { value: one.codiceCIG });
                                                message += one.msg;
                                                listaMessaggiPositivi.push({
                                                    message
                                                });
                                            } else if (one.caricato === false) {
                                                let message: string = this.translateService.instant('IMPORTA-GARA.ERRORE-IMPORT', { value: one.codiceCIG });
                                                message += one.msg;
                                                listaMessaggiNegativi.push({
                                                    message
                                                });
                                            }
                                        });
                                       
                                        const messagesConfig: Array<SdkMessagePanelConfig> = new Array();
                                        if(isObject(response.origineSAInfo) && !isEmpty(response.origineSAInfo.nome)){
                                            let message: string = this.translateService.instant('IMPORTA-GARA.SA-DIFFERENTE', { value: response.origineSAInfo.nome });
                                            listaMessaggiPositivi.push({
                                                message                                            
                                            });
                                        }
                                        if(isObject(response) && response.perfezionata === false){
                                            let message: string = this.translateService.instant('IMPORTA-GARA.NON-PERFEZIONATA');
                                            listaMessaggiPositivi.push({
                                                message                                            
                                            });
                                        }
                                        if (size(listaMessaggiPositivi) > 0) {
                                            messagesConfig.push({
                                                messages: listaMessaggiPositivi,
                                                type: 'success',
                                                list: true
                                            });
                                        }
    
                                        if (size(listaMessaggiNegativi) > 0) {
                                            messagesConfig.push({
                                                messages: listaMessaggiNegativi,
                                                type: 'error',
                                                list: true
                                            });
                                        }
                                        this.sdkMessagePanelService.show(messagesPanel, messagesConfig);
                                        this.scrollToMessagePanel(messagesPanel);
                                    }
    
                                }
    
                                return {
                                    ...response,
                                    defaultFormBuilderConfig,
                                    formBuilderConfig,
                                    success: true
                                };
                            }),
                            catchError((error: any, caught: Observable<any>) => {
                                this.scrollToMessagePanel(messagesPanel);
                                return throwError({ ...error, success: false });
                            })
                        );
                    } else{
                        this.sdkMessagePanelService.showError(messagesPanel, [
                            {
                                message: 'VALIDATORS.IMPORT-GARA-VALORE-NON-VALIDO'
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    }
                    
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
        } else if (args.buttonCode === 'migra-gara') {
            return this.migrazioneGara(args);
        } else if (args.buttonCode === 'presa-in-carico-gara-delegata') {
            return this.presaInCaricoGaraDelegata(args);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private homePageGare(setUpdateState: Function): Observable<IDictionary<any>> {

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.routerService.navigateToPage('home-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioGara(codGara: string, setUpdateState: Function): Observable<IDictionary<any>> {

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.dettaglioGaraStore.clear();
        this.dettaglioGaraStore.codGara = codGara;

        let params: IDictionary<any> = {
            codGara
        };
        this.routerService.navigateToPage('dettaglio-gara-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, syscon: string, cfRup: string, cfSA: string): ImportaGaraForm {
        let request: ImportaGaraForm = this.getDefaultForm(syscon, cfRup, cfSA);
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

    private getDefaultForm(syscon: string, cfRup: string, cfSA: string): ImportaGaraForm {
        let request: ImportaGaraForm = {
            syscon: +syscon,
            cfRup,
            cfSA
        };
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: ImportaGaraForm): ImportaGaraForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
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
            } else {
                if (!isUndefined(field.data)) {
                    field.data = field.data.trim();
                    set(request, field.mappingOutput, field.data);
                }
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: ImportaGaraForm): ImportaGaraForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: ImportaGaraForm): ImportaGaraForm {
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

    private importaGaraDaSimogFactory(form: ImportaGaraForm) {
        return () => {
            return this.importaGaraService.importaGaraDaSimog(form);
        }
    }

    private presaCaricoImportaGaraFactory(form: ImportaGaraForm) {
        return () => {
            return this.importaGaraService.presaCaricoImportaGara(form);
        }
    }

    private migrazioneGara(args: ImportaGaraSimogProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        const body: MigrazioneGaraForm = args.migraGaraForm;
        const factory = this.migraGaraFactory(body);
        return this.requestHelper.begin(factory, args.messagesPanel)
            .pipe(
                map(
                    (result: ResponseResult<any>) => {
                        let setUpdateState: Function = args.setUpdateState;
                        if (isFunction(setUpdateState)) {
                            setUpdateState(false);
                        }
                        return result;
                    }
                ),
                catchError((error: any) => {
                    this.scrollToMessagePanel(args.messagesPanel);
                    return throwError(() => error);
                })
            );
    }

    private migraGaraFactory(form: MigrazioneGaraForm) {
        return () => {
            return this.gareService.executeMigraGara(form);
        }
    }

    private presaInCaricoGaraDelegata(args: ImportaGaraSimogProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        const body: PresaInCaricoGaraDelegataForm = args.presaInCaricoGaraDelegataForm;
        const factory = this.presaInCaricoGaraDelegataFactory(body);
        return this.requestHelper.begin(factory, args.messagesPanel)
            .pipe(
                map(
                    (result: ResponseResult<ResponsePresaInCaricoGaraDelegata>) => {
                        return result;
                    }
                ),
                catchError((error: any) => {
                    this.scrollToMessagePanel(args.messagesPanel);
                    return throwError(() => error);
                })
            );
    }

    private presaInCaricoGaraDelegataFactory(form: PresaInCaricoGaraDelegataForm) {
        return () => {
            return this.importaGaraService.presaInCaricoGaraDelegata(form);
        }
    }

    private validateCigIdAvgaraIdAppaltoFormat(cigIdAvGara: string): boolean {
      
      if (!isValidUUID(cigIdAvGara) && !this.isCig(cigIdAvGara) && !this.isIdAvGara(cigIdAvGara)) {
        return false;
      }

      return true;
    }

    private isCig(cig: string){
        if(cig.length != 10){
            return false;        
        }
        return true;
    }



    private isIdAvGara(valore: string): boolean {        
        return /^\d+$/.test(valore) && parseInt(valore) > 0;
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get importaGaraService(): ImportaGaraService { return this.injectable(ImportaGaraService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get nuovoAttoValidationUtilsService(): NuovoAttoValidationUtilsService { return this.injectable(NuovoAttoValidationUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get dettaglioGaraStore(): DettaglioGaraStoreService { return this.injectable(DettaglioGaraStoreService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    // #endregion
}


