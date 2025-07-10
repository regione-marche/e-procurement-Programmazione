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
import { get, has, isEmpty, isFunction, isObject, reduce, remove } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { FlussiProgrammi } from '../../../../../models/pubblicazione/pubblicazione.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';

@Component({
    templateUrl: `dett-prog-pubblica-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioProgrammaPubblicaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dett-prog-pubblica-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private idProgramma: string;
    private tipologia: string;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsOI: Array<SdkMenuTab>;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    public pubblicazioni: Array<FlussiProgrammi>;
    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private readOnlyButtonsNoDelete: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private sidebarConfig: SdkSidebarConfig;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private userProfile: UserProfile;
    private programma: ProgrammaEntry;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public tipologiaInstallazione: string;

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

        this.initDialog();
        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadProgramma(() => {
            this.checkInfoBox();
            this.getTipologiaInstallazione();
            this.refreshTabs();
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.menuTabsOI = this.config.menuTabsOI;
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

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            if(button.code === 'elimina-flussi-programma'){
                this.sdkMessagePanelService.clear(this.messagesPanel);
                let func = this.deleteFlussoConfirm();
                this.dialogConfig.open.next(func);
            } else{
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    idProgramma: this.idProgramma,
                    tipologia: this.tipologia,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    tipologiaInstallazione: this.tipologiaInstallazione
                }).subscribe(this.manageExecutionProvider);
            }
            
        }
    }

    private deleteFlussoConfirm(): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_PROGRAMMI_DETTAGLIO_PROGRAMMA', {
                    buttonCode: 'elimina-flussi-programma',                  
                    messagesPanel: this.messagesPanel,
                    idProgramma: this.idProgramma,
                    tipologia: this.tipologia,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }

    public onFlussoClick(flusso: FlussiProgrammi, index: number): void {
        if (isObject(flusso)) {
            const dettaglioModal = this.config.dettaglioFlussoProgramma;
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

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
    }

    private refreshTabs(): void {
        if(this.programma.idProgramma.startsWith("OI")){
            this.menuTabs = this.menuTabsOI;
        }

        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, { tipologia: this.tipologia });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }


    private getListaPubblicazioni() {
        let factory = this.listaPubblicazioniFactory(this.idProgramma, this.stazioneAppaltanteInfo.codice);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<FlussiProgrammi>) => {
            this.markForCheck(() => {
                this.pubblicazioni = result;

                this.initButtons();
            });
        });
    }

    private listaPubblicazioniFactory(idProgramma: string, stazioneAppaltante: string): () => Observable<Array<FlussiProgrammi>> {
        return () => {
            return this.programmiService.getPubblicazioni(idProgramma, stazioneAppaltante);
        }
    }

    private getTipologiaInstallazione() {
        let factory = this.getTipologiaInstallazioneFactory();
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: string) => {
           this.tipologiaInstallazione = result
           this.getListaPubblicazioni();
        });
    }

    private getTipologiaInstallazioneFactory(): () => Observable<any> {
        return () => {
            return this.programmiService.getTipologiaInstallazione();
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'reload') === true) {
                this.sidebarConfig.openSubject.next(false);
                this.loadProgramma(() => {
                    this.getListaPubblicazioni();
                });
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

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadProgramma(callback?: Function): void {
        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
            this.programma = result;
            if (isFunction(callback)) {
                callback();
            }
        });
    }

    private initButtons(): void {
        if (this.programma.idRicevuto) {
            if(this.tipologiaInstallazione === '1'){                
                this.buttonsSubj.next(this.readOnlyButtonsNoDelete);
            } else{
                this.buttonsSubj.next(this.buttonsReadonly);
            }
            
        } else {
            this.buttonsSubj.next(this.buttons);
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
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
        this.readOnlyButtonsNoDelete = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
        let protezioniMap = this.getProtezioniMap();
        let key:string = 'FUNZ.VIS.ALT.W9.DEL.ENTITA_PER_AMMINISTRATORE';
        if (!has(protezioniMap, key)) {
            this.buttons.buttons = remove(this.buttons.buttons, (one: SdkBasicButtonInput) =>
                one.code !== 'elimina-flussi-gara'
            );
        }
    }

    // #endregion

}
