export interface ListaArchivioCdcParams {
    codiceCentro?: string;
    skip?: number;
    sort?: string;
    sortDirection?: string;
    stazioneAppaltante?: string;
    take?: number;
}

export interface RicercaAvanzataArchivioCdcForm {
    codiceCentro?: string;
    stazioneAppaltante?: string;
}

export interface ResponseListaCentriDiCosto {
    /**
     * Dati base delle imprese
     */
    data?: Array<CentroDiCostoEntry>;
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

export interface CentroDiCostoEntry {
    codiceCentro?: string;
    denominazione?: string;
    id?: number;
    note?: string;
    stazioneAppaltante?: string;
    tipologia?: number;
    nominativoStazioneAppaltante?: string;
    deletable: boolean;
}

export interface CentroDiCostoInsertForm {
    codiceCentro?: string;
    denominazione?: string;
    id?: number;
    note?: string;
    stazioneAppaltante?: string;
    tipologia?: number;
}
