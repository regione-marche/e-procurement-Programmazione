import { StazioneAppaltanteBaseInfo } from '../stazione-appaltante/stazione-appaltante.model';

export interface UserAccountInfo {
    /**
     * Chiavi di accesso per l'autenticazione verso OR
     */
    chiaviAccesso?: ChiaviAccessoOrt;
    richiestaAssistenzaAttiva?: boolean;
    /**
     * ruolo dell'utente
     */
    ruolo?: string;
    /**
     * Lista delle stazioni appaltanti abilitate per l'utente
     */
    stazioniAppaltanti?: Array<StazioneAppaltanteBaseInfo>;
    /**
     * Syscon dell'utente
     */
    syscon?: string;
    /**
     * Url dei manuali
     */
    urlManuali?: string;
    userEmail?: string;
    /**
     * Username dell'utente
     */
    username?: string;
}


export interface ChiaviAccessoOrt {
    clientId: string;
    clientKey: string;
    username: string;
    password: string;
}

export interface UsrSysconEntry {
    cf?: string;
    nome?: string;
    syscon?: number;
}

export interface UsrSysconCheckEntry extends UsrSysconEntry {
    checked?: boolean;
}


export interface UserDTO {
    syscon: number;
    username?: string;
    codiceFiscale?: string;
    nome?: string;
    cognome?: string;
    email?: string;
    disabilitato?: boolean;
    ldap?: boolean;
    ufficioAppartenenza?: number;
    categoria?: number;
    opzioniUtente?: Array<string>;
    dataScadenzaAccount?: Date;
    dataUltimoAccesso?: Date;
    deletable?: boolean;
}

export interface UserEditDTO {
    abilitaModificaUfficiIntestatari?: string;
    amministratore?: string;
    codiceFiscale?: string;
    controlliGdpr?: string;
    dataScadenzaAccount?: Date;
    denominazione: string;
    email?: string;
    gestioneUtenti?: string;
    scadenzaAccount?: string;
    sysab3?: string;
    sysabg?: string;
    username: string;
}

export interface OpzioniUtenteProdotto {
    op?: Array<string>;
    ou?: Array<string>;
}