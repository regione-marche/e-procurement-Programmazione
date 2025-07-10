import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
} from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkDateHelper,
    SdkLocaleService,
    SdkNumberFormatService,
    SdkProviderService,
    SdkRouterService,
    SdkStoreService,
    UserProfile
} from "@maggioli/sdk-commons";
import {
    SdkButtonGroupInput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkSidebarConfig,
} from "@maggioli/sdk-controls";
import { SdkDatasourceService, SdkTableConfig, SdkTableToolbarActionPerfomer } from "@maggioli/sdk-table";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, has, isEmpty, isObject, reduce, remove } from "lodash-es";
import { BehaviorSubject, mergeMap, Observable, Subject, tap } from "rxjs";
import { ProfiloConfigurationItem } from "../../model/profilo/profilo.model";
import { ComboDto } from "../../model/sdk-base.model";
import { StazioneAppaltanteBaseInfo } from "../../model/stazione-appaltante/stazione-appaltante.model";
import { SdkAppaltiecontrattiBaseConstants } from "../../sdk-appaltiecontratti-base.constants";
import { HttpRequestHelper } from "../../services/http/http-request-helper.service";
import { GridUtilsService } from "../../services/utils/grid-utils.service";
import { ProtectionUtilsService } from "../../services/utils/protection-utils.service";
import { SdkBaseBusinessWidget } from "./sdk-base-business-widget";

@Component({
    template: "",
})
export abstract class SdkBaseSectionWidget extends SdkBaseBusinessWidget implements OnInit, OnDestroy {
    //ABSTRACT METHODS
    protected abstract getValoriTabellatiConst(): string[];
    protected abstract loadTabellati(): Observable<IDictionary<Array<ComboDto>>>;
    //END ABSTRACT METHODS

    @ViewChild("messages") _messagesPanel: ElementRef;
    @ViewChild("infoBox") _infoBox: ElementRef;

    public config: any = {};
    protected stazioneAppaltante: StazioneAppaltanteBaseInfo;
    protected userProfile: UserProfile;

    protected buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);

    //protected datasource: ListaGareDatasource;
    protected valoriTabellati: IDictionary<Array<ComboDto>>;
    protected performers: IDictionary<SdkTableToolbarActionPerfomer>;

    public sidebarConfigObs: BehaviorSubject<SdkSidebarConfig> = new BehaviorSubject(null);;

    protected dialogConfig: SdkDialogConfig;
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    protected modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    protected infoBoxModalConfig: IDictionary<any>;

    protected menuTabs: Array<SdkMenuTab>;

    private _detailPage: boolean = false;

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    protected onInit(): void {

        super.onInit();

        this.addSubscription(
            this.store.select<UserProfile>(SdkAppaltiecontrattiBaseConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
                this.userProfile = userProfile;
            })
        );

        this.addSubscription(
            this.store.select(SdkAppaltiecontrattiBaseConstants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteBaseInfo) => {
                this.stazioneAppaltante = saInfo;
            })
        );

        this.loadButtons();
        if (this.refreshTabsOnInit()) {
            this.refreshTabs();
        }

    }

    protected refreshTabsOnInit() {
        return true;
    }

    protected onAfterViewInit(): void {
        setTimeout(() => {
            this.checkInfoBox();
            this.showButtons();
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        super.onConfig(config);

        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    protected refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let args: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltante,
                    syscon: this.userProfile.syscon,
                    userProfile: this.userProfile,
                };
                let visible: boolean = this.provider.run(one.visible, args);
                return visible === false;
            }
            return false;
        });

        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    // #region Getters

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected get activatedRoute(): ActivatedRoute {
        return this.injectable(ActivatedRoute);
    }

    protected get translateService(): TranslateService {
        return this.injectable(TranslateService);
    }

    protected get provider(): SdkProviderService {
        return this.injectable(SdkProviderService);
    }

    protected get factory(): SdkDatasourceService {
        return this.injectable(SdkDatasourceService);
    }

    protected get gridUtilsService(): GridUtilsService {
        return this.injectable(GridUtilsService);
    }

    protected get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
    }

    protected get store(): SdkStoreService {
        return this.injectable(SdkStoreService);
    }

    protected get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    protected get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    protected get dateHelper(): SdkDateHelper {
        return this.injectable(SdkDateHelper);
    }

    protected get routerService(): SdkRouterService {
        return this.injectable(SdkRouterService);
    }

    public get sdkDateHelper(): SdkDateHelper {
        return this.injectable(SdkDateHelper);
    }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    protected get sdkNumberFormatService(): SdkNumberFormatService { return this.injectable(SdkNumberFormatService) }

    protected get sdkLocaleService(): SdkLocaleService { return this.injectable(SdkLocaleService) }

    // #endregion

    protected get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    protected checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox,
            });
        }
    }

    protected loadButtons(): void {
        let buttonsConfig: SdkButtonGroupInput = {
            buttons: [],
        };

        if (this.config.body && this.config.body.buttons) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, ...this.config.body.buttons];
        }

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(buttonsConfig.buttons, this.userProfile.configurations),
        };

        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    protected showButtons(buttonsInput?: SdkButtonGroupInput): void {

        const buttonsLocal = buttonsInput ?? this.buttons;

        if (buttonsLocal && buttonsLocal.buttons) {
            let buttons: SdkButtonGroupInput = {
                buttons: cloneDeep(buttonsLocal.buttons),
            };

            this.injectDocAssociatiButton(buttons).pipe(
                mergeMap(() => this.injectModelliButton(buttons)),
                tap(() => {
                    buttons = this.checkButtonsStatus(buttons);
                    this.buttonsSubj.next(buttons);
                })
            ).subscribe();
        }
    }

    protected checkButtonsStatus(buttons: SdkButtonGroupInput): SdkButtonGroupInput {
        //Do nothing in base method, can be override to change buttons
        return buttons;
    }

    protected elaborateTabellati = (result: IDictionary<Array<ComboDto>>) => {
        this.valoriTabellati = result;
    };

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    protected translateCheckbox(value: any): string {
        return value ? this.translateService.instant("CHECKBOX.YES") : this.translateService.instant("CHECKBOX.NO");
    }

    protected initDeleteConfirmDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant("DIALOG.DELETE-TITLE"),
            message: this.translateService.instant("DIALOG.DELETE-TEXT"),
            acceptLabel: this.translateService.instant("DIALOG.CONFIRM-ACTION"),
            rejectLabel: this.translateService.instant("DIALOG.CANCEL-ACTION"),
            open: new Subject(),
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }



    protected getProtezioniMap(): IDictionary<ProfiloConfigurationItem> {
        let protectionMap: IDictionary<ProfiloConfigurationItem> = reduce(this.userProfile.configurations.configurazioni, (map: IDictionary<any>, one: ProfiloConfigurationItem) => {
            map[one.key] = one;
            return map;
        }, {});
        return protectionMap;
    }

    protected getProtezioniValue(key: string, defaultVal: boolean): boolean {
        let protezioniMap = this.getProtezioniMap();
        if (has(protezioniMap, key)) {
            let value: ProfiloConfigurationItem = get(protezioniMap, key);
            return value.valore;
        } else {
            if (key.endsWith('.*')) {
                key = key.substring(0, key.lastIndexOf('.*'));
            }
            if (key.indexOf('.') >= 0) {
                let subKey = key.substring(0, key.lastIndexOf('.'));
                return this.getProtezioniValue(subKey + '.*', defaultVal);
            }
        }
        return defaultVal;
    }

    protected set detailPage(value: boolean) {
        this._detailPage = value;
    }

    protected get detailPage() {
        return this._detailPage;
    }
}