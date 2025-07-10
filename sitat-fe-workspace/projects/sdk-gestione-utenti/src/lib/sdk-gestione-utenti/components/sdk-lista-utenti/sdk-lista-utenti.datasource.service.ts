import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ResponseListaUtentiDTO, RicercaUtentiFormDTOInternal, UserDTO } from '../../model/gestione-utenti.model';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';


export class SdkListaUtentiDatasource extends SdkTableDatasource {

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

        let ob = this.getUtenti(this.typedParams.searchForm, state); // no cache

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
        return (res: ResponseListaUtentiDTO<Array<UserDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getUtenti(searchForm: RicercaUtentiFormDTOInternal, state: SdkTableState): Observable<ResponseListaUtentiDTO<Array<UserDTO>>> {
        return this.gestioneUtentiService.getListaUtenti(searchForm, state);
    }

    private adaptServerDataItem = (item: UserDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: UserDTO): any => {
        return {
            ...item,
            abilitato: item.disabilitato === true ? this.translateService.instant('COMBOBOX.NO') : this.translateService.instant('COMBOBOX.SI')
        };
    }

    private get typedParams(): {
        searchForm: RicercaUtentiFormDTOInternal,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaUtentiFormDTOInternal,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}