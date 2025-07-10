import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FaseCantieriEntry, FaseCantieriInsertForm, InizFaseCantieriEntry } from '../../models/fasi/fase-cantieri.model';
import { BaseFaseService, FaseEntry } from '../../models/fasi/fasi.model';


@Injectable({ providedIn: 'root' })
export class FaseCantieriService extends SdkBaseService implements BaseFaseService {

    constructor(injector: Injector) {
        super(injector);
    }

    public getDatiInizializzazione(codGara: string, codLotto: string, progressivo: string): Observable<InizFaseCantieriEntry> {
        const params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };
        const appaltoIdMap: { [appaltoId: string]: number } = {
            6: 1,
            7: 3,
            8: 6,
            9: 4,
            10: 7,
            11: 2
        };
        return this.restHelper.get<ResponseResult<InizFaseCantieriEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getInizFaseCantieri`, params)
            .pipe(
                map((response: ResponseResult<InizFaseCantieriEntry>) => {
                    response.data.destinatariNotificaExists =
                        response.data.destinatariNotifica && response.data.destinatariNotifica.length > 0 ? '1' : '0';
                    response.data.tipoIntervento = appaltoIdMap[response.data.idAppalto];
                    return response.data;
                })
            );
    }

    public getFase(codGara: string, codLotto: string, progressivo: string): Observable<FaseCantieriEntry> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            num: progressivo
        };

        return this.restHelper.get<ResponseResult<FaseCantieriEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getFaseCantieri`, params)
            .pipe(
                map((response: ResponseResult<FaseCantieriEntry>) => {
                    response.data.destinatariNotificaExists =
                        response.data.destinatariNotifica && response.data.destinatariNotifica.length > 0 ? '1' : '0';
                    return response.data;
                })
            );
    }

    public insertFase(request: FaseCantieriInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertFaseCantieri`, request);
    }

    public updateFase(request: FaseCantieriInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateFaseCantieri`, request);
    }

    public deleteFase(fase: FaseEntry): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(fase.codGara),
            codLotto: toString(fase.codLotto),
            num: toString(fase.progressivo)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteFaseCantieri`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService); }

    // #endregion

}
