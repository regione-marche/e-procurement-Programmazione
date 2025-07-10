import { ImpresaEntry } from '@maggioli/app-commons';

export interface FaseInidoneitaTecnicoProfEntry {
    altreCause?: string;
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    dataUni?: Date;
    descrizione?: string;
    impresa?: ImpresaEntry;
    inadeguataFormazioneSicurezza?: string;
    mancataNominaMedico?: string;
    mancataNominaResp?: string;
    mancatoDocValutazioneRischi?: string;
    nonIdoneitaVerificaIscrizione?: string;
    nonIndicatiContratti?: string;
    num?: number;
    pubblicata?: boolean;
}

export interface FaseInidoneitaTecnicoProfInsertForm {
    altreCause?: string;
    codGara?: number;
    codImpresa?: string;
    codLotto?: number;
    dataUni?: Date;
    descrizione?: string;
    inadeguataFormazioneSicurezza?: string;
    mancataNominaMedico?: string;
    mancataNominaResp?: string;
    mancatoDocValutazioneRischi?: string;
    nonIdoneitaVerificaIscrizione?: string;
    nonIndicatiContratti?: string;
    num?: number;
}
