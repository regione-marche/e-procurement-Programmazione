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
import { HttpRequestHelper, ProtectionUtilsService, StazioneAppaltanteInfo, TabellatiCacheService, UserService, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary, SdkBreadcrumbsMessageService, SdkBusinessAbstractWidget, SdkProviderService, SdkSessionStorageService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkMessagePanelConfig,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkSidebarConfig,
} from '@maggioli/sdk-controls';
import { each, forEach, get, isEmpty, isEqual, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { FlussiFase, TipoInvio } from '../../../../../../modules/models/pubblicazioni/pubblicazione-fase.model';
import { GaraEntry, LottoEntry } from '../../../../../models/gare/gare.model';
import { PubblicazioneFaseLottoProviderArgs } from '../../../../../providers/gare/pubblicazione-fase-lotto.provider';
import { GareService } from '../../../../../services/gare/gare.service';
import { PubblicazioneFaseService } from '../../../../../services/pubblicazioni/pubblicazione-fase.service';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { TranslateService } from '@ngx-translate/core';

@Component({
    templateUrl: `pubblica-fase-lotto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PubblicaFaseLottoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class')
    public classNames = `pubblica-gara-section`;

    @ViewChild('messages')
    public _messagesPanel: ElementRef;

    @ViewChild('infoBox')
    public _infoBox: ElementRef;

    public config: any = {};

    private buttons: SdkButtonGroupInput;
    private buttonsLocked: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public pubblicazioni: Array<FlussiFase>;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private modalConfig: SdkModalConfig<any, void, any>;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private sidebarConfig: SdkSidebarConfig;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private userProfile: UserProfile;
    private gara: GaraEntry;
    private lotto: LottoEntry;
    private anagraficaGaraPubblicata: boolean;
    private appCode: string;
    private cfStazioneAppaltante: string;
    protected valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private loa : string;
    private fromLS: string;
    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onConfig(config: any): void {
        if (isObject(config)) {
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

                this.isReady = true;
            })
        }
    }

    protected onUpdateState(state: boolean): void {
        // Do nothing
    }

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            this.loa = this.userProfile.loa            
        }));
        this.appCode = this.sdkSessionStorageService.getItem(Constants.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
        const paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.fromLS = paramsMap.get('fromLS');

        this.loadButtons();
    }

    protected onDestroy(): void {
        // Do nothing
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
                map(this.elaborateTabellati),
                mergeMap(this.loadGara),            
                map(this.elaborateGara),
                map(this.loadLottoFactory),
                mergeMap(this.loadLotto),
                map(this.elaborateLotto),
                mergeMap(this.loadAnagraficaGaraPubblicata),
                map(this.elaborateAnagraficaGaraPubblicata),
                map(this.loadSAInfoFactory),
                mergeMap(this.loadSa),
                map(this.elaborateSa),
                map(() => this.checkInfoBox()),
                mergeMap(this.getListaPubblicazioni),
                map(this.elaborateListaPubblicazioni),
                map(this.elaborateButtons),
                map(() => this.refreshBreadcrumb()),
                catchError(this.handleError)
            ).subscribe();
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codFase: this.codiceFase,
                codLotto: this.codLotto,
                pcp: this.gara.pcp,
                numeroProgressivo: this.numeroProgressivo,
                cfStazioneAppaltante: this.cfStazioneAppaltante,
                stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                tipoInvio: this.pubblicazioni.length === 0 ? TipoInvio.PRIMA_PUBBLICAZIONE : TipoInvio.RETIFFICHE_AGGIORNAMENTO,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                syscon: this.userProfile.syscon,
                autore: this.userProfile.nome+' '+this.userProfile.cognome,                
                codProfilo: this.userProfile.configurations?.idProfilo,
                loa: this.loa,
                cfImpersonato: this.userProfile.cfImpersonato,
                loaImpersonato: this.userProfile.loaImpersonato,
                idpImpersonato: this.userProfile.idpImpersonato,
                fromLS: this.fromLS ? this.fromLS : null
            } as PubblicazioneFaseLottoProviderArgs).subscribe(this.manageExecutionProvider);
        }
    }


    

    public onFlussoClick(flusso: FlussiFase, index: number): void {
        if (isObject(flusso)) {
            const dettaglioModal = this.config.dettaglioFlussoFase;
            this.modalConfig = {
                ...this.modalConfig,
                component: dettaglioModal.modalComponent,
                componentConfig: {
                    ...dettaglioModal.modalComponentConfig,
                    locale: this.config.locale,
                    dettaglio: {
                        ...flusso,
                        tipoInvio: this.getTipoPubblicazione(flusso)
                    }
                }
            };

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public getTipoPubblicazione(pubb: any): string {
        let descrizione = '';
        let tabellati: Array<ValoreTabellato> = get(this.valoriTabellati, 'TipoInvio');            
        tabellati.forEach(tabellato => {
            if (tabellato.codice === pubb.tipoInvio) {
                descrizione = tabellato.descrizione;
            }
        });
            
        
        return descrizione;
    }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute); }

    private get infoBox(): HTMLElement { return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined; }

    private get messagesPanel(): HTMLElement { return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined; }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService); }

    private get pubblicazioneFaseService(): PubblicazioneFaseService { return this.injectable(PubblicazioneFaseService); }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper); }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService); }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService); }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get userService(): UserService { return this.injectable(UserService) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Private

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.PUBBLICA_FASE_LOTTO_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private listaPubblicazioniFactory(codGara, codiceFase, codLotto, numeroProgressivo): () => Observable<Array<FlussiFase>> {
        return () => {
            return this.pubblicazioneFaseService.getListaPubblicazioniFase(codGara, codiceFase, codLotto, numeroProgressivo);
        }
    }

    private getListaPubblicazioni = () => {
        const factory = this.listaPubblicazioniFactory(this.codGara, this.codiceFase, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaPubblicazioni = (result: Array<FlussiFase>) => {
        this.markForCheck(() => {
            this.pubblicazioni = result;  
            if(this.gara.pcp){
                let schedaConfermata: boolean = false;
                for (const flusso of this.pubblicazioni) {
                    if (flusso.tipoInvio === "3") {
                        schedaConfermata = true;                    
                    }
                }
                if(schedaConfermata == false){
                    const messageConfig: SdkMessagePanelConfig = {
                        messages: [
                            {
                                message: 'PUBBLICAZIONI.WARNING'
                            }
                        ],
                        type: 'warning',
                        list: false
                    };
                    this.sdkMessagePanelService.show(this.messagesPanel, [messageConfig]);                    
                } else{
                    this.sdkMessagePanelService.clear(this.messagesPanel);
                }
            }                      
        });
    }

    private elaborateListaPubbl = (result: Array<FlussiFase>) => {
        this.markForCheck(() => {
            this.pubblicazioni = result;                                   
        });
    }

    private manageExecutionProvider = (obj: any): void => {
        if (isObject(obj)) {
            if (get(obj, 'reload') === true) {
                this.sidebarConfig.openSubject.next(false);
                this.getListaPubblicazioni()
                    .pipe(
                        map(this.elaborateListaPubbl),
                        map(this.elaborateButtons)
                    ).subscribe();
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

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private loadLottoFactory = (): Function => {
        let factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return factory;
    }

    private loadLotto = (factory: Function): Observable<LottoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
    };

    private dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsLocked = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLocked, this.userProfile.configurations)
        };

        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };

        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadGara = (): Observable<GaraEntry> => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
    }

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

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsLocked);
        return throwError(() => err);
    }

    private elaborateButtons = () => {      
        if(this.fromLS) {
            this.buttonsSubj.next(this.buttonsLS);
        } 
        else {
            if(this.gara.readOnly){
                this.buttonsSubj.next(this.buttonsRO);
            } else{  
                let schedaConfermata: boolean = false;
                for (const flusso of this.pubblicazioni) {
                    if (flusso.tipoInvio === "3") {
                        schedaConfermata = true;                    
                    }
                }
                if(this.gara.pcp && schedaConfermata){
                    this.buttonsSubj.next(this.buttonsRO);
                } else{
                    if (isObject(this.userProfile) && this.userProfile.ruolo !== 'A') {
                        if (isObject(this.gara) && !this.gara.pcp){
                            if(isObject(this.gara.tecnico) && this.gara.tecnico?.cf?.toUpperCase() !== this.userProfile.codiceFiscale?.toUpperCase()) {
                                const filteredButtons = this.buttons.buttons.filter(function (obj) {
                                    return obj.code !== 'pubblica-fase';
                                });
                                this.buttonsSubj.next({
                                    buttons: filteredButtons
                                });
                            } else{
                                this.buttonsSubj.next(this.buttons);
                            }
                        } else if (isObject(this.gara) && this.gara.pcp){
                            if((isObject(this.gara.tecnico) && this.gara.tecnico?.cf?.toUpperCase() !== this.userProfile.codiceFiscale?.toUpperCase()) && (isObject(this.gara.delegato) && this.gara.delegato.cf?.toUpperCase() !== this.userProfile.codiceFiscale?.toUpperCase())) {
                                const filteredButtons = this.buttons.buttons.filter(function (obj) {
                                    return obj.code !== 'pubblica-fase';
                                });
                                this.buttonsSubj.next({
                                    buttons: filteredButtons
                                });
                            } else{
                                this.buttonsSubj.next(this.buttons);
                            }
                        }                    
                    } else {
                        this.buttonsSubj.next(this.buttons);
                    }
                }            
            }
        }
    }    
    
    private loadSAInfoFactory = () => {
        return () => {
            return this.userService.getSAInfo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, this.gara.codiceStazioneAppaltante, this.appCode, this.userProfile.syscon)
        }
    }

    private elaborateSa = (result: StazioneAppaltanteInfo) => {
        this.cfStazioneAppaltante = result.codFiscale;
    };

    private loadSa = (factory: Function): Observable<StazioneAppaltanteInfo> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
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