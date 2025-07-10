import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, SdkStoreAction, SdkStoreService } from '@maggioli/sdk-commons';
import { SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration, SdkMessagePanelService, SdkNotificationService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty, isEqual, isObject, isUndefined, set } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';

import { PubblicaGaraEntry } from '../../models/pubblicazioni/pubblicazione-gara.model';
import { PubblicazioneResult } from '../../models/pubblicazioni/pubblicazioni.model';
import { PubblicazioneGaraService } from '../../services/pubblicazioni/pubblicazione-gara.service';
import { GaraInsertForm } from '../../models/gare/gare.model';
import { impersonificaRupForm } from '../../models/impersonifica-rup/impersonifica-rup.model';
import { ImpersonificaRupService } from '../../services/impersonifica-rup/impersonifica-rup.service';
import { Constants } from '../../../app.constants';


export interface PubblicazioneGaraProviderArgs extends IDictionary<any> {
    codGara?: string,
    stazioneAppaltante?: StazioneAppaltanteInfo;
    cfStazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
    smartCig?:boolean;
}

@Injectable({ providedIn: 'root' })
export class ImpersonificaRupProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('GaraProvider >>>', args);

        if (args.buttonCode === 'save-rp') {
            const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
            const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
            const messagesPanel: HTMLElement = args.messagesPanel;
            let userProfile = args.userProfile;
            const setUpdateState: Function = args.setUpdateState;
            const codGara: string = args.codGara;
            const isTest: boolean = args.isTest === true;
            const stazioneAppaltante: StazioneAppaltanteInfo = args.stazioneAppaltante;
            const syscon: string = args.syscon;

            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
              
                
                // genero l'oggetto di richiesta

                let rpFormFactory: Function;

                
                let request: any = this.populateRequest(formBuilderConfig);               
                rpFormFactory = this.saveRpFactory(request);
                
                return this.requestHelper.begin(rpFormFactory, messagesPanel).pipe(
                    map((result: any) => {
                        if(result.esito == true){
                            userProfile = {
                                ...userProfile,
                                loaImpersonato: request.loaImpersonato,
                                idpImpersonato: request.idpImpersonato,
                                cfImpersonato: request.cfImpersonato,
                            };                    
                            this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, userProfile));
                            this.sdkMessagePanelService.showSuccess(messagesPanel, [
                                {
                                    message: 'IMPERSONIFICA.SUCCESS'
                                }
                            ])
                        } else{
                            this.sdkMessagePanelService.showError(messagesPanel, [
                                {
                                    message: 'IMPERSONIFICA.ERROR'
                                }
                            ]);
                        }
                                                
                        
                    }),
                    catchError((error: any, caught: Observable<any>) => {
                        this.scrollToMessagePanel(messagesPanel);
                        return throwError(() => error);
                    })
                );
                
            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-NON-COMPILATA'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            }
        } else if(args.buttonCode === 'back-home'){
            this.routerService.navigateToPage("home-page");
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

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara?: string): impersonificaRupForm {
        let request: impersonificaRupForm = {}
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

    private elaborateOne(field: SdkFormBuilderField, request: impersonificaRupForm): impersonificaRupForm {
        if (isObject(field) && !isEmpty(field.mappingOutput)) {
            if (field.type === 'COMBOBOX') {
                if (!isUndefined(field.data)) {
                    set(request, field.mappingOutput, field.data.key);
                }
            } else {                
                if (!isUndefined(field.data) && field.data != null) {
                    set(request, field.mappingOutput, field.data);
                }                                
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: impersonificaRupForm): impersonificaRupForm {
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

    private elaborateGroup(field: SdkFormBuilderField, request: impersonificaRupForm): impersonificaRupForm {
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
   
    private saveRpFactory(insertForm: GaraInsertForm) {
        return () => {
            return this.impersonificaRupService.saveRp(insertForm);
        }
    }

    // #endregion

    // #region Getters

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get impersonificaRupService(): ImpersonificaRupService { return this.injectable(ImpersonificaRupService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion
}
