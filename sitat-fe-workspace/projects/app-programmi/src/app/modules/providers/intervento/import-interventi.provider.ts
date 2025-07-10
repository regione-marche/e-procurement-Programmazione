import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { every, head, isObject } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { ChiaviIntervento, ImportInterventiForm } from '../../models/interventi/interventi-import.model';
import { ProgrammiService } from '../../services/programmi/programmi.service';

@Injectable({ providedIn: 'root' })
export class ImportInterventiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {

        if (args.buttonCode === 'back') {
            return this.back(args.idProgramma, args.tipologia, args.isNoRiproposto);
        } else if (args.buttonCode === 'import') {
            return this.importa(args.idProgramma, args.importData, args.messagesPanel, args.isNoRiproposto, args.tipologia, args.choice).pipe(map((data: any) => {
                return { reload: true };
            }));
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private back(idProgramma: string, tipologia: string, isNoRiproposto: boolean): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            idProgramma,
            tipologia
        };
        if (isNoRiproposto) {
            this.routerService.navigateToPage('dett-prog-interventi-non-riproposti-page', params);
        } else {
            this.routerService.navigateToPage('dett-prog-interventi-page', params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });


    }

    private importa(idProgramma: string, importData: Array<ChiaviIntervento>, messagesPanel: HTMLElement, isNoRiproposto: boolean, tipologia: string, choice:string): Observable<IDictionary<any>> {

        if (!isObject(importData) || importData.length == 0) {
            let message: string = '';
            if (tipologia === '1') {
                message = this.translate.instant('IMPORT-INTERVENTO.NO-SELECTION');
            } else if (tipologia === '2') {
                message = this.translate.instant('IMPORT-INTERVENTO.NO-SELECTION2');
            }
            this.sdkMessagePanelService.showError(messagesPanel, [
                {
                    message
                }
            ]);
            messagesPanel.scrollIntoView();
            return of(undefined);
        } else {

            let firstProgram: string = head(importData).idProgramma;
            let sameProgram: boolean = every(importData, one => one.idProgramma === firstProgram);
            if (sameProgram === false) {
                let message: string = '';
                if (tipologia === '1') {
                    message = this.translate.instant('IMPORT-INTERVENTO.INTERVENTI-NON-STESSO-PROGRAMMA');
                } else if (tipologia === '2') {
                    message = this.translate.instant('IMPORT-INTERVENTO.INTERVENTI-NON-STESSO-PROGRAMMA2');
                }
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message
                    }
                ]);
                messagesPanel.scrollIntoView();
                return of(undefined);
            }

            let form: ImportInterventiForm = {
                idProgramma: idProgramma,
                interventiToImport: importData
            }
            if( ! isNoRiproposto){
                form.choice = choice
            }
            let factory: any;
            if (isNoRiproposto) {
                factory = this.getImportaInterventiNRFactory(form);
            } else {
                factory = this.getImportaInterventiFactory(form);
            }

            return this.requestHelper.begin(factory, messagesPanel).pipe(
                map((result: any) => {
                    let params: IDictionary<any> = {
                        idProgramma,
                        tipologia
                    };
        
                    if (isNoRiproposto) {
                        this.routerService.navigateToPage('dett-prog-interventi-non-riproposti-page', params);
                    } else {
                        this.routerService.navigateToPage('dett-prog-interventi-page', params);
                    }

                    return {
                        ...result
                    }
                })
            );
        }
    }


    private getImportaInterventiNRFactory(form: ImportInterventiForm) {
        return () => {
            return this.programmiService.importaInterventiNonRip(form);
        }
    }

    private getImportaInterventiFactory(form: ImportInterventiForm) {
        return () => {
            return this.programmiService.importaInterventi(form);
        }
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get translate(): TranslateService { return this.injectable(TranslateService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

}
