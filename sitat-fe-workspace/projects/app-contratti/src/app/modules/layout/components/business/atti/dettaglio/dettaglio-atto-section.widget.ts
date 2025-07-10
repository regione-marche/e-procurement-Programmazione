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
    SdkMenuTab,
    SdkMessagePanelService,
    SdkSidebarConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {
    cloneDeep,
    filter,
    get,
    includes,
    isEmpty,
    isEqual,
    isObject,
    isString,
    map as mapArray,
    remove,
    set,
    split,
    toString,
    trim
} from 'lodash-es';
import { FlussiAtto } from 'projects/app-contratti/src/app/modules/models/pubblicazioni/pubblicazione-atto.model';
import { PubblicazioneAttoService } from 'projects/app-contratti/src/app/modules/services/pubblicazioni/pubblicazione-atto.service';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { AllegatoEntry } from '../../../../../models/atti-generali/atti-generali.model';
import { DettaglioAttoEntry, GaraEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { DettaglioAttoStoreService } from '../dettaglio-atto-store.service';


@Component({
    templateUrl: `dettaglio-atto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioAttoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-atto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttonsRO: SdkButtonGroupInput;
    private buttonsPcp: SdkButtonGroupInput;
    private buttons: SdkButtonGroupInput;
    private buttonsWithoutDelete: SdkButtonGroupInput;
    private defaultButtons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Subject<SdkDialogConfig> = new Subject<SdkDialogConfig>();

    private datePickerDialogConfig: SdkDialogConfig;
    public datePickerDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

    private motivazioneDialogConfig: SdkDialogConfig;
    public motivazioneDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

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
    private menuTabs: Array<SdkMenuTab>;
    private attiMap: IDictionary<string>;
    private anagraficaGaraPubblicata: boolean;
    private checkSmartCig: boolean;
    private sidebarConfig: SdkSidebarConfig;
    public pubblicazioni: Array<FlussiAtto>;
    public gara: GaraEntry;
    private annullato: boolean;
    private dataProgrammazione: Date;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            this.checkSmartCig = this.protectionUtilsService.isMenuTabVisible('W9.SMARTCIG-scheda.INVII', this.userProfile.configurations);
        }));

        this.attiMap = this.dettaglioAttoStore.attiMap;

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog('NORMAL');
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadAnagraficaGaraPubblicata()
            .pipe(
                map(this.elaborateAnagraficaGaraPubblicata),
                mergeMap(this.loadDettaglio),
                map(this.elaborateDettaglio),
                map(this.loadGaraFactory),
                mergeMap(this.loadGara),
                map(this.elaborateGara),
                map(() => this.refreshTabs()),
                map(() => this.loadForm()),
                mergeMap(this.getListaPubblicazioni),
                map(this.elaborateListaPubblicazioni)
            ).subscribe();
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
                this.menuTabs = this.config.menuTabs;
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

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get dettaglioAttoStore(): DettaglioAttoStoreService { return this.injectable(DettaglioAttoStoreService) }

    private get pubblicazioneAttoService(): PubblicazioneAttoService { return this.injectable(PubblicazioneAttoService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            if (button.code === 'delete-atto') {
                let func = this.executeButtonProviderFactory(button);
                this.dialogConfig.open.next(func);
            } else if(button.code === 'programma-pubblicazione') {
                this.programmaPubblicazione(+this.atto?.codGara, this.atto?.numPubb);
            } else if(button.code === 'pubblica-atto') {
                this.pubblicaAtto(this.atto?.codGara, this.atto?.numPubb);
            }
            else if (button.code === 'annulla-pubblicazione') {
                //Data futura
                if(this.atto?.dataPubbSistema > new Date()){
                    this.annullaPubblicazioneFutura(this.atto?.codGara, this.atto?.numPubb);
                }
                //Data passata o corrente
                else {
                    this.annullaPubblicazionePassataFirst(this.atto?.codGara, this.atto?.numPubb);
                }
            } else {
                this.executeButtonProvider(button);
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    // #endregion

    // #region Private

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private loadGara = (factory: Function): Observable<GaraEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if (this.gara.pcp) {
            this.menuTabs = filter(this.menuTabs, (one) => one.code !== 'pubblica-atto');
            this.refreshTabs();
        }
        this.showButtons();
    };

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProviderFactory(button: SdkButtonGroupOutput) {
        return () => {
            this.executeButtonProvider(button);
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    private executeButtonProvider = (button: SdkButtonGroupOutput): void => {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            tipoDocumento: this.tipoDocumento,
            numPubb: this.numPubb,
            campiVisibili: this.campiVisibiliString,
            campiObbligatori: this.campiObbligatoriString,
            attiMap: this.attiMap,
            chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
            syscon: this.userProfile.syscon,
            isSmartCig: this.gara.smartCig,
            stazioneAppaltante: this.gara.codiceStazioneAppaltante,
            idRicevuto: this.atto.idRicevuto
        }).subscribe(this.manageExecutionProvider);
    }

    private initDialog(action: string): SdkDialogConfig {

        let header: string;
        let message: string;
    
        switch(action) {
            case 'NORMAL':
                this.dialogConfig = {
                    header: this.translateService.instant('DIALOG.DELETE-TITLE'),
                    message: this.translateService.instant('DIALOG.DELETE-TEXT'),
                    acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
                    rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
                    open: new Subject()
                };
            case 'PUBBLICA-ATTO':
                header = this.translateService.instant('DIALOG.PUBBLICA-ATTO-TITLE');
                message = this.translateService.instant('DIALOG.PUBBLICA-ATTO-MESSAGE');
                break;
            case 'DELETE-DATA-PUBB-SISTEMA':
                header = this.translateService.instant('DIALOG.ANNULLA-PROGR-PUBBLICAZIONE');
                message = this.translateService.instant('DIALOG.DELETE-TEXT-DATA-PUBB-SISTEMA-FUTURA');
                break;
            case 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-FIRST':
                header = this.translateService.instant('DIALOG.ANNULLA-PROGR-PUBBLICAZIONE-MOTIVAZIONE-FIRST-HEADER');
                message = this.translateService.instant('DIALOG.ANNULLA-PROGR-PUBBLICAZIONE-MOTIVAZIONE-FIRST-MESSAGE');
                break;
            case 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND':
                this.motivazioneDialogConfig = {
                    header: this.translateService.instant('DIALOG.MOTIVAZIONE-ANNULLAMENTO-TITLE'),
                    message: this.translateService.instant('DIALOG.MOTIVAZIONE-ANNULLAMENTO-MESSAGE'),
                    acceptLabel: this.translateService.instant('DIALOG.CONFERMA-AZIONE'),
                    rejectLabel: this.translateService.instant('DIALOG.ANNULLA-AZIONE'),
                    motivazioneLabel: this.translateService.instant('DIALOG.MOTIVAZIONE-ANNULLAMENTO-LABEL'),
                    open: new Subject()
                };
                this.motivazioneDialogConfigObs.next(this.motivazioneDialogConfig);
                break;
            case 'DATEPICKER-PUBBLICAZIONE':
                this.datePickerDialogConfig = {
                    header: this.translateService.instant('DIALOG.DATEPICKER-PUBBLICAZIONE-TITLE'),
                    message: this.translateService.instant('DIALOG.DATEPICKER-PUBBLICAZIONE-MESSAGE'),
                    acceptLabel: this.translateService.instant('DIALOG.CONFERMA-AZIONE'),
                    rejectLabel: this.translateService.instant('DIALOG.ANNULLA-AZIONE'),
                    dataDialogLabel: this.translateService.instant('DIALOG.DATEPICKER-PUBBLICAZIONE-LABEL'),
                    open: new Subject()
                };
                this.datePickerDialogConfigObs.next(this.datePickerDialogConfig);
                break;
            default:
                header = 'DIALOG.DELETE-TITLE';
                message = 'DIALOG.DELETE-TEXT';
        }
    
        if(isEqual(action, 'DATEPICKER-PUBBLICAZIONE')) {
            return this.datePickerDialogConfig;
        }
        else if(isEqual(action, 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND')) {
            return this.motivazioneDialogConfig;
        }
        else {
            const dialogConfig: SdkDialogConfig = {
                header: this.translateService.instant(header),
                message: this.translateService.instant(message),
                acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
                rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
                open: new Subject<any>()
            };
        
            this.dialogConfigObs.next(dialogConfig);
            return dialogConfig;
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');
        this.annullato = paramsMap.get('annullato') === 'true';

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
        this.showButtons();

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: DettaglioAttoEntry) => {
            let mapping: boolean = true;

            if (field.code === 'DATA_DECRETO' || field.code === 'DATA_PROVVEDIMENTO' ||
                field.code === 'DATA_STIPULA' || field.code === 'DATA_VERB_AGGIUDICAZIONE' ||
                field.code === 'DATASCAD') {
                let value = get(restObject, field.mappingInput);
                if (value != null) {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    mapping = false;
                }
            } else if (field.code === 'dataPubbSistema' || field.code === 'DATAPUBB') {
                let value = get(restObject, field.mappingInput);
                if(value != null){
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.fullDateTimeFormat);
                    mapping = false;
                }
            } else if (field.code === 'primaPubblicazione' && field.type === 'TEXT') {
                const value = get(restObject, field.mappingInput);
                if(isEqual(value, '1')){
                    set(field, 'data', this.translateService.instant('COMBOBOX.SI'));
                }
                else{
                    set(field, 'data', this.translateService.instant('COMBOBOX.NO'));
                }
                mapping = false;
            } else if(field.code === 'annullato') {
                if(this.annullato === true) {
                    set(field, 'data', true);
                }
                else {
                    set(field, 'data', false);
                }
                mapping = false;
            } else if (field.code === 'dataCancellazione') {
                let value = get(restObject, field.mappingInput);
                if (value != null) {
                  field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                  mapping = false;
                }
            } else if (field.type === 'DOCUMENTS-LIST') {
                let existingDocuments: Array<AllegatoEntry> = this.atto?.documents;
                let sdkExistingDocuments: Array<SdkDocumentItem> = mapArray(existingDocuments, (one: AllegatoEntry) => {
                    let docItem: SdkDocumentItem = {
                        ...one,
                        code: toString(one.idAllegato),
                        titolo: one.url ? one.url : one.descrizione,
                        descrizione: undefined,
                        dataCanc: one.dataCanc ?? undefined,
                        motivoCanc: one.motivoCanc ?? undefined,
                        tipoFile: one.tipoFile
                    };

                    docItem.fileDownloadCallback = () => {
                        const factory = this.downloadDocumentoAttoFactory(+this.atto?.codGara, one?.idAllegato);
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

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.atto);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);

        if (this.anagraficaGaraPubblicata == null || this.anagraficaGaraPubblicata === false) {
            this.buttons.buttons = filter(this.buttons.buttons, (one) => one.code !== 'check-pubblicazione');
            this.buttonsWithoutDelete.buttons = filter(this.buttonsWithoutDelete.buttons, (one) => one.code !== 'check-pubblicazione');
        }
        if (this.gara.readOnly) {
            this.buttonsSubj.next(this.buttonsRO);
        } else {
            if (this.atto.deletable === true) {
                if(this.atto?.motivoCancellazione && this.atto?.dataCancellazione){
                    const filteredButtons = this.buttons.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto' && obj.code !== 'annulla-pubblicazione' && obj.code !== 'go-to-update-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else if(this.atto?.dataPubbSistema){
                    const filteredButtons = this.buttons.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else {
                    const filteredButtons = this.buttons.buttons.filter(function (obj) {
                        return obj.code !== 'annulla-pubblicazione';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
            } else {
                if(this.atto?.motivoCancellazione && this.atto?.dataCancellazione){
                    const filteredButtons = this.buttons.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto' && obj.code !== 'annulla-pubblicazione' && obj.code !== 'go-to-update-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else if(this.atto?.dataPubbSistema){
                    const filteredButtons = this.buttonsWithoutDelete.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else {
                    const filteredButtons = this.buttonsWithoutDelete.buttons.filter(function (obj) {
                        return obj.code !== 'annulla-pubblicazione';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
            }
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

    private loadDettaglio = (): Observable<DettaglioAttoEntry> => {
        let factory = this.dettaglioAttoFactory(this.codGara, this.tipoDocumento, this.numPubb);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDettaglio = (result: DettaglioAttoEntry) => {
        this.atto = result;
    }

    private dettaglioAttoFactory(codGara: string, tipoDocumento: string, numPubb: string): () => Observable<DettaglioAttoEntry> {
        return () => {
            return this.gareService.dettaglioAtto(codGara, tipoDocumento, numPubb);
        }
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private getListaPubblicazioni = () => {
        let factory = this.listaPubblicazioniFactory(this.codGara, this.numPubb);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaPubblicazioni = (result: Array<FlussiAtto>) => {
        this.pubblicazioni = result;

        if (this.pubblicazioni != null && this.pubblicazioni.length > 0) {
            if (this.anagraficaGaraPubblicata == null || this.anagraficaGaraPubblicata === false) {
                this.defaultButtons.buttons = filter(this.defaultButtons.buttons, (one) => one.code !== 'check-pubblicazione');
            }
            if (this.gara.readOnly) {
                this.buttonsSubj.next(this.buttonsRO);
            } else if (this.gara.pcp) {
                if(this.atto?.motivoCancellazione && this.atto?.dataCancellazione){
                    const filteredButtons = this.buttons.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto' && obj.code !== 'annulla-pubblicazione' && obj.code !== 'go-to-update-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else if(this.atto?.dataPubbSistema){
                    const filteredButtons = this.buttonsPcp.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else {
                    const filteredButtons = this.buttonsPcp.buttons.filter(function (obj) {
                        return obj.code !== 'annulla-pubblicazione';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
            } else {
                if(this.atto?.motivoCancellazione && this.atto?.dataCancellazione){
                    const filteredButtons = this.buttons.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto' && obj.code !== 'annulla-pubblicazione' && obj.code !== 'go-to-update-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else if(this.atto?.dataPubbSistema){
                    const filteredButtons = this.defaultButtons.buttons.filter(function (obj) {
                        return obj.code !== 'pubblica-atto';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
                else {
                    const filteredButtons = this.defaultButtons.buttons.filter(function (obj) {
                        return obj.code !== 'annulla-pubblicazione';
                    });
                    this.buttonsSubj.next({
                        buttons: filteredButtons
                    });
                }
            }
            try {
                document.getElementById('pubblica-atto_header').classList.remove('red-highlights');
                //document.getElementById('pubblica-atto_header').classList.add('p-panelmenu-header-link');
            } catch (e) {
            }
        } else {
            try {
                document.getElementById('pubblica-atto_header').classList.add('red-highlights');
                //  document.getElementById('pubblica-atto_header').classList.remove('p-panelmenu-header-link');
            } catch (e) {
            }
        }
    }

    private listaPubblicazioniFactory(codGara: string, num: string): () => Observable<Array<FlussiAtto>> {
        return () => {
            return this.pubblicazioneAttoService.getListaPubblicazioniAtto(codGara, num);
        }
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, { tipoDocumento: this.tipoDocumento, atto: this.atto });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsWithoutDelete = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsWithoutDelete, this.userProfile.configurations)
        };
        this.defaultButtons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.defaultButtons, this.userProfile.configurations)
        };
        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };
        this.buttonsPcp = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsPcp, this.userProfile.configurations)
        };
    }

    private anagraficaGaraPubblicataFactory(codGara: string): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, String(this.checkSmartCig));
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateAnagraficaGaraPubblicata = (pubblicata: boolean) => {
        this.anagraficaGaraPubblicata = pubblicata;
    }

    private downloadDocumentoAttoFactory(codGara: number, idAllegato: number) {
        return () => {
            return this.gareService.downloadDocumentoAtto(codGara, idAllegato);
        }
    }

    private showButtons(): void {
        if (this.gara.readOnly || this.gara.pcp) {
            this.buttonsSubj.next(this.buttonsRO);
        } else {
            if(this.atto?.motivoCancellazione && this.atto?.dataCancellazione){
                const filteredButtons = this.buttons.buttons.filter(function (obj) {
                    return obj.code !== 'pubblica-atto' && obj.code !== 'annulla-pubblicazione' && obj.code !== 'go-to-update-atto';
                });
                this.buttonsSubj.next({
                    buttons: filteredButtons
                });
            }
            else if(this.atto?.dataPubbSistema){
                const filteredButtons = this.defaultButtons.buttons.filter(function (obj) {
                    return obj.code !== 'pubblica-atto';
                });
                this.buttonsSubj.next({
                    buttons: filteredButtons
                });
            }
            else {
                const filteredButtons = this.defaultButtons.buttons.filter(function (obj) {
                    return obj.code !== 'annulla-pubblicazione';
                });
                this.buttonsSubj.next({
                    buttons: filteredButtons
                });
            }
        }
    }

    
    private pubblicaAtto(codGara: number, numPubb: number): void {
        const dialogConfig = this.initDialog('PUBBLICA-ATTO');
        const func = this.pubblicaAttoFactory(codGara, numPubb);
        dialogConfig.open.next(func);
    }

    private pubblicaAttoFactory(codGara: number, numPubb: number): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_ATTI', {
                    action: 'PUBBLICA-ATTO',
                    codGara: codGara,
                    numPubb: numPubb
                }).subscribe(this.manageReload)
            );
        }
    }

    private programmaPubblicazione(codGara: number, numPubb: number): void {
        const dialogConfig = this.initDialog('DATEPICKER-PUBBLICAZIONE');
        const func = this.programmaPubblicazioneFactory(codGara, numPubb);
        dialogConfig.open.next(func);
    }

    private programmaPubblicazioneFactory(codGara: number, numPubb: number): any {
        return (data: Date) => {

            //Non permetto all'utente di programmare la pubblicazione di un atto con una data antecedente alla data attuale.
            if(data === null || data === undefined) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'VALIDATORS.DATA-ANTECEDENTE'
                    }
                ]);
                return;
            }
            
            this.dataProgrammazione = data;

            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_ATTI', {
                    action: 'DATEPICKER-PUBB',
                    codGara: codGara,
                    numPubb: numPubb,
                    dataProgrammazione: data
                }).subscribe(this.manageReload)
            );
        }
    }

    private manageReload = (result: string) => {

        if (isObject(result) && get(result, 'pubblica') === true) {
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message: 'DIALOG.ATTO-PUBBLICATO-CORRETTO'
                }
            ]);
        }

        if (isObject(result) && get(result, 'programmato') === true) {
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message: this.translateService.instant('DIALOG.ATTO-PROGRAMMATO-CORRETTO', {
                        dataProgrammazione: this.formatDataProgrammazione(this.dataProgrammazione)
                    })
                }
            ]);
        }

        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadDettaglio();
        }
    }

    private formatDataProgrammazione(data: Date): string {
        if (!data) return '';
    
        const pad = (num: number) => num.toString().padStart(2, '0');
    
        const day = pad(data.getDate());
        const month = pad(data.getMonth() + 1);
        const year = data.getFullYear();
        const hours = pad(data.getHours());
        const minutes = pad(data.getMinutes());
    
        return `${day}/${month}/${year} - ${hours}:${minutes}`;
    }

    private reloadDettaglio(): void {
        this.loadDettaglio().pipe(
            map(this.elaborateDettaglio),
            map(() => this.loadForm())
        ).subscribe();
    }

    private annullaPubblicazioneFutura(codGara: number, numPubb: number): void {
        const dialogConfig = this.initDialog('DELETE-DATA-PUBB-SISTEMA');
        const func = this.annullaPubblicazioneFuturaFactory(codGara, numPubb);
        dialogConfig.open.next(func);
    }

    private annullaPubblicazioneFuturaFactory(codGara: number, numPubb: number): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_ATTI', {
                    action: 'DELETE-DATA-PUBB-SISTEMA',
                    codGara: codGara,
                    numPubb: numPubb
                }).subscribe(this.manageReload)
            );
        }
    }

    private annullaPubblicazionePassataFirst(codGara: number, numPubb: number): void {
        const dialogConfigFirst = this.initDialog('ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-FIRST');
        const funcFirst = this.annullaPubblicazionePassataSecond(codGara, numPubb);
        dialogConfigFirst.open.next(funcFirst);
    }

    private annullaPubblicazionePassataSecond(codGara: number, numPubb: number): any {
        return () => {
            const dialogConfig = this.initDialog('ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND');
            const func = this.sbiancaPubblicazioneMotivazioneFactory(codGara, numPubb);
            dialogConfig.open.next(func);
        }
    }

    private sbiancaPubblicazioneMotivazioneFactory(codGara: number, numPubb: number): any {
        return (motivazione: string) => {
            if(isEmpty(motivazione)){
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'LISTA-ATTI-GENERALI.VALIDATORS.MOTIVAZIONE-OBBLIGATORIA'
                    }
                ]);
            }
            else {
                this.addSubscription(
                    this.provider.run('APP_GARE_LISTA_ATTI', {
                        action: 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND',
                        codGara: codGara,
                        numPubb: numPubb,
                        motivoCanc: motivazione
                    }).subscribe(this.manageReload)
                );
            }
        }
    }

    // #endregion
}
