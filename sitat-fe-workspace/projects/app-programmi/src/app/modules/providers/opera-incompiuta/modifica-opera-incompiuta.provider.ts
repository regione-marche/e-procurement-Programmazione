import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { Observable, Observer } from 'rxjs';

export interface ModificaOperaIncompiutaProviderArgs extends IDictionary<any> {
    idProgramma?: string;
    tipologia?: string;
    idOperaIncompiuta?: string;
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
}

@Injectable({ providedIn: 'root' })
export class ModificaOperaIncompiutaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('DettaglioOperaIncompiutaProvider', args);
        if (args.buttonCode === 'back-generale') {
            let setUpdateState: Function = args.setUpdateState;
            setUpdateState(false);
            let params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia,
                idOperaIncompiuta: args.idOperaIncompiuta
            };

            this.routerService.navigateToPage('dett-generale-opera-incompiuta-page', params);

        } else if (args.buttonCode === 'back-altri-dati') {
            let setUpdateState: Function = args.setUpdateState;
            setUpdateState(false);
            let params: IDictionary<any> = {
                idProgramma: args.idProgramma,
                tipologia: args.tipologia,
                idOperaIncompiuta: args.idOperaIncompiuta
            };

            this.routerService.navigateToPage('dett-altri-dati-opera-incompiuta-page', params);

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
