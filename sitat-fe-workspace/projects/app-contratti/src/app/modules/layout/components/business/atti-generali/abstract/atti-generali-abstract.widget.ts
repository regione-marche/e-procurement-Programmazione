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
import {
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    Tecnico,
    UffEntry,
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
    SdkComboBoxItem,
    SdkDialogConfig,
    SdkDocumentItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkModalConfig,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, has, isEmpty, isEqual, isObject, isString, map as mapArray, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { AvvisoEntry, ExistingAvvisoDocument } from '../../../../../models/avviso/avviso.model';
import { AvvisiService } from '../../../../../services/avvisi/avvisi.service';
import { AttoGeneraleEntry } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';

@Directive()
export abstract class AttiGeneraliAbstractWidget extends SdkBusinessAbstractWidget<void> implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    protected userProfile: UserProfile;
    protected modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    protected attoGenerale: AttoGeneraleEntry;
    protected tipiAttoGeneraleMap: IDictionary<string> = {};
    private isUpdate: boolean;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private infoBoxModalConfig: IDictionary<any>;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.isUpdate = isObject(this.attoGenerale);
        this.elaborateConfig();
        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
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

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    protected get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    protected get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    protected get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    protected get avvisiService(): AvvisiService { return this.injectable(AvvisiService) }

    // #endregion

    // #region Private

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo
                }

                if (this.isUpdate && this.stazioneAppaltanteInfo != null && this.stazioneAppaltanteInfo.codice === '*') {
                    providerArgs = {
                        stazioneAppaltante: { codice: this.attoGenerale.stazioneAppaltante }
                    }
                }

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField) => {
                    let mapping: boolean = true;

                    if (field.code === 'stazione-appaltante' && this.stazioneAppaltanteInfo != null && this.stazioneAppaltanteInfo.codice !== '*') {
                        field.data = this.stazioneAppaltanteInfo.nome;
                        mapping = false;
                    }

                    if (this.isUpdate) {
                        if (field.type === 'COMBOBOX') {
                            let key: string = get(this.attoGenerale, 'tipoAvviso') + '';
                            let value = this.tipiAttoGeneraleMap[key];
                            let comboItem: SdkComboBoxItem = {
                                key: key,
                                value: value
                            }
                            set(field, 'data', comboItem);
                            mapping = false;
                        } else if (field.type === 'DATEPICKER' && !isEmpty(get(this.attoGenerale, field.mappingInput))) {
                            set(field, 'data', new Date(get(this.attoGenerale, field.mappingInput)));
                            mapping = false;
                        } else if (field.code === 'comune' && field.type === 'AUTOCOMPLETE') {
                            let data: string = get(this.attoGenerale, field.mappingInput);
                            if (data != null) {
                                let item: SdkAutocompleteItem = {
                                    text: data,
                                    _key: data
                                };
                                set(field, 'data', item);
                                mapping = false;
                            }
                        } else if (field.type === 'AUTOCOMPLETE' && field.code =='rup') {
                            let data: Tecnico = get(this.attoGenerale, 'rupEntry');
                            if (data) {
                                let item: SdkAutocompleteItem = {
                                    ...data,
                                    text: `${data.nominativo} (${data.cf})`,
                                    _key: data.codice
                                };
                                set(field, 'data', item);
                                mapping = false;
                            }
                        }  else if (field.type === 'AUTOCOMPLETE' && field.code =='ufficio') {
                            let data: UffEntry = get(this.attoGenerale, 'ufficio');
                            if (data) {
                                let item: SdkAutocompleteItem = {
                                    ...data,
                                    text: `${data.denominazione}`,
                                    _key: data.id+''
                                };
                                set(field, 'data', item);
                                mapping = false;
                            }
                        } /*else if (field.type === 'DOCUMENTS-LIST') {
                            let existingDocuments: Array<ExistingAvvisoDocument> = get(this.attoGenerale, field.mappingInput);
                            let sdkExistingDocuments: Array<SdkDocumentItem> = mapArray(existingDocuments, (one: ExistingAvvisoDocument) => {
                                let docItem: SdkDocumentItem = {
                                    ...one,
                                    code: toString(one.numDoc)
                                };

                                docItem.fileDownloadCallback = () => {
                                    const factory = this.downloadDocumentoAvvisoFactory(this.attoGenerale.idAtto, this.attoGenerale.stazioneAppaltante, one.numDoc);
                                    return this.requestHelper.begin(factory, this.messagesPanel);
                                };

                                return docItem;
                            });
                            set(field, 'documents', sdkExistingDocuments);
                            mapping = false;
                        }*/
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    return {
                        mapping,
                        field
                    };
                }

                let infoBox: boolean = this.isUpdate === true && this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);
                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, this.isUpdate === true);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, this.isUpdate, customPopulateFunction, this.attoGenerale, providerArgs);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj) && has(obj, 'formBuilderConfig')) {
            let formBuilderConfig = get(obj, 'formBuilderConfig');
            this.formBuilderConfig = formBuilderConfig;
            this.formBuilderConfigObs.next(formBuilderConfig);
        }
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.addSubscription(this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider));
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private downloadDocumentoAvvisoFactory(idAvviso: number, codein: string, numDoc: number) {
        return () => {
            return this.avvisiService.downloadDocumentoAvviso(idAvviso, codein, numDoc);
        }
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
            0
            if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.attoGenerale.stazioneAppaltante
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {

            let stazioneAppaltante = this.attoGenerale == null ? this.stazioneAppaltanteInfo.codice : this.attoGenerale.stazioneAppaltante;
            let obj: IDictionary<any> = {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    stazioneAppaltante: stazioneAppaltante,
                    syscon: this.userProfile.syscon,
                    messagesPanel: this.messagesPanel,
                    setUpdateState: this.setUpdateState
                }
            }

            this.addSubscription(this.provider.run(button.provider, obj).subscribe(this.manageExecutionProvider));
        }
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
    }

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