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
    HttpRequestHelper,
    ProfiloConfiguration,
    ProfiloConfigurationItem,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkSidebarConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { find, get, has, isEmpty, isEqual, isObject, reduce, remove, toString } from 'lodash-es';
import { GaraEntry } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';
import { SmartCigEntry } from 'projects/app-contratti/src/app/modules/models/smartcig/smartcig.model';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { SmartCigService } from 'projects/app-contratti/src/app/modules/services/smartcig/smartcig.service';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseImpresaEntry, RuoloImpresa } from '../../../../../../models/fasi/elenco-impr-inv-parte.model';
import { TipoInvio } from '../../../../../../models/pubblicazioni/pubblicazione-fase.model';
import { ElencoImpreseInvitatePartecipantiService } from '../../../../../../services/fasi/elenco-impr-inv-parte.service';

@Component({
    templateUrl: `lista-impr-inv-parte-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaImpreseInvitatePartecipantiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-imprese-invitate-partecipanti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public listaImpresePartecipanti: Array<FaseImpresaEntry>;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private updateDaexport = false;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsLS: Array<SdkMenuTab>;
    public gara: GaraEntry;
    private smartCig: SmartCigEntry;
    private providerCode: string;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private dialogConfig: SdkDialogConfig;
    private sidebarConfig: SdkSidebarConfig;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadQueryParams();
        this.initDialog();
        this.loadButtons();
        let protezioniMap = this.getProtezioniMap();
        let key: string = 'FUNZ.VIS.ALT.W9.W9LOTT-SMARTCIG-SALVAINVIA';
        if (has(protezioniMap, key)) {
            let protValue: ProfiloConfigurationItem = get(protezioniMap, key);
            this.updateDaexport = protValue.valore;
        }
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(() => this.checkPubblicazione()),
            mergeMap(this.loadListaImpresePartecipanti),
            map(this.elaborateListaImpresePartecipanti),
            map(() => this.refreshTabs),
            map(() => this.refreshBreadcrumb()),
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
                this.providerCode = this.config.body.provider;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get elencoImpreseInvitatePartecipantiService(): ElencoImpreseInvitatePartecipantiService { return this.injectable(ElencoImpreseInvitatePartecipantiService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public trackByCode(index: number, item: FaseImpresaEntry): string {
        return isObject(item) ? toString(item.num) : toString(index);
    }

    public goToDettaglioImpresa(impresa: FaseImpresaEntry): void {
        if (isObject(impresa)) {
            if(this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
                this.provider.run(this.providerCode, {
                    action: 'DETAIL-LS',
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    codiceFase: this.codiceFase,
                    numeroProgressivo: this.numeroProgressivo,
                    num: toString(impresa.num),
                    numRagg: toString(impresa.numRagg),
                    impresa,
                    fromLS: this.fromLS ? this.fromLS : null
                }).subscribe();
            }
            else {
                this.provider.run(this.providerCode, {
                    action: 'DETAIL',
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    codiceFase: this.codiceFase,
                    numeroProgressivo: this.numeroProgressivo,
                    num: toString(impresa.num),
                    numRagg: toString(impresa.numRagg),
                    impresa
                }).subscribe();
            }
            
        }
    }

    public delete(impresa: FaseImpresaEntry): void {
        if (isObject(impresa)) {
            let func = this.deleteConfirm(impresa);
            this.dialogConfig.open.next(func);
        }
    }

    private deleteConfirm = (impresa: FaseImpresaEntry): any => {
        return () => {
            this.provider.run(this.providerCode, {
                action: 'DELETE',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codLotto: this.codLotto,
                codiceFase: this.codiceFase,
                numeroProgressivo: this.numeroProgressivo,
                num: toString(impresa.num),
                numRagg: toString(impresa.numRagg),
                updateDaexport: this.updateDaexport,
                impresa
            }).subscribe(this.manageDelete);
        }
    }

    public getPartecipante(impresa: FaseImpresaEntry): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('ImpresaPartecipante', toString(impresa.partecipante));
        }
        return '';
    }

    public getRuolo(impresa: RuoloImpresa): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('RuoloAssociazione', toString(impresa.ruolo));
        }
        return '';
    }

    public getTipoImpresa(impresa: FaseImpresaEntry): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('TipologiaAggiudicatario', toString(impresa.tipologiaSoggetto));
        }
        return '';
    }

    // #endregion

    // #region Private

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        const data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codFase: this.codiceFase,
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            cfStazioneAppaltante: this.stazioneAppaltanteInfo.codFiscale,
            chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
            updateDaexport: this.updateDaexport,
        };

        if (this.listaImpresePartecipanti && this.listaImpresePartecipanti.length > 0) {
            data.tipoInvio = this.listaImpresePartecipanti[0].pubblicata ? TipoInvio.RETIFFICHE_AGGIORNAMENTO : TipoInvio.PRIMA_PUBBLICAZIONE;
        }

        this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto') || '1';
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo') || '1';
        this.fromLS = paramsMap.get('fromLS');
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

    private dettaglioSmartCigFactory(codGara: string): () => Observable<SmartCigEntry> {
        return () => {
            return this.smartCigService.dettaglioSmartCig(codGara);
        }
    }



    private checkPubblicazione() {
        let factory = this.loadGaraFactory();
        this.loadGara(factory).subscribe((result: GaraEntry) => {
            this.gara = result;
            this.showButtons();
            if (this.gara.smartCig) {
                this.requestHelper.begin(this.dettaglioSmartCigFactory(this.codGara), this.messagesPanel).subscribe((res: SmartCigEntry) => {
                    this.smartCig = res;
                    if (this.smartCig != null && (this.smartCig.modalitaRealizzazione === 11 || (this.smartCig.sceltaContraente == 18 || this.smartCig.sceltaContraente == 31))) {
                        this.config.sectionTitle = 'DETTAGLIO-GARA.TABS.IMPRESE-AGGIUDICATARIE';
                        this.markForCheck();
                    }
                    this.loadAnagraficaGaraPubblicata().subscribe((pubblicata: boolean) => {
                        if (pubblicata === false) {
                            try {
                                document.getElementById('pubblica-smartcig_header').classList.add('red-highlights');
                                //   document.getElementById('pubblica-smartcig_header').classList.remove('p-panelmenu-header-link');
                            } catch (e) {
                            }
                        } else {
                            try {
                                document.getElementById('pubblica-smartcig_header').classList.remove('red-highlights');
                                //    document.getElementById('pubblica-smartcig_header').classList.add('p-panelmenu-header-link');
                            } catch (e) {
                            }
                        }
                    });
                });
            }
        });
    }

    private anagraficaGaraPubblicataFactory(codGara: string, smartCig: boolean): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, smartCig + "");
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara, true);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private loadGara = (factory: Function): Observable<GaraEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }


    private dettaglioListaImpresePartecipantiFactory(codGara: string, codLotto: string): () => Observable<Array<FaseImpresaEntry>> {
        return () => {
            return this.elencoImpreseInvitatePartecipantiService.getImpresePartecipanti(codGara, codLotto);
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.ELENCO_IMPRESE_INVITATE_PARTECIPANTI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadListaImpresePartecipanti = (): Observable<Array<FaseImpresaEntry>> => {
        let factory = this.dettaglioListaImpresePartecipantiFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaImpresePartecipanti = (result: Array<FaseImpresaEntry>) => {
        this.markForCheck(() => {
            this.listaImpresePartecipanti = result;
            if (this.smartCig != null && (this.smartCig.modalitaRealizzazione === 11 || (this.smartCig.sceltaContraente == 18 || this.smartCig.sceltaContraente == 31))) {
                this.listaImpresePartecipanti.forEach(element => {
                    element.partecipante = 1;
                });
            }


        });
    };

    private manageDelete = (result: IDictionary<any>) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.loadListaImpresePartecipanti().pipe(
            map(this.elaborateListaImpresePartecipanti)
        ).subscribe();
    }

    private getDescrizioneTabellato(tabellatoType: string, tabellatoKey: string): string {
        if (tabellatoType && tabellatoKey) {
            let tabellati: Array<ValoreTabellato> = get(this.valoriTabellati, tabellatoType);
            let found: ValoreTabellato = find(tabellati, (one: ValoreTabellato) => one.codice === tabellatoKey);
            return found ? found.descrizione : tabellatoKey;
        }
        return '';
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private getProtezioniMap(): IDictionary<ProfiloConfigurationItem> {
        let protectionMap: IDictionary<ProfiloConfigurationItem> = reduce(this.userProfile.configurations.configurazioni, (map: IDictionary<any>, one: ProfiloConfigurationItem) => {
            map[one.key] = one;
            return map;
        }, {});
        return protectionMap;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };
        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }
    }

    private showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if (this.gara.readOnly) {
                this.buttonsSubj.next(this.buttonsRO);
            } else if (this.gara.pcp) {
                this.buttonsSubj.next(this.buttonsRO);
            } else {
                this.buttonsSubj.next(this.buttons);
            }
        }
    }

    private refreshTabs(): void {

        const isLS = this.fromLS !== null && isEqual(this.fromLS, 'LS');
        const tabsToFilter = isLS ? this.config.menuTabsLS : this.menuTabs;

        const filteredTabs = tabsToFilter.filter(tab => {
            if (this.smartCig != null && (this.smartCig.modalitaRealizzazione === 11 || (this.smartCig.sceltaContraente == 18 || this.smartCig.sceltaContraente == 31))) {
                if (tab.code === 'imprese-partecipanti') {
                    tab.title = 'DETTAGLIO-GARA.TABS.IMPRESE-AGGIUDICATARIE';
                }
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
                let visible: boolean = this.provider.run(tab.visible, { smartCig: true });
                return visible === false;
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

    private refreshBreadcrumb = () => {
        if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else {
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
    }

    // #endregion
}
