import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { SdkClickModule } from '@maggioli/sdk-commons';
import {
  SdkButtonModule,
  SdkDateModule,
  SdkDialogModule,
  SdkFormBuilderModule,
  SdkFormModule,
  SdkMessagePanelModule,
  SdkModalModule,
  SdkSearchModule
} from '@maggioli/sdk-controls';
import { SdkTableModule } from '@maggioli/sdk-table';
import { TranslateModule } from '@ngx-translate/core';
import { InputTextModule } from 'primeng/inputtext';
import { TableModule } from 'primeng/table';

import { AppInfoModalModalWidget } from './components/app-info-modal/app-info-modal.widget';
import { DettaglioCdcStoreService } from './components/archivio/archivio-centri-di-costo/dettaglio-cdc-store.service';
import {
  DettaglioCdcSectionWidget
} from './components/archivio/archivio-centri-di-costo/dettaglio/dettaglio-cdc-section.widget';
import {
  ListaArchivioCdcSectionWidget
} from './components/archivio/archivio-centri-di-costo/lista/lista-archivio-cdc-section.widget';
import {
  ModificaCdcSectionWidget
} from './components/archivio/archivio-centri-di-costo/modifica/modifica-cdc-section.widget';
import { NuovoCdcSectionWidget } from './components/archivio/archivio-centri-di-costo/nuovo/nuovo-cdc-section.widget';
import {
  RicercaAvanzataArchivioCdcSectionWidget
} from './components/archivio/archivio-centri-di-costo/ricerca-avanzata/ricerca-avanzata-archivio-cdc-section.widget';
import { DettaglioImpresaStoreService } from './components/archivio/archivio-imprese/dettaglio-impresa-store.service';
import {
  DettaglioImpresaSectionWidget
} from './components/archivio/archivio-imprese/dettaglio/dettaglio-impresa-section.widget';
import {
  ListaArchivioImpreseSectionWidget
} from './components/archivio/archivio-imprese/lista/lista-archivio-imprese-section.widget';
import {
  ModificaImpresaSectionWidget
} from './components/archivio/archivio-imprese/modifica/modifica-impresa-section.widget';
import { NuovaImpresaSectionWidget } from './components/archivio/archivio-imprese/nuova/nuova-impresa-section.widget';
import {
  RicercaAvanzataArchivioImpreseSectionWidget
} from './components/archivio/archivio-imprese/ricerca-avanzata/ricerca-avanzata-archivio-imprese-section.widget';
import {
  DettaglioStazioneAppaltanteStoreService
} from './components/archivio/archivio-stazioni-appaltanti/dettaglio-stazione-appaltante-store.service';
import {
  DettaglioStazioneAppaltanteSectionWidget
} from './components/archivio/archivio-stazioni-appaltanti/dettaglio/dettaglio-stazione-appaltante-section.widget';
import {
  ListaArchivioStazioniAppaltantiSectionWidget
} from './components/archivio/archivio-stazioni-appaltanti/lista/lista-archivio-stazioni-appaltanti-section.widget';
import {
  ModificaUtentiStazioneAppaltanteComponent
} from './components/archivio/archivio-stazioni-appaltanti/modifica-utenti-stazione-appaltante/modifica-utenti-stazione-appaltante.component';
import {
  ModificaStazioneAppaltanteSectionWidget
} from './components/archivio/archivio-stazioni-appaltanti/modifica/modifica-stazione-appaltante-section.widget';
import {
  NuovaStazioneAppaltanteSectionWidget
} from './components/archivio/archivio-stazioni-appaltanti/nuova/nuova-stazione-appaltante-section.widget';
import {
  RicercaAvanzataArchivioStazioniAppaltantiSectionWidget
} from './components/archivio/archivio-stazioni-appaltanti/ricerca-avanzata/ricerca-avanzata-archivio-stazioni-appaltanti-section.widget';
import {
  UtenteStazioneAppaltanteStoreService
} from './components/archivio/archivio-stazioni-appaltanti/utenti-stazione-appaltante/utente-stazione-appaltante-store.service';
import {
  UtentiStazioneAppaltanteComponent
} from './components/archivio/archivio-stazioni-appaltanti/utenti-stazione-appaltante/utenti-stazione-appaltante.component';
import { DettaglioTecnicoStoreService } from './components/archivio/archivio-tecnici/dettaglio-tecnico-store.service';
import {
  DettaglioTecnicoSectionWidget
} from './components/archivio/archivio-tecnici/dettaglio/dettaglio-tecnico-section.widget';
import {
  ListaArchivioTecniciSectionWidget
} from './components/archivio/archivio-tecnici/lista/lista-archivio-tecnici-section.widget';
import {
  ModificaTecnicoSectionWidget
} from './components/archivio/archivio-tecnici/modifica/modifica-tecnico-section.widget';
import { NuovoTecnicoSectionWidget } from './components/archivio/archivio-tecnici/nuovo/nuovo-tecnico-section.widget';
import {
  RicercaAvanzataArchivioTecniciSectionWidget
} from './components/archivio/archivio-tecnici/ricerca-avanzata/ricerca-avanzata-archivio-tecnici-section.widget';
import { DettaglioUfficioStoreService } from './components/archivio/archivio-uffici/dettaglio-ufficio-store.service';
import {
  DettaglioUfficioSectionWidget
} from './components/archivio/archivio-uffici/dettaglio/dettaglio-ufficio-section.widget';
import {
  ListaArchivioUfficiSectionWidget
} from './components/archivio/archivio-uffici/lista/lista-archivio-uffici-section.widget';
import {
  ModificaUfficioSectionWidget
} from './components/archivio/archivio-uffici/modifica/modifica-ufficio-section.widget';
import { NuovoUfficioSectionWidget } from './components/archivio/archivio-uffici/nuovo/nuovo-ufficio-section.widget';
import {
  RicercaAvanzataArchivioUfficiSectionWidget
} from './components/archivio/archivio-uffici/ricerca-avanzata/ricerca-avanzata-archivio-uffici-section.widget';
import { CheckPubblicazioneSectionWidget } from './components/check-pubblicazione/check-pubblicazione-section.widget';
import { ConfigurazioneUtenteSectionWidget } from './components/configurazione-utente/configurazione-utente-section.widget';
import { CpvModalWidget } from './components/cpv-modal/cpv-modal.widget';
import {
  DettaglioPubblicazioneModalWidget
} from './components/dettaglio-pubblicazione-modal/dettaglio-pubblicazione-modal.widget';
import { EsempioModalWidget } from './components/esempio-modal/esempio-modal.widget';
import { MessaggiAvvisiModalWidget } from './components/messaggi-avvisi-modal/messaggi-avvisi-modal.widget';
import { MessaggiAvvisiOverlayWidget } from './components/messaggi-avvisi-overlay/messaggi-avvisi-overlay.widget';
import { MnemonicoModalWidget } from './components/mnemonico-modal/mnemonico-modal.widget';
import {
  ModificaConfigurazioneUtenteSectionWidget
} from './components/modifica-configurazione-utente/modifica-configurazione-utente-section.widget';
import { NutsModalWidget } from './components/nuts-modal/nuts-modal.widget';
import { PubblicaTuttoModalConfig } from './components/pubblica-tutto/pubblica-tutto-modal.widget';
import { RedirectSectionWidget } from './components/redirect/redirect-section.widget';
import { RupModalWidget } from './components/rup-modal/rup-modal.widget';
import { RupRWModalWidget } from './components/rup-rw-modal/rup-rw-modal.widget';
import { StazioneAppaltanteModalWidget } from './components/stazione-appaltante-modal/stazione-appaltante-modal.widget';
import { TrasferimentoModalWidget } from './components/trasferimento-modal/trasferimento-modal.widget';
import { UffModalWidget } from './components/uff-modal/uff-modal.widget';
import { BackwardGuard } from './guards/backward.guard';
import { AdvancedSearchCdcProvider } from './providers/advanced-search/advanced-search-cdc.provider';
import { AdvancedSearchImpreseProvider } from './providers/advanced-search/advanced-search-imprese.provider';
import { AdvancedSearchTecnicoProvider } from './providers/advanced-search/advanced-search-tecnico.provider';
import { AdvancedSearchUfficiProvider } from './providers/advanced-search/advanced-search-uffici.provider';
import { ApplicazioniVisibleProvider } from './providers/applicazioni/applicazioni-visible.provider';
import {
  AdvancedSearchArchivioCdcProvider
} from './providers/archivio/archivio-centri-di-costo/advanced-search-archivio-cdc.provider';
import { CdcParamsProvider } from './providers/archivio/archivio-centri-di-costo/cdc-params.provider';
import { CdcProvider } from './providers/archivio/archivio-centri-di-costo/cdc.provider';
import { ListaArchivioCdcProvider } from './providers/archivio/archivio-centri-di-costo/lista-archivio-cdc.provider';
import {
  AdvancedSearchArchivioImpreseProvider
} from './providers/archivio/archivio-imprese/advanced-search-archivio-imprese.provider';
import { ImpresaParamsProvider } from './providers/archivio/archivio-imprese/impresa-params.provider';
import { ImpresaProvider } from './providers/archivio/archivio-imprese/impresa.provider';
import { ListaArchivioImpreseProvider } from './providers/archivio/archivio-imprese/lista-archivio-imprese.provider';
import {
  AdvancedSearchArchivioStazioniAppaltantiProvider
} from './providers/archivio/archivio-stazioni-appaltanti/advanced-search-archivio-stazioni-appaltanti.provider';
import {
  ArchivioStazioniAppaltantiMenuVisibleProvider
} from './providers/archivio/archivio-stazioni-appaltanti/archivio-stazioni-appaltanti-menu-visible.provider';
import {
  ArchivioStazioniAppaltantiProvider
} from './providers/archivio/archivio-stazioni-appaltanti/archivio-stazioni-appaltanti.provider';
import {
  StazioneAppaltanteParamsProvider
} from './providers/archivio/archivio-stazioni-appaltanti/stazione-appaltante-params.provider';
import {
  UtentiStazioneAppaltanteProvider
} from './providers/archivio/archivio-stazioni-appaltanti/utenti-stazione-appaltante.provider';
import {
  AdvancedSearchArchivioTecniciProvider
} from './providers/archivio/archivio-tecnici/advanced-search-archivio-tecnici.provider';
import { ListaTecniciProvider } from './providers/archivio/archivio-tecnici/lista-tecnici.provider';
import { TecnicoParamsProvider } from './providers/archivio/archivio-tecnici/tecnico-params.provider';
import { TecnicoProvider } from './providers/archivio/archivio-tecnici/tecnico.provider';
import {
  AdvancedSearchArchivioUfficiProvider
} from './providers/archivio/archivio-uffici/advanced-search-archivio-uffici.provider';
import { UfficioParamsProvider } from './providers/archivio/archivio-uffici/ufficio-params.provider';
import { UfficioProvider } from './providers/archivio/archivio-uffici/ufficio.provider';
import { ChangeUserPasswordVisibleProvider } from './providers/change-user-password/change-user-password-visible.provider';
import { ChangeUserPasswordProvider } from './providers/change-user-password/change-user-password.provider';
import { ManualiUrlProvider } from './providers/manuali/manuali-url.provider';
import { MessaggiAvvisiStatusProvider } from './providers/messaggi-avvisi/messaggi-avvisi-status.provider';
import { MessaggiAvvisiAdminVisibleProvider } from './providers/messaggi-avvisi/messaggi-avvisi-admin-visible.provider';
import { MessaggiAvvisiProvider } from './providers/messaggi-avvisi/messaggi-avvisi.provider';
import { MioAccountVisibleProvider } from './providers/mio-account/mio-account-visible.provider';
import { MioAccountProvider } from './providers/mio-account/mio-account.provider';
import { ProfiliVisibleProvider } from './providers/profili/profili-visible.provider';
import {
  StazioneAppaltanteUtentiVisibleProvider
} from './providers/stazioni-appaltanti/stazione-appaltante-utenti-visible.provider';
import {
  StazioniAppaltantiCountMenuVisibleProvider
} from './providers/stazioni-appaltanti/stazioni-appaltanti-count-menu-visible.provider';
import {
  UtenteStazioneAppaltanteParamsProvider
} from './providers/stazioni-appaltanti/utente-stazione-appaltante-params.provider';
import { UtenteStazioneAppaltanteProvider } from './providers/stazioni-appaltanti/utente-stazione-appaltante.provider';
import { TabellatiComboProvider } from './providers/tabellati/tabellati-combo.provider';
import { UserOptionsAutocompleteProvider } from './providers/user/user-options-autocomplete.provider';
import { ListaStazioniAppaltantiCountReducer } from './reducers/lista-stazioni-appaltanti-count.reducer';
import { SearchFormArchivioCdcReducer } from './reducers/search-form-archivio-cdc-reducer';
import { SearchFormArchivioImpreseReducer } from './reducers/search-form-archivio-imprese-reducer';
import { SearchFormArchivioStazioniAppaltantiReducer } from './reducers/search-form-archivio-stazioni-appaltanti-reducer';
import { SearchFormArchivioTecniciReducer } from './reducers/search-form-archivio-tecnici-reducer';
import { SearchFormArchivioUfficiReducer } from './reducers/search-form-archivio-uffici-reducer';
import { ArchivioCentriDiCostoService } from './services/archivio/archivio-centri-di-costo.service';
import { ArchivioImpreseService } from './services/archivio/archivio-imprese.service';
import { ConfigurazioneUtenteService } from './services/configurazione-utente/configurazione-utente.service';
import { HttpRequestHelper } from './services/http/http-request-helper.service';
import { TabellatiCacheService } from './services/tabellati/tabellati-cache.service';
import { TabellatiService } from './services/tabellati/tabellati.service';
import { UserService } from './services/user/user.service';
import { AbilitazioniUtilsService } from './services/utils/abilitazioni-utils.service';
import { CommonValidationUtilsService } from './services/utils/common-validation-utils.service';
import { FileUtilsService } from './services/utils/file-utils.service';
import { FormBuilderUtilsService } from './services/utils/form-builder-utils.service';
import { GridUtilsService } from './services/utils/grid-utils.service';
import { MessagesPanelUtilsService } from './services/utils/messages-panel-utils.service';
import { ProtectionUtilsService } from './services/utils/protection-utils.service';
import { TabellatiUtils } from './services/utils/tabellati-utils.service';
import { ImportInterventiModalWidget } from 'projects/app-programmi/src/app/modules/layout/components/business/interventi/import-modal/import-interventi-modal.widget';
import { ReportIndicatoriModalWidget } from './components/report-indicatori-modal/report-indicatori-modal.widget';
import { MatriceAttiModalWidget } from 'projects/app-contratti/src/app/modules/layout/components/business/atti/matrice/matrice-atti-modal-section.widget';
import { AssegnaCambiaDelegatoModalWidget } from './components/assegna-cambia-delegato-modal/assegna-cambia-delegato-modal.widget';
import { RiallineaAnacModalWidget } from './components/riallinea-anac-modal-widget/riallinea-anac-modal.widget';
import { AppInfoService } from './services/appinfo/app-info.service';
import { SdkUltimiAccessiProvider, UltimiAccessiService } from '@maggioli/sdk-widgets';
import { UltimiAccessiVisibleProvider } from './app-commons.exports';
import { UltimiAccessiProvider } from './providers/ultimi-accessi/ultimi-accessi.provider';
import { MessaggiAvvisiVisibleProvider } from './providers/messaggi-avvisi/messaggi-avvisi-visible.provider';

