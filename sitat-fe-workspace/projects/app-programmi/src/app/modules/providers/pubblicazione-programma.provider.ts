import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkStoreService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkNotificationService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';

import { ProgrammaPubblicatoForm } from '../models/programmi/programmi.model';
import {
    PubblicaProgrammaFornitureServiziEntry,
    PubblicaProgrammaLavoriEntry,
    PubblicazioneResult,
} from '../models/pubblicazione/pubblicazione.model';
import { ProgrammiService } from '../services/programmi/programmi.service';
import { PubblicazioneService } from '../services/pubblicazione/pubblicazione.service';

export interface PubblicazioneProgrammaProviderArgs extends IDictionary<any> {
    idProgramma?: string,
    tipologia?: string,
    stazioneAppaltante?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
}

@Injectable({ providedIn: 'root' })
export class PubblicazioneProgrammaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        if (args.buttonCode === 'check-pubblicazione') {
            if(args.tipologiaInstallazione != '1'){
                return this.checkPubblicazione(args.idProgramma, args.tipologia, args.stazioneAppaltante, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
            } else{
                return this.checkPubblicazioneDiretta(args.idProgramma, args.tipologia, args.stazioneAppaltante, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
            }            
        } else if (args.buttonCode === 'pubblica-programma') {
            if(args.tipologiaInstallazione != '1'){
                return this.pubblica(args.idProgramma, args.tipologia, args.stazioneAppaltante, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
            } else{
                return this.pubblicaDiretto(args.idProgramma, args.tipologia, args.stazioneAppaltante, args.messagesPanel, args.chiaviAccessoOrt, args.syscon);
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

    private checkPubblicazioneDiretta(idProgramma: string, tipologia: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {

        if (tipologia === '1') {
            return this.executeCheckPubblicazioneDirettoLavori(idProgramma, messagesPanel, chiaviAccessoOrt.clientId, syscon)
                .pipe(
                    map(result => {
                        return {
                            ...result,
                            action: 'open-sidebar'
                        }
                    })
                );
        } else if (tipologia === '2') {
            return this.executeCheckPubblicazioneDirettoFornitureServizi(idProgramma, messagesPanel, chiaviAccessoOrt.clientId, syscon)
                .pipe(
                    map(result => {
                        return {
                            ...result,
                            action: 'open-sidebar'
                        }
                    })
                );
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private checkPubblicazione(idProgramma: string, tipologia: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {

        if (tipologia === '1') {
            return this.getPubblicProgrammaLavori(idProgramma, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon)
                .pipe(
                    mergeMap(this.getServiceAccessPubblica),
                    mergeMap(this.executeCheckPubblicazioneLavori),
                    map(result => {
                        return {
                            ...result,
                            action: 'open-sidebar'
                        }
                    })
                );
        } else if (tipologia === '2') {
            return this.getPubblicProgrammaFornitureServizi(idProgramma, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon)
                .pipe(
                    mergeMap(this.getServiceAccessPubblica),
                    mergeMap(this.executeCheckPubblicazioneFornitureServizi),
                    map(result => {
                        return {
                            ...result,
                            action: 'open-sidebar'
                        }
                    })
                );
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private getPubblicaProgrammaLavoriFactory(idProgramma: string) {
        return () => {
            return this.programmiService.getRequestPubLavori(idProgramma);
        }
    }

    private getPubblicProgrammaLavori = (idProgramma: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> => {
        let factory = this.getPubblicaProgrammaLavoriFactory(idProgramma);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: PubblicaProgrammaLavoriEntry) => {
                return {
                    idProgramma,
                    programma: result,
                    stazioneAppaltante,
                    messagesPanel,
                    chiaviAccessoOrt,
                    syscon
                };
            })
        );
    }

    private getPubblicaProgrammaLavoriDirettoFactory(idProgramma: string, clientId: string, syscon: string) {
        return () => {
            return this.pubblicazioneService.pubblicaPubblicazioneLavoriDiretto(idProgramma, clientId, syscon);
        }
    }

    private checkPubblicazioneLavoriFactory(programma: PubblicaProgrammaLavoriEntry, token: string) {
        return () => {
            return this.pubblicazioneService.checkPubblicazioneLavori(programma, token);
        }
    }

    private executeCheckPubblicazioneLavori = ({ programma, messagesPanel, token }: IDictionary<any>): Observable<PubblicazioneResult> => {
        let factory = this.checkPubblicazioneLavoriFactory(programma, token);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private checkPubblicazioneDirettoLavoriFactory(idProgramma: string, clientId: string, syscon: string) {
        return () => {
            return this.pubblicazioneService.checkPubblicazioneDirettoLavori(idProgramma, clientId, syscon);
        }
    }

    private executeCheckPubblicazioneDirettoLavori(idProgramma, messagesPanel, clientId, syscon): Observable<PubblicazioneResult> {
        let factory = this.checkPubblicazioneDirettoLavoriFactory(idProgramma, clientId, syscon);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getPubblicProgrammaFornitureServiziFactory(idProgramma: string) {
        return () => {
            return this.programmiService.getRequestPubServizi(idProgramma);
        }
    }

    private getPubblicProgrammaFornitureServizi = (idProgramma: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> => {
        let factory = this.getPubblicProgrammaFornitureServiziFactory(idProgramma);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: PubblicaProgrammaLavoriEntry) => {
                return {
                    idProgramma,
                    programma: result,
                    stazioneAppaltante,
                    messagesPanel,
                    chiaviAccessoOrt,
                    syscon
                };
            })
        );
    }

    private getPubblicProgrammaFornitureServiziDirettoFactory(idProgramma: string, clientId: string, syscon: string) {
        return () => {
            return this.pubblicazioneService.pubblicaPubblicazioneFornitureServiziDiretto(idProgramma, clientId, syscon);
        }
    }

    private checkPubblicazioneFornitureServiziFactory(programma: PubblicaProgrammaFornitureServiziEntry, token: string) {
        return () => {
            return this.pubblicazioneService.checkPubblicazioneFornitureServizi(programma, token);
        }
    }

    private executeCheckPubblicazioneFornitureServizi = ({ programma, stazioneAppaltante, messagesPanel, token }: IDictionary<any>): Observable<PubblicazioneResult> => {
        let factory = this.checkPubblicazioneFornitureServiziFactory(programma, token);
        return this.requestHelper.begin(factory, messagesPanel);
    }


    private checkPubblicazioneDirettoFornitureServiziFactory(idProgramma: string, clientId: string, syscon: string) {
        return () => {
            return this.pubblicazioneService.checkPubblicazioneDirettoFornitureServizi(idProgramma, clientId, syscon);
        }
    }

    private executeCheckPubblicazioneDirettoFornitureServizi(idProgramma, messagesPanel, clientId, syscon): Observable<PubblicazioneResult> {
        let factory = this.checkPubblicazioneDirettoFornitureServiziFactory(idProgramma, clientId, syscon);
        return this.requestHelper.begin(factory, messagesPanel);
    }


    private pubblica(idProgramma: string, tipologia: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {

        if (tipologia === '1') {
            return this.getPubblicProgrammaLavori(idProgramma, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon)
                .pipe(
                    mergeMap(this.getServiceAccessPubblica),
                    mergeMap(this.executePubblicazioneLavori),
                    mergeMap(this.allineaPubblicazione),
                    tap(() => {
                        let message: string = this.translate.instant('PUBBLICAZIONI.PROGRAMMA-PUBBLICATO');
                        this.sdkMessagePanelService.showSuccess(messagesPanel, [
                            {
                                message: message
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    })
                );
        } else if (tipologia === '2') {
            return this.getPubblicProgrammaFornitureServizi(idProgramma, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon)
                .pipe(
                    mergeMap(this.getServiceAccessPubblica),
                    mergeMap(this.executePubblicazioneFornitureServizi),
                    mergeMap(this.allineaPubblicazione),
                    tap(() => {
                        let message: string = this.translate.instant('PUBBLICAZIONI.PROGRAMMA-PUBBLICATO');
                        this.sdkMessagePanelService.showSuccess(messagesPanel, [
                            {
                                message: message
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
                    })
                );
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private pubblicaDiretto(idProgramma: string, tipologia: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string): Observable<IDictionary<any>> {

        if (tipologia === '1') {
            this.sdkNotificationService.clearAll();
            let factory = this.getPubblicaProgrammaLavoriDirettoFactory(idProgramma, chiaviAccessoOrt.clientId, syscon);               
            return this.requestHelper.begin(factory).pipe( map((result: any) => {
                  
                if(result.esito){
                    let message: string = this.translate.instant('PUBBLICAZIONI.PROGRAMMA-PUBBLICATO');
                    this.sdkMessagePanelService.showSuccess(messagesPanel, [
                        {
                            message: message
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                }else{
                    if (result.validationError!=null && result.validationError.length>0){
                        const messages = result.validationError.map(message => ({ message }));
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                        this.scrollToMessagePanel(messagesPanel);
                    }
                }
                return {
                    reload: true
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
            }));                
        } else if (tipologia === '2') {
            this.sdkNotificationService.clearAll();
            let factory = this.getPubblicProgrammaFornitureServiziDirettoFactory(idProgramma, chiaviAccessoOrt.clientId, syscon);      
            return this.requestHelper.begin(factory).pipe( map((result: any) => {                  
                if(result.esito){
                    let message: string = this.translate.instant('PUBBLICAZIONI.PROGRAMMA-PUBBLICATO');
                    this.sdkMessagePanelService.showSuccess(messagesPanel, [
                        {
                            message: message
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                }else{
                    if (result.validationError!=null && result.validationError.length>0){
                        const messages = result.validationError.map(message => ({ message }));
                        this.sdkMessagePanelService.showError(messagesPanel, messages);
                        this.scrollToMessagePanel(messagesPanel);
                    }
                }
                return {
                    reload: true
                }
            }),
            catchError((error: any) => {
                console.log("ERROR:",error);
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: error.error.errorData
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
                return throwError(() => error);
            }));
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private pubblicazioneLavoriFactory(programma: PubblicaProgrammaLavoriEntry, token: string) {
        return () => {
            return this.pubblicazioneService.pubblicaPubblicazioneLavori(programma, token);
        }
    }

    private executePubblicazioneLavori = ({ idProgramma, programma, stazioneAppaltante, messagesPanel, token, syscon }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneLavoriFactory(programma, token);
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneResult) => {
                return {
                    idProgramma,
                    idRicevuto: result.id,
                    stazioneAppaltante,
                    messagesPanel,
                    syscon,
                    programma
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

    private pubblicazioneFornitureServiziFactory(programma: PubblicaProgrammaFornitureServiziEntry, token: string) {
        return () => {
            return this.pubblicazioneService.pubblicaPubblicazioneFornitureServizi(programma, token);
        }
    }

    private executePubblicazioneFornitureServizi = ({ idProgramma, programma, messagesPanel, stazioneAppaltante, token, syscon }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneFornitureServiziFactory(programma, token);
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneResult) => {
                return {
                    idProgramma,
                    idRicevuto: result.id,
                    stazioneAppaltante,
                    messagesPanel,
                    syscon
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

    private allineaPubblicazioneFactory(idProgramma: string, idRicevuto: string, stazioneAppaltante: string, syscon: string, payload:string) {
        return () => {
            let body: ProgrammaPubblicatoForm = {
                id: +idProgramma,
                idRicevuto: +idRicevuto,
                stazioneAppaltante,
                syscon,
                payload
            }
            return this.programmiService.allineaPubblicazione(body);
        }
    }

    private allineaPubblicazione = ({ idProgramma, idRicevuto, stazioneAppaltante, messagesPanel, syscon, programma }: IDictionary<any>) => {
        let factory = this.allineaPubblicazioneFactory(idProgramma, idRicevuto, stazioneAppaltante, syscon, JSON.stringify(programma));
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((_result: any) => {
            return {
                reload: true
            };
        }));
    }

    private getServiceAccessPubblica = ({ idProgramma, programma, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon }: IDictionary<any>) => {
        let factory = this.serviceAccessPubblicaFactory(chiaviAccessoOrt);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                idProgramma,
                programma,
                stazioneAppaltante,
                messagesPanel,
                token,
                syscon
            };
        }));
    }

    private serviceAccessPubblicaFactory(chiaviAccessoOrt: ChiaviAccessoOrt) {
        return () => {
            return this.pubblicazioneService.serviceAccessPubblica(chiaviAccessoOrt);
        }
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get pubblicazioneService(): PubblicazioneService { return this.injectable(PubblicazioneService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkNotificationService(): SdkNotificationService { return this.injectable(SdkNotificationService) }

    private get translate(): TranslateService { return this.injectable(TranslateService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion
}