import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, StazioneAppaltanteInfo, TabellatiService, Ufficio } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each, get } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class UfficiProgrammaProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        let stazioneAppaltante: StazioneAppaltanteInfo = get(args, 'stazioneAppaltante');
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getListaUfficiFactory(stazioneAppaltante, data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getListaUfficiFactory(stazioneAppaltante: StazioneAppaltanteInfo, data?: string): () => Observable<any> {
        return () => {
            return this.tabellatiService.listaOpzioniUffici(environment.GESTIONE_TABELLATI_BASE_URL, stazioneAppaltante.codice, data)
                .pipe(
                    map((result: Array<Ufficio>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (tipo: Ufficio) => {
                            let item: SdkAutocompleteItem = {
                                ...tipo,
                                text: tipo.denominazione,
                                _key: tipo.id
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