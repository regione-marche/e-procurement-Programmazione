import { Injector } from "@angular/core";
import { SdkBaseListDatasource } from "@maggioli/sdk-appaltiecontratti-base";
import { IDictionary } from "@maggioli/sdk-commons";
import { SdkTableDataResult, SdkTableRowDto, SdkTableState } from "@maggioli/sdk-table";
import { head, map as mapArray } from "lodash-es";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import { C0oggassListDto, C0oggassListRequest, C0oggassListResponse } from "../../../model/sdk-docassociati.model";
import { SdkDocAssociatiService } from "../../../services/sdk-docassociati.service";
import { SdkC0oggassDetailsStoreService } from "../sdk-c0oggass-details-store.service";
import { SdkC0oggassParamsStoreService } from "../sdk-c0oggass-params-store.service";


export class SdkC0oggassListDatasource extends SdkBaseListDatasource {
    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {
        this.logger.debug("SdkTableState >>>", state);

        let ob = this.c0oggassGetList(state);
        return ob.pipe(map(this.adaptServerResults(state)));
    }

    private adaptServerResults = (state: SdkTableState) => {
        return (res: C0oggassListResponse): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state,
            };
            return result;
        };
    };

    protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService {
        return this.injectable(SdkC0oggassParamsStoreService);
    }

    private c0oggassGetList(state: SdkTableState): Observable<C0oggassListResponse> {
        let factory = this.c0oggassGetListFactory(this.typedParams.codein, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private c0oggassGetListFactory(codein: string, state: SdkTableState) {
        return () => {
            let params: C0oggassListRequest = {
                codein,
                take: state.take,
                skip: state.skip,
                sort: head(state.sort).field,
                sortDirection: head(state.sort).dir,
                c0aent: this.sdkC0oggassParamsStoreService.c0oggassParams.c0aent,
                c0akey1: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1,
                c0akey2: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey2,
                c0akey3: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey3,
                c0akey4: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey4,
                c0akey5: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey5
            };
            return this.sdkDocAssociatiService.c0oggassGetList(params);
        };
    }

    private adaptServerDataItem = (item: C0oggassListDto): SdkTableRowDto => this.adaptDataItem(item);

    private adaptDataItem = (item: C0oggassListDto): any => {
        return {
            ...item,
        };
    };

    private get sdkDocAssociatiService(): SdkDocAssociatiService {
        return this.injectable(SdkDocAssociatiService);
    }

    private get sdkC0oggassDetailsStoreService(): SdkC0oggassDetailsStoreService {
        return this.injectable(SdkC0oggassDetailsStoreService);
    }
}
