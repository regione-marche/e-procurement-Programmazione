import { Injectable, Injector } from "@angular/core";
import {
	IDictionary,
	SdkBaseService,
	SdkProvider,
	SdkRouterService,
	SdkStoreAction,
	SdkStoreService,
} from "@maggioli/sdk-commons";
import { isFunction } from "lodash-es";
import { Observable, Observer } from "rxjs";
import { map } from "rxjs/operators";

import { SdkDettaglioModelloStoreService } from "../components/sdk-dettaglio-modello/sdk-dettaglio-modello-store.service";
import { SdkDettaglioParametroStoreService } from "../components/sdk-dettaglio-parametro/sdk-dettaglio-parametro-store.service";
import { RicercaModelliForm } from "../model/gestione-utenti.model";
import { SdkGestioneModelliConstants } from "../sdk-gestione-modelli.constants";
import { GestioneModelliService } from "../services/gestione-modelli.service";

@Injectable()
export class SdkListaModelliProvider
	extends SdkBaseService
	implements SdkProvider
{
	constructor(inj: Injector) {
		super(inj);
	}

	public run(args: any): Observable<IDictionary<any>> {
		this.logger.debug("SdkRicercaModelliProvider", args);
		if (args.action === "DETAIL") {
			this.dettaglioModello(args);
		} else if (args.action === "DETAIL-PARAMETRO") {
			this.dettaglioParametro(args);
		} else if (args.action === "DELETE") {
			return this.deleteModello(args);
		} else if (args.buttonCode === "back") {
			return this.backToRicercaModelli(args);
		} else if (args.buttonCode === "back-to-lista-modelli") {
			let setUpdateState: Function = args.setUpdateState;
			if (isFunction(setUpdateState)) {
				setUpdateState(false);
			}
			this.routerService.navigateToPage("lista-modelli-page");
		} else if (args.buttonCode === "back-to-lista-parametri") {
			let setUpdateState: Function = args.setUpdateState;
			if (isFunction(setUpdateState)) {
				setUpdateState(false);
			}
			this.sdkDettaglioModelloStoreService.clear();
			this.sdkDettaglioModelloStoreService.idModello = args.idModello;

			let params: IDictionary<any> = {
				idModello: args.idModello,
			};
			this.routerService.navigateToPage("lista-parametri-modello-page", params);
		} else if (args.buttonCode === "nuovo-parametro") {
			let setUpdateState: Function = args.setUpdateState;
			if (isFunction(setUpdateState)) {
				setUpdateState(false);
			}
			this.sdkDettaglioModelloStoreService.clear();
			this.sdkDettaglioModelloStoreService.idModello = args.idModello;

			let params: IDictionary<any> = {
				idModello: args.idModello,
				listaParametri: args.listaParametri,
			};
			this.routerService.navigateToPage("nuovo-parametro-page", params);
		} else if (args.buttonCode === "back-to-lista-modelli") {
			let setUpdateState: Function = args.setUpdateState;
			if (isFunction(setUpdateState)) {
				setUpdateState(false);
			}
			this.sdkDettaglioModelloStoreService.clear();
			this.sdkDettaglioModelloStoreService.idModello = args.idModello;

			let params: IDictionary<any> = {
				idModello: args.idModello,
			};
			this.routerService.navigateToPage("lista-parametri-page", params);
		} else if (args.buttonCode === "back-to-dettaglio") {
			let setUpdateState: Function = args.setUpdateState;
			if (isFunction(setUpdateState)) {
				setUpdateState(false);
			}

			this.sdkDettaglioModelloStoreService.clear();
			this.sdkDettaglioModelloStoreService.idModello = args.idModello;

			let params: IDictionary<any> = {
				idModello: args.idModello,
			};

			this.routerService.navigateToPage("dettaglio-modello-page", params);
		} else if (args.buttonCode === "clean-button") {
			let form: RicercaModelliForm = {};
			form.codProfiloAttivo = args.codProfiloAttivo;
			form.syscon = args.syscon;
			this.store.dispatch(
				new SdkStoreAction(
					SdkGestioneModelliConstants.SEARCH_FORM_MODELLI_DISPATCHER,
					form
				)
			);
			return new Observable((ob: Observer<IDictionary<any>>) => {
				ob.next({
					cleanSearch: true,
				});
				ob.complete();
			});
		} else if (args.buttonCode === "nuovo") {
			this.routerService.navigateToPage("nuovo-modello-page");
		}
		return new Observable((ob: Observer<IDictionary<any>>) => {
			ob.next(args);
			ob.complete();
		});
	}

	private dettaglioModello(args): Observable<IDictionary<any>> {
		let setUpdateState: Function = args.setUpdateState;
		if (isFunction(setUpdateState)) {
			setUpdateState(false);
		}

		this.sdkDettaglioModelloStoreService.clear();
		this.sdkDettaglioModelloStoreService.idModello = args.idModello;
		let params: IDictionary<any> = {
			idModello: args.idModello,
		};
		this.routerService.navigateToPage("dettaglio-modello-page", params);
		return new Observable((ob: Observer<IDictionary<any>>) => {
			ob.next(undefined);
			ob.complete();
		});
	}

	private dettaglioParametro(args): Observable<IDictionary<any>> {
		let setUpdateState: Function = args.setUpdateState;
		if (isFunction(setUpdateState)) {
			setUpdateState(false);
		}

		this.sdkDettaglioParametroStoreService.clear();
		this.sdkDettaglioParametroStoreService.idModello = args.idModello;
		this.sdkDettaglioParametroStoreService.idParametro = args.idParametro;
		let params: IDictionary<any> = {
			idModello: args.idModello,
			idParametro: args.idParametro,
		};
		this.routerService.navigateToPage("dettaglio-parametro-page", params);
		return new Observable((ob: Observer<IDictionary<any>>) => {
			ob.next(undefined);
			ob.complete();
		});
	}

	private deleteModello(args): Observable<any> {
		let setUpdateState: Function = args.setUpdateState;
		let idModello = args.idModello;
		if (isFunction(setUpdateState)) {
			setUpdateState(false);
		}
		return this.gestioneModelliService.deleteModello(idModello).pipe(
			map((result) => {
				return { reload: true };
			})
		);
	}
	private backToRicercaModelli(args: any): Observable<IDictionary<any>> {
		let setUpdateState = args.setUpdateState;

		if (isFunction(setUpdateState)) {
			setUpdateState(false);
		}

		this.routerService.navigateToPage("ricerca-modelli-page");

		return new Observable((ob: Observer<IDictionary<any>>) => {
			ob.next(args);
			ob.complete();
		});
	}

	// #region Getters

	private get routerService(): SdkRouterService {
		return this.injectable(SdkRouterService);
	}

	private get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	private get sdkDettaglioModelloStoreService(): SdkDettaglioModelloStoreService {
		return this.injectable(SdkDettaglioModelloStoreService);
	}

	private get sdkDettaglioParametroStoreService(): SdkDettaglioParametroStoreService {
		return this.injectable(SdkDettaglioParametroStoreService);
	}

	private get gestioneModelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	// #endregion
}
