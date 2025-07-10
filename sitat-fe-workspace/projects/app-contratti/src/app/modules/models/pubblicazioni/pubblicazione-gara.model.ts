/**
 * Contenitore per i dati di pubblicazione della gara
 */
export interface PubblicaGaraEntry {
    /**
     * Altri soggetti per cui agisce la S.A.
     */
    altreSA?: string;
    /**
     * Lista atti della gara (valorizzato solo nel metodo /Anagrafiche/DettaglioGara)
     */
    atti?: Array<AttoGaraEntry>;
    /**
     * La centrale di committenza provvede alla stipula?
     */
    centraleCommittenza?: string;
    /**
     * Codice fiscale del soggetto per cui agisce la S.A.
     */
    cfAgente?: string;
    /**
     * CIG accordo quadro/convenzione
     */
    cigAccQuadro?: string;
    /**
     * Codice Fiscale Stazione appaltante
     */
    codiceFiscaleSA: string;
    /**
     * Comune sede di gara
     */
    comune?: string;
    /**
     * *[Attributo aggiuntivo, previsto in ambiti regionali]Data perfezionamento bando
     */
    dataPerfezionamentoBando?: string;
    /**
     * *[Attributo aggiuntivo, previsto in ambiti regionali] Data pubblicazione bando su GURI,se prevista,ovvero su albo pretorio
     */
    dataPubblicazione?: string;
    /**
     * *[Attributo aggiuntivo, previsto in ambiti regionali] Data scadenza del bando
     */
    dataScadenza?: string;
    /**
     * *[Attributo aggiuntivo, previsto in ambiti regionali]Durata accordo quadro
     */
    durataAccordoQuadro?: number;
    /**
     * Numero gara ANAC (mettere 0 nel caso di richiesta Smartcig)
     */
    idAnac?: string;
    /**
     * Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive
     */
    idRicevuto?: number;
    /**
     * Importo della gara
     */
    importoGara: number;
    /**
     * Indirizzo sede di gara
     */
    indirizzo?: string;
    /**
     * Lista lotti da pubblicare
     */
    lotti: Array<PubblicaLottoEntry>;
    /**
     * Denominazione del soggetto per cui agisce la S.A.
     */
    nomeSA?: string;
    /**
     * Oggetto - descrizione
     */
    oggetto: string;
    /**
     * Data pubblicazione gara su sito SCP (valorizzato solo nel metodo /Anagrafiche/DettaglioGara)
     */
    primaPubblicazioneSCP?: string;
    /**
     * Provincia sede di gara
     */
    provincia?: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Pubblicazione bando
     */
    pubblicazioneBando?: PubblicazioneBandoEntry;
    /**
     * Modalita' di realizzazione
     */
    realizzazione?: number;
    /**
     * La stazione appaltante agisce per conto di altri soggetti?
     */
    saAgente?: string;
    /**
     * Tipo di settore
     */
    settore?: string;
    /**
     * *[Attributo aggiuntivo, previsto in ambiti regionali]Somma urgenza?
     */
    sommaUrgenza?: string;
    /**
     * Responsabile unico procedimento
     */
    tecnicoRup: DatiGeneraliTecnicoEntry;
    /**
     * Tipologia procedura (nel caso agisca per conto di altri)
     */
    tipoProcedura?: number;
    /**
     * Tipologia della stazione appaltante
     */
    tipoSA?: number;
    /**
     * Ufficio/area di pertinenza
     */
    ufficio?: string;
    /**
     * Utima modifica pubblicazione gara su sito SCP (valorizzato solo nel metodo /Anagrafiche/DettaglioGara)
     */
    ultimaModificaSCP?: string;
    /**
     * *[Attributo aggiuntivo, previsto in ambiti regionali]Versione SIMOG
     */
    versioneSimog?: number;
     /**
     * Utente che invia la gara
     */
    autore?: string;
}

/**
 * Riferimento all'Atto di gara
 */
export interface AttoGaraEntry {
    /**
     * Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive
     */
    idRicevuto?: number;
    /**
     * Tipologia pubblicazione
     */
    tipoDocumento?: number;
}

/**
 * Contenitore per i dati generali del soggetto
 */
export interface DatiGeneraliTecnicoEntry {
    /**
     * Codice di avviamento postale
     */
    cap?: string;
    /**
     * Codice fiscale
     */
    cfPiva: string;
    /**
     * Numero civico
     */
    civico?: string;
    /**
     * Cognome del tecnico
     */
    cognome: string;
    /**
     * *FAX
     */
    fax?: string;
    /**
     * Indirizzo (via/piazza/corso)
     */
    indirizzo?: string;
    /**
     * *Localita` di residenza
     */
    localita?: string;
    /**
     * Codice ISTAT del Comune luogo di esecuzione del contratto
     */
    luogoIstat?: string;
    /**
     * *Indirizzo E-mail
     */
    mail?: string;
    /**
     * Nome del tecnico
     */
    nome: string;
    /**
     * *Cognome e nome del tecnico
     */
    nomeCognome?: string;
    /**
     * Provincia di residenza
     */
    provincia?: string;
    /**
     * *Numero di telefono
     */
    telefono?: string;
}

