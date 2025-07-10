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
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
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
import { cloneDeep, get, isEmpty, isObject, isString, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, of, throwError } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ProgrammaBaseEntry, ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { catchError, map, mergeMap } from 'rxjs/operators';


@Component({
    templateUrl: `dett-prog-dati-generali-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioProgrammaDatiGeneraliSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class')
    public classNames = `dett-prog-dati-generali-section`;

    @ViewChild('messages')
    public _messagesPanel: ElementRef;

    @ViewChild('infoBox')
    public _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private soloOpere: boolean = false;
    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private idProgramma: string;
    private tipologia: string;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsOI: Array<SdkMenuTab>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private modalConfig: SdkModalConfig<any, void, any>;
    private programma: ProgrammaEntry;
    private userProfile: UserProfile;
    private sidebarConfig: SdkSidebarConfig;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private programmiDaConfronto: Array<ProgrammaBaseEntry>;
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

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati()
            .pipe(
                map(this.elaborateTabellati),
                mergeMap(this.loadProgramma),
                map(this.elaborateProgramma),
                mergeMap(this.loadListaProgrammiDaConfronto),
                map(this.elaborateListaProgrammiDaConfronto),
                mergeMap(this.getTipologiaInstallazione),
                map(this.elaborateTipoInstallazione),
                map(() => this.initButtons()),
                map(() => this.elaborateConfig()),
                map(() => this.refreshTabs()),
                map(() => this.checkInfoBox()),
                catchError(this.handleError)
            ).subscribe();
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
                this.menuTabs = this.config.menuTabs;
                this.menuTabsOI = this.config.menuTabsOI;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
    }

    private initButtons(): void {

        if (this.programmiDaConfronto == null || (this.programmiDaConfronto != null && this.programmiDaConfronto.length == 0)) {
            remove(this.buttonsReadonly.buttons, (one: SdkBasicButtonInput) => one.code === 'confronto-programmi');
            remove(this.buttons.buttons, (one: SdkBasicButtonInput) => one.code === 'confronto-programmi');
        }

        if (this.programma.idRicevuto) {
            this.buttonsSubj.next(this.buttonsReadonly);
        } else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {

                let fieldsLabel = 'body.lavoriFields';
                if (toString(this.programma.tipoProg) === '2') {
                    fieldsLabel = 'body.fornitureFields';
                }

                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, fieldsLabel)
                };
                if (this.programma.idProgramma.startsWith("OI")) {
                    formConfig.fields[1].visible = false;
                    formConfig.fields[2].visible = false;
                    this.soloOpere = true;
                }
                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
                    }

                    if (field.code === 'tipo-programma') {
                        if (toString(this.programma.tipoProg) === '1') {
                            field.data = this.translateService.instant('PROGRAMMA.LAVORI');
                        } else {
                            field.data = this.translateService.instant('PROGRAMMA.FORNITURE');
                        }
                        mapping = false;
                    } else if (field.code === 'stazione-appaltante') {
                        field.data = this.stazioneAppaltanteInfo.nome;
                        mapping = false;
                    } else if (field.code === 'ufficio') {
                        if (isObject(this.programma.ufficio)) {
                            let ufficio: any = <any>this.programma.ufficio;
                            field.data = ufficio.denominazione;
                        }
                        field.visible = this.programma.showUfficio;
                        mapping = false;
                    } else if (field.code === 'responsabile') {
                        if (isObject(this.programma.rup)) {
                            field.data = this.programma.rup.nominativo;
                            mapping = false;
                        }
                    } else if (field.code === 'data-pubblicazione-adozione' || field.code === 'data-adozione' ||
                        field.code === 'data-pubblicazione-approvazione' || field.code === 'data-approvazione') {
                        let value = get(this.programma, field.mappingInput);
                        if (value != null) {
                            field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
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
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.programma);

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

    private refreshTabs(): void {
        if (this.soloOpere) {
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

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
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

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.DETT_PROG);
    }

    private elaborateTabellati = (tabellati: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = tabellati;
    }

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadProgramma = (): Observable<ProgrammaEntry> => {
        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateProgramma = (programma: ProgrammaEntry) => {
        this.programma = programma;
    }

    private listaProgrammiDaConfrontoFactory(idProgramma: string): () => Observable<Array<ProgrammaBaseEntry>> {
        return () => {
            return this.programmiService.getListaProgrammiDaConfronto(idProgramma);
        }
    }

    private loadListaProgrammiDaConfronto = (): Observable<Array<ProgrammaBaseEntry>> => {
        if (this.programma.showConfrontoProgrammi == true) {
            let factory = this.listaProgrammiDaConfrontoFactory(this.idProgramma);
            return this.requestHelper.begin(factory, this.messagesPanel);
        }
        return of([])
    }

    private elaborateListaProgrammiDaConfronto = (programmiDaConfronto: Array<ProgrammaBaseEntry>) => {
        this.programmiDaConfronto = programmiDaConfronto;        
    }

    private getTipologiaInstallazione = (): Observable<any> =>{
        let factory = this.getTipologiaInstallazioneFactory();
        return this.requestHelper.begin(factory, this.messagesPanel)
    }

    private elaborateTipoInstallazione = (tipologiaInstallazione: string) => {
        this.tipologiaInstallazione = tipologiaInstallazione  
    }

    private getTipologiaInstallazioneFactory(): () => Observable<any> {
        return () => {
            return this.programmiService.getTipologiaInstallazione();
        }
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsReadonly);
        return throwError(() => err);
    }

    // #endregion

    // #region Public

    public manageFormClick(config: SdkTextOutput): void {


        if (isObject(config)) {

            if (config.code === 'urlApprovazione' || config.code === 'urlAdozione') {
                let link = document.createElement('a');
                document.body.appendChild(link);
                link.target = '_blank';
                link.href = config.data;
                link.click();
            } else {

                if (config.code === 'responsabile') {
                    config.modalComponentConfig.selectedItem = this.programma.rup;
                } else if (config.code === 'ufficio') {
                    config.modalComponentConfig.selectedItem = this.programma.ufficio;
                }
                this.modalConfig = {
                    ...this.modalConfig,
                    component: config.modalComponent,
                    componentConfig: config.modalComponentConfig
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            }
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button)) {
            if (button.code === 'confronto-programmi') {
                // apertura modale
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'confronto-programmi-modal-widget',
                    componentConfig: {
                        ...this.config.confrontoProgrammiModalConfig,
                        contri: this.programma.id,
                        idProgramma: this.programma.idProgramma,
                        programmiDaConfronto: this.programmiDaConfronto
                    }
                };
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
            } else if (isEmpty(button.provider) === false) {
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    idProgramma: this.programma.id,
                    idRicevuto: this.programma.idRicevuto,
                    tipologia: `${this.programma.tipoProg}`,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                    chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                    syscon: this.userProfile.syscon,
                    tipologiaInstallazione: this.tipologiaInstallazione
                }).subscribe(this.manageExecutionProvider);
            }
        }
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'modal') {
            if (isObject(_item.data)) {
                this.programma.rup = _item.data;
                this.loadProgramma();
            }
        }
    }

    // #endregion
}
