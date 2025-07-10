import { RupEntry, Ufficio } from '@maggioli/app-commons';

export interface SmartCigInsertForm {
    categoriaPrev?: string;
    cfAgenteStazioneAppaltante?: string;
    classeCategoriaPrev?: string;
    comuneSede?: string;
    cpv?: string;
    criteriAggiudicazione?: number;
    cup?: string;
    denomSoggStazioneAppaltante?: string;
    esenteCup?: string;
    flagSaAgente?: number;
    flagStipula?: number;
    idUfficio?: number;
    importoNetto?: number;
    importoSicurezza?: number;
    importoTotale?: number;
    indirizzoSede?: string;
    luogoIstat?: string;
    luogoNuts?: string;
    modalitaRealizzazione?: number;
    oggetto?: string;
    provinciaSede?: string;
    rup?: string;
    sceltaContraente?: number;
    smartCig?: string;
    stazioneAppaltante?: string;
    tipoAppalto?: string;
    tipoSettore?: string;
    tipologiaProcedura?: number;
    tipologiaStazioneAppaltante?: number;
    syscon?: number;
    dataUltimazione?: string;
	dataVerbInizio?: string ;
	importoCertificato?: number;
}

export interface SmartCigEntry {
    categoriaPrev?: string;
    cfAgenteStazioneAppaltante?: string;
    classeCategoriaPrev?: string;
    codGara?: number;
    comuneSede?: string;
    cpv?: string;
    criteriAggiudicazione?: number;
    cup?: string;
    denomSoggStazioneAppaltante?: string;
    descCpv?: string;
    esenteCup?: string;
    flagSaAgente?: number;
    flagStipula?: number;
    idUfficio?: number;
    importoNetto?: number;
    importoSicurezza?: number;
    importoTotale?: number;
    indirizzoSede?: string;
    luogoIstat?: string;
    luogoNuts?: string;
    modalitaRealizzazione?: number;
    nominativoStazioneAppaltante?: string;
    oggetto?: string;
    provinciaSede?: string;
    rup?: RupEntry;
    sceltaContraente?: number;
    smartCig?: string;
    stazioneAppaltante?: string;
    tipoAppalto?: string;
    tipoSettore?: string;
    tipologiaProcedura?: string;
    tipologiaStazioneAppaltante?: number;
    ufficio?: Ufficio;
    situazione?: number;
    dataUltimazione?: string;
	dataVerbInizio?: string ;
	importoCertificato?: number;
    daExport?: number;
    readOnly?: boolean;
}

export interface SmartCigUpdateForm {
    categoriaPrev?: string;
    cfAgenteStazioneAppaltante?: string;
    classeCategoriaPrev?: string;
    codGara?: number;
    comuneSede?: string;
    cpv?: string;
    criteriAggiudicazione?: number;
    cup?: string;
    denomSoggStazioneAppaltante?: string;
    esenteCup?: string;
    flagSaAgente?: number;
    flagStipula?: number;
    idUfficio?: number;
    importoNetto?: number;
    importoSicurezza?: number;
    importoTotale?: number;
    indirizzoSede?: string;
    luogoIstat?: string;
    luogoNuts?: string;
    modalitaRealizzazione?: number;
    oggetto?: string;
    provinciaSede?: string;
    rup?: string;
    sceltaContraente?: number;
    smartCig?: string;
    tipoAppalto?: string;
    tipoSettore?: string;
    tipologiaProcedura?: number;
    tipologiaStazioneAppaltante?: number;
    dataUltimazione?: string;
	dataVerbInizio?: string ;
	importoCertificato?: number;
}

export interface ImportSmartcigIniz{
    simogUser?:string;
    simogPassword?:string;
}
