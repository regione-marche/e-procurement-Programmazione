import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { find, get, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ResponseListaUtentiDTO, RicercaModelliForm, UserDTO } from '../../model/gestione-utenti.model';
import { GestioneModelliService } from '../../services/gestione-modelli.service';
import { ValoreTabellato } from '../../model/lib.model';


export class SdkListaModelliDatasource extends SdkTableDatasource {

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

        let ob = this.getModelli(this.typedParams.searchForm, state); // no cache

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

    private getModelli(searchForm: RicercaModelliForm, state: SdkTableState): Observable<ResponseListaUtentiDTO<Array<UserDTO>>> {
        return this.gestioneModelliService.getListaModelli(searchForm, state);
    }

    private adaptServerDataItem = (item): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item): any => {
 

        let tipoDocumentoCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'TipoDocumentoModello');
        let tipoDocumentoItem: ValoreTabellato = find(tipoDocumentoCombo, (one: ValoreTabellato) => one.codice.trim() === item.tipo.trim());
        let personaleDesc = item.personale == null ? '' : item.personale.toString() === '1' ? 'SI' : 'NO';
        let dispDesc = item.disp == null ? '' :item.disp.toString() === '1' ? 'SI' : 'NO';
        let owner = this.typedParams.utentiOptions.find((o: {sysCon: number, sysUte: string})=> o.sysCon == item.owner).sysUte;

        return {
            ...item,
            tipoDocumentoDesc: tipoDocumentoItem ? tipoDocumentoItem.descrizione : item.tip,
            personaleDesc:personaleDesc,
            dispDesc:dispDesc,
            owner:owner
        };
    }

    private get typedParams(): {
        searchForm: RicercaModelliForm,
        messagesPanel: HTMLElement,
        valoriTabellati: any,
        utentiOptions?: Array<{sysCon: number, sysUte: string}>

    } {
        return this.params as {
            searchForm: RicercaModelliForm,
            messagesPanel: HTMLElement,
            valoriTabellati: any,
            utentiOptions?: Array<{sysCon: number, sysUte: string}>

        }
    }

    // #region Getters

    private get gestioneModelliService(): GestioneModelliService { return this.injectable(GestioneModelliService) }

    // #endregion

}