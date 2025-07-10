import { ComboDto, SdkBaseListSectionWidget } from "@maggioli/sdk-appaltiecontratti-base";
import { IDictionary } from "@maggioli/sdk-commons";
import { Observable } from "rxjs";
import { SdkDocAssociatiService } from "../../services/sdk-docassociati.service";
import { TabellatiCacheService } from "../../services/tabellati-cache.service";

export abstract class SdkDocAssociatiBaseListSectionWidget extends SdkBaseListSectionWidget { 

    protected loadTabellati(): Observable<IDictionary<ComboDto[]>> {
        return this.tabellatiCacheService.tabGetMultiComboData(this.getValoriTabellatiConst());
    }

    protected get tabellatiCacheService(): TabellatiCacheService {
        return this.injectable(TabellatiCacheService);
    }

    protected get sdkDocAssociatiService(): SdkDocAssociatiService {
        return this.injectable(SdkDocAssociatiService);
    }
}