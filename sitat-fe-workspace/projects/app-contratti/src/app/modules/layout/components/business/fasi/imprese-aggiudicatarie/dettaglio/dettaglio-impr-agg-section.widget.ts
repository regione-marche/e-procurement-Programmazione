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
    SdkBasicButtonInput,
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
import { cloneDeep, filter, get, head, isEmpty, isEqual, isObject, isString, set, split, toString, trim } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { GaraEntry } from '../../../../../../models/gare/gare.model';
import {
    ImpresaAggiudicatariaEntry,
    ImpresaAggiudicatariaRaggEntry,
} from '../../../../../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';
import { GareService } from '../../../../../../services/gare/gare.service';
import { DettaglioFaseStoreService } from '../../dettaglio-fase-store.service';
import { DettaglioImpresaAggiudicatariaStoreService } from '../dettaglio-impresa-aggiudicataria-store.service';


@Component({
    templateUrl: `dettaglio-impr-agg-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioImpresaAggiudicatariaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-impresa-aggiudicataria-section`;

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
    private idGruppo: string;
    private impresa: ImpresaAggiudicatariaRaggEntry;
    private tipoDocumento: string;
    private numPubb: string;
    private campiVisibili: Array<string>;
    private campiVisibiliString: string;
    private campiObbligatori: Array<string>;
    private campiObbligatoriString: string;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private modalConfig: any;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private gara: GaraEntry;
    private atto: boolean = false;
    private current: boolean = true;
    private riaggiudicazione: boolean = false;
    private cigFiglio: boolean = false;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        if (this.dettaglioFaseStoreService.current != null) {
            this.current = this.dettaglioFaseStoreService.current;
        }

        if (this.dettaglioFaseStoreService.riaggiudicazione != null) {
            this.riaggiudicazione = this.dettaglioFaseStoreService.riaggiudicazione;
        }

        if (this.dettaglioFaseStoreService.cigFiglio != null) {
            this.cigFiglio = this.dettaglioFaseStoreService.cigFiglio;
        }

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            map(this.elaborateImpresa),
            map(() => this.showButtons()),
            map(() => this.refreshBreadcrumb())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.atto = this.config.body.atto;
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

    private get dettaglioImpresaAggiudicatariaStoreService(): DettaglioImpresaAggiudicatariaStoreService { return this.injectable(DettaglioImpresaAggiudicatariaStoreService) }

    private get gareService(): GareService { return this.injectable(GareService) }

    protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

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
            idGruppo: +this.idGruppo,
            impresa: this.impresa,
            tipoDocumento: this.tipoDocumento,
            numPubb: this.numPubb,
            campiVisibili: this.campiVisibiliString,
            campiObbligatori: this.campiObbligatoriString,
            riaggiudicazione: this.riaggiudicazione
        }).subscribe();
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.idGruppo = paramsMap.get('idGruppo');
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');
        this.fromLS = paramsMap.get('fromLS');
        let campiVisibili: string = paramsMap.get('campiVisibili');
        this.campiVisibiliString = campiVisibili;
        if (!isEmpty(campiVisibili)) {
            let trimmed: string = trim(campiVisibili, '|');
            this.campiVisibili = split(trimmed, '|');
        }
        let campiObbligatori: string = paramsMap.get('campiObbligatori');
        this.campiObbligatoriString = campiObbligatori;
        if (!isEmpty(campiObbligatori)) {
            let trimmed: string = trim(campiObbligatori, '|');
            this.campiObbligatori = split(trimmed, '|');
        }
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

            if (field.code === 'tipo-app') {
                field.data = this.gara.tipoApp;
                mapping = false;
            }

            if (field.code === 'nomest-singola' && ((<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 3 || (<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 5)) {
                let ruoloImpresa: ImpresaAggiudicatariaEntry = head((<ImpresaAggiudicatariaRaggEntry>restObject).imprese);
                if (isObject(ruoloImpresa) && isObject(ruoloImpresa.impresa)) {
                    field.data = ruoloImpresa.impresa.ragioneSociale;
                    set(field, 'textModalContent', ruoloImpresa.impresa);
                    mapping = false;
                }
            } else if (field.code === 'nomest-ausiliaria-singola' && ((<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 3 || (<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 5)) {
                let ruoloImpresa: ImpresaAggiudicatariaEntry = head((<ImpresaAggiudicatariaRaggEntry>restObject).imprese);
                if (isObject(ruoloImpresa) && isObject(ruoloImpresa.impresaAusiliaria)) {
                    field.data = ruoloImpresa.impresaAusiliaria.ragioneSociale;
                    set(field, 'textModalContent', ruoloImpresa.impresaAusiliaria);
                    mapping = false;
                }
            } else if (field.code === 'nomest') {
                set(field, 'textModalContent', (<ImpresaAggiudicatariaEntry>restObject).impresa);
            } else if (field.code === 'nomest-ausiliaria') {
                set(field, 'textModalContent', (<ImpresaAggiudicatariaEntry>restObject).impresaAusiliaria);
            } else if (field.code === 'flag-avvalimento-singolo' || field.code === 'flag-avvalimento-singolo-hidden' || field.code === 'importo-aggiudicazione-singola' || field.code === 'ribasso-aggiudicazione-singola' || field.code === 'offerta-aumento-singola') {
                const impresa: ImpresaAggiudicatariaEntry = head((<ImpresaAggiudicatariaRaggEntry>restObject).imprese);
                key = get(impresa, field.mappingInput);
                set(field, 'data', key);
                mapping = false;
            } else if (field.code === 'nome-legale-rappr-singola' && ((<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 3 || (<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 5)) {
                let ruoloImpresa: ImpresaAggiudicatariaEntry = head((<ImpresaAggiudicatariaRaggEntry>restObject).imprese);
                if (isObject(ruoloImpresa)) {
                    field.data = ruoloImpresa.nomeLegRap;                    
                    mapping = false;
                }
            } else if (field.code === 'cognome-legale-rappr-singola' && ((<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 3 || (<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 5)) {
                let ruoloImpresa: ImpresaAggiudicatariaEntry = head((<ImpresaAggiudicatariaRaggEntry>restObject).imprese);
                if (isObject(ruoloImpresa)) {
                    field.data = ruoloImpresa.cognomeLegRap;                    
                    mapping = false;
                }
            } else if (field.code === 'cf-legale-rappr-singola' && ((<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 3 || (<ImpresaAggiudicatariaRaggEntry>restObject).idTipoAgg === 5)) {
                let ruoloImpresa: ImpresaAggiudicatariaEntry = head((<ImpresaAggiudicatariaRaggEntry>restObject).imprese);
                if (isObject(ruoloImpresa)) {
                    field.data = ruoloImpresa.cfLegRap;                    
                    mapping = false;
                }
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, { ...this.impresa, tipoApp: this.gara.tipoApp });

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
        return this.tabellatiCacheService.getValoriTabellati(Constants.IMPRESE_AGGIUDICATARIE_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private elaborateImpresa = () => {
        this.impresa = cloneDeep(this.dettaglioImpresaAggiudicatariaStoreService.impresa);
        this.checkInfoBox();
        this.loadForm();
    };

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

        if (((this.atto == null || this.atto === false) && this.current === false) || this.cigFiglio === true) {
            this.buttons.buttons = filter(this.buttons.buttons, (one: SdkBasicButtonInput) => one.code !== 'go-to-update-impresa-aggiudicataria');
        }       
    }

    private showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if((this.gara.readOnly || this.gara.pcp) && (this.atto == null || this.atto === false)){
                this.buttonsSubj.next(this.buttonsRO);
            } else{            
                this.buttonsSubj.next(this.buttons);            
            }  
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
