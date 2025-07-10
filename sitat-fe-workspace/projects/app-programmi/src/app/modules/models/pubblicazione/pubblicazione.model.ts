/**
 * Contenitore per i dati di pubblicazione di un programma per lavori
 */
export interface PubblicaProgrammaLavoriEntry {
    /**
     * Anno di inizio del programma
     */
    anno: number;
    /**
     * Codice Fiscale Stazione appaltante
     */
    codiceFiscaleSA: string;
    /**
     * Data adozione (OBBLIGATORIO se non ci sono i dati di approvazione)
     */
    dataAdozione?: string;
    /**
     * Data approvazione (OBBLIGATORIO se non ci sono i dati di adozione)
     */
    dataApprovazione?: string;
    /**
     * Data pubblicazione adozione (OBBLIGATORIO se non ci sono i dati di approvazione)
     */
    dataPubblicazioneAdozione?: string;
    /**
     * Data pubblicazione approvazione (OBBLIGATORIO se non ci sono i dati di adozione)
     */
    dataPubblicazioneApprovazione?: string;
    /**
     * Descrizione del Programma
     */
    descrizione: string;
    /**
     * ID del programma('LP' + CodiceFiscaleSA(11 caratteri) + anno(4 cifre) + progressivo(3 cifre))
     */
    id: string;
    /**
     * Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive
     */
    idRicevuto?: number;
    /**
     * Lista interventi
     */
    interventi?: Array<InterventoPubbEntry>;
    /**
     * Lista interventi non riproposti
     */
    interventiNonRiproposti?: Array<InterventoNonRipropostoPubbEntry>;
    /**
     * Numero adozione
     */
    numeroAdozione?: string;
    /**
     * Numero approvazione
     */
    numeroApprovazione?: string;
    /**
     * Lista opere incompiute
     */
    opereIncompiute?: Array<OperaIncompiutaPubbEntry>;
    /**
     * Referente del programma
     */
    referente: DatiGeneraliTecnicoPubbEntry;
    syscon?: number;
    /**
     * Titolo atto adozione (OBBLIGATORIO se non ci sono i dati di approvazione)
     */
    titoloAttoAdozione?: string;
    /**
     * Titolo atto approvazione (OBBLIGATORIO se non ci sono i dati di adozione)
     */
    titoloAttoApprovazione?: string;
    /**
     * Ufficio/area di pertinenza
     */
    ufficio?: string;
    /**
     * Url atto di adozione (OBBLIGATORIO se non ci sono i dati di approvazione)
     */
    urlAttoAdozione?: string;
    /**
     * Url atto di approvazione (OBBLIGATORIO se non ci sono i dati di adozione)
     */
    urlAttoApprovazione?: string;
    /**
     * Autore del programma
     */
     autore?: string;
}

/**
 * Contenitore per i dati generali del soggetto
 */
export interface DatiGeneraliTecnicoPubbEntry {
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
     * FAX
     */
    fax?: string;
    /**
     * Indirizzo (via/piazza/corso)
     */
    indirizzo?: string;
    /**
     * Localita` di residenza
     */
    localita?: string;
    /**
     * Codice ISTAT del Comune luogo di esecuzione del contratto
     */
    luogoIstat?: string;
    /**
     * Indirizzo E-mail
     */
    mail?: string;
    /**
     * Nome del tecnico
     */
    nome: string;
    /**
     * Cognome e nome del tecnico
     */
    nomeCognome?: string;
    /**
     * Provincia di residenza
     */
    provincia?: string;
    /**
     * Numero di telefono
     */
    telefono?: string;
}

/**
 * Contenitore per i dati di un intervento non riproposto
 */
export interface InterventoNonRipropostoPubbEntry {
    /**
     * Codice CUI
     */
    cui: string;
    /**
     * Codice CUP di progetto (assegnato da CIPE)
     */
    cup?: string;
    /**
     * Descrizione dell'intervento
     */
    descrizione: string;
    /**
     * Importo complessivo
     */
    importo: number;
    /**
     * Motivo per il quale l'intervento non e' riproposto
     */
    motivo: string;
    /**
     * Livello di Priorita' (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Priorita)
     */
    priorita?: number;
}

