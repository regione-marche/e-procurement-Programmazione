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
    SdkSessionStorageService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { UserService } from '../../services/user/user.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `stazione-appaltante-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class StazioneAppaltanteModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private appCode: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.appCode = this.sdkSessionStorageService.getItem(Constants.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);




        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        if (this.stazioneAppaltanteInfo != null && this.stazioneAppaltanteInfo.codice === '*') {
            let getSaInfo = this.getSAInfoFactory(this.config.stazioneAppaltanteInfo);
            this.requestHelper.begin(getSaInfo, this.messagesPanel).subscribe((result: StazioneAppaltanteInfo) => {
                this.stazioneAppaltanteInfo = result;
                this.elaborateConfig();
            });
        } else {
            this.elaborateConfig();
        }

    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
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

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get userService(): UserService { return this.injectable(UserService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.logger.log('onButtonClick >>>', button);
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private getSAInfoFactory(codiceSA: string) {
        return () => {
            return this.userService.getSAInfo(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceSA, this.appCode, this.userProfile.syscon)
        }
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'stazioneAppaltanteData.fields')
                };
                each(formConfig.fields, (one: SdkFormBuilderField) => {
                    if (one.type === 'FORM-SECTION') {
                        one = this.elaborateSection(one);
                    } else if (one.type === 'FORM-GROUP') {
                        one = this.elaborateGroup(one);
                    } else {
                        one = this.elaborateMapping(one);
                    }
                });
                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private elaborateMapping(field: SdkFormBuilderField): SdkFormBuilderField {
        if (isObject(field) && !isEmpty(field.mappingInput)) {
            field.data = get(this.stazioneAppaltanteInfo, field.mappingInput);
        }
        return field;
    }

    private elaborateSection(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateSection(one);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateGroup(one);
            } else {
                one = this.elaborateMapping(one);
            }
        });
        return field;
    }

    private elaborateGroup(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateSection(one);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateGroup(one);
                } else {
                    one = this.elaborateMapping(one);
                }
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'formBuilderConfig')) {
                let formBuilderConfig = get(obj, 'formBuilderConfig');
                this.formBuilderConfig = formBuilderConfig;
                this.formBuilderConfigObs.next(formBuilderConfig);
            } else if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(undefined);
            }
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.stazioneAppaltanteData.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
