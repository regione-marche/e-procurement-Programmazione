import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each, get } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { LottoBaseEntry } from '../../models/gare/gare.model';
import { GareService } from '../../services/gare/gare.service';

@Injectable({ providedIn: 'root' })
export class CigMasterAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        const codGara: string = get(args, 'codGara');
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getCigMasterOptionsFactory(codGara, data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getCigMasterOptionsFactory(codGara: string, data?: string): () => Observable<any> {
        return () => {
            return this.gareService.getMultiLottoOptions(codGara, data)
                .pipe(
                    map((result: Array<LottoBaseEntry>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (one: LottoBaseEntry) => {
                            let item: SdkAutocompleteItem = {
                                ...one,
                                text: `${one.cig}`,
                                _key: one.cig
                            };
                            arr.push(item);
                        });
                        return arr;
                    })
                );
        }
    }

    // #region Getters

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    // #endregion

}