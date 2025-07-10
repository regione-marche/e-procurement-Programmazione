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
import {
	IDictionary,
	SdkBusinessAbstractWidget,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkComboBoxItem,
	SdkDialogConfig,
	SdkFormBuilderConfiguration,
	SdkFormBuilderField,
	SdkFormBuilderInput,
	SdkMessagePanelService,
	SdkMessagePanelTranslate,
} from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, isEmpty, map as mapArray, set, toString } from "lodash-es";
import { BehaviorSubject, Observable, Subject, of, throwError } from "rxjs";
import { catchError, map, mergeMap, tap, switchMap } from "rxjs/operators";

import { HttpErrorResponse } from "@angular/common/http";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { CustomParamsFunction, ValoreTabellato } from "../../model/lib.model";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GestioneModelliService } from "../../services/gestione-modelli.service";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkDettaglioModelloStoreService } from "@maggioli/sdk-gestione-modelli";

@Component({
	selector: "sdk-modifica-parametro",
	templateUrl: "./sdk-modifica-parametro.component.html",
})
export class SdkModificaParametroComponent
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-modifica-parametro-section`;

	@ViewChild("messages") _messagesPanel: ElementRef;
	@ViewChild("infoBox") _infoBox: ElementRef;

	public config: any = {};

	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
	public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> =
		new Subject();
	public comboArgomentoConfigSub: BehaviorSubject<Array<SdkComboBoxItem>> =
		new BehaviorSubject([]);
	public dialogConfigObs: Observable<SdkDialogConfig>;
	public schemaTabelleDictionary: IDictionary<any>;
	private buttons: SdkButtonGroupInput;
	private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
	private formBuilderConfig: SdkFormBuilderConfiguration;
	public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
	private userProfile: UserProfile;
	private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private dialogConfig: SdkDialogConfig;
	private idModello: string;
	private idParametro: string;
	private dettaglio: any;
	private schema: string;
	private tabCod: string;
	// #endregion

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	// #region Hooks

	protected onInit(): void {
		// set update state
		this.setUpdateState(true);

		this.addSubscription(
			this.store
				.select<UserProfile>(SdkGestioneModelliConstants.USER_PROFILE_SELECT)
				.subscribe((userProfile: UserProfile) => {
					this.userProfile = userProfile;
				})
		);

		this.loadButtons();
		this.initDialog();
		this.loadQueryParams();
	}

	private loadQueryParams(): void {
		let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
		this.idModello = paramsMap.get("idModello");
		this.idParametro = paramsMap.get("idParametro");
	}

	protected onAfterViewInit(): void {
		this.schema = this.sdkDettaglioModello.entita;
		this.modelliService
			.getInizNuovoModello()
			.pipe(
				tap((result) => {
					this.schemaTabelleDictionary = result.data;
				}),
				switchMap(() => this.loadTabellati()),
				tap(this.elaborateTabellati),
				switchMap(() => this.loadDettaglio()),
				tap(this.elaborateDettaglio),
				switchMap(() => {
					if (this.dettaglio.tab) {
						return this.modelliService.getTabFromCode(this.dettaglio.tab);
					} else {
						return of({ data: null });
					}
				}),
				tap((result) => {
					if (result && result.data) {
						this.tabCod = result.data;
					}
				}),
				tap(() => this.checkInfoBox()),
				tap(() => this.loadForm())
			)
			.subscribe();
	}

	protected onDestroy(): void {}

	protected onConfig(config: any): void {
		if (config != null) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.isReady = true;
			});
		}
	}

	private loadDettaglio = (): Observable<any> => {
		return this.modelliService
			.getDettaglioParametro(this.idModello, this.idParametro)
			.pipe(
				catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
					let err: any = error.error;
					if (err != null && err.messages != null && err.messages.length > 0) {
						let messages: Array<SdkMessagePanelTranslate> = mapArray(
							err.messages,
							(one: string) => {
								let message: SdkMessagePanelTranslate = {
									message: `SDK-UTENTE.VALIDATORS.${one}`,
								};
								return message;
							}
						);
						this.sdkMessagePanelService.showError(this.messagesPanel, messages);
					}
					return throwError(() => error);
				})
			);
	};

	private elaborateDettaglio = (result: any) => {
		this.dettaglio = result.data;
	};

	protected onUpdateState(_state: boolean): void {}

	// #endregion

	// #region Getters

	private get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	private get sdkMessagePanelService(): SdkMessagePanelService {
		return this.injectable(SdkMessagePanelService);
	}

	private get formBuilderUtilsService(): FormBuilderUtilsService {
		return this.injectable(FormBuilderUtilsService);
	}

	private get protectionUtilsService(): ProtectionUtilsService {
		return this.injectable(ProtectionUtilsService);
	}

	private get tabellatiCacheService(): TabellatiCacheService {
		return this.injectable(TabellatiCacheService);
	}

	private get provider(): SdkProviderService {
		return this.injectable(SdkProviderService);
	}

	private get translateService(): TranslateService {
		return this.injectable(TranslateService);
	}

	private get modelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	private get activatedRoute(): ActivatedRoute {
		return this.injectable(ActivatedRoute);
	}

	private get sdkDettaglioModello(): SdkDettaglioModelloStoreService {
		return this.injectable(SdkDettaglioModelloStoreService);
	}

	// #endregion

	// #region Public

	public manageFormOutput(config: SdkFormBuilderConfiguration): void {
		this.formBuilderConfig = config;
	}

	public manageOutputField(field: SdkFormBuilderField): void {}

	public onButtonClick(button: SdkButtonGroupOutput): void {
		if (button != null && isEmpty(button.provider) === false) {
			let data: IDictionary<any> = {
				buttonCode: button.code,
				messagesPanel: this.messagesPanel,
				defaultFormBuilderConfig: this.defaultFormBuilderConfig,
				formBuilderConfig: this.formBuilderConfig,
				setUpdateState: this.setUpdateState,
				syscon: this.userProfile.syscon,
				idModello: this.idModello,
				idParametro: this.idParametro,
				isUpdate: true,
				schema: this.schema,
			};
			if (button.code === "back-to-lista-parametri") {
				this.back(button, data);
			} else {
				this.provider
					.run(button.provider, data)
					.subscribe(this.manageExecutionProvider);
			}
		}
	}

	// #endregion

	// #region Private

	private get messagesPanel(): HTMLElement {
		return this._messagesPanel != null
			? this._messagesPanel.nativeElement
			: undefined;
	}

	private get infoBox(): HTMLElement {
		return this._infoBox != null ? this._infoBox.nativeElement : undefined;
	}

	private loadTabellati = (): Observable<
		IDictionary<Array<ValoreTabellato>>
	> => {
		return this.tabellatiCacheService.getValoriTabellati(
			SdkGestioneModelliConstants.RICERCA_MODELLI_TABELLATI
		);
	};

	private elaborateTabellati = (
		result: IDictionary<Array<ValoreTabellato>>
	) => {
		this.valoriTabellati = result;
	};

	private checkInfoBox(): void {
		if (!isEmpty(this.config.infoBox)) {
			this.sdkMessagePanelService.clear(this.infoBox);
			this.sdkMessagePanelService.showInfoBox(this.infoBox, {
				message: this.config.infoBox,
			});
		}
	}

	private loadForm = () => {
		if (this.config != null) {
			this.markForCheck(() => {
				let formConfig: SdkFormBuilderConfiguration = {
					fields: cloneDeep(get(this.config, "body.fields")),
				};

				let customPopulateFunction: CustomParamsFunction = (
					field: SdkFormBuilderField,
					restObject: any,
					dynamicField: boolean
				) => {
					let mapping: boolean = true;

					let keyAny: any = get(restObject, field.mappingInput);
					let key: string =
						dynamicField === true ? field.data : toString(keyAny);
					if (
						!isEmpty(field.listCode) &&
						field.type === "TEXT" &&
						!isEmpty(key)
					) {
						[field, mapping] = this.formBuilderUtilsService.populateListCode(
							this.valoriTabellati,
							field,
							key,
							mapping
						);
					}

					if (field.code === "tabellato") {
						let comboItem: SdkComboBoxItem = {
							key: this.dettaglio.tab,
							value: this.dettaglio.tabDesc, 
						};
						set(field, "data", comboItem);
						mapping = false;
					}

					return {
						mapping,
						field,
					};
				};

				formConfig = this.protectionUtilsService.applyFormProtections(
					formConfig,
					this.userProfile.configurations,
					false,
					false,
					false
				);
				formConfig = this.formBuilderUtilsService.populateForm(
					formConfig,
					true,
					customPopulateFunction,
					this.dettaglio
				);

				this.defaultFormBuilderConfig = cloneDeep(formConfig);
				this.formBuilderConfig = formConfig;

				this.formBuilderConfigObs.next(formConfig);
			});
		}
	};

	private loadButtons(): void {
		this.buttons = {
			buttons: this.protectionUtilsService.checkButtonsProtection(
				this.config.body.buttons,
				this.userProfile.configurations
			),
		};
		this.buttonsSubj = new BehaviorSubject(this.buttons);
	}

	private manageExecutionProvider = (obj: any) => {
		if (obj != null && obj.cleanSearch === true) {
			this.loadForm();
		}
	};

	private initDialog(): void {
		this.dialogConfig = {
			header: this.translateService.instant("DIALOG.BACK-TITLE"),
			message: this.translateService.instant("DIALOG.BACK-TEXT"),
			acceptLabel: this.translateService.instant("DIALOG.CONFIRM-ACTION"),
			rejectLabel: this.translateService.instant("DIALOG.CANCEL-ACTION"),
			open: new Subject(),
		};
		this.dialogConfigObs = of(this.dialogConfig);
	}

	private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
		let func = this.backConfirm(button, data);
		this.dialogConfig.open.next(func);
	}

	private backConfirm(
		button: SdkButtonGroupOutput,
		data: IDictionary<any>
	): any {
		return () => {
			this.provider.run(button.provider, data).subscribe();
		};
	}

	// #endregion
	private filterValoriTabellati(codes: string[]): void {
		if (this.valoriTabellati) {
			Object.keys(this.valoriTabellati).forEach((key) => {
				this.valoriTabellati[key] = this.valoriTabellati[key].filter((item) =>
					codes.includes(item.codice)
				);
			});
		}
	}
}
