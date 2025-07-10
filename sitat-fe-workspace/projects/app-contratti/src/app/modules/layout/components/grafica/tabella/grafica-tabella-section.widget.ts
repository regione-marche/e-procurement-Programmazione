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
import { ProtectionUtilsService } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkModalConfig,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { SdkTableConfig, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { isEmpty, isObject, remove } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { Constants } from '../../../../../app.constants';

@Component({
    templateUrl: `grafica-tabella-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class GraficaTabellaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `grafica-tabella-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;

    private menuTabs: Array<SdkMenuTab>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    private gridConfig: SdkTableConfig;
    public resetTable: Subject<boolean> = new Subject();
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private userProfile: UserProfile;
    private fasiSlugMap: IDictionary<string>;
    private statoIcons: IDictionary<string>;
    private sidebarConfig: SdkSidebarConfig;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));


        this.loadButtons();



    }

    protected onAfterViewInit(): void {
        this.refreshTabs();

    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.fasiSlugMap = this.config.body.fasiSlugMap;
                this.statoIcons = this.config.body.statoIcons;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }


    // #endregion

    // #region Public


    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {

        }
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {

        }
    }

    // #endregion

    // #region Private

    private loadButtons(): void {
        this.buttons = {
            buttons: this.config.body.buttons
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
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
                let visible: boolean = this.provider.run(one.visible);
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    // #endregion
}
