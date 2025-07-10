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
import { FormBuilder, FormGroup } from '@angular/forms';
import { ProtectionUtilsService } from '@maggioli/app-commons';
import { SdkAbstractComponent, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkBasicButtonOutput,
    SdkButtonGroupInput
} from '@maggioli/sdk-controls';
import { get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';
import { Constants } from '../../../../../../app.constants';

@Component({
    templateUrl: `export-interventi-acquisti-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ExportInterventiAcquistiModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    private buttons: SdkButtonGroupInput;
    private userProfile: UserProfile;

    public config: any;
    public data: any;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);

    public interventiForm: FormGroup;
    public acquistiForm: FormGroup;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();

        this.interventiForm = this.fb.group({
            showCUI: this.fb.control(true),
            showAnnualita: this.fb.control(true),
            showLottoF: this.fb.control(true),
            showDescrizione: this.fb.control(true),
            showRup: this.fb.control(true),
            showImportiAnnualita: this.fb.control(true),
            showApportoCapitalePrivato: this.fb.control(true),
            showPriorita: this.fb.control(true),
            showVariato: this.fb.control(true),
            showCodInt: this.fb.control(true),
            showCup: this.fb.control(true),
            showLavoroC: this.fb.control(true),
            showCodIstat: this.fb.control(true),
            showNuts: this.fb.control(true),
            showTipologia: this.fb.control(true),
            showCategoria: this.fb.control(true),
            showImmobili: this.fb.control(true),
            showScadenza: this.fb.control(true)
        });

        this.acquistiForm = this.fb.group({
            showCUI: this.fb.control(true),
            showAnnualita: this.fb.control(true),
            showCup: this.fb.control(true),
            showLottoF: this.fb.control(true),
            showDescrizione: this.fb.control(true),
            showRup: this.fb.control(true),
            showImportiAnnualita: this.fb.control(true),
            showApportoCapitalePrivato: this.fb.control(true),
            showPriorita: this.fb.control(true),
            showVariato: this.fb.control(true),
            showAcquistoRicompreso: this.fb.control(true),
            showCodCuiLavoroCollegato: this.fb.control(true),
            showAmbitoGeografico: this.fb.control(true),
            showSettore: this.fb.control(true),
            showCpv: this.fb.control(true),
            showDurataContratto: this.fb.control(true),
            showContrattoEssere: this.fb.control(true),
            showAusa: this.fb.control(true)
        });
    }

    protected onAfterViewInit(): void { }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
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

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj.next(this.buttons);
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && has(obj, 'close') && get(obj, 'close') === true) {
            this.emitOutput(undefined);
        }
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkBasicButtonOutput): void {
        if (button != null && !isEmpty(button.provider)) {
            this.provider.run(button.provider, {
                data: {
                    code: button.code
                },
                type: 'BUTTON',
                buttonCode: button.code,
                idProgramma: this.config.idProgramma,
                codiceProgramma: this.config.codiceProgramma,
                tipologia: this.config.tipologia,
                currentFilter: this.config.currentFilter,
                interventiForm: this.interventiForm,
                acquistiForm: this.acquistiForm,
                messagesPanel: this.messagesPanel
            }).subscribe(this.manageExecutionProvider)
        }
    }

    // #endregion

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get fb(): FormBuilder { return this.injectable(FormBuilder) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    // #endregion
}
