import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
    SdkBusinessAbstractWidget,
    SdkRouterService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboBoxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
} from '@maggioli/sdk-controls';
import { SdkDettaglioUtenteStoreService } from '@maggioli/sdk-gestione-utenti';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, filter, get, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import {
    centroCosto,
    ConfigurazioneUtente,
    ConfigurazioneUtenteInsertForm,
} from '../../models/configurazione-utente/configurazione-utente.model';
import { ConfigurazioneUtenteService } from '../../services/configurazione-utente/configurazione-utente.service';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { CustomParamsFunctionResponse, FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `modifica-configurazione-utente-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaConfigurazioneUtenteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any;
    public dettaglio: ConfigurazioneUtente;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private syscon: number;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        let factory = this.loadDettaglioFactory(this.syscon + '');
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ConfigurazioneUtente) => {
            this.dettaglio = result;
            this.elaborateConfig();
        });

    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    protected onData(_data: void): void { }

    // #endregion

    // #region Getters


    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get configurazioniService(): ConfigurazioneUtenteService { return this.injectable(ConfigurazioneUtenteService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkDettaglioUtenteStoreService(): SdkDettaglioUtenteStoreService { return this.injectable(SdkDettaglioUtenteStoreService) }

    // #endregion

    // #region Private

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +paramsMap.get('syscon');
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config.body, 'fields')
                };

                if (this.dettaglio != null && isEmpty(this.dettaglio.centriCosto)) {
                    let form: any = filter(formConfig.fields, (one: any) => one.code !== 'programmi-data');
                    formConfig = { fields: form };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, this.customPopulateFunction, this.dettaglio);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean): CustomParamsFunctionResponse => {
        let mapping: boolean = true;

        if (field.code === 'centro-di-costo-text') {
            if (this.dettaglio != null && (this.dettaglio.centriCosto == null || this.dettaglio.centriCosto.length == 0)) {
                field.visible = true;
                field.data = this.translateService.instant('CONFIGURAZIONE-UTENTI.NESSUN-CENTRO-COSTO');
            } else {
                field.visible = false;
                mapping = false;
            }
        }
        if (field.code === 'centro-di-costo') {
            if (this.dettaglio != null && this.dettaglio.centriCosto != null && this.dettaglio.centriCosto.length > 0) {

                let list: Array<SdkComboBoxItem> = new Array();
                each(this.dettaglio.centriCosto, (one: centroCosto) => {
                    list.push({
                        key: toString(one.id),
                        value: toString(one.denom)
                    });
                });
                field.itemsProvider = () => {
                    return of(list as Array<SdkComboBoxItem>);
                }
                if (this.dettaglio.centroCosto != null) {
                    let comboItem: SdkComboBoxItem = {
                        key: this.dettaglio.idCentroCosto,
                        value: this.dettaglio.centroCosto
                    }
                    set(field, 'data', comboItem);
                }
                mapping = false;

            } else {
                field.visible = false;
                mapping = false;
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

    private loadDettaglioFactory(syscon: string): () => Observable<ConfigurazioneUtente> {
        return () => {
            return this.configurazioniService.dettaglio(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, syscon);
        }
    }

    private salvaConfigurazioneFactory(configurazione: ConfigurazioneUtente): () => Observable<string> {
        return () => {
            return this.configurazioniService.modifica(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, configurazione);
        }
    }

    private manageExecutionProvider = (obj: any) => {
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }



    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button)) {
            if (button.code === 'back-to-configurazione-utente') {
                this.sdkDettaglioUtenteStoreService.clear();
                this.sdkDettaglioUtenteStoreService.syscon = this.syscon;
                let params: IDictionary<any> = {
                    syscon: this.syscon
                };
                this.routerService.navigateToPage('configurazione-utente-page', params);
            }
            if (button.code === 'save-configurazione') {
                let config: ConfigurazioneUtenteInsertForm = {};
                config.syscon = this.syscon + '';


                let dataContratti = this.formBuilderConfig.fields[0]?.fieldSections[0]?.data;
                let dataProgrammi = this.formBuilderConfig.fields[1]?.fieldSections[1]?.data;
                if (dataContratti != null && dataContratti.key != null) {
                    config.sysab3 = dataContratti.key;
                }
                if (dataProgrammi != null && dataProgrammi.key != null) {
                    config.idCentroCosto = dataProgrammi.key;
                }
                let factory = this.salvaConfigurazioneFactory(config);
                this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
                    this.sdkDettaglioUtenteStoreService.clear();
                    this.sdkDettaglioUtenteStoreService.syscon = this.syscon;
                    let params: IDictionary<any> = {
                        syscon: this.syscon
                    };
                    this.routerService.navigateToPage('configurazione-utente-page', params);
                });
            }
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion

}
