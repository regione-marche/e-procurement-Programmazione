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
    SdkStoreAction,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboboxConfig,
    SdkComboboxInput,
    SdkComboBoxItem,
    SdkComboboxOutput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {
    cloneDeep,
    each,
    find,
    get,
    has,
    isArray,
    isEmpty,
    isEqual,
    isObject,
    isUndefined,
    map as mapArray,
    max,
    min,
    remove,
    toString,
} from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { environment } from 'projects/app-contratti/src/environments/environment';
import { Constants, TEnvs } from '../../../../../../app.constants';
import { BaseFaseService, FaseEntry, FasiInfo } from '../../../../../models/fasi/fasi.model';
import { GaraEntry, LottoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { LottoUtilsService } from '../../../../../services/utils/lotto-utils.service';
import { DettaglioFaseStoreService } from '../dettaglio-fase-store.service';
import { FaseUtilsService } from '../fase-utils.service';


@Component({
    templateUrl: `lista-fasi-lotto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaFasiLottoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-fasi-lotto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsLocked: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    public gara: GaraEntry;
    private lotto: LottoEntry;
    public valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsLS: Array<SdkMenuTab>;
    private fasiSlugMap: IDictionary<string>;
    private statoIcons: IDictionary<string>;
    public invioDisabilitato: boolean = false;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private anagraficaGaraPubblicata: boolean;
    private nuovaFaseAggiudicazioneCode: number;
    private nuovaFaseAggiudicazioneSlug: string;
    public isCigFiglio: boolean = false;

    public fasiLotto: Array<FaseEntry>;
    public listaStoricoFasi: Array<number>;
    public current: boolean = true;
    public riaggiudicabile: boolean = false;
    private currentNumAppa: number;
    private riaggiudicazione: boolean = false;
    private isAdminOrRup: boolean = false;
    public isDrp: boolean = false;
    public isRp: boolean = false;
    public thereIsDrp: boolean = false;
    private deletePermission: boolean = true;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    private motivazioneDialogConfig: SdkDialogConfig;
    public motivazioneDialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);    
    private comboStoricoConfig: SdkComboboxConfig;
    public comboStoricoConfigSub: BehaviorSubject<SdkComboboxConfig> = new BehaviorSubject(null);
    public comboStoricoDataSub: BehaviorSubject<SdkComboboxInput> = new BehaviorSubject(null);
    public loa: string;
    public loaImpersonato: string;
    private fromLS: string;
    public isTest: boolean = environment.ENV as TEnvs != 'PRODUCTION';
    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            this.loa = this.userProfile.loa;
            this.loaImpersonato = this.userProfile.loaImpersonato;
        }));

        this.addSubscription(this.store.select(Constants.CURRENT_NUM_APPA_FASI_LOTTO_SELECT).subscribe((data: number) => {
            if (data != null) {
                this.currentNumAppa = data;
            }
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
        this.initCancellazioneDialog();

    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            mergeMap(this.loadAnagraficaGaraPubblicata),
            map(this.elaborateAnagraficaGaraPubblicata),
            map(this.loadLottoFactory),
            mergeMap(this.loadLotto),
            map(this.elaborateLotto),
            map(this.loadFasiLottoFactory),
            mergeMap(this.loadFasiLotto),
            map(this.elaborateFasiLotto),
            map(() => this.loadStartStoricoCombo()),
            map(() => this.refreshTabs()),
            map(() => this.refreshBreadcrumb()),
            map(() => this.elaborateButtons()),
            map(() => this.checkInfoUserLoa())
        ).subscribe({
            error: () => {
                this.buttonsSubj.next(this.buttonsLocked);
            }
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.fasiSlugMap = this.config.body.fasiSlugMap;
                this.statoIcons = this.config.body.statoIcons;
                this.comboStoricoConfig = this.config.body.comboStorico;
                this.nuovaFaseAggiudicazioneCode = this.config.body.nuovaFaseAggiudicazioneCode;
                this.nuovaFaseAggiudicazioneSlug = this.config.body.nuovaFaseAggiudicazioneSlug;
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

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get faseUtilsService(): FaseUtilsService { return this.injectable(FaseUtilsService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public trackByCode(index: number, item: FaseEntry): string {
        return isObject(item) ? `${item.fase}|${item.progressivo}` : toString(index);
    }

    public getDescrizioneFase(fase: number): string {
        let listaValori: Array<ValoreTabellato> = get(this.valoriTabellati, 'Fase');
        if (isArray(listaValori)) {
            let valoreTabellato: ValoreTabellato = find(listaValori, (one: ValoreTabellato) => one.codice === toString(fase));
            if (isObject(valoreTabellato)) {
                return valoreTabellato.descrizione;
            }
        }
        return toString(fase);
    }

    public goToDettaglioFase(fase: FaseEntry): void {
        if (isObject(fase)) {
            let codice: number = fase.fase;
            let progressivo: number = fase.progressivo;

            let dettaglioFasePage: string = this.getFasePage(toString(codice));

            this.provider.run('APP_GARE_LISTA_FASI', {
                action: 'DETAIL',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codLotto: this.codLotto,
                codiceFase: toString(codice),
                numeroProgressivo: toString(progressivo),
                fasePage: dettaglioFasePage,
                current: this.current,
                nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
                nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
                riaggiudicazione: this.riaggiudicazione,
                cigFiglio: this.isCigFiglio,
                fromLS: this.fromLS ? this.fromLS : null
            }).subscribe();
        }
    }

    public deleteFase(fase: FaseEntry): void {
        if (isObject(fase)) {
            const func = this.deleteFaseConfirm(fase);
            if (fase.deleteLogica === true) {
                this.initCancellazioneDialog();
                this.motivazioneDialogConfig.open.next(func);
            } else {
                this.initDialog();
                this.dialogConfig.open.next(func);
            }
        }
    }

    public deleteScheda(fase: FaseEntry): void {
        if (isObject(fase)) {
            const func = this.deleteSchedaConfirm(fase);
            if (fase.deleteLogica === true) {
                this.initCancellazioneDialog();
                this.motivazioneDialogConfig.open.next(func);
            } else {
                this.initDialog();
                this.dialogConfig.open.next(func);
            }
        }
    }

    public verificaScheda(fase: FaseEntry): void {
        if (isObject(fase)) {
            this.verificaSchedaconfirm(fase)();
        }
    }

    public dettaglioScheda(fase: FaseEntry): void {
        if (isObject(fase)) {
            this.dettaglioFaseconfirm(fase)();
        }
    }

    public pubblicaFase(fase: FaseEntry): void {
        if (fase.fase === 4 && this.checkFasiNonInviate() === true) {
            this.initFaseConcDialog();
            const func = this.pubblicaFaseconfirm(fase);
            this.dialogConfig.open.next(func);

        } else {
            this.pubblicaFaseconfirm(fase)();
        }
    }


    public getPubblicazioneIcon(fase: FaseEntry): string {
        if (this.lotto.situazione == 95 || fase.pubblicabile === false) {
            return get(this.statoIcons, 'invioFlussoBN');
        } else if (fase.pubblicata === true && fase.daExport === false) {
            return get(this.statoIcons, 'invioFlusso');
        } else if (fase.pubblicata === false && fase.daExport === true) {
            return get(this.statoIcons, 'invioFlussoInAttesa');
        } else if (fase.pubblicata === true && fase.daExport === true) {
            return get(this.statoIcons, 'invioRettificaFlusso');
        }
    }

    public getPubblicazioneIconAlt(fase: FaseEntry): string {
        if (this.lotto.situazione == 95 || fase.pubblicabile === false) {
            return this.translateService.instant('FASI-LOTTO.ICONS.INVIO-FLUSSO-BN');
        } else if (fase.pubblicata === true && fase.daExport === false) {
            return this.translateService.instant('FASI-LOTTO.ICONS.INVIO-FLUSSO');
        } else if (fase.pubblicata === false && fase.daExport === true) {
            return this.translateService.instant('FASI-LOTTO.ICONS.INVIO-FLUSSO-IN-ATTESA');
        } else if (fase.pubblicata === true && fase.daExport === true) {
            return this.translateService.instant('FASI-LOTTO.ICONS.INVIO-RETTIFICA-FLUSSO');
        }
    }

    public getPubblicazioneIconAltPcp(fase: FaseEntry): string {
        if (fase.recordTinvio2Uguale1 > 0 && fase.daExportNum === 1) {
            return this.translateService.instant('FASI-LOTTO.ICONS.NON-CONFERMATA');
        } else if (fase.daExportNum === 3) {
            return this.translateService.instant('FASI-LOTTO.ICONS.IN-ATT-CONF');
        } else if (fase.recordTinvio2Uguale3 > 0 && fase.daExportNum === 2) {
            return this.translateService.instant('FASI-LOTTO.ICONS.CONF');
        } else if (fase.recordTinvio2Uguale1 == 0) {
            return this.translateService.instant('FASI-LOTTO.ICONS.NON-TRASMESSA');
        }
    }

    public getDeleteTitle(fase): string {
        if (fase.deleteLogica === true)
            return this.translateService.instant('FASI-LOTTO.AZIONI.ANNULLA-INVIO');
        else if (fase.deleteFisica === true)
            return this.translateService.instant('FASI-LOTTO.AZIONI.DELETE');
    }

    public getDeleteIcon(fase): string {       
        if (fase.deleteLogica === true && this.isAdminOrRup)
            return "mgg-icons-action-undo";
        else if (fase.deleteFisica === true)
            return "mgg-icons-crud-delete";               
    }

    /*public getDeleteIconPcp(fase): string {       
        if (fase.deleteLogica === true && (this.isAdminOrRup || this.isDrp) && fase.daExportNum === 1 && fase.recordTinvio2Uguale3 === 0 && fase.recordTinvio2Uguale1 > 0 && (this.loa == '3' || this.loa == '4' || this.loaImpersonato == '3' || this.loaImpersonato == '4')){          
            return "mgg-icons-close-circle";
        } else if (fase.deleteFisica === true && fase.pubblicata === false){          
            return "mgg-icons-crud-delete";
        }
                             
     }*/

    public getInvioIcon(fase): string {
        if (this.lotto.situazione == 95 || fase.pubblicabile === false) {
            return "icon-invio-flusso-bn";
        } else if (fase.pubblicata === true && fase.daExport === false) {
            return "icon-invio-flusso";
        } else if (fase.pubblicata === false && fase.daExport === true) {
            return "icon-invio-in-attesa"
        } else if (fase.pubblicata === true && fase.daExport === true) {
            return "icon-rettifica-flusso";
        }
    }



    public changeStorico(output: SdkComboboxOutput): void {
        if (output != null && output.data != null && output.data.key != null && !isEmpty(output.data.key) && +output.data.key != this.currentNumAppa) {
            this.clearCurrentNumAppa();
            // get key
            const key: number = +output.data.key;
            // save key
            this.store.dispatch(new SdkStoreAction(Constants.CURRENT_NUM_APPA_FASI_LOTTO_DISPATCHER, key));
            // reload table
            this.loadTable();
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private refreshTabs(): void {
        const isLS = this.fromLS !== null && isEqual(this.fromLS, 'LS');
        const tabsToFilter = isLS ? this.config.menuTabsLS : this.menuTabs;

        const filteredTabs = tabsToFilter.filter(tab => {
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

    private refreshBreadcrumb = () => {
        if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else {
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            codGara: this.codGara,
            codLotto: this.codLotto,
            current: this.current,
            nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
            nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
            riaggiudicazione: this.riaggiudicazione,
            cigFiglio: this.isCigFiglio,
            fromLS: this.fromLS ? this.fromLS : null
        };
        if (button.code === 'riaggiudicazione') {
            this.executeRiaggiudicazione(button, data);
        } else {
            this.clearCurrentNumAppa();
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.fromLS = paramsMap.get('fromLS');
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfig);
    }

    private initCancellazioneDialog(): void {
        this.motivazioneDialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-FASE-LOGICA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            motivazioneLabel: this.translateService.instant('DIALOG.MOTIVAZIONE-CANCELLAZIONE-LOGICA-TEXT'),
            open: new Subject()
        };
        this.motivazioneDialogConfigObs.next(this.motivazioneDialogConfig);
    }

    private initRiaggiudicazioneDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.RIAGGIUDICAZIONE-TITLE'),
            message: this.translateService.instant('DIALOG.RIAGGIUDICAZIONE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfig);
    }

    private initFaseConcDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.CONCLUSIONE-TITLE'),
            message: this.translateService.instant('DIALOG.CONCLUSIONE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfig);
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

    private checkFasiNonInviate(): boolean {
        let hasFasiNonInviate: boolean = false;
        each(this.fasiLotto, function (fase: FaseEntry) {
            if (fase.pubblicata == false && fase.fase !== 4 && fase.fase !== 5) {
                hasFasiNonInviate = true;
            }
        });
        return hasFasiNonInviate;
    }

    private verificaSchedaconfirm(fase: FaseEntry): any {
        return () => {
            if (isObject(fase) && this.invioDisabilitato === false && fase.pubblicabile === true) {
                this.provider.run('APP_GARE_LISTA_FASI', {
                    action: 'CHECK',
                    codGara: fase.codGara,
                    codLotto: fase.codLotto,
                    codiceFase: fase.fase,
                    codFase: fase.fase,
                    numeroProgressivo: fase.progressivo,
                    current: this.current,
                    nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
                    nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
                    riaggiudicazione: this.riaggiudicazione,
                    cigFiglio: this.isCigFiglio,                    
                    codProfilo: this.userProfile.configurations?.idProfilo,
                    cfImpersonato: this.userProfile.cfImpersonato, 
                    loaImpersonato: this.userProfile.loaImpersonato, 
                    idpImpersonato: this.userProfile.idpImpersonato,
                    codein: this.stazioneAppaltanteInfo.codice,
                    fromLS: this.fromLS ? this.fromLS : null
                }).subscribe((data: IDictionary<any>)=>{
                    if (has(data, 'reload') && get(data, 'reload') === true) {                        
                        this.loadTable();
                    }
                });
            }
        }
    }

    private pubblicaFaseconfirm(fase: FaseEntry): any {
        return () => {
            if (isObject(fase) && this.invioDisabilitato === false && fase.pubblicabile === true) {
                this.provider.run('APP_GARE_LISTA_FASI', {
                    action: 'PUBLISH',
                    codGara: fase.codGara,
                    codLotto: fase.codLotto,
                    codiceFase: fase.fase,
                    numeroProgressivo: fase.progressivo,
                    current: this.current,
                    nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
                    nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
                    riaggiudicazione: this.riaggiudicazione,
                    cigFiglio: this.isCigFiglio,
                    fromLS: this.fromLS ? this.fromLS : null
                }).subscribe();
            }
        }
    }

    private dettaglioFaseconfirm(fase: FaseEntry): any {
        return () => {
            if (isObject(fase)) {
                this.provider.run('APP_GARE_LISTA_FASI', {
                    action: 'PUBLISH',
                    codGara: fase.codGara,
                    codLotto: fase.codLotto,
                    codiceFase: fase.fase,
                    numeroProgressivo: fase.progressivo,
                    current: this.current,
                    nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
                    nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
                    riaggiudicazione: this.riaggiudicazione,
                    cigFiglio: this.isCigFiglio,
                    fromLS: this.fromLS ? this.fromLS : null
                }).subscribe();
            }
        }
    }


    private dettaglioFasiLottoFactory(codGara: string, codLotto: string, numAppa?: number): () => Observable<FasiInfo> {
        return () => {
            return this.gareService.getFasiLotto(codGara, codLotto, numAppa);
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.FASI_LOTTO_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
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

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if(isObject(this.gara.tecnico) && (this.gara.tecnico?.cf?.toUpperCase() == this.userProfile.codiceFiscale?.toUpperCase() || this.userProfile.ruolo == 'A')){
            this.isAdminOrRup = true;
        }
        if(isObject(this.gara.delegato)){
            this.thereIsDrp = true;
            if((this.gara.delegato?.cf?.toUpperCase() == this.userProfile.codiceFiscale?.toUpperCase() || this.gara.delegato?.cf?.toUpperCase() == this.userProfile.cfImpersonato?.toUpperCase())){
                this.isDrp = true;
            }
            
        }
        if(isObject(this.gara.tecnico) && (this.gara.tecnico?.cf?.toUpperCase() == this.userProfile.codiceFiscale?.toUpperCase() || this.gara.tecnico?.cf?.toUpperCase() == this.userProfile.cfImpersonato?.toUpperCase())){
            this.isRp = true;
        }
        if(this.gara.ruoloCollaboratore != null && this.gara.ruoloCollaboratore === 1 && (this.userProfile.ruolo != 'A' && this.userProfile.ruolo != 'C')){
            this.isAdminOrRup = false;
            this.deletePermission = false;
        }
    };


    private lottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    private loadLottoFactory = (): Function => {
        let factory = this.lottoFactory(this.codGara, this.codLotto);
        return factory;
    }

    private loadLotto = (factory: Function): Observable<LottoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
        this.isCigFiglio = LottoUtilsService.isCigFiglio(this.lotto);
    };

    private anagraficaGaraPubblicataFactory(codGara: string): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, 'false');
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateAnagraficaGaraPubblicata = (pubblicata: boolean) => {
        this.anagraficaGaraPubblicata = pubblicata;
    }

    private loadFasiLottoFactory = (): Function => {
        let factory = this.dettaglioFasiLottoFactory(this.codGara, this.codLotto, this.currentNumAppa);
        return factory;
    }

    private loadFasiLotto = (factory: Function): Observable<FasiInfo> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateFasiLotto = (result: FasiInfo) => {
        this.markForCheck(() => {
            this.fasiLotto = result.listaFasi;
            this.listaStoricoFasi = result.listaStoricoFasi;
            this.current = result.current;
            this.riaggiudicabile = result.riaggiudicabile;
        });
    };

    private getFasePage(codiceFase: string): string {
        if (isUndefined(codiceFase)) {
            return undefined;
        }
        let pageSlug: string = get(this.fasiSlugMap, codiceFase);
        if (isUndefined(pageSlug) || isEmpty(pageSlug)) {
            return undefined;
        }
        return pageSlug;
    }

    private loadTable(): void {
        let factory = this.loadGaraFactory();
        this.loadGara(factory)
            .pipe(
                map(this.elaborateGara),
                map(this.loadLottoFactory),
                mergeMap(this.loadLotto),
                map(this.elaborateLotto),
                map(this.loadFasiLottoFactory),
                mergeMap(this.loadFasiLotto),
                map(this.elaborateFasiLotto),
                map(() => this.loadStoricoCombo()),
                map(() => this.elaborateButtons())
            ).subscribe({
                error: () => {
                    this.buttonsSubj.next(this.buttonsLocked);
                }
            });
    }

    private deleteFaseConfirm(fase: FaseEntry): any {
        return (motivazione?: string) => {
            this.dettaglioFaseStoreService.clear();
            let codiceFase: string = toString(fase.fase);

            let faseService: BaseFaseService;
            if (fase.deleteFisica === true) {
                faseService = this.faseUtilsService.getFaseService(codiceFase);
            }

            this.provider.run('APP_GARE_LISTA_FASI', {
                action: fase.deleteFisica === true ? 'DELETE' : 'DELETE-LOGICA',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codLotto: this.codLotto,
                faseService,
                fase,
                cfStazioneAppaltante: this.stazioneAppaltanteInfo.codFiscale,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                idAvGara: this.gara.identificativoGara,
                cig: this.lotto.cig,
                userProfile: this.userProfile,
                motivazione,
                current: this.current,
                nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
                nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
                riaggiudicazione: this.riaggiudicazione,
                cigFiglio: this.isCigFiglio
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reload') && get(data, 'reload') === true) {
                    this.clearCurrentNumAppa();
                    this.loadTable();
                }
            });
        }
    }

    private deleteSchedaConfirm(fase: FaseEntry): any {
        return (motivazione?: string) => {
            this.dettaglioFaseStoreService.clear();
            let codiceFase: string = toString(fase.fase);

            let faseService: BaseFaseService;
            if (fase.deleteFisica === true) {
                faseService = this.faseUtilsService.getFaseService(codiceFase);
            }

            this.provider.run('APP_GARE_LISTA_FASI', {
                action: fase.deleteFisica === true ? 'DELETE' : 'DELETE-LOGICA-PCP',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codLotto: this.codLotto,
                faseService,
                fase,
                cfStazioneAppaltante: this.stazioneAppaltanteInfo.codFiscale,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                idAvGara: this.gara.identificativoGara,
                cig: this.lotto.cig,
                userProfile: this.userProfile,
                motivazione,
                current: this.current,
                nuovaFaseAggiudicazioneCode: this.nuovaFaseAggiudicazioneCode,
                nuovaFaseAggiudicazioneSlug: this.nuovaFaseAggiudicazioneSlug,
                riaggiudicazione: this.riaggiudicazione,
                cigFiglio: this.isCigFiglio,
                codFase: fase.fase,
                pcp: this.gara.pcp,
                numeroProgressivo: fase.progressivo,
                stazioneAppaltante: this.stazioneAppaltanteInfo.codice,                                
                syscon: this.userProfile.syscon,                         
                codProfilo: this.userProfile.configurations?.idProfilo,
                loa: this.loa,
                cfImpersonato: this.userProfile.cfImpersonato,
                loaImpersonato: this.userProfile.loaImpersonato,
                idpImpersonato: this.userProfile.idpImpersonato,
                codein: this.stazioneAppaltanteInfo.codice,
                fromLS: this.fromLS ? this.fromLS : null             
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reload') && get(data, 'reload') === true) {
                    this.clearCurrentNumAppa();
                    this.loadTable();
                }
            });
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsLocked = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLocked, this.userProfile.configurations)
        };

        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }   
    }

    private elaborateButtons(): void {
        
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')){
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if(this.gara.readOnly){
                this.buttonsSubj.next(this.buttonsLocked);
            } else{
                // creazione disabilitata se situazione lotto 7 o 95
                if (this.lotto.situazione == 7 || this.lotto.situazione == 95 || this.lotto.contrattoEscluso161718 == '1') {
                    if (this.lotto.situazione == 95 || this.lotto.contrattoEscluso161718 == '1') {
                        this.invioDisabilitato = true;
                    }
                    if (this.lotto.contrattoEscluso161718 == '1') {
                        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                            {
                                message: 'FASI-LOTTO.ART_E2'
                            }
                        ]);
                    }
                }
    
                if (this.lotto.situazione == 7 || this.lotto.situazione == 95 || this.lotto.contrattoEscluso161718 == '1' ) {
                    if (this.riaggiudicabile === true && this.lotto.situazione != 95) {
                        let allButtons = cloneDeep(this.buttons);
                        // rimuovo il pulsante di riaggiudicazione se non sono nella fase corrente o il lotto non e' riaggiudicabile
                        // e rimuovo il pulsante di nuova fase se non sono nell'ultima aggiudicazione
                        remove(allButtons.buttons, (one: SdkBasicButtonInput) => {
                            return (one.code === 'riaggiudicazione' && (this.riaggiudicabile === false || this.current === false)) ||
                                (one.code === 'go-to-fasi-visibili' && (this.current === false || this.isCigFiglio === true));
                        });
                        this.buttonsSubj.next(allButtons);
                    } else {
                        this.buttonsSubj.next(this.buttonsLocked);
                    }
    
                
                } else {
                    let allButtons = cloneDeep(this.buttons);
                    // rimuovo il pulsante di riaggiudicazione se non sono nella fase corrente o il lotto non e' riaggiudicabile
                    // e rimuovo il pulsante di nuova fase se non sono nell'ultima aggiudicazione
                    remove(allButtons.buttons, (one: SdkBasicButtonInput) => {
                        return (one.code === 'riaggiudicazione' && (this.riaggiudicabile === false || this.current === false)) ||
                            (one.code === 'go-to-fasi-visibili' && (this.current === false || this.isCigFiglio === true));
                    });
                    this.buttonsSubj.next(allButtons);
                }
            }
        }
    }

    private loadStartStoricoCombo(): void {
        if (this.listaStoricoFasi != null && this.listaStoricoFasi.length > 1) {
            let tempListaStoricoFasi: Array<number> = cloneDeep(this.listaStoricoFasi);
            let currentAggiudicazioneValue: number = max(tempListaStoricoFasi);
            let listaStoricoFasiComboItems: Array<SdkComboBoxItem> = mapArray(tempListaStoricoFasi, (one: number) => {
                const item: SdkComboBoxItem = {
                    key: toString(one),
                    value: ''
                };
                if (one === 1) {
                    item.value = this.translateService.instant('FASI-LOTTO.STORICO-DESCRIPTION-AGGIUDICAZIONE')
                } else if (one === 2) {
                    item.value = this.translateService.instant('FASI-LOTTO.STORICO-DESCRIPTION-RIAGGIUDICAZIONE');
                } else {
                    item.value = this.translateService.instant('FASI-LOTTO.STORICO-DESCRIPTION', { param: one - 1 })
                }
                return item;
            })

            let comboConfig: SdkComboboxConfig = cloneDeep(this.comboStoricoConfig);
            comboConfig.itemsProvider = () => {
                return of(listaStoricoFasiComboItems);
            };
            this.comboStoricoConfigSub.next(comboConfig);
            let savedAggiudicazioneValue = currentAggiudicazioneValue;
            // se arrivo ad esempio da un dettaglio di uno storico allora carico la lista dello storico
            if (this.currentNumAppa != null) {
                savedAggiudicazioneValue = this.currentNumAppa;
            }
            this.comboStoricoDataSub.next(
                {
                    data: {
                        key: toString(savedAggiudicazioneValue),
                        value: ''
                    }
                }
            );
            if (this.currentNumAppa == null) {
                this.currentNumAppa = savedAggiudicazioneValue;
            }

            this.riaggiudicazione = !isEmpty(this.listaStoricoFasi) && min(this.listaStoricoFasi) != this.currentNumAppa;
        }
    }

    private loadStoricoCombo(): void {
        if (this.listaStoricoFasi != null && this.listaStoricoFasi.length > 1) {
            let tempListaStoricoFasi: Array<number> = cloneDeep(this.listaStoricoFasi);
            let currentAggiudicazioneValue: number = max(tempListaStoricoFasi);
            let listaStoricoFasiComboItems: Array<SdkComboBoxItem> = mapArray(tempListaStoricoFasi, (one: number) => {
                const item: SdkComboBoxItem = {
                    key: toString(one),
                    value: ''
                };
                if (one === 1) {
                    item.value = this.translateService.instant('FASI-LOTTO.STORICO-DESCRIPTION-AGGIUDICAZIONE')
                } else if (one === 2) {
                    item.value = this.translateService.instant('FASI-LOTTO.STORICO-DESCRIPTION-RIAGGIUDICAZIONE');
                } else {
                    item.value = this.translateService.instant('FASI-LOTTO.STORICO-DESCRIPTION', { param: one - 1 })
                }
                return item;
            })

            let comboConfig: SdkComboboxConfig = cloneDeep(this.comboStoricoConfig);
            comboConfig.itemsProvider = () => {
                return of(listaStoricoFasiComboItems);
            };
            this.comboStoricoConfigSub.next(comboConfig);
            if (this.currentNumAppa == null) {
                this.currentNumAppa = currentAggiudicazioneValue;
            }
            this.comboStoricoDataSub.next(
                {
                    data: {
                        key: toString(this.currentNumAppa),
                        value: ''
                    }
                }
            );
            this.riaggiudicazione = !isEmpty(this.listaStoricoFasi) && min(this.listaStoricoFasi) != this.currentNumAppa;
        }
    }

    private executeRiaggiudicazione(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        const func = this.riaggiudicazioneConfirm(button, data);
        this.initRiaggiudicazioneDialog();
        this.dialogConfig.open.next(func);
    }

    private riaggiudicazioneConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>) {
        return () => {
            this.clearCurrentNumAppa();
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private clearCurrentNumAppa(): void {
        delete this.currentNumAppa;
        // clear
        this.store.dispatch(new SdkStoreAction(Constants.CURRENT_NUM_APPA_FASI_LOTTO_DISPATCHER, undefined));
    }

    private checkInfoUserLoa(): void {
        if (this.messagesPanel != undefined) {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            if(this.gara.pcp && (isEmpty(this.userProfile.loa) || (!isEmpty(this.userProfile.loa) && +this.userProfile.loa < 3))){
                this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                    {
                        message: 'DIALOG.INSUFFICIENT-LOA'
                    }
                ]);
            } else if(this.gara.pcp && (isEmpty(this.userProfile.loa) || (!isEmpty(this.userProfile.loa) && +this.userProfile.loa < 3))){
                this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                    {
                        message: 'DIALOG.INSUFFICIENT-LOA'
                    }
                ]);
            }           
        }
    }

    // #endregion
}
