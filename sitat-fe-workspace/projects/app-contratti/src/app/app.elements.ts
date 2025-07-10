import { Type } from '@angular/core';
import {
    AppInfoModalModalWidget,
    CheckPubblicazioneSectionWidget,
    ConfigurazioneUtenteSectionWidget,
    CpvModalWidget,
    DettaglioCdcSectionWidget,
    DettaglioImpresaSectionWidget,
    DettaglioPubblicazioneModalWidget,
    DettaglioStazioneAppaltanteSectionWidget,
    DettaglioTecnicoSectionWidget,
    DettaglioUfficioSectionWidget,
    EsempioModalWidget,
    ListaArchivioCdcSectionWidget,
    ListaArchivioImpreseSectionWidget,
    ListaArchivioStazioniAppaltantiSectionWidget,
    ListaArchivioTecniciSectionWidget,
    ListaArchivioUfficiSectionWidget,
    MessaggiAvvisiModalWidget,
    MessaggiAvvisiOverlayWidget,
    MnemonicoModalWidget,
    ModificaCdcSectionWidget,
    ModificaConfigurazioneUtenteSectionWidget,
    ModificaImpresaSectionWidget,
    ModificaStazioneAppaltanteSectionWidget,
    ModificaTecnicoSectionWidget,
    ModificaUfficioSectionWidget,
    ModificaUtentiStazioneAppaltanteComponent,
    NuovaImpresaSectionWidget,
    NuovaStazioneAppaltanteSectionWidget,
    NuovoCdcSectionWidget,
    NuovoTecnicoSectionWidget,
    NuovoUfficioSectionWidget,
    NutsModalWidget,
    OauthLoginSectionWidget,
    //PubblicaTuttoModalConfig,
    RedirectSectionWidget,
    RicercaAvanzataArchivioCdcSectionWidget,
    RicercaAvanzataArchivioImpreseSectionWidget,
    RicercaAvanzataArchivioStazioniAppaltantiSectionWidget,
    RicercaAvanzataArchivioTecniciSectionWidget,
    RicercaAvanzataArchivioUfficiSectionWidget,
    RupModalWidget,
    RupRWModalWidget,
    SpidLoginSectionWidget,
    StazioneAppaltanteModalWidget,
    TrasferimentoModalWidget,
    UffModalWidget,
    UtentiStazioneAppaltanteComponent,
} from '@maggioli/app-commons';
import { IDictionary, SdkAbstractWidget } from '@maggioli/sdk-commons';
import {
    SdkCambiaPasswordAdminUtenteComponent,
    SdkChangePasswordFormComponent,
    SdkChangePasswordUtenteFormComponent,
    SdkCheckNuovoUtenteComponent,
    SdkDettaglioCodificaAutomaticaComponent,
    SdkDettaglioConfigurazioneComponent,
    SdkDettaglioEventoComponent,
    SdkDettaglioPianificazioneComponent,
    SdkDettaglioServerPostaComponent,
    SdkDettaglioTabellatoComponent,
    SdkDettaglioUtenteComponent,
    SdkEseguiRecuperoPasswordComponent,
    SdkEseguiRecuperoPasswordSuccessComponent,
    SdkImpostaPasswordPostaComponent,
    SdkInviaTestEmailComponent,
    SdkListaCodificaAutomaticaComponent,
    SdkListaConfigurazioniComponent,
    SdkListaDettaglioTabellatoComponent,
    SdkListaEventiComponent,
    SdkListaMnemoniciComponent,
    SdkListaPianificazioniComponent,
    SdkListaServerPostaComponent,
    SdkListaTabellatiComponent,
    SdkListaUtentiComponent,
    SdkLoginFormComponent,
    SdkMioAccountComponent,
    SdkModificaCodificaAutomaticaComponent,
    SdkModificaConfigurazioneComponent,
    SdkModificaPianificazioniComponent,
    SdkModificaProfiliUtenteComponent,
    SdkModificaServerPostaComponent,
    SdkModificaStazioniAppaltantiUtenteComponent,
    SdkModificaTabellatoComponent,
    SdkModificaUtenteComponent,
    SdkNuovoServerPostaComponent,
    SdkNuovoTabellatoComponent,
    SdkNuovoUtenteComponent,
    SdkProfiliUtenteComponent,
    SdkRecuperoPasswordComponent,
    SdkRecuperoPasswordInviataComponent,
    SdkRicercaConfigurazioniComponent,
    SdkRicercaEventiComponent,
    SdkRicercaTabellatiComponent,
    SdkRicercaUtentiComponent,
    SdkRichiestaAssistenzaCompletataComponent,
    SdkRichiestaAssistenzaComponent,
    SdkStazioniAppaltantiUtenteComponent,
} from '@maggioli/sdk-gestione-utenti';
import {
    SdkLayoutBreadcrumbsWidget,
    SdkLayoutContentWidget,
    SdkLayoutFooterWidget,
    SdkLayoutHeaderBottomWidget,
    SdkLayoutHeaderMenuWidget,
    SdkLayoutHeaderMidWidget,
    SdkLayoutHeaderTopBandWidget,
    SdkLayoutHeaderTopWidget,
    SdkLayoutSectionWidget,
    SdkLayoutTitleWidget,
    SdkUltimiAccessiComponent,
} from '@maggioli/sdk-widgets';

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
import { ReportIndicatoriModalWidget } from 'projects/app-commons/src/lib/modules/app-commons/components/report-indicatori-modal/report-indicatori-modal.widget';
import { NuovoDelegaSectionWidget } from './modules/layout/components/business/deleghe/nuovo/nuovo-delega-section.widget';
import { ModificaDelegaSectionWidget } from './modules/layout/components/business/deleghe/modifica/modifica-delega-section.widget';
import { DettaglioDelegaSectionWidget } from './modules/layout/components/business/deleghe/dettaglio/dettaglio-delega-section.widget';
import { ListaDelegheSectionWidget } from './modules/layout/components/business/deleghe/lista-deleghe/lista-deleghe-section.widget';
import { CredenzialiSimogModalWidget } from './modules/layout/components/business/credenziali-simog/credenziali-simog-modal/credenziali-simog-modal.widget';
import { MatriceAttiModalWidget } from './modules/layout/components/business/atti/matrice/matrice-atti-modal-section.widget';
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
import { SDKFiltroEnteModalWidget, SdkDettaglioModelloComponent, SdkDettaglioParametroComponent, SdkListaModelliComponent, SdkListaModelliPredispostiComponent, SdkListaParametriComponent, SdkModificaModelloComponent, SdkModificaParametroComponent, SdkNuovoModelloComponent, SdkNuovoParametroComponent, SdkRicercaModelliComponent } from '@maggioli/sdk-gestione-modelli';
import { SdkComponiParametriModalModalWidget } from 'projects/sdk-gestione-modelli/src/lib/sdk-gestione-modelli/components/sdk-componi-parametri-modal/sdk-componi-parametri-modal.widget';
import { DettaglioFaseIncarichiProfessionaliSectionWidget } from './modules/layout/components/business/fasi/fase-incarichi-professionali/dettaglio/dettaglio-fase-inc-prof-section.widget';
import { ModificaFaseIncarichiProfessionaliSectionWidget } from './modules/layout/components/business/fasi/fase-incarichi-professionali/modifica/modifica-fase-inc-prof-section.widget';
import { NuovaFaseIncarichiProfessionaliSectionWidget } from './modules/layout/components/business/fasi/fase-incarichi-professionali/nuovo/nuovo-fase-inc-prof-section.widget';
import { AssegnaCambiaDelegatoModalWidget } from 'projects/app-commons/src/lib/modules/app-commons/components/assegna-cambia-delegato-modal/assegna-cambia-delegato-modal.widget';
import { ImpersonificaSectionWidget } from './modules/layout/components/business/impersonifica-rup/impersonifica-rup-section.widget';
import { RiallineaAnacModalWidget } from 'projects/app-commons/src/lib/modules/app-commons/components/riallinea-anac-modal-widget/riallinea-anac-modal.widget';
import { DatiContabilitaModalWidget } from './modules/layout/components/business/dati-contabilita-modal/dati-contabilita-modal.widget';
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

