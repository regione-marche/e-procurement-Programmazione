import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBusinessAbstractWidget, SdkCaptchaApp, SdkProviderService } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { CustomParamsFunction } from '../../model/lib.model';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';

@Component({
    templateUrl: 'sdk-recupero-password.component.html',
    styleUrls: ['./sdk-recupero-password.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkRecuperoPasswordComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `sdk-recupero-password-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('form') _form: ElementRef;
    @ViewChild('captcha') _captcha: ElementRef;
    @ViewChild('errorCaptcha') _errorCaptcha: ElementRef;

    public config: any = {};
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public captchaResetSubject$: Subject<boolean> = new Subject();
    public serverCaptchaEnabled: boolean = true;

    private buttons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private dialogConfig: SdkDialogConfig;
    private captchaSolution: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) public appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.initButtons();
        this.initDialog();
        this.serverCaptchaEnabled = this.appConfig.environment.FRIENDLY_CAPTCHA_SITE_KEY != null && !isEmpty(this.appConfig.environment.FRIENDLY_CAPTCHA_SITE_KEY);
    }

    protected onAfterViewInit(): void {
        this.loadForm();
        this.loadButtons();
        if (!this.serverCaptchaEnabled) {
            this.initCaptcha();
        }
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

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public handleCaptchaSolution(solution: string): void {
        this.captchaSolution = solution;
    }

    // #endregion

    // #region Getters

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
            }

            return {
                mapping,
                field
            };
        }

        formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {

        if (!this.serverCaptchaEnabled && button.code === 'richiedi-recupero-password') {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.form.dispatchEvent(new Event('submit-captcha', { bubbles: true }));
        } else {
            let currentUrl: string = this.buildCurrentUrl();

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                currentUrl,
                serverCaptchaEnabled: this.serverCaptchaEnabled,
                captchaSolution: this.captchaSolution,
                captchaResetSubject$: this.captchaResetSubject$
            };

            if (button.code === 'back' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    private initButtons(): void {
        this.buttons = {
            buttons: this.config.body.buttons
        };
    }

    private loadButtons = () => {
        this.buttonsSubj.next(this.buttons);
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    public buildCurrentUrl(): string {
        let origin = window.location.origin;
        let pathname = window.location.pathname;
        return origin + pathname;
    }

    private initCaptcha(): void {
        new SdkCaptchaApp(this.captcha, this.errorCaptcha);
        this.form.addEventListener('submit-captcha', (event: Event) => {
            event.preventDefault();

            let currentUrl: string = this.buildCurrentUrl();

            let data: IDictionary<any> = {
                buttonCode: 'richiedi-recupero-password',
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                currentUrl,
                serverCaptchaEnabled: this.serverCaptchaEnabled,
                captchaResetSubject$: this.captchaResetSubject$
            };

            this.provider.run('SDK_GESTIONE_UTENTI_RECUPERO_PASSWORD', data).subscribe();
        });
    }

    private get form(): HTMLFormElement {
        return this._form.nativeElement;
    }

    private get captcha(): HTMLElement {
        return this._captcha.nativeElement;
    }

    private get errorCaptcha(): HTMLElement {
        return this._errorCaptcha.nativeElement;
    }

    // #endregion
}