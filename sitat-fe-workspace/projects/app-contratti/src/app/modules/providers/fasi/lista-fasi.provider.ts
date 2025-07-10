import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService, UserProfile } from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { get, isEmpty, isFunction, isObject, toString } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { DettaglioFaseStoreService } from '../../layout/components/business/fasi/dettaglio-fase-store.service';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';
import { PubblicazioneFaseEntry, TipoInvio } from '../../models/pubblicazioni/pubblicazione-fase.model';
import { PubblicazioneFaseService } from '../../services/pubblicazioni/pubblicazione-fase.service';

export interface ListaFasiProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DELETE-LOGICA' | 'DETAIL' | 'PUBLISH' | 'DELETE-LOGICA-PCP';
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    codLotto?: string;
    setUpdateState?: Function;
    fasePage?: string;
    codiceFase?: string;
    numeroProgressivo?: string;
    fase?: FaseEntry;
    faseService?: BaseFaseService;
    cfStazioneAppaltante?: string;
    chiaviAccessoOrt?: ChiaviAccessoOrt;
    idAvGara?: string;
    cig?: string;
    userProfile?: UserProfile;
    motivazione?: string;
    // parametri passati da lista-fasi-lotto-section.widget.ts
    current?: boolean;
    nuovaFaseAggiudicazioneCode?: number;
    nuovaFaseAggiudicazioneSlug?: string;
    codein?: string;
    // parametro passato da fase-abstract-section.widgets.ts
    riaggiudicazione?: boolean;
    cigFiglio?: boolean;
    fromLS?: string;
}