import {
    SdkDatiGeneraliReportComponent,
    SdkDefinizioneReportComponent,
    SdkParametriReportComponent, 
    SdkListaReportComponent,
    SdkModificaDatiGeneraliReportComponent,
    SdkModificaDefinizioneReportComponent,
    SdkNuovoParametroReportComponent,
    SdkDettaglioParametroReportComponent,
    SdkModificaDettaglioParametroComponent,
    SdkProfiliReportComponent,
    SdkInserisciParametriReportComponent,
    SdkModificaProfiliReportComponent,
    SdkCreaNuovoReportComponent,
    SdkUfficiIntestatariReportComponent,
    SdkModificaUfficiIntestatariReportComponent,
    SdkRisultatoEsecuzioneReportComponent,
    SdkListaReportPredefinitiComponent,
    SdkRisultatoEsecuzioneReportPredefinitiComponent
} from '@maggioli/sdk-gestione-reports';
import { SdkC0oggassCreateSectionWidget, SdkC0oggassDetailsSectionWidget, SdkC0oggassEditSectionWidget, SdkC0oggassListSectionWidget } from '@maggioli/sdk-docassociati';
import { SdkSignModalWidget } from 'projects/sdk-docassociati/src/lib/sdk-docassociati/components/c0oggass/c0oggass-details/sign-modal/sdk-sign-modal.widget';

