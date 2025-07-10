import { ImpresaEntry } from '@maggioli/app-commons';

import { LottoDynamicValue } from '../gare/gare.model';

export interface FaseCantieriEntry {
    civico?: string;
    codGara?: number;
    codIstatComune?: string;
    codLotto?: number;
    coordX?: number;
    coordY?: number;
    dataInizio?: Date;
    descInfrsatrutturaRete?: string;
    destinatariNotifica?: Array<LottoDynamicValue>;
    /**
     * decorator per la visibilità dei campi: 0 se il campo destinatariNotifica è vuoto altrimenti 1
     */
    destinatariNotificaExists?: '0' | '1'
    durataPresunta?: number;
    imprese?: Array<FaseCantieriImpEntry>;
    indirizzoCantiere?: string;
    infrastrutturaReteA?: number;
    infrastrutturaReteDa?: number;
    latitudine?: number;
    longitudine?: number;
    num?: number;
    numLavoratoriAutonomi?: number;
    numMaxLavoratori?: number;
    numeroImprese?: number;
    provincia?: string;
    pubblicata?: boolean;
    tipoIntervento?: number;
    tipoOpera?: number;
    tipologiaCostruttiva?: number;
    ulterioreMail?: string;
}

export interface FaseCantieriInsertForm {
    civico?: string;
    codGara?: number;
    codIstatComune?: string;
    codLotto?: number;
    coordX?: number;
    coordY?: number;
    dataInizio?: Date;
    descInfrsatrutturaRete?: string;
    destinatariNotifica?: Array<LottoDynamicValue>;
    durataPresunta?: number;
    imprese?: Array<FaseCantieriImpEntry>;
    indirizzoCantiere?: string;
    infrastrutturaReteA?: number;
    infrastrutturaReteDa?: number;
    latitudine?: number;
    longitudine?: number;
    num?: number;
    numLavoratoriAutonomi?: number;
    numMaxLavoratori?: number;
    numeroImprese?: number;
    provincia?: string;
    tipoIntervento?: number;
    tipoOpera?: number;
    tipologiaCostruttiva?: number;
    ulterioreMail?: string;
}

export interface FaseCantieriImpEntry {
    codGara?: number;
    codLotto?: number;
    codiceDurc?: string;
    codiceImpresa?: string;
    dataDurc?: Date;
    num?: number;
    numCant?: number;
    protocolloDurc?: string;
    impresa?: ImpresaEntry;
}


export interface InizFaseCantieriEntry { 
    dataInizioLavori?: Date;
    destinatariNotifica?: Array<LottoDynamicValue>;
    /**
     * decorator per la visibilità dei campi: 0 se il campo destinatariNotifica è vuoto altrimenti 1
     */
    destinatariNotificaExists?: '0' | '1';
    idAppalto?: number;
    tipoIntervento?: number;
}