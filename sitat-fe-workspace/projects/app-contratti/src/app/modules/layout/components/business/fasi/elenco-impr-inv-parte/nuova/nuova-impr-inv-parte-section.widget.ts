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
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProfiloConfigurationItem,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
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
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isEqual, isObject, isString, reduce, toString } from 'lodash-es';
import { GaraEntry } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';
import { SmartCigEntry } from 'projects/app-contratti/src/app/modules/models/smartcig/smartcig.model';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { SmartCigService } from 'projects/app-contratti/src/app/modules/services/smartcig/smartcig.service';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';


@Component({
    templateUrl: `nuova-impr-inv-parte-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaImpresaInvitataPartecipanteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `nuova-impresa-invitata-partecipante-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private updateDaexport = false;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private modalConfig: any;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private gara: GaraEntry;
    private smartCig: SmartCigEntry;
    private isImpresaAgg:boolean = false;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();  
        

        
        let protezioniMap = this.getProtezioniMap();
        let key:string = 'FUNZ.VIS.ALT.W9.W9LOTT-SMARTCIG-SALVAINVIA';
        if (has(protezioniMap, key)) {
            let protValue: ProfiloConfigurationItem = get(protezioniMap, key);
            this.updateDaexport = protValue.valore;
        }
    }

    private getProtezioniMap(): IDictionary<ProfiloConfigurationItem> {
        let protectionMap: IDictionary<ProfiloConfigurationItem> = reduce(this.userProfile.configurations.configurazioni, (map: IDictionary<any>, one: ProfiloConfigurationItem) => {
            map[one.key] = one;
            return map;
        }, {});
        return protectionMap;
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(() => this.checkInfoBox()),
            map(this.getGara),
            //map(() => this.loadForm())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    // #endregion

    // #region Public

    public getGara = () => {
        let factory = this.loadGaraFactory();
        this.loadGara(factory).subscribe((result: GaraEntry) => {
            this.gara = result;
            if (this.gara.smartCig) {
                this.requestHelper.begin(this.dettaglioSmartCigFactory(this.codGara), this.messagesPanel).subscribe((res: SmartCigEntry)=>{
                    this.smartCig = res;
                    if(this.smartCig != null && (this.smartCig.modalitaRealizzazione === 11 || (this.smartCig.sceltaContraente == 18 || this.smartCig.sceltaContraente == 31))){                       
                        this.isImpresaAgg = true;    
                        this.config.sectionTitle = 'TITLE.NUOVA-IMPRESE-AGGIUDICATARIE-PAGE';  
                        this.markForCheck();     
                        this.loadForm();                            
                    } else{
                        this.loadForm();
                    }
                });
            } else{
                this.loadForm();
            }
        });
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageFormClick(_config: SdkTextOutput): void { }

    public manageModalOutput(_item: SdkModalOutput<any>): void { }

    // #endregion

    // #region Private

    private dettaglioSmartCigFactory(codGara: string): () => Observable<SmartCigEntry> {
        return () => {
            return this.smartCigService.dettaglioSmartCig(codGara);
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {

        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            smartCig: this.gara.smartCig,
            isImpresaAgg: this.isImpresaAgg,
            updateDaexport: this.updateDaexport,
            setUpdateState: this.setUpdateState
        };

        if (button.code === 'back-to-lista-impr-inv-parte' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
            this.back(button, data);
        } else {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
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

            if(field.code === 'partecipante' && this.isImpresaAgg){
                field.visible = false;
                mapping = false;
            }

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

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction, undefined, {
            stazioneAppaltante: { codice : this.gara.codiceStazioneAppaltante }
        });

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);

        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
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

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.ELENCO_IMPRESE_INVITATE_PARTECIPANTI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
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

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private loadGara = (factory: Function): Observable<GaraEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        
    }

    // #endregion
}
