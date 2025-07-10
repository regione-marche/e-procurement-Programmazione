import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseAggiudicazioneSempEntry, FaseAggSempInsertForm } from '../../models/fasi/fase-aggiudicazione-semp.model';
import { InizFaseAggiudicazioneSempEntry } from '../../models/fasi/fase-iniziale.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseAggiudicazioneSempService extends SdkBaseService implements BaseFaseService {

    constructor(inj: Injector) { super(inj) }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseAggiudicazioneSempEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseAggiudicazioneSempEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseAggiudicazioneSemp`, params)
            .pipe(
                map((response: ResponseResult<FaseAggiudicazioneSempEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseAggSempInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseAggiudicazioneSemp`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseAggiudicazioneSemp`, null, params);
    }

    public updateFase(request: FaseAggSempInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseAggiudicazioneSemp`, request);
    }

    public getDatiInizializzazione(codGara : string, codLotto: string): Observable<ResponseResult<InizFaseAggiudicazioneSempEntry>>{
        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return  this.restHelper.get<ResponseResult<InizFaseAggiudicazioneSempEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseAggiudicazioneSemp`, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}