export function elementsMap(): IDictionary<Type<SdkAbstractWidget<any>> | any> {
    return {
        'sdk-layout-header-top': SdkLayoutHeaderTopWidget,
        'sdk-layout-header-top-band': SdkLayoutHeaderTopBandWidget,
        'sdk-layout-header-mid': SdkLayoutHeaderMidWidget,
        'sdk-layout-header-bottom': SdkLayoutHeaderBottomWidget,
        'sdk-layout-header-menu': SdkLayoutHeaderMenuWidget,

        'sdk-layout-breadcrumbs': SdkLayoutBreadcrumbsWidget,
        'sdk-layout-section': SdkLayoutSectionWidget,

        'sdk-layout-content': SdkLayoutContentWidget,

        'sdk-layout-footer': SdkLayoutFooterWidget,

        'sdk-layout-title': SdkLayoutTitleWidget,

        'layout-menu-tabs': LayoutMenuTabsWidget,

        'sdk-ricerca-modelli-section': SdkRicercaModelliComponent,
        'layout-side-menu-tabs': LayoutSideMenuTabsWidget,

        'layout-menu-tabs-old': LayoutMenuTabsOldWidget,

        'index-section': IndexSectionWidget,
        'choose-ente-section': ChooseEnteSectionWidget,
        'nuovo-avviso-section': NuovoAvvisoSectionWidget,
        'lista-avvisi-section': ListaAvvisiSectionWidget,
        'choose-profile-section': ChooseProfileSectionWidget,

        'dettaglio-avviso-section': DettaglioAvvisoSectionWidget,
        'pubblica-avviso-section': PubblicaAvvisoSectionWidget,

        'ricerca-avanzata-avvisi-section': RicercaAvanzataAvvisiSectionWidget,
        'modifica-avviso-section': ModificaAvvisoSectionWidget,
        'lista-archivio-tecnici-section': ListaArchivioTecniciSectionWidget,
        'nuovo-tecnico-section': NuovoTecnicoSectionWidget,
        'dettaglio-tecnico-section': DettaglioTecnicoSectionWidget,
        'modifica-tecnico-section': ModificaTecnicoSectionWidget,
        'ricerca-avanzata-archivio-tecnici-section': RicercaAvanzataArchivioTecniciSectionWidget,
        'ricerca-avanzata-gare-section': RicercaAvanzataGareSectionWidget,
        'lista-gare-section': ListaGareSectionWidget,
        'dettaglio-gara-section': DettaglioGaraSectionWidget,
        'ricerca-avanzata-archivio-imprese-section': RicercaAvanzataArchivioImpreseSectionWidget,
        'lista-archivio-imprese-section': ListaArchivioImpreseSectionWidget,
        'dettaglio-impresa-section': DettaglioImpresaSectionWidget,
        'nuova-impresa-section': NuovaImpresaSectionWidget,
        'modifica-impresa-section': ModificaImpresaSectionWidget,
        'ricerca-avanzata-archivio-cdc-section': RicercaAvanzataArchivioCdcSectionWidget,
        'lista-archivio-cdc-section': ListaArchivioCdcSectionWidget,
        'nuovo-cdc-section': NuovoCdcSectionWidget,
        'dettaglio-cdc-section': DettaglioCdcSectionWidget,
        'modifica-cdc-section': ModificaCdcSectionWidget,
        'modifica-gara-section': ModificaGaraSectionWidget,
        'lista-lotti-section': ListaLottiSectionWidget,
        'lista-atti-section': ListaAttiSectionWidget,
        'nuovo-atto-section': NuovoAttoSectionWidget,
        'dettaglio-atto-section': DettaglioAttoSectionWidget,
        'modifica-atto-section': ModificaAttoSectionWidget,
        'dettaglio-lotto-section': DettaglioLottoSectionWidget,
        'lotti-atto-section': LottiAttoSectionWidget,
        'modifica-lotto-section': ModificaLottoSectionWidget,
        'importa-gara-simog-section': ImportaGaraSimogSectionWidget,
        'import-imprese-aggiudicatarie-section': ImportImpreseAggiudicatarieSectionWidget,
        'nuova-impresa-aggiudicataria-section': NuovaImpresaAggiudicatariaSectionWidget,
        'pubblica-gara-section': PubblicaGaraSectionWidget,
        'pubblica-atto-section': PubblicaAttoSectionWidget,

        'lista-fasi-lotto-section': ListaFasiLottoSectionWidget,
        'nuova-fase-section': NuovaFaseSectionWidget,

        'nuova-fase-iniz-sottoscrizione-contratto-section': NuovaFaseInizSottoscrizioneContrattoSectionWidget,
        'dettaglio-fase-iniz-sottoscrizione-contratto-section': DettaglioFaseInizSottoscrizioneContrattoSectionWidget,
        'modifica-fase-iniz-sottoscrizione-contratto-section': ModificaFaseInizSottoscrizioneContrattoSectionWidget,
        
        'nuova-fase-iniziale-section': NuovaFaseInizialeSectionWidget,
        'dettaglio-fase-iniziale-section': DettaglioFaseInizialeSectionWidget,
        'modifica-fase-iniziale-section': ModificaFaseInizialeSectionWidget,        
        'dettaglio-scheda-sicurezza-section': DettaglioSchedaSicurezzaSectionWidget,
        'modifica-scheda-sicurezza-section': ModificaSchedaSicurezzaSectionWidget,
        'nuova-scheda-sicurezza-section': NuovaSchedaSicurezzaSectionWidget,

        'nuova-fase-aggiudicazione-section': NuovaFaseAggiudicazioneSectionWidget,
        'dettaglio-fase-aggiudicazione-section': DettaglioFaseAggiudicazioneSectionWidget,
        'modifica-fase-aggiudicazione-section': ModificaFaseAggiudicazioneSectionWidget,
        'lista-imprese-aggiudicatarie-section': ListaImpreseAggiudicatarieSectionWidget,
        'dettaglio-impresa-aggiudicataria-section': DettaglioImpresaAggiudicatariaSectionWidget,
        'modifica-impresa-aggiudicataria-section': ModificaImpresaAggiudicatariaSectionWidget,
        'dettaglio-incarichi-professionali-section': DettaglioIncarichiProfessionaliSectionWidget,
        'modifica-incarichi-professionali-section': ModificaIncarichiProfessionaliSectionWidget,
        'dettaglio-finanziamenti-section': DettaglioFinanziamentiSectionWidget,
        'modifica-finanziamenti-section': ModificaFinanziamentiSectionWidget,

        'dettaglio-fase-incarichi-professionali-section': DettaglioFaseIncarichiProfessionaliSectionWidget,
        'modifica-fase-incarichi-professionali-section': ModificaFaseIncarichiProfessionaliSectionWidget,
        'nuova-fase-incarichi-professionali-section': NuovaFaseIncarichiProfessionaliSectionWidget,

        'dettaglio-fase-variazione-aggiudicatari-section': DettaglioFaseVariazioneAggiudicatariSectionWidget,
        'modifica-fase-variazione-aggiudicatari-section': ModificaFaseVariazioneAggiudicatariSectionWidget,
        'nuova-fase-variazione-aggiudicatari-section': NuovaFaseVariazioneAggiudicatariSectionWidget,
        
        'nuova-comunicazione-esito-section': NuovaComunicazioneEsitoSectionWidget,
        'dettaglio-comunicazione-esito-section': DettaglioFaseComunicazioneEsitoSectionWidget,
        'modifica-comunicazione-esito-section': ModificaComunicazioneEsitoSectionWidget,

        'nuova-fase-aggiudicazione-semp-section': NuovaFaseAggiudicazioneSempSectionWidget,
        'dettaglio-fase-aggiudicazione-semp-section': DettaglioFaseAggiudicazioneSempSectionWidget,
        'modifica-fase-aggiudicazione-semp-section': ModificaFaseAggiudicazioneSempSectionWidget,

        'lista-elenco-impr-inv-parte-section': ListaImpreseInvitatePartecipantiSectionWidget,
        'nuova-impr-inv-parte-section': NuovaImpresaInvitataPartecipanteSectionWidget,
        'dettaglio-impr-inv-parte-section': DettaglioImpresaInvitataPartecipanteSectionWidget,
        'modifica-impr-inv-parte-section': ModificaImpresaInvitataPartecipanteSectionWidget,

        'nuova-fase-avanzamento-section': NuovaFaseAvanzamentoSectionWidget,
        'dettaglio-fase-avanzamento-section': DettaglioFaseAvanzamentoSectionWidget,
        'modifica-fase-avanzamento-section': ModificaFaseAvanzamentoSectionWidget,

        'nuova-fase-sospensione-section': NuovaFaseSospensioneSectionWidget,
        'dettaglio-fase-sospensione-section': DettaglioFaseSospensioneSectionWidget,
        'modifica-fase-sospensione-section': ModificaFaseSospensioneSectionWidget,

        'nuova-fase-ripresa-prestazione-section': NuovaFaseRipresaPrestazioneSectionWidget,
        'dettaglio-fase-ripresa-prestazione-section': DettaglioFaseRipresaPrestazioneSectionWidget,
        'modifica-fase-ripresa-prestazione-section': ModificaFaseRipresaPrestazioneSectionWidget,

        'nuova-fase-superamento-quarto-contrattuale-section': NuovaFaseSuperamentoQuartoContrattualeSectionWidget,
        'dettaglio-fase-superamento-quarto-contrattuale-section': DettaglioFaseSuperamentoQuartoContrattualeSectionWidget,
        'modifica-fase-superamento-quarto-contrattuale-section': ModificaFaseSuperamentoQuartoContrattualeSectionWidget,

        'nuova-fase-avanzamento-semp-section': NuovaFaseAvanzamentoSempSectionWidget,
        'dettaglio-fase-avanzamento-semp-section': DettaglioFaseAvanzamentoSempSectionWidget,
        'modifica-fase-avanzamento-semp-section': ModificaFaseAvanzamentoSempSectionWidget,

        'nuova-fase-modifica-contrattuale-section': NuovaFaseModificaContrattualeSectionWidget,
        'dettaglio-fase-modifica-contrattuale-section': DettaglioFaseModificaContrattualeSectionWidget,
        'modifica-fase-modifica-contrattuale-section': ModificaFaseModificaContrattualeSectionWidget,

        'nuova-fase-adesione-accordo-quadro-section': NuovaFaseAdesioneAccordoQuadroSectionWidget,
        'dettaglio-fase-adesione-accordo-quadro-section': DettaglioFaseAdesioneAccordoQuadroSectionWidget,
        'modifica-fase-adesione-accordo-quadro-section': ModificaFaseAdesioneAccordoQuadroSectionWidget,

        'modifica-fase-stipula-accordo-quadro-section': ModificaFaseStipulaAccordoQuadroSectionWidget,
        'dettaglio-fase-stipula-accordo-quadro-section': DettaglioFaseStipulaAccordoQuadroSectionWidget,
        'nuova-fase-stipula-accordo-quadro-section': NuovaFaseStipulaAccordoQuadroSectionWidget,

        'modifica-fase-conclusione-section': ModificaFaseConclusioneSectionWidget,
        'dettaglio-fase-conclusione-section': DettaglioFaseConclusioneSectionWidget,
        'nuova-fase-conclusione-section': NuovaFaseConclusioneSectionWidget,

        'modifica-fase-conclusione-affidamenti-diretti-section': ModificaFaseConclusioneAffidamentiDirettiSectionWidget,
        'dettaglio-fase-conclusione-affidamenti-diretti-section': DettaglioFaseConclusioneAffidamentiDirettiSectionWidget,
        'nuova-fase-conclusione-affidamenti-diretti-section': NuovaFaseConclusioneAffidamentiDirettiSectionWidget,

        'modifica-fase-iniziale-semp-section': ModificaFaseInizialeSempSectionWidget,
        'dettaglio-fase-iniziale-semp-section': DettaglioFaseInizialeSempSectionWidget,
        'nuova-fase-iniziale-semp-section': NuovaFaseInizialeSempSectionWidget,

        'modifica-misure-aggiuntive-sicurezza-section': ModificaMisureAggiuntiveSicurezzaSectionWidget,
        'dettaglio-misure-aggiuntive-sicurezza-section': DettaglioMisureAggiuntiveSicurezzaSectionWidget,
        'nuove-misure-aggiuntive-sicurezza-section': NuoveMisureAggiuntiveSicurezzaSectionWidget,

        'modifica-fase-accordo-bonario-section': ModificaFaseAccordoBonarioSectionWidget,
        'dettaglio-fase-accordo-bonario-section': DettaglioFaseAccordoBonarioSectionWidget,
        'nuova-fase-accordo-bonario-section': NuovaFaseAccordoBonarioSectionWidget,

        'modifica-fase-conclusione-semp-section': ModificaFaseConclusioneSempSectionWidget,
        'dettaglio-fase-conclusione-semp-section': DettaglioFaseConclusioneSempSectionWidget,
        'nuova-fase-conclusione-semp-section': NuovaFaseConclusioneSempSectionWidget,

        'modifica-fase-collaudo-section': ModificaFaseCollaudoSectionWidget,
        'dettaglio-fase-collaudo-section': DettaglioFaseCollaudoSectionWidget,
        'nuova-fase-collaudo-section': NuovaFaseCollaudoSectionWidget,

        'modifica-fase-subappalto-section': ModificaFaseSubappaltoSectionWidget,
        'dettaglio-fase-subappalto-section': DettaglioFaseSubappaltoSectionWidget,
        'nuova-fase-subappalto-section': NuovaFaseSubappaltoSectionWidget,

        'modifica-fase-richiesta-subappalto-section': ModificaFaseRichiestaSubappaltoSectionWidget,
        'dettaglio-fase-richiesta-subappalto-section': DettaglioFaseRichiestaSubappaltoSectionWidget,
        'nuova-fase-richiesta-subappalto-section': NuovaFaseRichiestaSubappaltoSectionWidget,

        'modifica-fase-esito-subappalto-section': ModificaFaseEsitoSubappaltoSectionWidget,
        'dettaglio-fase-esito-subappalto-section': DettaglioFaseEsitoSubappaltoSectionWidget,
        'nuova-fase-esito-subappalto-section': NuovaFaseEsitoSubappaltoSectionWidget,

        'modifica-fase-conclusione-subappalto-section': ModificaFaseConclusioneSubappaltoSectionWidget,
        'dettaglio-fase-conclusione-subappalto-section': DettaglioFaseConclusioneSubappaltoSectionWidget,
        'nuova-fase-conclusione-subappalto-section': NuovaFaseConclusioneSubappaltoSectionWidget,

        'modifica-fase-recesso-section': ModificaFaseRecessoSectionWidget,
        'dettaglio-fase-recesso-section': DettaglioFaseRecessoSectionWidget,
        'nuova-fase-recesso-section': NuovaFaseRecessoSectionWidget,

        'modifica-fase-inidoneita-tecnico-prof-section': ModificaFaseInidoneitaTecnicoProfSectionWidget,
        'dettaglio-fase-inidoneita-tecnico-prof-section': DettaglioFaseInidoneitaTecnicoProfSectionWidget,
        'nuova-fase-inidoneita-tecnico-prof-section': NuovaFaseInidoneitaTecnicoProfSectionWidget,

        'modifica-fase-inidoneita-contributiva-section': ModificaFaseInidoneitaContributivaSectionWidget,
        'dettaglio-fase-inidoneita-contributiva-section': DettaglioFaseInidoneitaContributivaSectionWidget,
        'nuova-fase-inidoneita-contributiva-section': NuovaFaseInidoneitaContributivaSectionWidget,

        'modifica-fase-inadempienze-sicurezza-section': ModificaFaseInadempienzeSicurezzaSectionWidget,
        'dettaglio-fase-inadempienze-sicurezza-section': DettaglioFaseInadempienzeSicurezzaSectionWidget,
        'nuova-fase-inadempienze-sicurezza-section': NuovaFaseInadempienzeSicurezzaSectionWidget,

        'modifica-fase-infortuni-section': ModificaFaseInfortuniSectionWidget,
        'dettaglio-fase-infortuni-section': DettaglioFaseInfortuniSectionWidget,
        'nuova-fase-infortuni-section': NuovaFaseInfortuniSectionWidget,

        'modifica-fase-cantieri-section': ModificaFaseCantieriSectionWidget,
        'dettaglio-fase-cantieri-section': DettaglioFaseCantieriSectionWidget,
        'nuova-fase-cantieri-section': NuovaFaseCantieriSectionWidget,

        'pubblica-fase-lotto-section': PubblicaFaseLottoSectionWidget,

        'nuova-gara-section': NuovaGaraSectionWidget,
        'nuovo-lotto-section': NuovoLottoSectionWidget,
        'lista-invii-fasi-section': ListaInviiFasiSectionWidget,

        'lista-atti-lotto-section': ListaAttiLottoSectionWidget,

        'nuovo-smartcig-section': NuovoSmartCigSectionWidget,
        'dettaglio-smartcig-section': DettaglioSmartCigSectionWidget,
        'modifica-smartcig-section': ModificaSmartCigSectionWidget,
        'gestione-smartcig-section': GestioneSmartCigSectionWidget,
        'importa-smartcig-section': ImportaSmartCigSectionWidget,

        'ricerca-avanzata-archivio-stazioni-appaltanti-section': RicercaAvanzataArchivioStazioniAppaltantiSectionWidget,
        'lista-archivio-stazioni-appaltanti-section': ListaArchivioStazioniAppaltantiSectionWidget,
        'nuova-stazione-appaltante-section': NuovaStazioneAppaltanteSectionWidget,
        'dettaglio-stazione-appaltante-section': DettaglioStazioneAppaltanteSectionWidget,
        'modifica-stazione-appaltante-section': ModificaStazioneAppaltanteSectionWidget,
        'utenti-stazione-appaltante-section': UtentiStazioneAppaltanteComponent,
        'modifica-utenti-stazione-appaltante-section': ModificaUtentiStazioneAppaltanteComponent,

        'grafica-section': GraficaSectionWidget,
        'grafica-lista-section': GraficaListaSectionWidget,
        'grafica-tabella-section': GraficaTabellaSectionWidget,
        'grafica-form-group-section': GraficaFormGroupSectionWidget,
        'grafica-document-panel-section': GraficaDocumentPanelSectionWidget,
        'grafica-message-panel-section': GraficaMessagePanelSectionWidget,

        'accorpamento-lotti-section': AccorpamentoLottiSectionWidget,
        'riepilogo-accorpamento-lotti-section': RiepilogoAccorpamentoLottiSectionWidget,
        'redirect-section': RedirectSectionWidget,
        'configurazione-utente-section-widget': ConfigurazioneUtenteSectionWidget,
        'modifica-configurazione-utente-section-widget': ModificaConfigurazioneUtenteSectionWidget,

        // Archivio uffici
        'ricerca-avanzata-archivio-uffici-section': RicercaAvanzataArchivioUfficiSectionWidget,
        'lista-archivio-uffici-section': ListaArchivioUfficiSectionWidget,
        'nuovo-ufficio-section': NuovoUfficioSectionWidget,
        'dettaglio-ufficio-section': DettaglioUfficioSectionWidget,
        'modifica-ufficio-section': ModificaUfficioSectionWidget,

        // SDK
        'sdk-ricerca-utenti-section': SdkRicercaUtentiComponent,
        'sdk-lista-utenti-section': SdkListaUtentiComponent,
        'sdk-check-nuovo-utente-section': SdkCheckNuovoUtenteComponent,
        'sdk-nuovo-utente-section': SdkNuovoUtenteComponent,
        'sdk-dettaglio-utente-section': SdkDettaglioUtenteComponent,
        'sdk-modifica-utente-section': SdkModificaUtenteComponent,
        'sdk-profili-utente-section': SdkProfiliUtenteComponent,
        'sdk-modifica-profili-utente-section': SdkModificaProfiliUtenteComponent,
        'sdk-stazioni-appaltanti-utente-section': SdkStazioniAppaltantiUtenteComponent,
        'sdk-modifica-stazioni-appaltanti-utente-section': SdkModificaStazioniAppaltantiUtenteComponent,
        'sdk-cambia-password-admin-utente-section': SdkCambiaPasswordAdminUtenteComponent,
        'sdk-richiesta-assistenza-section': SdkRichiestaAssistenzaComponent,
        'sdk-richiesta-assistenza-completata-section': SdkRichiestaAssistenzaCompletataComponent,

        'sdk-ricerca-configurazioni-section': SdkRicercaConfigurazioniComponent,
        'sdk-lista-configurazioni-section': SdkListaConfigurazioniComponent,
        'sdk-dettaglio-configurazione-section': SdkDettaglioConfigurazioneComponent,
        'sdk-modifica-configurazione-section': SdkModificaConfigurazioneComponent,
        'sdk-lista-pianificazioni-section': SdkListaPianificazioniComponent,
        'sdk-dettaglio-pianificazione-section': SdkDettaglioPianificazioneComponent,
        'sdk-modifica-pianificazione-section': SdkModificaPianificazioniComponent,
        'sdk-ricerca-eventi-section': SdkRicercaEventiComponent,
        'sdk-lista-eventi-section': SdkListaEventiComponent,
        'sdk-dettaglio-evento-section': SdkDettaglioEventoComponent,
        'sdk-lista-server-posta-section': SdkListaServerPostaComponent,
        'sdk-dettaglio-server-posta-section': SdkDettaglioServerPostaComponent,
        'sdk-nuovo-server-posta-section': SdkNuovoServerPostaComponent,
        'sdk-modifica-server-posta-section': SdkModificaServerPostaComponent,
        'sdk-imposta-password-posta-section': SdkImpostaPasswordPostaComponent,
        'sdk-invia-test-email-section': SdkInviaTestEmailComponent,
        'sdk-ricerca-tabellati-section': SdkRicercaTabellatiComponent,
        'sdk-lista-tabellati-section': SdkListaTabellatiComponent,
        'sdk-lista-dettaglio-tabellato-section': SdkListaDettaglioTabellatoComponent,
        'sdk-dettaglio-tabellato-section': SdkDettaglioTabellatoComponent,
        'sdk-nuovo-tabellato-section': SdkNuovoTabellatoComponent,
        'sdk-modifica-tabellato-section': SdkModificaTabellatoComponent,
        'sdk-lista-mnemonici-section': SdkListaMnemoniciComponent,
        'sdk-lista-codifica-automatica-section': SdkListaCodificaAutomaticaComponent,
        'sdk-dettaglio-codifica-automatica-section': SdkDettaglioCodificaAutomaticaComponent,
        'sdk-modifica-codifica-automatica-section': SdkModificaCodificaAutomaticaComponent,

        'sdk-login-form': SdkLoginFormComponent,
        'sdk-change-password-form': SdkChangePasswordFormComponent,
        'sdk-recupero-password-section': SdkRecuperoPasswordComponent,
        'sdk-recupero-password-inviata-section': SdkRecuperoPasswordInviataComponent,
        'sdk-esegui-recupero-password-section': SdkEseguiRecuperoPasswordComponent,
        'sdk-esegui-recupero-password-success-section': SdkEseguiRecuperoPasswordSuccessComponent,
        'sdk-ultimi-accessi-section': SdkUltimiAccessiComponent,

        'sdk-mio-utente-section': SdkMioAccountComponent,
        'sdk-change-user-password-section': SdkChangePasswordUtenteFormComponent,

        "lista-deleghe-section": ListaDelegheSectionWidget,
        "dettaglio-delega-section": DettaglioDelegaSectionWidget,
        "modifica-delega-section": ModificaDelegaSectionWidget,
        "nuovo-delega-section": NuovoDelegaSectionWidget,

        "sdk-lista-modelli-section": SdkListaModelliComponent,
        "sdk-dettaglio-modello-section": SdkDettaglioModelloComponent,
        'sdk-nuovo-modello-section': SdkNuovoModelloComponent,
        'sdk-modifica-modello-section': SdkModificaModelloComponent,
        'sdk-nuovo-parametro-section': SdkNuovoParametroComponent,
        'sdk-modifica-parametro-section': SdkModificaParametroComponent,
        'sdk-modelli-predisposti-section': SdkListaModelliPredispostiComponent,
        'sdk-componi-parametri-section':SdkComponiParametriModalModalWidget,

        'sdk-lista-report-section':SdkListaReportComponent,
        'sdk-dati-generali-report-section': SdkDatiGeneraliReportComponent,
        'sdk-definizione-report-section': SdkDefinizioneReportComponent,
        'sdk-parametri-report-section': SdkParametriReportComponent,
        'sdk-nuovo-parametro-report-section': SdkNuovoParametroReportComponent,
        'sdk-modifica-dati-generali-report-page-section': SdkModificaDatiGeneraliReportComponent,
        'sdk-modifica-definizione-report-section': SdkModificaDefinizioneReportComponent,
        'sdk-dettaglio-parametro-page-section': SdkDettaglioParametroReportComponent,
        'sdk-modifica-dettaglio-parametro-page-section': SdkModificaDettaglioParametroComponent,
        'sdk-profili-report-section': SdkProfiliReportComponent,
        'sdk-inserisci-parametri-page-section': SdkInserisciParametriReportComponent,
        'sdk-modifica-profili-report-section': SdkModificaProfiliReportComponent,
        'sdk-crea-nuovo-report-section': SdkCreaNuovoReportComponent,
        'sdk-uffici-intestatari-report-section': SdkUfficiIntestatariReportComponent,
        'sdk-modifica-uffici-intestatari-report-section': SdkModificaUfficiIntestatariReportComponent,
        'sdk-risultato-esecuzione-report-page-section': SdkRisultatoEsecuzioneReportComponent,
        'sdk-lista-report-predefiniti-section': SdkListaReportPredefinitiComponent,
        'sdk-risultato-esecuzione-report-predefiniti-page-section': SdkRisultatoEsecuzioneReportPredefinitiComponent,

        'spid-login-section': SpidLoginSectionWidget,
        'oauth-login-section': OauthLoginSectionWidget,

        'impersonifica-rup-section': ImpersonificaSectionWidget,
        
        //Cruscotto trasmissioni a PCP
        'ricerca-schede-trasmesse-pcp-section': RicercaSchedeTrasmessePcpSectionWidget,
        'lista-schede-trasmesse-pcp-section': ListaSchedeTrasmessePcpSectionWidget,

        'lista-atti-generali-section': ListaAttiGeneraliSectionWidget,
        'ricerca-atti-generali-section': RicercaAvanzataAttiGeneraliSectionWidget,
        'nuovo-atto-generale-section': NuovoAttoGeneraleSectionWidget,
        'dettaglio-atto-generale-section': DettaglioAttoGeneraleSectionWidget,
        'modifica-dettaglio-atto-generale-section': ModificaDettaglioAttoGeneraleSectionWidget,


        //SDK DOC ASSOCIATI

        'sdk-c0oggass-list-section': SdkC0oggassListSectionWidget,
        'sdk-c0oggass-details-section': SdkC0oggassDetailsSectionWidget,
        'sdk-c0oggass-create-section': SdkC0oggassCreateSectionWidget,
        'sdk-c0oggass-edit-section': SdkC0oggassEditSectionWidget,
        'sdk-sign-modal-widget': SdkSignModalWidget
    };

}

