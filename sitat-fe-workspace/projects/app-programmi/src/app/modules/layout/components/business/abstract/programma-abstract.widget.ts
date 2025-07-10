import {
    AfterViewInit,
    ChangeDetectorRef,
    Directive,
    ElementRef,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    Tecnico,
    Ufficio,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    SdkValidatorService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, has, isEmpty, isEqual, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../app.constants';
import { InizNuovoProgramma, ProgrammaEntry } from '../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../services/programmi/programmi.service';

@Directive()
export abstract class ProgrammaAbstractWidget extends SdkBusinessAbstractWidget<void> implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    protected userProfile: UserProfile;
    protected modalConfig: SdkModalConfig<any, void, any>;
    protected idProgramma: string;
    protected programma: ProgrammaEntry;
    private tipologia: string;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    protected isUpdate: boolean;
    private infoBoxModalConfig: IDictionary<any>;
    public dialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    private dialogConfig: SdkDialogConfig;
    private iniz: InizNuovoProgramma;
    private soloOpere = false;
    private anno: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.isUpdate = isObject(this.programma);       
        this.addSubscription(this.activatedRoute.queryParamMap.subscribe((queryParams: ParamMap) => {
            this.markForCheck(() => {
                if (queryParams.has('tipologia')) {
                    this.tipologia = queryParams.get('tipologia');
                } else {
                    this.tipologia = '1';
                }                                

                const factory = this.inizProgramma(this.stazioneAppaltanteInfo.codice, this.userProfile.syscon);
                this.requestHelper.begin(factory, this.messagesPanel)
                .pipe(
                    map(this.afterInizProgramma),
                    map(() => this.elaborateConfig())
                ).subscribe(); 
                
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
            });
        }));
        
           
        
        
        
        
      
       
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
                this.isReady = true;
            });
        }
    }

    protected manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field) && isObject(field.data)) {            
            if (field.code === 'solo-compilazione-scheda') {
                this.markForCheck(() => {
                    let checked = field.data[0].checked;
                    if(checked === true){
                        this.formBuilderConfig.fields[1].visible = false;
                        this.formBuilderConfig.fields[2].visible = false;
                        each(this.formBuilderConfig.fields[1].fieldSections, (field: SdkFormBuilderField) => {
                            field.data = null;
                        });
                        each(this.formBuilderConfig.fields[2].fieldSections, (field: SdkFormBuilderField) => {
                            field.data = null;
                        });
                        this.soloOpere = true;
                        let data;
                        each(this.formBuilderConfig.fields[0].fieldSections, (field: SdkFormBuilderField) => {

                            if(field.code === 'annoInizio'){
                                data = field.data;
                                field.data = null;
                                field.visible = false;
                            }
                        });
                        each(this.formBuilderConfig.fields[0].fieldSections, (field: SdkFormBuilderField) => {

                            if(field.code === 'annoRiferimento'){
                                field.data = data;
                                field.visible = true;
                            }
                        });
                        

                    } else {
                        this.formBuilderConfig.fields[1].visible = true;
                        this.formBuilderConfig.fields[2].visible = true;
                        this.soloOpere = false;
                        let data;
                        each(this.formBuilderConfig.fields[0].fieldSections, (field: SdkFormBuilderField) => {
       
                            if(field.code === 'annoRiferimento'){
                                data= field.data;
                                field.data = null;
                                field.visible = false;
                            } 
                        }); 
                        each(this.formBuilderConfig.fields[0].fieldSections, (field: SdkFormBuilderField) => {
       
                            if(field.code === 'annoInizio'){
                                field.data = data;
                                field.visible = true;
                            } 
                        }); 
                        

                        

                    }
                });
            }
        } else if (isObject(field)){
            if ((field.code === 'annoInizio' || field.code === 'annoRiferimento') && field.data != null) {
                this.anno = field.data.toString();
            }
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    protected get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    protected get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    protected get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

    protected get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    protected get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }    

    // #endregion

    // #region Private

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    

    private inizProgramma(stazioneAppaltante: string,syscon: string): any {
        return () => {
            return this.programmiService.getInizNuovoProgramma(stazioneAppaltante,syscon);
        }
    }

    private afterInizProgramma= (result: any) => {
        this.iniz = result; 
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let fieldsLabel = 'body.lavoriFields';
                if (this.tipologia === '2') {
                    fieldsLabel = 'body.fornitureFields';
                }

                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, fieldsLabel)
                };
                if(isObject(this.programma) && this.programma.idProgramma.startsWith("OI")){
                    formConfig.fields[1].visible = false;
                    formConfig.fields[2].visible = false;                  
                }

                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo
                }

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField) => {
                    let mapping: boolean = true;                    
                    
                    if (field.code === 'tipo-programma') {
                        if (this.tipologia === '1')
                            field.data = this.translateService.instant('PROGRAMMA.LAVORI');
                        else
                            field.data = this.translateService.instant('PROGRAMMA.FORNITURE');
                    }
                    if (this.isUpdate) {
                        if (field.code === 'stazione-appaltante') {
                            field.data = this.stazioneAppaltanteInfo.nome;
                            mapping = false;
                        } else if (field.code === 'tipo-programma') {
                            if (this.tipologia === '1')
                                field.data = this.translateService.instant('PROGRAMMA.LAVORI');
                            else
                                field.data = this.translateService.instant('PROGRAMMA.FORNITURE');
                            mapping = false;
                        } else if (field.code === 'ufficio') {
                            if (isObject(this.programma.ufficio)) {
                                let data: Ufficio = get(this.programma, 'ufficio');
                                let item: SdkAutocompleteItem = {
                                    ...data,
                                    text: data.denominazione,
                                    _key: data.id
                                };
                                set(field, 'data', item);
                                mapping = false;
                            }
                        } else if (field.code === 'responsabile') {
                            if (isObject(this.programma.rup)) {
                                let data: Tecnico = get(this.programma, 'rup');
                                let item: SdkAutocompleteItem = {
                                    ...data,
                                    text: `${data.nominativo} (${data.cf})`,
                                    _key: data.codice
                                };
                                set(field, 'data', item);
                                mapping = false;
                            }
                        } else if (field.code === 'descrizione-programma') {
                            set(field, 'data', this.programma.descrizioneBreve);
                            mapping = false;
                        }
                        if (field.type === 'DATEPICKER' && get(this.programma, field.mappingInput) != null) {
                            set(field, 'data', new Date(get(this.programma, field.mappingInput)))
                            mapping = false;
                        }
                    }

                    if(field.code === 'ufficio' && this.iniz != null && this.iniz.showUfficio != null){
                        field.visible = this.iniz.showUfficio && this.iniz.idUfficio == null;
                        mapping = false;
                    }

                    if(field.code === 'ufficio-desc' && this.iniz != null && this.iniz.idUfficio != null){
                        field.visible = this.iniz.showUfficio;
                        field.data = this.iniz.denomUfficio;
                        mapping = false;
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    return {
                        mapping,
                        field
                    };
                }

                let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, this.isUpdate === true);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, this.isUpdate, customPopulateFunction, this.programma, providerArgs, infoBox);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private initDialog(): void {
        
       
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj) && has(obj, 'formBuilderConfig')) {
            let formBuilderConfig = get(obj, 'formBuilderConfig');
            this.formBuilderConfig = formBuilderConfig;
            this.formBuilderConfigObs.next(formBuilderConfig);
        }
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


    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfig);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.addSubscription(
                this.provider.run(button.provider, {
                    type: 'BUTTON',
                    data: data
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }

    private saveConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.saveConfirmFunc(button, data);
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.SAVE-TITLE'),
            message: this.translateService.instant('DIALOG.SAVE-SOLO-OPERE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };                
        this.dialogConfigObs.next(this.dialogConfig);
        this.dialogConfig.open.next(func);
    }

    private saveConfirmFunc(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.addSubscription(
                this.provider.run(button.provider, {
                    type: 'BUTTON',
                    data: data
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }


    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion

    // #region Public

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };
            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                code: button.code,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel,
                tipologia: this.tipologia,
                idProgramma: this.idProgramma,
                iniz: this.iniz,
                setUpdateState: this.setUpdateState
            };

            if (button.code === 'back-button' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);            
            } else if (button.code === 'save-button' && this.soloOpere === true) {
                this.saveConfirm(button, data);
            } else{
                this.addSubscription(
                    this.provider.run(button.provider, {
                        type: 'BUTTON',
                        data: data
                    }).subscribe(this.manageExecutionProvider)
                );
            }
        }
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void { }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (this.isUpdate === true && isObject(this.infoBoxModalConfig) && isObject(item)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, 'component'),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, 'fields'),
                    buttons: get(this.infoBoxModalConfig, 'buttons'),
                    userProfile: this.userProfile,
                    mnemonico: item.mnemonico
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
    }

    // #endregion

}