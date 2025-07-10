import { HttpErrorResponse } from "@angular/common/http";
import {
	ChangeDetectorRef,
	Component,
	ElementRef,
	HostBinding,
	Injector,
	OnDestroy,
	OnInit,
	ViewChild,
} from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import {
	IDictionary,
	SdkBase64Helper,
	SdkBusinessAbstractWidget,
	SdkDateHelper,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkComboBoxItem,
	SdkFormBuilderConfiguration,
	SdkFormBuilderField,
	SdkMenuTab,
	SdkMessagePanelService,
	SdkMessagePanelTranslate,
	SdkTextOutput,
} from "@maggioli/sdk-controls";
import {
	cloneDeep,
	get,
	isEmpty,
	isObject,
	map as mapArray,
	remove,
	set,
	toString,
} from "lodash-es";
import { BehaviorSubject, Observable, of, Subject, throwError } from "rxjs";
import { catchError, map, mergeMap, tap, switchMap } from "rxjs/operators";

import { UserDTO } from "../../model/gestione-utenti.model";
import {
	CustomParamsFunction,
	ResponseDTO,
	ValoreTabellato,
} from "../../model/lib.model";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GestioneModelliService } from "../../services/gestione-modelli.service";
import { SdkDettaglioModelloStoreService } from "./sdk-dettaglio-modello-store.service";
import { ModelliUtils } from "../modelli-utils";
import { SdkBaseDetailSectionWidget } from "@maggioli/sdk-appaltiecontratti-base";

