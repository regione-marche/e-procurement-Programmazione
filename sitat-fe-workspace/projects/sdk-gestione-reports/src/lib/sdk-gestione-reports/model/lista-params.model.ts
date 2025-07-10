//#region DTO per un singolo parametro
export interface WRicParamDTO{
    idRicerca: number;
    progressivo?: number;
    codice: string;
    nome: string;
    descrizione?: string;
    tipo: string;
    codiceTabellato?: string;
    obbligatorio?: string;
    menuField?: string;
    value?: any;
    identificativoUtente?: number;
    stazioneAppaltante?: string;
}
//#endregion

//#region DTO per un singolo parametro con campo checked per indicare se il parametro è stato selezionato o meno
export interface WRicParamCheckDTO{
    idRicerca: number;
    progressivo?: number;
    codice: string;
    nome: string;
    descrizione?: string;
    tipo: string;
    codiceTabellato?: string;
    obbligatorio?: number;
    checked?: boolean;
}
//#endregion