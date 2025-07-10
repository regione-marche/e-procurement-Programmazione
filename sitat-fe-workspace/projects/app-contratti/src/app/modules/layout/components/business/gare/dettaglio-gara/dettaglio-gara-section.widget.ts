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
    Tecnico,
    Ufficio,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkProviderService,
    SdkRouterService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkButtonGroupSingleInput,
    SdkDialogConfig,
    SdkDropdownButtonData,
    SdkDropdownButtonInput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, filter, get, has, isEmpty, isObject, isString, remove, set, toString } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { GaraEntry, LottoAccorpabileEntry, LottoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { DettaglioIdGaraStoreService } from '../dettaglio-id-gara-store.service';
import { SdkBaseSectionWidget } from 'projects/sdk-appaltiecontratti-base/src/public-api';
import { SdkC0oggassParamsStoreService } from 'projects/sdk-docassociati/src/lib/sdk-docassociati/components/c0oggass/sdk-c0oggass-params-store.service';


@Component({
    templateUrl: `dettaglio-gara-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioGaraSectionWidget extends SdkBaseSectionWidget implements OnInit, OnDestroy {
    protected getValoriTabellatiConst(): string[] {
        throw new Error('Method not implemented.');
    }

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-gara-generali-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    //public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);

    protected buttonsROPcp: SdkButtonGroupInput;
    protected buttonsRO: SdkButtonGroupInput;
    protected buttonsPcp: SdkButtonGroupInput;
    protected buttonsPcpArchiviata: SdkButtonGroupInput;
    protected buttons: SdkButtonGroupInput;
    protected buttonsArchiviata: SdkButtonGroupInput;
    protected buttonsError: SdkButtonGroupInput;
    protected codGara: string;
    protected stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    protected modalConfig: any;
    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;
    protected userProfile: UserProfile;
    protected gara: GaraEntry;
    protected valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    protected menuTabs: Array<SdkMenuTab>;
    protected sidebarConfig: SdkSidebarConfig;
    protected dialogConfig: SdkDialogConfig;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        super.onInit();
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();

    }

    protected onAfterViewInit(): void {

        this.buttonsSubj.next(this.buttonsError);
        this.loadServices();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            super.onConfig(config);
            this.markForCheck(() => {                
                this.config = { ...config };
                
                if (isObject(get(this.config.body, 'sidebar'))) {
                    this.sidebarConfig = get(this.config.body, 'sidebar');
                    this.sidebarConfig.openSubject = new Subject();
                    this.sidebarConfigObs.next(this.sidebarConfig);
                }
                this.menuTabs = this.config.menuTabs;
                
                this.isReady = true;
            });
            
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    protected get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    //protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    protected get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    protected get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    protected get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    //protected get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    protected get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    protected get gareService(): GareService { return this.injectable(GareService) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    protected get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    protected get translateService(): TranslateService { return this.injectable(TranslateService) }

    protected get dettaglioIdGaraStoreService(): DettaglioIdGaraStoreService { return this.injectable(DettaglioIdGaraStoreService) }

    protected get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }

    protected get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService { return this.injectable(SdkC0oggassParamsStoreService);  }

    // #endregion

    // #region Private

    protected get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    protected dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    protected loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        if(!this.codGara){
            this.codGara = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1;
        }
    }

    protected elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (field.code === 'modalita-realizzazione' && this.gara.versioneSimog === 1) {
                        field.listCode = 'TipoRealizzazioneSimog1';
                    }

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
                    }

                    if (field.code === 'ufficio') {
                        if (restObject.ufficio != null) {
                            const ufficio: Ufficio = restObject.ufficio;
                            set(field, 'data', ufficio.denominazione);
                            mapping = false;
                        }
                    } else if (field.code === 'responsabile') {
                        if (isObject(restObject.tecnico)) {
                            let data: Tecnico = get(restObject, 'tecnico');
                            set(field, 'data', `${data.nominativo} (${data.cf})`);
                            mapping = false;
                        }
                    } else if (field.code === 'drp') {
                        if (isObject(restObject.delegato)) {
                            let data: Tecnico = get(restObject, 'delegato');
                            set(field, 'data', `${data.nominativo} (${data.cf})`);
                            mapping = false;
                        }
                    } else if (field.code === 'data-pubblicazione') {
                        let value = get(restObject, field.mappingInput);
                        if (value != null && field.type === 'TEXT') {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                            key = field.data;
                            mapping = false;
                        }
                    } else if (field.code === 'DATA_GUCE' || field.code === 'DATA_GURI' ||
                        field.code === 'DATA_ALBO' || field.code === 'DATA_BORE') {
                        let value = get(restObject, field.mappingInput);
                        if (value != null) {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                            mapping = false;
                        }
                    } else if (field.code === 'data-lettera-invito') {
                        let value = get(restObject, field.mappingInput);
                        if (value != null && field.type === 'TEXT') {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                            key = field.data;
                            mapping = false;
                        }
                        if (!this.gara.visibleDataLetteraInvito) {
                            field.visible = false;
                            mapping = false;
                        }

                    } else if (field.code === 'data-creazione') {
                        let value = get(restObject, field.mappingInput);
                        if (value != null && field.type === 'TEXT') {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                            key = field.data;
                            mapping = false;
                        }
                    } else if (field.code === 'gara-urgenza' || field.code === 'modalita-indizione-allegato-9') {
                        let value = get(restObject, field.mappingInput);
                        if (value == null) {
                            field.visible = false;
                            mapping = false;
                        }
                    }

                    if (this.gara.pcp && field.code === 'data-creazione') {
                        field.visible = false;
                        mapping = false;
                    }

                    if (this.gara.pcp && field.code === 'data-pubblicazione') {
                        field.visible = false;
                        mapping = false;
                    }

                    if (!this.gara.pcp && field.code === 'drp') {
                        field.visible = false;
                        mapping = false;
                    }

                    if (this.gara.pcp && field.code === 'tipo-settore') {
                        if(isEmpty(restObject.tipoSettore)){
                            field.visible = false;
                            mapping = false;
                        }                        
                    }

                    if (field.code === 'data-scadenza-offerta') {
                        let value = get(restObject, field.mappingInput);
                        if (value != null && field.type === 'TEXT') {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                            key = field.data;
                            mapping = false;
                        }
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
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.gara, null, null, this.config,);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
                this.sdkHttpLoaderService.hideLoader();
            });
        }
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

    protected refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
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

    protected loadGara = (): Observable<GaraEntry> => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel, false);
    }

    protected elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if (this.gara.pcp) {
            this.config.body.fields = filter(this.config.body.fields, (one) => one.code !== 'pubblicita-data');
            this.menuTabs = filter(this.config.menuTabs, (one) => one.code !== 'pubblica');
        } else {
            this.buttonsROPcp.buttons = filter(this.buttonsROPcp.buttons, (one) => one.code !== 'delega');
        }

        this.dettaglioIdGaraStoreService.gara = this.gara;
        //al momento da commentare su indicazione di lelio e riportare come in precedenza (VIGILANZA2-171)
        //if (this.gara.perfezionata) {
        if (this.gara.dataPubblicazione === null && environment.LOGIN_MODE !== "3" && !this.gara.pcp) {
            this.sdkMessagePanelService.showWarning(this.infoBox, [
                {
                    message: 'GARA.GENERAL-DATA.WARNING-NON-PERFEZIONATA'
                }
            ]);
        }
        this.loadAnagraficaGaraPubblicata().subscribe((pubblicata: boolean) => {
            if (pubblicata === false) {
                try {
                    document.getElementById('pubblica_header').classList.add('red-highlights');
                    //   document.getElementById('pubblica_header').classList.remove('p-panelmenu-header-link');
                } catch (e) {
                }
            } else {
                try {
                    document.getElementById('pubblica_header').classList.remove('red-highlights');
                    //  document.getElementById('pubblica_header').classList.add('p-panelmenu-header-link');
                } catch (e) {
                }
            }
        });



        this.refreshBreadcrumbs();
    }

    protected manageExecutionProvider = (obj: any) => {
        if (obj != null) {
            if (isObject(get(this.config.body, 'sidebar')) && obj.sidebar === true) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    protected loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };
        this.buttonsROPcp = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsROPcp, this.userProfile.configurations)
        };
        this.buttonsArchiviata = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsArchiviata, this.userProfile.configurations)
        };
        this.buttonsError = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsError, this.userProfile.configurations)
        }
        this.buttonsPcp = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsPcp, this.userProfile.configurations)
        }
        this.buttonsPcpArchiviata = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsPcpArchiviata, this.userProfile.configurations)
        }
    }

    protected showButtons(): void {
        if (this.gara.readOnly) {
            let buttons: SdkButtonGroupInput = {
                buttons: cloneDeep(this.buttonsRO.buttons)
            };
            this.buttonsSubj.next(buttons);
        } else if (this.gara.pcp && (this.gara.tecnico != null &&
            (this.gara.tecnico?.cf?.toUpperCase() == this.userProfile.codiceFiscale?.toUpperCase() || this.gara.tecnico?.cf?.toUpperCase() == this.userProfile.cfImpersonato?.toUpperCase())) &&
            (this.userProfile.loa != null && (this.userProfile.loa == '3' || this.userProfile.loa == '4' || this.userProfile.loaImpersonato == '3' || this.userProfile.loaImpersonato == '4'))) {

            if (this.gara.situazione == 91) {
                let buttons: SdkButtonGroupInput = {
                    buttons: cloneDeep(this.buttonsPcpArchiviata.buttons)
                };
                super.showButtons(buttons);
                this.buttonsSubj.next(buttons);
            } else {
                let buttons: SdkButtonGroupInput = {
                    buttons: cloneDeep(this.buttonsPcp.buttons)
                };
                super.showButtons(buttons);
                this.buttonsSubj.next(buttons);
            }

        } else if (this.gara.pcp) {
            if ((this.userProfile.loa === null || +this.userProfile.loa < 3) && (this.userProfile.loaImpersonato == null || +this.userProfile.loaImpersonato < 3)) {
                let buttonsWithOutDropdown: any = filter(this.buttonsPcp.buttons, (one) => one.code !== 'altre-azioni');
                let dropdownButtons: any = filter(this.buttonsROPcp.buttons, (one) => one.code === 'altre-azioni');
                if (!isEmpty(dropdownButtons)) {
                    let riallineaButtonRemove = filter(dropdownButtons[0].dropdownData, (one) => one.code !== 'presa-carico');
                    dropdownButtons[0].dropdownData = riallineaButtonRemove;
                    this.buttonsROPcp.buttons = buttonsWithOutDropdown.concat(dropdownButtons);
                }
            }

            if (this.gara.situazione == 91) {
                let buttons: SdkButtonGroupInput = {
                    buttons: cloneDeep(this.buttonsPcpArchiviata.buttons)
                };
                this.buttonsSubj.next(buttons);
            } else {
                let buttons: SdkButtonGroupInput = {
                    buttons: cloneDeep(this.buttonsROPcp.buttons)
                };
                super.showButtons(buttons);
                this.buttonsSubj.next(buttons);
            }
        } else {
            if (this.gara.situazione == 91) {
                let buttons: SdkButtonGroupInput = {
                    buttons: cloneDeep(this.buttonsArchiviata.buttons)
                };
                this.buttonsSubj.next(buttons);
            } else {
                let buttons: SdkButtonGroupInput = {
                    buttons: cloneDeep(this.buttons.buttons)
                };
                super.showButtons(buttons);
                this.buttonsSubj.next(buttons);
            }
            
        }

    }

    protected presaInCaricoPcp(): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        let func = this.presaInCaricoPcpConfirm();
        this.dialogConfig.open.next(func);
    }

    protected presaInCaricoPcpConfirm(): any {
        return () => {
            this.provider.run('APP_GARE_LISTA_GARE', {
                buttonCode: 'presa-carico',
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                codein: this.stazioneAppaltanteInfo.codice,
                cfImpersonato: this.userProfile.cfImpersonato,
                loaImpersonato: this.userProfile.loaImpersonato,
                idpImpersonato: this.userProfile.idpImpersonato,
            }).subscribe(this.manageReload);
        }
    }

    protected initPresaInCaricoPcpDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.PRESA-IN-CARICO-TITLE'),
            message: this.translateService.instant('DIALOG.PRESA-IN-CARICO-PCP-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    protected loadRiallineaDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.RIALLINEA-TITLE'),
            message: this.translateService.instant('DIALOG.RIALLINEA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    protected loadRiallineaAnacDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.RIALLINEA-ANAC-TITLE'),
            message: this.translateService.instant('DIALOG.RIALLINEA-ANAC-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    protected loadArchiviaGaraDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.ARCHIVIA-GARA-TITLE'),
            message: this.translateService.instant('DIALOG.ARCHIVIA-GARA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    protected loadAnnullaArchiviaGaraDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.ANNULLA-ARCHIVIA-GARA-TITLE'),
            message: this.translateService.instant('DIALOG.ANNULLA-ARCHIVIA-GARA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    protected loadEliminaAppaltoSchedaANN(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.ELIMINA-APPALTOP-PCP-TITLE'),
            message: this.translateService.instant('DIALOG.ELIMINA-APPALTOP-PCP-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }


    protected riallineaSimog(button: SdkButtonGroupOutput): void {
        this.sdkMessagePanelService.clear(this.infoBox);
        this.loadRiallineaDialog();
        let func = this.riallineaSimogConfirm(button);
        this.dialogConfig.open.next(func);
    }

    protected riallineaSimogConfirm(button: SdkButtonGroupOutput): any {
        return () => {
            let saInfo: StazioneAppaltanteInfo;
            if (this.stazioneAppaltanteInfo.codice === '*') {
                saInfo = { codice: this.gara.codiceStazioneAppaltante }
            } else {
                saInfo = this.stazioneAppaltanteInfo;
            }
            this.addSubscription(
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    gara: this.gara,
                    stazioneAppaltante: saInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt
                }).subscribe(this.manageReload)
            );
        }
    }


    protected riallineaAnac(button: SdkButtonGroupOutput, cancellaDatiEse: boolean): void {
        this.sdkMessagePanelService.clear(this.infoBox);
        this.loadRiallineaAnacDialog();
        let func = this.riallineaAnacConfirm(button, cancellaDatiEse);
        this.dialogConfig.open.next(func);
    }

    protected riallineaAnacConfirm(button: SdkButtonGroupOutput, cancellaDatiEse: boolean): any {
        return () => {
            let saInfo: StazioneAppaltanteInfo;
            if (this.stazioneAppaltanteInfo.codice === '*') {
                saInfo = { codice: this.gara.codiceStazioneAppaltante }
            } else {
                saInfo = this.stazioneAppaltanteInfo;
            }
            this.addSubscription(
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    codein: saInfo.codFiscale,
                    cfImpersonato: this.userProfile.cfImpersonato,
                    loaImpersonato: this.userProfile.loaImpersonato,
                    idpImpersonato: this.userProfile.idpImpersonato,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    cancellaDatiEse
                }).subscribe(this.manageReload)
            );
        }
    }

    protected manageReload = (result: any) => {
        if (result != null && result.reload === true) {            
            this.formBuilderConfigObs.next({ fields: [] });
            this.buttonsSubj.next({
                buttons: []
            });
            this.loadServices(false);
        } else if(result != null && result.response?.schedaAnn === true && this.gara?.pcp){
            this.eliminaAppaltoPcp();
        }else if(result != null && result.delete === true){
            this.routerService.navigateToPage('lista-gare-page');
        }
    }

    protected eliminaAppaltoPcp(): void {
        this.loadEliminaAppaltoSchedaANN();
        let func = this.eliminaAppaltoPcpConfirm();
        this.dialogConfig.open.next(func);
    }

    protected eliminaAppaltoPcpConfirm(): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_GARE', {
                    buttonCode: 'elimina-appalto-pcp',
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    gara: this.gara,
                    stazioneAppaltante: this.stazioneAppaltanteInfo
                }).subscribe(this.manageReload)
            );
        }
    }

    protected initModal() {
        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }

    protected delegaGara(button: SdkButtonGroupOutput): void {
        let delegaModalConfig = this.config;
        delegaModalConfig.modalComponentConfigDelega = {
            ...delegaModalConfig.modalComponentConfigDelega,
            codGara: this.codGara,
            stazioneAppaltante: this.stazioneAppaltanteInfo,
            gara: this.gara,
            ...this.config
        }
        this.modalConfig = {
            ...this.modalConfig,
            component: "assegna-cambia-delegato-modal-widget",
            componentConfig: delegaModalConfig.modalComponentConfigDelega,
        };
        this.modalConfigObs.next(this.modalConfig);
        this.modalConfig.openSubject.next(true);
    }

    protected riallineaAnacModal(button: SdkButtonGroupOutput): void {
        let riallineaAnacConfig = this.config;
        riallineaAnacConfig.modalComponentConfigRiallineaAnac = {
            ...riallineaAnacConfig.modalComponentConfigRiallineaAnac,
            codGara: this.codGara,
            stazioneAppaltante: this.stazioneAppaltanteInfo,
            gara: this.gara,
            ...this.config
        }
        this.modalConfig = {
            ...this.modalConfig,
            component: "riallinea-anac-modal-widget",
            componentConfig: riallineaAnacConfig.modalComponentConfigRiallineaAnac,
        };
        this.modalConfigObs.next(this.modalConfig);
        this.modalConfig.openSubject.next(true);
    }

    protected archiviaGara(button: SdkButtonGroupOutput): void {
        this.loadArchiviaGaraDialog();
        let func = this.archiviaGaraConfirm(button);
        this.dialogConfig.open.next(func);
    }

    protected archiviaGaraConfirm(button: SdkButtonGroupOutput): any {
        return () => {
            this.addSubscription(
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    gara: this.gara,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt
                }).subscribe(this.manageReload)
            );
        }
    }

    protected annullaArchiviaGara(button: SdkButtonGroupOutput): void {
        this.loadAnnullaArchiviaGaraDialog();
        let func = this.annullaArchiviaGaraConfirm(button);
        this.dialogConfig.open.next(func);
    }

    protected annullaArchiviaGaraConfirm(button: SdkButtonGroupOutput): any {
        return () => {
            this.addSubscription(
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    gara: this.gara,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt
                }).subscribe(this.manageReload)
            );
        }
    }

    protected loadTabellati(): Observable<IDictionary<Array<ValoreTabellato>>> {
        return this.tabellatiCacheService.getValoriTabellati(Constants.DETTAGLIO_GARA_TABELLATI);
    }

    protected elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
    }

    protected loadServices(initModal: boolean = true): void {
        this.loadTabellati()
            .pipe(
                map(this.elaborateTabellati),
                mergeMap(this.loadGara),
                map(this.elaborateGara),
                map(() => {                    
                    this.checkInfoBox();
                    this.refreshTabs();
                    this.elaborateConfig();
                    this.showButtons();
                    if (initModal === true) {
                        this.initModal();
                    }
                }),
                catchError(this.handleError)
            ).subscribe();
    }

    protected handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsError);
        this.sdkHttpLoaderService.hideLoader();
        return throwError(() => err);
    }

    protected anagraficaGaraPubblicataFactory(codGara: string): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, 'false');
        }
    }

    protected loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel, false);
    }

    // #endregion

    // #region Public

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'responsabile') {
                this.modalConfig.componentConfig = {
                    gara: this.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.gara.tecnico
                }
            } else if (config.code === 'codice-centro-costo' || config.code === 'denominazione-centro-costo') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    centroDiCosto: this.gara.centroDicosto
                }
            } else if (config.code === 'ufficio') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.gara.ufficio
                };
            } else if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.gara.codiceStazioneAppaltante
                }
            } else if (config.code === 'drp') {
                this.modalConfig.componentConfig = {
                    gara: this.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.gara.delegato
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            if (button.code === 'riallinea-simog') {
                this.riallineaSimog(button);
            } else if (button.code === 'archiviazione-gara') {
                this.archiviaGara(button);
            } else if (button.code === 'annulla-archiviazione-gara') {
                this.annullaArchiviaGara(button);
            } else if (button.code === 'delega') {
                this.delegaGara(button);
            } else if (button.code === 'presa-carico') {
                this.initPresaInCaricoPcpDialog();
                this.presaInCaricoPcp();
            } else if (button.code === 'riallinea-anac') {
                if (this.gara.existsFasiNonPubb === false) {
                    this.riallineaAnac(button, true);
                } else {
                    this.riallineaAnacModal(button);
                    //apro modale con scelta
                }

            } else {
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    codGara: this.codGara,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    gara: this.gara,
                    dialogConfigSubj: this.dialogConfigSubj,
                    entita: this.config.body.codiceEntitaModelli,
                    breadcrumbs: this.config.breadcrumbs,
                    identita: this.gara ? [{ nome: 'codgara', valore: this.gara.codgara }] : [],
                }).subscribe(this.manageExecutionProvider);
            }
        }
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'modal') {
            if (isObject(_item.data)) {
                if (get(_item.data, 'code') === 'cdc') {
                    this.gara.centroDicosto = get(_item.data, 'data');
                    set(this.gara.centroDicosto, 'text', undefined);
                } else if (get(_item.data, 'reload') === true) {
                    this.loadServices(false);
                } else if (get(_item.data, 'close') === true && has(_item.data, 'option')) {
                    this.loadServices(false);                    
                }  else if (get(_item.data, 'riallinea') === true && has(_item.data, 'option')) {
                    let saInfo: StazioneAppaltanteInfo;
                    if (this.stazioneAppaltanteInfo.codice === '*') {
                        saInfo = { codice: this.gara.codiceStazioneAppaltante }
                    } else {
                        saInfo = this.stazioneAppaltanteInfo;
                    }
                    this.addSubscription(
                        this.provider.run('APP_GARE_LISTA_GARE', {
                            buttonCode: 'riallinea-anac',
                            messagesPanel: this.messagesPanel,
                            codGara: this.codGara,
                            codein: saInfo.codFiscale,
                            cfImpersonato: this.userProfile.cfImpersonato,
                            loaImpersonato: this.userProfile.loaImpersonato,
                            idpImpersonato: this.userProfile.idpImpersonato,
                            chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                            cancellaDatiEse: get(_item.data, 'option') === '2' ? true : false
                        }).subscribe(this.manageReload)
                    );                    
                } else {
                    this.gara.tecnico = _item.data;
                    this.loadServices(false);
                }
            }
        }
    }

    // #endregion
}