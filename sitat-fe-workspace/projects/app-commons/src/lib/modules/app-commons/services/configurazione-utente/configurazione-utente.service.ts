import { Injectable, Injector } from '@angular/core';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResponseResult } from '../../models/common/common.model';
import { ConfigurazioneUtente } from '../../models/configurazione-utente/configurazione-utente.model';


@Injectable()
export class ConfigurazioneUtenteService extends SdkBaseService {

    constructor(inj: Injector) { super(inj); }

    public dettaglio(baseUrl: string, syscon: string): Observable<ConfigurazioneUtente> {

        let params: IHttpParams = {
            syscon
        };

        return this.restHelper.get<ResponseResult<ConfigurazioneUtente>>(`${baseUrl}/getConfigurazioniUtenti`, params)
            .pipe(
                map((result: ResponseResult<ConfigurazioneUtente>) => {
                    return result.data;
                })
            );
    }

    public modifica(baseUrl: string, configurazione: ConfigurazioneUtente): Observable<string> {

        return this.restHelper.post<ResponseResult<string>>(`${baseUrl}/modificaConfigurazioniUtenti`, configurazione)
            .pipe(
                map((result: ResponseResult<string>) => {
                    return '';
                })
            );
    }


   
    // #region gettetr

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}