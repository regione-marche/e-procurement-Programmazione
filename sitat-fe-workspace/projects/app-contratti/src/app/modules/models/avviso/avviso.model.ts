import { Tecnico } from '@maggioli/app-commons';

import { FlussiAvviso } from '../pubblicazioni/pubblicazione-avviso-model';

/**
 * Contenitore per il risultato dell'operazione di lista avvisi
 */
export interface ResponseListaAvvisi {
    /**
     * Dati degli avvisi
     */
    data?: Array<AvvisoEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    error?: string;
    /**
     * Risultato operazione di inserimento
     */
    esito?: boolean;
    /**
     * Numero totale degli avvisi estraibili dalla form di ricerca
     */
    totalCount?: number;
}

export interface BaseAvvisoForm {
    /**
     * Numero avviso
     */
    numeroAvviso?: string;
    /**
     * Stazione appaltante
     */
    stazioneAppaltante?: string;
}

export interface RicercaAvvisoForm extends BaseAvvisoForm {
    /**
     * Cig dell'avviso
     */
    cig?: string;
    /**
     * Limite superiore data avviso da filtrare
     */
    dataA?: string;
    /**
     * Limite inferiore data avviso da filtrare
     */
    dataDa?: string;
    /**
     * Limite superiore data scadenza da filtrare
     */
    dataScadenzaA?: string;
    /**
     * Limite inferiore data scadenza da filtrare
     */
    dataScadenzaDa?: string;
    /**
     * Descrizione
     */
    descrizione?: string;
    /**
     * syscon
     */
    syscon?: string;
    /**
     * Tipologia
     */
    tipologia?: string;
    /**
     * Determina se si arriva dalla pagina advanced search 
     */
    advancedSearch?: string;
    /**
     * cup
     */
    cup?: string;
}

export interface AvvisoDocumentInsert {
    binary?: string;
    idavviso?: number;
    titolo?: string;
    url?: string;
    tipoFile?: string;
}

export interface DocumentoAvviso {
    /**
     * Stazione appaltanta
     */
    codein?: string;
    /**
     * Id avviso
     */
    idavviso?: string;
    /**
     * Codice sistema
     */
    codsistema?: string;
    /**
     * Numero progressivo documento
     */
    numdoc?: string;
    /**
     * Titolo
     */
    titolo?: string;
    /**
     * Binario del documento
     */
    binary?: string;
    /**
     * URL del documento
     */
    url?: string;
    tipoFile?: string;
}


export interface AvvisoEntry {
    /**
     * Cig dell'avviso
     */
    cig?: string;
    /**
     * Codice dell'avviso
     */
    stazioneAppaltante?: string;
    /**
     * Codice sistema dell'avviso
     */
    codSistema?: number;
    /**
     * Cui dell'avviso
     */
    cui?: string;
    /**
     * Cup dell'avviso
     */
    cup?: string;
    /**
     * Data dell'avviso
     */
    dataAvviso?: string;
    /**
     * Data scadenza dell'avviso
     */
    dataScadenza?: string;
    /**
     * Descrizione dell'avviso
     */
    descrizione?: string;
    /**
     * Lista documenti dell'avviso
     */
    existingDocuments?: Array<ExistingAvvisoDocument>;
    /**
     * Id generato dell'avviso
     */
    idGenerato?: Number;
    /**
     * Id dell'avviso
     */
    numeroAvviso?: number;
    /**
     * Lista pubblicazioni dell'avviso
     */
    pubblicazioni?: Array<FlussiAvviso>;
    /**
     * Codice rup dell'avviso
     */
    rup?: string;
    /**
     * Rup dell'avviso
     */
    rupEntry?: Tecnico;
    /**
     * Codice del tabellato dell'avviso
     */
    tipoAvviso?: number;
    /**
     * Syscon associato all'avviso
     */
    syscon?: number;
    nominativoStazioneAppaltante?: string;
    indirizzo?: string;
    comune?: string;
    provincia?: string;
    idRicevuto?: string;
}

/**
 * Contenitore per i dati di un avviso in fase di inserimento e modifica
 */
export interface AvvisoInsertForm {
    /**
     * Cig dell'avviso
     */
    cig?: string;
    /**
     * Cod Sistema dell'avviso
     */
    codSistema?: number;
    /**
     * Cui dell'avviso
     */
    cui?: string;
    /**
     * Cup dell'avviso
     */
    cup?: string;
    /**
     * Data dell'avviso
     */
    dataAvviso?: string;
    /**
     * Data scadenza dell'avviso
     */
    dataScadenza?: string;
    /**
     * Descrizione dell'avviso
     */
    descrizione?: string;
    /**
     * Lista documentidell'avviso
     */
    documents?: Array<AvvisoDocumentInsert>;
    /**
     * Numero dell'avviso
     */
    numeroAvviso?: number;
    /**
     * Codice RUP dell'avviso
     */
    rupCod?: string;
    /**
     * Stazione appaltante dell'avviso
     */
    stazioneAppaltante?: string;
    /**
     * syscon dell'avviso
     */
    syscon?: number;
    /**
     * Codice tabellato TipoAvviso
     */
    tipologia?: number;
    indirizzo?: string;
    comune?: string;
    provincia?: string;
}

export interface AvvisoUpdateForm {
    /**
     * Cig dell'avviso
     */
    cig?: string;
    /**
     * Cod Sistema dell'avviso
     */
    codSistema?: number;
    /**
     * Cui dell'avviso
     */
    cui?: string;
    /**
     * Cup dell'avviso
     */
    cup?: string;
    /**
     * Data dell'avviso
     */
    dataAvviso?: string;
    /**
     * Data scadenza dell'avviso
     */
    dataScadenza?: string;
    /**
     * Descrizione dell'avviso
     */
    descrizione?: string;
    existingDocuments?: Array<ExistingAvvisoDocument>;
    /**
     * Lista documentidell'avviso
     */
    newDocuments?: Array<AvvisoDocumentInsert>;
    /**
     * Numero dell'avviso
     */
    numeroAvviso?: number;
    /**
     * Codice RUP dell'avviso
     */
    rupCod?: string;
    /**
     * Stazione appaltante dell'avviso
     */
    stazioneAppaltante?: string;
    /**
     * syscon dell'avviso
     */
    syscon?: number;
    /**
     * Codice tabellato TipoAvviso
     */
    tipologia?: number;
    indirizzo?: string;
    comune?: string;
    provincia?: string;
}

export interface ExistingAvvisoDocument {
    idAvviso?: number;
    numDoc?: number;
    titolo?: string;
    url?: string;
    tipoFile?: string;
}

export interface CambioSysconForm {
    idAvviso?: string;
    stazioneAppaltante?: string;
    syscon?: number;
}
