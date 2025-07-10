import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from "@maggioli/sdk-commons";
import { isFunction } from "lodash-es";
import { map, Observable, Observer, of } from "rxjs";
import { WRicParamDTO } from "../model/lista-params.model";
import { SdkDettaglioReportStoreService } from "../sdk-gestione-report.exports";
import { GestioneReportService } from "../services/gestione-report.service";

export interface SdkListaReportProviderArgs extends IDictionary<any> {
    action: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    idRicerca?: number;
    codice?: string;
    setUpdateState?: Function;
    syscon?: number;
    form?: Array<WRicParamDTO>;
    idProfilo?: string;
    fromDefinizione?: boolean;
    defSql?: string;
    menuField?: Array<string>;
    fromPredefiniti?: boolean;
}

@Injectable({
    providedIn: "root"
})
export class SdkListaReportProvider extends SdkBaseService implements SdkProvider{

    //#region Config

    constructor(inj: Injector){
        super(inj);
    }

    public run(args: SdkListaReportProviderArgs): Observable<IDictionary<any>>{

        this.logger.debug('SdkListaReportProviderArgs >>>', args);

        //ACTION
        if(args.action === 'DETAIL'){

            return this.detailSingleReport(args);
        } 
        else if (args.action === 'DEFINIZIONE'){

            return this.definizioneReport(args);
        }
        else if (args.action === 'PARAMETRI'){

            return this.parametriReport(args);
        }
        else if (args.action === 'UFF-INT'){

            this.ufficiIntestatariReport(args);
        }
        else if (args.action === 'DETAIL-PARAM'){

            return this.detailSingleParamReport(args);
        }
        else if (args.action === 'DELETE-PARAM-REPORT'){

            return this.deleteSingleParamReport(args);
        }
        else if (args.action === 'DELETE-SINGLE-REPORT'){

            return this.deleteSingleReport(args);
        }
        //BUTTONCODE
        else if(args.buttonCode === 'create-new-report'){

            this.goToCreateNewReport(args);
        }
        else if(args.buttonCode === 'back-to-lista-report'){

            this.backToListaReport(args);
        } 
        else if(args.buttonCode === 'back'){

            this.backToHomePage();
        } 
        else if(args.buttonCode === 'add-parametro-report'){

            this.goToAddParamReport(args);
        }
        else if(args.buttonCode === 'back-to-param-list'){

            this.parametriReport(args);
        }
        else if(args.buttonCode === 'back-def-or-param-list'){

            if(args.fromDefinizione === true){
                this.backToDefinizione(args);
            }
            else if (args.fromPredefiniti === true) {
                this.backListaReportPredefiniti(args);
            } 
            else {
                this.parametriReport(args);
            }
        }
        else if(args.buttonCode === 'modifica-dati-generali-report'){

            this.modificaDatiGeneraliReport(args);
        }
        else if(args.buttonCode === 'back-to-dati-generali'){

            this.backToDatiGenerali(args);
        }
        else if (args.buttonCode === 'back-to-definizione'){

            this.backToDefinizione(args);
        }
        else if (args.buttonCode === 'go-to-modifica-definizione'){

            this.goToModificaDefinizioneReport(args);
        }
        else if (args.buttonCode === 'back-to-lista-params'){

            this.backToListaParams(args);
        }
        else if (args.buttonCode === 'go-to-modifica-param-dettaglio'){

            this.goToModificaParamDettaglio(args);
        }
        else if (args.buttonCode === 'back-to-dettaglio-parametro'){

            this.detailSingleParamReport(args);
        }
        else if(args.buttonCode === 'back-to-profili-report'){

            this.backToProfiliReport(args, args.syscon);
        }
        else if (args.buttonCode === 'modifica-profili-report'){

            this.goToModificaProfiliReport(args, args.syscon);
        }
        else if (args.buttonCode === 'esegui-report'){

            //Se non ho parametri da inserire posso passare direttamente all'esecuzione del report.
            if(args.numParams === 0){

                this.goToResultsReport(args);
            }
            else{

                this.goToInserisciParams(args);
            }
            
        }
        else if (args.buttonCode === 'modifica-uffici-intestatari'){

            this.goToModificaUfficiIntestatariReport(args);
        }
        else if (args.buttonCode === 'back-to-uffici-intestatari-report'){

            this.backToUfficiIntestatariReport(args, args.syscon);
        }
        else if(args.buttonCode === 'esegui-report-params'){

            if(args.fromPredefiniti === true) {
                this.goToRisultatiPredefinitiReport(args);
            }
            else{
                this.goToResultsReport(args);
            }
        }

        return of(args);
    }

    //#endregion

    //#region Getter/Setters

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    //#endregion

    //#region Private

