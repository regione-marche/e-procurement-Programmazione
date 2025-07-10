import { Injectable, Injector } from '@angular/core';
import {  SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable, map } from 'rxjs';
import { ResponseResult } from '../../models/common/common.model';
import { AppInfoEntry } from '../../models/appinfo/app-info.model';


@Injectable()
export class AppInfoService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }


    public getAppinfo(baseUrl: string): Observable<any> {       
        return this.restHelper.get<ResponseResult<AppInfoEntry>>(`${baseUrl}/getAppInfo`).pipe(
            map((result: ResponseResult<AppInfoEntry>) => {
                return result.data;
            })
        );
    }

    // #region Getter

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}