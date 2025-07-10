import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseIstanzaRecessoEntry, FaseIstanzaRecessoInsertForm } from '../../models/fasi/fase-recesso.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseRecessoService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseIstanzaRecessoEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseIstanzaRecessoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseIstanzaRecesso`, params)
            .pipe(
                map((response: ResponseResult<FaseIstanzaRecessoEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseIstanzaRecessoInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseIstanzaRecesso`, request);
    }

    public updateFase(request: FaseIstanzaRecessoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseIstanzaRecesso`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseIstanzaRecesso`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