@Injectable({ providedIn: 'root' })
export class ListaFasiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaFasiProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteFase(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DELETE-LOGICA') {
            return this.deleteLogicaFase(args);
        } else if (args.action === 'DELETE-LOGICA-PCP') {
            return this.deleteLogicaScheda(args).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'PUBLISH') {
            return this.pubblicaFase(args);
        } else if (args.action === 'DETAIL') {
            return this.dettaglioFase(args);
        } else if (args.action === 'CHECK') {
            return this.verificaScheda(args);
        } else if (args.buttonCode === 'back-to-lista-fasi-lotto') {
            return this.listaFasiLotto(args);
        } else if (args.buttonCode === 'back-to-dettaglio-smartcig') {
            return this.backToSmartCig(args);
        } else if (args.buttonCode === 'back-to-lista-schede-trasmesse') {
            return this.backToListaSchedeTrasmesse(args);
        } else if (args.buttonCode === 'back-to-lista-fasi-lotto-schede-trasmesse') {
            return this.backListaFasiLottoSchedeTrasmesse(args);
        } else if (args.buttonCode === 'go-to-fasi-visibili') {
            return this.fasiVisibili(args);
        } else if (args.buttonCode === 'go-to-nuova-fase') {
            return this.nuovaFase(args);
        } else if (args.buttonCode === 'riaggiudicazione') {
            return this.riaggiudica(args);
        }
        return of(args);
    }

    private deleteFase(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {
        if (isObject(args.fase) && isObject(args.faseService)) {
            let factory = this.deleteFactory(args.faseService, args.fase);
            return this.requestHelper.begin(factory, args.messagesPanel);
        }
        return undefined;
    }

    private deleteLogicaFase(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        if (args.motivazione == null || args.motivazione.trim() == '') {
            this.sdkMessagePanelService.showError(args.messagesPanel, [
                {
                    message: 'VALIDATORS.MOTIVAZIONE-CANCELLAZIONE-LOGICA-OBBLIGATORIA'
                }
            ]);
            this.scrollToMessagePanel(args.messagesPanel);
        } else {
            this.sdkMessagePanelService.clear(args.messagesPanel);
            if (args.fase != null) {

                return this.getServiceAccessPubblica(args)
                    .pipe(
                        mergeMap(this.executeDeleteLogicaFase),
                        mergeMap(this.allineaDeleteLogica)
                    );
            }
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteLogicaScheda(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {
        let codProfilo = args.codProfilo;
        let cfImpersonato = args.cfImpersonato;
        let loaImpersonato = args.loaImpersonato;
        let idpImpersonato = args.idpImpersonato;
        let codGara: string = args.codGara;
        let codFase: string = args.codFase;
        let codLotto: string = args.codLotto;
        let numeroProgressivo: string = args.numeroProgressivo;
        let motivazione = args.motivazione;
        let codein: string = args.codein;
        if (args.motivazione == null || args.motivazione.trim() == '') {
            this.sdkMessagePanelService.showError(args.messagesPanel, [
                {
                    message: 'VALIDATORS.MOTIVAZIONE-CANCELLAZIONE-LOGICA-OBBLIGATORIA'
                }
            ]);
            this.scrollToMessagePanel(args.messagesPanel);
        } else {
            this.sdkMessagePanelService.clear(args.messagesPanel);

            if (isObject(args.fase)) {
                let factory = this.deleteSchedaPcpFactory(codGara, codFase, codLotto, numeroProgressivo, codProfilo, cfImpersonato, loaImpersonato, idpImpersonato, motivazione, codein);
                return this.requestHelper.begin(factory, args.messagesPanel);
            }
            return undefined;


        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }



    private deleteSchedaPcpFactory(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string, codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, motivazione: string, codein: string) {
        return () => {
            return this.pubblicazioneFaseService.cancellaSchedaPcp(codGara, codFase, codLotto, numeroProgressivo, codProfilo, cfImpersonato, loaImpersonato, idpImpersonato, motivazione, codein);
        }
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private pubblicaFase(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        const params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            codiceFase: args.codiceFase,
            numeroProgressivo: args.numeroProgressivo,
            fromLS: args.fromLS
        };
        this.routerService.navigateToPage('pubblica-fase-lotto-page', params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }


    private backToSmartCig(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        const params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('dettaglio-smartcig-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToListaSchedeTrasmesse(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        const params: IDictionary<any> = {};
        this.routerService.navigateToPage('lista-schede-trasmesse-pcp-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backListaFasiLottoSchedeTrasmesse(args:ListaFasiProviderArgs): Observable<IDictionary<any>> {
        const params: IDictionary<any> = {
            fromLS: 'LS',
            codGara: args.codGara,
            codLotto: args.codLotto,
            num: args.numeroProgressivo,
            codiceFase: args.codiceFase
        };
        this.dettaglioFaseStoreService.clearFromLS();
        this.routerService.navigateToPage('lista-fasi-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private dettaglioFase(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        if (!isEmpty(args.codiceFase) && !isEmpty(args.fasePage)) {

            let setUpdateState: Function = args.setUpdateState;
            if (isFunction(setUpdateState)) {
                setUpdateState(false);
            }

            this.dettaglioFaseStoreService.clear();
            this.dettaglioFaseStoreService.codGara = args.codGara;
            this.dettaglioFaseStoreService.codLotto = args.codLotto;
            this.dettaglioFaseStoreService.codiceFase = args.codiceFase;
            this.dettaglioFaseStoreService.numeroProgressivo = args.numeroProgressivo;
            this.dettaglioFaseStoreService.current = args.current;
            this.dettaglioFaseStoreService.riaggiudicazione = args.riaggiudicazione;
            this.dettaglioFaseStoreService.cigFiglio = args.cigFiglio;
            this.dettaglioFaseStoreService.fromLS = args.fromLS;

            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase,
                numeroProgressivo: args.numeroProgressivo,
                fromLS: args.fromLS
            };
            this.routerService.navigateToPage(args.fasePage, params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private verificaScheda(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        let factory = this.verificaStatoSchedaFactory(args.codGara, args.codFase, args.codLotto, args.numeroProgressivo, args.codProfilo, args.cfImpersonato, args.loaImpersonato, args.idpImpersonato, args.codein);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(map((result: any) => {
            if (result.esito == true) {
                return {
                    reload: true
                };
            }
        }));
    }

    private fasiVisibili(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto
        };

        //  if (args.riaggiudicazione === true) {
        //      this.routerService.navigateToPage('lista-fasi-lotto-page', params);
        //  } else {
        this.routerService.navigateToPage('nuova-fase-page', params);
        //  }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaFasiLotto(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto
        };
        this.routerService.navigateToPage('lista-fasi-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private nuovaFase(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {
        if (!isEmpty(args.codiceFase) && !isEmpty(args.fasePage)) {
            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: args.codiceFase
            };
            this.routerService.navigateToPage(args.fasePage, params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteFactory(faseService: BaseFaseService, fase: FaseEntry) {
        return () => {
            return faseService.deleteFase(fase);
        }
    }

    private deleteLogicaFactory(token: string, cig: string, codFase: number, idAvGara: string, num: number, stazioneAppaltante: string) {
        return () => {
            return this.pubblicazioneFaseService.deleteLogicaFase(token, cig, codFase, idAvGara, num, stazioneAppaltante);
        }
    }

    private verificaStatoSchedaFactory(codGara: string, codFase: string, codLotto: string, numeroProgressivo: string, codProfilo: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string) {
        return () => {
            return this.pubblicazioneFaseService.verificaStatoScheda(codGara, codFase, codLotto, numeroProgressivo, codProfilo, cfImpersonato, loaImpersonato, idpImpersonato, codein);
        }
    }

    private executeDeleteLogicaFase = ({ args, token }: IDictionary<any>) => {
        let factory = this.deleteLogicaFactory(token, args.cig, args.fase.fase, args.idAvGara, args.fase.progressivo, args.cfStazioneAppaltante);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(map((result: any) => {
            return args;
        }));
    }

    private getServiceAccessPubblica = (args: ListaFasiProviderArgs) => {
        let factory = this.serviceAccessPubblicaFactory(args.chiaviAccessoOrt);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(map((result: any) => {
            let token: string = get(result, 'token');
            return {
                args,
                token
            };
        }));
    }

    private serviceAccessPubblicaFactory(chiaviAccessoOrt: ChiaviAccessoOrt) {
        return () => {
            return this.pubblicazioneFaseService.serviceAccessPubblica(chiaviAccessoOrt);
        }
    }

    private allineaDeleteLogica = (args: ListaFasiProviderArgs) => {
        let syscon: string = args.userProfile.syscon;

        const pubblicazioneFase: PubblicazioneFaseEntry = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            cfStazioneAppaltante: args.cfStazioneAppaltante,
            codFase: toString(args.fase.fase),
            numeroProgressivo: toString(args.fase.progressivo),
            tipoInvio: TipoInvio.ALLINEAMENTO_CANCELLAZIONE_LOGICA,
            syscon,
            motivazione: args.motivazione
        }
        const factory = this.allineaPubblicazioneFactory(pubblicazioneFase);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(map((_result: any) => {
            return {
                reload: true
            };
        }));
    }

    private allineaPubblicazioneFactory(pubbFase: PubblicazioneFaseEntry) {
        return () => {
            return this.pubblicazioneFaseService.allineaPubblicazioneFase(pubbFase);
        }
    }

    private riaggiudica(args: ListaFasiProviderArgs): Observable<IDictionary<any>> {
        const current: boolean = args.current;
        const nuovaFaseAggiudicazioneCode: number = args.nuovaFaseAggiudicazioneCode;
        const nuovaFaseAggiudicazioneSlug: string = args.nuovaFaseAggiudicazioneSlug;
        if (current === true && nuovaFaseAggiudicazioneCode != null && nuovaFaseAggiudicazioneSlug != null) {
            let params: IDictionary<any> = {
                codGara: args.codGara,
                codLotto: args.codLotto,
                codiceFase: nuovaFaseAggiudicazioneCode,
                riaggiudicazione: 1
            };
            this.routerService.navigateToPage(nuovaFaseAggiudicazioneSlug, params);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    private get pubblicazioneFaseService(): PubblicazioneFaseService { return this.injectable(PubblicazioneFaseService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

}
