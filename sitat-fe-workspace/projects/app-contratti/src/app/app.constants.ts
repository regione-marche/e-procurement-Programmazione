export class Constants {

    // serve per salvare l'oggetto AuthenticationTokenInfo dopo l'arrivo sull'applicazione
    public static get AUTHENTICATION_TOKEN_INFO(): string {
        return 'AUTHENTICATION_TOKEN_INFO';
    }

    // serve per salvare l'appCode di autenticazione
    public static get AUTHENTICATION_APP_CODE(): string {
        return 'AUTHENTICATION_APP_CODE';
    }

    public static get USER_PROFILE_TO_LAUNCHER(): string {
        return 'USER_PROFILE_TO_LAUNCHER';
    }

    // serve per settare il codice applicativo del modulo selezionato
    public static get APP_CODE(): string {
        return 'APP_CODE';
    }

    public static get SESSION_EXPIRED(): string {
        return 'SESSION_EXPIRED';
    }

    public static get VALORI_TABELLATO(): string {
        return 'VALORI_TABELLATO';
    }

    public static get TIPO_AVVISO(): string {
        return 'TipoAvviso';
    }

    public static get TIPO_ATTO_GENERALE(): string {
        return 'TipoAttoGenerale';
    }

    public static get APP_NAME(): string {
        return 'app-contratti';
    }

    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get SA_INFO_DISPATCHER(): string {
        return 'SAINFO';
    }

    public static get SA_INFO_SELECT(): string {
        return 'saInfo';
    }

    public static get SEARCH_FORM_ATTI_GENERALI_DISPATCHER(): string {
        return 'searchFormAGD';
    }

    public static get SEARCH_FORM_ATTI_GENERALI_SELECT(): string {
        return 'searchFormAGS';
    }

    public static get SEARCH_FORM_AVVISI_DISPATCHER(): string {
        return 'searchFormD';
    }

    public static get SEARCH_FORM_AVVISI_SELECT(): string {
        return 'searchFormS';
    }

    public static get SEARCH_FORM_GARE_DISPATCHER(): string {
        return 'searchFormGD';
    }

    public static get SEARCH_FORM_GARE_SELECT(): string {
        return 'searchFormGS';
    }

    public static get SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_DISPATCHER(): string {
        return 'searchFormRPCPD';
    }

    public static get SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_SELECT(): string {
        return 'searchFormRPCPS';
    }

    public static get RICERCA_AVANZATA_GARE_TABELLATI(): Array<string> {
        return [
            'TipoRealizzazione',
            'Situazione',
            'SceltaContraente'
        ];
    }

    public static get LISTA_GARE_TABELLATI(): Array<string> {
        return [
            'Situazione'
        ];
    }

    public static get LISTA_SCHEDE_TABELLATI(): Array<string>{
        return [
            'Fase'
        ];
    }

    public static get DETTAGLIO_GARA_TABELLATI(): Array<string> {
        return [
            'Situazione',
            'Provenienza',
            'Settore',
            'Indizione',
            'TipoRealizzazione',
            'TipologiaSA',
            'TipologiaProcedura',
            'ModalitaIndizione9',
            'GaraUrgenza'
        ];
    }

    public static get PUBBLICA_FASE_LOTTO_TABELLATI(): Array<string> {
        return ['TipoInvio'];
    }

    public static get COMUNI(): string {
        return 'Comuni';
    }

    public static get PROVINCE(): string {
        return 'Province';
    }

    public static get LISTA_LOTTI_TABELLATI(): Array<string> {
        return [
            'Situazione',
            'TipoAppalto'
        ];
    }

    public static get LOTTO_TABELLATI(): Array<string> {
        return [
            'Situazione',
            'TipoAppalto',
            'Settore',
            'SceltaContraente',
            'SceltaContraente50',
            'CriterioAggiudicazione',
            'CategorieAll',
            'Classe',
            'PrestazioniComprese',
            'DerogaQualificazioneSa',
            'CategoriaMerc',
            'MotivoCollegamento',
            'PrevisioneQuota'
        ];
    }

    public static get FASI_LOTTO_TABELLATI(): Array<string> {
        return [
            'Fase'
        ];
    }

    public static get FASE_INIZIALE_TABELLATI(): Array<string> {
        return [
            'IncontriOrganiVigilanza'
        ];
    }

    public static get FASE_INCARICHI_PROFESSIONALI(): Array<string> {
        return [
            'TipologiaSoggetto',
            'tipoProgettazione'
        ];
    }

    public static get FASE_VARAIZIONE_AGGIUDICATARI(): Array<string> {
        return [
            'RuoloVaraggi',
            'TipoOperatoreEconomico',
            'FlagAvvalimento',
            'MotivoVariazione'
        ];
    }

    public static get FASE_AGGIUDICAZIONE_TABELLATI(): Array<string> {
        return [
            'CodStrumento',
            'ModalitaRiaggiudicazione',
            'SettoriSpeciali',
            'Indizione',
            'TipoAtto',
            'PercentualeIva'
        ];
    }

    public static get FASE_COMUNICAZIONE_ESITO_TABELLATI(): Array<string> {
        return [
            'EsitoProcedura'
        ];
    }

    public static get IMPRESE_AGGIUDICATARIE_TABELLATI(): Array<string> {
        return [
            'TipologiaAggiudicatario',
            'RuoloAssociazione',
            'Avvalimento'
        ];
    }

    public static get INCARICHI_PROFESSIONALI_TABELLATI(): Array<string> {
        return [
            'TipologiaSoggetto'
        ];
    }

    public static get FASE_AGGIUDICAZIONE_SEMP_TABELLATI(): Array<string> {
        return [
            'TipoAtto',
            'PercentualeIva'
        ];
    }

    public static get FINANZIAMENTI_TABELLATI(): Array<string> {
        return [
            'TipologiaFinanziamento'
        ];
    }

    public static get ELENCO_IMPRESE_INVITATE_PARTECIPANTI(): Array<string> {
        return [
            'TipologiaAggiudicatario',
            'RuoloAssociazione',
            'ImpresaPartecipante'
        ];
    }

    public static get FASE_AVANZAMENTO_TABELLATI(): Array<string> {
        return [
            'Ritardo',
            'Pagamento'
        ];
    }

    public static get FASE_SOSPENSIONE_TABELLATI(): Array<string> {
        return [
            'MotivoSospensione'
        ];
    }

    public static get FASE_MODIFICA_CONTRATTUALE_TABELLATI(): Array<string> {
        return [
            'FlagVariante',
            'FlagImporti',
            'motivoRevPrezzi'
        ];
    }

    public static get FASE_ADESIONE_ACCORDO_QUADRO_TABELLATI(): Array<string> {
        return [
            'CodStrumento',
            'TipoAtto'
        ]
    }

    public static get FASE_CONCLUSIONE_TABELLATI(): Array<string> {
        return [
            'MotivoInterruzione',
            'MotivoRisoluzione',
            'FlagOneri'
        ]
    }

    public static get FASE_CONCLUSIONE_SEMP_TABELLATI(): Array<string> {
        return this.FASE_CONCLUSIONE_TABELLATI;
    }

    public static get FASE_COLLAUDO_TABELLATI(): Array<string> {
        return [
            'ModoCollaudo',
            'EsitoCollaudo',
            'FlagImporti',
            'FlagSingoloCommissione',
            'TipoCollaudo'
        ];
    }

    public static get FASE_SUBAPPALTO_TABELLATI(): Array<string> {
        return [
            'Categorie',
            'MotivoMancatoSub'
        ];
    }

    public static get FASE_RECESSO_TABELLATI(): Array<string> {
        return [
            'TipologiaComunicazione'
        ];
    }

    public static get FASE_INIDONEITA_CONTRIBUTIVA_TABELLATI(): Array<string> {
        return [
            'RiscontroIrregolarita'
        ];
    }

    public static get FASE_CANTIERI_TABELLATI(): Array<string> {
        return [
            'TipologiaOpera',
            'TipologiaInterventoCantiere',
            'TipologiaCostruttiva'
        ];
    }

    public static get SERVER_DATE_FORMAT(): string {
        return 'yyyy-MM-dd';
    }

    public static get SMARTCIG_TABELLATI(): Array<string> {
        return [
            'TipoAppalto',
            'SceltaContraente',
            'CriterioAggiudicazione',
            'Settore',
            'TipoRealizzazione',
            'TipologiaSA',
            'TipologiaProcedura',
            'CategorieAll',
            'Classe'
        ];
    }

    public static get MIGRAZIONE_COMPLETATA(): string {
        return 'MIGRAZIONE_COMPLETATA';
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    public static get LOCAL_STORAGE_REPORT_PRED(): string {
        return 'GRP';
    }

    public static get CURRENT_NUM_APPA_FASI_LOTTO_DISPATCHER(): string {
        return 'currentNumAppaFasiLottoD';
    }

    public static get CURRENT_NUM_APPA_FASI_LOTTO_SELECT(): string {
        return 'currentNumAppaFasiLottoS';
    }

    public static get LOGOUT_PATH(): string {
        return 'LOGOUT_PATH';
    }

    public static get COUNT_REPORT_PREDEFINITI(): string {
        return 'COUNT_REPORT_PREDEFINITI';
    }

    public static get LISTA_STAZIONI_APPALTANTI_COUNT_DISPATCHER(): string {
        return 'STAZIONI_APPALTANTI_COUNT';
    }

    public static get LISTA_STAZIONI_APPALTANTI_COUNT_SELECT(): string {
        return 'stazioniAppaltantiCount';
    }

    public static get IMPRESA_TABELLATI(): Array<string> {
        return [
            'Province',
            'Nazioni',
            'FormaGiuridica'
        ];
    }

    public static get SEARCH_FORM_ARCHIVIO_IMPRESE_DISPATCHER(): string {
        return 'searchFormAID';
    }

    public static get SEARCH_FORM_ARCHIVIO_CDC_DISPATCHER(): string {
        return 'searchFormACDCD';
    }

    public static get SEARCH_FORM_ARCHIVIO_TECNICI_DISPATCHER(): string {
        return 'searchFormATECNICID';
    }

    public static get SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER(): string {
        return 'searchFormASAD';
    }

    public static get MANUALI_URL_STORAGE_CODE(): string {
        return 'MANUALI_URL';
    }

    public static get LISTA_DELEGHE_TABELLATI(): Array<string> {
        return [""];
    }

    public static get OU_ABILITA_TUTTI_UFFICI_INTESTATARI(): string {
        return 'ou238';
    }

    public static get OP_ABILITA_GESTIONE_REPORT_PREDEFINITI(): string {
        return 'op98';
    }

    public static get ABILITAZIONE_AMMINISTRATORE(): string {
        return 'ou89';
    }
}

export type TEnvs = 'DEVELOPMENT' | 'STAGING' | 'PRODUCTION' | 'DEVELOPMENT-PRODUCTION';
