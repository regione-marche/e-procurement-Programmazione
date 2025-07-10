import { ImpresaEntry } from '@maggioli/app-commons';

export interface FaseInfortuniEntry {
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    giornateRiconosciute?: number;
    impresa?: ImpresaEntry;
    infortuniMortali?: number;
    infortuniPermanenti?: number;
    num?: number;
    pubblicata?: boolean;
    totaleInfortuni?: number;
}

export interface FaseInfortuniInsertForm {
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    giornateRiconosciute?: number;
    infortuniMortali?: number;
    infortuniPermanenti?: number;
    num?: number;
    totaleInfortuni?: number;
}
