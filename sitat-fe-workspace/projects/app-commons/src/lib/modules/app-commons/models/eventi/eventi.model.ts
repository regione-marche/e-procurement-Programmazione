import { ResponseDTO } from "@maggioli/app-commons";

export interface WLogEventiDTO { 
    codApp?: string;
    codProfilo?: string;
    codiceEvento?: string;
    dataOra?: Date;
    descrizione?: string;
    descrizioneUtente?: string;
    errorMessage?: string;
    idEvento?: number;
    ipEvento?: string;
    livelloEvento?: number;
    oggettoEvento?: string;
    syscon?: number;
    dataOraFormattata?: string;
}

export interface ResponseListaDTO<T> extends ResponseDTO<T> {
    totalCount?: number;
}