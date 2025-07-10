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
import { cloneDeep, get, has, isEmpty, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../app-commons.constants';
import { ImpresaBaseEntry, RicercaAvanzataArchivioImpreseForm } from '../../../../models/archivio/archivio-imprese.models';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { GridUtilsService } from '../../../../services/utils/grid-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';
import { ListaArchivioImpreseDatasource } from './lista-archivio-imprese.datasource.service';

@Component({
    templateUrl: `lista-archivio-imprese-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaArchivioImpreseSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-archivio-imprese-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private buttons: SdkButtonGroupInput;
    private stazioneAppaltante: string;
    private searchForm: RicercaAvanzataArchivioImpreseForm;
    private userProfile: UserProfile;
    private gridConfig: SdkTableConfig;
    private datasource: ListaArchivioImpreseDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private dialogConfig: SdkDialogConfig;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SEARCH_FORM_ARCHIVIO_IMPRESE_SELECT).subscribe((form: RicercaAvanzataArchivioImpreseForm) => {
            this.searchForm = form;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo.codice;
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
                let item: ImpresaBaseEntry = tempItem as ImpresaBaseEntry;
                this.detailImpresa(toString(item.codiceImpresa));
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ImpresaBaseEntry = tempItem as ImpresaBaseEntry;
                this.deleteImpresa(toString(item.codiceImpresa));
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ImpresaBaseEntry = tempItem as ImpresaBaseEntry;
                return item.deletable === false;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: ImpresaBaseEntry = tempItem as ImpresaBaseEntry;
                return item.deletable !== false;
            }
        }
    }

    public detailImpresa(codiceImpresa: string): void {
        this.provider.run('APP_COMMONS_LISTA_IMPRESE', {
            action: 'DETAIL',
            codiceImpresa,
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    public deleteImpresa(codiceImpresa: string): void {
        let func = this.deleteImpresaConfirm(codiceImpresa);
        this.dialogConfig.open.next(func);
    }

    private deleteImpresaConfirm(codiceImpresa: string): any {
        return () => {
            this.provider.run('APP_COMMONS_LISTA_IMPRESE', {
                action: 'DELETE',
                codiceImpresa,
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

        this.datasource = this.factory.create(ListaArchivioImpreseDatasource, {
            searchForm: this.searchForm,
            stazioneAppaltante: this.stazioneAppaltante,
            messagesPanel: this.messagesPanel,
            appConfig: this.appConfig
        });

        if(this.stazioneAppaltante !== '*'){
            this.config.body.grid.columns.shift();            
        }
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
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
