import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ProtectionUtilsService } from '@maggioli/app-commons';
import { SdkAbstractComponent, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkMessagePanelService,
    SdkTextConfig,
    SdkTextInput,
} from '@maggioli/sdk-controls';
import { isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../app.constants';

@Component({
    templateUrl: `cui-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CuiModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: any;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    public dataConfig: Observable<any>;
    public cuiConfigurationObs: Observable<SdkTextConfig>;
    public cfConfigurationObs: Observable<SdkTextConfig>;
    public annoConfigurationObs: Observable<SdkTextConfig>;
    public counterConfigurationObs: Observable<SdkTextConfig>;

    public calculateCuiSubject: Subject<SdkTextInput> = new Subject();
    public calculateCfSubject: Subject<SdkTextInput> = new Subject();
    public calculateAnnoSubject: Subject<SdkTextInput> = new Subject();
    public calculateCounterSubject: Subject<SdkTextInput> = new Subject();

    public currentCui: string;
    public cf: string;
    public anno: string;
    public counter: string;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void {
        this.cf = this.currentCui.substring(1, 12);
        this.anno = this.currentCui.substring(12, 16);
        this.counter = this.currentCui.substring(16, 21);
        this.calculateCuiSubject.next({
            data: this.currentCui
        });
        this.calculateCfSubject.next({
            data: this.cf
        });
        this.calculateAnnoSubject.next({
            data: this.anno
        });
        this.calculateCounterSubject.next({
            data: this.counter
        });
    }

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.populateData();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.currentCui = this.config.selectedItem;
            });
        }
    }

    protected onOutput(data: any): void { }


    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    // #endregion

    // #region Private
    private populateData(): void {
        this.markForCheck(() => {
            this.cuiConfigurationObs = of({
                code: 'cui',
                label: 'CUI.CALCULATED',
                disabled: true
            } as SdkTextConfig);
            this.cfConfigurationObs = of({
                code: 'cf',
                label: 'CUI.CF',
                disabled: true
            } as SdkTextConfig);
            this.annoConfigurationObs = of({
                code: 'anno',
                label: 'CUI.ANNO'
            } as SdkTextConfig);
            this.counterConfigurationObs = of({
                code: 'progressivo',
                label: 'CUI.PROGRESSIVO'
            } as SdkTextConfig);
            this.isReady = true;
        });

    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {

        }
    }

    private padLeft(size, str) {
        while (str.length < (size || 2)) { str = "0" + str; }
        return str;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion

    // #region Public

    public setCounter($event): void {
        this.counter = $event.data;
    }

    public setAnno($event): void {
        this.anno = $event.data;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button.code === 'set-button') {

            if (!/^\d+$/.test(this.counter)) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'CUI.VALIDATORS.COUNTER_NUMERIC'
                    }
                ]);
            } else if (this.counter.length > 5) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'CUI.VALIDATORS.COUNTER_LENGTH'
                    }
                ]);
            } else if (parseInt(this.counter, 10) === 0) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'CUI.VALIDATORS.COUNTER_ZERO'
                    }
                ]);
            } else if (isNaN(+this.anno) || this.anno.length != 4) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'CUI.VALIDATORS.WRONG_YEAR'
                    }
                ]);



            } else {
                this.counter = this.padLeft(5, this.counter);
                this.currentCui = this.currentCui.substring(0, 12) + this.anno + this.counter;
                this.emitOutput({
                    op: 'update',
                    value: this.currentCui
                });
            }
        } else {
            this.emitOutput(undefined);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void { }

    // #endregion

    // #region Getters

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion
}
