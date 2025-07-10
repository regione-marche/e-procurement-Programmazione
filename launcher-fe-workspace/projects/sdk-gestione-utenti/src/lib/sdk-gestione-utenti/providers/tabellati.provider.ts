import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, isEmpty } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { RichiestaAssistenzaOggettoDTO } from '../model/gestione-utenti.model';
import { ValoreTabellato } from '../model/lib.model';
import { TabellatiCacheService } from '../services/tabellati/tabellati-cache.service';

@Injectable()
export class SdkGestioneUtentiTabellatiComboProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(providerArgs: IDictionary<any>): Function {

        return (args: IDictionary<any>): Observable<Array<SdkComboBoxItem>> => {
            const listCode: string = get(args, 'listCode');
            const advancedSearch: boolean = get(providerArgs, 'advancedSearch');
            const tipologiaRichiesta: Array<RichiestaAssistenzaOggettoDTO> = get(providerArgs, 'tipologiaRichiesta');
            const sezioniConfigurazioni: Array<string> = get(providerArgs, 'sezioniConfigurazioni');
            const codiciEventi: Array<string> = get(providerArgs, 'codiciEventi');
            const listaConfigurazioniPosta: Array<ValoreTabellato> = get(providerArgs, 'listaConfigurazioniPosta');
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
                } else if (listCode === 'GestioneUtenti') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.NESSUN-PRIVILEGIO')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.SOLA-LETTURA')
                        });
                        list.push({
                            key: '3',
                            value: this.translateService.instant('COMBOBOX.GESTIONE-COMPLETA')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'ScadenzaAccount') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.SCADENZA-ACCOUNT-MAI')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.SCADENZA-ACCOUNT-ALLA-FINE-DI')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'ControlliGdpr') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.SI')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.NO'),
                            description: 'SDK-UTENTE.DESCRIZIONE-NO-GDPR'
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'PrivilegiUtenteContratti') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: 'A',
                            value: this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-CONTRATTI.A')
                        });
                        list.push({
                            key: 'C',
                            value: this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-CONTRATTI.C')
                        });
                        list.push({
                            key: 'U',
                            value: this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-CONTRATTI.U')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'PrivilegiUtenteDL229') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: 'A',
                            value: this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-DL229.A')
                        });
                        list.push({
                            key: 'U',
                            value: this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-DL229.U')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'TipologiaRichiesta') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = tipologiaRichiesta.map((obj: RichiestaAssistenzaOggettoDTO) => {
                            return {
                                key: obj.key,
                                value: obj.value
                            };
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'SezioniConfigurazioni') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = sezioniConfigurazioni.map((obj: string) => {
                            return {
                                key: obj,
                                value: obj
                            };
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'CodiceEvento') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = codiciEventi.map((obj: string) => {
                            return {
                                key: obj,
                                value: obj
                            };
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'ListaConfigurazioniPosta') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = listaConfigurazioniPosta.map((obj: ValoreTabellato) => {
                            return {
                                key: obj.codice,
                                value: obj.descrizione
                            };
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'ProtocolloPosta') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '0',
                            value: this.translateService.instant('SDK-SERVER-POSTA.PROTOCOLLO-POSTA.SMTP')
                        });
                        list.push({
                            key: '1',
                            value: this.translateService.instant('SDK-SERVER-POSTA.PROTOCOLLO-POSTA.SMTPS')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('SDK-SERVER-POSTA.PROTOCOLLO-POSTA.STARTTLS')
                        });
                        ob.next(list);
                        ob.complete();
                    });
                } else if (listCode === 'GestioneModelliCombo') {
                    return new Observable((ob: Observer<Array<SdkComboBoxItem>>) => {
                        let list: Array<SdkComboBoxItem> = new Array();
                        list.push({
                            key: '1',
                            value: this.translateService.instant('COMBOBOX.NON-ABILITATA')
                        });
                        list.push({
                            key: '2',
                            value: this.translateService.instant('COMBOBOX.SOLO-MODELLI-PERSONALI')
                        });
                        list.push({
                            key: '3',
                            value: this.translateService.instant('COMBOBOX.GESTIONE-COMPLETA')
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

    // #endregion
}