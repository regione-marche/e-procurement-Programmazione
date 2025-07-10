import { AssociazionePagamentiEntry } from "../gare/gare.model";

export interface FaseAccordoBonarioEntry {
    codGara?: number;
    codLotto?: number;
    dataAccordo?: Date;
    dataFineAcc?: Date;
    dataInizioAcc?: Date;
    num?: number;
    numRiserve?: number;
    oneriDerivanti?: number;
    pubblicata?: boolean;
}

export interface FaseAccordoBonarioInsertForm {
    codGara?: number;
    codLotto?: number;
    dataAccordo?: Date;
    dataFineAcc?: Date;
    dataInizioAcc?: Date;
    num?: number;
    numRiserve?: number;
    oneriDerivanti?: number;
    associazioniPagamenti?: Array<AssociazionePagamentiEntry>
}
