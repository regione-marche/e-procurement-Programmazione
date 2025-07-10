import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    FaseInizialeEsecuzioneEntry,
    FaseInizialeEsecuzioneInsertForm,
    InizFaseInizialeContrattoEntry,
} from '../../models/fasi/fase-iniziale.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseSottoScrizioneContrattoService extends SdkBaseService implements BaseFaseService {

    constructor(inj: Injector) { super(inj) }

   
    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseInizialeEsecuzioneEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseInizialeEsecuzioneEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseInizialeSottoscrContr`, params)
            .pipe(
                map((response: ResponseResult<FaseInizialeEsecuzioneEntry>) => {
                    return response.data;
                })
            );
    }
    
    public insertFase(request: FaseInizialeEsecuzioneInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseInizialeSottoscrContr`, request);
    }
   
    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseInizialeSottoscrContr`, null, params);
    }
  
    public updateFase(request: FaseInizialeEsecuzioneInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseInizialeSottoscrContr`, request);
    }

    public getDatiInizializzazione(codGara: string, codLotto: string): Observable<InizFaseInizialeContrattoEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto
        };

        return this.restHelper.get<ResponseResult<InizFaseInizialeContrattoEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseInizialeContratto`, params)
            .pipe(
                map((response: ResponseResult<InizFaseInizialeContrattoEntry>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}