import { AssociazionePagamentiEntry } from "../gare/gare.model";

export interface FaseConclusioneAffidamentiDirettiEntry {
    codGara?: number;
    codLotto?: number;   
    dataVerbInizio?: Date;
	dataUltimazione?: Date;
    importoCertificato?: number;
    num?: number;    
    pubblicata?: boolean;
}

export interface FaseConclusioneAffidamentiDirettiInsertForm {
    codGara?: number;
    codLotto?: number;   
    dataVerbInizio?: Date;
	dataUltimazione?: Date;
    importoCertificato?: number;
    num?: number;    
    pubblicata?: boolean;
    associazioniPagamenti?: Array<AssociazionePagamentiEntry>
}