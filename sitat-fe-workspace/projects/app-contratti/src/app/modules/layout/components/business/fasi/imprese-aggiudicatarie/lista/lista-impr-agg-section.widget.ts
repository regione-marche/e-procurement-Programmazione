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
import { HttpRequestHelper, ProtectionUtilsService, TabellatiCacheService, ValoreTabellato } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {
    each,
    filter,
    find,
    get,
    has,
    isArray,
    isEmpty,
    isEqual,
    isObject,
    orderBy,
    set,
    split,
    toString,
    trim,
    values
} from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { FlussiAtto } from 'projects/app-contratti/src/app/modules/models/pubblicazioni/pubblicazione-atto.model';
import { FlussiFase } from 'projects/app-contratti/src/app/modules/models/pubblicazioni/pubblicazione-fase.model';
import { PubblicazioneAttoService } from 'projects/app-contratti/src/app/modules/services/pubblicazioni/pubblicazione-atto.service';
import { PubblicazioneFaseService } from 'projects/app-contratti/src/app/modules/services/pubblicazioni/pubblicazione-fase.service';
import { Constants } from '../../../../../../../app.constants';
import { DettaglioAttoEntry, GaraEntry } from '../../../../../../models/gare/gare.model';
import {
    ImpresaAggiudicatariaAttoEntry,
    ImpresaAggiudicatariaEntry,
    ImpresaAggiudicatariaRaggEntry,
} from '../../../../../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';
import { GareService } from '../../../../../../services/gare/gare.service';
import {
    ImpreseAggiudicatarieService,
} from '../../../../../../services/imprese-aggiudicatarie/imprese-aggiudicatarie.service';
import { DettaglioFaseStoreService } from '../../dettaglio-fase-store.service';

