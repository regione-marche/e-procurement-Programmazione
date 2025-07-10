import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    GridUtilsService,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkModalConfig,
    SdkModalOutput,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, Subscription } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { AvvisoEntry, RicercaAvvisoForm } from '../../../../../models/avviso/avviso.model';
import { ListaAvvisiDatasource } from './lista-avvisi.datasource.service';

@Component({
    templateUrl: `lista-avvisi-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaAvvisiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-avvisi-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};

    private searchForm: RicercaAvvisoForm = {};
    private stazioneAppaltante: string;
    private userProfile: UserProfile;
    private tipiAvviso: Array<ValoreTabellato>;
    public avvisi: Array<AvvisoEntry>;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private querySubscription: Subscription;
    private storeSearchSubscription: Subscription;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaAvvisiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private form: RicercaAvvisoForm;
    private descrizione: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo.codice;
        }));

        this.addSubscription(this.store.select<RicercaAvvisoForm>(Constants.SEARCH_FORM_AVVISI_SELECT).subscribe((form: RicercaAvvisoForm) => {
            this.form = form;
            if (this.form != null && !isEmpty(this.form)) {
                this.loadDefaultForm();
            }
        }));

        this.loadButtons();
        this.loadQuerySearch();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_AVVISO).subscribe((tipiAvviso: Array<ValoreTabellato>) => {
            this.tipiAvviso = tipiAvviso;
            setTimeout(() => {
                this.initPerformers();
                this.initGrid();
            });
        });
    }

    protected onDestroy(): void {
        if (this.querySubscription != null) {
            this.querySubscription.unsubscribe();
        }
        this.unsubscribeSearch();
    }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) };

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.addSubscription(
                this.provider.run('APP_AVVISI_LISTA_AVVISI', {
                    buttonCode: button.code,
                    searchForm: this.searchForm,
                    stateForm: this.form
                }).subscribe((data: IDictionary<any>) => {
                    if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                        this.breadcrumbs.emit(this.config.breadcrumbs);
                        this.loadDefaultForm();
                        this.reloadGrid();
                    }
                })
            );
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item != null && item.code === 'modal') {
            if (item.data != null && item.data.reloadGrid === true) {
                this.loadDefaultForm();
                this.reloadGrid();
            }
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQuerySearch(): void {
        this.addSubscription(this.activatedRoute.queryParamMap.subscribe((queryParams: ParamMap) => {
            if (queryParams.has('descrizione')) {
                this.descrizione = queryParams.get('descrizione');
                this.loadDefaultForm();
            }
        }));

    }

    private loadDefaultForm(): void {
        let paramsMap: IDictionary<any> = {};
        if(this.stazioneAppaltante === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
            paramsMap = {
                stazioneAppaltante: this.searchForm.stazioneAppaltante,
                syscon: this.userProfile.syscon
            };
        } else{
            paramsMap = {
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon
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
        this.searchForm = paramsMap;
        this.updateDatasource();
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

    private manageDelete = (result: string) => {

        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AvvisoEntry = selectedRow.dataItem as AvvisoEntry;
                this.detailAvviso(item.numeroAvviso, item.stazioneAppaltante);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AvvisoEntry = selectedRow.dataItem as AvvisoEntry;
                this.deleteAvviso(item.numeroAvviso);
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: any = selectedRow.dataItem as any;
                return item.pubblicato === true;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: any = selectedRow.dataItem as any;
                return item.pubblicato === true ? false : true;
            },
            trasferimentoAvviso: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AvvisoEntry = selectedRow.dataItem as AvvisoEntry;
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'trasferimento-modal-widget',
                    componentConfig: {
                        ...get(this.config, 'trasferimentoAvvisoModalConfig'),
                        descrizioneAvviso: item.descrizione,
                        idAvviso: item.numeroAvviso
                    }
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            },
            trasferimentoAvvisoHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AvvisoEntry = selectedRow.dataItem as AvvisoEntry;
                return !(this.userProfile.ruolo === 'A' || +this.userProfile.syscon === item.syscon);
            }
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {
        if(this.stazioneAppaltante === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
            this.datasource = this.factory.create(ListaAvvisiDatasource, {
                searchForm: this.searchForm,
                tipiAvviso: this.tipiAvviso,
                stazioneAppaltante: this.searchForm.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel
            });
        } else{
            this.datasource = this.factory.create(ListaAvvisiDatasource, {
                searchForm: this.searchForm,
                tipiAvviso: this.tipiAvviso,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel
            });
        }
        if(this.stazioneAppaltante !== '*'){
            this.config.body.grid.columns.shift();            
        }

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private deleteAvviso(numeroAvviso: number): void {
        let func = this.deleteAvvisoConfirm(numeroAvviso);
        this.dialogConfig.open.next(func);

    }

    private deleteAvvisoConfirm(numeroAvviso: number): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_AVVISI_LISTA_AVVISI', {
                    action: 'DELETE',
                    idAvviso: numeroAvviso,
                    stazioneAppaltante: this.stazioneAppaltante,
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageDelete)
            );
        }
    }

    private detailAvviso(numeroAvviso: number,stazioneAppaltante: string): void {

        this.addSubscription(
            this.provider.run('APP_AVVISI_LISTA_AVVISI', {
                action: 'DETAIL',
                idAvviso: numeroAvviso,
                stazioneAppaltante: stazioneAppaltante
            }).subscribe()
        );
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private unsubscribeSearch(): void {
        if (this.storeSearchSubscription != null) {
            this.storeSearchSubscription.unsubscribe();
        }
    }

    private updateDatasource(): void {
        if (this.datasource != null) {
            if(this.stazioneAppaltante === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
                this.datasource.params = {
                    ...this.datasource.params,
                    searchForm: this.searchForm,
                    tipiAvviso: this.tipiAvviso,
                    stazioneAppaltante: this.searchForm.stazioneAppaltante,
                    syscon: this.userProfile.syscon
                };
            } else{
                this.datasource.params = {
                    ...this.datasource.params,
                    searchForm: this.searchForm,
                    tipiAvviso: this.tipiAvviso,
                    stazioneAppaltante: this.stazioneAppaltante,
                    syscon: this.userProfile.syscon
                };
            }
            
        }
    }

    // #endregion
}
