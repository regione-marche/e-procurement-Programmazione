import {
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
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService } from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

@Component({
    templateUrl: 'sdk-esegui-recupero-password-success.component.html',
    styleUrls: ['./sdk-esegui-recupero-password-success.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkEseguiRecuperoPasswordSuccessComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `sdk-esegui-recupero-password-success-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;

    private buttons: SdkButtonGroupInput;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.initButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadMessage();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            setUpdateState: this.setUpdateState
        };

        this.provider.run(button.provider, data).subscribe();
    }

    private initButtons(): void {
        this.buttons = {
            buttons: this.config.body.buttons
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private loadMessage(): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
            {
                message: 'SDK-RECUPERO-PASSWORD.SEND-SUCCESS'
            }
        ]);
    }

    // #endregion
}