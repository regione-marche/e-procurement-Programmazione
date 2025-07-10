import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FinanziamentiEntry, FinanziamentiInsertForm } from '../../models/finanziamenti/finanziamenti.model';


@Injectable({ providedIn: 'root' })
export class FinanziamentiService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getFinanziamenti(codGara: string, codLotto: string, progressivo: string): Observable<Array<FinanziamentiEntry>> {
        let params: IHttpParams = {
            codGara,
            codLotto,
            numAppa: progressivo
        };

        return this.restHelper.get<ResponseResult<Array<FinanziamentiEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFinanziamenti`, params)
            .pipe(
                map((response: ResponseResult<Array<FinanziamentiEntry>>) => {
                    return response.data;
                })
            );
    }

    public updateFinanziamenti(form: FinanziamentiInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFinanziamenti`, form);
    }

    public getDatiInizializzazione(codGara: string, codLotto: string): Observable<number> {
        const params: IHttpParams = {
            codGara,
            codLotto
        };
        return this.restHelper.get<ResponseResult<number>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFinanziamenti`, params)
            .pipe(
                map((response: ResponseResult<number>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}