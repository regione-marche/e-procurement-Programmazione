import { ChangeDetectorRef, Component, Directive, ElementRef, Injector, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    UserService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkProviderService,
    SdkSessionStorageService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkSidebarConfig,
    SdkTextboxOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, filter, find, get, isArray, isEmpty, isEqual, isObject, remove, sum, toNumber } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, Observer, of, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap, tap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { AssociazionePagamentiEntry, GaraEntry, LottoEntry, PagamentoEntry } from '../../../../../models/gare/gare.model';
import { TipoInvio } from '../../../../../models/pubblicazioni/pubblicazione-fase.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { DettaglioFaseStoreService } from '../dettaglio-fase-store.service';
import { SdkBaseSectionWidget } from '@maggioli/sdk-appaltiecontratti-base';
import { SdkC0oggassParamsStoreService } from 'projects/sdk-docassociati/src/lib/sdk-docassociati/components/c0oggass/sdk-c0oggass-params-store.service';

@Component({
    template: "",
})
export abstract class FaseAbstractSectionWidget extends SdkBaseSectionWidget implements OnInit, OnDestroy {

    // #region Variables

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;
    //public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    protected userProfile: UserProfile;
    protected codGara: string;
    protected codLotto: string;
    protected codiceFase: string;
    protected numeroProgressivo: string;
    protected riaggiudicazione: boolean = false;
    protected valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    protected gara: GaraEntry;
    protected lotto: LottoEntry;
    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;
    protected modalConfig: SdkModalConfig<any, void, any>;

    protected buttonsReadOnly: SdkButtonGroupInput;
    protected buttonsRO: SdkButtonGroupInput;
    protected buttons: SdkButtonGroupInput;
    protected buttonsErr: SdkButtonGroupInput;
    protected buttonsLS: SdkButtonGroupInput;
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    protected dialogConfig: SdkDialogConfig;
    protected _loadListaTabellati: boolean;
    protected _updateFase: boolean = false;
    protected dettaglio: any;
    protected menuTabs: Array<SdkMenuTab>;
    protected infoBoxModalConfig: IDictionary<any>;
    protected sidebarConfig: SdkSidebarConfig;
    protected current: boolean = true;
    protected cigFiglio: boolean = false;
    protected cfStazioneAppaltante: string;
    protected appCode: string;
    protected fromLS: string;
    protected associazioniPagamenti: Array<AssociazionePagamentiEntry> = new Array<AssociazionePagamentiEntry>();

    // #endregion

    constructor(protected inj: Injector, protected cdr: ChangeDetectorRef) { super(inj, cdr) }

    protected getValoriTabellatiConst(): string[] {
        throw new Error('Method not implemented.');
    }

    // #region Hooks

