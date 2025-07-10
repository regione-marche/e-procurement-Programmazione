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
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, isString, remove, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { InterventoEntry } from '../../../../../models/interventi/interventi.model';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { DettaglioInterventoStore } from '../dettaglio-intervento-store.service';
import { InterventoService } from '../intervento.service';


@Component({
    templateUrl: `dett-intervento-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioInterventoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-intervento-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private comuniMap: IDictionary<string> = {};
    private provinceMap: IDictionary<string> = {};
    private idProgramma: string;
    private tipologia: string;
    private idIntervento: string;
    private intervento: InterventoEntry;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);

    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private programma: ProgrammaEntry;
    private userProfile: UserProfile;
    private menuTabs: Array<SdkMenuTab>;
    private risorseDiCapitoloProtectionCode: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.setUpdateState(false);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI).subscribe((comuni: Array<ValoreTabellato>) => {
            each(comuni, (comune: ValoreTabellato) => {
                this.comuniMap[comune.codistat] = comune.descrizione;
            });
        });

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.descrizione;
            });
        });

        this.loadButtons();

        this.tabellatiCacheService.getValoriTabellati(Constants.INTERVENTO_TABELLATI).subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
            this.valoriTabellati = result;
            this.loadQueryParams();
            this.refreshTabs();
            this.checkInfoBox();

            this.markForCheck(() => this.isReady = true);

            let factory = this.dettaglioProgrammaFactory(this.idProgramma);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
                this.programma = result;
                let factory2 = this.dettaglioInterventoFactory(this.idProgramma, this.idIntervento);
                this.requestHelper.begin(factory2, this.messagesPanel).subscribe((result: InterventoEntry) => {
                    this.intervento = result;
                    this.elaborateConfig();
                });
            });
        });
    }

    protected onAfterViewInit(): void { }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.risorseDiCapitoloProtectionCode = this.config.risorseDiCapitoloProtectionCode;
                // this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService); }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get dettaglioInterventoStore(): DettaglioInterventoStore { return this.injectable(DettaglioInterventoStore) }

    private get interventoService(): InterventoService { return this.injectable(InterventoService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                tipologia: this.tipologia,
                idIntervento: this.idIntervento
            }).subscribe();
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private dettaglioInterventoFactory(idProgramma: string, idIntervento: string): () => Observable<InterventoEntry> {
        return () => {
            return this.programmiService.dettaglioIntervento(idProgramma, idIntervento);
        }
    }

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
        this.idIntervento = paramsMap.get('idIntervento');
        this.dettaglioInterventoStore.idProgramma = this.idProgramma;
        this.dettaglioInterventoStore.tipologia = this.tipologia;
        this.dettaglioInterventoStore.idIntervento = this.idIntervento;
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
                let visible: boolean = this.provider.run(one.visible, { tipologia: this.tipologia });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {

                this.initButtons();

                let fields: Array<SdkFormBuilderField>;
                if (this.tipologia === '1') {
                    fields = get(this.config, 'body.lavoriFields');
                } else if (this.tipologia === '2') {
                    if(this.programma.norma === 3){
                        fields = get(this.config, 'body.acquistiFieldsNorma3')
                    } else{
                        fields = get(this.config, 'body.acquistiFields')
                    }
                }

                let formConfig: SdkFormBuilderConfiguration = {
                    fields
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (field.code === 'provincia') {
                        if (has(restObject, 'codIstatComune') && !isEmpty(get(restObject, 'codIstatComune'))) {
                            let codIstatProvincia = '0' + restObject.codIstatComune.substring(3, 6);
                            field.data = this.provinceMap[codIstatProvincia];
                        }
                        mapping = false;
                    } else if (field.code === 'comune') {
                        if (has(restObject, 'codIstatComune') && !isEmpty(get(restObject, 'codIstatComune'))) {
                            field.data = this.comuniMap[restObject.codIstatComune];
                        }
                        mapping = false;
                    } else if (field.code === 'provincia-immobile') {
                        if (has(restObject, 'codIstat') && !isEmpty(get(restObject, 'codIstat'))) {
                            let codIstatProvincia = '0' + restObject.codIstat.substring(3, 6);
                            field.data = this.provinceMap[codIstatProvincia];
                        }
                        mapping = false;
                    } else if (field.code === 'comune-immobile') {
                        if (has(restObject, 'codIstat') && !isEmpty(get(restObject, 'codIstat'))) {
                            field.data = this.comuniMap[restObject.codIstat];
                        }
                        mapping = false;
                    } else if (field.code === 'scadenza-utilizzo-finanziamento-mutuo') {
                        let value = get(this.intervento, field.mappingInput);
                        if (value != null) {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                            mapping = false;
                        }
                    } else if (field.code === 'intervento-variato-modifica-programma') {
                        // prima modifica
                        if (this.programma.idProgramma != null && this.programma.idProgramma.endsWith('001')) {
                            field.visible = false;
                        }
                    }


                    if (field.code === 'codice-rup') {
                        let data: Tecnico = get(this.intervento, 'rupEntry');
                        if (isObject(data)) {
                            set(field, 'data', `${data.nominativo} (${data.cf})`);
                            mapping = false;
                        }
                    }

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping, this.programma.annoInizio);
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    return {
                        mapping,
                        field
                    };
                }

                let providerArgs: IDictionary<any> = {
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    tipologia: this.tipologia
                };

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.intervento, providerArgs);
                formConfig = this.interventoService.updateProtectionsForRisorseDiCapitolo(formConfig, this.userProfile.configurations, this.risorseDiCapitoloProtectionCode);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);

                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
            });
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
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
    }

    private initButtons(): void {
        if (this.programma.idRicevuto) {
            this.buttonsSubj.next(this.buttonsReadonly);
        } else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    // #endregion
}
