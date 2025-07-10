import { Injectable, Injector } from '@angular/core';
import { ChiaviAccessoOrt, ResponseResult } from '@maggioli/app-commons';
import { IHttpHeaders, IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import {
    AvvisoEntry,
    AvvisoInsertForm,
    CambioSysconForm,
    ResponseListaAvvisi,
    RicercaAvvisoForm,
} from '../../models/avviso/avviso.model';
import {
    AllineamentoPubblicazioneModel,
    PubblicazioneAvvisoModel,
} from '../../models/pubblicazioni/pubblicazione-avviso-model';


@Injectable({ providedIn: 'root' })
export class AvvisiService extends SdkBaseService {    
    

    constructor(inj: Injector) { super(inj); }

    public dettaglioAvviso(numeroAvviso: string, stazioneAppaltante: string): Observable<AvvisoEntry> {

        let params: IHttpParams = {
            numeroAvviso,
            stazioneAppaltante
        };

        return this.restHelper.get<ResponseResult<AvvisoEntry>>(`${environment.GESTIONE_AVVISI_BASE_URL}/GetAvviso`, params)
            .pipe(
                map((result: ResponseResult<AvvisoEntry>) => {
                    return result.data;
                })
            );
    }

    public getListaAvvisi(form: RicercaAvvisoForm, state: SdkTableState): Observable<ResponseListaAvvisi> {

        let params: IHttpParams = {
            numeroAvviso: form.numeroAvviso ? form.numeroAvviso : '',
            stazioneAppaltante: form.stazioneAppaltante ? form.stazioneAppaltante : '',
            cig: form.cig ? form.cig : '',
            cup: form.cup ? form.cup : '',
            dataA: form.dataA ? form.dataA : '',
            dataDa: form.dataDa ? form.dataDa : '',
            dataScadenzaA: form.dataScadenzaA ? form.dataScadenzaA : '',
            dataScadenzaDa: form.dataScadenzaDa ? form.dataScadenzaDa : '',
            descrizione: form.descrizione ? form.descrizione : '',
            syscon: form.syscon ? form.syscon : '',
            tipologia: form.tipologia ? form.tipologia : '',
            skip: toString(state.skip),
            take: toString(state.take),
            sort: head(state.sort).field,
            sortDirection: head(state.sort).dir
        };

        return this.restHelper.get<ResponseListaAvvisi>(`${environment.GESTIONE_AVVISI_BASE_URL}/GetListaAvvisi`, params);
    }

    public deleteAvviso(numeroAvviso: string, stazioneAppaltante: string): Observable<any> {
        let params: IHttpParams = {
            numeroAvviso,
            stazioneAppaltante
        };

        return this.restHelper.delete(`${environment.GESTIONE_AVVISI_BASE_URL}/DeleteAvviso`, null, params);
    }


    public createAvviso(insertForm: AvvisoInsertForm): Observable<string> {
        return this.restHelper.post(`${environment.GESTIONE_AVVISI_BASE_URL}/InsertAvviso`, insertForm);

    }

    public modificaAvviso(updateForm: AvvisoInsertForm): Observable<string> {
        return this.restHelper.put(`${environment.GESTIONE_AVVISI_BASE_URL}/UpdateAvviso`, updateForm);

    }


    public checkPubblicazioneAvviso(model: PubblicazioneAvvisoModel, token: string): Observable<string> {
        let params: IHttpParams = {
            modalitaInvio: '1'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post(`${environment.PUBBLICAZIONE_AVVISI_BASE_URL}/Pubblica`, model, params, headers);
    }


    public pubblicaAvviso(model: PubblicazioneAvvisoModel, token: string): Observable<string> {

        let params: IHttpParams = {
            modalitaInvio: '2'
        };

        let headers: IHttpHeaders = {
            applicationToken: token
        };
        return this.restHelper.post(`${environment.PUBBLICAZIONE_AVVISI_BASE_URL}/Pubblica`, model, params, headers);
    }

    public setIdRicevuto(model: AllineamentoPubblicazioneModel): Observable<any> {
        return this.restHelper.put(`${environment.GESTIONE_AVVISI_BASE_URL}/SetPubbId`, model);
    }

    public serviceAccessPubblica(chiaviAccessoOrt: ChiaviAccessoOrt): Observable<any> {

        let params: IHttpParams = {
            ...chiaviAccessoOrt
        };
        return this.restHelper.get<any>(`${environment.PUBBLICAZIONE_AVVISI_BASE_URL}/serviceAccessPubblica`, params);
    }

    public cambiaSyscon(form: CambioSysconForm): Observable<ResponseResult<any>> {
        return this.restHelper.put<ResponseResult<any>>(`${environment.GESTIONE_AVVISI_BASE_URL}/cambiaSyscon`, form);
    }

    public downloadDocumentoAvviso(idAvviso: number, codiceStazioneAppaltante: string, numDoc: number): Observable<string> {
        let params: IHttpParams = {
            idAvviso: toString(idAvviso),
            codiceStazioneAppaltante,
            numDoc: toString(numDoc)
        };
        return this.restHelper.get<ResponseResult<string>>(`${environment.GESTIONE_AVVISI_BASE_URL}/downloadDocumentoAvviso`, params)
            .pipe(map((result: ResponseResult<string>) => {
                return result.data;
            }));
    }

    public getRequestPubblicazioneAvviso(idAvviso: number, codiceStazioneAppaltante: string): Observable<PubblicazioneAvvisoModel> {
        let params: IHttpParams = {
            idAvviso: toString(idAvviso),
            codiceStazioneAppaltante
        };
        return this.restHelper.get<ResponseResult<PubblicazioneAvvisoModel>>(`${environment.GESTIONE_AVVISI_BASE_URL}/getRequestPubblicazioneAvviso`, params)
            .pipe(map((result: ResponseResult<PubblicazioneAvvisoModel>) => {
                return result.data;
            }));
    }

    public getListaAvvisiNonPaginata(stazioneAppaltante: string, syscon: number, searchString: string): Observable<Array<AvvisoEntry>> {
        const params: IHttpParams = {
            stazioneAppaltante,
            syscon: toString(syscon),
            searchString
        };

        return this.restHelper.get<ResponseResult<Array<AvvisoEntry>>>(`${environment.GESTIONE_AVVISI_BASE_URL}/getListaAvvisiNonPaginata`, params)
            .pipe(
                map((result: ResponseResult<Array<AvvisoEntry>>) => {
                    return result.data;
                })
            );
    }

    public deleteFlussoAvviso(idAvviso: string) : Observable<any> {
        const params: IHttpParams = {
            idAvviso            
        };

        return this.restHelper.delete<ResponseResult<any>>(`${environment.GESTIONE_AVVISI_BASE_URL}/deleteFlussoAvviso`, null, params);
    }

    public getLinkPubbblicazione(idGenerato: string,stazioneAppaltante: string,entita: string): Observable<ResponseResult<string>> {
        const params: IHttpParams = {
            idRicevuto:idGenerato,
            stazioneAppaltante,
            entita
        };
        
        return this.restHelper.get<ResponseResult<string>>(`${environment.PUBBLICAZIONE_ATTI_BASE_URL}/getPubblicazioneLink`,params)           
    }

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService); };

    // #endregion
}