import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, filter, get, includes, isEmpty, isObject, toNumber } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { GaraEntry, ValoreTabellato } from '../../models/tabellati/tabellato.model';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { TabellatiUtils } from '../../services/utils/tabellati-utils.service';

@Injectable()
export class TabellatiComboProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(providerArgs: IDictionary<any>): Function {

        return (args: IDictionary<any>): Observable<Array<SdkComboBoxItem>> => {
            const listCode: string = get(args, 'listCode');
            const annoInizio: number = get(providerArgs, 'annoInizio');
            const tipologia: string = get(providerArgs, 'tipologia');
            const tipoNorma: number = get(providerArgs, 'tipoNorma');
            const advancedSearch: boolean = get(providerArgs, 'advancedSearch');
            if (!isEmpty(listCode)) {
                if (listCode === 'sino') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.SI')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.NO')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'EsitoSubappalto') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.POSITIVO')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.NEGATIVO')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'EsecuzioneSuba') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.ESEGUITO')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.NON-ESEGUITO')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'loaCombo') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '3',
                            value: this.translateService.instant('COMBOBOX.LOA.LOA3')
                        });
                        list.push({
                            key: '4',
                            value: this.translateService.instant('COMBOBOX.LOA.LOA4')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'idpCombo') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: 'SPID',
                            value: this.translateService.instant('COMBOBOX.IDP.SPID')
                        });
                        list.push({
                            key: 'CIE',
                            value: this.translateService.instant('COMBOBOX.IDP.CIE')
                        });
                        list.push({
                            key: 'CUSTOM',
                            value: this.translateService.instant('COMBOBOX.IDP.CUSTOM')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'annoriferimento') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = this.tabellatiUtils.getAnnoInizioCombo(annoInizio, tipologia, tipoNorma);
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'tipoImpresa') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.IMPRESA-SINGOLA')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.MANDATARIA')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'ambitoTerritoriale') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.AMBITO-TERRITORIALE-IT')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.AMBITO-TERRITORIALE-UE')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'settorecustom') {
                    return this.tabellatiCacheService.getValoriTabellato('TipoAppalto')
                        .pipe(
                            map((result: Array<ValoreTabellato>) => {
                                // filtro il tabellato rimuovendo la scelta "Lavori"
                                let filteredArr = filter(result, (one: ValoreTabellato) => one.codice !== 'L');
                                let arr: Array<SdkComboBoxItem> = [];
                                each(filteredArr, (tipo: ValoreTabellato) => {
                                    if (advancedSearch === true) {
                                        arr.push({ key: tipo.codice, value: tipo.descrizione });
                                    } else {
                                        arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                    }
                                });
                                return arr;
                            })
                        );
                } else if (listCode === 'CriterioAggiudicazioneSimog1') {
                    return this.tabellatiCacheService.getValoriTabellato('CriterioAggiudicazione').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let filteredArr = filter(result, (one: ValoreTabellato) => toNumber(one.codice) <= 2);
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: false });
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoAggiudicazione') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['1', '2', '3', '4', '5', '6', '7', '8', '14', '15', '16', '17', '18', '19'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoPcp') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['5', '6', '7', '8', '10', '13', '18'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'ProceduraAffidamento') {
                    return this.tabellatiCacheService.getValoriTabellato('ProceduraAffidamento').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['15', '16'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => !includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                arr.push({ key: tipo.codice, value: tipo.descrizione });

                            });
                            return arr;
                        })
                    );
                }



                else if (listCode === 'TipologiaSoggettoAggiudicazioneSemplificata') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['14'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoIniziale') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['5', '6', '7', '8', '16', '17', '98', '99'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoAdesione') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['5', '6', '14', '15', '16', '17', '18', '19'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoCollaudo') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            const codici: Array<string> = ['10', '11', '12', '13'];
                            return filter(result, (one: ValoreTabellato) => includes(codici, one.codice))
                                .map((tipo: ValoreTabellato) => {
                                    if (advancedSearch === true) {
                                        return { key: tipo.codice, value: tipo.descrizione };
                                    } else {
                                        return { key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' };
                                    }
                                });
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoCantieri') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            const codici: Array<string> = ['6', '7'];
                            return filter(result, (one: ValoreTabellato) => includes(codici, one.codice))
                                .map((tipo: ValoreTabellato) => {
                                    if (advancedSearch === true) {
                                        return { key: tipo.codice, value: tipo.descrizione };
                                    } else {
                                        return { key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' };
                                    }
                                });
                        })
                    );
                } else if (listCode === 'TipologiaSoggettoInizialeSemp') {
                    return this.tabellatiCacheService.getValoriTabellato('TipologiaSoggetto').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let codici: Array<string> = ['1', '2', '3', '4', '5', '6', '7', '8', '14', '98', '99'];
                            let filteredArr = filter(result, (one: ValoreTabellato) => includes(codici, one.codice));
                            let arr: Array<SdkComboBoxItem> = [];
                            each(filteredArr, (tipo: ValoreTabellato) => {
                                if (advancedSearch === true) {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione });
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione, disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if (listCode === 'MotivoInterruzioneSimogEq1') {
                    return this.tabellatiCacheService.getValoriTabellato('MotivoInterruzione').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            return filter(result, (one: ValoreTabellato) => +one.codice <= 5)
                                .map((tipo: ValoreTabellato) => {
                                    return { key: tipo.codice, value: tipo.descrizione };
                                });
                        })
                    );
                } else if (listCode === 'MotivoRisoluzioneSimoqEq1') {
                    return this.tabellatiCacheService.getValoriTabellato('MotivoRisoluzione').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            return filter(result, (one: ValoreTabellato) => +one.codice <= 5)
                                .map((tipo: ValoreTabellato) => {
                                    return { key: tipo.codice, value: tipo.descrizione };
                                });
                        })
                    );
                } else if (listCode === 'TipoRealizzazioneSimog1') {
                    return this.tabellatiCacheService.getValoriTabellato('TipoRealizzazione').pipe(
                        map((result: Array<ValoreTabellato>) => {
                            return filter(result, (one: ValoreTabellato) => +one.codice <= 12)
                                .map((tipo: ValoreTabellato) => {
                                    return { key: tipo.codice, value: tipo.descrizione };
                                });
                        })
                    );
                } else if (listCode === 'ApplicativoSA') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: 'W9-PT',
                            value: this.translateService.instant('INDEX-PAGE.PANELS.FIRST.TITLE')
                        });
                        list.push({
                            key: 'W9-AEC',
                            value: this.translateService.instant('INDEX-PAGE.PANELS.SECOND.TITLE')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'ApplicativoOR') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: 'W9-PT',
                            value: this.translateService.instant('INDEX-PAGE.PANELS.FIRST.TITLE')
                        });
                        list.push({
                            key: 'W9-AEC',
                            value: this.translateService.instant('INDEX-PAGE.PANELS.SECOND.TITLE')
                        });
                        list.push({
                            key: 'W9-INBOX',
                            value: this.translateService.instant('INDEX-PAGE.PANELS.THIRD.TITLE')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'EsitoProcedura') {
                    return this.tabellatiCacheService.getValoriTabellato(listCode).pipe(
                        map((result: Array<ValoreTabellato>) => {
                            let arr: Array<SdkComboBoxItem> = [];
                            each(result, (tipo: ValoreTabellato) => {
                                if (isObject(providerArgs.gara)) {
                                    let gara: GaraEntry = providerArgs.gara;
                                    if (gara.idFDelegate === 4) {
                                        arr.push({ key: tipo.codice, value: tipo.descrizione,  disabled: tipo.archiviato === '1'  });
                                    } else if (tipo.codice !== '5') {
                                        arr.push({ key: tipo.codice, value: tipo.descrizione ,  disabled: tipo.archiviato === '1' });
                                    }
                                } else {
                                    arr.push({ key: tipo.codice, value: tipo.descrizione,  disabled: tipo.archiviato === '1' });
                                }
                            });
                            return arr;
                        })
                    );
                } else if(listCode === 'TipoAttoGeneraleCombo') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('ATTI-GENERALI-LIST-CODE.ATTI-GENERALI')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('ATTI-GENERALI-LIST-CODE.MANIFESTAZIONE-E-ALTRI')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else {
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

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get tabellatiUtils(): TabellatiUtils { return this.injectable(TabellatiUtils) }

    // #endregion

}
