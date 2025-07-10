import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SAEntry } from '../models/tabellati/tabellato.model';
import { TabellatiService } from '../services/tabellati.service';

@Injectable({ providedIn: 'root' })
export class StazioneAppaltanteAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Function {
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let baseUrl: string = args.tabellatiBaseUrl;
            return this.tabellatiService.getStazioniAppaltantiOptions(baseUrl, data)
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
        }
    }
    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    // #endregion

}