    protected onInit(): void {
        super.onInit();
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.appCode = this.sdkSessionStorageService.getItem(Constants.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);

        if (this.dettaglioFaseStoreService.current != null) {
            this.current = this.dettaglioFaseStoreService.current;
        }

        if (this.dettaglioFaseStoreService.riaggiudicazione != null) {
            this.riaggiudicazione = this.dettaglioFaseStoreService.riaggiudicazione;
        }

        if (this.dettaglioFaseStoreService.cigFiglio != null) {
            this.cigFiglio = this.dettaglioFaseStoreService.cigFiglio;
        }

        this.loadQueryParams();
        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        if (this.loadListaTabellati === true) {
            this.loadTabellati().pipe(
                map(this.elaborateTabellati),
                map(this.loadGaraFactory),
                mergeMap(this.loadGara),
                map(this.elaborateGara),
                map(this.loadSAInfoFactory),
                mergeMap(this.loadSa),
                map(this.elaborateSa),
                map(this.loadLottoFactory),
                mergeMap(this.loadLotto),
                map(this.elaborateLotto),
                tap(() => this.refreshTabs()),
                mergeMap(this.performLoadDettaglio),
                map(this.elaborateDettaglio),
                map(() => this.refreshBreadcrumb()),
                map(() => this.loadForm()),
                map(() => this.showButtons()),
                catchError(this.handleError)
            ).subscribe();
        } else {
            let factory = this.loadGaraFactory();
            this.loadGara(factory).pipe(
                map(this.elaborateGara),
                map(this.loadSAInfoFactory),
                mergeMap(this.loadSa),
                map(this.elaborateSa),
                map(this.loadLottoFactory),
                mergeMap(this.loadLotto),
                map(this.elaborateLotto),
                tap(() => this.refreshTabs()),
                mergeMap(this.performLoadDettaglio),
                map(this.elaborateDettaglio),
                map(() => this.loadForm()),
                map(() => this.showButtons()),
                map(() => this.refreshBreadcrumb()),
                catchError(this.handleError)
            ).subscribe();
        }
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            super.onConfig(config);
            this.markForCheck(() => {
                this.config = { ...config };

                if (isObject(get(this.config.body, 'sidebar'))) {
                    this.sidebarConfig = get(this.config.body, 'sidebar');
                    this.sidebarConfig.openSubject = new Subject();
                    this.sidebarConfigObs.next(this.sidebarConfig);
                }
                if (isArray(this.config.menuTabs)) {
                    this.menuTabs = this.config.menuTabs;
                }
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


    override refreshTabsOnInit() {
        return false;
    }

    // #endregion

    // #region Getters

    protected get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    protected get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    protected get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    protected get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    //protected get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    protected get gareService(): GareService { return this.injectable(GareService) }

    //protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    protected get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    protected get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    protected get translateService(): TranslateService { return this.injectable(TranslateService) }

    protected get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    protected get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    protected get userService(): UserService { return this.injectable(UserService) }

    protected get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService { return this.injectable(SdkC0oggassParamsStoreService); }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public abstract manageOutputField(field: SdkFormBuilderField): void;

    protected setValueInAssociazionePagamenti(field: SdkFormBuilderField): void {
        if (field.nomeCampo) {
            let associazionePagamenti = find(this.associazioniPagamenti, (one: AssociazionePagamentiEntry) => one.codeCampo === field.code);
            if (associazionePagamenti) {
                associazionePagamenti.importoCampo = toNumber(field.data);
            }
        }
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (this.updateFase === true && isObject(this.infoBoxModalConfig) && isObject(item)) {
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

    protected updateFieldDataByFieldCode(fieldCode: string, data: any): void {
        const toUpdate: SdkFormBuilderInput = {
            code: fieldCode,
            data: data
        };

        this.formBuilderDataSubject.next(toUpdate);
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item.data) {
            let associazionePagamenti = item.data;
            remove(this.associazioniPagamenti, (one: AssociazionePagamentiEntry) => one.nomeCampo === associazionePagamenti.nomeCampo);
            this.associazioniPagamenti.push(associazionePagamenti);
            this.updateFieldDataByFieldCode(associazionePagamenti.codeCampo, associazionePagamenti.importoTotalePagamenti);
        }
    }



    public manageOutputActionClick(item: SdkFormBuilderField): void {

        if (item.nomeCampo) {
            this.showModalDatiPagamento(item.code, item.nomeCampo);
        }
    }

    protected showModalDatiPagamento(codeCampo: string, nomeCampo: string): void {

        const associazionePagamenti: AssociazionePagamentiEntry = {
            codeCampo: codeCampo,
            nomeCampo: nomeCampo,
        };

        let datiContabilitaModalConfig =
        {
            ...this.config.modalComponentConfigDatiContabilita,
            stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
            codGara: this.codGara,
            codLotto: this.codLotto,
            cig: this.lotto.cig,
            associazionePagamenti: associazionePagamenti,
            isSelection: true
        }

        this.modalConfig = {
            ...this.modalConfig,
            component: "dati-contabilita-modal-widget",
            componentConfig: datiContabilitaModalConfig,

        };
        this.modalConfigObs.next(this.modalConfig);
        this.modalConfig.openSubject.next(true);

    }

    // #endregion

    // #region Protected

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected abstract customPopulateFunction: CustomParamsFunction;

    protected abstract tabellati(): Array<string>;

    protected abstract loadDettaglio: () => Observable<any>;

    protected set loadListaTabellati(value: boolean) {
        this._loadListaTabellati = value;
    }

    protected get loadListaTabellati(): boolean {
        return this._loadListaTabellati;
    }

    protected set updateFase(value: boolean) {
        this._updateFase = value;
    }

    protected get updateFase(): boolean {
        return this._updateFase;
    }

    // #endregion

    // #region Private

    protected manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    protected executeButtonProvider(button: SdkButtonGroupOutput): void {


        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codiceFase: this.codiceFase,
            codFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            cfStazioneAppaltante: this.cfStazioneAppaltante,
            chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
            setUpdateState: this.setUpdateState,
            updateFase: this.updateFase,
            riaggiudicazione: this.riaggiudicazione,
            autore: this.userProfile.nome + ' ' + this.userProfile.cognome,
            pcp: this.gara.pcp,
            associazioniPagamenti: this.associazioniPagamenti
        };
        if (this.dettaglio) {
            data.tipoInvio = this.dettaglio.pubblicata ? TipoInvio.RETIFFICHE_AGGIORNAMENTO : TipoInvio.PRIMA_PUBBLICAZIONE;
        }

        if ((button.code === 'go-to-fasi-visibili' || button.code === 'back-to-dettaglio-fase') && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
            this.back(button, data);
        } else {
            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    protected loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.fromLS = paramsMap.get('fromLS');
        if (paramsMap.has('riaggiudicazione')) {
            const riaggiudicazioneParam: string = paramsMap.get('riaggiudicazione');
            this.riaggiudicazione = riaggiudicazioneParam != null && +riaggiudicazioneParam == 1;
        }
        if (!this.codGara) {
            this.codGara = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1;
        }
        if (!this.codLotto) {
            this.codLotto = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey2;
        }
        if (!this.codiceFase) {
            this.codiceFase = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey3;
        }
        if (this.numeroProgressivo == null && this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey4 != '#') {
            this.numeroProgressivo = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey4;
        }
    }

    protected get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    protected checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    protected loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(this.tabellati());
    }

    protected elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    protected dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    protected dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    protected loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    protected loadGara = (factory: Function): Observable<GaraEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    protected elaborateGara = (result: GaraEntry) => {
        this.gara = result;
    }

    protected loadLottoFactory = (): Function => {
        let factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return factory;
    }

    protected loadLotto = (factory: Function): Observable<LottoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    protected elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
    };

    protected elaborateDettaglio = (result: any) => {
        this.dettaglio = result;
        if (this.dettaglio != null && this.dettaglio != undefined && this.dettaglio.daExport == '2' && this.updateFase === true) {
            this.showSchedaInviataMessageBox();
        }
    }

    protected loadSAInfoFactory = () => {
        return () => {
            return this.userService.getSAInfo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, this.gara.codiceStazioneAppaltante, this.appCode, this.userProfile.syscon)
        }
    }

    protected elaborateSa = (result: StazioneAppaltanteInfo) => {
        this.cfStazioneAppaltante = result.codFiscale;
    };

    protected loadSa = (factory: Function): Observable<StazioneAppaltanteInfo> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }



