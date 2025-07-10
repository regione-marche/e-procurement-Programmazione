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
    SdkFormFieldGroupConfiguration,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, find, findIndex, get, isEmpty, isEqual, isFinite, isObject, isString, sum } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { InterventoService } from '../intervento.service';


@Component({
    templateUrl: `nuovo-intervento-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovoInterventoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `nuovo-intervento-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private comuniMap: IDictionary<string> = {};
    private provinceMap: IDictionary<string> = {};
    private idProgramma: string;
    private tipologia: string;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private programma: ProgrammaEntry;
    private userProfile: UserProfile;
    private risorseDiCapitoloProtectionCode: string;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.initDialog();

        this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI).subscribe((comuni: Array<ValoreTabellato>) => {
            each(comuni, (comune: ValoreTabellato) => {
                this.comuniMap[comune.codistat] = comune.descrizione;
            });
        });

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.descrizione;
            });
        });

        this.tabellatiCacheService.getValoriTabellati(Constants.INTERVENTO_TABELLATI).subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
            this.valoriTabellati = result;
            this.loadQueryParams();
            this.checkInfoBox();
            this.loadProgramma();
        });
    }

    protected onAfterViewInit(): void { }

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
                this.risorseDiCapitoloProtectionCode = this.config.risorseDiCapitoloProtectionCode;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get interventoService(): InterventoService { return this.injectable(InterventoService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'valore-stimato-primo-anno') {
                this.elaborateImportoImmobile(field, 'IM1TRI', 'valore-stimato-secondo-anno', 'valore-stimato-annualita-successive', 'valore-stimato-terzo-anno');
            } else if (field.code === 'valore-stimato-secondo-anno') {
                this.elaborateImportoImmobile(field, 'IM2TRI', 'valore-stimato-primo-anno', 'valore-stimato-annualita-successive', 'valore-stimato-terzo-anno');
            } else if (field.code === 'valore-stimato-terzo-anno') {
                this.elaborateImportoImmobile(field, 'IM3TRI', 'valore-stimato-primo-anno', 'valore-stimato-secondo-anno', 'valore-stimato-annualita-successive');
            } else if (field.code === 'valore-stimato-annualita-successive') {
                this.elaborateImportoImmobile(field, 'IM9TRI', 'valore-stimato-primo-anno', 'valore-stimato-secondo-anno', 'valore-stimato-terzo-anno');
            } else if (field.code === 'comune-immobile') {
                this.manageIstat(field, 'provincia-immobile', 'codice-istat-immobile');
            } else if (field.code === 'comune') {
                this.manageIstat(field, 'provincia', 'codice-istat');
            } else if (field.code === 'acquisti-verdi-importo-netto-iva') {
                // calcolo campo AVIMPTOT                
                this.calculateSectionTotal(field, 'acquisti-verdi-data', 'acquisti-verdi-importo-iva', 'acquisti-verdi-importo-totale');
            } else if (field.code === 'acquisti-verdi-importo-iva') {
                // calcolo campo AVIMPTOT                
                this.calculateSectionTotal(field, 'acquisti-verdi-data', 'acquisti-verdi-importo-netto-iva', 'acquisti-verdi-importo-totale');
            } else if (field.code === 'materiali-riciclati-importo-netto-iva') {
                // calcolo campo MRIMPTOT                
                this.calculateSectionTotal(field, 'materiali-riciclati-data', 'materiali-riciclati-importo-iva', 'materiali-riciclati-importo-totale');
            } else if (field.code === 'materiali-riciclati-importo-iva') {
                // calcolo campo MRIMPTOT                
                this.calculateSectionTotal(field, 'materiali-riciclati-data', 'materiali-riciclati-importo-netto-iva', 'materiali-riciclati-importo-totale');
            } else if (field.code === 'acquisti-verdi-cpv') {
                this.formBuilderDataSubject.next({
                    code: 'acquisti-verdi-descrizione-cpv',
                    data: isObject(field.modalContent) ? get(field, 'modalContent.label') : ''
                });
            } else if (field.code === 'materiali-riciclati-cpv') {
                this.formBuilderDataSubject.next({
                    code: 'materiali-riciclati-descrizione-cpv',
                    data: isObject(field.modalContent) ? get(field, 'modalContent.label') : ''
                });
            } else if (field.code === 'anno-riferimento') {
                let value: string = field.data.key === '1' ? '1' : '2';
                this.formBuilderDataSubject.next({
                    code: 'annint',
                    data: value
                });
            }
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                tipologia: this.tipologia,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState
            };

            if (button.code === 'back-to-dett-prog' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageAddPanel(data: boolean): void {
        this.recalculateMatrix();
    }

    public manageRemovePanel(data: boolean): void {
        this.recalculateMatrix();
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {

                let fields: Array<SdkFormBuilderField>;

                if (this.tipologia === '1') {
                    fields = get(this.config, 'body.lavoriFields')
                } else if (this.tipologia === '2') {
                    if(this.programma.norma === 3){
                        fields = get(this.config, 'body.acquistiFieldsNorma3')
                    } else{
                        fields = get(this.config, 'body.acquistiFields')
                    }
                }

                let formConfig: SdkFormBuilderConfiguration = {
                    fields
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    if (field.code === 'intervento-variato-modifica-programma') {
                        // prima modifica
                        if (this.programma.idProgramma != null && this.programma.idProgramma.endsWith('001')) {
                            field.visible = false;
                        }
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
                    annoInizio: this.programma.annoInizio,
                    tipologia: this.tipologia,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    tipoNorma: this.programma.norma,
                });

                formConfig = this.interventoService.updateProtectionsForRisorseDiCapitolo(formConfig, this.userProfile.configurations, this.risorseDiCapitoloProtectionCode);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;

                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }


    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };
            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }


    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadProgramma(): void {
        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
            this.programma = result;
            if (this.programma.idRicevuto) {
                this.setUpdateState(false);
                history.back();
            } else {
                this.elaborateConfig();
            }
        });
    }

    private elaborateImportoImmobile(currentField: SdkFormBuilderField, matrixCellCode: string, one: string, two: string, three?: string): void {
        let groupCode: string = currentField.groupCode;
        let value: number = currentField.data;
        let sectionIndex: number = findIndex(this.formBuilderConfig.fields, ((one: SdkFormBuilderField) => one.code === 'immobili-data'));
        let formGroup: SdkFormBuilderField = this.formBuilderConfig.fields[sectionIndex].fieldSections[0];
        let values: Array<number> = [value];
        if (formGroup.type === 'FORM-GROUP') {
            each(formGroup.fieldGroups, (group: SdkFormFieldGroupConfiguration) => {
                if (group.code === groupCode) {
                    // calcolo il totale del singolo immobile
                    let primo: SdkFormBuilderField = find(group.fields, (f: SdkFormBuilderField) => f.code === one);
                    let primoData: number = isFinite(primo.data) ? primo.data : 0;
                    let secondo: SdkFormBuilderField = find(group.fields, (f: SdkFormBuilderField) => f.code === two);
                    let secondoData: number = isFinite(secondo.data) ? secondo.data : 0;
                    let terzo: SdkFormBuilderField = find(group.fields, (f: SdkFormBuilderField) => f.code === three);
                    let terzoData: number = 0;
                    if (isObject(terzo)) {
                        terzoData = isFinite(terzo.data) ? terzo.data : 0;
                    }
                    let totale: number = sum([value, primoData, secondoData, terzoData]);
                    this.formBuilderDataSubject.next({
                        code: 'valore-stimato-immobile',
                        groupCode,
                        data: totale
                    });
                } else {
                    // salvo i valori del rispettivo campo
                    let secondo: SdkFormBuilderField = find(group.fields, (f: SdkFormBuilderField) => f.code === currentField.code);
                    if (isObject(secondo)) {
                        let secondoData: number = isFinite(secondo.data) ? secondo.data : 0;
                        values = [...values, secondoData];
                    }
                }
            });
        }
        let totaleMatrix: number = sum(values);
        this.formBuilderDataSubject.next({
            code: 'id-textbox-matrix',
            cellCode: matrixCellCode,
            data: totaleMatrix
        });
    }

    private recalculateMatrix(): void {
        this.recalculateSingoloImporto('IM1TRI', 'valore-stimato-primo-anno', 'id-textbox-matrix');
        this.recalculateSingoloImporto('IM2TRI', 'valore-stimato-secondo-anno', 'id-textbox-matrix');
        if (this.tipologia === '1') {
            this.recalculateSingoloImporto('IM3TRI', 'valore-stimato-terzo-anno', 'id-textbox-matrix');
        }
        this.recalculateSingoloImporto('IM9TRI', 'valore-stimato-annualita-successive', 'id-textbox-matrix');
    }

    private recalculateSingoloImporto(matrixCellCode: string, fieldCode: string, matrixId: string): void {
        let sectionIndex: number = findIndex(this.formBuilderConfig.fields, ((one: SdkFormBuilderField) => one.code === 'immobili-data'));
        let formGroup: SdkFormBuilderField = this.formBuilderConfig.fields[sectionIndex].fieldSections[0];
        let values: Array<number> = [];
        if (formGroup.type === 'FORM-GROUP') {
            each(formGroup.fieldGroups, (group: SdkFormFieldGroupConfiguration) => {
                // salvo i valori del rispettivo campo
                let currField: SdkFormBuilderField = find(group.fields, (f: SdkFormBuilderField) => f.code === fieldCode);
                if (isObject(currField)) {
                    let currFieldData: number = isFinite(currField.data) ? currField.data : 0;
                    values = [...values, currFieldData];
                }
            });
        }
        let totaleMatrix: number = sum(values);
        this.formBuilderDataSubject.next({
            code: matrixId,
            cellCode: matrixCellCode,
            data: totaleMatrix
        });
    }

    private manageIstat(field: SdkFormBuilderField, provinciaCode: string, codiceIstatCode: string): void {
        let codIstat: string = undefined;
        let provincia: string = undefined;
        if (field.data) {
            codIstat = field.data.codistat;
            let codIstatProvincia = '0' + codIstat.substring(3, 6);
            provincia = this.provinceMap[codIstatProvincia];
        }
        let provinciaField: any = {
            code: provinciaCode,
            data: provincia
        }
        let codistatField: any = {
            code: codiceIstatCode,
            data: codIstat
        }
        if (field.groupCode) {
            provinciaField.groupCode = field.groupCode;
            codistatField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(provinciaField);
        this.formBuilderDataSubject.next(codistatField);
    }

    private calculateSectionTotal(field: SdkFormBuilderField, sectionCode: string, otherFieldCode: string, sumFieldCode: string): void {
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

    // #endregion
}
