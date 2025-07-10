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
    ImpresaEntry,
    ProtectionUtilsService,
    ResponseResult,
    StazioneAppaltanteInfo,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkDocumentItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, isObject, isString, map as mapArray, set, toString } from 'lodash-es';
import { GaraEntry } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import {
    DocumentoFaseEntry,
    MisureAggiuntiveSicurezzaEntry,
} from '../../../../../../models/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza.models';
import {
    MisureAggiuntiveSicurezzaService,
} from '../../../../../../services/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza.service';


@Component({
    templateUrl: `modifica-mis-agg-sic-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaMisureAggiuntiveSicurezzaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-misure-aggiuntive-sicurezza-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private misureAggiuntiveSicurezza: MisureAggiuntiveSicurezzaEntry;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;
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

        let factory = this.dettaglioGaraFactory(this.codGara);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: GaraEntry)=>{
            this.gara = result;
        });
           
        
        this.initDialog();
    }

    protected onAfterViewInit(): void {                         
        this.loadMisureAggiuntiveSicurezza().pipe(
            map(this.elaborateMisureAggiuntiveSicurezza),
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

    private get misureAggiuntiveSicurezzaService(): MisureAggiuntiveSicurezzaService { return this.injectable(MisureAggiuntiveSicurezzaService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get gareService(): GareService { return this.injectable(GareService) }

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
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            setUpdateState: this.setUpdateState,
            update: true
        };

        if (button.code === 'back-to-dettaglio-misure-aggiuntive-sicurezza' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
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

            if (field.code === 'documents-list') {
                let documents: Array<DocumentoFaseEntry> = get(restObject, field.mappingInput);
                if (isObject(documents)) {
                    let sdkExistingDocuments: Array<SdkDocumentItem> = mapArray(documents, (one: DocumentoFaseEntry) => {
                        let nuovo: SdkDocumentItem = {
                            code: toString(one.progressivoDocumento),
                            binary: one.binary,
                            titolo: one.titolo
                        };
                        return nuovo;
                    })
                    set(field, 'documents', sdkExistingDocuments);
                }
                mapping = false;
            } else if (field.type === 'AUTOCOMPLETE') {
                let data: ImpresaEntry = get(restObject, field.mappingInput);
                if (isObject(data)) {
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${data.codiceFiscale} ${data.ragioneSociale}`,
                        _key: data.codiceImpresa
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            }

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.misureAggiuntiveSicurezza, {
            stazioneAppaltante: { codice: this.gara.codiceStazioneAppaltante },
            codGara: this.codGara,
            codLotto: this.codLotto
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

    private misureAggiuntiveSicurezzaFactory(codGara: string, codLotto: string, codiceFase: string, numeroProgressivo: string): () => Observable<ResponseResult<MisureAggiuntiveSicurezzaEntry>> {
        return () => {
            return this.misureAggiuntiveSicurezzaService.getMisureAggiuntiveSicurezza(codGara, codLotto, codiceFase, numeroProgressivo);
        }
    }

    private loadMisureAggiuntiveSicurezza = (): Observable<ResponseResult<MisureAggiuntiveSicurezzaEntry>> => {
        let factory = this.misureAggiuntiveSicurezzaFactory(this.codGara, this.codLotto, this.codiceFase, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateMisureAggiuntiveSicurezza = (result: ResponseResult<MisureAggiuntiveSicurezzaEntry>) => {
        if (isObject(result.data)) {
            this.misureAggiuntiveSicurezza = result.data;
            this.checkInfoBox();
            this.loadForm();
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

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

   


    // #endregion
}
