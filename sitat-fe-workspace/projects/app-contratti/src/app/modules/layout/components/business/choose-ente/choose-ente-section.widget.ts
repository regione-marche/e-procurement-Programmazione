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
import {
    AuthenticationTokenInfo,
    ChiaviAccessoOrt,
    HttpRequestHelper,
    OpzioniUtenteProdotto,
    ResponseResult,
    StazioneAppaltanteBaseInfo,
    StazioneAppaltanteInfo,
    TabellatiService,
    UserAccountInfo,
    UserService
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkRouterService,
    SdkSessionStorageService,
    SdkStoreAction,
    SdkStoreService,
    UserProfile,
    decodeJwt,
} from '@maggioli/sdk-commons';
import { head, isEmpty, isObject, size, toString } from 'lodash-es';
import { Observable, Observer, Subject, fromEvent } from 'rxjs';
import { debounceTime, distinctUntilChanged, map, mergeMap, tap } from 'rxjs/operators';

import { environment } from '../../../../../../environments/environment';
import { Constants } from '../../../../../app.constants';

@Component({
    templateUrl: `choose-ente-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChooseEnteSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `choose-ente-section-widget`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('content') _content: ElementRef;

    public config: any = {};
    private userProfile: UserProfile;
    private syscon: string;
    public stazioniAppaltanti: Array<StazioneAppaltanteBaseInfo>;
    private appCode: string;
    private chiaviAccessoOrt: ChiaviAccessoOrt;
    private ruolo: string;
    private richiestaAssistenzaAttiva: boolean = false;
    private userEmail: string;
    private debounce: Subject<any> = new Subject<any>();
    private _searchSA: string;
    private messaggisticaInternaAttiva: boolean;

    public debounceSearch$: Subject<any> = new Subject<any>();
    public vediTutteSA: boolean = false;
    public totalSACount: number;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.debounce.pipe(
            debounceTime(350),
            distinctUntilChanged())
            .subscribe((value: IDictionary<number>) => {
                this.resizeComponent(value.offsetTop, value.viewportHeight);
            }));
        this.addSubscription(this.debounceSearch$.pipe(
            debounceTime(350),
            distinctUntilChanged())
            .subscribe((event: any) => {
                let value: string = event.target.value;
                this.searchSA = value;
            }));
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
    }

    protected onAfterViewInit(): void {
        this.initResizeObservable();
        this.appCode = this.sdkSessionStorageService.getItem(Constants.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
        this.storeUserAuthInfo();
        this.loadData();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get userService(): UserService { return this.injectable(UserService) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    // #endregion

    // #region Private

    private storeUserAuthInfo(): void {
        let authTokenInfo = this.sdkSessionStorageService.getItem<AuthenticationTokenInfo>(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
        let userInfo: any = decodeJwt(authTokenInfo.token);

        if (environment.LOGIN_MODE != 0) {

            this.userProfile = {
                nome: userInfo.USER_NAME,
                cognome: userInfo.USER_SURNAME,
                codiceFiscale: userInfo.USER_CF,
                login: userInfo.USER_LOGIN,
                internal: userInfo.internal,
                loa: userInfo.USER_LOA,
                providerType: userInfo.USER_IDP_TYPE
            };
            this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
        } else {
            let cf = userInfo.preferred_username != null ? (userInfo.preferred_username as string).replace('tinit-', '') : null;
            if (userInfo.internal === true) {

                let userProfile = {
                    nome: userInfo.given_name,
                    cognome: userInfo.family_name,
                    codiceFiscale: cf,
                    login: cf,
                    loa: userInfo.auth_level,
                    providerType: userInfo.auth_type,
                    internal: userInfo.internal
                };
                this.userProfile = userProfile;
            }

            if (this.userProfile != null && this.userProfile.login == null) {
                this.userProfile.login = cf;
            }

            this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
        }
    }

    private getUserInfo = () => {
        let getUserInfo = this.getUserInfoFactory(this.searchSA);
        return this.requestHelper.begin(getUserInfo, this.messagesPanel, true, false);
    }

    private getUserInfoFactory(searchSA: string) {
        return () => {
            return this.userService.getUserInfo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, searchSA)
        }
    }

    private manageUserInfo = (response: UserAccountInfo) => {

        this.chiaviAccessoOrt = response.chiaviAccesso;
        this.syscon = response.syscon;
        this.stazioniAppaltanti = response.stazioniAppaltanti;
        this.ruolo = response.ruolo;
        this.richiestaAssistenzaAttiva = response.richiestaAssistenzaAttiva;
        this.userEmail = response.userEmail;
        let urlManuali: string = response.urlManuali;
        this.totalSACount = response.totalSACount;
        this.messaggisticaInternaAttiva = response.messaggisticaInternaAttiva;
        if (!isEmpty(urlManuali)) {
            this.sdkSessionStorageService.setItem(Constants.MANUALI_URL_STORAGE_CODE, urlManuali, Constants.LOCAL_STORAGE_PREFIX);
        }

        this.store.dispatch(new SdkStoreAction(Constants.LISTA_STAZIONI_APPALTANTI_COUNT_DISPATCHER, this.totalSACount));

        this.markForCheck();
        // se e' presente solamente una stazione appaltante, la seleziono e navigo alla pagina di scelta profili
        if (size(this.stazioniAppaltanti) === 1 && !this.vediTutteSA && this.searchSA == null) {
            let first: StazioneAppaltanteBaseInfo = head(this.stazioniAppaltanti);
            this.selezionaEnte(first.codice);
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private getAbilitazioniFactory() {
        return () => {
            return this.userService.getAbilitazioni(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL)
        }
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
            return this.tabellatiService.getLogoutUrl(environment.GESTIONE_TABELLATI_BASE_URL);
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

    private elaborateAbilitazioni = (data: OpzioniUtenteProdotto) => {
        this.userProfile = {
            ...this.userProfile,
            abilitazioni: data.ou,
            opzioniProdotto: data.op
        };
        if (this.userProfile.abilitazioni.includes(Constants.OU_ABILITA_TUTTI_UFFICI_INTESTATARI)) {
            this.vediTutteSA = true;
        }
        this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
    }

    private getSAInfoFactory(codiceSA: string) {
        return () => {
            return this.userService.getSAInfo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceSA, this.appCode, this.syscon, true)
        }
    }

    private elaborateSAInfo = (response: StazioneAppaltanteInfo): void => { // salvataggio utente aggiornato con campo syscon

        this.userProfile = {
            ...this.userProfile,
            syscon: this.syscon,
            chiaviAccessoOrt: this.chiaviAccessoOrt,
            ruolo: this.ruolo,
            richiestaAssistenzaAttiva: this.richiestaAssistenzaAttiva,
            userEmail: this.userEmail,
            messaggisticaInternaAttiva: this.messaggisticaInternaAttiva
        };

        this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));

        // salvataggio stazione appaltante selezionata
        this.store.dispatch(new SdkStoreAction(Constants.SA_INFO_DISPATCHER, response));

        this.routerService.navigateToPage('choose-profile-page');
    }

    private get content(): HTMLElement {
        return this._content.nativeElement;
    }

    private initResizeObservable(): void {
        if (this.content) {
            const initialOffsetTop: number = this.content.offsetTop;
            const initialViewportHeight: number = window.innerHeight;
            this.resizeComponent(initialOffsetTop, initialViewportHeight);
            this.addSubscription(fromEvent(window, 'resize').subscribe((event: any) => {
                const currentOffsetTop: number = this.content.offsetTop;
                const currentViewportHeight: number = event.target.innerHeight;
                this.debounce.next({
                    offsetTop: currentOffsetTop,
                    viewportHeight: currentViewportHeight
                });
            }));
        }
    }

    private resizeComponent(offsetTop: number, viewportHeight: number): void {
        if (isFinite(offsetTop) && isFinite(viewportHeight) && viewportHeight >= offsetTop) {
            const totalHeight: number = viewportHeight - offsetTop;
            this.content.style.height = `${totalHeight}px`;
        }
    }

    private set searchSA(value: string) {
        this._searchSA = isEmpty(value) ? null : value;
        // load
        this.getUserInfo().pipe(
            map(this.manageUserInfo)
        ).subscribe();
    }

    private get searchSA(): string {
        return this._searchSA;
    }

    // #endregion

    // #region Public

    public selezionaEnte(codiceSA: string): void {
        let getSaInfo = this.getSAInfoFactory(codiceSA);
        this.requestHelper.begin(getSaInfo, this.messagesPanel).subscribe(this.elaborateSAInfo);
    }

    public trackByCode(index: number, sa: StazioneAppaltanteBaseInfo): string {
        return sa != null ? sa.codice : toString(index);
    }

    // #endregion

}
