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
import { ActivatedRoute } from '@angular/router';
import {
    GridUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { SimogCredentialsForm } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';
import { ResponseListaCollaborazioni } from 'projects/app-contratti/src/app/modules/models/gare/importa-gara.model';
import { DelegheService } from 'projects/app-contratti/src/app/modules/services/deleghe/deleghe.service';
import { ImportaGaraService } from 'projects/app-contratti/src/app/modules/services/gare/importa-gara.service';
import { Constants } from '../../../../../../app.constants';
import { DelegaBaseEntry, DelegaEntry } from '../../../../../models/deleghe/deleghe.model';
import { ListaDelegheDatasource } from './lista-deleghe.datasource.service';

@Component({
    templateUrl: `lista-deleghe-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ListaDelegheSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {
    // #region Variables

    @HostBinding('class') classNames = `lista-deleghe-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private stazioneAppaltante: StazioneAppaltanteInfo;
    private userProfile: UserProfile;

    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private insertDelegaButton: SdkBasicButtonInput;

    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();

    private gridConfig: SdkTableConfig;

    private datasource: ListaDelegheDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;

    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    private dialogConfig: SdkDialogConfig;
    public dialogConfigSubj: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private countDeleghe: number = 0;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(
            this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
                this.userProfile = userProfile;
            })
        );

        this.addSubscription(
            this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
                this.stazioneAppaltante = saInfo;
            })
        );

        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati()
            .pipe(map(this.elaborateTabellati))
            .subscribe(() => {
                setTimeout(() => {
                    this.initPerformers();
                    this.initGrid();
                    this.checkInfoBox();
                    this.showButtons();
                });
            });
    }

    protected onDestroy(): void {}

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void {}

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute {
        return this.injectable(ActivatedRoute);
    }

    private get translateService(): TranslateService {
        return this.injectable(TranslateService);
    }

    private get provider(): SdkProviderService {
        return this.injectable(SdkProviderService);
    }

    private get factory(): SdkDatasourceService {
        return this.injectable(SdkDatasourceService);
    }

    private dettaglioDelegaFactory(id: string): () => Observable<DelegaEntry> {
        return () => {
            return this.delegheService.dettaglioDelega(id);
        };
    }

    private get gridUtilsService(): GridUtilsService {
        return this.injectable(GridUtilsService);
    }

    private get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
    }

    private get store(): SdkStoreService {
        return this.injectable(SdkStoreService);
    }

    private get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get tabellatiCacheService(): TabellatiCacheService {
        return this.injectable(TabellatiCacheService);
    }

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get delegheService(): DelegheService {
        return this.injectable(DelegheService);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                button: button,
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                cfrup: this.userProfile.codiceFiscale,
                idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
            };

            if (button.code === 'nuovo-delega') {
                
                //Ci sono gia' deleghe in griglia, esegui metodo nuova delega del provider
                this.provider.run(button.provider, data);
                
            } else {
                this.provider.run(button.provider, data).subscribe((data: IDictionary<any>) => {
                    if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                        this.reloadGrid();
                    }
                });
            }
        }
    }

    private checkUtenteRUP(button: SdkButtonGroupOutput, simogCredentials: SimogCredentialsForm) {
        const messagesPanel: HTMLElement = this.messagesPanel;
        this.sdkMessagePanelService.clear(messagesPanel);

        const factory = this.checkRupFactory(simogCredentials);

        this.requestHelper.begin(factory, messagesPanel).subscribe((result: ResponseListaCollaborazioni) => {
            if (result.rpntLoginFailed) {
                //Riprova saltanto rpnt
                this.manageRpntLoginFailed(() => this.checkUtenteRUP(button, new SimogCredentialsForm({ skipRpntLogin: true })));
            } else if (result.rupCredentialsInvalid || result.rupCredentialsMissing || result.errorData === 'NO_LOGIN_SIMOG' ) {
                //Chiedi credenziali all'utente
                this.richiediCredenzialiSimog(button, null, result.simogUsername);
            } else {
                //Tutto OK, continue esecuzione
                let data: IDictionary<any> = {
                    button: button,
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    setUpdateState: this.setUpdateState,
                    stazioneAppaltante: this.stazioneAppaltante,
                    cfrup: this.userProfile.codiceFiscale,
                    syscon: this.userProfile.syscon,
                    idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                };
                this.provider.run(button.provider, data);
            }
        },
        (result)=>{
            if (result.error.rpntLoginFailed) {
                //Riprova saltanto rpnt
                this.manageRpntLoginFailed(() => this.checkUtenteRUP(button, new SimogCredentialsForm({ skipRpntLogin: true })));
            } else if (result.error.rupCredentialsInvalid || result.error.rupCredentialsMissing || result.error.errorData === 'NO_LOGIN_SIMOG' ) {
                //Chiedi credenziali all'utente
                this.richiediCredenzialiSimog(button, null, result.error.simogUsername);
            }
        });
    }

    private checkRupFactory(simogCredentials: SimogCredentialsForm) {
        let stazioneAppaltante = this.stazioneAppaltante.codice;
        let cfStazioneAppaltante = this.stazioneAppaltante.codFiscale;
        let syscon = this.userProfile.syscon;        
        return () => {
            return this.importaGaraService.checkRup(stazioneAppaltante, cfStazioneAppaltante, syscon, simogCredentials);
        };
    }

    private get importaGaraService(): ImportaGaraService {
        return this.injectable(ImportaGaraService);
    }

    private manageRpntLoginFailed(func: Function): void {
        //Mostra conferma all'utente: Le credenziali applicative per i servizi SIMOG non sono corrette. Utilizzare le credenziali del RUP?
        this.loadRpntLoginFialedDialog();
        this.dialogConfig.open.next(func);
    }

    private loadRpntLoginFialedDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.CONFIRM-TITLE'),
            message: this.translateService.instant('DIALOG.SKIP-RPNT-LOGIN'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject(),
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'credenzialiSimog') {
            if (isObject(_item.data)) {
                let data: any = _item.data;

                //Riprova passando i nuovi parametri
                this.checkUtenteRUP(
                    data.sourceButton,
                    new SimogCredentialsForm({
                        skipRpntLogin: true,
                        username: data.username,
                        password: data.password,
                        saveCredentials: data.saveCredentials,
                    })
                );
            }
        }
    }

    // #endregion

    // #region Richiesta Credenziali Simog

    private richiediCredenzialiSimog = (button: SdkButtonGroupOutput, form: any, simogUsername: string) => {
        //Apre finestra richiesta credenziali
        this.initCredenzialiSimogModal(button, form, simogUsername);
        this.modalConfig.openSubject.next(true);
    };

    private initCredenzialiSimogModal(button: SdkButtonGroupOutput, form: any, simogUsername: string) {
        this.modalConfig = {
            code: 'credenzialiSimog',
            title: '',
            openSubject: new Subject(),
            component: 'credenziali-simog-modal-widget',
            componentConfig: {
                credenzialiSimogData: this.config.body.credenzialiSimogData,
                sourceButton: button,
                sourceForm: form,
                simogUsername,
            },
        };
        this.modalConfigObs.next(this.modalConfig);
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
            open: new Subject(),
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: DelegaBaseEntry = tempItem as DelegaBaseEntry;
                this.detailDelega(toString(item.id));
            },
            delete: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: DelegaBaseEntry = tempItem as DelegaBaseEntry;
                this.deleteDelega(toString(item.id));
            },
            deleteHidden: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                return false;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                return true;
            },
        };
    }

    private detailDelega(id: string): void {
        this.provider
            .run('APP_CONTRATTI_LISTA_DELEGHE', {
                action: 'DETAIL',
                id,
                messagesPanel: this.messagesPanel,
            })
            .subscribe();
    }

    private deleteDelega(id: string): void {
        let func = this.deleteDelegaConfirm(id);
        this.initDialog();
        this.dialogConfig.open.next(func);
    }

    private deleteDelegaConfirm(id: string): any {
        return () => {
            this.addSubscription(
                this.provider
                    .run('APP_CONTRATTI_LISTA_DELEGHE', {
                        action: 'DELETE',
                        syscon: this.userProfile.syscon,
                        cfrup: this.userProfile.codiceFiscale,
                        idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                        id,
                        messagesPanel: this.messagesPanel,
                    })
                    .subscribe(this.manageDelete)
            );
        };
    }

    private manageDelete = (result: string) => {
        if (isObject(result) && get(result, 'reload') === true) {
            this.reloadGrid();
        }
    };

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {
        this.datasource = this.factory.create(ListaDelegheDatasource, {
            stazioneAppaltante: this.stazioneAppaltante.codice,
            syscon: this.userProfile.syscon,
            cfrup: this.userProfile.codiceFiscale,
            idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
            valoriTabellati: this.valoriTabellati,
            messagesPanel: this.messagesPanel,
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(
            this.gridConfig,
            this.datasource,
            this.performers,
            this.config.locale.dateFormat,
            this.userProfile.configurations
        );

        this.configSub.next(this.gridConfig);

        //Registra per ottenere numero dati della griglia
        this.datasource.result$.subscribe((val: any) => {
            if (isObject(val)) {
                this.countDeleghe = get(val, 'total');
            }
        });
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {        
        this.sdkMessagePanelService.clear(this.infoBox);
        this.sdkMessagePanelService.showInfoBox(this.infoBox, {
            message: 'LISTA-DELEGHE.INFO',
        });        
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.LISTA_DELEGHE_TABELLATI);
    };

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    };

    private loadButtons(): void {
        let buttonsConfig: SdkButtonGroupInput = {
            buttons: [],
        };

        if (this.config.body.buttons) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, ...this.config.body.buttons];
        }

        if (this.config.body.insertDelegaButton) {
            buttonsConfig.buttons = [...buttonsConfig.buttons, this.config.body.insertDelegaButton];
        }

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(buttonsConfig.buttons, this.userProfile.configurations),
        };

        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private showButtons(): void {
        let buttons: SdkButtonGroupInput = {
            buttons: cloneDeep(this.buttons.buttons),
        };

        buttons = this.checkButtonsStatus(buttons);

        this.buttonsSubj.next(buttons);
    }

    private checkButtonsStatus(buttons: SdkButtonGroupInput): SdkButtonGroupInput {
        //Abilita tasti
        // let buttonsArray = buttons.buttons;
        // for (let i = buttonsArray.length - 1; i >= 0; i--) {
        //     const btn = buttonsArray[i];
        //     let hideButton = false;

        //     if (btn.code == 'nuovo-delega') {
        //     }

        //     if (hideButton) {
        //         buttonsArray.splice(i, 1);
        //     }
        // }

        // buttons.buttons = buttonsArray;

        return buttons;
    }
}
