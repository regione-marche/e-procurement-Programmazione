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
import { ActivatedRoute, ParamMap } from '@angular/router';
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService } from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

@Component({
    templateUrl: 'sdk-richiesta-assistenza-completata.component.html',
    styleUrls: ['./sdk-richiesta-assistenza-completata.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkRichiestaAssistenzaCompletataComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `sdk-richiesta-assistenza-completata-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);

    private buttons: SdkButtonGroupInput;
    private buttonsErr: SdkButtonGroupInput;
    private done: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.initButtons();
        this.checkInfoBox();
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

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel
        };

        this.provider.run(button.provider, data).subscribe();
    }

    private initButtons(): void {
        this.buttons = {
            buttons: this.config.body.buttons
        };

        this.buttonsErr = {
            buttons: this.config.body.buttonsErr
        };

        if (this.done == 'Y') {
            this.buttonsSubj.next(this.buttons);
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message: 'SDK-RICHIESTA-ASSISTENZA.SEND-SUCCESS'
                }
            ]);
        } else {
            this.buttonsSubj.next(this.buttonsErr);
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.sdkMessagePanelService.showError(this.messagesPanel, [
                {
                    message: 'SDK-RICHIESTA-ASSISTENZA.SEND-FAILURE'
                }
            ]);
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.done = paramsMap.get('done');
    }

    // #endregion
}