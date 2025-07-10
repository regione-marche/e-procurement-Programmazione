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
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboBoxItem,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, isEmpty, isEqual, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { GaraEntry, UffEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';


@Component({
    templateUrl: `modifica-gara-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaGaraSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-gara-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private codGara: string;
    private gara: GaraEntry;
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private infoBoxModalConfig: IDictionary<any>;
    private provinceMap: IDictionary<string> = {};

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.codice;
            });
        });

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                currentCodice: this.codGara,
                codGara: this.codGara
            };

            if (button.code === 'back-to-dettaglio-gara' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'comune-sede-gara') {
                this.manageComune(field);
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
            if (config.code === 'ufficio') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    selectedItem: this.gara.ufficio
                };
            } else if (config.code === 'stazione-appaltante') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    stazioneAppaltanteInfo: this.gara.codiceStazioneAppaltante
                }
            } else {
                this.modalConfig.componentConfig = {
                    ...config.modalComponentConfig,
                    centroDiCosto: this.gara.centroDicosto
                };
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, 'component'),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, 'fields'),
                    buttons: get(this.infoBoxModalConfig, 'buttons'),
                    userProfile: this.userProfile,
                    mnemonico: item.mnemonico
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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

            if (field.code === 'responsabile') {
                if (isObject(restObject.tecnico)) {
                    let data: Tecnico = get(restObject, field.mappingInput);
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${data.nominativo} (${data.cf})`,
                        _key: data.codice
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            } else if (field.code === 'drp') {
                if (isObject(restObject.delegato)) {
                    let data: Tecnico = get(restObject, field.mappingInput);
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${data.nominativo} (${data.cf})`,
                        _key: data.codice
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            } else if (field.code === 'responsabile-readonly') {
                let data: Tecnico = get(restObject, field.mappingInput);
                if (data != null) {
                    set(field, 'data', `${data.nominativo} (${data.cf})`);
                    mapping = false;
                }
            } else if (field.type === 'AUTOCOMPLETE' && field.code =='ufficio') {
                let data: UffEntry = get(this.gara, 'ufficio');
                if (data) {
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${data.denominazione}`,
                        _key: data.id+''
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            } else if (field.code.startsWith('data-') && field.type === 'TEXT') {
                const value = get(restObject, field.mappingInput);
                if (value != null) {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    key = field.data;
                    mapping = false;
                }
            } else if(field.code === 'gara-urgenza' || field.code === 'modalita-indizione-allegato-9'){
                let value = get(restObject, field.mappingInput);
                if(value == null){
                    field.visible = false;
                    mapping = false;
                }
            }

            if (field.code === 'data-lettera-invito') {
                
                if(!this.gara.visibleDataLetteraInvito){
                    field.visible = false;
                    mapping = false;
                }      

                if(!this.gara.sceltaContra4){
                    field.type = 'TEXT';
                    mapping = false;
                }

                const value = get(restObject, field.mappingInput);
                if (value != null) {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    key = field.data;
                    mapping = false;
                }
                                 
            }

            if (field.code === 'comune-sede-gara' && field.type === 'AUTOCOMPLETE') {
                let item: SdkAutocompleteItem = {
                    text: restObject.comuneSede,
                    _key: restObject.comuneSede
                };
                set(field, 'data', item);
                mapping = false;
            }

            if (field.code === 'modalita-realizzazione' && this.gara.versioneSimog === 1) {
                field.listCode = 'TipoRealizzazioneSimog1';
            }

            if (field.code === 'flag-sa-agente' && this.gara.versioneSimog === 5 && get(restObject, field.mappingInput) == null) {
                let comboItem: SdkComboBoxItem = {
                    key: "2",
                    value: 'No'
                }
                set(field, 'data', comboItem);
                mapping = false;
            }

            if(this.gara.pcp && field.code === 'data-creazione'){
                field.visible = false;
                mapping = false;
            }

            if(this.gara.pcp && field.code === 'data-pubblicazione'){
                field.visible = false;
                mapping = false;
            }
            
            if(this.gara.pcp && field.code === 'drp'){
                field.visible = false;
                mapping = false;
            }


            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
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

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction,
            {
                ...this.gara,
                ruoloUtente: this.userProfile.ruolo
            },
            {
                stazioneAppaltante: { codice:this.gara.codiceStazioneAppaltante}
            },
            infoBox);

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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private manageComune(field: SdkFormBuilderField): void {

        let provinciaCode: string = 'provincia-sede-gara';

        let codIstat: string = undefined;
        let codIstatProvincia: string = undefined;
        if (field.data) {
            codIstat = field.data.codistat;
            codIstatProvincia = `0${codIstat.substring(3, 6)}`;
        }

        let provinciaField: any = {
            code: provinciaCode,
            data: get(this.provinceMap, codIstatProvincia)
        };

        if (field.groupCode) {
            provinciaField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(provinciaField);
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.DETTAGLIO_GARA_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private loadGara = (factory: Function): Observable<GaraEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        this.checkInfoBox();
        this.loadForm();
    };

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
