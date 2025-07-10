import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isFunction, isObject } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';

import { DettaglioAttoStoreService } from '../../layout/components/business/atti/dettaglio-atto-store.service';

export interface ListaAttiLottoProviderArgs extends IDictionary<any> {
    action: 'DETAIL' | 'NUOVO';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    tipoDocumento?: string;
    numPubb?: string;
    campiObbligatori?: string;
    campiVisibili?: string;
    setUpdateState?: Function;
    attiMap?: IDictionary<string>;
}

@Injectable({ providedIn: 'root' })
export class ListaAttiLottoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaAttiLottoProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaAttiLottoProviderArgs >>>', args);
        if (args.buttonCode === 'go-to-nuovo-atto') {
            return this.nuovoAtto(args);
        } else if (args.buttonCode === 'back-to-lista-atti-lotto') {
            return this.backListaAttiLotto(args);
        } else if (args.buttonCode === 'go-to-update-atto') {
            return this.updateAtto(args);
        } else if (args.buttonCode === 'back-to-dettaglio-atto' || args.action === 'DETAIL') {
            return this.detailAtto(args);
        } else if (args.buttonCode === 'back-to-lista-lotti') {
            return this.listaLotti(args);
        }
        return of(args);
    }

    private detailAtto(args: ListaAttiLottoProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioAttoStore.clear();
        this.dettaglioAttoStore.codGara = args.codGara;
        this.dettaglioAttoStore.codLotto = args.codLotto;
        this.dettaglioAttoStore.tipoDocumento = args.tipoDocumento;
        this.dettaglioAttoStore.numPubb = args.numPubb;
        this.dettaglioAttoStore.campiObbligatori = args.campiObbligatori;
        this.dettaglioAttoStore.campiVisibili = args.campiVisibili;
        this.dettaglioAttoStore.attiMap = args.attiMap;
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            tipoDocumento: args.tipoDocumento,
            numPubb: args.numPubb,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('dettaglio-atto-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovoAtto(args: ListaAttiLottoProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.attiMap)) {
            this.dettaglioAttoStore.clear();
            this.dettaglioAttoStore.attiMap = args.attiMap;
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            tipoDocumento: args.tipoDocumento,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('nuovo-atto-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaAttiLotto(args: ListaAttiLottoProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto
        };

        this.routerService.navigateToPage('lista-atti-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateAtto(args: ListaAttiLottoProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            tipoDocumento: args.tipoDocumento,
            numPubb: args.numPubb,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('modifica-atto-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaLotti(args: ListaAttiLottoProviderArgs): Observable<IDictionary<any>> {

        this.dettaglioAttoStore.clear();
        let params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('lista-lotti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get dettaglioAttoStore(): DettaglioAttoStoreService { return this.injectable(DettaglioAttoStoreService) }

    // #endregion

}