@Component({
    templateUrl: `lista-impr-agg-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaImpreseAggiudicatarieSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-imprese-aggiudicatarie-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    private buttonsWithoutImprese: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private tipoDocumento: string;
    private numPubb: string;
    private campiVisibili: Array<string>;
    private campiVisibiliString: string;
    private campiObbligatori: Array<string>;
    private campiObbligatoriString: string;
    public listaImpreseAggiudicatarie: Array<ImpresaAggiudicatariaRaggEntry>;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsLS: Array<SdkMenuTab>;
    private providerCode: string;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    public atto: boolean = false;
    private gara: GaraEntry;
    private attoEntry: DettaglioAttoEntry;
    public current: boolean = true;
    private riaggiudicazione: boolean = false;
    private cigFiglio: boolean = false;
    public pubblicazioni: Array<FlussiAtto>;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        if (this.dettaglioFaseStoreService.current != null) {
            this.current = this.dettaglioFaseStoreService.current;
        }

        if (this.dettaglioFaseStoreService.riaggiudicazione != null) {
            this.riaggiudicazione = this.dettaglioFaseStoreService.riaggiudicazione;
        }

        if (this.dettaglioFaseStoreService.cigFiglio != null) {
            this.cigFiglio = this.dettaglioFaseStoreService.cigFiglio;
        }

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            mergeMap(this.loadAtto),
            map(this.elaborateAtto),
            mergeMap(this.loadListaImpreseAggiudicatarie),
            map(this.elaborateListaImpreseAggiudicatarie),
            mergeMap(this.getListaPubblicazioni),
            map(this.elaborateListaPubblicazioni),
            map(() => this.refreshBreadcrumb())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.providerCode = this.config.body.provider;
                this.atto = this.config.body.atto;
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

    private get impreseAggiudicatarieService(): ImpreseAggiudicatarieService { return this.injectable(ImpreseAggiudicatarieService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    private get pubblicazioneAttoService(): PubblicazioneAttoService { return this.injectable(PubblicazioneAttoService) }

    private get pubblicazioneFaseService(): PubblicazioneFaseService { return this.injectable(PubblicazioneFaseService); }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public trackByCode(index: number, item: ImpresaAggiudicatariaRaggEntry): string {
        return isObject(item) ? toString(item.idGruppo) : toString(index);
    }

    public goToDettaglioImpresa(impresa: ImpresaAggiudicatariaRaggEntry): void {
        if (isObject(impresa)) {

            this.provider.run(this.providerCode, {
                action: 'DETAIL',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codLotto: this.codLotto,
                codiceFase: this.codiceFase,
                numeroProgressivo: this.numeroProgressivo,
                idGruppo: impresa.idGruppo,
                impresa,
                tipoDocumento: this.tipoDocumento,
                numPubb: this.numPubb,
                campiVisibili: this.campiVisibiliString,
                campiObbligatori: this.campiObbligatoriString,
                riaggiudicazione: this.riaggiudicazione,
                fromLS: this.fromLS ? this.fromLS : null
            }).subscribe();
        }
    }

    public delete(impresa: ImpresaAggiudicatariaRaggEntry): void {
        if (isObject(impresa)) {
            this.deleteImpresa(impresa);
        }
    }

    public getRuolo(impresa: ImpresaAggiudicatariaEntry): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('RuoloAssociazione', toString(impresa.ruolo));
        }
        return '';
    }

    public getTipoImpresa(impresa: ImpresaAggiudicatariaRaggEntry): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('TipologiaAggiudicatario', toString(impresa.idTipoAgg));
        }
        return '';
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
                let visible: boolean = this.provider.run(tab.visible, { tipoDocumento: this.tipoDocumento, atto: this.attoEntry });
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

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            tipoDocumento: this.tipoDocumento,
            numPubb: this.numPubb,
            campiVisibili: this.campiVisibiliString,
            campiObbligatori: this.campiObbligatoriString
        }).subscribe();
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');
        let campiVisibili: string = paramsMap.get('campiVisibili');
        this.campiVisibiliString = campiVisibili;
        this.fromLS = paramsMap.get('fromLS');
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

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.IMPRESE_AGGIUDICATARIE_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private dettaglioListaImpreseAggiudicatarieFactory(codGara: string, codLotto: string, numProgressivoFase: string): () => Observable<Array<ImpresaAggiudicatariaEntry>> {
        return () => {
            return this.impreseAggiudicatarieService.getImpreseAggiudicatarie(codGara, codLotto, numProgressivoFase);
        }
    }

    private dettaglioListaImpreseAggiudicatarieAttoFactory(codGara: string, numPubb: string): () => Observable<Array<ImpresaAggiudicatariaAttoEntry>> {
        return () => {
            return this.impreseAggiudicatarieService.getImpreseAggiudicatarieAtto(codGara, numPubb);
        }
    }

    private loadListaImpreseAggiudicatarie = (): Observable<Array<ImpresaAggiudicatariaEntry | ImpresaAggiudicatariaAttoEntry>> => {
        let factory: Function;
        if (this.atto === true) {
            factory = this.dettaglioListaImpreseAggiudicatarieAttoFactory(this.codGara, this.numPubb);
        } else {
            factory = this.dettaglioListaImpreseAggiudicatarieFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        }
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaImpreseAggiudicatarie = (result: Array<ImpresaAggiudicatariaEntry | ImpresaAggiudicatariaAttoEntry>) => {
        this.markForCheck(() => {
            this.listaImpreseAggiudicatarie = this.reduceImpreseList(result);
            if(this.fromLS) {
                this.buttonsSubj.next(this.buttonsLS);
            }
            else {
                if (this.gara.readOnly || (this.gara.pcp && (this.atto == null || this.atto === false))) {
                    this.buttonsSubj.next(this.buttonsRO);
                } else {
                    if (isArray(result) && !isEmpty(result) && this.gareService.isAccordoConvenzione(this.gara) === false) {
                        this.buttonsSubj.next(this.buttons);
                    } else {
                        this.buttonsSubj.next(this.buttonsWithoutImprese);
                    }
                }
            }
        });
    };

    private manageDelete = (result: IDictionary<any>) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.loadListaImpreseAggiudicatarie().pipe(
            map(this.elaborateListaImpreseAggiudicatarie)
        ).subscribe();
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

    private deleteImpresa(impresa: ImpresaAggiudicatariaRaggEntry): void {
        let func = this.deleteImpresaConfirm(impresa);
        this.dialogConfig.open.next(func);
    }

    private deleteImpresaConfirm(impresa: ImpresaAggiudicatariaRaggEntry): any {
        return () => {

            this.provider.run(this.providerCode, {
                action: 'DELETE',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codLotto: this.codLotto,
                codiceFase: this.codiceFase,
                numeroProgressivo: this.numeroProgressivo,
                idGruppo: impresa.idGruppo,
                impresa,
                tipoDocumento: this.tipoDocumento,
                numPubb: this.numPubb,
                campiVisibili: this.campiVisibiliString,
                campiObbligatori: this.campiObbligatoriString
            }).subscribe(this.manageDelete);
        }
    }

    private reduceImpreseList(result: Array<ImpresaAggiudicatariaEntry | ImpresaAggiudicatariaAttoEntry>): Array<ImpresaAggiudicatariaRaggEntry> {
        let mappa: IDictionary<ImpresaAggiudicatariaRaggEntry> = {};
        each(result, (one: ImpresaAggiudicatariaEntry | ImpresaAggiudicatariaAttoEntry, index: number) => {
            if (one.idGruppo != null) {
                let record: ImpresaAggiudicatariaRaggEntry = {
                    idGruppo: one.idGruppo,
                    imprese: [],
                    index,
                    idTipoAgg: one.idTipoAgg,
                    numAppa: get(one, 'numAppa')
                };

                if (has(mappa, one.idGruppo)) {
                    record = get(mappa, one.idGruppo);
                }
                record.imprese.push(one);
                set(mappa, one.idGruppo, record);
            } else {
                let record: ImpresaAggiudicatariaRaggEntry = {
                    idGruppo: 0,
                    imprese: [],
                    index,
                    idTipoAgg: one.idTipoAgg,
                    numAppa: get(one, 'numAppa')
                };
                record.imprese.push(one);
                set(mappa, this.getMapKey(), record);
            }
        });

        return orderBy(values(mappa), 'index', 'asc');
    }

    private getMapKey(): string {
        return Math.random().toString(36).slice(2); //NOSONAR
    }

    private getDescrizioneTabellato(tabellatoType: string, tabellatoKey: string): string {
        if (tabellatoType && tabellatoKey) {
            let tabellati: Array<ValoreTabellato> = get(this.valoriTabellati, tabellatoType);
            let found: ValoreTabellato = find(tabellati, (one: ValoreTabellato) => one.codice === tabellatoKey);
            return found ? found.descrizione : tabellatoKey;
        }
        return '';
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

        this.buttonsWithoutImprese = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsWithoutImprese, this.userProfile.configurations)
        };

        if (((this.atto == null || this.atto === false) && this.current === false) || this.cigFiglio === true) {
            this.buttons.buttons = filter(this.buttons.buttons, (one: SdkBasicButtonInput) => one.code !== 'nuova-impresa');
            this.buttonsWithoutImprese.buttons = filter(this.buttonsWithoutImprese.buttons, (one: SdkBasicButtonInput) => one.code !== 'nuova-impresa');
        }
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
        if (this.gara.pcp) {
            this.menuTabs = filter(this.menuTabs, (one) => one.code !== 'pubblica-atto');
            this.menuTabs = filter(this.menuTabs, (one) => one.code !== 'dettaglio-inc-prof-fase-agg');
            this.refreshTabs();
        }
    }

    private dettaglioAttoFactory(codGara: string, tipoDocumento: string, numPubb: string): () => Observable<DettaglioAttoEntry> {
        return () => {
            return this.gareService.dettaglioAtto(codGara, tipoDocumento, numPubb);
        }
    }

    private loadAtto = () => {
        if (this.atto === true) {
            let factory = this.dettaglioAttoFactory(this.codGara, this.tipoDocumento, this.numPubb);
            return this.requestHelper.begin(factory, this.messagesPanel);
        }
        return of(null);
    }

    private elaborateAtto = (atto: DettaglioAttoEntry) => {
        if (this.atto === true && atto != null) {
            this.attoEntry = atto;
        }
        this.refreshTabs()
    }

    private getListaPubblicazioni = () => {
        let factory: Function;
        if (this.atto === true) {
            factory = this.listaPubblicazioniAttoFactory(this.codGara, this.numPubb);
        } else {
            factory = this.listaPubblicazioniFaseFactory(this.codGara, this.codiceFase, this.codLotto, this.numeroProgressivo);
        }
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaPubblicazioni = (result: Array<any>) => {
        this.pubblicazioni = result;

        if (this.pubblicazioni != null && this.pubblicazioni.length > 0) {

            try {
                document.getElementById('pubblica-atto_header').classList.remove('red-highlights');
                //  document.getElementById('pubblica-atto_header').classList.add('p-panelmenu-header-link');
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

    private listaPubblicazioniAttoFactory(codGara: string, num: string): () => Observable<Array<FlussiAtto>> {
        return () => {
            return this.pubblicazioneAttoService.getListaPubblicazioniAtto(codGara, num);
        }
    }

    private listaPubblicazioniFaseFactory(codGara: string, codFase: string, codLotto: string, numProgressivoFase: string): () => Observable<Array<FlussiFase>> {
        return () => {
            return this.pubblicazioneFaseService.getListaPubblicazioniFase(codGara, codFase, codLotto, numProgressivoFase);
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
