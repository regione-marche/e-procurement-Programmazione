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
    RupEntry,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    Ufficio,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkDateHelper,
    SdkProviderService,
    SdkRouterService,
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
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
    SdkRadioConfig,
    SdkRadioInput,
    SdkRadioOutput,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, find, get, has, isEmpty, isObject, isString, remove, set, sum, toString } from 'lodash-es';
import { env } from 'process';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { environment } from '../../../../../../../environments/environment';
import { Constants } from '../../../../../../app.constants';
import { SmartCigEntry } from '../../../../../models/smartcig/smartcig.model';
import { SmartCigService } from '../../../../../services/smartcig/smartcig.service';
import { DettaglioIdGaraStoreService } from '../../gare/dettaglio-id-gara-store.service';


@Component({
    templateUrl: `gestione-smartcig-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class GestioneSmartCigSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `gestione-smartcig-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;    
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private sidebarConfig: SdkSidebarConfig;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;    
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private radioValue = '1';
    public visibleImportaMenu: boolean = false;

    public radioConfig: Observable<SdkRadioConfig> = of({
        code: 'id-radio',
        label: 'LABEL-RADIO-SMARTCIG.TITLE',
        choices: [
            {
                code: '1',
                label: 'LABEL-RADIO-SMARTCIG.COMPILAZIONE'
            },
            {
                code: '2',
                label: 'LABEL-RADIO-SMARTCIG.IMPORTA'
            }
        ]
    } as SdkRadioConfig);

    public radioData: Observable<SdkRadioInput> = of({
        data: {
            code: '1',
            label: 'LABEL-RADIO-SMARTCIG.COMPILAZIONE'
        }
    } as SdkRadioInput);
    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        // Se SCP non si può importare smartcig, forward all'inserimento automatico.
        if(environment.LOGIN_MODE === 3){
            this.routerService.navigateToPage('nuovo-smartcig-page');
        } else {

            this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
                this.stazioneAppaltanteInfo = saInfo;
            }));

            this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
                this.userProfile = userProfile;
            }));

            this.loadButtons();
        }
       
    }

    protected onAfterViewInit(): void {
        if(environment.LOGIN_MODE !== 3){
            this.showButtons();
            this.loadForm();
        }
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

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {           
            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
                syscon: this.userProfile.syscon,
                radioData: this.radioValue
            };
            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            
        }
    }

    public onOutput(item: SdkRadioOutput): void {
        if(!isEmpty(item)){
            this.radioValue = item.data.code;
        }      
        if(this.radioValue === '1'){
            this.visibleImportaMenu = false;
        }else{
            this.visibleImportaMenu = true;
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
           if (isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = {
                    ...this.sidebarConfig.componentConfig,
                    ...obj
                };
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'importo-lotto' && field.type === 'NUMERIC-TEXTBOX') {
                this.calcoloImportoTotale(field, 'dati-economici-data', 'importo-attuazione-sicurezza', 'importo-totale');
            } else if (field.code === 'importo-attuazione-sicurezza' && field.type === 'NUMERIC-TEXTBOX') {
                this.calcoloImportoTotale(field, 'dati-economici-data', 'importo-lotto', 'importo-totale');
            }
        }
    }

    public manageFormClick(config: SdkTextOutput): void {
        
    }

    
   

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
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

 
    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(undefined, field, key, mapping);
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
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, {
            nominativoStazioneAppaltante: this.stazioneAppaltanteInfo.nome
        }, {
            stazioneAppaltante: this.stazioneAppaltanteInfo
        }, undefined);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);

       
    }
    
   

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };        
        
    }

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

    private showButtons(): void {
        this.buttonsSubj.next(this.buttons);        
    }

    // #endregion
}
