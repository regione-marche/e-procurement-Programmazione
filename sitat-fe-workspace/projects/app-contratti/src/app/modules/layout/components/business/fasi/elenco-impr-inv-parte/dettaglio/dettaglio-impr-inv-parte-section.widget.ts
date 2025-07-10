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
    ProtectionUtilsService,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, head, isEmpty, isEqual, isObject, isString, set, toString } from 'lodash-es';
import { GaraEntry } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';
import { SmartCigEntry } from 'projects/app-contratti/src/app/modules/models/smartcig/smartcig.model';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { SmartCigService } from 'projects/app-contratti/src/app/modules/services/smartcig/smartcig.service';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseImpresaEntry, RuoloImpresa } from '../../../../../../models/fasi/elenco-impr-inv-parte.model';
import { DettaglioImpresaInvitataPartecipanteStoreService } from '../dettaglio-impr-inv-parte-store.service';


@Component({
    templateUrl: `dettaglio-impr-inv-parte-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioImpresaInvitataPartecipanteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-impresa-invitata-partecipante-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsRO: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private num: string;
    private numRagg: string;
    private impresa: FaseImpresaEntry;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private modalConfig: any;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private gara: GaraEntry;    
    private smartCig: SmartCigEntry;
    private isImpresaAgg:boolean = false;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.elaborateImpresa),
            map(() => this.checkInfoBox()),            
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            map(() => this.refreshBreadcrumb()),
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

    private get dettaglioImprInvParteStoreService(): DettaglioImpresaInvitataPartecipanteStoreService { return this.injectable(DettaglioImpresaInvitataPartecipanteStoreService) }

    private get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get smartCigService(): SmartCigService { return this.injectable(SmartCigService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: {
                    ...config.modalComponentConfig,
                    impresa: config.textModalContent
                }
            };
            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void { }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            num: this.num,
            numRagg: this.numRagg,
            impresa: this.impresa,
            smartCig: this.gara.smartCig,
            setUpdateState: this.setUpdateState
        }).subscribe();
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.num = paramsMap.get('num');
        this.numRagg = paramsMap.get('numRagg');
        this.fromLS = paramsMap.get('fromLS');
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: FaseImpresaEntry | RuoloImpresa, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if(field.code === 'partecipante' && this.isImpresaAgg){
                field.visible = false;
                mapping = false;
            }

            if (field.code === 'denominazione-impresa-singola' && (<FaseImpresaEntry>restObject).tipologiaSoggetto === 3) {
                let ruoloImpresa: RuoloImpresa = head((<FaseImpresaEntry>restObject).imprese);
                if (isObject(ruoloImpresa) && isObject(ruoloImpresa.impresa)) {
                    field.data = ruoloImpresa.impresa.ragioneSociale;
                    set(field, 'textModalContent', ruoloImpresa.impresa);
                    mapping = false;
                }
            } else if (field.code === 'denominazione-impresa' || field.code === 'denominazione-impresa2') {
                set(field, 'textModalContent', (<RuoloImpresa>restObject).impresa);
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.impresa);

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

    private elaborateImpresa = () => {
        this.impresa = cloneDeep(this.dettaglioImprInvParteStoreService.impresa);
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsRO, this.userProfile.configurations)
        }; 
        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }
    }

    private showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if(this.gara.readOnly || this.gara.pcp){
                this.buttonsSubj.next(this.buttonsRO);
            } else{            
                this.buttonsSubj.next(this.buttons);            
            } 
        }
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

    private dettaglioSmartCigFactory(codGara: string): () => Observable<SmartCigEntry> {
        return () => {
            return this.smartCigService.dettaglioSmartCig(codGara);
        }
    }

    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        this.showButtons();
        if(this.gara.smartCig === true){            
            this.config.breadcrumbs =  this.config.breadcrumbsSmartCig;
            this.breadcrumbs.emit(this.config.breadcrumbsSmartCig);
            this.requestHelper.begin(this.dettaglioSmartCigFactory(this.codGara), this.messagesPanel).subscribe((res: SmartCigEntry)=>{
                this.smartCig = res;
                if(this.smartCig != null && (this.smartCig.modalitaRealizzazione === 11 || (this.smartCig.sceltaContraente == 18 || this.smartCig.sceltaContraente == 31))){                       
                    this.isImpresaAgg = true;    
                    this.config.sectionTitle = 'TITLE.DETT-IMPRESE-AGGIUDICATARIE-PAGE';  
                    this.markForCheck();    
                    this.loadForm();                            
                    } else{
                        this.loadForm();
                    }
                });
            } else{
                this.loadForm();
            }             
        }    

        private refreshBreadcrumb = () => {
            if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
                this.breadcrumbs.emit(this.config.breadcrumbsLS);
            }
            else {
                this.breadcrumbs.emit(this.config.breadcrumbs);
            }
        }

    // #endregion
}
