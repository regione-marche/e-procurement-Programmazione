import { PubblicaGaraEntry } from './pubblicazione-gara.model';

/**
 * Contenitore per i dati di pubblicazione di un atto
 */
export interface PubblicaAttoEntry {
    /**
     * Aggiudicatari obbligatori solo per Avviso di aggiudicazione o affidamento
     */
    aggiudicatari?: Array<AggiudicatarioEntry>;
    /**
     * Lista di CIG associati all'atto
     */
    cigAssociati?: Array<string>;
    /**
     * Data di aggiudicazione definitiva
     */
    dataAggiudicazione?: string;
    /**
     * Data decreto
     */
    dataDecreto?: string;
    /**
     * Data del provvedimento
     */
    dataProvvedimento?: string;
    /**
     * Data pubblicazione
     */
    dataPubblicazione?: string;
    /**
     * Data scadenza
     */
    dataScadenza?: string;
    /**
     * Data di stipula
     */
    dataStipula?: string;
    /**
     * Documenti allegati alla pubblicazione
     */
    documenti: Array<AllegatoAttiEntry>;
    /**
     * Eventuale specificazione
     */
    eventualeSpecificazione?: string;
    /**
     * Dati gara da pubblicare
     */
    gara: PubblicaGaraEntry;
    /**
     * Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive
     */
    idRicevuto?: number;
    /**
     * Importo di aggiudicazione
     */
    importoAggiudicazione?: number;
    /**
     * Numero provvedimento
     */
    numeroProvvedimento?: string;
    /**
     * Numero repertorio
     */
    numeroRepertorio?: string;
    /**
     * Offerta in aumento %
     */
    offertaAumento?: number;
    /**
     * Data pubblicazione atto su sito SCP (valorizzato solo nel metodo /Atti/DettaglioAtto)
     */
    primaPubblicazioneSCP?: string;
    /**
     * Ribasso di aggiudicazione %
     */
    ribassoAggiudicazione?: number;
    /**
     * Tipologia pubblicazione
     */
    tipoDocumento: number;
    /**
     * Utima modifica pubblicazione atto su sito SCP (valorizzato solo nel metodo /Atti/DettaglioAtto)
     */
    ultimaModificaSCP?: string;
    /**
     * URL profilo del committente
     */
    urlCommittente?: string;
    /**
     * Autore dell'atto
     */
    autore?: string;
    
}

/**
 * Dati relativi ad un aggiudicatario
 */
export interface AggiudicatarioEntry {
    /**
     * Numero raggruppamento
     */
    idGruppo?: number;
    /**
     * Dati dell'Operatore economico
     */
    impresa: ImpresaPubbEntry;
    /**
     * Ruolo nell'associazione
     */
    ruolo?: number;
    /**
     * Tipologia del soggetto aggiudicatario
     */
    tipoAggiudicatario: number;
}

/**
 * Nome e numero allegato
 */
export interface AllegatoAttiEntry {
    /**
     * Titolo del documento
     */
    titolo: string;
    /**
     * URL documentazione
     */
    url?: string;
    /**
     * Byte array del file in chiaro
     */
    file?: Array<any>;
    /**
     * Stringa base64 codificata del file
     */
    binary?: string;
}

/**
 * Contenitore per i dati generali dell'Operatore economico
 */
export interface ImpresaPubbEntry {
    /**
     * *Codice di avviamento postale
     */
    cap?: string;
    /**
     * Codice fiscale
     */
    codiceFiscale: string;
    /**
     * *FAX
     */
    fax?: string;
    /**
     * Forma giuridica
     */
    formaGiuridica?: number;
    /**
     * *Indirizzo (via/piazza/corso) dell'impresa
     */
    indirizzo?: string;
    /**
     * *Localita' dell'impresa
     */
    localita?: string;
    /**
     * Nazionalit� (Codice ISO 3166-1 alpha-2)
     */
    nazione?: string;
    /**
     * *Numero dell'iscrizione al Registro Imprese
     */
    numeroCCIAA?: string;
    /**
     * *Numero civico dell'impresa
     */
    numeroCivico?: string;
    /**
     * Partita I.V.A. o V.A.T.
     */
    partitaIva?: string;
    /**
     * Provincia dell'impresa
     */
    provincia?: string;
    /**
     * Ragione sociale dell'impresa
     */
    ragioneSociale: string;
    /**
     * *Telefono
     */
    telefono?: string;
}

export interface FlussiAtto {
    autore?: string;
    codGara?: number;
    dataInvio?: Date;
    noteInvio?: string;
    numAtto?: number;
    xml?: string;
    idFlusso: string;
}