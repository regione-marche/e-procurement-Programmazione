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
import { HttpRequestHelper, ProfiloConfigurationItem, ProtectionUtilsService, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
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
import { get, has, isEmpty, isObject, reduce, remove, split, trim } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { DettaglioAttoEntry, GaraEntry } from '../../../../../models/gare/gare.model';
import { FlussiAtto } from '../../../../../models/pubblicazioni/pubblicazione-atto.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { PubblicazioneAttoService } from '../../../../../services/pubblicazioni/pubblicazione-atto.service';


@Component({
    templateUrl: `pubblica-atto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PubblicaAttoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `pubblica-atto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private modalConfig: SdkModalConfig<any, void, any>;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private codGara: string;
    private codLotto: string;
    private tipoDocumento: string;
    private numPubb: string;
    private campiVisibili: Array<string>;
    private campiVisibiliString: string;
    private campiObbligatori: Array<string>;
    private campiObbligatoriString: string;
    private menuTabs: Array<SdkMenuTab>;
    public pubblicazioni: Array<FlussiAtto>;
    private buttons: SdkButtonGroupInput;
    private defaultButtons: SdkButtonGroupInput;
    private buttonsNoPubb: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private sidebarConfig: SdkSidebarConfig;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private userProfile: UserProfile;
    private atto: DettaglioAttoEntry;
    private anagraficaGaraPubblicata: boolean;
    private gara: GaraEntry;
    private checkSmartCig: boolean;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public showHelpBox: boolean = false;
    public collapse: boolean = false;
    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            this.checkSmartCig = this.protectionUtilsService.isMenuTabVisible('W9.SMARTCIG-scheda.INVII', this.userProfile.configurations);
        }));

        this.initDialog();
        this.loadButtons();
        this.loadQueryParams();

    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadAnagraficaGaraPubblicata()
            .pipe(
                map(this.elaborateAnagraficaGaraPubblicata),
                mergeMap(this.loadGara),
                map(this.elaborateGara),
                mergeMap(this.loadAtto),
                map(this.elaborateAtto),
                map(() => this.refreshTabs()),
                mergeMap(this.getListaPubblicazioni),
                map(this.elaborateListaPubblicazioni),
                map(() => this.loadFinalButtons()),
            ).subscribe();
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

                let showHelpBox = get(this.config, 'showHelpBox');
                if (showHelpBox != null && showHelpBox != undefined && showHelpBox === true) {
                    this.showHelpBox = true;
                }
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

    private get pubblicazioneAttoService(): PubblicazioneAttoService { return this.injectable(PubblicazioneAttoService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            if (button.code === 'elimina-flussi-atto') {
                this.sdkMessagePanelService.clear(this.messagesPanel);
                let func = this.deleteFlussoConfirm();
                this.dialogConfig.open.next(func);
            } else {
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    numPubb: this.numPubb,
                    tipoDocumento: this.tipoDocumento,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    isSmartCig: this.gara.smartCig === true
                }).subscribe(this.manageExecutionProvider);
            }
        }
    }

    private deleteFlussoConfirm(): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_ATTI', {
                    buttonCode: 'elimina-flussi-atto',
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    numPubb: this.numPubb,
                    tipoDocumento: this.tipoDocumento,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    isSmartCig: this.gara.smartCig === true
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }

    public onFlussoClick(flusso: FlussiAtto, index: number): void {
        if (isObject(flusso)) {
            const dettaglioModal = this.config.dettaglioFlussoAtto;
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

    public getTipoPubblicazione(index: number): string {
        return index === 0 ? 'PUBBLICAZIONI.PRIMA-PUBBLICAZIONE-IN-DATA' : 'PUBBLICAZIONI.RETTIFICA-PUBBLICAZIONE-IN-DATA';
    }

    // #endregion

    // #region Private

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

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');

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


    private getListaPubblicazioni = () => {
        let factory = this.listaPubblicazioniFactory(this.codGara, this.numPubb);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaPubblicazioni = (result: Array<FlussiAtto>) => {
        this.markForCheck(() => this.pubblicazioni = result);
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

    private listaPubblicazioniFactory(codGara: string, num: string): () => Observable<Array<FlussiAtto>> {
        return () => {
            return this.pubblicazioneAttoService.getListaPubblicazioniAtto(codGara, num);
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'reload') === true) {
                this.sidebarConfig.openSubject.next(false);
                this.getListaPubblicazioni()
                    .pipe(
                        map(this.elaborateListaPubblicazioni)
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
        this.defaultButtons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.defaultButtons, this.userProfile.configurations)
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

    private loadGara = (): Observable<GaraEntry> => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private dettaglioAttoFactory(codGara: string, tipoDocumento: string, numPubb: string): () => Observable<DettaglioAttoEntry> {
        return () => {
            return this.gareService.dettaglioAtto(codGara, tipoDocumento, numPubb);
        }
    }

    private loadAtto = () => {
        let factory = this.dettaglioAttoFactory(this.codGara, this.tipoDocumento, this.numPubb);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateAtto = (atto: DettaglioAttoEntry) => {
        this.atto = atto;
    }

    private loadFinalButtons(): void {
        if (this.gara.readOnly) {
            this.buttonsSubj.next(this.buttonsRO);
        } else {
            if (this.pubblicazioni != null && this.pubblicazioni.length > 0) {
                this.buttonsSubj.next(this.buttons);
            } else {
                this.buttonsSubj.next(this.buttonsNoPubb);
            }
        }
    }

    public collassaEspandi(): void {
        this.collapse = !this.collapse;
    }

    // #endregion

}
