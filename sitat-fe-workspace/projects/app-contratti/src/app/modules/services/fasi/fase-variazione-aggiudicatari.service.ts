import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';
import { FaseVariazioneAggiudicatariEntry, FaseVariazioneAggiudicatariInsertForm, InizFaseVariazioneAggiudicatariEntry } from '../../models/fasi/fase-variazione-aggiudicatari.model';


@Injectable({ providedIn: 'root' })
export class FaseVariazioneAggiudicatariService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseVariazioneAggiudicatariEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseVariazioneAggiudicatariEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseVariazioneAggiudicatari`, params)
            .pipe(
                map((response: ResponseResult<FaseVariazioneAggiudicatariEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseVariazioneAggiudicatariInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseVariazioneAggiudicatari`, request);
    }

    public updateFase(request: FaseVariazioneAggiudicatariInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseVariazioneAggiudicatari`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseVariazioneAggiudicatari`, null, params);
    }

    public getDatiInizializzazione(codGara : string, codLotto: string): Observable<InizFaseVariazioneAggiudicatariEntry>{
        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<InizFaseVariazioneAggiudicatariEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseVariazioneAggiudicatari`, params).pipe(
            map((response: ResponseResult<InizFaseVariazioneAggiudicatariEntry>) => {
                return response.data;
            })
        );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
