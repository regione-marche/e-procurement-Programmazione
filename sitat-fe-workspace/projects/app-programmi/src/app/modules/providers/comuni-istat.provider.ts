import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, TabellatiService, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ComuniIstatProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getListaComuniFactory(args, data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getListaComuniFactory(args: any, data?: string): () => Observable<any> {
        return () => {
            const province: IDictionary<ValoreTabellato> = args.provinceMap;
            return this.tabellatiService.listaOpzioniComuni(environment.GESTIONE_TABELLATI_BASE_URL, data)
                .pipe(
                    map((result: Array<ValoreTabellato>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (comune: ValoreTabellato) => {
                            const codiceIstatProvincia: string = `0${comune.codistat.substring(3, 6)}`;
                            let item: SdkAutocompleteItem = {
                                ...comune,
                                text: `${comune.descrizione} - ${province[codiceIstatProvincia].descrizione} (${comune.codistat})`,
                                _key: comune.codistat
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