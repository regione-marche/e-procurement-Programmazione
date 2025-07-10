import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkRouterService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, isEmpty, isString, map as mapArray } from "lodash-es";
import { BehaviorSubject, Observable, Subject, catchError, map, mergeMap, of, throwError } from 'rxjs';
import { CustomParamsFunctionResponse, ResponseDTO, ValoreTabellato } from "../../model/lib.model";
import { WRicercheDTO } from "../../model/lista-report.model";
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneReportService } from "../../services/gestione-report.service";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkDettaglioReportStoreService } from "../sdk-dati-generali-report/sdk-dati-generali-report-store.service";

@Component({
    templateUrl: './sdk-modifica-dati-generali-report.component.html',
    styleUrls: ['./sdk-modifica-dati-generali-report.component.scss']
})
export class SdkModificaDatiGeneraliReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    //#region Variables

    @HostBinding('class') classNames = `sdk-modifica-dati-generali-report-page-section`;

    @ViewChild('messages') _messagePanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObjs: Observable<SdkDialogConfig>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public schedula: string;

    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private dettaglioReport: WRicercheDTO;
    private warning: string;
    private idProfilo: string;
    private contieneParams: boolean;

    //#endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef){
        super(inj, cdr);
    }

    //#region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.setUpdateState(true);
        
        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadWarning();
        this.loadDettaglio()
        .pipe(
            map(this.elaborateDettaglio),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm())
        ).subscribe();
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

    //#endregion

    // #region Getters

    private get messagesPanel(): HTMLElement {
        return this._messagePanel != null ? this._messagePanel.nativeElement : undefined;
    }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    //#region Private 

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +this.userProfile.syscon;//+paramsMap.get('syscon');
        this.idProfilo = this.userProfile?.configurations?.idProfilo;
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObjs = of(this.dialogConfig);
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

    private loadForm(): void {

        let formConfig: SdkFormBuilderConfiguration = {
            fields: cloneDeep(get(this.config, 'body.fields'))
        };

        let form: IDictionary<any> = {
            ...this.dettaglioReport,
            idRicerca: this.dettaglioReport.idRicerca,
            nome: this.dettaglioReport.nome,
            descrizione: this.dettaglioReport.descrizione,
            pubblicato: this.dettaglioReport.pubblicato,
            sysute: this.dettaglioReport.sysute,
            //esponiComeServizio: this.dettaglioReport.esponiComeServizio,
            codReportWs: this.dettaglioReport.codReportWs,
            //schedula: this.dettaglioReport.schedula,
            cronExpression: this.dettaglioReport.cronExpression,
            formatoSchedulazione: this.dettaglioReport.formatoSchedulazione,
            emailSchedulazioneRisultato: this.dettaglioReport.emailSchedulazioneRisultato,
            noOutputVuoto: this.dettaglioReport.noOutputVuoto
        }

        let customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean): CustomParamsFunctionResponse => {
            let mapping: boolean = true;
    
            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }
    
            return {
                mapping,
                field
            };
        }

        if(formConfig.fields.length > 1){
            let newSchedula: any = formConfig.fields[1].fieldSections[0];

            if(this.contieneParams === true){
                newSchedula = {
                    ...newSchedula, 
                    disabled: true,
                    showLabelHelp: true,
                    helpLabelDescription: this.translateService.instant('SDK-DETTAGLIO-REPORT.VALIDATORS.REPORT-NO-SCHEDULAZIONE-PARAMETRI')
                };

                /*this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                    {
                        message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.REPORT-NO-SCHEDULAZIONE-PARAMETRI'
                    }
                ]);*/
            }
            else{
                newSchedula = {...newSchedula, disabled: false};
            }

            formConfig.fields[1].fieldSections[0] = {...newSchedula};
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, form);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.schedula = this.formBuilderConfig?.fields[1]?.fieldSections[0]?.data?.key;
        
        this.formBuilderConfigObs.next(formConfig);
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }
    
    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadDettaglio = (): Observable<ResponseDTO<WRicercheDTO>> => {

        return this.gestioneReportService.getDetailReport(this.sdkDettaglioReportStoreService.idRicerca)
            .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                    let message: SdkMessagePanelTranslate = {
                    message: `SDK-UTENTE.VALIDATORS.${one}`
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
        this.contieneParams = this.dettaglioReport.contieneParametri;
    }

    private loadWarning(): void {
        if (this.warning != null) {
            if (this.warning == 'GESTIONE_REPORTS_RESPONSE_NOT_DONE') {
                this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                    {
                        message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.GESTIONE_REPORTS_RESPONSE_NOT_DONE'
                    }
                ]);
            }
            if (this.warning == 'GESTIONE_REPORTS_UPDATE_FALSE') {
                this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                    {
                        message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.GESTIONE_REPORTS_UPDATE_FALSE'
                    }
                ]);
            }
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
                idRicerca: this.sdkDettaglioReportStoreService.idRicerca,
                syscon: this.syscon,
                idProfilo: this.userProfile?.configurations?.idProfilo
            };


            if (button.code === 'back-to-dati-generali') {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            }
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
        this.schedula = this.formBuilderConfig?.fields[1]?.fieldSections[0]?.data?.key;
    }

    //#endregion
}