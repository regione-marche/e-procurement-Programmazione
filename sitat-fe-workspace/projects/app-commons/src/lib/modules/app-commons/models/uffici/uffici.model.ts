export interface Ufficio {
    denominazione?: string;
    id?: string;
    stazioneAppaltante?: string;
    deletable?: boolean;
    centroCosto?: string;
}

export interface UfficioInsertForm {
    denominazione?: string;
    id?: string;
    stazioneAppaltante?: string;
    centroCosto?: string;
}

export interface ListaUfficiRequest {
    searchString?: string;
    stazioneAppaltante: string;
}

export interface ResponseListaUfficiPaginata {
    /**
     * Dati dei tecnici
     */
    data?: Array<Ufficio>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale degli uffici estraibili dalla form di ricerca
     */
    totalCount?: number;
}
