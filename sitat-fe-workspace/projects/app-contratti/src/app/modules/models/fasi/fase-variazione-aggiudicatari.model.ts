import { ImpresaEntry } from "@maggioli/app-commons";

export interface FaseVariazioneAggiudicatariEntry {
    codGara?: number;
    codLotto?: number;
    numVaraggi?: number;
    idPartecipante?: string;
    impresa?: ImpresaEntry;
    idRuolo?: number;
    partecipante?: string;
    tipoOe?: number;
    flagAvvalimento?: number;
    motivoVariazione?: number;
}

export interface FaseVariazioneAggiudicatariInsertForm {
    codGara?: number;
    codLotto?: number;
    numVaraggi?: number;
    idPartecipante?: string;
    codImpresa?: String;
    idRuolo?: number;
    tipoOe?: number;
    flagAvvalimento?: number;
    motivoVariazione?: number;

}

export interface InizFaseVariazioneAggiudicatariEntry{
    idPartecipanteList?: Array<string>;
}