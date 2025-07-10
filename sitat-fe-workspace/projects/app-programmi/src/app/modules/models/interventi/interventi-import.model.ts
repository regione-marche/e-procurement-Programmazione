export interface InterventiDaRiproporre {
    anno?: number;
    cui?: string;
    descrizione?: string;
    id?: number;
    idProgramma?: number;
    importo?: number;
    piatriId?: string;
}

export interface ChiaviIntervento {
    idProgramma: string,
    idIntervento: string
}

export interface ImportInterventiForm {
    idProgramma: string,
    interventiToImport: Array<ChiaviIntervento>;
    choice?:string;
}

export interface InterventiDaRiproporreExtended extends InterventiDaRiproporre {
    disabled?: boolean;
    checked?: boolean;
}