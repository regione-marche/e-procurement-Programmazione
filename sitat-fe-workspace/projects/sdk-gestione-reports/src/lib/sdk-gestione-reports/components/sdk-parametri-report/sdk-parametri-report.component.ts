import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { FormBuilderUtilsService, ProtectionUtilsService, TabellatiCacheService } from "@maggioli/app-commons";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { 
    SdkButtonGroupInput, 
    SdkButtonGroupOutput, 
    SdkDialogConfig, 
    SdkFormBuilderConfiguration, 
    SdkFormBuilderField, 
    SdkMenuTab, 
    SdkMessagePanelService, 
    SdkMessagePanelTranslate 
} from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, isEmpty, isString, map as mapArray, max, min, remove } from "lodash-es";
import { BehaviorSubject, Observable, Subject, catchError, map, of, throwError } from "rxjs";
import { mergeMap } from 'rxjs/operators';
import { CustomParamsFunctionResponse, ResponseDTO, ResponseListaDTO, ValoreTabellato } from "../../model/lib.model";
import { WRicParamDTO } from "../../model/lista-params.model";
import { WRicercheDTO } from "../../model/lista-report.model";
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneParametriService } from "../../services/gestione-parametri.service";
import { GestioneReportService } from "../../services/gestione-report.service";
import { SdkDettaglioReportStoreService } from "../sdk-dati-generali-report/sdk-dati-generali-report-store.service";

@Component({
    templateUrl: './sdk-parametri-report.component.html',
    styleUrls: ['./sdk-parametri-report.component.scss']
})
export class SdkParametriReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    //#region Variables

    @HostBinding('class') classNames = `sdk-parametri-report-section`;

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
    public listaParamsFromWRicParam: Array<WRicParamDTO>;
    private idRicerca: number;
    private codice: string;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private dettaglioReport: WRicercheDTO;
    private progrArray: Array<number> = [];
    private minProgrArray: number;
    private maxProgrArray: number;
    private numParams: number;
    private idProfilo: string;
    private abilitazioneUffInt: number;
    private showWarning: boolean;
    private menuField: Array<String>;
    private mandatoryParamsNotUsed: Array<string>;

    //#endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef){
        super(inj, cdr);
    }

    //#region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            this.syscon = +userProfile.syscon;
        }));
        
        this.initDialog();
        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {

        if(this.showWarning){
            this.showWarningDisabledButton();
        }
        this.checkInfoBox();
        this.reloadGrid();
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

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get gestioneParametriService(): GestioneParametriService { return this.injectable(GestioneParametriService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    // #endregion

    //#region Private 

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        //Piccolo controllo per disabilitare bottone di aggiunta parametro in caso di schedulazione del report. 
        if(this.sdkDettaglioReportStoreService.schedula === '1'){
            let button = this.buttons.buttons.find(addParam => addParam.code === "add-parametro-report");
            
            button.disabled = true;
            this.showWarning = true;
        }
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private showWarningDisabledButton(): void{
        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
            {
                message: 'SDK-DETTAGLIO-REPORT.PARAMETRI.AGGIUNGI-PARAMETRO.DISABLED-WARNING'
            }
        ], true);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idRicerca = +paramsMap.get('idRicerca');
        this.codice = paramsMap.get('codice');
        this.idProfilo = this.userProfile?.configurations?.idProfilo;//paramsMap.get('idProfilo');
        this.syscon = +this.userProfile?.syscon;
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
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

        let form: IDictionary<any> = {
            isReportSchedulato: this.dettaglioReport.schedula === "1" ? true : false
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.valoriTabellati.tipoParametroReport, form);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
    }    

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
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

    private loadListaParamsFromWRicParam = (): Observable<ResponseListaDTO<Array<WRicParamDTO>>> => {

        return this.gestioneParametriService.getListaParametriReport(this.sdkDettaglioReportStoreService.idRicerca, this.syscon)
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
        this.mandatoryParamsNotUsed = result.mandatoryParamsNotUsed;

        if(this.mandatoryParamsNotUsed?.length > 0){

            let mandatoryParamsString: string = this.mandatoryParamsNotUsed.join(", ");

            this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                {
                    message: this.translateService.instant('SDK-DETTAGLIO-REPORT.PARAMETRI.MANDATORY-PARAMS-NOT-USED') + mandatoryParamsString
                }
            ], true);
        }
    }

    private reloadGrid = () => {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadListaParamsFromWRicParam),
            map(this.elaborateListaParamFromWRicParam),
            map(this.calcMinMaxProgrArray),
            mergeMap(this.loadDettaglioReport),
            map(this.elaborateDettaglioReport),
            mergeMap(this.loadAbilitazioneUffInt),
            map(this.elaborateConfigUffInt),
            map(() => this.refreshTabs()),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm())
        ).subscribe(); 
    }

    private calcMinMaxProgrArray = () => {
        this.progrArray = [];
        
        for(let param of this.listaParamsFromWRicParam){
            this.progrArray = [...this.progrArray, param.progressivo];
        }

        this.minProgrArray = min(this.progrArray);
        this.maxProgrArray = max(this.progrArray);
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(SdkGestioneReportConstants.TIPO_PARAMETRI_REPORTS_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadDettaglioReport = (): Observable<ResponseDTO<WRicercheDTO>> => {

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
    
    private elaborateDettaglioReport = (result: ResponseDTO<WRicercheDTO>) => {
        this.dettaglioReport = result.response;
    }

    private deleteParametroConfirm(codice: string): any {
        return () => {
            this.provider.run('SDK_GESTIONE_REPORT_LISTA_REPORT', {
                action: 'DELETE-PARAM-REPORT',
                codice,
                idRicerca: this.idRicerca,
                messagesPanel: this.messagesPanel,
                syscon: this.syscon,
                idProfilo: this.idProfilo
            }).subscribe(this.reloadGrid); 
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
                idRicerca: this.idRicerca,
                codice: this.codice,
                numParams: this.numParams,
                form: this.listaParamsFromWRicParam,
                nomeReport: this.dettaglioReport?.nome,
                fromDefinizione: false,
                menuField: this.menuField
            };

            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public loadDettaglioParamReport(codice: string): void{
        this.provider.run('SDK_GESTIONE_REPORT_LISTA_REPORT', {
            action: 'DETAIL-PARAM',
            idRicerca: this.idRicerca,
            codice: codice,
            menuField: this.menuField,
            setUpdateState: this.setUpdateState,
            messagesPanel: this.messagesPanel
        }).subscribe();
    }

    public performModalOnCancel(codice: string) {
        let func = this.deleteParametroConfirm(codice);
        this.dialogConfig.open.next(func);
    }

    public moveRowParamUp(codice: string){

        return this.gestioneReportService.moveRowUpParam(this.idRicerca, codice)
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

    public moveRowParamDown(codice: string){

        return this.gestioneReportService.moveRowDownParam(this.idRicerca, codice)
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

    public isFirst(progressivo: number){
        return progressivo === this.minProgrArray;
    }

    public isLast(progressivo: number){
        return progressivo === this.maxProgrArray;
    }

    public getParamTipo(tipo: string){

        switch(tipo) {
            case 'S': 
                return 'Stringa';
            case 'T':
                return 'Dato tabellato';
            case 'F':
                return 'Numero con virgola';
            case 'I':
                return 'Intero';
            case 'D':
                return 'Data';
            case 'UC':
                return 'Identificativo utente';
            case 'UI':
                return 'Ufficio intestatario';
            default:
                return tipo;
        }
    }

    //#endregion
}