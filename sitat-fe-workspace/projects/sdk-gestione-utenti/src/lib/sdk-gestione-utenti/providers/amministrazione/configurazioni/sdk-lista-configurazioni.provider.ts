import { Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import { isFunction } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';

import {
    SdkDettaglioConfigurazioneStoreService,
} from '../../../components/amministrazione/configurazioni/sdk-dettaglio-configurazione/sdk-dettaglio-configurazione-store.service';
import { RicercaConfigurazioniFormDTO, WConfigDTO } from '../../../model/amministrazione.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';

export interface SdkListaConfigurazioniProviderArgs extends IDictionary<any> {
    action: 'DETAIL';
    searchForm?: RicercaConfigurazioniFormDTO;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    item?: WConfigDTO;
    setUpdateState?: Function;
}

@Injectable()
export class SdkListaConfigurazioniProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaConfigurazioniProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaConfigurazioniProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.detailConfigurazione(args);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'back-to-lista-configurazioni') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-configurazione') {
            return this.goUpdate(args);
        } else if (args.buttonCode === 'back-to-dettaglio-configurazione') {
            return this.detailConfigurazione(args);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(SdkGestioneUtentiConstants.SEARCH_FORM_GESTIONE_CONFIGURAZIONI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        }
        return of(args);
    }

    private detailConfigurazione(args: SdkListaConfigurazioniProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioConfigurazioneStoreService.clear();
        this.sdkDettaglioConfigurazioneStoreService.codApp = args.item.codApp;
        this.sdkDettaglioConfigurazioneStoreService.chiave = args.item.chiave;
        let params: IDictionary<any> = {
            codApp: args.item.codApp,
            chiave: args.item.chiave
        };
        this.routerService.navigateToPage('dettaglio-configurazione-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-configurazioni-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaConfigurazioniProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-configurazioni-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(args: SdkListaConfigurazioniProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codApp: args.item.codApp,
            chiave: args.item.chiave
        };
        this.routerService.navigateToPage('modifica-configurazione-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioConfigurazioneStoreService(): SdkDettaglioConfigurazioneStoreService { return this.injectable(SdkDettaglioConfigurazioneStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
