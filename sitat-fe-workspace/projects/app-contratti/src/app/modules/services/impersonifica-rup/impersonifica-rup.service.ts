import { Injectable, Injector } from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { IHttpParams, SdkBaseService, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkTableState } from '@maggioli/sdk-table';
import { head, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';
import { FasiInfo, TabellatoFaseEntry } from '../../models/fasi/fasi.model';
import {
    AccorpaMultilottoEntry,
    AccorpaMultilottoForm,
    AttoEntry,
    AttoInsertForm,
    AttoLottoEntry,
    AttoUpdateForm,
    DatiAccorpamentoEntry,
    DettaglioAttoEntry,
    GaraBaseEntry,
    GaraEntry,
    GaraInsertForm,
    GaraUpdateForm,
    ListaGareParams,
    LottoAccorpabileEntry,
    LottoBaseEntry,
    LottoEntry,
    LottoInsertForm,
    MigrazioneGaraForm,
    PubblicitaGaraEntry,
    PubblicitaGaraInsertForm,
    PulisciAccorpamentiMultilottoForm,
    ResponseAttiAssociatiLotto,
    ResponseExportCsv,
    ResponseInizInsertLotto,
    ResponseListaGare,
    ResponseListaInviiFasi,
    RupGaraUpdateForm,
    SysconGaraUpdateForm,
} from '../../models/gare/gare.model';


@Injectable({ providedIn: 'root' })
export class ImpersonificaRupService extends SdkBaseService {
    
    constructor(inj: Injector) { super(inj) }


    public saveRp(request: any): Observable<ResponseResult<any>> {

        return this.restHelper.post<ResponseResult<any>>(`${environment.GESTIONE_CONTRATTI_BASE_URL}/impersonificaRup`, request);            
    }

   

    // #region Getters

    private get restHelper(): SdkRestHelperService { return this.injectable(SdkRestHelperService) }

    // #endregion
}