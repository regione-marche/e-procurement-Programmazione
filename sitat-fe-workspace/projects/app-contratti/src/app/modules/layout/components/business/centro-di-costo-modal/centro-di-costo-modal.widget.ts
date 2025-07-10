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
    CentroDiCostoEntry,
    FormBuilderUtilsService,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
} from '@maggioli/app-commons';
import { SdkAbstractComponent, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../../../../app.constants';

@Component({
    templateUrl: `centro-di-costo-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CentroDiCostoModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private selectedItem: SdkAutocompleteItem;
    private userProfile: UserProfile;
    private centroDiCosto: any;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
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
                this.centroDiCosto = this.config.centroDiCosto;
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

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

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
                    fields: get(this.config, 'cdcData.fields')
                };

                let checkCampiSolaLetturaDaConfig: boolean = true;
                if (this.selectedItem == null || isEmpty(this.selectedItem)) {
                    checkCampiSolaLetturaDaConfig = false;
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, checkCampiSolaLetturaDaConfig);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, undefined, this.centroDiCosto);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }



    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'formBuilderConfig')) {
                let formBuilderConfig = get(obj, 'formBuilderConfig');
                this.formBuilderConfig = formBuilderConfig;
                this.formBuilderConfigObs.next(formBuilderConfig);
            } else if (has(obj, 'close') && get(obj, 'close') === true) {
                if (has(obj, 'data') && isObject(get(obj, 'data'))) {
                    let data: CentroDiCostoEntry = get(obj, 'data') as CentroDiCostoEntry;
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: data.denominazione,
                        _key: data.codiceCentro
                    };
                    this.emitOutput({ code: 'cdc', data: item });
                } else {
                    this.emitOutput(undefined);
                }
            }
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.cdcData.buttons, this.userProfile.configurations)
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
                    centroDiCosto: this.centroDiCosto,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion
}
