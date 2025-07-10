
export interface FaseConclusioneEntry {
    codGara?: number;
    codLotto?: number;
    dataRisoluzione?: Date;
    dataTermineContrattuale?: Date;
    dataUltimazione?: Date;
    dataVerbaleConsegna?: Date;
    faseInizialeExists?: boolean;
    flagOneri?: string;
    flagPolizza?: string;
    importoOneri?: number;
    interruzioneAnticipata?: string;
    motivoInterruzione?: number;
    motivoRisoluzione?: number;
    num?: number;
    numInfortuni?: number;
    numInfortuniMortali?: number;
    numInfortuniPermanenti?: number;
    numgiorniProroga?: number;
    oreLavorate?: number;
    pubblicata?: boolean;
}

export interface FaseConclusioneInsertForm {
    codGara?: number;
    codLotto?: number;
    dataRisoluzione?: Date;
    dataTermineContrattuale?: Date;
    dataUltimazione?: Date;
    dataVerbaleConsegna?: Date;
    flagOneri?: string;
    flagPolizza?: string;
    importoOneri?: number;
    interruzioneAnticipata?: string;
    motivoInterruzione?: number;
    motivoRisoluzione?: number;
    num?: number;
    numInfortuni?: number;
    numInfortuniMortali?: number;
    numInfortuniPermanenti?: number;
    numgiorniProroga?: number;
    oreLavorate?: number;
}

export interface InizFaseConclusioneContrattoEntry {
    dataVerbale?: Date;
	dataTermine?: Date;
    faseInizialeExists?: boolean;
    numGiorniProroga?: number;
}