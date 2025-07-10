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
import { HttpRequestHelper, ProfiloConfigurationItem, ProtectionUtilsService, StazioneAppaltanteInfo, UserService } from '@maggioli/app-commons';
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkSessionStorageService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkSidebarConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get, has, isEmpty, isObject, reduce, remove } from 'lodash-es';
import { GaraEntry } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';
import { SmartCigEntry } from 'projects/app-contratti/src/app/modules/models/smartcig/smartcig.model';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { SmartCigService } from 'projects/app-contratti/src/app/modules/services/smartcig/smartcig.service';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { FlussiGara } from '../../../../../models/pubblicazioni/pubblicazione-gara.model';
import { PubblicazioneGaraService } from '../../../../../services/pubblicazioni/pubblicazione-gara.service';

@Component({
    templateUrl: `pubblica-gara-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PubblicaGaraSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `pubblica-gara-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public pubblicazioni: Array<FlussiGara>;
    private buttons: SdkButtonGroupInput;
    private buttonsNoPubb: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private modalConfig: SdkModalConfig<any, void, any>;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private codGara: string;
    private menuTabs: Array<SdkMenuTab>;
    private sidebarConfig: SdkSidebarConfig;
    private userProfile: UserProfile;
    private gara: GaraEntry;
    private cfStazioneAppaltante: string;
    private appCode: string;
    private smartCig: SmartCigEntry;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.appCode = this.sdkSessionStorageService.getItem(Constants.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);



        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();



    }

    private dettaglioSmartCigFactory(codGara: string): () => Observable<SmartCigEntry> {
        return () => {
            return this.smartCigService.dettaglioSmartCig(codGara);
        }
    }


    private anagraficaGaraPubblicataFactory(codGara: string, smartCig: boolean): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, smartCig + "");
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara, this.gara.smartCig);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }


    protected onAfterViewInit(): void {
        let factory = this.loadGaraFactory();
        this.loadGara(factory).subscribe((result: GaraEntry) => {
            this.gara = result;
            this.showButtons();
            let getSaInfo = this.getSAInfoFactory(this.gara.codiceStazioneAppaltante);
            this.requestHelper.begin(getSaInfo, this.messagesPanel).subscribe((result: StazioneAppaltanteInfo) => {
                this.cfStazioneAppaltante = result.codFiscale;
                this.loadAnagraficaGaraPubblicata().subscribe((pubblicata: boolean) => {
                    if (this.gara.smartCig) {
                        if (pubblicata === false) {
                            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                                {
                                    message: 'PUBBLICAZIONI.ANAGRAFICA-GARA-DA-PUBBLICARE'
                                }
                            ]);
                            try {
                                document.getElementById('pubblicazioni_header').classList.add('red-highlights');
                                //   document.getElementById('pubblicazioni_header').classList.remove('p-panelmenu-header-link');
                            } catch (e) {
                            }
                        } else {
                            try {
                                document.getElementById('pubblicazioni_header').classList.remove('red-highlights');
                                //  document.getElementById('pubblicazioni_header').classList.add('p-panelmenu-header-link');
                            } catch (e) {
                            }

                        }
                    } else {
                        if (pubblicata === false) {
                            this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                                {
                                    message: 'PUBBLICAZIONI.ANAGRAFICA-GARA-DA-PUBBLICARE'
                                }
                            ]);
                            try {
                                document.getElementById('pubblica_header').classList.add('red-highlights');
                                document.getElementById('pubblica_header').classList.remove('p-panelmenu-header-link');
                            } catch (e) {
                            }
                        } else {
                            try {
                                document.getElementById('pubblica_header').classList.remove('red-highlights');
                                document.getElementById('pubblica_header').classList.add('p-panelmenu-header-link');
                            } catch (e) {
                            }
                        }
                    }
                    if (this.gara.smartCig) {
                        this.requestHelper.begin(this.dettaglioSmartCigFactory(this.codGara), this.messagesPanel).subscribe((res: SmartCigEntry) => {
                            this.smartCig = res;
                            this.refreshTabs();
                            this.checkInfoBox();
                            this.getListaPubblicazioni();
                        });
                    } else {
                        this.refreshTabs();
                        this.checkInfoBox();
                        this.getListaPubblicazioni();
                    }

                });
            });
        });

    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
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

                this.isReady = true;
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get userService(): UserService { return this.injectable(UserService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    private getProtezioniMap(): IDictionary<ProfiloConfigurationItem> {
        let protectionMap: IDictionary<ProfiloConfigurationItem> = reduce(this.userProfile.configurations.configurazioni, (map: IDictionary<any>, one: ProfiloConfigurationItem) => {
            map[one.key] = one;
            return map;
        }, {});
        return protectionMap;
    }

    private getSAInfoFactory(codiceSA: string) {
        return () => {
            return this.userService.getSAInfo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceSA, this.appCode, this.userProfile.syscon)
        }
    }

    public getTipoPubblicazione(index: number): string {
        return index === 0 ? 'PUBBLICAZIONI.PRIMA-PUBBLICAZIONE-IN-DATA' : 'PUBBLICAZIONI.RETTIFICA-PUBBLICAZIONE-IN-DATA';
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-FLUSSO-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-FLUSSO-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    public onFlussoClick(flusso: FlussiGara, index: number): void {
        if (isObject(flusso)) {
            const dettaglioModal = this.config.dettaglioFlussoGara;
            this.modalConfig = {
                ...this.modalConfig,
                component: dettaglioModal.modalComponent,
                componentConfig: {
                    ...dettaglioModal.modalComponentConfig,
                    locale: this.config.locale,
                    dettaglio: {
                        ...flusso,
                        tipoInvio: index === 0 ? 'PUBBLICAZIONI.PRIMA-PUBBLICAZIONE' : 'PUBBLICAZIONI.RETTIFICA-PUBBLICAZIONE'
                    }
                }
            };

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            if (button.code === 'elimina-flussi-gara') {
                this.sdkMessagePanelService.clear(this.messagesPanel);
                let func = this.deleteFlussoConfirm();
                this.dialogConfig.open.next(func);
            } else {
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    cfStazioneAppaltante: this.cfStazioneAppaltante,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    smartCig: this.gara.smartCig
                }).subscribe(this.manageExecutionProvider);
            }

        }
    }

    private deleteFlussoConfirm(): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_PUBBLICA', {
                    buttonCode: 'elimina-flussi-gara',
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    cfStazioneAppaltante: this.cfStazioneAppaltante,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    smartCig: this.gara.smartCig
                }).subscribe(this.manageExecutionProvider)
            );
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
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(this.smartCig) && (this.smartCig.modalitaRealizzazione === 11 || (this.smartCig.sceltaContraente == 18 || this.smartCig.sceltaContraente == 31))) {
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
                let visible: boolean = this.provider.run(one.visible);
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }


    private getListaPubblicazioni() {
        let factory = this.listaPubblicazioniFactory(this.codGara);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<FlussiGara>) => {
            this.markForCheck(() => {
                this.pubblicazioni = result;
                if (this.gara.readOnly) {
                    this.buttonsSubj.next(this.buttonsRO);
                } else {
                    if (this.pubblicazioni != null && this.pubblicazioni.length > 0) {
                        this.buttonsSubj.next(this.buttons);
                    } else {
                        this.buttonsSubj.next(this.buttonsNoPubb);
                    }
                }
            });
        });
    }

    private listaPubblicazioniFactory(codGara: string): () => Observable<Array<FlussiGara>> {
        return () => {
            return this.pubblicazioneGaraService.getListaPubblicazioniGara(codGara);
        }
    }

    private manageExecutionProvider = (obj: any) => {

        if (isObject(obj)) {
            if (get(obj, 'reload') === true) {
                this.sidebarConfig.openSubject.next(false);
                this.getListaPubblicazioni();
                if (this.gara.readOnly) {
                    this.buttonsSubj.next(this.buttonsRO);
                } else {
                    if (this.pubblicazioni != null && this.pubblicazioni.length > 0) {
                        this.buttonsSubj.next(this.buttons);
                    } else {
                        this.buttonsSubj.next(this.buttonsNoPubb);
                    }
                }
            } else if (isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = {
                    ...this.sidebarConfig.componentConfig,
                    ...obj
                };
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
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
        this.buttonsNoPubb = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsNoPubb, this.userProfile.configurations)
        };
        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };

        let protezioniMap = this.getProtezioniMap();
        let key: string = 'FUNZ.VIS.ALT.W9.DEL.ENTITA_PER_AMMINISTRATORE';
        if (!has(protezioniMap, key)) {
            this.buttons.buttons = remove(this.buttons.buttons, (one: SdkBasicButtonInput) =>
                one.code !== 'elimina-flussi-gara'
            );
        }
    }

    private showButtons(): void {
        if (this.gara.readOnly) {
            this.buttonsSubj.next(this.buttonsRO);
        } else {
            this.buttonsSubj.next(this.buttons);
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

    // #endregion

}
