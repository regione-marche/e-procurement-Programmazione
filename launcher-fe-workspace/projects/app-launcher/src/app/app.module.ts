import './app.locale';

import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { Injector, NgModule, Type } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import { BrowserModule, HammerModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {
  SDK_APP_CONFIG,
  SdkAbstractWidget,
  SdkClickModule,
  SdkDebounceClickModule,
  SdkFriendlyCaptchaModule,
  createTranslateLoader,
} from '@maggioli/sdk-commons';
import {
  SdkButtonModule,
  SdkFormBuilderModule,
  SdkFormModule,
  SdkLoaderModule,
  SdkMessagePanelModule,
} from '@maggioli/sdk-controls';
import { SdkLayoutRenderedModule, SdkWidgetsModule } from '@maggioli/sdk-widgets';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { OAuthModule } from 'angular-oauth2-oidc';
import { forOwn } from 'lodash-es';
import { ButtonModule } from 'primeng/button';

import { SdkGestioneUtentiModule } from '@maggioli/sdk-gestione-utenti';
import { environment } from '../environments/environment';
import { AppWidget } from './app.component';
import { Constants } from './app.constants';
import { elementsMap } from './app.elements';
import { AppRoutingModule } from './app.routing';
import { IndexSectionWidget } from './modules/layout/components/business/index/index-section.widget';
import { LoginSectionWidget } from './modules/layout/components/business/login/login-section.widget';
import {
  RegistrazioneUtenteCompletataSectionWidget,
} from './modules/layout/components/business/registrazione-utente-completata/registrazione-utente-completata-section.widget';
import {
  RegistrazioneUtenteSectionWidget,
} from './modules/layout/components/business/registrazione-utente/registrazione-utente-section.widget';
import { SpidLoginSectionWidget } from './modules/layout/components/business/spid-login/spid-login-section.widget';
import { SdkAppWidget } from './modules/layout/components/sdk-pages/sdk-app.widget';
import { SdkNotFoundWidget } from './modules/layout/components/sdk-pages/sdk-not-found.widget';
import { AuthenticationInterceptor } from './modules/services/authentication/authentication.interceptor';
import { OauthLoginSectionWidget } from './modules/layout/components/business/oauth-login/oauth-login-section.widget';

@NgModule({
  declarations: [
    AppWidget,

    IndexSectionWidget,
    LoginSectionWidget,
    RegistrazioneUtenteSectionWidget,
    RegistrazioneUtenteCompletataSectionWidget,
    SpidLoginSectionWidget,
    OauthLoginSectionWidget,
    SdkAppWidget,
    SdkNotFoundWidget
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HammerModule,
    HttpClientModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    SdkWidgetsModule,
    SdkLayoutRenderedModule,
    SdkLoaderModule,
    SdkMessagePanelModule,
    OAuthModule.forRoot(),
    ButtonModule,
    SdkFormModule,
    SdkFormBuilderModule,
    SdkButtonModule,
    SdkDebounceClickModule,
    SdkClickModule,
    SdkGestioneUtentiModule.forRoot(),
    SdkFriendlyCaptchaModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthenticationInterceptor, multi: true },
    {
      provide: SDK_APP_CONFIG,
      useValue: {
        environment,
        APP_NAME: Constants.APP_NAME
      }
    }
  ],
  bootstrap: [AppWidget]
})
export class AppLauncher {

  constructor(private inj: Injector) { this.defineElements() }

  protected defineElements(): void {
    forOwn(elementsMap(), this.defineElement)
  }

  protected defineElement = (type: Type<SdkAbstractWidget<any>>, key: string): void => {
    customElements.define(key, createCustomElement(type, { injector: this.inj }))
  }

}
