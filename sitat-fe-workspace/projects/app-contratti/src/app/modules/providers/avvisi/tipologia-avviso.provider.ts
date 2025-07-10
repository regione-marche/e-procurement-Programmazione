import { Injectable, Injector } from '@angular/core';
import { TabellatiCacheService, ValoreTabellato } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../app.constants';

@Injectable({ providedIn: 'root' })
export class TipologiaAvvisoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        return (): Observable<Array<SdkComboBoxItem>> => {
            return this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_AVVISO)
                .pipe(
                    map((tipiAvviso: Array<ValoreTabellato>) => {
                        let arr: Array<SdkComboBoxItem> = [];
                        each(tipiAvviso, (tipo: ValoreTabellato) => {                            
                            arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                        });
                        return arr;
                    })
                );
        }
    }

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService); }

    // #endregion

}