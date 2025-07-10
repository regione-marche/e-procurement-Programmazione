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
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    RupEntry,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    Ufficio,
    ValoreTabellato,
} from '@maggioli/app-commons';
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
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, find, get, has, isEmpty, isObject, isString, remove, set, sum, toString } from 'lodash-es';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { SmartCigEntry } from '../../../../../models/smartcig/smartcig.model';
import { SmartCigService } from '../../../../../services/smartcig/smartcig.service';
import { DettaglioIdGaraStoreService } from '../../gare/dettaglio-id-gara-store.service';


@Component({
    templateUrl: `dettaglio-smartcig-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioSmartCigSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-smartcig-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private codGara: string;
    private gara: SmartCigEntry;
    private buttonsArchiviata: SdkButtonGroupInput;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private sidebarConfig: SdkSidebarConfig;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private comuniMap: IDictionary<ValoreTabellato> = {};
    private provinceMap: IDictionary<ValoreTabellato> = {};
    private menuTabs: Array<SdkMenuTab>;
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
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

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();

    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            mergeMap(this.loadDettaglio),
            map(this.elaborateDettaglio),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm()),
            map(() => this.showButtons()),
            map(() => this.checkPubblicazioni())
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
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioIdGaraStoreService(): DettaglioIdGaraStoreService { return this.injectable(DettaglioIdGaraStoreService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            if (button.code === 'archiviazione-gara') {
                this.archiviaGara(button);
            } else if (button.code === 'annulla-archiviazione-gara') {
                this.annullaArchiviaGara(button);
            } else {
                let data: IDictionary<any> = {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    setUpdateState: this.setUpdateState,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    codGara: this.codGara
                };
                this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            }
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = {
                    ...this.sidebarConfig.componentConfig,
                    ...obj
                };
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }



    private checkPubblicazioni(): void {
        this.loadAnagraficaGaraPubblicata().subscribe((pubblicata: boolean) => {
            if (pubblicata === false) {
                try {
                    document.getElementById('pubblicazioni_header').classList.add('red-highlights');
                    //  document.getElementById('pubblicazioni_header').classList.remove('p-panelmenu-header-link');
                } catch (e) {
                }
            } else {
                try {
                    document.getElementById('pubblicazioni_header').classList.remove('red-highlights');
                    //   document.getElementById('pubblicazioni_header').classList.add('p-panelmenu-header-link');
                } catch (e) {
                }
            }

        });

        if (this.gara != null && this.gara.daExport === 3) {
            this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                {
                    message: 'SMARTCIG.GENERAL-DATA.SCHEDA-NON-INVIATA'
                }
            ]);
        }
    }


    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'importo-lotto' && field.type === 'NUMERIC-TEXTBOX') {
                this.calcoloImportoTotale(field, 'dati-economici-data', 'importo-attuazione-sicurezza', 'importo-totale');
            } else if (field.code === 'importo-attuazione-sicurezza' && field.type === 'NUMERIC-TEXTBOX') {
                this.calcoloImportoTotale(field, 'dati-economici-data', 'importo-lotto', 'importo-totale');
            }
        }
    }

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
                    selectedItem: this.gara.rup
                };
            } else if (config.code === 'ufficio') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.gara.ufficio
                };
            } else if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.gara.stazioneAppaltante
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (isObject(item) && item.code === 'modal') {
            if (isObject(item.data)) {
                this.gara.rup = item.data;
                this.loadGara();
            }
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

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = toString(keyAny);

            if (
                field.code === 'data-ultimazione' ||
                field.code === 'data-verbale'
            ) {
                let value = get(restObject, field.mappingInput);
                if (value != null && field.type === 'TEXT') {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    key = field.data;
                    mapping = false;
                }
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
                if (restObject.rup != null) {
                    let data: RupEntry = get(restObject, 'rup');
                    set(field, 'data', `${data.nominativo} (${data.cf})`);
                    mapping = false;
                }
            } else if (field.code === 'luogo-istat') {
                if (has(this.comuniMap, key)) {
                    const istatComune: string = key;
                    const istatProvincia: string = `0${istatComune.substring(3, 6)}`;
                    field.data = `${this.comuniMap[istatComune].descrizione} - ${this.provinceMap[istatProvincia].descrizione} (${istatComune})`
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

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.gara, {
            stazioneAppaltante: this.stazioneAppaltanteInfo,
            provinceMap: this.provinceMap
        }, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);

        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
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
        return this.tabellatiCacheService.getValoriTabellati(Constants.SMARTCIG_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadComuni = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI);
    }

    private elaborateComuni = (comuni: Array<ValoreTabellato>) => {
        each(comuni, (comune: ValoreTabellato) => {
            this.comuniMap[comune.codistat] = comune;
        });
    }

    private loadProvince = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE);
    }

    private elaborateProvince = (province: Array<ValoreTabellato>) => {
        each(province, (provincia: ValoreTabellato) => {
            this.provinceMap[provincia.codistat] = provincia;
        });
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<SmartCigEntry> {
        return () => {
            return this.smartCigService.dettaglioSmartCig(codGara);
        }
    }

    private loadDettaglio = (): Observable<SmartCigEntry> => {
        const factory = this.dettaglioGaraFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDettaglio = (gara: SmartCigEntry) => {
        this.gara = gara;
        this.dettaglioIdGaraStoreService.gara = gara;
        this.refreshBreadcrumbs();
        this.refreshTabs();
    }

    private anagraficaGaraPubblicataFactory(codGara: string): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, 'true');
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }


    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsArchiviata = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsArchiviata, this.userProfile.configurations)
        };

        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };

    }

    private calcoloImportoTotale(field: SdkFormBuilderField, sectionCode: string, otherFieldCode: string, sumFieldCode: string): void {
        let currentValue: number = field.data;
        let section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === sectionCode);
        let otherField: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === otherFieldCode);
        let otherValue: number = isObject(otherField) && otherField.data ? otherField.data : 0;
        let finalValue: number = sum([currentValue, otherValue]);
        this.formBuilderDataSubject.next({
            code: sumFieldCode,
            data: finalValue
        });
    }

    private loadGara(): void {
        let factory = this.dettaglioGaraFactory(this.codGara);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: SmartCigEntry) => {
            this.gara = result;
            this.checkInfoBox();
            this.loadForm();
            this.showButtons();
        });
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(this.gara.smartCig) && (this.gara.modalitaRealizzazione === 11 || (this.gara.sceltaContraente == 18 || this.gara.sceltaContraente == 31))) {
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
                let visible: boolean = this.provider.run(one.visible, { smartCig: true });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);

    }

    private archiviaGara(button: SdkButtonGroupOutput): void {
        this.loadArchiviaGaraDialog();
        let func = this.archiviaGaraConfirm(button);
        this.dialogConfig.open.next(func);
    }

    private archiviaGaraConfirm(button: SdkButtonGroupOutput): any {
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

    private loadArchiviaGaraDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.ARCHIVIA-GARA-TITLE'),
            message: this.translateService.instant('DIALOG.ARCHIVIA-GARA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    private loadAnnullaArchiviaGaraDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.ANNULLA-ARCHIVIA-GARA-TITLE'),
            message: this.translateService.instant('DIALOG.ANNULLA-ARCHIVIA-GARA-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    private annullaArchiviaGara(button: SdkButtonGroupOutput): void {
        this.loadAnnullaArchiviaGaraDialog();
        let func = this.annullaArchiviaGaraConfirm(button);
        this.dialogConfig.open.next(func);
    }

    private annullaArchiviaGaraConfirm(button: SdkButtonGroupOutput): any {
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

    private manageReload = (result: any) => {
        if (result != null && result.reload === true) {
            this.loadGara();
        }
    }

    private showButtons(): void {
        if (this.gara.readOnly) {
            this.buttonsSubj.next(this.buttonsRO);
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
                this.buttonsSubj.next(buttons);
            }
        }


    }

    // #endregion
}
