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
import { DettaglioImpresaStoreService } from '../../../components/archivio/archivio-imprese/dettaglio-impresa-store.service';
import { RicercaAvanzataArchivioImpreseForm } from '../../../models/archivio/archivio-imprese.models';
import { ArchivioImpreseService } from '../../../services/archivio/archivio-imprese.service';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';

export interface ListaArchivioImpreseProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    stazioneAppaltante?: string;
    searchForm?: RicercaAvanzataArchivioImpreseForm;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codiceImpresa?: string;
    setUpdateState?: Function;
}

@Injectable()
export class ListaArchivioImpreseProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: ListaArchivioImpreseProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaArchivioImpreseProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteImpresa(args.codiceImpresa, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailImpresa(args);
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'nuovo') {
            return this.nuovo();
        } else if (args.buttonCode === 'back-to-archivio-imprese') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-imprese') {
            return this.goUpdate(args.codiceImpresa);
        } else if (args.buttonCode === 'back-to-dettaglio-imprese') {
            return this.detailImpresa(args);
        } else if (args.buttonCode === 'clean-button') {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_IMPRESE_DISPATCHER, undefined));
            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next({
                    cleanSearch: true
                });
                ob.complete();
            });
        }
        return of(args);
    }

    private deleteImpresa(codiceImpresa: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteImpresaFactory(codiceImpresa);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteImpresaFactory(codiceImpresa: string) {
        return () => {
            return this.archivioImpreseService.deleteImpresa(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codiceImpresa);
        }
    }

    private detailImpresa(args: ListaArchivioImpreseProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioImpresaStoreService.clear();
        this.dettaglioImpresaStoreService.codiceImpresa = args.codiceImpresa;
        let params: IDictionary<any> = {
            codiceImpresa: args.codiceImpresa
        };
        this.routerService.navigateToPage('dettaglio-impresa-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-archivio-imprese-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovo(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('nuova-impresa-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: ListaArchivioImpreseProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-archivio-imprese-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(codiceImpresa: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codiceImpresa
        };
        this.routerService.navigateToPage('modifica-impresa-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    private get dettaglioImpresaStoreService(): DettaglioImpresaStoreService { return this.injectable(DettaglioImpresaStoreService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
