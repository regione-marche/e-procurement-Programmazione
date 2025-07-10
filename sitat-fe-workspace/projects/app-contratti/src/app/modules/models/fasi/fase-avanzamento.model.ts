import { AssociazionePagamentiEntry } from "../gare/gare.model";

export interface FaseAvanzamentoEntry { 
    codGara?: number;
    codLotto?: number;
    dataAnticipazione?: Date;
    dataCertificato?: Date;
    dataRaggiungimento?: Date;
    denomAvanzamento?: string;
    flagPagamento?: number;
    flagRitardo?: string;
    importoAnticipazione?: number;
    importoCertificato?: number;
    importoSal?: number;
    num?: number;
    numGiorniProroga?: number;
    numGiorniScost?: number;
    pubblicata?: boolean;
    subappalti?: string;
}

export interface FaseAvanzamentoInsertForm { 
    codGara?: number;
    codLotto?: number;
    dataAnticipazione?: Date;
    dataCertificato?: Date;
    dataRaggiungimento?: Date;
    denomAvanzamento?: string;
    flagPagamento?: number;
    flagRitardo?: string;
    importoAnticipazione?: number;
    importoCertificato?: number;
    importoSal?: number;
    num?: number;
    numGiorniProroga?: number;
    numGiorniScost?: number;
    subappalti?: string;
    associazioniPagamenti?: Array<AssociazionePagamentiEntry>
}

export interface ResponseMaxNumAvan {
    esito?: boolean;
    data?: number;
    errorData?: string;
    totalCount?: number;
}