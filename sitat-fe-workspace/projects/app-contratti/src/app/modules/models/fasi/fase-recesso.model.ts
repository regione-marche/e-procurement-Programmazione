export interface FaseIstanzaRecessoEntry {
    codGara?: number;
    codLotto?: number;
    dataConsegna?: Date;
    dataIstanzaRecesso?: Date;
    dataTermine?: Date;
    durataSospensione?: number;
    flagAccolta?: string;
    flagRipresa?: string;
    flagRiserva?: string;
    flagTardiva?: string;
    importoOneri?: number;
    importoSpese?: number;
    motivoSospensione?: string;
    num?: number;
    pubblicata?: boolean;
    ritardo?: number;
    tipoComunicazione?: string;
}

export interface FaseIstanzaRecessoInsertForm {
    codGara?: number;
    codLotto?: number;
    dataConsegna?: Date;
    dataIstanzaRecesso?: Date;
    dataTermine?: Date;
    durataSospensione?: number;
    flagAccolta?: string;
    flagRipresa?: string;
    flagRiserva?: string;
    flagTardiva?: string;
    importoOneri?: number;
    importoSpese?: number;
    motivoSospensione?: string;
    num?: number;
    ritardo?: number;
    tipoComunicazione?: string;
}
