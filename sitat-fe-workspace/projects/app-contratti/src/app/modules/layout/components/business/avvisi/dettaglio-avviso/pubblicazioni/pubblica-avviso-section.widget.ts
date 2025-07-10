import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
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
import { get, has, isArray, isEmpty, isObject, reduce, remove } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../../app.constants';
import { AvvisoEntry } from '../../../../../../models/avviso/avviso.model';
import { FlussiAvviso } from '../../../../../../models/pubblicazioni/pubblicazione-avviso-model';
import { AvvisiService } from '../../../../../../services/avvisi/avvisi.service';
import { DettaglioAvvisoStoreService } from '../dettaglio-avviso-store.service';

@Component({
    templateUrl: `pubblica-avviso-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class PubblicaAvvisoSectionWidget extends SdkBusinessAbstractWidget<void> {

    // #region Variables

    @HostBinding('class')
    public classNames = `pubblica-avviso-section`;

    @ViewChild('messages')
    public _messagesPanel: ElementRef;

    @ViewChild('infoBox')
    public _infoBox: ElementRef;

    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();

    public pubblicazioni: Array<FlussiAvviso>;

    public config: any = {};

    private buttons: SdkButtonGroupInput;
    private buttonsNoPubb: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;

    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    protected userProfile: UserProfile;

    private modalConfig: SdkModalConfig<any, void, any>;

    private menuTabs: Array<SdkMenuTab>;

    private sidebarConfig: SdkSidebarConfig;

    private idAvviso: string;

    private avviso: AvvisoEntry;

    private stazioneAppaltante: string;

    private cfStazioneAppaltante: string;

    private appCode: string;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    public onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.initDialog();

        this.appCode = this.sdkSessionStorageService.getItem(Constants.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.refreshTabs();
        const queryParams: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idAvviso = queryParams.get('idAvviso');
        this.stazioneAppaltante = queryParams.get('stazioneAppaltante');
        let getSaInfo = this.getSAInfoFactory(this.stazioneAppaltante);
        this.requestHelper.begin(getSaInfo, this.messagesPanel).subscribe((result:StazioneAppaltanteInfo)=>{
            this.cfStazioneAppaltante = result.codFiscale;
            this.loadData();
        });
    }

    public onDestroy(): void {
        // Do nothing
    }

    protected onConfig(config: any): void {
        if (isObject(config)) {
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

                this.isReady = true
            })
        }
    }

    protected onUpdateState(): void {
        // Do nothing
    }

    // #endregion

    // #region Public

    public onFlussoClick(flusso: FlussiAvviso, index: number): void {
        if (isObject(flusso)) {
            const dettaglioModal = this.config.dettaglioFlussoAvviso;
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

    // #region Getters

    private get activatedRoute(): ActivatedRoute {
        return this.injectable(ActivatedRoute);
    }

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    protected get provider(): SdkProviderService {
        return this.injectable(SdkProviderService);
    }

    protected get store(): SdkStoreService {
        return this.injectable(SdkStoreService);
    }

    private get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
    }

    private get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get dettaglioAvvisoStoreService(): DettaglioAvvisoStoreService {
        return this.injectable(DettaglioAvvisoStoreService);
    }

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get avvisiService(): AvvisiService {
        return this.injectable(AvvisiService);
    }

    private get userService(): UserService { return this.injectable(UserService) }

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #region Getters

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: any = {
                code: button.code,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                messagesPanel: this.messagesPanel,
                idAvviso: this.idAvviso,
                avviso: this.avviso,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                codiceFiscaleStazioneAppaltante: this.cfStazioneAppaltante
            }

            if(button.code === 'elimina-flussi-avviso'){
                this.sdkMessagePanelService.clear(this.messagesPanel);
                let func = this.deleteFlussoConfirm(data);
                this.dialogConfig.open.next(func);
            } else{
                this.addSubscription(
                    this.provider
                        .run(button.provider, data)
                        .subscribe(this.manageExecutionProvider)
                );
            }
            
        }
    }

    // #region Private

    private deleteFlussoConfirm(data: any): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_AVVISI_DETTAGLIO_AVVISO', {
                    buttonCode: 'elimina-flussi-avviso',                  
                   ...data
                }).subscribe(this.manageExecutionProvider)
            );
        }
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

    private getSAInfoFactory(codiceSA: string) {
        return () => {
            return this.userService.getSAInfo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceSA, this.appCode, this.userProfile.syscon)
        }
    }

    private getDettaglioAvvisoFactory() {
        return () => {
            return this.avvisiService.dettaglioAvviso(this.idAvviso, this.stazioneAppaltante);
        }
    }

    private loadData(): void {
        const dettaglioAvvisoFactory = this.getDettaglioAvvisoFactory();
        this.requestHelper
            .begin(dettaglioAvvisoFactory, this.messagesPanel)
            .subscribe((response) => {
                this.markForCheck(() => {
                    this.avviso = response;
                    this.pubblicazioni = this.avviso.pubblicazioni;
                    if(this.pubblicazioni != null && this.pubblicazioni.length > 0){
                        this.buttonsSubj.next(this.buttons);            
                    }else{
                        this.buttonsSubj.next(this.buttonsNoPubb);
                    }
                })
            });
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

    private refreshTabs(): void {
        if (isArray(this.menuTabs)) {
            remove(this.menuTabs, (one: SdkMenuTab) => {
                if (!isEmpty(one.oggettoProtezione)) {
                    const visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                    if (visible !== true) {
                        return true;
                    }
                }
                if (!isEmpty(one.visible)) {
                    const visible: boolean = this.provider.run(one.visible, { avviso: this.avviso });
                    return visible === false;
                }
                return false;
            });
            this.sdkLayoutMenuTabs.emit(this.menuTabs);
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if(this.pubblicazioni != null && this.pubblicazioni.length > 0){
            this.buttonsSubj.next(this.buttons);            
        }else{
            this.buttonsSubj.next(this.buttonsNoPubb);
        }
        if (obj && has(obj, 'reload') && obj.reload) {
            this.loadData();
        } else if (obj && isObject(get(this.config.body, 'sidebar'))) {
            this.sidebarConfig.componentConfig = {

                ...this.sidebarConfig.componentConfig,
                ...obj
            };
            this.sidebarConfigObs.next(this.sidebarConfig);
            this.sidebarConfig.openSubject.next(true);
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
        this.buttonsNoPubb = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsNoPubb, this.userProfile.configurations)
        };        
        let protezioniMap = this.getProtezioniMap();
        let key:string = 'FUNZ.VIS.ALT.W9.DEL.ENTITA_PER_AMMINISTRATORE';
        if (!has(protezioniMap, key)) {
            this.buttons.buttons = remove(this.buttons.buttons, (one: SdkBasicButtonInput) =>
                one.code !== 'elimina-flussi-gara'
            );
        }
        this.buttonsSubj = new BehaviorSubject(this.buttonsNoPubb);
       
    }

    // #endregion
}
