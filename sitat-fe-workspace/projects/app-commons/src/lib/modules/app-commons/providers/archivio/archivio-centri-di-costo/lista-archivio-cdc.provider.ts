import { Inject, Injectable, Injector } from '@angular/core';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import { isFunction } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../app-commons.constants';
import { DettaglioCdcStoreService } from '../../../components/archivio/archivio-centri-di-costo/dettaglio-cdc-store.service';
import { RicercaAvanzataArchivioCdcForm } from '../../../models/archivio/archivio-centri-di-costo.models';
import { ArchivioCentriDiCostoService } from '../../../services/archivio/archivio-centri-di-costo.service';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';

export interface ListaArchivioCdcProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    stazioneAppaltante?: string;
    searchForm?: RicercaAvanzataArchivioCdcForm;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    idCentro?: string;
    setUpdateState?: Function;
}

@Injectable()
export class ListaArchivioCdcProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: ListaArchivioCdcProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaArchivioCdcProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteCdc(args.idCentro, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailCdc(args);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-archivio-cdc') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-cdc') {
            return this.goUpdate(args.idCentro);
        } else if (args.buttonCode === 'back-to-dettaglio-cdc') {
            return this.detailCdc(args);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_CDC_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        }
        return of(args);
    }

    private deleteCdc(idCentro: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteCdcFactory(idCentro);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteCdcFactory(idCentro: string) {
        return () => {
            return this.archivioCdcService.deleteCdc(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, idCentro);
        }
    }

    private detailCdc(args: ListaArchivioCdcProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioCdcStoreService.clear();
        this.dettaglioCdcStoreService.idCentro = args.idCentro;
        let params: IDictionary<any> = {
            idCentro: args.idCentro
        };
        this.routerService.navigateToPage('dettaglio-cdc-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-archivio-cdc-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuovo-cdc-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: ListaArchivioCdcProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-archivio-cdc-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(idCentro: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idCentro
        };
        this.routerService.navigateToPage('modifica-cdc-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioCdcService(): ArchivioCentriDiCostoService { return this.injectable(ArchivioCentriDiCostoService) }

    private get dettaglioCdcStoreService(): DettaglioCdcStoreService { return this.injectable(DettaglioCdcStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
