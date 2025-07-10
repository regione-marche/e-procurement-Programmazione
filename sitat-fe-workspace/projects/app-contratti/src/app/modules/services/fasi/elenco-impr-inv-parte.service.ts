import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { isEmpty, set, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseImpresaEntry, FaseImpresaInsertForm } from '../../models/fasi/elenco-impr-inv-parte.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class ElencoImpreseInvitatePartecipantiService extends SdkBaseService implements BaseFaseService {

    constructor(inj: Injector) { super(inj) }

    public getImpresePartecipanti(codGara: string, codLotto: string): Observable<Array<FaseImpresaEntry>> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<Array<FaseImpresaEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseImprese`, params)
            .pipe(
                map((response: ResponseResult<Array<FaseImpresaEntry>>) => {
                    return response.data;
                })
            );
    }

    public insertImpresa(request: FaseImpresaInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseImprese`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseImprese`, null, params);
    }

    public deleteSingolaImpresa(codGara: string, codLotto: string, num: string, numRagg?: string, updateDaexport?: boolean): Observable<any> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num,
            updateDaexport: updateDaexport == null? null: toString(updateDaexport)
        };
        if (!isEmpty(numRagg)) {
            set(params, 'numRagg', numRagg);
        }

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseImprese`, null, params);
    }

    public updateImpresa(request: FaseImpresaInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseImprese`, request);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}