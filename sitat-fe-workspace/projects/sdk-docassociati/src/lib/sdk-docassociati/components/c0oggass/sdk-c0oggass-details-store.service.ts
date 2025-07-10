import { Injectable, Injector } from "@angular/core";
import { SdkBaseService } from "@maggioli/sdk-commons";
import { C0oggassListDto } from "../../model/sdk-docassociati.model";

@Injectable({ providedIn: "root" })
export class SdkC0oggassDetailsStoreService extends SdkBaseService {

    private _listDataItem: C0oggassListDto;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug("CLEAR");
        delete this._listDataItem;
    }

    public get listDataItem(): C0oggassListDto {
        this.logger.debug("GET listDataItem >>>", this._listDataItem);
        return this._listDataItem;
    }

    public set listDataItem(listDataItem: C0oggassListDto) {
        this.logger.debug("SET listDataItem >>>", listDataItem);
        this._listDataItem = listDataItem;
    }
}
