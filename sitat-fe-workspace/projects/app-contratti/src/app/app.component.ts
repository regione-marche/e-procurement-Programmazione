import { ChangeDetectorRef, Component, HostListener, Injector, OnDestroy, OnInit } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { AuthenticationTokenInfo, TabellatiService } from '@maggioli/app-commons';
import {
  SdkAbstractView,
  SdkAppConfig,
  SdkConfigLoader,
  SdkLocaleService,
  SdkProviderService,
  SdkRouterService,
  SdkSessionStorageService,
  SdkStateMap,
  SdkStoreAction,
  SdkStoreService,
  SdkValidatorService,
  decodeJwt,
  getUrlWithTimestamp,
} from '@maggioli/sdk-commons';
import { TranslateService } from '@ngx-translate/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { get, has, isEmpty, isObject } from 'lodash-es';
import { PrimeNGConfig } from 'primeng/api';
import { Observable, of } from 'rxjs';
import { map, mergeMap, tap } from 'rxjs/operators';

import { environment } from '../environments/environment';
import { Constants } from './app.constants';
import { providers } from './app.providers';
import { reducers } from './app.reducers';
import { validators } from './app.validators';
import { SdkStyleLoader } from '@maggioli/sdk-widgets';

@Component({
  selector: 'app-contratti',
  template: '<div *ngIf="isReady"><sdk-layout-blocco></sdk-layout-blocco><router-outlet></router-outlet><sdk-loader></sdk-loader></div>'
})
export class AppWidget extends SdkAbstractView implements OnInit, OnDestroy {

  constructor(inj: Injector, cdr: ChangeDetectorRef) {
    super(inj, cdr);
  }

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

    if (has(config, 'settings.loadRemoteTitle') === true && get(config, 'settings.loadRemoteTitle') === true) {
      return this.tabellatiService.getApplicationTitle(environment.GESTIONE_TABELLATI_PUBLIC_BASE_URL)
        .pipe(
          tap((data: string) => { this.title.setTitle(data) }),
          mergeMap((data: string) => of(config))
        );
    }
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

  private initOauth() {
    if (!isEmpty(environment.OAUTH2_CONFIGURATION) && environment.LOGIN_MODE == 0) {
      let auth: AuthenticationTokenInfo = this.sdkSessionStorageService.getItem<AuthenticationTokenInfo>(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
      let userInfo: any = auth != null && auth.token != null ? decodeJwt(auth.token) : null;
      if (userInfo != null && userInfo.internal != true) {
        let navigate = true;
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: any) => {
          if (isObject(userProfile)) {
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
            this.updateOauthItem();
          }
        });

        this.updateOauthItem();

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
          this.sdkSessionStorageService.setItem(Constants.APP_CODE, environment.APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
          this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_APP_CODE, Constants.AUTHENTICATION_APP_CODE, Constants.LOCAL_STORAGE_PREFIX);
          this.sdkSessionStorageService.setItem(Constants.USER_PROFILE_TO_LAUNCHER, userProfile, Constants.LOCAL_STORAGE_PREFIX);
          this.routerService.navigateToPage('choose-ente-page');
        }
      }
    }
  }

  private configUrl = (name: string) => `assets/cms/app/${name}.json`;

  private onLoad = (_config: SdkAppConfig) => {
    this.initOauth();
    this.isReady = true
  }

  private onError = (err: Error) => this.logger.error('AppWidget::initConfig', err);

  private removeTokenInfo(): void {
    this.sdkSessionStorageService.removeItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
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

  private updateOauthItem(): void {
    let authItem = {
      token: this.oauthService.getAccessToken(),
      dataCreazione: '',
      refreshToken: this.oauthService.getRefreshToken()
    };
    this.oauthService.loadUserProfile();
    this.sdkSessionStorageService.setItem(Constants.AUTHENTICATION_TOKEN_INFO, authItem, Constants.LOCAL_STORAGE_PREFIX);
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

  private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

  private get title(): Title { return this.injectable(Title) }

  private get primeNGConfig(): PrimeNGConfig { return this.injectable(PrimeNGConfig) }

  private get oauthService(): OAuthService { return this.injectable(OAuthService) }

  private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }


  // #endregion

  // #region Public

  @HostListener('window:unload', ['$event'])
  public unloadHandler(event) {
    if (environment.production === true) {
      this.removeTokenInfo();
    }
  }

  @HostListener('window:beforeunload', ['$event'])
  public beforeUnloadHander(event) {
    if (environment.production === true) {
      this.removeTokenInfo();
    }
  }

  // #endregion

}
