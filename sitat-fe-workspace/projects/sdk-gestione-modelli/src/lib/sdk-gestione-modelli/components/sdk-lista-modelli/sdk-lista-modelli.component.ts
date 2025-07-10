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
	SdkBusinessAbstractWidget,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkDialogConfig,
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
import { BehaviorSubject, Observable, of, Subject, throwError, forkJoin } from "rxjs";
import { catchError, switchMap, map, tap } from "rxjs/operators";
import { HttpErrorResponse } from "@angular/common/http";

import { RicercaModelliForm, UserDTO } from "../../model/gestione-utenti.model";
import { ValoreTabellato } from "../../model/lib.model";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { GridUtilsService } from "../../utils/grid-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkListaModelliDatasource } from "./sdk-lista-modelli.datasource.service";
import { SdkGestioneUtentiConstants } from "projects/sdk-gestione-utenti/src/lib/sdk-gestione-utenti/sdk-gestione-utenti.constants";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { GestioneModelliService } from "../../services/gestione-modelli.service";
import { TabellatiService } from "../../services/tabellati/tabellati.service";
@Component({
	templateUrl: `sdk-lista-modelli.component.html`,
})
export class SdkListaModelliComponent
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-lista-modelli-section`;

	@ViewChild("messages") _messagesPanel: ElementRef;

	@ViewChild("infoBox") _infoBox: ElementRef;

	public config: any = {};
	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
	public configSub = new BehaviorSubject<SdkTableConfig>(null);
	public resetTable: Subject<boolean> = new Subject();
	public dialogConfigObs: Observable<SdkDialogConfig>;
	private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private searchForm: RicercaModelliForm;
	private userProfile: UserProfile;
	private buttons: SdkButtonGroupInput;
	private buttonsReadonly: SdkButtonGroupInput;
	private gridConfig: SdkTableConfig;
	private datasource: SdkListaModelliDatasource;
	private performers: IDictionary<SdkTableToolbarActionPerfomer>;
	private dialogConfig: SdkDialogConfig;
	opzioniUtenti: any;

	// #endregion

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	// #region Hooks

	protected onInit(): void {
		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.SEARCH_FORM_MODELLI_SELECT)
				.subscribe((form: RicercaModelliForm) => {
					this.searchForm = form;
				})
		);

		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.USER_PROFILE_SELECT)
				.subscribe((userProfile: UserProfile) => {
					this.userProfile = userProfile;
				}) 
		);

		this.loadButtons();
		this.initDialog();
	}

	protected onAfterViewInit(): void {
    this.checkInfoBox();
    
    const codProfiloAttivo = this.userProfile?.configurations?.idProfilo;
    
    forkJoin({
        tabellati: this.tabellatiCacheService.getValoriTabellati(SdkGestioneModelliConstants.LISTA_MODELLI_TABELLATI),
        opzioniUtenti: this.tabellatiService.listaOpzioniUtenti(codProfiloAttivo)
    })
    .pipe(
        tap(result => {
            this.valoriTabellati = result.tabellati;
            this.opzioniUtenti = result.opzioniUtenti.utentiOptions;
            this.initPerformers();
            this.initGrid();
        }),
        catchError(error => {
            this.sdkMessagePanelService.showError(this.messagesPanel, [
                { message: "ERRORS.UNEXPECTED-ERROR" }
            ]);
            return throwError(() => error);
        })
    )
    .subscribe();
}

	protected onDestroy(): void {}

	protected onConfig(config: any): void {
		if (isObject(config)) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.isReady = true;
			});
		}
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
	private get tabellatiService(): TabellatiService {
		return this.injectable(TabellatiService);
	}
	private get modelliService(): GestioneModelliService {
		return this.injectable(GestioneModelliService);
	}

  private get sdkBase64Helper(): SdkBase64Helper { return this.injectable(SdkBase64Helper) }
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
				this.detailModello(item.idModello);
			},
			delete: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
				let tempItem: unknown = selectedRow.dataItem as unknown;
				let item = tempItem;
				this.deleteModello(get(item, "idModello"));
			},
			download: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
				let tempItem: unknown = selectedRow.dataItem as unknown;
				let item = tempItem;
				this.sdkMessagePanelService.clear(this.messagesPanel);
				this.downloadModello(get(item, "idModello"));
			}
		}}

private downloadModello(idModello: string): void {

  this.modelliService.downloadDocumento(idModello)
    .pipe(
      switchMap(contenutoDocumento => {
        return this.modelliService.getDettaglioModello(+idModello)
          .pipe(
            map(dettaglioModello => ({ contenutoDocumento, dettaglioModello })),
            catchError(error => {
              this.sdkMessagePanelService.showError(this.messagesPanel, [
                { message: "ERRORS.DOCUMENTO-DETTAGLI-ERROR" }
              ]);
              return throwError(() => error);
            })
          );
      }),
      catchError((error: HttpErrorResponse) => {
        const errorMessage = error.error?.errorData === "DOCUMENTO_NON_TROVATO" 
          ? "ERRORS.DOCUMENTO-NON-TROVATO" 
          : "ERRORS.UNEXPECTED-ERROR";
          
        this.sdkMessagePanelService.showError(this.messagesPanel, [
          { message: errorMessage }
        ]);
        return throwError(() => error);
      })
    )
    .subscribe({
      next: ({ contenutoDocumento, dettaglioModello }) => {
        if (!dettaglioModello?.data) {
          this.sdkMessagePanelService.showError(this.messagesPanel, [
            { message: "ERRORS.DOCUMENTO-DATI-MANCANTI" }
          ]);
          return;
        }
        
        this.processDocumentDownload(contenutoDocumento, dettaglioModello.data.nomefile);
      }
    });
}


private processDocumentDownload(documentContent: string, filename: string): void {
  try {
    const arrayBuffer = this.sdkBase64Helper.base64ToArrayBuffer(documentContent);
    const blob = new Blob([arrayBuffer], {
      type: 'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
    });
    
    const downloadUrl = window.URL.createObjectURL(blob);
    const downloadLink = document.createElement('a');
    
    downloadLink.href = downloadUrl;
    downloadLink.download = filename;
    
    document.body.appendChild(downloadLink);
    downloadLink.click();
    
    window.URL.revokeObjectURL(downloadUrl);
    downloadLink.remove();
    
  } catch (error) {
    this.sdkMessagePanelService.showError(this.messagesPanel, [
      { message: "ERRORS.ERRORE-CREAZIONE-LINK-DOWNLOAD-DOCUMENTO" }
    ]);
  }
}
			
	
	private detailModello(idModello: number): void {
		this.provider
			.run("SDK_GESTIONE_MODELLI_LISTA", {
				action: "DETAIL",
				idModello,
				setUpdateState: this.setUpdateState,
				messagesPanel: this.messagesPanel,
			})
			.subscribe();
	}

	private deleteModello(idModello: number): void {
		let func = this.deleteModelloConfirm(idModello);
		this.dialogConfig.open.next(func);
	}

	private deleteModelloConfirm(idModello: number): any {
		return () => {
			this.provider
				.run("SDK_GESTIONE_MODELLI_LISTA", {
					action: "DELETE",
					idModello,
					messagesPanel: this.messagesPanel,
				})
				.subscribe(this.manageProviderResponse);
		};
	}

	private manageProviderResponse = (result: string) => {
		if (isObject(result) && get(result, "reload") === true) {
			this.reloadGrid();
		}
	};

	private reloadGrid(): void {
		this.resetTable.next(true);
	}

	private initGrid(): void {
	let visible: boolean = this.userProfile && (
            this.userProfile?.abilitazioni?.includes('ou50') );
		if (this.searchForm == null) {
			this.searchForm = {};
			this.searchForm.codProfiloAttivo =
				this.userProfile.configurations.idProfilo;
			this.searchForm.syscon = this.userProfile.syscon;
		}

		this.datasource = this.factory.create(SdkListaModelliDatasource, {
			searchForm: this.searchForm,
			messagesPanel: this.messagesPanel,
			valoriTabellati: this.valoriTabellati,
			utentiOptions: this.opzioniUtenti,
		});

		this.gridConfig = cloneDeep(this.config.body.grid);
		
		if(!visible){
			remove(this.gridConfig.columns, { field: "owner" });
			remove(this.gridConfig.columns, { field: "dispDesc" });
			remove(this.gridConfig.columns, { field: "personaleDesc" });
		}
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

	// #endregion
}
