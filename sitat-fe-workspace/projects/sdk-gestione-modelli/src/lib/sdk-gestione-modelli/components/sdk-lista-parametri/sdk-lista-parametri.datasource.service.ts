import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ResponseListaUtentiDTO, RicercaModelliForm, UserDTO } from '../../model/gestione-utenti.model';
import { GestioneModelliService } from '../../services/gestione-modelli.service';
import { ValoreTabellato } from '../../model/lib.model';


export class SdkListaParametriDatasource extends SdkTableDatasource {

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

        let ob = this.getModelli(this.typedParams.idModello, state); // no cache

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
        return (res: any): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getModelli(idModello: string, state: SdkTableState): Observable<ResponseListaUtentiDTO<Array<UserDTO>>> {
        return this.gestioneModelliService.getListaParametri(idModello, state);
    }

    private adaptServerDataItem = (item): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item): any => {   
        let obblDesc = item.obbl == null ? '' : item.obbl.toString() === '1' ? 'SI' : 'NO';
        let tipoCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'TipoParametroModello');   
        let tipoItem: ValoreTabellato = find(tipoCombo, (one: ValoreTabellato) => one.codice.trim() === item.tipo.trim());
        return {
            ...item,
            obblDesc,
            tipoDesc: tipoItem ? tipoItem.descrizione : item.tip,
        };
    }

    private get typedParams(): {
        idModello: string,
        messagesPanel: HTMLElement,
        valoriTabellati: any;
    } {
        return this.params as {
            idModello: string,
            messagesPanel: HTMLElement,
            valoriTabellati: any
        }
    }

    // #region Getters

    private get gestioneModelliService(): GestioneModelliService { return this.injectable(GestioneModelliService) }

    // #endregion

}