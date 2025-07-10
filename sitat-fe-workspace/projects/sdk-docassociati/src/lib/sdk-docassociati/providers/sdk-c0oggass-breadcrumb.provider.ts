import { Injectable, Injector } from "@angular/core";
import { SdkBaseProvider, StazioneAppaltanteBaseInfo } from "@maggioli/sdk-appaltiecontratti-base";
import { IDictionary, SdkProvider } from "@maggioli/sdk-commons";
import { toString } from "lodash-es";
import { SdkC0oggassDetailsStoreService } from "../components/c0oggass/sdk-c0oggass-details-store.service";


@Injectable({ providedIn: "root" })
export class SdkC0oggassBreadcrumbProvider extends SdkBaseProvider implements SdkProvider {
    constructor(inj: Injector) {
        super(inj);
    }

    public run(_args: IDictionary<any>): string {
        if (this.c0oggassDetailsStoreService.listDataItem) {
            return toString(this.c0oggassDetailsStoreService.listDataItem.c0acod);
        }
        return null;
    }

    private get c0oggassDetailsStoreService(): SdkC0oggassDetailsStoreService {
        return this.injectable(SdkC0oggassDetailsStoreService);
    }

    //Not necessary for breadcrumb provider
    protected getDefaultFormItem(args: any, stazioneAppaltante: StazioneAppaltanteBaseInfo, syscon: number) { 
        //Emtpty 
    }

    }
