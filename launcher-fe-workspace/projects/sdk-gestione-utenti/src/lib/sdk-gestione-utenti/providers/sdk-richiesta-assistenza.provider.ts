import { Injectable, Injector } from '@angular/core';
import { IBrowserInfo, IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { each, get, isEmpty, isEqual, isObject, map as mapArray, set } from 'lodash-es';
import { Observable, Observer, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { RichiestaAssistenzaFormDTO, UserDTO } from '../model/gestione-utenti.model';
import { ResponseDTO } from '../model/lib.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../services/gestione-utenti.service';
import { GestioneUtentiValidationUtilsService } from '../utils/gestione-utenti-validation-utils.service';

export interface SdkRichiestaAssistenzaProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    setUpdateState?: Function;
    browserInfo?: IBrowserInfo;
    homeSlug?: string;
    serverCaptchaEnabled?: boolean;
    captchaSolution?: string;
    captchaResetSubject$?: Subject<boolean>
}

@Injectable()
export class SdkRichiestaAssistenzaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkRichiestaAssistenzaProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkRichiestaAssistenzaProvider >>>', args);

        if (args.buttonCode === 'richiedi-assistenza') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            let messagesPanel: HTMLElement = args.messagesPanel;
            let browserInfo: IBrowserInfo = args.browserInfo;
            let serverCaptchaEnabled: boolean = args.serverCaptchaEnabled;
            let captchaSolution: string = args.captchaSolution;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                this.sdkMessagePanelService.clear(messagesPanel);

                if (serverCaptchaEnabled && isEmpty(captchaSolution)) {
                    this.sdkMessagePanelService.showError(messagesPanel, [
                        {
                            message: 'CAPTCHA.ERROR'
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                } else {
                    // controllo la validita' del modello
                    let valid: boolean = this.gestioneUtentiValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);

                    if (valid === true) {

                        // genero l'oggetto di richiesta
                        let form: RichiestaAssistenzaFormDTO = this.populateRequest(formBuilderConfig, browserInfo, captchaSolution);

                        return this.gestioneUtentiService.richiestaAssistenza(form).pipe(
                            map((result: ResponseDTO<UserDTO>) => {

                                if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {

                                    args.captchaResetSubject$.next(true);

                                    let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                                        let message: SdkMessagePanelTranslate = {
                                            message: `SDK-RICHIESTA-ASSISTENZA.VALIDATORS.${one}`
                                        };
                                        return message;
                                    })
                                    this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    this.scrollToMessagePanel(messagesPanel);

                                    return {
                                        defaultFormBuilderConfig,
                                        formBuilderConfig
                                    };

                                } else {

                                    let params: IDictionary<any> = {
                                        done: result.response ? 'Y' : 'N'
                                    };

                                    this.routerService.navigateToPage('richiesta-assistenza-completata-page', params);

                                    return {
                                        defaultFormBuilderConfig,
                                        formBuilderConfig,
                                        done: result.response ? 'Y' : 'N'
                                    };

                                }
                            }),
                            catchError((error: any, caught: Observable<any>) => {
                                args.captchaResetSubject$.next(true);
                                if (error != null && error.error != null) {
                                    if (error.error.error != null) {
                                        let message: SdkMessagePanelTranslate = {
                                            message: 'SDK-RICHIESTA-ASSISTENZA.VALIDATORS.ERRORE-GENERICO',
                                            messageParams: {
                                                value: error.error.error
                                            }
                                        };
                                        this.sdkMessagePanelService.showError(messagesPanel, [
                                            message
                                        ]);
                                    } else {
                                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                                            let message: SdkMessagePanelTranslate = {
                                                message: one
                                            };
                                            return message;
                                        })
                                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                                    }
                                } else {
                                    this.sdkMessagePanelService.showError(messagesPanel, [
                                        {
                                            message: 'ERRORS.UNEXPECTED-ERROR'
                                        }
                                    ]);
                                }
                                this.scrollToMessagePanel(messagesPanel);
                                return throwError(() => error);
                            })
                        );
                    } else {
                        this.scrollToMessagePanel(messagesPanel);
                    }
                }

            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-NON-COMPILATA'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            }
        } else if (args.buttonCode === 'back-to-home-page') {
            return this.backHome(args);
        } else if (args.buttonCode === 'back-to-richiesta-assistenza-page') {
            return this.backRichiestaAssistenza();
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, browserInfo: IBrowserInfo, captchaSolution: string): RichiestaAssistenzaFormDTO {
        let request: RichiestaAssistenzaFormDTO = this.getDefaultForm(browserInfo, captchaSolution);
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

    private getDefaultForm(browserInfo: IBrowserInfo, captchaSolution: string): RichiestaAssistenzaFormDTO {
        let obj: RichiestaAssistenzaFormDTO = {
            nominativoEnteAmministrazione: null,
            referenteDaContattare: null,
            email: null,
            tipologiaRichiesta: null,
            captchaSolution
        };

        if (browserInfo != null) {
            obj = {
                ...obj,
                ...browserInfo
            };
        }

        return obj;
    }

    private elaborateOne(field: SdkFormBuilderField, request: RichiestaAssistenzaFormDTO): RichiestaAssistenzaFormDTO {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'COMBOBOX') {
                set(request, field.mappingOutput, field.data.key);
            } else if (field.type === 'DATEPICKER') {
                if (field.data != null) {
                    let data: Date = get(field, 'data');
                    if (isObject(data)) {
                        set(request, field.mappingOutput, data.getTime());
                    }
                }
            } else if (field.type === 'DOCUMENT') {
                if (field.file != null) {
                    set(request, field.mappingOutput, field.file);
                    set(request, `${field.mappingOutput}Name`, field.fileName);
                }
            } else {
                set(request, field.mappingOutput, field.data);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: RichiestaAssistenzaFormDTO): RichiestaAssistenzaFormDTO {
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

    private elaborateGroup(field: SdkFormBuilderField, request: RichiestaAssistenzaFormDTO): RichiestaAssistenzaFormDTO {
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

    private backHome(args: SdkRichiestaAssistenzaProviderArgs): Observable<IDictionary<any>> {
        let homeSlug = args.homeSlug != null ? args.homeSlug : 'home-page';
        this.routerService.navigateToPage(homeSlug);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backRichiestaAssistenza(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('richiesta-assistenza-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiValidationUtilsService(): GestioneUtentiValidationUtilsService { return this.injectable(GestioneUtentiValidationUtilsService) }

    // #endregion

}
