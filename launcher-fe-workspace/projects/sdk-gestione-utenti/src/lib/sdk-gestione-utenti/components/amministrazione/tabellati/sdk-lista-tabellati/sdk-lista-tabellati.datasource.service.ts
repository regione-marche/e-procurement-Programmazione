import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ResponseListaDTO, RicercaTabellatiFormDTO, VTab4Tab6DTO } from '../../../../model/amministrazione.model';
import { GestioneTabellatiService } from '../../../../services/gestione-tabellati.service';


export class SdkListaTabellatiDatasource extends SdkTableDatasource {

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

        let ob = this.getTabellati(this.typedParams.searchForm, state); // no cache

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
        return (res: ResponseListaDTO<Array<VTab4Tab6DTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getTabellati(searchForm: RicercaTabellatiFormDTO, state: SdkTableState): Observable<ResponseListaDTO<Array<VTab4Tab6DTO>>> {
        return this.gestioneTabellatiService.getListaTabellati(searchForm, state);
    }

    private adaptServerDataItem = (item: VTab4Tab6DTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: VTab4Tab6DTO): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        searchForm: RicercaTabellatiFormDTO,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchForm: RicercaTabellatiFormDTO,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get gestioneTabellatiService(): GestioneTabellatiService { return this.injectable(GestioneTabellatiService) }

    // #endregion

}