/**
 * Contenitore per i dati di un intervento
 */
export interface InterventoPubbEntry {
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Acquisto di beni realizzati con materiali riciclati? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Acquisto)
     */
    acquistoMaterialiRiciclati?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Sono presenti acq. verdi art. 34 Dlgs 50/2016 (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Acquisto)
     */
    acquistoVerdi?: number;
    /**
     * Annualita' di riferimento avvio procedura di affidamento(1,2,3 anno)
     */
    anno: number;
    /**
     * Classificazione intervento: Categoria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=CategoriaIntervento)
     */
    categoria: string;
    /**
     * Codice interno attribuito dall'amministrazione
     */
    codiceInterno?: string;
    /**
     * Codice AUSA del Soggetto delegato (OBBLIGATORIO se anno = 1)
     */
    codiceSoggettoDelegato?: string;
    /**
     * Svolta verifica conformita' vincoli ambientali? (OBBLIGATORIO se anno = 1)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    conformitaAmbientale?: string;
    /**
     * Svolta verifica conformita' urbanistica? (OBBLIGATORIO se anno = 1)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    conformitaUrbanistica?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] L'acquisto ha copertura finanziaria? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaFinanziaria?: string;
    /**
     * Codice CPV
     */
    cpv?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Codice CPV materiali riciclati
     */
    cpvMatRic?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Codice CPV verdi
     */
    cpvVerdi?: string;
    /**
     * Numero intervento CUI ('L' + CodiceFiscale Stazione Appaltante(11 char) + anno (4 char) + progressivo (5 char))
     */
    cui: string;
    /**
     * Codice CUP di progetto (assegnato da CIPE) (OBBLIGATORIO se esenteCup = No)
     */
    cup?: string;
    /**
     * Si intende delegare la procedura di affidamento? (OBBLIGATORIO se anno = 1) (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    delega?: string;
    /**
     * Descrizione intervento
     */
    descrizione: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Direzione generale
     */
    direzioneGenerale?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Dirigente responsabile d'ufficio
     */
    dirigenteResponsabile?: string;
    /**
     * Esente CUP o CUP non ancora richiesto? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    esenteCup: string;
    /**
     * Finalita' dell'intervento (OBBLIGATORIO se anno = 1)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Finalita)
     */
    finalita?: string;
    /**
     * Immobili da trasferire
     */
    immobili?: Array<ImmobilePubbEntry>;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA materiali riciclati
     */
    importoIvaMatRic?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA verdi
     */
    importoIvaVerdi?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo al netto dell'IVA materiali riciclati
     */
    importoNettoIvaMatRic?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo al netto dell'IVA verdi
     */
    importoNettoIvaVerdi?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo Totale materiali riciclati
     */
    importoTotMatRic?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo Totale verdi
     */
    importoTotVerdi?: number;
    /**
     * Localizzazione - Codice ISTAT del Comune (OBBLIGATORIO se nuts non valorizzato)
     */
    istat?: string;
    /**
     * Lavoro complesso? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    lavoroComplesso: string;
    /**
     * Lotto funzionale? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    lottoFunzionale: string;
    /**
     * Mese previsto per avvio procedura contrattuale
     */
    meseAvvioProcedura?: number;
    /**
     * Denominazione del Soggetto delegato (OBBLIGATORIO se anno = 1)
     */
    nomeSoggettoDelegato?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Normativa di riferimento
     */
    normativaRiferimento?: string;
    /**
     * Eventuali note
     */
    note?: string;
    /**
     * Numero progressivo intervento
     */
    numeroProgressivo: number;
    /**
     * Localizzazione - NUTS (OBBLIGATORIO se istat non valorizzato)
     */
    nuts?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Oggetto materiali riciclati
     */
    oggettoMatRic?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Oggetto verdi
     */
    oggettoVerdi?: string;
    /**
     * Livello di Priorita' (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Priorita)
     */
    priorita: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Procedura affidamento (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=ProceduraAffidamento)
     */
    proceduraAffidamento?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Referente per i dati comunicati
     */
    referenteDati?: string;
    /**
     * Altra tipologia - Primo anno
     */
    risorseAltro1?: number;
    /**
     * Altra tipologia - Secondo anno
     */
    risorseAltro2?: number;
    /**
     * Altra tipologia - Terzo anno
     */
    risorseAltro3?: number;
    /**
     * Altra tipologia - Annualita' successive
     */
    risorseAltroSucc?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Primo anno
     */
    risorseArt31?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Secondo anno
     */
    risorseArt32?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Terzo anno
     */
    risorseArt33?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Annualita' successive
     */
    risorseArt3Succ?: number;
    /**
     * Stanziamenti di bilancio - Primo anno
     */
    risorseBilancio1?: number;
    /**
     * Stanziamenti di bilancio - Secondo anno
     */
    risorseBilancio2?: number;
    /**
     * Stanziamenti di bilancio - Terzo anno
     */
    risorseBilancio3?: number;
    /**
     * Stanziamenti di bilancio - Annualita' successive
     */
    risorseBilancioSucc?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Primo anno
     */
    risorseImmobili1?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Secondo anno
     */
    risorseImmobili2?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Terzo anno
     */
    risorseImmobili3?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Annualita' successive
     */
    risorseImmobiliSucc?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Primo anno
     */
    risorseMutuo1?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Secondo anno
     */
    risorseMutuo2?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Terzo anno
     */
    risorseMutuo3?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Annualita' successive
     */
    risorseMutuoSucc?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Primo anno
     */
    risorsePrivati1?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Secondo anno
     */
    risorsePrivati2?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Terzo anno
     */
    risorsePrivati3?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Annualita' successive
     */
    risorsePrivatiSucc?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Primo anno
     */
    risorseVincolatePerLegge1?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Secondo anno
     */
    risorseVincolatePerLegge2?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Terzo anno
     */
    risorseVincolatePerLegge3?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Annualita' successive
     */
    risorseVincolatePerLeggeSucc?: number;
    /**
     * Responsabile unico RUP
     */
    rup: DatiGeneraliTecnicoPubbEntry;
    /**
     * Scadenza finanziamento da mutuo
     */
    scadenzaFinanziamento?: string;
    /**
     * Spese gia' sostenute
     */
    speseSostenute?: number;
    /**
     * Stato Progettazione approvata (OBBLIGATORIO se anno = 1)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=StatoProgettazione)
     */
    statoProgettazione?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Struttura operativa
     */
    strutturaOperativa?: string;
    /**
     * Classificazione intervento: Tipologia (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipologiaIntervento)
     */
    tipologia: string;
    /**
     * Tipologia apporto di capitale privato (OBBLIGATORIO se risorsePrivati1, risorsePrivati2, risorsePrivati3 valorizzati)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipologiaCapitalePrivato)
     */
    tipologiaCapitalePrivato?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Valutazione del responsabile dl programma (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Valutazione)
     */
    valutazione?: number;
    /**
     * Intervento variato a seguito di modifica programma (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Variato)
     */
    variato?: number;
    
    cigAccordoQuadro?: string;
}

