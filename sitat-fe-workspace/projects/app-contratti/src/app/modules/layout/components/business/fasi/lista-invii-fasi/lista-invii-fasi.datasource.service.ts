import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { find, get, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { GareService } from '../../../../../../modules/services/gare/gare.service';
import { FullFlussiFase, ResponseListaInviiFasi } from '../../../../../models/gare/gare.model';


export class ListaInviiFasiDatasource extends SdkTableDatasource {

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

        let ob = this.getListaInviiFasi(state); // no cache

        // let ob = this.cacheService.cacheObservable({ _: 'products_test_1_datasource', state }, () => {
        //     return this.inj.get(HttpClient).get(this.endpoint(state))
        // }) // con cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && this.typedParams.codGara != null && this.typedParams.codLotto != null;
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     * Il parametro aggiuntivo moreInfoGara (in questo caso) corrisponde al booleano isPcp per capire se la gara è simog o PCP.
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaInviiFasi): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, item => this.adaptServerDataItem(item, res.moreInfoGara)),
                total: res.totalCount,
                gridState: state,
                moreInfoGara: res.moreInfoGara
            };
            return result;
        }
    }

    private getListaInviiFasi(state: SdkTableState): Observable<ResponseListaInviiFasi> {
        let factory = this.getListaInviiFasiFactory(this.typedParams.codGara, this.typedParams.codLotto, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaInviiFasiFactory(codGara: string, codLotto: string, state: SdkTableState) {
        return () => {
            return this.gareService.getListaInviiFasi(codGara, codLotto, state);
        }
    }

    private adaptServerDataItem = (item: FullFlussiFase, moreInfoGara: any): SdkTableRowDto => this.adaptDataItem(item, moreInfoGara)

    private adaptDataItem = (item: FullFlussiFase, moreInfoGara: any): any => {
        const listaValori: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'Fase');
        const valoreTabellato: ValoreTabellato = find(listaValori, (one: ValoreTabellato) => one.codice === toString(item.numFase));
        return {
            ...item,
            faseEvento: valoreTabellato.descrizione,
            tipoInvioString: item.tipoInvio === '1' ? (
                moreInfoGara === true ?
                    this.translateService.instant(`TIPO-INVIO.${item.tipoInvio}.PCP`) 
                    :
                    this.translateService.instant(`TIPO-INVIO.${item.tipoInvio}.SIMOG`)
                )
            :
            this.translateService.instant(`TIPO-INVIO.${item.tipoInvio}`)
        };
    }

    private get typedParams(): {
        messagesPanel: HTMLElement,
        codGara: string,
        codLotto: string,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>
    } {
        return this.params as {
            messagesPanel: HTMLElement,
            codGara: string,
            codLotto: string,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}