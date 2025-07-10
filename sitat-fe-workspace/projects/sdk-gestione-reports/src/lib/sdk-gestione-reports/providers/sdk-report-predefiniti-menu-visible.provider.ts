import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider, SdkSessionStorageService } from "@maggioli/sdk-commons";
import { SdkGestioneReportConstants } from "../sdk-gestione-report.constants";

@Injectable({providedIn: "root"})
export class SdkReportPredefinitiMenuVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj)
    }

    //#region Public

    public run(args: IDictionary<any>): boolean {
        
        let countListaReportPredefiniti: number = this.sdkSessionStorageService.getItem(SdkGestioneReportConstants.COUNT_REPORT_PREDEFINITI);
        return countListaReportPredefiniti > 0;
    }

    //#endregion

    //#region Getters/Setters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    //#endregion
}