/**
 * Contenitore per i dati di un'opera incompiuta
 */
export interface OperaIncompiutaPubbEntry {
    /**
     * Altri dati
     */
    altriDati?: AltriDatiOperaIncompiutaPubbEntry;
    /**
     * Ambito di interesse dell'opera (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Ambito)
     */
    ambito: string;
    /**
     * Anno ultimo q.e. approvato
     */
    anno: number;
    /**
     * Causa per la quale l'opera e' incompiuta (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Causa)
     */
    causa: string;
    /**
     * Cessione per realizzazione di altra opera? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    cessione: string;
    /**
     * Codice CUP
     */
    cup: string;
    /**
     * Opera da demolire? (OBBLIGATORIO  se previstaVendita = No)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    demolizione?: string;
    /**
     * Descrizione opera
     */
    descrizione: string;
    /**
     * Destinazione d'uso (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=DestinazioneUso)
     */
    destinazioneUso: string;
    /**
     * Determinazioni dell'amministrazione (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Determinazioni)
     */
    determinazioneAmministrazione: string;
    /**
     * Opera fruibile parzialmente? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    fruibile: string;
    /**
     * Immobili da trasferire
     */
    immobili?: Array<ImmobilePubbEntry>;
    /**
     * Importo ultimo SAL
     */
    importoAvanzamento: number;
    /**
     * Importo complessivo dell'intervento
     */
    importoIntervento: number;
    /**
     * Importo complessivo lavori
     */
    importoLavori: number;
    /**
     * Parte di infrastruttura di rete? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    infrastruttura?: string;
    /**
     * Oneri necessari per l'ultimazione dei lavori
     */
    oneri: number;
    /**
     * Oneri Sito
     */
    oneriSito?: number;
    /**
     * Percentuale avanzamento lavori %
     */
    percentualeAvanzamento: number;
    /**
     * Prevista la vendita? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    previstaVendita: string;
    /**
     * Utilizzo ridimensionato? (OBBLIGATORIO se fruibile = Si)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    ridimensionato?: string;
    /**
     * Stato di realizzazione (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=StatoRealizzazione)
     */
    stato: string;
}

