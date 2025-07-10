import { Injectable } from '@angular/core';
import { IHttpParams, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable, map } from 'rxjs';
import { InfoCampo, ResponseResult } from '../model/sdk-base.model';

@Injectable()
export class MnemonicoService {

    constructor(private restHelper: SdkRestHelperService) {

    }

    public getInfoCampo(baseUrl: string, mnemonico: string): Observable<ResponseResult<InfoCampo>> {
        let params: IHttpParams = {
            mnemonico
        };

        return this.restHelper.get<ResponseResult<InfoCampo>>(`${baseUrl}/getInfoCampo`, params);
    }
}