/**
 * Contenitore per i dati di pubblicazione del lotto
 */
 export interface PubblicaLottoEntry { 
    /**
     * Categoria prevalente
     */
    categoria?: string;
    /**
     * Categorie del lotto
     */
    categorie?: Array<CategoriaLottoEntry>;
    /**
     * Codice individuazione CIG
     */
    cig: string;
    /**
     * Classe importo categoria prevalente
     */
    classe?: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Codice intervento
     */
    codiceIntervento?: string;
    /**
     * Contratto escluso alleggerito
     */
    contrattoEsclusoAlleggerito?: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Contratto escluso ex artt. 16,17,18 D.Lgs 163/06?
     */
    contrattoEsclusoArt16e17e18?: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Contratto escluso ex artt. 19/26 D.Lgs 163/06?
     */
    contrattoEsclusoArt19e26?: string;
    /**
     * Codice CPV
     */
    cpv?: string;
    /**
     * Categorie CPV del lotto
     */
    cpvSecondari?: Array<CpvLottoEntry>;
    /**
     * Criterio di aggiudicazione
     */
    criterioAggiudicazione?: number;
    /**
     * CUI progettazione
     */
    cui?: string;
    /**
     * Codice CUP
     */
    cup?: string;
    /**
     * Esente dalla richiesta CUP?
     */
    cupEsente?: string;
    /**
     * Esclusione regime speciale
     */
    esclusioneRegimeSpeciale?: string;
    /**
     * Procedura di scelta contraente ai sensi del D.lgs. 50/2016
     */
    idSceltaContraente50?: number;
    /**
     * Importo del lotto al netto della sicurezza
     */
    importoLotto: number;
    /**
     * *Importo totale per l'attuazione della sicurezza
     */
    importoSicurezza?: number;
    /**
     * Codice ISTAT del Comune luogo di esecuzione del contratto
     */
    luogoIstat?: string;
    /**
     * Luogo di esecuzione del contratto - NUTS
     */
    luogoNuts?: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Posa in opera o manodopera?
     */
    manodopera?: string;
    masterCig?: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Modalit� acquisizione forniture
     */
    modalitaAcquisizioneForniture?: Array<AppaFornEntry>;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Motivazione ricorso a procedura negoziata
     */
    motivazioniProceduraNegoziata?: Array<MotivazioneProceduraNegoziataEntry>;
    /**
     * Numero del lotto della gara
     */
    numeroLotto?: number;
    /**
     * Oggetto del lotto
     */
    oggetto: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Prestazioni comprese nell'appalto
     */
    prestazioniComprese?: number;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Somma urgenza?
     */
    sommaUrgenza?: string;
    /**
     * Tipo appalto
     */
    tipoAppalto: string;
    /**
     * *[Attributo aggiuntivo, previsto in Simog] Tipologie dei lavori
     */
    tipologieLavori?: Array<AppaLavEntry>;
    /**
     * *Indirizzo/link profilo del committente
     */
    urlCommittente?: string;
    /**
     * *Indirizzo/link piattaforma telematica
     */
    urlPiattaformaTelematica?: string;
}

/**
 * *Contenitore per i dati della pubblicazione di un bando
 */
export interface PubblicazioneBandoEntry {
    /**
     * Data Albo pretorio del comuni ove si eseguono i lavori
     */
    dataAlbo?: string;
    /**
     * Data Gazzetta ufficiale o bollettino regionale
     */
    dataBore?: string;
    /**
     * Data Gazzetta Ufficiale Comunita Europea
     */
    dataGuce?: string;
    /**
     * Data Gazzetta ufficiale Repubblica Italiana
     */
    dataGuri?: string;
    /**
     * Numero periodici
     */
    periodici?: number;
    /**
     * Profilo del committente (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    profiloCommittente?: string;
    /**
     * Sito Informatico Ministero Infrastrutture (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    profiloInfTrasp?: string;
    /**
     * Sito Informatico Osservatorio Contratti Pubblici (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    profiloOsservatorio?: string;
    /**
     * Numero quotidiani locali
     */
    quotidianiLocali?: number;
    /**
     * Numero quotidiani nazionali
     */
    quotidianiNazionali?: number;
}

/**
 * *Modalita' acquisizione forniture
 */
export interface AppaFornEntry {
    /**
     * modalità di acquisizione forniture/servizi
     */
    modalitaAcquisizione: number;
}

/**
 * *Tipologia del lavoro
 */
export interface AppaLavEntry {
    /**
     * Tipologia del lavoro
     */
    tipologiaLavoro: number;
}

/**
 * Categoria del lotto
 */
export interface CategoriaLottoEntry {
    /**
     * Categoria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Categorie)
     */
    categoria: string;
    /**
     * Classe importo categoria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Classe)
     */
    classe: string;
    /**
     * Categoria scorporabile? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    scorporabile: string;
    /**
     * Categoria subappaltabile? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    subappaltabile: string;
}

/**
 * Categoria CPV del lotto
 */
export interface CpvLottoEntry {
    /**
     * Codice CPV secondario
     */
    cpv: string;
}

/**
 * Motivazione ricorso a procedura negoziata
 */
export interface MotivazioneProceduraNegoziataEntry {
    /**
     * Condizione
     */
    condizione: number;
}

export interface FlussiGara {
    autore?: string;
    codGara?: number;
    dataInvio?: Date;
    noteInvio?: string;
    xml?: string;
}