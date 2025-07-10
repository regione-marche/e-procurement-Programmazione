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
import { ProtectionUtilsService } from '@maggioli/app-commons';
import {
    SdkAbstractComponent,
    SdkProviderService,
    SdkStoreService,
    SdkValidatorConfig,
    SdkValidatorService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isObject, isUndefined } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';



@Component({
    templateUrl: `update-intervento-norip-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class UpdateInterventoNoRipModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.elaborateConfig();
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

    private get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'data.fields')
                };
                each(formConfig.fields, (one: SdkFormBuilderField) => {
                    if (one.type === 'FORM-SECTION') {
                        one = this.elaborateSection(one);
                    } else if (one.type === 'FORM-GROUP') {
                        one = this.elaborateGroup(one);
                    } else {
                        one = this.elaborateProvider(one);
                    }
                });
                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private elaborateProvider(field: SdkFormBuilderField): SdkFormBuilderField {
        if (!isEmpty(field.itemsProviderCode)) {
            field.itemsProvider = this.provider.run(field.itemsProviderCode);
        }

        if (!isEmpty(field.validators)) {
            each(field.validators, (one: any) => {
                if (isObject(one.config)) {
                    let config: SdkValidatorConfig = one.config;
                    if (get(config, 'required') === true) {
                        one.validator = this.validator.run('MANDATORY', one.messages);
                    } else if (!isEmpty(get(config, 'regexp'))) {
                        one.validator = this.validator.run('REGEXP', one.messages);
                    } else {
                        one.validator = this.validator.run(one.validator, one.messages);
                    }
                }
            });
        }
        if (field.code === 'motivo') {
            field.data = this.config.motivo;
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
                one = this.elaborateProvider(one);
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
                    one = this.elaborateProvider(one);
                }
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }

    private manageExecutionProvider = (obj: any) => {
        if (!isUndefined(obj))
            this.emitOutput(obj);
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

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                buttonCode: button.code,
                data: {
                    code: button.code
                },
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                messagesPanel: this.messagesPanel,
                idIntervento: this.config.idIntervento,
                idProgramma: this.config.idProgramma
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
