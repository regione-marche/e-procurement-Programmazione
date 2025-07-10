import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkNotificationService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get, size } from 'lodash-es';
import { Observable, Observer, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';

import {
    PubblicazioneFaseEntry,
    ResponsePubblicaFase,
    TipoInvio,
} from '../../models/pubblicazioni/pubblicazione-fase.model';
import { PubblicaGaraEntry } from '../../models/pubblicazioni/pubblicazione-gara.model';
import { PubblicazioneResult } from '../../models/pubblicazioni/pubblicazioni.model';
import { PubblicazioneFaseService } from '../../services/pubblicazioni/pubblicazione-fase.service';
import { PubblicazioneGaraService } from '../../services/pubblicazioni/pubblicazione-gara.service';
import { pubblicaFaseResponse } from '../../models/fasi/fasi.model';

import { TEnvs } from '../../../app.constants';
import { environment } from 'projects/app-contratti/src/environments/environment';


export interface PubblicazioneFaseLottoProviderArgs extends IDictionary<any> {
    codGara: string;
    codLotto: string;
    codFase: string;
    numeroProgressivo: string;
    cfStazioneAppaltante: string;
    stazioneAppaltante:string
    tipoInvio: TipoInvio;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
    syscon?: string;
    autore?: string;
    pcp?: boolean;
    codProfilo?: string;
}

@Injectable({ providedIn: 'root' })
export class PubblicazioneFaseLottoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    // #region Public

    public run(args: any): Observable<IDictionary<any>> {
        if (args.buttonCode === 'check-pubblicazione') {
            return this.verifica(this.mapToEntry(args), args.messagesPanel, args.chiaviAccessoOrt);
        } else if (args.buttonCode === 'pubblica-fase' && !args.pcp) {
            return this.pubblica(this.mapToEntry(args), args.messagesPanel, args.chiaviAccessoOrt,args.codGara, args.syscon, args.stazioneAppaltante );
        } else if (args.buttonCode === 'pubblica-fase'  && args.pcp) {            
            return this.pubblicaPcp(this.mapToEntry(args), args.messagesPanel, args.codProfilo, args.cfImpersonato, args.loaImpersonato, args.idpImpersonato, args.stazioneAppaltante);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #endregion

    // #region Protected

    protected verifica(pubblicazioneFase: PubblicazioneFaseEntry, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt): Observable<ResponsePubblicaFase> {
        this.sdkMessagePanelService.clear(messagesPanel);
        
        if(pubblicazioneFase.pcp){
            return this.eseguiVerificaFase({ pubblicazioneFase, messagesPanel }).pipe(
                map(result => {
                    return {
                        ...result,
                        action: 'open-sidebar'
                    }
                })
            );
        } else{
            return this.getServiceAccessPubblica({ pubblicazioneFase, messagesPanel, chiaviAccessoOrt })
            .pipe(
                mergeMap(this.verificaGara),
                mergeMap(this.eseguiVerificaFase),
                map(result => {
                    return {
                        ...result,
                        action: 'open-sidebar'
                    }
                })
            );
        }
        
    }

    protected pubblicaPcp(pubblicazioneFase: PubblicazioneFaseEntry, messagesPanel: HTMLElement,codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string): Observable<IDictionary<any>> {
        this.sdkNotificationService.clearAll();
        let factory = this.pubblicaFasePcpFactory(pubblicazioneFase,codProfilo,cfImpersonato, loaImpersonato, idpImpersonato, codein);                
        return this.requestHelper.begin(factory).pipe(
            map((result: pubblicaFaseResponse) => {
                if(result.anacErrors != null){
                    let anacErrorsArray = [];
                    result.anacErrors.forEach(element => {
                        anacErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(messagesPanel, anacErrorsArray);
                }
                if(result.internalErrors != null){
                    let internalErrorsArray = [];
                    result.internalErrors.forEach(element => {
                        internalErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(messagesPanel, internalErrorsArray);
                }
                if(result.validationErrors != null){
                    
                }
    
                if(result.pubblicato){
                    let message: string = this.translate.instant('PUBBLICAZIONI.FASE-PUBBLICATA');
                        this.sdkMessagePanelService.showSuccess(messagesPanel, [
                            {
                                message: message
                            }
                        ]);
                        this.scrollToMessagePanel(messagesPanel);
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
            })
        );
    }
    
    protected pubblica(pubblicazioneFase: PubblicazioneFaseEntry, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt,codGara,syscon,stazioneAppaltante): Observable<IDictionary<any>> {
        this.sdkNotificationService.clearAll();
        return this.getServiceAccessPubblica({ pubblicazioneFase, messagesPanel, chiaviAccessoOrt, codGara, syscon, stazioneAppaltante })
        .pipe(
            mergeMap(this.pubblicaGara),
            mergeMap(this.eseguiPubblicaFase),
            mergeMap(this.allineaPubblicazione),
            tap(() => {
                
                let message: string = this.translate.instant('PUBBLICAZIONI.FASE-PUBBLICATA');
                this.sdkMessagePanelService.showSuccess(messagesPanel, [
                    {
                        message: message
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
                
            })
        );
        
        
    }

    // #endregion

    // #region Private

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    //
    // VERIFICA FASE
    //

    private verificaFaseFactory(pubblicazioneFase: PubblicazioneFaseEntry, token: string) {
        return () => {
            return this.pubblicazioneFaseService.verificaPubblicazioneFase(pubblicazioneFase, token);
        }
    }

    private eseguiVerificaFase = ({ pubblicazioneFase, messagesPanel, token, validateGara }: IDictionary<any>): Observable<ResponsePubblicaFase> => {
        let factory = null;
        if(pubblicazioneFase.pcp){
            factory = this.verificaFasePcpFactory(pubblicazioneFase);           
        } else{
            factory = this.verificaFaseFactory(pubblicazioneFase, token);            
        }

        return this.requestHelper.begin(factory, messagesPanel)
                .pipe(
                    map((result: ResponsePubblicaFase) => {
                        return {
                            ...result,
                            validate: validateGara != null ? [...validateGara, ...result.validate] : result.validate
                        };
                    })
                );
        
    }

    //
    // PUBBLICAZIONE FASE
    //

    private pubblicaFaseFactory(pubblicazioneFase: PubblicazioneFaseEntry, token: string) {
        return () => {
            return this.pubblicazioneFaseService.pubblicaFase(pubblicazioneFase, token);
        }
    }

    private pubblicaFasePcpFactory(pubblicazioneFase: PubblicazioneFaseEntry, codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string) {
        return () => {
            return this.pubblicazioneFaseService.pubblicazioneFasePcp(pubblicazioneFase.codGara,pubblicazioneFase.codFase,pubblicazioneFase.codLotto,pubblicazioneFase.numeroProgressivo,codProfilo,cfImpersonato, loaImpersonato, idpImpersonato, codein );
        }
    }

    private verificaFasePcpFactory(pubblicazioneFase: PubblicazioneFaseEntry) {
        return () => {
            return this.pubblicazioneFaseService.verificaFasePcp(pubblicazioneFase.codGara,pubblicazioneFase.codFase,pubblicazioneFase.codLotto,pubblicazioneFase.numeroProgressivo);
        }
    }

    private eseguiPubblicaFase = ({ pubblicazioneFase, messagesPanel, token }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicaFaseFactory(pubblicazioneFase, token);                
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneResult) => {
                return {
                    result,
                    pubblicazioneFase,
                    messagesPanel
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

    private allineaPubblicazioneFactory(pubbFase: PubblicazioneFaseEntry) {
        return () => {
            return this.pubblicazioneFaseService.allineaPubblicazioneFase(pubbFase);
        }
    }

    private allineaPubblicazione = ({ result, pubblicazioneFase, messagesPanel, executeNext }: IDictionary<any>) => {
        
        const factory = this.allineaPubblicazioneFactory(pubblicazioneFase);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((_result: any) => {
            return {
                executeNext,
                reload: true
            };
        }));
    }

    private getServiceAccessPubblica = ({ pubblicazioneFase, messagesPanel, chiaviAccessoOrt,codGara,syscon,stazioneAppaltante }: IDictionary<any>) => {
        let factory = this.serviceAccessPubblicaFactory(chiaviAccessoOrt);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                pubblicazioneFase,
                messagesPanel,
                token,
                codGara,
                syscon,
                stazioneAppaltante
            };
        }));
    }

    private serviceAccessPubblicaFactory(chiaviAccessoOrt: ChiaviAccessoOrt) {
        return () => {
            return this.pubblicazioneFaseService.serviceAccessPubblica(chiaviAccessoOrt);
        }
    }

    private mapToEntry(providerData: PubblicazioneFaseLottoProviderArgs): PubblicazioneFaseEntry {
        return {
            codGara: providerData.codGara,
            codLotto: providerData.codLotto,
            codFase: providerData.codFase,
            numeroProgressivo: providerData.numeroProgressivo,
            cfStazioneAppaltante: providerData.cfStazioneAppaltante,
            tipoInvio: providerData.tipoInvio,
            syscon: providerData.syscon,
            autore: providerData.autore,
            pcp: providerData.pcp
        };
    }

    private pubblicaGara = ({codGara, stazioneAppaltante, messagesPanel, token, syscon,pubblicazioneFase}: IDictionary<any>): Observable<IDictionary<any>> => {
        this.sdkMessagePanelService.clear(messagesPanel);
        return this.getRequestPubblicazioneGara(codGara, stazioneAppaltante, messagesPanel, token, syscon,pubblicazioneFase)
            .pipe(                
                mergeMap(this.executePubblicazioneGara),
                mergeMap(this.getListaPubblicazioniGara),
                mergeMap(this.allineaPubblicazioneGara)      
            );

    }

    private verificaGara = ({ pubblicazioneFase, messagesPanel, token }: IDictionary<any>): Observable<IDictionary<any>> => {
        this.sdkMessagePanelService.clear(messagesPanel);
        return this.getRequestPubblicazioneGara(pubblicazioneFase.codGara, null, messagesPanel, token, pubblicazioneFase.syscon,null)
            .pipe(
                mergeMap(this.executeCheckPubblicazioneGara),
                map((result: any) => {
                    return {
                        pubblicazioneFase,
                        messagesPanel,
                        token,
                        validateGara: result.validate
                    };
                })
            );
    }

    private getRequestPubblicazioneGaraFactory(codGara: string) {
        return () => {
            return this.pubblicazioneGaraService.getRequestPubblicazioneGara(codGara);
        }
    }

    private getRequestPubblicazioneGara = (codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, token: string, syscon: string,pubblicazioneFase): Observable<IDictionary<any>> => {
        let factory = this.getRequestPubblicazioneGaraFactory(codGara);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: any) => {
                return {
                    codGara,
                    gara: result,
                    stazioneAppaltante,
                    messagesPanel,
                    token,
                    syscon,
                    pubblicazioneFase
                };
            })
        );
    }

   
    private pubblicazioneGaraFactory(gara: PubblicaGaraEntry, token: string) {
        return () => {
            return this.pubblicazioneGaraService.pubblicaGara(gara, token);
        }
    } 
    private executePubblicazioneGara = ({ codGara, gara, stazioneAppaltante, messagesPanel, token, syscon,pubblicazioneFase }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneGaraFactory(gara, token);
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneResult) => {
                return {
                    codGara,
                    stazioneAppaltante,
                    idRicevuto: result.id,
                    messagesPanel,
                    syscon,
                    pubblicazioneFase,
                    token,
                    gara
                }
            }),
            catchError((error: any) => {
                if(error.error.errorData === 'errore validazione dati'){
                    this.sdkMessagePanelService.showError(messagesPanel, [
                        {
                            message: 'ERRORS.PUBLISH-FAILURE'
                        }
                    ]);
                    
                }else{
                    this.sdkMessagePanelService.showError(messagesPanel, [
                        {
                            message: 'ERRORS.PUBLISH-FAILURE-GARA'
                        }
                    ]);
                   
                }
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

    private getListaPubblicazioniGara = ({ codGara, stazioneAppaltante, idRicevuto, messagesPanel, syscon, pubblicazioneFase, token, gara }: IDictionary<any>): Observable<IDictionary<any>> => {
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
                    pubblicazioneFase,
                    token,
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

    private allineaPubblicazioneGaraFactory(codGara: string, stazioneAppaltante: string, idRicevuto: string, tipoInvio: string, syscon: string, gara) {
        return () => {
            return this.pubblicazioneGaraService.allineaPubblicazioneGara(codGara, stazioneAppaltante, idRicevuto, tipoInvio, syscon,JSON.stringify(gara));
        }
    }

    private allineaPubblicazioneGara = ({ codGara, stazioneAppaltante, idRicevuto, tipoInvio, messagesPanel, syscon,pubblicazioneFase,token, gara }: IDictionary<any>) => {
        let factory = this.allineaPubblicazioneGaraFactory(codGara, pubblicazioneFase.cfStazioneAppaltante , idRicevuto, tipoInvio, syscon, gara);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((_result: any) => {
            return {
                pubblicazioneFase,
                messagesPanel,
                token,
            };
        }));
    }

    private executeCheckPubblicazioneGara = ({ gara, messagesPanel, token }: IDictionary<any>): Observable<PubblicazioneResult> => {
        let factory = this.checkPubblicazioneGaraFactory(gara, token);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private checkPubblicazioneGaraFactory(gara: PubblicaGaraEntry, token: string) {
        return () => {
            return this.pubblicazioneGaraService.checkPubblicazioneGara(gara, token);
        }
    }

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkNotificationService(): SdkNotificationService { return this.injectable(SdkNotificationService) }

    private get translate(): TranslateService { return this.injectable(TranslateService) }

    private get pubblicazioneFaseService(): PubblicazioneFaseService { return this.injectable(PubblicazioneFaseService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }

    // #endregion
}