import { Injectable, Injector } from '@angular/core';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ResponseResult } from '../models/common/common.model';
import { SAEntry } from '../models/tabellati/tabellato.model';


@Injectable({ providedIn: 'root' })
export class TabellatiService extends SdkBaseService {

    constructor(inj: Injector) { super(inj); }

    public getStazioniAppaltantiOptions(baseUrl: string, desc: string): Observable<Array<SAEntry>> {

        let params: IHttpParams = {
            desc
        };

        return this.restHelper.get<ResponseResult<Array<SAEntry>>>(`${baseUrl}/getStazioniAppaltantiOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<SAEntry>>) => {
                    return result.data;
                })
            );
    }

    // #region gettetr

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}