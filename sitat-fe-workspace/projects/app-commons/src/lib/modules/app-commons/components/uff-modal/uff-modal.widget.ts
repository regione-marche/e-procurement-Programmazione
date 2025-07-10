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
import {
    SdkAbstractComponent,
    SdkProviderService,
    SdkStoreService,
    SdkValidatorConfig,
    SdkValidatorService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, set } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { Ufficio } from '../../models/uffici/uffici.model';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `uff-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class UffModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private selectedItem: SdkAutocompleteItem;
    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.elaborateConfig();
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.selectedItem = this.config.selectedItem;
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

    private get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'ufficiData.fields')
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
        if (isObject(this.selectedItem)) {
            set(field, 'data', get(this.selectedItem, field.mappingInput));
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
        if (isObject(obj)) {
            if (has(obj, 'formBuilderConfig')) {
                let formBuilderConfig = get(obj, 'formBuilderConfig');
                this.formBuilderConfig = formBuilderConfig;
                this.formBuilderConfigObs.next(formBuilderConfig);
            } else if (has(obj, 'close') && get(obj, 'close') === true) {
                if (has(obj, 'data') && isObject(get(obj, 'data'))) {
                    let data: Ufficio = get(obj, 'data');
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: data.denominazione,
                        _key: data.id
                    };
                    this.emitOutput(item);
                } else {
                    this.emitOutput(undefined);
                }
            }
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.ufficiData.buttons, this.userProfile.configurations)
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
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    messagesPanel: this.messagesPanel,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    selectedItem: this.selectedItem
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion
}
