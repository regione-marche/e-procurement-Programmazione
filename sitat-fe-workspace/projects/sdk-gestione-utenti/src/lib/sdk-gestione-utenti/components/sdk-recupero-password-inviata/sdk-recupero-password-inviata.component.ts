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
import { SdkButtonGroupInput, SdkButtonGroupOutput } from '@maggioli/sdk-controls';
import { isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

@Component({
    templateUrl: 'sdk-recupero-password-inviata.component.html',
    styleUrls: ['./sdk-recupero-password-inviata.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkRecuperoPasswordInviataComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `sdk-recupero-password-inviata-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public email: string = '';

    private buttons: SdkButtonGroupInput;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.initButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
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

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.email = paramsMap.get('email');
    }

    // #endregion
}