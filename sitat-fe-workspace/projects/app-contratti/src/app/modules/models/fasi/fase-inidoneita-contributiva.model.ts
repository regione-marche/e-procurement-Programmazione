import { ImpresaEntry } from '@maggioli/app-commons';

export interface FaseInidoneitaContributivaEntry {
    cassaEdile?: string;
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    dataComunicazione?: Date;
    dataDurc?: Date;
    descrizione?: string;
    estremiDurc?: string;
    impresa?: ImpresaEntry;
    num?: number;
    pubblicata?: boolean;
    riscontroIrregolare?: number;
}

export interface FaseInidoneitaContributivaInsertForm {
    cassaEdile?: string;
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    dataComunicazione?: Date;
    dataDurc?: Date;
    descrizione?: string;
    estremiDurc?: string;
    num?: number;
    riscontroIrregolare?: number;
}
