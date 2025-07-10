import {
	AfterViewInit,
	ChangeDetectionStrategy,
	ChangeDetectorRef,
	Component,
	ElementRef,
	Inject,
	Injector,
	OnDestroy,
	OnInit,
	ViewChild,
} from "@angular/core";
import { DatePipe } from "@angular/common";
import {
	SDK_APP_CONFIG,
	SdkAbstractComponent,
	SdkAppEnvConfig,
	SdkBase64Helper,
	SdkHttpLoaderService,
	SdkHttpLoaderType,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkBasicButtonInput,
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkFormBuilderConfiguration,
	SdkMessagePanelService,
	SdkSimpleSearchConfig,
	SdkSimpleSearchInput,
	SdkTextboxConfig,
	SdkTextInput,
	SdkTreeConfig,
} from "@maggioli/sdk-controls";
import {
	clone,
	each,
	get,
	has,
	head,
	isArray,
	isEmpty,
	isObject,
	set,
	split,
} from "lodash-es";
import {
	BehaviorSubject,
	catchError,
	map,
	Observable,
	of,
	Subject,
	tap,
	throwError,
} from "rxjs";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { StazioneAppaltanteInfo } from "../../model/lib.model";
import { TranslateService } from "@ngx-translate/core";
import { GestioneModelliService } from "../../services/gestione-modelli.service";
import { SdkDocumentUtils } from "@maggioli/sdk-controls";
import { SdkDateHelper } from "@maggioli/sdk-commons";
import { PrimeNGConfig } from "primeng/api";
@Component({
	templateUrl: `sdk-componi-parametri-modal.widget.html`,
	changeDetection: ChangeDetectionStrategy.OnPush,
	styleUrl: "sdk-componi-parametri-modal.widget.scss",
})
export class SdkComponiParametriModalModalWidget
	extends SdkAbstractComponent<any, void, any>
	implements OnInit, AfterViewInit, OnDestroy
{
	// #region Variables
	public config: any;
	public data: any;
	public originalData: any;
	private buttons: SdkButtonGroupInput;
	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
	public userProfile: UserProfile;
	public parameters: Array<any>;
	public stazioneAppaltanteInfo: StazioneAppaltanteInfo;
	private entita: string;
	private identita: [];
	private storedFileKey: string;
	public informativeLabel: string;

	@ViewChild("messages") _messagesPanel: ElementRef;
	private idModello: string;
	listIdSessione: number[] = [];

	// #endregion

	constructor(
		inj: Injector,
		cdr: ChangeDetectorRef,
		@Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig,
		private primengConfig: PrimeNGConfig
	) {
		super(inj, cdr);
	}

	// #region Hooks

	protected onAfterViewInit(): void {}

	protected onOutput(item: any): void {
		if (isObject(item)) {
		}
	}

	protected onInit(): void {
		this.primengConfig.setTranslation(this.it);

		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.USER_PROFILE_SELECT)
				.subscribe((userProfile: UserProfile) => {
					this.userProfile = userProfile;
				})
		);
		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.SA_INFO_SELECT)
				.subscribe((saInfo: StazioneAppaltanteInfo) => {
					this.stazioneAppaltanteInfo = saInfo;
				})
		);
		const syscon = this.userProfile.syscon;
		const idModello = this.idModello;
		this.loadButtons();
		this.addSubscription(
			this.gestioneModelliService
				.getCacheModParam(syscon, idModello)
				.subscribe((result) => this.handleCacheModParamResult(result))
		);
	}

	protected onDestroy(): void {
		if (this.listIdSessione.length > 0) {
			this.gestioneModelliService
				.eliminaParametriCompositore(this.listIdSessione)
				.subscribe(() => {
					this.listIdSessione = [];
				});
			this.removeFileFromStorage();
		}
	}

	protected onConfig(config: any): void {
		if (isObject(config)) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.idModello = this.config.idModello;
				this.parameters = this.config.parameters;
				this.entita = this.config.entita;
				this.identita = this.config.identita;
				this.isReady = true;
			});
		}
	}

	private loadButtons(): void {
		let buttons = this.protectionUtilsService.checkButtonsProtection(
			this.config.buttons,
			this.userProfile.configurations
		);

		if (!this.parameters || this.parameters.length === 0) {
			buttons = buttons.filter((button) => button.code !== "clean-button");

			// Imposta l'etichetta informativa iniziale
			const downloadButton = buttons.find(
				(b) => b.code === "download-file-button"
			);
			this.informativeLabel = downloadButton
				? this.translateService.instant(
						"MODELLI-PREDISPOSTI.INFO-NO-PARAMETRI-SCARICA"
				  )
				: this.translateService.instant(
						"MODELLI-PREDISPOSTI.INFO-NO-PARAMETRI-COMPONI"
				  );
		}

		this.buttons = {
			buttons: buttons,
		};
		this.buttonsSubj = new BehaviorSubject(this.buttons);
	}

	protected onData(_data: void): void {}

	/**
	 * @ignore
	 */
	protected onUpdateState(state: boolean): void {}

	private get messagesPanel(): HTMLElement {
		return isObject(this._messagesPanel)
			? this._messagesPanel.nativeElement
			: undefined;
	}

	// #endregion

	// #region Getters

	private get protectionUtilsService(): ProtectionUtilsService {
		return this.injectable(ProtectionUtilsService);
	}

	private get sdkDateHelper(): SdkDateHelper {
		return this.injectable(SdkDateHelper);
	}

	private get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	private get provider(): SdkProviderService {
		return this.injectable(SdkProviderService);
	}

	private get translateService(): TranslateService {
		return this.injectable(TranslateService);
	}

	private get sdkMessagePanelService(): SdkMessagePanelService {
		return this.injectable(SdkMessagePanelService);
	}

	private get gestioneModelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	private get sdkBase64Helper(): SdkBase64Helper {
		return this.injectable(SdkBase64Helper);
	}

	private get sdkHttpLoaderService(): SdkHttpLoaderService {
		return this.injectable(SdkHttpLoaderService);
	}

	// #endregion

	// #region Private

	public onButtonClick(button: SdkButtonGroupOutput): void {
		let data: any = {
			code: button.code,
			messagesPanel: this.messagesPanel,
		};
		const cacheForm: any = {
			syscon: this.userProfile.syscon,
			idModello: this.idModello,
			parameters: [],
		};
		if (button.code === "invia-parametri-button") {
			this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);

			let errors = [];

			this.parameters.forEach((param) => {
				if (
					(param.value === null ||
						param.value === undefined ||
						param.value === "") &&
					param.tipo !== "UI" &&
					param.tipo !== "U" &&
					param.obbl === 1
				) {
					let errorMessage = {
						message: this.translateService.instant(
							"ERRORS.VALORIZZA-PARAMETRO",
							{ value: param.codice }
						),
					};
					errors.push(errorMessage);
				}
				cacheForm.parameters.push({
					codice: param.codice,
					value: param.value,
				});
			});
			if (!isEmpty(errors)) {
				this.sdkMessagePanelService.showError(this.messagesPanel, errors);
				this.sdkHttpLoaderService.hideLoader();
			} else {
				let form = {
					parametri: this.parameters,
					entita: this.entita,
					identita: this.identita,
					syscon: this.userProfile.syscon,
					codein: this.stazioneAppaltanteInfo.codice,
					idModello: this.idModello,
				};

				this.gestioneModelliService
					.componiParametri(form)
					.pipe(
						tap(
							this.gestioneModelliService.cacheModParam(
								this.handleCacheModParamSave(form)
							)
						),
						tap((result: any) => {
							this.listIdSessione.push(result.idSessione);
							this.sdkHttpLoaderService.hideLoader();

							const newButtons = clone(this.buttons);
							const downloadButtonIndex = newButtons.buttons.findIndex(
								(b) => b.code === "invia-parametri-button"
							);
							if (downloadButtonIndex >= 0) {
								newButtons.buttons[downloadButtonIndex].code =
									"download-file-button";
								newButtons.buttons[downloadButtonIndex].label =
									this.translateService.instant("BUTTONS.DOWNLOAD");
								newButtons.buttons[downloadButtonIndex].icon =
									"mgg-icons-action-download";
								if (!this.parameters || this.parameters.length === 0) {
									this.informativeLabel = this.translateService.instant(
										"MODELLI-PREDISPOSTI.INFO-NO-PARAMETRI-SCARICA"
									);
								}
							}
							this.buttonsSubj.next(newButtons);

							this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
								{
									message: this.translateService.instant(
										"MODELLI-PREDISPOSTI.FILE-GENERATO"
									),
								},
							]);
							let randomSuffix = Math.floor(
								100000000 + Math.random() * 900000000
							);
							this.storedFileKey =
								result.filename.replace(/\.[^/.]+$/, "") + "_" + randomSuffix;
							let fileObj = {
								base64: result.data,
								filename: result.filename,
								tipofile: result.tipofile,
								downloaded: false,
							};
							sessionStorage.setItem(
								this.storedFileKey,
								JSON.stringify(fileObj)
							);
						}),
						catchError((error: any, caught: Observable<any>) => {
							this.sdkHttpLoaderService.hideLoader();
							let errorData = `ERRORS.UNEXPECTED-ERROR`;
							if (isObject(error) && isObject(get(error, "error"))) {
								let errorObject: any = get(error, "error");
								errorData =
									errorObject.errorData === "DOCUMENTO_NON_TROVATO"
										? "ERRORS.DOCUMENTO-NON-TROVATO"
										: errorObject.errorData;
							}
							this.sdkMessagePanelService.showError(this.messagesPanel, [
								{
									message: this.manageError(errorData),
								},
							]);
							this.scrollToMessagePanel(this.messagesPanel);
							return throwError(() => error);
						})
					)
					.subscribe();
			}
		} else if (button.code === "download-file-button") {
			if (this.storedFileKey) {
				let fileObjStr = sessionStorage.getItem(this.storedFileKey);
				if (fileObjStr) {
					let fileObj = JSON.parse(fileObjStr);
					const base64File = fileObj.base64;
					const filename = fileObj.filename || "file.pdf";
					const tipofile = fileObj.tipofile || "pdf";

					if (base64File) {
						const arrBuffer =
							this.sdkBase64Helper.base64ToArrayBuffer(base64File);
						const blob = new Blob([arrBuffer], {
							type: SdkDocumentUtils.getMimeTypeFromExtension(tipofile),
						});
						const data = window.URL.createObjectURL(blob);
						const link = document.createElement("a");
						document.body.appendChild(link);
						link.href = data;
						link.download = filename;
						link.click();
						window.URL.revokeObjectURL(data);
						link.remove();
						this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
							{
								message: this.translateService.instant(
									this.translateService.instant(
										"MODELLI-PREDISPOSTI.FILE-SCARICATO"
									)
								),
							},
						]);
						fileObj.downloaded = true;
						sessionStorage.setItem(this.storedFileKey, JSON.stringify(fileObj));

						this.removeFileFromStorage();
						let newButtons = clone(this.buttons);
						const oldButtonIndex = newButtons.buttons.findIndex(
							(b) => b.code === "download-file-button"
						);
						if (oldButtonIndex >= 0) {
							newButtons.buttons[oldButtonIndex].code =
								"invia-parametri-button";
							newButtons.buttons[oldButtonIndex].label =
								this.translateService.instant("MODELLI-PREDISPOSTI.COMPONI");
							newButtons.buttons[oldButtonIndex].icon = "mgg-icons-write-only";
							if (!this.parameters || this.parameters.length === 0) {
								this.informativeLabel =
									"MODELLI-PREDISPOSTI.INFO-NO-PARAMETRI-COMPONI";
							}
						}
						this.buttonsSubj.next(newButtons);
					} else {
						this.sdkMessagePanelService.showError(this.messagesPanel, [
							{
								message: this.translateService.instant(
									"ERRORS.FILE-NON-DISPONIBILE"
								),
							},
						]);
					}
				}
			}
		} else if (button.code === "clean-button") {
			this.parameters = this.parameters.map((param) => {
				return {
					...param,
					value: null,
				};
			});
		} else if (isObject(button) && isEmpty(button.provider) === false) {
			this.provider
				.run(button.provider, {
					type: "BUTTON",
					data,
				})
				.subscribe(this.manageExecutionProvider);
		}
	}
	private manageError = (error: any) => {
		return this.userProfile.abilitazioni.includes("ou50")
			? error
			: this.translateService.instant("ERRORS.ERRORE_COMPOSIZIONE");
	};
	private manageExecutionProvider = (obj: any) => {
		if (isObject(obj)) {
			if (has(obj, "close") && get(obj, "close") === true) {
				if (!this.canCloseModal()) {
					if (!window.confirm("Attenzione file non ancora scaricato")) {
						return;
					}
				}
				this.removeFileFromStorage();
				this.emitOutput(undefined);
			}
		}
	};

	private scrollToMessagePanel(messagesPanel: HTMLElement): void {
		messagesPanel.scrollIntoView();
	}

	public manageFormOutput(config: SdkFormBuilderConfiguration): void {}

	private tipoHandlers = {
		D: (value: any) => {
			if (value === null || value === undefined || value === "") {
				return "";
			}
			if (typeof value === "string") {
				const [day, month, year] = value.split(".");
				const date = new Date(`${year}-${month}-${day}`);
				return isNaN(date.getTime()) ? null : date;
			}
			const date = new Date(value);
			return isNaN(date.getTime()) ? null : date;
		},
		F: (value: any) => {
			const n = value.replace(".", ",");
			// return isNaN(n) ? null : n;
			return n
		},
		I: (value: any) => {
			const n = value;
			return isNaN(n) ? null : n;
		},
		T: (value: any) => value,
		M: (value: any) => value,
		S: (value: any) => value?.toString() ?? "",
		N: (value: any) => value?.toString() ?? "",
		U: (value: any) => value,
		UI: (value: any) => value,
	};
	private saveCacheTipoHandlers = {
		D: (value: any) => {
			if (value === null || value === undefined || value === "") {
				return null;
			}
			const dateCache = new Date(value);
			return isNaN(dateCache.getTime())
				? null
				: this.formatToDdMmYyyy(dateCache);
		},
		F: (value: any) => {
			// const n = parseFloat(value);
			// return isNaN(n) ? null : n;
			// return new Number(value).toLocaleString("en-EN", {
			// 	maximumSignificantDigits: 20,
			// 	useGrouping: false,
			// });
			return value.replace(",", ".");
		},
		I: (value: any) => {
			// const n = parseInt(value, 10);
			// return isNaN(n) ? null : n;
			return value.toString();
		},
		T: (value: any) => value,
		M: (value: any) => value,
		S: (value: any) => value?.toString() ?? "",
		N: (value: any) => value?.toString() ?? "",
		U: (value: any) => value,
		UI: (value: any) => value,
	};

	private canCloseModal(): boolean {
		if (!this.storedFileKey) return true;
		let fileObjStr = sessionStorage.getItem(this.storedFileKey);
		if (!fileObjStr) return true;
		let fileObj = JSON.parse(fileObjStr);
		return fileObj.downloaded === true;
	}

	private removeFileFromStorage(): void {
		if (this.storedFileKey) {
			sessionStorage.removeItem(this.storedFileKey);
		}
	}

	private handleCacheModParamResult(result: any): void {
		if (result.esito) {
			const cachedParamsMap = new Map(
				result.parameters.map((p) => [p.codice, p.value])
			);

			this.parameters = this.parameters.map((param) => {
				const cachedValue = cachedParamsMap.get(param.codice);
				if (cachedValue !== undefined) {
					const handler = this.tipoHandlers[param.tipo];
					param.value = handler ? handler(cachedValue) : cachedValue;
				}
				return param;
			});
			this.markForCheck();
		}
	}

	private handleCacheModParamSave(form: any): any {
		form.parametri = form.parametri.map((param) => {
			if (param.value === null || param.value === undefined) {
				return {
					...param,
					value: "",
				};
			}

			const handler = this.saveCacheTipoHandlers[param.tipo];
			return {
				...param,
				value: handler ? handler(param.value) : param.value,
			};
		});
		return form;
	}
	formatToDdMmYyyy = (date: Date): string => {
		const [day, month, year] = date.toLocaleDateString("it-IT").split("/");
		return `${day}.${month}.${year}`;
	};
	public it = {
		firstDayOfWeek: 1,
		dayNames: [
			"Domenica",
			"Lunedì",
			"Martedì",
			"Mercoledì",
			"Giovedì",
			"Venerdì",
			"Sabato",
		],
		dayNamesShort: ["Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"],
		dayNamesMin: ["D", "L", "M", "M", "G", "V", "S"],
		monthNames: [
			"Gennaio",
			"Febbraio",
			"Marzo",
			"Aprile",
			"Maggio",
			"Giugno",
			"Luglio",
			"Agosto",
			"Settembre",
			"Ottobre",
			"Novembre",
			"Dicembre",
		],
		monthNamesShort: [
			"Gen",
			"Feb",
			"Mar",
			"Apr",
			"Mag",
			"Giu",
			"Lug",
			"Ago",
			"Set",
			"Ott",
			"Nov",
			"Dic",
		],
		today: "Oggi",
		clear: "Cancella",
	};
	// #endregion
}
