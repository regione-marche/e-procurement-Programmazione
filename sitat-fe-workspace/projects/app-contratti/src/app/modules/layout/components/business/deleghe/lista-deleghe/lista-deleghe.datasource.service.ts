import { ResponseListaDeleghe, DelegaBaseEntry, ListaDelegheParams } from "./../../../../../models/deleghe/deleghe.model";
import { Injector } from "@angular/core";
import { HttpRequestHelper, ValoreTabellato } from "@maggioli/app-commons";
import { IDictionary } from "@maggioli/sdk-commons";
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from "@maggioli/sdk-table";
import { find, get, isEmpty, isObject, map as mapArray, toString } from "lodash-es";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

import { DelegheService } from "../../../../../services/deleghe/deleghe.service";

export class ListaDelegheDatasource extends SdkTableDatasource {
    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    /**
     * Implementazione metodo di interrogazione
     *
     * @param state: SdkTableState
     */
    protected fetchDataItems(state: SdkTableState): Observable<SdkTableDataResult> {
        this.logger.debug("SdkTableState >>>", state);

        let ob = this.getListaDeleghe(state); // no cache
        return ob.pipe(map(this.adaptServerResults(state)));
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrodeleghe il server
     *
     * @param state: SdkTableState
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.cfrup);
    }

    /**
     * Metodo per l'adapting dei dati ritornati dal server
     */
    private adaptServerResults = (state: SdkTableState) => {
        return (res: ResponseListaDeleghe): SdkTableDataResult => {
            let result = {
                data: mapArray(res.data, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state,
            };
            return result;
        };
    };

    private getListaDeleghe(state: SdkTableState): Observable<ResponseListaDeleghe> {
        let factory = this.getListaDelegheFactory(this.typedParams.stazioneAppaltante, this.typedParams.cfrup, state);
        return this.requestHelper.begin(factory, this.typedParams.messagesPanel);
    }

    private getListaDelegheFactory(stazioneAppaltante: string, cfrup: string, state: SdkTableState) {
        return () => {
            let params: ListaDelegheParams = {
                stazioneAppaltante,
                cfrup
            };
            return this.delegheService.getListaDeleghe(params, state);
        };
    }

    private adaptServerDataItem = (item: DelegaBaseEntry): SdkTableRowDto => this.adaptDataItem(item);

    private adaptDataItem = (item: DelegaBaseEntry): any => {
        return {
            ...item,
        };
    };

    private get typedParams(): {
        stazioneAppaltante: string;
        cfrup: string;
        valoriTabellati: IDictionary<Array<ValoreTabellato>>;
        messagesPanel: HTMLElement;
    } {
        return this.params as {
            stazioneAppaltante: string;
            cfrup: string;
            valoriTabellati: IDictionary<Array<ValoreTabellato>>;
            messagesPanel: HTMLElement;
        };
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get delegheService(): DelegheService {
        return this.injectable(DelegheService);
    }

    // #endregion
}
