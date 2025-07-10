import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isObject, map as mapArray, size } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { WQuartzDTO } from '../../../../model/amministrazione.model';
import { ResponseDTO } from '../../../../model/lib.model';
import { GestionePianificazioniService } from '../../../../services/gestione-pianificazioni.service';


export class SdkListaPianificazioniDatasource extends SdkTableDatasource {

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

        let ob = this.getPianificazioni(); // no cache

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
        return (res: ResponseDTO<Array<WQuartzDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: size(res.response),
                gridState: state
            };
            return result;
        }
    }

    private getPianificazioni(): Observable<ResponseDTO<Array<WQuartzDTO>>> {
        return this.gestionePianificazioniService.getListaPianificazioni();
    }

    private adaptServerDataItem = (item: WQuartzDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: WQuartzDTO): any => {
        return {
            ...item
        };
    }

    private get typedParams(): {
        messagesPanel: HTMLElement
    } {
        return this.params as {
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get gestionePianificazioniService(): GestionePianificazioniService { return this.injectable(GestionePianificazioniService) }

    // #endregion

}