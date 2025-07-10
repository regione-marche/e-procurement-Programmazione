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
import { cloneDeep, get, has, isEmpty, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { WMailDTO } from '../../../../model/amministrazione.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GridUtilsService } from '../../../../utils/grid-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';
import { SdkListaServerPostaDatasource } from './sdk-lista-server-posta.datasource.service';

@Component({
    templateUrl: `sdk-lista-server-posta.component.html`
})
export class SdkListaServerPostaComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-server-posta-section`;

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
    private datasource: SdkListaServerPostaDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private dialogConfig: SdkDialogConfig;
    private homeSlug: string;

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
                this.homeSlug = this.config.homeSlug;
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
                homeSlug: this.homeSlug
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                    this.reloadGrid();
                }
            });
        }
    }

    public trackByCode(index: number, server: WMailDTO): string {
        return server != null ? server.idCfg : toString(index);
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
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
                let item: WMailDTO = tempItem as WMailDTO;
                this.dettaglioServerPosta(item);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: WMailDTO = tempItem as WMailDTO;
                this.deleteServerPosta(item);
            }
        }
    }

    private dettaglioServerPosta(config: WMailDTO): void {
        this.provider.run('SDK_GESTIONE_POSTA_LISTA', {
            action: 'DETAIL',
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel,
            item: config
        }).subscribe();
    }

    private deleteServerPosta(item: WMailDTO): void {
        let func = this.deleteServerPostaConfirm(item);
        this.dialogConfig.open.next(func);
    }

    private deleteServerPostaConfirm(item: WMailDTO): any {
        return () => {
            this.provider.run('SDK_GESTIONE_POSTA_LISTA', {
                action: 'DELETE',
                item,
                messagesPanel: this.messagesPanel
            }).subscribe(this.manageProviderResponse);
        }
    }

    private manageProviderResponse = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(SdkListaServerPostaDatasource, {
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);


        this.configSub.next(this.gridConfig);
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
