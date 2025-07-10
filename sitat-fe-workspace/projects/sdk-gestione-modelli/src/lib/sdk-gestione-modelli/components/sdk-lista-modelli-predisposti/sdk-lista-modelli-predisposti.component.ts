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
	SdkBase64Helper,
	SdkBreadcrumbsMessageService,
	SdkBusinessAbstractWidget,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkDialogConfig,
	SdkDocumentUtils,
	SdkMessagePanelService,
	SdkModalConfig,
} from "@maggioli/sdk-controls";
import {
	SdkDatasourceService,
	SdkTableConfig,
	SdkTableRowDto,
	SdkTableToolbarActionPerfomer,
} from "@maggioli/sdk-table";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, has, isEmpty, isObject, remove } from "lodash-es";
import { BehaviorSubject, EMPTY, Observable, of, Subject } from "rxjs";
import { catchError, tap, map } from "rxjs/operators";

import { RicercaModelliForm, UserDTO } from "../../model/gestione-utenti.model";
import { StazioneAppaltanteInfo, ValoreTabellato } from "../../model/lib.model";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GridUtilsService } from "../../utils/grid-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkListaModelliPredispostiDatasource } from "./sdk-lista-modelli-predisposti.datasource.service";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { SdkBreadcrumbsModelliStoreService } from "./sdk-breadcrumbsmodelli-store.service";
import { GestioneModelliService } from "../../services/gestione-modelli.service";

