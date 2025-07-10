
export interface FaseConclusioneSempEntry {
    codGara?: number;
    codLotto?: number;
    dataRisoluzione?: Date;
    dataUltimazione?: Date;
    faseInizialeExists?: boolean;
    flagOneri?: string;
    flagPolizza?: string;
    importoOneri?: number;
    interruzioneAnticipata?: string;
    motivoInterruzione?: number;
    motivoRisoluzione?: number;
    num?: number;
    oreLavorate?: number;
    pubblicata?: boolean;
}

export interface FaseConclusioneSempInsertForm {
    codGara?: number;
    codLotto?: number;
    dataRisoluzione?: Date;
    dataUltimazione?: Date;
    flagOneri?: string;
    flagPolizza?: string;
    importoOneri?: number;
    interruzioneAnticipata?: string;
    motivoInterruzione?: number;
    motivoRisoluzione?: number;
    num?: number;
    oreLavorate?: number;
}
