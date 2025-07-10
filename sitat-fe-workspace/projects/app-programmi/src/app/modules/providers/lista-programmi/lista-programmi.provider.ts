import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    DettaglioProgrammaStoreService,
} from '../../layout/components/business/dettaglio-programma/dettaglio-programma-store.service';
import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface ListaProgrammiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL' | 'COPY-UPDATE';
    idProgramma?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    tipologia?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaProgrammiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaProgrammiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaProgrammiProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteProgramma(args.idProgramma, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailProgramma(args.idProgramma, args.tipologia);
        } else if (args.action === 'COPY-UPDATE') {
            return this.copyUpdate(args.idProgramma, args.messagesPanel);
        } else {
            if (args.buttonCode === 'back') {
                return this.back();
            } else if (args.buttonCode === 'pulisciFiltri') {
                return this.clean();
            }
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private deleteProgramma(idProgramma: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteProgrammaFactory(idProgramma);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteProgrammaFactory(idProgramma: string) {
        return () => {
            return this.programmiService.deleteProgramma(idProgramma);
        }
    }

    private detailProgramma(idProgramma: string, tipologia: string): Observable<IDictionary<any>> {
        this.dettaglioProgrammaStore.clear();
        this.dettaglioProgrammaStore.idProgramma = idProgramma;
        this.dettaglioProgrammaStore.tipologia = tipologia;
        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        };
        this.routerService.navigateToPage('dett-prog-dati-generali-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('home-page');
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

    private copyUpdate(idProgramma: string, messagesPanel: HTMLElement): Observable<IDictionary<any>> {
        let factory = this.getCopiaPerAggiornamentoFactory(idProgramma);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((idProgrammaCopiato: number) => {
                return {
                    idProgrammaCopiato
                };
            })
        )
    }

    private getCopiaPerAggiornamentoFactory(idProgramma: string) {
        return () => {
            return this.programmiService.copiaPerAggiornamento(idProgramma);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioProgrammaStore(): DettaglioProgrammaStoreService { return this.injectable(DettaglioProgrammaStoreService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion

}
