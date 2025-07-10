import { Inject, Injectable, Injector } from '@angular/core';
import {
    FilterObject,
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBaseService,
    SdkProvider,
    SdkRouterService,
} from '@maggioli/sdk-commons';
import { each, filter, findIndex, map as mapArray } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { UsrSysconCheckEntry } from '../../../models/user/user.model';
import { HttpRequestHelper } from '../../../services/http/http-request-helper.service';
import { UserService } from '../../../services/user/user.service';


export interface UtentiStazioneAppaltanteProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
    codice?: string;
    listaUtenti?: Array<UsrSysconCheckEntry>;
    currentFilter?: FilterObject<UsrSysconCheckEntry>;
}

@Injectable()
export class UtentiStazioneAppaltanteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: UtentiStazioneAppaltanteProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('UtentiStazioneAppaltanteProvider >>>', args);

        if (args.buttonCode === 'save-utenti-stazione-appaltante') {
            let messagesPanel: HTMLElement = args.messagesPanel;
            let setUpdateState: Function = args.setUpdateState;
            let codice: string = args.codice;
            let listaUtenti: Array<UsrSysconCheckEntry> = args.listaUtenti;

            let listaUtentiToSave: Array<number> = mapArray(
                filter(listaUtenti, (one: UsrSysconCheckEntry) => one.checked === true),
                (utenteToSave: UsrSysconCheckEntry) => utenteToSave.syscon
            );

            let factory = this.setUtentiStazioneAppaltanteFactory(codice, listaUtentiToSave);

            return this.requestHelper.begin(factory, messagesPanel).pipe(
                map((result: boolean) => {

                    setUpdateState(false);

                    let params: IDictionary<any> = {
                        codice
                    };

                    this.routerService.navigateToPage('utenti-stazione-appaltante-page', params);

                    return {
                        response: result
                    };
                }),
                catchError((error: any, caught: Observable<any>) => {
                    this.scrollToMessagePanel(messagesPanel);
                    return throwError(() => error);
                })
            );

        } else if (args.buttonCode === 'select-current-filtered-utenti') {

            let listaUtenti: Array<UsrSysconCheckEntry> = args.listaUtenti;
            let currentFilter: FilterObject<UsrSysconCheckEntry> = args.currentFilter;

            if (listaUtenti != null && currentFilter != null && currentFilter.filteredValue != null && currentFilter.filteredValue.length > 0) {
                each(currentFilter.filteredValue, (one: UsrSysconCheckEntry) => {
                    let usrIndex: number = findIndex(listaUtenti, (k: UsrSysconCheckEntry) => k.syscon === one.syscon);
                    if (usrIndex != null && usrIndex > -1) {
                        listaUtenti[usrIndex].checked = true;
                    }
                });
            }

            let returnObj: IDictionary<any> = {
                ...args,
                listaUtentiEdit: listaUtenti
            };

            return new Observable((ob: Observer<IDictionary<any>>) => {
                ob.next(returnObj);
                ob.complete();
            });
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private setUtentiStazioneAppaltanteFactory(codiceSA: string, listaUtentiStazioneAppaltante: Array<number>) {
        return () => {
            return this.userService.setUtentiStazioneAppaltante(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceSA, listaUtentiStazioneAppaltante);
        };
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get userService(): UserService { return this.injectable(UserService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}
