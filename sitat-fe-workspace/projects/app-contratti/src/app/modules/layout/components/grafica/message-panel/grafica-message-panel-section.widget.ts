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
import { SdkBusinessAbstractWidget } from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkMenuTab, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isObject } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

@Component({
    templateUrl: `grafica-message-panel-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class GraficaMessagePanelSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `grafica-message-panel-section`;

    @ViewChild('error') _error: ElementRef;

    @ViewChild('warning') _warning: ElementRef;

    @ViewChild('info') _info: ElementRef;


    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private menuTabs: Array<SdkMenuTab>;


    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

    }

    protected onAfterViewInit(): void {
        this.showPanel();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public


    public showPanel() {
        this.sdkMessagePanelService.showError(this.errorPanel, [
            {
                message: 'Esempio error'
            }
        ]);
        this.scrollToMessagePanel(this.errorPanel);

        this.sdkMessagePanelService.showInfoBox(this.infoPanel, {
            message: 'Esempio info'
        });
        this.scrollToMessagePanel(this.infoPanel);

        this.sdkMessagePanelService.showWarning(this.warningPanel, [
            {
                message: 'Esempio warning'
            }
        ]);
        this.scrollToMessagePanel(this.warningPanel);

    }


    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    // #endregion

    // #region Private

    private get errorPanel(): HTMLElement {
        return isObject(this._error) ? this._error.nativeElement : undefined;
    }

    private get warningPanel(): HTMLElement {
        return isObject(this._warning) ? this._warning.nativeElement : undefined;
    }

    private get infoPanel(): HTMLElement {
        return isObject(this._info) ? this._info.nativeElement : undefined;
    }

    // #endregion
}
