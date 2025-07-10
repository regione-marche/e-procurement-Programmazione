import { Injector } from '@angular/core';
import { HttpRequestHelper, TabellatiUtils } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isEmpty, isObject, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { InterventoBaseEntry, ResponseListaInterventi } from '../../../../../models/interventi/interventi.model';
import { ListaInterventiFilter, ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


export class DettaglioProgrammaInterventiDatasource extends SdkTableDatasource {

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

        let ob = this.getListaInterventi(state); // no cache

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
        return (res: ResponseListaInterventi): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getListaInterventi(state: SdkTableState): Observable<ResponseListaInterventi> {
        let factory = this.getListaInterventiFactory(this.typedParams.idProgramma, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaInterventiFactory(idProgramma: string, state: SdkTableState) {
        return () => {
            let filter: ListaInterventiFilter = this.calculateFilter(state);
            return this.programmiService.getListaInterventi(idProgramma, state, filter);
        }
    }

    private adaptServerDataItem = (item: InterventoBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: InterventoBaseEntry): any => {
        let annoInizio: number = this.typedParams.programma.annoInizio;
        let tipologia: string = this.typedParams.tipologia;
        let tipoNorma: number = this.typedParams.programma.norma;
        let list: Array<SdkComboBoxItem> = this.tabellatiUtils.getAnnoInizioCombo(annoInizio, tipologia, tipoNorma);
        let comboItem: SdkComboBoxItem = find(list, (one: SdkComboBoxItem) => one.key === toString(item.annoAvvio));
        return {
            ...item,
            descrizioneAnnualita: comboItem ? comboItem.value : item.annoAvvio
        };
    }

    private calculateFilter(state: SdkTableState): ListaInterventiFilter {
        let filter: ListaInterventiFilter = null;
        if(state.filter != null) {
            filter = {
                annualita: this.getFilterField(state, 'annualita'),
                codInterno: this.getFilterField(state, 'codInterno'),
                codiceCUP: this.getFilterField(state, 'codiceCUP'),
                descrizione: this.getFilterField(state, 'descrizione'),
                importoTotaleDa: this.getFilterField(state, 'importoTotaleDa'),
                importoTotaleA: this.getFilterField(state, 'importoTotaleA'),
                numeroCui: this.getFilterField(state, 'numeroCui'),
                rup: this.getFilterField(state, 'rup')
            };
        }
        this.typedParams.filterOut.next(filter);
        return filter;
    }

    private getFilterField(state: SdkTableState, filterCode: string): any {
        if (state != null && state.filter != null && filterCode) {
            let filterValue: any = get(state.filter, filterCode) != null ? get(state.filter, filterCode) : null;
            return filterValue;
        }
        return null;
    }

    private get typedParams(): {
        idProgramma: string,
        tipologia: string,
        programma: ProgrammaEntry,
        messagesPanel: HTMLElement,
        filterOut: BehaviorSubject<ListaInterventiFilter>
    } {
        return this.params as {
            idProgramma: string,
            tipologia: string,
            programma: ProgrammaEntry,
            messagesPanel: HTMLElement,
            filterOut: BehaviorSubject<ListaInterventiFilter>
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get tabellatiUtils(): TabellatiUtils { return this.injectable(TabellatiUtils) }

    // #endregion

}