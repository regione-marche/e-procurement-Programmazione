import { ChangeDetectorRef, Component, HostListener, Injector, OnDestroy, OnInit } from '@angular/core';
import {
  IDictionary,
  SdkAbstractView,
  SdkAppConfig,
  SdkConfigLoader,
  SdkLocaleService,
  SdkProviderService,
  SdkRedirectService,
  SdkRouterService,
  SdkSessionStorageService,
  SdkStateMap,
  SdkStoreAction,
  SdkStoreService,
  SdkValidatorService,
  decodeJwt,
  getUrlWithTimestamp
} from '@maggioli/sdk-commons';
import { AppHomeIndexPanel, SdkStyleLoader } from '@maggioli/sdk-widgets';
import { TranslateService } from '@ngx-translate/core';
import { get, isEmpty } from 'lodash-es';
import { PrimeNGConfig } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { OAuthService } from 'angular-oauth2-oidc';
import { environment } from '../environments/environment';
import { Constants } from './app.constants';
import { providers } from './app.providers';
import { reducers } from './app.reducers';
import { validators } from './app.validators';
import { AuthenticationTokenInfo, RichiestaTokenResponse, UserExistsInfo } from './modules/models/authentication/authentication.model';
import { AuthenticationService } from './modules/services/authentication/authentication.service';

@Component({
  selector: 'app-launcher',
  template: '<div *ngIf="isReady"><sdk-layout-blocco></sdk-layout-blocco><router-outlet></router-outlet><sdk-loader></sdk-loader></div>'
})
export class AppWidget extends SdkAbstractView implements OnInit, OnDestroy {

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {
    this.initConfig().pipe(
      mergeMap(this.initTitle),
      mergeMap(this.initLang),
      mergeMap(this.loadLang),
      mergeMap(this.initState),
      mergeMap(this.initStyles),
      mergeMap(this.initProviders),
      mergeMap(this.initValidators)
    ).subscribe({
      next: this.onLoad,
      error: this.onError
    })
  }

  protected onAfterViewInit(): void { }

  protected onDestroy(): void { }

  /**
   * @ignore
   */
  protected onUpdateState(state: boolean): void { }

  // #endregion

  // #region Private

  private initConfig = (): Observable<SdkAppConfig> => {
    return this.loader.load(getUrlWithTimestamp(this.configUrl(Constants.APP_NAME)));
  }

