export interface ListaArchivioImpreseParams {
    codiceFiscale?: string;
    comune?: string;
    email?: string;
    legale?: string;
    partitaIva?: string;
    pec?: string;
    provincia?: string;
    ragioneSociale?: string;
    skip?: number;
    sort?: string;
    sortDirection?: string;
    stazioneAppaltante?: string;
    take?: number;
}

export interface RicercaAvanzataArchivioImpreseForm {
    codiceFiscale?: string;
    comune?: string;
    email?: string;
    legale?: string;
    partitaIva?: string;
    pec?: string;
    provincia?: string;
    ragioneSociale?: string;
    advancedSearch?: string;
    stazioneAppaltante?: string;
}

export interface ResponseListaImprese {
    /**
     * Dati base delle imprese
     */
    data?: Array<ImpresaBaseEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale delle imprese estraibili dalla form di ricerca
     */
    totalCount?: number;
}

export interface ImpresaBaseEntry {
    codiceFiscale?: string;
    codiceImpresa?: string;
    comune?: string;
    deletable?: boolean;
    partitaIva?: string;
    ragioneSociale?: string;
}

export interface ImpresaEntry {
    cap?: string;
    codComune?: string;
    codiceFiscale?: string;
    codiceImpresa?: string;
    codiceInail?: string;
    comune?: string;
    deletable?: boolean;
    email?: string;
    fax?: string;
    formaGiuridica?: number;
    indirizzo?: string;
    nazione?: number;
    numeroCivico?: string;
    numeroIscrizione?: string;
    numeroIscrizioneAlbo?: string;
    partitaIva?: string;
    pec?: string;
    provincia?: string;
    ragioneSociale?: string;
    rappresentante?: LegaleRappresentanteEntry;
    telefono?: string;
}

export interface LegaleRappresentanteEntry {
    cf?: string;
    cognome?: string;
    nome?: string;
}

export interface ImpresaInsertForm {
    stazioneAppaltante?: string;
    cap?: string;
    codiceFiscale?: string;
    codiceImpresa?: string;
    codiceInail?: string;
    comune?: string;
    email?: string;
    fax?: string;
    formaGiuridica?: number;
    indirizzo?: string;
    nazione?: number;
    numeroCivico?: string;
    numeroIscrizione?: string;
    numeroIscrizioneAlbo?: string;
    partitaIva?: string;
    pec?: string;
    provincia?: string;
    ragioneSociale?: string;
    rappresentante?: LegaleRappresentanteEntry;
    telefono?: string;
}
