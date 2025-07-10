import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, IHttpParams, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ResponseResult, StazioneAppaltanteEntry, ValoreTabellato } from '../../model/lib.model';


@Injectable({ providedIn: 'root' })
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

        return this.restHelper.get<ResponseResult<IDictionary<Array<ValoreTabellato>>>>(`${baseUrl}/listaTabellati`, params)
            .pipe(
                map((result: ResponseResult<IDictionary<Array<ValoreTabellato>>>) => {
                    return result.data;
                })
            );
    }

    public listaOpzioniUtenti(baseUrl: string, stazioneAppaltante: string, rup: string): Observable<Array<any>> {

        let params: IHttpParams = {
            stazioneAppaltante,
            rup
        };

        return this.restHelper.get<ResponseResult<Array<any>>>(`${baseUrl}/getUtentiOptions`, params)
            .pipe(
                map((result: ResponseResult<Array<any>>) => {
                    return result.data;
                })
            );
    }

    public getStazioneAppaltante(baseUrl: string, codiceSA: string): Observable<StazioneAppaltanteEntry> {

        const params: IHttpParams = {
            codiceSA
        };

        return this.restHelper.get<ResponseResult<StazioneAppaltanteEntry>>(`${baseUrl}/getSa`, params)
            .pipe(
                map((result: ResponseResult<StazioneAppaltanteEntry>) => {
                    return result.data;
                })
            );
    }


    // #region gettetr

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}