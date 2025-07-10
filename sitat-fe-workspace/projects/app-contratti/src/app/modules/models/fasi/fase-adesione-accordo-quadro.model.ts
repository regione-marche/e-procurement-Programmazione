
export interface FaseAdesioneAccordoQuadroEntry {
    codGara?: number;
    codLotto?: number;
    codStrumento?: string;
    dataAtto?: Date;
    dataVerbAggiudicazione?: Date;
    flagRichSubappalto?: string;
    impNonAssog?: number;
    importoAggiudicazione?: number;
    importoComplAppalto?: number;
    importoComplIntervento?: number;
    importoForniture?: number;
    importoLavori?: number;
    importoProgettazione?: number;
    importoServizi?: number;
    importoSicurezza?: number;
    importosubtotale?: number;
    numeroAtto?: string;
    percOffAumento?: number;
    percentRibassoAgg?: number;
    pubblicata?: boolean;
    tipoAtto?: number;
}

export interface FaseAdesioneAccordoQuadroInsertForm {
    codGara?: number;
    codLotto?: number;
    codStrumento?: string;
    dataAtto?: Date;
    dataVerbAggiudicazione?: Date;
    flagRichSubappalto?: string;
    impNonAssog?: number;
    importoAggiudicazione?: number;
    importoComplAppalto?: number;
    importoComplIntervento?: number;
    importoForniture?: number;
    importoLavori?: number;
    importoProgettazione?: number;
    importoServizi?: number;
    importoSicurezza?: number;
    importosubtotale?: number;
    num?: number;
    numeroAtto?: string;
    percOffAumento?: number;
    percentRibassoAgg?: number;
    tipoAtto?: number;
}
