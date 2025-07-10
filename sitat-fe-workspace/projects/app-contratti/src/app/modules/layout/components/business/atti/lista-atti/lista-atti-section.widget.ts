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
import { HttpRequestHelper, ProtectionUtilsService, StazioneAppaltanteInfo, ValoreTabellato } from '@maggioli/app-commons';
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
    SdkButtonGroupSingleInput,
    SdkCheckboxOutput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkPanelbarConfig,
    SdkPanelbarItem,
    SdkPanelbarOutput,
    SdkPanelbarOutputAction
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {
    each,
    filter,
    find,
    get,
    has,
    head,
    isEmpty,
    isObject,
    join,
    reduce,
    remove,
    size,
    split,
    toString
} from 'lodash-es';
import { SmartCigEntry } from 'projects/app-contratti/src/app/modules/models/smartcig/smartcig.model';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap, tap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { AttoEntry, GaraEntry, IdPubblicati } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { SmartCigService } from '../../../../../services/smartcig/smartcig.service';

@Component({
    templateUrl: `lista-atti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaAttiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-atti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    private config: any = {};
    private userProfile: UserProfile;

    private buttonsRO: SdkButtonGroupInput;
    private buttonsMultilotto: SdkButtonGroupInput;

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public listaAttiSubject: Subject<SdkPanelbarConfig> = new Subject();
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private codGara: string;
    private menuTabs: Array<SdkMenuTab>;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private attiMap: IDictionary<string>;
    private tuttiGliAtti: Array<AttoEntry>;
    private attiPreferiti: Array<AttoEntry> = [];
    private listaAtti: Array<AttoEntry>;
    private smartCig: boolean = false;
    private buttons: SdkButtonGroupInput;
    private buttonsBack: SdkButtonGroupInput;
    private dialogConfig: SdkDialogConfig;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private modalConfig: any;
    private gara: SmartCigEntry;
    public garaEntry: GaraEntry;
    private isSceltaContrVal: Boolean = false;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initModal();
    }

    protected onAfterViewInit(): void {
        this.checkSmartCig()
            .pipe(
                map(this.elaborateCheckSmartCig),
                map(() => this.checkInfoBox()),
                mergeMap(() => this.getGara()),
                mergeMap(this.loadDettaglio),
                map(this.elaborateDettaglio),
                map(() => this.refreshTabs()),
                mergeMap(this.initGrid),
                map(this.manageListaAtti)
            ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

    // #region Public

    public getGara(): Observable<GaraEntry> {
        let factory = this.loadGaraFactory();
        return this.requestHelper.begin(factory, this.messagesPanel).pipe(
            tap((result: GaraEntry) => {
                this.garaEntry = result;
                this.showButtons();
                if (this.garaEntry.pcp) {
                    this.menuTabs = filter(this.config.menuTabs, (one) => one.code !== 'pubblica');
                    this.refreshTabs();
                }
                if (!this.garaEntry.pcp) {
                    if (this.garaEntry.sceltaContraenteValorizzata) {
                        this.isSceltaContrVal = true;
                        this.markForCheck();
                    } else {
                        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                            {
                                message: "SCELTA-CONTRAENTE.INFO",
                            },
                        ]);
                    }
                } else {
                    this.markForCheck();
                }
            })
        );
    }

    public onOutput(item: SdkCheckboxOutput): void {
        this.manageListaAtti(this.tuttiGliAtti);
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            let data = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                attiMap: this.attiMap,
                listaAtti: this.listaAtti,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                syscon: this.userProfile.syscon,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                isSmartCig: this.smartCig
            };

            /*if (button.code === 'pubblica-tutto') {

                let isPubblicato = false;
                this.listaAtti.forEach(atto => {
                    if (atto.statiId.stato != null && atto.statiId.stato === 'Pubblicato') {
                        isPubblicato = true;
                        return;
                    }
                });

                let pubblicaTuttoModalConfig = this.config;
                pubblicaTuttoModalConfig.modalComponentConfigPubblica = {
                    ...pubblicaTuttoModalConfig.modalComponentConfigPubblica,
                    data,
                    isPubblicato
                }

                this.modalConfig = {
                    ...this.modalConfig,
                    component: "pubblica-tutto-modal-widget",
                    componentConfig: pubblicaTuttoModalConfig.modalComponentConfigPubblica
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);


            } else*/ if (button.code === 'matrice-atti') {
                let componentConfig = this.config.modalComponentConfigMatrice;
                componentConfig.codGara = this.codGara;

                this.modalConfig = {
                    ...this.modalConfig,
                    component: "matrice-atti-modal-widget",
                    componentConfig: componentConfig,
                    width: 90
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            } else {
                this.provider.run(button.provider, data).subscribe((data: IDictionary<any>) => {
                    if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                        this.reloadGrid();
                    }
                });
            }
        }
    }

    public trackByCode(index: number, item: AttoEntry): string {
        return isObject(item) ? toString(item.id) : toString(index);
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (isObject(item) && isObject(item.data)) {
            if (has(item.data, 'reload') && get(item.data, 'reload') === true) {
                this.reloadGrid();
            }
        }
    }

    public manageItemClick(result: SdkPanelbarOutput): void {
        if (result != null && result.item != null) {
            const action: SdkPanelbarOutputAction = result.action;
            const code: string = result.item.code;
            const parts: Array<string> = split(code, '|');
            const tipologia: string = head(parts);
            const item: AttoEntry = find(this.listaAtti, (one: AttoEntry) => toString(one.id) === tipologia);

            if (action == 'NEW') {
                this.nuovoAtto(tipologia, item);
            } else if (action == 'DELETE') {
                if (size(parts) > 1) {
                    const numPubb: string = parts[1];
                    this.deleteAtto(numPubb);
                } else if (item.statiId && !isEmpty(item.statiId.ids)) {
                    const idPubblicato: IdPubblicati = head(item.statiId.ids);
                    const numPubb: number = idPubblicato.id;
                    this.deleteAtto(toString(numPubb));
                }
            } else if (action == 'OPEN') {
                if (size(parts) > 1) {
                    const numPubb: string = parts[1];
                    this.detailAtto(tipologia, numPubb, item);
                } else if (item.statiId && !isEmpty(item.statiId.ids)) {
                    const idPubblicato: IdPubblicati = head(item.statiId.ids);
                    const numPubb: number = idPubblicato.id;
                    this.detailAtto(tipologia, toString(numPubb), item);
                }
            }

        }
    }

    // #endregion

    // #region Private

    private initModal() {
        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }


    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
    }

    private detailAtto(tipoDocumento: string, numPubb: string, item: AttoEntry): void {
        this.provider.run('APP_GARE_LISTA_ATTI', {
            action: 'DETAIL',
            codGara: this.codGara,
            tipoDocumento,
            numPubb,
            campiObbligatori: item.campiObbligatori,
            campiVisibili: item.campiVisibili,
            messagesPanel: this.messagesPanel,
            attiMap: this.attiMap,
            annullato: item.statiId.ids.find(item => item.id.toString() === numPubb).annullato
        }).subscribe();
    }

    private nuovoAtto(tipoDocumento: string, item: AttoEntry): void {
        this.provider.run('APP_GARE_LISTA_ATTI', {
            action: 'NUOVO',
            codGara: this.codGara,
            tipoDocumento,
            campiObbligatori: item.campiObbligatori,
            campiVisibili: item.campiVisibili,
            messagesPanel: this.messagesPanel,
            attiMap: this.attiMap
        }).subscribe();
    }

    private reloadGrid(): void {
        this.initGrid();
    }

    private initGrid = (): Observable<Array<AttoEntry>> => {
        const factory = this.getListaAttiFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (this.smartCig && this.gara != null && (this.gara.modalitaRealizzazione === 11 || (this.gara.sceltaContraente == 18 || this.gara.sceltaContraente == 31))) {
                if (one.code === 'imprese-partecipanti') {
                    one.title = 'DETTAGLIO-GARA.TABS.IMPRESE-AGGIUDICATARIE';
                }
            }
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, {
                    smartCig: this.smartCig
                });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
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

    private getListaAttiFactory(codGara: string) {
        return () => {
            return this.gareService.getListaAtti(codGara);
        }
    }

    private manageListaAtti = (response: Array<AttoEntry>) => {
        this.tuttiGliAtti = response;
        this.listaAtti = this.tuttiGliAtti;

        this.showButtons();
        this.buildAttiMap();
        let panelbarConfig: SdkPanelbarConfig = this.buildAccordion(this.listaAtti);
        this.listaAttiSubject.next(panelbarConfig);
        this.loadAnagraficaGaraPubblicata().subscribe((pubblicata: boolean) => {
            if (pubblicata) {

                if (this.smartCig === true) {

                    try {
                        document.getElementById('pubblica-smartcig_header').classList.remove('red-highlights');
                        //  document.getElementById('pubblica-smartcig_header').classList.add('p-panelmenu-header-link');
                    } catch (e) {
                    }
                } else {

                    try {
                        document.getElementById('pubblica_header').classList.remove('red-highlights');
                        //   document.getElementById('pubblica_header').classList.add('p-panelmenu-header-link');
                    } catch (e) {
                    }

                }
            }

            else {
                if (this.smartCig === true) {

                    try {
                        document.getElementById('pubblica-smartcig_header').classList.add('red-highlights');
                        //   document.getElementById('pubblica-smartcig_header').classList.remove('p-panelmenu-header-link');
                    } catch (e) {
                    }
                } else {

                    try {
                        document.getElementById('pubblica_header').classList.add('red-highlights');
                        //   document.getElementById('pubblica_header').classList.remove('p-panelmenu-header-link');
                    } catch (e) {
                    }

                }
            }
        });
    }

    private buildAccordion(lista: Array<AttoEntry>): SdkPanelbarConfig {
        let newOutput: Array<SdkButtonGroupSingleInput> = this.protectionUtilsService.checkButtonsProtection([this.config?.body?.nuovoButtonConfig], this.userProfile.configurations);
        let showNuovo: boolean = newOutput && newOutput.length == 1;
        let eliminaOutput: Array<SdkButtonGroupSingleInput> = this.protectionUtilsService.checkButtonsProtection([this.config?.body?.eliminaButtonConfig], this.userProfile.configurations);
        let showElimina: boolean = eliminaOutput && eliminaOutput.length == 1;
        let panelbarConfig: SdkPanelbarConfig = {
            items: [],
            nuovoButton: this.config.body.nuovoButtonConfig,
            eliminaButton: this.config.body.eliminaButtonConfig
        };
        each(lista, (one: AttoEntry) => {
            let item: SdkPanelbarItem = null;
            if (this.garaEntry.readOnly) {
                item = {
                    code: toString(one.id),
                    text: one.nome,
                    children: [],
                    showNuovo: false,
                    showElimina: false
                };
            } else {
                item = {
                    code: toString(one.id),
                    text: one.nome,
                    children: [],
                    showNuovo,
                    showElimina
                };
            }

            if (one.tipo == 'B' && one.statiId != null && one.statiId.ids != null && one.statiId.ids.length >= 1) {
                item.showNuovo = false;
            }

            if (isObject(one.statiId) && !isEmpty(one.statiId.ids)) {
                item.cssClass = 'bold';
                each(one.statiId.ids, (idPubb: IdPubblicati) => {

                    item.icon = 
                        idPubb.annullato 
                            ? 
                                'mgg-icons-close-circle' 
                            :
                                idPubb.dataPubbSistema && idPubb.dataPubbSistema <= new Date() 
                                ? 
                                    'fas fa-flag' 
                                : 
                                    (idPubb.dataPubbSistema && idPubb.dataPubbSistema > new Date() 
                                        ? 
                                            'far fa-flag' 
                                        : 
                                            undefined
                                    );

                    let id: number = idPubb.id;

                    let labelPubblicazione: string;
                    let label: string;
                    if(!isEmpty(idPubb.descrizione)){
                        //Controllo datapubbsistema per comporre la stringa.
                        if(idPubb.dataPubbSistema){
                            label = idPubb.descrizione + this.translateService.instant('LISTA-ATTI-LOTTO.PUBBLICATO-IL') + this.dateHelper.format(idPubb.dataPubbSistema, this.config.locale.dateFormat);
                        } else {
                            //Tolgo la datapubbsistema dalla stringa.    
                            label = idPubb.descrizione
                        }
                    } else {
                        //Inserire la descrizione della tipologia dell'atto.
                        label = one.nome
                    }
                    
                    let child: SdkPanelbarItem = {
                        code: join([toString(one.id), toString(id)], '|'),
                        label: label,
                        labelParams: {
                            value: toString(id)
                        },
                        iconTooltip: idPubb.parziale === true ? idPubb.countParziale + this.translateService.instant('LISTA-ATTI.LOTTI-PARZIALI') + idPubb.totalParziale : '',
                        showElimina: showElimina && (this.userProfile?.abilitazioni?.includes(Constants.ABILITAZIONE_AMMINISTRATORE) || isEmpty(idPubb.dataPubbSistema)),
                        icon: idPubb.parziale === true ? 'mgg-icons-todo-list' : (idPubb.annullato ? 'mgg-icons-close-circle' : (idPubb.dataPubbSistema ? (idPubb.dataPubbSistema < new Date() ? 'fas fa-flag' : 'far fa-flag') : undefined))
                    };
                    item.children.push(child);
                });
            }
            panelbarConfig.items.push(item);
        });
        return panelbarConfig;
    }

    private buildAttiMap(): void {
        let attiMap: IDictionary<string> = reduce(this.listaAtti, (map: IDictionary<string>, one: AttoEntry) => {
            map[one.id] = one.nome;
            return map;
        }, {});
        this.attiMap = attiMap;
    }

    private loadButtons(): void {

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsBack = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsBack, this.userProfile.configurations)
        };

        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };

        this.buttonsMultilotto = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsMultilotto, this.userProfile.configurations)
        };

    }


    private showButtons(): void {
        if (this.garaEntry.readOnly || this.garaEntry.pcp) {
            this.buttonsSubj.next(this.buttonsRO);
        } else {
            if (this.garaEntry.sceltaContraenteValorizzata) {
                if (this.listaAtti != null && this.listaAtti.length > 0) {
                    if (this.garaEntry.numLotti > 1) {
                        this.buttonsSubj.next(this.buttonsMultilotto);
                    } else {
                        this.buttonsSubj.next(this.buttons);
                    }
                } else {
                    this.buttonsSubj.next(this.buttonsBack);
                }
            } else {
                this.buttonsSubj.next(this.buttonsRO);
            }
        }
    }

    private dettaglioGaraSmartCigFactory(codGara: string): () => Observable<SmartCigEntry> {
        return () => {
            return this.smartCigService.dettaglioSmartCig(codGara);
        }
    }

    private loadDettaglio = (): Observable<SmartCigEntry> => {
        if (this.smartCig) {
            const factory = this.dettaglioGaraSmartCigFactory(this.codGara);
            return this.requestHelper.begin(factory, this.messagesPanel);
        } else {
            return of(null);
        }
    }

    private elaborateDettaglio = (gara: SmartCigEntry) => {
        this.gara = gara;
    }

    private checkSmartCigFactory(codGara: string) {
        return () => {
            return this.smartCigService.isGaraSmartCig(codGara);
        }
    }

    private checkSmartCig(): Observable<boolean> {
        const factory = this.checkSmartCigFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateCheckSmartCig = (response: boolean) => {
        this.smartCig = response;
    }

    private deleteAtto(numPubb: string): void {
        this.initDialog();
        this.sdkMessagePanelService.clear(this.messagesPanel);
        let func = this.deleteAttoConfirm(numPubb);
        this.dialogConfig.open.next(func);
    }

    private deleteAttoConfirm(numPubb: string): any {
        return () => {
            this.provider.run('APP_GARE_LISTA_ATTI', {
                buttonCode: 'delete-atto',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                numPubb,
            }).subscribe(this.manageExecutionProvider);
        }
    }

    private manageExecutionProvider = (obj: IDictionary<any>) => {
        if (obj != null) {
            if (obj.reloadGrid == true) {
                delete this.listaAtti;
                delete this.attiMap;
                this.initGrid().pipe(
                    map(this.manageListaAtti)
                ).subscribe();
            }
        }
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    private anagraficaGaraPubblicataFactory(codGara: string): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, this.smartCig + "");
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    // #endregion
}
