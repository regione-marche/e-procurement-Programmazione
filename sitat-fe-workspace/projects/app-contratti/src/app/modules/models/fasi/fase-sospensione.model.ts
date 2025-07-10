export interface FaseSospensioneEntry {
    codGara?: number;
    codLotto?: number;
    dataVerbRipr?: Date;
    dataVerbSosp?: Date;
    flagRiserve?: string;
    flagSuperoTempo?: string;
    flagVerbale?: string;
    motivoSosp?: number;
    num?: number;
    pubblicata?: boolean;
    sospParziale?: string;
    incCrono?: number;
    dataSuperQuarto?: Date;
}

export interface FaseSospensioneInsertForm {
    codGara?: number;
    codLotto?: number;
    dataVerbRipr?: Date;
    dataVerbSosp?: Date;
    flagRiserve?: string;
    flagSuperoTempo?: string;
    flagVerbale?: string;
    motivoSosp?: number;
    num?: number;
    sospParziale?: string;
    incCrono?: number;
    dataSuperQuarto?: Date;
}

export interface FaseRipresaPrestazioneInsertForm {
    codGara?: number;
    codLotto?: number;
    num?: number;
    incCrono?: number;
}

export interface FaseSuperamentoQuartoContrattualeInsertForm {
    codGara?: number;
    codLotto?: number;
    num?: number;
    dataSuperQuarto?: Date;
}

export interface InizFaseRipresaPrestazioneEntry {
    codGara?: number;
    codLotto?: number;
    listaSosp?: Array<FaseSospensioneEntry>;
    
}
