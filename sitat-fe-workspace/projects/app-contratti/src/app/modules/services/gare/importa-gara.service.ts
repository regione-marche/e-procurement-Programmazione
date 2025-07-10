import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { toString } from 'lodash-es';
import { environment } from '../../../../environments/environment';
import {
    AllineaGaraForm,
    CheckMigrazioneForm,
    CheckMigrazioneGaraEntry,
    Collaborazione,
    ConsultaGaraEntry,
    ImportaGaraForm,
    PresaInCaricoGaraDelegataForm,
    ResponseListaCollaborazioni,
    ResponsePresaInCaricoGaraDelegata,
} from '../../models/gare/importa-gara.model';
import { SimogCredentialsForm } from '../../models/gare/gare.model';


@Injectable({ providedIn: 'root' })
export class ImportaGaraService extends SdkBaseService {
    
    constructor(inj: Injector) { super(inj) }

    public importaGaraDaSimog(request: ImportaGaraForm): Observable<ConsultaGaraEntry> {                
        return this.restHelper.post<ResponseResult<ConsultaGaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/importaGara`, request)
            .pipe(
                map((response: ResponseResult<ConsultaGaraEntry>) => {
                    return response.data;
                })
            );
    }

    public presaCaricoImportaGara(request: ImportaGaraForm): Observable<ConsultaGaraEntry> {                
        return this.restHelper.post<ResponseResult<ConsultaGaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/presaCaricoImportaGara`, request)
            .pipe(
                map((response: ResponseResult<ConsultaGaraEntry>) => {
                    return response.data;
                })
            );
    }
    

    public allineaGaraDaSimog(request: AllineaGaraForm): Observable<ResponseResult<any>> {
        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/allineaGara`, request);
    }

    public checkMigrazione(request: CheckMigrazioneForm): Observable<ResponseResult<CheckMigrazioneGaraEntry>> {
        return this.restHelper.post<ResponseResult<CheckMigrazioneGaraEntry>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/checkMigrazione`, request);
    }

    public getListaCollaborazioni(cfTecnico: string): Observable<Array<Collaborazione>> {
        const params: IHttpParams = {
            cfRup: cfTecnico
        };
        return this.restHelper.get<ResponseResult<Array<Collaborazione>>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/getListaCollaborazioni`, params)
            .pipe(
                map((response: ResponseResult<Array<Collaborazione>>) => {
                    return response.data;
                })
            );
    }

    public presaInCaricoGaraDelegata(form: PresaInCaricoGaraDelegataForm): Observable<ResponseResult<ResponsePresaInCaricoGaraDelegata>> {
        return this.restHelper.post<ResponseResult<ResponsePresaInCaricoGaraDelegata>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/presaInCaricoGaraDelegata`, form);
    }

    public checkRup(
        stazioneAppaltante: string,
        cfStazioneAppaltante: string,
        syscon: string,
        simogCredentials: SimogCredentialsForm
    ): Observable<ResponseListaCollaborazioni> {
        const params: IHttpParams = {
            stazioneAppaltante,
            cfStazioneAppaltante,
            syscon,
            skipRpntLogin: "" + simogCredentials.skipRpntLogin,
            simogUsername: simogCredentials.username,
            simogPassword: simogCredentials.password,
            simogSaveCredentials: "" + simogCredentials.saveCredentials,
        };
        return this.restHelper.get<ResponseListaCollaborazioni>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/checkRup`, params).pipe(
            map((response: ResponseListaCollaborazioni) => {
                return response;
            })
        );
    }

    public allineaGaraDaAnac(body: any) {
        return this.restHelper.post<any>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/riallineaAnac`, body);
    }


    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}