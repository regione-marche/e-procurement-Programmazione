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
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkProviderService,
    SdkStoreAction,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkSidebarConfig,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, filter, get, has, isEmpty, isEqual, isObject, isString, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { GaraEntry, LottoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { DettaglioCigLottoStoreService } from '../dettaglio-cig-lotto-store.service';
import { SdkBaseSectionWidget } from '@maggioli/sdk-appaltiecontratti-base';
import { SdkC0oggassParamsStoreService } from 'projects/sdk-docassociati/src/lib/sdk-docassociati/components/c0oggass/sdk-c0oggass-params-store.service';


@Component({
    templateUrl: `dettaglio-lotto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioLottoSectionWidget extends SdkBaseSectionWidget implements OnInit, OnDestroy {
    

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-lotto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    protected buttonsRO: SdkButtonGroupInput;
    protected buttons: SdkButtonGroupInput;
    protected buttonsBase: SdkButtonGroupInput;
    protected buttonsLS: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    //public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();

    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;
    protected userProfile: UserProfile;
    protected codGara: string;
    protected codLotto: string;
    protected from: string;
    protected gara: GaraEntry;
    protected lotto: LottoEntry;
    protected valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    protected menuTabs: Array<SdkMenuTab>;
    protected menuTabsLS: Array<SdkMenuTab>;
    protected sidebarConfig: SdkSidebarConfig;
    protected comuniMap: IDictionary<ValoreTabellato> = {};
    protected provinceMap: IDictionary<ValoreTabellato> = {};
    protected modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    protected isAttivoIndicatoriLotto = false;
    protected fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        super.onInit();
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        // clear storico fasi
        this.store.dispatch(new SdkStoreAction(Constants.CURRENT_NUM_APPA_FASI_LOTTO_DISPATCHER, undefined));

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {        
        this.loadTabellati().pipe(            
            map(this.elaborateTabellati),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            map(this.loadLottoFactory),
            mergeMap(this.loadLotto),
            map(this.elaborateLotto),
            map(this.refreshBreadcrumb)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        this.loadQueryParams();
        if (isObject(config)) {
            super.onConfig(config);
            this.markForCheck(() => {
                this.config = { ...config };

                if (isObject(get(this.config.body, 'sidebar'))) {
                    this.sidebarConfig = get(this.config.body, 'sidebar');
                    this.sidebarConfig.openSubject = new Subject();
                    this.sidebarConfigObs.next(this.sidebarConfig);
                }
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
                this.menuTabs = this.config.menuTabs;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }
    
    protected getValoriTabellatiConst(): string[] {
        throw new Error('Method not implemented.');
    }

    override refreshTabsOnInit() {        
        return false;
    }

    // #endregion

    // #region Getters

    protected get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    protected get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    protected get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    protected get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    protected get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    //protected get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    //protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    protected get gareService(): GareService { return this.injectable(GareService) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    protected get dettaglioCigLottoStoreService(): DettaglioCigLottoStoreService { return this.injectable(DettaglioCigLottoStoreService) }

    protected get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }
        
    protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService { return this.injectable(SdkC0oggassParamsStoreService);  }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            if(button.code === 'report-indicatori'){
                let reportIndicatoriModalConfig = this.config;
                reportIndicatoriModalConfig.modalComponentConfigReportIndicatori = 
                {
                    ...reportIndicatoriModalConfig.modalComponentConfigReportIndicatori,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    cig: this.lotto.cig
                }
                this.modalConfig = {
                    ...this.modalConfig,
                    component: "report-indicatori-modal-widget",
                    componentConfig: reportIndicatoriModalConfig.modalComponentConfigReportIndicatori,
                    
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);

            } else if(button.code === 'dati-contabilita'){

                let datiContabilitaModalConfig = 
                {
                    ...this.config.modalComponentConfigDatiContabilita,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    cig: this.lotto.cig
                }
                
                this.modalConfig = {
                    ...this.modalConfig,
                    component: "dati-contabilita-modal-widget",
                    componentConfig: datiContabilitaModalConfig,
                    
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            } else{
                this.executeButtonProvider(button);
            }
            
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Private

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected refreshTabs(): void {
        const isLS = this.fromLS !== null && isEqual(this.fromLS, 'LS');
        const tabsToFilter = isLS ? this.config.menuTabsLS : this.menuTabs;
    
        const filteredTabs = tabsToFilter.filter(tab => {
            if (!isLS && 
                (tab.code === 'lista-fasi-lotto' || tab.code === 'lista-invii-fasi') && 
                this.gara?.dataPubblicazione == null && 
                !this.gara?.pcp) {
                return false;
            }
    
            if (!isEmpty(tab.oggettoProtezione)) {
                const visible = this.protectionUtilsService.isMenuTabVisible(
                    tab.oggettoProtezione, 
                    this.userProfile.configurations
                );
                if (!visible) {
                    return false;
                }
            }
    
            if (!isEmpty(tab.visible)) {
                return this.provider.run(tab.visible, { lotto: this.lotto });
            }
    
            return true;
        });
    
        this.sdkLayoutMenuTabs.emit(filteredTabs);
        if (isLS) {
            this.menuTabsLS = filteredTabs;
        } else {
            this.menuTabs = filteredTabs;
        }
    }

    protected executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
            chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
            from: this.fromLS ? this.fromLS : this.from
        }).subscribe(this.manageExecutionProvider);
    }

    protected manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    protected loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.from = paramsMap.get('from');
        this.fromLS = paramsMap.get('fromLS');
        if(!this.codGara){
            this.codGara = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1;
        }
        if(!this.codLotto){
            this.codLotto = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey2;
        }

    }

    protected loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        if( this.lotto.qualificazioneSa === null &&
            this.lotto.categoriaMerceologica === null &&
            this.lotto.flagPrevedeRip === null &&
            this.lotto.durataRinnovi === null &&
            this.lotto.motivoCollegamento === null &&
            this.lotto.cigOrigineRip === null &&
            this.lotto.flagPnrrPnc === null &&
            this.lotto.flagPrevisioneQuota === null &&
            this.lotto.flagMisurePreliminari === null &&
            this.lotto.dataScadenzaPagamenti){
                remove(fields, (one: SdkFormBuilderField) => one.code === 'altri-dati');
        }

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'art-e1' || field.code === 'id-scelta-contraente-50') {
                // set visible
                field.visible = this.gara.versioneSimog <= 2;
            } else if (field.code === 'id-modo-gara') {
                if (this.gara.versioneSimog === 1) {
                    field.listCode = 'CriterioAggiudicazioneSimog1';
                }
            } else if (field.code === 'in-opera-manodopera') {
                field.visible = this.lotto.tipologia !== 'L';
            } else if (field.code === 'condizioni-procedura-negoziata-data') {
                let visible: boolean = false;
                if (this.gara.versioneSimog === 1) {
                    visible = true;
                }
                if (visible === false) {
                    field.visibleCondition = undefined;
                }
            } else if (field.code === 'luogo-istat') {
                if (has(this.comuniMap, key)) {
                    const istatComune: string = key;
                    const istatProvincia: string = `0${istatComune.substring(3, 6)}`;
                    field.data = `${this.comuniMap[istatComune].descrizione} - ${this.provinceMap[istatProvincia].descrizione} (${istatComune})`
                    mapping = false;
                }
            } else if (field.code === 'contratto-escluso-alleggerito' || field.code === 'esclusione-regime-speciale') {
                field.visible = this.gara.versioneSimog > 2;
            } else if(field.code === 'qualificazioneSa' 
                        || field.code === 'categoriaMerceologica'
                        || field.code === 'flagPrevedeRip'
                        || field.code === 'durataRinnovi'
                        || field.code === 'motivoCollegamento'
                        || field.code === 'cigOrigineRip'
                        || field.code === 'flagPnrrPnc'
                        || field.code === 'flagPrevisioneQuota'
                        || field.code === 'flagMisurePreliminari'
                        || field.code === 'dataScadenzaPagamenti'){
                let value = get(restObject, field.mappingInput);
                if(value == null || (isString(value) && isEmpty(value))){
                    field.visible = false;
                    mapping = false;
                }
            }

            if(this.gara.pcp){
                if (field.code === 'importo-lotto') {
                    field.visible = false;
                    mapping = false;
                }
                if (field.code === 'importo-attuazione-sicurezza') {
                    field.visible = false;
                    mapping = false;
                }
            }

            if (field.code === 'dataScadenzaPagamenti' && field.visible !== false) {        
                let value = get(restObject, field.mappingInput);                
                if (value != null && field.type === 'TEXT') {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    key = field.data;
                    mapping = false;
                }
            }

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.lotto, null, null, this.config);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
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

    protected dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    protected dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    protected isAttivoIndicatoriLottoFactory(): () => Observable<any> {
        return () => {
            return this.gareService.isAttivoIndicatoriLotto();
        }
    }

    protected loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.LOTTO_TABELLATI);
    }

    protected elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    protected loadComuni = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI);
    }

    protected elaborateComuni = (comuni: Array<ValoreTabellato>) => {
        each(comuni, (comune: ValoreTabellato) => {
            this.comuniMap[comune.codistat] = comune;
        });
    }

    protected loadProvince = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE);
    }

    protected elaborateProvince = (province: Array<ValoreTabellato>) => {
        each(province, (provincia: ValoreTabellato) => {
            this.provinceMap[provincia.codistat] = provincia;
        });
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
        if(this.from == 'L'){
            this.codLotto = this.gara.codlott;
        }
    };

    protected loadLottoFactory = (): Function => {
        let factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return factory;
    }

    protected loadLotto = (factory: Function): Observable<LottoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    protected elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;

        if (this.gara.pcp && !this.lotto.hasAggiudicazione && !isEqual(this.fromLS, 'LS')) {
            this.menuTabs = filter(this.menuTabs, (one) => one.code !== 'lista-invii-fasi');
        }

        this.dettaglioCigLottoStoreService.lotto = result;
        this.refreshBreadcrumbs();
        this.checkInfoBox();        
        this.loadForm();
        this.showButtons();
        this.refreshTabs();
    };

    protected loadButtons(): void {
        let factory = this.isAttivoIndicatoriLottoFactory();
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result)=> {
            this.isAttivoIndicatoriLotto = result.data

            this.buttons = {
                buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
            };
            this.buttonsBase = {
                buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsBase, this.userProfile.configurations)
            };
            this.buttonsRO = {
                buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
            };
            this.buttonsLS = {
                buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
            }                  
           

            //this.markForCheck();
        });
        
    }

    protected refreshBreadcrumb = () => {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')){
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else{
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
    }

    protected showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')){
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if(this.gara.readOnly){
                this.buttonsSubj.next(this.buttonsRO);
            } else if(this.gara.pcp){                
                super.showButtons(this.buttonsRO);
            } else{
                if(this.isAttivoIndicatoriLotto === true){   
                    super.showButtons(this.buttons);      
                } else{
                    super.showButtons(this.buttonsBase);
                }
            }
        }        
    }

    // #endregion
}
