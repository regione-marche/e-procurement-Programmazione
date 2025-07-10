import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { HttpRequestHelper, ProtectionUtilsService } from "@maggioli/app-commons";
import { FilterObject, IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkRouterService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { isEmpty, map as mapArray } from 'lodash-es';
import { Table } from "primeng/table";
import { BehaviorSubject, Observable, Observer, Subject, catchError, map, of, throwError } from 'rxjs';
import { ResponseDTO } from "../../model/lib.model";
import { WRicercheDTO } from "../../model/lista-report.model";
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneReportService } from "../../services/gestione-report.service";

@Component({
    templateUrl: './sdk-lista-report.component.html',
    styleUrls: ['./sdk-lista-report.component.scss']
})
export class SdkListaReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    // #region Variables

    @HostBinding('class') classNames = `sdk-lista-report-section`;

    @ViewChild('dt2') table: Table;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public hoveredRow: number | null = null;
    public listaReportFromWRicerche: Array<WRicercheDTO>;
    public listaReportPreferiti: Array<WRicercheDTO>;
    public listaAllReports: Array<WRicercheDTO>;

    private buttons: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private currentFilter: FilterObject<WRicercheDTO>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private idProfilo: string;
    private homeSlug: string;

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

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

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
        //let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +this.userProfile.syscon;//+paramsMap.get('syscon');
        this.idProfilo = this.userProfile?.configurations?.idProfilo;
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

    private loadListaReportFromWRicerche = (): Observable<ResponseDTO<Array<WRicercheDTO>>> => {
        return this.gestioneReportService.getListaReport()
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

    private elaborateListaReportFromWRicerche = (result: ResponseDTO<Array<WRicercheDTO>>) => {

        const allReports = result.response || [];

        this.listaReportFromWRicerche = allReports.filter((report) => !report.isPreferito);
        this.listaReportPreferiti = allReports.filter((report) => report.isPreferito);

        this.listaAllReports = [...this.listaReportPreferiti, ...this.listaReportFromWRicerche];

        this.markForCheck(() => {
            if (this.table) {
                this.table.reset(); 
            }
        });
    }

    private reloadGrid = () => {
        this.loadListaReportFromWRicerche()
        .pipe(
            map(this.elaborateListaReportFromWRicerche)
        ).subscribe();
    }

    private deleteReportConfirm(idRicerca: number): any {
        return () => {
            this.provider.run('SDK_GESTIONE_REPORT_LISTA_REPORT', {
                action: 'DELETE-SINGLE-REPORT',
                idRicerca: idRicerca,
                idProfilo: this.idProfilo,
                syscon: this.syscon,
                messagesPanel: this.messagesPanel
            }).subscribe(this.reloadGrid); 
        }
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

    public performModalOnCancel(idRicerca: number) {
        let func = this.deleteReportConfirm(idRicerca);
        this.dialogConfig.open.next(func);
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState
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

    public loadDettaglioReport(idRicerca: number): void{
        
        this.provider.run('SDK_GESTIONE_REPORT_LISTA_REPORT', {
            action: 'DETAIL',
            idRicerca: idRicerca,
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel,
            syscon: this.syscon,
            idProfilo: this.userProfile?.configurations?.idProfilo
        }).subscribe();
    }

    public copyRowReport(idRicerca: number){

        return this.gestioneReportService.copyRowReport(idRicerca, this.syscon, this.idProfilo)
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
            ).subscribe(this.reloadGrid);   
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