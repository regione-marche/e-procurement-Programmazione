import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBase64Helper, SdkBaseService, SdkProvider, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkDocumentUtils, SdkFormBuilderConfiguration, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get } from 'lodash-es';
import { Observable, of, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { AvvisoEntry, DocumentoAvviso } from '../../models/avviso/avviso.model';
import {
    AllineamentoPubblicazioneModel,
    PubblicazioneAvvisoModel,
} from '../../models/pubblicazioni/pubblicazione-avviso-model';
import { AvvisiService } from '../../services/avvisi/avvisi.service';
import { PubblicazioneGaraService } from '../../services/pubblicazioni/pubblicazione-gara.service';

export interface DettaglioAvvisoProviderArgs extends IDictionary<any> {
    code: 'back' | 'publish' | 'verify' | 'update' | 'download';
    avviso?: AvvisoEntry;
    document?: DocumentoAvviso;
    syscon?: String;
    messagesPanel?: HTMLElement;
    defaultFormBuilderConfig?: SdkFormBuilderConfiguration;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
    codiceFiscaleStazioneAppaltante?: string;
}


@Injectable({ providedIn: 'root' })
export class DettaglioAvvisoProvider extends SdkBaseService implements SdkProvider {

    public run(args: DettaglioAvvisoProviderArgs): Observable<IDictionary<any>> {
        if (args.code === 'back') {
            let params: IDictionary<string> = {
                'load': 'true'
            }
            this.routerService.navigateToPage('lista-avvisi-page', params);
            return of(undefined);
        }
        if (args.code === 'update') {
            let params: IDictionary<string> = {
                'idAvviso': args.avviso.numeroAvviso.toString(),
                stazioneAppaltante: args.avviso.stazioneAppaltante
            }
            this.routerService.navigateToPage('modifica-avviso-page', params);
            return of(undefined);
        }
        if (args.code === 'publish') {

            return this.pubblicaAvviso(args.avviso, args.syscon, args.messagesPanel, args.chiaviAccessoOrt, args.avviso.stazioneAppaltante);
        }
        if (args.code === 'verify') {
            return this.getServiceAccessPubblica(args.avviso, args.syscon, args.messagesPanel, args.chiaviAccessoOrt, args.avviso.stazioneAppaltante)
                .pipe(
                    mergeMap(this.verificaPubblicazione)
                );
        }
        if (args.code === 'download') {
            this.downloadFile(args.document)
            return of(undefined);
        }

        if (args.buttonCode === 'elimina-flussi-avviso') {
            return this.eliminaFlussiAvviso(args, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));            
        }

        if (args.buttonCode === 'redirect-link-pubb') {
            this.linkPubbblicazione(args)           
        }

    }

    constructor(inj: Injector) {
        super(inj);
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get avvisiService(): AvvisiService { return this.injectable(AvvisiService) }

    private get translate(): TranslateService { return this.injectable(TranslateService) }

    private get sdkBase64Helper(): SdkBase64Helper { return this.injectable(SdkBase64Helper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }
    
    // #endregion

    // #region Private

    private linkPubbblicazione(args: DettaglioAvvisoProviderArgs) {
        let idGenerato: string = args.idGenerato;    
        let messagesPanel: HTMLElement = args.messagesPanel;
        let factory = this.getlinkPubbblicazioneFactory(idGenerato,args.stazioneAppaltante,'AVVISO');
        this.requestHelper.begin(factory, messagesPanel).subscribe((result: any)=>{          
            window.open(result.data, "_blank");
        });
    }

    private getlinkPubbblicazioneFactory(idGenerato: string,stazioneAppaltante: string,entita: string) {
        return () => {
            return this.avvisiService.getLinkPubbblicazione(idGenerato,stazioneAppaltante,entita);
        }
    }

    private eliminaFlussiAvviso(args: DettaglioAvvisoProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;       
        factory = this.deleteFlussiAvvisoFactory(args.idAvviso);       
        return this.requestHelper.begin(factory, messagesPanel);
    }
    
    private deleteFlussiAvvisoFactory(idAvviso: string) {
        return () => {
            return this.avvisiService.deleteFlussoAvviso(idAvviso);
        }
    }   

    private pubblicaAvviso(avviso: AvvisoEntry, syscon: String, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, codiceStazioneAppaltante: string): Observable<IDictionary<any>> {

        return this.getServiceAccessPubblica(avviso, syscon, messagesPanel, chiaviAccessoOrt, codiceStazioneAppaltante)
            .pipe(
                mergeMap(this.getRequestPubblicazioneAvviso),
                mergeMap(this.executePubblicazioneAvviso),
                tap(() => {
                    let message: string = this.translate.instant('PUBBLICA-AVVISO.AVVISO-PUBBLICATO');
                    this.sdkMessagePanelService.showSuccess(messagesPanel, [
                        {
                            message: message
                        }
                    ]);
                    this.scrollToMessagePanel(messagesPanel);
                })
            );
    }

    private getRequestPubblicazioneAvviso = ({ avviso, syscon, messagesPanel, token, codiceStazioneAppaltante }: IDictionary<any>): Observable<AllineamentoPubblicazioneModel> => {
        const requestFactory = this.getRequestPubblicazioneAvvisoFactory(avviso.numeroAvviso, codiceStazioneAppaltante);
        return this.requestHelper.begin(requestFactory, messagesPanel)
            .pipe(
                map((result: PubblicazioneAvvisoModel) => {
                    result = {
                        ...result,
                        clientId: environment.CLIENT_ID,
                        syscon
                    };
                    return result;
                }),
                mergeMap((pubbModel: PubblicazioneAvvisoModel) => {
                    let func = this.getPubblicaAvvisoFactory(pubbModel, token);
                    return this.requestHelper.begin(func, messagesPanel)
                        .pipe(
                            map((response: any) => {
                                return {
                                    tipologia: avviso.tipoAvviso,
                                    numeroAvviso: avviso.numeroAvviso,
                                    idRicevuto: response.id,
                                    syscon: syscon,
                                    messagesPanel,
                                    stazioneAppaltante: avviso.stazioneAppaltante,
                                    payload: JSON.stringify(pubbModel)
                                }
                            }),
                            catchError((error: any) => {
                                this.sdkMessagePanelService.showError(messagesPanel, [
                                    {
                                        message: 'ERRORS.PUBLISH-FAILURE'
                                    }
                                ]);
                                return throwError(() => error);
                            })
                        );
                })
            );
    }

    private executePubblicazioneAvviso = (allineamentoPubblicazioneModel: AllineamentoPubblicazioneModel): Observable<any> => {
        let messagesPanel: HTMLElement = get(allineamentoPubblicazioneModel, 'messagesPanel');
        allineamentoPubblicazioneModel = {
            stazioneAppaltante: allineamentoPubblicazioneModel.stazioneAppaltante,
            tipologia: allineamentoPubblicazioneModel.tipologia,
            numeroAvviso: allineamentoPubblicazioneModel.numeroAvviso,
            idRicevuto: allineamentoPubblicazioneModel.idRicevuto,
            syscon: allineamentoPubblicazioneModel.syscon,
            payload: allineamentoPubblicazioneModel.payload
        };
        let idGeneratoFunc = this.getIdRicevutoFactory(allineamentoPubblicazioneModel);
        return this.requestHelper.begin(idGeneratoFunc, messagesPanel)
            .pipe(
                map((result: any) => {
                    return {
                        reload: true
                    }
                })
            );
    }

    private verificaPubblicazione = ({ avviso, syscon, messagesPanel, token, codiceStazioneAppaltante }: IDictionary<any>): Observable<IDictionary<any>> => {
        const requestFactory = this.getRequestPubblicazioneAvvisoFactory(avviso.numeroAvviso, codiceStazioneAppaltante);
        return this.requestHelper.begin(requestFactory, messagesPanel)
            .pipe(
                map((result: PubblicazioneAvvisoModel) => {
                    result = {
                        ...result,
                        clientId: environment.CLIENT_ID,
                        syscon
                    };
                    return result;
                }),
                mergeMap((pubbModel: PubblicazioneAvvisoModel) => {
                    let func = this.getCheckPubblicazioneFactory(pubbModel, token);
                    return this.requestHelper.begin(func, messagesPanel);
                })
            );
    }

    private getPubblicaAvvisoFactory(pubAvvisoModel: PubblicazioneAvvisoModel, token: string) {
        return () => {
            return this.avvisiService.pubblicaAvviso(pubAvvisoModel, token);
        }
    }

    private getCheckPubblicazioneFactory(pubAvvisoModel: PubblicazioneAvvisoModel, token: string) {
        return () => {
            return this.avvisiService.checkPubblicazioneAvviso(pubAvvisoModel, token);
        }
    }

    private getIdRicevutoFactory(allineamentoPubblicazioneModel: AllineamentoPubblicazioneModel) {
        return () => {
            return this.avvisiService.setIdRicevuto(allineamentoPubblicazioneModel);
        }
    }

    private getRequestPubblicazioneAvvisoFactory(idAvviso: number, codiceStazioneAppaltante: string) {
        return () => {
            return this.avvisiService.getRequestPubblicazioneAvviso(idAvviso, codiceStazioneAppaltante);
        }
    }

    private downloadFile(jsondocument: DocumentoAvviso): void {

        if (jsondocument.url) {
            let link = document.createElement('a');
            document.body.appendChild(link);
            link.target = '_blank';
            link.href = jsondocument.url;
            link.click();
        } else {

            let arrBuffer = this.sdkBase64Helper.base64ToArrayBuffer(jsondocument.binary);
            let newBlob = new Blob([arrBuffer], { type: SdkDocumentUtils.getMimeTypeFromExtension(jsondocument.tipoFile) });
            let data = window.URL.createObjectURL(newBlob);
            let link = document.createElement('a');
            document.body.appendChild(link);
            link.href = data;
            link.download = jsondocument.titolo;
            link.click();
            window.URL.revokeObjectURL(data);
            link.remove();
        }
    };

    private getServiceAccessPubblica = (avviso: AvvisoEntry, syscon: String, messagesPanel: HTMLElement, chiaviAccessoOrt: ChiaviAccessoOrt, codiceStazioneAppaltante: string) => {
        let factory = this.serviceAccessPubblicaFactory(chiaviAccessoOrt);
        return this.requestHelper.begin(factory, messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                avviso,
                syscon,
                messagesPanel,
                token,
                codiceStazioneAppaltante
            };
        }));
    }

    private serviceAccessPubblicaFactory(chiaviAccessoOrt: ChiaviAccessoOrt) {
        return () => {
            return this.avvisiService.serviceAccessPubblica(chiaviAccessoOrt);
        }
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }
    // #endregion
}