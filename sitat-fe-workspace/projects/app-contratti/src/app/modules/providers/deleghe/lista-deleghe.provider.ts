import { Injectable, Injector } from "@angular/core";
import { HttpRequestHelper } from "@maggioli/app-commons";
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from "@maggioli/sdk-commons";
import { isFunction } from "lodash-es";
import { Observable, Observer, of } from "rxjs";
import { map } from "rxjs/operators";

import { DettaglioDelegaStoreService } from "../../layout/components/business/deleghe/dettaglio-delega-store.service";
import { DelegheService } from "../../services/deleghe/deleghe.service";

export interface ListaDelegheProviderArgs extends IDictionary<any> {
    action: "DELETE" | "DETAIL";
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    id?: string;
    setUpdateState?: Function;
}

@Injectable({ providedIn: "root" })
export class ListaDelegheProvider extends SdkBaseService implements SdkProvider {
    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaDelegheProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug("ListaDelegheProviderArgs >>>", args);
        if (args.action === "DELETE") {
            return this.deleteDelega(args.syscon, args.idProfilo, args.id, args.messagesPanel).pipe(
                map((data: any) => {
                    return { reload: true };
                })
            );
        } else if (args.action === "DETAIL") {
            return this.detailDelega(args);
        } else if (args.buttonCode === "back") {
            return this.back();
        } else if (args.buttonCode === "back-to-lista-deleghe") {
            return this.backListaDeleghe(args);
        } else if (args.buttonCode === "go-to-update-delega") {
            return this.goUpdate(args);
        } else if (args.buttonCode === "back-to-dettaglio-delega") {
            return this.detailDelega(args);
        } else if (args.buttonCode === "nuovo-delega") {
            return this.nuovoDelega(args);
        }
        return of(args);
    }

    private deleteDelega(syscon: string, idProfilo: string, id: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteDelegaFactory(syscon, idProfilo, id);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteDelegaFactory(syscon: string, idProfilo: string, id: string) {
        return () => {
            return this.delegheService.deleteDelega(syscon, idProfilo, id);
        };
    }

    private detailDelega(args: ListaDelegheProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioDelegaStore.clear();
        this.dettaglioDelegaStore.id = args.id;
        let params: IDictionary<any> = {
            id: args.id,
        };
        this.routerService.navigateToPage("dettaglio-delega-page", params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage("home-page");
        return of(undefined);
    }

    private backListaDeleghe(args: ListaDelegheProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {};

        this.routerService.navigateToPage("lista-deleghe-page", params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(args: ListaDelegheProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            id: args.id,
        };
        this.routerService.navigateToPage("modifica-delega-page", params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovoDelega(args: ListaDelegheProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {};

        this.routerService.navigateToPage("nuovo-delega-page", params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService {
        return this.injectable(SdkRouterService);
    }

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get dettaglioDelegaStore(): DettaglioDelegaStoreService {
        return this.injectable(DettaglioDelegaStoreService);
    }

    private get delegheService(): DelegheService {
        return this.injectable(DelegheService);
    }

    // #endregion
}
