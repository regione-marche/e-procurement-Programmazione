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
    SdkHttpLoaderService,
    SdkHttpLoaderType,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkDropdownButtonInput,
    SdkMenuTab,
    SdkMessagePanelService
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, filter, get, has, isEmpty, isObject, remove, toString } from 'lodash-es';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { BehaviorSubject, Observable, Subject, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../../../../environments/environment';
import { Constants, TEnvs } from '../../../../../../app.constants';
import { GaraEntry, LottoAccorpabileEntry, LottoBaseEntry } from '../../../../../models/gare/gare.model';
import { ListaLottiDatasource } from './lista-lotti.datasource.service';

@Component({
    templateUrl: `lista-lotti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaLottiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lista-lotti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;

    private buttonsRO: SdkButtonGroupInput;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private insertLottoButton: SdkBasicButtonInput;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaLottiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private codGara: string;
    private menuTabs: Array<SdkMenuTab>;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private isTest: boolean = environment.ENV as TEnvs != 'PRODUCTION';

    private gara: GaraEntry;

    private lottoAccorpabile: LottoAccorpabileEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        this.loadButtons();
        this.initDialog();
        this.loadQueryParams();
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
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati)
        ).subscribe(() => {
            this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
            setTimeout(() => {
                this.getGara();

            });
        });
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

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }


    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                codGara: this.codGara,
                isTest: this.isTest,
                lottoAccorpabile: this.lottoAccorpabile
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                    this.reloadGrid();
                }
            });
        }
    }

    public getGara() {
        let factory = this.loadGaraFactory();
        return this.requestHelper.begin(factory, this.messagesPanel, false).subscribe((result: GaraEntry) => {
            this.gara = result;
            if (this.gara.pcp) {
                this.menuTabs = filter(this.config.menuTabs, (one) => one.code !== 'pubblica');
            }
            const factory = this.checkLottiAccorpatiGaraFactory(this.codGara);
            return this.requestHelper.begin(factory, this.messagesPanel, false).subscribe((result: LottoAccorpabileEntry) => {
                this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
                this.lottoAccorpabile = result;

                this.initPerformers();
                this.initGrid();
                this.checkInfoBox();

                this.showButtons()
                this.refreshTabs();

            });

        });
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

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: LottoBaseEntry = tempItem as LottoBaseEntry;
                this.detailLotto(toString(item.codLotto));
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: LottoBaseEntry = tempItem as LottoBaseEntry;
                this.deleteLotto(toString(item.codLotto));
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: LottoBaseEntry = selectedRow.dataItem as LottoBaseEntry;
                if (item.deletable === false || this.gara.readOnly || this.gara.pcp) {
                    return true;
                } else {
                    return false;
                }

            },
            deleteNotHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let item: LottoBaseEntry = selectedRow.dataItem as LottoBaseEntry;
                if (item.deletable === true && !this.gara.readOnly && !this.gara.pcp) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private detailLotto(codLotto: string): void {
        this.provider.run('APP_GARE_LISTA_LOTTI', {
            action: 'DETAIL',
            codGara: this.codGara,
            codLotto,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    private deleteLotto(codLotto: string): void {
        let func = this.deleteLottoConfirm(codLotto);
        this.dialogConfig.open.next(func);
    }

    private deleteLottoConfirm(codLotto: string): any {
        return () => {
            this.addSubscription(
                this.provider.run('APP_GARE_LISTA_LOTTI', {
                    action: 'DELETE',
                    codGara: this.codGara,
                    codLotto,
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageDelete)
            );
        }
    }

    private manageDelete = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(ListaLottiDatasource, {
            codGara: this.codGara,
            valoriTabellati: this.valoriTabellati,
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
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
                let visible: boolean = this.provider.run(one.visible);
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

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.LISTA_LOTTI_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        this.valoriTabellati = result;
    }

    private checkLottiAccorpatiGaraFactory(codGara: string) {
        return () => {
            return this.gareService.checkLottiAccorpatiGara(codGara);
        }
    }



    private loadButtons(): void {

        let buttonsConfig: SdkButtonGroupInput = {
            buttons: []
        };

        if (this.config.body.buttons) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, ...this.config.body.buttons];
        }

        if (this.config.body.insertLottoButton && this.isTest === true) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, this.config.body.insertLottoButton];
        }

        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        };

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(buttonsConfig.buttons, this.userProfile.configurations)
        };

    }

    private showButtons(): void {
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);
        if (this.gara.readOnly) {
            this.buttonsRO = this.removeAccorpamentoButton(this.buttonsRO);
            this.buttonsSubj.next(this.buttonsRO);
        } else if (this.gara.pcp) {
            this.buttonsRO = this.removeAccorpamentoButton(this.buttonsRO);
            this.buttonsSubj.next(this.buttonsRO);
        } else {
            this.buttons = this.removeAccorpamentoButton(this.buttons);
            this.buttonsSubj.next(this.buttons);
        }
        this.sdkHttpLoaderService.hideLoader();
    }

    private removeAccorpamentoButton(buttons: SdkButtonGroupInput): SdkButtonGroupInput {
        if (this.lottoAccorpabile != null && this.lottoAccorpabile.allowMultiotto === false) {
            remove(buttons.buttons, (one: SdkDropdownButtonInput) => {
                return one.code == 'accorpamento-lotti';
            });
        }
        return buttons;
    }

    private anagraficaGaraPubblicataFactory(codGara: string): () => Observable<boolean> {
        return () => {
            return this.gareService.isAnagraficaGaraPubblicata(codGara, 'false');
        }
    }

    private loadAnagraficaGaraPubblicata = () => {
        let factory = this.anagraficaGaraPubblicataFactory(this.codGara);
        return this.requestHelper.begin(factory, this.messagesPanel, false);
    }

    // #endregion
}
