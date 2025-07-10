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
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../../../app-commons.constants';
import { RicercaAvanzataArchivioUfficiForm } from '../../../../models/archivio/archivio-uffici.models';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';

@Component({
    templateUrl: `ricerca-avanzata-archivio-uffici-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaAvanzataArchivioUfficiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `ricerca-avanzata-archivio-uffici-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private buttons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private form: RicercaAvanzataArchivioUfficiForm;
    private userProfile: UserProfile;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SEARCH_FORM_ARCHIVIO_UFFICI_SELECT).subscribe((form: RicercaAvanzataArchivioUfficiForm) => {
            this.form = form;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadForm();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.addSubscription(
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }

    // #endregion

    // #region Private

    private loadForm = () => {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    advancedSearch: true
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, null, this.form, providerArgs);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;

                this.formBuilderConfigObs.next(formConfig);
            });

        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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

    // #endregion

}
