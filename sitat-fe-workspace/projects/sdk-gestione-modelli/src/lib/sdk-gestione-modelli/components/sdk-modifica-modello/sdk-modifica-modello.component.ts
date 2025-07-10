import {
	ChangeDetectorRef,	Component,
	ElementRef,
	HostBinding,
	Injector,
	OnDestroy,
	OnInit,
	ViewChild,
} from "@angular/core";
import {
	IDictionary,
	SdkBase64Helper,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkComboBoxItem,
	SdkDialogConfig,
	SdkDocumentItem,
	SdkFormBuilderConfiguration,
	SdkFormBuilderField,
	SdkMessagePanelService,
	SdkMessagePanelTranslate,
	SdkTextOutput,
} from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import {
	cloneDeep,
	get,
	isEmpty,
	map as mapArray,
	isObject,
	toString,
	set,
} from "lodash-es";
import {
	BehaviorSubject,
	forkJoin,
	Observable,
	of,
	Subject,
	throwError,
} from "rxjs";
import { catchError, map, max, mergeMap, tap } from "rxjs/operators";

import { CustomParamsFunction, ValoreTabellato } from "../../model/lib.model";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GestioneModelliService } from "../../services/gestione-modelli.service";
import { HttpErrorResponse } from "@angular/common/http";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { ModelliUtils } from "../modelli-utils";
import { SdkBaseDetailSectionWidget } from "@maggioli/sdk-appaltiecontratti-base";

