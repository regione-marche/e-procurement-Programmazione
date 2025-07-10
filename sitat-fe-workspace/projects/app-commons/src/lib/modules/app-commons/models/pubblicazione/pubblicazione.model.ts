export interface CheckBlockingErrors {
    title?: string;
    desc?: string;
}

export interface CheckData {
    blockingErrorNum?: number;
    blockingErrors?: Array<CheckBlockingErrors>;
    warningErrorNum?: number,
    warningErrors?: Array<CheckBlockingErrors>
}

export interface DettaglioPubblicazione {
    autore?: string;
    dataInvio?: number;
    noteInvio?: string;
    xml?: string;
    idFlusso?: number;
    contri?: number;
    idSchedaLocale?: string;
}