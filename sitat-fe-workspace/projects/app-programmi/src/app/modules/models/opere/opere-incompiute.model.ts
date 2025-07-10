import { IDictionary } from '@maggioli/sdk-commons';
import { ImmobileEntry, ImmobileInsertForm } from '../immobili/immobile.model';
import { ResponseResult } from '@maggioli/app-commons';

export interface ResponseListaOpereIncompiute extends ResponseResult<Array<OperaIncompiutaBaseEntry>> {
    totalCount?: number;
}

export interface OperaIncompiutaBaseEntry {
    annoUltimoQe?: number;
    avanzamento?: number;
    cup?: string;
    descrizione?: string;
    id?: number;
    idProgramma?: number;
}

export interface DettaglioCupForm{
    cup:string;
    username:string;
    password:string;
    syscon:string;
}

export interface DettaglioCupIniz{
    username?:string;
    password?:string;
}

export interface OperaIncompiutaEntry {
    ambitoInt?: string;
    annoUltimoQe?: number;
    avanzamento?: number;
    categoriaIntervento?: string;
    causa?: string;
    cessione?: string;
    codIstat?: string;
    codNuts?: string;
    copAltraPubblica?: string;
    copComunale?: string;
    copComunitaria?: string;
    copPrivata?: string;
    copProvinciale?: string;
    copRegionale?: string;
    copStatale?: string;
    costo?: number;
    cup?: string;
    demolizione?: string;
    descrizione?: string;
    destinazioneUso?: string;
    determinazioni?: string;
    dimensionamentoUm?: string;
    dimensionamentoValore?: number;
    discontinuitaRete?: string;
    finanzaProgetto?: string;
    finanziamento?: number;
    fruibile?: string;
    id?: number;
    idProgramma?: number;
    immobili?: Array<ImmobileEntry>;
    importoInt?: number;
    importoLav?: number;
    importoSal?: number;
    infrastruttura?: string;
    oneriSito?: number;
    oneriUlt?: number;
    requisitiCapitolato?: string;
    requisitiProgetto?: string;
    sponsorizzazione?: string;
    statoReal?: string;
    tipologiaIntervento?: string;
    utilizzoRid?: string;
    vendita?: string;
}

export interface OperaIncompiutaInsertForm {
    ambitoInt?: string;
    annoUltimoQe?: number;
    avanzamento?: number;
    categoriaIntervento?: string;
    causa?: string;
    cessione?: string;
    codIstat?: string;
    codNuts?: string;
    copAltraPubblica?: string;
    copComunale?: string;
    copComunitaria?: string;
    copPrivata?: string;
    copProvinciale?: string;
    copRegionale?: string;
    copStatale?: string;
    costo?: number;
    cup?: string;
    demolizione?: string;
    descrizione?: string;
    destinazioneUso?: string;
    determinazioni?: string;
    dimensionamentoUm?: string;
    dimensionamentoValore?: number;
    discontinuitaRete?: string;
    finanzaProgetto?: string;
    finanziamento?: number;
    fruibile?: string;
    id?: number;
    idProgramma?: number;
    immobili?: Array<ImmobileInsertForm>;
    importoInt?: number;
    importoLav?: number;
    importoSal?: number;
    infrastruttura?: string;
    oneriSito?: number;
    oneriUlt?: number;
    requisitiCapitolato?: string;
    requisitiProgetto?: string;
    sponsorizzazione?: string;
    statoReal?: string;
    tipologiaIntervento?: string;
    utilizzoRid?: string;
    vendita?: string;
    additionalInfo?: any;
}