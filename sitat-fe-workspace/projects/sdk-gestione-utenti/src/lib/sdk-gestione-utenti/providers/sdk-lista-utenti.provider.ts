import { HttpErrorResponse } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { isEmpty, isFunction, map as mapArray } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { SdkDettaglioUtenteStoreService } from '../components/sdk-dettaglio-utente/sdk-dettaglio-utente-store.service';
import { RicercaUtentiFormDTOInternal } from '../model/gestione-utenti.model';
import { ResponseDTO } from '../model/lib.model';
import { SdkGestioneUtentiConstants } from '../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../services/gestione-utenti.service';

export interface SdkListaUtentiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    searchForm?: RicercaUtentiFormDTOInternal;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    syscon?: number;
    setUpdateState?: Function;
}

@Injectable()
export class SdkListaUtentiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaUtentiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaUtentiProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            this.sdkMessagePanelService.clear(args.messagesPanel);
            return this.deleteUtente(args.syscon, args.messagesPanel).pipe(map((data: boolean) => {
                if (data) {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-UTENTE.ELIMINATO-CON-SUCCESSO',
                    };
                    this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                } else {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-UTENTE.VALIDATORS.DELETE-ERROR',
                    };
                    this.sdkMessagePanelService.showError(args.messagesPanel, [
                        message
                    ]);
                    return {};
                }
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailUtente(args);
        } else if (args.action === 'ENABLE') {
            this.sdkMessagePanelService.clear(args.messagesPanel);
            return this.abilitaUtente(args.syscon, args.messagesPanel).pipe(map((data: boolean) => {
                if (data) {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-UTENTE.ABILITATO-CON-SUCCESSO',
                    };
                    this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                } else {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-UTENTE.VALIDATORS.EMAIL-NON-INVIATA',
                    };
                    this.sdkMessagePanelService.showWarning(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                }
            }));
        } else if (args.action === 'DISABLE') {
            this.sdkMessagePanelService.clear(args.messagesPanel);
            return this.disabilitaUtente(args.syscon, args.messagesPanel).pipe(map((data: boolean) => {
                if (data) {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-UTENTE.DISABILITATO-CON-SUCCESSO',
                    };
                    this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                } else {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-UTENTE.VALIDATORS.EMAIL-NON-INVIATA',
                    };
                    this.sdkMessagePanelService.showWarning(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                }
            }));
        } else if (args.action === 'CHANGE-PASSWORD') {
            return this.changePassword(args.syscon);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-lista-utenti') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-utente') {
            return this.goUpdate(args.syscon);
        } else if (args.buttonCode === 'back-to-dettaglio-utente') {
            return this.detailUtente(args);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(SdkGestioneUtentiConstants.SEARCH_FORM_UTENTI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        } else if (args.buttonCode === 'go-to-update-profili-utente') {
            return this.goUpdateProfili(args.syscon);
        } else if (args.buttonCode === 'back-to-profili-utente') {
            return this.backProfiliUtente(args);
        } else if (args.buttonCode === 'go-to-update-stazioni-appaltanti-utente') {
            return this.goUpdateStazioniAppaltanti(args.syscon);
        } else if (args.buttonCode === 'back-to-stazioni-appaltanti-utente') {
            return this.backStazioniAppaltantiUtente(args);
        } else if (args.buttonCode === 'download-csv-utente') {
            this.exportCsv(args);
        }
        return of(args);
    }

    private deleteUtente(syscon: number, messagesPanel: HTMLElement): Observable<any> {
        return this.gestioneUtentiService.deleteUser(syscon)
            .pipe(
                map((result: ResponseDTO<boolean>) => {
                    return result.response;
                }),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
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
                    return throwError(() => error);
                })
            );
    }

    private detailUtente(args: SdkListaUtentiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioUtenteStoreService.clear();
        this.sdkDettaglioUtenteStoreService.syscon = args.syscon;
        let params: IDictionary<any> = {
            syscon: args.syscon
        };
        this.routerService.navigateToPage('dettaglio-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-utenti-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuovo-utente-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaUtentiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-utenti-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(syscon: number): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            syscon
        };
        this.routerService.navigateToPage('modifica-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private abilitaUtente(syscon: number, messagesPanel: HTMLElement): Observable<boolean> {
        return this.gestioneUtentiService.enableUser(syscon)
            .pipe(
                map((result: ResponseDTO<boolean>) => {
                    return result.response;
                }),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
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
                                message: one
                            };
                            return message;
                        })
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                    }
                    return throwError(() => error);
                })
            );
    }

    private disabilitaUtente(syscon: number, messagesPanel: HTMLElement): Observable<boolean> {
        return this.gestioneUtentiService.disableUser(syscon)
            .pipe(
                map((result: ResponseDTO<boolean>) => {
                    return result.response;
                }),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
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
                                message: one
                            };
                            return message;
                        })
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                    }
                    return throwError(() => error);
                })
            );
    }

    private goUpdateProfili(syscon: number): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            syscon
        };
        this.routerService.navigateToPage('modifica-profili-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backProfiliUtente(args: SdkListaUtentiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            syscon: args.syscon
        };

        this.routerService.navigateToPage('profili-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdateStazioniAppaltanti(syscon: number): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            syscon
        };
        this.routerService.navigateToPage('modifica-stazioni-appaltanti-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backStazioniAppaltantiUtente(args: SdkListaUtentiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            syscon: args.syscon
        };

        this.routerService.navigateToPage('stazioni-appaltanti-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private changePassword(syscon: number): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            syscon
        };
        this.routerService.navigateToPage('cambia-password-admin-utente-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }


    private exportCsv(args: SdkListaUtentiProviderArgs): void {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        this.gestioneUtentiService.getDownloadUtentiCsv(args.searchForm).subscribe((result: any) => {
            if (!isEmpty(result.response)) {
                let csvFile = document.createElement('a');

                csvFile.href = 'data:text/csv;charset=utf-8,' + encodeURI(result.response);
                csvFile.target = '_blank';
                csvFile.download = 'Lista_utenti.csv';

                csvFile.click();
            } else {
                let message: SdkMessagePanelTranslate = {
                    message: 'SDK-UTENTE.VALIDATORS.CSV-EMPTY',
                };
                this.sdkMessagePanelService.showError(args.messagesPanel, [
                    message
                ]);
            }
        },
            (err) => {
                let message: SdkMessagePanelTranslate = {
                    message: 'SDK-UTENTE.VALIDATORS.ERROR_UNEXPECTED',
                };
                this.sdkMessagePanelService.showError(args.messagesPanel, [
                    message
                ]);
            });

    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get sdkDettaglioUtenteStoreService(): SdkDettaglioUtenteStoreService { return this.injectable(SdkDettaglioUtenteStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

}
