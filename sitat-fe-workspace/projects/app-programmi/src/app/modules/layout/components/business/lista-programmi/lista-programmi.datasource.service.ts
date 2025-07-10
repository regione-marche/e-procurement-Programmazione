import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    ListaProgrammiRequest,
    ProgrammaBaseEntry,
    ResponseListaProgrammi,
} from '../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../services/programmi/programmi.service';


export class ListaProgrammiDatasource extends SdkTableDatasource {

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

        let ob = this.getProgrammi(state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.stazioneAppaltante) && !isEmpty(this.typedParams.syscon)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaProgrammi): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getProgrammi(state: SdkTableState): Observable<ResponseListaProgrammi> {
        let requestParams: ListaProgrammiRequest = {
            searchString: this.typedParams.searchString,
            tipologia: this.typedParams.tipologia,
            stazioneAppaltante: this.typedParams.stazioneAppaltante,
            syscon: this.typedParams.syscon
        }
        let getListaProgrammi = this.getProgrammiFactory(requestParams, state);
        return this.requestHelper.begin(getListaProgrammi, this.typedParams.messagesPanel);
    }

    private getProgrammiFactory(requestParams: ListaProgrammiRequest, state: SdkTableState) {
        return () => {
            return this.programmiService.getListaProgrammi(requestParams, state);
        }
    }

    private adaptServerDataItem = (item: ProgrammaBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: ProgrammaBaseEntry): any => {
        return {
            ...item,
            descrizioneTipoProgramma: item.tipoProg === 1 ? this.translateService.instant('PROGRAMMA-LAVORI') : this.translateService.instant('PROGRAMMA-FORNITURE-SERVIZI')
        };
    }

    private get typedParams(): {
        searchString: string,
        tipologia: string,
        stazioneAppaltante: string,
        tipiProgramma: Array<ValoreTabellato>,
        syscon: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchString: string,
            tipologia: string,
            stazioneAppaltante: string,
            tipiProgramma: Array<ValoreTabellato>,
            syscon: string,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}