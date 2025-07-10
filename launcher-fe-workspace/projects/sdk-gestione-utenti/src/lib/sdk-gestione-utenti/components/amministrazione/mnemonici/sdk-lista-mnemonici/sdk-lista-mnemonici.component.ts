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
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GridUtilsService } from '../../../../utils/grid-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';
import { SdkListaMnemoniciDatasource } from './sdk-lista-mnemonici.datasource.service';

@Component({
    templateUrl: `sdk-lista-mnemonici.component.html`
})
export class SdkListaMnemoniciComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-mnemonici-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();
    public debounce: Subject<any> = new Subject<any>();

    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private gridConfig: SdkTableConfig;
    private datasource: SdkListaMnemoniciDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private searchData: string;
    private homeSlug: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.debounce.pipe(
            debounceTime(350),
            distinctUntilChanged())
            .subscribe((value: string) => {
                this.refreshData(value);
            }));

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
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

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private initPerformers(): void {
        this.performers = {}
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(SdkListaMnemoniciDatasource, {
            searchData: this.searchData,
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
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

    private refreshData(value: string): void {
        if (value == null || isEmpty(value)) {
            delete this.searchData;
        } else {
            this.searchData = value;
        }
        this.updateDatasource();
    }

    private updateDatasource(): void {
        this.datasource.params = {
            ...this.datasource.params,
            searchData: this.searchData
        };
    }

    // #endregion
}
