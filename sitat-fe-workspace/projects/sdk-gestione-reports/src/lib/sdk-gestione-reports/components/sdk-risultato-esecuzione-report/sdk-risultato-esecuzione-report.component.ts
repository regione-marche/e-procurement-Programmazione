import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild, ViewEncapsulation } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { ProtectionUtilsService } from "@maggioli/app-commons";
import { FilterObject, IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkMenuConfig, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { isArray, isEmpty, map as mapArray, merge } from "lodash-es";
import { BehaviorSubject, Observable, Subject, catchError, map, of, throwError } from "rxjs";
import { ResponseDTO, ResponseListaDTO, ResultQueryExecutionFormDTO, SdkBreadcrumbItem } from "../../model/lib.model";
import { WRicParamDTO } from "../../model/lista-params.model";
import { WRicercheDTO } from "../../model/lista-report.model";
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneReportService } from "../../services/gestione-report.service";
import { SdkDettaglioReportStoreService } from '../sdk-dati-generali-report/sdk-dati-generali-report-store.service';
import { Table } from "primeng/table";

@Component({
    templateUrl: './sdk-risultato-esecuzione-report.component.html',
    styleUrls: ['./sdk-risultato-esecuzione-report.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkRisultatoEsecuzioneReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    // #region Variables

    @HostBinding('class') classNames = `sdk-risultato-esecuzione-report-page-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    @ViewChild('dt2') table: Table;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public nomeReport: string;
    public detailedError: string;

    private buttons: SdkButtonGroupInput;
    private buttonsErr: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private currentFilter: FilterObject<WRicercheDTO>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private formParamsToParse: string;
    private formParams: Array<WRicParamDTO>;
    private idRicerca: number;
    public executionFormDTO: ResultQueryExecutionFormDTO;
    public listaColumnNames: Array<string>;
    private listaValues: Array<any>;
    private colNumber: number;
    private idProfilo: string;
    private queryReplacedWithParams: string;
    private paramsToShowUser: { [key: string]: any };
    private paramsNumber: number;
    private paramsKey: string;
    private fromDefinizione: boolean;
    private totalCount: number;
    private maxNumRecordConfig: number;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.reloadGrid();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            });
        }
    }

    protected onUpdateState(_state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    // #endregion

    //#region Private 

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsErr = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsErr, this.userProfile.configurations)
        }
        
        this.buttonsSubj.next(this.buttons);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +this.userProfile.syscon;
        this.idRicerca = +paramsMap.get('idRicerca');
        this.formParamsToParse = sessionStorage.getItem('formParams');
        sessionStorage.removeItem('formParams');
        this.formParams = JSON.parse(this.formParamsToParse);
        this.idProfilo = this.userProfile?.configurations?.idProfilo;
        this.fromDefinizione = paramsMap.get('fromDefinizione')?.toLowerCase() === "true";
        this.nomeReport = this.sdkDettaglioReportStoreService.nome;
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

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(
                this.infoBox, 
                {
                    message: this.config.infoBox
                }
            );
        }
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private loadEsecuzioneReportResponse = (): Observable<ResponseListaDTO<ResultQueryExecutionFormDTO>> => {
        return this.gestioneReportService.executeReportWithParams(this.idRicerca, this.syscon, this.idProfilo, this.formParams)
        .pipe(
            //Questo blocca la response. Va tolto e gestito diversamente
            map((result: ResponseListaDTO<ResultQueryExecutionFormDTO>) => {

                if (result.done === SdkGestioneReportConstants.RESPONSE_Y && result.messages != null && result.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(result.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    })
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                    this.buttonsSubj.next(this.buttonsErr);
                }
                return result;
            }),
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 1) {

                    let message: SdkMessagePanelTranslate = {
                        message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${err.messages[0]}`
                    };
                    
                    this.sdkMessagePanelService.showError(this.messagesPanel, [message]);
                    this.detailedError = err.messages[1];
                }
                else {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                this.buttonsSubj.next(this.buttonsErr);
                return throwError(() => error);
            })
        );
    }

    private elaborateEsecuzioneReportResponse = (result: ResponseListaDTO<ResultQueryExecutionFormDTO>) => {
        this.executionFormDTO = result.response;
        this.listaColumnNames = result.response?.columnNames;
        this.listaValues = result.response?.values;
        this.colNumber = this.listaColumnNames?.length || 0;
        this.queryReplacedWithParams = result.response?.queryReplacedWithParams;
        this.paramsToShowUser = result.response?.paramsToShowUser;
        this.totalCount = result.totalCount;
        this.maxNumRecordConfig = result.maxNumRecordConfig;

        this.refreshBreadcrumbs();
    }

    getParamsLength(): number {
        return Object.keys(this.paramsToShowUser).length;
    }

    getParamsArray(): {key: string, value: any}[] {
        return Object.entries(this.paramsToShowUser).map(([key, value]) => ({key, value}));
    }


    private reloadGrid = () => {
        this.loadEsecuzioneReportResponse()
        .pipe(
            map(this.elaborateEsecuzioneReportResponse)
        ).subscribe();
    }

    //#endregion

    //#region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                idRicerca: this.idRicerca,
                syscon: this.syscon,
                formParams: this.executionFormDTO,
                columnNames: this.listaColumnNames,
                values: this.listaValues,
                idProfilo: this.idProfilo,
                fromDefinizione: this.fromDefinizione,
                maxNumRecordConfig: this.maxNumRecordConfig,
                totalCount: this.totalCount
            };

            this.provider.run(button.provider, data).subscribe();
        }
    }

    public onFilter(event: FilterObject<WRicercheDTO>): void {
        this.currentFilter = event;
    }

    //#endregion

}