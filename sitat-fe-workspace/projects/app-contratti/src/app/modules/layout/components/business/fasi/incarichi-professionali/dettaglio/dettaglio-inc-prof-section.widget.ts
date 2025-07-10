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
    RupEntry,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
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
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, filter, get, isEmpty, isEqual, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { GaraEntry, LottoEntry } from '../../../../../../models/gare/gare.model';
import { IncarichiProfEntry } from '../../../../../../models/incarichi-professionali/incarichi-professionali.model';
import { GareService } from '../../../../../../services/gare/gare.service';
import {
    IncarichiProfessionaliService,
} from '../../../../../../services/incarichi-professionali/incarichi-professionali.service';
import { DettaglioFaseStoreService } from '../../dettaglio-fase-store.service';


@Component({
    templateUrl: `dettaglio-inc-prof-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioIncarichiProfessionaliSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-incarichi-professionali-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    protected buttonsRO: SdkButtonGroupInput;
    protected buttonsLS: SdkButtonGroupInput;
    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private incarichi: IDictionary<Array<IncarichiProfEntry>>;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private modalConfig: any;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsLS: Array<SdkMenuTab>;
    private lotto: LottoEntry;
    private current: boolean = true;
    private riaggiudicazione: boolean = false;
    private cigFiglio: boolean = false;
    private gara: GaraEntry;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        if (this.dettaglioFaseStoreService.current != null) {
            this.current = this.dettaglioFaseStoreService.current;
        }

        if (this.dettaglioFaseStoreService.riaggiudicazione != null) {
            this.riaggiudicazione = this.dettaglioFaseStoreService.riaggiudicazione;
        }

        if (this.dettaglioFaseStoreService.cigFiglio != null) {
            this.cigFiglio = this.dettaglioFaseStoreService.cigFiglio;
        }

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadLottoFactory),
            mergeMap(this.loadLotto),
            map(this.elaborateLotto),
            map(() => this.refreshTabs()),
            mergeMap(this.loadIncarichiProfessionali),
            map(this.elaborateIncarichiProfessionali),
            map(() => this.getGara()),
            map(() => this.showButtons()),
            map(() => this.refreshBreadcrumb())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
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

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get incarichiProfessionaliService(): IncarichiProfessionaliService { return this.injectable(IncarichiProfessionaliService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'nomtec') {
                this.modalConfig.componentConfig = {
                    gara: this.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: config.textModalContent
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item.data != null) {
            this.loadIncarichiProfessionali().pipe(
                map(this.elaborateIncarichiProfessionali)
            ).subscribe();
        }
    }

    public getGara() {
        let factory = this.loadGaraFactory();
        return this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: GaraEntry) => {
            this.gara = result;
        });        
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            riaggiudicazione: this.riaggiudicazione
        }).subscribe();
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.fromLS = paramsMap.get('fromLS');
    }    

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: IncarichiProfEntry, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'nomtec') {
                if (isObject(get(restObject, field.mappingInput))) {
                    let data: RupEntry = get(restObject, field.mappingInput);
                    set(field, 'data', `${data.nominativo} (${data.cf})`);
                    set(field, 'textModalContent', data);
                    mapping = false;
                }
            }

            if (
                field.code === 'data-aff-prog-esterna' ||
                field.code === 'data-cons-prog-esterna'
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

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field
            };
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.incarichi);

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
        return this.tabellatiCacheService.getValoriTabellati(Constants.INCARICHI_PROFESSIONALI_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    private loadLottoFactory = (): Function => {
        let factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return factory;
    }

    private loadLotto = (factory: Function): Observable<LottoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
    };

    private incarichiProfessionaliFactory(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): () => Observable<Array<IncarichiProfEntry>> {
        return () => {
            return this.incarichiProfessionaliService.getIncarichiProfessionali(codGara, codLotto, codiceFase, numeroProgressivo);
        }
    }

    private loadIncarichiProfessionali = (): Observable<Array<IncarichiProfEntry>> => {
        let factory = this.incarichiProfessionaliFactory(this.codGara, this.codLotto, this.codiceFase, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateIncarichiProfessionali = (result: Array<IncarichiProfEntry>) => {
        this.incarichi = {
            incarichi: result
        };
        this.checkInfoBox();
        this.loadForm();
    }

    private refreshTabs(): void {
        const isLS = this.fromLS !== null && isEqual(this.fromLS, 'LS');
        const tabsToFilter = isLS ? this.config.menuTabsLS : this.menuTabs;

        const filteredTabs = tabsToFilter.filter(tab => {
            if (!isEmpty(tab.oggettoProtezione)) {
                const visible = this.protectionUtilsService.isMenuTabVisible(
                    tab.oggettoProtezione, 
                    this.userProfile.configurations
                );
                if (!visible) {
                    return false;
                }
            }
    
            if (!isEmpty(tab.visible)) {
                let visible: boolean = this.provider.run(tab.visible, { lotto: this.lotto });
                return visible === false;
            }
    
            return true;
        });

        this.sdkLayoutMenuTabs.emit(filteredTabs);
        if (isLS) {
            this.menuTabsLS = filteredTabs;
        } else {
            this.menuTabs = filteredTabs;
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttonsRO, this.userProfile.configurations)
        };

        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        };

        if (this.current === false || this.cigFiglio === true) {
            this.buttons.buttons = filter(this.buttons.buttons, (one: SdkBasicButtonInput) => one.code !== 'go-to-update-incarichi');
        }        
    }

    private refreshBreadcrumb = () => {
        if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else {
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
    }

    private showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if(this.gara.readOnly){
                this.buttonsSubj.next(this.buttonsRO);
            } else{
                this.buttonsSubj.next(this.buttons);
            }  
        }   
    }

    // #endregion
}
