import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isObject, map as mapArray, size } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { WMailDTO } from '../../../../model/amministrazione.model';
import { ResponseDTO } from '../../../../model/lib.model';
import { GestioneServerPostaService } from '../../../../services/gestione-server-posta.service';


export class SdkListaServerPostaDatasource extends SdkTableDatasource {

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

        let ob = this.getServerPosta(); // no cache

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
        return (res: ResponseDTO<Array<WMailDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: size(res.response),
                gridState: state
            };
            return result;
        }
    }

    private getServerPosta(): Observable<ResponseDTO<Array<WMailDTO>>> {
        return this.gestioneServerPostaService.getListaServerPosta();
    }

    private adaptServerDataItem = (item: WMailDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: WMailDTO): any => {
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

    private get gestioneServerPostaService(): GestioneServerPostaService { return this.injectable(GestioneServerPostaService) }

    // #endregion

}