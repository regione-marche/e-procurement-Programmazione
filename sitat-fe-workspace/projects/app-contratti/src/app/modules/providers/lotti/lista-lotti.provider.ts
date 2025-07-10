import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isFunction } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { DettaglioLottoStoreService } from '../../layout/components/business/lotti/dettaglio-lotto-store.service';
import { GareService } from '../../services/gare/gare.service';
import { PubblicazioneFaseService } from '../../services/pubblicazioni/pubblicazione-fase.service';

export interface ListaLottiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    setUpdateState?: Function;
    isTest?: boolean;
    stazioneAppaltante?: string;
    tipoReport?: string;
    cig?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaLottiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaLottiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaLottiProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteLotto(args.codGara, args.codLotto, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailLotto(args);
        } else if (args.buttonCode === 'back') {
            return this.backListaGare();
        } else if (args.buttonCode === 'back-to-lista-lotti') {
            return this.backListaLotti(args);
        } else if (args.buttonCode === 'go-to-update-lotto') {
            return this.goUpdate(args);
        } else if (args.buttonCode === 'back-to-dettaglio-lotto') {
            return this.detailLotto(args);
        } else if (args.buttonCode === 'nuovo-lotto') {
            return this.nuovoLotto(args);
        } else if (args.buttonCode === 'elimina-flussi-lotto') {
            return this.eliminaFlussiLotto(args, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));            
        } else if (args.buttonCode === 'conferma-report-indicatori') {
            this.reportIndicatori(args);
        } else if (args.buttonCode === 'back-to-lista-schede-trasmesse') {
            return this.backToListaSchedeTrasmessePCP(args);
        }
        return of(args);
    }
    
    private reportIndicatori(args: ListaLottiProviderArgs): void {
        let factory: Function;       
        factory = this.getReportIndicatoriFactory(args.codGara, args.codLotto,args.stazioneAppaltante,args.tipoReport,args.cig);       
        this.requestHelper.begin(factory, args.messagesPanel).subscribe((result)=>{            
            var element = document.createElement('a');
            element.setAttribute('href', `data:application/pdf;base64,${result.data}`);
            if(args.tipoReport === 'anomalia'){
                element.setAttribute('download', 'reportAnomalia_' + args.cig + '.pdf');
            } else{
                element.setAttribute('download', 'reportPreliminari_' + args.cig + '.pdf');
            }
            

            element.style.display = 'none';
            document.body.appendChild(element);

            element.click();

            document.body.removeChild(element);

            
        });
    }

    private eliminaFlussiLotto(args: ListaLottiProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;       
        factory = this.deleteFlussiLottoFactory(args.codGara, args.codLotto);       
        return this.requestHelper.begin(factory, messagesPanel);
    }
    
    private deleteFlussiLottoFactory(codGara: string,codLotto: string) {
        return () => {
            return this.pubblicazioneFaseService.deleteFlussoLotto(codGara,codLotto);
        }
    }

    private getReportIndicatoriFactory(codGara: string,codLotto: string,stazioneAppaltante: string,tipoReport: string,cig: string) {
        return () => {
            return this.gareService.getReportIndicatori(codGara,codLotto,stazioneAppaltante,tipoReport,cig);
        }
    }

    private deleteLotto(codGara: string, codLotto: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteLottoFactory(codGara, codLotto);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteLottoFactory(codGara: string, codLotto: string) {
        return () => {
            return this.gareService.deleteLotto(codGara, codLotto);
        }
    }

    private detailLotto(args: ListaLottiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioLottoStore.clear();
        this.dettaglioLottoStore.codGara = args.codGara;
        this.dettaglioLottoStore.codLotto = args.codLotto;
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto
        };
        if(args.from != null){
            params = {...params,from:args.from};
        }
        this.routerService.navigateToPage('dettaglio-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaGare(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('lista-gare-page', { load: 'true' });
        return of(undefined);
    }

    private backListaLotti(args: ListaLottiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };

        if(args.from != null){           
            this.routerService.navigateToPage('lista-gare-page');                   
        } else{
            this.routerService.navigateToPage('lista-lotti-page', params);
        }
        
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(args: ListaLottiProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto
        };
        if(args.from != null){
            params = {...params,from:args.from};
        }
        this.routerService.navigateToPage('modifica-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovoLotto(args: ListaLottiProviderArgs): Observable<IDictionary<any>> {

        if (args.isTest === true) {
            let params: IDictionary<any> = {
                codGara: args.codGara
            };

            this.routerService.navigateToPage('nuovo-lotto-page', params);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToListaSchedeTrasmessePCP(args: ListaLottiProviderArgs): Observable<IDictionary<any>> {
    
        this.dettaglioLottoStore.clearFromLS();
        const params: IDictionary<any> = {};
        this.routerService.navigateToPage('lista-schede-trasmesse-pcp-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioLottoStore(): DettaglioLottoStoreService { return this.injectable(DettaglioLottoStoreService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get pubblicazioneFaseService(): PubblicazioneFaseService { return this.injectable(PubblicazioneFaseService) }

    // #endregion

}
