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
    HttpRequestHelper,
    ProtectionUtilsService,
    TabellatiCacheService,
    ValoreTabellato,
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
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isNull, isObject, isUndefined, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { InterventoNonRipropostoEntry } from '../../../../../models/interventi/interventi-non-riproposti.model';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import {
    DettaglioProgrammaInterventiNonRipropostiDatasource,
} from './dett-prog-interventi-non-riproposti.datasource.service';

@Component({
    templateUrl: `dett-prog-interventi-non-riproposti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioProgrammaInterventiNonRipropostiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dett-prog-interventi-non-riproposti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;

    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private buttonsNoInterventi: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;
    protected modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigSubj: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private datasource: DettaglioProgrammaInterventiNonRipropostiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private idProgramma: string;
    private tipologia: string;
    private menuTabs: Array<SdkMenuTab>;
    private programma: ProgrammaEntry;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
        this.refreshTabs();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati()
            .pipe(
                map(this.elaborateTabellati),
                mergeMap(this.loadProgramma),
                map(this.elaborateProgramma)
            ).subscribe();
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null) {
            if (button.code === 'export') {
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'export-interventi-acquisti-nr-modal-widget',
                    componentConfig: {
                        ...this.config.exportInterventiAcquistiNrModalConfig,
                        idProgramma: this.idProgramma,
                        codiceProgramma: this.programma.idProgramma,
                        tipologia: this.tipologia
                    }
                };
                this.modalConfigSubj.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            } else if (isEmpty(button.provider) === false) {
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

    public manageFormOutput(object: any): void { }

    public manageModalOutput(object: any): void {
        if (has(object, 'data')) {
            if (object.data.reload === true) {
                this.reloadGrid();
            }
        }
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
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private initPerformers(): void {
        this.performers = {
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: InterventoNonRipropostoEntry = tempItem as InterventoNonRipropostoEntry;
                this.deleteIntervento(toString(item.idIntervento));
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                return this.programma.idRicevuto !== undefined && this.programma.idRicevuto !== null;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                return isUndefined(this.programma.idRicevuto) || isNull(this.programma.idRicevuto);
            },
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: InterventoNonRipropostoEntry = selectedRow.dataItem as InterventoNonRipropostoEntry;
                this.detailIntervento(item.idProgramma, item.idIntervento);
            }
        }
    }

    private detailIntervento(idProgramma: number, idIntervento: number): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.addSubscription(
            this.provider.run('APP_PROGRAMMI_LISTA_INTERVENTI_NP', {
                action: 'DETAIL',
                idProgramma,
                idIntervento,
                tipologia: this.tipologia
            }).subscribe()
        );
    }

    private deleteIntervento(idIntervento: string): void {
        let func = this.deleteInterventoConfirm(idIntervento);
        this.dialogConfig.open.next(func);
    }

    private deleteInterventoConfirm(idIntervento: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_PROGRAMMI_LISTA_INTERVENTI_NP', {
                    action: 'DELETE',
                    idProgramma: this.idProgramma,
                    idIntervento,
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

        this.datasource = this.factory.create(DettaglioProgrammaInterventiNonRipropostiDatasource, {
            idProgramma: this.idProgramma,
            messagesPanel: this.messagesPanel,
            valoriTabellati: this.valoriTabellati
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
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

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private initButtons(): void {
        if (this.programma.idRicevuto) {
            this.buttonsSubj.next(this.buttonsReadonly);
        } else if (this.programma.existProgrammaAnnoPrecedente === true) {
            this.buttonsSubj.next(this.buttons);
        } else {
            this.buttonsSubj.next(this.buttonsNoInterventi);
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
        this.buttonsNoInterventi = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsNoInterventi, this.userProfile.configurations)
        };
    }

    private loadTabellati = () => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.INTERVENTO_NON_RIPROPOSTO_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadProgramma = (): Observable<ProgrammaEntry> => {
        const factory = this.dettaglioProgrammaFactory(this.idProgramma);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateProgramma = (result: ProgrammaEntry) => {
        this.programma = result;
        setTimeout(() => {
            this.initPerformers();
            this.initButtons();
            this.initGrid();
            this.modalConfig = {
                code: 'modal',
                title: '',
                openSubject: new Subject()
            };
            this.modalConfigSubj.next(this.modalConfig);
            this.checkInfoBox();
        });
    }

    // #endregion
}
