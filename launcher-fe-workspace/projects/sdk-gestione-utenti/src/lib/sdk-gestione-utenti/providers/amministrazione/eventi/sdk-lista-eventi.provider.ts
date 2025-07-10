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
    SdkDettaglioEventoStoreService,
} from '../../../components/amministrazione/eventi/sdk-dettaglio-evento/sdk-dettaglio-evento-store.service';
import { RicercaEventiFormDTO, WLogEventiDTO } from '../../../model/amministrazione.model';
import { SdkGestioneUtentiConstants } from '../../../sdk-gestione-utenti.constants';

export interface SdkListaEventiProviderArgs extends IDictionary<any> {
    action: 'DETAIL';
    searchForm?: RicercaEventiFormDTO;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    item?: WLogEventiDTO;
    setUpdateState?: Function;
}

@Injectable()
export class SdkListaEventiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaEventiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaEventiProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.detailEvento(args);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'back-to-lista-eventi') {
            return this.backLista(args);
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

    private detailEvento(args: SdkListaEventiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioEventoStoreService.clear();
        this.sdkDettaglioEventoStoreService.idEvento = args.item.idEvento;
        let params: IDictionary<any> = {
            idEvento: args.item.idEvento
        };
        this.routerService.navigateToPage('dettaglio-evento-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-eventi-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaEventiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-eventi-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioEventoStoreService(): SdkDettaglioEventoStoreService { return this.injectable(SdkDettaglioEventoStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