/**
 * Contenitore per dati di un'immobile
 */
export interface ImmobilePubbEntry {
    /**
     * [Attributo non utilizzato] Alienati per il finanziamento e la realizzazione di opere pubbliche (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    alienati?: string;
    /**
     * Codice univoco immobile ('I' + CodiceFiscale Stazione Appaltante(11 char) + anno (4 char) + progressivo (5 char))
     */
    cui: string;
    /**
     * Descrizione dell'immobile
     */
    descrizione: string;
    /**
     * Immobile disponibile art. 21-c5 (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=ImmobileDisponibile)
     */
    immobileDisponibile: number;
    /**
     * Gia' incluso in programma di dismissione art. 27 DL 201/2011 (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=ProgrammaDismissione)
     */
    inclusoProgrammaDismissione: number;
    /**
     * Localizzazione - Codice ISTAT del Comune (OBBLIGATORIO se nuts non valorizzato)
     */
    istat?: string;
    /**
     * Localizzazione - NUTS (OBBLIGATORIO se istat non valorizzato)
     */
    nuts?: string;
    /**
     * Tipo proprieta' (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipoDisponibilita)
     */
    tipoDisponibilita: number;
    /**
     * [Attributo non utilizzato] Tipo proprieta' (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipoProprieta)
     */
    tipoProprieta?: number;
    /**
     * Trasferimento immobile a titolo corrispettivo (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TrasferimentoImmobile)
     */
    trasferimentoTitoloCorrispettivo: number;
    /**
     * Valore stimato dell'immobile - Primo anno
     */
    valoreStimato1?: number;
    /**
     * Valore stimato dell'immobile - Secondo anno
     */
    valoreStimato2?: number;
    /**
     * Valore stimato dell'immobile - Terzo anno
     */
    valoreStimato3?: number;
    /**
     * Valore stimato dell'immobile - Annualita' successive
     */
    valoreStimatoSucc?: number;
}

/**
 * Contenitore per gli altri dati di un'opera incompiuta
 */
