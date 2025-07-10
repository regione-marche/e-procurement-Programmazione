import { ResponseListaDeleghe, DelegaEntry, DelegaInsertForm, CredenzialiSimogUpdateForm, LoaderAppaltoUsrEntry } from "./../../models/deleghe/deleghe.model";
import { Injectable, Injector } from "@angular/core";
import { ResponseResult } from "@maggioli/app-commons";
import { IHttpParams, SdkBaseService, SdkRestHelperService } from "@maggioli/sdk-commons";
import { SdkTableState } from "@maggioli/sdk-table";
import { head, toString } from "lodash-es";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

import { environment } from "../../../../environments/environment";
import { ListaDelegheParams } from "../../models/deleghe/deleghe.model";

@Injectable({ providedIn: "root" })
export class DelegheService extends SdkBaseService {
    constructor(inj: Injector) {
        super(inj);
    }

    public getListaDeleghe(request: ListaDelegheParams, state: SdkTableState): Observable<ResponseListaDeleghe> {
        let params: IHttpParams = {
            stazioneAppaltante: request.stazioneAppaltante,
            cfrup: request.cfrup,
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
        };

        return this.restHelper.get<ResponseListaDeleghe>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaDeleghe`, params);
    }

    public dettaglioDelega(id: string): Observable<DelegaEntry> {
        let params: IHttpParams = {
            id,
        };

        return this.restHelper.get<ResponseResult<DelegaEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDettaglioDelega`, params).pipe(
            map((response: ResponseResult<DelegaEntry>) => {
                return response.data;
            })
        );
    }

    public insertDelega(request: DelegaInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertDelega`, request);
    }

    public updateDelega(request: DelegaInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateDelega`, request);
    }

    public deleteDelega(syscon: string, idProfilo: string, id: string): Observable<any> {
        let params: IHttpParams = {
            syscon,
            idProfilo,
            id,
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteDelega`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService {
        return this.injectable(SdkRestHelperService);
    }

    // #endregion
}
