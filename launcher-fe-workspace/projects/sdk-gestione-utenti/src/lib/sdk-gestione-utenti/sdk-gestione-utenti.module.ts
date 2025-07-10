import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IDictionary, SdkClickModule, SdkFriendlyCaptchaModule } from '@maggioli/sdk-commons';
import {
  SdkButtonModule,
  SdkDialogModule,
  SdkFormBuilderModule,
  SdkFormModule,
  SdkMessagePanelModule,
} from '@maggioli/sdk-controls';
import { SdkTableModule } from '@maggioli/sdk-table';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';

import { InputTextareaModule } from 'primeng/inputtextarea';
import {
  SdkDettaglioCodificaAutomaticaStoreService,
} from './components/amministrazione/codifica-automatica/sdk-dettaglio-codifica-automatica/sdk-dettaglio-codifica-automatica-store.service';
import {
  SdkDettaglioCodificaAutomaticaComponent,
} from './components/amministrazione/codifica-automatica/sdk-dettaglio-codifica-automatica/sdk-dettaglio-codifica-automatica.component';
import {
  SdkListaCodificaAutomaticaComponent,
} from './components/amministrazione/codifica-automatica/sdk-lista-codifica-automatica/sdk-lista-codifica-automatica.component';
import {
  SdkModificaCodificaAutomaticaComponent,
} from './components/amministrazione/codifica-automatica/sdk-modifica-codifica-automatica/sdk-modifica-codifica-automatica.component';
import {
  SdkDettaglioConfigurazioneStoreService,
} from './components/amministrazione/configurazioni/sdk-dettaglio-configurazione/sdk-dettaglio-configurazione-store.service';
import {
  SdkDettaglioConfigurazioneComponent,
} from './components/amministrazione/configurazioni/sdk-dettaglio-configurazione/sdk-dettaglio-configurazione.component';
import {
  SdkListaConfigurazioniComponent,
} from './components/amministrazione/configurazioni/sdk-lista-configurazioni/sdk-lista-configurazioni.component';
import {
  SdkModificaConfigurazioneComponent,
} from './components/amministrazione/configurazioni/sdk-modifica-configurazione/sdk-modifica-configurazione.component';
import {
  SdkRicercaConfigurazioniComponent,
} from './components/amministrazione/configurazioni/sdk-ricerca-configurazioni/sdk-ricerca-configurazioni.component';
import {
  SdkDettaglioEventoStoreService,
} from './components/amministrazione/eventi/sdk-dettaglio-evento/sdk-dettaglio-evento-store.service';
import {
  SdkDettaglioEventoComponent,
} from './components/amministrazione/eventi/sdk-dettaglio-evento/sdk-dettaglio-evento.component';
import { SdkListaEventiComponent } from './components/amministrazione/eventi/sdk-lista-eventi/sdk-lista-eventi.component';
import {
  SdkRicercaEventiComponent,
} from './components/amministrazione/eventi/sdk-ricerca-eventi/sdk-ricerca-eventi.component';
import {
  SdkListaMnemoniciComponent,
} from './components/amministrazione/mnemonici/sdk-lista-mnemonici/sdk-lista-mnemonici.component';
import {
  SdkDettaglioPianificazioneStoreService,
} from './components/amministrazione/pianificazioni/sdk-dettaglio-pianificazione/sdk-dettaglio-pianificazione-store.service';
import {
  SdkDettaglioPianificazioneComponent,
} from './components/amministrazione/pianificazioni/sdk-dettaglio-pianificazione/sdk-dettaglio-pianificazione.component';
import {
  SdkListaPianificazioniComponent,
} from './components/amministrazione/pianificazioni/sdk-lista-pianificazioni/sdk-lista-pianificazioni.component';
import {
  SdkModificaPianificazioniComponent,
} from './components/amministrazione/pianificazioni/sdk-modifica-pianificazioni/sdk-modifica-pianificazioni.component';
import {
  SdkDettaglioServerPostaStoreService,
} from './components/amministrazione/server-posta/sdk-dettaglio-server-posta/sdk-dettaglio-server-posta-store.service';
import {
  SdkDettaglioServerPostaComponent,
} from './components/amministrazione/server-posta/sdk-dettaglio-server-posta/sdk-dettaglio-server-posta.component';
import {
  SdkImpostaPasswordPostaComponent,
} from './components/amministrazione/server-posta/sdk-imposta-password-posta/sdk-imposta-password-posta.component';
import {
  SdkInviaTestEmailComponent,
} from './components/amministrazione/server-posta/sdk-invia-test-email/sdk-invia-test-email.component';
import {
  SdkListaServerPostaComponent,
} from './components/amministrazione/server-posta/sdk-lista-server-posta/sdk-lista-server-posta.component';
import {
  SdkModificaServerPostaComponent,
} from './components/amministrazione/server-posta/sdk-modifica-server-posta/sdk-modifica-server-posta.component';
import {
  SdkNuovoServerPostaComponent,
} from './components/amministrazione/server-posta/sdk-nuovo-server-posta/sdk-nuovo-server-posta.component';
import {
  SdkDettaglioTabellatoStoreService,
} from './components/amministrazione/tabellati/sdk-dettaglio-tabellato/sdk-dettaglio-tabellato-store.service';
import {
  SdkDettaglioTabellatoComponent,
} from './components/amministrazione/tabellati/sdk-dettaglio-tabellato/sdk-dettaglio-tabellato.component';
import {
  SdkListaDettaglioTabellatoStoreService,
} from './components/amministrazione/tabellati/sdk-lista-dettaglio-tabellato/sdk-lista-dettaglio-tabellato-store.service';
import {
  SdkListaDettaglioTabellatoComponent,
} from './components/amministrazione/tabellati/sdk-lista-dettaglio-tabellato/sdk-lista-dettaglio-tabellato.component';
import {
  SdkListaTabellatiComponent,
} from './components/amministrazione/tabellati/sdk-lista-tabellati/sdk-lista-tabellati.component';
import {
  SdkModificaTabellatoComponent,
} from './components/amministrazione/tabellati/sdk-modifica-tabellato/sdk-modifica-tabellato.component';
import {
  SdkNuovoTabellatoComponent,
} from './components/amministrazione/tabellati/sdk-nuovo-tabellato/sdk-nuovo-tabellato.component';
import {
  SdkRicercaTabellatiComponent,
} from './components/amministrazione/tabellati/sdk-ricerca-tabellati/sdk-ricerca-tabellati.component';
import {
  SdkCambiaPasswordAdminUtenteComponent,
} from './components/sdk-cambia-password-admin-utente/sdk-cambia-password-admin-utente.component';
import { SdkChangePasswordFormComponent } from './components/sdk-change-password-form/sdk-change-password-form.component';
import {
  SdkChangePasswordUtenteFormComponent,
} from './components/sdk-change-password-utente-form/sdk-change-password-utente-form.component';
import { SdkCheckNuovoUtenteComponent } from './components/sdk-check-nuovo-utente/sdk-check-nuovo-utente.component';
import { SdkDettaglioUtenteStoreService } from './components/sdk-dettaglio-utente/sdk-dettaglio-utente-store.service';
import { SdkDettaglioUtenteComponent } from './components/sdk-dettaglio-utente/sdk-dettaglio-utente.component';
import {
  SdkEseguiRecuperoPasswordSuccessComponent,
} from './components/sdk-esegui-recupero-password-success/sdk-esegui-recupero-password-success.component';
import {
  SdkEseguiRecuperoPasswordComponent,
} from './components/sdk-esegui-recupero-password/sdk-esegui-recupero-password.component';
import { SdkListaUtentiComponent } from './components/sdk-lista-utenti/sdk-lista-utenti.component';
import { SdkLoginFormComponent } from './components/sdk-login-form/sdk-login-form.component';
import { SdkMioAccountComponent } from './components/sdk-mio-account/sdk-mio-account.component';
import {
  SdkModificaProfiliUtenteComponent,
} from './components/sdk-modifica-profili-utente/sdk-modifica-profili-utente.component';
import {
  SdkModificaStazioniAppaltantiUtenteComponent,
} from './components/sdk-modifica-stazioni-appaltanti-utente/sdk-modifica-stazioni-appaltanti-utente.component';
import { SdkModificaUtenteComponent } from './components/sdk-modifica-utente/sdk-modifica-utente.component';
import { SdkNuovoUtenteComponent } from './components/sdk-nuovo-utente/sdk-nuovo-utente.component';
import { SdkProfiliUtenteComponent } from './components/sdk-profili-utente/sdk-profili-utente.component';
import {
  SdkRecuperoPasswordInviataComponent,
} from './components/sdk-recupero-password-inviata/sdk-recupero-password-inviata.component';
import { SdkRecuperoPasswordComponent } from './components/sdk-recupero-password/sdk-recupero-password.component';
import { SdkRicercaUtentiComponent } from './components/sdk-ricerca-utenti/sdk-ricerca-utenti.component';
import {
  SdkRichiestaAssistenzaCompletataComponent,
} from './components/sdk-richiesta-assistenza-completata/sdk-richiesta-assistenza-completata.component';
import { SdkRichiestaAssistenzaComponent } from './components/sdk-richiesta-assistenza/sdk-richiesta-assistenza.component';
import {
  SdkStazioniAppaltantiUtenteComponent,
} from './components/sdk-stazioni-appaltanti-utente/sdk-stazioni-appaltanti-utente.component';
import {
  SdkCodificaAutomaticaProvider,
} from './providers/amministrazione/codifica-automatica/sdk-codifica-automatica.provider';
import {
  SdkDettaglioCodificaAutomaticaParamsProvider,
} from './providers/amministrazione/codifica-automatica/sdk-dettaglio-codifica-automatica-params.provider';
import {
  SdkListaCodificaAutomaticaProvider,
} from './providers/amministrazione/codifica-automatica/sdk-lista-codifica-automatica.provider';
import { SdkConfigurazioneProvider } from './providers/amministrazione/configurazioni/sdk-configurazione.provider';
import {
  SdkDettaglioConfigurazioneParamsProvider,
} from './providers/amministrazione/configurazioni/sdk-dettaglio-configurazione-params.provider';
import {
  SdkListaConfigurazioniProvider,
} from './providers/amministrazione/configurazioni/sdk-lista-configurazioni.provider';
import {
  SdkGestioneConfigurazioniSearchProvider,
} from './providers/amministrazione/configurazioni/sdk-search-configurazioni.provider';
import { SdkDettaglioEventoParamsProvider } from './providers/amministrazione/eventi/sdk-dettaglio-evento-params.provider';
import { SdkListaEventiProvider } from './providers/amministrazione/eventi/sdk-lista-eventi.provider';
import { SdkGestioneEventiSearchProvider } from './providers/amministrazione/eventi/sdk-search-eventi.provider';
import { SdkListaMnemoniciProvider } from './providers/amministrazione/mnemonici/sdk-lista-mnemonici.provider';
import {
  SdkDettaglioPianificazioneParamsProvider,
} from './providers/amministrazione/pianificazioni/sdk-dettaglio-pianificazione-params.provider';
import {
  SdkListaPianificazioniProvider,
} from './providers/amministrazione/pianificazioni/sdk-lista-pianificazioni.provider';
import { SdkPianificazioneProvider } from './providers/amministrazione/pianificazioni/sdk-pianificazione.provider';
import {
  SdkGestioneUtentiAmministrazioneMenuVisibleProvider,
} from './providers/amministrazione/sdk-gestione-utenti-amministrazione-menu-visible.provider';
import {
  SdkDettaglioServerPostaParamsProvider,
} from './providers/amministrazione/server-posta/sdk-dettaglio-server-posta-params.provider';
import {
  SdkImpostaPasswordServerPostaProvider,
} from './providers/amministrazione/server-posta/sdk-imposta-password-server-posta.provider';
import { SdkInviaTestEmailPostaProvider } from './providers/amministrazione/server-posta/sdk-invia-test-emailprovider';
import { SdkListaServerPostaProvider } from './providers/amministrazione/server-posta/sdk-lista-server-posta.provider';
import { SdkServerPostaProvider } from './providers/amministrazione/server-posta/sdk-server-posta.provider';
import {
  SdkDettaglioTabellatoParamsProvider,
} from './providers/amministrazione/tabellati/sdk-dettaglio-tabellato-params.provider';
import {
  SdkListaDettaglioTabellatoParamsProvider,
} from './providers/amministrazione/tabellati/sdk-lista-dettaglio-tabellato-params.provider';
import { SdkListaTabellatiProvider } from './providers/amministrazione/tabellati/sdk-lista-tabellati.provider';
import { SdkGestioneTabellatiSearchProvider } from './providers/amministrazione/tabellati/sdk-search-tabellati.provider';
import { SdkTabellatoProvider } from './providers/amministrazione/tabellati/sdk-tabellato.provider';
import { SdkProfiloAutocompleteProvider } from './providers/autocomplete/sdk-profilo-autocomplete.provider';
import {
  SdkUfficioIntestatarioAutocompleteProvider,
} from './providers/autocomplete/sdk-ufficio-intestatario-autocomplete.provider';
import { SdkCambiaPasswordAdminUtenteProvider } from './providers/sdk-cambia-password-admin-utente.provider';
import { SdkCheckNuovoUtenteProvider } from './providers/sdk-check-nuovo-utente.provider';
import { SdkDettaglioUtenteParamsProvider } from './providers/sdk-dettaglio-utente-params.provider';
import { SdkGestioneUtentiGestoreProfiliVisibleProvider } from './providers/sdk-gestione-utenti-gestore-profili-tab-visible.provider';
import { SdkGestioneUtentiGestoreStazioniAppaltantiVisibleProvider } from './providers/sdk-gestione-utenti-gestore-stazioni-appaltanti-tab-visible.provider';
import { SdkGestioneUtentiMenuVisibleProvider } from './providers/sdk-gestione-utenti-menu-visible.provider';
import {
  SdkGestioneUtentiRichiestaAssistenzaMenuVisibleProvider,
} from './providers/sdk-gestione-utenti-richiesta-assistenza-menu-visible.provider';
import { SdkGestioneUtentiStazioniAppaltantiVisibleProvider } from './providers/sdk-gestione-utenti-stazioni-appaltanti-tab-visible.provider';
import { SdkListaUtentiProvider } from './providers/sdk-lista-utenti.provider';
import { SdkPasswordRecoveryProvider } from './providers/sdk-password-recovery.provider';
import { SdkProfiliUtenteProvider } from './providers/sdk-profili-utente.provider';
import { SdkRichiestaAssistenzaProvider } from './providers/sdk-richiesta-assistenza.provider';
import { SdkGestioneUtentiSearchProvider } from './providers/sdk-search-utenti.provider';
import { SdkStazioniAppaltantiUtenteProvider } from './providers/sdk-stazioni-appaltanti-utente.provider';
import { SdkUtenteConnessoProvider } from './providers/sdk-utente-connesso.provider';
import { SdkUtenteProvider } from './providers/sdk-utente.provider';
import { SdkGestioneUtentiTabellatiComboProvider } from './providers/tabellati.provider';
import { SdkSearchFormConfigurazioniReducer } from './reducers/sdk-search-form-configurazioni-reducer';
import { SdkSearchFormEventiReducer } from './reducers/sdk-search-form-eventi-reducer';
import { SdkSearchFormTabellatiReducer } from './reducers/sdk-search-form-tabellati-reducer';
import { SdkSearchFormUtentiReducer } from './reducers/sdk-search-form-utenti-reducer';
import { AuthenticationService } from './services/authentication.service';
import { GestioneCodificaAutomaticaService } from './services/gestione-codifica-automatica.service';
import { GestioneConfigurazioniService } from './services/gestione-configurazioni.service';
import { GestioneEventiService } from './services/gestione-eventi.service';
import { GestioneMnemoniciService } from './services/gestione-mnemonici.service';
import { GestionePianificazioniService } from './services/gestione-pianificazioni.service';
import { GestioneServerPostaService } from './services/gestione-server-posta.service';
import { GestioneTabellatiService } from './services/gestione-tabellati.service';
import { GestioneUtentiService } from './services/gestione-utenti.service';
import { TabellatiCacheService } from './services/tabellati/tabellati-cache.service';
import { TabellatiService } from './services/tabellati/tabellati.service';
import { FileUtilsService } from './utils/file-utils.service';
import { FormBuilderUtilsService } from './utils/form-builder-utils.service';
import { GestioneUtentiValidationUtilsService } from './utils/gestione-utenti-validation-utils.service';
import { GridUtilsService } from './utils/grid-utils.service';
import { MessagesPanelUtilsService } from './utils/messages-panel-utils.service';
import { ProtectionUtilsService } from './utils/protection-utils.service';

