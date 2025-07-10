import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    MisureAggiuntiveSicurezzaEntry,
    MisureAggiuntivesicurezzaInsertForm,
    MisureAggiuntivesicurezzaUpdateForm,
} from '../../models/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza.models';

@Injectable({ providedIn: 'root' })
export class MisureAggiuntiveSicurezzaService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getMisureAggiuntiveSicurezza(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): Observable<ResponseResult<MisureAggiuntiveSicurezzaEntry>> {
        let params: IHttpParams = {
            codGara,
            codLotto,
            codFase: codiceFase,
            numIniz: numeroProgressivo
        };

        return this.restHelper.get<ResponseResult<MisureAggiuntiveSicurezzaEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getMisureAggiuntiveSicurezza`, params);
    }

    public isnertMisureAggiuntiveSicurezza(form: MisureAggiuntivesicurezzaInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertMisureAggiuntiveSicurezza`, form);
    }

    public updateMisureAggiuntiveSicurezza(form: MisureAggiuntivesicurezzaUpdateForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateMisureAggiuntiveSicurezza`, form);
    }

    public deleteMisureAggiuntiveSicurezza(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): Observable<any> {

        let params: IHttpParams = {
            codGara,
            codLotto,
            codFase: codiceFase,
            numIniz: numeroProgressivo
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteMisureAggiuntiveSicurezza`, null, params);
    }

    public downloadDocumentoMisureAggiuntiveSicurezza(codGara: number, codLotto: number, codFase: string, numeroProgressivoFase: string, numDoc: number): Observable<string> {
        const params: IHttpParams = {
            codGara: toString(codGara),
            codLotto: toString(codLotto),
            codFase,
            numeroProgressivoFase,
            numDoc: toString(numDoc)
        };
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/downloadDocumentoMisureAggiuntiveSicurezza`, params)
            .pipe(
                map((response: ResponseResult<string>) => {
                    return response.data;
                })
            );
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}