import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    FaseInidoneitaTecnicoProfEntry,
    FaseInidoneitaTecnicoProfInsertForm,
} from '../../models/fasi/fase-inidoneita-tecnico-prof.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseInidoneitaTecnicoProfService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseInidoneitaTecnicoProfEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseInidoneitaTecnicoProfEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseInidoneitaTecnicoProf`, params)
            .pipe(
                map((response: ResponseResult<FaseInidoneitaTecnicoProfEntry>) => {
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseInidoneitaTecnicoProfInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseInidoneitaTecnicoProf`, request);
    }

    public updateFase(request: FaseInidoneitaTecnicoProfInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseInidoneitaTecnicoProf`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseInidoneitaTecnicoProf`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion

}
