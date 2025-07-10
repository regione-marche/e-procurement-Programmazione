import { RupEntry, Ufficio } from '@maggioli/app-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { AllegatoEntry, AllegatoMotivazioneEntry } from '../atti-generali/atti-generali.model';

export interface ListaGareParams {
    archiviate?: boolean;
    cigLotto?: string;
    codiceTecnico?: string;
    cupLotto?: string;
    modalitaRealizzazione?: number;
    numGara?: string;
    rup?: Array<string>;
    oggetto?: string;
    oggettoLotto?: string;
    proceduraContraenteLotto?: number;
    descrizione?: string;
    situazioneGara?: number;
    skip?: number;
    sort?: string;
    sortDirection?: string;
    stazioneAppaltante?: string;
    syscon?: number;
    take?: number;
    tipoAppaltoLotto?: string;
    dataDa?:Date;
    dataA?:Date;
    importoGaraDa?:number;
    importoGaraA?:number;
    importoLottoDa?:number;
    importoLottoA?:number;
    cfTecnico?: string;
}

export interface RicercaGareForm {
    archiviate?: boolean;
    cigLotto?: string;
    codiceTecnico?: string;
    cupLotto?: string;
    modalitaRealizzazione?: number;
    numGara?: string;
    oggetto?: string;
    oggettoLotto?: string;
    proceduraContraenteLotto?: number;
    searchString?: string;
    situazioneGara?: number;
    tipoAppaltoLotto?: string;
    advancedSearch?: string;
    dataDa?:Date;
    dataA?:Date;
    cfTecnico?: string;
    rup?: Array<string>;
    rupData?:Array<SdkAutocompleteItem>;
    stazioneAppaltante?: string;
    stazioneAppaltanteData?:Array<SdkAutocompleteItem>;
}

export interface ResponseListaGare {
    /**
     * List gare
     */
    data?: Array<GaraBaseEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale delle gare estraibili dalla form di ricerca
     */
    totalCount?: number;
}

export interface ResponseExportCsv {
    /**
     * CSV File
     */
    data?: string;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;   
    /**
     * numero di gare estratte
     */
    rowNumber?: number;
}

export interface GaraBaseEntry {
    /**
     * Codici CIG dei lotti della gara
     */
    cigLotti?: string;
    /**
     * codice univoco della gara
     */
    codgara?: number;
    /**
     * flag cancellazione abilitata/disabilitata
     */
    deletable?: boolean;
    /**
     * numero ANAC della gara
     */
    identificativoGara?: string;
    /**
     * importo della gara
     */
    importoGara?: number;
    /**
     * oggetto di gara
     */
    oggetto?: string;
    /**
     * RUP della gara
     */
    rup?: string;
    /**
     * situazione di gara
     */
    situazione?: number;
    /**
     * flag che identifica se la gara è uno SMART CIG
     */
    smartCig?: boolean;
    /**
     * tipologia di gara
     */
    tipoApp?: number;
    /**
     * flag trasferimento RUP abilitato/disabilitato
     */
    trasferimentoRUP?: boolean;

    dataPubblicazione?: Date;

