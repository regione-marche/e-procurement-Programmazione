import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseVarianteEntry, FaseVarianteInsertForm } from '../../models/fasi/fase-modifica-contrattuale.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';
import { LottoDynamicValue, FaseVarianteIniz } from '../../models/gare/gare.model';


@Injectable({ providedIn: 'root' })
export class FaseModificaContrattualeService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseVarianteEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseVarianteEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseVariante`, params)
            .pipe(
                map((response: ResponseResult<FaseVarianteEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseVarianteInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseVariante`, request);
    }

    public updateFase(request: FaseVarianteInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseVariante`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseVariante`, null, params);
    }

    public getInizFaseVariante(codGara: string, codLotto: string): Observable<FaseVarianteIniz> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<FaseVarianteIniz>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseVariante`, params)
            .pipe(
                map((response: ResponseResult<FaseVarianteIniz>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
