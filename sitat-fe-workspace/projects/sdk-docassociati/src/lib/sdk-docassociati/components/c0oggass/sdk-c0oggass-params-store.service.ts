import { Injectable, Injector } from "@angular/core";
import { SdkBaseService } from "@maggioli/sdk-commons";
import { C0oggassParams } from "../../model/sdk-docassociati.model";
import { SdkBreadcrumbItem } from "@maggioli/sdk-controls";


@Injectable({ providedIn: "root" })
export class SdkC0oggassParamsStoreService extends SdkBaseService {

    private _c0oggassParams: C0oggassParams;
    private _parentBreadcrumbs: SdkBreadcrumbItem[];
    
    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug("CLEAR");
        
        delete this._c0oggassParams;
        delete this._parentBreadcrumbs;
    }

    public get c0oggassParams(): C0oggassParams {
        this.logger.debug("GET portale >>>", this._c0oggassParams);
        return this._c0oggassParams;
    }

    public set c0oggassParams(c0oggassParams: C0oggassParams) {
        this.logger.debug("SET c0oggassParams >>>", c0oggassParams);
        this._c0oggassParams = c0oggassParams;
    }

    public get parentBreadcrumbs(): SdkBreadcrumbItem[] {
        this.logger.debug("GET parentBreadcrumbs >>>", this._parentBreadcrumbs);
        return this._parentBreadcrumbs;
    }

    public set parentBreadcrumbs(parentBreadcrumbs: SdkBreadcrumbItem[]) {
        this.logger.debug("SET parentBreadcrumbs >>>", parentBreadcrumbs);
        this._parentBreadcrumbs = parentBreadcrumbs;
    }
}