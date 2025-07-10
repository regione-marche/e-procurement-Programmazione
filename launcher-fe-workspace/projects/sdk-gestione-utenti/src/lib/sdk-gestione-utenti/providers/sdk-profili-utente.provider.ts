import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { filter, map as mapArray } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { ProfiloCheckDTO } from '../model/gestione-utenti.model';
import { ResponseDTO } from '../model/lib.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../services/gestione-utenti.service';

export interface SdkProfiliUtenteProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
    syscon?: number;
    listaProfili?: Array<ProfiloCheckDTO>;
}

@Injectable()
export class SdkProfiliUtenteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkProfiliUtenteProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkProfiliUtenteProvider >>>', args);

        if (args.buttonCode === 'save-profili-utente') {
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let listaProfili: Array<ProfiloCheckDTO> = args.listaProfili;

            let listaProfiliToSave: Array<string> = mapArray(filter(listaProfili, (one: ProfiloCheckDTO) => one.checked === true), (profiloToSave: ProfiloCheckDTO) => profiloToSave.codice);

            return this.gestioneUtentiService.setListaProfiliUtente(syscon, listaProfiliToSave).pipe(
                map((result: ResponseDTO<boolean>) => {

                    if (result.done === SdkGestioneUtentiConstants.RESPONSE_N && result.messages != null && result.messages.length > 0) {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: `SDK-UTENTE.VALIDATORS.${one}`
                            };
                            return message;
                        })
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                        this.scrollToMessagePanel(messagesPanel);
                    } else {
                        setUpdateState(false);

                        let params: IDictionary<any> = {
                            syscon
                        };
                        this.routerService.navigateToPage('profili-utente-page', params);
                    }

                    return {
                        response: result.response
                    };
                }),
                catchError((error: any, caught: Observable<any>) => {
                    if (error != null && error.error != null) {
                        if (error.error.error != null) {
                            let message: SdkMessagePanelTranslate = {
                                message: 'SDK-UTENTE.VALIDATORS.ERRORE-GENERICO',
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
                                    message: `SDK-UTENTE.VALIDATORS.${one}`
                                };
                                return message;
                            })
                            this.sdkMessagePanelService.showError(messagesPanel, messages);
                        }
                    }
                    this.scrollToMessagePanel(messagesPanel);
                    return throwError(() => error);
                })
            );

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

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

}
