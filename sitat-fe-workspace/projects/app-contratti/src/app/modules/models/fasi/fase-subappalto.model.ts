import { ImpresaEntry } from '@maggioli/app-commons';
import { CPVSecondario } from '../gare/gare.model';

export interface FaseSubappaltoEntry {
    codGara?: number;
    codImpresa?: string;
    codImpresaAgg?: string;
    codLotto?: number;
    dataAuth?: Date;
    descCpv?: string;
    idCategoria?: string;
    idCpv?: string;
    importoEffettivo?: number;
    importoPresunto?: number;
    impresaAggiudicatrice?: ImpresaEntry;
    impresaSubappaltatrice?: ImpresaEntry;
    num?: number;
    oggetto?: string;
    pubblicata?: boolean;
    mandanti?:Array<MandanteEntry>;
    dgue?: any;
    motivoMancatoSub?: string;
    dataUltimazione?: string;
    motivoMancataEsec?: string;
    esecuzioneSuba?: string
}

export interface FaseSubappaltoInsertForm {
    codGara?: number;
    codImpresa?: string;
    codImpresaAgg?: string;
    codLotto?: number;
    dataAuth?: Date;
    idCategoria?: string;
    idCpv?: string;
    importoEffettivo?: number;
    importoPresunto?: number;
    num?: number;
    oggetto?: string;
    tipoImpresa?: string;
    mandanti?:Array<MandanteEntry>;
    dgue?: any;
    newDgue?: any;
    motivoMancatoSub?: string;
}

export interface InizFaseSubappaltoEntry {
    singolaImpresa?: boolean;
    listaCpv?: Array<CPVSecondario>;
    listaSuba?: Array<FaseSubappaltoEntry>;
}

export interface MandanteEntry {
    nomeMandante?: string;
}
