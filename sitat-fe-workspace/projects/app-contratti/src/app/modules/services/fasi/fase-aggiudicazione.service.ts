import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseAggInsertForm, FaseAggiudicazioneEntry } from '../../models/fasi/fase-aggiudicazione.model';
import { InizFaseAggiudicazioneEntry } from '../../models/fasi/fase-iniziale.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseAggiudicazioneService extends SdkBaseService implements BaseFaseService {

    constructor(inj: Injector) { super(inj) }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseAggiudicazioneEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseAggiudicazioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseAggiudicazione`, params)
            .pipe(
                map((response: ResponseResult<FaseAggiudicazioneEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseAggInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseAggiudicazione`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseAggiudicazione`, null, params);
    }

    public updateFase(request: FaseAggInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseAggiudicazione`, request);
    }

    public getDatiInizializzazione(codGara : string, codLotto: string): Observable<ResponseResult<InizFaseAggiudicazioneEntry>>{
        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return  this.restHelper.get<ResponseResult<InizFaseAggiudicazioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseAggiudicazione`, params);
    }


    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}