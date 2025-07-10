import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { isObject, map as mapArray, size } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { GConfCodDTO } from '../../../../model/amministrazione.model';
import { ResponseDTO } from '../../../../model/lib.model';
import { GestioneCodificaAutomaticaService } from '../../../../services/gestione-codifica-automatica.service';


export class SdkListaCodificaAutomaticaDatasource extends SdkTableDatasource {

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

        let ob = this.getListaCodificaAutomatica(this.typedParams.codiceApplicazione); // no cache

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
        return (res: ResponseDTO<Array<GConfCodDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: size(res.response),
                gridState: state
            };
            return result;
        }
    }

    private getListaCodificaAutomatica(codiceApplicazione: string): Observable<ResponseDTO<Array<GConfCodDTO>>> {
        return this.gestioneCodificaAutomaticaService.getListaCodificaAutomatica(codiceApplicazione);
    }

    private adaptServerDataItem = (item: GConfCodDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: GConfCodDTO): any => {
        return {
            ...item,
            codificaAutomaticaAttiva: item.codCal != null && item.contat != null ? this.translateService.instant('COMBOBOX.SI') : this.translateService.instant('COMBOBOX.NO')
        };
    }

    private get typedParams(): {
        codiceApplicazione: string,
        messagesPanel: HTMLElement
    } {
        return this.params as {
            codiceApplicazione: string,
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get gestioneCodificaAutomaticaService(): GestioneCodificaAutomaticaService { return this.injectable(GestioneCodificaAutomaticaService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}