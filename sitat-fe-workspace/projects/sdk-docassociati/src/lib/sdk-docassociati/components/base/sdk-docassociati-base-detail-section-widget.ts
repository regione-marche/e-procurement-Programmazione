import { ComboDto, SdkBaseDetailSectionWidget } from "@maggioli/sdk-appaltiecontratti-base";
import { IDictionary } from "@maggioli/sdk-commons";
import { SdkFormBuilderField } from "@maggioli/sdk-controls";
import { set } from "lodash-es";
import { Observable } from "rxjs";
import { SdkDocAssociatiService } from "../../services/sdk-docassociati.service";
import { TabellatiCacheService } from "../../services/tabellati-cache.service";

export abstract class SdkDocAssociatiBaseDetailSectionWidget extends SdkBaseDetailSectionWidget { 

    protected allowedFileExtensions: string;
    protected maxAllowedFileSize: number;

    protected loadTabellati(): Observable<IDictionary<ComboDto[]>> {
        return this.tabellatiCacheService.tabGetMultiComboData(this.getValoriTabellatiConst());
    }

    protected get tabellatiCacheService(): TabellatiCacheService {
        return this.injectable(TabellatiCacheService);
    }

    protected get sdkDocAssociatiService(): SdkDocAssociatiService {
        return this.injectable(SdkDocAssociatiService);
    }

    protected manageFieldsForCustomPopulateFunction(field: SdkFormBuilderField, mapping: boolean) {

        if (field.code === 'digoggBase64') {
            set(field, 'accept', this.allowedFileExtensions);
            set(field, 'maxFileSize', this.maxAllowedFileSize + ' MB');
        }
        
        return mapping;
    }
}