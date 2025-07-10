import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, ResponseResult, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBaseService,
    SdkDateHelper,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService
} from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkComboBoxItem, SdkDialogConfig, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty, isFunction, isObject, isUndefined, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Observer, of, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';
import { DettaglioGaraStoreService } from '../../layout/components/business/gare/dettaglio-gara-store.service';
import { DettaglioIdGaraStoreService } from '../../layout/components/business/gare/dettaglio-id-gara-store.service';
import { DettaglioLottoStoreService } from '../../layout/components/business/lotti/dettaglio-lotto-store.service';
import { DettaglioSmartCigStoreService } from '../../layout/components/business/smartcig/dettaglio-smartcig-store.service';
import { GaraEntry, ListaGareParams, LottoAccorpabileEntry, MigrazioneGaraForm, ResponseExportCsv, RicercaGareForm } from '../../models/gare/gare.model';
import { AllineaGaraForm, CheckMigrazioneForm, CheckMigrazioneGaraEntry } from '../../models/gare/importa-gara.model';
import { RicercaSchedePcp } from '../../models/schede-trasmesse-pcp/schede-trasmesse-pcp.model';
import { GareService } from '../../services/gare/gare.service';
import { ImportaGaraService } from '../../services/gare/importa-gara.service';
import { SmartCigService } from '../../services/smartcig/smartcig.service';

export interface ListaGareProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    stazioneAppaltante?: StazioneAppaltanteInfo;
    searchForm?: RicercaGareForm;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    codGara?: string;
    gara?: GaraEntry;
    idAvGara?: string;
    setUpdateState?: Function;
    smartCig?: boolean;
    syscon?: number;
    dialogConfigSubj?: BehaviorSubject<SdkDialogConfig>;
    lottoAccorpabile?: LottoAccorpabileEntry;
    cigLotto?: string;
    faseEsecuzione?: string;
    progressivoScheda?: number;
    codLotto?: number;
}

