import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isEmpty, isUndefined } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { GareService } from '../../services/gare/gare.service';

@Injectable({ providedIn: 'root' })
export class LottiAttoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<any> {
        this.logger.debug('LottiAttoProvider >>>', args);

        if (args.buttonCode === 'save-lotti-atto') {
            let messagesPanel: HTMLElement = args.messagesPanel;
            let codGara: string = args.codGara;
            let numPubb: string = args.numPubb;
            let listaAssociazioni: Array<string> = args.listaAssociazioni;
            this.sdkMessagePanelService.clear(messagesPanel);
            if (isUndefined(listaAssociazioni) || isEmpty(listaAssociazioni)) {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'LOTTI-ATTO.SELEZIONARE-UN-ELEMENTO'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            } else {
                let factory = this.associaLottiAttoFactory(codGara, numPubb, listaAssociazioni);
                return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
                    return {
                        reload: true
                    };
                }));
            }

        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private associaLottiAttoFactory(codGara: string, numPubb: string, idLotti: Array<string>) {
        return () => {
            return this.gareService.associaLottiAtto(codGara, numPubb, idLotti);
        }
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion
}
