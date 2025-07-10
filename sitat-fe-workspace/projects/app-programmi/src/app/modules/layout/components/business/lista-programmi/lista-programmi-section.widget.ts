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
import { GridUtilsService, ProtectionUtilsService, StazioneAppaltanteInfo, ValoreTabellato } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkRouterService,
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
import { cloneDeep, each, get, has, isEmpty, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, Subscription } from 'rxjs';

import { Constants } from '../../../../../app.constants';
import { ProgrammaBaseEntry } from '../../../../models/programmi/programmi.model';
import { DettaglioProgrammaStoreService } from '../dettaglio-programma/dettaglio-programma-store.service';
import { ListaProgrammiStoreService } from './lista-programmi-store.service';
import { ListaProgrammiDatasource } from './lista-programmi.datasource.service';

@Component({
    templateUrl: `lista-programmi-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaProgrammiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-programmi-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    private searchString: string = '';
    private stazioneAppaltante: string;
    private tipologia: string;
    private tipiProgramma: Array<ValoreTabellato>;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaProgrammiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private querySearchSubscription: Subscription;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo.codice;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQuerySearch();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        setTimeout(() => {
            this.initPerformers();
            this.initGrid();
            this.checkInfoBox();
        });
    }

    protected onDestroy(): void {
        this.unsubscribeQuerySearchSubscription();
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
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get listaProgrammiStore(): ListaProgrammiStoreService { return this.injectable(ListaProgrammiStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get dettaglioProgrammaStore(): DettaglioProgrammaStoreService { return this.injectable(DettaglioProgrammaStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                    this.loadBaseForm();
                    this.reloadGrid();
                }
            });
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item != null && item.code === 'modal') {
            if (item.data != null && item.data.reloadGrid === true) {
                this.loadBaseForm();
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
        this.querySearchSubscription = this.activatedRoute.queryParamMap.subscribe((queryParams: ParamMap) => {
            let paramsMap: IDictionary<any> = {};
            each(queryParams.keys, (key: string) => {
                paramsMap[key] = queryParams.get(key);
            });
            this.listaProgrammiStore.clear();
            this.searchString = get(paramsMap, 'searchString');
            if (this.searchString != null) {
                this.listaProgrammiStore.searchString = cloneDeep(this.searchString);
            }
            this.tipologia = get(paramsMap, 'tipologia');
            if (this.tipologia != null) {
                this.listaProgrammiStore.tipologia = cloneDeep(this.tipologia);
            }

            this.updateDatasource();
        });
    }

    private loadBaseForm(): void {
        this.searchString = '';
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

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                this.detailProgramma(toString(item.id), toString(item.tipoProg));
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                this.deleteProgramma(toString(item.id));
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                return item.idRicevuto ? true : false;
            },
            isNotPubblicato: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                return item.idRicevuto ? false : true;
            },
            isPubblicato: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                return item.idRicevuto ? true : false;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;                
                return item.idRicevuto ? false : true;
            },
            copiaPerAggiornamento: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                this.copiaPerAggiornamento(toString(item.id), toString(item.tipoProg));
            },
            trasferimentoProgramma: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'trasferimento-modal-widget',
                    componentConfig: {
                        ...get(this.config, 'trasferimentoProgrammaModalConfig'),
                        descrizioneProgramma: item.descrizioneBreve,
                        idProgramma: item.id,
                        referenteProgrammazione: item.referenteProgrammazione
                    }
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            },
            trasferimentoProgrammaHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ProgrammaBaseEntry = tempItem as ProgrammaBaseEntry;
                return !(this.userProfile.ruolo === 'A' || +this.userProfile.syscon === item.syscon);
            }
        }
    }

    private detailProgramma(idProgramma: string, tipologia: string): void {
        this.unsubscribeQuerySearchSubscription();
        this.provider.run('APP_PROGRAMMI_LISTA_PROGRAMMI', {
            action: 'DETAIL',
            idProgramma: idProgramma,
            messagesPanel: this.messagesPanel,
            tipologia
        }).subscribe();
    }

    private deleteProgramma(idProgramma: string): void {
        let func = this.deleteProgrammaConfirm(idProgramma);
        this.dialogConfig.open.next(func);
    }

    private deleteProgrammaConfirm(idProgramma: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_PROGRAMMI_LISTA_PROGRAMMI', {
                    action: 'DELETE',
                    idProgramma,
                    stazioneAppaltante: this.stazioneAppaltante,
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageDelete)
            );
        }
    }

    private manageDelete = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(ListaProgrammiDatasource, {
            searchString: this.searchString,
            stazioneAppaltante: this.stazioneAppaltante,
            tipologia: this.tipologia,
            tipiProgramma: this.tipiProgramma,
            syscon: this.userProfile.syscon,
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private unsubscribeQuerySearchSubscription(): void {
        if (isObject(this.querySearchSubscription)) {
            this.querySearchSubscription.unsubscribe();
        }
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    public copiaPerAggiornamento(idProgramma: string, tipologia: string): void {
        this.addSubscription(
            this.provider.run('APP_PROGRAMMI_LISTA_PROGRAMMI', {
                action: 'COPY-UPDATE',
                idProgramma,
                norma: 0,
                messagesPanel: this.messagesPanel
            }).subscribe((result: IDictionary<any>) => {
                let idProgrammaCopiato: number = result.idProgrammaCopiato;
                this.dettaglioProgrammaStore.clear();
                this.dettaglioProgrammaStore.idProgramma = toString(idProgrammaCopiato);
                this.dettaglioProgrammaStore.tipologia = tipologia;
                if (idProgrammaCopiato) {
                    let params: IDictionary<any> = {
                        idProgramma: toString(idProgrammaCopiato),
                        tipologia
                    };
                    this.routerService.navigateToPage('dett-prog-dati-generali-page', params);
                }
            })
        );
    }

    private updateDatasource() {
        if (this.datasource != null) {
            this.datasource.params = {
                ...this.datasource.params,
                tipologia: this.tipologia,
                searchString: this.searchString
            };
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
