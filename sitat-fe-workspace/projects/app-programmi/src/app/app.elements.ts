import { Type } from '@angular/core';
import {
    AppInfoModalModalWidget,
    CheckPubblicazioneSectionWidget,
    ConfigurazioneUtenteSectionWidget,
    CpvModalWidget,
    DettaglioPubblicazioneModalWidget,
    DettaglioStazioneAppaltanteSectionWidget,
    DettaglioTecnicoSectionWidget,
    DettaglioUfficioSectionWidget,
    ListaArchivioStazioniAppaltantiSectionWidget,
    ListaArchivioTecniciSectionWidget,
    ListaArchivioUfficiSectionWidget,
    MessaggiAvvisiModalWidget,
    MessaggiAvvisiOverlayWidget,
    MnemonicoModalWidget,
    ModificaConfigurazioneUtenteSectionWidget,
    ModificaStazioneAppaltanteSectionWidget,
    ModificaTecnicoSectionWidget,
    ModificaUfficioSectionWidget,
    ModificaUtentiStazioneAppaltanteComponent,
    NuovaStazioneAppaltanteSectionWidget,
    NuovoTecnicoSectionWidget,
    NuovoUfficioSectionWidget,
    NutsModalWidget,
    OauthLoginSectionWidget,
    RedirectSectionWidget,
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
import { ImportInterventiModalWidget } from './modules/layout/components/business/interventi/import-modal/import-interventi-modal.widget';
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
import { ExportInterventiAcquistiNrModalWidget } from './modules/layout/components/business/interventi-non-riproposti/export-interventi-acquisti-nr-modal/export-interventi-acquisti-nr-modal.widget';
import { ConfrontoProgrammiModalWidget } from './modules/layout/components/business/dettaglio-programma/confronto-programmi-modal/confronto-programmi-modal.widget';
import { 
    SdkCreaNuovoReportComponent,
    SdkDatiGeneraliReportComponent,
    SdkDefinizioneReportComponent, 
    SdkDettaglioParametroReportComponent, 
    SdkInserisciParametriReportComponent, 
    SdkListaReportComponent, 
    SdkListaReportPredefinitiComponent, 
    SdkModificaDatiGeneraliReportComponent, 
    SdkModificaDefinizioneReportComponent, 
    SdkModificaDettaglioParametroComponent, 
    SdkModificaProfiliReportComponent, 
    SdkModificaUfficiIntestatariReportComponent, 
    SdkNuovoParametroReportComponent, 
    SdkParametriReportComponent,
    SdkProfiliReportComponent,
    SdkRisultatoEsecuzioneReportComponent,
    SdkRisultatoEsecuzioneReportPredefinitiComponent,
    SdkUfficiIntestatariReportComponent
} from '@maggioli/sdk-gestione-reports';import { DettaglioInterventoNonRipropostoSectionWidget } from './modules/layout/components/business/interventi-non-riproposti/dettaglio/dettaglio-intervento-non-riproposto-section.widget';
import { ModificaInterventoNonRipropostoSectionWidget } from './modules/layout/components/business/interventi-non-riproposti/modifica/modifica-intervento-non-riproposto-section.widget';


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

        'layout-side-menu-tabs': LayoutSideMenuTabsWidget,
        'layout-menu-tabs-old': LayoutMenuTabsOldWidget,

        'index-section': IndexSectionWidget,
        'choose-ente-section': ChooseEnteSectionWidget,
        'choose-profile-section': ChooseProfileSectionWidget,
        'lista-programmi-section': ListaProgrammiSectionWidget,
        'dett-prog-dati-generali-section': DettaglioProgrammaDatiGeneraliSectionWidget,
        'dett-prog-opere-incompiute-section': DettaglioProgrammaOpereIncompiuteSectionWidget,
        'dett-prog-interventi-section': DettaglioProgrammaInterventiSectionWidget,
        'dett-prog-interventi-non-riproposti-section': DettaglioProgrammaInterventiNonRipropostiSectionWidget,
        'dett-prog-riepilogo-section': DettaglioProgrammaRiepilogoSectionWidget,
        'dett-prog-pubblica-section': DettaglioProgrammaPubblicaSectionWidget,
        'nuovo-programma-section': NuovoProgrammaSectionWidget,
        'modifica-programma-section': ModificaProgrammaSectionWidget,
        'import-interventi-non-riproposti-section': ImportInterventiNonRipropostiSectionWidget,
        'import-interventi-section': ImportInterventiSectionWidget,
        'dett-generale-opera-incompiuta-section': DettaglioGeneraleOperaIncompiutaSectionWidget,
        'dett-altri-dati-opera-incompiuta-section': DettaglioAltriDatiOperaIncompiutaSectionWidget,
        'nuova-opera-incompiuta-section': NuovaOperaIncompiutaSectionWidget,
        'dett-intervento-section': DettaglioInterventoSectionWidget,
        'modifica-generale-opera-incompiuta-section': ModificaGeneraleOperaIncompiutaSectionWidget,
        'modifica-altri-dati-opera-incompiuta-section': ModificaAltriDatiOperaIncompiutaSectionWidget,
        'nuovo-intervento-section': NuovoInterventoSectionWidget,
        'risorse-di-capitolo-section': ListaRisorseDiCapitoloSectionWidget,
        'nuova-risorsa-di-capitolo-section': NuovaRisorsaDiCapitoloSectionWidget,
        'modifica-intervento-section': ModificaInterventoSectionWidget,
        'dett-risorsa-di-capitolo-section': DettaglioRisorsaDiCapitoloSectionWidget,
        'modifica-risorsa-di-capitolo-section': ModificaRisorsaDiCapitoloSectionWidget,
        'lista-archivio-tecnici-section': ListaArchivioTecniciSectionWidget,
        'nuovo-tecnico-section': NuovoTecnicoSectionWidget,
        'dettaglio-tecnico-section': DettaglioTecnicoSectionWidget,
        'modifica-tecnico-section': ModificaTecnicoSectionWidget,

        'lista-archivio-uffici-section': ListaArchivioUfficiSectionWidget,
        'nuovo-ufficio-section': NuovoUfficioSectionWidget,
        'dettaglio-ufficio-section': DettaglioUfficioSectionWidget,
        'modifica-ufficio-section': ModificaUfficioSectionWidget,

        'ricerca-avanzata-archivio-stazioni-appaltanti-section': RicercaAvanzataArchivioStazioniAppaltantiSectionWidget,
        'lista-archivio-stazioni-appaltanti-section': ListaArchivioStazioniAppaltantiSectionWidget,
        'nuova-stazione-appaltante-section': NuovaStazioneAppaltanteSectionWidget,
        'dettaglio-stazione-appaltante-section': DettaglioStazioneAppaltanteSectionWidget,
        'modifica-stazione-appaltante-section': ModificaStazioneAppaltanteSectionWidget,
        'utenti-stazione-appaltante-section': UtentiStazioneAppaltanteComponent,
        'modifica-utenti-stazione-appaltante-section': ModificaUtentiStazioneAppaltanteComponent,
        'nuovo-intervento-non-riproposto-section': NuovoInterventoNonRipropostoSectionWidget,
        'dettaglio-intervento-non-riproposto-section': DettaglioInterventoNonRipropostoSectionWidget,
        'modifica-intervento-non-riproposto-section': ModificaInterventoNonRipropostoSectionWidget,
        'redirect-section': RedirectSectionWidget,
        'ricerca-avanzata-archivio-tecnici-section': RicercaAvanzataArchivioTecniciSectionWidget,
        'ricerca-avanzata-archivio-uffici-section': RicercaAvanzataArchivioUfficiSectionWidget,
        'configurazione-utente-section-widget': ConfigurazioneUtenteSectionWidget,
        'modifica-configurazione-utente-section-widget': ModificaConfigurazioneUtenteSectionWidget,


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

        'sdk-mio-utente-section': SdkMioAccountComponent,
        'sdk-change-user-password-section': SdkChangePasswordUtenteFormComponent,
        'spid-login-section': SpidLoginSectionWidget,
        'oauth-login-section': OauthLoginSectionWidget
    }
}

