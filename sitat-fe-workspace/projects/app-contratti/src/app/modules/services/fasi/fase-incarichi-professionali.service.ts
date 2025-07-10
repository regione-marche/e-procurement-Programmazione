import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {    
    FaseSubappaltoInsertForm,
    InizFaseSubappaltoEntry,
} from '../../models/fasi/fase-subappalto.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';
import { IncarichiProfEntry } from '../../models/incarichi-professionali/incarichi-professionali.model';


@Injectable({ providedIn: 'root' })
export class FaseIncarichiProfessionaliService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<any> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseIncarichiProfessionali`, params);
    }

    public insertFase(request: FaseSubappaltoInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseIncarichiProfessionali`, request);
    }

    public updateFase(request: FaseSubappaltoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseIncarichiProfessionali`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseIncarichiProfessionali`, null, params);
    }
 
    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
