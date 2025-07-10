import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, SdkStoreService } from '@maggioli/sdk-commons';
import { Observable, Observer } from 'rxjs';
import { SdkListaModelliPredispostiStoreService } from '../components/sdk-lista-modelli-predisposti/sdk-lista-modelli-predisposti-store.service';
import { ActivatedRoute } from '@angular/router';
import { SdkBreadcrumbsModelliStoreService } from '../components/sdk-lista-modelli-predisposti/sdk-breadcrumbsmodelli-store.service';



@Injectable()
export class SdkModelliPredispostiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        this.logger.debug('SdkModelliPredispostiProvider', args);
        if (args.buttonCode === 'go-to-modelli-predisposti') {

            let queryParams = this.activatedRoute.snapshot.queryParams;
            let currentUrl = window.location.href;
            let split = currentUrl.split('/');
            let finalUrlPart = split[split.length - 1];
            let slug = finalUrlPart.split('?')[0];

            this.sdkListaModelliPredispostiStoreService.clear();
            this.sdkListaModelliPredispostiStoreService.from = slug;
            this.sdkListaModelliPredispostiStoreService.queryParams = queryParams;

            this.sdkBreadcrumbsModelliStoreService.clear();
            this.sdkBreadcrumbsModelliStoreService.parentBreadcrumbs = args.breadcrumbs;
            this.sdkBreadcrumbsModelliStoreService.idEntita = args.identita;

            let params: IDictionary<any> = {
                entita: args.entita,
                schema: args.schema,
            };

            this.routerService.navigateToPage('sdk-modelli-predisposti-page', params);

        } else if (args.buttonCode === 'go-to-modelli-predisposti') {
            this.gestioneModelliList(args)
        } else if (args.buttonCode === 'back') {

            let slug: string = this.sdkListaModelliPredispostiStoreService.from;
            let queryParams: IDictionary<any> = this.sdkListaModelliPredispostiStoreService.queryParams;
            this.routerService.navigateToPage(slug, queryParams);
        }


        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private gestioneModelliList(args: any): void {
        this.routerService.navigateToPage('sdk-modelli-predisposti-page', args);

    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkListaModelliPredispostiStoreService(): SdkListaModelliPredispostiStoreService { return this.injectable(SdkListaModelliPredispostiStoreService) }

    private get sdkBreadcrumbsModelliStoreService(): SdkBreadcrumbsModelliStoreService { return this.injectable(SdkBreadcrumbsModelliStoreService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }
    // #endregion

}




