import { Injectable, Injector } from '@angular/core';
import { CentroDiCostoEntry, HttpRequestHelper, StazioneAppaltanteInfo, TabellatiService } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each, get, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class CdcAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        let stazioneAppaltante: StazioneAppaltanteInfo = get(args, 'stazioneAppaltante');
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getListaCdcFactory(stazioneAppaltante, data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getListaCdcFactory(stazioneAppaltante: StazioneAppaltanteInfo, data?: string): () => Observable<any> {
        return () => {
            return this.tabellatiService.listaOpzioniCentriDiCosto(environment.GESTIONE_TABELLATI_BASE_URL, stazioneAppaltante.codice, data)
                .pipe(
                    map((result: Array<CentroDiCostoEntry>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (tipo: CentroDiCostoEntry) => {
                            let item: SdkAutocompleteItem = {
                                ...tipo,
                                text: tipo.denominazione,
                                _key: toString(tipo.id)
                            };
                            arr.push(item);
                        });
                        return arr;
                    })
                );
        }
    }

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}