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
import { ActivatedRoute, ParamMap } from '@angular/router';
import { IDictionary, SdkBusinessAbstractWidget, SdkRedirectService, SdkRouterService } from '@maggioli/sdk-commons';
import { SdkMessagePanelService, SdkMixedSearchConfig, SdkMixedSearchOutput } from '@maggioli/sdk-controls';
import { isEmpty, isObject, set } from 'lodash-es';
import { Observable, of } from 'rxjs';

interface HomePageCard {
    icon: string;
    label: string;
    slug?: string;
    url?: string;
    params?: IDictionary<string>;
}

@Component({
    templateUrl: `index-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class IndexSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `index-section`;

    @ViewChild('infoBox') _infoBox: ElementRef;

    @ViewChild('messages') public _messagesPanel: ElementRef;

    public config: any = {};

    public searchFieldObs: Observable<SdkMixedSearchConfig>;
    public cards: Array<HomePageCard>;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void { }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.checkMessages();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.searchFieldObs = of(this.config.searchField);
                this.cards = this.config.cards;
                this.isReady = true;
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    // #endregion

    // #region Public

    public onOutput(item: SdkMixedSearchOutput): void {
        let params: IDictionary<string> = {};
        // controllo che tipologia sia diverso da 0
        if (!isEmpty(item.filterCode) && item.filterCode !== '0') {
            set(params, 'tipologia', item.filterCode);
        }
        if (!isEmpty(item.data)) {
            set(params, 'searchString', item.data);
        }
        this.routerService.navigateToPage('lista-programmi-page', params);
    }

    public getIcons(card: HomePageCard): Array<string> {
        let classes: Array<string> = [];

        if (isObject(card) && !isEmpty(card.icon)) {
            classes.push(card.icon);
        }

        return classes;
    }

    public manageCardClick(card: HomePageCard): void {
        if (isObject(card)) {
            if (!isEmpty(card.slug)) {
                let params: IDictionary<string> = null;
                if (isObject(card.params)) {
                    params = card.params
                }
                this.routerService.navigateToPage(card.slug, params);
            } else if (!isEmpty(card.url)) {
                this.sdkRedirectService.redirect(card.url);
            }
        }
    }

    // #endregion

    // #region Private

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

    private checkMessages(): void {
        let paramMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        if (paramMap != null && paramMap.get('successMessage') != null) {
            const successMessage: string = paramMap.get('successMessage');
            if (!isEmpty(successMessage)) {
                this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                    {
                        message: successMessage
                    }
                ]);
            }
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    // #endregion

}
