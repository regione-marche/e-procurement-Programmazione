import { Injectable, Injector } from "@angular/core";
import { IHttpParams, SdkBaseService, SdkHttpLoaderService, SdkRestHelperService } from "@maggioli/sdk-commons";
import { SdkTableState } from "@maggioli/sdk-table";
import { head, toString } from "lodash-es";
import { environment } from "projects/app-contratti/src/environments/environment";
import { Observable } from "rxjs";
import { ListaRicercaSchedeTrasmessePcpParams, ResponseSchedeTrasmessePcp } from "../../models/schede-trasmesse-pcp/schede-trasmesse-pcp.model";

@Injectable({
    providedIn: 'root'
})
export class SchedeTrasmessePcpService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    //#region Public

    public getListaSchedeTrasmessePcp(request: ListaRicercaSchedeTrasmessePcpParams, state: SdkTableState): Observable<ResponseSchedeTrasmessePcp> {

        let params: IHttpParams = {
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir,
            stazioneAppaltante: request.stazioneAppaltante,
            autore: request.autore,
            dataTrasmissioneDa: toString(request.dataTrasmissioneDa),
            dataTrasmissioneA: toString(request.dataTrasmissioneA),
            idAppalto: request.idAppalto,
            cigLottoNumber: request.cig,
            tipoScheda: toString(request.tipoScheda),
            progressivoScheda: toString(request.progressivoScheda),
            syscon: toString(request.syscon),
            uffInt: request.uffInt
        };

        return this.restHelper.get<ResponseSchedeTrasmessePcp>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaSchedeTrasmessePcp`, params);
    }

    //#endregion

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    // #endregion

}