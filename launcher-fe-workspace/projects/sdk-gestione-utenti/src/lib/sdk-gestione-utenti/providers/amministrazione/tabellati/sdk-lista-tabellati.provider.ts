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
import { isFunction, map as mapArray, toString } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import {
    SdkDettaglioTabellatoStoreService,
} from '../../../components/amministrazione/tabellati/sdk-dettaglio-tabellato/sdk-dettaglio-tabellato-store.service';
import {
    SdkListaDettaglioTabellatoStoreService,
} from '../../../components/amministrazione/tabellati/sdk-lista-dettaglio-tabellato/sdk-lista-dettaglio-tabellato-store.service';
import {
    RicercaTabellatiFormDTO,
    Tab0DTO,
    Tab1DTO,
    Tab2DTO,
    Tab3DTO,
    Tab5DTO,
    TabXDTO,
    VTab4Tab6DTO,
} from '../../../model/amministrazione.model';
import { ResponseDTO } from '../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';
import { GestioneTabellatiService } from '../../../services/gestione-tabellati.service';

export interface SdkListaTabellatiProviderArgs extends IDictionary<any> {
    action: 'DETAIL' | 'DETAIL-2' | 'DELETE';
    searchForm?: RicercaTabellatiFormDTO;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    item?: VTab4Tab6DTO;
    item2?: TabXDTO;
    provenienza?: string;
    codiceTabellato?: string;
    identificativo?: string;
    setUpdateState?: Function;
}

@Injectable()
export class SdkListaTabellatiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaTabellatiProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.listaDettaglioTabellato(args);
        } else if (args.action === 'DETAIL-2') {
            return this.dettaglioTabellato(args);
        } else if (args.action === 'DELETE') {
            this.sdkMessagePanelService.clear(args.messagesPanel);
            return this.deleteTabellato(args).pipe(map((result: ResponseDTO<boolean>) => {
                if (result.done == SdkGestioneUtentiConstants.RESPONSE_N) {
                    let message: SdkMessagePanelTranslate;
                    if (result.messages.includes(SdkGestioneUtentiConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO)) {
                        message = {
                            message: `SDK-TABELLATO.VALIDATORS.${SdkGestioneUtentiConstants.GESTIONE_TABELLATI_TABELLATO_BLOCCATO}`
                        };
                    } else {
                        message = {
                            message: 'SDK-TABELLATO.VALIDATORS.DELETE-ERROR',
                        };
                    }
                    this.sdkMessagePanelService.showError(args.messagesPanel, [
                        message
                    ]);
                    return {};
                } else if(result.response) {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-TABELLATO.ELIMINATO-CON-SUCCESSO',
                    };
                    this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                }
            }));
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'back-to-lista-tabellati') {
            return this.backLista(args);
        } else if (args.buttonCode === 'back-to-lista-dettaglio-tabellato') {
            return this.backListaDettaglio(args);
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo(args);
        } else if (args.buttonCode === 'go-to-update-tabellato') {
            return this.modifica(args);
        } else if (args.buttonCode === 'back-to-dettaglio-tabellato') {
            return this.dettaglioTabellato(args);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(SdkGestioneUtentiConstants.SEARCH_FORM_GESTIONE_EVENTI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        }
        return of(args);
    }

    // va alla lista dei tabtip
    private listaDettaglioTabellato(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkListaDettaglioTabellatoStoreService.clear();
        this.sdkListaDettaglioTabellatoStoreService.provenienza = args.item.provenienza;
        this.sdkListaDettaglioTabellatoStoreService.codiceTabellato = args.item.codiceTabellato;
        let params: IDictionary<any> = {
            provenienza: args.item.provenienza,
            codiceTabellato: args.item.codiceTabellato
        };
        this.routerService.navigateToPage('lista-dettaglio-tabellato-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // va al dettaglio del tabtip
    private dettaglioTabellato(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let identificativo: string = this.getIdentificativo(args);

        this.sdkDettaglioTabellatoStoreService.clear();
        this.sdkDettaglioTabellatoStoreService.provenienza = args.provenienza;
        this.sdkDettaglioTabellatoStoreService.codiceTabellato = args.codiceTabellato;
        this.sdkDettaglioTabellatoStoreService.identificativo = identificativo;
        let params: IDictionary<any> = {
            provenienza: args.provenienza,
            codiceTabellato: args.codiceTabellato,
            identificativo
        };
        this.routerService.navigateToPage('dettaglio-tabellato-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-tabellati-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-tabellati-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaDettaglio(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            provenienza: args.provenienza,
            codiceTabellato: args.codiceTabellato
        };

        this.routerService.navigateToPage('lista-dettaglio-tabellato-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            provenienza: args.provenienza,
            codiceTabellato: args.codiceTabellato
        };

        this.routerService.navigateToPage('nuovo-tabellato-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private modifica(args: SdkListaTabellatiProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            provenienza: args.provenienza,
            codiceTabellato: args.codiceTabellato,
            identificativo: args.identificativo
        };

        this.routerService.navigateToPage('modifica-tabellato-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteTabellato(args: SdkListaTabellatiProviderArgs): Observable<any> {

        let identificativo: string = this.getIdentificativo(args);

        return this.gestioneTabellatiService.deleteTabellato(args.provenienza, args.codiceTabellato, identificativo)
            .pipe(
                map((result: ResponseDTO<boolean>) => {
                    return result;
                }),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    if (error.error.error != null) {

                        let message: SdkMessagePanelTranslate;

                        if(error.error.error == 'GESTIONE_TABELLATI_TABELLATO_BLOCCATO') {
                            message = {
                                message: `SDK-TABELLATO.VALIDATORS.${error.error.error}`
                            };
                        } else {
                            message = {
                                message: 'SDK-TABELLATO.VALIDATORS.ERRORE-GENERICO',
                                messageParams: {
                                    value: error.error.error
                                }
                            };
                        }
                        
                        this.sdkMessagePanelService.showError(args.messagesPanel, [
                            message
                        ]);
                    } else {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(error.error.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: one
                            };
                            return message;
                        })
                        this.sdkMessagePanelService.showError(args.messagesPanel, messages);
                    }
                    return throwError(() => error);
                })
            );
    }

    private getIdentificativo(args: SdkListaTabellatiProviderArgs): string {
        const provenienza: string = args.provenienza;
        let identificativo: string;

        if (provenienza === 'TAB0') {
            identificativo = (args.item2 as Tab0DTO).tab0tip;
        } else if (provenienza === 'TAB1') {
            identificativo = toString((args.item2 as Tab1DTO).tab1tip);
        } else if (provenienza === 'TAB2') {
            identificativo = (args.item2 as Tab2DTO).tab2tip;
        } else if (provenienza === 'TAB3') {
            identificativo = (args.item2 as Tab3DTO).tab3tip;
        } else if (provenienza === 'TAB5') {
            identificativo = (args.item2 as Tab5DTO).tab5tip;
        }
        return identificativo;
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkListaDettaglioTabellatoStoreService(): SdkListaDettaglioTabellatoStoreService { return this.injectable(SdkListaDettaglioTabellatoStoreService) }

    private get sdkDettaglioTabellatoStoreService(): SdkDettaglioTabellatoStoreService { return this.injectable(SdkDettaglioTabellatoStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneTabellatiService(): GestioneTabellatiService { return this.injectable(GestioneTabellatiService) }

    // #endregion

}
