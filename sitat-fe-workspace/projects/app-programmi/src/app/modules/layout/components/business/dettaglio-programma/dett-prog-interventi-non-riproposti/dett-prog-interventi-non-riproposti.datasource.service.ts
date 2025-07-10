import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isEmpty, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    InterventoNonRipropostoEntry,
    ResponseListaInterventiNR,
} from '../../../../../models/interventi/interventi-non-riproposti.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


export class DettaglioProgrammaInterventiNonRipropostiDatasource extends SdkTableDatasource {

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

        let ob = this.getListaInterventiNonRiproposti(state); // no cache

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
        return (res: ResponseListaInterventiNR): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getListaInterventiNonRiproposti(state: SdkTableState): Observable<ResponseListaInterventiNR> {
        let factory = this.getListaInterventiNonRipropostiFactory(this.typedParams.idProgramma, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaInterventiNonRipropostiFactory(idProgramma: string, state: SdkTableState) {
        return () => {
            return this.programmiService.getListaInterventiNonRiproposti(idProgramma, state);
        }
    }

    private adaptServerDataItem = (item: InterventoNonRipropostoEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: InterventoNonRipropostoEntry): any => {
        let descrizionePriorita: ValoreTabellato = find(get(this.typedParams.valoriTabellati, 'Priorita'), (one: ValoreTabellato) => one.codice === toString(item.priorita));
        return {
            ...item,
            descrizionePriorita: descrizionePriorita != null ? descrizionePriorita.descrizione : item.priorita
        };
    }

    private get typedParams(): {
        idProgramma: string,
        messagesPanel: HTMLElement,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>
    } {
        return this.params as {
            idProgramma: string,
            messagesPanel: HTMLElement,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion

}