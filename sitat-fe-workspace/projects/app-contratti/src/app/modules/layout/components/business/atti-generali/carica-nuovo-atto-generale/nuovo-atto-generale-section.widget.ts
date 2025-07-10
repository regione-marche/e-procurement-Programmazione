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
import { FormBuilderUtilsService, HttpRequestHelper, ProtectionUtilsService, StazioneAppaltanteInfo, TabellatiCacheService, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary, SdkBusinessAbstractWidget, SdkDateHelper, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkComboBoxItem, SdkDialogConfig, SdkDocumentItem, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormBuilderInput, SdkMessagePanelService, SdkModalOutput } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, isEmpty, isEqual, isObject, isString, map, set, toString } from 'lodash-es';
import { Constants } from 'projects/app-contratti/src/app/app.constants';
import { AllegatoEntry, AttoGeneraleEntry } from 'projects/app-contratti/src/app/modules/models/atti-generali/atti-generali.model';
import { AttiGeneraliService } from 'projects/app-contratti/src/app/modules/services/atti-generali/atti-generali.service';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

@Component({
    templateUrl: `nuovo-atto-generale-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovoAttoGeneraleSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `nuovo-atto-generale-section`;

    @ViewChild('infoBox') _infoBox: ElementRef;

    @ViewChild('messages') _messagesPanel: ElementRef;

    //#region Variables

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private tipoDocumento: string;
    private attiGeneraliMap: IDictionary<string>;
    private dialogConfig: SdkDialogConfig;
    private buttons: SdkButtonGroupInput;
    private tipoAttoMap: IDictionary<string> = {};
    private attiComboItem: SdkComboBoxItem;
    private attoGenerale: AttoGeneraleEntry;

    //#endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    protected onInit(): void {
        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.TIPO_ATTO_GENERALE).subscribe((tipiAttiGenerali: Array<ValoreTabellato>) => {
            each(tipiAttiGenerali, (tipoAttoGenerale: ValoreTabellato) => {
                this.tipoAttoMap[tipoAttoGenerale.codice] = tipoAttoGenerale.descrizione;
            });
        });

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadForm();
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

    // #region Getters

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService); }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper); }

    private get attiGeneraliService(): AttiGeneraliService { return this.injectable(AttiGeneraliService); }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                setUpdateState: this.setUpdateState
            };

            if(button.code === 'back-home' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false){

                this.back(button, data);

            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item.data != null) {
            this.loadForm();
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
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

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let providerArgs: IDictionary<any> = {
            stazioneAppaltante: this.stazioneAppaltanteInfo
        }

        let customPopulateFunction = (field: SdkFormBuilderField, restObject?: AttoGeneraleEntry, dynamicField?: boolean) => {
            let mapping: boolean = true;
    
            const keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);
    
            if (field.code === 'stazione-appaltante' && field.type === 'TEXT') {
                set(field, 'data', this.stazioneAppaltanteInfo.nome);
                mapping = false;
            } else if (field.code === 'tipologia' && field.type === 'COMBOBOX') {
                //Controllo su campo per disabilitarlo nel caso in cui la combobox "Atti generali" non sia popolato
                if(this.attiComboItem?.key == undefined || this.attiComboItem?.key == null){
                    field = {
                        ...field,
                        disabled: true
                    }
                    mapping = false;
                }
            } else if(field.code === 'primaPubb') {
                let comboItem: SdkComboBoxItem = {
                    key: '1',
                    value: this.translateService.instant("COMBOBOX.SI")
                }
                set(field, 'data', comboItem);
                mapping = false;
            } else if (field.code === 'dataPrimaPubb') {
                field.max = new Date();
                mapping = false;
            } else if(field.code === 'dataPubbSistema') {
                field.visible = false;
                mapping = false;
            } else if (field.type === 'DOCUMENTS-LIST') {
                let existingDocuments: Array<AllegatoEntry> = get(this.attoGenerale, field.mappingInput);
                let sdkExistingDocuments: Array<SdkDocumentItem> = map(existingDocuments, (one: AllegatoEntry) => {
                    let docItem: SdkDocumentItem = {
                        ...one,
                        code: toString(one.idAllegato),
                        titolo: one.descrizione || one.url,
                        descrizione: undefined,
                        tipoFile: one.tipoFile
                    };
    
                    docItem.fileDownloadCallback = () => {
                        const factory = this.downloadDocumentoAttoGeneraleFactory(+this.attoGenerale.idAtto, one.idAllegato);
                        return this.requestHelper.begin(factory, this.messagesPanel);
                    };
    
                    return docItem;
                });
                set(field, 'documents', sdkExistingDocuments);
                mapping = false;
            } else if ( field.code === 'rup' && restObject != null && restObject.rupData != null ) {
                mapping = false;                       
                set(field, 'data', restObject.rupData);
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, undefined, providerArgs);

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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private downloadDocumentoAttoGeneraleFactory(idAtto: number, numDoc: number) {
        return () => {
            return this.attiGeneraliService.downloadDocumentoAttoGenerale(idAtto, numDoc);
        }
    }

    //#endregion

}
