import { Inject, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig } from '@maggioli/sdk-commons';
import { SdkTableDataResult, SdkTableDatasource, SdkTableRowDto, SdkTableState } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { isObject, map as mapArray } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ListaDettaglioTabellatoFormDTO, ResponseListaDTO, TabXDTO } from '../../../../model/amministrazione.model';
import { GestioneTabellatiService } from '../../../../services/gestione-tabellati.service';


export class SdkListaDettaglioTabellatoDatasource extends SdkTableDatasource {

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

        let ob = this.getDettaglioTabellato(state); // no cache

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
        return (res: ResponseListaDTO<Array<TabXDTO>>): SdkTableDataResult => {
            let result = {
                data: mapArray(res.response, this.adaptServerDataItem),
                total: res.totalCount,
                gridState: state
            };
            return result;
        }
    }

    private getDettaglioTabellato(state: SdkTableState): Observable<ResponseListaDTO<Array<TabXDTO>>> {
        const request: ListaDettaglioTabellatoFormDTO = {
            skip: state.skip,
            take: state.take,
            provenienza: this.typedParams.provenienza,
            codiceTabellato: this.typedParams.codiceTabellato
        };
        return this.gestioneTabellatiService.getListaDettaglioTabellato(request);
    }

    private adaptServerDataItem = (item: TabXDTO): SdkTableRowDto => this.adaptDataItem(item)

    private adaptDataItem = (item: TabXDTO): any => {
        return {
            ...item,
            bloccato: item.bloccato === '1' ? this.translateService.instant('COMBOBOX.SI') : this.translateService.instant('COMBOBOX.NO'),
            archiviato: item.archiviato === '1' ? this.translateService.instant('COMBOBOX.SI') : this.translateService.instant('COMBOBOX.NO')
        };
    }

    private get typedParams(): {
        provenienza: string,
        codiceTabellato: string
        messagesPanel: HTMLElement
    } {
        return this.params as {
            provenienza: string,
            codiceTabellato: string
            messagesPanel: HTMLElement
        }
    }

    // #region Getters

    private get gestioneTabellatiService(): GestioneTabellatiService { return this.injectable(GestioneTabellatiService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}