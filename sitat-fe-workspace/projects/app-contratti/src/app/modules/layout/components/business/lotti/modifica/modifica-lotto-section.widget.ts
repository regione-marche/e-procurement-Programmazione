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
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {
    cloneDeep,
    each,
    find,
    get,
    has,
    isEmpty,
    isEqual,
    isObject,
    isString,
    remove,
    set,
    sum,
    toString,
} from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { GaraEntry, LottoDynamicValue, LottoEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';


@Component({
    templateUrl: `modifica-lotto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaLottoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-lotto-section`;

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
    private gara: GaraEntry;
    private lotto: LottoEntry;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private comuniMap: IDictionary<ValoreTabellato> = {};
    private provinceMap: IDictionary<ValoreTabellato> = {};

    private from: string;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            map(this.loadLottoFactory),
            mergeMap(this.loadLotto),
            map(this.elaborateLotto)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
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

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

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

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'importo-lotto' && field.type === 'NUMERIC-TEXTBOX') {
                this.calcoloImportoTotale(field, 'economics-data', 'importo-attuazione-sicurezza', 'importo-totale');
            } else if (field.code === 'importo-attuazione-sicurezza' && field.type === 'NUMERIC-TEXTBOX') {
                this.calcoloImportoTotale(field, 'economics-data', 'importo-lotto', 'importo-totale');
            } else if (field.code === 'cpv') {
                if (field.clear === true || (field.clear !== true && isObject(field.modalContent))) {
                    this.formBuilderDataSubject.next({
                        code: 'descrizione-cpv',
                        data: isObject(field.modalContent) ? get(field, 'modalContent.label') : ''
                    });
                }
            } else if (field.code === 'cpv-secondario') {
                if (field.clear === true || (field.clear !== true && isObject(field.modalContent))) {
                    this.formBuilderDataSubject.next({
                        code: 'descrizione-cpv-secondario',
                        data: isObject(field.modalContent) ? get(field, 'modalContent.label') : '',
                        groupCode: field.groupCode
                    });
                }
            } else if (field.code === 'tipo-appalto' || field.code === 'importo-totale' || field.code === 'tipo-settore') {
                this.elaborateCondizioniRicorso(field);
            }
        }
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, 'component'),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, 'fields'),
                    buttons: get(this.infoBoxModalConfig, 'buttons'),
                    userProfile: this.userProfile,
                    mnemonico: item.mnemonico
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
    }

    // #endregion

    // #region Private

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
            setUpdateState: this.setUpdateState,            
            from: this.from
        };

        if (button.code === 'back-to-dettaglio-lotto' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
            this.back(button, data);
        } else {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.from = paramsMap.get('from');
        
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        if( this.lotto.qualificazioneSa === null &&
            this.lotto.categoriaMerceologica === null &&
            this.lotto.flagPrevedeRip === null &&
            this.lotto.durataRinnovi === null &&
            this.lotto.motivoCollegamento === null &&
            this.lotto.cigOrigineRip === null &&
            this.lotto.flagPnrrPnc === null &&
            this.lotto.flagPrevisioneQuota === null &&
            this.lotto.flagMisurePreliminari === null &&
            this.lotto.dataScadenzaPagamenti){
                remove(fields, (one: SdkFormBuilderField) => one.code === 'altri-dati');
        }

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'art-e1' || field.code === 'id-scelta-contraente-50') {
                // set visible
                field.visible = this.gara.versioneSimog <= 2;
            } else if (field.code === 'id-modo-gara') {
                if (this.gara.versioneSimog === 1) {
                    field.listCode = 'CriterioAggiudicazioneSimog1';
                }
            } else if (field.code === 'in-opera-manodopera') {
                field.visible = this.lotto.tipologia !== 'L';
            } else if (field.code === 'condizioni-procedura-negoziata-data') {
                let visible: boolean = false;
                if (this.gara.versioneSimog === 1) {
                    visible = true;
                }
                if (visible === false) {
                    field.visibleCondition = undefined;
                }
            } else if (field.code === 'luogo-istat' && field.type === 'AUTOCOMPLETE') {
                if (has(this.comuniMap, get(restObject, field.mappingInput))) {
                    const comune: ValoreTabellato = get(this.comuniMap, get(restObject, field.mappingInput));
                    const istatProvincia: string = `0${comune.codistat.substring(3, 6)}`;
                    let item: SdkAutocompleteItem = {
                        ...comune,
                        text: `${comune.descrizione} - ${this.provinceMap[istatProvincia].descrizione} (${comune.codistat})`,
                        _key: comune.codistat
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            } else if (field.code === 'contratto-escluso-alleggerito' || field.code === 'esclusione-regime-speciale') {
                field.visible = this.gara.versioneSimog > 2;
            }  else if(field.code === 'qualificazioneSa' 
                        || field.code === 'categoriaMerceologica'
                        || field.code === 'flagPrevedeRip'
                        || field.code === 'durataRinnovi'
                        || field.code === 'motivoCollegamento'
                        || field.code === 'cigOrigineRip'
                        || field.code === 'flagPnrrPnc'
                        || field.code === 'flagPrevisioneQuota'
                        || field.code === 'flagMisurePreliminari'
                        || field.code === 'dataScadenzaPagamenti'){
                let value = get(restObject, field.mappingInput);
                if(value == null || (isString(value) && isEmpty(value))){
                    field.visible = false;
                    mapping = false;
                }
            }

            if (field.code === 'dataScadenzaPagamenti' && field.visible !== false) {    
                let value = get(restObject, field.mappingInput);                
                if (value != null && field.type === 'TEXT') {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    key = field.data;
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

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.lotto, {
            provinceMap: this.provinceMap
        }, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
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

    private dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.LOTTO_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result
    }

    private loadComuni = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI);
    }

    private elaborateComuni = (comuni: Array<ValoreTabellato>) => {
        each(comuni, (comune: ValoreTabellato) => {
            this.comuniMap[comune.codistat] = comune;
        });
    }

    private loadProvince = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE);
    }

    private elaborateProvince = (province: Array<ValoreTabellato>) => {
        each(province, (provincia: ValoreTabellato) => {
            this.provinceMap[provincia.codistat] = provincia;
        });
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
    };

    private loadLottoFactory = (): Function => {
        let factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return factory;
    }

    private loadLotto = (factory: Function): Observable<LottoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
        this.checkInfoBox();
        this.loadForm();
    };

    private calcoloImportoTotale(field: SdkFormBuilderField, sectionCode: string, otherFieldCode: string, sumFieldCode: string): void {
        let currentValue: number = field.data;
        let section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === sectionCode);
        let otherField: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === otherFieldCode);
        let otherValue: number = isObject(otherField) && otherField.data ? otherField.data : 0;
        let finalValue: number = sum([currentValue, otherValue]);
        this.formBuilderDataSubject.next({
            code: sumFieldCode,
            data: finalValue
        });
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

    private elaborateCondizioniRicorso(field: SdkFormBuilderField): void {

        let tipoContratto: string;
        if (field.code === 'tipo-appalto') {
            tipoContratto = field.data != null ? field.data.key : null;
        } else {
            const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'general-data');
            const otherField: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === 'tipo-appalto');
            const otherValue: string = otherField.data != null ? otherField.data.key : null;
            tipoContratto = otherValue;
        }

        let importo: number;
        if (field.code === 'tipo-appalto') {
            importo = field.data != null ? field.data : null;
        } else {
            const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'economics-data');
            const otherField: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === 'importo-totale');
            const otherValue: number = otherField.data != null ? otherField.data : null;
            importo = otherValue;
        }

        let tipoSettore: string = this.gara.tipoSettore;

        let keysToRemove: Array<number> = new Array();

        if (tipoContratto === 'L') {
            // Se TIPO_CONTRATTO = 'L' nascondere i valori 4, 5, 6, 7, 8, 9, 10, 11
            keysToRemove = keysToRemove.concat([4, 5, 6, 7, 8, 9, 10, 11]);
        } else if (tipoContratto === 'F') {
            // Se TIPO_CONTRATTO = 'F' nascondere i valori 1, 8, 9, 10, 11, 27, 31
            keysToRemove = keysToRemove.concat([1, 8, 9, 10, 11, 27, 31]);
        } else if (tipoContratto === 'S') {
            // Se TIPO_CONTRATTO = 'S' nascondere i valori 1, 4, 5, 6, 7, 27, 31
            keysToRemove = keysToRemove.concat([1, 4, 5, 6, 7, 27, 31]);
        }

        if (importo >= 1000000) {
            // Se IMPORTO_TOT >= 1000000 nascondere il valore 1
            keysToRemove = keysToRemove.concat([1]);
        }

        if (tipoSettore === 'O') {
            // Se W9LOTT.FLAG_ENTE_SPECIALE= 'O' nascondere i valori 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24
            keysToRemove = keysToRemove.concat([12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 23, 24]);
        }

        let values: Array<LottoDynamicValue> = cloneDeep(this.lotto.condizioniRicorsoProcNeg);
        if (keysToRemove.length > 0) {
            remove(values, (one: LottoDynamicValue) => {
                return keysToRemove.includes(one.codice);
            });
        }
        this.formBuilderDataSubject.next({
            code: 'condizioni-procedura-negoziata-data',
            dynamicFieldValues: values != null ? values : []
        });
    }

    // #endregion
}
