import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";
import { SdkDettaglioReportStoreService } from "../components/sdk-dati-generali-report/sdk-dati-generali-report-store.service";

@Injectable({
    providedIn: "root"
})
export class SdkDettaglioReportParamsProvider extends SdkBaseService implements SdkProvider{

    constructor(inj: Injector) {
        super(inj);
    }

    //#region Public

    public run(args: IDictionary<any>): IDictionary<any> {
        this.logger.debug('DettaglioReportParamsProvider', args);
        let idRicerca: number = this.sdkDettaglioReportStoreService.idRicerca;
        return {
            idRicerca
        };
    }

    //#endregion

    // #region Getters

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    // #endregion
}