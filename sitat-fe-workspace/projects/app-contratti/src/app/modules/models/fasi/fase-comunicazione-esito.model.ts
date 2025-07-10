export interface FaseComunicazioneEntry { 
    esito?: FaseComEsitoForm;
    pubblicata?: boolean;
    pubblicazioneEsito?: FaseComPubbEsitoForm;
    hasAggiudicazione?: boolean;
}

export interface FaseComEsitoForm { 
    binary?: string;
    codGara?: number;
    codLotto?: number;
    dataAggiudicazione?: Date;
    esito?: number;
    fileAllegato?: string;
    importoAggiudicazione?: number;
}

export interface FaseComPubbEsitoForm { 
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
