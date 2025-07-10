import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import {
    ResponseListaDTO,
    RicercaConfigurazioniFormDTO,
    WConfigDTO,
} from '../../../../model/amministrazione.model';
import { GestioneConfigurazioniService } from '../../../../services/gestione-configurazioni.service';


export class SdkListaConfigurazioniDatasource extends SdkTableDatasource {

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

        let ob = this.getConfigurazioni(this.typedParams.searchForm, state); // no cache

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
        return (res: ResponseListaDTO<Array<WConfigDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getConfigurazioni(searchForm: RicercaConfigurazioniFormDTO, state: SdkTableState): Observable<ResponseListaDTO<Array<WConfigDTO>>> {
        return this.gestioneConfigurazioniService.getListaConfigurazioni(searchForm, state);
    }

    private adaptServerDataItem = (item: WConfigDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: WConfigDTO): any => {
        return {
            ...item,
            criptato: item.criptato === '1' ? this.translateService.instant('COMBOBOX.SI') : this.translateService.instant('COMBOBOX.NO')
        };
    }

    private get typedParams(): {
        searchForm: RicercaConfigurazioniFormDTO,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaConfigurazioniFormDTO,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get gestioneConfigurazioniService(): GestioneConfigurazioniService { return this.injectable(GestioneConfigurazioniService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}