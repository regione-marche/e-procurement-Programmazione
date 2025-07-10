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
import { AuthenticationTokenInfo, ChiaviAccessoOrt, HttpRequestHelper, OpzioniUtenteProdotto, ResponseResult, StazioneAppaltanteBaseInfo, StazioneAppaltanteInfo, TabellatiService, TokenContent, UserAccountInfo, UserService } from '@maggioli/app-commons';
import { SDK_APP_CONFIG, SdkAppEnvConfig, SdkBusinessAbstractWidget, SdkProviderService, SdkRedirectService, SdkRouterService, SdkSessionStorageService, SdkStoreAction, SdkStoreService, UserProfile, decodeJwt } from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { isEmpty, isObject, shuffle } from 'lodash-es';
import { environment } from 'projects/app-contratti/src/environments/environment';
import { UserExistsInfo } from 'projects/app-home/src/app/modules/models/authentication/authentication.model';
import { AuthenticationService } from 'projects/app-home/src/app/modules/services/authentication/authentication.service';
import { Observable, Observer, Subject, debounceTime, distinctUntilChanged, map, mergeMap, of, tap } from 'rxjs';
import { Constants } from '../../app-commons.constants';


@Component({
    templateUrl: `spid-login-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.Default
})
export class SpidLoginSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @ViewChild('messages') _messagesPanel: ElementRef;
    public debounce: Subject<any> = new Subject<any>();
    public config: any = {};
    public providers: any = {};
    public buttons: Observable<SdkButtonGroupInput>;
    public visibleMenu: boolean = false;
    public multiente: boolean = false;
    public stazioniAppaltanti: Array<StazioneAppaltanteBaseInfo>;
    public stazioniAppaltantiToshow: Array<StazioneAppaltanteBaseInfo>;
    private codeinSelezionato: string = '-';
    private userProfile: UserProfile;
    private syscon: string;
    private appCode: string;
    private chiaviAccessoOrt: ChiaviAccessoOrt;
    private ruolo: string;
    private richiestaAssistenzaAttiva: boolean = false;
    public visibleValidator: boolean = false;
    public errorData = null
    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.debounce.pipe(
            debounceTime(350),
            distinctUntilChanged())
            .subscribe((value: any) => {
                this.onChangeEvent(value);
            }));

        this.isReady = true;
    }

    protected onAfterViewInit(): void {
        this.sdkSessionStorageService.setItem(Constants.APP_CODE, this.appConfig.environment.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
        this.appCode = this.appConfig.environment.APP_CODE;

        let authId: string = this.sdkSessionStorageService.getItem('spid-auth-id', 'SSA');
        if (authId != null && !isEmpty(authId)) {
            this.sdkSessionStorageService.setItem('spid-auth-id', null, 'SSA');
            let codein: string = this.sdkSessionStorageService.getItem('spid-auth-id-codein', 'SSA');
            this.isReady = true;
            this.userService.getUserSPID(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL, authId, codein, 'SPID').subscribe((responseData: any) => {
                let response: any = {
                    token: responseData.data.token,
                    dataCreazione: '',
                    refreshToken: responseData.data.refreshToken,
                };

                // load logout url spid
                if (response.token != null) {
                    let userInfo: TokenContent = decodeJwt(response.token);
                    if (userInfo.SSO_LOGOUT_URL != null)
                        this.sdkSessionStorageService.setItem(Constants.LOGOUT_PATH, userInfo.SSO_LOGOUT_URL, Constants.LOCAL_STORAGE_PREFIX);
                }

                if (!responseData.data.userExists) {
                    if (responseData.data.abilitaRegistrazione == true) {
                        let url = this.appConfig.environment.APP_LAUNCHER_CONTEXT_URL;
                        let params = {
                            authorizationCode: responseData.data.ssoJwtToken
                        }
                        this.redirectService.redirect(url, params);
                    } else {
                        let userInfo: TokenContent = decodeJwt(response.token);
                        let message = this.translateService.instant('ERRORS.NO-USER',
                            {
                                utente: userInfo.USER_LOGIN
                            });

                        this.sdkMessagePanelService.showError(this.messagesPanel, [
                            {
                                message
                            }
                        ]);
                    }

                } else {
                    let userInfo: TokenContent = decodeJwt(response.token);
                    let codiceFiscale = '';
                    if (this.appConfig.environment.LOGIN_MODE == 0) {
                        codiceFiscale = userInfo.preferred_username;
                    } else {
                        codiceFiscale = userInfo.USER_CF;
                    }
                    this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, response, Constants.LOCAL_STORAGE_PREFIX);
                    let factory = this.getChekUserFactory(codiceFiscale);
                    this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: UserExistsInfo) => {
                        if (result.exist === 'NO_USER' && result.abilitaRegistrazione === true) {
                            this.sdkRouterService.navigateToPage('registrazione-utente-page');
                        } else if (result.exist === 'NO_USER' && result.abilitaRegistrazione === false) {
                            this.redirectService.redirect(environment.APP_LAUNCHER_CONTEXT_URL + 'page/registrazione-utente-completata-page', { stato: '3', cf: codiceFiscale });
                            //this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '3' });
                        } else if (result.exist === 'DISABLED_USER') {
                            // Caso di utente registrato ma disabilitato. Forward a pagina di cortesia
                            //this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '2' });
                            this.redirectService.redirect(environment.APP_LAUNCHER_CONTEXT_URL + 'page/registrazione-utente-completata-page', { stato: '2', cf: codiceFiscale });
                        } else if (result.exist === 'OK_USER') {
                            this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, response, Constants.LOCAL_STORAGE_PREFIX);
                            if (isEmpty(codein) || codein === '-') {
                                let authTokenInfo: AuthenticationTokenInfo = JSON.parse(JSON.stringify(response)) as AuthenticationTokenInfo;
                                this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, authTokenInfo, Constants.LOCAL_STORAGE_PREFIX);
                                this.sdkSessionStorageService.setItem(Constants.APP_CODE, this.appConfig.environment.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
                                this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_APP_CODE, Constants.AUTHENTICATION_APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
                                this.routerService.navigateToPage('choose-ente-page');
                            } else {
                                this.codeinSelezionato = codein;
                                if (!responseData.data.uffintOk) {
                                    this.sdkMessagePanelService.showError(this.messagesPanel, [
                                        {
                                            message: 'ERRORS.NO-VALID-SA'
                                        }
                                    ]);
                                } else {
                                    if (this.appConfig.environment.LOGIN_MODE == 0) {
                                        this.userProfile = {
                                            nome: userInfo.name,
                                            cognome: userInfo.family_name,
                                            codiceFiscale: userInfo.preferred_username,
                                            internal: userInfo.internal
                                        };
                                    } else {
                                        this.userProfile = {
                                            nome: userInfo.USER_NAME,
                                            cognome: userInfo.USER_SURNAME,
                                            codiceFiscale: userInfo.USER_CF,
                                            login: userInfo.USER_LOGIN,
                                            internal: userInfo.internal
                                        };
                                    }
                                    this.userProfile.loa = userInfo.USER_LOA;
                                    this.userProfile.providerType = userInfo.USER_IDP_TYPE;
                                    this.loadData();
                                }
                            }
                        }
                    });
                }
            });


        } else {
            const factory = this.getInizSpidLogin();
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
                this.multiente = result.multiente;
                this.visibleValidator = result.visibleValidator;
                this.stazioniAppaltanti = result.stazioniAppaltanti;
                if (this.multiente) {
                    const factory2 = this.getSpidSa(null);
                    this.requestHelper.begin(factory2, this.messagesPanel).subscribe((result: any) => {
                        this.stazioniAppaltantiToshow = result.data?.stazioniAppaltanti;

                    });
                }

            });
        }
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.buttons = of({
                    buttons: this.config.body.buttons
                });
                this.providers = shuffle(this.config.body.providers);
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public getSpidUrl(idProvider: string) {
        let callbackUrl = encodeURIComponent(window.location.href);
        this.userService.getUrlSPID(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL, idProvider, callbackUrl, this.codeinSelezionato).subscribe((response: any) => {
            let authId = response.data.authId;
            this.sdkSessionStorageService.setItem('spid-auth-id', authId, 'SSA');
            this.sdkSessionStorageService.setItem('spid-auth-id-codein', this.codeinSelezionato, 'SSA');
            window.location.href = response.data.url;
        });
    }

    public onChangeEvent(event) {

        let val = event.target.value;
        if (val == "" || (val != null && val.length > 2)) {
            const factory = this.getSpidSa(val);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
                this.stazioniAppaltantiToshow = result.data.stazioniAppaltanti;
            });
        }
    }

    protected getInizSpidLogin(): any {
        return () => {
            return this.userService.getInizSpidLogin(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL);
        }
    }

    protected getSpidSa(searchUfficioIntestatario: string): any {
        return () => {
            return this.userService.getSpidSa(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL, searchUfficioIntestatario);
        }
    }

    public showMenu() {
        this.visibleMenu = !this.visibleMenu;
    }

    // #endregion

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get userService(): UserService { return this.injectable(UserService) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get redirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    // #endregion

    // #region Private

    public onButtonClick($event) {
        this.provider.run(this.config.body.logoutProvider, {
            type: 'BUTTON',
            data: {
                code: 'logout',
                messagesPanel: this.messagesPanel
            }
        }).subscribe();
    }

    public selezionaEnte(codiceSA, nomeSA) {
        this.codeinSelezionato = codiceSA;
        this.multiente = false;
    }

    private loadData(): void {
        this.loadLogoutUrl()
            .pipe(
                tap(this.elaborateLogoutUrl),
                mergeMap(this.loadAbilitazioni),
                map(this.elaborateAbilitazioni),
                mergeMap(this.getUserInfo),
                map(this.manageUserInfo)
            ).subscribe();
    }

    private getLogoutUrlFactory() {
        return () => {
            return this.tabellatiService.getLogoutUrl(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL);
        }
    }

    private loadLogoutUrl(): Observable<ResponseResult<string>> {
        const logoutUrlPresente: string = this.sdkSessionStorageService.getItem(Constants.LOGOUT_PATH, Constants.LOCAL_STORAGE_PREFIX);
        if (logoutUrlPresente == null && !isEmpty(logoutUrlPresente)) {
            const factory = this.getLogoutUrlFactory();
            return this.requestHelper.begin(factory, null, false);
        }
        return new Observable((ob: Observer<ResponseResult<string>>) => {
            ob.next({});
            ob.complete();
        });
    }

    private elaborateLogoutUrl = (response: ResponseResult<string>) => {
        if (response.data != null) {
            this.sdkSessionStorageService.setItem(Constants.LOGOUT_PATH, response.data, Constants.LOCAL_STORAGE_PREFIX);
        }
    }

    private loadAbilitazioni = () => {
        let factory = this.getAbilitazioniFactory();
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private getAbilitazioniFactory() {
        return () => {
            return this.userService.getAbilitazioni(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL)
        }
    }

    private elaborateAbilitazioni = (data: OpzioniUtenteProdotto) => {
        this.userProfile = {
            ...this.userProfile,
            abilitazioni: data.ou,
            opzioniProdotto: data.op
        };
        this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
    }

    private getUserInfo = () => {
        let getUserInfo = this.getUserInfoFactory('');
        return this.requestHelper.begin(getUserInfo, this.messagesPanel, true, false);
    }

    private getUserInfoFactory(searchSA: string) {
        return () => {
            return this.userService.getUserInfo(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, searchSA)
        }
    }

    private getChekUserFactory(codiceFiscaleUtente: string) {
        return () => {
            return this.authenticationService.checkExistingUser(codiceFiscaleUtente);
        }
    }

    private manageUserInfo = (response: UserAccountInfo) => {

        this.chiaviAccessoOrt = response.chiaviAccesso;
        this.syscon = response.syscon;
        this.stazioniAppaltanti = response.stazioniAppaltanti;
        this.ruolo = response.ruolo;
        this.richiestaAssistenzaAttiva = response.richiestaAssistenzaAttiva;
        let urlManuali: string = response.urlManuali;
        if (!isEmpty(urlManuali)) {
            this.sdkSessionStorageService.setItem(Constants.MANUALI_URL_STORAGE_CODE, urlManuali, Constants.LOCAL_STORAGE_PREFIX);
        }

        this.store.dispatch(new SdkStoreAction(Constants.LISTA_STAZIONI_APPALTANTI_COUNT_DISPATCHER, 1));

        this.markForCheck();

        let getSaInfo = this.getSAInfoFactory(this.codeinSelezionato);
        this.requestHelper.begin(getSaInfo, this.messagesPanel).subscribe(this.elaborateSAInfo);
    }

    private getSAInfoFactory(codiceSA: string) {
        return () => {
            return this.userService.getSAInfo(this.appConfig.environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceSA, this.appCode, this.syscon)
        }
    }

    private elaborateSAInfo = (response: StazioneAppaltanteInfo): void => { // salvataggio utente aggiornato con campo syscon

        this.userProfile = {
            ...this.userProfile,
            syscon: this.syscon,
            chiaviAccessoOrt: this.chiaviAccessoOrt,
            ruolo: this.ruolo,
            richiestaAssistenzaAttiva: this.richiestaAssistenzaAttiva,
        };

        this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
        this.sdkSessionStorageService.setItem('spid-auth-id-codein', null, 'SSA');
        // salvataggio stazione appaltante selezionata
        this.store.dispatch(new SdkStoreAction(Constants.SA_INFO_DISPATCHER, response));

        this.routerService.navigateToPage('choose-profile-page');
    }
}
