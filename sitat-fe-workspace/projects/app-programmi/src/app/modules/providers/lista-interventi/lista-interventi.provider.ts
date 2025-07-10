import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface ListaInterventiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    idIntervento?: string,
    idProgramma?: string,
    tipologia?: string,
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaInterventiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaInterventiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaInterventiProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteIntervento(args.idProgramma, args.idIntervento, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.dettaglioIntervento(args.idProgramma, args.idIntervento, args.tipologia);
        } else if (args.buttonCode === 'import') {
            return this.importInterventi(args.idProgramma, args.tipologia);
        } else if (args.buttonCode === 'nuovo') {
            return this.goToNuovoIntervento(args.idProgramma, args.tipologia);
        } else if (args.action === 'REPORT') {
            return this.reportSoggetto(args.idProgramma, args.messagesPanel);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    public base64ToBlob(b64Data, sliceSize = 512) {
        let byteCharacters = atob(b64Data); //data.file there
        let byteArrays = [];
        for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            let slice = byteCharacters.slice(offset, offset + sliceSize);

            let byteNumbers = new Array(slice.length);
            for (var i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }
            let byteArray = new Uint8Array(byteNumbers);
            byteArrays.push(byteArray);
        }
        return new Blob(byteArrays, { type: 'application/vnd.ms-excel' });
    }


    private reportSoggetto(idProgramma: string, messagesPanel: HTMLElement): any {
        let factory = this.getReportSoggettoFactory(idProgramma);
        return this.requestHelper.begin(factory, messagesPanel).subscribe((result: any) => {
            if (result.esito == true) {
                if (result.data != null) {
                    let blob = this.base64ToBlob(result.data);
                    let data = window.URL.createObjectURL(blob);
                    let link = document.createElement('a');
                    document.body.appendChild(link);
                    link.href = data;
                    link.download = 'soggettiAggregatori.xlsx';
                    link.click();
                    window.URL.revokeObjectURL(data);
                    link.remove();
                    //return { reload: true };
                }
            }
        });
    }

    private getReportSoggettoFactory(idProgramma: string) {
        return () => {
            return this.programmiService.getReportSoggetto(idProgramma);
        }
    }

    private deleteIntervento(idProgramma: string, idIntervento: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteInterventoFactory(idProgramma, idIntervento);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteInterventoFactory(idProgramma: string, idIntervento: string) {
        return () => {
            return this.programmiService.deleteIntervento(idProgramma, idIntervento);
        }
    }

    private importInterventi(idProgramma: string, tipologia: string): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        };
        this.routerService.navigateToPage('import-interventi-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioIntervento(idProgramma: string, idIntervento: string, tipologia: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            idIntervento,
            tipologia
        };
        this.routerService.navigateToPage('dett-intervento-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToNuovoIntervento(idProgramma: string, tipologia: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        }
        this.routerService.navigateToPage('nuovo-intervento-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion

}
