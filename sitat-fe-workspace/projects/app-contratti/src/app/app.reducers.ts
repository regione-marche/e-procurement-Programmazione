import { Type } from '@angular/core';
import {
    ListaStazioniAppaltantiCountReducer,
    SearchFormArchivioCdcReducer,
    SearchFormArchivioImpreseReducer,
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

import { NumAppaFasiLottoReducer } from './modules/reducers/num-appa-fasi-lotto.reducer';
import { SearchFormGareReducer } from './modules/reducers/search-form-gare-reducer';
import { SearchFormReducer } from './modules/reducers/search-form-reducer';
import { StazioneAppaltanteInfoReducer } from './modules/reducers/stazione-appaltante.reducer';
import { SdkSearchFormModelliReducer } from 'projects/sdk-gestione-modelli/src/lib/sdk-gestione-modelli/reducers/sdk-search-form-modelli-reducer';

import { SearchSchedeTrasmessePcpReducer } from './modules/reducers/search-schede-trasmesse-pcp-reducer';

import { SearchFormAttiGeneraliReducer } from './modules/reducers/search-form-atti-generali.reducer';


export function reducers(): IDictionary<Type<SdkReducer>> {
    return {
        USER: SdkUserReducer,
        SAINFO: StazioneAppaltanteInfoReducer,
        searchFormD: SearchFormReducer,
        searchFormGD: SearchFormGareReducer,
        searchFormAID: SearchFormArchivioImpreseReducer,
        searchFormACDCD: SearchFormArchivioCdcReducer,
        searchFormASAD: SearchFormArchivioStazioniAppaltantiReducer,
        searchFormATECNICID: SearchFormArchivioTecniciReducer,
        currentNumAppaFasiLottoD: NumAppaFasiLottoReducer,
        STAZIONI_APPALTANTI_COUNT: ListaStazioniAppaltantiCountReducer,
        searchFormAUFFICID: SearchFormArchivioUfficiReducer,

        searchFormRPCPD: SearchSchedeTrasmessePcpReducer,

        searchFormAGD: SearchFormAttiGeneraliReducer,

        // SDK
        searchFormUtentiD: SdkSearchFormUtentiReducer,
        searchFormGestioneConfigurazioniD: SdkSearchFormConfigurazioniReducer,
        searchFormGestioneEventiD: SdkSearchFormEventiReducer,
        searchFormGestioneTabellatiD: SdkSearchFormTabellatiReducer,
        searchFormModelliD: SdkSearchFormModelliReducer
    };
}
