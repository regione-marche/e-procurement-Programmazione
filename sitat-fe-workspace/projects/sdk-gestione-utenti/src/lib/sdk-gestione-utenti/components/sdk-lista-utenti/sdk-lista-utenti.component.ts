import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isObject, remove } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, map, of } from 'rxjs';

import { InitRicercaUtentiDTO, RicercaUtentiFormDTOInternal, UserDTO } from '../../model/gestione-utenti.model';
import { ResponseDTO } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { GridUtilsService } from '../../utils/grid-utils.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';
import { SdkListaUtentiDatasource } from './sdk-lista-utenti.datasource.service';
import { ActivatedRoute, ParamMap } from '@angular/router';

@Component({
    templateUrl: `sdk-lista-utenti.component.html`
})
export class SdkListaUtentiComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-utenti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private searchForm: RicercaUtentiFormDTOInternal;
    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private gridConfig: SdkTableConfig;
    private datasource: SdkListaUtentiDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private dialogConfig: SdkDialogConfig;
    private initRicercaUtenti: InitRicercaUtentiDTO;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.SEARCH_FORM_UTENTI_SELECT).subscribe((form: RicercaUtentiFormDTOInternal) => {
            this.searchForm = form;
        }));

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadQueryParams();
        this.loadInitRicercaUtenti().pipe(
            map(this.elaborateInitRicercaUtenti),
            map(() => this.checkInfoBox()),
            map(() => this.initPerformers()),
            map(() => this.initGrid())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                searchForm: this.searchForm
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                    this.reloadGrid();
                } else if (has(data, 'cleanSearch') && get(data, 'cleanSearch') === true) {
                    delete this.searchForm;
                    this.datasource.params = {
                        ...this.datasource.params,
                        searchForm: undefined
                    };
                    this.reloadGrid();
                }
            });
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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
                let item: UserDTO = tempItem as UserDTO;
                this.detailUtente(item.syscon);
            },
            delete: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                this.deleteUtente(item.syscon);
            },
            deleteHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                let hasDifferentUfficiIntestatari: boolean = this.initRicercaUtenti.utenteDelegatoGestioneUtenti == true && this.gestioneUtentiService.userHasDifferentUfficiIntestatariThanSessionUser(item.ufficiIntestatari, this.initRicercaUtenti.stazioniAppaltantiAssociate);
                return hasDifferentUfficiIntestatari || !(this.isGestioneCompletaUtenteEnabled() && item.deletable == true);
            },
            cambioPassword: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                this.cambioPassword(item.syscon);
            },
            abilita: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                this.abilitaUtente(item.syscon);
            },
            disabilita: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                this.disabilitaUtente(item.syscon);
            },
            abilitaHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                let hasDifferentUfficiIntestatari: boolean = this.initRicercaUtenti.utenteDelegatoGestioneUtenti == true && this.gestioneUtentiService.userHasDifferentUfficiIntestatariThanSessionUser(item.ufficiIntestatari, this.initRicercaUtenti.stazioniAppaltantiAssociate);
                return hasDifferentUfficiIntestatari || item.disabilitato !== true;
            },
            disabilitaHidden: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: UserDTO = tempItem as UserDTO;
                let hasDifferentUfficiIntestatari: boolean = this.initRicercaUtenti.utenteDelegatoGestioneUtenti == true && this.gestioneUtentiService.userHasDifferentUfficiIntestatariThanSessionUser(item.ufficiIntestatari, this.initRicercaUtenti.stazioniAppaltantiAssociate);
                return hasDifferentUfficiIntestatari || item.disabilitato === true;
            }
        }
    }

    private detailUtente(syscon: number): void {
        this.provider.run('SDK_GESTIONE_UTENTI_LISTA', {
            action: 'DETAIL',
            syscon,
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    private deleteUtente(syscon: number): void {
        let func = this.deleteUtenteConfirm(syscon);
        this.dialogConfig.open.next(func);
    }

    private deleteUtenteConfirm(syscon: number): any {
        return () => {
            this.provider.run('SDK_GESTIONE_UTENTI_LISTA', {
                action: 'DELETE',
                syscon,
                messagesPanel: this.messagesPanel
            }).subscribe(this.manageProviderResponse);
        }
    }

    private cambioPassword(syscon: number): void {
        this.provider.run('SDK_GESTIONE_UTENTI_LISTA', {
            action: 'CHANGE-PASSWORD',
            syscon,
            messagesPanel: this.messagesPanel
        }).subscribe(this.manageProviderResponse);
    }

    private manageProviderResponse = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(SdkListaUtentiDatasource, {
            searchForm: this.searchForm,
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        if (!this.isGestioneCompletaUtenteEnabled()) {
            remove(this.gridConfig.columns, (one: any) => {
                one.type === 'SDK_GRID_VIEW_CELL_ACTIONS_ICON';
            });
        }
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);


        this.configSub.next(this.gridConfig);
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
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsReadonly, this.userProfile.configurations)
        };

        if (this.isGestioneCompletaUtenteEnabled()) {
            this.buttonsSubj = new BehaviorSubject(this.buttons);
        } else {
            this.buttonsSubj = new BehaviorSubject(this.buttonsReadonly);
        }
    }

    private abilitaUtente(syscon: number): void {
        this.provider.run('SDK_GESTIONE_UTENTI_LISTA', {
            action: 'ENABLE',
            syscon,
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel
        }).subscribe(this.manageProviderResponse);
    }

    private disabilitaUtente(syscon: number): void {
        this.provider.run('SDK_GESTIONE_UTENTI_LISTA', {
            action: 'DISABLE',
            syscon,
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel
        }).subscribe(this.manageProviderResponse);
    }

    private isGestioneCompletaUtenteEnabled(): boolean {
        if (this.userProfile.abilitazioni != null && this.userProfile.abilitazioni.length > 0) {
            return this.userProfile.abilitazioni.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_COMPLETA) &&
                !this.userProfile.abilitazioni.includes(SdkGestioneUtentiConstants.OU_GESTIONE_UTENTI_OU12);
        }
        return false;
    }

    private loadInitRicercaUtenti = (): Observable<InitRicercaUtentiDTO> => {
        return this.gestioneUtentiService.initRicercaUtenti()
            .pipe(
                map((result: ResponseDTO<InitRicercaUtentiDTO>) => result.response)
            );
    }

    private elaborateInitRicercaUtenti = (result: InitRicercaUtentiDTO): void => {
        this.initRicercaUtenti = result;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        let changePassword = paramsMap.get('changePassword');
        if (changePassword != null && changePassword == '1') {
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message: 'SDK-UTENTE.CHANGE-PASSWORD-SUCCESS'
                }
            ]);
        }
    }

    // #endregion
}
