import { Type } from '@angular/core';
import {
    ListaStazioniAppaltantiCountReducer,
    SearchFormArchivioStazioniAppaltantiReducer,
    SearchFormArchivioTecniciReducer,
    SearchFormArchivioUfficiReducer,
} from '@maggioli/app-commons';
import { IDictionary, SdkReducer } from '@maggioli/sdk-commons';
import {
    SdkSearchFormConfigurazioniReducer,
    SdkSearchFormEventiReducer,
    SdkSearchFormTabellatiReducer,
    SdkSearchFormUtentiReducer,
} from '@maggioli/sdk-gestione-utenti';
import { SdkUserReducer } from '@maggioli/sdk-widgets';

import { StazioneAppaltanteInfoReducer } from './modules/reducers/stazione-appaltante.reducer';

export function reducers(): IDictionary<Type<SdkReducer>> {
    return {
        USER: SdkUserReducer,
        SAINFO: StazioneAppaltanteInfoReducer,
        searchFormASAD: SearchFormArchivioStazioniAppaltantiReducer,
        searchFormATECNICID: SearchFormArchivioTecniciReducer,
        searchFormAUFFICID: SearchFormArchivioUfficiReducer,
        STAZIONI_APPALTANTI_COUNT: ListaStazioniAppaltantiCountReducer,
        // SDK
        searchFormUtentiD: SdkSearchFormUtentiReducer,
        searchFormGestioneConfigurazioniD: SdkSearchFormConfigurazioniReducer,
        searchFormGestioneEventiD: SdkSearchFormEventiReducer,
        searchFormGestioneTabellatiD: SdkSearchFormTabellatiReducer
    };
}
