import './app.locale';

import { HTTP_INTERCEPTORS, HttpClient, HttpClientModule } from '@angular/common/http';
import { Injector, NgModule, Type } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import { BrowserModule, HammerModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppCommonsModule, OauthLoginSectionWidget, SpidLoginSectionWidget } from '@maggioli/app-commons';
import {
  SDK_APP_CONFIG,
  SdkAbstractWidget,
  SdkClickModule,
  SdkFormatModule,
  createTranslateLoader,
} from '@maggioli/sdk-commons';
import {
  SdkButtonModule,
  SdkDateModule,
  SdkDialogModule,
  SdkFormBuilderModule,
  SdkFormModule,
  SdkLoaderModule,
  SdkMenuModule,
  SdkMessagePanelModule,
  SdkModalModule,
  SdkNotificationModule,
  SdkSearchModule,
  SdkSidebarModule,
  SdkTabsModule,
} from '@maggioli/sdk-controls';
import { SdkGestioneUtentiModule } from '@maggioli/sdk-gestione-utenti';
import { SdkTableModule } from '@maggioli/sdk-table';
import { SdkLayoutRenderedModule, SdkWidgetsModule } from '@maggioli/sdk-widgets';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { OAuthModule } from 'angular-oauth2-oidc';
import { forOwn } from 'lodash-es';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CheckboxModule } from 'primeng/checkbox';
import { environment } from '../environments/environment';
import { AppWidget } from './app.component';
import { Constants } from './app.constants';
import { customElementsMap, elementsMap } from './app.elements';
import { AppRoutingModule } from './app.routing';
import { ChooseEnteSectionWidget } from './modules/layout/components/business/choose-ente/choose-ente-section.widget';
import {
  ChooseProfileSectionWidget,
} from './modules/layout/components/business/choose-profile/choose-profile-section.widget';
import { CuiModalWidget } from './modules/layout/components/business/cui-modal/cui-modal.widget';
import {
  DettaglioProgrammaDatiGeneraliSectionWidget,
} from './modules/layout/components/business/dettaglio-programma/dett-prog-dati-generali/dett-prog-dati-generali-section.widget';
import {
  DettaglioProgrammaInterventiNonRipropostiSectionWidget,
} from './modules/layout/components/business/dettaglio-programma/dett-prog-interventi-non-riproposti/dett-prog-interventi-non-riproposti-section.widget';
import {
  DettaglioProgrammaInterventiSectionWidget,
} from './modules/layout/components/business/dettaglio-programma/dett-prog-interventi/dett-prog-interventi-section.widget';
import {
  DettaglioProgrammaOpereIncompiuteSectionWidget,
} from './modules/layout/components/business/dettaglio-programma/dett-prog-opere-incompiute/dett-prog-opere-incompiute-section.widget';
import {
  DettaglioProgrammaPubblicaSectionWidget,
} from './modules/layout/components/business/dettaglio-programma/dett-prog-pubblica/dett-prog-pubblica-section.widget';
import {
  DettaglioProgrammaRiepilogoSectionWidget,
} from './modules/layout/components/business/dettaglio-programma/dett-prog-riepilogo/dett-prog-riepilogo-section.widget';
import { IndexSectionWidget } from './modules/layout/components/business/index/index-section.widget';
import { ExportInterventiAcquistiNrModalWidget } from './modules/layout/components/business/interventi-non-riproposti/export-interventi-acquisti-nr-modal/export-interventi-acquisti-nr-modal.widget';
import {
  ImportInterventiNonRipropostiSectionWidget,
} from './modules/layout/components/business/interventi-non-riproposti/import/import-interventi-non-riproposti-section.widget';
import {
  UpdateInterventoNoRipModalWidget,
} from './modules/layout/components/business/interventi-non-riproposti/modifica/update-intervento-norip-modal.widget';
import {
  NuovoInterventoNonRipropostoSectionWidget,
} from './modules/layout/components/business/interventi-non-riproposti/nuovo-intervento-nr/nuovo-intervento-nr-section.widget';
import {
  DettaglioInterventoSectionWidget,
} from './modules/layout/components/business/interventi/dett-intervento/dett-intervento-section.widget';
import { ExportInterventiAcquistiModalWidget } from './modules/layout/components/business/interventi/export-interventi-acquisti-modal/export-interventi-acquisti-modal.widget';
import {
  ImportInterventiSectionWidget,
} from './modules/layout/components/business/interventi/import/import-interventi-section.widget';
import {
  ModificaInterventoSectionWidget,
} from './modules/layout/components/business/interventi/modifica-intervento/modifica-intervento-section.widget';
import {
  NuovoInterventoSectionWidget,
} from './modules/layout/components/business/interventi/nuovo-intervento/nuovo-intervento-section.widget';
import {
  ListaProgrammiSectionWidget,
} from './modules/layout/components/business/lista-programmi/lista-programmi-section.widget';
import {
  ModificaProgrammaSectionWidget,
} from './modules/layout/components/business/modifica-programma/modifica-programma-section.widget';
import {
  NuovoProgrammaSectionWidget,
} from './modules/layout/components/business/nuovo-programma/nuovo-programma-section.widget';
import {
  DettaglioAltriDatiOperaIncompiutaSectionWidget,
} from './modules/layout/components/business/opera-incompiuta/dett-altri-dati-opera-incompiuta/dett-altri-dati-opera-incompiuta-section.widget';
import { DettaglioCupModalWidget } from './modules/layout/components/business/opera-incompiuta/dett-cup-modal/dett-cup-modal.widget';
import {
  DettaglioGeneraleOperaIncompiutaSectionWidget,
} from './modules/layout/components/business/opera-incompiuta/dett-generale-opera-incompiuta/dett-generale-opera-incompiuta-section.widget';
import {
  ModificaAltriDatiOperaIncompiutaSectionWidget,
} from './modules/layout/components/business/opera-incompiuta/modifica-altri-dati-opera-incompiuta/modifica-altri-dati-opera-incompiuta-section.widget';
import {
  ModificaGeneraleOperaIncompiutaSectionWidget,
} from './modules/layout/components/business/opera-incompiuta/modifica-generale-opera-incompiuta/modifica-generale-opera-incompiuta-section.widget';
import {
  NuovaOperaIncompiutaSectionWidget,
} from './modules/layout/components/business/opera-incompiuta/nuova-opera-incompiuta/nuova-opera-incompiuta-section.widget';
import {
  DettaglioRisorsaDiCapitoloSectionWidget,
} from './modules/layout/components/business/risorse-di-capitolo/dettaglio-risorsa-di-capitolo/dettaglio-risorsa-di-capitolo-section.widget';
import {
  ListaRisorseDiCapitoloSectionWidget,
} from './modules/layout/components/business/risorse-di-capitolo/lista/lista-risorse-di-capitolo-section.widget';
import {
  ModificaRisorsaDiCapitoloSectionWidget,
} from './modules/layout/components/business/risorse-di-capitolo/modifica/modifica-risorsa-di-capitolo-section.widget';
import {
  NuovaRisorsaDiCapitoloSectionWidget,
} from './modules/layout/components/business/risorse-di-capitolo/nuova-risorsa-di-capitolo/nuova-risorsa-di-capitolo-section.widget';
import { LayoutMenuTabsOldWidget } from './modules/layout/components/layout/layout-menu-tabs-old.widget';
import { LayoutMenuTabsWidget } from './modules/layout/components/layout/layout-menu-tabs.widget';
import { LayoutSideMenuTabsWidget } from './modules/layout/components/layout/layout-side-menu-tabs.widget';
import { SdkAppWidget } from './modules/layout/components/sdk-pages/sdk-app.widget';
import { SdkNotFoundWidget } from './modules/layout/components/sdk-pages/sdk-not-found.widget';
import { AuthenticationInterceptor } from './modules/services/authentication/authentication.interceptor';
import { ConfrontoProgrammiModalWidget } from './modules/layout/components/business/dettaglio-programma/confronto-programmi-modal/confronto-programmi-modal.widget';
import { SdkGestioneReportsModule } from '@maggioli/sdk-gestione-reports';
import { DettaglioInterventoNonRipropostoSectionWidget } from './modules/layout/components/business/interventi-non-riproposti/dettaglio/dettaglio-intervento-non-riproposto-section.widget';
import { ModificaInterventoNonRipropostoSectionWidget } from './modules/layout/components/business/interventi-non-riproposti/modifica/modifica-intervento-non-riproposto-section.widget';
import { SdkGestioneModelliModule } from '@maggioli/sdk-gestione-modelli';

