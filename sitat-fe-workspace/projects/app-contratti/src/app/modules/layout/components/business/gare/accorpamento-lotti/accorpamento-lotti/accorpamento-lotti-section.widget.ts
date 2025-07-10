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
import { HttpRequestHelper, ProtectionUtilsService } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteConfig,
    SdkAutocompleteOutput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { each, find, isEmpty, isObject, set } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

import { Constants } from '../../../../../../../app.constants';
import { LottoBaseEntry } from '../../../../../../models/gare/gare.model';
import { GareService } from '../../../../../../services/gare/gare.service';


@Component({
    templateUrl: `accorpamento-lotti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AccorpamentoLottiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `accorpamento-lotti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    public cigMasterConfigSubj: BehaviorSubject<SdkAutocompleteConfig> = new BehaviorSubject(null);
    public listaLottiAccorpabili: Array<LottoBaseEntry>;
    public checkedCig: IDictionary<boolean>;

    private buttons: SdkButtonGroupInput;
    private buttonsError: SdkButtonGroupInput;
    private codGara: string;
    private userProfile: UserProfile;
    private dialogConfig: SdkDialogConfig;
    private cigMasterConfig: SdkAutocompleteConfig;
    private cigMaster: string;
    private codLottoMaster: number;

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
        this.initCigMasterConfig();
    }

    protected onAfterViewInit(): void {
        this.buttonsSubj.next(this.buttonsError);
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

    private initCigMasterConfig(): void {
        const providerArgs: IDictionary<any> = {
            codGara: this.codGara
        };
        this.cigMasterConfig = {
            code: 'cig-master',
            label: 'ACCORPAMENTO-LOTTI.CIG-MASTER',
            itemsProvider: this.provider.run('APP_GARA_CIG_MASTER_AUTOCOMPLETE', providerArgs),
            newEditAvailable: false,
            noDataLabel: 'NO-DATA'
        };
        this.cigMasterConfigSubj.next(this.cigMasterConfig);
    }

    private getLottiAccorpabiliFactory(codGara: string, codLottoMaster: string) {
        return () => {
            return this.gareService.getLottiAccorpabili(codGara, codLottoMaster);
        }
    }

    private loadLottiAccorpabili(codLottoMaster: string): void {
        const factory = this.getLottiAccorpabiliFactory(this.codGara, codLottoMaster);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<LottoBaseEntry>) => {
            this.markForCheck(() => {
                this.clearCigMaster();

                this.listaLottiAccorpabili = result;
                this.checkedCig = {};
                each(this.listaLottiAccorpabili, (one: LottoBaseEntry) => {
                    set(this.checkedCig, one.cig, true);
                });

                this.buttonsSubj.next(this.buttons);
            });
        });
    }

    private clearCigMaster(): void {
        delete this.listaLottiAccorpabili;
        delete this.checkedCig;
    }

    private getCheckedCodLotti(checkedCig: IDictionary<boolean>): Array<number> {
        const checkedCodLotto: Array<number> = new Array();
        each(checkedCig, (value: boolean, key: string) => {
            if (value === true) {
                const found: LottoBaseEntry = find(this.listaLottiAccorpabili, (one: LottoBaseEntry) => one.cig === key);
                if (found != null) {
                    checkedCodLotto.push(+found.codLotto);
                }
            }
        });
        return checkedCodLotto;
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                cigMaster: this.cigMaster,
                codLottoMaster: this.codLottoMaster,
                checkedCodLotti: this.getCheckedCodLotti(this.checkedCig),
                dialogConfigSubj: this.dialogConfigSubj,
                syscon: this.userProfile.syscon,
                setUpdateState: this.setUpdateState
            }).subscribe(this.manageExecutionProvider);
        }
    }

    public onCigMasterOutput(item: SdkAutocompleteOutput): void {
        if (item != null) {
            if (item.data == null) {
                // clear tabella
                delete this.cigMaster;
                delete this.codLottoMaster;
                this.markForCheck(() => this.clearCigMaster());
            } else {
                // load tabella
                const lotto: LottoBaseEntry = <LottoBaseEntry>item.data;
                this.cigMaster = lotto.cig;
                this.codLottoMaster = +lotto.codLotto;
                this.loadLottiAccorpabili(lotto.codLotto);
            }
        }
    }

    public manageCheckboxes(cigLotto: string, event: any): void {
        if (event != null && event.target != null) {
            set(this.checkedCig, cigLotto, event.target.checked === true);
        }
    }

    // #endregion
}
