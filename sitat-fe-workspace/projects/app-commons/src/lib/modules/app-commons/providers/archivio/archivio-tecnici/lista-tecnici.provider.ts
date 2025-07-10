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
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../app-commons.constants';
import { DettaglioTecnicoStoreService } from '../../../components/archivio/archivio-tecnici/dettaglio-tecnico-store.service';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../services/tabellati/tabellati.service';

export interface ListaTecniciProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    codice?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
}

@Injectable()
export class ListaTecniciProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: ListaTecniciProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaTecniciProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteTecnico(args.codice, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailTecnico(args);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-archivio-tecnici') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-tecnico') {
            return this.goUpdate(args.codice);
        } else if (args.buttonCode === 'back-to-dettaglio-tecnico') {
            return this.detailTecnico(args);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_TECNICI_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private deleteTecnico(codice: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteTecnicoFactory(codice);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteTecnicoFactory(codice: string) {
        return () => {
            return this.tabellatiService.deleteTecnico(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codice);
        }
    }

    private detailTecnico(args: ListaTecniciProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioTecnicoStore.clear();
        this.dettaglioTecnicoStore.codice = args.codice;
        let params: IDictionary<any> = {
            codice: args.codice
        };
        this.routerService.navigateToPage('dettaglio-tecnico-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-archivio-tecnici-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuovo-tecnico-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: ListaTecniciProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-archivio-tecnici-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(codice: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codice
        };
        this.routerService.navigateToPage('modifica-tecnico-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get dettaglioTecnicoStore(): DettaglioTecnicoStoreService { return this.injectable(DettaglioTecnicoStoreService) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