@Component({
	templateUrl: "./sdk-dettaglio-modello.component.html",
})
export class SdkDettaglioModelloComponent
	extends SdkBaseDetailSectionWidget
	implements OnInit, OnDestroy
{
	protected getEntity() {
	return this.dettaglio 
	}
	protected getValoriTabellatiConst(): string[] {
	return []
	}
	// #region Variables
	
	@HostBinding("class") classNames = `sdk-dettaglio-modello-section`;
	
	@ViewChild("messages") _messagesPanel: ElementRef;
	@ViewChild("infoBox") _infoBox: ElementRef;
	
	public config: any = {};
	
	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> =
	new BehaviorSubject(null);
	public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> =
	new Subject();
	
	protected buttons: SdkButtonGroupInput;
	private buttonsReadonly: SdkButtonGroupInput;
	protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
	protected formBuilderConfig: SdkFormBuilderConfiguration;
	protected userProfile: UserProfile;
	protected valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private idModello: number;
	private dettaglio: any;
	protected menuTabs: Array<SdkMenuTab>;
	private argomentDesc: any;
	private allowDownload: boolean;

	// #endregion

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	// #region Hooks

	protected onInit(): void {
		console.log("onInit");
		this.addSubscription(
			this.store
			.select<UserProfile>(SdkGestioneModelliConstants.USER_PROFILE_SELECT)
			.subscribe((userProfile: UserProfile) => {
				this.userProfile = userProfile;
			})
		);
		this.allowDownload = this.getProtezioniValue(
				"COLS.VIS.GENEWEB.W_DOCDIG.DIGOGG",
				false
			);

		this.loadButtons();
		this.loadQueryParams();
	}

	protected onAfterViewInit(): void {
		this.refreshTabs();
		this.loadTabellati()
			.pipe(
				map(this.elaborateTabellati),
				mergeMap(this.loadDettaglio),
				map(this.elaborateDettaglio),
				map(() => this.checkInfoBox()),
				map(() => this.loadForm()),
				map(this.elaborateButtons),
				catchError(this.handleError),
			)
			.subscribe();
	}

	protected onDestroy(): void {}

	protected onConfig(config: any): void {
		if (config != null) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.menuTabs = this.config.menuTabs;
				this.isReady = true;
			});
		}
	}

	protected onUpdateState(_state: boolean): void {}

	// #endregion

	// #region Getters

	protected get activatedRoute(): ActivatedRoute {
		return this.injectable(ActivatedRoute);
	}

	protected get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	protected get sdkMessagePanelService(): SdkMessagePanelService {
		return this.injectable(SdkMessagePanelService);
	}


	protected get tabellatiCacheService(): TabellatiCacheService {
		return this.injectable(TabellatiCacheService);
	}

	protected get provider(): SdkProviderService {
		return this.injectable(SdkProviderService);
	}

	private get gestioneModelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	protected get dateHelper(): SdkDateHelper {
		return this.injectable(SdkDateHelper);
	}

	// #endregion

	// #region Public

	public manageFormOutput(config: SdkFormBuilderConfiguration): void {
		this.formBuilderConfig = config;
	}

	public onButtonClick(button: SdkButtonGroupOutput): void {
		if (button != null && isEmpty(button.provider) === false) {
			let data: IDictionary<any> = {
				buttonCode: button.code,
				messagesPanel: this.messagesPanel,
				defaultFormBuilderConfig: this.defaultFormBuilderConfig,
				formBuilderConfig: this.formBuilderConfig,
				setUpdateState: this.setUpdateState,
				idModello: this.idModello,
			};

			this.provider
				.run(button.provider, data)
				.subscribe(this.manageExecutionProvider);
		}
	}

	// #endregion

	// #region Private

	protected get messagesPanel(): HTMLElement {
		return this._messagesPanel != null
			? this._messagesPanel.nativeElement
			: undefined;
	}

	private get sdkBase64Helper(): SdkBase64Helper {
		return this.injectable(SdkBase64Helper);
	}

	private get modelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	protected get infoBox(): HTMLElement {
		return this._infoBox != null ? this._infoBox.nativeElement : undefined;
	}

	protected loadTabellati = (): Observable<
		IDictionary<Array<ValoreTabellato>>
	> => {
		return this.tabellatiCacheService.getValoriTabellati(
			SdkGestioneModelliConstants.DETTAGLIO_MODELLO_TABELLATI
		);
	};

	protected elaborateTabellati = (
		result: IDictionary<Array<ValoreTabellato>>
	) => {
		this.valoriTabellati = result;
	};

	protected refreshTabs(): void {
		remove(this.menuTabs, (one: SdkMenuTab) => {
			if (!isEmpty(one.oggettoProtezione)) {
				let visible: boolean = this.protectionUtilsService.isMenuTabVisible(
					one.oggettoProtezione,
					this.userProfile.configurations
				);
				if (visible !== true) {
					return true;
				}
			}
			if (!isEmpty(one.visible)) {
				let visible: boolean = this.provider.run(one.visible, {
					userProfile: this.userProfile,
				});
				return visible === false;
			}
			return false;
		});
		this.sdkLayoutMenuTabs.emit(this.menuTabs);
	}

	protected checkInfoBox(): void {
		if (!isEmpty(this.config.infoBox)) {
			this.sdkMessagePanelService.clear(this.infoBox);
			this.sdkMessagePanelService.showInfoBox(this.infoBox, {
				message: this.config.infoBox,
			});
		}
	}

	protected manageFieldsForCustomPopulateFunction(
		field: SdkFormBuilderField,
		mapping: boolean
	) {

					if (field.code === "isOU50") {
						const enabled = this.userProfile.abilitazioni.includes('ou50');

						set(field, "data", enabled ? 1 : 0);
						mapping = false;
					}
					if (field.code === "schemaPrincipale") {
						
						const item = ModelliUtils.whichDescrizione(this.dettaglio.schema);
						set(field, "data", item);
						mapping = false;			
						}
					if(field.code === "nomeFile" && !this.allowDownload) {
						set(field, "link", false);					}

						return	mapping
					}



	public manageFormClick(config: SdkTextOutput): void {
		if (isObject(config)) {
			if (config.code === "nomeFile" && this.allowDownload) {
				this.modelliService
					.downloadDocumento(this.idModello)
					.pipe(
						catchError((error: HttpErrorResponse) => {
							if (error.error && error.error.errorData === "DOCUMENTO_NON_TROVATO") {
								this.sdkMessagePanelService.showError(this.messagesPanel, [
									{ message: "ERRORS.DOCUMENTO-NON-TROVATO" }
								]);
							} else {
								this.sdkMessagePanelService.showError(this.messagesPanel, [
									{ message: "ERRORS.UNEXPECTED-ERROR" }
								]);
							}
							return throwError(() => error);
						})
					)
					.subscribe({
						next: (text) => {
							try {
								let arrBuffer = this.sdkBase64Helper.base64ToArrayBuffer(text);
								var blob = new Blob([arrBuffer], {
									type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
								});
								let data = window.URL.createObjectURL(blob);
								let link = document.createElement("a");
								document.body.appendChild(link);
								link.href = data;
								link.download = this.dettaglio.nomefile;
								link.click();
								window.URL.revokeObjectURL(data);
								link.remove();
							} catch (err) {
								this.sdkMessagePanelService.showError(this.messagesPanel, [
									{ message: "ERRORS.ERRORE-GENERAZIONE_DOCUMENTO_DOWNLOAD" }
								]);
							}
						}
					});
			}
		}
	}

	protected loadButtons(): void {
		this.buttons = {
			buttons: this.protectionUtilsService.checkButtonsProtection(
				this.config.body.buttons,
				this.userProfile.configurations
			),
		};

		this.buttonsReadonly = {
			buttons: this.protectionUtilsService.checkButtonsProtection(
				this.config.body.buttonsReadonly,
				this.userProfile.configurations
			),
		};
	}

	private manageExecutionProvider = (obj: any) => {
		if (obj != null && obj.cleanSearch === true) {
			this.loadForm();
		}
	};

	private loadQueryParams(): void {
		let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
		this.idModello = +paramsMap.get("idModello");
	}

	private loadDettaglio = (): any => {
		return this.gestioneModelliService.getDettaglioModello(this.idModello).pipe(
			tap((result: any) => {
				this.sdkDettaglioModello.entita = result.data.entprinc
			}),
			switchMap((result: any) =>
				this.modelliService.getArgomentiDesc([`${result.data.entprinc}.${result.data.schema}`]).pipe(
					map((r) => ({
						...result.data,
						entprinc: `${result.data.entprinc} - ${r.argomentiDesc[0].c0earg} - ${r.argomentiDesc[0].c0edes}`,
					}))
				)
			)
		);
	};

	private elaborateDettaglio = (result: any) => {
   this.dettaglio = result;
			};

	private elaborateButtons = () => {
		this.buttonsSubj.next(this.buttons);
	};

	private handleError = (err: any) => {
		this.buttonsSubj.next(this.buttonsReadonly);
		return throwError(() => err);
	};

    private get sdkDettaglioModello(): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

	// #endregion
}
