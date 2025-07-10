import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    SDK_APP_CONFIG,
    SdkAbstractComponent,
    SdkAppEnvConfig,
    SdkProviderService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkFormBuilderConfiguration } from '@maggioli/sdk-controls';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { InfoCampo } from '../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiService } from '../../services/tabellati/tabellati.service';
import { FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `mnemonico-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class MnemonicoModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private userProfile: UserProfile;
    public mnemonico: string;
    private tabellatiBaseUrl: string;
    private infoCampo: InfoCampo;
    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        if (this.mnemonico) {
            this.tabellatiBaseUrl = this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL;
            let factory = this.infoCampoFactory(this.tabellatiBaseUrl, this.mnemonico);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((data: InfoCampo) => {
                this.infoCampo = data;
                this.elaborateConfig();
            });
        }
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.userProfile = this.config.userProfile;
                this.mnemonico = this.config.mnemonico ? this.config.mnemonico : null;
                this.isReady = true;
            });
        }
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'fields')
                };

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, undefined, this.infoCampo);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(undefined);
            }
        }
    }

    private infoCampoFactory(tabellatiBaseUrl: string, mnemonico: string) {
        return () => {
            return this.tabellatiService.getInfoCampo(tabellatiBaseUrl, mnemonico);
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    // #endregion
}
