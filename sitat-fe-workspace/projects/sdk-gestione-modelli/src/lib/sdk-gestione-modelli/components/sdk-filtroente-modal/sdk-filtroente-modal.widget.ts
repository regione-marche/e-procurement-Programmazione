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
} from '@angular/core';
import {
    SDK_APP_CONFIG,
    SdkAbstractComponent,
    SdkAppEnvConfig,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkSimpleSearchConfig,
    SdkSimpleSearchInput,
    SdkTextboxConfig,
    SdkTextInput,
    SdkTreeConfig,
} from '@maggioli/sdk-controls';
import { clone, each, get, has, head, isArray, isEmpty, isObject, set, split } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { SdkGestioneModelliConstants } from '../../sdk-gestione-modelli.constants';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';




@Component({
    templateUrl: `sdk-filtroente-modal.widget.html`,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class SDKFiltroEnteModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {
    

    // #region Variables

    public config: any;
    public data: any;
    public originalData: any;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>; 
    private userProfile: UserProfile;
    private currentfilter: string;
    
    

    public filtroenteConfigurationObs: Observable<SdkTextboxConfig> = of({
        code: 'filtroentita',
        label: 'SDK-MODELLO.GENERAL-DATA.FILTROENT'
    } as SdkTextboxConfig);

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void {
    }

    protected onOutput(item: any): void {
        if(isObject(item)){
            this.currentfilter = get(item, 'data');
        }
    }

    protected onInit(): void {

        this.addSubscription(this.store.select(SdkGestioneModelliConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };               
                this.isReady = true;
            });
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

    // #region Private
   

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button.code === 'set-button') {

            this.emitOutput({
                op: 'update',
                // value: this.padRight(8, this.currentCpv),
                value: this.currentfilter
            });
        } else {
            this.emitOutput(undefined);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void { }

    // #endregion
}