const widgets: Array<any> = [
  CuiModalWidget,
  DettaglioCupModalWidget,
  IndexSectionWidget,
  ChooseEnteSectionWidget,
  ChooseProfileSectionWidget,
  ListaProgrammiSectionWidget,
  DettaglioProgrammaDatiGeneraliSectionWidget,
  DettaglioProgrammaOpereIncompiuteSectionWidget,
  DettaglioProgrammaInterventiSectionWidget,
  DettaglioProgrammaInterventiNonRipropostiSectionWidget,
  DettaglioProgrammaRiepilogoSectionWidget,
  DettaglioProgrammaPubblicaSectionWidget,
  ImportInterventiNonRipropostiSectionWidget,
  NuovoProgrammaSectionWidget,
  ModificaProgrammaSectionWidget,
  ImportInterventiSectionWidget,
  ModificaGeneraleOperaIncompiutaSectionWidget,
  DettaglioGeneraleOperaIncompiutaSectionWidget,
  DettaglioAltriDatiOperaIncompiutaSectionWidget,
  NuovaOperaIncompiutaSectionWidget,
  ModificaAltriDatiOperaIncompiutaSectionWidget,
  SpidLoginSectionWidget,
  OauthLoginSectionWidget,
  DettaglioInterventoSectionWidget,
  UpdateInterventoNoRipModalWidget,
  NuovoInterventoSectionWidget,
  ModificaInterventoSectionWidget,
  ListaRisorseDiCapitoloSectionWidget,
  NuovaRisorsaDiCapitoloSectionWidget,
  DettaglioRisorsaDiCapitoloSectionWidget,
  ModificaRisorsaDiCapitoloSectionWidget,
  NuovoInterventoNonRipropostoSectionWidget,
  ExportInterventiAcquistiModalWidget,
  ExportInterventiAcquistiNrModalWidget,
  ConfrontoProgrammiModalWidget,
  DettaglioInterventoNonRipropostoSectionWidget,
  ModificaInterventoNonRipropostoSectionWidget
];

