import { ResponseResult } from '@maggioli/app-commons';

export interface ResponseListaInterventiNR extends ResponseResult<Array<InterventoNonRipropostoEntry>> {
    totalCount?: number;
}

export interface InterventoNonRipropostoEntry {
    cui?: string;
    cup?: string;
    descrizione?: string;
    idIntervento?: number;
    idProgramma?: number;
    importoComplessivo?: number;
    motivo?: string;
    priorita?: number;
}

export interface InterventoNonRipropostoInsertForm {
    cui?: string;
    cup?: string;
    descrizione?: string;
    idIntervento?: number;
    idProgramma?: number;
    importoComplessivo?: number;
    motivo?: string;
    priorita?: number;
}

export interface InterventoNonRipropostoUpdateForm {
    cui?: string;
    cup?: string;
    descrizione?: string;
    idIntervento?: number;
    idProgramma?: number;
    importoComplessivo?: number;
    motivo?: string;
    priorita?: number;
}