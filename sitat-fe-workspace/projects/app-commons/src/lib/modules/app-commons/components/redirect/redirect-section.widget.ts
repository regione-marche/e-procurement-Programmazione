import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { IDictionary, SdkBusinessAbstractWidget, SdkRedirectService, SdkRouterService } from '@maggioli/sdk-commons';

@Component({
    templateUrl: `redirect-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RedirectSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `redirect-section`;

    public config: any = {};

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void { }

    protected onAfterViewInit(): void {
        this.loadQueryString();
    }

    protected onDestroy(): void { }

    protected onConfig(_config: any): void { }

    protected onUpdateState(_state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    // #endregion

    // #region Public

    // #endregion

    // #region Private

    private loadQueryString(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        const redirectUrl: string = paramsMap.get('redirectUrl');
        const redirectParams: string = paramsMap.get('redirectParams');
        let params: IDictionary<any> = {};
        if (redirectParams != null) {
            params = this.redirectService.parseQueryParams(redirectParams);
        }
        this.routerService.navigateToPage(redirectUrl, params);
    }

    // #endregion
}
