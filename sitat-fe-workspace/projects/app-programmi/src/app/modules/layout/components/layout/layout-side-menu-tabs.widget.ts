import { state, style, trigger } from '@angular/animations';
import { ChangeDetectorRef, Component, HostBinding, Injector, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import { IDictionary, SdkAbstractWidget, SdkBreadcrumbsMessageService, SdkProviderService, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkBreadcrumbItem, SdkMenuConfig, SdkMenuItem, SdkMenuOutput, SdkMenuTab } from '@maggioli/sdk-controls';
import { SdkLayoutBreadcrumbsCollapseService } from '@maggioli/sdk-widgets';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, isArray, isEmpty, isObject, isString, map, merge } from 'lodash-es';
import { Subject } from 'rxjs';


@Component({
    templateUrl: `layout-side-menu-tabs.widget.html`,
    encapsulation: ViewEncapsulation.None,
    animations: [
        trigger('tabsContent', [
            state('hidden', style({
                display: 'none'
            })),
            state('visible', style({
                display: 'block'
            }))
            // ,
            // transition('visible <=> hidden', animate('{{transitionParams}}'))
        ])
    ]
})
export class LayoutSideMenuTabsWidget extends SdkAbstractWidget<any> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `layout-side-menu-tabs`;

    private config: SdkMenuConfig;

    public configSub: Subject<SdkMenuConfig> = new Subject();
    public transitionOptions: string = '400ms cubic-bezier(0.86, 0, 0.07, 1)';
    public animating: boolean;
    public selected: boolean;
    private origItems: Array<SdkMenuTab>;
    public menuTitle: string;
    private breadcrumbsValues: Array<SdkBreadcrumbItem> = new Array();

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.initAnimationState();
    }

    protected onAfterViewInit(): void {
        this.addSubscription(this.sdkLayoutMenuTabs.on(this.onData));
        this.addSubscription(this.breadcrumbsCollapseService.sub((collapsed: boolean) => {
            setTimeout(() => this.toggle(collapsed));
        }));
        this.addSubscription(this.getRefreshBreadcrumbsSubject(() => {
            if (this.breadcrumbsValues != null) {
                this.logger.debug('Breadcrumb refreshed');
                this.onBreadcrumbsData(this.breadcrumbsValues);
            }
        }));
    }

    protected onDestroy(): void {
        this.reset();
    }

    protected onConfig(config: IDictionary<any>): void {
        if (isObject(config)) {
            this.config = get(config, 'sideMenu');
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Private
    private onBreadcrumbsData = (values: Array<SdkBreadcrumbItem>) => {
        if (isArray(values)) {
            this.breadcrumbsValues = values;
            let one = values[values.length - 1]
            if (one.labelProvider != null) {
                const providerValue: string = this.provider.run(one.labelProvider);
                if (providerValue != null) {
                    if (one.labelParams == null) {
                        one.labelParams = {};
                    }
                    one.labelParams = merge(one.labelParams, {
                        value: providerValue
                    });
                }
            }
            this.menuTitle = this.translateService.instant(one.label, one.labelParams);
        } else {
            this.menuTitle = '';
        }
        this.config.menuTitle = this.menuTitle;
        this.configSub.next(this.config);

    }


    private onData = (values: Array<SdkMenuTab>) => {
        setTimeout(() => {
            this.origItems = cloneDeep(values);
            const items: Array<SdkMenuTab> = map(cloneDeep(values), (one: SdkMenuTab) => {
                const item: SdkMenuItem = {
                    code: one.code,
                    label: one.title,
                    disabled: one.disabled,
                    active: one.active,
                    slug: one.slug,
                    additionalParams: one.additionalParams,
                    oggettoProtezione: one.oggettoProtezione
                };
                return item;
            });

            this.config.items = [...items];
            this.addSubscription(this.breadcrumbs.on(this.onBreadcrumbsData));
        });
    }

    private initAnimationState(): void {
        this.selected = !this.breadcrumbsCollapseService.collapseStatus;
    }

    private reset(): void {
        this.selected = true;
        this.animating = false;
        this.breadcrumbsCollapseService.expand();
    }

    private navigateTab(item: SdkMenuItem): void {
        if (isString(item.additionalParams)) {
            let prov: string = item.additionalParams;
            let params: IDictionary<any> = this.provider.run(prov);
            this.routerService.navigateToPage(item.slug, params);
        } else if (isObject(item.additionalParams)) {
            each(item.additionalParams, (value: any, key: string) => {
                let param: any = this.provider.run(value);
                value = param;
                item.additionalParams[key] = value;
            });
            this.routerService.navigateToPage(item.slug, item.additionalParams);
        } else {
            this.routerService.navigateToPage(item.slug);
        }
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get breadcrumbsCollapseService(): SdkLayoutBreadcrumbsCollapseService { return this.injectable(SdkLayoutBreadcrumbsCollapseService) }

    private get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public mangageMenuOutput(out: SdkMenuOutput): void {
        let item: SdkMenuItem = out.item;
        if (isObject(item)) {
            if (!isEmpty(item.slug)) {
                this.navigateTab(item);
            }
        }
    }

    public toggle(collapsed: boolean): boolean {
        if (this.config.disabled || this.animating || this.getUpdateState() === true) {
            return false;
        }

        this.animating = true;

        if (collapsed) {
            // chiudo tabs

            // verifico il caso in cui collapsed sia true 
            // ma sono gia' nella situazione della sidebar collassata
            // Cio' non fa triggerare il metodo di termine dell'animazione pertanto lasciando la variabile
            // animating a true sempre...
            this.selected ? this.selected = false : this.animating = false;
        } else {
            this.selected = true;
            this.animating = false;
        }
    }

    public onToggleDone(_event: Event): void {
        this.animating = false;
    }

    // #endregion
}
