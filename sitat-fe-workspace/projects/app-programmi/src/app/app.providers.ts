import { Type } from '@angular/core';
import {
    AdvancedSearchArchivioStazioniAppaltantiProvider,
    AdvancedSearchArchivioTecniciProvider,
    AdvancedSearchArchivioUfficiProvider,
    AdvancedSearchTecnicoProvider,
    AdvancedSearchUfficiProvider,
    ApplicazioniVisibleProvider,
    ArchivioStazioniAppaltantiMenuVisibleProvider,
    ArchivioStazioniAppaltantiProvider,
    ChangeUserPasswordProvider,
    ChangeUserPasswordVisibleProvider,
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

import { SdkGestioneModelliMenuVisibleProvider } from '@maggioli/sdk-gestione-modelli';
import { UltimiAccessiProvider } from 'projects/app-commons/src/lib/modules/app-commons/providers/ultimi-accessi/ultimi-accessi.provider';
import {
    HeaderAdvancedSearchProgrammiProvider,
} from './modules/providers/advanced-search/advanced-search-programmi.provider';
import { CambioModuloProvider } from './modules/providers/cambio-modulo.provider';
import { ChooseEnteProvider } from './modules/providers/choose-ente.provider';
import { ChooseProfileProvider } from './modules/providers/choose-profile.provider';
import { ComuniDescrizioneProvider } from './modules/providers/comuni-descrizione.provider';
import { ComuniIstatProvider } from './modules/providers/comuni-istat.provider';
import { ComuniProvider } from './modules/providers/comuni.provider';
import { CuiInterventoProvider } from './modules/providers/cui-intervento.provider';
import { HeaderProvider } from './modules/providers/header.provider';
import { SdkInternalLoginProvider } from './modules/providers/internal-login/sdk-internal-login.provider';
import {
    InterventoNonRipropostoProvider,
} from './modules/providers/intervento-non-riproposto/intervento-non-riproposto.provider';
import { DettaglioInterventoParamsProvider } from './modules/providers/intervento/dettaglio-intervento-params.provider';
import { DettaglioInterventoProvider } from './modules/providers/intervento/dettaglio-intervento.provider';
import { ImportInterventiProvider } from './modules/providers/intervento/import-interventi.provider';
import { InterventoProvider } from './modules/providers/intervento/intervento.provider';
import { ModificaInterventoProvider } from './modules/providers/intervento/modifica-intervento.provider';
import { ExportInterventoAcquistoNrProvider } from './modules/providers/lista-interventi/export-intervento-acquisto-nr.provider';
import { ExportInterventoAcquistoProvider } from './modules/providers/lista-interventi/export-intervento-acquisto.provider';
import { ListaInterventiNonRipropostiProvider } from './modules/providers/lista-interventi/lista-interventi-nonrip.provider';
import { ListaInterventiProvider } from './modules/providers/lista-interventi/lista-interventi.provider';
import { ListaProgrammiParamsProvider } from './modules/providers/lista-programmi/lista-programmi-params.provider';
import { ListaProgrammiProvider } from './modules/providers/lista-programmi/lista-programmi.provider';
import { LogoutProvider } from './modules/providers/logout.provider';
import { ModalWindowProvider } from './modules/providers/modal/modal-window-provider';
import { RupNewModalProvider } from './modules/providers/modal/rup-new-modal.provider';
import { UfficiNewModalProvider } from './modules/providers/modal/uffici-new-modal.provider';
import {
    DettaglioOperaIncompiutaParamsProvider,
} from './modules/providers/opera-incompiuta/dettaglio-opera-incompiuta-params.provider';
import { DettaglioOperaIncompiutaProvider } from './modules/providers/opera-incompiuta/dettaglio-opera-incompiuta.provider';
import { ModificaOperaIncompiutaProvider } from './modules/providers/opera-incompiuta/modifica-opera-incompiuta.provider';
import { OperaIncompiutaProvider } from './modules/providers/opera-incompiuta/opera-incompiuta-provider';
import { ConfrontoProgrammiProvider } from './modules/providers/programma/confronto-programmi.provider';
import {
    DettProgFornitureTabsVisibleProvider,
} from './modules/providers/programma/dett-prog-forniture-tabs-visible.provider';
import { DettProgLavoriTabsVisibleProvider } from './modules/providers/programma/dett-prog-lavori-tabs-visible.provider';
import { DettaglioProgrammaParamsProvider } from './modules/providers/programma/dettaglio-programma-params.provider';
import { DettaglioProgrammaProvider } from './modules/providers/programma/dettaglio-programma.provider';
import { ModificaProgrammaProvider } from './modules/providers/programma/modifica-programma.provider';
import { NuovoProgrammaProvider } from './modules/providers/programma/nuovo-programma.provider';
import { TrasferimentoProgrammaProvider } from './modules/providers/programma/trasferimento-programma.provider';
import { PubblicazioneProgrammaProvider } from './modules/providers/pubblicazione-programma.provider';
import {
    DettaglioRisorsaDiCapitoloParamsProvider,
} from './modules/providers/risorse-di-capitolo/dettaglio-risorsa-di-capitolo-params.provider';
import { ListaRisorseDiCapitoloProvider } from './modules/providers/risorse-di-capitolo/lista-risorse-di-capitolo.provider';
import { RisorsaDiCapitoloProvider } from './modules/providers/risorse-di-capitolo/risorsa-di-capitolo.provider';
import { RupProgrammaProvider } from './modules/providers/rup-programma.provider';
import { StazioneAppaltanteAutocompleteProvider } from './modules/providers/stazione-appaltante-autocomplete.provider';
import { UfficiProgrammaProvider } from './modules/providers/uffici-programma.provider';
import { SdkUltimiAccessiProvider } from '@maggioli/sdk-widgets';
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

export function providers(): IDictionary<Type<SdkProvider>> {
    return {
        APP_PROGRAMMI_LOGOUT: LogoutProvider,
        APP_PROGRAMMI_CHOOSE_ENTE: ChooseEnteProvider,
        APP_PROGRAMMI_CHOOSE_PROFILE: ChooseProfileProvider,
        APP_PROGRAMMI_LISTA_PROGRAMMI: ListaProgrammiProvider,
        APP_PROGRAMMI_MODAL_WINDOW: ModalWindowProvider,
        APP_PROGRAMMI_UFFICI_NEW_MODAL: UfficiNewModalProvider,
        APP_PROGRAMMI_UFFICI_PROGRAMMA: UfficiProgrammaProvider,
        APP_PROGRAMMI_NUOVO_PROGRAMMA: NuovoProgrammaProvider,
        APP_PROGRAMMI_MODIFICA_PROGRAMMA: ModificaProgrammaProvider,
        APP_PROGRAMMI_RUP_PROGRAMMA: RupProgrammaProvider,
        APP_PROGRAMMI_RUP_NEW_PROGRAMMA: RupNewModalProvider,
        APP_PROGRAMMI_LISTA_PROGRAMMI_PARAMS: ListaProgrammiParamsProvider,
        APP_PROGRAMMI_DETTAGLIO_PROGRAMMA_PARAMS: DettaglioProgrammaParamsProvider,
        APP_PROGRAMMI_DETTAGLIO_PROGRAMMA: DettaglioProgrammaProvider,
        APP_PROGRAMMI_DETT_PROG_LAVORI_TABS_VISIBLE: DettProgLavoriTabsVisibleProvider,
        APP_PROGRAMMI_DETT_PROG_FORNITURE_TABS_VISIBLE: DettProgFornitureTabsVisibleProvider,
        APP_PROGRAMMI_LISTA_INTERVENTI_NP: ListaInterventiNonRipropostiProvider,
        APP_PROGRAMMI_IMPORTA_INTERVENTI: ImportInterventiProvider,
        APP_PROGRAMMI_DETTAGLIO_OPERA_INCOMPIUTA_PARAMS: DettaglioOperaIncompiutaParamsProvider,
        APP_PROGRAMMI_LISTA_INTERVENTI: ListaInterventiProvider,
        APP_PROGRAMMI_DETTAGLIO_INTERVENTO: DettaglioInterventoProvider,
        APP_PROGRAMMI_OPERA_INCOMPIUTA: OperaIncompiutaProvider,
        APP_PROGRAMMI_TABELLATI_COMBO: TabellatiComboProvider,
        APP_PROGRAMMI_DETTAGLIO_OPERA_INCOMPIUTA: DettaglioOperaIncompiutaProvider,
        APP_PROGRAMMI_MODIFICA_OPERA_INCOMPIUTA: ModificaOperaIncompiutaProvider,
        APP_PROGRAMMI_COMUNI: ComuniProvider,
        APP_PROGRAMMI_COMUNI_DESCRIZIONE: ComuniDescrizioneProvider,
        APP_PROGRAMMI_INTERVENTO_PROVIDER: InterventoProvider,
        APP_PROGRAMMI_MODIFICA_INTERVENTO_PROVIDER: ModificaInterventoProvider,
        APP_PROGRAMMI_DETTAGLIO_INTERVENTO_PARAMS: DettaglioInterventoParamsProvider,
        APP_PROGRAMMI_LISTA_RISORSE_DI_CAPITOLO: ListaRisorseDiCapitoloProvider,
        APP_PROGRAMMI_RISORSA_DI_CAPITOLO: RisorsaDiCapitoloProvider,
        APP_PROGRAMMI_DETTAGLIO_RISORSA_PARAMS: DettaglioRisorsaDiCapitoloParamsProvider,
        APP_PROGRAMMI_PUBBLICA: PubblicazioneProgrammaProvider,
        APP_PROGRAMMI_HEADER_MID: HeaderProvider,
        APP_COMMONS_LISTA_TECNICI: ListaTecniciProvider,
        APP_COMMONS_TECNICO: TecnicoProvider,
        APP_COMMONS_TECNICO_PARAMS: TecnicoParamsProvider,
        APP_PROGRAMMI_CAMBIO_MODULO: CambioModuloProvider,
        APP_PROGRAMMI_MESSAGGI_AVVISI_ADMIN_VISIBLE: MessaggiAvvisiAdminVisibleProvider,
        APP_COMMONS_MESSAGGI_AVVISI_VISIBLE: MessaggiAvvisiVisibleProvider,
        APP_PROGRAMMI_MESSAGGI_AVVISI: MessaggiAvvisiProvider,
        APP_TRASFERIMENTO_PROGRAMMA: TrasferimentoProgrammaProvider,
        APP_USER_OPTIONS_AUTOCOMPLETE: UserOptionsAutocompleteProvider,
        APP_COMMONS_UFFICIO_PARAMS: UfficioParamsProvider,
        APP_COMMONS_UFFICIO: UfficioProvider,
        APP_COMMONS_ARCHIVIO_TECNICI_ADVANCED_SEARCH: AdvancedSearchArchivioTecniciProvider,
        APP_COMMONS_ARCHIVIO_UFFICI_ADVANCED_SEARCH: AdvancedSearchArchivioUfficiProvider,
        STAZIONE_APPALTANTE_AUTOCOMPLETE: StazioneAppaltanteAutocompleteProvider,
        APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI: ArchivioStazioniAppaltantiProvider,
        APP_COMMONS_ADVANCED_SEARCH_ARCHIVIO_STAZIONI_APPALTANTI: AdvancedSearchArchivioStazioniAppaltantiProvider,
        APP_COMMONS_STAZIONE_APPALTANTE_PARAMS: StazioneAppaltanteParamsProvider,
        APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI_UTENTI: UtentiStazioneAppaltanteProvider,
        APP_COMMONS_ARCHIVIO_STAZIONI_APPALTANTI_MENU_VISIBLE: ArchivioStazioniAppaltantiMenuVisibleProvider,

        APP_PROGRAMMI_COMUNI_ISTAT: ComuniIstatProvider,
        APP_PROGRAMMI_INTERVENTO_NON_RIPROPOSTO_PROVIDER: InterventoNonRipropostoProvider,
        APP_PROGRAMMI_CUI_AUTOCOMPLETE: CuiInterventoProvider,

        HEADER_ADVANCED_SEARCH_TECNICO: AdvancedSearchTecnicoProvider,
        HEADER_ADVANCED_SEARCH_UFFICI: AdvancedSearchUfficiProvider,
        HEADER_ADVANCED_SEARCH_PROGRAMMI: HeaderAdvancedSearchProgrammiProvider,

        APP_PROGRAMMI_STAZIONI_APPALTANTI_COUNT_VISIBLE: StazioniAppaltantiCountMenuVisibleProvider,
        APP_PROGRAMMI_PROFILI_VISIBLE: ProfiliVisibleProvider,
        APP_PROGRAMMI_APPLICAZIONI_VISIBLE: ApplicazioniVisibleProvider,

        // SDK
        SDK_GESTIONE_UTENTI_TABELLATI_COMBO: SdkGestioneUtentiTabellatiComboProvider,
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
        APP_COMMONS_ULTIMI_ACCESSI_VISIBLE: UltimiAccessiVisibleProvider,
        APP_COMMONS_ULTIMI_ACCESSI: UltimiAccessiProvider,
        SDK_ULTIMI_ACCESSI: SdkUltimiAccessiProvider,
        APP_COMMONS_MANUALI_URL: ManualiUrlProvider,
        APP_PROGRAMMI_EXPORT_INTERVENTI_ACQUISTI: ExportInterventoAcquistoProvider,
        APP_PROGRAMMI_EXPORT_INTERVENTI_ACQUISTI_NR: ExportInterventoAcquistoNrProvider,
        APP_PROGRAMMI_CONFRONTO_PROGRAMMI: ConfrontoProgrammiProvider,
        SDK_GESTIONE_MODELLI_MENU_VISIBLE: SdkGestioneModelliMenuVisibleProvider,
        SDK_GESTIONE_UTENTI_GESTORE_STAZIONI_APPALTANTI_TABS_VISIBLE: SdkGestioneUtentiGestoreStazioniAppaltantiVisibleProvider,
        SDK_GESTIONE_UTENTI_GESTORE_PROFILI_TABS_VISIBLE: SdkGestioneUtentiGestoreProfiliVisibleProvider,

        SDK_GESTIONE_REPORT_MENU_VISIBLE: SdkGestioneReportMenuVisibleProvider,
        SDK_GESTIONE_REPORT_LISTA_REPORT: SdkListaReportProvider,
        SDK_GESTIONE_REPORT_DETTAGLIO_REPORT_PARAMS: SdkDettaglioReportParamsProvider,
        SDK_GESTIONE_REPORT_DETTAGLIO_REPORT_PROVIDER: SdkGestioneReportProvider,
        SDK_GESTIONE_REPORT_TABELLATI_PROVIDER: SdkGestioneReportsTabellatiProvider,
        SDK_GESTIONE_REPORT_PROFILI_MENU_VISIBLE: SdkGestioneReportsProfiliVisibleProvider,
        SDK_GESTIONE_REPORT_UFFINT_MENU_VISIBLE: SdkGestioneReportsUfficiIntestatariVisibleProvider,
        SDK_REPORT_PREDEFINITI_MENU_VISIBLE: SdkReportPredefinitiMenuVisibleProvider,
        SDK_REPORT_PREDEFINITI_LISTA_REPORT_PROVIDER: SdkListaReportPredefinitiProvider,
        BREADCRUMB_NOME_REPORT_PROVIDER: BreadcrumbNomeReportProvider
    };
}
