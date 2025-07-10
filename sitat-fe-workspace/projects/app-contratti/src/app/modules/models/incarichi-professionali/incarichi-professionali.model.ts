import { RupEntry } from '@maggioli/app-commons';

export interface IncarichiProfessionaliInsertForm {
    codGara?: number;
    codLotto?: number;
    codFase?: number;
    incarichi?: Array<IncaricoProfessionaleInsertForm>;
    numAppa?: number;
    num?: number;
}

export interface IncaricoProfessionaleInsertForm {
    cigProgEsterna?: string;
    codiceTecnico?: string;
    dataAffProgEsterna?: Date;
    dataConsProgEsterna?: Date;
    idRuolo?: number;
    sezione?: string;
}

export interface IncarichiProfEntry {
    cigProgEsterna?: string;
    codGara?: number;
    codLotto?: number;
    codiceTecnico?: string;
    dataAffProgEsterna?: Date;
    dataConsProgEsterna?: Date;
    idRuolo?: number;
    num?: number;
    numInca?: number;
    sezione?: string;
    tecnico?: RupEntry;
    pcp?: boolean;
}

export interface ResponseIncarichiProfListEntry{
    incarichi?: Array<IncarichiProfEntry>;
    pubblicata?: boolean;
}
