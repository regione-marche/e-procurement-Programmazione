import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SdkAppEnvConfig,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    SDK_APP_CONFIG,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, find, get, isEmpty, isEqual, isObject, join, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { Constants } from '../../../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { RupEntry, ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { TabellatiService } from '../../../../services/tabellati/tabellati.service';
import { AbilitazioniUtilsService } from '../../../../services/utils/abilitazioni-utils.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';


@Component({
    templateUrl: `modifica-tecnico-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaTecnicoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-tecnico-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private stazioneAppaltante: string;
    private codice: string;
    private tecnico: RupEntry;
    private userProfile: UserProfile;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private provinceMap: IDictionary<string> = {};

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo.codice;
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
        let factory = this.dettaglioTecnicoFactory(this.codice);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: RupEntry) => {
            this.tecnico = result;
            this.checkInfoBox();
            this.loadForm();
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
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

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

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
                stazioneAppaltante: this.stazioneAppaltante,
                currentCodice: this.codice,
                codice: this.codice
            };

            if (button.code === 'back-to-dettaglio-tecnico' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
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

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'comune') {
                this.manageComune(field);
            } else if (field.code === 'nome' || field.code === 'cognome') {
                this.manageIntestazione(field);
            }
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

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'comune' && field.type === 'AUTOCOMPLETE') {
                let data: string = get(restObject, field.mappingInput);
                if (data != null) {
                    let item: SdkAutocompleteItem = {
                        text: data,
                        _key: data
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            }

            return {
                mapping,
                field
            };
        }

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.tecnico, undefined, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
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
        this.codice = paramsMap.get('codice');
    }

    private dettaglioTecnicoFactory(codice: string): () => Observable<RupEntry> {
        return () => {
            return this.tabellatiService.getTecnico(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codice)
        }
    }

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

    private manageComune(field: SdkFormBuilderField): void {

        let capCode: string = 'cap';
        let provinciaCode: string = 'provincia';
        let istatCode: string = 'cod-istat';

        let cap: string = undefined;
        let codIstat: string = undefined;
        let codIstatProvincia: string = undefined;
        if (field.data) {
            codIstat = field.data.codistat;
            codIstatProvincia = `0${codIstat.substring(3, 6)}`;
            cap = field.data.cap;
        }

        let capField: any = {
            code: capCode,
            data: cap
        };

        let provinciaField: any = {
            code: provinciaCode,
            data: get(this.provinceMap, codIstatProvincia)
        };

        let codIstatField: any = {
            code: istatCode,
            data: codIstat
        };

        if (field.groupCode) {
            capField.groupCode = field.groupCode;
            provinciaField.groupCode = field.groupCode;
            codIstatField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(capField);
        this.formBuilderDataSubject.next(provinciaField);
        this.formBuilderDataSubject.next(codIstatField);
    }

    private manageIntestazione(field: SdkFormBuilderField): void {

        const nomeCode: string = 'nome';
        const cognomeCode: string = 'cognome';
        const intestazioneCode: string = 'intestazione';
        let nomeValue: string = '';
        let cognomeValue: string = '';
        let intestazioneValue: string = '';

        if (field.code === nomeCode) {
            nomeValue = field.data;
            // recupero il valore del cognome
            const cognomeField: SdkFormBuilderField = this.getFieldByCode(cognomeCode);
            cognomeValue = cognomeField.data != null ? cognomeField.data.trim() : '';
        } else if (field.code === cognomeCode) {
            cognomeValue = field.data;
            // recupero il valore del nome
            const nomeField: SdkFormBuilderField = this.getFieldByCode(nomeCode);
            nomeValue = nomeField.data != null ? nomeField.data.trim() : '';
        }

        intestazioneValue = join([cognomeValue, nomeValue], ' ').trim();

        const toUpdate: SdkFormBuilderInput = {
            code: intestazioneCode,
            data: intestazioneValue
        };

        this.formBuilderDataSubject.next(toUpdate);

    }

    private getFieldByCode(fieldCode: string): SdkFormBuilderField {
        const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'rup-data');
        const field: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
        return field;
    }

    // #endregion
}
