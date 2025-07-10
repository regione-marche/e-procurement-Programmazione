import { Injector } from "@angular/core";
import { IDictionary } from "@maggioli/sdk-commons";
import { SdkTableDatasource, SdkTableState } from "@maggioli/sdk-table";
import { get, isEmpty, isObject,  } from "lodash-es";
import { HttpRequestHelper } from "../../services/http/http-request-helper.service";
import { ComboDto } from "../../model/sdk-base.model";


export abstract class SdkBaseListDatasource extends SdkTableDatasource {
    constructor(injector: Injector, params: IDictionary<any>) {
        super(injector, params);
    }

    /**
     * Metodo che verifica se ci sono le condizioni per poter interrogare il server
     *
     * @param state: SdkTableState
     */
    protected isReadyForFetch(_state: SdkTableState): boolean {
        return isObject(this.typedParams) && !isEmpty(this.typedParams.codein);
    }

    protected get typedParams(): {
        codein: string;
        idProfilo: string;
        valoriTabellati: IDictionary<Array<ComboDto>>;
        messagesPanel: HTMLElement;
        id: number;
    } {
        return this.params as {
            codein: string;
            idProfilo: string;
            valoriTabellati: IDictionary<Array<ComboDto>>;
            messagesPanel: HTMLElement;
            id: number;
        };
    }

    protected getFilterField(state: SdkTableState, filterCode: string): any {
        if (state != null && state.filter != null && filterCode) {
            let filterValue: any = get(state.filter, filterCode) != null ? get(state.filter, filterCode) : null;
            return filterValue;
        }
        return null;
    }

    protected applyFilterFields(state: SdkTableState, params: any): any {
        if (state != null && state.filter != null) {
            //TODO
        }
        return null;
    }

    // #region Getters

    protected get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }



    // #endregion
}
