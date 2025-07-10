import { BaseResponse } from "./gare.model";
export interface ConsultaGaraEntry { 
    cfRup?: string;
    cigInDb?: boolean;
    codGara?: string;
    listaLotti?: Array<ConsultaGaraBean>;
    operation?: string;
    origineSAInfo?: SABaseEntry;
    idAvGara?: string;
    perfezionata?: boolean;
    internalErrors?: Array<string>;
    validationErrors?: Array<string>;
    anacErrors?: Array<string>;
    inserito?: boolean;
    presaCarico?: boolean;
}

export interface ConsultaGaraBean {
    caricato?: boolean;
    codiceCIG?: string;
    msg?: string;
}

export interface ImportaGaraForm {
    cfRup?: string;
    cfSA?: string;
    cigIdAvGara?: string;
    syscon?: number;
    sendMail?: boolean;
    cfImpersonato?: string;
    loaImpersonato?: string;
    idpImpersonato?: string;    
    codProfilo?: string;
    codein?: string;
}

export interface ImportaSmartcigForm {
    username?: string;
    password?: string;
    cig?: string;
    codiceStazioneAppaltante?: string;
    saveCredentials?: boolean;
    syscon?: number;
}

export interface AllineaGaraForm {
    codiceSA?: string;
    idavGara?: string;
}

export interface CheckMigrazioneForm {
    cfStazioneAppaltante?: string;
    idavGara?: string;
    syscon?: number;
}

export interface SABaseEntry { 
    codice?: string;
    listaRup?: Array<BaseRupInfo>;
    nome?: string;
}

export interface CheckMigrazioneGaraEntry {
    codiceFiscaleRupGara?: string;
    stazioneAppaltante?: SABaseEntry;
}

export interface BaseRupInfo { 
    cf?: string;
    codice?: string;
    nominativo?: string;
}

export enum ConsultaGaraOperations {
    OP_PRESA_IN_CARICO = 'OP_PRESA_IN_CARICO',
    OP_PRESA_IN_CARICO_DELEGA_U = 'OP_PRESA_IN_CARICO_DELEGA_U',
    OP_PRESA_IN_CARICO_DELEGA_AC = 'OP_PRESA_IN_CARICO_DELEGA_AC',
    OP_CONSULTA_GARA = 'OP_CONSULTA_GARA',
    OP_IMPORT_ANAC = 'OP_IMPORT_ANAC'
}

export interface Collaborazione { 
    aziendaCodiceFiscale?: string;
    aziendaDenominazione?: string;
    idOsservatorio?: string;
    index?: string;
    ufficioDenominazione?: string;
    ufficioId?: string;
    ufficioProfilo?: string;
}

export interface PresaInCaricoGaraDelegataForm { 
    cfRup?: string;
    idAvGara?: string;
    indexCollaborazione?: string;
}

export interface ResponsePresaInCaricoGaraDelegata { 
    data?: string;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
}

export interface ResponseListaCollaborazioni extends BaseResponse {
    data?: Collaborazione[];
}

export interface Collaborazione {
    aziendaCodiceFiscale?: string;
    aziendaDenominazione?: string;
    idOsservatorio?: string;
    index?: string;
    ufficioDenominazione?: string;
    ufficioId?: string;
    ufficioProfilo?: string;
}