import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GridUtilsService, ProtectionUtilsService, StazioneAppaltanteInfo, TabellatiCacheService, ValoreTabellato } from "@maggioli/app-commons";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkRouterService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkMessagePanelService } from "@maggioli/sdk-controls";
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from "@maggioli/sdk-table";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, each, find, get, isArray, isEmpty, isObject, set, toString } from "lodash-es";
import { Constants } from "projects/app-contratti/src/app/app.constants";
import { RicercaSchedePcp, RicercaSchedePcpEntry } from "projects/app-contratti/src/app/modules/models/schede-trasmesse-pcp/schede-trasmesse-pcp.model";
import { BehaviorSubject, Observable, Subject, map, of } from "rxjs";
import { ListaSchedeTrasmessePcpDatasource } from "./lista-schede-trasmesse.datasource.service";

@Component({
    templateUrl: './lista-schede-trasmesse-pcp-section.widget.html',
    styleUrls: []
})
export class ListaSchedeTrasmessePcpSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    // #region Variables

    @HostBinding('class') classNames = `lista-schede-trasmesse-pcp-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public resetTable: Subject<boolean> = new Subject();
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    private searchForm: RicercaSchedePcp = {};
    private buttons: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private idProfilo: string;
    private stazioneAppaltante: StazioneAppaltanteInfo;
    private descrizione: string;
    private gridConfig: SdkTableConfig;
    private datasource: ListaSchedeTrasmessePcpDatasource;
    private form: RicercaSchedePcp;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo;
        }));

        this.addSubscription(this.store.select<RicercaSchedePcp>(Constants.SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_SELECT).subscribe((form: RicercaSchedePcp) => {
            this.form = form;

            if(this.form?.uffInt != null){
                const codeinArray: string[] = (this.form.uffInt as any[]).map(item => item.codein);
                
                this.form = {
                    ...this.form, 
                    uffInt: codeinArray
                }
            }

            if (this.form != null && !isEmpty(this.form)) {
                this.loadDefaultForm();
            }
            
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();

        this.tabellatiCacheService.getValoriTabellati(Constants.LISTA_SCHEDE_TABELLATI).subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
            this.valoriTabellati = result;
            setTimeout(() => {
                this.initPerformers();
                this.initGrid();
            });
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            });
        }
    }

    protected onUpdateState(_state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    //#region Private 

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadQueryParams(): void {
        this.syscon = +this.userProfile.syscon;
        this.idProfilo = this.userProfile?.configurations?.idProfilo;
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(
                this.infoBox, 
                {
                    message: this.config.infoBox
                }
            );
        }
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: RicercaSchedePcpEntry = selectedRow.dataItem as RicercaSchedePcpEntry;
                this.detailLotto(item.codGara, toString(item.codLotto));
            }           
        }
    }

    private detailLotto(codGara: string, codLotto: string): void {
        this.provider.run('APP_GARE_LISTA_GARE', {
            action: 'DETAIL-LOTTO-SCHEDA',
            codGara: codGara,
            codLotto,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    private loadDefaultForm(): void {
        let paramsMap: IDictionary<any> = {};

        if(this.stazioneAppaltante.codice === '*' && this.searchForm.uffInt != null && !isEmpty(this.searchForm.uffInt)){
            paramsMap = {
                stazioneAppaltante: null,
                syscon: this.userProfile.syscon,
                cfTecnico: this.userProfile.codiceFiscale,
                uffInt: this.searchForm.uffInt
            };
        } else{
            paramsMap = {
                stazioneAppaltante: this.stazioneAppaltante.codice,
                syscon: this.userProfile.syscon,
                cfTecnico: this.userProfile.codiceFiscale,
                uffInt: this.searchForm?.uffInt
            };
        }

        if (this.descrizione != null) {
            paramsMap = {
                ...paramsMap,
                descrizione: this.descrizione
            }
        } else if (this.form != null) {
            paramsMap = {
                ...paramsMap,
                ...this.form
            };
        }
        this.searchForm = this.mapSearchParams(paramsMap);
        this.updateDatasource();
    }

    private updateDatasource(): void {
        if (this.datasource != null) {
            if(this.stazioneAppaltante.codice === '*' && this.searchForm.uffInt != null && !isEmpty(this.searchForm.uffInt)){
                this.datasource.params = {
                    ...this.datasource.params,
                    searchForm: this.searchForm,
                    syscon: this.userProfile.syscon
                };
            } else{
                this.datasource.params = {
                    ...this.datasource.params,
                    searchForm: this.searchForm,
                    stazioneAppaltante: this.stazioneAppaltante.codice,
                    syscon: this.userProfile.syscon
                };
            }
        }
    }

    private initGrid(): void {
        if(this.stazioneAppaltante.codice === '*' && this.searchForm.uffInt != null && !isEmpty(this.searchForm.uffInt)){
            this.datasource = this.factory.create(ListaSchedeTrasmessePcpDatasource, {
                searchForm: this.searchForm,
                stazioneAppaltante: this.searchForm.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                valoriTabellati: this.valoriTabellati,
                messagesPanel: this.messagesPanel
            });
        } else {
            this.datasource = this.factory.create(ListaSchedeTrasmessePcpDatasource, {
                searchForm: this.searchForm,
                stazioneAppaltante: this.stazioneAppaltante.codice,
                syscon: this.userProfile.syscon,
                valoriTabellati: this.valoriTabellati,
                messagesPanel: this.messagesPanel
            });
        }
        
        if(this.stazioneAppaltante.codice !== '*'){
            this.config.body.grid.columns.shift();            
        }
        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.fullDateTimeFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private mapSearchParams(paramsMap: IDictionary<any>): RicercaSchedePcp {
        let obj: RicercaSchedePcp = {};
        each(paramsMap, (value: any, key: string) => {
            if (value) {
                set(obj, key, value);
            }
        });
        return obj;
    }

    //#endregion

    //#region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState
            };

            this.provider.run(button.provider, data).subscribe();
        }
    }

    //#endregion
}
