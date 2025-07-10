import { ChangeDetectionStrategy, Component, ElementRef, HostBinding, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, ParamMap } from "@angular/router";
import { CustomParamsFunctionResponse, FormBuilderUtilsService, HttpRequestHelper, ProtectionUtilsService, StazioneAppaltanteInfo, TabellatiCacheService } from "@maggioli/app-commons";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkAutocompleteItem, SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkMessagePanelService } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, isEmpty, isObject, isString, set } from "lodash-es";
import { Constants } from "projects/app-contratti/src/app/app.constants";
import { RicercaSchedePcp } from 'projects/app-contratti/src/app/modules/models/schede-trasmesse-pcp/schede-trasmesse-pcp.model';
import { SchedeTrasmessePcpService } from "projects/app-contratti/src/app/modules/services/schede-trasmesse-pcp/schede-trasmesse-pcp.service";
import { BehaviorSubject, Observable, of, Subject } from "rxjs";

@Component({
    templateUrl: `ricerca-schede-trasmesse-pcp-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaSchedeTrasmessePcpSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    // #region Variables

    public config: any = {};
    public data: void;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public dialogConfigObjs: Observable<SdkDialogConfig>;
    
    private dialogConfig: SdkDialogConfig;
    private buttons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private selectedItem: SdkAutocompleteItem;
    private userProfile: UserProfile;
    private impresa: any;
    private abilitazioneUffInt: string;
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private form: RicercaSchedePcp;

    @HostBinding('class') classNames = `ricerca-schede-trasmesse-pcp-section`;
    
    @ViewChild('messages') _messagePanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    // #endregion



    // #region Getters

    private get messagesPanel(): HTMLElement {
        return this._messagePanel != null ? this._messagePanel.nativeElement : undefined;
    }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get schedeTrasmessePcpService(): SchedeTrasmessePcpService { return this.injectable(SchedeTrasmessePcpService) }

    private get httpRequestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    //#region Protected

    protected onUpdateState(_state: boolean): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            });
        }
    }

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_SELECT).subscribe((form: RicercaSchedePcp) => {
            this.form = form;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onAfterViewInit(): void {
        
        this.loadForm();
    }

    //#endregion

    //#region Private

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObjs = of(this.dialogConfig);
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private loadForm(): void {

        if(isObject(this.config)){
            this.markForCheck(() => {

                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };
        
                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo
                }
        
                let customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean): CustomParamsFunctionResponse => {

                    let mapping: boolean = true;

                    if (field.code === 'uffInt' && field.type === 'TEXTBOX' && this.stazioneAppaltanteInfo.codice !== '*'){
                        field.visible = false;
                        mapping = false;
                    }
            
                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    if(field.code === 'cig' && restObject != null && restObject.cig != null){
                        mapping = false;
                        let item: SdkAutocompleteItem = {
                            text: restObject.cig,
                            _key: restObject.cig
                        };
                        set(field, 'data', item);
                    }

                    if(field.code === 'progressivoScheda' && restObject != null && restObject.progressivoScheda != null) {
                        mapping = false;
                        set(field, 'data', restObject.progressivoScheda);
                    }
            
                    return {
                        mapping,
                        field
                    };
                }
        
                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.form, providerArgs);
        
                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            })
        }
    }
   
    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    //#endregion

    //#region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                idProfilo: this.userProfile?.configurations?.idProfilo
            };

            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    //#endregion
}