import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import {
    SdkDialogConfig,
    SdkMessagePanelConfig,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, isFunction } from 'lodash-es';
import { BehaviorSubject, Observable, Observer, Subject } from 'rxjs';

import { AccorpaMultilottoEntry, AccorpaMultilottoForm } from '../../models/gare/gare.model';
import { GareService } from '../../services/gare/gare.service';

export interface AccorpamentoLottiProviderArgs extends IDictionary<any> {
    stazioneAppaltante?: StazioneAppaltanteInfo;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    cigMaster?: string;
    codLottoMaster?: number;
    checkedCodLotti?: Array<number>;
    dialogConfigSubj?: BehaviorSubject<SdkDialogConfig>;
    syscon?: number;
}

@Injectable({ providedIn: 'root' })
export class AccorpamentoLottiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: AccorpamentoLottiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('AccorpamentoLottiProviderArgs >>>', args);
        if (args.buttonCode === 'conferma') {
            return this.conferma(args);
        } else if (args.buttonCode === 'continua') {
            return this.continua(args);
        } else if (args.buttonCode === 'termina') {
            return this.termina(args);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private conferma(args: AccorpamentoLottiProviderArgs): Observable<IDictionary<any>> {

        const dialogConfig: SdkDialogConfig = {
            header: this.translateService.instant('DIALOG.CONFERMA-ACCORPAMENTO-TITLE'),
            message: this.translateService.instant('DIALOG.CONFERMA-ACCORPAMENTO-TEXT', {
                cigMaster: args.cigMaster
            }),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        args.dialogConfigSubj.next(dialogConfig);
        const func = this.confermaAccorpamentoConfirm(args);
        dialogConfig.open.next(func);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private confermaAccorpamentoConfirm(args: AccorpamentoLottiProviderArgs): any {
        return () => {
            // chiamo il backend
            const body: AccorpaMultilottoForm = {
                codGara: +args.codGara,
                syscon: args.syscon,
                codLottoMaster: args.codLottoMaster,
                codLottoAccorpati: args.checkedCodLotti
            };
            const factory = this.accorpaMultilottoFactory(body);

            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result: AccorpaMultilottoEntry) => {
                if (result != null) {
                    if (result.anagraficaOk === true && result.aggiudicazioniOk === true) {
                        let setUpdateState: Function = args.setUpdateState;
                        if (isFunction(setUpdateState)) {
                            setUpdateState(false);
                        }
                        const params: IDictionary<any> = {
                            codGara: args.codGara,
                            cigMaster: args.cigMaster
                        };
                        this.routerService.navigateToPage('riepilogo-accorpamento-lotti-page', params);
                    } else {
                        const listaCig: Array<string> = result.cigInvalidi;
                        const messageConfig: SdkMessagePanelConfig = {
                            messages: [
                                {
                                    message: 'ACCORPAMENTO-LOTTI.VALIDAZIONE-KO'
                                }
                            ],
                            type: 'error',
                            list: true
                        };

                        const messages: Array<SdkMessagePanelTranslate> = new Array();
                        each(listaCig, (one: string) => {
                            messages.push({
                                message: 'ACCORPAMENTO-LOTTI.VALIDAZIONE-CIG',
                                messageParams: {
                                    value: one
                                }
                            });
                        });

                        messageConfig.messages = [...messageConfig.messages, ...messages];
                        this.sdkMessagePanelService.show(args.messagesPanel, [messageConfig]);
                        this.scrollToMessagePanel(args.messagesPanel);
                    }
                }
            });

        }
    }

    private termina(args: AccorpamentoLottiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('dettaglio-gara-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private accorpaMultilottoFactory(body: AccorpaMultilottoForm) {
        return () => {
            return this.gareService.accorpaMultilotto(body);
        }
    }

    private continua(args: AccorpamentoLottiProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('accorpamento-lotti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}
