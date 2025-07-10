import { HttpErrorResponse } from "@angular/common/http";
import { ChangeDetectorRef, Component, ElementRef, HostBinding, Injector, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkDialogConfig, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkMenuTab, SdkMessagePanelService, SdkMessagePanelTranslate } from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, get, isEmpty, isString, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, catchError, map, mergeMap, of, throwError } from "rxjs";
import { CustomParamsFunction, ResponseDTO, ValoreTabellato } from "../../model/lib.model";
import { WRicParamDTO } from '../../model/lista-params.model';
import { SdkGestioneReportConstants } from "../../sdk-gestione-report.constants";
import { GestioneParametriService } from "../../services/gestione-parametri.service";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkDettaglioReportStoreService } from "../sdk-dati-generali-report/sdk-dati-generali-report-store.service";

@Component({
    templateUrl: './sdk-dettaglio-parametro-report.component.html',
    styleUrls: []
})
export class SdkDettaglioParametroReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy{

    //#region Variables

    @HostBinding('class') classNames = `sdk-dettaglio-parametro-page-section`;

    @ViewChild('messages') _messagePanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObjs: Observable<SdkDialogConfig>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    private idRicerca: number;
    private codice: string;
    private dialogConfig: SdkDialogConfig;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private menuTabs: Array<SdkMenuTab>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private dettaglioParametro: WRicParamDTO;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private menuField: Array<string>;

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
        this.loadTabellati()
        .pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadDettaglioParam),
            map(this.elaborateDettaglio),
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

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }
 
    private get gestioneParametriService(): GestioneParametriService { return this.injectable(GestioneParametriService) }

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

        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let formDettaglio: IDictionary<any> = {
            ...this.dettaglioParametro,
            idRicerca: this.dettaglioParametro.idRicerca,
            nome: this.dettaglioParametro.nome,
            codice: this.dettaglioParametro.codice,
            descrizione: this.dettaglioParametro.descrizione,
            tipo: this.valoriTabellati.tipoParametroReport.find(item => item.codice === this.dettaglioParametro.tipo)?.descrizione,
            obbligatorio: this.dettaglioParametro.obbligatorio,
            progressivo: this.dettaglioParametro.progressivo,
            codiceTabellato: this.dettaglioParametro.codiceTabellato,
            menuField: this.dettaglioParametro.menuField
        }

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(undefined, field, key, mapping);
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field
            };
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, formDettaglio);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
    }

    private loadDettaglioParam = (): Observable<ResponseDTO<WRicParamDTO>> => {
        return this.gestioneParametriService.getDetailParamReport(this.idRicerca, this.codice)
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
    
    private elaborateDettaglio = (result: ResponseDTO<WRicParamDTO>) => {
        this.dettaglioParametro = result.response;
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(SdkGestioneReportConstants.TIPO_PARAMETRI_REPORTS_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
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
                codice: this.sdkDettaglioReportStoreService.codice
            };

            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    //#endregion
}