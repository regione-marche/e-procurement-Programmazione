//#region DTO per un singolo report proveniente dalla WRicerche
export interface WRicercheDTO{
    idRicerca: number;
    nome: string;
    descrizione: string;
    pubblicato: string;
    sysute: string;
    codApp?: string;
    tipo?: string;
    valoriDistinti?: number;
    risperpag?: number;
    vismodelli?: number;
    entprinc?: string;
    ownerTry?: number;
    famiglia?: number;
    idProspetto?: number;
    personale?: number;
    filtroUtente?: number;
    profiloOwner?: string;
    visparam?: number;
    linkScheda?: number;
    filtroUffInt?: number;
    defSql?: string;
    tuttiProfili?: number;
    tuttiUffici?: number;
    syscon?: number;
    codReportWs?: string;
    esponiComeServizio?: string;
    schedula?: string;
    cronExpression?: string;
    formatoSchedulazione?: string;
    emailSchedulazioneRisultato?: string;
    noOutputVuoto?: number;
    contieneParametri?: boolean;
    isPreferito?: boolean;
}
//#endregion