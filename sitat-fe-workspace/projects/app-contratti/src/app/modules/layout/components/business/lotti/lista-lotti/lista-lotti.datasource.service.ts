import { Injector } from '@angular/core';
import { HttpRequestHelper, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary, SdkHttpLoaderService, SdkHttpLoaderType } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { find, get, isEmpty, isObject, map as mapArray, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { LottoBaseEntry, ResponseListaLotti } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';


export class ListaLottiDatasource extends SdkTableDatasource {

    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    /**
     * Implementazione metodo di interrogazione
     * 
     * @param state: SdkTableState
     */
    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        this.logger.debug('SdkTableState >>>', state);

        let ob = this.getListaInterventi(state); // no cache

        return ob.pipe(map(this.adaptServerResults(state)))
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server 
     * 
     * @param state: SdkTableState 
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.codGara)
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaLotti): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            this.sdkHttpLoaderService.hideLoader();
            return result;
        }
    }

    private getListaInterventi(state: SdkTableState): Observable<ResponseListaLotti> {
        let factory = this.getListaLottiFactory(this.typedParams.codGara, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel, false);
    }

    private getListaLottiFactory(codGara: string, state: SdkTableState) {
        return () => {
            return this.gareService.getListaLotti(codGara, state);
        }
    }

    private adaptServerDataItem = (item: LottoBaseEntry): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: LottoBaseEntry): any => {
        let situazioneCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'Situazione');
        let situazioneItem: ValoreTabellato = find(situazioneCombo, (one: ValoreTabellato) => one.codice === toString(item.situazione));
        let tipoAppaltoCombo: Array<ValoreTabellato> = get(this.typedParams.valoriTabellati, 'TipoAppalto');
        let tipoAppaltoItem: ValoreTabellato = find(tipoAppaltoCombo, (one: ValoreTabellato) => one.codice === item.tipologia);
        return {
            ...item,
            situazione: situazioneItem ? situazioneItem.descrizione : item.situazione,
            tipologia: tipoAppaltoItem ? tipoAppaltoItem.descrizione : item.tipologia,
            cig: {
                value: item.cig,
                children: mapArray(item.lottiAccorpati, (one: LottoBaseEntry) => {
                    return {
                        ...one,
                        value: one.cig,
                        _key: one.cig
                    };
                })
            }
        };
    }

    private get typedParams(): {
        codGara: string,
        valoriTabellati: IDictionary<Array<ValoreTabellato>>,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            codGara: string,
            valoriTabellati: IDictionary<Array<ValoreTabellato>>,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }


    // #endregion

}