@NgModule({
  declarations: [
    AppWidget,
    LayoutMenuTabsWidget,
    LayoutSideMenuTabsWidget,
    LayoutMenuTabsOldWidget,

    widgets,

    SdkAppWidget,
    SdkNotFoundWidget
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    SdkWidgetsModule.forRoot(),
    SdkLayoutRenderedModule,
    SdkMessagePanelModule,
    SdkNotificationModule,
    SdkButtonModule,
    SdkSearchModule,
    SdkFormModule,
    SdkFormBuilderModule,
    SdkModalModule,
    SdkDateModule,
    SdkLoaderModule,
    SdkDialogModule,
    SdkMenuModule,
    SdkTableModule.forRoot(),
    SdkTabsModule,
    SdkSidebarModule,
    SdkFormatModule,
    AppCommonsModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    }),
    HammerModule,
    SdkGestioneUtentiModule.forRoot(),
    SdkGestioneModelliModule.forRoot(),
    TableModule,
    InputTextModule,
    OAuthModule.forRoot(),
    SdkClickModule,
    FormsModule,
    ReactiveFormsModule,
    CheckboxModule,
    SdkGestioneReportsModule.forRoot()
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
  bootstrap: [
    AppWidget
  ]
})
export class AppProgrammiModule {

  constructor(private inj: Injector) { this.defineElements() }

  protected defineElements(): void {
    forOwn(elementsMap(), this.defineElement)

    forOwn(customElementsMap(), this.defineCustomElement)
  }

  protected defineElement = (type: Type<SdkAbstractWidget<any>>, key: string): void => {
    customElements.define(key, createCustomElement(type, { injector: this.inj }))
  }


  protected defineCustomElement = (type: Type<any>, key: string): void => {
    customElements.define(key, createCustomElement(type, { injector: this.inj }))
  }

}