const archiviComponents: Array<any> = [
  ListaArchivioTecniciSectionWidget,
  NuovoTecnicoSectionWidget,
  DettaglioTecnicoSectionWidget,
  ModificaTecnicoSectionWidget,
  RicercaAvanzataArchivioTecniciSectionWidget,
  RicercaAvanzataArchivioImpreseSectionWidget,
  ListaArchivioImpreseSectionWidget,
  DettaglioImpresaSectionWidget,
  NuovaImpresaSectionWidget,
  ModificaImpresaSectionWidget,
  RicercaAvanzataArchivioCdcSectionWidget,
  ListaArchivioCdcSectionWidget,
  NuovoCdcSectionWidget,
  DettaglioCdcSectionWidget,
  ModificaCdcSectionWidget,
  RicercaAvanzataArchivioStazioniAppaltantiSectionWidget,
  ListaArchivioStazioniAppaltantiSectionWidget,
  NuovaStazioneAppaltanteSectionWidget,
  DettaglioStazioneAppaltanteSectionWidget,
  ModificaStazioneAppaltanteSectionWidget,
  UtentiStazioneAppaltanteComponent,
  ModificaUtentiStazioneAppaltanteComponent,
  RicercaAvanzataArchivioUfficiSectionWidget,
  NuovoUfficioSectionWidget,
  ModificaUfficioSectionWidget,
  ListaArchivioUfficiSectionWidget,
  DettaglioUfficioSectionWidget
];

