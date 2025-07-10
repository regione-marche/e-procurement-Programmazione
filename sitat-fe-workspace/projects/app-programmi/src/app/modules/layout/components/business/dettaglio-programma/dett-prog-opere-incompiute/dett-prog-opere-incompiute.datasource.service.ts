import { Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { OperaIncompiutaBaseEntry, ResponseListaOpereIncompiute } from '../../../../../models/opere/opere-incompiute.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


export class DettaglioProgrammaOpereIncompiuteDatasource extends SdkTableDatasource {

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

        let ob = this.getListaOpereIncompiute(state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.idProgramma)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaOpereIncompiute): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getListaOpereIncompiute(state: SdkTableState): Observable<ResponseListaOpereIncompiute> {
        let factory = this.getListaOpereIncompiuteFactory(this.typedParams.idProgramma, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaOpereIncompiuteFactory(idProgramma: string, state: SdkTableState) {
        return () => {
            return this.programmiService.getListaOpereIncompiute(idProgramma, state);
        }
    }

    private adaptServerDataItem = (item: OperaIncompiutaBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: OperaIncompiutaBaseEntry): any => {
        // let tipoProgramma: ValoreTabellato = find(this.typedParams.tipiProgramma, (one: ValoreTabellato) => one.codice === toString(item.tipoProg));
        return {
            ...item
            // ,
            // descrizioneTipoProgramma: isObject(tipoProgramma) ? tipoProgramma.descrizione : item.tipoProg
        };
    }

    private get typedParams(): {
        idProgramma: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            idProgramma: string,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion

}