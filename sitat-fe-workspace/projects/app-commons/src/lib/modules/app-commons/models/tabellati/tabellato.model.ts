export interface TabellatoInfo {
    data: Array<ValoreTabellato>;
    expiration?: Date;
}

export interface ValoreTabellato {
    archiviato: string;
    cap?: string;
    codice: string;
    codiceProvincia?: string;
    codistat?: string;
    descrizione: string;
}

export interface ListaTecniciRequest {
    searchString?: string;
    stazioneAppaltante: string;
    codiceFiscale?: string;
}

export interface RupEntry {
    /**
     * CAP del RUP
     */
    cap?: string;
    /**
     * CF del RUP
     */
    cf?: string;
    /**
     * Codice Istat comune del RUP
     */
    codIstat?: string;
    /**
     * Codice del RUP
     */
    codice?: string;
    /**
     * Cognome del RUP
     */
    cognome?: string;
    /**
     * Comune del RUP
     */
    comune?: string;
    /**
     * Flag che determina se il tecnico possa essere cancellato
     */
    deletable?: boolean;
    /**
     * Email del RUP
     */
    email?: string;
    /**
     * Fax del RUP
     */
    fax?: string;
    /**
     * Indirizzo del RUP
     */
    indirizzo?: string;
    /**
     * Nome del RUP
     */
    nome?: string;
    /**
     * Nominativo del RUP
     */
    nominativo?: string;
    /**
     * Numero civico del RUP
     */
    numCivico?: string;
    /**
     * Provincia del RUP
     */
    provincia?: string;
    /**
     * Codice stazione appaltante del RUP
     */
    stazioneAppaltante?: string;
    /**
     * Telefono del RUP
     */
    telefono?: string;
}

export interface ResponseListaRupPaginata {
    /**
     * Dati dei tecnici
     */
    data?: Array<RupEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale dei tecnici estraibili dalla form di ricerca
     */
    totalCount?: number;
}

export interface InfoCampo {
    campo?: string;
    chiaveEntita?: Array<string>;
    descrizione?: string;
    descrizioneEntita?: string;
    descrizioneSchema?: string;
    entita?: string;
    formato?: string;
    mnemonico?: string;
    schema?: string;
    valoriTabellati?: Array<string>;
}

export interface MessageReceiverEntry {
    nominativo?: string;
    syscon?: number;
}

export interface MessageEntry {
    corpoMessaggio?: string;
    dataMessaggio?: Date;
    id?: number;
    infoSender?: string;
    letto?: number;
    oggetto?: string;
    sysconSender?: number;
}

export interface MessageForm {
    corpoMessaggio?: string;
    dataMessaggio?: Date;
    id?: number;
    oggetto?: string;
    sysconReceivers?: Array<number>;
    sysconSender?: number;
}

export interface SAEntry {
    /**
     * CAP stazione appaltante
     */
    capein?: string;
    /**
     * CF stazione appaltante
     */
    cfein?: string;
    /**
     * Comune stazione appaltante
     */
    citein?: string;
    /**
     * Codice stazione appaltante
     */
    codein?: string;
    /**
     * Cod istat comune stazione appaltante
     */
    codcit?: string;
    /**
     * Email stazione appaltante
     */
    emai2in?: string;
    /**
     * Fax stazione appaltante
     */
    faxein?: string;
    /**
     * Numero civico stazione appaltante
     */
    nciein?: string;
    /**
     * Nome stazione appaltante
     */
    nomein?: string;
    /**
     * Provincia stazione appaltante
     */
    proein?: string;
    /**
     * Telefono stazione appaltante
     */
    telein?: string;
    /**
     * Indirizzo stazione appaltante
     */
    viaein?: string;
}

export interface GaraEntry {
    centroDicosto?: CentriCostoEntry;
    cfAgenteStazioneAppaltante?: string;
    /**
     * Codici CIG dei lotti della gara
     */
    cigLotti?: string;
    cigQuadro?: string;
    /**
     * codice univoco della gara
     */
    codgara?: number;
    codiceCentroCosto?: string;
    codiceStazioneAppaltante?: string;
    codiceTecnico?: string;
    comuneSede?: string;
    dataPubblicazione?: Date;
    /**
     * flag cancellazione abilitata/disabilitata
     */
    deletable?: boolean;
    denomSoggStazioneAppaltante?: string;
    dispArtDlgs?: number;
    durataAcquadro?: number;
    flagSaAgente?: number;
    flagStipula?: number;
    idCentroDiCosto?: number;
    idRicevuto?: number;
    idUfficio?: number;
    identificativoGara?: string;
    /**
     * importo della gara
     */
    importoGara?: number;
    indirizzoSede?: string;
    modalitaIndizione?: number;
    nominativoCentroCosto?: string;
    nominativoStazioneAppaltante?: string;
    nominativoUfficio?: string;
    numLotti?: number;
    /**
     * oggetto di gara
     */
    oggetto?: string;
    provenienzaDato?: number;
    provinciaSede?: string;
    ricostruzioneAlluvione?: number;
    /**
     * RUP della gara
     */
    rup?: string;
    sisma?: number;
    /**
     * situazione di gara
     */
    situazione?: number;
    /**
     * flag che identifica se la gara è uno SMART CIG
     */
    smartCig?: boolean;
    sommaUrgenza?: number;
    syscon?: number;
    tecnico?: RupEntry;
    /**
     * tipologia di gara
     */
    tipoApp?: number;
    tipoSettore?: string;
    tipologiaProcedura?: string;
    tipologiaStazioneAppaltante?: number;
    /**
     * flag trasferimento RUP abilitato/disabilitato
     */
    trasferimentoRUP?: boolean;
    ufficio?: UffEntry;
    versioneSimog?: number;
    idFDelegate?: number;
    codlott?: string;
    dataLetteraInvito?: Date;    
    pubblicitaGara?: PubblicitaGaraEntry;
	perfezionata?: boolean;
	visibleDataLetteraInvito?: boolean;
}

export interface CentriCostoEntry {
    id?: number;
    codiceCDC?: string;
    nominativoCDC?: string;
    note?: string;
    tipologia?: string;
}
export interface UffEntry {
    /**
     * denominazione dell'ufficio
     */
    denominazione?: string;
    /**
     * id dell'ufficio
     */
    id?: number;
    /**
     * codice stazione appaltante dell'ufficio
     */
    stazioneAppaltante?: string;
}

export interface PubblicitaGaraEntry {
    alboPretorioComuni?: Date;
    codiceGara?: number;
    dataBollettino?: Date;
    gazzettaUffEuropea?: Date;
    gazzettaUffItaliana?: Date;
    numPeriodici?: number;
    numQuotidianiLocali?: number;
    numQuotidianiNazionali?: number;
    profiloCommittente?: string;
    sitoMinisteriInfr?: string;
    sitoOsservatorioContratti?: string;
}