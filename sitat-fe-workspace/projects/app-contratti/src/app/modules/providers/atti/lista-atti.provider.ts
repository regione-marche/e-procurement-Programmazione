import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isFunction, isObject } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { DettaglioAttoStoreService } from '../../layout/components/business/atti/dettaglio-atto-store.service';
import { GareService } from '../../services/gare/gare.service';
import { PubblicazioneGaraService } from '../../services/pubblicazioni/pubblicazione-gara.service'; 
import { IdPubblicati } from '../../models/gare/gare.model';
export interface ListaAttiProviderArgs extends IDictionary<any> {
    action: 'DETAIL' | 'NUOVO';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    tipoDocumento?: string;
    numPubb?: string;
    campiObbligatori?: string;
    campiVisibili?: string;
    setUpdateState?: Function;
    attiMap?: IDictionary<string>;
    dataProgrammazione?: Date;
    annullato?: Array<IdPubblicati>;
}

@Injectable({ providedIn: 'root' })
export class ListaAttiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaAttiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaAttiProviderArgs >>>', args);
        if (args.action === 'DETAIL') {
            return this.detailAtto(args);
        } else if (args.action === 'NUOVO' || args.buttonCode === 'go-to-nuovo-atto') {
            return this.nuovoAtto(args);
        } else if (args.action === 'DELETE') {
            return this.deleteAttoSingolo(+args.codGara, +args.numPubb, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if(args.action === 'DELETE-DATA-PUBB-SISTEMA') {
            return this.annullaPubblicazione(+args.codGara, +args.numPubb, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if(args.action === 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND') {
            return this.annullaPubblicazioneMotivazione(+args.codGara, +args.numPubb, args.motivoCanc, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.buttonCode === 'back') {
            return this.backListaGare();
        } else if (args.buttonCode === 'back-to-lista-atti') {
            return this.backListaAtti(args);
        } else if (args.buttonCode === 'go-to-update-atto') {
            return this.updateAtto(args);
        } else if (args.buttonCode === 'delete-atto') {
            return this.deleteAtto(args);
        } else if (args.buttonCode === 'back-to-dettaglio-atto') {
            return this.detailAtto(args);
        } else if (args.buttonCode === 'elimina-flussi-atto') {
            return this.eliminaFlussiAtto(args, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));            
        } else if (args.buttonCode === 'redirect-link-pubb') {
            return this.linkPubbblicazione(args).pipe(map((data: any) => {
                return undefined;
            }));                      
        } else if(args.action === 'PUBBLICA-ATTO') { 
            return this.pubblicaAtto(+args.codGara, +args.numPubb, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true, pubblica: true };
            }));
        } else if (args.action === 'DATEPICKER-PUBB') {
            return this.programmaPubblicazioneAtto(+args.codGara, +args.numPubb, args.dataProgrammazione, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        }
            
        return of(args);
    }

    private linkPubbblicazione(args: ListaAttiProviderArgs) {
        let idRicevuto: string = args.idRicevuto;    
        let messagesPanel: HTMLElement = args.messagesPanel;
        let factory = this.getlinkPubbblicazioneFactory(idRicevuto,args.stazioneAppaltante,'ATTO');
        this.requestHelper.begin(factory, messagesPanel).subscribe((result: any)=>{          
            window.open(result.data, "_blank");
        });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private getlinkPubbblicazioneFactory(idRicevuto: string,stazioneAppaltante: string,entita: string) {
        return () => {
            return this.gareService.getLinkPubbblicazione(idRicevuto,stazioneAppaltante,entita);
        }
    }

    private eliminaFlussiAtto(args: ListaAttiProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;       
        factory = this.deleteFlussiAttoFactory(args.codGara, args.numPubb);       
        return this.requestHelper.begin(factory, messagesPanel);
    }
    
    private deleteFlussiAttoFactory(codGara: string,numPubb: string) {
        return () => {
            return this.pubblicazioneGaraService.deleteFlussoAtto(codGara,numPubb);
        }
    }   

    private detailAtto(args: ListaAttiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioAttoStore.clear();
        this.dettaglioAttoStore.codGara = args.codGara;
        this.dettaglioAttoStore.tipoDocumento = args.tipoDocumento;
        this.dettaglioAttoStore.numPubb = args.numPubb;
        this.dettaglioAttoStore.campiObbligatori = args.campiObbligatori;
        this.dettaglioAttoStore.campiVisibili = args.campiVisibili;
        this.dettaglioAttoStore.attiMap = args.attiMap;
        let params: IDictionary<any> = {
            codGara: args.codGara,
            tipoDocumento: args.tipoDocumento,
            numPubb: args.numPubb,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori,
            annullato: args.annullato
        };
        if (args.codLotto != null) {
            this.dettaglioAttoStore.codLotto = args.codLotto;
            params.codLotto = args.codLotto;
        }
        this.routerService.navigateToPage('dettaglio-atto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovoAtto(args: ListaAttiProviderArgs): Observable<IDictionary<any>> {

        if (isObject(args.attiMap)) {
            this.dettaglioAttoStore.clear();
            this.dettaglioAttoStore.attiMap = args.attiMap;
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            tipoDocumento: args.tipoDocumento,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('nuovo-atto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaGare(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('lista-gare-page', { load: 'true' });
        return of(undefined);
    }

    private backListaAtti(args: ListaAttiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };

        this.routerService.navigateToPage('lista-atti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private updateAtto(args: ListaAttiProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            codGara: args.codGara,
            tipoDocumento: args.tipoDocumento,
            numPubb: args.numPubb,
            campiVisibili: args.campiVisibili,
            campiObbligatori: args.campiObbligatori
        };
        this.routerService.navigateToPage('modifica-atto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteAtto(args: ListaAttiProviderArgs): Observable<IDictionary<any>> {

        let factory = this.deleteAttoFactory(args.codGara, args.numPubb);

        return this.requestHelper.begin(factory, args.messagesPanel)
            .pipe(map((_result: any) => {
                this.dettaglioAttoStore.clear();

                return {
                    reloadGrid: true
                };
            }));

    }

    private deleteAttoFactory(codGara: string, numPubb: string): () => Observable<any> {
        return () => {
            return this.gareService.deleteAtto(codGara, numPubb);
        }
    }

    private deleteAttoSingolo(codGara: number, numPubb: number, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.deleteAttoSingoloFactory(codGara, numPubb);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private deleteAttoSingoloFactory(idAtto: number, numPubb: number) {
        return () => {
            return this.gareService.deleteAttoSingolo(idAtto, numPubb);
        }
    }

    private pubblicaAtto(codGara: number, numPubb: number, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getPubblicaAttoFactory(codGara, numPubb);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getPubblicaAttoFactory(codGara: number, numPubb: number) {
        return () => {
            return this.gareService.pubblicaAtto(codGara, numPubb);
        }
    }

    private programmaPubblicazioneAtto(codGara: number, numPubb: number, dataProgrammazione: Date, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getProgrammaPubblicazioneAttoFactory(codGara, numPubb, dataProgrammazione);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getProgrammaPubblicazioneAttoFactory(idAtto: number, numPubb: number, dataProgrammazione: Date,) {
        return () => {
            return this.gareService.programmaPubbAtto(idAtto, numPubb, dataProgrammazione);
        }
    }

    private annullaPubblicazione(codGara: number, numPubb: number, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.annullaPubblicazioneFactory(codGara, numPubb);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private annullaPubblicazioneFactory(idAtto: number, numPubb: number) {
        return () => {
            return this.gareService.annullaPubblicazione(idAtto, numPubb);
        }
    }

    private annullaPubblicazioneMotivazione(codGara: number, numPubb: number, motivazione: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.annullaPubblicazioneMotivazioneFactory(codGara, numPubb, motivazione);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private annullaPubblicazioneMotivazioneFactory(idAtto: number, numPubb: number, motivazione: string,) {
        return () => {
            return this.gareService.annullaPubblicazioneMotivazione(idAtto, numPubb, motivazione);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get dettaglioAttoStore(): DettaglioAttoStoreService { return this.injectable(DettaglioAttoStoreService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }

    // #endregion

}
