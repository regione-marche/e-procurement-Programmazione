import { RupEntry } from "@maggioli/app-commons";
import { SdkAutocompleteItem } from "@maggioli/sdk-controls";

export interface RicercaAttiGeneraliForm {
    stazioneAppaltante?: string;
    stazioneAppaltanteData?: Array<SdkAutocompleteItem>;
    idAtto?: string;
    tipologia?: number;
    descrizione?: string;
    rup?: Array<string>;
    rupData?: Array<SdkAutocompleteItem>;
    dataPubbSistema?: Date;
    dataPubbSistemaDa?: Date;
    dataPubbSistemaA?: Date;
    syscon?: string;
    cfTecnico?: string;
    cfNull?: boolean;
    annullato?: boolean;
}

export interface AttoGeneraleEntry {
    stazioneAppaltante?: string;
    idAtto?: string;
    utenteProp?: string;
    nomein?: string;
    rup?: string;
    tecnico?: RupEntry;
    rupData?: SdkAutocompleteItem;
    tipologia?: number;
    descrizione?: string;
    dataAtto?: Date;
    numeroAtto?: string;
    primaPubb?: string;
    dataPrimaPubblicazione?: Date;
    dataPubbSistema?: Date;
    pubblicato?: boolean;
    annullato?: boolean;
    dataScadenza?: Date;
    dataCanc?: Date;
    motivoCanc?: string;
    existingDocuments?: Array<AllegatoEntry>;
    documents?: Array<AllegatoEntry>;
}

export interface ResponseListaAttiGenerali {
    data?: Array<AttoGeneraleEntry>;
    errorData?: string;
    esito?: boolean;
    totalCount?: number;
}

export interface ResponseDettaglioAttoGenerale {
    data?: AttoGeneraleEntry;
    errorData?: string;
    esito?: boolean;
    totalCount?: number;
}

export interface ListaAttiGeneraliParams {
    nomein?: string;
    idAtto?: string;
    tipologia?: number;
    descrizione?: string;
    rupData?: Array<SdkAutocompleteItem>;
    rup?: Array<string>;
    dataPubbSistema?: Date;
    dataPubbSistemaDa?: Date;
    dataPubbSistemaA?: Date;
    dataScadenza?: Date;
    syscon?: string;
    sort?: string;
    skip?: number;
    sortDirection?: string;
    take?: number;
    stazioneAppaltante?: string;
    stazioneAppaltanteData?: Array<SdkAutocompleteItem>;
}

/* Contenitore per l'atto generale in fase di modifica */
export interface AttoGeneraleUpdateForm {
    idAtto?: string;
    stazioneAppaltante?: string;
    rup?: string;
    tipologia?: number;
    descrizione?: string;
    dataAtto?: Date;
    numeroAtto?: string;
    primaPubb?: string;
    dataPrimaPubb?: Date;
    dataPubbSistema?: Date;
    dataScadenza?: Date;
    documents?: Array<AllegatoEntry>;
    updateDocuments?: Array<AllegatoEntry>;
    allegatiDaAnnullare?: Array<AllegatoMotivazioneEntry>;
}

/* Contenitore per l'atto generale in fase di inserimento */
export interface AttoGeneraleInsertForm {
    stazioneAppaltante?: string;
    rup?: string;
    tipologia?: number;
    descrizione?: string;
    dataAtto?: Date;
    numeroAtto?: string;
    primaPubb?: string;
    dataPrimaPubb?: Date;
    dataPubbSistema?: Date;
    dataScadenza?: Date;
    updateDocuments?: Array<AllegatoEntry>;
}

export interface AllegatoEntry {
    idAllegato?: number;
    key01?: string;
    key02?: string;
    key03?: string;
    tabella?: string;
    descrizione?: string;
    binary?: string;
    url?: string;
    tipoFile?: string;
    numOrdine?: number;
    utenteCreatore?: number;
    dataAggiunta?: string;
    utenteCanc?: number;
    dataCanc?: Date;
    motivoCanc?: string;
    syscon?: number;
}

export interface AllegatoMotivazioneEntry {
    idAllegato?: number;
    key01?: string;
    motivoCanc?: string;
}