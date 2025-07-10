import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    ImportImpresaAgiudicatariaForm,
    ImpresaAggiudicatariaAttoEntry,
    ImpresaAggiudicatariaEntry,
    ImpresaAggiudicatariaInsertForm,
    ImpreseAggiudicatarieAttoInsertForm,
    ImpreseAggiudicatarieUpdateForm,
} from '../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';


@Injectable({ providedIn: 'root' })
export class ImpreseAggiudicatarieService extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getImpreseAggiudicatarie(codGara: string, codLotto: string, numProgressivoFase: string): Observable<Array<ImpresaAggiudicatariaEntry>> {
        let params: IHttpParams = {
            codGara,
            codLotto,
            numAppa: numProgressivoFase
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaAggiudicatariaEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getImpreseAggiudicatarie`, params)
            .pipe(
                map((response: ResponseResult<Array<ImpresaAggiudicatariaEntry>>) => {
                    return response.data;
                })
            );
    }

    public insertImpresaAggiudicataria(form: ImpresaAggiudicatariaInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertImpresaAggiudicataria`, form);
    }

    public updateImpresaAggiudicataria(form: ImpreseAggiudicatarieUpdateForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateImpresaAggiudicataria`, form);
    }

    public deleteImpresaAggiudicataria(codGara: string, codLotto: string, numAggi: number, numAppa: number): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(codGara),
            codLotto: toString(codLotto),
            numAggi: toString(numAggi),
            numAppa: toString(numAppa)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteImpresaAggiudicataria`, null, params);
    }

    public importImpresaAggiudicataria(form: ImportImpresaAgiudicatariaForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/importImpresaAggiudicataria`, form);
    }

    // Atti

    public getImpreseAggiudicatarieAtto(codGara: string, numPubb: string): Observable<Array<ImpresaAggiudicatariaAttoEntry>> {
        let params: IHttpParams = {
            codGara,
            numPubb
        };

        return this.restHelper.get<ResponseResult<Array<ImpresaAggiudicatariaAttoEntry>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getImpreseAggiudicatarieAtto`, params)
            .pipe(
                map((response: ResponseResult<Array<ImpresaAggiudicatariaAttoEntry>>) => {
                    return response.data;
                })
            );
    }

    public insertImpresaAggiudicatariaAtto(form: ImpreseAggiudicatarieAttoInsertForm): Observable<any> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/insertImpresaAggiudicatariaAtto`, form);
    }

    public updateImpresaAggiudicatariaAtto(form: ImpreseAggiudicatarieAttoInsertForm): Observable<any> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/updateImpresaAggiudicatariaAtto`, form);
    }

    public deleteImpresaAggiudicatariaAtto(codGara: string, numPubb: string): Observable<any> {

        let params: IHttpParams = {
            codGara: toString(codGara),
            numPubb: toString(numPubb)
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/deleteImpresaAggiudicatariaAtto`, null, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}