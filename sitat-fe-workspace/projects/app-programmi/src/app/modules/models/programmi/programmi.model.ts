import { ResponseResult, Tecnico, Ufficio } from '@maggioli/app-commons';

export interface ListaProgrammiRequest {
    searchString?: string;
    stazioneAppaltante?: string;
    tipologia?: string;
    syscon?: string;
}

export interface ResponseListaProgrammi extends ResponseResult<Array<ProgrammaBaseEntry>> {
    totalCount?: number;
}

export interface ProgrammaBaseEntry {
    annoInizio?: number;
    descrizioneBreve?: string;
    id?: number;
    idProgramma?: string;
    idRicevuto?: number;
    syscon?: number;
    tipoProg?: number;
    referenteProgrammazione?: string;
    norma?: number;
}

export interface ProgrammaInsertForm {
    annoInizio?: number;
    dataApprovazione?: Date;
    dataAtto?: Date;
    dataPubblicazioneAdo?: Date;
    dataPubblicazioneAppr?: Date;
    descrzioneBreve?: string;
    id?: number;
    idProgramma?: string;
    idUfficio?: number;
    norma?: number;
    numeroApprovazione?: string;
    numeroAtto?: string;
    pubblica?: string;
    responsabile?: string;
    stazioneappaltante?: string;
    syscon?: number;
    tipoProg?: number;
    titoloAdozione?: string;
    titoloApprovazione?: string;
    urlAdozione?: string;
    urlApprovazione?: string;
}

export interface ProgrammaUpdateForm {
    dataApprovazione?: Date;
    dataAtto?: Date;
    dataPubblicazioneAdo?: Date;
    dataPubblicazioneAppr?: Date;
    descrzioneBreve?: string;
    id?: number;
    numeroApprovazione?: string;
    numeroAtto?: string;
    responsabile?: string;
    titoloAdozione?: string;
    titoloApprovazione?: string;
    urlAdozione?: string;
    urlApprovazione?: string;
}

export interface ProgrammaEntry {
    annoInizio?: number;
    dataApprovazione?: Date;
    dataAtto?: Date;
    dataPubblicazioneAdo?: Date;
    dataPubblicazioneAppr?: Date;
    descrizioneBreve?: string;
    existProgrammaAnnoPrecedente?: boolean;
    id?: number;
    idProgramma?: string;
    idRicevuto?: number;
    idUfficio?: number;
    importoAcquistoMutuo1?: number;
    importoAcquistoMutuo2?: number;
    importoAcquistoMutuo3?: number;
    importoAltreRis1?: number;
    importoAltreRis2?: number;
    importoAltreRis3?: number;
    importoCapitalePr1?: number;
    importoCapitalePr2?: number;
    importoCapitalePr3?: number;
    importoDestVincolo1?: number;
    importoDestVincolo2?: number;
    importoDestVincolo3?: number;
    importoFinanz1?: number;
    importoFinanz2?: number;
    importoFinanz3?: number;
    importoRisorseFinReg?: number;
    importoRisorseFinStato?: number;
    importoStanzBilanciamento1?: number;
    importoStanzBilanciamento2?: number;
    importoStanzBilanciamento3?: number;
    importoTrasfImmobile1?: number;
    importoTrasfImmobile2?: number;
    importoTrasfImmobile3?: number;
    norma?: number;
    numeroApprovazione?: string;
    numeroAtto?: string;
    pubblica?: string;
    responsabile?: string;
    rup?: Tecnico;
    stazioneappaltante?: string;
    showConfrontoProgrammi?: boolean;
    tipoApprovazione?: number;
    tipoAtto?: number;
    tipoProg?: number;
    titoloAdozione?: string;
    titoloApprovazione?: string;
    totaleRisDisp1?: number;
    totaleRisDisp2?: number;
    totaleRisDisp3?: number;
    ufficio?: Ufficio;
    urlAdozione?: string;
    urlApprovazione?: string;
    showUfficio?: boolean;
}

/**
 * Contenitore per i dati necessari ad aggiornare l'id di pubblicazione di un avviso
 */
export interface ProgrammaPubblicatoForm {
    /**
     * Id programma
     */
    id?: number;
    /**
     * Id generato in fase di pubblicazione
     */
    idRicevuto?: number;
    /**
     * Codice stazione appaltante del programma
     */
    stazioneAppaltante?: string;
    /**
     * syscon dell'autore dell'invio
     */
    syscon?: string;
    /**
     * syscon dell'autore dell'invio
     */
     payload?: string;
}

export interface CambioSysconForm {
    contri?: number;
    stazioneAppaltante?: string;
    syscon?: number;
}

export interface InizNuovoProgramma {
    showUfficio? :boolean;
	denomUfficio? :string;
	idUfficio? :string;
}

export interface ListaInterventiFilter {
    annualita?: string;
    codInterno?: string;
    codiceCUP?: string;
    descrizione?: string;
    importoTotaleDa?: string;
    importoTotaleA?: string;
    numeroCui?: string;
    rup?: string;
}

export interface InterventiDaConfrontoDTO { 
    cui?: string;
    descrizione?: string;
    descrizioneTabellato?: string;
    variazione?: InterventiDaConfrontoDTO.VariazioneEnum;
    warningVariatoNull?: boolean;
}
export namespace InterventiDaConfrontoDTO {
    export type VariazioneEnum = 'AGGIUNTO' | 'ELIMINATO' | 'TABELLATO' | 'VARIATA_ANNUALITA' | 'VARIATO_QUADRO_ECONOMICO';
    export const VariazioneEnum = {
        AGGIUNTO: 'AGGIUNTO' as VariazioneEnum,
        ELIMINATO: 'ELIMINATO' as VariazioneEnum,
        TABELLATO: 'TABELLATO' as VariazioneEnum,
        VARIATAANNUALITA: 'VARIATA_ANNUALITA' as VariazioneEnum,
        VARIATOQUADROECONOMICO: 'VARIATO_QUADRO_ECONOMICO' as VariazioneEnum
    };
}