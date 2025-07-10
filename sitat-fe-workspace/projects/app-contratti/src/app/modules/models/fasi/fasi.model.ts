import { Observable } from 'rxjs';

export interface FaseEntry {
    codGara?: number;
    codLotto?: number;
    daExport?: boolean;
    deleteFisica?: boolean;
    deleteLogica?: boolean;
    fase?: number;
    progressivo?: number;
    pubblicabile?: boolean;
    pubblicata?: boolean;
    daExportNum?: number;
    recordTinvio2Uguale3?: number;
	recordTinvio2Uguale1?: number;
}

export interface TabellatoFaseEntry {
    abilitato?: boolean;
    /**
     * flag che stabilisce se il valore tabellato è ancora valido oppure no
     */
    archiviato?: number;
    /**
     * Codice tabellato
     */
    codice?: number;
    /**
     * Descrizione tabellato
     */
    descrizione?: string;
}

export interface BaseFaseService {
    deleteFase(fase: FaseEntry): Observable<any>;
}

export interface FasiInfo {
    listaFasi?: Array<FaseEntry>;
    listaStoricoFasi?: Array<number>;
    current?: boolean;
    riaggiudicabile?: boolean;
}

export interface pubblicaFaseResponse{
    internalErrors?: Array<string>;
    validationErrors?: Array<string>;
    anacErrors?: Array<string>;
    pubblicato?: boolean;
}
