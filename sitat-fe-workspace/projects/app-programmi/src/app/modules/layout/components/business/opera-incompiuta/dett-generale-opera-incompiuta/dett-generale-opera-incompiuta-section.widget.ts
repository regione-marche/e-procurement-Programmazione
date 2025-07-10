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
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, isString, remove, round, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { OperaIncompiutaEntry } from '../../../../../models/opere/opere-incompiute.model';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


@Component({
    templateUrl: `dett-generale-opera-incompiuta-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioGeneraleOperaIncompiutaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dett-generale-opera-incompiuta-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private readOnlyButtons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private menuTabs: Array<SdkMenuTab>;
    private idProgramma: string;
    private tipologia: string;
    private idOperaIncompiuta: string;
    private programma: ProgrammaEntry;
    private operaIncompiuta: OperaIncompiutaEntry;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private comuniMap: IDictionary<string> = {};
    private provinceMap: IDictionary<string> = {};

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.refreshTabs();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            map(() => this.elaborateButtons()),
            mergeMap(this.loadDettaglioProgramma),
            map(this.elaborateDettaglioProgramma),
            mergeMap(this.loadDettaglioOperaIncompiuta),
            map(this.elaborateDettaglioOperaIncompiuta),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm()),
            catchError(this.handleError)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
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

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

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

    public manageOutputField(field: SdkFormBuilderField): void { }

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

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible);
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            idProgramma: this.idProgramma,
            tipologia: this.tipologia,
            idOperaIncompiuta: this.idOperaIncompiuta
        }).subscribe(this.manageExecutionProvider);
    }

    private manageExecutionProvider = (obj: any) => {
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;
            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = toString(keyAny);
            if (isObject(restObject)) {
                if (field.code === 'comune-immobile') {
                    if (has(restObject, 'codIstat') && get(restObject, 'codIstat') != null) {
                        field.data = this.comuniMap[get<any, any>(restObject, 'codIstat')];
                    }
                    mapping = false;
                } else if (field.code === 'provincia-immobile' && get(restObject, 'codIstat') != null) {
                    if (has(restObject, 'codIstat')) {
                        let codIstatProvincia = '0' + get<any, any>(restObject, 'codIstat').substring(3, 6);
                        field.data = this.provinceMap[codIstatProvincia]
                    }
                    mapping = false;
                } else if (field.code === 'percentuale-avanzamento-lavori') {
                    let value: number = get(restObject, 'avanzamento');
                    field.data = round(value, 2);
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.operaIncompiuta);

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

        this.showButtons(this.programma);
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

    private loadButtons(): void {

        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.readOnlyButtons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
    }

    private elaborateButtons(): void {
        this.buttonsSubj.next(this.buttons);
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.readOnlyButtons);
        return throwError(() => err);
    }

    private showButtons(programma: ProgrammaEntry): void {
        if (programma.idRicevuto) {
            this.buttonsSubj.next(this.readOnlyButtons);
        } else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    // #endregion
}
