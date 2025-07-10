import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { SchedaSicurezzaEntry, SchedaSicurezzaInsertForm } from '../../models/scheda-sicurezza/scheda-sicurezza.model';


@Injectable({ providedIn: 'root' })
export class SchedaSicurezzaService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getSchedaSicurezza(codGara: string, codLotto: string, numeroProgressivo: string): Observable<ResponseResult<SchedaSicurezzaEntry>> {
        let params: IHttpParams = {
            codGara,
            codLotto,
            numIniz: numeroProgressivo
        };

        return this.restHelper.get<ResponseResult<SchedaSicurezzaEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getSchedaSicurezza`, params);
    }

    public insertSchedaSicurezza(form: SchedaSicurezzaInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertSchedaSicurezza`, form);
    }

    public updateSchedaSicurezza(form: SchedaSicurezzaInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateSchedaSicurezza`, form);
    }

    public deleteSchedaSicurezza(codGara: string, codLotto: string, numeroProgressivo: string): Observable<any> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            numIniz: numeroProgressivo
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteSchedaSicurezza`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}