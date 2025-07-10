import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseSospensioneEntry, FaseRipresaPrestazioneInsertForm, InizFaseRipresaPrestazioneEntry } from '../../models/fasi/fase-sospensione.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseRipresaPrestazioneService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseSospensioneEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseSospensioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseRipresaPrestazione`, params)
            .pipe(
                map((response: ResponseResult<FaseSospensioneEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseRipresaPrestazioneInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseRipresaPrestazione`, request);
    }

    public updateFase(request: FaseRipresaPrestazioneInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseRipresaPrestazione`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseRipresaPrestazione`, null, params);
    }

    public getDatiInizializzazione(codGara: string, codLotto: string): Observable<InizFaseRipresaPrestazioneEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<InizFaseRipresaPrestazioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseRipresaPrestazione`, params)
            .pipe(
                map((response: ResponseResult<InizFaseRipresaPrestazioneEntry>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
