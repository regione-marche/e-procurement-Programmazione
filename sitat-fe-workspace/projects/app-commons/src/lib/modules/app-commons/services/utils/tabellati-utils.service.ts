import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { toString } from 'lodash-es';

@Injectable()
export class TabellatiUtils extends SdkBaseService {

    constructor(inj: Injector) { super(inj) }

    public getAnnoInizioCombo(annoInizio: number, tipologia: string, tipoNorma: number,): Array<SdkComboBoxItem> {

        let list: Array<SdkComboBoxItem> = new Array();
        list.push({
            key: '1',
            value: toString(annoInizio)
        });
        list.push({
            key: '2',
            value: toString(annoInizio + 1)
        });
        if (tipologia === '2' && tipoNorma === 3) {
            list.push({
                key: '3',
                value: toString(annoInizio + 2)
            });
        }
        if (tipologia === '1') {
            list.push({
                key: '3',
                value: toString(annoInizio + 2)
            });
        }
        return list;
    }
}