export interface PubblicazioneTecnicoModel {
    codice?: String;
    cognome?: String;
    nome?: String;
    nomeCognome?: String;
    indirizzo?: String;
    civico?: String;
    localita?: String;
    provincia?: String;
    cap?: String;
    luogoIstat?: String;
    telefono?: String;
    fax?: String;
    cfPiva?: String;
    mail?: String;
}

export interface PubblicazioneDocumentoModel {
    titolo?: String;
    file_allegato?: String;
    url?: String;
    tipoFile?: string;
}

export interface PubblicazioneAvvisoModel {
    codiceFiscaleSA?: String;
    tipologia?: Number;
    data?: String;
    descrizione?: String;
    cig?: String;
    cup?: String;
    cui?: String;
    scadenza?: String;
    rup?: PubblicazioneTecnicoModel;
    clientId?: String;
    documenti?: Array<PubblicazioneDocumentoModel>;
    idRicevuto?: Number;
    syscon?: String;
    indirizzo?: string;
    comune?: string;
    provincia?: string;
}

export interface AllineamentoPubblicazioneModel {
    stazioneAppaltante?: String;
    tipologia?: Number;
    numeroAvviso?: Number;
    idRicevuto?: String;
    syscon?: String;
    payload?: string;
}

export interface FlussiAvviso { 
    autore?: string;
    dataInvio?: Date;
    noteInvio?: string;
    numeroAvviso?: number;
    stazioneAppaltante?: string;
    xml?: string;
}