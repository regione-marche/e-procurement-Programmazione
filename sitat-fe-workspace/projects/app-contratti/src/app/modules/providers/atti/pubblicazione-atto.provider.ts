import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get, isEmpty, isObject } from 'lodash-es';
import { PubblicazioneResult } from 'projects/app-programmi/src/app/modules/models/pubblicazione/pubblicazione.model';
import { Observable, Observer, of, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';
import { AttoEntry } from '../../models/gare/gare.model';

import { PubblicaAttoEntry } from '../../models/pubblicazioni/pubblicazione-atto.model';
import { PubblicaGaraEntry } from '../../models/pubblicazioni/pubblicazione-gara.model';
import { PubblicazioneAttoResult } from '../../models/pubblicazioni/pubblicazioni.model';
import { PubblicazioneAttoService } from '../../services/pubblicazioni/pubblicazione-atto.service';
import { PubblicazioneGaraService } from '../../services/pubblicazioni/pubblicazione-gara.service';


export interface PubblicazioneAttoProviderArgs extends IDictionary<any> {
    codGara?: string,
    numPubb?: string,
    tipoDocumento?: string,
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
    syscon?: string;
    isSmartCig?: boolean;
    stazioneAppaltante?: string;
}

@Injectable({ providedIn: 'root' })
export class PubblicazioneAttoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    private attiMap: Map<string,string> = new Map<string, string>();

    public run(args: any): Observable<IDictionary<any>> {
        if (args.buttonCode === 'check-pubblicazione') {
            return this.checkPubblicazione(args.codGara, args.numPubb, args.tipoDocumento, args.messagesPanel, args.chiaviAccessoOrt, args.syscon, args.stazioneAppaltante,args.isSmartCig);
        } else if (args.buttonCode === 'pubblica-atto') {
            return this.pubblica(args.codGara, args.numPubb, args.tipoDocumento, args.messagesPanel, args.chiaviAccessoOrt, args.syscon, args.stazioneAppaltante,args.isSmartCig);
        } else if (args.buttonCode === 'pubblica-all') {
            return this.pubblicaAll(args.codGara, args.stazioneAppaltante.codice, args.messagesPanel, args.chiaviAccessoOrt, args.syscon, args.listaAtti, args.pubblicatoTutto, args.isSmartCig);
            
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Private

    private pubblica(codGara: string, numPubb: string, tipoDocumento: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string, stazioneAppaltante: string,isSmartCig: boolean ): Observable<IDictionary<any>> {
        return this.getRequestPubblicazioneGara(codGara, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon,undefined,numPubb,    tipoDocumento,isSmartCig )
            .pipe(
                mergeMap(this.getServiceAccessPubblicaGara),
                mergeMap(this.executePubblicazioneGara),
                mergeMap(this.getListaPubblicazioniGara),
                mergeMap(this.allineaPubblicazione),       

                mergeMap(this.getRequestPubblicazioneAtto),
                mergeMap(this.executePubblicazioneAtto),
                mergeMap(this.getListaPubblicazioniAtto),
                mergeMap(this.allineaPubblicazioneAtto),
                tap(() => {
                    let message: string = this.translate.instant('PUBBLICAZIONI.ATTO-PUBBLICATO');
                    this.sdkMessagePanelService.showSuccess(messagesPanel, [
                        {
                            message: message
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                })
            );

    }

    private pubblicaAll(codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string, listaAtti: Array<AttoEntry>,pubblicatoTutto: boolean,isSmartCig: boolean): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(messagesPanel);
        return this.getRequestPubblicazioneGara(codGara, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon, pubblicatoTutto,undefined,undefined,isSmartCig)
            .pipe(
                mergeMap(this.getServiceAccessPubblicaGara),
                mergeMap(this.executePubblicazioneGara),
                mergeMap(this.getListaPubblicazioniGara),
                mergeMap(this.allineaPubblicazione),
                mergeMap(this.pubblicaAtti),
                map(this.managePubblicaAtti)
            );

    }
  

    

   

    private getRequestPubblicazioneGara = (codGara: string, stazioneAppaltante: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string, pubblicatoTutto?: boolean,numPubb?: string, tipoDocumento?: string, isSmartCig?:boolean   ): Observable<IDictionary<any>> => {
        if(isSmartCig){
            let factory = this.getRequestPubblicazioneSmartCigFactory(codGara);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: PubblicaGaraEntry) => {
                return {
                    codGara,
                    gara: result,
                    stazioneAppaltante,
                    messagesPanel,
                    chiaviAccessoOrt,
                    syscon,
                    pubblicatoTutto,
                    numPubb,    
                    tipoDocumento,
                    isSmartCig
                };
            })
        );
        }else {
            let factory = this.getRequestPubblicazioneGaraFactory(codGara);
            return this.requestHelper.begin(factory, messagesPanel).pipe(
                map((result: PubblicaGaraEntry) => {
                    return {
                        codGara,
                        gara: result,
                        stazioneAppaltante,
                        messagesPanel,
                        chiaviAccessoOrt,
                        syscon,
                        pubblicatoTutto,
                        numPubb,    
                        tipoDocumento,
                        isSmartCig
                    };
                })
            );
        }
        
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

    private executePubblicazioneGara = ({ codGara, gara, stazioneAppaltante, messagesPanel, token, syscon, pubblicatoTutto,numPubb,  tipoDocumento,isSmartCig  }: IDictionary<any>): Observable<IDictionary<any>> => {
        if(isSmartCig){
            let factory = this.pubblicazioneSmartCigFactory(gara, token);
            return this.requestHelper.begin(factory).pipe(
                map((result: PubblicazioneResult) => {
                    return {
                        codGara,
                        stazioneAppaltante,
                        idRicevuto: result.id,
                        messagesPanel,
                        syscon,
                        pubblicatoTutto,
                        numPubb,
                        tipoDocumento,
                        token 
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
        } else{
            let factory = this.pubblicazioneGaraFactory(gara, token);
            return this.requestHelper.begin(factory).pipe(
                map((result: PubblicazioneResult) => {
                    return {
                        codGara,
                        stazioneAppaltante,
                        idRicevuto: result.id,
                        messagesPanel,
                        syscon,
                        pubblicatoTutto,
                        numPubb,
                        tipoDocumento,
                        token 
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

    private getListaPubblicazioniGara = ({ codGara, stazioneAppaltante, idRicevuto, messagesPanel, syscon,pubblicatoTutto,numPubb,    tipoDocumento,    token  }: IDictionary<any>): Observable<IDictionary<any>> => {
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
                    pubblicatoTutto,
                    numPubb,    tipoDocumento,    token 
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

    private allineaPubblicazione = ({ codGara, stazioneAppaltante, idRicevuto, tipoInvio, messagesPanel, syscon, pubblicatoTutto,numPubb,    tipoDocumento,    token }: IDictionary<any>) => {
        let factory = this.allineaPubblicazioneFactory(codGara, stazioneAppaltante, idRicevuto, tipoInvio, syscon);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((_result: any) => {
            return {               
                codGara,
                syscon,
                messagesPanel,
                pubblicatoTutto,                
                numPubb,
                tipoDocumento,
                token,
                stazioneAppaltante               
            };
        }));
    }

    private allineaPubblicazioneFactory(codGara: string, stazioneAppaltante: string, idRicevuto: string, tipoInvio: string, syscon: string) {
        return () => {
            return this.pubblicazioneGaraService.allineaPubblicazioneGara(codGara, stazioneAppaltante, idRicevuto, tipoInvio, syscon, 'payload');
        }
    }

    

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private checkPubblicazione(codGara: string, numPubb: string, tipoDocumento: string, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, syscon: string, stazioneAppaltante: string, isSmartCig: boolean): Observable<IDictionary<any>> {

        return this.getServiceAccessPubblica({ codGara, numPubb, tipoDocumento, messagesPanel, chiaviAccessoOrt, syscon, stazioneAppaltante,isSmartCig })
            .pipe(
                mergeMap(this.getRequestPubblicazioneAtto),
                mergeMap(this.executeCheckPubblicazioneAtto),
                mergeMap(this.checkPubblicazioneGaraSmart)
            );
    }

    private checkPubblicazioneGaraSmart  = ({codGara, messagesPanel, chiaviAccessoOrt, syscon,isSmartCig,validate}: IDictionary<any>): Observable<IDictionary<any>> =>{
        if(isSmartCig){
            console.log("Check Pubblica smartCig");
            return this.getRequestPubblicazioneGara(codGara, null, messagesPanel, chiaviAccessoOrt, syscon,validate, undefined, undefined, true)
            .pipe(
                mergeMap(this.getServiceAccessPubblica),
                mergeMap(this.executeCheckPubblicazioneSmartCig),
                map((value: any) => {
                    value.validate = [...value.validate,...validate];
                    return { ...value, sidebar: true };
                })
            );
        } else{
            this.sdkMessagePanelService.clear(messagesPanel);
            return this.getRequestPubblicazioneGara(codGara, null, messagesPanel, chiaviAccessoOrt, syscon)
                .pipe(                    
                    mergeMap(this.getServiceAccessPubblica),
                    mergeMap(this.executeCheckPubblicazioneGara),
                    map((value: any) => {
                        value.validate = [...value.validate,...validate];
                        return { ...value, sidebar: true };
                    })
                );
        }
        
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

    private getRequestPubblicazioneAttoFactory(codGara: string, numPubb: string, tipoDocumento: string) {
        return () => {
            return this.pubblicazioneAttoService.getRequestPubblicazioneAtto(codGara, numPubb, tipoDocumento);
        }
    }

    private getRequestPubblicazioneAtto = ({ codGara, numPubb, tipoDocumento, messagesPanel, token, syscon, stazioneAppaltante,isSmartCig,chiaviAccessoOrt }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.getRequestPubblicazioneAttoFactory(codGara, numPubb, tipoDocumento);
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: PubblicaAttoEntry) => {
                return {
                    codGara,
                    numPubb,
                    tipoDocumento,
                    atto: result,
                    messagesPanel,
                    token,
                    syscon,
                    stazioneAppaltante,
                    isSmartCig,                        
                    chiaviAccessoOrt
                };
            })
        );
    }

    private checkPubblicazioneAttoFactory(atto: PubblicaAttoEntry, token: string) {
        return () => {
            return this.pubblicazioneAttoService.checkPubblicazioneAtto(atto, token);
        }
    }

    private executeCheckPubblicazioneAtto = ({ atto, messagesPanel, token,isSmartCig,codGara,chiaviAccessoOrt,syscon }: IDictionary<any>): Observable<any> => {
        let factory = this.checkPubblicazioneAttoFactory(atto, token);
        return this.requestHelper.begin(factory, messagesPanel)
            .pipe(
                map((result: PubblicazioneAttoResult) => {
                    return {
                        ...result,
                        validate: result.validate,
                        isSmartCig,
                        codGara, 
                        messagesPanel,                         
                        chiaviAccessoOrt, 
                        syscon
                    };
                })
            );
    }

    

    

    private executePubblicazioneAtto = ({ codGara, numPubb, tipoDocumento, atto, messagesPanel, token, syscon }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneAttoFactory(atto, token);
        return this.requestHelper.begin(factory).pipe(
            map((result: PubblicazioneAttoResult) => {
                return {
                    codGara,
                    numPubb,
                    tipoDocumento,
                    idRicevuto: result.idExArt29,
                    idGara: result.idGara,
                    messagesPanel,
                    syscon,
                    atto
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

    private pubblicazioneAttoFactory(atto: PubblicaAttoEntry, token: string) {
        return () => {
            return this.pubblicazioneAttoService.pubblicaAtto(atto, token);
        }
    }

    private getListaPubblicazioniAttoFactory(codGara: string, numPubb: string) {
        return () => {
            return this.pubblicazioneAttoService.getListaPubblicazioniAtto(codGara, numPubb);
        }
    }

    private getListaPubblicazioniAtto = ({ codGara, numPubb, tipoDocumento, idRicevuto, idGara, messagesPanel, syscon, atto }: IDictionary<any>): Observable<IDictionary<any>> => {
        let factory = this.getListaPubblicazioniAttoFactory(codGara, numPubb);
        return this.requestHelper.begin(factory).pipe(
            map((result: Array<string>) => {
                return {
                    codGara,
                    numPubb,
                    tipoDocumento,
                    idRicevuto,
                    idGara,
                    tipoInvio: result == null || result.length == 0 ? '1' : '2', // 1 primo invio, 2 rettifica
                    messagesPanel,
                    syscon,
                    atto
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

    private allineaPubblicazioneAttoFactory(codGara: string, numPubb: string, tipoDocumento: string, idGara: string, idRicevuto: string, tipoInvio: string, syscon: string, payload: string) {
        return () => {
            return this.pubblicazioneAttoService.allineaPubblicazioneAtto(codGara, numPubb, tipoDocumento, idGara, idRicevuto, tipoInvio, syscon, payload);
        }
    }

    private allineaPubblicazioneAtto = ({ codGara, numPubb, tipoDocumento, idGara, idRicevuto, tipoInvio, messagesPanel, syscon, atto }: IDictionary<any>) => {
        let factory = this.allineaPubblicazioneAttoFactory(codGara, numPubb, tipoDocumento, idGara, idRicevuto, tipoInvio, syscon, JSON.stringify(atto));
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((_result: any) => {
            return {
                reload: true
            };
        }));
    }

    private getServiceAccessPubblica = ({ codGara, numPubb, tipoDocumento, messagesPanel, chiaviAccessoOrt, syscon, stazioneAppaltante,isSmartCig,gara }: IDictionary<any>) => {
        let factory = this.serviceAccessPubblicaFactory(chiaviAccessoOrt);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                codGara,
                numPubb,
                tipoDocumento,
                messagesPanel,
                token,
                syscon,
                stazioneAppaltante,
                isSmartCig,
                chiaviAccessoOrt,
                gara
            };
        }));
    }

    private getServiceAccessPubblicaGara = ({ codGara, gara, stazioneAppaltante, messagesPanel, chiaviAccessoOrt, syscon, pubblicatoTutto,numPubb, tipoDocumento,isSmartCig }: IDictionary<any>) => {
        let factory = this.serviceAccessPubblicaFactory(chiaviAccessoOrt);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                codGara,
                gara,
                stazioneAppaltante,
                messagesPanel,
                token,
                syscon,
                pubblicatoTutto,
                numPubb,
                tipoDocumento,   
                isSmartCig
            };
        }));
    }

    private serviceAccessPubblicaFactory(chiaviAccessoOrt: ChiaviAccessoOrt) {
        return () => {
            return this.pubblicazioneAttoService.serviceAccessPubblica(chiaviAccessoOrt);
        }
    }






    private pubblicazioneAttiFactory(codGara: string, syscon:string, pubblicatoTutto: boolean) {
        return () => {
            return this.pubblicazioneAttoService.pubblicaAtti(codGara,syscon,pubblicatoTutto);
        }
    }

    private pubblicaAtti = ({codGara, messagesPanel,syscon,pubblicatoTutto}): Observable<IDictionary<any>> => {
        let factory = this.pubblicazioneAttiFactory(codGara,syscon,pubblicatoTutto);
        
        return this.requestHelper.begin(factory).pipe(
            map((result: any) => {                
                return {
                    result,
                    messagesPanel
                }
            }),
            catchError((error: any) => {         
                let httpError: any = error.error as any;     
               let errorData: string = 'UNEXPECTED-ERROR';
               if (isObject(httpError) && !isEmpty(get(httpError, 'errorData'))) {
                   errorData = get(httpError, 'errorData');
               }
              
               let message: string = `ERRORS.${errorData}`;

                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: message
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
                return throwError(() => error);
            })
            
        );
    }   

    private managePubblicaAtti = ({result,messagesPanel}) => {
        let nonvalido = false;
        let valido = false;
        
        let message: string = '';
        let listNonvalido: string = '';
       
        for (let obj of result) {
            
            if(obj.validato == true){
                valido = true;
            } else{
                listNonvalido = 'Nome: '+obj.nome+' - '+obj.label+' \n' + listNonvalido;               
                nonvalido = true;
            }
        }
       
        if(!valido){           
            message = 'PUBBLICAZIONI.MESSAGE-KO';
        } else if(valido && nonvalido){                
                message = 'PUBBLICAZIONI.MESSAGE-ERROR';
        } else if(!nonvalido){             
                message = 'PUBBLICAZIONI.MESSAGE-OK';
        }
       
        this.sdkMessagePanelService.showInfo(messagesPanel, [
            {
                message: message,
                messageParams: {
                    value: listNonvalido
                }
            }
        ]);
        this.scrollToMessagePanel(messagesPanel);
        return {reload: true};
    }

    

  

    // #endregion

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translate(): TranslateService { return this.injectable(TranslateService) }

    private get pubblicazioneAttoService(): PubblicazioneAttoService { return this.injectable(PubblicazioneAttoService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }

    // #endregion
}