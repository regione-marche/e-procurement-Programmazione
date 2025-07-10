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
  SdkPanelbarModule,
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

import { environment } from '../environments/environment';
import { AppWidget } from './app.component';
import { Constants } from './app.constants';
import { customElementsMap, elementsMap } from './app.elements';
import { AppRoutingModule } from './app.routing';
import {
  DettaglioAttoSectionWidget,
} from './modules/layout/components/business/atti/dettaglio/dettaglio-atto-section.widget';
import { ListaAttiSectionWidget } from './modules/layout/components/business/atti/lista-atti/lista-atti-section.widget';
import { LottiAttoSectionWidget } from './modules/layout/components/business/atti/lotti-atto/lotti-atto-section.widget';
import { ModificaAttoSectionWidget } from './modules/layout/components/business/atti/modifica/modifica-atto-section.widget';
import { NuovoAttoSectionWidget } from './modules/layout/components/business/atti/nuovo/nuovo-atto-section.widget';
import {
  PubblicaAttoSectionWidget,
} from './modules/layout/components/business/atti/pubblica-atto/pubblica-atto-section.widget';
import {
  DettaglioAvvisoSectionWidget,
} from './modules/layout/components/business/avvisi/dettaglio-avviso/dettaglio/dettaglio-avviso-section.widget';
import {
  PubblicaAvvisoSectionWidget,
} from './modules/layout/components/business/avvisi/dettaglio-avviso/pubblicazioni/pubblica-avviso-section.widget';
import {
  ListaAvvisiSectionWidget,
} from './modules/layout/components/business/avvisi/lista-avvisi/lista-avvisi-section.widget';
import {
  ModificaAvvisoSectionWidget,
} from './modules/layout/components/business/avvisi/modifica-avviso/modifica-avviso-section.widget';
import {
  NuovoAvvisoSectionWidget,
} from './modules/layout/components/business/avvisi/nuovo-avviso/nuovo-avviso-section.widget';
import {
  RicercaAvanzataAvvisiSectionWidget,
} from './modules/layout/components/business/avvisi/ricerca-avanzata-avvisi/ricerca-avanzata-avvisi-section.widget';
import {
  CentroDiCostoModalWidget,
} from './modules/layout/components/business/centro-di-costo-modal/centro-di-costo-modal.widget';
import { ChooseEnteSectionWidget } from './modules/layout/components/business/choose-ente/choose-ente-section.widget';
import {
  ChooseProfileSectionWidget,
} from './modules/layout/components/business/choose-profile/choose-profile-section.widget';
import { CredenzialiSimogModalWidget } from './modules/layout/components/business/credenziali-simog/credenziali-simog-modal/credenziali-simog-modal.widget';
import { DettaglioDelegaSectionWidget } from './modules/layout/components/business/deleghe/dettaglio/dettaglio-delega-section.widget';
import { ListaDelegheSectionWidget } from './modules/layout/components/business/deleghe/lista-deleghe/lista-deleghe-section.widget';
import { ModificaDelegaSectionWidget } from './modules/layout/components/business/deleghe/modifica/modifica-delega-section.widget';
import { NuovoDelegaSectionWidget } from './modules/layout/components/business/deleghe/nuovo/nuovo-delega-section.widget';
import {
  DettaglioImpresaInvitataPartecipanteSectionWidget,
} from './modules/layout/components/business/fasi/elenco-impr-inv-parte/dettaglio/dettaglio-impr-inv-parte-section.widget';
import {
  ListaImpreseInvitatePartecipantiSectionWidget,
} from './modules/layout/components/business/fasi/elenco-impr-inv-parte/lista/lista-impr-inv-parte-section.widget';
import {
  ModificaImpresaInvitataPartecipanteSectionWidget,
} from './modules/layout/components/business/fasi/elenco-impr-inv-parte/modifica/modifica-impr-inv-parte-section.widget';
import {
  NuovaImpresaInvitataPartecipanteSectionWidget,
} from './modules/layout/components/business/fasi/elenco-impr-inv-parte/nuova/nuova-impr-inv-parte-section.widget';
import {
  DettaglioFaseAccordoBonarioSectionWidget,
} from './modules/layout/components/business/fasi/fase-accordo-bonario/dettaglio/dettaglio-fase-accordo-bonario-section.widget';
import {
  ModificaFaseAccordoBonarioSectionWidget,
} from './modules/layout/components/business/fasi/fase-accordo-bonario/modifica/modifica-fase-accordo-bonario-section.widget';
import {
  NuovaFaseAccordoBonarioSectionWidget,
} from './modules/layout/components/business/fasi/fase-accordo-bonario/nuova/nuova-fase-accordo-bonario-section.widget';
import {
  DettaglioFaseAdesioneAccordoQuadroSectionWidget,
} from './modules/layout/components/business/fasi/fase-adesione-accordo-quadro/dettaglio/dettaglio-fase-adesione-accordo-quadro-section.widget';
import {
  ModificaFaseAdesioneAccordoQuadroSectionWidget,
} from './modules/layout/components/business/fasi/fase-adesione-accordo-quadro/modifica/modifica-fase-adesione-accordo-quadro-section.widget';
import {
  NuovaFaseAdesioneAccordoQuadroSectionWidget,
} from './modules/layout/components/business/fasi/fase-adesione-accordo-quadro/nuova/nuova-fase-adesione-accordo-quadro-section.widget';
import {
  DettaglioFaseAggiudicazioneSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-aggiudicazione-semp/dettaglio/dettaglio-fase-agg-semp-section.widget';
import {
  ModificaFaseAggiudicazioneSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-aggiudicazione-semp/modifica/modifica-fase-agg-semp-section.widget';
import {
  NuovaFaseAggiudicazioneSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-aggiudicazione-semp/nuova/nuova-fase-agg-semp-section.widget';
import {
  DettaglioFaseAggiudicazioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-aggiudicazione/dettaglio/dettaglio-fase-agg-section.widget';
import {
  ModificaFaseAggiudicazioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-aggiudicazione/modifica/modifica-fase-agg-section.widget';
import {
  NuovaFaseAggiudicazioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-aggiudicazione/nuova/nuova-fase-agg-section.widget';
import {
  DettaglioFaseAvanzamentoSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-avanzamento-semp/dettaglio/dettaglio-fase-avanzamento-semp-section.widget';
import {
  ModificaFaseAvanzamentoSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-avanzamento-semp/modifica/modifica-fase-avanzamento-semp-section.widget';
import {
  NuovaFaseAvanzamentoSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-avanzamento-semp/nuova/nuova-fase-avanzamento-semp-section.widget';
import {
  DettaglioFaseAvanzamentoSectionWidget,
} from './modules/layout/components/business/fasi/fase-avanzamento/dettaglio/dettaglio-fase-avanz-section.widget';
import {
  ModificaFaseAvanzamentoSectionWidget,
} from './modules/layout/components/business/fasi/fase-avanzamento/modifica/modifica-fase-avanz-section.widget';
import {
  NuovaFaseAvanzamentoSectionWidget,
} from './modules/layout/components/business/fasi/fase-avanzamento/nuova/nuova-fase-avanz-section.widget';
import {
  DettaglioFaseCantieriSectionWidget,
} from './modules/layout/components/business/fasi/fase-cantieri/dettaglio/dettaglio-fase-cantieri-section.widget';
import {
  ModificaFaseCantieriSectionWidget,
} from './modules/layout/components/business/fasi/fase-cantieri/modifica/modifica-fase-cantieri-section.widget';
import {
  NuovaFaseCantieriSectionWidget,
} from './modules/layout/components/business/fasi/fase-cantieri/nuova/nuova-fase-cantieri-section.widget';
import {
  DettaglioFaseCollaudoSectionWidget,
} from './modules/layout/components/business/fasi/fase-collaudo/dettaglio/dettaglio-fase-collaudo-section.widget';
import {
  ModificaFaseCollaudoSectionWidget,
} from './modules/layout/components/business/fasi/fase-collaudo/modifica/modifica-fase-collaudo-section.widget';
import {
  NuovaFaseCollaudoSectionWidget,
} from './modules/layout/components/business/fasi/fase-collaudo/nuova/nuova-fase-collaudo-section.widget';
import {
  DettaglioFaseComunicazioneEsitoSectionWidget,
} from './modules/layout/components/business/fasi/fase-comunicazione-esito/dettaglio/dettaglio-com-esito-section.widget';
import {
  ModificaComunicazioneEsitoSectionWidget,
} from './modules/layout/components/business/fasi/fase-comunicazione-esito/modifica/modifica-com-esito-section.widget';
import {
  NuovaComunicazioneEsitoSectionWidget,
} from './modules/layout/components/business/fasi/fase-comunicazione-esito/nuova/nuova-com-esito-section.widget';
import {
  DettaglioFaseConclusioneSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-conclusione-semp/dettaglio/dettaglio-fase-conclusione-semp-section.widget';
import {
  ModificaFaseConclusioneSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-conclusione-semp/modifica/modifica-fase-conclusione-semp-section.widget';
import {
  NuovaFaseConclusioneSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-conclusione-semp/nuova/nuova-fase-conclusione-semp-section.widget';
import {
  DettaglioFaseConclusioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-conclusione/dettaglio/dettaglio-fase-conclusione-section.widget';
import {
  ModificaFaseConclusioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-conclusione/modifica/modifica-fase-conclusione-section.widget';
import {
  NuovaFaseConclusioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-conclusione/nuova/nuova-fase-conclusione-section.widget';
import {
  DettaglioFaseInadempienzeSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/fase-inadempienze-sicurezza/dettaglio/dettaglio-fase-inadempienze-sicurezza-section.widget';
import {
  ModificaFaseInadempienzeSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/fase-inadempienze-sicurezza/modifica/modifica-fase-inadempienze-sicurezza-section.widget';
import {
  NuovaFaseInadempienzeSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/fase-inadempienze-sicurezza/nuova/nuova-fase-inadempienze-sicurezza-section.widget';
import {
  DettaglioFaseInfortuniSectionWidget,
} from './modules/layout/components/business/fasi/fase-infortuni/dettaglio/dettaglio-fase-infortuni-section.widget';
import {
  ModificaFaseInfortuniSectionWidget,
} from './modules/layout/components/business/fasi/fase-infortuni/modifica/modifica-fase-infortuni-section.widget';
import {
  NuovaFaseInfortuniSectionWidget,
} from './modules/layout/components/business/fasi/fase-infortuni/nuova/nuova-fase-infortuni-section.widget';
import {
  DettaglioFaseInidoneitaContributivaSectionWidget,
} from './modules/layout/components/business/fasi/fase-inidoneita-contributiva/dettaglio/dettaglio-fase-inidoneita-contributiva-section.widget';
import {
  ModificaFaseInidoneitaContributivaSectionWidget,
} from './modules/layout/components/business/fasi/fase-inidoneita-contributiva/modifica/modifica-fase-inidoneita-contributiva-section.widget';
import {
  NuovaFaseInidoneitaContributivaSectionWidget,
} from './modules/layout/components/business/fasi/fase-inidoneita-contributiva/nuova/nuova-fase-inidoneita-contributiva-section.widget';
import {
  DettaglioFaseInidoneitaTecnicoProfSectionWidget,
} from './modules/layout/components/business/fasi/fase-inidoneita-tecnico-prof/dettaglio/dettaglio-fase-inidoneita-tecnico-prof-section.widget';
import {
  ModificaFaseInidoneitaTecnicoProfSectionWidget,
} from './modules/layout/components/business/fasi/fase-inidoneita-tecnico-prof/modifica/modifica-fase-inidoneita-tecnico-prof-section.widget';
import {
  NuovaFaseInidoneitaTecnicoProfSectionWidget,
} from './modules/layout/components/business/fasi/fase-inidoneita-tecnico-prof/nuova/nuova-fase-inidoneita-tecnico-prof-section.widget';
import {
  DettaglioFaseInizialeSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-iniziale-semp/dettaglio/dettaglio-fase-iniziale-semp-section.widget';
import {
  ModificaFaseInizialeSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-iniziale-semp/modifica/modifica-fase-iniziale-semp-section.widget';
import {
  NuovaFaseInizialeSempSectionWidget,
} from './modules/layout/components/business/fasi/fase-iniziale-semp/nuova/nuova-fase-iniziale-semp-section.widget';
import {
  DettaglioFaseInizialeSectionWidget,
} from './modules/layout/components/business/fasi/fase-iniziale/dettaglio/dettaglio-fase-iniziale-section.widget';
import {
  ModificaFaseInizialeSectionWidget,
} from './modules/layout/components/business/fasi/fase-iniziale/modifica/modifica-fase-iniziale-section.widget';
import {
  NuovaFaseInizialeSectionWidget,
} from './modules/layout/components/business/fasi/fase-iniziale/nuova/nuova-fase-iniziale-section.widget';
import {
  DettaglioFaseModificaContrattualeSectionWidget,
} from './modules/layout/components/business/fasi/fase-modifica-contrattuale/dettaglio/dettaglio-fase-modifica-contrattuale-section.widget';
import {
  ModificaFaseModificaContrattualeSectionWidget,
} from './modules/layout/components/business/fasi/fase-modifica-contrattuale/modifica/modifica-fase-modifica-contrattuale-section.widget';
import {
  NuovaFaseModificaContrattualeSectionWidget,
} from './modules/layout/components/business/fasi/fase-modifica-contrattuale/nuova/nuova-fase-modifica-contrattuale-section.widget';
import {
  DettaglioFaseRecessoSectionWidget,
} from './modules/layout/components/business/fasi/fase-recesso/dettaglio/dettaglio-fase-recesso-section.widget';
import {
  ModificaFaseRecessoSectionWidget,
} from './modules/layout/components/business/fasi/fase-recesso/modifica/modifica-fase-recesso-section.widget';
import {
  NuovaFaseRecessoSectionWidget,
} from './modules/layout/components/business/fasi/fase-recesso/nuova/nuova-fase-recesso-section.widget';
import {
  DettaglioFaseSospensioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-sospensione/dettaglio/dettaglio-fase-sospensione-section.widget';
import {
  ModificaFaseSospensioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-sospensione/modifica/modifica-fase-sospensione-section.widget';
import {
  NuovaFaseSospensioneSectionWidget,
} from './modules/layout/components/business/fasi/fase-sospensione/nuova/nuova-fase-sospensione-section.widget';
import {
  DettaglioFaseStipulaAccordoQuadroSectionWidget,
} from './modules/layout/components/business/fasi/fase-stipula-accordo-quadro/dettaglio/dettaglio-fase-stipula-accordo-quadro-section.widget';
import {
  ModificaFaseStipulaAccordoQuadroSectionWidget,
} from './modules/layout/components/business/fasi/fase-stipula-accordo-quadro/modifica/modifica-fase-stipula-accordo-quadro-section.widget';
import {
  NuovaFaseStipulaAccordoQuadroSectionWidget,
} from './modules/layout/components/business/fasi/fase-stipula-accordo-quadro/nuova/nuova-fase-stipula-accordo-quadro-section.widget';
import {
  DettaglioFaseSubappaltoSectionWidget,
} from './modules/layout/components/business/fasi/fase-subappalto/dettaglio/dettaglio-fase-subappalto-section.widget';
import {
  ModificaFaseSubappaltoSectionWidget,
} from './modules/layout/components/business/fasi/fase-subappalto/modifica/modifica-fase-subappalto-section.widget';
import {
  NuovaFaseSubappaltoSectionWidget,
} from './modules/layout/components/business/fasi/fase-subappalto/nuova/nuova-fase-subappalto-section.widget';
import {
  DettaglioFinanziamentiSectionWidget,
} from './modules/layout/components/business/fasi/finanziamenti/dettaglio/dettaglio-finanziamenti-section.widget';
import {
  ModificaFinanziamentiSectionWidget,
} from './modules/layout/components/business/fasi/finanziamenti/modifica/modifica-finanziamenti-section.widget';
import {
  DettaglioImpresaAggiudicatariaSectionWidget,
} from './modules/layout/components/business/fasi/imprese-aggiudicatarie/dettaglio/dettaglio-impr-agg-section.widget';
import {
  ImportImpreseAggiudicatarieSectionWidget,
} from './modules/layout/components/business/fasi/imprese-aggiudicatarie/import/import-impr-agg-section.widget';
import {
  ListaImpreseAggiudicatarieSectionWidget,
} from './modules/layout/components/business/fasi/imprese-aggiudicatarie/lista/lista-impr-agg-section.widget';
import {
  ModificaImpresaAggiudicatariaSectionWidget,
} from './modules/layout/components/business/fasi/imprese-aggiudicatarie/modifica/modifica-impr-agg-section.widget';
import {
  NuovaImpresaAggiudicatariaSectionWidget,
} from './modules/layout/components/business/fasi/imprese-aggiudicatarie/nuova/nuova-impr-agg-section.widget';
import {
  DettaglioIncarichiProfessionaliSectionWidget,
} from './modules/layout/components/business/fasi/incarichi-professionali/dettaglio/dettaglio-inc-prof-section.widget';
import {
  ModificaIncarichiProfessionaliSectionWidget,
} from './modules/layout/components/business/fasi/incarichi-professionali/modifica/modifica-inc-prof-section.widget';
import {
  ListaFasiLottoSectionWidget,
} from './modules/layout/components/business/fasi/lista-fasi-lotto/lista-fasi-lotto-section.widget';
import {
  ListaInviiFasiSectionWidget,
} from './modules/layout/components/business/fasi/lista-invii-fasi/lista-invii-fasi-section.widget';
import {
  DettaglioMisureAggiuntiveSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/misure-aggiuntive-sicurezza/dettaglio/dettaglio-mis-agg-sic-section.widget';
import {
  ModificaMisureAggiuntiveSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/misure-aggiuntive-sicurezza/modifica/modifica-mis-agg-sic-section.widget';
import {
  NuoveMisureAggiuntiveSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/misure-aggiuntive-sicurezza/nuove/nuove-mis-agg-sic-section.widget';
import { NuovaFaseSectionWidget } from './modules/layout/components/business/fasi/nuova-fase/nuova-fase-section.widget';
import {
  PubblicaFaseLottoSectionWidget,
} from './modules/layout/components/business/fasi/pubblica-fase-lotto/pubblica-fase-lotto-section.widget';
import {
  DettaglioSchedaSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/scheda-sicurezza/dettaglio/dettaglio-sche-sic-section.widget';
import {
  ModificaSchedaSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/scheda-sicurezza/modifica/modifica-sche-sic-section.widget';
import {
  NuovaSchedaSicurezzaSectionWidget,
} from './modules/layout/components/business/fasi/scheda-sicurezza/nuova/nuova-sche-sic-section.widget';
import {
  AccorpamentoLottiSectionWidget,
} from './modules/layout/components/business/gare/accorpamento-lotti/accorpamento-lotti/accorpamento-lotti-section.widget';
import {
  RiepilogoAccorpamentoLottiSectionWidget,
} from './modules/layout/components/business/gare/accorpamento-lotti/riepilogo-accorpamento-lotti/riepilogo-accorpamento-lotti-section.widget';
import {
  DettaglioGaraSectionWidget,
} from './modules/layout/components/business/gare/dettaglio-gara/dettaglio-gara-section.widget';
import {
  ImportaGaraSimogSectionWidget,
} from './modules/layout/components/business/gare/importa-gara-simog/importa-gara-simog-section.widget';
import {
  ListaCollaborazioniModalWidget,
} from './modules/layout/components/business/gare/lista-collaborazioni-modal/lista-collaborazioni-modal.widget';
import { ListaGareSectionWidget } from './modules/layout/components/business/gare/lista-gare/lista-gare-section.widget';
import {
  ListaTecniciModalWidget,
} from './modules/layout/components/business/gare/lista-tecnici-modal/lista-tecnici-modal.widget';
import { ModificaGaraSectionWidget } from './modules/layout/components/business/gare/modifica/modifica-gara-section.widget';
import { NuovaGaraSectionWidget } from './modules/layout/components/business/gare/nuova/nuova-gara-section.widget';
import {
  PubblicaGaraSectionWidget,
} from './modules/layout/components/business/gare/pubblica-gara/pubblica-gara-section.widget';
import {
  RicercaAvanzataGareSectionWidget,
} from './modules/layout/components/business/gare/ricerca-avanzata-gare/ricerca-avanzata-gare-section.widget';
import { ImpresaModalWidget } from './modules/layout/components/business/impresa-modal/impresa-modal.widget';
import { IndexSectionWidget } from './modules/layout/components/business/index/index-section.widget';
import {
  DettaglioLottoSectionWidget,
} from './modules/layout/components/business/lotti/dettaglio/dettaglio-lotto-section.widget';
import {
  ListaAttiLottoSectionWidget,
} from './modules/layout/components/business/lotti/lista-atti-lotto/lista-atti-lotto-section.widget';
import { ListaLottiSectionWidget } from './modules/layout/components/business/lotti/lista-lotti/lista-lotti-section.widget';
import {
  ModificaLottoSectionWidget,
} from './modules/layout/components/business/lotti/modifica/modifica-lotto-section.widget';
import { NuovoLottoSectionWidget } from './modules/layout/components/business/lotti/nuovo/nuovo-lotto-section.widget';
import {
  DettaglioSmartCigSectionWidget,
} from './modules/layout/components/business/smartcig/dettaglio/dettaglio-smartcig-section.widget';
import {
  GestioneSmartCigSectionWidget,
} from './modules/layout/components/business/smartcig/gestione/gestione-smartcig-section.widget';
import {
  ImportaSmartCigSectionWidget,
} from './modules/layout/components/business/smartcig/importa/importa-smartcig-section.widget';
import {
  ModificaSmartCigSectionWidget,
} from './modules/layout/components/business/smartcig/modifica/modifica-smartcig-section.widget';
import {
  NuovoSmartCigSectionWidget,
} from './modules/layout/components/business/smartcig/nuovo/nuovo-smartcig-section.widget';
import {
  GraficaDocumentPanelSectionWidget,
} from './modules/layout/components/grafica/document-panel/grafica-document-panel-section.widget';
import {
  GraficaFormGroupSectionWidget,
} from './modules/layout/components/grafica/form-group/grafica-form-group-section.widget';
import { GraficaSectionWidget } from './modules/layout/components/grafica/grafica-section.widget';
import { GraficaListaSectionWidget } from './modules/layout/components/grafica/lista/grafica-lista-section.widget';
import {
  GraficaMessagePanelSectionWidget,
} from './modules/layout/components/grafica/message-panel/grafica-message-panel-section.widget';
import { GraficaTabellaSectionWidget } from './modules/layout/components/grafica/tabella/grafica-tabella-section.widget';
import { LayoutMenuTabsOldWidget } from './modules/layout/components/layout/layout-menu-tabs-old.widget';
import { LayoutMenuTabsWidget } from './modules/layout/components/layout/layout-menu-tabs.widget';
import { LayoutSideMenuTabsWidget } from './modules/layout/components/layout/layout-side-menu-tabs.widget';
import { SdkAppWidget } from './modules/layout/components/sdk-pages/sdk-app.widget';
import { SdkNotFoundWidget } from './modules/layout/components/sdk-pages/sdk-not-found.widget';
import { AuthenticationInterceptor } from './modules/services/authentication/authentication.interceptor';
import { NuovaFaseInizSottoscrizioneContrattoSectionWidget } from './modules/layout/components/business/fasi/fase-iniziale-sottoscrizione/nuova/nuova-fase-iniz-sottoscrizione-contratto-section.widget';
import { DettaglioFaseInizSottoscrizioneContrattoSectionWidget } from './modules/layout/components/business/fasi/fase-iniziale-sottoscrizione/dettaglio/dettaglio-fase-iniz-sottoscrizione-contratto-section.widget';
import { ModificaFaseInizSottoscrizioneContrattoSectionWidget } from './modules/layout/components/business/fasi/fase-iniziale-sottoscrizione/modifica/modifica-fase-iniz-sottoscrizione-contratto-section.widget';
import { ModificaFaseConclusioneAffidamentiDirettiSectionWidget } from './modules/layout/components/business/fasi/fase-conclusione-affidamenti-diretti/modifica/modifica-fase-conclusione-affidamenti-diretti-section.widget';
import { DettaglioFaseConclusioneAffidamentiDirettiSectionWidget } from './modules/layout/components/business/fasi/fase-conclusione-affidamenti-diretti/dettaglio/dettaglio-fase-conclusione-affidamenti-diretti-section.widget';
import { NuovaFaseConclusioneAffidamentiDirettiSectionWidget } from './modules/layout/components/business/fasi/fase-conclusione-affidamenti-diretti/nuova/nuova-fase-conclusione-affidamenti-diretti-section.widget';
import { NuovaFaseRipresaPrestazioneSectionWidget } from './modules/layout/components/business/fasi/fase-ripresa-prestazione/nuova/nuova-fase-ripresa-prestazione-section.widget';
import { DettaglioFaseRipresaPrestazioneSectionWidget } from './modules/layout/components/business/fasi/fase-ripresa-prestazione/dettaglio/dettaglio-fase-ripresa-prestazione-section.widget';
import { ModificaFaseRipresaPrestazioneSectionWidget } from './modules/layout/components/business/fasi/fase-ripresa-prestazione/modifica/modifica-fase-ripresa-prestazione-section.widget';
import { NuovaFaseSuperamentoQuartoContrattualeSectionWidget } from './modules/layout/components/business/fasi/fase-superamento-quarto-contrattuale/nuova/nuova-fase-superamento-quarto-contrattuale-section.widget';
import { DettaglioFaseSuperamentoQuartoContrattualeSectionWidget } from './modules/layout/components/business/fasi/fase-superamento-quarto-contrattuale/dettaglio/dettaglio-fase-superamento-quarto-contrattuale-section.widget';
import { ModificaFaseSuperamentoQuartoContrattualeSectionWidget } from './modules/layout/components/business/fasi/fase-superamento-quarto-contrattuale/modifica/modifica-fase-superamento-quarto-contrattuale-section.widget';
import { ModificaFaseRichiestaSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-richiesta-subappalto/modifica/modifica-fase-richiesta-subappalto-section.widget';
import { DettaglioFaseRichiestaSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-richiesta-subappalto/dettaglio/dettaglio-fase-richiesta-subappalto-section.widget';
import { NuovaFaseRichiestaSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-richiesta-subappalto/nuova/nuova-fase-richiesta-subappalto-section.widget';
import { ModificaFaseEsitoSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-esito-subappalto/modifica/modifica-fase-esito-subappalto-section.widget';
import { DettaglioFaseEsitoSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-esito-subappalto/dettaglio/dettaglio-fase-esito-subappalto-section.widget';
import { NuovaFaseEsitoSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-esito-subappalto/nuova/nuova-fase-esito-subappalto-section.widget';
import { ModificaFaseConclusioneSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-conclusione-subappalto/modifica/modifica-fase-conclusione-subappalto-section.widget';
import { DettaglioFaseConclusioneSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-conclusione-subappalto/dettaglio/dettaglio-fase-conclusione-subappalto-section.widget';
import { NuovaFaseConclusioneSubappaltoSectionWidget } from './modules/layout/components/business/fasi/fase-conclusione-subappalto/nuova/nuova-fase-conclusione-subappalto-section.widget';
import { SdkGestioneModelliModule } from '@maggioli/sdk-gestione-modelli';
import { DettaglioFaseIncarichiProfessionaliSectionWidget } from './modules/layout/components/business/fasi/fase-incarichi-professionali/dettaglio/dettaglio-fase-inc-prof-section.widget';
import { ModificaFaseIncarichiProfessionaliSectionWidget } from './modules/layout/components/business/fasi/fase-incarichi-professionali/modifica/modifica-fase-inc-prof-section.widget';
import { NuovaFaseIncarichiProfessionaliSectionWidget } from './modules/layout/components/business/fasi/fase-incarichi-professionali/nuovo/nuovo-fase-inc-prof-section.widget';
import { ImpersonificaSectionWidget } from './modules/layout/components/business/impersonifica-rup/impersonifica-rup-section.widget';
import { DatiContabilitaModalWidget } from './modules/layout/components/business/dati-contabilita-modal/dati-contabilita-modal.widget';
import { TabMenuModule } from 'primeng/tabmenu';

import { SdkGestioneReportsModule } from '@maggioli/sdk-gestione-reports';

import { RicercaSchedeTrasmessePcpSectionWidget } from './modules/layout/components/business/ricerca-schede-trasmesse-pcp/ricerca/ricerca-schede-trasmesse-pcp-section.widget';
import { ListaSchedeTrasmessePcpSectionWidget } from './modules/layout/components/business/ricerca-schede-trasmesse-pcp/lista/lista-schede-trasmesse-pcp-section.widget';

import { ListaAttiGeneraliSectionWidget } from './modules/layout/components/business/atti-generali/lista-atti-generali/lista-atti-generali-section.widget';
import { RicercaAvanzataAttiGeneraliSectionWidget } from './modules/layout/components/business/atti-generali/ricerca-avanzata-atti-generali/ricerca-avanzata-atti-generali-section.widget';
import { NuovoAttoGeneraleSectionWidget } from './modules/layout/components/business/atti-generali/carica-nuovo-atto-generale/nuovo-atto-generale-section.widget';
import { DettaglioAttoGeneraleSectionWidget } from './modules/layout/components/business/atti-generali/dettaglio-atto/dettaglio/dettaglio-atto-generale-section.widget';
import { ModificaDettaglioAttoGeneraleSectionWidget } from './modules/layout/components/business/atti-generali/dettaglio-atto/modifica-dettaglio/modifica-dettaglio-atto-generale-section.widget';


import { DettaglioFaseVariazioneAggiudicatariSectionWidget } from './modules/layout/components/business/fasi/fase-variazione-aggiudicatari/dettaglio/dettaglio-fase-variazione-aggiudicatari-section.widget';
import { ModificaFaseVariazioneAggiudicatariSectionWidget } from './modules/layout/components/business/fasi/fase-variazione-aggiudicatari/modifica/modifica-fase-variazione-aggiudicatari-section.widget';
import { NuovaFaseVariazioneAggiudicatariSectionWidget } from './modules/layout/components/business/fasi/fase-variazione-aggiudicatari/nuovo/nuovo-fase-variazione-aggiudicatari-section.widget';
import { SdkAppaltiecontrattiBaseModule } from '@maggioli/sdk-appaltiecontratti-base';
import { SdkDocAssociatiModule } from '@maggioli/sdk-docassociati';


const widgets: Array<any> = [
  IndexSectionWidget,
  ChooseEnteSectionWidget,
  NuovoAvvisoSectionWidget,
  ListaAvvisiSectionWidget,
  ChooseProfileSectionWidget,
  RicercaAvanzataAvvisiSectionWidget,
  DettaglioAvvisoSectionWidget,
  PubblicaAvvisoSectionWidget,
  ModificaAvvisoSectionWidget,
  RicercaAvanzataGareSectionWidget,
  ListaGareSectionWidget,
  DettaglioGaraSectionWidget,
  CentroDiCostoModalWidget,
  ModificaGaraSectionWidget,
  ListaLottiSectionWidget,
  ListaAttiSectionWidget,
  NuovoAttoSectionWidget,
  DettaglioAttoSectionWidget,
  ModificaAttoSectionWidget,
  DettaglioLottoSectionWidget,
  LottiAttoSectionWidget,
  ModificaLottoSectionWidget,
  ImpresaModalWidget,
  ImportaGaraSimogSectionWidget,
  ImportImpreseAggiudicatarieSectionWidget,
  NuovaImpresaAggiudicatariaSectionWidget,
  PubblicaGaraSectionWidget,
  PubblicaAttoSectionWidget,
  NuovaGaraSectionWidget,
  NuovoLottoSectionWidget,

  ListaFasiLottoSectionWidget,
  NuovaFaseSectionWidget,

  NuovaFaseInizSottoscrizioneContrattoSectionWidget,
  DettaglioFaseInizSottoscrizioneContrattoSectionWidget,
  ModificaFaseInizSottoscrizioneContrattoSectionWidget,

  NuovaFaseInizialeSectionWidget,
  DettaglioFaseInizialeSectionWidget,
  ModificaFaseInizialeSectionWidget,
  DettaglioSchedaSicurezzaSectionWidget,
  ModificaSchedaSicurezzaSectionWidget,
  NuovaSchedaSicurezzaSectionWidget,

  NuovaFaseAggiudicazioneSectionWidget,
  DettaglioFaseAggiudicazioneSectionWidget,
  ModificaFaseAggiudicazioneSectionWidget,
  ListaImpreseAggiudicatarieSectionWidget,
  DettaglioImpresaAggiudicatariaSectionWidget,
  ModificaImpresaAggiudicatariaSectionWidget,
  DettaglioIncarichiProfessionaliSectionWidget,
  ModificaIncarichiProfessionaliSectionWidget,
  DettaglioFinanziamentiSectionWidget,
  ModificaFinanziamentiSectionWidget,

  NuovaFaseAvanzamentoSectionWidget,
  DettaglioFaseAvanzamentoSectionWidget,
  ModificaFaseAvanzamentoSectionWidget,

  NuovaComunicazioneEsitoSectionWidget,
  DettaglioFaseComunicazioneEsitoSectionWidget,
  ModificaComunicazioneEsitoSectionWidget,

  NuovaFaseAggiudicazioneSempSectionWidget,
  DettaglioFaseAggiudicazioneSempSectionWidget,
  ModificaFaseAggiudicazioneSempSectionWidget,

  ListaImpreseInvitatePartecipantiSectionWidget,
  NuovaImpresaInvitataPartecipanteSectionWidget,
  DettaglioImpresaInvitataPartecipanteSectionWidget,
  ModificaImpresaInvitataPartecipanteSectionWidget,

  NuovaFaseSospensioneSectionWidget,
  DettaglioFaseSospensioneSectionWidget,
  ModificaFaseSospensioneSectionWidget,

  NuovaFaseRipresaPrestazioneSectionWidget,
  DettaglioFaseRipresaPrestazioneSectionWidget,
  ModificaFaseRipresaPrestazioneSectionWidget,

  NuovaFaseSuperamentoQuartoContrattualeSectionWidget,
  DettaglioFaseSuperamentoQuartoContrattualeSectionWidget,
  ModificaFaseSuperamentoQuartoContrattualeSectionWidget,

  NuovaFaseAvanzamentoSempSectionWidget,
  DettaglioFaseAvanzamentoSempSectionWidget,
  ModificaFaseAvanzamentoSempSectionWidget,

  NuovaFaseModificaContrattualeSectionWidget,
  DettaglioFaseModificaContrattualeSectionWidget,
  ModificaFaseModificaContrattualeSectionWidget,

  ModificaFaseAdesioneAccordoQuadroSectionWidget,
  DettaglioFaseAdesioneAccordoQuadroSectionWidget,
  NuovaFaseAdesioneAccordoQuadroSectionWidget,

  ModificaFaseStipulaAccordoQuadroSectionWidget,
  DettaglioFaseStipulaAccordoQuadroSectionWidget,
  NuovaFaseStipulaAccordoQuadroSectionWidget,

  ModificaFaseConclusioneSectionWidget,
  DettaglioFaseConclusioneSectionWidget,
  NuovaFaseConclusioneSectionWidget,

  ModificaFaseConclusioneAffidamentiDirettiSectionWidget,
  DettaglioFaseConclusioneAffidamentiDirettiSectionWidget,
  NuovaFaseConclusioneAffidamentiDirettiSectionWidget,

  ModificaFaseInizialeSempSectionWidget,
  DettaglioFaseInizialeSempSectionWidget,
  NuovaFaseInizialeSempSectionWidget,

  ModificaMisureAggiuntiveSicurezzaSectionWidget,
  DettaglioMisureAggiuntiveSicurezzaSectionWidget,
  NuoveMisureAggiuntiveSicurezzaSectionWidget,

  ModificaFaseAccordoBonarioSectionWidget,
  DettaglioFaseAccordoBonarioSectionWidget,
  NuovaFaseAccordoBonarioSectionWidget,

  ModificaFaseConclusioneSempSectionWidget,
  DettaglioFaseConclusioneSempSectionWidget,
  NuovaFaseConclusioneSempSectionWidget,

  ModificaFaseCollaudoSectionWidget,
  DettaglioFaseCollaudoSectionWidget,
  NuovaFaseCollaudoSectionWidget,

  ModificaFaseSubappaltoSectionWidget,
  DettaglioFaseSubappaltoSectionWidget,
  NuovaFaseSubappaltoSectionWidget,
  
  ModificaFaseRichiestaSubappaltoSectionWidget,
  DettaglioFaseRichiestaSubappaltoSectionWidget,
  NuovaFaseRichiestaSubappaltoSectionWidget,

  ModificaFaseEsitoSubappaltoSectionWidget,
  DettaglioFaseEsitoSubappaltoSectionWidget,
  NuovaFaseEsitoSubappaltoSectionWidget,

  ModificaFaseConclusioneSubappaltoSectionWidget,
  DettaglioFaseConclusioneSubappaltoSectionWidget,
  NuovaFaseConclusioneSubappaltoSectionWidget,

  ModificaFaseRecessoSectionWidget,
  DettaglioFaseRecessoSectionWidget,
  NuovaFaseRecessoSectionWidget,

  ModificaFaseInidoneitaTecnicoProfSectionWidget,
  DettaglioFaseInidoneitaTecnicoProfSectionWidget,
  NuovaFaseInidoneitaTecnicoProfSectionWidget,

  ModificaFaseInidoneitaContributivaSectionWidget,
  DettaglioFaseInidoneitaContributivaSectionWidget,
  NuovaFaseInidoneitaContributivaSectionWidget,

  ModificaFaseInadempienzeSicurezzaSectionWidget,
  DettaglioFaseInadempienzeSicurezzaSectionWidget,
  NuovaFaseInadempienzeSicurezzaSectionWidget,

  ModificaFaseInfortuniSectionWidget,
  DettaglioFaseInfortuniSectionWidget,
  NuovaFaseInfortuniSectionWidget,

  ModificaFaseCantieriSectionWidget,
  DettaglioFaseCantieriSectionWidget,
  NuovaFaseCantieriSectionWidget,

  PubblicaFaseLottoSectionWidget,
  ListaInviiFasiSectionWidget,

  ListaAttiLottoSectionWidget,

  NuovoSmartCigSectionWidget,
  DettaglioSmartCigSectionWidget,
  ModificaSmartCigSectionWidget,
  GestioneSmartCigSectionWidget,
  ImportaSmartCigSectionWidget,

  GraficaSectionWidget,
  GraficaListaSectionWidget,
  GraficaTabellaSectionWidget,
  GraficaFormGroupSectionWidget,
  GraficaDocumentPanelSectionWidget,
  GraficaMessagePanelSectionWidget,

  ListaTecniciModalWidget,
  ListaCollaborazioniModalWidget,
  AccorpamentoLottiSectionWidget,
  RiepilogoAccorpamentoLottiSectionWidget,
  OauthLoginSectionWidget,
  CredenzialiSimogModalWidget,

  ListaDelegheSectionWidget,
  DettaglioDelegaSectionWidget,
  ModificaDelegaSectionWidget,
  NuovoDelegaSectionWidget,

  DettaglioFaseIncarichiProfessionaliSectionWidget,
  ModificaFaseIncarichiProfessionaliSectionWidget,
  NuovaFaseIncarichiProfessionaliSectionWidget,

  DettaglioFaseVariazioneAggiudicatariSectionWidget,
  ModificaFaseVariazioneAggiudicatariSectionWidget,
  NuovaFaseVariazioneAggiudicatariSectionWidget,

  SpidLoginSectionWidget,
  ImpersonificaSectionWidget,

  DatiContabilitaModalWidget,
  RicercaSchedeTrasmessePcpSectionWidget,
  ListaSchedeTrasmessePcpSectionWidget,

  ListaAttiGeneraliSectionWidget,
  RicercaAvanzataAttiGeneraliSectionWidget,
  NuovoAttoGeneraleSectionWidget,
  DettaglioAttoGeneraleSectionWidget,
  ModificaDettaglioAttoGeneraleSectionWidget
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
    SdkFormBuilderModule,
    SdkButtonModule,
    SdkDateModule,
    SdkTabsModule,
    SdkDialogModule,
    SdkModalModule,
    SdkSidebarModule,
    SdkLoaderModule,
    SdkFormModule,
    SdkSearchModule,
    SdkMenuModule,
    SdkPanelbarModule,
    SdkTableModule.forRoot(),
    AppCommonsModule.forRoot(),
    SdkFormatModule,
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
    SdkGestioneReportsModule.forRoot(),    
    SdkDocAssociatiModule.forRoot(),
    SdkAppaltiecontrattiBaseModule.forRoot(),
    TableModule,
    InputTextModule,
    OAuthModule.forRoot(),
    SdkClickModule,
    TabMenuModule
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
export class AppContrattiModule {

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
