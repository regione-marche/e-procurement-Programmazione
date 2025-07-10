import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkMessagePanelService
} from '@maggioli/sdk-controls';
import { Observable, Observer, of } from 'rxjs';

import { FormGroup } from '@angular/forms';
import { each, every, keys, map as mapArray } from 'lodash-es';
import { map } from 'rxjs/operators';
import { ExportInterventiAquistiForm, ExportInterventiAquistiNonRipropostiForm } from '../../models/interventi/interventi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface ExportInterventoAcquistoNrProviderArgs extends IDictionary<any> {
    buttonCode?: string;
    idProgramma?: string;
    codiceProgramma?: string;
    interventiAcquistiNrForm?: FormGroup;
    messagesPanel?: HTMLElement;
}

@Injectable({ providedIn: 'root' })
export class ExportInterventoAcquistoNrProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ExportInterventoAcquistoNrProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ExportInterventoAcquistoNrProvider >>>', args);
        if (args.buttonCode === 'export') {
            let form: ExportInterventiAquistiNonRipropostiForm = {};
            if (this.checkEmpty(args.interventiAcquistiNrForm)) {
                this.sdkMessagePanelService.showWarning(args.messagesPanel, [
                    {
                        message: 'EXPORT-INTERVENTI-ACQUISTI.EMPTY-COLUMNS'
                    }
                ])
                return of({});
            }

            each(keys(args.interventiAcquistiNrForm.controls), (key: string) => {
                form[key] = args.interventiAcquistiNrForm.get(key).value;
            });

            form.contri = +args.idProgramma;

            let factory: Function = this.exportInterventiAcquistiNrFactory(form);

            return this.requestHelper.begin(factory, args.messagesPanel).pipe(
                map((result: string) => {

                    let id: string = `${args.codiceProgramma}-export`;

                    let blob = new Blob([result], { type: 'text/csv;charset=utf-8;' });

                    let url = URL.createObjectURL(blob);
                    let link = document.createElement('a');
                    link.setAttribute('href', url);
                    link.setAttribute('id', id);
                    link.setAttribute('download', `${args.codiceProgramma}-non-riproposti.csv`);
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);

                    return {
                        close: true
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

    private exportInterventiAcquistiNrFactory(form: ExportInterventiAquistiForm) {
        return () => {
            return this.programmiService.exportInterventiAcquistiNr(form);
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
