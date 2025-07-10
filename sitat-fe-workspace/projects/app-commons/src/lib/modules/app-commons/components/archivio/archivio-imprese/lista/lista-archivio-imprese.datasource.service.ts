import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    ImpresaBaseEntry,
    ListaArchivioImpreseParams,
    ResponseListaImprese,
    RicercaAvanzataArchivioImpreseForm,
} from '../../../../models/archivio/archivio-imprese.models';
import { ResponseListaRupPaginata } from '../../../../models/tabellati/tabellato.model';
import { ArchivioImpreseService } from '../../../../services/archivio/archivio-imprese.service';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';


export class ListaArchivioImpreseDatasource extends SdkTableDatasource {

    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    /**
     * Implementazione metodo di interrogazione
     * 
     * @param state: SdkTableState
     */
    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {

        this.logger.debug('SdkTableState >>>', state);

        let ob = this.getImprese(this.typedParams.searchForm, this.typedParams.stazioneAppaltante, state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.stazioneAppaltante)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaImprese): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getImprese(searchForm: RicercaAvanzataArchivioImpreseForm, stazioneAppaltante: string, state: SdkTableState): Observable<ResponseListaRupPaginata> {
        let params: ListaArchivioImpreseParams = {
            ...searchForm,
        };
        if(isEmpty(searchForm.stazioneAppaltante)){
            params = {
                ...searchForm,
                stazioneAppaltante
            };
        }
        
        let getListaTecnici = this.getImpreseFactory(params, state);
        return this.requestHelper.begin(getListaTecnici, this.typedParams.messagesPanel);
    }

    private getImpreseFactory(params: ListaArchivioImpreseParams, state: SdkTableState) {
        return () => {
            return this.archivioImpreseService.getListaImprese(this.typedParams.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, params, state);
        }
    }

    private adaptServerDataItem = (item: ImpresaBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: ImpresaBaseEntry): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        searchForm: RicercaAvanzataArchivioImpreseForm,
        stazioneAppaltante: string,
        messagesPanel: HTMLElement,
        appConfig: SdkAppEnvConfig
    } {
        return this.params as {
            searchForm: RicercaAvanzataArchivioImpreseForm,
            stazioneAppaltante: string,
            messagesPanel: HTMLElement,
            appConfig: SdkAppEnvConfig
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    // #endregion

}