@Component({
	templateUrl: "./sdk-modifica-modello.component.html",
})
export class SdkModificaModelloComponent
	extends SdkBaseDetailSectionWidget
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-modifica-modello-section`;

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
	protected buttons: SdkButtonGroupInput;
	protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
	protected formBuilderConfig: SdkFormBuilderConfiguration;
	protected userProfile: UserProfile;
	protected valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	protected dialogConfig: SdkDialogConfig;
	private dettaglio: any;
	private idModello: any;
	private currentSchema: string;
	private existingPreviousFile: boolean = true;
	private defaultSchema: { [schema: string]: string } = {};
	private defaultArgomento: { [argomento: string]: string } = {};
	public listSchema: Array<SdkComboBoxItem> = [];
	private listArgomentoDesc: Array<SdkComboBoxItem> = [];
	private allowDownload: boolean;
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
		this.allowDownload = this.getProtezioniValue(
				"COLS.VIS.GENEWEB.W_DOCDIG.DIGOGG",
				false
			);
	}

	protected onAfterViewInit(): void {
		forkJoin({
			defaultSchema: this.modelliService.getDefaultSchema(),
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
				mergeMap(this.loadDettaglio),
				map(this.elaborateDettaglio),
				map(() => this.checkInfoBox()),
				map(() => this.loadForm())
			)
			.subscribe();
	}

	protected getEntity() {
		return this.dettaglio;
	}

	protected getValoriTabellatiConst(): string[] {
		return [];
	}

	private loadDettaglio = (): Observable<any> => {
		return this.modelliService.getDettaglioModello(this.idModello).pipe(
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

	private loadQueryParams(): void {
		let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
		this.idModello = +paramsMap.get("idModello");
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

	protected get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	protected get sdkMessagePanelService(): SdkMessagePanelService {
		return this.injectable(SdkMessagePanelService);
	}

	

	private get tabellatiCacheService(): TabellatiCacheService {
		return this.injectable(TabellatiCacheService);
	}

	protected get provider(): SdkProviderService {
		return this.injectable(SdkProviderService);
	}

	protected get translateService(): TranslateService {
		return this.injectable(TranslateService);
	}

	private get modelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	protected get activatedRoute(): ActivatedRoute {
		return this.injectable(ActivatedRoute);
	}

	private get sdkBase64Helper(): SdkBase64Helper {
		return this.injectable(SdkBase64Helper);
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
			}
			if (field.code === "documents-list") {
				let fieldExistingDoc =
					this.formBuilderConfig.fields[0].fieldSections[8];
				let fieldUpladDoc = this.formBuilderConfig.fields[0].fieldSections[9];
				fieldExistingDoc.visible = false;
				fieldUpladDoc.visible = true;
				this.formBuilderConfigObs.next(this.formBuilderConfig);
			}
		}
	}

	public manageFormClick(config: SdkTextOutput): void {
		if (isObject(config)) {
			if (config.code === "nomeFile" && this.allowDownload) {
				this.modelliService
					.downloadDocumento(this.idModello)
					.subscribe((text) => {
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
					});
			}
		}
	}

	private fixComboArgomento(key) {
		if (this.currentSchema === key) {
			return;
		} else {
			this.currentSchema = key;
		}

		let listaTAbelle = this.schemaTabelleDictionary[key];
		let list: Array<SdkComboBoxItem> = new Array();
		listaTAbelle.forEach(function (key) {
			list.push({
				key: toString(key),
				value: toString(key),
			});
		});
		this.comboArgomentoConfigSub.next(list);
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
				isUpdate: true,
				idModello: this.idModello,
			};
			if(button.code === "back-to-dettaglio") {
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

	protected get messagesPanel(): HTMLElement {
		return this._messagesPanel != null
			? this._messagesPanel.nativeElement
			: undefined;
	}

	protected get infoBox(): HTMLElement {
		return this._infoBox != null ? this._infoBox.nativeElement : undefined;
	}

	protected loadTabellati = (): Observable<
		IDictionary<Array<ValoreTabellato>>
	> => {
		return this.tabellatiCacheService.getValoriTabellati(
			SdkGestioneModelliConstants.RICERCA_MODELLI_TABELLATI
		);
	};

	protected elaborateTabellati = (
		result: IDictionary<Array<ValoreTabellato>>
	) => {
		this.valoriTabellati = result;
	};

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
	){

					if (field.code === "schema") {
						let list: Array<SdkComboBoxItem> = [];
						Object.keys(this.schemaTabelleDictionary).forEach((dictKey) => {
							list.push({
								key: toString(dictKey),
								value:
									toString(dictKey) +
									" - " +
									ModelliUtils.whichDescrizione(dictKey),
							});
						});
						field.itemsProvider = () => of(list as Array<SdkComboBoxItem>);
						if (this.dettaglio.schema !== null) {
							field.data = {
								key: this.dettaglio.schema,
								value:
									this.dettaglio.schema +
									" - " +
									ModelliUtils.whichDescrizione(this.dettaglio.schema),
							};
						}
						mapping = false;
					}

					if (field.code === "argomento") {
						field.itemsProvider = () => {
							return this.comboArgomentoConfigSub;
						};
						if (this.dettaglio.entprinc !== null) {
							this.fixComboArgomento(this.dettaglio.schema);
							field.data = {
								key: this.dettaglio.entprinc,
								value: this.dettaglio.entprinc,
							};
						}
						mapping = false;
					}
					if (field.code === "isOU50") {
						const enabled = this.userProfile.abilitazioni.includes('ou50') ;
						set(field, "data", enabled ? 1 : 0);
						mapping = false;
					}

					if (field.code === "tipoDocumento") {
						const tipo = this.dettaglio.tipo.padStart(3, " ");
						let comboItem: SdkComboBoxItem = {
							key: tipo,
							value: this.valoriTabellati.TipoDocumentoModello.find(
								(tipoDocumento) => tipoDocumento.codice === tipo
							).descrizione,
						};
						set(field, "data", comboItem);
						mapping = false;
					}
					if (field.type === "DOCUMENT") {
						let maxSize = (field.maxFileSize = ModelliUtils.extractMaxFileSize(
							this.valoriTabellati.DimensioneDocumento[0].descrizione
						));
						set(field, "maxFileSize", maxSize);
					} else if (field.type === "DOCUMENTS-LIST") {
						let existingDocuments: Array<any> = get(
							this.dettaglio,
							field.mappingInput
						);
						this.allowDownload || (field.clickable = false);
						let docItem: SdkDocumentItem = existingDocuments[0];
						docItem.tipoFile = this.dettaglio.nomefile.split(".").pop();
						docItem.fileDownloadCallback = () => {
							return this.modelliService.downloadDocumento(this.idModello)
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
								);
						};
						let sdkExistingDocuments: Array<any> = [];
						sdkExistingDocuments.push(docItem);
						set(field, "documents", sdkExistingDocuments);
						mapping = false;
					}

				return mapping;	
	};

	protected loadButtons(): void {
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

	protected backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
		return () => {
			this.provider.run(button.provider, data).subscribe();
		}
	}
}

// #endregion