    codlott?: number;
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
    idFDelegateDescription?: string;
    codlott?: string;
    dataLetteraInvito?: Date;    
    pubblicitaGara?: PubblicitaGaraEntry;
	perfezionata?: boolean;
	visibleDataLetteraInvito?: boolean;
    readOnly?: boolean;
    ruoloCollaboratore?: number;
    sceltaContraenteValorizzata?: boolean;
    modalitaIndizioneAllegato9?: number;
    motivoSommaUrgenza?: number;
    sceltaContra4?: boolean;
    pcp?: boolean;
    drp?: string;
    delegato?: RupEntry;
    riallineaVisible?: boolean;
    existsFasiNonPubb?: boolean;
    dataScadPresentazioneOfferta?: Date;
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

export interface CentriCostoEntry {
    id?: number;
    codiceCDC?: string;
    nominativoCDC?: string;
    note?: string;
    tipologia?: string;
}

export interface ResponseListaLotti {
    /**
     * Dati base dei lotti
     */
    data?: Array<LottoBaseEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale dei lotti estraibili dalla form di ricerca
     */
    totalCount?: number;
}



export interface LottoBaseEntry {
    /**
     * cig del lotto
     */
    cig?: string;
    /**
     * codice univioco del lotto per la gara
     */
    codLotto?: string;
    /**
     * codice gara del lotto
     */
    codgara?: string;
    /**
     * flag cancellazione abilitata/disabilitata
     */
    deletable?: boolean;
    /**
     * importo netto del lotto
     */
    importoNetto?: number;
    lottiAccorpati?: Array<LottoBaseEntry>;
    /**
     * cig master in caso di lotti accorpati
     */
    masterCig?: string;
    /**
     * Numero lotto
     */
    numLotto?: number;
    /**
     * oggetto del lotto
     */
    oggetto?: string;
    /**
     * situazione del lotto
     */
    situazione?: number;
    /**
     * tipologia del lotto
     */
    tipologia?: string;
}


export interface AttoEntry {
    campiObbligatori?: string;
    campiVisibili?: string;
    id?: number;
    nome?: string;
    statiId?: StatiId;
    tipo?: string;
}

export interface StatiId {
    ids?: Array<IdPubblicati>;
    stato?: string;
}

export interface IdPubblicati {
    dataPubblicazione?: Date;
    descrizione?: string;
    primaPubblicazione?: string;
	dataPubbSistema?: Date;
	utenteCancellazione?: number;
	dataCancellazione?: Date;
	motivoCancellazione?: string;
    id?: number;
    pubblicato?: boolean;
    parziale?: boolean;
	countParziale?: number;
	totalParziale?: number;
    pubblicatoAtto?: boolean;
    annullato?: boolean;
}

export interface AttoInsertForm {
    /**
     * Codice gara dell'atto
     */
    codGara?: number;
    /**
     * codice lotto dell'atto
     */
    codLotto?: number;
    /**
     * data decreto dell'atto
     */
    dataDecreto?: Date;
    /**
     * data provvedimento dell'atto
     */
    dataProvvedimento?: Date;
    /**
     * data pubblicazione dell'atto
     */
    dataPubb?: Date;
    /**
     * data scadenza dell'atto
     */
    dataScad?: Date;
    /**
     * data stipula dell'atto
     */
    dataStipula?: Date;
    /**
     * data verbale aggiudicazione dell'atto
     */
    dataVerbAggiudicazione?: Date;
    /**
     * descrizione dell'atto
     */
    descriz?: string;
    /**
     * documenti dell'atto
     */
    documents?: Array<AttoDocument>;
    /**
     * importo aggiudicazione dell'atto
     */
    importoAggiudicazione?: number;
    /**
     * numero provvedimento dell'atto
     */
    numProvvedimento?: string;
    /**
     * numero pubblicazione dell'atto
     */
    numPubb?: number;
    /**
     * numero repertorio dell'atto
     */
    numRepertorio?: string;
    /**
     * percentuale offerta aumento dell'atto
     */
    percOffAumento?: number;
    /**
     * percentuale ribasso aggiudicazione dell'atto
     */
    percRibassoAgg?: number;
    /**
     * tipologia di'atto
     */
    tipDoc?: number;
    /**
     * url del committente dell'atto
     */
    urlCommittente?: string;
    /**
     * url eproc dell'atto
     */
    urlEproc?: string;
    /**
     * Combobox per indicare se è o meno una prima pubblicazione
     */
    primaPubblicazione?: string;
    /**
     * Data di pubblicazione sul sistema
     */
	dataPubbSistema?: Date;
    /**
     * Syscon dell'utente che annulla la pubblicazione di un atto
     */
	utenteCancellazione?: number;
    /**
     * Data in cui viene annullata la pubblicazione di un atto
     */
	dataCancellazione?: Date;
    /**
     * Motivo di cancellazione di un atto
     */
	motivoCancellazione?: string;
    /**
     * Nuovi documenti da inserire per il nuovo atto
     */
    updateDocuments?: Array<AllegatoEntry>;
}

export interface AttoUpdateForm {
    /**
     * Codice gara dell'atto
     */
    codGara?: number;
    /**
     * codice lotto dell'atto
     */
    codLotto?: number;
    /**
     * data decreto dell'atto
     */
    dataDecreto?: Date;
    /**
     * data provvedimento dell'atto
     */
    dataProvvedimento?: Date;
    /**
     * data pubblicazione dell'atto
     */
    dataPubb?: Date;
    /**
     * data scadenza dell'atto
     */
    dataScad?: Date;
    /**
     * data stipula dell'atto
     */
    dataStipula?: Date;
    /**
     * data verbale aggiudicazione dell'atto
     */
    dataVerbAggiudicazione?: Date;
    /**
     * descrizione dell'atto
     */
    descriz?: string;
    /**
     * documenti esistenti dell'atto
     */
    existingDocuments?: Array<ExistingAttoDocument>;
    /**
     * documenti nuovi dell'atto
     */
    newDocuments?: Array<AttoDocument>;
    /**
     * importo aggiudicazione dell'atto
     */
    importoAggiudicazione?: number;
    /**
     * numero provvedimento dell'atto
     */
    numProvvedimento?: string;
    /**
     * numero pubblicazione dell'atto
     */
    numPubb?: number;
    /**
     * numero repertorio dell'atto
     */
    numRepertorio?: string;
    /**
     * percentuale offerta aumento dell'atto
     */
    percOffAumento?: number;
    /**
     * percentuale ribasso aggiudicazione dell'atto
     */
    percRibassoAgg?: number;
    /**
     * tipologia di'atto
     */
    tipDoc?: number;
    /**
     * url del committente dell'atto
     */
    urlCommittente?: string;
    /**
     * url eproc dell'atto
     */
    urlEproc?: string;
    /**
     * Combobox per indicare se è o meno una prima pubblicazione
     */
    primaPubblicazione?: string;
    /**
     * Data di pubblicazione sul sistema
     */
	dataPubbSistema?: Date;
    /**
     * Syscon dell'utente che annulla la pubblicazione di un atto
     */
	utenteCancellazione?: number;
    /**
     * Data in cui viene annullata la pubblicazione di un atto
     */
	dataCancellazione?: Date;
    /**
     * Motivo di cancellazione di un atto
     */
	motivoCancellazione?: string;
    /**
     * Documenti esistenti dell'atto 
     */
    documents?: Array<AllegatoEntry>;
    /**
     * Nuovi documenti dell'atto
     */
    updateDocuments?: Array<AllegatoEntry>;
    /**
     * Allegati da annullare
     */
    allegatiDaAnnullare?: Array<AllegatoMotivazioneEntry>;
}

export interface AttoDocument {
    /**
     * contenuto del file (stringa)
     */
    binary?: string;
    /**
     * codice gara dell'atto
     */
    codGara?: number;
    /**
     * contenuto del file (byte array)
     */
    fileAllegato?: string;
    /**
     * numero univoco del documento
     */
    numDoc?: number;
    /**
     * numero pubblicazione dell'atto
     */
    numPubb?: number;
    /**
     * titolo dell'atto
     */
    titolo?: string;
    /**
     * url del documento
     */
    url?: string;
    tipoFile?: string;
}


export interface DettaglioAttoEntry {
    codGara?: number;
    countLotti?: number;
    dataDecreto?: Date;
    dataProvvedimento?: Date;
    dataPubb?: Date;
    dataScad?: Date;
    dataStipula?: Date;
    dataVerbAggiudicazione?: Date;
    deletable?: boolean;
    descriz?: string;
    existingDocuments?: Array<ExistingAttoDocument>;
    idGenerato?: number;
    idRicevuto?: number;
    importoAggiudicazione?: number;
    numProvvedimento?: string;
    numPubb?: number;
    numRepertorio?: string;
    percOffAumento?: number;
    percRibassoAgg?: number;
    tipDoc?: number;
    urlCommittente?: string;
    urlEproc?: string;
    primaPubblicazione?: string;
	dataPubbSistema?: Date;
	utenteCancellazione?: number;
	dataCancellazione?: Date;
	motivoCancellazione?: string;
    documents?: Array<AllegatoEntry>;
}

export interface ExistingAttoDocument {
    /**
     * codice gara dell'atto
     */
    codGara?: number;
    /**
     * numero univoco del documento
     */
    numDoc?: number;
    /**
     * numero pubblicazione dell'atto
     */
    numPubb?: number;
    /**
     * titolo dell'atto
     */
    titolo?: string;
    tipoFile?: string;
    /**
     * url del documento
     */
    url?: string;
}

export interface LottoEntry {
    artE1?: string;
    categoriaPrev?: string;
    /**
     * cig del lotto
     */
    cig?: string;
    classeCategoriaPrev?: string;
    /**
     * codice univioco del lotto per la gara
     */
    codLotto?: string;
    /**
     * codice gara del lotto
     */
    codgara?: string;
    condizioniRicorsoProcNeg?: Array<LottoDynamicValue>;
    contrattoEscluso?: string;
    contrattoEscluso161718?: string;
    contrattoEsclusoAlleggerito?: string;
    cpv?: string;
    cpvSecondari?: Array<CPVSecondario>;
    criteriAggiudicazione?: number;
    cui?: string;
    cup?: string;
    /**
     * flag cancellazione abilitata/disabilitata
     */
    deletable?: boolean;
    descCpv?: string;
    esclusioneRegimeSpeciale?: string;
    esenteCup?: string;
    exsottosoglia?: string;
    idSchedalocale?: string;
    /**
     * importo netto del lotto
     */
    importoNetto?: number;
    importoSicurezza?: number;
    importoTotale?: number;
    lottiAccorpati?: Array<LottoBaseEntry>;
    luogoIstat?: string;
    luogoNuts?: string;
    manodopera?: string;
    /**
     * cig master in caso di lotti accorpati
     */
    masterCig?: string;
    modalitaAcquisizione?: Array<LottoDynamicValue>;
    modalitaTipologiaLavoro?: Array<LottoDynamicValue>;
    /**
     * Numero lotto
     */
    numLotto?: number;
    /**
     * oggetto del lotto
     */
    oggetto?: string;
    prestazioneComprese?: number;
    rup?: string;
    sceltaContraente?: number;
    sceltaContraenteLgs?: number;
    /**
     * situazione del lotto
     */
    situazione?: number;
    tipoSettore?: string;
    /**
     * tipologia del lotto
     */
    tipologia?: string;
    ulterioriCategorie?: Array<CategoriaLotto>;
    urlEproc?: string;
    qualificazioneSa?: number;
	categoriaMerceologica?: number;
	flagPrevedeRip?: string;
	durataRinnovi?: number;
	motivoCollegamento?: number;
	cigOrigineRip?: string;
	flagPnrrPnc?: string;
	flagPrevisioneQuota?: string;
	flagMisurePreliminari?: string;
    listaCup?:string;
    dataScadenzaPagamenti?: string;    
    sottoSoglia?: boolean;
    hasAggiudicazione?: boolean;
}

export interface LottoDynamicValue {
    codice?: number;
    descrizione?: string;
    value?: number;
}

export interface FaseVarianteIniz {
    motivazioni: Array<LottoDynamicValue>;
    countW9moti: number;
}

export interface CategoriaLotto {
    categoria?: string;
    classe?: string;
    scorporabile?: string;
    subappaltabile?: string;
}

export interface CPVSecondario {
    codCpv?: string;
    descCpv?: string;
}

export interface AttoLottoEntry {
    /**
     * flag che identifica se l'atto sia associato al lotto
     */
    associato?: boolean;
    /**
     * cig del lotto
     */
    cig?: string;
    /**
     * codice univioco del lotto per la gara
     */
    codLotto?: string;
    /**
     * codice gara del lotto
     */
    codgara?: string;
    /**
     * importo netto del lotto
     */
    importoNetto?: number;
    /**
     * oggetto del lotto
     */
    oggetto?: string;
    /**
     * situazione del lotto
     */
    situazione?: string;
    /**
     * tipologia del lotto
     */
    tipologia?: string;
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

export interface PubblicitaGaraInsertForm {
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

export interface GaraUpdateForm {
    cfAgenteStazioneAppaltante?: string;
    codGara?: number;
    codiceTecnico?: string;
    comuneSede?: string;
    denomSoggStazioneAppaltante?: string;
    flagSaAgente?: number;
    flagStipula?: number;
    importoGara?: number;
    indirizzoSede?: string;
    provinciaSede?: string;
    ricostruzioneAlluvione?: number;
    tipoSettore?: string;
    tipologiaProcedura?: number;
    tipologiaStazioneAppaltante?: number;
    durataAcquadro?: number;
    pubblicitaGara?: PubblicitaGaraInsertForm;
    dataLetteraInvito?: string;
}

export interface LottoInsertForm {
    categoriaPrev?: string;
    cig?: string;
    classeCategoriaPrev?: string;
    codGara?: number;
    codLotto?: number;
    condizioniRicorsoProcNeg?: Array<LottoDynamicValue>;
    contrattoEscluso?: string;
    cpv?: string;
    cpvSecondari?: Array<CPVSecondario>;
    criteriAggiudicazione?: number;
    cui?: string;
    cup?: string;
    esenteCup?: string;
    exsottosoglia?: string;
    idSchedalocale?: string;
    importoNetto?: number;
    importoSicurezza?: number;
    importoTotale?: number;
    luogoIstat?: string;
    luogoNuts?: string;
    manodopera?: string;
    modalitaAcquisizione?: Array<LottoDynamicValue>;
    modalitaTipologiaLavoro?: Array<LottoDynamicValue>;
    numLotto?: number;
    oggetto?: string;
    prestazioneComprese?: number;
    rup?: string;
    sceltaContraente?: number;
    sceltaContraenteLgs?: number;
    situazione?: number;
    tipoSettore?: string;
    tipologia?: string;
    ulterioriCategorie?: Array<CategoriaLotto>;
    urlEproc?: string;
}

export interface GaraInsertForm {
    altreSA?: string;
    cfAgenteStazioneAppaltante?: string;
    cigQuadro?: string;
    /**
     * codice univoco della gara
     */
    codgara?: number;
    codiceStazioneAppaltante?: string;
    codiceTecnico?: string;
    comuneSede?: string;
    dataPubblicazione?: Date;
    dataScadenza?: Date;
    denomSoggStazioneAppaltante?: string;
    dispArtDlgs?: number;
    durataAcquadro?: number;
    flagSaAgente?: number;
    flagStipula?: number;
    idCentroDiCosto?: number;
    idUfficio?: number;
    identificativoGara?: string;
    /**
     * importo della gara
     */
    importoGara?: number;
    indirizzoSede?: string;
    modalitaIndizione?: number;
    numLotti?: number;
    /**
     * oggetto di gara
     */
    oggetto?: string;
    provenienzaDato?: number;
    provinciaSede?: string;
    ricostruzioneAlluvione?: number;
    sisma?: number;
    /**
     * situazione di gara
     */
    situazione?: number;
    sommaUrgenza?: number;
    syscon?: number;
    /**
     * tipologia di gara
     */
    tipoApp?: number;
    tipoSettore?: string;
    tipologiaProcedura?: number;
    tipologiaStazioneAppaltante?: number;
    versioneSimog?: number;
    
