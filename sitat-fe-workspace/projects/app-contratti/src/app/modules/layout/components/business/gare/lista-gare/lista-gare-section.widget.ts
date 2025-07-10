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
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, has, isEmpty, isObject, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { GaraBaseEntry, RicercaGareForm } from '../../../../../models/gare/gare.model';
import { ListaGareDatasource } from './lista-gare.datasource.service';

@Component({
    templateUrl: `lista-gare-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaGareSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-gare-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};

    private searchForm: RicercaGareForm = {};
    private stazioneAppaltante: StazioneAppaltanteInfo;
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaGareDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private form: RicercaGareForm;
    private descrizione: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
           
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo;
        }));

        this.addSubscription(this.store.select<RicercaGareForm>(Constants.SEARCH_FORM_GARE_SELECT).subscribe((form: RicercaGareForm) => {
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
        this.loadMessages();
        this.tabellatiCacheService.getValoriTabellati(Constants.LISTA_GARE_TABELLATI).subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
            this.valoriTabellati = result;
            setTimeout(() => {
                this.initPerformers();
                this.initGrid();
            });
        });
    }

    protected onDestroy(): void { }

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

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.provider.run(button.provider, {
                buttonCode: button.code,
                searchForm: this.searchForm,
                stateForm: this.form,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                    this.breadcrumbs.emit(this.config.breadcrumbs);
                    this.loadDefaultForm();
                    this.reloadGrid();
                }
            });
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

        if(this.stazioneAppaltante.codice === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
            paramsMap = {
                stazioneAppaltante: this.searchForm.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                cfTecnico: this.userProfile.codiceFiscale
            };
        } else{
            paramsMap = {
                stazioneAppaltante: this.stazioneAppaltante.codice,
                syscon: this.userProfile.syscon,
                cfTecnico: this.userProfile.codiceFiscale
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
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                this.detailGara(item.codgara, item.smartCig);
            },
            dettaglioLotto: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                this.detailLotto(item.codgara, item.cigLotti, item.identificativoGara, item.smartCig, item.codlott);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                this.deleteGara(item.codgara, item.smartCig);
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                return item.deletable === false;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                return item.deletable === true;
            },
            cambioRup: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                this.sdkMessagePanelService.clear(this.messagesPanel);
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'trasferimento-modal-widget',
                    componentConfig: {
                        ...get(this.config, 'cambioRupModalConfig'),
                        oggettoGara: item.oggetto,
                        codGara: item.codgara,
                        trasferimento: false
                    }
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            },
            cambioReferente: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                this.sdkMessagePanelService.clear(this.messagesPanel);
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'trasferimento-modal-widget',
                    componentConfig: {
                        ...get(this.config, 'cambioReferenteModalConfig'),
                        oggettoGara: item.oggetto,
                        codGara: item.codgara,
                        trasferimento: true
                    }
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            },
            cambioRupHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: GaraBaseEntry = selectedRow.dataItem as GaraBaseEntry;
                return item.trasferimentoRUP === false;
            }           
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {
        this.searchForm.cfTecnico = this.userProfile.codiceFiscale;
        if(this.stazioneAppaltante.codice === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
            this.datasource = this.factory.create(ListaGareDatasource, {
                searchForm: this.searchForm,
                stazioneAppaltante: this.searchForm.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                valoriTabellati: this.valoriTabellati,
                messagesPanel: this.messagesPanel
            });
        } else{
            this.datasource = this.factory.create(ListaGareDatasource, {
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
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private mapSearchParams(paramsMap: IDictionary<any>): RicercaGareForm {
        let obj: RicercaGareForm = {};
        each(paramsMap, (value: any, key: string) => {
            if (value) {
                if (key === 'archiviate') {
                    set(obj, key, toString(value) === '1' ? true : false);
                } else if (key === 'modalitaRealizzazione' || key === 'proceduraContraenteLotto' || key === 'situazioneGara') {
                    set(obj, key, +value);
                } else {
                    set(obj, key, value);
                }
            }
        });
        return obj;
    }

    private deleteGara(codGara: number, smartCig: boolean): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        let func = this.deleteGaraConfirm(codGara, smartCig);
        this.dialogConfig.open.next(func);
    }

    private deleteGaraConfirm(codGara: number, smartCig: boolean): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_GARE', {
                    action: 'DELETE',
                    codGara,
                    smartCig,
                    stazioneAppaltante: this.stazioneAppaltante,
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageDelete)
            );
        }
    }

    private detailGara(codGara: number, smartCig: boolean): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.addSubscription(
            this.provider.run('APP_GARE_LISTA_GARE', {
                action: 'DETAIL',
                codGara,
                smartCig
            }).subscribe()
        );
    }

    private detailLotto(codGara: number, cig:string, identificativoGara:string, smartCig: boolean, codlott: number): void {
        let action = 'DETAIL-LOTTO';
        if(cig != null && cig.length > 10){
            action = 'LISTA-LOTTI';
        }
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.addSubscription(
            this.provider.run('APP_GARE_LISTA_GARE', {
                action,
                codGara,
                cig,
                identificativoGara,
                smartCig,
                codlott
            }).subscribe()
        );
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadMessages(): void {
        const queryParams: ParamMap = this.activatedRoute.snapshot.queryParamMap;

        if (queryParams.has('message')) {
            const message: string = queryParams.get('message');
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message
                }
            ])
        }
    }

    private updateDatasource(): void {
        if (this.datasource != null) {
            if(this.stazioneAppaltante.codice === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
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

    // #endregion
}
