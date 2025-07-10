export enum ModalitaInvio {
    VERIFICA = 1,
    PUBBLICA = 2
}

export enum TipoInvio {
    PRIMA_PUBBLICAZIONE = 1,
    RETIFFICHE_AGGIORNAMENTO = 2,
    ALLINEAMENTO_CANCELLAZIONE_LOGICA = -1
}

export interface PubblicazioneFaseEntry {
    codGara: string;
    codFase: string;
    codLotto: string;
    numeroProgressivo: string;
    cfStazioneAppaltante: string;
    tipoInvio: TipoInvio;
    syscon?: string;
    motivazione?: string;
    autore?: string;
    pcp?: boolean
}

export interface FlussiFase {
    autore?: string;
    codGara?: number;
    codLotto?: number;
    dataInvio?: Date;
    noteInvio?: string;
    num?: number;
    numFase?: number;
    xml?: string;
    tipoInvio?: string;
}

export interface ResponseListaPubblicazioneFase {
    data?: Array<FlussiFase>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
}

export interface ResponseRequestFase {
    data?: string;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
}

export interface PubblicaSchedaInsertForm {
    /**
     * Codice fiscale Stazione Appaltante
     */
    cfStazioneAppaltante: string;
    /**
     * CIG della gara
     */
    cig: string;
    /**
     * Codice fase
     */
    codFase: number;
    /**
     * Modalita' di invio, 1 = verifica, 2 = pubblica
     */
    modalitaInvio: number;
    /**
     * JSON dell'oggetto scheda
     */
    oggettoScheda: string;
    /**
     * Tipo di invio
     */
    tipoInvio: number;
}

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
export interface ResponsePubblicaFase {
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Esito opetrazione di pubblicazione
     */
    esito?: boolean;
    /**
     * Eventuale lista dei controlli di validazione che hanno generato errore
     */
    validate?: Array<ValidateEntry>;
}