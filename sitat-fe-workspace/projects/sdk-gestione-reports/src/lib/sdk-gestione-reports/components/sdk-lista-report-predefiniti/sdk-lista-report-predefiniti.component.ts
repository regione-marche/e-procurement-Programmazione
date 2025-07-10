import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { HttpRequestHelper, ProtectionUtilsService, StazioneAppaltanteInfo } from "@maggioli/app-commons";
import { FilterObject, IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkRouterService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { isEmpty, map as mapArray } from "lodash-es";
import { BehaviorSubject, Observable, Observer, catchError, concatMap, map, tap, throwError } from "rxjs";
import { ResponseDTO, ResponseListaDTO } from "../../model/lib.model";
import { WRicParamDTO } from "../../model/lista-params.model";
import { WRicercheDTO } from "../../model/lista-report.model";
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneParametriService } from "../../services/gestione-parametri.service";
import { GestioneReportPredefinitiService } from "../../services/gestione-report-predefiniti.service";
import { GestioneReportService } from "../../services/gestione-report.service";

@Component({
    templateUrl: './sdk-lista-report-predefiniti.component.html',
    styleUrls: ['./sdk-lista-report-predefiniti.component.scss']
})
export class SdkListaReportPredefinitiComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-report-predefiniti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public listaReportPredefinitiFromWRicerche: Array<WRicercheDTO>;
    public listaParamsFromWRicParam: Array<WRicParamDTO>;
    public listaReportPreferiti: Array<WRicercheDTO>;
    public listaAllReports: Array<WRicercheDTO>;
    public hoveredRow: number | null = null;

    private buttons: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private currentFilter: FilterObject<WRicercheDTO>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo; 
    private dettaglioReport: WRicercheDTO;
    private idProfilo: string;
    private numParams: number;
    private homeSlug: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(SdkGestioneReportConstants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.loadButtons();
        this.loadQueryParams();
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
                this.homeSlug = this.config.homeSlug;
                this.isReady = true
            });
        }
    }

    protected onUpdateState(_state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get gestioneReportPredefinitiService(): GestioneReportPredefinitiService { return this.injectable(GestioneReportPredefinitiService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get gestioneParametriService(): GestioneParametriService { return this.injectable(GestioneParametriService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

    //#region Private 

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +this.userProfile.syscon;//+paramsMap.get('syscon');
        this.idProfilo = this.userProfile?.configurations?.idProfilo;//paramsMap.get('idProfilo');
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

    private back(): void {
        
        let homeSlug = this.homeSlug != null ? this.homeSlug : 'home-page';
        this.routerService.navigateToPage(homeSlug, { load: 'true' });        
        new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private loadListaReportPredefinitiFromWRicerche = (): Observable<ResponseListaDTO<Array<WRicercheDTO>>> => {
        return this.gestioneReportPredefinitiService.getListaReportPredefiniti(this.userProfile.syscon, this.userProfile.configurations.idProfilo, this.stazioneAppaltanteInfo.codice)
        .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        );
    }

    private elaborateListaReportPredefinitiFromWRicerche = (result: ResponseListaDTO<Array<WRicercheDTO>>) => {
        this.markForCheck(() => {
            const allReports = result.response || [];

            this.listaReportPredefinitiFromWRicerche = allReports.filter((report) => !report.isPreferito);
            this.listaReportPreferiti = allReports.filter((report) => report.isPreferito);

            this.listaAllReports = [...this.listaReportPreferiti, ...this.listaReportPredefinitiFromWRicerche];
        });
    }

    private reloadGrid = () => {
        this.loadListaReportPredefinitiFromWRicerche()
        .pipe(
            map(this.elaborateListaReportPredefinitiFromWRicerche)
        ).subscribe();
    }

    private loadListaParamsFromWRicParam = (idRicerca: number, syscon: number): Observable<ResponseListaDTO<Array<WRicParamDTO>>> => {

        return this.gestioneParametriService.getListaParametriReport(idRicerca, syscon)
        .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        );
    }

    private elaborateListaParamFromWRicParam = (result: ResponseListaDTO<Array<WRicParamDTO>>) => {
        this.listaParamsFromWRicParam = result.response;
        this.numParams = result.totalCount;
    }

    private loadDettaglio = (idRicerca: number): Observable<ResponseDTO<WRicercheDTO>> => {

        return this.gestioneReportService.getDetailReport(idRicerca)
            .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        );
    }

    private elaborateDettaglio = (result: ResponseDTO<WRicercheDTO>) => {
        this.dettaglioReport = result.response;
    }

    private aggiungiReportPreferito(idRicerca: number, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {
        let aggiungiRepPrefe = this.aggiungiReportPreferitoFactory(idRicerca, +syscon, idProfilo);
        return this.requestHelper.begin(aggiungiRepPrefe, this.messagesPanel);
    }

    private aggiungiReportPreferitoFactory(idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            return this.gestioneReportService.aggiungiReportPreferito(idRicerca, syscon, idProfilo);
        }
    }

    private rimuoviReportPreferito(idRicerca: number, syscon: number, idProfilo: string): Observable<ResponseDTO<WRicercheDTO>> {
        let rimuoviRepPrefe = this.rimuoviReportPreferitoFactory(idRicerca, +syscon, idProfilo);
        return this.requestHelper.begin(rimuoviRepPrefe, this.messagesPanel);
    }

    private rimuoviReportPreferitoFactory(idRicerca: number, syscon: number, idProfilo: string) {
        return () => {
            return this.gestioneReportService.rimuoviReportPreferito(idRicerca, syscon, idProfilo);
        }
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
                fromPredefiniti: true,
                fromDefinizione: false
            };

            if (button.code === 'back') {
                this.back();
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public onFilter(event: FilterObject<WRicercheDTO>): void {
        this.currentFilter = event;
    }

    public eseguiReport(idRicerca: number): any{

        //L'utente va mandato alla pagina dei parametri solo se presenti, altrimenti viene mandato direttamente alla pagina di risultato del report.
        //Se sono presenti parametri, l'utente è in grado di impostarne di nuovi, salvandoli nella w_cachericpar.

        //Prima di eseguire il report recupero i parametri da passare e poi mando in esecuzione.
        //Per farlo devo recuperare il sysute del report in modo da capire chi ha inserito i parametri per poi recuperarli dalla w_cachericpar
        this.loadDettaglio(idRicerca).pipe(
            tap((result) => this.elaborateDettaglio(result)),
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            }),
            concatMap(() => {

                return this.loadListaParamsFromWRicParam(idRicerca, +this.dettaglioReport.syscon).pipe(
                    tap(this.elaborateListaParamFromWRicParam),
                    catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                        let err: ResponseDTO<any> = error.error;
                        if (err != null && err.messages != null && err.messages.length > 0) {
                            let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                                let message: SdkMessagePanelTranslate = {
                                    message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                                };
                                return message;
                            });
                            this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                        }
                        return throwError(() => error);
                    })
                )
            })
        ).subscribe(() => {

            return this.provider.run('SDK_REPORT_PREDEFINITI_LISTA_REPORT_PROVIDER', {
                action: 'ESEGUI-REPORT',
                nomeReport: this.dettaglioReport.nome,
                idRicerca: idRicerca,
                syscon: this.syscon,
                numParams: this.numParams,
                fromDefinizione: false,
                fromPredefiniti: true,
                form: this.listaParamsFromWRicParam,
                setUpdateState: this.setUpdateState,
                messagesPanel: this.messagesPanel
            }).subscribe();
        });
    }

    public togglePreferito(report: WRicercheDTO): void {
        report.isPreferito = !report.isPreferito;

        const richiesta$ = report.isPreferito
            ?
                this.aggiungiReportPreferito(report.idRicerca, report.syscon, this.idProfilo)
            :
                this.rimuoviReportPreferito(report.idRicerca, report.syscon, this.idProfilo)

        richiesta$
        .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                report.isPreferito = !report.isPreferito;

                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        )
        .subscribe(this.reloadGrid);
    }

    //#endregion

}