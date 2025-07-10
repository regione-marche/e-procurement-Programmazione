import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";

@Injectable({providedIn: "root"})
export class SdkGestioneReportsUfficiIntestatariVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    //#region Public

    public run(args: IDictionary<any>): boolean {
        
        //Recupero il campo tutti_uffici dalla w_ricerche per il report con idRicerca corrispondente.
        return (args?.utente?.tuttiUffici === 0 || args?.utente?.tuttiUffici === "0") && args.configUffInt === 1;
    }

    //#endregion
}