    pubblicitaGara?: PubblicitaGaraInsertForm;
}

export interface ResponseInizInsertLotto {
    condizioniRicorsoProcNeg?: Array<TabellatoEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    modalitaAcquisizione?: Array<TabellatoEntry>;
    modalitaTipologiaLavoro?: Array<TabellatoEntry>;
}

export interface TabellatoEntry {
    /**
     * flag che stabilisce se il valore tabellato è ancora valido oppure no
     */
    archiviato?: number;
    /**
     * Codice tabellato
     */
    codice?: number;
    /**
     * Descrizione tabellato
     */
    descrizione?: string;
}

export interface ResponseListaInviiFasi {
    data?: Array<FullFlussiFase>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
    /**
     * Numero totale degli interventi non riproposti estraibili dalla form di ricerca
     */
    totalCount?: number;
    /**
     * Info eventuali aggiuntive sulla gara
     */
    moreInfoGara?: any;
}

export interface ImpegnoEntry {
	codiceImpegno?: string;
	dataImpegno?: Date;
	importo?: number;
	descrizione?: string;
	atto?: string;
	impresa?: string;
}

export interface PagamentoEntry {
    selected?: boolean;
    idx: number;
    codicePagamento?: string;
	dataPagamento?: Date;
	importo?: number;
	oggetto?: string;	
	impresa?: string;
    utilizzato?: string;
}


export interface AssociazionePagamentiEntry {
    codeCampo?: string;
	nomeCampo?: string;
    importoCampo?: number;
	importoTotalePagamenti?: number;
	pagamenti?: Array<PagamentoEntry>;
}

export interface ResponseDatiContabilita extends BaseResponse {
    impegni?: Array<ImpegnoEntry>;
    pagamenti?: Array<PagamentoEntry>;
}



export interface FullFlussiFase {
    autore?: string;
    codGara?: number;
    codLotto?: number;
    dataInvio?: Date;
    noteInvio?: string;
    num?: number;
    numFase?: number;
    tipoInvio?: string;
    xml?: string;
    idSchedaLocale?: string;
}


export interface FullDettaglioAttoEntry {
    /**
     * campi obbligatori per la creazione dell'atto
     */
    campiObbligatori?: string;
    /**
     * campi ulteriori per la creazione dell'atto
     */
    campiVisibili?: string;
    codGara?: number;
    dataDecreto?: Date;
    dataProvvedimento?: Date;
    dataPubb?: Date;
    dataScad?: Date;
    dataStipula?: Date;
    dataVerbAggiudicazione?: Date;
    descriz?: string;
    documents?: Array<AttoDocument>;
    idGenerato?: number;
    idRicevuto?: number;
    importoAggiudicazione?: number;
    numProvvedimento?: string;
    numPubb?: number;
    numRepertorio?: string;
    percOffAumento?: number;
    percRibassoAgg?: number;
    tipDoc?: number;
    /**
     * Descrizione relativa al campo \"tipdoc\"
     */
    tipDocDesc?: string;
    urlCommittente?: string;
    urlEproc?: string;
}

export interface ResponseAttiAssociatiLotto {
    data?: Array<FullDettaglioAttoEntry>;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione
     */
    esito?: boolean;
}

export interface RupGaraUpdateForm {
    codGara?: number;
    rup?: string;
}

export interface SysconGaraUpdateForm {
    codGara?: number;
    syscon?: number;
}


export interface MigrazioneGaraForm {
    codiceFiscaleRupGara?: string;
    codGara?: number;
    stazioneAppaltante?: string;
}

export interface LottoAccorpabileEntry {
    allowMultiotto?: boolean;
    hasMultiLotto?: boolean;
}

export interface DatiAccorpamentoEntry {
    accorpamenti?: Array<AccorpamentoEntry>;
    accorpamentiDisponibili?: boolean;
}

export interface AccorpamentoEntry {
    cigAccorpati?: Array<string>;
    cigMaster?: string;
}

export interface PulisciAccorpamentiMultilottoForm {
    codGara?: number;
}

export interface AccorpaMultilottoForm {
    codGara?: number;
    codLottoAccorpati?: Array<number>;
    codLottoMaster?: number;
    syscon?: number;
}

export interface AccorpaMultilottoEntry {
    aggiudicazioniOk?: boolean;
    anagraficaOk?: boolean;
    cigInvalidi?: Array<string>;
}



export class SimogCredentialsForm {
    skipRpntLogin: boolean;
    username: string;
    password: string;
    saveCredentials: boolean;

    public constructor(data: any = {}) {
        this.skipRpntLogin = false;
        this.username = null;
        this.password = null;
        this.saveCredentials = false;

        Object.assign(this, data);
    }
}

export interface BaseResponse {
    error?: string;
    errorData?: string;
    esito?: boolean;
    rpntLoginFailed?: boolean;
    simogUsername?: string;
    cfRup?: string;
    rupCredentialsInvalid?: boolean;
    rupCredentialsMissing?: boolean;
    infoMessaggi?: string[];
}