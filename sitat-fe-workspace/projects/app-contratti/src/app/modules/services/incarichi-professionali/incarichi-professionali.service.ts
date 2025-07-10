import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    IncarichiProfEntry,
    IncarichiProfessionaliInsertForm,
} from '../../models/incarichi-professionali/incarichi-professionali.model';


@Injectable({ providedIn: 'root' })
export class IncarichiProfessionaliService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getIncarichiProfessionali(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): Observable<Array<IncarichiProfEntry>> {
        let params: IHttpParams = {
            codGara,
            codLotto,
            num: numeroProgressivo,
            codFase: codiceFase
        };

        return this.restHelper.get<ResponseResult<Array<IncarichiProfEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getIncarichiProfessionali`, params)
            .pipe(
                map((response: ResponseResult<Array<IncarichiProfEntry>>) => {
                    return response.data;
                })
            );
    }

    public updateIncarichiProfessionali(form: IncarichiProfessionaliInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateIncarichiProfessionali`, form);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}