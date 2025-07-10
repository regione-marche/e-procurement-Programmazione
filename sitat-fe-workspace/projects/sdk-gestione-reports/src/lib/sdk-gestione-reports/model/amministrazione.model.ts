import { ResponseDTO, ValoreTabellato } from './lib.model';

export interface RicercaConfigurazioniInizDTO {
    listaSezioni?: Array<string>;
}

export interface RicercaConfigurazioniFormDTO {
    sezione?: string;
    chiave?: string;
    valore?: string;
    descrizione?: string;
}

export interface ResponseListaDTO<T> extends ResponseDTO<T> {
    totalCount?: number;
}

export interface WConfigDTO {
    codApp?: string;
	chiave?: string;
	valore?: string;
	sezione?: string;
	descrizione?: string;
	criptato?: string;
}

export interface WConfigEditDTO { 
    chiave: string;
    codApp: string;
    valore?: string;
}

export interface WQuartzDTO { 
    beanId?: string;
    codApp?: string;
    dayOfMonth?: string;
    dayOfWeek?: string;
    descrizione?: string;
    expression?: string;
    hours?: string;
    minutes?: string;
    month?: string;
    seconds?: string;
    year?: string;
}

export interface WQuartzEditDTO { 
    beanId?: string;
    codApp?: string;
    dayOfMonth?: string;
    dayOfWeek?: string;
    hours?: string;
    minutes?: string;
    month?: string;
    seconds?: string;
    year?: string;
}

export interface RicercaEventiFormDTO {
    idEvento?: number;
    dataOraDa?: string;
    dataOraA?: string;
    descrizioneUtente?: string;
	livelloEvento?: string;
	codiceEvento?: string;
	oggettoEvento?: string;
	descrizione?: string;
}

export interface RicercaEventiInizDTO { 
    listaCodiciEventi?: Array<string>;
}

export interface WLogEventiDTO { 
    codApp?: string;
    codProfilo?: string;
    codiceEvento?: string;
    dataOra?: Date;
    descrizione?: string;
    descrizioneUtente?: string;
    errorMessage?: string;
    idEvento?: number;
    ipEvento?: string;
    livelloEvento?: number;
    oggettoEvento?: string;
    syscon?: number;
}

export interface WMailDTO { 
    codApp?: string;
    delay?: string;
    dimTotaleAllegati?: string;
    idCfg?: string;
    idCfgReadonly?: string;
    idUser?: string;
    mailMittente?: string;
    passwordImpostata?: boolean;
    porta?: string;
    protocollo?: string;
    server?: string;
    timeout?: string;
    tracciaturaMessaggi?: string;
}

export interface WMailInizDTO { 
    delay?: number;
    dimTotaleAllegati?: number;
    listaConfigurazioni?: Array<ValoreTabellato>;
    timeout?: string;
}

export interface WMailEditDTO { 
    delay?: number;
    dimTotaleAllegati?: number;
    idCfg: string;
    idUser?: string;
    mailMittente: string;
    porta?: number;
    protocollo?: string;
    server: string;
    timeout?: string;
    tracciaturaMessaggi?: string;
}

export interface WMailEditPasswordDTO { 
    confermaNuovaPassword?: string;
    idCfg: string;
    nuovaPassword?: string;
    vecchiaPassword?: string;
}

export interface WMailTestSendDTO { 
    idCfg: string;
    testEmail: string;
}

export interface RicercaTabellatiFormDTO {
    codiceTabellato?: string;
    descrizioneTabellato?: string;
}

export interface VTab4Tab6DTO {
    tab46Cod?: string;
	codiceTabellato?: string;
	descrizioneTabellato?: string;
	provenienza?: string;
}

export interface Tab0DTO { 
    archiviato?: string;
    bloccato?: string;
    descrizione?: string;
    ordinamento?: number;
    tab0cod: string;
    tab0tip: string;
    tab0vid?: string;
}

export interface Tab1DTO { 
    archiviato?: string;
    bloccato?: string;
    descrizione?: string;
    ordinamento?: number;
    tab1cod: string;
    tab1tip: number;
}

export interface Tab2DTO { 
    archiviato?: string;
    bloccato?: string;
    descrizione?: string;
    ordinamento?: number;
    parametroAssociato?: string;
    tab2cod: string;
    tab2tip: string;
}

export interface Tab3DTO { 
    archiviato?: string;
    bloccato?: string;
    descrizione?: string;
    ordinamento?: number;
    tab3cod: string;
    tab3tip: string;
}

export interface Tab5DTO { 
    archiviato?: string;
    bloccato?: string;
    descrizione?: string;
    ordinamento?: number;
    tab5cod: string;
    tab5tip: string;
}

export type TabXDTO = Tab0DTO | Tab1DTO | Tab2DTO | Tab3DTO | Tab5DTO;

export interface ListaDettaglioTabellatoFormDTO {
    skip: number;
    take: number;
    provenienza: string;
    codiceTabellato: string;
}

export interface C0CampiDTO {
	c0c_mne_ber: string;
	coc_conta: number;
	c0c_tip?: string;
	c0c_chi?: string;
	coc_mne_uni?: string;
	coc_des?: string;
	coc_des_frm?: string;
	c0c_fs?: string;
	c0c_tab1?: string;
	coc_dom?: string;
	coc_des_web?: string;
}

export interface ListaCodificaAutomaticaInizDTO { 
    codiceApplicazione?: string;
    titoloApplicazione?: string;
}

export interface GConfCodDTO { 
    codApp?: string;
    codCal?: string;
    contat?: number;
    desCam?: string;
    nomCam: string;
    nomEnt: string;
    numOrd?: number;
    tipCam: number;
}

export interface GConfCodEditDTO { 
    codCal?: string;
    contat?: number;
    nomCam: string;
    nomEnt: string;
    tipCam: number;
    codificaAutomaticaAttiva?: string;
}
