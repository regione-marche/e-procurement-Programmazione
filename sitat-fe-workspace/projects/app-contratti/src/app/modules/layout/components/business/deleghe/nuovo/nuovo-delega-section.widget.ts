import { DelegaEntry } from "./../../../../../models/deleghe/deleghe.model";
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
} from "@angular/core";
import { ActivatedRoute, ParamMap } from "@angular/router";
import {
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from "@maggioli/app-commons";
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboBoxItem,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
    SdkModalConfig,
} from "@maggioli/sdk-controls";
import { TranslateService } from "@ngx-translate/core";
import { cloneDeep, each, find, get, isEmpty, isEqual, isObject, isString, remove, sum, toString } from "lodash-es";
import { BehaviorSubject, Observable, Observer, of, Subject } from "rxjs";
import { map, mergeMap } from "rxjs/operators";

import { environment } from "../../../../../../../environments/environment";
import { Constants, TEnvs } from "../../../../../../app.constants";
import { DelegheService } from "../../../../../services/deleghe/deleghe.service";

@Component({
    templateUrl: `nuovo-delega-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NuovoDelegaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {
    // #region Variables

    @HostBinding("class") classNames = `nuovo-delega-section`;

    @ViewChild("messages") _messagesPanel: ElementRef;

    @ViewChild("infoBox") _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

    private userProfile: UserProfile;
    private stazioneAppaltante: StazioneAppaltanteInfo;

    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private delega: DelegaEntry;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {
        // set update state
        this.setUpdateState(true);

        this.addSubscription(
            this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
                this.stazioneAppaltante = saInfo;
            })
        );

        this.addSubscription(
            this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
                this.userProfile = userProfile;
            })
        );

        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadForm();
    }

    protected onDestroy(): void {}

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void {}

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute {
        return this.injectable(ActivatedRoute);
    }

    private get formBuilderUtilsService(): FormBuilderUtilsService {
        return this.injectable(FormBuilderUtilsService);
    }

    private get provider(): SdkProviderService {
        return this.injectable(SdkProviderService);
    }

    private get sdkMessagePanelService(): SdkMessagePanelService {
        return this.injectable(SdkMessagePanelService);
    }

    private get store(): SdkStoreService {
        return this.injectable(SdkStoreService);
    }

    private get requestHelper(): HttpRequestHelper {
        return this.injectable(HttpRequestHelper);
    }

    private get delegheService(): DelegheService {
        return this.injectable(DelegheService);
    }

    private get tabellatiCacheService(): TabellatiCacheService {
        return this.injectable(TabellatiCacheService);
    }

    private get translateService(): TranslateService {
        return this.injectable(TranslateService);
    }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService {
        return this.injectable(AbilitazioniUtilsService);
    }

    private get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
              button: button,
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante,
                userProfile: this.userProfile,
                syscon: this.userProfile.syscon,
                idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
            };

            this.provider.run(button.provider, data).subscribe();
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, "component"),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, "fields"),
                    buttons: get(this.infoBoxModalConfig, "buttons"),
                    userProfile: this.userProfile,
                    mnemonico: item.mnemonico,
                },
            };
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, "body.fields"));

        let formConfig: SdkFormBuilderConfiguration = {
            fields,
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'ruolo') {
                field.data = 'Solo compilazione';
                mapping = false;
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field,
            };
        };

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);
        formConfig = this.formBuilderUtilsService.populateForm(
            formConfig,
            true,
            customPopulateFunction,
            this.delega,
            {
                stazioneAppaltante: this.stazioneAppaltante,
                userProfile: this.userProfile,
            },
            infoBox
        );

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
                message: this.config.infoBox,
            });
        }
    }

    private dettaglioDelegaFactory(id: string): () => Observable<DelegaEntry> {
        return () => {
            return this.delegheService.dettaglioDelega(id);
        };
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.LOTTO_TABELLATI);
    };

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    };

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant("DIALOG.BACK-TITLE"),
            message: this.translateService.instant("DIALOG.BACK-TEXT"),
            acceptLabel: this.translateService.instant("DIALOG.CONFIRM-ACTION"),
            rejectLabel: this.translateService.instant("DIALOG.CANCEL-ACTION"),
            open: new Subject(),
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
        };
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations),
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
