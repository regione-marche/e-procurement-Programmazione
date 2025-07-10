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
    SdkDateHelper,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, isObject, isString, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, of, throwError } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ProgrammaBaseEntry, ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { InterventoNonRipropostoEntry } from 'projects/app-programmi/src/app/modules/models/interventi/interventi-non-riproposti.model';


@Component({
    templateUrl: `modifica-intervento-non-riproposto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaInterventoNonRipropostoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class')
    public classNames = `modifica-intervento-non-riproposto-section`;

    @ViewChild('messages')
    public _messagesPanel: ElementRef;

    @ViewChild('infoBox')
    public _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private idProgramma: string;
    private idIntervento: string;
    private tipologia: string;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private itnerventoNr: InterventoNonRipropostoEntry;
    private programma: ProgrammaEntry;
    private userProfile: UserProfile;
    private sidebarConfig: SdkSidebarConfig;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    public tipologiaInstallazione: string;
    private dialogConfig: SdkDialogConfig;    
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.setUpdateState(true);
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.initDialog();
        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati()
            .pipe(
                map(this.elaborateTabellati),
                mergeMap(this.loadProgramma),
                map(this.elaborateProgramma),
                mergeMap(this.loadInterventoNr),
                map(this.elaborateInterventoNr),                
                map(() => this.initButtons()),
                map(() => this.elaborateConfig()),
                map(() => this.checkInfoBox()),
                catchError(this.handleError)
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

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    // #endregion

    // #region Private

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

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.idIntervento = paramsMap.get('idIntervento');
        this.tipologia = paramsMap.get('tipologia');
    }

    private initButtons(): void {        
        this.buttonsSubj.next(this.buttons);        
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {

                let fields: Array<SdkFormBuilderField>;

                if(this.programma.existProgrammaAnnoPrecedente === true){
                    if (this.tipologia === '1') {
                        fields = cloneDeep(get(this.config, 'body.interventiExistsFields'))
                    } else if (this.tipologia === '2') {
                        fields = cloneDeep(get(this.config, 'body.acquistiExistsFields'))
                    }
                } else{
                    if (this.tipologia === '1') {
                        fields = cloneDeep(get(this.config, 'body.interventiFields'))
                    } else if (this.tipologia === '2') {
                        fields = cloneDeep(get(this.config, 'body.acquistiFields'))
                    }
                }
                

                let formConfig: SdkFormBuilderConfiguration = {
                    fields
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
                    }

                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.itnerventoNr);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);

            });
        }
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

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.DETT_PROG);
    }

    private elaborateTabellati = (tabellati: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = tabellati;
    }

    private dettaglioInterventoNrFactory(idProgramma: string, idIntervento: string): () => Observable<InterventoNonRipropostoEntry> {
        return () => {
            return this.programmiService.dettaglioInterventoNr(idProgramma,idIntervento);
        }
    }

    private loadInterventoNr = (): Observable<InterventoNonRipropostoEntry> => {
        let factory = this.dettaglioInterventoNrFactory(this.idProgramma, this.idIntervento);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateInterventoNr = (itnerventoNr: InterventoNonRipropostoEntry) => {
        this.itnerventoNr = itnerventoNr;
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsReadonly);
        return throwError(() => err);
    }

    // #endregion

    // #region Public

    public manageFormClick(config: SdkTextOutput): void {


        if (isObject(config)) {

           
        }
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                idIntervento: this.idIntervento,
                tipologia: this.tipologia,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState
            };

            if (button.code === 'back-to-dettaglio') {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
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

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadProgramma = (): Observable<ProgrammaEntry> => {
        const factory = this.dettaglioProgrammaFactory(this.idProgramma);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateProgramma = (result: ProgrammaEntry) => {
        this.programma = result;        
    }

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'modal') {
            if (isObject(_item.data)) {
                        
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {            
        }
    }
    

    // #endregion
}
