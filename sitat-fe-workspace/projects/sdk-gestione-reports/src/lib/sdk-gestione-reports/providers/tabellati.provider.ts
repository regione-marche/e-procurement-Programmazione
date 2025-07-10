import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';
import { ValoreTabellato } from '../model/lib.model';
import { TabellatiCacheService } from '../services/tabellati/tabellati-cache.service';

@Injectable({ providedIn: 'root' })
export class SdkGestioneReportsTabellatiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    //#region Public

    public run(providerArgs: IDictionary<any>): Function {

        return (args: IDictionary<any>): Observable<Array<SdkComboBoxItem>> => {
            const listCode: string = get(args, 'listCode');
            const advancedSearch: boolean = get(providerArgs, 'advancedSearch');

            if (!isEmpty(listCode)) {
                if (listCode === 'siNo') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.SI')
                        });
                        list.push({
                            key: '0',
                            value: this.translateService.instant('COMBOBOX.NO')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                }
                else if(listCode === 'formatoExportSchedulato'){
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '0',
                            value: this.translateService.instant('FORMATI.CSV')
                        });
                        list.push({
                            key: '1',
                            value: this.translateService.instant('FORMATI.XLSX')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('FORMATI.DOCX')
                        });
                        list.push({
                            key: '3',
                            value: this.translateService.instant('FORMATI.PDF')
                        });
                        list.push({
                            key: '4',
                            value: this.translateService.instant('FORMATI.JSON')
                        });
                        ob.next(list);
                        ob.complete();
                    })
                }
                else {
                    return this.tabellatiCacheService.getValoriTabellato(listCode).pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let arr: Array<SdkComboBoxItem> = [];
                            each(result, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                }
            }
        }

    }

    //#endregion

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion
}