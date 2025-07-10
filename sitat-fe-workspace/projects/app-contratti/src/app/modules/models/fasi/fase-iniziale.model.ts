import { AssociazionePagamentiEntry } from "../gare/gare.model";
import { ImpresaAggiudicatariaEntry } from "../imprese-aggiudicatarie/imprese-aggiudicatarie.model";

export interface FaseInizialeEsecuzioneEntry {
    codGara?: number;
    codLotto?: number;
    dataApprovazioneProg?: Date;
    dataEsecutivita?: Date;
    dataInizioProg?: Date;
    dataStipula?: Date;
    dataTermine?: Date;
    dataVerbInizio?: Date;
    dataVerbaleCons?: Date;
    dataVerbaleDef?: Date;
    frazionata?: string;
    importoCauzione?: number;
    incontriVigil?: number;
    pubblicata?: boolean;
    pubblicazioneEsito?: FaseInizPubbEsitoForm;
    riserva?: string;    
    dataScadenza?: Date;
    dataDecorrenza?: Date;
}

export interface InizFaseAdesioneAccordoQuadroEntry {
	percRibassoAgg?: number;
	percOffAumento?: number;
	dataVerbaleAgg?: Date;
	importoAgg?: number;
	flagSubappalto?: string;
    inibita?:string;

}

export interface InizFaseAggiudicazioneEntry {
	percRibassoAgg?: number;	
	dataVerbaleAgg?: Date;
	importoAgg?: number;
    inibita?:string;	
}

export interface InizFaseAggiudicazioneSempEntry {
	percRibassoAgg?: number;	
	dataVerbaleAgg?: Date;
	importoAgg?: number;	
    inibita?:string;
   
}



export interface FaseInizialeEsecuzioneInsertForm {
    codGara?: number;
    codLotto?: number;
    dataApprovazioneProg?: Date;
    dataEsecutivita?: Date;
    dataInizioProg?: Date;
    dataStipula?: Date;
    dataTermine?: Date;
    dataVerbInizio?: Date;
    dataVerbaleCons?: Date;
    dataVerbaleDef?: Date;
    frazionata?: string;
    importoCauzione?: number;
    incontriVigil?: number;
    num?: number;
    pubblicazioneEsito?: FaseInizPubbEsitoForm;
    riserva?: string;
    dataScadenza?: Date;
    dataDecorrenza?: Date;
}

export interface FaseInizPubbEsitoForm {
    codGara?: number;
    codLotto?: number;
    counter?: number;
    dataAlbo?: Date;
    dataGuce?: Date;
    dataGuri?: Date;
    numIniziale?: number;
    profiloCommittente?: string;
    quotidianiNaz?: number;
    quotidianiReg?: number;
    sitoMinInfTrasp?: string;
    sitoOsservatorio?: string;
}

export interface InizFaseInizialeContrattoEntry {
    dataVerbale?: Date;
    incontriVigilVisible?: boolean;
}
