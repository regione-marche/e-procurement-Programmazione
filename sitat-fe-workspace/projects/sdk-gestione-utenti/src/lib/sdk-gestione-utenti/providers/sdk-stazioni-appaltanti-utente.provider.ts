import { Injectable, Injector } from '@angular/core';
import { FilterObject, IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { each, filter, findIndex, map as mapArray } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { UfficioIntestatarioCheckDTO } from '../model/gestione-utenti.model';
import { ResponseDTO } from '../model/lib.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../services/gestione-utenti.service';

export interface SdkStazioniAppaltantiUtenteProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
    syscon?: number;
    listaStazioniAppaltanti?: Array<UfficioIntestatarioCheckDTO>;
    currentFilter?: FilterObject<UfficioIntestatarioCheckDTO>;
}

@Injectable()
export class SdkStazioniAppaltantiUtenteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkStazioniAppaltantiUtenteProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkStazioniAppaltantiUtenteProvider >>>', args);

        if (args.buttonCode === 'save-stazioni-appaltanti-utente') {
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let syscon: number = args.syscon;
            let listaStazioniAppaltanti: Array<UfficioIntestatarioCheckDTO> = args.listaStazioniAppaltanti;

            let listaStazioniAppaltantiToSave: Array<string> = mapArray(
                filter(listaStazioniAppaltanti, (one: UfficioIntestatarioCheckDTO) => one.checked === true),
                (saToSave: UfficioIntestatarioCheckDTO) => saToSave.codice
            );

            return this.gestioneUtentiService.setListaUfficiIntestatariUtente(syscon, listaStazioniAppaltantiToSave).pipe(
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
                        this.routerService.navigateToPage('stazioni-appaltanti-utente-page', params);
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

        } else if (args.buttonCode === 'select-current-filtered-stazioni-appaltanti') {

            let listaStazioniAppaltanti: Array<UfficioIntestatarioCheckDTO> = args.listaStazioniAppaltanti;
            let currentFilter: FilterObject<UfficioIntestatarioCheckDTO> = args.currentFilter;

            if (listaStazioniAppaltanti != null && currentFilter != null && currentFilter.filteredValue != null && currentFilter.filteredValue.length > 0) {
                each(currentFilter.filteredValue, (one: UfficioIntestatarioCheckDTO) => {
                    let saIndex: number = findIndex(listaStazioniAppaltanti, (k: UfficioIntestatarioCheckDTO) => k.codice === one.codice);
                    if (saIndex != null && saIndex > -1) {
                        listaStazioniAppaltanti[saIndex].checked = true;
                    }
                });
            }

            let returnObj: IDictionary<any> = {
                ...args,
                listaStazioniAppaltantiEdit: listaStazioniAppaltanti
            };

            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next(returnObj);
                ob.complete();
            });
        } else if (args.buttonCode === 'unselect-current-filtered-stazioni-appaltanti') {
            let listaStazioniAppaltanti: Array<UfficioIntestatarioCheckDTO> = args.listaStazioniAppaltanti;
            let currentFilter: FilterObject<UfficioIntestatarioCheckDTO> = args.currentFilter;

            if (listaStazioniAppaltanti != null && currentFilter != null && currentFilter.filteredValue != null && currentFilter.filteredValue.length > 0) {
                each(currentFilter.filteredValue, (one: UfficioIntestatarioCheckDTO) => {
                    let saIndex: number = findIndex(listaStazioniAppaltanti, (k: UfficioIntestatarioCheckDTO) => k.codice === one.codice);
                    if (saIndex != null && saIndex > -1) {
                        listaStazioniAppaltanti[saIndex].checked = false;
                    }
                });
            }
            let returnObj: IDictionary<any> = {
                ...args,
                listaStazioniAppaltantiEdit: listaStazioniAppaltanti
            };

            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next(returnObj);
                ob.complete();
            });
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
