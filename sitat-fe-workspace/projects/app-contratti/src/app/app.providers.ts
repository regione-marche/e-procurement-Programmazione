import { Type } from '@angular/core';
import {
    AdvancedSearchArchivioCdcProvider,
    AdvancedSearchArchivioImpreseProvider,
    AdvancedSearchArchivioStazioniAppaltantiProvider,
    AdvancedSearchArchivioTecniciProvider,
    AdvancedSearchArchivioUfficiProvider,
    AdvancedSearchCdcProvider,
    AdvancedSearchImpreseProvider,
    AdvancedSearchTecnicoProvider,
    ApplicazioniVisibleProvider,
    ArchivioStazioniAppaltantiMenuVisibleProvider,
    ArchivioStazioniAppaltantiProvider,
    CdcParamsProvider,
    CdcProvider,
    ChangeUserPasswordProvider,
    ChangeUserPasswordVisibleProvider,
    ImpresaParamsProvider,
    ImpresaProvider,
    ListaArchivioCdcProvider,
    ListaArchivioImpreseProvider,
    ListaTecniciProvider,
    ManualiUrlProvider,
    MessaggiAvvisiProvider,
    MessaggiAvvisiStatusProvider,
    MessaggiAvvisiAdminVisibleProvider,
    MioAccountProvider,
    MioAccountVisibleProvider,
    ProfiliVisibleProvider,
    StazioneAppaltanteParamsProvider,
    StazioneAppaltanteUtentiVisibleProvider,
    StazioniAppaltantiCountMenuVisibleProvider,
    TabellatiComboProvider,
    TecnicoParamsProvider,
    TecnicoProvider,
    UfficioParamsProvider,
    UfficioProvider,
    UltimiAccessiVisibleProvider,
    UserOptionsAutocompleteProvider,
    UtenteStazioneAppaltanteParamsProvider,
    UtenteStazioneAppaltanteProvider,
    UtentiStazioneAppaltanteProvider,
    MessaggiAvvisiVisibleProvider,
} from '@maggioli/app-commons';
import { IDictionary, SdkProvider } from '@maggioli/sdk-commons';
import {
    SdkCambiaPasswordAdminUtenteProvider,
    SdkCheckNuovoUtenteProvider,
    SdkCodificaAutomaticaProvider,
    SdkConfigurazioneProvider,
    SdkDettaglioCodificaAutomaticaParamsProvider,
    SdkDettaglioConfigurazioneParamsProvider,
    SdkDettaglioEventoParamsProvider,
    SdkDettaglioPianificazioneParamsProvider,
    SdkDettaglioServerPostaParamsProvider,
    SdkDettaglioTabellatoParamsProvider,
    SdkDettaglioUtenteParamsProvider,
    SdkGestioneConfigurazioniSearchProvider,
    SdkGestioneEventiSearchProvider,
    SdkGestioneTabellatiSearchProvider,
    SdkGestioneUtentiAmministrazioneMenuVisibleProvider,
    SdkGestioneUtentiGestoreProfiliVisibleProvider,
    SdkGestioneUtentiGestoreStazioniAppaltantiVisibleProvider,
    SdkGestioneUtentiMenuVisibleProvider,
    SdkGestioneUtentiRichiestaAssistenzaMenuVisibleProvider,
    SdkGestioneUtentiSearchProvider,
    SdkGestioneUtentiTabellatiComboProvider,
    SdkImpostaPasswordServerPostaProvider,
    SdkInviaTestEmailPostaProvider,
    SdkListaCodificaAutomaticaProvider,
    SdkListaConfigurazioniProvider,
    SdkListaDettaglioTabellatoParamsProvider,
    SdkListaEventiProvider,
    SdkListaMnemoniciProvider,
    SdkListaPianificazioniProvider,
    SdkListaServerPostaProvider,
    SdkListaTabellatiProvider,
    SdkListaUtentiProvider,
    SdkPasswordRecoveryProvider,
    SdkPianificazioneProvider,
    SdkProfiliUtenteProvider,
    SdkProfiloAutocompleteProvider,
    SdkRichiestaAssistenzaProvider,
    SdkServerPostaProvider,
    SdkStazioniAppaltantiUtenteProvider,
    SdkTabellatoProvider,
    SdkUfficioIntestatarioAutocompleteProvider,
    SdkUtenteConnessoProvider,
    SdkUtenteProvider,
} from '@maggioli/sdk-gestione-utenti';

