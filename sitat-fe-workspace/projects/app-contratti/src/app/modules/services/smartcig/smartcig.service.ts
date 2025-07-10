import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { SdkBaseService, SdkRestHelperService, IHttpParams } from '@maggioli/sdk-commons';
import { Observable, of } from 'rxjs';

import { environment } from '../../../../environments/environment';
import { SmartCigInsertForm, SmartCigEntry, SmartCigUpdateForm } from '../../models/smartcig/smartcig.model';
import { map } from 'rxjs/operators';
import { ImportaSmartcigForm } from '../../models/gare/importa-gara.model';


@Injectable({ providedIn: 'root' })
export class SmartCigService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public dettaglioSmartCig(codGara: string): Observable<SmartCigEntry> {

        const params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<SmartCigEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getDettaglioSmartCig`, params)
            .pipe(
                map((response: ResponseResult<SmartCigEntry>) => {
                    return response.data;
                })
            );
    }

    public insertSmartCig(request: SmartCigInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertSmartCig`, request);
    }

    public insertPubblicaSmartCig(request: SmartCigInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertPubblicaSmartCig`, request);
    }

    public updateAndPublishSmartCigFactory(request: SmartCigUpdateForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updatePubblicaSmartCig`, request);
    }
    

    public updateSmartCig(request: SmartCigUpdateForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateSmartCig`, request);
    }

    public deleteSmartCig(codGara: string): Observable<any> {

        const params: IHttpParams = {
            codGara
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteSmartCig`, null, params);
    }

    public importaSmartCig(form: ImportaSmartcigForm): Observable<any>{
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/importaSmartcig`, form);
    }


    public isGaraSmartCig(codGara: string): Observable<boolean> {
        const params: IHttpParams = {
            codGara
        };

        return this.restHelper.get<ResponseResult<boolean>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/isGaraSmartCig`, params)
            .pipe(
                map((response: ResponseResult<boolean>) => {
                    return response.data;
                })
            );
    }

    public getInizImportSmartcig(syscon: string): Observable<any> {
        const params: IHttpParams = {
            syscon
        };

        return this.restHelper.get<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizImportSmartcig`, params)
            .pipe(
                map((response: ResponseResult<any>) => {
                    return response.data;
                })
            );
    }
    


    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}