import { Type } from '@angular/core';
import { IDictionary, SdkProvider } from '@maggioli/sdk-commons';
import { SdkGestioneUtentiTabellatiComboProvider, SdkRichiestaAssistenzaProvider } from '@maggioli/sdk-gestione-utenti';

import { LogoutProvider } from './modules/providers/logout.provider';
import { RegistrazioneUtenteProvider } from './modules/providers/registrazione-utente.provider';
import { StazioneAppaltanteAutocompleteProvider } from './modules/providers/stazione-appaltante-autocomplete.provider';

export function providers(): IDictionary<Type<SdkProvider>> {
    return {
        LAUNCHER_LOGOUT: LogoutProvider,
        LAUNCHER_STAZIONE_APPALTANTE_AUTOCOMPLETE: StazioneAppaltanteAutocompleteProvider,
        LAUNCHER_REGISTRAZIONE_UTENTE: RegistrazioneUtenteProvider,
        SDK_GESTIONE_UTENTI_RICHIESTA_ASSISTENZA: SdkRichiestaAssistenzaProvider,
        SDK_GESTIONE_UTENTI_TABELLATI_COMBO: SdkGestioneUtentiTabellatiComboProvider
    };
}
