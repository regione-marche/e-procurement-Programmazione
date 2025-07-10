import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { Observable, Observer } from 'rxjs';

export interface DettaglioInterventoProviderArgs extends IDictionary<any> {
    idProgramma?: string;
    tipologia?: string;
    idIntervento?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class DettaglioInterventoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: DettaglioInterventoProviderArgs): Observable<IDictionary<any>> {
        if (args.buttonCode === 'update') {

            let params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia,
                idIntervento: args.idIntervento
            }
            this.routerService.navigateToPage('modifica-intervento-page', params);

        } else if (args.buttonCode === 'back') {
            let params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia
            }
            this.routerService.navigateToPage('dett-prog-interventi-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }


    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

}
