import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { isFunction } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface ListaRisorseDiCapitoloProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    idIntervento?: string,
    idProgramma?: string,
    tipologia?: string,
    idRisorsaDiCapitolo?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaRisorseDiCapitoloProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaRisorseDiCapitoloProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaRisorseDiCapitoloProvider >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteRisorsaDiCapitolo(args.idProgramma, args.idIntervento, args.idRisorsaDiCapitolo, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.dettaglioRisorsaDiCapitolo(args.idProgramma, args.tipologia, args.idIntervento, args.idRisorsaDiCapitolo);
        } else if (args.buttonCode === 'back') {
            return this.back(args.idProgramma, args.tipologia);
        } else if (args.buttonCode === 'nuovo') {
            return this.goToNuovaRisorsaDiCapitolo(args.idProgramma, args.tipologia, args.idIntervento);
        } else if (args.buttonCode === 'back-to-risorse-di-capitolo') {
            let setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }
            return this.backToRisorseDiCapitolo(args.idProgramma, args.tipologia, args.idIntervento);
        } else if (args.buttonCode === 'update') {
            return this.goToModificaRisorsaDiCapitolo(args.idProgramma, args.tipologia, args.idIntervento, args.idRisorsaDiCapitolo);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private deleteRisorsaDiCapitolo(idProgramma: string, idIntervento: string, idRisorsaDiCapitolo: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteRisorsaDiCapitoloFactory(idProgramma, idIntervento, idRisorsaDiCapitolo);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteRisorsaDiCapitoloFactory(idProgramma: string, idIntervento: string, idRisorsaDiCapitolo: string) {
        return () => {
            return this.programmiService.deleteRisorsaDiCapitolo(idProgramma, idIntervento, idRisorsaDiCapitolo);
        }
    }

    private back(idProgramma: string, tipologia: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        }
        this.routerService.navigateToPage('dett-prog-interventi-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioRisorsaDiCapitolo(idProgramma: string, tipologia: string, idIntervento: string, idRisorsaDiCapitolo: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia,
            idIntervento,
            idRisorsaDiCapitolo
        };
        this.routerService.navigateToPage('dett-risorsa-di-capitolo-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToRisorseDiCapitolo(idProgramma: string, tipologia: string, idIntervento: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia,
            idIntervento
        }
        this.routerService.navigateToPage('risorse-di-capitolo-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToNuovaRisorsaDiCapitolo(idProgramma: string, tipologia: string, idIntervento: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia,
            idIntervento
        }
        this.routerService.navigateToPage('nuova-risorsa-di-capitolo-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToModificaRisorsaDiCapitolo(idProgramma: string, tipologia: string, idIntervento: string, idRisorsaDiCapitolo: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia,
            idIntervento,
            idRisorsaDiCapitolo
        }
        this.routerService.navigateToPage('modifica-risorsa-di-capitolo-page', params);
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
