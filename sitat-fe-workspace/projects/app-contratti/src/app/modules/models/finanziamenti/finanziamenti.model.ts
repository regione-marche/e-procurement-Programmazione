export interface FinanziamentiEntry {
    codGara?: number;
    codLotto?: number;
    idFinanziamento?: string;
    importoFinanziamento?: number;
    numAppa?: number;
    numFina?: number;
}

export interface FinanziamentiInsertForm {
    codGara?: number;
    codLotto?: number;
    finanziamenti?: Array<FinanziamentoInsertForm>;
    numAppa?: number;
}

export interface FinanziamentoInsertForm {
    idFinanziamento?: string;
    importoFinanziamento?: number;
    numFina?: number;
}
