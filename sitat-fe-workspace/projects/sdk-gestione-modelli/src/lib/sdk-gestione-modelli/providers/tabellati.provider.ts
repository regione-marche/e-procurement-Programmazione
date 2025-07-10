import { Injectable, Injector } from "@angular/core";
import {
	IDictionary,
	SdkBaseService,
	SdkProvider,
} from "@maggioli/sdk-commons";
import { SdkComboBoxItem } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { each, get, isEmpty } from "lodash-es";
import { Observable, Observer } from "rxjs";
import { map, tap, switchMap } from "rxjs/operators";
import { ValoreTabellato } from "../model/lib.model";
import { TabellatiCacheService } from "../services/tabellati/tabellati-cache.service";
import { TabellatiService } from "../services/tabellati/tabellati.service";
import { GestioneModelliService } from "../services/gestione-modelli.service";
import { SdkDettaglioModelloStoreService } from "../sdk-gestione-modelli.exports";
import { BaseResponse, ResponseResult } from "@maggioli/sdk-appaltiecontratti-base";

@Injectable()
export class SdkGestioneModelliTabellatiComboProvider
	extends SdkBaseService
	implements SdkProvider
{
	constructor(inj: Injector) {
		super(inj);
	}
	public run(providerArgs: IDictionary<any>): Function {
		return (args: IDictionary<any>): Observable<Array<SdkComboBoxItem>> => {
			const codProfiloAttivo: string = providerArgs?.codiceProfilo;
			const listCode: string = get(args, "listCode");
			const advancedSearch: boolean = get(providerArgs, "advancedSearch");
			if (!isEmpty(listCode)) {
				switch (listCode) {
					case "sino":
						return new Observable((ob: Observer<SdkComboBoxItem[]>) => {
							const list: SdkComboBoxItem[] = [
								{
									key: "1",
									value: this.translateService.instant("COMBOBOX.SI"),
								},
								{
									key: "2",
									value: this.translateService.instant("COMBOBOX.NO"),
								},
							];
							ob.next(list);
							ob.complete();
						});

					case "sinoboolean":
						return new Observable((ob: Observer<SdkComboBoxItem[]>) => {
							const list: SdkComboBoxItem[] = [
								{
									key: "1",
									value: this.translateService.instant("COMBOBOX.SI"),
								},
								{
									key: "0",
									value: this.translateService.instant("COMBOBOX.NO"),
								},
							];
							ob.next(list);
							ob.complete();
						});
					case "dropdownUtentiOptions":
						return this.tabellatiService
							.listaOpzioniUtenti(codProfiloAttivo)
							.pipe(
								map((result: any) => {
									const utentiOptions = result.utentiOptions;
									const arr: Array<SdkComboBoxItem> = [];
									each(utentiOptions, (user: any) => {
										arr.push({ key: `${user.sysCon}`, value: user.sysUte });
									});
									return arr;
								})
							);
					case "ListaTabellatoParametro":
						return this.modelliService.getListaTabellatoParametro(this.sdkModelloStore.entita)
							.pipe(
								map((result: ResponseResult<Array<{c0c_mne_ber: string, coc_des: string}>>) => {
									if (!result.data) return [];
									
									return result.data.map((r: {c0c_mne_ber: string, coc_des: string}) => {
										return { key: r.c0c_mne_ber, value: r.coc_des };
									});
								})
							);
					default:
						return this.tabellatiCacheService.getValoriTabellato(listCode).pipe(
							map((result: Array<ValoreTabellato>) => {
								let arr: Array<SdkComboBoxItem> = [];
								each(result, (tipo: ValoreTabellato) => {
									if (advancedSearch === true) {
										arr.push({ key: tipo.codice, value: tipo.descrizione });
									} else {
										arr.push({
											key: tipo.codice,
											value: tipo.descrizione,
											disabled: tipo.archiviato === "1",
										});
									}
								});
								return arr;
							})
						);
				}
			}
		};
	}

	// #region Getters

	private get tabellatiCacheService(): TabellatiCacheService {
		return this.injectable(TabellatiCacheService);
	}
	private get tabellatiService(): TabellatiService {
		return this.injectable(TabellatiService);
	}
	private get translateService(): TranslateService {
		return this.injectable(TranslateService);
	}
	private get modelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}
	private get sdkModelloStore(): SdkDettaglioModelloStoreService {
		return this.injectable(SdkDettaglioModelloStoreService);
	}
	// #endregion
}
