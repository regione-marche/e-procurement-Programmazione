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
import { HttpRequestHelper, ProtectionUtilsService, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, isEmpty, isObject, remove } from 'lodash-es';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { DatiAccorpamentoEntry } from '../../../../../../models/gare/gare.model';
import { GareService } from '../../../../../../services/gare/gare.service';


@Component({
    templateUrl: `riepilogo-accorpamento-lotti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RiepilogoAccorpamentoLottiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `riepilogo-accorpamento-lotti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    public riepilogoAccorpamenti: DatiAccorpamentoEntry;

    private buttons: SdkButtonGroupInput;
    private buttonsError: SdkButtonGroupInput;
    private codGara: string;
    private cigMaster: string;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private userProfile: UserProfile;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.setUpdateState(true);

        this.loadButtons();
        this.checkInfoBox();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
            {
                message: 'ACCORPAMENTO-LOTTI.SUCCESS-ACCORPAMENTO',
                messageParams: {
                    value: this.cigMaster
                }
            }
        ]);
        this.loadRiepilogoAccorpamenti()
            .pipe(
                map(this.elaborateRiepilogoAccorpamenti),
                catchError(this.handleError)
            ).subscribe();
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

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.cigMaster = paramsMap.get('cigMaster');
    }

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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsError = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsError, this.userProfile.configurations)
        };
    }

    private manageExecutionProvider = (obj: any) => {
    }

    private getRiepilogoAccorpamentiFactory(codGara: string) {
        return () => {
            return this.gareService.getRiepilogoAccorpamenti(codGara);
        }
    }

    private loadRiepilogoAccorpamenti(): Observable<DatiAccorpamentoEntry> {
        const factory = this.getRiepilogoAccorpamentiFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateRiepilogoAccorpamenti = (result: DatiAccorpamentoEntry): void => {
        this.riepilogoAccorpamenti = result;
        const buttons: SdkButtonGroupInput = {
            buttons: cloneDeep(this.buttons.buttons)
        };
        remove(buttons.buttons, (one: SdkBasicButtonInput) => {
            return one.code === 'continua' && this.riepilogoAccorpamenti.accorpamentiDisponibili === false;
        });
        this.buttonsSubj.next(buttons);
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsError);
        return throwError(() => err);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                syscon: this.userProfile.syscon,
                setUpdateState: this.setUpdateState
            }).subscribe(this.manageExecutionProvider);
        }
    }

    // #endregion
}
