import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkProviderService,
    SdkStoreService,
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
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isEqual, isObject, isString, map, set, toString } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { TranslateService } from '@ngx-translate/core';
import { AllegatoEntry, AllegatoMotivazioneEntry, AttoGeneraleEntry, RicercaAttiGeneraliForm } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { AttiGeneraliService } from 'projects/app-contratti/src/app/modules/services/atti-generali/atti-generali.service';
import { Constants } from '../../../../../../../app.constants';
import { DettaglioAttoGeneraleStoreService } from '../dettaglio-atto-generale-store.service';


@Component({
    templateUrl: `modifica-dettaglio-atto-generale-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaDettaglioAttoGeneraleSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-dettaglio-atto-generale-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);

    private motivazioneDialogConfig: SdkDialogConfig;
    public motivazioneDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

    private stazioneAppaltanteInfoProva: StazioneAppaltanteInfo;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private stazioneAppaltante: string;
    private idAtto: string;
    private attoGenerale: AttoGeneraleEntry;
    private tipoAttoMap: IDictionary<string> = {};
    private menuTabs: Array<SdkMenuTab>;
    private sidebarConfig: SdkSidebarConfig;
    private userProfile: UserProfile;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private modalConfig: any;
    private buttons: SdkButtonGroupInput;
    private buttonsPubblicato: SdkButtonGroupInput;
    private searchForm: RicercaAttiGeneraliForm;
    private attiComboItem: SdkComboBoxItem;
    private daAnnullare: Array<AllegatoMotivazioneEntry> = [];

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #endregion

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_ATTO_GENERALE).subscribe((tipiAttiGenerali: Array<ValoreTabellato>) => {
            each(tipiAttiGenerali, (tipoAttoGenerale: ValoreTabellato) => {
                this.tipoAttoMap[tipoAttoGenerale.codice] = tipoAttoGenerale.descrizione;
            });
        });

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();

        let queryParams: ParamMap = this.activatedRoute.snapshot.queryParamMap;

        this.searchForm = JSON.parse(queryParams.get('searchForm'));
        this.attoGenerale = JSON.parse(queryParams.get('attoGenerale'));
        this.stazioneAppaltante = queryParams.get('stazioneAppaltante');
        this.loadForm();
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
                if (isObject(get(this.config.body, 'sidebar'))) {
                    this.sidebarConfig = get(this.config.body, 'sidebar');
                    this.sidebarConfig.openSubject = new Subject();
                    this.sidebarConfigObs.next(this.sidebarConfig);
                }
                
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService); }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute); };

    private get store(): SdkStoreService { return this.injectable(SdkStoreService); }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper); }

    private get attiGeneraliService(): AttiGeneraliService { return this.injectable(AttiGeneraliService); }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService); }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService); }

    private get dettaglioAvvisoStoreService(): DettaglioAttoGeneraleStoreService { return this.injectable(DettaglioAttoGeneraleStoreService); }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService); }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService); }

    protected get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper); }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            
            let data: IDictionary<any> = {
                attoGenerale: this.attoGenerale,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel,
                formBuilderConfig: this.formBuilderConfig,
                idAtto: this.attoGenerale.idAtto,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                codiceFiscaleStazioneAppaltante: this.stazioneAppaltanteInfo.codFiscale,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                buttonCode: button.code,
                nomein: this.attoGenerale.stazioneAppaltante,
                daAnnullare: this.daAnnullare
            }

            if (button.code === 'back-dettaglio-atto' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {

                let dataBack: IDictionary<any> = {
                    attoGenerale: this.attoGenerale,
                    syscon: this.userProfile.syscon,
                    messagesPanel: this.messagesPanel,
                    formBuilderConfig: this.formBuilderConfig,
                    idAtto: this.attoGenerale.idAtto,
                    buttonCode: button.code,
                    nomein: this.attoGenerale.stazioneAppaltante
                }
                this.back(button, dataBack);
            } else if(button.code === 'salva-atto' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {

                if(this.attoGenerale?.dataPubbSistema && this.attoGenerale?.dataPubbSistema < new Date()){
                    const fields = this.formBuilderConfig?.fields.find(item => item.code === 'documenti-data');

                    const newDocumentsFile = fields?.fieldSections?.find(f => f.code === 'documenti-data-upload');
                    const newDocumentsUrl = fields?.fieldSections?.find(f => f.code === 'documenti-data-url');

                    const documentsDataGroupFile = newDocumentsFile?.fieldSections?.find(f => f.code === 'documents-data-group');
                    const documentsDataGroupUrl = newDocumentsUrl?.fieldSections?.find(f => f.code === 'documents-data-group');
                    
                    const fileUploader = documentsDataGroupFile?.fieldGroups;

                    if(this.attoGenerale?.primaPubb === '1' && fileUploader.length > 0){
                        this.salvaAtto(button, data);
                    } else if(this.attoGenerale?.primaPubb === '2' && documentsDataGroupUrl?.fieldGroups && documentsDataGroupUrl?.fieldGroups?.length > 0){
                        this.salvaAtto(button, data);
                    } else {
                        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
                    }
                }
                else {
                    this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
                }
            } else {
                this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;

        const fields = this.formBuilderConfig?.fields.find(item => item.code === 'documenti-data');
        const documentsFields = fields?.fieldSections?.find(f => f.type === 'DOCUMENTS-LIST');
        
        if(documentsFields && documentsFields?.documents) {

            for(let document of documentsFields?.documents){
                if(document && !isEmpty(document?.motivoCanc) && document.daAnnullare){

                    const esisteGia = this.daAnnullare.some(allegato => allegato.idAllegato === document.idAllegato && allegato.key01 === document.key01);
                    if(!esisteGia){
                        this.daAnnullare.push({
                            idAllegato: document.idAllegato,
                            key01: document.key01,
                            motivoCanc: document.motivoCanc
                        });
                    }
                }
            }

            this.dialogConfigObs.next(documentsFields?.dialogConfigObs);
        }
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'tipoAtto' && (isObject(field.data) && Object.keys(field.data).length === 0)) {
                const isEmpty = field.data === null || field.data === undefined || (isObject(field.data) && Object.keys(field.data).length === 0);
        
                const tipologiaField = this.formBuilderConfig.fields[1].fieldSections.find(f => f.code === 'tipologia');

                if (tipologiaField && isEmpty) {
                    let tipologia: any = {
                        code: 'tipologia',
                        data: null
                    };

                    this.formBuilderDataSubject.next(tipologia);
                }
            }
        }
    }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'rup') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.attoGenerale.rup
                }
            } else if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.attoGenerale.stazioneAppaltante
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item.data != null) {
            this.loadForm();
        }
    }

    // #endregion

    // #region Private

    private initDialog(action: string): void {
        let header = 'DIALOG.DELETE-TITLE';
        let message = 'DIALOG.DELETE-TEXT';
   
        switch(action) {
            case 'SALVA-ATTO':
                header = this.translateService.instant('DIALOG.PUBBLICA-NUOVO-ALLEGATO-HEADER');
                message = this.translateService.instant('DIALOG.PUBBLICA-NUOVO-ALLEGATO-MESSAGE');
                break;
            default:
                header = this.translateService.instant('DIALOG.BACK-TITLE');
                message = this.translateService.instant('DIALOG.BACK-TEXT');
        }

        this.dialogConfig = {
            header: this.translateService.instant(header),
            message: this.translateService.instant(message),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
    
        this.dialogConfigObs.next(this.dialogConfig);        
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        this.initDialog('');
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }
    
    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private salvaAtto(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        this.initDialog('SALVA-ATTO');
        let func = this.salvaAttoConfirm(button, data);
        this.dialogConfig.open.next(func);
    }
    
    private salvaAttoConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    private loadForm(): void {

        let providerArgs: IDictionary<any> = {
            stazioneAppaltante: this.stazioneAppaltanteInfo
        }

        const fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));
        let formConfig: SdkFormBuilderConfiguration = this.protectionUtilsService.applyFormProtections({ fields }, this.userProfile.configurations);

        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, this.customPopulateFunction, this.attoGenerale, providerArgs);
        
        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: AttoGeneraleEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code === 'stazione-appaltante' && field.type === 'TEXT') {
            if (get(restObject, field.mappingInput)) {
                field.data = this.stazioneAppaltanteInfo.nome;
                mapping = false;
            }
        } else if ( field.code === 'rup' && restObject != null && restObject.tecnico != null ) {
            if (isObject(restObject.tecnico)) {
                let item: SdkAutocompleteItem = {
                    text: `${this.attoGenerale?.tecnico?.nominativo} (${this.attoGenerale?.tecnico?.cf})`,
                    _key: this.attoGenerale?.tecnico?.codice
                };
                set(field, 'data', item);
                mapping = false;
            }
        } else if(field.code === 'tipoAtto' && field.type === 'COMBOBOX') {
            if((restObject != null || restObject != undefined) && +restObject.tipologia < 100){
                let comboItem: SdkComboBoxItem = {
                    key: '1',
                    value: this.translateService.instant('ATTI-GENERALI-LIST-CODE.ATTI-GENERALI')
                }
                set(field, 'data', comboItem);
                this.attiComboItem = comboItem;
            }
            else {
                let comboItem: SdkComboBoxItem = {
                    key: '2',
                    value: this.translateService.instant('ATTI-GENERALI-LIST-CODE.MANIFESTAZIONE-E-ALTRI')
                }
                set(field, 'data', comboItem);
                this.attiComboItem = comboItem;
            }
            mapping = false;
        } else if (field.code === 'tipologia' && field.type === 'COMBOBOX') {

            //Controllo su campo per disabilitarlo nel caso in cui "Atti generali" non sia popolato
            if(this.attiComboItem?.key == undefined || this.attiComboItem?.key == null){
                set(field, 'disabled', true);
                mapping = false;
            }

            const value = get(restObject, field.mappingInput);

            if (value) {
                let comboItem: SdkComboBoxItem = {
                    key: value,
                    value: this.tipoAttoMap[value]
                }
                set(field, 'data', comboItem);
                mapping = false;
            }
        } else if (field.code === 'dataPrimaPubb') {
            let value = get(restObject, field.mappingInput);
            if (value != null || value != undefined) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                mapping = false;
            }
            field.max = new Date();
            mapping = false;
        } else if(field.code === 'dataPubbSistema') {
            let value = get(restObject, field.mappingInput);
            if (value != null || value != undefined) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.fullDateTimeFormat);
                mapping = false;
            }
            else {
                field.visible = false;
                mapping = false;
            }
        } else if (field.type === 'DOCUMENTS-LIST') {
            let existingDocuments: Array<AllegatoEntry> = get(this.attoGenerale, field.mappingInput);

            //let existingDocuments: Array<AllegatoEntry> = existingDocumentsWithAllegatiAnnullati.filter(item => item.motivoCanc == null);

            let sdkExistingDocuments: Array<SdkDocumentItem> = map(existingDocuments, (one: AllegatoEntry) => {
                let docItem: SdkDocumentItem = {
                    ...one,
                    code: toString(one.idAllegato),
                    titolo: one.descrizione || one.url,
                    descrizione: undefined,
                    deleteLogica: this.attoGenerale?.dataPubbSistema && this.attoGenerale?.dataPubbSistema < new Date() ? true : false ,
                    dataCanc: one.dataCanc ?? undefined,
                    motivoCanc: one.motivoCanc ?? undefined,
                    daAnnullare: false,
                    tipoFile: one.tipoFile
                };

                docItem.fileDownloadCallback = () => {
                    const factory = this.downloadDocumentoAttoGeneraleFactory(+this.attoGenerale.idAtto, one.idAllegato);
                    return this.requestHelper.begin(factory, this.messagesPanel);
                };

                return docItem;
            });
            set(field, 'documents', sdkExistingDocuments);
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

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsPubblicato = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsPubblicato, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private downloadDocumentoAttoGeneraleFactory(idAtto: number, numDoc: number) {
        return () => {
            return this.attiGeneraliService.downloadDocumentoAttoGenerale(idAtto, numDoc);
        }
    }

    // #endregion
}