    private detailSingleReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            syscon: args.syscon,
            idProfilo: args.idProfilo
        };
        this.routerService.navigateToPage('dati-generali-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private definizioneReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>>{

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        };
        this.routerService.navigateToPage('definizione-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private parametriReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>>{

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }
        this.routerService.navigateToPage('parametri-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private ufficiIntestatariReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>>{

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }
        this.routerService.navigateToPage('uffici-intestatari-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private goToCreateNewReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();

        this.routerService.navigateToPage('crea-nuovo-report-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private backToListaReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>>{

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();

        this.routerService.navigateToPage('lista-report-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToHomePage(): Observable<IDictionary<any>>{

        this.routerService.navigateToPage('home-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToAddParamReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('nuovo-parametro-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private modificaDatiGeneraliReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('modifica-dati-generali-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private backToDatiGenerali(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('dati-generali-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private backToDefinizione(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('definizione-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private backListaReportPredefiniti(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false);
        }

        this.routerService.navigateToPage('lista-report-predefiniti-page');
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private goToModificaDefinizioneReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        sessionStorage.setItem("defSqlDefinizione", args.defSql);

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('modifica-definizione-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private backToListaParams(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('parametri-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private goToModificaParamDettaglio(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false)
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        this.sdkDettaglioReportStoreService.codice = args.codice;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            codice: args.codice
        }

        this.routerService.navigateToPage('modifica-dettaglio-parametro-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private detailSingleParamReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        this.sdkDettaglioReportStoreService.codice = args.codice;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            codice: args.codice,
            menuField: args.menuField
        };

        this.routerService.navigateToPage('dettaglio-parametro-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToProfiliReport(args: SdkListaReportProviderArgs, syscon: number): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            syscon: syscon
        }

        this.routerService.navigateToPage('profili-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
    }

    private goToModificaProfiliReport(args: SdkListaReportProviderArgs, syscon: number): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            syscon: syscon
        };

        this.routerService.navigateToPage('modifica-profili-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToInserisciParams(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        this.sdkDettaglioReportStoreService.nome = args.nomeReport;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            codice: args.codice,
            fromDefinizione: args.fromDefinizione,
            fromPredefiniti: args.fromPredefiniti,
            menuField: args.menuField
        };

        this.routerService.navigateToPage('inserisci-parametri-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToRisultatiPredefinitiReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        this.sdkDettaglioReportStoreService.nome = args.nomeReport;

        //Evito di mostrare nell'URL all'utente i dati dell'intero form passato a parametro. 
        //Utilizzo quindi il sessionStorage solo in questo caso. Arrivato nella pagina di destinazione,
        //i dati del form vengono recuperati in un oggetto apposito e subito eliminati dal sessionStorage.
        sessionStorage.setItem('formParams', JSON.stringify(args.form));

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            fromPredefiniti: args.fromPredefiniti
        };

        this.routerService.navigateToPage('risultato-esecuzione-report-predefiniti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToResultsReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;
        this.sdkDettaglioReportStoreService.nome = args.nomeReport;

        //Evito di mostrare nell'URL all'utente i dati dell'intero form passato a parametro. 
        //Utilizzo quindi il sessionStorage solo in questo caso. Arrivato nella pagina di destinazione,
        //i dati del form vengono recuperati in un oggetto apposito e subito eliminati dal sessionStorage.
        sessionStorage.setItem('formParams', JSON.stringify(args.form));

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            syscon: args.syscon,
            idProfilo: args.idProfilo,
            fromDefinizione: args.fromDefinizione
        };

        this.routerService.navigateToPage('risultato-esecuzione-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private goToModificaUfficiIntestatariReport(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        };

        this.routerService.navigateToPage('modifica-uffici-intestatari-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToUfficiIntestatariReport(args: SdkListaReportProviderArgs, syscon: number): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca,
            syscon: syscon
        };

        this.routerService.navigateToPage('uffici-intestatari-report-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private deleteSingleParamReport(args: SdkListaReportProviderArgs): Observable<any> {
        let setUpdateState: Function = args.setUpdateState;
        let codice = args.codice;
        let idRicerca = args.idRicerca;
        let syscon = args.syscon;
        let idProfilo = args.idProfilo;

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        return this.gestioneReportService.deleteParamRowReport(idRicerca, codice, syscon, idProfilo).pipe(
            map((result)=> {return {reload:true}})
        );
    }

    private deleteSingleReport(args: SdkListaReportProviderArgs): Observable<any> {
        let setUpdateState: Function = args.setUpdateState;
        let idRicerca = args.idRicerca;
        let syscon = args.syscon;
        let idProfilo = args.idProfilo;

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }
        return this.gestioneReportService.deleteRowReport(idRicerca, syscon, idProfilo).pipe(
            map((result)=> {return {reload:true}})
        );
    }

    //#endregion
}