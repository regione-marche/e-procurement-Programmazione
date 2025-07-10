import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseAvanzamentoEntry, FaseAvanzamentoInsertForm, ResponseMaxNumAvan } from '../../models/fasi/fase-avanzamento.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';

@Injectable({ providedIn: 'root' })
export class FaseAvanzamentoService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseAvanzamentoEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseAvanzamentoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseAvanzamento`, params)
            .pipe(
                map((response: ResponseResult<FaseAvanzamentoEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseAvanzamentoInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseAvanzamento`, request);
    }

    public updateFase(request: FaseAvanzamentoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseAvanzamento`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseAvanzamento`, null, params);
    }

    public getMaxNumAvan(codGara: number, codLotto: number): Observable<ResponseMaxNumAvan> {

        let params: IHttpParams = {
            codGara: toString(codGara),
            codLotto: toString(codLotto)
        }

        return this.restHelper.get<ResponseResult<ResponseMaxNumAvan>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getMaxNumAvan`, params)
            .pipe(
                map((response: ResponseResult<ResponseMaxNumAvan>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}