const archiviProviders: Array<any> = [
  DettaglioCdcStoreService,
  DettaglioImpresaStoreService,
  DettaglioStazioneAppaltanteStoreService,
  DettaglioTecnicoStoreService,
  AdvancedSearchArchivioCdcProvider,
  CdcParamsProvider,
  CdcProvider,
  ListaArchivioCdcProvider,
  AdvancedSearchArchivioImpreseProvider,
  ImpresaParamsProvider,
  ImpresaProvider,
  ListaArchivioImpreseProvider,
  AdvancedSearchArchivioStazioniAppaltantiProvider,
  ArchivioStazioniAppaltantiProvider,
  StazioneAppaltanteParamsProvider,
  AdvancedSearchArchivioTecniciProvider,
  ListaTecniciProvider,
  TecnicoParamsProvider,
  TecnicoProvider,
  SearchFormArchivioCdcReducer,
  SearchFormArchivioImpreseReducer,
  SearchFormArchivioStazioniAppaltantiReducer,
  SearchFormArchivioTecniciReducer,
  DettaglioUfficioStoreService,
  SearchFormArchivioUfficiReducer,
  AdvancedSearchArchivioUfficiProvider,
  UfficioParamsProvider,
  UfficioProvider,
  ArchivioStazioniAppaltantiMenuVisibleProvider
];

