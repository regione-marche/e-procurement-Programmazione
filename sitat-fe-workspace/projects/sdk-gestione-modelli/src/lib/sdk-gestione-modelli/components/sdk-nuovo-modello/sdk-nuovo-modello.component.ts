import {
	ChangeDetectorRef,
	Component,
	ElementRef,
	HostBinding,
	Injector,
	OnDestroy,
	OnInit,
	ViewChild,
	ViewEncapsulation,
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
	SdkComboboxConfig,
	SdkDialogConfig,
	SdkFormBuilderConfiguration,
	SdkFormBuilderField,
	SdkFormBuilderInput,
	SdkMessagePanelService,
} from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import {
	cloneDeep,
	each,
	find,
	get,
	has,
	isEmpty,
	isEqual,
	isObject,
	result,
	set,
	toString,
} from "lodash-es";
import { BehaviorSubject, Observable, of, Subject, forkJoin, throwError } from "rxjs";
import { filter, map, take, tap, mergeMap, catchError } from "rxjs/operators";

import {
	ArgomentoDesc,
	CustomParamsFunction,
	ValoreTabellato,
} from "../../model/lib.model";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GestioneModelliService } from "../../services/gestione-modelli.service";
import { ModelliUtils } from "../modelli-utils";

@Component({
	templateUrl: "./sdk-nuovo-modello.component.html",
})
export class SdkNuovoModelloComponent
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-nuovo-modello-section`;

	@ViewChild("messages") _messagesPanel: ElementRef;
	@ViewChild("infoBox") _infoBox: ElementRef;

	public config: any = {};

	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
	public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> =
		new Subject();
	public comboArgomentoConfigSub: BehaviorSubject<Array<SdkComboBoxItem>> =
		new BehaviorSubject([]);
	public dialogConfigObs: Observable<SdkDialogConfig>;
	public schemaTabelleDictionary: IDictionary<any> = {};
	private buttons: SdkButtonGroupInput;
	private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
	private formBuilderConfig: SdkFormBuilderConfiguration;
	public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
	private userProfile: UserProfile;
	private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private dialogConfig: SdkDialogConfig;
	private defaultSchema: { [schema: string]: string } = {};
	private defaultArgomento: { [argomento: string]: string } = {};
	public listSchema: Array<SdkComboBoxItem> = [];
	private listArgomentoDesc: Array<SdkComboBoxItem> = [];
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
	}
	protected onAfterViewInit(): void {
		forkJoin({
			defaultSchema: this.modelliService.getDefaultSchema().pipe(
				catchError((error) => {
					if (error) {
						return of({ schema: "" });
					}
					return throwError(() => error);
				})
			),
			defaultArgomento: this.modelliService.getDefaultArgomento(),
			inizNuovoModello: this.modelliService.getInizNuovoModello(),
			tabellati: this.loadTabellati(),
		})
			.pipe(
				tap((results) => {
					({
						defaultSchema: this.defaultSchema,
						defaultArgomento: this.defaultArgomento,
						schemaTabelleDictionary: this.schemaTabelleDictionary,
						listSchema: this.listSchema,
					} = ModelliUtils.initializeData(results));
				}),
				tap((results) => this.elaborateTabellati(results.tabellati)),
				mergeMap((results) => {
					const argomenti = ModelliUtils.createArgomentiArray(
						results.inizNuovoModello.data
					);
					return this.modelliService.getArgomentiDesc(argomenti);
				}),
				tap(
					(r) =>
						(this.listArgomentoDesc = ModelliUtils.mapArgomentoDesc(
							r.argomentiDesc
						))
				),
				map(() => this.checkInfoBox()),
				map(() => this.loadForm())
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

	// #endregion

	// #region Public

	public manageFormOutput(config: SdkFormBuilderConfiguration): void {
		this.formBuilderConfig = config;
	}

	public manageOutputField(field: SdkFormBuilderField): void {
		if (isObject(field)) {
			if (field.code === "schema") {
				const list = ModelliUtils.createComboBoxListGenerator(
					this.schemaTabelleDictionary,
					this.listArgomentoDesc
				)({ data: { key: field.data.key } });
				this.comboArgomentoConfigSub.next(list);
			} else if (field.code === "filtroentita") {
				this.formBuilderDataSubject.next({
					code: "descrizione-filtroentita",
					data: field.data,
				});
			}
		}
	}

	public onButtonClick(button: SdkButtonGroupOutput): void {
		if (button != null && isEmpty(button.provider) === false) {
			let data: IDictionary<any> = {
				buttonCode: button.code,
				messagesPanel: this.messagesPanel,
				defaultFormBuilderConfig: this.defaultFormBuilderConfig,
				formBuilderConfig: this.formBuilderConfig,
				setUpdateState: this.setUpdateState,
				syscon: this.userProfile.syscon,
				idProfilo: this.userProfile.configurations.idProfilo,
			}
			if(button.code === "back-to-lista-modelli") {
				this.back(button, data);
			}else{

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
					const key: string = dynamicField
						? field.data
						: toString(get(restObject, field.mappingInput));
					return this.populateField(field, key);
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
					false,
					customPopulateFunction
				);

				this.defaultFormBuilderConfig = cloneDeep(formConfig);
				this.formBuilderConfig = formConfig;

				this.formBuilderConfigObs.next(formConfig);
			});
		}
	};

	private populateField(
		field: SdkFormBuilderField,
		key: string
	): { field: SdkFormBuilderField; mapping: boolean } {
		let mapping = true;
		switch (field.code) {
			case "isOU50": 
				const enabled =
					this.userProfile.abilitazioni.includes("ou50");

				set(field, "data", enabled ? 1 : 0);
				mapping = false;
				break
			
			case "schema":
				field.itemsProvider = () => of(this.listSchema);
				const defaultSchema: SdkComboBoxItem = this.listSchema.find(
					(item) => item.key === this.defaultSchema?.schema
				) || { key: "", value: "" };
				set(field, "data", defaultSchema);
				mapping = false;
				break;
			case "argomento":
				field.itemsProvider = () => this.comboArgomentoConfigSub;
				const defaultArgomento =
					(
						this.schemaTabelleDictionary?.[this.defaultSchema?.schema] || []
					).find((item) => item === this.defaultArgomento?.argomento) || "";
				set(field, "data", { key: defaultArgomento, value: defaultArgomento });
				mapping = false;
				break;
			case "personale":
				set(field, "data", {
					key: "1",
					value: this.translateService.instant("COMBOBOX.SI"),
				});
				mapping = false;
				break;
			case "predisposto":
				set(field, "data", {
					key: "1",
					value: this.translateService.instant("COMBOBOX.SI"),
				});
				mapping = false;
				break;
			case "pdf":
				set(field, "data", {
					key: "0",
					value: this.translateService.instant("COMBOBOX.NO"),
				});
				mapping = false;
				break;
			case "tipoDocumento":
				const tipo = this.valoriTabellati.TipoDocumentoModello[0].codice;
				const comboItem: SdkComboBoxItem = {
					key: tipo,
					value: this.valoriTabellati.TipoDocumentoModello.find(
						(td) => td.codice === tipo
					)?.descrizione,
				};
				set(field, "data", comboItem);
				mapping = false;
				break;
			default:
				break;
		}
		if (field.type === "DOCUMENT") {
			const maxSize = ModelliUtils.extractMaxFileSize(
				this.valoriTabellati.DimensioneDocumento[0].descrizione
			);
			set(field, "maxFileSize", maxSize);
		}
		if (!isEmpty(field.listCode) && field.type === "TEXT" && !isEmpty(key)) {
			[field, mapping] = this.formBuilderUtilsService.populateListCode(
				this.valoriTabellati,
				field,
				key,
				mapping
			);
		}
		return { field, mapping };
	}

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

	private initializeData(results: {
		defaultSchema: any;
		defaultArgomento: any;
		inizNuovoModello: { data: IDictionary<any> };
		tabellati: any;
	}): void {
		this.elaborateTabellati(results.tabellati);
		this.defaultSchema = results.defaultSchema;
		this.defaultArgomento = results.defaultArgomento;
		this.schemaTabelleDictionary = results.inizNuovoModello.data;
		this.listSchema = Object.keys(this.schemaTabelleDictionary).map((key) => ({
			key,
			value: `${key} - ${ModelliUtils.whichDescrizione(key)}`,
		}));
	}

	// #endregion
}
