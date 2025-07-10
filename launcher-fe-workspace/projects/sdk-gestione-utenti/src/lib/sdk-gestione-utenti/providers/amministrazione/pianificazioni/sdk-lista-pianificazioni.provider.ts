import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isFunction } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';

import {
    SdkDettaglioPianificazioneStoreService,
} from '../../../components/amministrazione/pianificazioni/sdk-dettaglio-pianificazione/sdk-dettaglio-pianificazione-store.service';
import { WQuartzDTO } from '../../../model/amministrazione.model';

export interface SdkListaPianificazioniProviderArgs extends IDictionary<any> {
    action: 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    item?: WQuartzDTO;
    setUpdateState?: Function;
    homeSlug?: string;
}

@Injectable()
export class SdkListaPianificazioniProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaPianificazioniProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaPianificazioniProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.detailPianificazione(args);
        } else if (args.buttonCode === 'back') {
            return this.back(args);
        } else if (args.buttonCode === 'back-to-lista-pianificazioni') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-pianificazione') {
            return this.goUpdate(args);
        } else if (args.buttonCode === 'back-to-dettaglio-pianificazione') {
            return this.detailPianificazione(args);
        }
        return of(args);
    }

    private detailPianificazione(args: SdkListaPianificazioniProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioPianificazioneStoreService.clear();
        this.sdkDettaglioPianificazioneStoreService.codApp = args.item.codApp;
        this.sdkDettaglioPianificazioneStoreService.beanId = args.item.beanId;
        let params: IDictionary<any> = {
            codApp: args.item.codApp,
            beanId: args.item.beanId
        };
        this.routerService.navigateToPage('dettaglio-pianificazione-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(args: SdkListaPianificazioniProviderArgs): Observable<IDictionary<any>> {
        let homeSlug = args.homeSlug != null ? args.homeSlug : 'home-page';
        this.routerService.navigateToPage(homeSlug);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaPianificazioniProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-pianificazioni-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(args: SdkListaPianificazioniProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codApp: args.item.codApp,
            beanId: args.item.beanId
        };
        this.routerService.navigateToPage('modifica-pianificazione-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioPianificazioneStoreService(): SdkDettaglioPianificazioneStoreService { return this.injectable(SdkDettaglioPianificazioneStoreService) }

    // #endregion

}
