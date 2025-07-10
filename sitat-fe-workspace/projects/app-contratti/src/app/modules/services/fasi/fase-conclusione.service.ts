import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    FaseConclusioneEntry,
    FaseConclusioneInsertForm,
    InizFaseConclusioneContrattoEntry,
} from '../../models/fasi/fase-conclusione.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseConclusioneService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseConclusioneEntry> {

        const params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseConclusioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseConclusioneContratto`, params)
            .pipe(
                map((response: ResponseResult<FaseConclusioneEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseConclusioneInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseConclusioneContratto`, request);
    }

    public updateFase(request: FaseConclusioneInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseConclusioneContratto`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseConclusioneContratto`, null, params);
    }

    public getDatiInizializzazione(codGara: string, codLotto: string, progressivo: string): Observable<InizFaseConclusioneContrattoEntry> {
        const params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };
        return this.restHelper
            .get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseConclusioneContratto`, params)
            .pipe(
                map((response: ResponseResult<InizFaseConclusioneContrattoEntry>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
