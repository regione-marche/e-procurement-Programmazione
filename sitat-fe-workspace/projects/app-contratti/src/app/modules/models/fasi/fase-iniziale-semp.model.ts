export interface FaseInizialeSemplificataEntry {
    codGara?: number;
    codLotto?: number;
    dataStipula?: Date;
    dataTermine?: Date;
    dataVerbale?: Date;
    flagRiserva?: string;
    num?: number;
    pubblicata?: boolean;
}

export interface FaseInizialeSemplificataInsertForm {
    codGara?: number;
    codLotto?: number;
    dataStipula?: Date;
    dataTermine?: Date;
    dataVerbale?: Date;
    flagRiserva?: string;
    num?: number;
}
