import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { StazioneAppaltanteInfo } from "@maggioli/app-commons";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { isEmpty, map as mapArray, isEqual } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, catchError, map, of, throwError } from 'rxjs';
import { mergeMap, single } from 'rxjs/operators';
import { MenuFieldTabellato, ResponseDTO, ValoreTabellato } from "../../model/lib.model";
import { WRicParamDTO } from '../../model/lista-params.model';
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneParametriService } from '../../services/gestione-parametri.service';
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkDettaglioReportStoreService } from "../sdk-dati-generali-report/sdk-dati-generali-report-store.service";

@Component({
    templateUrl: './sdk-inserisci-parametri-report.component.html',
    styleUrls: ['./sdk-inserisci-parametri-report.component.scss']
})
export class SdkInserisciParametriReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    //#region Variables

    @HostBinding('class') classNames = `sdk-inserisci-parametri-page-section`;

    @ViewChild('messages') _messagePanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObjs: Observable<SdkDialogConfig>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    public userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private dialogConfig: SdkDialogConfig;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    public valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private warning: string;
    private codice: string;
    private idRicerca: number;
    public listaParamsFromWRicParam: Array<WRicParamDTO>;
    public stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private idProfilo: string;
    private syscon: number;
    private fromDefinizione: boolean;
    private menuField: Array<string>;
    public menuFieldTabellato: MenuFieldTabellato[] = [];
    private fromPredefiniti: boolean;

    //#endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef){
        super(inj, cdr);
    }

    //#region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(SdkGestioneReportConstants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.setUpdateState(true);
        
        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadWarning();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadListaParamsFromWRicParam),
            map(this.elaborateListaParamFromWRicParam),
            map(this.getMenuFieldIfPresent),
            map(() => this.checkInfoBox())
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get gestioneParametriService(): GestioneParametriService { return this.injectable(GestioneParametriService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

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
        this.idRicerca = +paramsMap.get('idRicerca');
        this.codice = paramsMap.get('codice');
        this.fromDefinizione = paramsMap.get('fromDefinizione')?.toLowerCase() === "true";
        this.fromPredefiniti = paramsMap.get('fromPredefiniti')?.toLowerCase() === "true";
        this.syscon = +this.userProfile?.syscon;
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

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }
    
    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadListaParamsFromWRicParam = (): Observable<ResponseDTO<Array<WRicParamDTO>>> => {
        return this.gestioneParametriService.getListaParametriReport(this.sdkDettaglioReportStoreService.idRicerca, +this.userProfile.syscon)
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

    private elaborateListaParamFromWRicParam = (result: ResponseDTO<Array<WRicParamDTO>>) => {
        this.listaParamsFromWRicParam = result.response;
        this.allParamsFromStringToDate();
    }

    private getMenuFieldIfPresent = () => {
        for (let singleParam of this.listaParamsFromWRicParam) {
            if (singleParam.tipo === "M" && singleParam.menuField) {
                const menuFields: string[] = singleParam.menuField.split("|");
                let i = 0;
                let elem: any = {};
                for (let i = 0; i < menuFields.length; i++) {
                    const [codice, descrizione] = menuFields[i].split(',');
                    elem = {
                        codice: +codice,
                        descrizione: descrizione.trim()
                    };

                    this.menuFieldTabellato.push(elem);
                    if(singleParam.value === codice) {
                        singleParam.value = elem.descrizione;
                    }
                }
            }
        }
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

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(SdkGestioneReportConstants.TIPO_PARAMETRI_REPORTS_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    //Le date, se presenti nella lista di parametri, hanno bisogno di essere convertite in formato Date 
    //per poter essere usate nell'ngModel del p-calendar.
    private allParamsFromStringToDate () {
        for(let singleParam of this.listaParamsFromWRicParam){
            if(singleParam.tipo === 'D'){
                let dateToConvert: string = singleParam?.value; 
                singleParam.value = new Date(dateToConvert);
            }
        }
    }

    private cleanParams(){
        
        for(let singleParam of this.listaParamsFromWRicParam){
            singleParam.value = null;
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
                form: this.listaParamsFromWRicParam,
                syscon: this.syscon,
                idProfilo: this.idProfilo,
                nomeReport: this.sdkDettaglioReportStoreService.nome,
                fromDefinizione: this.fromDefinizione,
                fromPredefiniti: this.fromPredefiniti
            };

            if (button.code === 'back-def-or-param-list') {
                this.back(button, data);
            } 
            else if(button.code === 'reimposta-inserimento-params'){
                this.cleanParams();
            }
            else {

                for(let param of this.listaParamsFromWRicParam) {
                    if(param.tipo === 'UI') {
                        if(this.stazioneAppaltanteInfo?.nome === 'Tutte le Stazioni Appaltanti') {
                            param.value = '*';
                        }
                        else {
                            param.value = this.stazioneAppaltanteInfo?.codice
                        }
                    }
                    else if (param.tipo === 'UC') {
                        param.value = this.userProfile?.syscon
                    }
                    else if (param.tipo === 'M' && (param.value != null || param.value != undefined)) {
                        param.value = this.menuFieldTabellato.find((item) => item.descrizione === param.value).codice;
                    }
                }

                let data: IDictionary<any> = {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    setUpdateState: this.setUpdateState,
                    idRicerca: this.idRicerca,
                    codice: this.codice,
                    form: this.listaParamsFromWRicParam,
                    syscon: this.syscon,
                    idProfilo: this.idProfilo,
                    nomeReport: this.sdkDettaglioReportStoreService.nome,
                    fromDefinizione: this.fromDefinizione,
                    fromPredefiniti: this.fromPredefiniti
                };

                //Controllo per parametri obbligatori.
                if(!this.loadErrorOnMandatoryParams(this.listaParamsFromWRicParam)){

                    this.sdkMessagePanelService.showError(this.messagesPanel, [
                        {
                            message: 'SDK-DETTAGLIO-REPORT.VALIDATORS.PARAMETRI-OBBLIGATORIO'
                        }
                    ]);
                    return;
                }

                //Esecuzione normale nel provider 
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public getDescrizione(tipo: string): string {
        const result = this.valoriTabellati.tipoParametroReport.find(t => t.codice === tipo);
        return result ? result.descrizione : 'SDK-DETTAGLIO-REPORT.PARAMETRI.TIPO-SCONOSCIUTO';
    }

    private loadErrorOnMandatoryParams(params: Array<WRicParamDTO>){

        for(let param of params){
            if(param.obbligatorio === "Si" && (param.value === undefined || param.value === null || param.value === "")){

                return false;
            }
        }

        return true;
    }

    //#endregion
}