    protected loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let infoBox: boolean = this.updateFase === true && this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, this.updateFase === true);

        if (isObject(this.dettaglio)) {
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, this.customPopulateFunction, this.dettaglio, {
                codGara: this.codGara,
                codLotto: this.codLotto,
                stazioneAppaltante: { codice: this.gara.codiceStazioneAppaltante },
                gara: this.gara
            }, infoBox, this.config);
        } else {
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, this.customPopulateFunction, undefined, {
                codGara: this.codGara,
                codLotto: this.codLotto,
                stazioneAppaltante: { codice: this.gara.codiceStazioneAppaltante },
                gara: this.gara
            }, infoBox);
        }

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    protected performLoadDettaglio = () => {
        let dettaglioObs = this.loadDettaglio();
        if (isObject(dettaglioObs)) {
            return dettaglioObs;
        }
        return new Observable((ob: Observer<any>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    protected refreshTabs(): void {
        if (isArray(this.menuTabs)) {
            remove(this.menuTabs, (one: SdkMenuTab) => {
                if (!isEmpty(one.oggettoProtezione)) {
                    let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                    if (visible !== true) {
                        return true;
                    }
                }
                if (!isEmpty(one.visible)) {
                    let visible: boolean = this.provider.run(one.visible, { lotto: this.lotto });
                    return visible === false;
                }
                return false;
            });
            this.sdkLayoutMenuTabs.emit(this.menuTabs);
        }
    }

    protected refreshBreadcrumb = () => {
        if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else {
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
    }

    protected initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    protected back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    protected backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    protected showSchedaInviataMessageBox(): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
            {
                message: 'DETTAGLIO-FASE.SCHEDA-INVIATA'
            }
        ]);
    }

    protected loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttonsReadOnly, this.userProfile.configurations)
        };

        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }

        if (this.current === false || this.cigFiglio === true) {
            this.buttons.buttons = filter(this.buttons.buttons, (one: SdkBasicButtonInput) => one.code !== 'go-to-update-fase');
        }

        this.buttonsErr = {
            buttons: cloneDeep(this.buttons.buttons)
        };

        remove(this.buttonsErr.buttons, (one: SdkBasicButtonInput) =>
            one.code === 'save-fase' || one.code === 'check-pubblicazione' || one.code === 'go-to-update-fase'
        );
    }

    protected handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsErr);
        return throwError(() => err);
    }

    protected showButtons(): void {
        if (this.dettaglio != null) {
            if (this.fromLS) {
                //this.buttonsSubj.next(this.buttonsLS);
                super.showButtons(this.buttonsLS);
            } else {
                if (this.gara.readOnly) {
                    this.buttonsSubj.next(this.buttonsRO);
                } else {
                    if (this.gara.pcp && this.dettaglio != null && this.dettaglio.pubblicata) {
                        //this.buttonsSubj.next(this.buttonsRO);
                        super.showButtons(this.buttonsRO);
                    } else {
                        //this.buttonsSubj.next(this.buttons);
                        super.showButtons(this.buttons);
                    }
                }
            }
        } else {
            if (this.fromLS) {
                this.buttonsSubj.next(this.buttonsLS);
            } else {
                if (this.gara.readOnly) {
                    this.buttonsSubj.next(this.buttonsRO);
                } else {
                    if (this.gara.pcp && this.dettaglio != null && this.dettaglio.pubblicata) {
                        this.buttonsSubj.next(this.buttonsRO);
                    } else {
                        this.buttonsSubj.next(this.buttons);
                    }
                }
            }
        }

    }


    // #endregion
}
