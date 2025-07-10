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
    Tecnico,
    ValoreTabellato,
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
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkSidebarConfig,
    SdkTextOutput
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isEqual, isObject, isString, map, set, toString } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { TranslateService } from '@ngx-translate/core';
import { AllegatoEntry, AttoGeneraleEntry, ResponseDettaglioAttoGenerale, RicercaAttiGeneraliForm } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { AttiGeneraliService } from 'projects/app-contratti/src/app/modules/services/atti-generali/atti-generali.service';
import { Constants } from '../../../../../../../app.constants';
import { DettaglioAttoGeneraleStoreService } from '../dettaglio-atto-generale-store.service';


@Component({
    templateUrl: `dettaglio-atto-generale-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioAttoGeneraleSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-atto-generale-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private dialogConfig: SdkDialogConfig;
    //public dialogConfigObs: Observable<SdkDialogConfig>;
    public dialogConfigObs: Subject<SdkDialogConfig> = new Subject<SdkDialogConfig>();

    private datePickerDialogConfig: SdkDialogConfig;
    public datePickerDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

    private motivazioneDialogConfig: SdkDialogConfig;
    public motivazioneDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null); 

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private stazioneAppaltante: string;
    private idAtto: number;
    private attoGenerale: AttoGeneraleEntry;
    private tipoAttoMap: IDictionary<string> = {};
    private menuTabs: Array<SdkMenuTab>;
    private userProfile: UserProfile;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private modalConfig: any;
    private buttons: SdkButtonGroupInput;
    private buttonsPubblicato: SdkButtonGroupInput;
    private buttonsAnnullato: SdkButtonGroupInput;
    private searchForm: RicercaAttiGeneraliForm;
    private valoriTabellato: Array<ValoreTabellato>;
    private ricerca: string;
    private modifica: string;
    private annullato: boolean;
    private dataProgrammazione: Date;

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
                this.valoriTabellato = tipiAttiGenerali;
                this.tipoAttoMap[tipoAttoGenerale.codice] = tipoAttoGenerale.descrizione;
            });
        });

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();

        let queryParams: ParamMap = this.activatedRoute.snapshot.queryParamMap;

        this.searchForm = JSON.parse(queryParams.get('searchForm'));
        this.stazioneAppaltante = queryParams.get('stazioneAppaltante');
        this.ricerca = queryParams.get('ricerca');
        this.idAtto = +queryParams.get('idAtto');
        this.modifica = queryParams.get('modifica');
        this.annullato = this.searchForm?.annullato;
        this.loadData();
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
                code: button.code,
                attoGenerale: this.attoGenerale,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel,
                formBuilderConfig: this.formBuilderConfig,
                idAtto: this.idAtto,
                searchForm: this.searchForm,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                codiceFiscaleStazioneAppaltante: this.stazioneAppaltanteInfo.codFiscale,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                buttonCode: button.code
            };

            if(button.code === 'programma-pubblicazione') {
                this.programmaPubblicazione(+this.attoGenerale?.idAtto);
            } else if(button.code === 'pubblica-atto') {
                this.pubblicaAtto(this.attoGenerale?.idAtto);
            } else if (button.code === 'annulla-pubblicazione') {
                //Data futura
                if(this.attoGenerale?.dataPubbSistema > new Date()){
                    this.annullaPubblicazioneFutura(this.attoGenerale?.idAtto);
                }
                //Data passata o corrente
                else {
                    this.annullaPubblicazionePassataFirst(this.attoGenerale?.idAtto);
                }
            }
            else{
                this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void { }

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
                    selectedItem: this.attoGenerale?.tecnico
                }
            } else if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.attoGenerale?.stazioneAppaltante
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item.data != null) {
            this.loadData();
        }
    }

    // #endregion

    // #region Private

    private initDialog(action: string): SdkDialogConfig {
        let header: string;
        let message: string;
    
        switch(action) {
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

    private loadData(): void {
        const dettaglioAttoGeneraleFactory = this.getDettaglioAttoGeneraleFactory();
        this.requestHelper
            .begin(dettaglioAttoGeneraleFactory, this.messagesPanel)
            .subscribe((response) => this.loadForm(response));
    }

    private loadForm(response: ResponseDettaglioAttoGenerale): void {
        this.attoGenerale = response.data;
        this.showButtons();

        let providerArgs: IDictionary<any> = {
            stazioneAppaltante: this.stazioneAppaltanteInfo
        }

        const fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));
        let formConfig: SdkFormBuilderConfiguration = this.protectionUtilsService.applyFormProtections({ fields }, this.userProfile.configurations);

        if (isObject(this.attoGenerale)) {
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, this.customPopulateFunction, this.attoGenerale, providerArgs);
        } else {
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, this.customPopulateFunction);
        }

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private getDettaglioAttoGeneraleFactory() {
        return () => {
            if(this.ricerca === 'S'){
                return this.attiGeneraliService.dettaglioAttoGenerale(this.searchForm);
            }
            else if (this.modifica === 'S') {
                let searchForm: RicercaAttiGeneraliForm = {
                    idAtto: this.idAtto.toString(),
                    stazioneAppaltante: this.stazioneAppaltante
                }
                return this.attiGeneraliService.dettaglioAttoGenerale(searchForm);
            } else {
                let searchForm: RicercaAttiGeneraliForm = {
                    idAtto: this.searchForm?.idAtto,
                    stazioneAppaltante: this.searchForm?.stazioneAppaltante
                }
                return this.attiGeneraliService.dettaglioAttoGenerale(searchForm);
            }
            
        }
    }

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: AttoGeneraleEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);
        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(Date.parse(value)), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        } else if ( field.code === 'rup' && restObject != null && restObject.tecnico != null ) {
            if (isObject(restObject.tecnico)) {
                let data: Tecnico = get(restObject, 'tecnico');
                set(field, 'data', `${data.nominativo} (${data.cf})`);
                mapping = false;
            }
            //mapping = false;                       
            //set(field, 'data', restObject.rupData);
        } else if (field.code === 'stazione-appaltante' && field.type === 'TEXT') {
            if (get(restObject, field.mappingInput)) {
                field.data = this.attoGenerale.stazioneAppaltante;
                mapping = false;
            }
        } else if (field.code === 'dataAtto' || field.code === 'dataScadenza' || field.code === 'dataPrimaPubb') {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
              field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
              mapping = false;
            }
        } else if(field.code === 'dataPubbSistema' && field.type === 'TEXT'){
            let value = get(restObject, field.mappingInput);
            if (value != null) {
              field.data = this.dateHelper.format(new Date(value), this.config.locale.fullDateTimeFormat);
              mapping = false;
            }
        } else if (field.code === 'tipoAtto' && field.type === 'TEXT') {
            if((restObject != null || restObject != undefined) && +restObject.tipologia < 100){
                set(field, 'data', this.translateService.instant('ATTI-GENERALI-LIST-CODE.ATTI-GENERALI'));
            }
            else {
                set(field, 'data', this.translateService.instant('ATTI-GENERALI-LIST-CODE.MANIFESTAZIONE-E-ALTRI'));
            }
            mapping = false;
        } else if (field.code === 'tipoAttoKey') {
            if((restObject != null || restObject != undefined) && +restObject.tipologia < 100){
                set(field, 'data', '1');
            }
            else {
                set(field, 'data', '2');
            }
            mapping = false;
        } else if (field.code === 'tipologia' && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if (value) {
                field.data = this.tipoAttoMap[value];
                mapping = false;
            }
        } else if (field.code === 'primaPubb' && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if(isEqual(value, '1')){
                set(field, 'data', this.translateService.instant('COMBOBOX.SI'));
            }
            else{
                set(field, 'data', this.translateService.instant('COMBOBOX.NO'));
            }
            mapping = false;
        } else if (field.code === 'annullato') {
            if(this.annullato === true) {
                set(field, 'data', true);
            }
            else {
                set(field, 'data', false);
            }
            mapping = false;
        } else if (field.code === 'dataCanc') {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
              field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
              mapping = false;
            }
        } else if (field.type === 'DOCUMENTS-LIST') {

            let existingDocuments: Array<AllegatoEntry> = this.attoGenerale.documents;
            let sdkExistingDocuments: Array<SdkDocumentItem> = map(existingDocuments, (one: AllegatoEntry) => {
                let docItem: SdkDocumentItem = {
                    ...one,
                    code: toString(one.idAllegato),
                    titolo: this.attoGenerale.primaPubb === '1' 
                        ? 
                            one.descrizione || one.url 
                        : 
                            this.attoGenerale.primaPubb === '2' 
                            ? 
                                one.descrizione + (one.url ? '\n' + one.url : '') 
                            : 
                                one.url || one.descrizione,
                    descrizione: undefined,
                    dataCanc: one.dataCanc ?? undefined,
                    motivoCanc: one.motivoCanc ?? undefined,
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
        if (isObject(obj)) {
            if (get(obj, 'reload') === true) {
                this.loadData();
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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsPubblicato = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsPubblicato, this.userProfile.configurations)
        };

        this.buttonsAnnullato = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsAnnullato, this.userProfile.configurations)
        };
    }

    private downloadDocumentoAttoGeneraleFactory(idAtto: number, numDoc: number) {
        return () => {
            return this.attiGeneraliService.downloadDocumentoAttoGenerale(idAtto, numDoc);
        }
    }

    private programmaPubblicazione(idAtto: number): void {
        const dialogConfig = this.initDialog('DATEPICKER-PUBBLICAZIONE');
        const func = this.programmaPubblicazioneFactory(idAtto);
        dialogConfig.open.next(func);
    }

    private programmaPubblicazioneFactory(idAtto: number): any {
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
                this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                    action: 'DATEPICKER-PUBB',
                    idAtto: idAtto,
                    dataProgrammazione: data
                }).subscribe(this.manageDelete)
            );
        }
    }

    private manageDelete = (result: string) => {

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
        if(isObject(result) && get(result, 'esito') === true) {
            this.annullato = true;
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
        this.loadData();
    }

    private showButtons(): void {
        if(this.annullato) {
            this.buttonsSubj.next(this.buttonsAnnullato);
        } 
        else if(this.attoGenerale?.dataPubbSistema) {
            this.buttonsSubj.next(this.buttonsPubblicato);
        }
        else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    private pubblicaAtto(idAtto: string): void {
        const dialogConfig = this.initDialog('PUBBLICA-ATTO');
        const func = this.pubblicaAttoFactory(idAtto);
        dialogConfig.open.next(func);
    }

    private pubblicaAttoFactory(idAtto: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                    action: 'PUBBLICA-ATTO',
                    idAtto: idAtto
                }).subscribe(this.manageDelete)
            );
        }
    }

    private annullaPubblicazioneFutura(idAtto: string): void {
        const dialogConfig = this.initDialog('DELETE-DATA-PUBB-SISTEMA');
        const func = this.annullaPubblicazioneFuturaFactory(idAtto);
        dialogConfig.open.next(func);
    }

    private annullaPubblicazioneFuturaFactory(idAtto: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                    action: 'DELETE-DATA-PUBB-SISTEMA',
                    idAtto: idAtto
                }).subscribe(this.manageDelete)
            );
        }
    }

    private annullaPubblicazionePassataFirst(idAtto: string): void {
        const dialogConfigFirst = this.initDialog('ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-FIRST');
        const funcFirst = this.annullaPubblicazionePassataSecond(idAtto);
        dialogConfigFirst.open.next(funcFirst);
    }

    private annullaPubblicazionePassataSecond(idAtto: string): any {
        return () => {
            const dialogConfig = this.initDialog('ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND');
            const func = this.sbiancaPubblicazioneMotivazioneFactory(idAtto);
            dialogConfig.open.next(func);
        }
    }

    private sbiancaPubblicazioneMotivazioneFactory(idAtto: string): any {
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
                    this.provider.run('APP_CONTRATTI_ATTI_GENERALI', {
                        action: 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND',
                        idAtto: idAtto,
                        motivoCanc: motivazione
                    }).subscribe(this.manageDelete)
                );
            }
        }
    }

    // #endregion
}
