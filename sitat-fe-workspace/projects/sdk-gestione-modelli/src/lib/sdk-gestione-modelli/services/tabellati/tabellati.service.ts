import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, IHttpParams, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';

import { ResponseResult, ValoreTabellato } from '../../model/lib.model';


@Injectable()
export class TabellatiService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public listaValori(baseUrl: string, cod: string): Observable<Array<ValoreTabellato>> {

        let params: IHttpParams = {
            cod
        };

        return this.restHelper.get<ResponseResult<Array<ValoreTabellato>>>(`${baseUrl}/Valori`, params)
            .pipe(
                map((result: ResponseResult<Array<ValoreTabellato>>) => {
                    return result.data;
                })
            );
    }

    public getMultipleListaValori(baseUrl: string, codici: Array<string>): Observable<IDictionary<Array<ValoreTabellato>>> {

        let params: IHttpParams = {
            cods: codici
        };

        return this.restHelper.get<ResponseResult<IDictionary<Array<ValoreTabellato>>>>(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + '/listaTabellati', params)
            .pipe(
                map((result: ResponseResult<IDictionary<Array<ValoreTabellato>>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniUtenti(codProfiloAttivo: string): Observable<any> {

        let params: IHttpParams = {
            codProfiloAttivo
        };

        return this.restHelper.get<ResponseResult<any>>(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + '/getUtentiOptions', params)
            .pipe(
                map((result: ResponseResult<any>) => {
                    return result;
                })
            );
    }


    // #region gettetr

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}