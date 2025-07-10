import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";

@Injectable({providedIn: "root"})
export class SdkGestioneReportsProfiliVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    //#region Public

    public run(args: IDictionary<any>): boolean {
        
        //Recupero il campo tutti_profili dalla w_ricerche per il report con idRicerca corrispondente.
        return args?.utente?.tuttiProfili === 0 || args?.utente?.tuttiProfili === "0";
    }

    //#endregion
}