@NgModule({
  declarations: [
    RupModalWidget,
    DettaglioPubblicazioneModalWidget,
    StazioneAppaltanteModalWidget,
    CheckPubblicazioneSectionWidget,
    ConfigurazioneUtenteSectionWidget,
    ModificaConfigurazioneUtenteSectionWidget,
    MnemonicoModalWidget,
    MessaggiAvvisiModalWidget,
    UffModalWidget,
    CpvModalWidget,
    ImportInterventiModalWidget,
    NutsModalWidget,
    MessaggiAvvisiOverlayWidget,
    RupRWModalWidget,
    TrasferimentoModalWidget,
    EsempioModalWidget,
    AppInfoModalModalWidget,
    RedirectSectionWidget,
    archiviComponents,
    PubblicaTuttoModalConfig,
    ReportIndicatoriModalWidget,
	  MatriceAttiModalWidget,
    AssegnaCambiaDelegatoModalWidget,
    RiallineaAnacModalWidget
  ],
  imports: [
    CommonModule,
    SdkMessagePanelModule,
    SdkFormBuilderModule,
    SdkButtonModule,
    SdkDateModule,
    SdkFormModule,
    SdkSearchModule,
    TranslateModule,
    SdkTableModule,
    SdkDialogModule,
    SdkModalModule,
    TableModule,
    InputTextModule,
    SdkClickModule
  ],
  exports: [
    RupModalWidget,
    ConfigurazioneUtenteSectionWidget,
    ModificaConfigurazioneUtenteSectionWidget,
    DettaglioPubblicazioneModalWidget,
    StazioneAppaltanteModalWidget,
    CheckPubblicazioneSectionWidget,
    MnemonicoModalWidget,
    MessaggiAvvisiModalWidget,
    UffModalWidget,
    CpvModalWidget,
    NutsModalWidget,
    MessaggiAvvisiOverlayWidget,
    RupRWModalWidget,
    TrasferimentoModalWidget,
    EsempioModalWidget,
    AppInfoModalModalWidget,
    RedirectSectionWidget,
    archiviComponents,
    PubblicaTuttoModalConfig,
    ReportIndicatoriModalWidget,
    AssegnaCambiaDelegatoModalWidget,
    RiallineaAnacModalWidget
  ]
})
export class AppCommonsModule {
  static forRoot(): ModuleWithProviders<AppCommonsModule> {
    return {
      ngModule: AppCommonsModule,
      providers: [
        GridUtilsService,
        MessagesPanelUtilsService,
        ProtectionUtilsService,
        UserService,
        TabellatiService,
        FormBuilderUtilsService,
        AbilitazioniUtilsService,
        BackwardGuard,
        TabellatiCacheService,
        HttpRequestHelper,
        ArchivioImpreseService,
        ConfigurazioneUtenteService,
        ArchivioCentriDiCostoService,
        TabellatiUtils,
        TabellatiComboProvider,
        MessaggiAvvisiAdminVisibleProvider,
        MessaggiAvvisiVisibleProvider,
        MessaggiAvvisiProvider,
        UserOptionsAutocompleteProvider,
        FileUtilsService,
        AdvancedSearchTecnicoProvider,
        AdvancedSearchImpreseProvider,
        AdvancedSearchCdcProvider,
        AdvancedSearchUfficiProvider,
        ProfiliVisibleProvider,
        ApplicazioniVisibleProvider,
        UtentiStazioneAppaltanteProvider,
        StazioniAppaltantiCountMenuVisibleProvider,
        ListaStazioniAppaltantiCountReducer,
        CommonValidationUtilsService,
        archiviProviders,
        StazioneAppaltanteUtentiVisibleProvider,
        UtenteStazioneAppaltanteStoreService,
        UtenteStazioneAppaltanteParamsProvider,
        UtenteStazioneAppaltanteProvider,
        MioAccountProvider,
        MioAccountVisibleProvider,
        MessaggiAvvisiStatusProvider,
        ChangeUserPasswordVisibleProvider,
        ChangeUserPasswordProvider,
        UltimiAccessiVisibleProvider,
        SdkUltimiAccessiProvider,
        UltimiAccessiProvider,
        ManualiUrlProvider,
        AppInfoService,
        UltimiAccessiService
      ]
    }
  }
}
