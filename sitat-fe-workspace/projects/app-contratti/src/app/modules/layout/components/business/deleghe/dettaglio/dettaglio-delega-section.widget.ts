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
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from "@maggioli/app-commons";
import { IDictionary, SdkBusinessAbstractWidget, SdkDateHelper, SdkProviderService, SdkStoreAction, SdkStoreService, UserProfile } from "@maggioli/sdk-commons";
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkSidebarConfig,
} from "@maggioli/sdk-controls";
import { cloneDeep, each, filter, get, has, isEmpty, isObject, isString, remove, toString } from "lodash-es";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { map, mergeMap } from "rxjs/operators";

import { Constants } from "../../../../../../app.constants";
import { DelegheService } from "../../../../../services/deleghe/deleghe.service";
import { TranslateService } from "@ngx-translate/core";

@Component({
    templateUrl: `dettaglio-delega-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DettaglioDelegaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {
    // #region Variables

    @HostBinding("class") classNames = `dettaglio-delega-section`;

    @ViewChild("messages") _messagesPanel: ElementRef;

    @ViewChild("infoBox") _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private id: string;
    private delega: DelegaEntry;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(
            this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
                this.stazioneAppaltanteInfo = saInfo;
            })
        );

        this.addSubscription(
            this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
                this.userProfile = userProfile;
            })
        );

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadDelega(this.loadDelegaFactory()).pipe(map(this.elaborateDelega)).subscribe();
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

    private get protectionUtilsService(): ProtectionUtilsService {
        return this.injectable(ProtectionUtilsService);
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

    protected get dateHelper(): SdkDateHelper {
        return this.injectable(SdkDateHelper);
    }

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

    public manageOutputField(field: SdkFormBuilderField): void {}

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider
            .run(button.provider, {
              button: button,
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                id: this.id,
                stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
                syscon: this.userProfile.syscon,
                idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                chiaviAccessoOrt: this.userProfile.chiaviAccessoOrt,
            })
            .subscribe(this.manageExecutionProvider);
    }

    private manageExecutionProvider = (obj: any) => {};

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.id = paramsMap.get("id");
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

            if (!isEmpty(field.listCode) && field.type === "TEXT" && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field,
            };
        };

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.delega);

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

    private get translateService(): TranslateService {
        return this.injectable(TranslateService);
    }

    private loadDelegaFactory = (): Function => {
        let factory = this.dettaglioDelegaFactory(this.id);
        return factory;
    };

    private loadDelega = (factory: Function): Observable<DelegaEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    };

    private elaborateDelega = (result: DelegaEntry) => {
        this.delega = result;
        this.refreshBreadcrumbs();
        this.checkInfoBox();
        this.loadForm();
        this.showButtons();
    };

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations),
        };

        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private showButtons(): void {
        let buttons: SdkButtonGroupInput = {
            buttons: cloneDeep(this.buttons.buttons),
        };

        this.buttonsSubj.next(buttons);
    }

    // #endregion
}
