import { HttpErrorResponse } from '@angular/common/http';
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
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
    SdkRadioConfig,
    SdkRadioOutput,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableRowDto, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { cloneDeep, get, has, isEmpty, isObject, map as mapArray } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { GConfCodDTO, ListaCodificaAutomaticaInizDTO } from '../../../../model/amministrazione.model';
import { ResponseDTO } from '../../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GestioneCodificaAutomaticaService } from '../../../../services/gestione-codifica-automatica.service';
import { GridUtilsService } from '../../../../utils/grid-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';
import { SdkListaCodificaAutomaticaDatasource } from './sdk-lista-codifica-automatica.datasource.service';

@Component({
    templateUrl: `sdk-lista-codifica-automatica.component.html`
})
export class SdkListaCodificaAutomaticaComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-codifica-automatica-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    public resetTable: Subject<boolean> = new Subject();
    public radioConfigSub: Subject<SdkRadioConfig> = new Subject();

    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private gridConfig: SdkTableConfig;
    private datasource: SdkListaCodificaAutomaticaDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private iniz: ListaCodificaAutomaticaInizDTO;
    private _codiceApplicazione: string = SdkGestioneUtentiConstants.CODIFICA_AUTOMATICA_DEFAULT_CODAPP;
    private homeSlug: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.loadIniz().pipe(
            map(this.elaborateIniz),
            map(() => this.elaborateRadioButton()),
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
                this.homeSlug = this.config.homeSlug;
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get gestioneCodificaAutomaticaService(): GestioneCodificaAutomaticaService { return this.injectable(GestioneCodificaAutomaticaService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                homeSlug: this.homeSlug
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reloadGrid') && get(data, 'reloadGrid') === true) {
                    this.reloadGrid();
                }
            });
        }
    }

    public manageRadioChange(event: SdkRadioOutput): void {
        if (event != null && event.data != null && event.data.code != null && event.data.code != this.codiceApplicazione) {
            this.codiceApplicazione = event.data.code;
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get codiceApplicazione(): string {
        return this._codiceApplicazione;
    }

    private set codiceApplicazione(value: string) {
        this._codiceApplicazione = value;
        this.datasource.params = {
            ...this.datasource.params,
            codiceApplicazione: this._codiceApplicazione
        };
    }

    private loadIniz = (): Observable<ResponseDTO<ListaCodificaAutomaticaInizDTO>> => {
        return this.gestioneCodificaAutomaticaService.getInizLista()
            .pipe(
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    let err: ResponseDTO<any> = error.error;
                    if (err != null && err.messages != null && err.messages.length > 0) {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: `SDK-CODIFICA-AUTOMATICA.VALIDATORS.${one}`
                            };
                            return message;
                        });
                        this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                    } else {
                        this.sdkMessagePanelService.showError(this.messagesPanel, [
                            {
                                message: `ERRORS.UNEXPECTED-ERROR`
                            }
                        ]);
                    }
                    return throwError(() => error);
                })
            );
    }

    private elaborateIniz = (result: ResponseDTO<ListaCodificaAutomaticaInizDTO>) => {
        this.iniz = result.response;
    }

    private initPerformers(): void {
        this.performers = {
            dettaglio: (selectedRow: { rowIndex: number, dataItem: SdkTableRowDto }) => {
                let tempItem: unknown = selectedRow.dataItem as unknown;
                let item: GConfCodDTO = tempItem as GConfCodDTO;
                this.dettaglio(item);
            }
        }
    }

    private dettaglio(config: GConfCodDTO): void {
        this.provider.run('SDK_GESTIONE_CODIFICA_AUTOMATICA_LISTA', {
            action: 'DETAIL',
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel,
            item: config
        }).subscribe();
    }

    private reloadGrid(): void {
        this.resetTable.next(true);
    }

    private initGrid(): void {

        this.datasource = this.factory.create(SdkListaCodificaAutomaticaDatasource, {
            codiceApplicazione: this.codiceApplicazione,
            messagesPanel: this.messagesPanel
        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.fullDateformat, this.userProfile.configurations);


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

        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private elaborateRadioButton(): void {
        const radioButtonConfig: SdkRadioConfig = {
            code: 'applications',
            choices: [
                {
                    code: 'G_',
                    label: 'SDK-CODIFICA-AUTOMATICA.ARCHIVI',
                    checked: true
                },
                {
                    code: this.iniz.codiceApplicazione,
                    label: this.iniz.titoloApplicazione
                }
            ]
        };

        this.radioConfigSub.next(radioButtonConfig);
    }

    // #endregion
}
