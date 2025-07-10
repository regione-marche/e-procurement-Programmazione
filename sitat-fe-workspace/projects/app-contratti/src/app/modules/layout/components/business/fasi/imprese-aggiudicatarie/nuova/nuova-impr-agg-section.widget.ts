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
    ImpresaEntry,
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
    SdkModalOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, isObject, isString, split, toString, trim } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { GaraEntry } from '../../../../../../models/gare/gare.model';
import { GareService } from '../../../../../../services/gare/gare.service';



@Component({
    templateUrl: `nuova-impr-agg-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaImpresaAggiudicatariaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `nuova-impresa-aggiudicataria-section`;

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
    private tipoDocumento: string;
    private numPubb: string;
    private campiVisibili: Array<string>;
    private campiVisibiliString: string;
    private campiObbligatori: Array<string>;
    private campiObbligatoriString: string;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private gara: GaraEntry;

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
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara)
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

    private get gareService(): GareService { return this.injectable(GareService) }

    protected get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }


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

    public manageModalOutput(_item: SdkModalOutput<any>): void { }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if(field.code === 'nomest' && field.data != null && isObject(field.data)){
                this.manageImpresaLegRapGroup(field);
            } else if(field.code === 'nomest-singola' && field.data != null && isObject(field.data)){
                this.manageImpresaLegRapSingolo(field);
            }
        }
    }

    private manageImpresaLegRapSingolo(field: SdkFormBuilderField): void {

        let nomeCode: string = 'nome-legale-rappr-singola';
        let cognomeCode: string = 'cognome-legale-rappr-singola';
        let cfCode: string = 'cf-legale-rappr-singola';

        let nome: string = undefined;
        let cognome: string = undefined;
        let cf: string = undefined;
        if (field.data != null && !isEmpty(field.data.rappresentante)) {
            nome = field.data.rappresentante.nome;
            cognome = field.data.rappresentante.cognome;
            cf = field.data.rappresentante.cf;

            let nomeField: any = {
                code: nomeCode,
                data: nome
            };
    
            let cognomeField: any = {
                code: cognomeCode,
                data: cognome
            };
    
            let cfField: any = {
                code: cfCode,
                data: cf
            };
    
          
    
            this.formBuilderDataSubject.next(nomeField);
            this.formBuilderDataSubject.next(cognomeField);
            this.formBuilderDataSubject.next(cfField);
        }

        
    }

    private manageImpresaLegRapGroup(field: SdkFormBuilderField): void {

        let nomeCode: string = 'nome-legale-rappr';
        let cognomeCode: string = 'cognome-legale-rappr';
        let cfCode: string = 'cf-legale-rappr';

        let nome: string = undefined;
        let cognome: string = undefined;
        let cf: string = undefined;
        if (field.data) {
            nome = field.data.rappresentante.nome;
            cognome = field.data.rappresentante.cognome;
            cf = field.data.rappresentante.cf;
        }

        let nomeField: any = {
            code: nomeCode,
            data: nome
        };

        let cognomeField: any = {
            code: cognomeCode,
            data: cognome
        };

        let cfField: any = {
            code: cfCode,
            data: cf
        };

        if (field.groupCode) {
            nomeField.groupCode = field.groupCode;
            cognomeField.groupCode = field.groupCode;
            cfField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(nomeField);
        this.formBuilderDataSubject.next(cognomeField);
        this.formBuilderDataSubject.next(cfField);
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
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            tipoDocumento: this.tipoDocumento,
            numPubb: this.numPubb,
            campiVisibili: this.campiVisibiliString,
            campiObbligatori: this.campiObbligatoriString,
            setUpdateState: this.setUpdateState
        };

        if (button.code === 'back-to-lista-imprese-aggiudicatarie' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
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
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');
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

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: ImpresaEntry, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            const key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'tipo-app') {
                field.data = this.gara.tipoApp;
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

        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, { tipoApp: this.gara.tipoApp }, {
            stazioneAppaltante: this.stazioneAppaltanteInfo
        });

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
        return this.tabellatiCacheService.getValoriTabellati(Constants.IMPRESE_AGGIUDICATARIE_TABELLATI);
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
        this.checkInfoBox();
        this.loadForm();
    }



    // #endregion
}
