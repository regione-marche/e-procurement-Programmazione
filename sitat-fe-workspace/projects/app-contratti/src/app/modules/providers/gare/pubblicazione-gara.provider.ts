import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkStoreService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkNotificationService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';

import { PubblicaGaraEntry } from '../../models/pubblicazioni/pubblicazione-gara.model';
import { PubblicazioneResult } from '../../models/pubblicazioni/pubblicazioni.model';
import { PubblicazioneGaraService } from '../../services/pubblicazioni/pubblicazione-gara.service';


export interface PubblicazioneGaraProviderArgs extends IDictionary<any> {
    codGara?: string,
    stazioneAppaltante?: StazioneAppaltanteInfo;
    cfStazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
    smartCig?:boolean;
}

@Injectable({ providedIn: 'root' })
export class PubblicazioneGaraProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        if (args.buttonCode === 'check-pubblicazione') {
            return this.checkPubblicazione(args.codGara, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
        } else if (args.buttonCode === 'pubblica-gara') {
            return this.pubblica(args.codGara, args.cfStazioneAppaltante, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
        } else if (args.buttonCode === 'check-pubblicazione-smart') {
            return this.checkPubblicazioneSmart(args.codGara, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
        } else if (args.buttonCode === 'pubblica-smart') {
            return this.pubblicaSmart(args.codGara, args.cfStazioneAppaltante, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
        } else if (args.buttonCode === 'elimina-flussi-gara') {
            return this.eliminaFlussiGara(args, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
            
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

    private checkPubblicazione(codGara: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(messagesPanel);
        return this.getRequestPubblicazioneGara(codGara, null, messagesPanel, chiaviAccessoOrt, syscon)
            .pipe(
                mergeMap(this.getServiceAccessPubblica),
                mergeMap(this.executeCheckPubblicazioneGara),
                map((value: any) => {
                    return { ...value, sidebar: true };
                })
            );
    }

    private checkPubblicazioneSmart(codGara: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {
        console.log("Check Pubblica smartCig");
        return this.getRequestPubblicazioneSmartCig(codGara, null, messagesPanel, chiaviAccessoOrt, syscon)
        .pipe(
            mergeMap(this.getServiceAccessPubblica),
            mergeMap(this.executeCheckPubblicazioneSmartCig),
            map((value: any) => {
                return { ...value, sidebar: true };
            })
        );
    }


    private getRequestPubblicazioneGaraFactory(codGara: string) {
        return () => {
            return this.pubblicazioneGaraService.getRequestPubblicazioneGara(codGara);
        }
    }

    private getRequestPubblicazioneSmartCigFactory(codGara: string) {
        return () => {
            return this.pubblicazioneGaraService.getRequestPubblicazioneSmartCig(codGara);
        }
    }
    


    private getRequestPubblicazioneGara = (codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> => {
        let factory = this.getRequestPubblicazioneGaraFactory(codGara);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: PubblicaGaraEntry) => {
                return {
                    codGara,
                    gara: result,
                    stazioneAppaltante,
                    messagesPanel,
                    chiaviAccessoOrt,
                    syscon
                };
            })
        );
    }

    
    private getRequestPubblicazioneSmartCig = (codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> => {
        let factory = this.getRequestPubblicazioneSmartCigFactory(codGara);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: PubblicaGaraEntry) => {
                return {
                    codGara,
                    gara: result,
                    stazioneAppaltante,
                    messagesPanel,
                    chiaviAccessoOrt,
                    syscon
                };
            })
        );
    }

    private checkPubblicazioneGaraFactory(gara: PubblicaGaraEntry, token: string) {
        return () => {
            return this.pubblicazioneGaraService.checkPubblicazioneGara(gara, token);
        }
    }

    private checkPubblicazionSmartCigFactory(gara: PubblicaGaraEntry, token: string) {
        return () => {
            return this.pubblicazioneGaraService.checkPubblicazioneSmartCig(gara, token);
        }
    }

    private executeCheckPubblicazioneGara = ({ gara, messagesPanel, token }: IDictionary<any>): Observable<PubblicazioneResult> => {
        let factory = this.checkPubblicazioneGaraFactory(gara, token);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private executeCheckPubblicazioneSmartCig = ({ gara, messagesPanel, token }: IDictionary<any>): Observable<PubblicazioneResult> => {
        let factory = this.checkPubblicazionSmartCigFactory(gara, token);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private pubblica(codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(messagesPanel);
        return this.getRequestPubblicazioneGara(codGara, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon)
            .pipe(
                mergeMap(this.getServiceAccessPubblica),
                mergeMap(this.executePubblicazioneGara),
                mergeMap(this.getListaPubblicazioniGara),
                mergeMap(this.allineaPubblicazione),
                tap(() => {
                    let message: string = this.translate.instant('PUBBLICAZIONI.GARA-PUBBLICATA');
                    this.sdkMessagePanelService.showSuccess(messagesPanel, [
                        {
                            message: message
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                })
            );

    }

    private eliminaFlussiGara(args: PubblicazioneGaraProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;       
        factory = this.deleteFlussiGaraFactory(args.codGara);       
        return this.requestHelper.begin(factory, messagesPanel);
    }
    
    private deleteFlussiGaraFactory(codGara: string) {
        return () => {
            return this.pubblicazioneGaraService.deleteFlussoGara(codGara);
        }
    }   

    private pubblicaSmart(codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {
        console.log("Pubblica smartCig");
        this.sdkMessagePanelService.clear(messagesPanel);
        return this.getRequestPubblicazioneSmartCig(codGara, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon)
            .pipe(
                mergeMap(this.getServiceAccessPubblica),
                mergeMap(this.executePubblicazioneSmartCig),
                mergeMap(this.getListaPubblicazioniGara),
                mergeMap(this.allineaPubblicazione),
                tap(() => {
                    let message: string = this.translate.instant('PUBBLICAZIONI.GARA-PUBBLICATA');
                    this.sdkMessagePanelService.showSuccess(messagesPanel, [
                        {
                            message: message
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                })
            );
    }
    

    
    private pubblicazioneSmartCigFactory(gara: any, token: string) {
        return () => {
            return this.pubblicazioneGaraService.pubblicaSmartCig(gara, token);
        }
    }

    private pubblicazioneGaraFactory(gara: PubblicaGaraEntry, token: string) {
        return () => {
            return this.pubblicazioneGaraService.pubblicaGara(gara, token);
        }
    }

    private executePubblicazioneGara = ({ codGara, gara, stazioneAppaltante, messagesPanel, token, syscon }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneGaraFactory(gara, token);
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneResult) => {
                return {
                    codGara,
                    stazioneAppaltante,
                    idRicevuto: result.id,
                    messagesPanel,
                    syscon,
                    gara
                }
            }),
            catchError((error: any) => {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'ERRORS.PUBLISH-FAILURE'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
                return throwError(() => error);
            })
        );
    }

    private executePubblicazioneSmartCig = ({ codGara, gara, stazioneAppaltante, messagesPanel, token, syscon }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneSmartCigFactory(gara, token);
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneResult) => {
                return {
                    codGara,
                    stazioneAppaltante,
                    idRicevuto: result.id,
                    messagesPanel,
                    syscon,
                    gara
                }
            }),
            catchError((error: any) => {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'ERRORS.PUBLISH-FAILURE'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
                return throwError(() => error);
            })
        );
    }


    private getListaPubblicazioniGaraFactory(codGara: string) {
        return () => {
            return this.pubblicazioneGaraService.getListaPubblicazioniGara(codGara);
        }
    }

    private getListaPubblicazioniGara = ({ codGara, stazioneAppaltante, idRicevuto, messagesPanel, syscon, gara }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.getListaPubblicazioniGaraFactory(codGara);
        return this.requestHelper.begin(factory).pipe(
            map((result: Array<string>) => {
                return {
                    codGara,
                    stazioneAppaltante,
                    idRicevuto,
                    tipoInvio: result == null || result.length == 0 ? '1' : '2', // 1 primo invio, 2 rettifica
                    messagesPanel,
                    syscon,
                    gara
                }
            }),
            catchError((error: any) => {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'ERRORS.PUBLISH-FAILURE'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
                return throwError(() => error);
            })
        );
    }

    private allineaPubblicazioneFactory(codGara: string, stazioneAppaltante: string, idRicevuto: string, tipoInvio: string, syscon: string, payload: string) {
        return () => {
            return this.pubblicazioneGaraService.allineaPubblicazioneGara(codGara, stazioneAppaltante, idRicevuto, tipoInvio, syscon, payload);
        }
    }

    private allineaPubblicazione = ({ codGara, stazioneAppaltante, idRicevuto, tipoInvio, messagesPanel, syscon, gara }: IDictionary<any>) => {
        let factory = this.allineaPubblicazioneFactory(codGara, stazioneAppaltante, idRicevuto, tipoInvio, syscon, JSON.stringify(gara));
        console.log(gara);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((_result: any) => {
            return {
                reload: true
            };
        }));
    }

    private getServiceAccessPubblica = ({ codGara, gara, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon }: IDictionary<any>) => {
        let factory = this.serviceAccessPubblicaFactory(chiaviAccessoOrt);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                codGara,
                gara,
                stazioneAppaltante,
                messagesPanel,
                token,
                syscon
            };
        }));
    }

    private serviceAccessPubblicaFactory(chiaviAccessoOrt: ChiaviAccessoOrt) {
        return () => {
            return this.pubblicazioneGaraService.serviceAccessPubblica(chiaviAccessoOrt);
        }
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkNotificationService(): SdkNotificationService { return this.injectable(SdkNotificationService) }

    private get translate(): TranslateService { return this.injectable(TranslateService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    

    // #endregion
}