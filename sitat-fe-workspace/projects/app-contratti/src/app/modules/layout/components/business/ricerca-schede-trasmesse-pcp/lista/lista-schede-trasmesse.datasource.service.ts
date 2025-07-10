import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ListaRicercaSchedeTrasmessePcpParams, ResponseSchedeTrasmessePcp, RicercaSchedePcp, RicercaSchedePcpEntry } from 'projects/app-contratti/src/app/modules/models/schede-trasmesse-pcp/schede-trasmesse-pcp.model';
import { SchedeTrasmessePcpService } from 'projects/app-contratti/src/app/modules/services/schede-trasmesse-pcp/schede-trasmesse-pcp.service';


export class ListaSchedeTrasmessePcpDatasource extends SdkTableDatasource {

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

        let ob = this.getSchedeTrasmessePcp(state); //no cache

        // con cache
        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && isObject(this.typedParams.searchForm)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseSchedeTrasmessePcp): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getSchedeTrasmessePcp(state: SdkTableState): Observable<ResponseSchedeTrasmessePcp> {
        let getListaSchedeTrasmessePcp = this.getListaSchedeTrasmessePcpFactory(this.typedParams.searchForm, this.typedParams.stazioneAppaltante, this.typedParams.syscon, state);
        return this.requestHelper.begin(getListaSchedeTrasmessePcp, this.typedParams.messagesPanel);
    }

    private getListaSchedeTrasmessePcpFactory(searchForm: RicercaSchedePcp, stazioneAppaltante: string, syscon: string, state: SdkTableState) {
        return () => {
            let params: ListaRicercaSchedeTrasmessePcpParams = {
                ...searchForm,
                stazioneAppaltante,
                syscon: +syscon
            }
            return this.schedeTrasmessePcpService.getListaSchedeTrasmessePcp(params, state);
        }
    }

    private adaptServerDataItem = (item: RicercaSchedePcpEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: RicercaSchedePcpEntry): any => {

        let faseEsecuzioneCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'Fase');
        let faseEsecuzioneItem: ValoreTabellato = find(faseEsecuzioneCombo, (one: ValoreTabellato) => one.codice === toString(item.faseEsecuzione));
        return {
            ...item,
            faseEsecuzione: faseEsecuzioneItem ? faseEsecuzioneItem.descrizione : item.faseEsecuzione,
        };
    }

    private get typedParams(): {
        searchForm: RicercaSchedePcp,
        stazioneAppaltante: string,
        syscon: string,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaSchedePcp,
            stazioneAppaltante: string,
            syscon: string,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get schedeTrasmessePcpService(): SchedeTrasmessePcpService { return this.injectable(SchedeTrasmessePcpService) }

    // #endregion

}