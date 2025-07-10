import { Injector } from '@angular/core';
import { IDictionary, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { RicercaAvanzataArchivioTecniciForm } from '../../../../models/archivio/archivio-tecnici.models';
import { ListaTecniciRequest, ResponseListaRupPaginata, RupEntry } from '../../../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiService } from '../../../../services/tabellati/tabellati.service';


export class ListaArchivioTecniciDatasource extends SdkTableDatasource {

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

        let ob = this.getTecnici(state); // no cache

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
        return (res: ResponseListaRupPaginata): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getTecnici(state: SdkTableState): Observable<ResponseListaRupPaginata> {
        let requestParams: ListaTecniciRequest = {
            searchString: this.typedParams.searchForm ? this.typedParams.searchForm.nominativo : null,
            codiceFiscale: this.typedParams.searchForm ? this.typedParams.searchForm.codiceFiscale : null,
            stazioneAppaltante: this.typedParams.searchForm && this.typedParams.searchForm.stazioneAppaltante ? this.typedParams.searchForm.stazioneAppaltante: this.typedParams.stazioneAppaltante
        }
        let getListaTecnici = this.getTecniciFactory(requestParams, state);
        return this.requestHelper.begin(getListaTecnici, this.typedParams.messagesPanel);
    }

    private getTecniciFactory(requestParams: ListaTecniciRequest, state: SdkTableState) {
        return () => {
            return this.tabellatiService.getListaTecnici(this.typedParams.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, requestParams, state);
        }
    }

    private adaptServerDataItem = (item: RupEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: RupEntry): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        searchForm: RicercaAvanzataArchivioTecniciForm,
        stazioneAppaltante: string,
        messagesPanel: HTMLElement,
        appConfig: SdkAppEnvConfig
    } {
        return this.params as {
            searchForm: RicercaAvanzataArchivioTecniciForm,
            stazioneAppaltante: string,
            messagesPanel: HTMLElement,
            appConfig: SdkAppEnvConfig
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    // #endregion

}