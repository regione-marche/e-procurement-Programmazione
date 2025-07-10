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
    FormBuilderUtilsService,
    GridUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    ResponseResult,
    StazioneAppaltanteInfo,
} from '@maggioli/app-commons';
import {
    IDictionary,
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
    SdkFormBuilderField,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableCellButtonViewerComponent, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isArray, isEmpty, isNull, isObject, isUndefined, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ListaInterventiFilter, ProgrammaEntry } from '../../../../..//models/programmi/programmi.model';
import { InterventoBaseEntry } from '../../../../../models/interventi/interventi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { DettaglioProgrammaInterventiDatasource } from './dett-prog-interventi.datasource.service';

@Component({
    templateUrl: `dett-prog-interventi-section.widget.html`,
    styleUrls: ['dett-prog-interventi-section.widget.scss'],
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioProgrammaInterventiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dett-prog-interventi-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private modalConfig: SdkModalConfig<any, void, any>;

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public modalConfigSubj: Subject<SdkModalConfig<any, void, any>> = new Subject();

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: DettaglioProgrammaInterventiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private filterOutSubj: BehaviorSubject<ListaInterventiFilter> = new BehaviorSubject(null);

    private idProgramma: string;
    private tipologia: string;
    private menuTabs: Array<SdkMenuTab>;
    private programma: ProgrammaEntry;
    private currentFilter: ListaInterventiFilter;

    private dialogConfig: SdkDialogConfig;

    public dialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    private dialogConfigExport: SdkDialogConfig;


    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.filterOutSubj.subscribe((currentFilter: ListaInterventiFilter) => {
            this.currentFilter = currentFilter;
        }));

        this.loadButtons();
        this.loadQueryParams();

        this.refreshTabs();
    }

    protected onAfterViewInit(): void {
        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
            this.programma = result;
            setTimeout(() => {
                this.initPerformers();
                this.initButtons();
                this.initGrid();
                this.checkInfoBox();
            });
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (button != null) {
            if (button.code === 'export') {
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject(),
                    component: 'export-interventi-acquisti-modal-widget',
                    componentConfig: {
                        ...this.config.exportInterventiAcquistiModalConfig,
                        idProgramma: this.idProgramma,
                        codiceProgramma: this.programma.idProgramma,
                        tipologia: this.tipologia,
                        currentFilter: this.currentFilter
                    }
                };
                this.modalConfigSubj.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            } else if (isEmpty(button.provider) === false) {
                if (button.code === 'report-sogg') {
                    this.reportSogg();
                } else {
                    this.provider.run(button.provider, {
                        buttonCode: button.code,
                        messagesPanel: this.messagesPanel,
                        idProgramma: this.idProgramma,
                        tipologia: this.tipologia
                    }).subscribe((data: IDictionary<any>) => {
                        if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                            this.reloadGrid();
                        }
                    });
                }
            }
        }
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfig);
    }

    private initDialogExport(): void {
        this.dialogConfigExport = {
            header: this.translateService.instant('DIALOG.EXPORT-SOGG.TITLE'),
            message: this.translateService.instant('DIALOG.EXPORT-SOGG.TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.EXPORT-SOGG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.EXPORT-SOGG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfigExport);
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: InterventoBaseEntry = tempItem as InterventoBaseEntry;
                this.detailIntervento(toString(item.id));
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: InterventoBaseEntry = tempItem as InterventoBaseEntry;
                this.deleteIntervento(toString(item.id));
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                return this.programma.idRicevuto !== undefined && this.programma.idRicevuto !== null;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                return isUndefined(this.programma.idRicevuto) || isNull(this.programma.idRicevuto);
            }
        }
    }

    private detailIntervento(idIntervento: string): void {
        this.provider.run('APP_PROGRAMMI_LISTA_INTERVENTI', {
            action: 'DETAIL',
            idProgramma: this.idProgramma,
            tipologia: this.tipologia,
            idIntervento: idIntervento,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    private deleteIntervento(idIntervento: string): void {
        this.initDialog();
        let func = this.deleteInterventoConfirm(idIntervento);
        this.dialogConfig.open.next(func);
    }

    private deleteInterventoConfirm(idIntervento: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_PROGRAMMI_LISTA_INTERVENTI', {
                    action: 'DELETE',
                    idProgramma: this.idProgramma,
                    idIntervento,
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageReload)
            );
        }
    }


    private reportSogg(): void {
        this.initDialogExport();
        let func = this.reportSoggConfirm();
        this.dialogConfigExport.open.next(func);
    }

    private reportSoggConfirm(): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_PROGRAMMI_LISTA_INTERVENTI', {
                    action: 'REPORT',
                    idProgramma: this.idProgramma,
                    tipologia: this.tipologia,
                    messagesPanel: this.messagesPanel
                })
            );
        }
    }

    private manageReload = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(DettaglioProgrammaInterventiDatasource, {
            idProgramma: this.idProgramma,
            tipologia: this.tipologia,
            programma: this.programma,
            messagesPanel: this.messagesPanel,
            filterOut: this.filterOutSubj
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        if (this.gridConfig.filterable === true) {
            this.gridConfig.filter.fields = this.loadFilterForm(this.gridConfig.filter.fields);
        }
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, { tipologia: this.tipologia });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private showSoggReportFactory(idProgramma: string): () => Observable<any> {
        return () => {
            return this.programmiService.showSoggReport(idProgramma);
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

    private initButtons(): void {
        let factory = this.showSoggReportFactory(this.programma.id.toString());
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ResponseResult<string>) => {
            if (this.programma.idRicevuto) {
                if (result.data == "false") {
                    this.buttonsReadonly.buttons = this.buttonsReadonly.buttons.filter(e => e.code !== 'report-sogg');
                }
                this.buttonsSubj.next(this.buttonsReadonly);
            } else {
                if (result.data == "false") {
                    this.buttons.buttons = this.buttons.buttons.filter(e => e.code !== 'report-sogg');
                }
                this.buttonsSubj.next(this.buttons);
            }
        });

    }

    private loadFilterForm(fields: Array<SdkFormBuilderField>): Array<SdkFormBuilderField> {
        if (isArray(fields)) {
            let formConfig: SdkFormBuilderConfiguration = {
                fields
            };

            formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, undefined, undefined, {
                annoInizio: this.programma.annoInizio,
                tipologia: this.tipologia,
                stazioneAppaltante: this.stazioneAppaltanteInfo
            });

            return formConfig.fields;
        }
        return fields;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
    }

    // #endregion
}
