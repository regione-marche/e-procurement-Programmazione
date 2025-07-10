import { HttpErrorResponse } from '@angular/common/http';
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
import {
    IBrowserInfo,
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBrowserHelperService,
    SdkBusinessAbstractWidget,
    SdkCaptchaApp,
    SdkProviderService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isEqual, isObject, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { RichiestaAssistenzaInitDTO } from '../../model/gestione-utenti.model';
import { CustomParamsFunction, ResponseDTO, StazioneAppaltanteInfo } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
    templateUrl: 'sdk-richiesta-assistenza.component.html',
    styleUrls: ['./sdk-richiesta-assistenza.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkRichiestaAssistenzaComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `sdk-richiesta-assistenza-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('form') _form: ElementRef;
    @ViewChild('captcha') _captcha: ElementRef;
    @ViewChild('errorCaptcha') _errorCaptcha: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public captchaResetSubject$: Subject<boolean> = new Subject();
    public serverCaptchaEnabled: boolean = true;

    private buttons: SdkButtonGroupInput;
    private buttonsErr: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private inizRichiestaAssistenza: RichiestaAssistenzaInitDTO;
    private browserInfo: IBrowserInfo;
    private userProfile: UserProfile;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private homeSlug: string;
    private dialogConfig: SdkDialogConfig;
    private captchaSolution: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) public appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.initButtons();
        this.browserInfo = this.sdkBrowserHelperService.getBrowserInfo();

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.initDialog();
        this.serverCaptchaEnabled = this.appConfig.environment.FRIENDLY_CAPTCHA_SITE_KEY != null && !isEmpty(this.appConfig.environment.FRIENDLY_CAPTCHA_SITE_KEY);
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.getInizRichiestaAssistenza()
            .pipe(
                map(this.elaborateInizRichiestaAssistenza),
                map(() => this.loadForm()),
                map(() => this.loadButtons()),
                map(() => {
                    if (!this.serverCaptchaEnabled) {
                        this.initCaptcha();
                    }
                }),
                catchError(this.handleError)
            ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.homeSlug = this.config.homeSlug;
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get sdkBrowserHelperService(): SdkBrowserHelperService { return this.injectable(SdkBrowserHelperService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

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

        let iniz: IDictionary<any> = {};

        if (this.userProfile != null) {
            iniz.referenteDaContattare = this.userProfile.cognome + ' ' + this.userProfile.nome;
            iniz.email = this.userProfile.userEmail;
        }

        if (this.stazioneAppaltanteInfo != null) {
            iniz.nominativoEnteAmministrazione = this.stazioneAppaltanteInfo.nome;
        }

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
            }

            if (field.code === 'upload-documento' && this.isRichiestaAssistenzaAttiva && this.inizRichiestaAssistenza.allegatoFileSize != null) {
                field.maxFileSize = `${this.inizRichiestaAssistenza.allegatoFileSize} MB`;
            }

            return {
                mapping,
                field
            };
        }

        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, iniz, {
            tipologiaRichiesta: this.inizRichiestaAssistenza.listaOggetti
        });

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {

        if (!this.serverCaptchaEnabled && button.code === 'richiedi-assistenza') {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.form.dispatchEvent(new Event('submit-captcha', { bubbles: true }));
        } else {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                browserInfo: this.browserInfo,
                homeSlug: this.homeSlug,
                serverCaptchaEnabled: this.serverCaptchaEnabled,
                captchaSolution: this.captchaSolution,
                captchaResetSubject$: this.captchaResetSubject$
            };

            if (button.code === 'back-to-home-page' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
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

        this.buttonsErr = {
            buttons: this.config.body.buttonsErr
        };
    }

    private loadButtons = () => {
        if (this.isRichiestaAssistenzaAttiva) {
            this.buttonsSubj.next(this.buttons);
        } else {
            this.buttonsSubj.next(this.buttonsErr);
        }
    }

    private getInizRichiestaAssistenza(): Observable<ResponseDTO<RichiestaAssistenzaInitDTO>> {
        return this.gestioneUtentiService.getInizRichiestaAssistenza()
            .pipe(
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    let err: ResponseDTO<any> = error.error;
                    if (err != null && err.messages != null && err.messages.length > 0) {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: `SDK-RICHIESTA-ASSISTENZA.VALIDATORS.${one}`
                            };
                            return message;
                        });
                        this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                    } else {
                        this.sdkMessagePanelService.showError(this.messagesPanel, [
                            {
                                message: 'ERRORS.UNEXPECTED-ERROR'
                            }
                        ]);
                    }
                    return throwError(() => error);
                })
            );
    }

    private elaborateInizRichiestaAssistenza = (result: ResponseDTO<RichiestaAssistenzaInitDTO>) => {
        this.inizRichiestaAssistenza = result.response;
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsErr);
        return throwError(() => err);
    }

    private get isRichiestaAssistenzaAttiva(): boolean {
        return this.inizRichiestaAssistenza != null && this.inizRichiestaAssistenza.assistenzaAttiva;
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

    private initCaptcha(): void {
        new SdkCaptchaApp(this.captcha, this.errorCaptcha);
        this.form.addEventListener('submit-captcha', (event: Event) => {
            event.preventDefault();

            let data: IDictionary<any> = {
                buttonCode: 'richiedi-assistenza',
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                browserInfo: this.browserInfo,
                homeSlug: this.homeSlug,
                serverCaptchaEnabled: this.serverCaptchaEnabled,
                captchaResetSubject$: this.captchaResetSubject$
            };

            this.provider.run('SDK_GESTIONE_UTENTI_RICHIESTA_ASSISTENZA', data).subscribe();
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

    // #endregion
}