export function customElementsMap(): IDictionary<Type<any>> {
    return {
        'rup-modal-widget': RupModalWidget,
        'stazione-appaltante-modal-widget': StazioneAppaltanteModalWidget,
        'check-pubblicazione-widget': CheckPubblicazioneSectionWidget,
        'messaggi-avvisi-modal-widget': MessaggiAvvisiModalWidget,
        'cdc-modal-widget': CentroDiCostoModalWidget,
        'uff-modal-widget': UffModalWidget,
        'cpv-modal-widget': CpvModalWidget,
        'impresa-modal-widget': ImpresaModalWidget,
        'nuts-modal-widget': NutsModalWidget,
        'messaggi-avvisi-overlay-widget': MessaggiAvvisiOverlayWidget,
        'mnemonico-modal-widget': MnemonicoModalWidget,
        'dettaglio-pubblicazione-modal-widget': DettaglioPubblicazioneModalWidget,
        'rup-rw-modal-widget': RupRWModalWidget,
        'trasferimento-modal-widget': TrasferimentoModalWidget,
        'esempio-modal-widget': EsempioModalWidget,
        'lista-tecnici-modal-widget': ListaTecniciModalWidget,
        'lista-collaborazioni-modal-widget': ListaCollaborazioniModalWidget,
        'app-info-modal-widget': AppInfoModalModalWidget,
        //'pubblica-tutto-modal-widget': PubblicaTuttoModalConfig,
        'matrice-atti-modal-widget': MatriceAttiModalWidget,
        'report-indicatori-modal-widget':ReportIndicatoriModalWidget,
        'dati-contabilita-modal-widget':DatiContabilitaModalWidget,
        "credenziali-simog-modal-widget": CredenzialiSimogModalWidget,
        'sdk-filtro-entita-modal':SDKFiltroEnteModalWidget,
        'sdk-lista-parametri-section':SdkListaParametriComponent,
        'sdk-dettaglio-parametro-section': SdkDettaglioParametroComponent,
        'sdk-parametri-compositore-modal-widget':SdkComponiParametriModalModalWidget,
        'assegna-cambia-delegato-modal-widget': AssegnaCambiaDelegatoModalWidget,
        'riallinea-anac-modal-widget': RiallineaAnacModalWidget
    }
}
