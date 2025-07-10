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
    GridUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    TabellatiCacheService,
    ValoreTabellato
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { cloneDeep, isEmpty, isEqual, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { FullFlussiFase, GaraEntry, LottoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { ListaInviiFasiDatasource } from './lista-invii-fasi.datasource.service';

@Component({
    templateUrl: `lista-invii-fasi-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaInviiFasiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-invii-fasi-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    private codGara: string;
    private codLotto: string;
    private buttons: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    private buttonsLocked: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsLS: Array<SdkMenuTab>;
    private lotto: LottoEntry;
    private idSchedaLocale: string;
    public valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaInviiFasiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private moreInfoGara: any;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQuerySearch();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadLotto),
            map(this.elaborateLotto),
            map(this.refreshBreadcrumb)
        ).subscribe();
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
                this.menuTabs = this.config.menuTabs;

                this.isReady = true;
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                codGara: this.codGara
            }).subscribe();
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private loadQuerySearch(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.fromLS = paramsMap.get('fromLS');
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: FullFlussiFase = selectedRow.dataItem as FullFlussiFase;
                this.getIdSchedaLocale(item).pipe(
                    map(this.elaborateIdSchedaLocale)
                ).subscribe({
                    next: () => { 
                        this.dettaglioFlusso(item)
                    }
                });
            }
        }
    }

    private initGrid(): void {

        this.datasource = this.factory.create(ListaInviiFasiDatasource, {
            messagesPanel: this.messagesPanel,
            codGara: this.codGara,
            codLotto: this.codLotto,
            valoriTabellati: this.valoriTabellati
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.fullDateformat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private getIdSchedaLocale(item: FullFlussiFase): Observable<string> {
        const factory = this.getIdSchedaLocaleFactory(item);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private getIdSchedaLocaleFactory(item: FullFlussiFase): () => Observable<string> {
        return () => {
            return this.gareService.getIdSchedaLocale(item);
        }
    }

    private elaborateIdSchedaLocale = (result: string) => {
        this.idSchedaLocale = result === 'N' ? null : result;
    }

    private dettaglioFlusso(flusso: FullFlussiFase): void {
        if (isObject(flusso)) {
            const dettaglioModal = this.config.dettaglioFlussoGara;
            this.modalConfig = {
                ...this.modalConfig,
                component: dettaglioModal.modalComponent,
                componentConfig: {
                    ...dettaglioModal.modalComponentConfig,
                    locale: this.config.locale,
                    dettaglio: {
                        ...flusso,
                        tipoInvio: this.getTipoInvioString(flusso),
                        idSchedaLocale: this.idSchedaLocale
                    }
                }
            };

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    private getTipoInvioString(flusso: FullFlussiFase): string {
        switch (flusso.tipoInvio) {
            case '1':
                if(this.moreInfoGara) {
                    return 'PUBBLICAZIONI.PRIMA-PUBBLICAZIONE-FASE.PCP';
                }
                else {
                    return 'PUBBLICAZIONI.PRIMA-PUBBLICAZIONE-FASE.SIMOG';
                }
            case '2':
                return 'PUBBLICAZIONI.RETTIFICA-PUBBLICAZIONE';
            default:
                return undefined;
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }   

        this.buttonsSubj = new BehaviorSubject(this.buttons);
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
                return this.provider.run(tab.visible, { lotto: this.lotto });
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

    private refreshBreadcrumb = () => {
        if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else {
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
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
        return this.tabellatiCacheService.getValoriTabellati(Constants.FASI_LOTTO_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    private loadLotto = (): Observable<LottoEntry> => {
        const factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
        let factory = this.loadGaraFactory();
        return this.requestHelper.begin(factory, this.messagesPanel, false).subscribe((result: GaraEntry) => {
            this.moreInfoGara = result.versioneSimog != null && result.versioneSimog === 9
      
            this.checkInfoBox();
            this.refreshTabs();
            this.showButtons();
            this.initPerformers();
            this.initGrid();
        });
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

    private showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')){
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    // #endregion
}
