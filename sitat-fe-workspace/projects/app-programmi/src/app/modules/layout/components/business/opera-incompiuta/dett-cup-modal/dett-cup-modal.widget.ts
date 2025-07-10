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
import { CustomParamsFunction, FormBuilderUtilsService, HttpRequestHelper, ProtectionUtilsService } from '@maggioli/app-commons';
import {
    SDK_APP_CONFIG,
    SdkAbstractComponent,
    SdkAppEnvConfig,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkRadioConfig,
    SdkRadioInput,
    SdkRadioOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isObject } from 'lodash-es';
import { Constants } from 'projects/app-programmi/src/app/app.constants';
import { DettaglioCupIniz } from 'projects/app-programmi/src/app/modules/models/opere/opere-incompiute.model';
import { ProgrammiService } from 'projects/app-programmi/src/app/modules/services/programmi/programmi.service';
import { BehaviorSubject, Subject } from 'rxjs';
import { map } from 'rxjs/operators';



@Component({
    templateUrl: `dett-cup-modal.widget.html`,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioCupModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {
    
    // #region Variables
    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private cup: string;
    private inizDettaglioCup: DettaglioCupIniz;


    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void {
        const factory = this.inizImportDettaglioCup(this.userProfile.syscon);
        this.requestHelper.begin(factory, this.messagesPanel)
        .pipe(
            map(this.afterInizDettaglioCup),
            map(() => this.loadForm())
        ).subscribe();       
        this.loadForm();
    }

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.loadButtons();
    }

    protected onDestroy(): void { }

    protected onOutput(data: any): void { }

    protected onConfig(config: any): void {
        this.markForCheck(() => {
            this.config = { ...config };
            this.cup = this.config.cup;
            this.isReady = true;
        });
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters


      public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            if(button.code === 'set'){
                let username = this.formBuilderConfig.fields[0].fieldSections[0].data;
                let password = this.formBuilderConfig.fields[0].fieldSections[1].data;

                if (isEmpty(username) || isEmpty(password)) {
                    this.sdkMessagePanelService.showError(this.messagesPanel, [
                        {
                            message: 'ERRORS.USER-PWD-MANDATORY'
                        }
                    ]);
                    this.scrollToMessagePanel(this.messagesPanel);
                } else {
                    this.provider.run('APP_PROGRAMMI_OPERA_INCOMPIUTA', {
                        type: 'BUTTON',   
                        buttonCode: 'dettaglio-cup',               
                        data: {
                            cup: this.cup,
                            username,
                            password,
                            syscon: this.userProfile.syscon,
                            messagesPanel: this.messagesPanel
                        }
                    }).subscribe(this.manageExecutionProvider)
                }
            } else {
                this.provider.run('APP_PROGRAMMI_MODAL_WINDOW', {
                    type: 'BUTTON',
                    data: {
                        code: 'modal-close-button'
                    }
                }).subscribe(this.manageExecutionProvider)
            }
        }
    }

    private afterInizDettaglioCup = (result: any) => {
        this.inizDettaglioCup = result; 
    }

    private scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }


    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {            
            this.emitOutput(get(obj, 'response'));        
        }
    }

    public manageOutputField(field: SdkFormBuilderField): void {}

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;


            if(field.code === 'username' && isObject(this.inizDettaglioCup) && !isEmpty(this.inizDettaglioCup.username)){
                field.data = this.inizDettaglioCup.username;
                mapping = false;
            }

            if(field.code === 'password' && isObject(this.inizDettaglioCup) && !isEmpty(this.inizDettaglioCup.password)){
                field.data = this.inizDettaglioCup.password;
                mapping = false;
            }
          
            return {
                mapping,
                field
            };
        }


        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

      private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);

    }

    protected inizImportDettaglioCup(syscon): any {
        return () => {
            return this.programmiService.getInizDettaglioCup(syscon);
        }
    }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }
    
}

