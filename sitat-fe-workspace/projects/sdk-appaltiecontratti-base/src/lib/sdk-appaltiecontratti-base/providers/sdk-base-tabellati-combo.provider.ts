import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {  get, isEmpty } from 'lodash-es';
import { Observable, Observer } from 'rxjs';



@Injectable({ providedIn: "root" })
export abstract class SdkBaseTabellatiComboProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(providerArgs: IDictionary<any>): Function {

        return (args: IDictionary<any>): Observable<Array<SdkComboBoxItem>> => {
            
            const listCode: string = get(args, 'listCode');
            

            if (!isEmpty(listCode)) {
                return this.getComboItems(listCode);
            }
        }
    }

    protected getComboItems(listCode: string): Observable<SdkComboBoxItem[]> {
        if (listCode === 'sino') {
            return this.getSiNoComboItems();
        } else {
            return this.getComboItemsFromService(listCode);
        }
    }

    protected abstract getComboItemsFromService(listCode: string): Observable<SdkComboBoxItem[]>;

    private getSiNoComboItems(): Observable<SdkComboBoxItem[]> {
        return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
            let list: Array<SdkComboBoxItem> = new Array();
            list.push({
                key: "1",
                value: this.translateService.instant('COMBOBOX.SI')
            });
            list.push({
                key: "0",
                value: this.translateService.instant('COMBOBOX.NO')
            });
            ob.next(list);
            ob.complete();
        });
    }

    // #region Getters

    protected get translateService(): TranslateService { return this.injectable(TranslateService) }
    // #endregion

}
