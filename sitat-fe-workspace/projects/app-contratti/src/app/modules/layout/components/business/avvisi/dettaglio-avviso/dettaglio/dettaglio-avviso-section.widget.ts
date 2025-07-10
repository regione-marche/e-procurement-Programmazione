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
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
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
    SdkDocumentItem,
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
import { cloneDeep, each, get, isArray, isEmpty, isObject, isString, map, remove, set, toString } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../../../../../../app.constants';
import { AvvisoEntry, ExistingAvvisoDocument } from '../../../../../../models/avviso/avviso.model';
import { AvvisiService } from '../../../../../../services/avvisi/avvisi.service';
import { DettaglioAvvisoStoreService } from '../dettaglio-avviso-store.service';

@Component({
    templateUrl: `dettaglio-avviso-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioAvvisoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-avviso-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    private buttons: SdkButtonGroupInput;

    private buttonsPubblicato: SdkButtonGroupInput;

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;

    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();

    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    private stazioneAppaltante: string;

    private idAvviso: string;

    private avviso: AvvisoEntry;

    private tipiAvvisoMap: IDictionary<string> = {};

    private menuTabs: Array<SdkMenuTab>;

    private sidebarConfig: SdkSidebarConfig;

    private userProfile: UserProfile;

    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;

    private formBuilderConfig: SdkFormBuilderConfiguration;

    private modalConfig: any;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #endregion

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_AVVISO).subscribe((tipiAvviso: Array<ValoreTabellato>) => {
            each(tipiAvviso, (tipoAvviso: ValoreTabellato) => {
                this.tipiAvvisoMap[tipoAvviso.codice] = tipoAvviso.descrizione;
            });
        });

        this.dettaglioAvvisoStoreService.clearIdAvviso();

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.refreshTabs();
        let queryParams: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idAvviso = queryParams.get('idAvviso');
        this.stazioneAppaltante = queryParams.get('stazioneAppaltante');
        this.dettaglioAvvisoStoreService.idAvviso = this.idAvviso;
        this.dettaglioAvvisoStoreService.stazioneAppaltante = this.stazioneAppaltante;
        this.loadData();
        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }

    protected onDestroy(): void { }

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
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService); }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute); };

    private get store(): SdkStoreService { return this.injectable(SdkStoreService); }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper); }

    private get avvisiService(): AvvisiService { return this.injectable(AvvisiService); }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService); }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService); }

    private get dettaglioAvvisoStoreService(): DettaglioAvvisoStoreService { return this.injectable(DettaglioAvvisoStoreService); }

    private get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
    }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService); }

    protected get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper); }


    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.addSubscription(
                this.provider.run(button.provider, {
                    code: button.code,
                    avviso: this.avviso,
                    syscon: this.userProfile.syscon,
                    messagesPanel: this.messagesPanel,
                    formBuilderConfig: this.formBuilderConfig,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    codiceFiscaleStazioneAppaltante: this.stazioneAppaltanteInfo.codFiscale,
                    stazioneAppaltante: this.avviso.stazioneAppaltante,
                    buttonCode: button.code,
                    idGenerato: this.avviso.idRicevuto
                }).subscribe(this.manageExecutionProvider)
            );
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'rup') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.avviso.rupEntry
                }
            } else if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.avviso.stazioneAppaltante
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item.data != null) {
            this.loadData();
        }
    }

    // #endregion

    // #region Private

    private loadData(): void {
        const dettaglioAvvisoFactory = this.getDettaglioAvvisoFactory();
        this.requestHelper
            .begin(dettaglioAvvisoFactory, this.messagesPanel)
            .subscribe((response) => this.loadForm(response));
    }

    private loadForm(response: AvvisoEntry): void {
        this.avviso = response;
        if(this.avviso.pubblicazioni != null && this.avviso.pubblicazioni.length > 0){
            this.buttonsSubj.next(this.buttonsPubblicato);
        }
        const fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));
        let formConfig: SdkFormBuilderConfiguration = this.protectionUtilsService.applyFormProtections({ fields }, this.userProfile.configurations);

        if (isObject(this.avviso)) {
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, this.customPopulateFunction, this.avviso);
        } else {
            formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, this.customPopulateFunction);
        }

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private getDettaglioAvvisoFactory() {
        return () => {
            return this.avvisiService.dettaglioAvviso(this.idAvviso, this.stazioneAppaltante);
        }
    }

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: AvvisoEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);
        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(Date.parse(value)), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        } else if (field.code === 'stazione-appaltante' && field.type === 'TEXT') {
            if (get(restObject, field.mappingInput)) {
                field.data = this.avviso.nominativoStazioneAppaltante;
                mapping = false;
            }
        } else if (field.code === 'tipologia-avviso' && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if (value) {
                field.data = this.tipiAvvisoMap[value];
                mapping = false;
            }
        } else if (field.type === 'DOCUMENTS-LIST') {
            let existingDocuments: Array<ExistingAvvisoDocument> = get(this.avviso, field.mappingInput);
            let sdkExistingDocuments: Array<SdkDocumentItem> = map(existingDocuments, (one: ExistingAvvisoDocument) => {
                let docItem: SdkDocumentItem = {
                    ...one,
                    code: toString(one.numDoc)
                };

                docItem.fileDownloadCallback = () => {
                    const factory = this.downloadDocumentoAvvisoFactory(this.avviso.numeroAvviso, this.avviso.stazioneAppaltante, one.numDoc);
                    return this.requestHelper.begin(factory, this.messagesPanel);
                };

                return docItem;
            });
            set(field, 'documents', sdkExistingDocuments);
            mapping = false;
        }

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'reload') === true) {
                this.sidebarConfig.openSubject.next(false);
                this.loadData();
            } else if (isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsPubblicato = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsPubblicato, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private downloadDocumentoAvvisoFactory(idAvviso: number, codein: string, numDoc: number) {
        return () => {
            return this.avvisiService.downloadDocumentoAvviso(idAvviso, codein, numDoc);
        }
    }

    // #endregion
}
