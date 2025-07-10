import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { isEmpty, isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { FullDettaglioAttoEntry, ResponseAttiAssociatiLotto } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';


export class ListaAttiLottoDatasource extends SdkTableDatasource {

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

        const ob = this.getListaAttiLotto(); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.codGara) && !isEmpty(this.typedParams.codLotto)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseAttiAssociatiLotto): SdkTableDataResult => {
            const size = res.data ? res.data.length : 0;
            const result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: size,
                gridState: state
            };
            return result;
        }
    }

    private getListaAttiLotto(state?: SdkTableState): Observable<ResponseAttiAssociatiLotto> {
        let factory = this.getListaLottiFactory(this.typedParams.codGara, this.typedParams.codLotto);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaLottiFactory(codGara: string, codLotto: string) {
        return () => {
            return this.gareService.getListaAttiLotto(codGara, codLotto);
        }
    }

    private adaptServerDataItem = (item: FullDettaglioAttoEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: FullDettaglioAttoEntry): any => {
        return item;
    }

    private get typedParams(): {
        codGara: string,
        codLotto: string,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            codGara: string,
            codLotto: string,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get gareService(): GareService {
        return this.injectable(GareService);
    }

    // #endregion

}