  private initTitle = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    return of(config);
  }

  private initLang = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    this.translate.setDefaultLang(config.settings.locale.langCode);
    this.translate.use(config.settings.locale.langCode);

    // set locale in base a descrittore
    let locale: string = config.settings.locale.langCode;
    this.sdkLocaleService.locale = locale;
    let currency: string = config.settings.locale.currencyCode;
    this.sdkLocaleService.currency = currency;

    return of(config);
  }

  private loadLang = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    return this.translate.get('primeng').pipe(map((res) => {
      this.primeNGConfig.setTranslation(res);
      return config;
    }));
  }

  private initStyles = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    return this.style.load(config.styleUrls).pipe(map(() => config));
  }

  private initState = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    this.store.init(new SdkStateMap(config), reducers());

    return of(config);
  }

  private initProviders = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    this.provider.init(providers());

    return of(config);
  }

  private initValidators = (config: SdkAppConfig): Observable<SdkAppConfig> => {
    this.validator.init(validators());

    return of(config);
  }

  private configUrl = (name: string) => `assets/cms/app/${name}.json`;

  private onLoad = (_config: SdkAppConfig) => {
    this.initOauth();
    this.isReady = true;
  }

  private onError = (err: Error) => this.logger.error('AppWidget::initConfig', err);

  private removeTokenInfo(): void {
    this.sdkSessionStorageService.removeItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
  }

  private initOauth() {
    if (!isEmpty(environment.OAUTH2_CONFIGURATION) && environment.LOGIN_MODE == 0) {
      let auth: AuthenticationTokenInfo = this.sdkSessionStorageService.getItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
      let userInfo: any = auth != null && auth.token != null ? decodeJwt(auth.token) : null;
      if (auth == null) {

        let navigate = true;
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: any) => {
          if (userProfile != null) {
            navigate = false;
          }
        }));

        this.oauthService.configure(environment.OAUTH2_CONFIGURATION);
        this.oauthService.setStorage(sessionStorage);
        this.oauthService.loadDiscoveryDocumentAndTryLogin();
        this.oauthService.setupAutomaticSilentRefresh();
        this.oauthService.events.subscribe(e => {
          // salvataggio token
          if (e.type === 'token_received') {
            let authItem = this.updateOauthItem();
            if (navigate) {
              let userProfileToscana: any = this.oauthService.getIdentityClaims();
              let cf: string = userProfileToscana.preferred_username;
              if (cf.startsWith('tinit-'))
                cf = cf.replace('tinit-', '');
              let userProfile = {
                nome: userProfileToscana.given_name,
                cognome: userProfileToscana.family_name,
                codiceFiscale: cf,
                login: cf,
                loa: this.handleLoa(userProfileToscana.auth_level),
                providerType: this.handleProviderType(userProfileToscana.auth_type)
              };
              this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, userProfile));
              this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_APP_CODE, Constants.AUTHENTICATION_APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
              this.sdkSessionStorageService.setItem(Constants.USER_PROFILE_TO_LAUNCHER, userProfile, Constants.LOCAL_STORAGE_PREFIX);
              this.checkUserExists(cf, authItem);
            }
          }
        });
      } else if (userInfo != null && userInfo.internal != true) {
        this.oauthService.configure(environment.OAUTH2_CONFIGURATION);
        this.oauthService.setStorage(sessionStorage);
        this.oauthService.loadDiscoveryDocumentAndTryLogin();
        this.oauthService.setupAutomaticSilentRefresh();
        this.oauthService.events.subscribe(e => {
          // salvataggio token
          if (e.type === 'token_received') {
            this.updateOauthItem();
          }
        });
      }
    }
  }

  private handleLoa(loa: string): string {
    switch (loa) {
      case '1':
        return '2';
      case '2':
        return '3';
      case '3':
        return '4';
      default:
        return '2';
    }
  }

  private handleProviderType(idpType: string): string {
    switch (idpType) {
      case 'CieId':
        return 'CIE';
      default:
        return 'SPID';
    }
  }

  private checkUserExists(codiceFiscale: string, authItem: RichiestaTokenResponse): void {
    const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
    if (moduloSelezionato != null) {
      const baseUrl: string = this.authenticationService.getApplicationLoginConfiguration(moduloSelezionato).AUTHENTICATION_CHECK_USER_BASE_URL;
      this.authenticationService.checkExistingUser(baseUrl, codiceFiscale).pipe(
        map((result: UserExistsInfo) => {
          if (result.exist === 'OK_USER') {
            this.authenticationService.clearAuthInfo();
            let redirectUrl: string = get(environment, moduloSelezionato.targetUrl);
            let params: IDictionary<string> = {
              auth: JSON.stringify(authItem),
              appCode: moduloSelezionato.panelCode,
              loa: null
            }
            this.sdkRedirectService.redirect(redirectUrl, params);
          } else if (result.exist === 'NO_USER' && result.abilitaRegistrazione) {
            this.sdkSessionStorageService.setItem(Constants.CURRENT_MODULE, moduloSelezionato, Constants.LOCAL_STORAGE_PREFIX);
            this.sdkRouterService.navigateToPage('registrazione-utente-page');
          } else if (result.exist === 'DISABLED_USER') {
            // Caso di utente registrato ma disabilitato. Forward a pagina di cortesia
            this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '2', cf: codiceFiscale });
          } else if (result.exist === 'EXPIRED_USER') {
            // Caso di utente registrato ma con ultimo accesso più vecchio di dutrata Account.
            this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '4', cf: codiceFiscale });
          } else {
            this.sdkRouterService.navigateToPage('registrazione-utente-completata-page', { stato: '3', cf: codiceFiscale });
          }
        })
      ).subscribe();
    }
  }

  private updateOauthItem(): any {
    let authItem = {
      token: this.oauthService.getAccessToken(),
      dataCreazione: '',
      refreshToken: this.oauthService.getRefreshToken()
    };
    this.oauthService.loadUserProfile();
    this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, authItem, Constants.LOCAL_STORAGE_PREFIX);
    return authItem;
  }

  // #endregion

  // #region Getters

  private get translate(): TranslateService { return this.injectable(TranslateService) }

  private get loader(): SdkConfigLoader { return this.injectable(SdkConfigLoader) }

  private get style(): SdkStyleLoader { return this.injectable(SdkStyleLoader) }

  private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

  private get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

  private get sdkLocaleService(): SdkLocaleService { return this.injectable(SdkLocaleService) }

  private get primeNGConfig(): PrimeNGConfig { return this.injectable(PrimeNGConfig) }

  private get oauthService(): OAuthService { return this.injectable(OAuthService) }

  private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

  private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

  private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

  // #endregion

  // #region Public

  @HostListener('window:unload', ['$event'])
  public unloadHandler(event) {
    this.removeTokenInfo();
  }

  @HostListener('window:beforeunload', ['$event'])
  public beforeUnloadHander(event) {
    this.removeTokenInfo();
  }

  // #endregion

}