export function customElementsMap(): IDictionary<Type<any>> {
    return {
        'uff-modal-widget': UffModalWidget,
        'rup-modal-widget': RupModalWidget,
        'nuts-modal-widget': NutsModalWidget,
        'cpv-modal-widget': CpvModalWidget,
        'import-interventi-modal-widget': ImportInterventiModalWidget,
        'dettaglio-cup-modal-widget': DettaglioCupModalWidget,
        'cui-modal-widget': CuiModalWidget,
        'stazione-appaltante-modal-widget': StazioneAppaltanteModalWidget,
        'update-intervento-norip-modal-widget': UpdateInterventoNoRipModalWidget,
        'check-pubblicazione-widget': CheckPubblicazioneSectionWidget,
        'mnemonico-modal-widget': MnemonicoModalWidget,
        'messaggi-avvisi-modal-widget': MessaggiAvvisiModalWidget,
        'messaggi-avvisi-overlay-widget': MessaggiAvvisiOverlayWidget,
        'dettaglio-pubblicazione-modal-widget': DettaglioPubblicazioneModalWidget,
        'rup-rw-modal-widget': RupRWModalWidget,
        'trasferimento-modal-widget': TrasferimentoModalWidget,
        'app-info-modal-widget': AppInfoModalModalWidget,        
        'export-interventi-acquisti-modal-widget': ExportInterventiAcquistiModalWidget,
        'export-interventi-acquisti-nr-modal-widget': ExportInterventiAcquistiNrModalWidget,
        'confronto-programmi-modal-widget': ConfrontoProgrammiModalWidget

    }
}