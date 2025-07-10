import { HttpErrorResponse, HttpClient, HttpParams } from "@angular/common/http";
import { Inject, Injectable, Injector } from "@angular/core";
import {
	IHttpParams,
	SDK_APP_CONFIG,
	SdkAppEnvConfig,
	SdkBaseService,
	SdkHttpLoaderService,
	SdkHttpLoaderType,
	SdkRestHelperService,
} from "@maggioli/sdk-commons";
import { SdkTableState } from "@maggioli/sdk-table";
import { head, isEmpty, toString } from "lodash-es";
import { Observable, throwError } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { RicercaModelliForm } from "../model/gestione-utenti.model";
import { SdkDettaglioModelloStoreService } from "../sdk-gestione-modelli.exports";
import { SdkGestioneModelliConstants } from "../sdk-gestione-modelli.constants";

export interface CacheModParamForm {
	syscon: number;
	idModello: number;
	parameters: { codice: string; valore: any }[];
}



@Injectable()
export class GestioneModelliService extends SdkBaseService {
	getListaParametri(idModello: string, state: SdkTableState): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		let params: IHttpParams = {
			idModello,
		};

		return this.restHelper
			.get<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/listaParametri",
				params
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				tap((r) => {
					this.sdkParametroStore.progr =
						Math.max(...r.data.map((e) => e.id.progr)) ?? 0;
				}),
				catchError((error: HttpErrorResponse) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	constructor(
		inj: Injector,
		@Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig
	) {
		super(inj);
	}

	// #region Public

	public getListaModelli(
		searchForm: RicercaModelliForm,
		state: SdkTableState
	): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		let params: IHttpParams = {
			skip: toString(state.skip),
			take: toString(state.take),
			sort: head(state.sort).field,
			sortDirection: head(state.sort).dir,
		};

		if (searchForm != null) {
			params = {
				...params,

				tipoDocumento: searchForm.tipoDocumento,
				nome: searchForm.nome,
				descrizione: searchForm.descrizione,
				file: searchForm.file,
				modelloPersonale: searchForm.modelloPersonale,
				presenteModelliPredisposti: searchForm.presenteModelliPredisposti,
				syscon: searchForm.syscon,
				codProfiloAttivo: searchForm.codProfiloAttivo,
				utenteCreatore: searchForm.utenteCreatore,
			};
		}

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + "/lista",
				params
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public getListaModelliPredisposti(
		searchForm: RicercaModelliForm,
		state: SdkTableState
	): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		let params: IHttpParams = {
			skip: toString(state.skip),
			take: toString(state.take),
			sort: head(state.sort).field,
			sortDirection: head(state.sort).dir,
		};

		if (searchForm != null) {
			params = {
				...params,
				entitaPrincipale: searchForm.entitaPrincipale,
				codProfiloAttivo: searchForm.codProfiloAttivo,
				syscon: searchForm.syscon
			};
		}

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/listaModelliPredisposti",
				params
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public getInizNuovoModello(): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.get<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/getInizNuovoModello"
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public getDettaglioModello(idModello: number): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
		let params: IHttpParams = {
			idModello: toString(idModello),
		};
		return this.restHelper
			.get<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + "/dettaglio",
				params
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public downloadDocumento(idModello: any): Observable<any> {
		const params: IHttpParams = {
			idModello: toString(idModello),
		};
		return this.restHelper
			.get<any>(
				`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/downloadDocumento`,
				params
			)
			.pipe(
				map((response: any) => {
					return response.data;
				})
			);
	}

	public deleteModello(idModello: number): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.delete<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + "/delete",
				idModello
			)
			.pipe(
				tap((_response) => {
					this.sdkHttpLoaderService.hideLoader();
				}),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public deleteParametro(
		idModello: number,
		idParametro: number
	): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");
		let form = {
			idModello,
			idParametro,
		};
		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.delete<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/deleteParametro",
				form
			)
			.pipe(
				tap((_response) => {
					this.sdkHttpLoaderService.hideLoader();
				}),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	updateModello(form: any): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + "/update",
				form
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public insertModello(form): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + "/create",
				form
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public insertParametroModello(form): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/createParametro",
				form
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	updateParametroModello(form: any): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/updateParametro",
				form
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public getDettaglioParametro(idModello, idParametro): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
		let params: IHttpParams = {
			idModello: toString(idModello),
			idParametro: toString(idParametro),
		};
		return this.restHelper
			.get<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/dettaglioParametro",
				params
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	public componiParametri(parameters): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");
		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/componiParametri",
				parameters
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}
	public moveParametro(
		idModello: number,
		idParametro: number,
		direction: string
	): Observable<any> {
		const body = {
			idModello,
			idParametro,
			direction,
		};
		return this.restHelper.post(
			this.appConfig.environment.GESTIONE_MODELLI_BASE_URL + "/moveParametro",
			body
		);
	}

	public cacheModParam(cacheForm: any): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/saveCacheModParam",
				cacheForm
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}
	public getCacheModParam(syscon: string, idModello: string): Observable<any> {
		if (isEmpty(this.appConfig.environment.GESTIONE_MODELLI_BASE_URL))
			throw new Error("GESTIONE_MODELLI_BASE_URL empty");

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
		return this.restHelper
			.get<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/getCacheModParam",
				{ syscon, idModello }
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}
	public getDefaultSchema(): Observable<{ [schema: string]: string }> {
		return this.restHelper.get<any>(
			`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/getDefaultSchema`
		);
	}
	public getDefaultArgomento(): Observable<{ [argomento: string]: string }> {
		return this.restHelper.get<any>(
			`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/getDefaultArgomento`
		);
	}
	public getArgomentiDesc(argomenti: string[]): Observable<any> {
		const params: IHttpParams = { listaArgomentiDesc: argomenti };
		return this.restHelper.get<any>(
			`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/listaArgomentiDesc`, params
		);
	}
	public getListaTabellatoParametro(schema: string): Observable<any> {
		const params: IHttpParams = { schema: schema };
		return this.restHelper.get<any>(
			`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/getListaTabellatoParametro`, params
		);
	}
	public eliminaParametriCompositore(idSessioneList: number[]): Observable<any> {

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

		return this.restHelper
			.post<any>(
				this.appConfig.environment.GESTIONE_MODELLI_BASE_URL +
				"/eliminaParametriCompositore",
				idSessioneList
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
	}

	
	public existDocument(idModello: any): Observable<any> {

		this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
		
		const params: IHttpParams = {
			idModello: toString(idModello)
		};
		
		return this.restHelper
			.get<any>(
				`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/existDocument`,
				params
			)
			.pipe(
				tap((_response) => this.sdkHttpLoaderService.hideLoader()),
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					this.sdkHttpLoaderService.hideLoader();
					return throwError(() => error);
				})
			);
		}
		public getTabFromCode(cod: string): Observable<any> {
			const params: IHttpParams = {
				cod
			};
			return this.restHelper.get<any>(
				`${this.appConfig.environment.GESTIONE_MODELLI_BASE_URL}/getTabFromCode`,
				params
			);

			
		}

	// #endregion

	// #region Getters

	private get restHelper(): SdkRestHelperService {
		return this.injectable(SdkRestHelperService);
	}

	private get sdkHttpLoaderService(): SdkHttpLoaderService {
		return this.injectable(SdkHttpLoaderService);
	}

	private get sdkParametroStore(): SdkDettaglioModelloStoreService {
		return this.injectable(SdkDettaglioModelloStoreService);
	}
	// #endregion
}
