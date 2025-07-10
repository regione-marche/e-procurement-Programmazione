import { ImpresaEntry } from '@maggioli/app-commons';

export interface FaseImpresaEntry {
    codGara?: number;
    codLotto?: number;
    imprese?: Array<RuoloImpresa>;
    num?: number;
    numRagg?: number;
    partecipante?: number;
    pubblicata?: boolean;
    tipologiaSoggetto?: number;
}

export interface RuoloImpresa {
    codImpresa?: string;
    ruolo?: number;
    impresa?: ImpresaEntry;
}

export interface FaseImpresaInsertForm {
    codGara?: number;
    codLotto?: number;
    imprese?: Array<RuoloImpresa>;
    num?: number;
    numRagg?: number;
    partecipante?: number;
    tipologiaSoggetto?: number;
    updateDaexport?: boolean;
}
