import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkRadioItem } from '@maggioli/sdk-controls';
import { Observable, Observer } from 'rxjs';


export interface SdkCheckNuovoUtenteProviderArgs extends IDictionary<any> {
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    selezioneUtenteData?: SdkRadioItem
}

@Injectable()
export class SdkCheckNuovoUtenteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: SdkCheckNuovoUtenteProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('SdkCheckNuovoUtenteProvider', args);

        if (args.buttonCode === 'conferma') {
            let selezioneUtenteData: SdkRadioItem = args.selezioneUtenteData;
            if (selezioneUtenteData.code === '1') {

            } else {
                this.routerService.navigateToPage('nuovo-utente-page');
            }
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
