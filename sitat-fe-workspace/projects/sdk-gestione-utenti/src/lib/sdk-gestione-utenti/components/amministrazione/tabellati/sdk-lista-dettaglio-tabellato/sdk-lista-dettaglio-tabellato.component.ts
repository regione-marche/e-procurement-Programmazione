import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { TabXDTO } from '../../../../model/amministrazione.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GridUtilsService } from '../../../../utils/grid-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';
import { SdkListaDettaglioTabellatoDatasource } from './sdk-lista-dettaglio-tabellato.datasource.service';

@Component({
    templateUrl: `sdk-lista-dettaglio-tabellato.component.html`
})
export class SdkListaDettaglioTabellatoComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-dettaglio-tabellato-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private gridConfig: SdkTableConfig;
    private datasource: SdkListaDettaglioTabellatoDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private provenienza: string;
    private codiceTabellato: string;
    private dialogConfig: SdkDialogConfig;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.initPerformers();
        this.initGrid();
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

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                provenienza: this.provenienza,
                codiceTabellato: this.codiceTabellato
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.provenienza = paramsMap.get('provenienza');
        this.codiceTabellato = paramsMap.get('codiceTabellato');
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: TabXDTO = tempItem as TabXDTO;
                this.dettaglio(item);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: TabXDTO = tempItem as TabXDTO;
                this.delete(item);
            }
        }
    }

    private dettaglio(config: TabXDTO): void {
        this.provider.run('SDK_GESTIONE_TABELLATI_LISTA', {
            action: 'DETAIL-2',
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel,
            item2: config,
            provenienza: this.provenienza,
            codiceTabellato: this.codiceTabellato
        }).subscribe();
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(SdkListaDettaglioTabellatoDatasource, {
            messagesPanel: this.messagesPanel,
            provenienza: this.provenienza,
            codiceTabellato: this.codiceTabellato
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.getColumns(this.gridConfig);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.fullDateformat, this.userProfile.configurations);


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

    private getColumns(gridConfig: SdkTableConfig): SdkTableConfig {

        if (gridConfig != null && this.provenienza != null) {
            const columns: Array<any> = get(this.config.body, `${this.provenienza}_columns`);
            if (columns == null) {
                this.logger.error('Errore nel recupero delle colonne per la provenienza selezionata');
            } else {
                gridConfig.columns = cloneDeep(columns);
            }
        }

        return gridConfig;
    }

    private delete(config: TabXDTO): void {
        let func = this.deleteConfirm(config);
        this.dialogConfig.open.next(func);
    }

    private deleteConfirm(config: TabXDTO): any {
        return () => {
            this.provider.run('SDK_GESTIONE_TABELLATI_LISTA', {
                action: 'DELETE',
                provenienza: this.provenienza,
                codiceTabellato: this.codiceTabellato,
                item2: config,
                messagesPanel: this.messagesPanel
            }).subscribe(this.manageProviderResponse);
        }
    }

    private manageProviderResponse = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
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

    // #endregion
}
