import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt } from '@maggioli/app-commons';
import { IHttpHeaders, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';

import { environment } from '../../../../environments/environment';
import {
    PubblicaProgrammaFornitureServiziEntry,
    PubblicaProgrammaLavoriEntry,
    PubblicazioneResult,
} from '../../models/pubblicazione/pubblicazione.model';

@Injectable({ providedIn: 'root' })
export class PubblicazioneService extends SdkBaseService {

    constructor(inj: Injector) { super(inj); }

    public checkPubblicazioneLavori(programma: PubblicaProgrammaLavoriEntry, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '1'
        };
        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post(`${environment.PUBBLICAZIONE_PROGRAMMI_BASE_URL}/PubblicaLavori`, programma, params, headers);
    }

    public checkPubblicazioneFornitureServizi(programma: PubblicaProgrammaFornitureServiziEntry, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '1'
        };
        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post(`${environment.PUBBLICAZIONE_PROGRAMMI_BASE_URL}/PubblicaFornitureServizi`, programma, params, headers);
    }

    public checkPubblicazioneDirettoLavori(idProgramma: string, clientId: string, syscon: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '1',
            idProgramma,
            clientId,
            syscon
        };
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/PubblicaLavori`, undefined, params);
    }

    public checkPubblicazioneDirettoFornitureServizi(idProgramma: string, clientId: string, syscon: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '1',
            idProgramma,
            clientId,
            syscon
        };
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/PubblicaFornitureServizi`, undefined, params);
    }

    public pubblicaPubblicazioneLavori(programma: PubblicaProgrammaLavoriEntry, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '2'
        };
        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post(`${environment.PUBBLICAZIONE_PROGRAMMI_BASE_URL}/PubblicaLavori`, programma, params, headers);
    }

    public pubblicaPubblicazioneFornitureServizi(programma: PubblicaProgrammaFornitureServiziEntry, token: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            modalitaInvio: '2'
        };
        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post(`${environment.PUBBLICAZIONE_PROGRAMMI_BASE_URL}/PubblicaFornitureServizi`, programma, params, headers);
    }

    public pubblicaPubblicazioneLavoriDiretto(idProgramma: string, clientId: string, syscon: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            idProgramma,
            modalitaInvio: '2',
            clientId,
            syscon
        };
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/PubblicaLavori`, undefined, params, undefined);
    }

    public pubblicaPubblicazioneFornitureServiziDiretto(idProgramma: string, clientId: string, syscon: string): Observable<PubblicazioneResult> {
        let params: IHttpParams = {
            idProgramma,
            modalitaInvio: '2',
            clientId,
            syscon
        };
        return this.restHelper.post(`${environment.GESTIONE_PROGRAMMI_BASE_URL}/PubblicaFornitureServizi`, undefined, params, undefined);
    }

    public serviceAccessPubblica(chiaviAccessoOrt: ChiaviAccessoOrt): Observable<any> {

        let params: IHttpParams = {
            ...chiaviAccessoOrt
        };
        return this.restHelper.get<any>(`${environment.PUBBLICAZIONE_PROGRAMMI_BASE_URL}/serviceAccessPubblica`, params);
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService); };

    // #endregion
}