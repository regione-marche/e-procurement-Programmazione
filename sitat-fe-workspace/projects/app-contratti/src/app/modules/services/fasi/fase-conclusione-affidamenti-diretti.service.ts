import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    FaseConclusioneAffidamentiDirettiEntry,
    FaseConclusioneAffidamentiDirettiInsertForm,
} from '../../models/fasi/fase-conclusione-affidamenti-diretti.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseConclusioneAffidamentiDirettiService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseConclusioneAffidamentiDirettiEntry> {

        const params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseConclusioneAffidamentiDirettiEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseConclusioneAffidamentiDiretti`, params)
            .pipe(
                map((response: ResponseResult<FaseConclusioneAffidamentiDirettiEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseConclusioneAffidamentiDirettiInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseConclusioneAffidamentiDiretti`, request);
    }

    public updateFase(request: FaseConclusioneAffidamentiDirettiInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseConclusioneAffidamentiDiretti`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseConclusioneAffidamentiDiretti`, null, params);
    }
       
    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
