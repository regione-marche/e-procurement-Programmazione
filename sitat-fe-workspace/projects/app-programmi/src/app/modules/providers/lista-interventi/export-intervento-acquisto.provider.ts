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
import { ExportInterventiAquistiForm } from '../../models/interventi/interventi.model';
import { ListaInterventiFilter } from '../../models/programmi/programmi.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';

export interface ExportInterventoAcquistoProviderArgs extends IDictionary<any> {
    buttonCode?: string;
    idProgramma?: string;
    codiceProgramma?: string;
    tipologia?: string;
    currentFilter?: ListaInterventiFilter;
    interventiForm?: FormGroup;
    acquistiForm?: FormGroup;
    messagesPanel?: HTMLElement;
}

@Injectable({ providedIn: 'root' })
export class ExportInterventoAcquistoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ExportInterventoAcquistoProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ExportInterventoAcquistoProvider >>>', args);
        if (args.buttonCode === 'export') {
            let form: ExportInterventiAquistiForm = {};
            if (args.tipologia == '1') {

                if (this.checkEmpty(args.interventiForm)) {
                    this.sdkMessagePanelService.showWarning(args.messagesPanel, [
                        {
                            message: 'EXPORT-INTERVENTI-ACQUISTI.EMPTY-COLUMNS'
                        }
                    ])
                    return of({});
                }

                each(keys(args.interventiForm.controls), (key: string) => {
                    form[key] = args.interventiForm.get(key).value;
                });
            } else {

                if (this.checkEmpty(args.acquistiForm)) {
                    this.sdkMessagePanelService.showWarning(args.messagesPanel, [
                        {
                            message: 'EXPORT-INTERVENTI-ACQUISTI.EMPTY-COLUMNS'
                        }
                    ])
                    return of({});
                }

                each(keys(args.acquistiForm.controls), (key: string) => {
                    form[key] = args.acquistiForm.get(key).value;
                });
            }
            form.contri = +args.idProgramma;

            if (args.currentFilter != null) {
                form.searchForm = {
                    annualita: args.currentFilter.annualita,
                    codiceCUP: args.currentFilter.codiceCUP,
                    codInterno: args.currentFilter.codInterno,
                    descrizione: args.currentFilter.descrizione,
                    importoTotaleDa: args.currentFilter.importoTotaleDa != null ? +args.currentFilter.importoTotaleDa : null,
                    importoTotaleA: args.currentFilter.importoTotaleA != null ? +args.currentFilter.importoTotaleA : null,
                    numeroCui: args.currentFilter.numeroCui,
                    rup: args.currentFilter.rup
                };
            }

            let factory: Function = this.exportInterventiAcquistiFactory(form);

            return this.requestHelper.begin(factory, args.messagesPanel).pipe(
                map((result: string) => {

                    let id: string = `${args.codiceProgramma}-export`;

                    let blob = new Blob([result], { type: 'text/csv;charset=utf-8;' });

                    let url = URL.createObjectURL(blob);
                    let link = document.createElement('a');
                    link.setAttribute('href', url);
                    link.setAttribute('id', id);
                    link.setAttribute('download', `${args.codiceProgramma}.csv`);
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

    private exportInterventiAcquistiFactory(form: ExportInterventiAquistiForm) {
        return () => {
            return this.programmiService.exportInterventiAcquisti(form);
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
