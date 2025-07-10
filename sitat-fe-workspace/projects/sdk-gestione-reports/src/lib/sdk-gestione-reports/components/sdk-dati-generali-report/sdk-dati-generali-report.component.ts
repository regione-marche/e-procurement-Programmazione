import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkRouterService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkMenuTab, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, isEmpty, isString, map as mapArray, remove, toString } from "lodash-es";
import { BehaviorSubject, Observable, Subject, catchError, map, mergeMap, of, throwError } from "rxjs";
import { CustomParamsFunction, ResponseDTO, ResponseListaDTO, ValoreTabellato } from "../../model/lib.model";
import { WRicercheDTO } from "../../model/lista-report.model";
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneReportService } from "../../services/gestione-report.service";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkDettaglioReportStoreService } from './sdk-dati-generali-report-store.service';

@Component({
    templateUrl: './sdk-dati-generali-report.component.html',
    styleUrls: ['./sdk-dati-generali-report.component.scss']
})
export class SdkDatiGeneraliReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    //#region Variables

    @HostBinding('class') classNames = `sdk-dati-generali-report-section`;

    @ViewChild('messages') _messagePanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObjs: Observable<SdkDialogConfig>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private menuTabs: Array<SdkMenuTab>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private dettaglioReport: WRicercheDTO;
    private idProfilo: string;
    private abilitazioneUffInt: number;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private fromCreazione: string;
    private schedula: string;

    //#endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef){
        super(inj, cdr);
    }

    //#region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        
        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        
        this.loadDettaglio()
        .pipe(
            map(this.elaborateDettaglio),
            mergeMap(this.loadAbilitazioneUffInt),
            map(this.elaborateConfigUffInt),
            map(() => this.refreshTabs()),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm()),
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
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
        this.fromCreazione = get(paramsMap, 'fromCreazione');
        this.schedula = get(paramsMap, 'schedula');
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

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, {
                    utente: this.dettaglioReport,
                    userProfile: this.userProfile,
                    configUffInt: this.abilitazioneUffInt
                });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private loadForm(): void {

        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field
            };
        }

        let form: IDictionary<any> = {
            ...this.dettaglioReport,
            idRicerca: this.dettaglioReport.idRicerca,
            nome: this.dettaglioReport.nome,
            descrizione: this.dettaglioReport.descrizione ? this.dettaglioReport.descrizione : null,
            pubblicato: this.dettaglioReport.pubblicato,
            sysute: this.dettaglioReport.sysute,
            tuttiProfili: this.dettaglioReport.tuttiProfili,
            tuttiUffici: this.dettaglioReport.tuttiUffici,
            esponiComeServizio: this.dettaglioReport.esponiComeServizio,
            codReportWs: this.dettaglioReport.codReportWs,
            schedula: this.fromCreazione === 'S' ? this.schedula : this.dettaglioReport.schedula,
            cronExpression: this.dettaglioReport.cronExpression,
            formatoSchedulazione: this.dettaglioReport.formatoSchedulazione,
            emailSchedulazioneRisultato: this.dettaglioReport.emailSchedulazioneRisultato,
            noOutputVuoto: this.dettaglioReport.noOutputVuoto
        }

        this.sdkDettaglioReportStoreService.schedula = form.schedula;

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, form);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
    }

    private loadAbilitazioneUffInt = (): Observable<ResponseListaDTO<number>> => {

        return this.gestioneReportService.getAbilitazioneUffInt()
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

    private elaborateConfigUffInt = (result: ResponseListaDTO<number>) => {
        this.abilitazioneUffInt = result.response;
    }

    private loadDettaglio = (): Observable<ResponseDTO<WRicercheDTO>> => {

        return this.gestioneReportService.getDetailReport(this.sdkDettaglioReportStoreService.idRicerca)
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

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
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

            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    //#endregion
}