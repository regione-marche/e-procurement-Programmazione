import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    FaseSubappaltoEntry,
    FaseSubappaltoInsertForm,
    InizFaseSubappaltoEntry,
} from '../../models/fasi/fase-subappalto.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseConclusioneSubappaltoService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseSubappaltoEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseSubappaltoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseConclusioneSubappalto`, params)
            .pipe(
                map((response: ResponseResult<FaseSubappaltoEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseSubappaltoInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseConclusioneSubappalto`, request);
    }

    public updateFase(request: FaseSubappaltoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseConclusioneSubappalto`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseConclusioneSubappalto`, null, params);
    }

    public getDatiInizializzazione(codGara: string, codLotto: string): Observable<InizFaseSubappaltoEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<InizFaseSubappaltoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseConclusioneSubappalto`, params)
            .pipe(
                map((response: ResponseResult<InizFaseSubappaltoEntry>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