export interface AltriDatiOperaIncompiutaPubbEntry {
    /**
     * Classificazione intervento: Categoria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=CategoriaIntervento)
     */
    categoriaIntervento: string;
    /**
     * Tipologia copertura finanziaria Altra Pubblica (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaAltro: string;
    /**
     * Tipologia copertura finanziaria Comunale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaComunale: string;
    /**
     * Tipologia copertura finanziaria Comunitaria (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaComunitaria: string;
    /**
     * Tipologia copertura finanziaria Privata (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaPrivata: string;
    /**
     * Tipologia copertura finanziaria Provinciale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaProvinciale: string;
    /**
     * Tipologia copertura finanziaria Regionale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaRegionale: string;
    /**
     * Tipologia copertura finanziaria Statale (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaStatale: string;
    /**
     * Costo progetto
     */
    costoProgetto: number;
    /**
     * Dimensionamento dell'opera (valore)
     */
    dimensione: number;
    /**
     * Finanza di progetto (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    finanzaDiProgetto: string;
    /**
     * Finanziamento assegnato
     */
    finanziamento: number;
    /**
     * Localizzazione - Codice ISTAT del Comune (OBBLIGATORIO se nuts non valorizzato)
     */
    istat?: string;
    /**
     * Localizzazione - NUTS (OBBLIGATORIO se istat non valorizzato)
     */
    nuts?: string;
    /**
     * Opera rispondente a tutti i requisiti dell'ultimo progetto approvato? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    requisitiApprovato: string;
    /**
     * Opera rispondente a tutti i requisiti di capitolato? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    requisitiCapitolato: string;
    /**
     * Sponsorizzazione (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    sponsorizzazione: string;
    /**
     * Classificazione intervento: Tipologia (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipologiaIntervento)
     */
    tipologiaIntervento: string;
    /**
     * Dimensionamento dell'opera (unita' di misura)
     */
    unitaMisura: string;
}

/**
 * Contenitore per i dati di pubblicazione di un programma di forniture e servizi
 */
export interface PubblicaProgrammaFornitureServiziEntry {
    /**
     * Lista acquisti
     */
    acquisti?: Array<AcquistoPubbEntry>;
    /**
     * Lista acquisti non riproposti
     */
    acquistiNonRiproposti?: Array<AcquistoNonRipropostoPubbEntry>;
    /**
     * Anno di inizio del programma
     */
    anno: number;
    /**
     * Codice Fiscale Stazione appaltante
     */
    codiceFiscaleSA: string;
    /**
     * Data approvazione
     */
    dataApprovazione: string;
    /**
     * Data pubblicazione approvazione
     */
    dataPubblicazioneApprovazione: string;
    /**
     * Descrizione del Programma
     */
    descrizione: string;
    /**
     * ID del programma ('FS' + CodiceFiscaleSA(11 caratteri) + anno(4 cifre) + progressivo(3 cifre))
     */
    id: string;
    /**
     * Id univoco generato dal sistema remoto; deve essere utilizzato per le chiamate successive
     */
    idRicevuto?: number;
    /**
     * Numero approvazione
     */
    numeroApprovazione?: string;
    /**
     * Referente del programma
     */
    referente: DatiGeneraliTecnicoPubbEntry;
    syscon?: number;
    /**
     * Titolo atto approvazione
     */
    titoloAttoApprovazione: string;
    /**
     * Ufficio/area di pertinenza
     */
    ufficio?: string;
    /**
     * Url atto di approvazione
     */
    urlAttoApprovazione: string;
}

/**
 * Contenitore per i dati di un acquisto non riproposto
 */
export interface AcquistoNonRipropostoPubbEntry {
    /**
     * Codice CUI
     */
    cui: string;
    /**
     * Codice CUP di progetto (assegnato da CIPE)
     */
    cup?: string;
    /**
     * Descrizione dell'intervento
     */
    descrizione: string;
    /**
     * Importo complessivo
     */
    importo: number;
    /**
     * Motivo per il quale l'intervento non e' riproposto
     */
    motivo: string;
    /**
     * Livello di Priorita' (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Priorita)
     */
    priorita?: number;
}

/**
 * Contenitore per i dati di un intervento
 */