import { AccorpamentoLottiProvider } from './modules/providers/accorpamento-lotti/accorpamento-lotti.provider';
import { CigMasterAutocompleteProvider } from './modules/providers/accorpamento-lotti/cig-master-autocomplete.provider';
//import { HeaderAdvancedSearchAvvisiProvider } from './modules/providers/advanced-search/advanced-search-avvisi.provider';
import { HeaderAdvancedSearchGareProvider } from './modules/providers/advanced-search/advanced-search-gare.provider';
import { AttoImpreseAggiudicatarieTabsVisibleProvider } from './modules/providers/atti/atto-impr-agg-tabs-visible.provider';
import { AttoLottiTabsVisibleProvider } from './modules/providers/atti/atto-lotti-tabs-visible.provider';
import { AttoLottoProvider } from './modules/providers/atti/atto-lotto.provider';
import { AttoProvider } from './modules/providers/atti/atto.provider';
import { DettaglioAttoParamsProvider } from './modules/providers/atti/dettaglio-atto-params.provider';
import { ListaAttiProvider } from './modules/providers/atti/lista-atti.provider';
import { ListaImpreseAggiudicatarieAttoProvider } from './modules/providers/atti/lista-impr-agg-atto.provider';
import { LottiAttoProvider } from './modules/providers/atti/lotti-atto.provider';
import { PubblicazioneAttoProvider } from './modules/providers/atti/pubblicazione-atto.provider';
import { AdvancedSearchAvvisiProvider } from './modules/providers/avvisi/advanced-search-avvisi.provider';
import { DettaglioAvvisoParamsProvider } from './modules/providers/avvisi/dettaglio-avviso-params.provider';
import { DettaglioAvvisoProvider } from './modules/providers/avvisi/dettaglio-avviso.provider';
import { IdAvvisoProvider } from './modules/providers/avvisi/id-avviso.provider';
import { ListaAvvisiProvider } from './modules/providers/avvisi/lista-avvisi.provider';
import { ModificaAvvisoProvider } from './modules/providers/avvisi/modifica-avviso.provider';
import { NuovoAvvisoProvider } from './modules/providers/avvisi/nuovo-avviso.provider';
import { RupAvvisoProvider } from './modules/providers/avvisi/rup-avviso.provider';
import { TipologiaAvvisoProvider } from './modules/providers/avvisi/tipologia-avviso.provider';
import { TrasferimentoAvvisoProvider } from './modules/providers/avvisi/trasferimento-avviso.provider';
import {
    BreadcrumbDettaglioGaraNumberProvider,
} from './modules/providers/breadcrumb/breadcrumb-dettaglio-gara-number.provider';
import {
    BreadcrumbDettaglioLottoNumberProvider,
} from './modules/providers/breadcrumb/breadcrumb-dettaglio-lotto-number.provider';
import {
    BreadcrumbRicercaAvanzataAvvisiVisibleProvider,
} from './modules/providers/breadcrumb/breadcrumb-ricerca-avanzata-avvisi-visible.provider';
import {
    BreadcrumbRicercaAvanzataGareVisibleProvider,
} from './modules/providers/breadcrumb/breadcrumb-ricerca-avanzata-gare-visible.provider';
import { CambioModuloProvider } from './modules/providers/cambio-modulo.provider';
import { CambioRupProvider } from './modules/providers/cambio-rup/cambio-rup.provider';
import { ChooseEnteProvider } from './modules/providers/choose-ente.provider';
import { ChooseProfileProvider } from './modules/providers/choose-profile.provider';
import { ComuniIstatProvider } from './modules/providers/comuni-istat.provider';
import { ComuniProvider } from './modules/providers/comuni.provider';
import { DettaglioFaseParamsProvider } from './modules/providers/fasi/dettaglio-fase-params.provider';
import { DettaglioImpresaParteParamsProvider } from './modules/providers/fasi/dettaglio-impresa-parte-params.provider';
import { ElencoImpreseInvitatePartecipantiProvider } from './modules/providers/fasi/elenco-impr-inv-parte.provider';
import { FaseAccordoBonarioProvider } from './modules/providers/fasi/fase-accordo-bonario.provider';
import { FaseAdesioneAccordoQuadroProvider } from './modules/providers/fasi/fase-adesione-accordo-quadro.provider';
import { FaseAggiudicazioneSempProvider } from './modules/providers/fasi/fase-aggiudicazione-semp.provider';
import { FaseAggiudicazioneProvider } from './modules/providers/fasi/fase-aggiudicazione.provider';
import { FaseAvanzamentoSempProvider } from './modules/providers/fasi/fase-avanzamento-semp.provider';
import { FaseAvanzamentoProvider } from './modules/providers/fasi/fase-avanzamento.provider';
import { FaseCantieriProvider } from './modules/providers/fasi/fase-cantieri.provider';
import { FaseCollaudoProvider } from './modules/providers/fasi/fase-collaudo.provider';
import { FaseComunicazioneEsitoProvider } from './modules/providers/fasi/fase-comunicazione-esito.provider';
import { FaseConclusioneSempProvider } from './modules/providers/fasi/fase-conclusione-semp.provider';
import { FaseConclusioneProvider } from './modules/providers/fasi/fase-conclusione.provider';
import { FaseInadempienzeSicurezzaProvider } from './modules/providers/fasi/fase-inadempienze-sicurezza.provider';
import { FaseInfortuniProvider } from './modules/providers/fasi/fase-infortuni.provider';
import { FaseInidoneitaContributivaProvider } from './modules/providers/fasi/fase-inidoneita-contributiva.provider';
import { FaseInidoneitaTecnicoProfProvider } from './modules/providers/fasi/fase-inidoneita-tecnico-prof.provider';
import { FaseInizialeSempProvider } from './modules/providers/fasi/fase-iniziale-semp.provider';
import { FaseInizialeProvider } from './modules/providers/fasi/fase-iniziale.provider';
import { FaseModificaContrattualeProvider } from './modules/providers/fasi/fase-modifica-contrattuale.provider';
import { FaseRecessoProvider } from './modules/providers/fasi/fase-recesso.provider';
import { FaseSospensioneProvider } from './modules/providers/fasi/fase-sospensione.provider';
import { FaseStipulaAccordoQuadroProvider } from './modules/providers/fasi/fase-stipula-accordo-quadro.provider';
import { FaseSubappaltoProvider } from './modules/providers/fasi/fase-subappalto.provider';
import { ListaFasiProvider } from './modules/providers/fasi/lista-fasi.provider';
import { ListaInviiFasiVisibleProvider } from './modules/providers/fasi/lista-invii-fasi-visible.provider';
import {
    FinanziamentiFaseAdesioneAccordoProvider,
} from './modules/providers/finanziamenti/finanziamenti-fase-adesione-accordo.provider';
import { FinanziamentiFaseAggiudicazioneProvider } from './modules/providers/finanziamenti/finanziamenti-fase-agg.provider';
import { FinanziamentiTabsVisibleProvider } from './modules/providers/finanziamenti/finanziamenti-tabs-visible.provider';
import { AdvancedSearchGareProvider } from './modules/providers/gare/advanced-search-gare.provider';
import { CambioReferenteProvider } from './modules/providers/gare/cambio-referente.provider';
import { CdcAutocompleteProvider } from './modules/providers/gare/cdc-autocomplete.provider';
import { CdcModalProvider } from './modules/providers/gare/cdc-modal.provider';
import { DettaglioGaraParamsProvider } from './modules/providers/gare/dettaglio-gara-params.provider';
import { GaraStandardTabsVisibleProvider } from './modules/providers/gare/gara-standard-tabs-visible.provider';
import { GaraProvider } from './modules/providers/gare/gara.provider';
import { HeaderGareProvider } from './modules/providers/gare/header-gare.provider';
import { ImportaGaraSimogProvider } from './modules/providers/gare/importa-gara-simog.provider';
import { ListaCollaborazioniModalProvider } from './modules/providers/gare/lista-collaborazioni-modal.provider';
import { ListaGareProvider } from './modules/providers/gare/lista-gare.provider';
import { ListaTecniciModalProvider } from './modules/providers/gare/lista-tecnici-modal.provider';
import { PubblicazioneFaseLottoProvider } from './modules/providers/gare/pubblicazione-fase-lotto.provider';
import { PubblicazioneGaraProvider } from './modules/providers/gare/pubblicazione-gara.provider';
import { UfficiProvider } from './modules/providers/gare/uffici.provider';
import { ImpresaNewModalProvider } from './modules/providers/impresa-new-modal.provider';
import {
    DettaglioImpresaAggiudicatariaParamsProvider,
} from './modules/providers/imprese-aggiudicatarie/dettaglio-impr-agg-params.provider';
import {
    ImpreseAggiudicatarieAggiAutocompleteProvider,
} from './modules/providers/imprese-aggiudicatarie/imprese-aggiudicatarie-aggi-autocomplete.provider';
import {
    ImpreseAggiudicatarieAggiSubaAutocompleteProvider,
} from './modules/providers/imprese-aggiudicatarie/imprese-aggiudicatarie-aggi-suba-autocomplete.provider';
import {
    ImpreseAggiudicatarieAutocompleteProvider,
} from './modules/providers/imprese-aggiudicatarie/imprese-aggiudicatarie-autocomplete.provider';
import {
    ListaImpreseAggiudicatarieFaseAdesioneAccordoProvider,
} from './modules/providers/imprese-aggiudicatarie/lista-impr-agg-fase-adesione-accordo.provider';
import {
    ListaImpreseAggiudicatarieFaseAggiudicazioneSempProvider,
} from './modules/providers/imprese-aggiudicatarie/lista-impr-agg-fase-agg-semp.provider';
import {
    ListaImpreseAggiudicatarieFaseAggiudicazioneProvider,
} from './modules/providers/imprese-aggiudicatarie/lista-impr-agg-fase-agg.provider';
import {
    IncarichiProfessionaliFaseAdesioneAccordoProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-adesione-accordo.provider';
import {
    IncarichiProfessionaliFaseAggiudicazioneSempProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-agg-semp.provider';
import {
    IncarichiProfessionaliFaseAggiudicazioneProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-agg.provider';
import {
    IncarichiProfessionaliFaseCantieriProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-cantieri.provider';
import {
    IncarichiProfessionaliFaseCollaudoProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-collaudo.provider';
import {
    IncarichiProfessionaliFaseInizialeSempProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-ini-semp.provider';
import {
    IncarichiProfessionaliFaseInizialeProvider,
} from './modules/providers/incarichi-professionali/inc-prof-fase-ini.provider';
import { SdkInternalLoginProvider } from './modules/providers/internal-login/sdk-internal-login.provider';
import { LogoutProvider } from './modules/providers/logout.provider';
import { DettaglioLottoParamsProvider } from './modules/providers/lotti/dettaglio-lotto-params.provider';
import { ListaAttiLottoProvider } from './modules/providers/lotti/lista-atti-lotto.provider';
import { ListaImpreseAggiudicatarieAttoLottoProvider } from './modules/providers/lotti/lista-impr-agg-atto-lotto.provider';
import { ListaLottiProvider } from './modules/providers/lotti/lista-lotti.provider';
import { LottoProvider } from './modules/providers/lotti/lotto.provider';
import {
    MisureAggiuntiveSicurezzaFaseInizialeSempProvider,
} from './modules/providers/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza-fase-ini-semp.provider';
import {
    MisureAggiuntiveSicurezzaFaseInizialeProvider,
} from './modules/providers/misure-aggiuntive-sicurezza/misure-aggiuntive-sicurezza-fase-ini.provider';
import { ModalWindowProvider } from './modules/providers/modal-window-provider';
import { UfficiNewModalProvider } from './modules/providers/modal/uffici-new-modal.provider';
import { RupNewModalProvider } from './modules/providers/rup-new-modal.provider';
import {
    SchedaSicurezzaFaseInizialeSempProvider,
} from './modules/providers/scheda-sicurezza/scheda-sicurezza-fase-ini-semp.provider';
import {
    SchedaSicurezzaFaseInizialeProvider,
} from './modules/providers/scheda-sicurezza/scheda-sicurezza-fase-ini.provider';
import { SchedaSicurezzaTabsVisible } from './modules/providers/scheda-sicurezza/scheda-sicurezza-tabs-visible.provider';
import { SmartCigTabsVisibleProvider } from './modules/providers/smartcig/smartcig-tabs-visible.provider';
import { SmartCigProvider } from './modules/providers/smartcig/smartcig.provider';
import { StazioneAppaltanteAutocompleteProvider } from './modules/providers/stazione-appaltante-autocomplete.provider';
import { ListaDelegheProvider } from './modules/providers/deleghe/lista-deleghe.provider';
import { DelegaProvider } from './modules/providers/deleghe/delega.provider';
import { DettaglioDelegaParamsProvider } from './modules/providers/deleghe/dettaglio-delega-params.provider';
import { CredenzialiSimogModalProvider } from './modules/providers/gare/credenziali-simog-modal.provider';
import { ListaDelegheVisibleProvider } from './modules/providers/deleghe/lista-deleghe-visible.provider';
import { FaseInizialeSottoscrContrProvider } from './modules/providers/fasi/fase-iniziale-sottoscrizione-contratto.provider';
import { FaseConclusioneAffidamentiDirettiProvider } from './modules/providers/fasi/fase-conclusione-affidamenti-diretti.provider';
import { FaseRipresaPrestazioneProvider } from './modules/providers/fasi/fase-ripresa-prestazione.provider';
import { FaseSuperamentoQuartoContrattualeProvider } from './modules/providers/fasi/fase-superamento-quarto-contrattuale.provider';
import { FaseRichiestaSubappaltoProvider } from './modules/providers/fasi/fase-richiesta-subappalto.provider';
import { FaseEsitoSubappaltoProvider } from './modules/providers/fasi/fase-esito-subappalto.provider';
import { FaseConclusioneSubappaltoProvider } from './modules/providers/fasi/fase-conclusione-subappalto.provider';
import { SdkCompositoreProvider, SdkDettaglioModelloParamsProvider, SdkGestioneModelliMenuVisibleProvider, SdkGestioneModelliTabellatiComboProvider, SdkListaModelliPredispostiComponent, SdkListaModelliProvider, SdkListaParametriParamsProvider, SdkModelliPredispostiProvider, SdkModelloProvider, SdkParametroProvider, SdkRicercaModelliAutocompleteProvider, SdkRicercaModelliProvider } from '@maggioli/sdk-gestione-modelli';
import { FaseIncarichiProfessionaliProvider } from './modules/providers/fasi/fase-incarichi-professionali.provider';
import { ImpersonificaRupVisibleProvider } from './modules/providers/impersonifica-rup/impersonifica-rup-visible.provider';
import { ImpersonificaRupProvider } from './modules/providers/impersonifica-rup/impersonifica-rup.provider';
import { SdkUltimiAccessiProvider } from '@maggioli/sdk-widgets';
import { UltimiAccessiProvider } from 'projects/app-commons/src/lib/modules/app-commons/providers/ultimi-accessi/ultimi-accessi.provider';

import { 
    SdkDettaglioReportParamsProvider, 
    SdkGestioneReportMenuVisibleProvider, 
    SdkGestioneReportProvider, 
    SdkListaReportProvider,
    SdkGestioneReportsTabellatiProvider,
    SdkGestioneReportsProfiliVisibleProvider,
    SdkGestioneReportsUfficiIntestatariVisibleProvider,
    SdkReportPredefinitiMenuVisibleProvider,
    SdkListaReportPredefinitiProvider,
    BreadcrumbNomeReportProvider
} from '@maggioli/sdk-gestione-reports';

import { SchedeTrasmesseFiltroUffIntProvider } from './modules/providers/schede-trasmesse-pcp/schede-trasmesse-filtro-uffint.provider';

import { AttiGeneraliProvider } from './modules/providers/atti-generali/atti-generali.provider';
import { ModificaAttoGeneraleProvider } from './modules/providers/atti-generali/modifica-atto-generale.provider';


import { FaseVariazioneAggiudicatariProvider } from './modules/providers/fasi/fase-variazione-aggiudicatari.provider';
import { ImpreseVaraggiAutocompleteProvider } from './modules/providers/imprese-aggiudicatarie/imprese-varaggi-autocomplete.provider';
import { ListaCigProvider } from './modules/providers/lista-cig/lista-cig.provider';
import { SdkC0oggassBreadcrumbProvider, SdkC0oggassProvider, SdkDocAssociatiModalWindowProvider, SdkDocAssociatiTabellatiComboProvider } from '@maggioli/sdk-docassociati';


export function providers(): IDictionary<Type<SdkProvider>> {
    return {
        APP_AVVISI_LOGOUT: LogoutProvider,
        APP_AVVISI_CHOOSE_ENTE: ChooseEnteProvider,
        APP_AVVISI_CHOOSE_PROFILE: ChooseProfileProvider,
        APP_AVVISI_ADVANCED_SEARCH: AdvancedSearchAvvisiProvider,
        APP_AVVISO_DETTAGLIO_FASE_PARAMS: DettaglioAvvisoParamsProvider,
        APP_AVVISI_LISTA_AVVISI: ListaAvvisiProvider,
        APP_AVVISI_DETTAGLIO_AVVISO: DettaglioAvvisoProvider,
        APP_AVVISI_TIPOLOGIA_AVVISO: TipologiaAvvisoProvider,
        APP_AVVISI_RUP_AVVISO: RupAvvisoProvider,
        APP_CONTRATTI_LISTA_CIG: ListaCigProvider,
        APP_GARE_CAMBIO_REFERENTE: CambioReferenteProvider,
        APP_AVVISI_NUOVO_AVVISO: NuovoAvvisoProvider,
        APP_AVVISI_MODIFICA_AVVISO: ModificaAvvisoProvider,
        APP_AVVISI_RUP_NEW_MODAL: RupNewModalProvider,
        APP_AVVISI_MODAL_WINDOW: ModalWindowProvider,
        APP_AVVISI_ID_AVVISO: IdAvvisoProvider,
        APP_COMMONS_LISTA_TECNICI: ListaTecniciProvider,
        APP_COMMONS_TECNICO: TecnicoProvider,
        APP_COMMONS_TECNICO_PARAMS: TecnicoParamsProvider,
        APP_AVVISI_CAMBIO_MODULO: CambioModuloProvider,
        APP_GARE_TABELLATI_COMBO: TabellatiComboProvider,
        APP_GARE_UFFICI: UfficiProvider,
        APP_COMMONS_ARCHIVIO_IMPRESE_ADVANCED_SEARCH: AdvancedSearchArchivioImpreseProvider,
        APP_COMMONS_LISTA_IMPRESE: ListaArchivioImpreseProvider,
        APP_COMMONS_IMPRESA_PARAMS: ImpresaParamsProvider,
        APP_GARE_COMUNI: ComuniProvider,
        APP_GARE_COMUNI_ISTAT: ComuniIstatProvider,
        APP_COMMONS_IMPRESA: ImpresaProvider,
        APP_COMMONS_ARCHIVIO_CDC_ADVANCED_SEARCH: AdvancedSearchArchivioCdcProvider,
        APP_COMMONS_LISTA_CDC: ListaArchivioCdcProvider,
        APP_COMMONS_CDC: CdcProvider,
        APP_GARE_CDC_OPTIONS: CdcAutocompleteProvider,
        APP_COMMONS_CDC_PARAMS: CdcParamsProvider,
        APP_GARE_CDC_MODAL: CdcModalProvider,
        APP_COMMONS_ARCHIVIO_TECNICI_ADVANCED_SEARCH: AdvancedSearchArchivioTecniciProvider,
        APP_GARE_ADVANCED_SEARCH: AdvancedSearchGareProvider,
        APP_GARE_LISTA_GARE: ListaGareProvider,
        APP_GARE_HEADER_MID: HeaderGareProvider,
        APP_GARE_DETTAGLIO_GARA_PARAMS: DettaglioGaraParamsProvider,
        APP_GARE_UFFICI_NEW_MODAL: UfficiNewModalProvider,
        APP_GARE_LISTA_LOTTI: ListaLottiProvider,
        APP_GARE_LISTA_ATTI: ListaAttiProvider,
        APP_GARE_LISTA_ATTI_LOTTO: ListaAttiLottoProvider,
        APP_GARE_ATTO: AttoProvider,
        APP_GARE_ATTO_LOTTO: AttoLottoProvider,
        APP_GARE_DETTAGLIO_ATTO_PARAMS: DettaglioAttoParamsProvider,
        APP_GARE_DETTAGLIO_LOTTO_PARAMS: DettaglioLottoParamsProvider,
        APP_GARE_LOTTI_ATTO: LottiAttoProvider,
        APP_GARE_GARA: GaraProvider,
        APP_GARE_LOTTO: LottoProvider,
        APP_GARE_LISTA_FASI: ListaFasiProvider,
        APP_GARE_DETTAGLIO_FASE_PARAMS: DettaglioFaseParamsProvider,
        APP_GARE_DETTAGLIO_IMPRESA_AGGIUDICATARIA_PARAMS: DettaglioImpresaAggiudicatariaParamsProvider,
        APP_GARE_IMPRESE_AGGIUDICATARIE_AUTOCOMPLETE: ImpreseAggiudicatarieAutocompleteProvider,
        APP_GARE_IMPRESE_AGGIUDICATARIE_AGGI_AUTOCOMPLETE: ImpreseAggiudicatarieAggiAutocompleteProvider,
        APP_GARE_IMPRESE_AGGIUDICATARIE_AGGI_SUBA_AUTOCOMPLETE: ImpreseAggiudicatarieAggiSubaAutocompleteProvider,
        APP_GARE_IMPRESE_W9VARAGGI_AUTOCOMPLETE: ImpreseVaraggiAutocompleteProvider,
        APP_GARE_SCHE_SIC_TABS_VISIBLE: SchedaSicurezzaTabsVisible,
        APP_GARE_IMPORTA_GARA: ImportaGaraSimogProvider,

        APP_GARE_FASE_INIZIALE_SOTTOSCR_CONTR_PROVIDER: FaseInizialeSottoscrContrProvider,
        APP_GARE_FASE_INIZIALE_PROVIDER: FaseInizialeProvider,
        APP_GARE_INC_PROF_FASE_INI: IncarichiProfessionaliFaseInizialeProvider,
        APP_GARE_SCHE_SIC_FASE_INI: SchedaSicurezzaFaseInizialeProvider,
        APP_GARE_MIS_AGG_SIC_FASE_INI: MisureAggiuntiveSicurezzaFaseInizialeProvider,

        APP_GARE_INC_PROF_FASE_AGG: IncarichiProfessionaliFaseAggiudicazioneProvider,
        APP_GARE_LISTA_IMPR_AGG_FASE_AGG: ListaImpreseAggiudicatarieFaseAggiudicazioneProvider,
        APP_GARE_FINANZIAMENTI_FASE_AGG: FinanziamentiFaseAggiudicazioneProvider,

        APP_GARE_FASE_AGGIUDICAZIONE_PROVIDER: FaseAggiudicazioneProvider,

        APP_GARE_COMUNICAZIONE_ESITO_PROVIDER: FaseComunicazioneEsitoProvider,

        APP_GARE_FASE_AGGIUDICAZIONE_SEMP_PROVIDER: FaseAggiudicazioneSempProvider,
        APP_GARE_LISTA_IMPR_AGG_FASE_AGG_SEMP: ListaImpreseAggiudicatarieFaseAggiudicazioneSempProvider,
        APP_GARE_INC_PROF_FASE_AGG_SEMP: IncarichiProfessionaliFaseAggiudicazioneSempProvider,

        APP_GARE_FASE_ELENCO_IMPR_INV_PARTE_PROVIDER: ElencoImpreseInvitatePartecipantiProvider,
        APP_GARE_DETTAGLIO_IMPRESA_PARTE_PARAMS: DettaglioImpresaParteParamsProvider,

        APP_GARE_FASE_AVANZAMENTO_PROVIDER: FaseAvanzamentoProvider,

        APP_GARE_FASE_SOSPENSIONE_PROVIDER: FaseSospensioneProvider,

        APP_GARE_FASE_RIPRESA_PRESTAZIONE_PROVIDER: FaseRipresaPrestazioneProvider,

        APP_GARE_FASE_SUPERAMENTO_QUARTO_CONTRATTUALE_PROVIDER: FaseSuperamentoQuartoContrattualeProvider,
    
        APP_GARE_FASE_AVANZAMENTO_SEMP_PROVIDER: FaseAvanzamentoSempProvider,

        APP_GARE_FASE_ADESIONE_ACCORDO_QUADRO_PROVIDER: FaseAdesioneAccordoQuadroProvider,
        APP_GARE_LISTA_IMPR_AGG_FASE_ADESIONE_ACCORDO: ListaImpreseAggiudicatarieFaseAdesioneAccordoProvider,
        APP_GARE_INC_PROF_FASE_ADESIONE_ACCORDO: IncarichiProfessionaliFaseAdesioneAccordoProvider,
        APP_GARE_FINANZIAMENTI_FASE_ADESIONE_ACCORDO: FinanziamentiFaseAdesioneAccordoProvider,

        APP_GARE_FASE_MODIFICA_CONTRATTUALE_PROVIDER: FaseModificaContrattualeProvider,

        APP_GARE_FASE_INCARICHI_PROFESSIONALI_PROVIDER: FaseIncarichiProfessionaliProvider,

        APP_GARE_FASE_VARIAZIONE_AGGIUDICATARI_PROVIDER: FaseVariazioneAggiudicatariProvider,

        APP_GARE_FASE_STIPULA_ACCORDO_QUADRO_PROVIDER: FaseStipulaAccordoQuadroProvider,

        APP_GARE_FASE_CONCLUSIONE_QUADRO_PROVIDER: FaseConclusioneProvider,
        
        APP_GARE_FASE_CONCLUSIONE_AFFIDAMENTI_DIRETTI_PROVIDER: FaseConclusioneAffidamentiDirettiProvider,

        APP_GARE_FASE_INIZIALE_SEMP_PROVIDER: FaseInizialeSempProvider,
        APP_GARE_INC_PROF_FASE_INI_SEMP: IncarichiProfessionaliFaseInizialeSempProvider,
        APP_GARE_SCHE_SIC_FASE_INI_SEMP: SchedaSicurezzaFaseInizialeSempProvider,
        APP_GARE_MIS_AGG_SIC_FASE_INI_SEMP: MisureAggiuntiveSicurezzaFaseInizialeSempProvider,

        APP_GARE_FASE_ACCORDO_BONARIO_PROVIDER: FaseAccordoBonarioProvider,

        APP_GARE_FASE_CONCLUSIONE_SEMP_QUADRO_PROVIDER: FaseConclusioneSempProvider,

        APP_GARE_FASE_COLLAUDO_QUADRO_PROVIDER: FaseCollaudoProvider,
        APP_GARE_INC_PROF_FASE_COLLAUDO: IncarichiProfessionaliFaseCollaudoProvider,

        APP_GARE_FASE_SUBAPPALTO_PROVIDER: FaseSubappaltoProvider,

        APP_GARE_FASE_RICHIESTA_SUBAPPALTO_PROVIDER: FaseRichiestaSubappaltoProvider,
        
        APP_GARE_FASE_ESITO_SUBAPPALTO_PROVIDER: FaseEsitoSubappaltoProvider,

        APP_GARE_FASE_CONCLUSIONE_SUBAPPALTO_PROVIDER: FaseConclusioneSubappaltoProvider,

        APP_GARE_FASE_RECESSO_PROVIDER: FaseRecessoProvider,

        APP_GARE_FASE_INIDONEITA_TECNICO_PROF_PROVIDER: FaseInidoneitaTecnicoProfProvider,

        APP_GARE_FASE_INIDONEITA_CONTRIBUTIVA_QUADRO_PROVIDER: FaseInidoneitaContributivaProvider,

        APP_GARE_FASE_INADEMPIENZE_SICUREZZA_PROVIDER: FaseInadempienzeSicurezzaProvider,

        APP_GARE_FASE_INFORTUNI_PROVIDER: FaseInfortuniProvider,

        APP_GARE_FASE_CANTIERI_PROVIDER: FaseCantieriProvider,
        APP_GARE_INC_PROF_FASE_CANTIERI: IncarichiProfessionaliFaseCantieriProvider,

        APP_GARE_PUBBLICA: PubblicazioneGaraProvider,
        APP_GARE_PUBBLICA_ATTO: PubblicazioneAttoProvider,
        APP_GARE_MESSAGGI_AVVISI_ADMIN_VISIBLE: MessaggiAvvisiAdminVisibleProvider,
        APP_COMMONS_MESSAGGI_AVVISI_VISIBLE: MessaggiAvvisiVisibleProvider,
        APP_GARE_MESSAGGI_AVVISI: MessaggiAvvisiProvider,

        APP_FASI_LOTTO_PUBBLICA: PubblicazioneFaseLottoProvider,
        APP_GARE_ATTO_IMPRESE_AGGIUDICATARIE_TABS_VISIBLE: AttoImpreseAggiudicatarieTabsVisibleProvider,
        APP_GARE_ATTO_LOTTI_TABS_VISIBLE: AttoLottiTabsVisibleProvider,
        APP_GARE_LISTA_IMPR_AGG_ATTO: ListaImpreseAggiudicatarieAttoProvider,
        APP_CAMBIO_RUP: CambioRupProvider,
        APP_TRASFERIMENTO_AVVISO: TrasferimentoAvvisoProvider,
        APP_USER_OPTIONS_AUTOCOMPLETE: UserOptionsAutocompleteProvider,
        APP_GARE_LISTA_INVII_FASI_VISIBLE: ListaInviiFasiVisibleProvider,

        APP_GARE_SMARTCIG: SmartCigProvider,
        APP_GARE_GARA_SMARTCIG_VISIBLE: SmartCigTabsVisibleProvider,
        APP_GARE_GARA_STANDARD_VISIBLE: GaraStandardTabsVisibleProvider,
        APP_GARE_LISTA_IMPR_AGG_ATTO_LOTTO: ListaImpreseAggiudicatarieAttoLottoProvider,

        APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI: ArchivioStazioniAppaltantiProvider,
        APP_COMMONS_ADVANCED_SEARCH_ARCHIVIO_STAZIONI_APPALTANTI: AdvancedSearchArchivioStazioniAppaltantiProvider,
        APP_COMMONS_STAZIONE_APPALTANTE_PARAMS: StazioneAppaltanteParamsProvider,
        APP_GARE_IMPRESA_NEW_MODAL: ImpresaNewModalProvider,
        APP_GARE_ARCHIVIO_STAZIONI_APPALTANTI_UTENTI: UtentiStazioneAppaltanteProvider,
        STAZIONE_APPALTANTE_AUTOCOMPLETE: StazioneAppaltanteAutocompleteProvider,
        APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI_MENU_VISIBLE: ArchivioStazioniAppaltantiMenuVisibleProvider,

        BREADCRUMB_RICERCA_GARE_VISIBLE: BreadcrumbRicercaAvanzataGareVisibleProvider,
        BREADCRUMB_RICERCA_AVVISI_VISIBLE: BreadcrumbRicercaAvanzataAvvisiVisibleProvider,
        BREADCRUMB_DETTAGLIO_GARA_NUMBER: BreadcrumbDettaglioGaraNumberProvider,
        BREADCRUMB_DETTAGLIO_LOTTO_NUMBER: BreadcrumbDettaglioLottoNumberProvider,

        APP_GARE_FINANZIAMENTI_TABS_VISIBLE: FinanziamentiTabsVisibleProvider,

        APP_GARE_LISTA_TECNICI_MODAL: ListaTecniciModalProvider,
        APP_GARE_LISTA_COLLABORAZIONI_MODAL: ListaCollaborazioniModalProvider,
        APP_GARA_CIG_MASTER_AUTOCOMPLETE: CigMasterAutocompleteProvider,
        APP_GARE_ACCORPAMENTO_LOTTI: AccorpamentoLottiProvider,

        APP_CONTRATTI_ATTI_GENERALI: AttiGeneraliProvider,
        APP_CONTRATTI_MODIFICA_ATTO_GENERALE: ModificaAttoGeneraleProvider,

        HEADER_ADVANCED_SEARCH_TECNICO: AdvancedSearchTecnicoProvider,
        HEADER_ADVANCED_SEARCH_IMPRESE: AdvancedSearchImpreseProvider,
        HEADER_ADVANCED_SEARCH_CDC: AdvancedSearchCdcProvider,
        HEADER_ADVANCED_SEARCH_GARE: HeaderAdvancedSearchGareProvider,
        //VIGILANZA2-407: Rimozione tab 'Avvisi' dalla textbox di ricerca nell'intestazione  
        //HEADER_ADVANCED_SEARCH_AVVISI: HeaderAdvancedSearchAvvisiProvider,

        APP_CONTRATTI_STAZIONI_APPALTANTI_COUNT_VISIBLE: StazioniAppaltantiCountMenuVisibleProvider,
        APP_CONTRATTI_PROFILI_VISIBLE: ProfiliVisibleProvider,
        APP_CONTRATTI_APPLICAZIONI_VISIBLE: ApplicazioniVisibleProvider,

        APP_GARE_IMPERSONIFICA_RUP: ImpersonificaRupProvider,

        // Archivio uffici
        APP_COMMONS_ARCHIVIO_UFFICI_ADVANCED_SEARCH: AdvancedSearchArchivioUfficiProvider,
        APP_COMMONS_UFFICIO_PARAMS: UfficioParamsProvider,
        APP_COMMONS_UFFICIO: UfficioProvider,

        // SDK
        SDK_GESTIONE_UTENTI_TABELLATI_COMBO: SdkGestioneUtentiTabellatiComboProvider,
        SDK_GESTIONE_MODELLI_TABELLATI_COMBO: SdkGestioneModelliTabellatiComboProvider,

        SDK_GESTIONE_UTENTI_SEARCH: SdkGestioneUtentiSearchProvider,
        SDK_GESTIONE_UTENTI_LISTA: SdkListaUtentiProvider,
        SDK_GESTIONE_UTENTI_CHECK_NUOVO_UTENTE: SdkCheckNuovoUtenteProvider,
        SDK_GESTIONE_UTENTI_UTENTE: SdkUtenteProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_UTENTE_PARAMS: SdkDettaglioUtenteParamsProvider,
        SDK_GESTIONE_UTENTI_UFFICIO_INTESTATARIO_AUTOCOMPLETE: SdkUfficioIntestatarioAutocompleteProvider,
        SDK_GESTIONE_UTENTI_PROFILO_AUTOCOMPLETE: SdkProfiloAutocompleteProvider,
        SDK_GESTIONE_UTENTI_MENU_VISIBLE: SdkGestioneUtentiMenuVisibleProvider,
        
        SDK_GESTIONE_UTENTI_PROFILI_UTENTE: SdkProfiliUtenteProvider,
        SDK_GESTIONE_UTENTI_STAZIONI_APPALTANTI_UTENTE: SdkStazioniAppaltantiUtenteProvider,
        SDK_GESTIONE_UTENTI_CAMBIA_PASSWORD_ADMIN_UTENTE: SdkCambiaPasswordAdminUtenteProvider,
        SDK_GESTIONE_UTENTI_RICHIESTA_ASSISTENZA: SdkRichiestaAssistenzaProvider,
        SDK_GESTIONE_UTENTI_RICHIESTA_ASSISTENZA_MENU_VISIBLE: SdkGestioneUtentiRichiestaAssistenzaMenuVisibleProvider,
        SDK_GESTIONE_UTENTI_AMMINISTRAZIONE_MENU_VISIBLE: SdkGestioneUtentiAmministrazioneMenuVisibleProvider,
        SDK_GESTIONE_CONFIGURAZIONI_SEARCH: SdkGestioneConfigurazioniSearchProvider,
        SDK_GESTIONE_CONFIGURAZIONI_LISTA: SdkListaConfigurazioniProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_CONFIGURAZIONE_PARAMS: SdkDettaglioConfigurazioneParamsProvider,
        SDK_GESTIONE_UTENTI_CONFIGURAZIONE: SdkConfigurazioneProvider,
        SDK_GESTIONE_PIANIFICAZIONI_LISTA: SdkListaPianificazioniProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_PIANIFICAZIONE_PARAMS: SdkDettaglioPianificazioneParamsProvider,
        SDK_GESTIONE_UTENTI_PIANIFICAZIONE: SdkPianificazioneProvider,
        SDK_GESTIONE_EVENTI_SEARCH: SdkGestioneEventiSearchProvider,
        SDK_GESTIONE_EVENTI_LISTA: SdkListaEventiProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_EVENTO_PARAMS: SdkDettaglioEventoParamsProvider,
        SDK_GESTIONE_POSTA_LISTA: SdkListaServerPostaProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_POSTA_PARAMS: SdkDettaglioServerPostaParamsProvider,
        SDK_GESTIONE_UTENTI_POSTA: SdkServerPostaProvider,
        SDK_GESTIONE_UTENTI_IMPOSTA_PASSWORD_POSTA: SdkImpostaPasswordServerPostaProvider,
        SDK_GESTIONE_UTENTI_INVIA_TEST_EMAIL: SdkInviaTestEmailPostaProvider,
        SDK_GESTIONE_TABELLATI_SEARCH: SdkGestioneTabellatiSearchProvider,
        SDK_GESTIONE_TABELLATI_LISTA: SdkListaTabellatiProvider,
        SDK_GESTIONE_UTENTI_LISTA_DETTAGLIO_TABELLATO_PARAMS: SdkListaDettaglioTabellatoParamsProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_TABELLATO_PARAMS: SdkDettaglioTabellatoParamsProvider,
        SDK_GESTIONE_TABELLATI_TABELLATO: SdkTabellatoProvider,
        SDK_GESTIONE_MNEMONICI_LISTA: SdkListaMnemoniciProvider,
        SDK_GESTIONE_CODIFICA_AUTOMATICA_LISTA: SdkListaCodificaAutomaticaProvider,
        SDK_GESTIONE_UTENTI_DETTAGLIO_CODIFICA_AUTOMATICA_PARAMS: SdkDettaglioCodificaAutomaticaParamsProvider,
        SDK_GESTIONE_CODIFICA_AUTOMATICA: SdkCodificaAutomaticaProvider,
        APP_COMMONS_STAZIONE_APPALTANTE_UTENTI_VISIBLE: StazioneAppaltanteUtentiVisibleProvider,
        APP_COMMONS_UTENTE_STAZIONE_APPALTANTE_PARAMS: UtenteStazioneAppaltanteParamsProvider,
        APP_COMMONS_UTENTE_STAZIONE_APPALTANTE: UtenteStazioneAppaltanteProvider,
        SDK_INTERNAL_LOGIN: SdkInternalLoginProvider,
        SDK_GESTIONE_UTENTI_RECUPERO_PASSWORD: SdkPasswordRecoveryProvider,

        SDK_MIO_UTENTE: SdkUtenteConnessoProvider,
        APP_COMMONS_MIO_ACCOUNT: MioAccountProvider,
        APP_COMMONS_MIO_ACCOUNT_VISIBLE: MioAccountVisibleProvider,
        APP_COMMONS_MESSAGGI_AVVISI_STATUS: MessaggiAvvisiStatusProvider,
        APP_COMMONS_CHANGE_USER_PASSWORD_VISIBLE: ChangeUserPasswordVisibleProvider,
        APP_COMMONS_CHANGE_USER_PASSWORD: ChangeUserPasswordProvider,
        APP_COMMONS_MANUALI_URL: ManualiUrlProvider,
        APP_COMMONS_ULTIMI_ACCESSI_VISIBLE: UltimiAccessiVisibleProvider,
        APP_COMMONS_ULTIMI_ACCESSI: UltimiAccessiProvider,
        SDK_ULTIMI_ACCESSI: SdkUltimiAccessiProvider,

        APP_CONTRATTI_LISTA_DELEGHE: ListaDelegheProvider,
        APP_CONTRATTI_DELEGA: DelegaProvider,
        APP_CONTRATTI_DETTAGLIO_DELEGA_PARAMS: DettaglioDelegaParamsProvider,

        APP_CONTRATTI_CREDENZIALI_SIMOG_MODAL: CredenzialiSimogModalProvider,
        APP_CONTRATTI_LISTA_DELEGHE_MENU_VISIBLE: ListaDelegheVisibleProvider,
        APP_CONTRATTI_IMPERSONIFICA_RUP_MENU_VISIBLE: ImpersonificaRupVisibleProvider,

        APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI_UTENTI: UtentiStazioneAppaltanteProvider,
        
        SDK_RICERCA_MODELLI_AUTOCOMPLETE: SdkRicercaModelliAutocompleteProvider,
        SDK_GESTIONE_MODELLI_SEARCH: SdkRicercaModelliProvider,
        SDK_GESTIONE_MODELLI_LISTA: SdkListaModelliProvider,
        SDK_MODELLI_TABELLATI_COMBO: SdkGestioneModelliTabellatiComboProvider,
        SDK_GESTIONE_MODELLI_MODELLO: SdkModelloProvider,
        SDK_MODELLO_PARAMS: SdkListaParametriParamsProvider,
        SDK_DETTAGLIO_MODELLO_PARAMS: SdkDettaglioModelloParamsProvider,
        SDK_GESTIONE_PARAMETRO: SdkParametroProvider,
        SDK_GESTIONE_MODELLI_MENU_VISIBLE: SdkGestioneModelliMenuVisibleProvider,
        SDK_GESTIONE_MODELLI_PREDISPOSTI: SdkModelliPredispostiProvider,
        SDK_COMPOSITORE: SdkCompositoreProvider,
        SDK_GESTIONE_UTENTI_GESTORE_STAZIONI_APPALTANTI_TABS_VISIBLE: SdkGestioneUtentiGestoreStazioniAppaltantiVisibleProvider,
        SDK_GESTIONE_UTENTI_GESTORE_PROFILI_TABS_VISIBLE: SdkGestioneUtentiGestoreProfiliVisibleProvider,

        //Schede trasmesse a PCP
        APP_CONTRATTI_SCHEDE_TRASMESSE_PCP_FILTRO_UFFINT: SchedeTrasmesseFiltroUffIntProvider,
        SDK_GESTIONE_REPORT_MENU_VISIBLE: SdkGestioneReportMenuVisibleProvider,
        SDK_GESTIONE_REPORT_LISTA_REPORT: SdkListaReportProvider,
        SDK_GESTIONE_REPORT_DETTAGLIO_REPORT_PARAMS: SdkDettaglioReportParamsProvider,
        SDK_GESTIONE_REPORT_DETTAGLIO_REPORT_PROVIDER: SdkGestioneReportProvider,
        SDK_GESTIONE_REPORT_TABELLATI_PROVIDER: SdkGestioneReportsTabellatiProvider,
        SDK_GESTIONE_REPORT_PROFILI_MENU_VISIBLE: SdkGestioneReportsProfiliVisibleProvider,
        SDK_GESTIONE_REPORT_UFFINT_MENU_VISIBLE: SdkGestioneReportsUfficiIntestatariVisibleProvider,
        SDK_REPORT_PREDEFINITI_MENU_VISIBLE: SdkReportPredefinitiMenuVisibleProvider,
        SDK_REPORT_PREDEFINITI_LISTA_REPORT_PROVIDER: SdkListaReportPredefinitiProvider,
        BREADCRUMB_NOME_REPORT_PROVIDER: BreadcrumbNomeReportProvider,

        //SDK DOCASSOCIATI
        SDK_DOC_ASSOCIATI_C0OGGASS_BREADCRUMB: SdkC0oggassBreadcrumbProvider,
        SDK_DOC_ASSOCIATI_C0OGGASS: SdkC0oggassProvider,
        SDK_DOC_ASSOCIATI_TABELLATI_COMBO: SdkDocAssociatiTabellatiComboProvider,
        DOCASSOCIATI_MODAL_WINDOW: SdkDocAssociatiModalWindowProvider,
    };
}
