export interface FaseStipulaPubbEsitoForm {
    codGara?: number;
    codLotto?: number;
    counter?: number;
    dataAlbo?: Date;
    dataGuce?: Date;
    dataGuri?: Date;
    numIniziale?: number;
    profiloCommittente?: string;
    quotidianiNaz?: number;
    quotidianiReg?: number;
    sitoMinInfTrasp?: string;
    sitoOsservatorio?: string;
}

export interface FaseStipulaAccordoQuadroEntry {
    codGara?: number;
    codLotto?: number;
    dataDecorrenza?: Date;
    dataScadenza?: Date;
    dataStipula?: Date;
    pubblicata?: boolean;
    pubblicazioneEsito?: FaseStipulaPubbEsitoForm;
}

export interface FaseStipulaAccordoQuadroInsertForm {
    codGara?: number;
    codLotto?: number;
    dataDecorrenza?: Date;
    dataScadenza?: Date;
    dataStipula?: Date;
    num?: number;
    pubblicazioneEsito?: FaseStipulaPubbEsitoForm;
}
