import { HttpErrorResponse } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMessagePanelTranslate } from '@maggioli/sdk-controls';
import { isFunction, map as mapArray } from 'lodash-es';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import {
    SdkDettaglioServerPostaStoreService,
} from '../../../components/amministrazione/server-posta/sdk-dettaglio-server-posta/sdk-dettaglio-server-posta-store.service';
import { WMailDTO } from '../../../model/amministrazione.model';
import { ResponseDTO } from '../../../model/lib.model';
import { GestioneServerPostaService } from '../../../services/gestione-server-posta.service';

export interface SdkListaServerPostaProviderArgs extends IDictionary<any> {
    action: 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    item?: WMailDTO;
    setUpdateState?: Function;
    homeSlug?: string;
}

@Injectable()
export class SdkListaServerPostaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaServerPostaProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.detailServerPosta(args);
        } else if (args.action === 'DELETE') {
            this.sdkMessagePanelService.clear(args.messagesPanel);
            return this.deleteServerPosta(args.item.idCfg, args.messagesPanel).pipe(map((data: boolean) => {
                if (data) {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-SERVER-POSTA.ELIMINATO-CON-SUCCESSO',
                    };
                    this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                        message
                    ]);
                    return { reload: true };
                } else {
                    let message: SdkMessagePanelTranslate = {
                        message: 'SDK-SERVER-POSTA.VALIDATORS.DELETE-ERROR',
                    };
                    this.sdkMessagePanelService.showError(args.messagesPanel, [
                        message
                    ]);
                    return {};
                }
            }));
        } else if (args.buttonCode === 'back') {
            return this.back(args);
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-lista-server-posta') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-server-posta') {
            return this.goUpdate(args);
        } else if (args.buttonCode === 'back-to-dettaglio-server-posta') {
            return this.detailServerPosta(args);
        } else if (args.buttonCode === 'go-to-set-password-posta') {
            return this.setPasswordPosta(args);
        } else if (args.buttonCode === 'go-to-send-test-email') {
            return this.sendTestEmail(args);
        }
        return of(args);
    }

    private deleteServerPosta(idCfg: string, messagesPanel: HTMLElement): Observable<any> {
        return this.gestioneServerPostaService.deleteServerPosta(idCfg)
            .pipe(
                map((result: ResponseDTO<boolean>) => {
                    return result.response;
                }),
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    if (error.error.error != null) {
                        let message: SdkMessagePanelTranslate = {
                            message: 'SDK-SERVER-POSTA.VALIDATORS.ERRORE-GENERICO',
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

    private detailServerPosta(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioServerPostaStoreService.clear();
        this.sdkDettaglioServerPostaStoreService.idCfg = args.item.idCfg;
        let params: IDictionary<any> = {
            idCfg: args.item.idCfg
        };
        this.routerService.navigateToPage('dettaglio-server-posta-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {
        let homeSlug = args.homeSlug != null ? args.homeSlug : 'home-page';
            this.routerService.navigateToPage(homeSlug);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuovo-server-posta-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-server-posta-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idCfg: args.item.idCfg
        };
        this.routerService.navigateToPage('modifica-server-posta-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private setPasswordPosta(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idCfg: args.item.idCfg
        };
        this.routerService.navigateToPage('imposta-password-posta-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private sendTestEmail(args: SdkListaServerPostaProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idCfg: args.item.idCfg
        };
        this.routerService.navigateToPage('invia-test-email-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioServerPostaStoreService(): SdkDettaglioServerPostaStoreService { return this.injectable(SdkDettaglioServerPostaStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneServerPostaService(): GestioneServerPostaService { return this.injectable(GestioneServerPostaService) }

    // #endregion

}
