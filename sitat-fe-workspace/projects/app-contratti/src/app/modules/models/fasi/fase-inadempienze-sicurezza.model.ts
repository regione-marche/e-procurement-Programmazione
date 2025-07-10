import { ImpresaEntry } from '@maggioli/app-commons';

export interface FaseInadempienzeSicurezzaEntry {
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    comma3A?: string;
    comma3B?: string;
    comma45A?: string;
    comma5?: string;
    comma6?: string;
    commaAltro?: string;
    dataInadempienza?: Date;
    descrizione?: string;
    impresa?: ImpresaEntry;
    num?: number;
    pubblicata?: boolean;
}

export interface FaseInadempienzeSicurezzaInsertForm {
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    comma3A?: string;
    comma3B?: string;
    comma45A?: string;
    comma5?: string;
    comma6?: string;
    commaAltro?: string;
    dataInadempienza?: Date;
    descrizione?: string;
    num?: number;
}
