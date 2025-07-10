import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ResponseListaDTO, RicercaEventiFormDTO, WLogEventiDTO } from '../../../../model/amministrazione.model';
import { ValoreTabellato } from '../../../../model/lib.model';
import { GestioneEventiService } from '../../../../services/gestione-eventi.service';


export class SdkListaEventiDatasource extends SdkTableDatasource {

    constructor(injector: Injector, params: IDictionary<any>, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(injector, params);
    }

    /**
     * Implementazione metodo di interrogazione
     * 
     * @param state: SdkTableState
     */
    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {

        this.logger.debug('SdkTableState >>>', state);

        let ob = this.getEventi(this.typedParams.searchForm, state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaDTO<Array<WLogEventiDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getEventi(searchForm: RicercaEventiFormDTO, state: SdkTableState): Observable<ResponseListaDTO<Array<WLogEventiDTO>>> {
        return this.gestioneEventiService.getListaEventi(searchForm, state);
    }

    private adaptServerDataItem = (item: WLogEventiDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: WLogEventiDTO): any => {
        let livelloEventoCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'LivelloEvento');
        let livelloEventoItem: ValoreTabellato = find(livelloEventoCombo, (one: ValoreTabellato) => one.codice === toString(item.livelloEvento));
        return {
            ...item,
            livelloEvento: livelloEventoItem != null ? livelloEventoItem.descrizione : item.livelloEvento
        };
    }

    private get typedParams(): {
        searchForm: RicercaEventiFormDTO,
        messagesPanel: HTMLElement,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>
    } {
        return this.params as {
            searchForm: RicercaEventiFormDTO,
            messagesPanel: HTMLElement,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>
        }
    }

    // #region Getters

    private get gestioneEventiService(): GestioneEventiService { return this.injectable(GestioneEventiService) }

    // #endregion

}