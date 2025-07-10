import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseComEsitoForm, FaseComunicazioneEntry } from '../../models/fasi/fase-comunicazione-esito.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseComunicazioneEsitoService extends SdkBaseService implements BaseFaseService {

    constructor(inj: Injector) { super(inj) }

    public getFase(codGara: string, codLotto: string): Observable<FaseComunicazioneEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<FaseComunicazioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseComunicazione`, params)
            .pipe(
                map((response: ResponseResult<FaseComunicazioneEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseComEsitoForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseComunicazione`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseComunicazione`, null, params);
    }

    public updateFase(request: FaseComEsitoForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseComunicazione`, request);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}