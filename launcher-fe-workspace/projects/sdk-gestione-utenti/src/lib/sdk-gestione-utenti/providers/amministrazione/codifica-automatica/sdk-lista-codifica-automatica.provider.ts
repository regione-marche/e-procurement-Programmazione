import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isFunction } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';

import {
    SdkDettaglioCodificaAutomaticaStoreService,
} from '../../../components/amministrazione/codifica-automatica/sdk-dettaglio-codifica-automatica/sdk-dettaglio-codifica-automatica-store.service';
import { GConfCodDTO } from '../../../model/amministrazione.model';

export interface SdkListaCodificaAutomaticaProviderArgs extends IDictionary<any> {
    action: 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    item?: GConfCodDTO;
    setUpdateState?: Function;
    homeSlug?: string;
}

@Injectable()
export class SdkListaCodificaAutomaticaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkListaCodificaAutomaticaProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkListaCodificaAutomaticaProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.detailCodificaAutomatica(args);
        } else if (args.buttonCode === 'back') {
            return this.back(args);
        } else if (args.buttonCode === 'back-to-lista-codifica-automatica') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-codifica-automatica') {
            return this.goUpdate(args);
        } else if (args.buttonCode === 'back-to-dettaglio-codifica-automatica') {
            return this.detailCodificaAutomatica(args);
        }
        return of(args);
    }

    private detailCodificaAutomatica(args: SdkListaCodificaAutomaticaProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioCodificaAutomaticaStoreService.clear();
        this.sdkDettaglioCodificaAutomaticaStoreService.nomEnt = args.item.nomEnt;
        this.sdkDettaglioCodificaAutomaticaStoreService.nomCam = args.item.nomCam;
        this.sdkDettaglioCodificaAutomaticaStoreService.tipCam = args.item.tipCam;
        let params: IDictionary<any> = {
            nomEnt: args.item.nomEnt,
            nomCam: args.item.nomCam,
            tipCam: args.item.tipCam
        };
        this.routerService.navigateToPage('dettaglio-codifica-automatica-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(args: SdkListaCodificaAutomaticaProviderArgs): Observable<IDictionary<any>> {
        let homeSlug = args.homeSlug != null ? args.homeSlug : 'home-page';
            this.routerService.navigateToPage(homeSlug);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: SdkListaCodificaAutomaticaProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-codifica-automatica-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(args: SdkListaCodificaAutomaticaProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            nomEnt: args.item.nomEnt,
            nomCam: args.item.nomCam,
            tipCam: args.item.tipCam
        };
        this.routerService.navigateToPage('modifica-codifica-automatica-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioCodificaAutomaticaStoreService(): SdkDettaglioCodificaAutomaticaStoreService { return this.injectable(SdkDettaglioCodificaAutomaticaStoreService) }

    // #endregion

}
