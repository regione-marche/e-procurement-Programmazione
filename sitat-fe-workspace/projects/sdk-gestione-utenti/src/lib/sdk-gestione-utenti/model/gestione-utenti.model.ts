import { IBrowserInfo } from '@maggioli/sdk-commons';

import { ResponseDTO } from './lib.model';

export interface RicercaUtentiFormDTOInternal {
    denominazione?: string;
    username?: string;
    usernameCF?: string;
    abilitato?: string;
    codiceFiscale?: string;
    email?: string;
    ufficioIntestatario?: UfficioIntestatarioDTO;
    ufficioIntestatarioKey?: string;
    gestioneUtenti?: string;
    amministratore?: string;
    profilo?: ProfiloDTO;
    profiloKey?: string;
}

export interface RicercaUtentiFormDTO {
    denominazione?: string;
    username?: string;
    abilitato?: string;
    codiceFiscale?: string;
    email?: string;
    ufficioIntestatarioKey?: string;
    gestioneUtenti?: string;
    amministratore?: string;
    profiloKey?: string;
}

export interface ResponseListaUtentiDTO<T> extends ResponseDTO<T> {
    totalCount?: number;
}

export interface UserDTO {
    categoria?: number;
    codiceFiscale?: string;
    cognome?: string;
    dataScadenzaAccount?: Date;
    dataUltimoAccesso?: Date;
    deletable?: boolean;
    disabilitato?: boolean;
    email?: string;
    ldap?: boolean;
    nome?: string;
    opzioniUtente?: Array<string>;
    sysab3?: string;
    sysabg?: string;
    syscon?: number;
    ufficiIntestatari?: Array<string>;
    ufficioAppartenenza?: number;
    username?: string;
}

export interface UserInsertDTO {
    abilitaModificaUfficiIntestatari?: string;
    abilitaTuttiUfficiIntestatari?: string;
    amministratore?: string;
    codiceFiscale?: string;
    confermaPassword: string;
    controlliGdpr?: string;
    dataScadenzaAccount?: Date;
    denominazione: string;
    email?: string;
    gestioneUtenti?: string;
    password: string;
    scadenzaAccount?: string;
    sysab3?: string;
    sysabg?: string;
    username?: string;
    usernameCF?: string;
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

export interface UfficioIntestatarioDTO {
    codice?: string;
    codiceFiscale?: string;
    denominazione?: string;
}

export interface UfficioIntestatarioCheckDTO {
    codice?: string;
    codiceFiscale?: string;
    denominazione?: string;
    checked?: boolean;
}

export interface UfficioIntestatarioEditDTO {
    listaUfficiIntestatari?: Array<string>;
}

export interface ProfiloDTO {
    codice?: string;
    codiceApplicazione?: string;
    descrizione?: string;
    nome?: string;
}

export interface ProfiloCheckDTO {
    codice?: string;
    codiceApplicazione?: string;
    descrizione?: string;
    nome?: string;
    checked?: boolean;
}

export interface ProfiliUtenteEditDTO {
    listaProfili?: Array<string>;
}

export interface UserChangePasswordAdminDTO {
    confermaPassword: string;
    password: string;
}

export interface RichiestaAssistenzaFormDTO extends IBrowserInfo {
    allegato?: string;
    allegatoName?: string;
    captchaSolution: string;
    descrizione?: string;
    email: string;
    nominativoEnteAmministrazione: string;
    referenteDaContattare: string;
    telefono?: string;
    tipologiaRichiesta: string;
}

export interface RichiestaAssistenzaInitDTO {
    allegatoFileSize?: string;
    assistenzaAttiva?: boolean;
    assistenzaEmail?: boolean;
    assistenzaTelefonica?: boolean;
    listaOggetti?: Array<RichiestaAssistenzaOggettoDTO>;
}

export interface RichiestaAssistenzaOggettoDTO {
    key?: string;
    value?: string;
}

export interface UserConnectedDTO {
    denominazione?: string;
    email?: string;
    syscon?: number;
    username?: string;
}

export interface UserConnectedEditDTO {
    email?: string;
}

export interface InitRicercaUtentiDTO {
    profiliAssociati?: Array<string>;
    registrazioneLoginCF?: boolean;
    stazioniAppaltantiAssociate?: Array<string>;
    utenteDelegatoGestioneUtenti?: boolean;
}