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
import { SdkAbstractComponent, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';


@Component({
    templateUrl: `trasferimento-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class TrasferimentoModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    private userProfile: UserProfile;
    private buttonsFirstPage: SdkButtonGroupInput;
    private buttonsSecondPage: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    public config: any;
    public data: void;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: BehaviorSubject<SdkFormBuilderConfiguration> = new BehaviorSubject(null);
    public firstPage: boolean = true;

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
        this.elaborateConfig()
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (config != null) {
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

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (this.config != null) {
            const fields = cloneDeep(get(this.config, 'fields'));

            let formConfig: SdkFormBuilderConfiguration = {
                fields
            };

            let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                let mapping: boolean = true;

                return {
                    mapping,
                    field
                };
            }

            formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction,
                {
                    oggettoGara: this.config.oggettoGara,
                    descrizioneAvviso: this.config.descrizioneAvviso,
                    descrizioneProgramma: this.config.descrizioneProgramma,
                    referenteProgrammazione: this.config.referenteProgrammazione
                },
                {
                    stazioneAppaltante: this.stazioneAppaltanteInfo
                }
            );

            this.defaultFormBuilderConfig = cloneDeep(formConfig);
            this.formBuilderConfig = formConfig;
            this.formBuilderConfigObs.next(formConfig);
            this.loadSecondPageButtons();
            this.markForCheck(() => this.firstPage = false);
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(undefined);
            } else if (has(obj, 'conferma') && get(obj, 'conferma') === true) {
                this.elaborateConfig();
            } else if (has(obj, 'update') && get(obj, 'update') === true) {
                this.emitOutput(
                    {
                        reloadGrid: true
                    }
                );
            }
        }
    }

    private loadButtons(): void {
        this.buttonsFirstPage = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttonsFirstPage, this.userProfile.configurations)
        };
        this.buttonsSecondPage = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttonsSecondPage, this.userProfile.configurations)
        }
        this.buttonsSubj = new BehaviorSubject(this.buttonsFirstPage);
    }

    private loadSecondPageButtons(): void {
        this.buttonsSubj.next(this.buttonsSecondPage);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    messagesPanel: this.messagesPanel,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    codGara: this.config.codGara,
                    idAvviso: this.config.idAvviso,
                    idProgramma: this.config.idProgramma
                }
            }).subscribe(this.manageExecutionProvider);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion
}
