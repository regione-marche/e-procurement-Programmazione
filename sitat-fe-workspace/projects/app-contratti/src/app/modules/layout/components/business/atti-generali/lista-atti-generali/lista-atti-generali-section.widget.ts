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
    SdkFormBuilderConfiguration,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, find, get, isEmpty, isEqual, isObject, remove, toString } from 'lodash-es';
import { BehaviorSubject, Subject, Subscription } from 'rxjs';

import { AttoGeneraleEntry, RicercaAttiGeneraliForm } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { Constants } from '../../../../../../app.constants';
import { ListaAttiGeneraliDatasource } from './lista-atti-generali.datasource.service';

@Component({
    templateUrl: `lista-atti-generali-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaAttiGeneraliSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-atti-generali-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};

    private searchForm: RicercaAttiGeneraliForm = {};
    private stazioneAppaltante: string;
    private userProfile: UserProfile;
    private tipologieAtto: Array<ValoreTabellato>;
    public attiGenerali: Array<AttoGeneraleEntry>;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private querySubscription: Subscription;
    private storeSearchSubscription: Subscription;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaAttiGeneraliDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Subject<SdkDialogConfig> = new Subject<SdkDialogConfig>();

    private motivazioneDialogConfig: SdkDialogConfig;
    public motivazioneDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private form: RicercaAttiGeneraliForm;
    private descrizione: string;

    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;

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

        this.addSubscription(this.store.select<RicercaAttiGeneraliForm>(Constants.SEARCH_FORM_ATTI_GENERALI_SELECT).subscribe((form: RicercaAttiGeneraliForm) => {
            this.form = form;
            if (this.form != null && !isEmpty(this.form)) {
                this.loadDefaultForm();
            }
        }));

        this.loadButtons();
        this.loadQuerySearch();
        this.initDialog('');
    }

    protected onAfterViewInit(): void {
        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_ATTO_GENERALE).subscribe((tipologie: Array<ValoreTabellato>) => {
            this.tipologieAtto = tipologie;
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                searchForm: this.searchForm,
                stateForm: this.form,
                setUpdateState: this.setUpdateState
            };

            this.provider.run(button.provider, data).subscribe();
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

    private initDialog(action: string): SdkDialogConfig {
        let header: string;
        let message: string;
    
        switch(action) {
            case 'DELETE':
                header = this.translateService.instant('DIALOG.DELETE-TITLE');
                message = this.translateService.instant('DIALOG.DELETE-TEXT');
                break;
            case 'DELETE-DATA-PUBB-SISTEMA':
                header = this.translateService.instant('DIALOG.ANNULLA-PROGR-PUBBLICAZIONE');
                message = this.translateService.instant('DIALOG.DELETE-TEXT-DATA-PUBB-SISTEMA-FUTURA');
                break;
            case 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-FIRST':
                header = this.translateService.instant('DIALOG.ANNULLA-PROGR-PUBBLICAZIONE-MOTIVAZIONE-FIRST-HEADER');
                message = this.translateService.instant('DIALOG.ANNULLA-PROGR-PUBBLICAZIONE-MOTIVAZIONE-FIRST-MESSAGE');
                break;
            case 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND':
                this.motivazioneDialogConfig = {
                    header: this.translateService.instant('DIALOG.MOTIVAZIONE-ANNULLAMENTO-TITLE'),
                    message: this.translateService.instant('DIALOG.MOTIVAZIONE-ANNULLAMENTO-MESSAGE'),
                    acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
                    rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
                    motivazioneLabel: this.translateService.instant('DIALOG.MOTIVAZIONE-ANNULLAMENTO-LABEL'),
                    open: new Subject()
                };
                this.motivazioneDialogConfigObs.next(this.motivazioneDialogConfig);
            default:
                header = 'DIALOG.DELETE-TITLE';
                message = 'DIALOG.DELETE-TEXT';
        }
    
        if(isEqual(action, 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND')) {
            return this.motivazioneDialogConfig;
        }
        else {
            const dialogConfig: SdkDialogConfig = {
                header: this.translateService.instant(header),
                message: this.translateService.instant(message),
                acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
                rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
                open: new Subject<any>()
            };
        
            this.dialogConfigObs.next(dialogConfig);
            return dialogConfig;
        }
    }

    private manageDelete = (result: string) => {

        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;

                let tipologiaAtto: ValoreTabellato = find(this.tipologieAtto, (one: ValoreTabellato) => one.descrizione === toString(item.tipologia));
                let tipoAtto: string = tipologiaAtto.codice;
                let annullato: boolean = item.annullato;
                
                let newForm: RicercaAttiGeneraliForm = {
                    stazioneAppaltante: item.stazioneAppaltante,
                    idAtto: item.idAtto,
                    rup: [item.rup],
                    syscon: this.userProfile.syscon,
                    tipologia: +tipoAtto,
                    dataPubbSistema: item.dataPubbSistema,
                    annullato: annullato
                }

                this.detailAttoGenerale(newForm);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                this.deleteAttoGenerale(item?.idAtto);
            },
            deleteAttoFutura: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                this.sbiancaDataPubbSistema(item.idAtto);
            },
            deleteAttoPassata: (selectedRow: {rowIndex: number, dataItem: SdkTableRowDto}) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                this.sbiancaPubblicazioneMotivazioneFirst(item.idAtto);
            },
            // Visibilità tasti "Azione"
            deleteAttoHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(this.userProfile?.abilitazioni?.includes(Constants.ABILITAZIONE_AMMINISTRATORE) || item?.dataPubbSistema === null || item?.dataPubbSistema === undefined);
            },
            deleteFuturaHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(item?.dataPubbSistema && new Date(item?.dataPubbSistema) > new Date());
            },
            deletePassataHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(item?.dataPubbSistema && new Date(item?.dataPubbSistema) < new Date()) || item?.annullato === true;
            },
            pubblicatoHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(!!item?.dataPubbSistema && new Date(item?.dataPubbSistema) <= new Date() && item?.annullato === false);
            },
            programmatoHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(!!item?.dataPubbSistema && new Date(item?.dataPubbSistema) > new Date() && item?.annullato === false);
            },
            annullatoHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(item?.annullato === true && !!item?.dataPubbSistema && !!item?.motivoCanc && !!item?.dataCanc)
            },
            compilazioneHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: AttoGeneraleEntry = selectedRow.dataItem as AttoGeneraleEntry;
                return !(!item?.dataPubbSistema && !item?.motivoCanc && item?.annullato !== true && !item?.dataCanc)
            }
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {
        if(this.stazioneAppaltante === '*' && this.searchForm.stazioneAppaltante != null && !isEmpty(this.searchForm.stazioneAppaltante)){
            this.datasource = this.factory.create(ListaAttiGeneraliDatasource, {
                searchForm: this.searchForm,
                tipologieAtto: this.tipologieAtto,
                stazioneAppaltante: this.searchForm.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel
            });
        } else{
            this.datasource = this.factory.create(ListaAttiGeneraliDatasource, {
                searchForm: this.searchForm,
                tipologieAtto: this.tipologieAtto,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel
            });
        }
        if(this.stazioneAppaltante !== '*'){
            //Rimuovo la colonna con il nome dell'ente selezionato.
            remove(this.config.body.grid.columns.find((item) => item.field === 'stazioneAppaltante'));
        }

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.fullDateTimeFormat, this.userProfile.configurations);

        //Settare il dateFormat per la colonna dataScadenza. 
        let dataScadenzaColumn : any = this.gridConfig?.columns?.find((item) => item.field === 'dataScadenza')?.viewer?.params;
        dataScadenzaColumn.format = 'dd/MM/yyyy';
        
        this.configSub.next(this.gridConfig);
    }

    private deleteAttoGenerale(idAtto: string): void {
        const dialogConfig = this.initDialog('DELETE');
        const func = this.deleteAttoGeneraleFactory(idAtto);
        dialogConfig.open.next(func);
    }

    private deleteAttoGeneraleFactory(idAtto: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                    action: 'DELETE',
                    idAtto: idAtto,
                    codProfilo: this.userProfile?.configurations?.idProfilo
                }).subscribe(this.manageDelete)
            );
        }
    }

    private sbiancaDataPubbSistema(idAtto: string): void {
        const dialogConfig = this.initDialog('DELETE-DATA-PUBB-SISTEMA');
        const func = this.sbiancaDataPubbSistemaFactory(idAtto);
        dialogConfig.open.next(func);
    }

    private sbiancaDataPubbSistemaFactory(idAtto: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                    action: 'DELETE-DATA-PUBB-SISTEMA',
                    idAtto: idAtto,
                    codProfilo: this.userProfile?.configurations?.idProfilo
                }).subscribe(this.manageDelete)
            );
        }
    }

    private sbiancaPubblicazioneMotivazioneFirst(idAtto: string): void {
        const dialogConfigFirst = this.initDialog('ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-FIRST');
        const funcFirst = this.sbiancaPubblicazioneMotivazioneSecond(idAtto);
        dialogConfigFirst.open.next(funcFirst);
    }

    private sbiancaPubblicazioneMotivazioneSecond(idAtto: string): any {
        return () => {
            const dialogConfig = this.initDialog('ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND');
            const func = this.sbiancaPubblicazioneMotivazioneFactory(idAtto);
            dialogConfig.open.next(func);
        }
    }

    private sbiancaPubblicazioneMotivazioneFactory(idAtto: string): any {
        return (motivazione: string) => {
            if(isEmpty(motivazione)){
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'LISTA-ATTI-GENERALI.VALIDATORS.MOTIVAZIONE-OBBLIGATORIA'
                    }
                ]);
            }
            else {
                this.addSubscription(
                    this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                        action: 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND',
                        idAtto: idAtto,
                        motivoCanc: motivazione,
                        codProfilo: this.userProfile?.configurations?.idProfilo
                    }).subscribe(this.manageDelete)
                );
            }
        }
    }

    private detailAttoGenerale(attoGenerale: RicercaAttiGeneraliForm): void {

        this.addSubscription(
            this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                action: 'DETAIL',
                searchForm: attoGenerale
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
                    tipologieAtto: this.tipologieAtto,
                    stazioneAppaltante: this.searchForm.stazioneAppaltante,
                    syscon: this.userProfile.syscon
                };
            } else{
                this.datasource.params = {
                    ...this.datasource.params,
                    searchForm: this.searchForm,
                    tipologieAtto: this.tipologieAtto,
                    stazioneAppaltante: this.stazioneAppaltante,
                    syscon: this.userProfile.syscon
                };
            }
        }
    }

    // #endregion
}
