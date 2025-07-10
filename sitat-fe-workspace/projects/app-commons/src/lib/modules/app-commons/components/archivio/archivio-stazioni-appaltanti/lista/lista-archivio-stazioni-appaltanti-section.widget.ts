import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    IDictionary,
    SdkAppEnvConfig,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    SDK_APP_CONFIG,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, findIndex, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../app-commons.constants';
import {
    RicercaAvanzataArchivioStazioniAppaltantiForm,
    StazioneAppaltanteListaEntry,
} from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { GridUtilsService } from '../../../../services/utils/grid-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';
import { ListaArchivioStazioniAppaltantiDatasource } from './lista-archivio-stazioni-appaltanti.datasource.service';

@Component({
    templateUrl: `lista-archivio-stazioni-appaltanti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaArchivioStazioniAppaltantiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-archivio-stazioni-appaltanti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private buttons: SdkButtonGroupInput;
    private buttonsWithoutPermission: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private gridConfig: SdkTableConfig;
    private datasource: ListaArchivioStazioniAppaltantiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private dialogConfig: SdkDialogConfig;
    private searchForm: RicercaAvanzataArchivioStazioniAppaltantiForm;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_SELECT).subscribe((form: RicercaAvanzataArchivioStazioniAppaltantiForm) => {
            this.searchForm = form;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        setTimeout(() => {
            this.initPerformers();
            this.initGrid();
            this.checkInfoBox();
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

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
                    this.reloadGrid();
                } else if (has(data, 'cleanSearch') && get(data, 'cleanSearch') === true) {
                    delete this.searchForm;
                    this.datasource.params = {
                        ...this.datasource.params,
                        searchForm: undefined
                    };
                    this.reloadGrid();
                }
            });
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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
                let item: StazioneAppaltanteListaEntry = tempItem as StazioneAppaltanteListaEntry;
                this.detailSa(item.codice);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: StazioneAppaltanteListaEntry = tempItem as StazioneAppaltanteListaEntry;
                this.deleteSa(item.codice);
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: StazioneAppaltanteListaEntry = tempItem as StazioneAppaltanteListaEntry;
                return item.deletable === false;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: StazioneAppaltanteListaEntry = tempItem as StazioneAppaltanteListaEntry;
                return item.deletable === true;
            }
        }
    }

    public detailSa(codice: string): void {
        this.provider.run('APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI', {
            action: 'DETAIL',
            codice,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    public deleteSa(codice: string): void {
        let func = this.deleteSaConfirm(codice);
        this.dialogConfig.open.next(func);
    }

    private deleteSaConfirm(codice: string): any {
        return () => {
            this.provider.run('APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI', {
                action: 'DELETE',
                codice,
                messagesPanel: this.messagesPanel
            }).subscribe(this.manageDelete);
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

        this.datasource = this.factory.create(ListaArchivioStazioniAppaltantiDatasource, {
            messagesPanel: this.messagesPanel,
            searchForm: this.searchForm,
            appConfig: this.appConfig
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsWithoutPermission = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsWithoutPermission, this.userProfile.configurations)
        }
        if (this.isUtenteAbilitatoSA()) {
            this.buttonsSubj = new BehaviorSubject(this.buttons);
        } else {
            this.buttonsSubj = new BehaviorSubject(this.buttonsWithoutPermission);
        }
    }

    private isUtenteAbilitatoSA(): boolean {
        return findIndex(this.userProfile.abilitazioni, (one: string) => one === Constants.OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI) == -1;
    }

    // #endregion
}