const components: Array<any> = [
  SdkLoginFormComponent,
  SdkChangePasswordFormComponent,
  SdkRicercaUtentiComponent,
  SdkListaUtentiComponent,
  SdkNuovoUtenteComponent,
  SdkCheckNuovoUtenteComponent,
  SdkDettaglioUtenteComponent,
  SdkModificaUtenteComponent,
  SdkProfiliUtenteComponent,
  SdkModificaProfiliUtenteComponent,
  SdkStazioniAppaltantiUtenteComponent,
  SdkModificaStazioniAppaltantiUtenteComponent,
  SdkCambiaPasswordAdminUtenteComponent,
  SdkRichiestaAssistenzaComponent,
  SdkRichiestaAssistenzaCompletataComponent,
  // amministrazione
  SdkRicercaConfigurazioniComponent,
  SdkListaConfigurazioniComponent,
  SdkDettaglioConfigurazioneComponent,
  SdkModificaConfigurazioneComponent,
  SdkListaPianificazioniComponent,
  SdkDettaglioPianificazioneComponent,
  SdkModificaPianificazioniComponent,
  SdkRicercaEventiComponent,
  SdkListaEventiComponent,
  SdkDettaglioEventoComponent,
  SdkListaServerPostaComponent,
  SdkDettaglioServerPostaComponent,
  SdkNuovoServerPostaComponent,
  SdkModificaServerPostaComponent,
  SdkImpostaPasswordPostaComponent,
  SdkInviaTestEmailComponent,
  SdkRicercaTabellatiComponent,
  SdkListaTabellatiComponent,
  SdkListaDettaglioTabellatoComponent,
  SdkDettaglioTabellatoComponent,
  SdkNuovoTabellatoComponent,
  SdkModificaTabellatoComponent,
  SdkListaMnemoniciComponent,
  SdkListaCodificaAutomaticaComponent,
  SdkDettaglioCodificaAutomaticaComponent,
  SdkModificaCodificaAutomaticaComponent,
  SdkRecuperoPasswordComponent,
  SdkRecuperoPasswordInviataComponent,
  SdkEseguiRecuperoPasswordComponent,
  SdkEseguiRecuperoPasswordSuccessComponent,
  // il mio account
  SdkMioAccountComponent,
  SdkChangePasswordUtenteFormComponent
];
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    InputTextareaModule,
    ButtonModule,
    SdkMessagePanelModule,
    TranslateModule,
    SdkButtonModule,
    SdkFormModule,
    SdkFormBuilderModule,
    SdkTableModule,
    SdkDialogModule,
    TableModule,
    DropdownModule,
    SdkClickModule,
    SdkFriendlyCaptchaModule
  ],
  declarations: [
    components
  ],
  exports: [
    components
  ]
})
export class SdkGestioneUtentiModule {
  static forRoot(params?: IDictionary<string>): ModuleWithProviders<SdkGestioneUtentiModule> {
    return {
      ngModule: SdkGestioneUtentiModule,
      providers: [
        AuthenticationService,
        FormBuilderUtilsService,
        ProtectionUtilsService,
        GridUtilsService,
        TabellatiCacheService,
        TabellatiService,
        SdkGestioneUtentiTabellatiComboProvider,
        SdkGestioneUtentiSearchProvider,
        SdkSearchFormUtentiReducer,
        GestioneUtentiService,
        SdkListaUtentiProvider,
        SdkCheckNuovoUtenteProvider,
        SdkUtenteProvider,
        MessagesPanelUtilsService,
        GestioneUtentiValidationUtilsService,
        SdkDettaglioUtenteStoreService,
        SdkDettaglioUtenteParamsProvider,
        SdkUfficioIntestatarioAutocompleteProvider,
        SdkProfiloAutocompleteProvider,
        SdkGestioneUtentiMenuVisibleProvider,
        SdkProfiliUtenteProvider,
        SdkStazioniAppaltantiUtenteProvider,
        SdkCambiaPasswordAdminUtenteProvider,
        FileUtilsService,
        SdkRichiestaAssistenzaProvider,
        SdkGestioneUtentiRichiestaAssistenzaMenuVisibleProvider,
        // amministrazione
        SdkGestioneUtentiAmministrazioneMenuVisibleProvider,
        GestioneConfigurazioniService,
        SdkGestioneConfigurazioniSearchProvider,
        SdkDettaglioConfigurazioneStoreService,
        SdkListaConfigurazioniProvider,
        SdkDettaglioConfigurazioneParamsProvider,
        SdkSearchFormConfigurazioniReducer,
        SdkConfigurazioneProvider,
        GestionePianificazioniService,
        SdkListaPianificazioniProvider,
        SdkDettaglioPianificazioneStoreService,
        SdkDettaglioPianificazioneParamsProvider,
        SdkPianificazioneProvider,
        GestioneEventiService,
        SdkSearchFormEventiReducer,
        SdkGestioneEventiSearchProvider,
        SdkListaEventiProvider,
        SdkDettaglioEventoStoreService,
        SdkDettaglioEventoParamsProvider,
        GestioneServerPostaService,
        SdkListaServerPostaProvider,
        SdkDettaglioServerPostaStoreService,
        SdkDettaglioServerPostaParamsProvider,
        SdkServerPostaProvider,
        SdkImpostaPasswordServerPostaProvider,
        SdkInviaTestEmailPostaProvider,
        SdkSearchFormTabellatiReducer,
        GestioneTabellatiService,
        SdkGestioneTabellatiSearchProvider,
        SdkListaTabellatiProvider,
        SdkListaDettaglioTabellatoStoreService,
        SdkDettaglioTabellatoStoreService,
        SdkListaDettaglioTabellatoParamsProvider,
        SdkDettaglioTabellatoParamsProvider,
        SdkTabellatoProvider,
        GestioneMnemoniciService,
        SdkListaMnemoniciProvider,
        GestioneCodificaAutomaticaService,
        SdkListaCodificaAutomaticaProvider,
        SdkDettaglioCodificaAutomaticaStoreService,
        SdkDettaglioCodificaAutomaticaParamsProvider,
        SdkCodificaAutomaticaProvider,
        SdkPasswordRecoveryProvider,
        // il mio account
        SdkUtenteConnessoProvider,
        SdkGestioneUtentiStazioniAppaltantiVisibleProvider,
        SdkGestioneUtentiGestoreStazioniAppaltantiVisibleProvider,
        SdkGestioneUtentiGestoreProfiliVisibleProvider,
        {
          provide: 'localStoragePrefix',
          useValue: params != null ? params.localStoragePrefix : null
        }
      ]
    }
  }
}
