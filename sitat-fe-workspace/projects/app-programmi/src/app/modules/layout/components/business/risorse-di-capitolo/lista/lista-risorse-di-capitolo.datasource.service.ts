import { Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray, sum } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    ResponseListaRisorsaCapitolo,
    RisorsaCapitoloBaseEntry,
} from '../../../../../models/risorse-capitolo/risorsa-capitolo.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


export class ListaRisorseDiCapitoloDatasource extends SdkTableDatasource {

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

        let ob = this.getListRisorseDiCapitolo(state); // no cache

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
        return (res: ResponseListaRisorsaCapitolo): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getListRisorseDiCapitolo(state: SdkTableState): Observable<ResponseListaRisorsaCapitolo> {
        let factory = this.getListaRisorseDiCapitoloFactory(this.typedParams.idProgramma, this.typedParams.idIntervento, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaRisorseDiCapitoloFactory(idProgramma: string, idIntervento: string, state: SdkTableState) {
        return () => {
            return this.programmiService.getListaRisorseDiCapitolo(idProgramma, idIntervento, state);
        }
    }

    private adaptServerDataItem = (item: RisorsaCapitoloBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: RisorsaCapitoloBaseEntry): any => {
        let importoComplessivo: number = sum([item.importoComplessivo1, item.importoComplessivo2, item.importoComplessivo3, item.importoComplessivoSucc]);
        return {
            ...item,
            importoComplessivo
        };
    }

    private get typedParams(): {
        idProgramma: string,
        idIntervento: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            idProgramma: string,
            idIntervento: string,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    // #endregion

}