import { ProfiloInfo } from '../profilo/profilo.model';

export interface StazioneAppaltanteBaseInfo {
    /**
     * Codice della stazione appaltante
     */
    codice?: string;

    /**
     * Nome della stazione appaltante
     */
    nome?: string;
}

export interface StazioneAppaltanteInfo extends StazioneAppaltanteBaseInfo {
    /**
     * CAP stazione appaltante
     */
    cap?: string;
    /**
     * CF stazione appaltante
     */
    codFiscale?: string;
    /**
     * Cod istat comune stazione appaltante
     */
    codistat?: string;
    /**
     * Comune stazione appaltante
     */
    comune?: string;
    /**
     * Email stazione appaltante
     */
    email?: string;
    /**
     * Fax stazione appaltante
     */
    fax?: string;
    /**
     * Indirizzo stazione appaltante
     */
    indirizzo?: string;
    /**
     * Numero civico stazione appaltante
     */
    numCivico?: string;
    /**
     * Lista dei profili abilitati per l'utente e la stazione appaltante
     */
    profili?: Array<ProfiloInfo>;
    /**
     * Provincia stazione appaltante
     */
    provincia?: string;
    /**
     * Telefono stazione appaltante
     */
    telefono?: string;

    codAusa?: string;
}

export interface ListaStazioniAppaltantiRequest {
    citta?: string;
    codFisc?: string;
    codiceAnagrafico?: string;
    denominazione?: string;
    indirizzo?: string;
    provincia?: string;
    codAusa?: string;
}

export interface ResponseListaStazioneAppaltante { 
    /**
     * Dati base dei centrri di costo
     */
    data?: Array<StazioneAppaltanteListaEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale dei centri di costo estraibili dalla form di ricerca
     */
    totalCount?: number;
}

export interface StazioneAppaltanteListaEntry { 
    codFisc?: string;
    codice?: string;
    deletable?: boolean;
    denominazione?: string;
}

export interface StazioneAppaltanteEntry { 
    cap?: string;
    cfAnac?: string;
    codFisc?: string;
    codIstat?: string;
    codein?: string;
    denominazione?: string;
    email?: string;
    fax?: string;
    idAmministrazione?: number;
    indirizzo?: string;
    isCuc?: string;
    nCivico?: string;
    telefono?: string;
    tipologia?: number;
    urlProfiloC?: string;
    codausa?: string;
}

export interface StazioneAppaltanteInsertForm { 
    cap?: string;
    cfAnac?: string;
    codFisc?: string;
    codIstat?: string;
    codein?: string;
    denominazione?: string;
    email?: string;
    fax?: string;
    idAmministrazione?: number;
    indirizzo?: string;
    isCuc?: string;
    nCivico?: string;
    telefono?: string;
    tipologia?: number;
    urlProfiloC?: string;
    codausa?: string;
}

export interface StazioneAppaltanteUpdateForm { 
    cap?: string;
    cfAnac?: string;
    codFisc?: string;
    codIstat?: string;
    codein?: string;
    denominazione?: string;
    email?: string;
    fax?: string;
    idAmministrazione?: number;
    indirizzo?: string;
    isCuc?: string;
    nCivico?: string;
    telefono?: string;
    tipologia?: number;
    urlProfiloC?: string;
    codausa?: string;
}

export interface RicercaAvanzataArchivioStazioniAppaltantiForm {
    codiceAnagrafico?: string;
    denominazione?: string;
    codiceFiscale?: string;
    indirizzo?: string;
    provincia?: string;
    citta?: string;
    codAusa?: string;
}

export interface StazioneAppaltanteBaseEntry { 
    codein?: string;
    codFisc?: string;
    denominazione?: string;
}