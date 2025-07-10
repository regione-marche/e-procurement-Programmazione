import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBase64Helper, SdkBaseService, SdkProvider, SdkRedirectService, SdkRouterService } from '@maggioli/sdk-commons';
import { set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    DettaglioProgrammaStoreService,
} from '../../layout/components/business/dettaglio-programma/dettaglio-programma-store.service';
import { ListaProgrammiStoreService } from '../../layout/components/business/lista-programmi/lista-programmi-store.service';
import {
    DettaglioOperaIncompiutaStoreService,
} from '../../layout/components/business/opera-incompiuta/dettaglio-opera-incompiuta-store.service';
import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface DettaglioProgrammaProviderArgs extends IDictionary<any> {
    action: 'DETAIL-OPERA-INCOMPIUTA' | 'DELETE-OPERA-INCOMPIUTA';
    idProgramma?: string;
    tipologia?: string;
    idOperaIncompiuta?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class DettaglioProgrammaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: DettaglioProgrammaProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('DettaglioProgrammaProvider', args);
        if (args.action === 'DETAIL-OPERA-INCOMPIUTA') {
            this.detailOperaIncompiuta(args);
        } else if (args.action === 'DELETE-OPERA-INCOMPIUTA') {
            return this.deleteOperaIncompiuta(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.buttonCode === 'back') {
            return this.back();
        } else if (args.buttonCode === 'back-to-dett-prog') {
            let setUpdateState: Function = args.setUpdateState;
            setUpdateState(false);
            return this.backDettProg();
        } else if (args.buttonCode === 'nuova-opera-incompiuta') {
            return this.nuovaOperaIncompiuta();
        } else if (args.buttonCode === 'pulisciFiltri') {
            return this.clean();
        } else if (args.buttonCode === 'update') {
            let params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia
            }
            this.routerService.navigateToPage('modifica-programma-page', params);
        } else if (args.buttonCode === 'download') {
            this.download(args.identificativoProg, args.tipologia, args.messagesPanel);
        } else if (args.buttonCode === 'elimina-flussi-programma') {
            return this.eliminaFlussiProgramma(args, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));            
        } else if (args.buttonCode === 'redirect-link-pubb') {
            this.linkPubbblicazione(args)           
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private eliminaFlussiProgramma(args: DettaglioProgrammaProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;       
        factory = this.deleteFlussiProgrammaFactory(args.idProgramma);       
        return this.requestHelper.begin(factory, messagesPanel);
    }
    
    private deleteFlussiProgrammaFactory(idProgramma: string) {
        return () => {
            return this.programmiService.deleteFlussoProgramma(idProgramma);
        }
    }   

    private back(): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {};
        let searchString: string = this.listaProgrammiStore.searchString;
        if (searchString != null) {
            set(params, 'searchString', searchString);
        }

        let tipologia: string = this.listaProgrammiStore.tipologia;
        if (tipologia != null) {
            set(params, 'tipologia', tipologia);
        }

        this.routerService.navigateToPage('lista-programmi-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backDettProg(): Observable<IDictionary<any>> {
        let idProgramma: string = this.dettaglioProgrammaStore.idProgramma;
        let tipologia: string = this.dettaglioProgrammaStore.tipologia;
        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        };
        this.routerService.navigateToPage('dett-prog-opere-incompiute-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private clean(): Observable<IDictionary<any>> {
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(
                {
                    reloadGrid: true
                }
            );
            ob.complete();
        });
    }

    private detailOperaIncompiuta(args: DettaglioProgrammaProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma: args.idProgramma,
            tipologia: args.tipologia,
            idOperaIncompiuta: args.idOperaIncompiuta
        };

        this.dettaglioOperaIncompiutaStore.clear();
        this.dettaglioOperaIncompiutaStore.idProgramma = args.idProgramma;
        this.dettaglioOperaIncompiutaStore.tipologia = args.tipologia;
        this.dettaglioOperaIncompiutaStore.idOperaIncompiuta = args.idOperaIncompiuta;

        this.routerService.navigateToPage('dett-generale-opera-incompiuta-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteOperaIncompiuta(args: DettaglioProgrammaProviderArgs): Observable<any> {
        let idProgramma: string = args.idProgramma;
        let idOperaIncompiuta: string = args.idOperaIncompiuta;
        let messagesPanel: HTMLElement = args.messagesPanel;
        let factory = this.getDeleteOperaIncompiutaFactory(idProgramma, idOperaIncompiuta);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteOperaIncompiutaFactory(idProgramma: string, idOperaIncompiuta: string) {
        return () => {
            return this.programmiService.deleteOperaIncompiuta(idProgramma, idOperaIncompiuta);
        }
    }

    private linkPubbblicazione(args: DettaglioProgrammaProviderArgs) {
        let idRicevuto: string = args.idRicevuto;    
        let messagesPanel: HTMLElement = args.messagesPanel;
        let factory = this.getlinkPubbblicazioneFactory(idRicevuto);
        this.requestHelper.begin(factory, messagesPanel).subscribe((result: any)=>{          
            window.open(result.data, "_blank");
        });
    }

    private getlinkPubbblicazioneFactory(idRicevuto: string) {
        return () => {
            return this.programmiService.getLinkPubbblicazione(idRicevuto);
        }
    }
    

    private nuovaOperaIncompiuta(): Observable<IDictionary<any>> {
        let idProgramma: string = this.dettaglioProgrammaStore.idProgramma;
        let tipologia: string = this.dettaglioProgrammaStore.tipologia;
        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        };
        this.routerService.navigateToPage('nuova-opera-incompiuta-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }


    private download(identificativoProgramma: string, tipologia: string, messagesPanel: HTMLElement) {
        let factory = this.getDownloadFactoryFactory(identificativoProgramma, tipologia);
        return this.requestHelper.begin(factory, messagesPanel).subscribe((response: any) => {
            let arrBuffer = this.sdkBase64Helper.base64ToArrayBuffer(response.data);
            let newBlob = new Blob([arrBuffer], { type: 'application/pdf' });
            let data = window.URL.createObjectURL(newBlob);
            let link = document.createElement('a');
            document.body.appendChild(link);
            link.href = data;
            link.download = identificativoProgramma;
            link.click();
            window.URL.revokeObjectURL(data);
            link.remove();
        });
    }

    private getDownloadFactoryFactory(idProgramma: string, tipologia: string) {
        return () => {
            return this.programmiService.getPdf(idProgramma, tipologia);
        }
    }





    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get listaProgrammiStore(): ListaProgrammiStoreService { return this.injectable(ListaProgrammiStoreService) }

    private get dettaglioProgrammaStore(): DettaglioProgrammaStoreService { return this.injectable(DettaglioProgrammaStoreService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkBase64Helper(): SdkBase64Helper { return this.injectable(SdkBase64Helper) }

    private get dettaglioOperaIncompiutaStore(): DettaglioOperaIncompiutaStoreService { return this.injectable(DettaglioOperaIncompiutaStoreService) }    

    // #endregion

}
