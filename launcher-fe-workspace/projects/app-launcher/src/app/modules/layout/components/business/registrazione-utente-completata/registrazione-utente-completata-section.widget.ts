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
import { SdkBusinessAbstractWidget } from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { isObject } from 'lodash-es';

@Component({
    templateUrl: `registrazione-utente-completata-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegistrazioneUtenteCompletataSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `registrazione-utente-completata-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    public stato: string = '1';
    public cf: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        let message = this.translateService.instant('REGISTRAZIONE-UTENTE-COMPLETATA');
        if (this.stato === '1') {
            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                {
                    message
                }
            ]);
        } else if (this.stato === '2') {
            message = this.translateService.instant('UTENTE-DISABILITATO', { utente: this.cf });
            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                {
                    message
                }
            ]);
        } else if (this.stato === '3') {
            message = this.translateService.instant('REGISTRAZIONE-UTENTE-DISABILITATA-SSO', { utente: this.cf });
            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                {
                    message
                }
            ]);
        } else if (this.stato === '4') {
            message = this.translateService.instant('REGISTRAZIONE-UTENTE-SCADUTO', { utente: this.cf });
            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                {
                    message
                }
            ]);
        } else if (this.stato === '5') {
            message = this.translateService.instant('REGISTRAZIONE-UTENTE-DISABILITATA');
            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                {
                    message
                }
            ]);
        }
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }


    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.stato = paramsMap.get('stato');
        this.cf = paramsMap.get('cf');
    }

    // #endregion
}
