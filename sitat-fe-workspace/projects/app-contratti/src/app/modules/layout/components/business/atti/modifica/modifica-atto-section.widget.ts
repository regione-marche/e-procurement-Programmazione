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
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
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
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkDocumentItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, includes, isEmpty, isEqual, isObject, isString, map as mapArray, set, split, toString, trim } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { AllegatoEntry, AllegatoMotivazioneEntry } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { Constants } from '../../../../../../app.constants';
import { DettaglioAttoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { DettaglioAttoStoreService } from '../dettaglio-atto-store.service';


@Component({
    templateUrl: `modifica-atto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaAttoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-atto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private tipoDocumento: string;
    private numPubb: string;
    private campiVisibili: Array<string>;
    private campiVisibiliString: string;
    private campiObbligatori: Array<string>;
    private campiObbligatoriString: string;
    private atto: DettaglioAttoEntry;
    private attiMap: IDictionary<string>;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);

    private motivazioneDialogConfig: SdkDialogConfig;
    public motivazioneDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;
    private daAnnullare: Array<AllegatoMotivazioneEntry> = [];

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.attiMap = this.dettaglioAttoStore.attiMap;

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadDettaglio();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get dettaglioAttoStore(): DettaglioAttoStoreService { return this.injectable(DettaglioAttoStoreService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                codGara: this.codGara,
                codLotto: this.codLotto,
                tipoDocumento: this.tipoDocumento,
                numPubb: this.numPubb,
                campiVisibili: this.campiVisibiliString,
                campiObbligatori: this.campiObbligatoriString,
                attiMap: this.attiMap,
                daAnnullare: this.daAnnullare
            };

            if (button.code === 'back-to-dettaglio-atto' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            }
            else if(button.code === 'save-atto' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {

                if(this.atto?.dataPubbSistema && this.atto?.dataPubbSistema < new Date()){
                    const fields = this.formBuilderConfig?.fields.find(item => item.code === 'documenti-data');

                    const newDocumentsFile = fields?.fieldSections?.find(f => f.code === 'documenti-data-upload');
                    const newDocumentsUrl = fields?.fieldSections?.find(f => f.code === 'documenti-data-url');

                    const documentsDataGroupFile = newDocumentsFile?.fieldSections?.find(f => f.code === 'documents-data-group');
                    const documentsDataGroupUrl = newDocumentsUrl?.fieldSections?.find(f => f.code === 'documents-data-group');
                    
                    const fileUploader = documentsDataGroupFile?.fieldGroups;

                    if(this.atto?.primaPubblicazione === '1' && fileUploader.length > 0){
                        this.salvaAtto(button, data);
                    } else if(this.atto?.primaPubblicazione === '2' && documentsDataGroupUrl?.fieldGroups && documentsDataGroupUrl?.fieldGroups?.length > 0){
                        this.salvaAtto(button, data);
                    } else {
                        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
                    }
                }
                else {
                    this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
                }
            }
            else {
                this.provider.run(button.provider, data).subscribe();
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

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
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

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');

        let campiVisibili: string = paramsMap.get('campiVisibili');
        this.campiVisibiliString = campiVisibili;
        if (!isEmpty(campiVisibili)) {
            let trimmed: string = trim(campiVisibili, '|');
            this.campiVisibili = split(trimmed, '|');
        }
        let campiObbligatori: string = paramsMap.get('campiObbligatori');
        this.campiObbligatoriString = campiObbligatori;
        if (!isEmpty(campiObbligatori)) {
            let trimmed: string = trim(campiObbligatori, '|');
            this.campiObbligatori = split(trimmed, '|');
        }
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: DettaglioAttoEntry) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = toString(keyAny);

            if (field.code === 'DATAPUBB') {
                const oggi: Date = new Date();
                field.max = oggi;
            }

            if (field.type === 'DATEPICKER' && get(restObject, field.mappingInput) != null) {
                set(field, 'data', new Date(get(restObject, field.mappingInput)))
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
                let existingDocuments: Array<AllegatoEntry> = get(this.atto, field.mappingInput);

                //let existingDocuments: Array<AllegatoEntry> = existingDocumentsWithAllegatiAnnullati.filter(item => item.motivoCanc == null);

                let sdkExistingDocuments: Array<SdkDocumentItem> = mapArray(existingDocuments, (one: AllegatoEntry) => {
                    let docItem: SdkDocumentItem = {
                        ...one,
                        code: toString(one.idAllegato),
                        titolo: one.descrizione || one.url,
                        descrizione: undefined,
                        deleteLogica: this.atto?.dataPubbSistema && this.atto?.dataPubbSistema < new Date() ? true : false ,
                        dataCanc: one.dataCanc ?? undefined,
                        motivoCanc: one.motivoCanc ?? undefined,
                        daAnnullare: false,
                        tipoFile: one.tipoFile
                    };
    
                    docItem.fileDownloadCallback = () => {
                        const factory = this.downloadDocumentoAttoFactory(+restObject.codGara, one.idAllegato);
                        return this.requestHelper.begin(factory, this.messagesPanel);
                    };
    
                    return docItem;
                });
                set(field, 'documents', sdkExistingDocuments);
                mapping = false;
            } else if (field.code === 'tipologia-pubblicazione') {
                let key: string = get(restObject, field.mappingInput);
                if (key) {
                    let value: string = get(this.attiMap, key);
                    field.data = value;
                    mapping = false;
                }
            }

            if (includes(this.campiVisibili, field.code)) {
                field.visible = true;
            }

            if (includes(this.campiObbligatori, field.code)) {
                field.validators = [
                    {
                        config: {
                            required: true
                        },
                        messages: [
                            {
                                level: 'error',
                                text: `ATTO.VALIDATORS.${field.code}-OBBLIGATORIO`
                            }
                        ]
                    }
                ]
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

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.atto, undefined, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
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

    private loadDettaglio(): void {
        let factory = this.dettaglioAttoFactory(this.codGara, this.tipoDocumento, this.numPubb);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: DettaglioAttoEntry) => {
            this.atto = result;
            this.loadForm();
        });
    }

    private dettaglioAttoFactory(codGara: string, tipoDocumento: string, numPubb: string): () => Observable<DettaglioAttoEntry> {
        return () => {
            return this.gareService.dettaglioAtto(codGara, tipoDocumento, numPubb);
        }
    }

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

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private downloadDocumentoAttoFactory(codGara: number, idAllegato: number) {
        return () => {
            return this.gareService.downloadDocumentoAtto(codGara, idAllegato);
        }
    }

    // #endregion
}
