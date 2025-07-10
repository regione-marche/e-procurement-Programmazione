import { ImpresaEntry } from '@maggioli/app-commons';

export interface MisureAggiuntiveSicurezzaEntry {
    codGara?: number;
    codLotto?: number;
    codiceImpresa?: string;
    descrizione?: string;
    documents?: Array<ExistingDocumentoFaseEntry>;
    impresa?: ImpresaEntry;
    numIniz?: number;
}

export interface ExistingDocumentoFaseEntry {
    codGara?: number;
    codLotto?: number;
    progressivoDocumento?: number;
    titolo?: string;
    tipoFile?: string;
}

export interface DocumentoFaseEntry {
    binary?: string;
    codGara?: number;
    codLotto?: number;
    progressivoDocumento?: number;
    titolo?: string;
}

export interface MisureAggiuntivesicurezzaInsertForm {
    codGara?: number;
    codLotto?: number;
    codFase?: number;
    codiceImpresa?: string;
    descrizione?: string;
    documents?: Array<DocumentoFaseInsertForm>;
    numIniz?: number;
}

export interface MisureAggiuntivesicurezzaUpdateForm {
    codGara?: number;
    codLotto?: number;
    codFase?: number;
    codiceImpresa?: string;
    descrizione?: string;
    existingDocuments?: Array<ExistingDocumentoFaseEntry>;
    newDocuments?: Array<DocumentoFaseInsertForm>;
    numIniz?: number;
}

export interface DocumentoFaseInsertForm {
    binary?: string;
    codGara?: number;
    codLotto?: number;
    codFase?: number;
    numeroProgressivoFase?: number;
    titolo?: string;
    tipoFile?: string;
}