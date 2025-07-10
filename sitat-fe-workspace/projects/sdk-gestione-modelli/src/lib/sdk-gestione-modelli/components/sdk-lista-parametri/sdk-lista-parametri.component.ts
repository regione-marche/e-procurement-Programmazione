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
	SdkDialogConfig,
	SdkMenuTab,
	SdkMessagePanelService,
} from "@maggioli/sdk-controls";
import {
	SdkDatasourceService,
	SdkTableConfig,
	SdkTableRowDto,
	SdkTableToolbarActionPerfomer,
} from "@maggioli/sdk-table";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, has, isEmpty, isObject, remove } from "lodash-es";
import { BehaviorSubject, Observable, of, Subject, tap } from "rxjs";

import { RicercaModelliForm, UserDTO } from "../../model/gestione-utenti.model";
import { ValoreTabellato } from "../../model/lib.model";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GridUtilsService } from "../../utils/grid-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";

import { SdkGestioneUtentiConstants } from "projects/sdk-gestione-utenti/src/lib/sdk-gestione-utenti/sdk-gestione-utenti.constants";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { SdkListaModelliDatasource } from "../sdk-lista-modelli/sdk-lista-modelli.datasource.service";
import { SdkListaParametriDatasource } from "./sdk-lista-parametri.datasource.service";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { SdkDettaglioModelloStoreService } from "@maggioli/sdk-gestione-modelli";
import { GestioneModelliService } from "../../services/gestione-modelli.service";

@Component({
	templateUrl: `sdk-lista-parametri.component.html`,
})
export class SdkListaParametriComponent
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-lista-parametri-section`;

	@ViewChild("messages") _messagesPanel: ElementRef;

	@ViewChild("infoBox") _infoBox: ElementRef;

	public config: any = {};
	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
	public configSub = new BehaviorSubject<SdkTableConfig>(null);
	public resetTable: Subject<boolean> = new Subject();
	public dialogConfigObs: Observable<SdkDialogConfig>;
	private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private datasource: SdkListaParametriDatasource;
	private userProfile: UserProfile;
	private buttons: SdkButtonGroupInput;
	private buttonsReadonly: SdkButtonGroupInput;
	private gridConfig: SdkTableConfig;
	private performers: IDictionary<SdkTableToolbarActionPerfomer>;
	private dialogConfig: SdkDialogConfig;
	private idModello: string;
	private menuTabs: Array<SdkMenuTab>;
	// #endregion

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	// #region Hooks

	protected onInit(): void {
		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.USER_PROFILE_SELECT)
				.subscribe((userProfile: UserProfile) => {
					this.userProfile = userProfile;
				})
		);
		this.loadQueryParams();
		this.loadButtons();
		this.initDialog();
		this.tabellatiCacheService
			.getValoriTabellati(SdkGestioneModelliConstants.LISTA_PARAMETRI_TABELLATI)
			.subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
				this.valoriTabellati = result;
				setTimeout(() => {
					this.initPerformers();
					this.initGrid();
				});
			});
	}

	protected onAfterViewInit(): void {
		this.refreshTabs();
		this.checkInfoBox();
		this.initPerformers();
		this.initGrid();
	
		
	}

	protected onDestroy(): void {}

	protected onConfig(config: any): void {
		if (isObject(config)) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.menuTabs = this.config.menuTabs;
				this.isReady = true;
			});
		}
	}

	protected onUpdateState(state: boolean): void {}

	private loadQueryParams(): void {
		let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
		this.idModello = paramsMap.get("idModello");
	}

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
	private get modelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

	private get activatedRoute(): ActivatedRoute {
		return this.injectable(ActivatedRoute);
	}
    private get sdkDettaglioModello(): SdkDettaglioModelloStoreService { return this.injectable(SdkDettaglioModelloStoreService) }

	// #endregion

	// #region Public

	public onButtonClick(button: SdkButtonGroupOutput): void {
		if (isObject(button) && isEmpty(button.provider) === false) {
			this.provider
				.run(button.provider, {
					buttonCode: button.code,
					messagesPanel: this.messagesPanel,
					codProfiloAttivo: this.userProfile.configurations.idProfilo,
					syscon: this.userProfile.syscon,
					idModello: this.idModello,
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
						};
						this.reloadGrid();
					}
				});
		}
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
			dettaglio: (selectedRow: {
				rowIndex: number;
				dataItem: SdkTableRowDto;
			}) => {
				let tempItem: unknown = selectedRow.dataItem as unknown;
				let item: any = tempItem as any;
				this.detailParametro(item.id.idModello, item.id.progr);
			},
			delete: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
				let tempItem: unknown = selectedRow.dataItem as unknown;
				let item = tempItem;
				this.deleteParametro(get(item, "id.idModello"), get(item, "id.progr"));
			},
			spostaSu: (selectedRow: {
				rowIndex: number;
				dataItem: SdkTableRowDto;
			}) => {
				const item: any = selectedRow.dataItem;
				this.spostaParametro(item.id.idModello, item.id.progr, "UP");
			},
			spostaSuHidden: (selectedRow) => {
				let isFirstRow = false;
				this.datasource.result$.subscribe(res => {
					const items = res.data;
					isFirstRow = items.length > 0 && items[0].id === selectedRow.dataItem.id;
				});
				return isFirstRow;
			},
			spostaGiu: (selectedRow: {
				rowIndex: number;
				dataItem: SdkTableRowDto;
			}) => {
				const item: any = selectedRow.dataItem;
				this.spostaParametro(item.id.idModello, item.id.progr, "DOWN");
			},
			spostaGiuHidden: (selectedRow) => {
				let isLastRow = false;
				this.datasource.result$.subscribe(res => {
					const items = res.data;
					isLastRow = items.length > 0 && items[items.length - 1].id === selectedRow.dataItem.id;
				});
				return isLastRow;
			},
		};
	}

	private detailParametro(idModello: number, idParametro): void {
		this.provider
			.run("SDK_GESTIONE_MODELLI_LISTA", {
				action: "DETAIL-PARAMETRO",
				idModello,
				idParametro,
				setUpdateState: this.setUpdateState,
				messagesPanel: this.messagesPanel,
			})
			.subscribe();
	}

	private deleteParametro(idModello: number, idParametro: number): void {
		let func = this.deleteModelloConfirm(idModello, idParametro);
		this.dialogConfig.open.next(func);
	}

	private deleteModelloConfirm(idModello: number, idParametro: number): any {
		return () => {
			this.provider
				.run("SDK_GESTIONE_PARAMETRO", {
					action: "DELETE",
					idModello,
					idParametro,
					messagesPanel: this.messagesPanel,
				})
				.subscribe(this.manageProviderResponse);
		};
	}

	private spostaParametro(
		idModello: number,
		idParametro: number,
		direction: string
	): void {
		this.provider
			.run("SDK_GESTIONE_PARAMETRO", {
				action: "MOVE",
				direction,
				idModello,
				idParametro,
				messagesPanel: this.messagesPanel,
			})
			.subscribe(this.manageProviderResponse);
	}

	private manageProviderResponse = (result: any) => {
		if (isObject(result) && get(result, "reload") === true) {
			this.reloadGrid();
		}
	};

	private reloadGrid(): void {
		this.resetTable.next(true);
	}

	private initGrid(): void {
		this.datasource = this.factory.create(SdkListaParametriDatasource, {
			idModello: this.idModello,
			messagesPanel: this.messagesPanel,
			valoriTabellati: this.valoriTabellati,
		});

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
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

	

	private refreshTabs(): void {
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

	

	// #endregion
}