export interface AcquistoPubbEntry {
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Acquisto di beni realizzati con materiali riciclati? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Acquisto)
     */
    acquistoMaterialiRiciclati?: number;
    /**
     * Acquisto ricompreso nell'importo di lavoro o altra acquisizione nel programma? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=AcquistoRicompreso)
     */
    acquistoRicompreso: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Sono presenti acq. verdi art. 34 Dlgs 50/2016 (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Acquisto)
     */
    acquistoVerdi?: number;
    /**
     * Annualita' di riferimento avvio procedura di affidamento (1,2,3 anno)
     */
    anno: number;
    /**
     * Codice interno attribuito dall'amministrazione
     */
    codiceInterno?: string;
    /**
     * Codice AUSA Centrale di Committenza o Soggetto Aggregatore (OBBLIGATORIO se anno = 1)
     */
    codiceSoggettoDelegato?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] L'acquisto ha copertura finanziaria? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    coperturaFinanziaria?: string;
    /**
     * Codice CPV
     */
    cpv: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Codice CPV materiali riciclati
     */
    cpvMatRic?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Codice CPV verdi
     */
    cpvVerdi?: string;
    /**
     * Numero intervento CUI ('F' + CodiceFiscale Stazione Appaltante(11 char) + anno (4 char) + progressivo (5 char))
     */
    cui: string;
    /**
     * Codice CUI dell'intervento collegato (OBBLIGATORIO quando acquistoRicompreso = No)
     */
    cuiCollegato?: string;
    /**
     * Codice CUP di progetto (assegnato da CIPE) (OBBLIGATORIO se esenteCup = No)
     */
    cup?: string;
    /**
     * Si intende delegare la procedura di affidamento? (OBBLIGATORIO se anno = 1)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    delega?: string;
    /**
     * Descrizione acquisto
     */
    descrizione: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Direzione generale
     */
    direzioneGenerale?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Dirigente responsabile d'ufficio
     */
    dirigenteResponsabile?: string;
    /**
     * Durata del contratto (mesi)
     */
    durataInMesi: number;
    /**
     * Esente CUP o CUP non ancora richiesto? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    esenteCup: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA - Primo anno
     */
    importoIva1?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA - Secondo anno
     */
    importoIva2?: number;
        /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA - Terzo anno
     */
        importoIva3?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA materiali riciclati
     */
    importoIvaMatRic?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA - Annualit� successive
     */
    importoIvaSucc?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo IVA verdi
     */
    importoIvaVerdi?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo al netto dell'IVA materiali riciclati
     */
    importoNettoIvaMatRic?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo al netto dell'IVA verdi
     */
    importoNettoIvaVerdi?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo risorse finanziarie stato/UE
     */
    importoRisorseFinanziarie?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo risorse finanziarie altro
     */
    importoRisorseFinanziarieAltro?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo risorse finanziarie regionali
     */
    importoRisorseFinanziarieRegionali?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo Totale materiali riciclati
     */
    importoTotMatRic?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Importo Totale verdi
     */
    importoTotVerdi?: number;
    /**
     * Localizzazione - Codice ISTAT del Comune (OBBLIGATORIO se nuts non valorizzato)
     */
    istat?: string;
    /**
     * Lotto funzionale? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    lottoFunzionale: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Mese previsto per avvio procedura contrattuale
     */
    meseAvvioProcedura?: number;
    /**
     * Denominazione Centrale di Committenza o Soggetto Aggregatore (OBBLIGATORIO se anno = 1)
     */
    nomeSoggettoDelegato?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Normativa di riferimento
     */
    normativaRiferimento?: string;
    /**
     * Eventuali note
     */
    note?: string;
    /**
     * Nuovo affidamento contratto in essere? (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=SN)
     */
    nuovoAffidamento: string;
    /**
     * Localizzazione - NUTS (OBBLIGATORIO se istat non valorizzato)
     */
    nuts?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Oggetto materiali riciclati
     */
    oggettoMatRic?: string;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Oggetto verdi
     */
    oggettoVerdi?: string;
    /**
     * Livello di Priorita' (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Priorita)
     */
    priorita: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Procedura affidamento (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=ProceduraAffidamento)
     */
    proceduraAffidamento?: number;
    /**
     * Quantita'
     */
    quantita?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Referente per i dati comunicati
     */
    referenteDati?: string;
    /**
     * Altra tipologia - Primo anno
     */
    risorseAltro1?: number;
    /**
     * Altra tipologia - Secondo anno
     */
    risorseAltro2?: number;
    /**
     * Altra tipologia - Annualita' successive
     */
    risorseAltroSucc?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Primo anno
     */
    risorseArt31?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Secondo anno
     */
    risorseArt32?: number;
    /**
     * Finanziamenti art. 3 DL 310/1990 - Annualita' successive
     */
    risorseArt3Succ?: number;
    /**
     * Stanziamenti di bilancio - Primo anno
     */
    risorseBilancio1?: number;
    /**
     * Stanziamenti di bilancio - Secondo anno
     */
    risorseBilancio2?: number;
    /**
     * Stanziamenti di bilancio - Annualita' successive
     */
    risorseBilancioSucc?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Primo anno
     */
    risorseImmobili1?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Secondo anno
     */
    risorseImmobili2?: number;
    /**
     * Risorse derivanti da trasferimento immobili - Annualita' successive
     */
    risorseImmobiliSucc?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Primo anno
     */
    risorseMutuo1?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Secondo anno
     */
    risorseMutuo2?: number;
    /**
     * Risorse derivanti da entrate acquisite mediante contrazione di mutuo - Annualita' successive
     */
    risorseMutuoSucc?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Primo anno
     */
    risorsePrivati1?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Secondo anno
     */
    risorsePrivati2?: number;
    /**
     * Risorse acquisite mediante apporti di capitale privato - Annualita' successive
     */
    risorsePrivatiSucc?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Primo anno
     */
    risorseVincolatePerLegge1?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Secondo anno
     */
    risorseVincolatePerLegge2?: number;
    /**
     * Risorse derivanti da entrate aventi destinazione vincolata per legge - Annualita' successive
     */
    risorseVincolatePerLeggeSucc?: number;
    /**
     * Responsabile unico RUP
     */
    rup: DatiGeneraliTecnicoPubbEntry;
    /**
     * Settore F=Forniture, S=Servizi
     */
    settore: AcquistoPubbEntry.SettoreEnum;
    /**
     * Spese gia' sostenute
     */
    speseSostenute?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Struttura operativa
     */
    strutturaOperativa?: string;
    /**
     * Tipologia apporto di capitale privato (OBBLIGATORIO se risorsePrivati1, risorsePrivati2, risorsePrivati3 valorizzati)(/WSTabelleDiContesto/rest/Tabellati/Valori?cod=TipologiaCapitalePrivato)
     */
    tipologiaCapitalePrivato?: string;
    /**
     * Unita' di misura (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=UnitaMisura)
     */
    unitaMisura?: number;
    /**
     * [Attributo aggiuntivo, previsto in ambiti regionali] Valutazione del responsabile dl programma (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Valutazione)
     */
    valutazione?: number;
    /**
     * Intervento variato a seguito di modifica programma (/WSTabelleDiContesto/rest/Tabellati/Valori?cod=Variato)
     */
    variato?: number;
    
    cigAccordoQuadro?: string;
}
export namespace AcquistoPubbEntry {
    export type SettoreEnum = 'F' | 'S';
    export const SettoreEnum = {
        F: 'F' as SettoreEnum,
        S: 'S' as SettoreEnum
    };
}

/**
 * Risultato della pubblicazione
 */
export interface PubblicazioneResult {
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    error?: string;
    /**
     * Risultato operazione di inserimento
     */
    esito?: boolean;
    /**
     * Id univoco generato dal sistema, deve essere utilizzato per le chiamate successive
     */
    id?: number;
    /**
     * Eventuale lista dei controlli di validazione che hanno generato errore
     */
    validate?: Array<ValidateEntry>;
}

/**
 * Errore di validazione
 */
export interface ValidateEntry {
    /**
     * Messaggio di errore
     */
    messaggio?: string;
    /**
     * Nome campo validato
     */
    nome?: string;
}


export interface FlussiProgrammi {
    autore?: string;
    contri?: number;
    dataInvio?: Date;
    noteInvio?: string;
    xml?: string;
}