import { ProfiloInfo } from "../profilo/profilo.model";

export interface StazioneAppaltanteBaseEntry {
    codein?: string;
    codFisc?: string;
    denominazione?: string;
}


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
}
