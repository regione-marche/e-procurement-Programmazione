import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    FaseAdesioneAccordoQuadroEntry,
    FaseAdesioneAccordoQuadroInsertForm,
} from '../../models/fasi/fase-adesione-accordo-quadro.model';
import { InizFaseAdesioneAccordoQuadroEntry } from '../../models/fasi/fase-iniziale.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseAdesioneAccordoQuadroService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseAdesioneAccordoQuadroEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseAdesioneAccordoQuadroEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseAdesioneAccordoQuadro`, params)
            .pipe(
                map((response: ResponseResult<FaseAdesioneAccordoQuadroEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseAdesioneAccordoQuadroInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseAdesioneAccordoQuadro`, request);
    }

    public updateFase(request: FaseAdesioneAccordoQuadroInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseAdesioneAccordoQuadro`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseAdesioneAccordoQuadro`, null, params);
    }

    public getDatiInizializzazione(codGara : string, codLotto: string): Observable<ResponseResult<InizFaseAdesioneAccordoQuadroEntry>>{
        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return  this.restHelper.get<ResponseResult<InizFaseAdesioneAccordoQuadroEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseAdesioneAccordoQuadro`, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
