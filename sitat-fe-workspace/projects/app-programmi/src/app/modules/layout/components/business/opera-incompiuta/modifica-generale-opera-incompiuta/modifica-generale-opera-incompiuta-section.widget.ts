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
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, divide, each, find, findIndex, get, has, isEmpty, isEqual, isObject, isString, round, set, sum } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { OperaIncompiutaEntry } from '../../../../../models/opere/opere-incompiute.model';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


@Component({
    templateUrl: `modifica-generale-opera-incompiuta-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaGeneraleOperaIncompiutaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-generale-opera-incompiuta-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();

    private buttons: SdkButtonGroupInput;
    private readOnlyButtons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private idProgramma: string;
    private tipologia: string;
    private idOperaIncompiuta: string;
    private programma: ProgrammaEntry;
    private operaIncompiuta: OperaIncompiutaEntry;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private comuniMap: IDictionary<string> = {};
    private provinceMap: IDictionary<string> = {};
    private dialogConfig: SdkDialogConfig;
    private infoBoxModalConfig: IDictionary<any>;
    private modalConfig: SdkModalConfig<any, void, any>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadQueryParams();
        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();

        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            mergeMap(this.loadDettaglioProgramma),
            map(this.elaborateDettaglioProgramma),
            mergeMap(this.loadDettaglioOperaIncompiuta),
            map(this.elaborateDettaglioOperaIncompiuta),
            map(() => this.checkInfoBox()),
            map(() => this.elaborateButtons()),
            map(() => this.loadForm()),
            catchError(this.handleError)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.infoBoxModalConfig = get(this.config, 'infoBoxModalConfig');                
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.inj.get(AbilitazioniUtilsService) }

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
        if (field.code === 'comune-immobile') {

            let codIstat: string = undefined;
            let provincia: string = undefined;
            if (field.data) {
                codIstat = field.data.codistat;
                let codIstatProvincia = '0' + codIstat.substring(3, 6);
                provincia = this.provinceMap[codIstatProvincia];
            }
            let provinciaField: any = {
                code: 'provincia-immobile',
                data: provincia
            }
            let codistatField: any = {
                code: 'codice-istat-immobile',
                data: codIstat
            }
            if (field.groupCode) {
                provinciaField.groupCode = field.groupCode;
                codistatField.groupCode = field.groupCode;
            }

            this.formBuilderDataSubject.next(provinciaField);
            this.formBuilderDataSubject.next(codistatField);
        } else if (field.code === 'valore-stimato-primo-anno') {
            this.elaborateImportoImmobile(field, 'valore-stimato-secondo-anno', 'valore-stimato-annualita-successive', 'valore-stimato-terzo-anno');
        } else if (field.code === 'valore-stimato-secondo-anno') {
            this.elaborateImportoImmobile(field, 'valore-stimato-primo-anno', 'valore-stimato-annualita-successive', 'valore-stimato-terzo-anno');
        } else if (field.code === 'valore-stimato-terzo-anno') {
            this.elaborateImportoImmobile(field, 'valore-stimato-primo-anno', 'valore-stimato-secondo-anno', 'valore-stimato-annualita-successive');
        } else if (field.code === 'valore-stimato-annualita-successive') {
            this.elaborateImportoImmobile(field, 'valore-stimato-primo-anno', 'valore-stimato-secondo-anno', 'valore-stimato-terzo-anno');
        } else if (field.code === 'importo-complessivo-lavori' || field.code === 'importo-ultimo-sal' || field.code === 'oneri-ultimazione-lavori') {
            this.elaboratePercentualeAvanzamento(field);
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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
        this.idOperaIncompiuta = paramsMap.get('idOperaIncompiuta');
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
        return this.tabellatiCacheService.getValoriTabellati(Constants.OPERE_INCOMPIUTE_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadComuni = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI);
    }

    private elaborateComuni = (comuni: Array<ValoreTabellato>) => {
        each(comuni, (comune: ValoreTabellato) => {
            this.comuniMap[comune.codistat] = comune.descrizione;
        });
    }

    private loadProvince = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE);
    }

    private elaborateProvince = (province: Array<ValoreTabellato>) => {
        each(province, (provincia: ValoreTabellato) => {
            this.provinceMap[provincia.codistat] = provincia.descrizione;
        });
    }

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadDettaglioProgramma = () => {
        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDettaglioProgramma = (result: ProgrammaEntry) => {
        this.programma = result;
    }

    private dettaglioOperaIncompiutaFactory(idProgramma: string, idOperaIncompiuta: string): () => Observable<OperaIncompiutaEntry> {
        return () => {
            return this.programmiService.dettaglioOperaIncompiuta(idProgramma, idOperaIncompiuta);
        }
    }

    private loadDettaglioOperaIncompiuta = () => {
        let factory = this.dettaglioOperaIncompiutaFactory(this.idProgramma, this.idOperaIncompiuta);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDettaglioOperaIncompiuta = (result: OperaIncompiutaEntry) => {
        this.operaIncompiuta = result;
    }

    private elaborateButtons(): void {
        this.buttonsSubj.next(this.buttons);
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.readOnlyButtons);
        return throwError(() => err);
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;
            if (field.code === 'comune-immobile') {
                if (has(restObject, 'codIstat') && restObject.codIstat != null) {
                    field.data = {
                        text: this.comuniMap[restObject.codIstat]
                    }
                }
                mapping = false;
            } else if (field.code === 'provincia-immobile' && restObject.codIstat != null) {
                if (has(restObject, 'codIstat')) {
                    let codIstatProvincia = '0' + restObject.codIstat.substring(3, 6);
                    field.data = this.provinceMap[codIstatProvincia]
                }
                mapping = false;
            }

            if (field.type === 'DATEPICKER' && get(restObject, field.mappingInput) != null) {
                set(field, 'data', new Date(get(restObject, field.mappingInput)))
                mapping = false;
            }
            if (field.code === 'cui-immobile' && get(restObject, field.mappingInput) != undefined) {
                field.type = 'TEXTMODAL';
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.operaIncompiuta, null, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    private loadButtons(): void {

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.readOnlyButtons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {

        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            idProgramma: this.idProgramma,
            tipologia: this.tipologia,
            idOperaIncompiuta: this.idOperaIncompiuta,
            operaIncompiuta: this.operaIncompiuta,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            setUpdateState: this.setUpdateState
        };

        if (button.code === 'back-generale' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
            this.back(button, data);
        } else {
            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
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
            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    private manageExecutionProvider = (obj: any) => {
    }

    private elaborateImportoImmobile(currentField: SdkFormBuilderField, one: string, two: string, three?: string): void {
        let groupCode: string = currentField.groupCode;
        let value: number = currentField.data;
        let sectionIndex: number = findIndex(this.formBuilderConfig.fields, ((one: SdkFormBuilderField) => one.code === 'immobili-data'));
        let formGroup: SdkFormBuilderField = this.formBuilderConfig.fields[sectionIndex].fieldSections[0];
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
                }
            });
        }
    }

    private elaboratePercentualeAvanzamento(currentField: SdkFormBuilderField): void {
        let sectionIndex: number = findIndex(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'importi-data');

        let importoLavoriField: SdkFormBuilderField;
        let importoSalField: SdkFormBuilderField;
        let oneriUltField: SdkFormBuilderField;

        if (currentField.code === 'importo-complessivo-lavori') {
            importoLavoriField = currentField;
            importoSalField = find(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'importo-ultimo-sal');
            oneriUltField = find(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'oneri-ultimazione-lavori');
        } else if (currentField.code === 'importo-ultimo-sal') {
            importoLavoriField = find(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'importo-complessivo-lavori');
            importoSalField = currentField;
            oneriUltField = find(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'oneri-ultimazione-lavori');
        } else if (currentField.code === 'oneri-ultimazione-lavori') {
            importoLavoriField = find(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'importo-complessivo-lavori');
            importoSalField = find(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'importo-ultimo-sal');
            oneriUltField = currentField;
        }

        let value: number = 0;

        if (isObject(importoSalField)) {
            let importoSal: number = importoSalField.data ? importoSalField.data : 0;
            let importoLavori: number = isObject(importoLavoriField) && importoLavoriField.data ? importoLavoriField.data : 0;
            let importoOneri: number = isObject(oneriUltField) && oneriUltField.data ? oneriUltField.data : 0;
            let divisore: number = sum([importoLavori, importoOneri]);
            if (divisore > 0) {
                value = divide(importoSal, divisore);
            }
        }

        // value = toNumber(value.toFixed(9)) * 100;
        value = round(value * 100, 2);
        this.formBuilderDataSubject.next({
            code: 'percentuale-avanzamento-lavori',
            data: value
        });
    }

    // #endregion
}