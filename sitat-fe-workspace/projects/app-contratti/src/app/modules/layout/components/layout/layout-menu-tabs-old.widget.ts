import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewEncapsulation,
} from '@angular/core';
import { IDictionary, SdkAbstractWidget, SdkProviderService, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMenuTab, SdkMenuTabsConfig, SdkMenuTabsOutput } from '@maggioli/sdk-controls';
import { each, get, isEmpty, isObject, isString } from 'lodash-es';
import { Subject } from 'rxjs';


@Component({
    // to avoid NG0912: Component ID generation collision detected with layout-menu-tabs (do not use this selector!)
    selector: 'layout-menu-tabs-old-local',
    templateUrl: `layout-menu-tabs.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class LayoutMenuTabsOldWidget extends SdkAbstractWidget<any> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `layout-menu-tabs`;

    private config: SdkMenuTabsConfig;
    public configSub: Subject<SdkMenuTabsConfig> = new Subject();

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void { }

    protected onAfterViewInit(): void {
        this.addSubscription(this.sdkLayoutMenuTabs.on(this.onData));
    }

    protected onDestroy(): void { }

    protected onConfig(config: IDictionary<any>): void {
        if (isObject(config)) {
            this.config = get(config, 'menuTabs');
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Private

    private onData = (values: Array<SdkMenuTab>) => {
        setTimeout(() => {
            this.config.tabs = [...values];
            this.configSub.next(this.config);
        });
    }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    // #endregion

    public mangageMenuOutput(out: SdkMenuTabsOutput): void {
        let item: SdkMenuTab = out.item;
        if (isObject(item) && !isEmpty(item.slug)) {
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
    }

}
