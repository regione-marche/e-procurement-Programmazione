import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";
import { SdkDettaglioReportStoreService } from "../components/sdk-dati-generali-report/sdk-dati-generali-report-store.service";

@Injectable({ providedIn: 'root' })
export class BreadcrumbNomeReportProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(_args: IDictionary<any>): string {

        const nomeReport: string = this.sdkDettaglioReportStoreService.nome;
        this.logger.debug('BreadcrumbNomeReportProvider >>>', nomeReport);
        return nomeReport != null ? nomeReport : null;
    }

    // #region Getters

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    // #endregion

}