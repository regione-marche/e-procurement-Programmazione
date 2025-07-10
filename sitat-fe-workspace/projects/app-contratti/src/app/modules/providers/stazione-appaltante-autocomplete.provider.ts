import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, SAEntry, TabellatiService } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class StazioneAppaltanteAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(_args: any): Function {
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getListaOpzioniEnteFactory(data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getListaOpzioniEnteFactory(data?: string): () => Observable<any> {
        return () => {
            return this.tabellatiService.getStazioniAppaltantiOptions(environment.GESTIONE_TABELLATI_PUBLIC_BASE_URL, data)
                .pipe(
                    map((result: Array<SAEntry>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (sa: SAEntry) => {
                            let item: SdkAutocompleteItem = {
                                ...sa,
                                text: `${sa.nomein} (${sa.cfein})`,
                                _key: sa.codein
                            };
                            arr.push(item);
                        });
                        return arr;
                    })
                );
            return null;
        }
    }

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}