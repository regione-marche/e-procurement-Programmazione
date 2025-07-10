import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import { isEmpty } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { RicercaAvvisoForm } from '../../models/avviso/avviso.model';
import { AvvisiService } from '../../services/avvisi/avvisi.service';

export interface ListaAvvisiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    idAvviso?: string;
    stazioneAppaltante?: string;
    searchForm?: RicercaAvvisoForm;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaAvvisiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaAvvisiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaAvvisiProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteAvviso(args.idAvviso, args.stazioneAppaltante, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailAvviso(args.idAvviso,args.stazioneAppaltante);
        } else {
            if (args.buttonCode === 'back') {
                return this.back(args);
            } else if (args.buttonCode === 'pulisciFiltri') {
                return this.clean();
            }
        }
        return of(args);
    }

    private deleteAvviso(idAvviso: string, stazioneAppaltante: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteAvvisoFactory(idAvviso, stazioneAppaltante);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteAvvisoFactory(idAvviso: string, stazioneAppaltante: string) {
        return () => {
            return this.avvisiService.deleteAvviso(idAvviso, stazioneAppaltante);
        }
    }

    private detailAvviso(idAvviso: string, stazioneAppaltante: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idAvviso,
            stazioneAppaltante
        }
        this.routerService.navigateToPage('dettaglio-avviso-page', params);
        return of(undefined);

    }

    private back(args: ListaAvvisiProviderArgs): Observable<IDictionary<any>> {
        const stateForm: RicercaAvvisoForm = args.stateForm;
        if (stateForm != null && !isEmpty(stateForm)) {
            this.routerService.navigateToPage('ricerca-avanzata-avvisi-page');
        } else {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_AVVISI_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        }
        return of(undefined);
    }

    private clean(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-avvisi-page');
        return of(undefined);
    }

    // #region public

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get avvisiService(): AvvisiService { return this.injectable(AvvisiService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

}
