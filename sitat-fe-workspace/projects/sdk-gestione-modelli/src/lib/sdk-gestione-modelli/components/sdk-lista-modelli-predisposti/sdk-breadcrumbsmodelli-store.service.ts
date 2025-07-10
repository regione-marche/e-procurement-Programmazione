import { Injectable, Injector } from "@angular/core";
import { SdkBaseService } from "@maggioli/sdk-commons";
import { SdkBreadcrumbItem } from "@maggioli/sdk-controls";


@Injectable({ providedIn: "root" })
export class SdkBreadcrumbsModelliStoreService extends SdkBaseService {

    private _parentBreadcrumbs: SdkBreadcrumbItem[];

    private _idEntita: [];
    
    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug("CLEAR");
        
        delete this._parentBreadcrumbs;
        delete this._idEntita;
    }

    public get parentBreadcrumbs(): SdkBreadcrumbItem[] {
        this.logger.debug("GET parentBreadcrumbs >>>", this._parentBreadcrumbs);
        return this._parentBreadcrumbs;
    }

    public set parentBreadcrumbs(parentBreadcrumbs: SdkBreadcrumbItem[]) {
        this.logger.debug("SET parentBreadcrumbs >>>", parentBreadcrumbs);
        this._parentBreadcrumbs = parentBreadcrumbs;
    }

    public get idEntita():[] {
        this.logger.debug("GET idEntita >>>", this._idEntita);
        return this._idEntita;
    }

    public set idEntita(idEntita:[]) {
        this.logger.debug("SET idEntita >>>", idEntita);
        this._idEntita = idEntita;
    }
}