@Injectable({ providedIn: 'root' })
export class ListaGareProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('ListaGareProviderArgs >>>', args);
        if (args.action === 'DELETE') {
            return this.deleteGara(args, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if (args.action === 'DETAIL') {
            return this.detailGara(args);
        } else if (args.action === 'DETAIL-LOTTO-SCHEDA') {
            return this.detailLottoSchedeTrasmesse(args);
        } else if (args.buttonCode === 'back') {
            return this.back(args);
        } else if (args.buttonCode === 'back-ricerca-schede-pcp') {
            return this.backRicercaSchedePcp(args);
        } else if (args.buttonCode === 'pulisciFiltri') {
            return this.clean();
        } else if (args.buttonCode === 'pulisciFiltriSchedeTrasmessePcp') {
            return this.cleanFiltersRicercaSchedeTrasmessePcp(args);
        } else if (args.buttonCode === 'search-button'){
            return this.searchSchedeTrasmessePcp(args);
        } else if (args.buttonCode === 'back-to-lista-gare') {
            return this.backLista(args);
        } else if (args.buttonCode === 'go-to-update-gara') {
            return this.goUpdate(args.codGara);
        } else if (args.buttonCode === 'back-to-dettaglio-gara') {
            return this.detailGara(args);
        } else if (args.buttonCode === 'go-to-update-pubblicita-gara') {
            return this.goUpdatePubblicita(args);
        } else if (args.buttonCode === 'back-to-dettaglio-pubblicita-gara') {
            return this.backDettaglioPubblicitaGara(args);
        } else if (args.buttonCode === 'riallinea-simog') {
            return this.riallineaSimog(args);
        } else if (args.buttonCode === 'riallinea-anac') {
            return this.riallineaAnac(args);
        } else if (args.buttonCode === 'migrazione-gara') {
            return this.migrazioneGara(args);
        } else if (args.buttonCode === 'archiviazione-gara') {
            return this.archiviazioneGara(args);
        } else if (args.buttonCode === 'annulla-archiviazione-gara') {
            return this.annullaArchiviazioneGara(args);
        } else if (args.buttonCode === 'accorpamento-lotti') {
            return this.accorpamentoLotti(args);
        } else if (args.buttonCode === 'exportCsv') {
            this.exportCsv(args);
        } else if(args.buttonCode === 'elimina-appalto-pcp'){
            return this.deleteAppaltoPcp(args, args.messagesPanel).pipe(map((data: any) => {
                return { delete: true };
            }));
        } else if (args.action === 'DETAIL-LOTTO') {
            return this.detailLotto(args);
        } else if (args.buttonCode === 'presa-carico') {
            return this.presaCarico(args, args.messagesPanel, args.cfImpersonato, args.loaImpersonato, args.idpImpersonato, args.codein).pipe(map((data: any) => {
                if(data.anacErrors != null){
                    let anacErrorsArray = [];
                    data.anacErrors.forEach(element => {
                        anacErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(args.messagesPanel, anacErrorsArray);
                }
                if(data.internalErrors != null){
                    let internalErrorsArray = [];
                    data.internalErrors.forEach(element => {
                        internalErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(args.messagesPanel, internalErrorsArray);
                }
                if(data.validationErrors != null){
                    
                }
                
                if(data.errorData != null && data.presaCaricoCompleted === true){
                    this.sdkMessagePanelService.showError(args.messagesPanel, [
                        {
                            message: this.translateService.instant('ERRROS.PRESA-CARICO-COMPLETE') + this.translateService.instant(data.errorData)
                        }
                    ]);
                }
                if(data.inserito == true){
                    return { reload: true };
                }
                
            }));
        } else if (args.action === 'LISTA-LOTTI') {
            return this.listaLotti(args);
        } else if (args.data.code === 'conferma-delega') {
            return this.confermaDelega(args.data, args.data.messagesPanel, args.data.cfImpersonato, args.data.loaImpersonato, args.data.idpImpersonato, args.data.codein, args.data.inserisciDelegato).pipe(map((data: any) => {
                if(data.anacErrors != null){
                    let anacErrorsArray = [];
                    data.anacErrors.forEach(element => {
                        anacErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(args.data.messagesPanel, anacErrorsArray);
                }
                if(data.internalErrors != null){
                    let internalErrorsArray = [];
                    data.internalErrors.forEach(element => {
                        internalErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(args.data.messagesPanel, internalErrorsArray);
                }
                if(data.validationErrors != null){
                    
                }
                if(data.inserito == true){
                    return { reload: true };
                }
                
            }));
        }
        return of(args);
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    private deleteAppaltoPcp(args: ListaGareProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;
        factory = this.getDeleteAppaltoPcpFactory(args.codGara);        
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteAppaltoPcpFactory(codGara: string) {
        return () => {
            return this.gareService.deleteAppaltoPcp(codGara);
        }
    }

    private deleteGara(args: ListaGareProviderArgs, messagesPanel: HTMLElement): Observable<any> {
        let factory: Function;
        if (args.smartCig === true) {
            factory = this.getDeleteSmartCigFactory(args.codGara);
        } else {
            factory = this.getDeleteGaraFactory(args.codGara);
        }
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteGaraFactory(codGara: string) {
        return () => {
            return this.gareService.deleteGara(codGara);
        }
    }

    private confermaDelega(data: any, messagesPanel: HTMLElement,cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string, inserisciDelegato: boolean): Observable<any> {
        let factory: Function;
        let request: any = this.populateRequest(data.formBuilderConfig);
        factory = this.confermaDelegaFactory(data.codGara, request.delegato?.codice,cfImpersonato, loaImpersonato, idpImpersonato, codein, inserisciDelegato);
        
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private presaCarico(data: any, messagesPanel: HTMLElement,cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string): Observable<any> {
        let factory: Function;        
        factory = this.presaCaricoFactory(data.codGara,cfImpersonato, loaImpersonato, idpImpersonato, codein);
        
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private confermaDelegaFactory(codGara: string, codtec: string,cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string, inserisciDelegato: boolean) {
        return () => {
            return this.gareService.confermaDelegato(codGara, codtec,cfImpersonato, loaImpersonato, idpImpersonato, codein, inserisciDelegato);
        }
    }

    private presaCaricoFactory(codGara: string, cfImpersonato: string, loaImpersonato: string, idpImpersonato: string, codein: string) {
        return () => {
            return this.gareService.presaCarico(codGara,cfImpersonato, loaImpersonato, idpImpersonato, codein);
        }
    }

    private getDeleteSmartCigFactory(codGara: string) {
        return () => {
            return this.smartCigService.deleteSmartCig(codGara);
        }
    }

    private detailGara(args: ListaGareProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };

        this.dettaglioSmartCigStoreService.clear();
        this.dettaglioGaraStoreService.clear();

        if (args.smartCig === true) {
            this.dettaglioSmartCigStoreService.codGara = args.codGara;
            this.routerService.navigateToPage('dettaglio-smartcig-page', params);
        } else {
            this.dettaglioGaraStoreService.codGara = args.codGara;
            this.routerService.navigateToPage('dettaglio-gara-page', params);
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private detailLotto(args: ListaGareProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codlott,
            from: 'L'
        };

        let gara = {
            identificativoGara:args.identificativoGara
        }

        
        this.dettaglioGaraStoreService.clear();
        this.dettaglioIdGaraStoreService.gara = gara;
        this.dettaglioGaraStoreService.codGara = args.codGara;


        this.dettaglioLottoStore.clear();
        this.dettaglioLottoStore.codGara = args.codGara;
        this.dettaglioLottoStore.codLotto = args.codlott;      
       


        if(args.smartCig){
            this.routerService.navigateToPage('dettaglio-smartcig-page', params);
        } else{
            this.routerService.navigateToPage('dettaglio-lotto-page', params);
        }
       
      

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private listaLotti(args: ListaGareProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };

        let gara = {
            identificativoGara:args.identificativoGara
        }

        this.dettaglioGaraStoreService.clear();
        this.dettaglioIdGaraStoreService.gara = gara;
        this.dettaglioGaraStoreService.codGara = args.codGara;
        this.routerService.navigateToPage('lista-lotti-page', params);
      

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

 

    private exportListaGare(searchForm: RicercaGareForm, stazioneAppaltante: string, syscon: string) {
        return () => {
            let params: ListaGareParams = {
                ...searchForm,
                stazioneAppaltante,
                syscon: +syscon
            }
            return this.gareService.exportListaGare(params);
        }
    }

    private exportCsv(args: ListaGareProviderArgs) {
        let factory: Function;
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let stazioneAppaltante = '';
        if(args.searchForm.stazioneAppaltante != null){
            stazioneAppaltante = args.searchForm.stazioneAppaltante
        } else{
            stazioneAppaltante = args.stazioneAppaltante.codice
        }
        
        factory = this.exportListaGare(args.searchForm,stazioneAppaltante,args.syscon.toString());
        
        this.requestHelper.begin(factory, args.messagesPanel).subscribe((result :ResponseExportCsv)=>{   
            if(result.rowNumber > 0){
                if(result.rowNumber > 1000){
                    this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                        {
                            message: 'LISTA-GARE.MAX-ROW-CSV'
                        }
                    ])
                    this.scrollToMessagePanel(args.messagesPanel);
                }

                let csvContent = result.data;
                let blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
                let url = URL.createObjectURL(blob);
                
                let csvFile = document.createElement('a');
                csvFile.href = url;
                if(args.stazioneAppaltante.codice != null && args.stazioneAppaltante.codice === '*'){
                    csvFile.setAttribute('download', 'Lista_gare.csv');
                } else{
                    csvFile.setAttribute('download', 'Lista_gare_'+args.stazioneAppaltante.codice+'.csv');
                    
                }
                document.body.appendChild(csvFile);
                csvFile.click();
                document.body.removeChild(csvFile);
               
            } else{
                this.sdkMessagePanelService.showInfo(args.messagesPanel, [
                    {
                        message: 'LISTA-GARE.NO-CSV'
                    }
                ])
                this.scrollToMessagePanel(args.messagesPanel);
            }
            
        });
               
    }

    private back(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState = args.setUpdateState;
        const stateForm: RicercaGareForm = args.stateForm;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        if (stateForm != null && !isEmpty(stateForm)) {
            this.routerService.navigateToPage('ricerca-avanzata-gare-page');
        } else {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_GARE_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        }
        return of(undefined);
    }

    private clean(): Observable<IDictionary<any>> {
        this.routerService.navigateToPage('ricerca-avanzata-gare-page');
        return of(undefined);
    }

    private cleanFiltersRicercaSchedeTrasmessePcp(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_DISPATCHER, undefined));
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next({
                cleanSearch: true
            });
            ob.complete();
        });
        //this.routerService.navigateToPage('ricerca-schede-trasmesse-pcp-page');
        //return of(undefined);
    }

    private backRicercaSchedePcp(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState = args.setUpdateState;

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.routerService.navigateToPage('ricerca-schede-trasmesse-pcp-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private searchSchedeTrasmessePcp(args: ListaGareProviderArgs): Observable<IDictionary<any>> {

        let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        let form: RicercaSchedePcp = this.populateRequest(formBuilderConfig);

        const params: IDictionary<any> = {
            ...form
        };

        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_DISPATCHER, params));
        this.routerService.navigateToPage('lista-schede-trasmesse-pcp-page');
    
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }    
    
    private detailLottoSchedeTrasmesse(args: ListaGareProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.dettaglioLottoStore.clear();
        this.dettaglioLottoStore.codGara = args.codGara;
        this.dettaglioLottoStore.codLotto = toString(args.codLotto);
        this.dettaglioLottoStore.fromLS = 'LS';

        let params: IDictionary<any> = {
            codGara: args.codGara,
            codLotto: args.codLotto,
            fromLS: 'LS'
        };
        
        this.routerService.navigateToPage('dettaglio-lotto-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backLista(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        this.routerService.navigateToPage('lista-gare-page', { load: 'true' });
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdate(codGara: string): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara
        };
        this.routerService.navigateToPage('modifica-gara-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goUpdatePubblicita(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('modifica-pubblicita-gara-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backDettaglioPubblicitaGara(args: ListaGareProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        let params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('pubblicita-gara-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private riallineaSimog(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        const body: AllineaGaraForm = {
            codiceSA: args.stazioneAppaltante.codice,
            idavGara: args.gara.identificativoGara
        };
        const factory = this.riallineaSimogFactory(body);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(
            map((result: ResponseResult<any>) => {
                this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                    {
                        message: 'DETTAGLIO-GARA.RIALLINEA-SUCCESS'
                    }
                ])
                this.scrollToMessagePanel(args.messagesPanel);
                return {
                    reload: result.esito === true
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(args.messagesPanel);
                return throwError(() => error);
            })
        );
    }

    private riallineaSimogFactory(body: AllineaGaraForm) {
        return () => {
            return this.importaGaraService.allineaGaraDaSimog(body);
        }
    }



    private riallineaAnac(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        let cfImpersonato = args.cfImpersonato;
        let loaImpersonato = args.loaImpersonato;
        let idpImpersonato = args.idpImpersonato;
        let codein = args.codein;
        let codGara = args.codGara;
        let cancellaDatiEse = args.cancellaDatiEse
        const body: any = {
            cfImpersonato,
            loaImpersonato,
            idpImpersonato,
            codein,
            codGara,
            cancellaDatiEse
        };
        const factory = this.riallineaAnacFactory(body);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(
            map((result: any) => {

                if(result.allineato){
                    this.sdkMessagePanelService.showSuccess(args.messagesPanel, [
                        {
                            message: 'DETTAGLIO-GARA.RIALLINEA-SUCCESS-ANAC'
                        }
                    ])
                } else{
                    this.sdkMessagePanelService.showWarning(args.messagesPanel, [
                        {
                            message: 'DETTAGLIO-GARA.RIALLINEA-NOTHING-ANAC'
                        }
                    ])
                }
                if(result.anacErrors != null){
                    let anacErrorsArray = [];
                    result.anacErrors.forEach(element => {
                        anacErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(args.messagesPanel, anacErrorsArray);
                }
                if(result.internalErrors != null){
                    let internalErrorsArray = [];
                    result.internalErrors.forEach(element => {
                        internalErrorsArray.push({message : element});
                    });
                    this.sdkMessagePanelService.showError(args.messagesPanel, internalErrorsArray);
                }
                if(result.validationErrors != null){
                    
                }

                this.scrollToMessagePanel(args.messagesPanel);
                return {
                    response: result,
                    reload: result.esito === true
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(args.messagesPanel);
                return throwError(() => error);
            })
        );
    }

    private riallineaAnacFactory(body: AllineaGaraForm) {
        return () => {
            return this.importaGaraService.allineaGaraDaAnac(body);
        }
    }

    private checkMigrazioneFactory(body: CheckMigrazioneForm) {
        return () => {
            return this.importaGaraService.checkMigrazione(body);
        }
    }

    private migrazioneGara(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        const body: CheckMigrazioneForm = {
            cfStazioneAppaltante: args.stazioneAppaltante.codFiscale,
            idavGara: args.gara.identificativoGara,
            syscon: args.syscon
        };
        const factory = this.checkMigrazioneFactory(body);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(
            map((result: ResponseResult<CheckMigrazioneGaraEntry>) => {
                if (result.data != null) {
                    this.showMigraDialogDialog(args, result.data);
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(args.messagesPanel);
                return throwError(() => error);
            })
        );
    }

    private showMigraDialogDialog(args: ListaGareProviderArgs, data: CheckMigrazioneGaraEntry): void {
        const saBaseEntry = data.stazioneAppaltante;
        const dialogConfig: SdkDialogConfig = {
            header: this.translateService.instant('DIALOG.MIGRA-TITLE'),
            message: this.translateService.instant('DIALOG.MIGRA-TEXT', {
                stazioneAppaltante: `${saBaseEntry.codice} - ${saBaseEntry.nome}`
            }),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        args.dialogConfigSubj.next(dialogConfig);
        const func = this.migraDialogConfirm(args, data);
        dialogConfig.open.next(func);
    }

    private migraDialogConfirm(args: ListaGareProviderArgs, data: CheckMigrazioneGaraEntry): any {
        return () => {
            const form: MigrazioneGaraForm = {
                stazioneAppaltante: data.stazioneAppaltante.codice,
                codiceFiscaleRupGara: data.codiceFiscaleRupGara,
                codGara: +args.codGara
            };
            const factory = this.migraGaraFactory(form);
            this.requestHelper.begin(factory, args.messagesPanel).subscribe(
                (result: ResponseResult<any>) => {
                    this.resetDialog(args);
                    let setUpdateState: Function = args.setUpdateState;
                    if (isFunction(setUpdateState)) {
                        setUpdateState(false);
                    }
                    this.routerService.navigateToPage('lista-gare-page', { load: 'true', message: Constants.MIGRAZIONE_COMPLETATA });
                },
                (error: any) => {
                    this.scrollToMessagePanel(args.messagesPanel);
                }
            );
        }
    }

    private migraGaraFactory(form: MigrazioneGaraForm) {
        return () => {
            return this.gareService.executeMigraGara(form);
        }
    }

    private archiviaGaraFactory(codGara: string) {
        return () => {
            return this.gareService.archiviGara(codGara);
        }
    }

    private annullaArchiviaGaraFactory(codGara: string) {
        return () => {
            return this.gareService.annullaArchiviGara(codGara);
        }
    }

    private resetDialog(args: ListaGareProviderArgs): void {
        const dialogConfig: SdkDialogConfig = {
            header: this.translateService.instant('DIALOG.RIALLINEA-TITLE'),
            message: this.translateService.instant('DIALOG.RIALLINEA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        args.dialogConfigSubj.next(dialogConfig);
    }

    private archiviazioneGara(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);

        const factory = this.archiviaGaraFactory(args.codGara);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(
            map((result: ResponseResult<any>) => {
                return {
                    reload: result.esito === true
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(args.messagesPanel);
                return throwError(() => error);
            })
        );

    }

    private annullaArchiviazioneGara(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        const factory = this.annullaArchiviaGaraFactory(args.codGara);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(
            map((result: ResponseResult<any>) => {
                return {
                    reload: result.esito === true
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(args.messagesPanel);
                return throwError(() => error);
            })
        );

    }

    private accorpamentoLotti(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        this.sdkMessagePanelService.clear(args.messagesPanel);
        if (args.lottoAccorpabile != null && args.lottoAccorpabile.hasMultiLotto === true) {
            const dialogConfig: SdkDialogConfig = {
                header: this.translateService.instant('DIALOG.EXISTS-ACCORPAMENTO-LOTTI-TITLE'),
                message: this.translateService.instant('DIALOG.EXISTS-ACCORPAMENTO-LOTTI-TEXT'),
                acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
                rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
                open: new Subject()
            };
            args.dialogConfigSubj.next(dialogConfig);
            const func = this.accorpamentoLottiConfirm(args);
            dialogConfig.open.next(func);
        } else {
            return this.navigateToAccorpamentoLotti(args);
        }
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private accorpamentoLottiConfirm(args: ListaGareProviderArgs) {
        return () => {
            const factory = this.pulisciAccorpamentiMultilottoFactory(args.codGara);
            this.requestHelper.begin(factory, args.messagesPanel).subscribe((result: ResponseResult<any>) => {
                if (result.esito === true) {
                    this.navigateToAccorpamentoLotti(args);
                }
            });
        }
    }

    private navigateToAccorpamentoLotti(args: ListaGareProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            codGara: args.codGara
        };
        this.routerService.navigateToPage('accorpamento-lotti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private pulisciAccorpamentiMultilottoFactory(codGara: string) {
        return () => {
            return this.gareService.pulisciAccorpamentiMultilotto(codGara);
        }
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration, codGara?: string): any {
        let request: any = {};
        each(formBuilderConfig.fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION') {
                request = this.elaborateSection(field, request);
            } else if (field.type === 'FORM-GROUP') {
                request = this.elaborateGroup(field, request);
            } else {
                request = this.elaborateOne(field, request);
            }
        });
        return request;
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field)) {
            if (field.visible !== false) {
                if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item) && !isEmpty(field.mappingOutput)) {
                        const key = item.key;
                        //if (!isEmpty(key)) {
                        set(request, field.mappingOutput, item.key);
                        //}
                    }
                } else if (field.type === 'AUTOCOMPLETE') {
                    if (field.data != null) {
                        let item: SdkAutocompleteItem = get(field, 'data');
                        set(request, field.mappingOutput, item._key);
                    }
                } else if (field.type === 'MULTIPLE-AUTOCOMPLETE') {
                    if (field.data != null) {
                        let items: Array<SdkAutocompleteItem> = get(field, 'data');
                        let keys: Array<String> = [];
                        each(items, (item: SdkAutocompleteItem) => {
                            if (!isEmpty(item._key)) {
                                keys.push(item._key)
                            }
                        });
                        set(request, field.mappingOutput, keys);
                        if(field.code === 'uffInt'){
                            set(request, 'uffInt', items);
                        } else if(field.code === 'rup'){
                            set(request, 'rup', items);
                        }
                    }
                } else if (field.type === 'DATEPICKER' && field.data) {
                    let finalDate: string = this.dateHelper.format(<Date>field.data, 'yyyy/MM/dd');
                    set(request, field.mappingOutput, finalDate);
                } else if (!isUndefined(field.data)) {
                    if (!isEmpty(field.mappingOutput)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            } else {
                set(request, field.mappingOutput, undefined);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: any): any {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request);
            } else {
                request = this.elaborateOne(one, request);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: any): any {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    request = this.elaborateSection(one, request);
                } else if (one.type === 'FORM-GROUP') {
                    request = this.elaborateGroup(one, request);
                } else {
                    request = this.elaborateOne(one, request);
                }
            });
            field.fieldGroups[index] = group;
        });
        return request;
    }

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioGaraStoreService(): DettaglioGaraStoreService { return this.injectable(DettaglioGaraStoreService) }

    private get dettaglioLottoStore(): DettaglioLottoStoreService { return this.injectable(DettaglioLottoStoreService) }

    private get dettaglioSmartCigStoreService(): DettaglioSmartCigStoreService { return this.injectable(DettaglioSmartCigStoreService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get importaGaraService(): ImportaGaraService { return this.injectable(ImportaGaraService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get dettaglioIdGaraStoreService(): DettaglioIdGaraStoreService { return this.injectable(DettaglioIdGaraStoreService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) };

    // #endregion

}
