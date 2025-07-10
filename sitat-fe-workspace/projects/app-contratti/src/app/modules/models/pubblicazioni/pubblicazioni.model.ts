/**
 * Risultato della pubblicazione
 */
export interface PubblicazioneResult {
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    error?: string;
    /**
     * Risultato operazione di inserimento
     */
    esito?: boolean;
    /**
     * Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive
     */
    id?: number;
    /**
     * Eventuale lista dei controlli di validazione che hanno generato errore
     */
    validate?: Array<ValidateEntry>;
}

/**
 * Errore di validazione
 */
export interface ValidateEntry {
    /**
     * Messaggio di errore
     */
    messaggio?: string;
    /**
     * Nome campo validato
     */
    nome?: string;
    /**
     * Tipo di messaggio, 'E' = errore bloccante, 'W' = avviso
     */
    tipo?: string;
}

/**
 * Risultato della pubblicazione
 */
export interface PubblicazioneAttoResult {
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    error?: string;
    /**
     * Esito opetrazione di pubblicazione
     */
    esito?: boolean;
    /**
     * Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive
     */
    idExArt29?: number;
    /**
     * Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive
     */
    idGara?: number;
    /**
     * Eventuale lista dei controlli di validazione che hanno generato errore
     */
    validate?: Array<ValidateEntry>;
}