@Component({
	templateUrl: `sdk-lista-modelli-predisposti.component.html`,
})
export class SdkListaModelliPredispostiComponent
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-lista-modelli-predisposti-section`;

	@ViewChild("messages") _messagesPanel: ElementRef;

	@ViewChild("infoBox") _infoBox: ElementRef;

	public config: any = {};
	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> =
		new BehaviorSubject<SdkButtonGroupInput>(null);
	public configSub = new BehaviorSubject<SdkTableConfig>(null);
	public resetTable: Subject<boolean> = new Subject();
	public dialogConfigObs: Observable<SdkDialogConfig>;
	private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private searchForm: RicercaModelliForm;
	private userProfile: UserProfile;
	private buttons: SdkButtonGroupInput;
	private gridConfig: SdkTableConfig;
	private datasource: SdkListaModelliPredispostiDatasource;
	private performers: IDictionary<SdkTableToolbarActionPerfomer>;
	private dialogConfig: SdkDialogConfig;
	private entita: string;
	public entitaToShow: string;
	private modalConfig: SdkModalConfig<any, void, any>;
	public modalConfigObs: Subject<SdkModalConfig<any, void, any>> =
	new Subject();
	private identita: [];
	public stazioneAppaltanteInfo: StazioneAppaltanteInfo;
	
	// #endregion
	
	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}
	
	// #region Hooks
	
	protected onInit(): void {
		this.searchForm = {};
		
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
		this.loadQueryParams();
		this.loadButtons();
		this.initDialog();
		this.tabellatiCacheService
		.getValoriTabellati(
				SdkGestioneModelliConstants.LISTA_MODELLI_PREDISPOST_TABELLATI
			)
			.subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
				this.valoriTabellati = result;
				setTimeout(() => {
					this.initPerformers();
					this.initGrid();
				});
			});
	}

	private loadQueryParams(): void {
		let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
		this.entita = paramsMap.get("entita");
		this.gestioneModelliService.getArgomentiDesc([ `${paramsMap.get("entita")}.${paramsMap.get("schema")}` ])
		.pipe(
			tap(r => { 
				let x = r.argomentiDesc[0].c0earg === this.entita.toUpperCase() ?  "" : `(${this.entita})` 
				this.entitaToShow = `[PER ${r.argomentiDesc[0].c0edes} ${r.argomentiDesc[0].c0earg} ${x}]`.toUpperCase(); 
			}),
		)
		.subscribe();
		this.searchForm.entitaPrincipale = paramsMap.get("entita");
		this.searchForm.codProfiloAttivo =
		this.userProfile.configurations.idProfilo;
		this.searchForm.syscon = this.userProfile.syscon;
		this.identita = this.sdkBreadcrumbsModelliStoreService.idEntita;
	}

	protected onAfterViewInit(): void {
		this.checkInfoBox();
		this.initPerformers();
		this.initGrid();
	}

	protected onDestroy(): void {}

	protected onConfig(config: any): void {
		if (isObject(config)) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.setupUI();
				this.modalConfig = {
					code: "modal",
					title: "",
					openSubject: new Subject(),
				};
				this.modalConfigObs.next(this.modalConfig);
				this.isReady = true;
			});
		}
	}

	private setupUI() {
		let configBreadcrumbs = this.config.breadcrumbs; //.slice(1);
		this.config.breadcrumbs = [
			...this.sdkBreadcrumbsModelliStoreService.parentBreadcrumbs,
			...configBreadcrumbs,
		];
		this.breadcrumbs.emit(this.config.breadcrumbs);
	}

	protected onUpdateState(state: boolean): void {}
	
	// #endregion
	
	// #region Getters
	
	private get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	private get provider(): SdkProviderService {
		return this.injectable(SdkProviderService);
	}

	private get factory(): SdkDatasourceService {
		return this.injectable(SdkDatasourceService);
	}

	private get gridUtilsService(): GridUtilsService {
		return this.injectable(GridUtilsService);
	}

	private get translateService(): TranslateService {
		return this.injectable(TranslateService);
	}

	private get sdkMessagePanelService(): SdkMessagePanelService {
		return this.injectable(SdkMessagePanelService);
	}

	private get protectionUtilsService(): ProtectionUtilsService {
		return this.injectable(ProtectionUtilsService);
	}

	private get tabellatiCacheService(): TabellatiCacheService {
		return this.injectable(TabellatiCacheService);
	}
	
	private get activatedRoute(): ActivatedRoute {
		return this.injectable(ActivatedRoute);
	}
	
	protected get breadcrumbs(): SdkBreadcrumbsMessageService {
		return this.injectable(SdkBreadcrumbsMessageService);
	}
	
	protected get sdkBreadcrumbsModelliStoreService(): SdkBreadcrumbsModelliStoreService {
		return this.injectable(SdkBreadcrumbsModelliStoreService);
	}
	
	private get gestioneModelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	private get sdkBase64Helper(): SdkBase64Helper {
		return this.injectable(SdkBase64Helper);
	}
	
	// #endregion
	
	// #region Public
	
	public onButtonClick(button: SdkButtonGroupOutput): void {
		if (isObject(button) && isEmpty(button.provider) === false) {
			this.provider
			.run(button.provider, {
					buttonCode: button.code,
					messagesPanel: this.messagesPanel,
					searchForm: this.searchForm,
					codProfiloAttivo: this.userProfile.configurations.idProfilo,
					syscon: this.userProfile.syscon,
				})
				.subscribe((data: IDictionary<any>) => {
					if (has(data, "reloadGrid") && get(data, "reloadGrid") === true) {
						this.reloadGrid();
					} else if (
						has(data, "cleanSearch") &&
						get(data, "cleanSearch") === true
					) {
						this.datasource.params = {
							...this.datasource.params,
							searchForm: this.searchForm,
						};
						this.reloadGrid();
					}
				});
		}
	}
	public closeModal(){

	}

	// #endregion

	// #region Private
	
	private get messagesPanel(): HTMLElement {
		return isObject(this._messagesPanel)
			? this._messagesPanel.nativeElement
			: undefined;
	}

	private initDialog(): void {
		this.dialogConfig = {
			header: this.translateService.instant("DIALOG.DELETE-TITLE"),
			message: this.translateService.instant("DIALOG.DELETE-TEXT"),
			acceptLabel: this.translateService.instant("DIALOG.CONFIRM-ACTION"),
			rejectLabel: this.translateService.instant("DIALOG.CANCEL-ACTION"),
			open: new Subject(),
		};
		this.dialogConfigObs = of(this.dialogConfig);
	}

	private initPerformers(): void {
		this.performers = {
			componi: (selectedRow: {
				rowIndex: number;
				dataItem: SdkTableRowDto;
			}) => {
				let tempItem: unknown = selectedRow.dataItem as unknown;
				let item: any = tempItem as any;
				this.gestioneModelliService
					.getListaParametri(item.idModello, null)
					.subscribe((response: any) => {
						this.modalConfig = {
							...this.modalConfig,
							component: "sdk-componi-parametri-modal-widget",
							componentConfig: {
								idModello: item.idModello,
								buttons: get(this.config.modalConfig, "buttons"),
								parameters: response?.data, 
								identita: this.identita,
								entita: this.entita,
							},
						};
						this.modalConfigObs.next(this.modalConfig);
						this.modalConfig.openSubject.next(true);
					});
			},
		};
	}

	private reloadGrid(): void {
		this.resetTable.next(true);
	}

	private initGrid(): void {
		console.log("INIT GRID", this.searchForm);
		if (this.searchForm == null) {
			this.searchForm = {};
			this.searchForm.codProfiloAttivo =
				this.userProfile.configurations.idProfilo;
			this.searchForm.syscon = this.userProfile.syscon;
		}
		this.datasource = this.factory.create(
			SdkListaModelliPredispostiDatasource,
			{
				searchForm: this.searchForm,
				messagesPanel: this.messagesPanel,
				valoriTabellati: this.valoriTabellati,
			}
		);

		this.gridConfig = cloneDeep(this.config.body.grid);
		this.gridConfig = this.gridUtilsService.parseDescriptor(
			this.gridConfig,
			this.datasource,
			this.performers,
			this.config.locale.dateFormat,
			this.userProfile.configurations
		);

		this.configSub.next(this.gridConfig);
	}

	private get infoBox(): HTMLElement {
		return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
	}

	private checkInfoBox(): void {
		if (!isEmpty(this.config.infoBox)) {
			this.sdkMessagePanelService.clear(this.infoBox);
			this.sdkMessagePanelService.showInfoBox(this.infoBox, {
				message: this.config.infoBox,
			});
		}
	}

	private loadButtons(): void {
		this.buttons = {
			buttons: this.protectionUtilsService.checkButtonsProtection(
				this.config.body.buttons,
				this.userProfile.configurations
			),
		};

		this.buttonsSubj.next(this.buttons);
	}

	// #endregion
}
