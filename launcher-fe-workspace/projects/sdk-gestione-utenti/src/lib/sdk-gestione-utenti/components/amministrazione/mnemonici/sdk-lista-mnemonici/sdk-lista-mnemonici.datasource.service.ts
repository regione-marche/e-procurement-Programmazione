import { Injector } from '@angular/core';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isObject, join, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { C0CampiDTO, ResponseListaDTO } from '../../../../model/amministrazione.model';
import { GestioneMnemoniciService } from '../../../../services/gestione-mnemonici.service';


export class SdkListaMnemoniciDatasource extends SdkTableDatasource {

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

        let ob = this.getMnemonici(this.typedParams.searchData, state); // no cache

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
        return (res: ResponseListaDTO<Array<C0CampiDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getMnemonici(searchData: string, state: SdkTableState): Observable<ResponseListaDTO<Array<C0CampiDTO>>> {
        return this.gestioneMnemoniciService.getListaMnemonici(searchData, state);
    }

    private adaptServerDataItem = (item: C0CampiDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: C0CampiDTO): any => {
        return {
            ...item,
            coc_mne_uni_entita: this.getEntita(item),
            coc_mne_uni_campo: this.getCampo(item)
        };
    }

    private get typedParams(): {
        searchData: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            searchData: string,
            messagesPanel: HTMLElement
        }
    }

    private getEntita(item: C0CampiDTO): string {
        if (item.coc_mne_uni != null) {
            let parts: Array<string> = item.coc_mne_uni.split('.');
            if (parts != null && parts.length == 3) {
                return join([parts[1], parts[2]], '.');
            }
        }

        return null;
    }

    private getCampo(item: C0CampiDTO): string {
        if (item.coc_mne_uni != null) {
            let parts: Array<string> = item.coc_mne_uni.split('.');
            if (parts != null && parts.length == 3) {
                return parts[0];
            }
        }

        return null;
    }

    // #region Getters

    private get gestioneMnemoniciService(): GestioneMnemoniciService { return this.injectable(GestioneMnemoniciService) }

    // #endregion

}