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
    SdkProviderService,
    SdkRouterService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMenuTab,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, filter, get, isEmpty, isObject, isString, remove, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, catchError, map, mergeMap, throwError } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { ConfigurazioneUtente } from '../../models/configurazione-utente/configurazione-utente.model';
import { ConfigurazioneUtenteService } from '../../services/configurazione-utente/configurazione-utente.service';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { CustomParamsFunctionResponse, FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';
import { GestioneUtentiService, InitRicercaUtentiDTO } from '@maggioli/sdk-gestione-utenti';
import { ResponseDTO } from '../../models/utils/utils.model';

@Component({
    templateUrl: `configurazione-utente-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ConfigurazioneUtenteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any;
    public dettaglio: ConfigurazioneUtente;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private syscon: number;
    private menuTabs: Array<SdkMenuTab>;
    private initRicercaUtenti: InitRicercaUtentiDTO;

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
        this.loadInitRicercaUtenti().pipe(
            map(this.elaborateInitRicercaUtenti),
            mergeMap(this.loadConfigurazioni),
            map(this.elaborateConfigurazioni),
            map(() => this.refreshTabs()),
            map(() => this.elaborateConfig()),
            map(() => this.elaborateButtons()),
            catchError(this.handleError)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

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

    protected onData(_data: void): void { }

    // #endregion

    // #region Getters


    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get configurazioniService(): ConfigurazioneUtenteService { return this.injectable(ConfigurazioneUtenteService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

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


        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code === 'centroCosto' && this.dettaglio != null && this.dettaglio.centroCosto != null) {
            field.data = this.dettaglio.centroCosto;
            mapping = false;

        }
        if (field.code === 'privilegi-utente-contratti' && !isEmpty(key)) {
            if (key === 'A') {
                set(field, 'data', this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-CONTRATTI.A'));
                mapping = false;
            } else if (key === 'C') {
                set(field, 'data', this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-CONTRATTI.C'));
                mapping = false;
            } else if (key === 'U') {
                set(field, 'data', this.translateService.instant('SDK-UTENTE.PRIVILEGI-UTENTE-CONTRATTI.U'));
                mapping = false;
            }
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

    private manageExecutionProvider = (obj: any) => {
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsReadonly, this.userProfile.configurations)
        };
    }

    private refreshTabs(): void {
        if (this.menuTabs != null) {
            remove(this.menuTabs, (one: SdkMenuTab) => {
                if (!isEmpty(one.oggettoProtezione)) {
                    let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                    if (visible !== true) {
                        return true;
                    }
                }
                if (!isEmpty(one.visible)) {
                    let visible: boolean = this.provider.run(one.visible, {
                        initRicercaUtenti: this.initRicercaUtenti
                    });
                    return visible === false;
                }
                return false;
            });
            this.sdkLayoutMenuTabs.emit(this.menuTabs);
        }
    }

    private loadInitRicercaUtenti = (): Observable<InitRicercaUtentiDTO> => {
        return this.gestioneUtentiService.initRicercaUtenti()
            .pipe(
                map((result: ResponseDTO<InitRicercaUtentiDTO>) => result.response)
            );
    }

    private elaborateInitRicercaUtenti = (result: InitRicercaUtentiDTO): void => {
        this.initRicercaUtenti = result;
    }

    private loadConfigurazioni = (): Observable<ConfigurazioneUtente> => {
        let factory = this.loadDettaglioFactory(this.syscon + '');
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateConfigurazioni = (result: ConfigurazioneUtente): void => {
        this.dettaglio = result;
    }

    private elaborateButtons(): void {
        if (this.initRicercaUtenti != null && this.initRicercaUtenti.utenteDelegatoGestioneUtenti == true) {
            this.buttonsSubj.next(this.buttonsReadonly);
        } else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsReadonly);
        return throwError(() => err);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && button.code === 'go-to-update-configurazione') {
            let params: IDictionary<any> = {
                syscon: this.syscon
            };
            this.routerService.navigateToPage('modifica-configurazione-utente-page', params);
        } else if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                syscon: this.syscon
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion

}
