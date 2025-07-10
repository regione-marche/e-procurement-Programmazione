import { ImpresaEntry } from '@maggioli/app-commons';

export interface ImpresaAggiudicatariaEntry {
    codGara?: number;
    codImpAus?: string;
    codImpresa?: string;
    codLotto?: number;
    flagAvvallamento?: string;
    idGruppo?: number;
    idTipoAgg?: number;
    impresa?: ImpresaEntry;
    impresaAusiliaria?: ImpresaEntry;
    numAggi?: number;
    numAppa?: number;
    ruolo?: number;
    importoAggiudicazione?: number;
    ribassoAggiudicazione?: number;
    offertaAumento?: number;
    nomeLegRap?: string;
	cognomeLegRap?: string; 
	cfLegRap?: string;
}

export interface ImpresaAggiudicatariaInsertForm {
    codGara?: number;
    codImpAus?: string;
    codImpresa?: string;
    codLotto?: number;
    flagAvvallamento?: string;
    idGruppo?: number;
    idTipoAgg?: number;
    numAggi?: number;
    numAppa?: number;
    ruolo?: number;
    importoAggiudicazione?: number;
    ribassoAggiudicazione?: number;
    offertaAumento?: number;
    nomeLegRap?: string;
	cognomeLegRap?: string; 
	cfLegRap?: string;
}

export interface ImpreseAggiudicatarieInsertForm {
    codGara?: number;
    codLotto?: number;
    idTipoAgg?: number;
    imprese?: Array<ImpresaAggiudicatariaInsertForm>;
}

export interface ImportImpresaAgiudicatariaForm {
    codGara?: number;
    codLotto?: number;
    num?: number;
    numRagg?: number;
}

export interface ImpresaAggiudicatariaRaggEntry {
    codGara?: number;
    codLotto?: number;
    numAppa?: number;
    idGruppo?: number;
    imprese: Array<ImpresaAggiudicatariaEntry | ImpresaAggiudicatariaAttoEntry>;
    index?: number;
    idTipoAgg?: number;
}

export interface ImpresaAggiudicatariaAttoEntry {
    codGara?: number;
    codImpresa?: string;
    codInps?: string;
    codiceCassa?: string;
    idGruppo?: number;
    idTipoAgg?: number;
    impresa?: ImpresaEntry;
    numAggi?: number;
    numPubb?: number;
    ruolo?: number;
}

export interface ImpreseAggiudicatarieAttoInsertForm {
    codGara?: number;
    idTipoAgg?: number;
    imprese?: Array<ImpresaAggiudicatariaAttoInsertForm>;
    numPubb?: number;
}

export interface ImpresaAggiudicatariaAttoInsertForm {
    codImpresa?: string;
    idGruppo?: number;
    idTipoAgg?: number;
    ruolo?: number;
}

export interface ImpreseAggiudicatarieUpdateForm { 
    codGara?: number;
    codLotto?: number;
    idTipoAgg?: number;
    imprese?: Array<ImpresaAggiudicatariaInsertForm>;
    impreseDaCancellare?: Array<number>;
    numAppa?: number;
}
