import { Injectable, Injector } from '@angular/core';
import { ComboDto, SdkBaseTabellatiComboProvider } from '@maggioli/sdk-appaltiecontratti-base';
import { SdkProvider } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { TabellatiCacheService } from '../services/tabellati-cache.service';


@Injectable({ providedIn: "root" })
export class SdkDocAssociatiTabellatiComboProvider extends SdkBaseTabellatiComboProvider implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    protected getComboItemsFromService(listCode: string): Observable<SdkComboBoxItem[]> {
        return this.tabellatiCacheService.tabellatiGetComboData(listCode)
            .pipe(
                map((result: Array<ComboDto>) => {
                    let arr: Array<SdkComboBoxItem> = [];
                    each(result, (tipo: ComboDto) => {
                        arr.push({ key: tipo.key, value: tipo.value, disabled: false });
                    });
                    return arr;
                })
            );
    }

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

}
