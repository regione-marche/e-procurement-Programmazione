import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider, SdkRouterService } from "@maggioli/sdk-commons";
import { Observable, Observer, of } from "rxjs";
import { WRicParamDTO } from "../model/lista-params.model";
import { SdkListaReportProviderArgs } from "./sdk-lista-report.provider";
import { isFunction } from "lodash-es";
import { SdkDettaglioReportStoreService } from "../sdk-gestione-report.exports";

export interface SdkListaReportPredefinitiProviderArgs extends IDictionary<any> {
    action: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    idRicerca?: number;
    codice?: string;
    setUpdateState?: Function;
    syscon?: number;
    form?: Array<WRicParamDTO>;
    idProfilo?: string;
    numParams?: number;
    menuField?: Array<string>;
    fromDefinizione?: boolean;
    fromPredefiniti?: boolean;
}

@Injectable({
    providedIn: "root"
})
export class SdkListaReportPredefinitiProvider extends SdkBaseService implements SdkProvider{

    //#region Config

    constructor(inj: Injector){
        super(inj);
    }

    public run(args: SdkListaReportPredefinitiProviderArgs): Observable<IDictionary<any>>{

        this.logger.debug('SdkListaReportPredefinitiProviderArgs >>>', args);

        if(args.action === 'ESEGUI-REPORT'){

            //Se non ho parametri da inserire posso passare direttamente all'esecuzione del report.
            if(args.numParams === 0){

                this.goToResultsReport(args);
            }
            else{

                this.goToInserisciParams(args);
            }
        }
        //buttonCode
        if(args.buttonCode === 'back'){

            this.backToHomePage();
        }
        else if(args.buttonCode === 'back-to-lista-report-predefiniti'){

            this.backToListaReportPredefiniti(args);
        }

        return of(args);
    }

    private backToHomePage(): Observable<IDictionary<any>>{

        this.routerService.navigateToPage('home-page');
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
            fromPredefiniti: args.fromPredefiniti
        };

        this.routerService.navigateToPage('risultato-esecuzione-report-predefiniti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private backToListaReportPredefiniti(args: SdkListaReportProviderArgs): Observable<IDictionary<any>> {

        let setUpdateState: Function = args.setUpdateState;
        if(isFunction(setUpdateState)){
            setUpdateState(false);
        }

        this.sdkDettaglioReportStoreService.clear();
        this.sdkDettaglioReportStoreService.idRicerca = args.idRicerca;

        let params: IDictionary<any> = {
            idRicerca: args.idRicerca
        }

        this.routerService.navigateToPage('lista-report-predefiniti-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        })
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
            menuField: args.menuField,
            fromDefinizione: args.fromDefinizione,
            fromPredefiniti: args.fromPredefiniti
        };

        this.routerService.navigateToPage('inserisci-parametri-page', params);
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    //#region Getter/Setters
    
    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    //#endregion

}