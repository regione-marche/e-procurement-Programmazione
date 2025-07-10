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
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, filter, isEmpty, isObject, remove } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { environment } from '../../../../../../../environments/environment';
import { Constants, TEnvs } from '../../../../../../app.constants';
import { FullDettaglioAttoEntry, GaraEntry, LottoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { ListaAttiLottoDatasource } from './lista-atti-lotto.datasource.service';

@Component({
    templateUrl: `lista-atti-lotto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaAttiLottoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables
    @HostBinding('class')
    public classNames = `lista-atti-lotto-section`;

    @ViewChild('messages')
    public _messagesPanel: ElementRef;

    @ViewChild('infoBox')
    public _infoBox: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;

    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private insertLottoButton: SdkBasicButtonInput;
    private gara: GaraEntry;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaAttiLottoDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private codGara: string;
    private codLotto: string;
    private menuTabs: Array<SdkMenuTab>;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private lotto: LottoEntry;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private isTest: boolean = environment.ENV as TEnvs != 'PRODUCTION';

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.initDialog();
        this.loadQueryParams();
    }

    protected onDestroy(): void {
        // Do nothing
    }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void {
        // Do nothing
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            map(this.loadLottoFactory),
            mergeMap(this.loadLotto),
            map(this.elaborateLotto)
        ).subscribe(() => {
            // setTimeout(() => {
            //     this.refreshTabs();
            //     this.initPerformers();
            //     this.initGrid();
            //     this.checkInfoBox();
            // });
        });
    }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute {
        return this.injectable(ActivatedRoute);
    }

    private get factory(): SdkDatasourceService {
        return this.injectable(SdkDatasourceService);
    }

    private get gridUtilsService(): GridUtilsService {
        return this.injectable(GridUtilsService);
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
    }

    private get provider(): SdkProviderService {
        return this.injectable(SdkProviderService);
    }

    private get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get store(): SdkStoreService {
        return this.injectable(SdkStoreService);
    }

    private get tabellatiCacheService(): TabellatiCacheService {
        return this.injectable(TabellatiCacheService);
    }

    private get translateService(): TranslateService {
        return this.injectable(TranslateService);
    }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    // #region Private

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private detailAtto(atto: FullDettaglioAttoEntry): void {
        const attiMap: IDictionary<string> = {};
        attiMap[atto.tipDoc] = atto.tipDocDesc;
        this.provider.run('APP_GARE_LISTA_ATTI_LOTTO', {
            action: 'DETAIL',
            codGara: this.codGara,
            codLotto: this.codLotto,
            tipoDocumento: atto.tipDoc,
            numPubb: atto.numPubb,
            campiObbligatori: atto.campiObbligatori,
            campiVisibili: atto.campiVisibili,
            attiMap: attiMap,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private refreshTabs(): void {
        const tabsToFilter = this.menuTabs;
    
        const filteredTabs = tabsToFilter.filter(tab => {
            if ((tab.code === 'lista-fasi-lotto' || tab.code === 'lista-invii-fasi') && this.gara.dataPubblicazione == null && !this.gara.pcp) {
                return false;
            }
    
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
        this.menuTabs = filteredTabs;
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(ListaAttiLottoDatasource, {
            codGara: this.codGara,
            codLotto: this.codLotto,
            valoriTabellati: this.valoriTabellati,
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.LISTA_LOTTI_TABELLATI);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                const tempItem: unknown = selectedRow.dataItem as unknown;
                const item: FullDettaglioAttoEntry = tempItem as FullDettaglioAttoEntry;
                this.detailAtto(item);
            }
        }
    }

    private loadButtons(): void {

        const buttonsConfig: SdkButtonGroupInput = {
            buttons: []
        };

        if (this.config.body.buttons) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, ...this.config.body.buttons];
        }

        if (this.config.body.insertLottoButton && this.isTest === true) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, this.config.body.insertLottoButton];
        }

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(buttonsConfig.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
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
        this.refreshTabs();
        this.initPerformers();
        this.initGrid();
        this.checkInfoBox();
    };

    private loadGara = (): Observable<GaraEntry> => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                isTest: this.isTest
            }).subscribe();
        }
    }

    // #endregion
}