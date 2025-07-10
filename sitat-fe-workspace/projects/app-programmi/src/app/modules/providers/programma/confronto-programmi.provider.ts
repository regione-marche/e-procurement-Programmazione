import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkMessagePanelService
} from '@maggioli/sdk-controls';
import { Observable, Observer } from 'rxjs';

import { FormGroup } from '@angular/forms';
import { every, keys, map as mapArray } from 'lodash-es';
import { map } from 'rxjs/operators';
import { ExportInterventiAquistiForm } from '../../models/interventi/interventi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface ConfrontoProgrammiProviderArgs extends IDictionary<any> {
    buttonCode?: string;
    contri?: string;
    idProgramma?: string;
    contriDaConfrontare?: string;
    idProgrammaDaConfrontare?: string;
    messagesPanel?: HTMLElement;
}

@Injectable({ providedIn: 'root' })
export class ConfrontoProgrammiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ConfrontoProgrammiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ConfrontoProgrammiProvider >>>', args);
        if (args.buttonCode === 'download-csv') {

            let factory: Function = this.downloadListaInterventiDaConfrontoFactory(args.contri, args.contriDaConfrontare);

            return this.requestHelper.begin(factory, args.messagesPanel).pipe(
                map((result: string) => {

                    let id: string = `${args.idProgramma}-${args.idProgrammaDaConfrontare}-export`;

                    let blob = new Blob([result], { type: 'text/csv;charset=utf-8;' });

                    let url = URL.createObjectURL(blob);
                    let link = document.createElement('a');
                    link.setAttribute('href', url);
                    link.setAttribute('id', id);
                    link.setAttribute('download', `${args.idProgramma}-${args.idProgrammaDaConfrontare}.csv`);
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);

                    return {
                        // close: true
                    };
                })
            );
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

    private downloadListaInterventiDaConfrontoFactory(contri: string, contriDaConfrontare: string) {
        return () => {
            return this.programmiService.downloadListaInterventiDaConfronto(contri, contriDaConfrontare);
        }
    }

    private checkEmpty(form: FormGroup): boolean {
        let checkList: Array<boolean> = mapArray(keys(form.controls), (key: string) => {
            return form.get(key).value;
        });
        return every(checkList, value => value === false);
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion
}
