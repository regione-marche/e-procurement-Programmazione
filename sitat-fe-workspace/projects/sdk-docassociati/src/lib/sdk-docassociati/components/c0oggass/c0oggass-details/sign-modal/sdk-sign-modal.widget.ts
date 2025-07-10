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
} from "@angular/core";
import { IDictionary, SdkAbstractComponent, SdkDateHelper, SdkProviderService, UserProfile } from "@maggioli/sdk-commons";
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService
} from "@maggioli/sdk-controls";
import { cloneDeep, get, has, isObject, isString } from "lodash-es";
import { BehaviorSubject, isEmpty, Subject } from "rxjs";
import { SignatoryDto } from "../../../../model/sdk-docassociati.model";
import { CustomParamsFunction, FormBuilderUtilsService, ProtectionUtilsService } from "@maggioli/sdk-appaltiecontratti-base";

@Component({
    templateUrl: `sdk-sign-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SdkSignModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {
   
    
    // #region Variables

    @ViewChild("messages") _messagesPanel: ElementRef;

    public config: any;
    public data: void;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;

    private buttons: SdkButtonGroupInput;

    private signatory: SignatoryDto
    private errorData: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {
        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        if(this.errorData != null){
            this.sdkMessagePanelService.showError(this.messagesPanel, [{ message: this.errorData }]);
        }else {
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [{ message: 'SDK-DOC-ASSOCIATI.SIGN-SUCCESS' }]);
        }       
        this.loadForm();
        
    }

    protected onDestroy(): void {}

    protected onOutput(_data: void): void {}

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.signatory = this.config.signatory;
                this.errorData = this.config.errorData;
                this.isReady = true;
            });
        }
    }

    protected onData(_data: void): void {}

    // #endregion

    // #region Getters

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }    

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }
    
    // #endregion

    // #region Public

    protected onUpdateState(state: boolean): void { }

        public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.defaultFormBuilderConfig = config;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.config.buttons
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadForm(): void {
		let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'fields'));

		let formConfig: SdkFormBuilderConfiguration = {
			fields
		};

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: SignatoryDto) => {
            let mapping: boolean = true;
            

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            if(field.code === 'date'){
                let value = get(restObject, field.mappingInput);
                if (value != null && field.type === 'TEXT') {
                    field.data = this.dateHelper.format(value, this.config.locale.fullDateformat);
                    mapping = false;
                }
            }
            

            return {
                mapping,
                field
            };
        }

		formConfig = this.formBuilderUtilsService.populateForm(this.config, formConfig, true, customPopulateFunction, this.signatory, undefined, null);

		this.defaultFormBuilderConfig = cloneDeep(formConfig);
		this.formBuilderConfig = formConfig;
		this.formBuilderConfigObs.next(formConfig);
	}


    public manageOutputField(field: SdkFormBuilderField): void {
       
    }

        public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button)) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                type: 'BUTTON',
                data:{
                    code:'modal-close-button'
                }
            };
           
            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(undefined);
            }
        }
    }

   
 
    // #endregion
}
