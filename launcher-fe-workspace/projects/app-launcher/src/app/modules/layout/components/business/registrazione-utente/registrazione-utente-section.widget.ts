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
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkCaptchaApp,
    SdkProviderService,
    SdkRouterService,
    SdkSessionStorageService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { AppHomeIndexPanel } from '@maggioli/sdk-widgets';
import { cloneDeep, get, isEmpty, isObject, toString } from 'lodash-es';
import { Observable, Subject, of } from 'rxjs';

import { environment } from '../../../../../../environments/environment';
import { Constants } from '../../../../../app.constants';
import { ApplicationLoginConfiguration } from '../../../../models/application.model';
import { AuthenticationService } from '../../../../services/authentication/authentication.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';

@Component({
    templateUrl: `registrazione-utente-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegistrazioneUtenteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `registrazione-utente-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('form') _form: ElementRef;
    @ViewChild('captcha') _captcha: ElementRef;
    @ViewChild('errorCaptcha') _errorCaptcha: ElementRef;

    public externalLink: Boolean = false;
    public config: any = {};
    public buttons: Observable<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public captchaResetSubject$: Subject<boolean> = new Subject();
    public serverCaptchaEnabled: boolean = true;

    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private modelloAccreditamento: string;
    private modelloAccreditamentoLink: string;
    private panelCode: string;
    private registrazionePubblica: boolean = false;
    private captchaSolution: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.serverCaptchaEnabled = environment.FRIENDLY_CAPTCHA_SITE_KEY != null && !isEmpty(environment.FRIENDLY_CAPTCHA_SITE_KEY);
    }

    protected onAfterViewInit(): void {
        this.initComponent();
        if (!this.serverCaptchaEnabled) {
            this.initCaptcha();
        }
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.buttons = of({
                    buttons: this.config.body.buttons
                });
                if (!isEmpty(this.config.body.externalLink)) {
                    this.externalLink = true;
                }
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

    public donwloadModulo(): void {
        let link = document.createElement('a');

        if (isEmpty(this.modelloAccreditamentoLink)) {
            if (!isEmpty(this.modelloAccreditamento)) {
                link.download = `${this.modelloAccreditamento}`;
                link.href = `assets/templates/${this.modelloAccreditamento}`;
            } else {
                link.download = 'modello_accreditamento_nuovo_utente.pdf';
                link.href = 'assets/templates/modello_accreditamento_nuovo_utente.pdf';
            }
            link.click();
        } else {
            window.open(this.modelloAccreditamentoLink, '_blank');
        }

    }

    public manageFormClick(config: SdkTextOutput): void {

    }

    public handleCaptchaSolution(solution: string): void {
        this.captchaSolution = solution;
    }

    // #endregion

    // #region Getters

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadForm(): void {

        const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);

        if (this.panelCode == null) {
            this.panelCode = moduloSelezionato.panelCode;
        }

        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, `body.${this.panelCode}`).form);
        this.modelloAccreditamento = get(this.config, `body.${this.panelCode}`).modelloAccreditamento;
        this.modelloAccreditamentoLink = get(this.config, `body.${this.panelCode}`).modelloAccreditamentoLink;
        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let applicationLoginConfiguration: ApplicationLoginConfiguration = this.getApplicationLoginConfiguration();

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'download-documento') {
                mapping = false;
            }

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
            }

            if (restObject.registrazionePubblica == 'NO' && (field.code === 'nome' || field.code === 'cognome')) {
                field.type = 'TEXT';
                field.data = get(restObject, field.mappingInput);
                mapping = false;
            }

            return {
                mapping,
                field
            };
        }

        let body: IDictionary<any> = {
            registrazionePubblica: this.registrazionePubblica ? 'SI' : 'NO'
        };

        if (this.userProfile != null) {
            body = {
                ...body,
                nome: this.userProfile.nome,
                cognome: this.userProfile.cognome,
                codiceFiscale: this.userProfile.codiceFiscale
            };
        }

        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, body, {
            tabellatiBaseUrl: applicationLoginConfiguration.AUTHENTICATION_TABELLATI_BASE_URL
        });

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {

        if (!this.serverCaptchaEnabled && button.code === 'save-utente') {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.form.dispatchEvent(new Event('submit-captcha', { bubbles: true }));
        } else {
            let applicationLoginConfiguration: ApplicationLoginConfiguration = this.getApplicationLoginConfiguration();

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                registrationBaseUrl: applicationLoginConfiguration.AUTHENTICATION_CHECK_USER_BASE_URL,
                registrazionePubblica: this.registrazionePubblica,
                serverCaptchaEnabled: this.serverCaptchaEnabled,
                captchaSolution: this.captchaSolution,
                captchaResetSubject$: this.captchaResetSubject$
            };

            this.provider.run(button.provider, data).subscribe();
        }
    }

    private getApplicationLoginConfiguration(): ApplicationLoginConfiguration {
        const appLoginConfig: ApplicationLoginConfiguration = get(environment.PANELS_CONFIGURATION, this.panelCode);

        if (appLoginConfig == null) {
            this.sdkRouterService.navigateToPage('home-page');
        }

        return appLoginConfig;
    }

    private initComponent(): void {
        let localPanelCode: string = this.sdkSessionStorageService.getItem<string>(Constants.PANEL_CODE_SELEZIONATO, Constants.LOCAL_STORAGE_PREFIX);

        this.panelCode = localPanelCode;

        this.registrazionePubblica = localPanelCode != null;

        if (this.registrazionePubblica) {
            let applicationLoginConfiguration: ApplicationLoginConfiguration = this.getApplicationLoginConfiguration();
            this.authenticationService.isRegistrazioneAttiva(applicationLoginConfiguration.AUTHENTICATION_GET_TOKEN_BASE_URL)
                .subscribe((active: boolean) => {
                    if (!active) {
                        this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '5' });
                    } else {
                        this.loadForm();
                    }
                });
        } else {
            this.loadForm();
        }
    }

    private initCaptcha(): void {
        new SdkCaptchaApp(this.captcha, this.errorCaptcha);
        this.form.addEventListener('submit-captcha', (event: Event) => {
            event.preventDefault();

            let applicationLoginConfiguration: ApplicationLoginConfiguration = this.getApplicationLoginConfiguration();

            let data: IDictionary<any> = {
                buttonCode: 'save-utente',
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                registrationBaseUrl: applicationLoginConfiguration.AUTHENTICATION_CHECK_USER_BASE_URL,
                registrazionePubblica: this.registrazionePubblica,
                serverCaptchaEnabled: this.serverCaptchaEnabled,
                captchaResetSubject$: this.captchaResetSubject$
            };

            this.provider.run('LAUNCHER_REGISTRAZIONE_UTENTE', data).subscribe();
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
