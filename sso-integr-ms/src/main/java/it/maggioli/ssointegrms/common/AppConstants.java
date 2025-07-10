package it.maggioli.ssointegrms.common;

/**
 * @author Cristiano Perin
 */
public class AppConstants {

    // Configurazioni API

    // gestione utenti
    public static final String RESPONSE_DONE_Y = "Y";
    public static final String RESPONSE_DONE_N = "N";
    public static final String GESTIONE_UTENTE_USER_ALREADY_EXISTS = "GESTIONE_UTENTE_USER_ALREADY_EXISTS";
    public static final String GESTIONE_UTENTE_PASSWORD_CONFIRM_PASSWORD_MISMATCH = "GESTIONE_UTENTE_PASSWORD_CONFIRM_PASSWORD_MISMATCH";
    public static final String GESTIONE_UTENTE_FORMATO_CODICE_FISCALE_NON_VALIDO = "GESTIONE_UTENTE_FORMATO_CODICE_FISCALE_NON_VALIDO";
    public static final String GESTIONE_UTENTE_CODICE_FISCALE_GIA_UTILIZZATO = "GESTIONE_UTENTE_CODICE_FISCALE_GIA_UTILIZZATO";
    public static final String GESTIONE_UTENTE_FORMATO_EMAIL_NON_VALIDO = "GESTIONE_UTENTE_FORMATO_EMAIL_NON_VALIDO";
    public static final String GESTIONE_UTENTE_UFFICIO_APPARTENENZA_OBBLIGATORIO = "GESTIONE_UTENTE_UFFICIO_APPARTENENZA_OBBLIGATORIO";
    public static final String GESTIONE_UTENTE_AMMINISTRATORE_CANCELLAZIONE_INTERVALLO_TEMPO = "GESTIONE_UTENTE_AMMINISTRATORE_CANCELLAZIONE_INTERVALLO_TEMPO";
    public static final String GESTIONE_UTENTE_USER_NOT_FOUND = "GESTIONE_UTENTE_USER_NOT_FOUND";
    public static final String GESTIONE_UTENTE_USER_FORBIDDEN = "GESTIONE_UTENTE_USER_FORBIDDEN";
    public static final String GESTIONE_UTENTE_USER_EDIT_FORBIDDEN = "GESTIONE_UTENTE_USER_EDIT_FORBIDDEN";
    public static final String GESTIONE_UTENTE_USER_NOT_ADMINISTRATOR = "GESTIONE_UTENTE_USER_NOT_ADMINISTRATOR";
    public static final String GESTIONE_UTENTE_USER_DATA_ULTIMO_ACCESSO_NOT_NULL = "GESTIONE_UTENTE_USER_DATA_ULTIMO_ACCESSO_NOT_NULL";
    public static final String GESTIONE_UTENTE_USER_EDIT_AMMINISTRAZIONI_WITHOUT_GESTIONE_COMPLETA = "GESTIONE_UTENTE_USER_EDIT_AMMINISTRAZIONI_WITHOUT_GESTIONE_COMPLETA";
    public static final String GESTIONE_UTENTE_NO_UFFINT_SELECTED = "GESTIONE_UTENTE_NO_UFFINT_SELECTED";
    public static final String GESTIONE_UTENTE_NO_PROFILES_SELECTED = "GESTIONE_UTENTE_NO_PROFILES_SELECTED";
    public static final String GESTIONE_UTENTE_USER_DELEGATO_ALL_SA = "GESTIONE_UTENTE_USER_DELEGATO_ALL_SA";
    public static final String SYSDISAB_UTENTE_ABILITATO = "0";
    public static final String SYSDISAB_UTENTE_DISABILITATO = "1";

    // login interna

    public static final String LOGIN_UNAUTHORIZED = "LOGIN_UNAUTHORIZED";
    public static final String LOGIN_MAX_TENTATIVI = "LOGIN_MAX_TENTATIVI";
    public static final String LOGIN_ACCOUNT_EXPIRED = "LOGIN_ACCOUNT_EXPIRED";
    public static final String LOGIN_PASSWORD_EXPIRED = "LOGIN_PASSWORD_EXPIRED";
    public static final String LOGIN_FIRST_ACCESS = "LOGIN_FIRST_ACCESS";
    public static final String LOGIN_USER_DISABLED = "LOGIN_USER_DISABLED";
    public static final String LOGIN_MTOKEN_CERT_NOT_FOUND = "LOGIN_MTOKEN_CERT_NOT_FOUND";
    public static final String LOGIN_MTOKEN_MOTIVAZIONE_NOT_FOUND = "LOGIN_MTOKEN_MOTIVAZIONE_NOT_FOUND";
    public static final String LOGIN_MTOKEN_FAILED = "LOGIN_MTOKEN_FAILED";
    public static final String CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH = "CHANGE_PASSWORD_CONFIRM_PASSWORD_MISMATCH";
    public static final String CHANGE_PASSWORD_LENGTH = "CHANGE_PASSWORD_LENGTH";
    public static final String CHANGE_PASSWORD_LENGTH_ADMIN = "CHANGE_PASSWORD_LENGTH_ADMIN";
    public static final String CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS = "CHANGE_PASSWORD_COMPLEXITY_ALLOWED_CHARACTERS";
    public static final String CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS = "CHANGE_PASSWORD_COMPLEXITY_LOWER_CASE_CHARACTERS";
    public static final String CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS = "CHANGE_PASSWORD_COMPLEXITY_UPPER_CASE_CHARACTERS";
    public static final String CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS = "CHANGE_PASSWORD_COMPLEXITY_NUMBERS_CHARACTERS";
    public static final String CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS = "CHANGE_PASSWORD_COMPLEXITY_SPECIAL_CHARACTERS";
    public static final String CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS = "CHANGE_PASSWORD_CONTENT_FOLLOWING_CHARACTERS";
    public static final String CHANGE_PASSWORD_ALREADY_USED = "CHANGE_PASSWORD_ALREADY_USED";
    public static final String CHANGE_PASSWORD_CANNOT_CHANGE_TIME = "CHANGE_PASSWORD_CANNOT_CHANGE_TIME";
    public static final String PASSWORD_RECOVERY_TOKEN_NOT_FOUND = "PASSWORD_RECOVERY_TOKEN_NOT_FOUND";
    public static final String PASSWORD_RECOVERY_TOKEN_EXPIRED = "PASSWORD_RECOVERY_TOKEN_EXPIRED";
    public static final String PASSWORD_RECOVERY_PASSWORD_CONFIRM_PASSWORD_MISMATCH = "PASSWORD_RECOVERY_PASSWORD_CONFIRM_PASSWORD_MISMATCH";
    public static final String PASSWORD_RECOVERY_USER_NOT_FOUND = "PASSWORD_RECOVERY_USER_NOT_FOUND";

    // amministrazione

    public static final String GESTIONE_CONFIGURAZIONI_CONFIGURAZIONE_NOT_FOUND = "GESTIONE_CONFIGURAZIONI_CONFIGURAZIONE_NOT_FOUND";
    public static final String GESTIONE_PIANIFICAZIONI_PIANIFICAZIONE_NOT_FOUND = "GESTIONE_PIANIFICAZIONI_PIANIFICAZIONE_NOT_FOUND";
    public static final String GESTIONE_EVENTI_EVENTO_NOT_FOUND = "GESTIONE_EVENTI_EVENTO_NOT_FOUND";
    public static final String GESTIONE_WMAIL_WMAIL_NOT_FOUND = "GESTIONE_WMAIL_WMAIL_NOT_FOUND";
    public static final String GESTIONE_WMAIL_WMAIL_ALREADY_EXISTS = "GESTIONE_WMAIL_WMAIL_ALREADY_EXISTS";
    public static final String GESTIONE_WMAIL_TIMEOUT_NOT_A_NUMBER = "GESTIONE_WMAIL_TIMEOUT_NOT_A_NUMBER";
    public static final String GESTIONE_WMAIL_OLD_PASSWORD_EMPTY = "GESTIONE_WMAIL_OLD_PASSWORD_EMPTY";
    public static final String GESTIONE_WMAIL_OLD_PASSWORD_MISMATCH = "GESTIONE_WMAIL_OLD_PASSWORD_MISMATCH";
    public static final String GESTIONE_WMAIL_NEW_PASSWORD_MISMATCH = "GESTIONE_WMAIL_NEW_PASSWORD_MISMATCH";
    public static final String GESTIONE_WMAIL_OLD_NEW_PASSWORD_MATCH = "GESTIONE_WMAIL_OLD_NEW_PASSWORD_MATCH";
    public static final String GESTIONE_WMAIL_ERROR_TEST_SEND = "GESTIONE_WMAIL_ERROR_TEST_SEND";
    public static final String GESTIONE_TABELLATI_IDENTIFICATIVO_NULL = "GESTIONE_TABELLATI_IDENTIFICATIVO_NULL";
    public static final String GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED = "GESTIONE_TABELLATI_IDENTIFICATIVO_ALREADY_INSERTED";
    public static final String GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND = "GESTIONE_TABELLATI_IDENTIFICATIVO_NOT_FOUND";
    public static final String GESTIONE_TABELLATI_TABELLATO_BLOCCATO = "GESTIONE_TABELLATI_TABELLATO_BLOCCATO";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_G_CONFCOD_NOT_FOUND = "GESTIONE_CODIFICA_AUTOMATICA_G_CONFCOD_NOT_FOUND";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA = "GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_NO_COUNTER = "GESTIONE_CODIFICA_AUTOMATICA_NO_COUNTER";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_OVER_ONE_COUNTER = "GESTIONE_CODIFICA_AUTOMATICA_OVER_ONE_COUNTER";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_CARATTERI_NON_ACCETTATI = "GESTIONE_CODIFICA_AUTOMATICA_CARATTERI_NON_ACCETTATI";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_CARATTERI_SUPERIORI_LUNGHEZZA_CAMPO = "GESTIONE_CODIFICA_AUTOMATICA_CARATTERI_SUPERIORI_LUNGHEZZA_CAMPO";
    public static final String GESTIONE_CODIFICA_AUTOMATICA_CODCAL_CONTAT_NULL = "GESTIONE_CODIFICA_AUTOMATICA_CODCAL_CONTAT_NULL";

    // Captcha
    public static final String CAPTCHA_ERROR = "CAPTCHA.ERROR-SOLUTION";

    // w_config
    public static final String UFFICIO_APPARTENENZA_OBBLIGATORIO = "it.eldasoft.dettaglioAccount.ufficioAppartenenza.obbligatorio";
    public static final String PROFILO_DEFAULT = "it.eldasoft.account.insert.profiloDefault";
    public static final String INVIO_MAIL_ABILITAZIONE_UTENTE = "it.eldasoft.mail.invioInAbilitazione";
    public static final String TITOLO_APPLICATIVO = "it.eldasoft.titolo";
    public static final String GESTIONE_GRUPPI_DISABILITATA = "it.eldasoft.associazioneGruppiDisabilitata";
    public static final String ESTENSIONI_AMMESSE = "uploadFile.estensioniAmmesse";
    public static final String DATABASE = "it.eldasoft.dbms";
    public static final String ACQUIRENTE_SW = "it.eldasoft.acquirenteSW";

    public static final String W_CONFIG_DURATA_ACCOUNT = "account.durata";
    public static final String W_CONFIG_INTERVALLO_MINIMO_CAMBIO_PASSWORD = "account.intervalloMinimoCambioPassword";
    public static final String W_CONFIG_LOGIN_KO_DELAY_SECONDI = "account.loginKO.delaySecondi";
    public static final String W_CONFIG_LOGIN_KO_DURATA_BLOCCO_MINUTI = "account.loginKO.durataBloccoMinuti";
    public static final String W_CONFIG_LOGIN_KO_MAX_NUM_TENTATIVI = "account.loginKO.maxNumTentativi";
    public static final String W_CONFIG_DURATA_PASSWORD = "it.eldasoft.account.durataPassword";
    public static final String W_CONFIG_VALIDITA_TOKEN_MINUTI = "account.recuperoPassword.validitaTokenMinuti";
    public static final String W_CONFIG_REGISTRAZIONE_LOGIN_CF = "registrazione.loginCF";

    // w_att
    public static final String W_ATT_OPZIONI = "it.eldasoft.opzioni";

    // General
    public static final String OU_SEPARATORE_REGEX = "\\|";
    public static final String COMBOBOX_SI = "1";
    public static final String COMBOBOX_NO = "2";
    public static final String W_CONFIG_COD_APP_STANDARD = "W_";
    public static final String W_CONFIG_COD_APP_GENE_STANDARD = "G_";
    public static final String W_CONFIG_JWT_KEY_CHIAVE = "it.maggioli.eldasoft.wslogin.jwtKey";
    public static final String LOGIN_MODE_TOSCANA = "0";
    public static final String G_CODCONF_DEFAULT_CODAPP = "G_";
    public static final String W_CONFIG_SI = "1";

    // w_genchiavi

    public static final String W_GENCHIAVI_W_LOGEVENTI = "W_LOGEVENTI";
    public static final String W_GENCHIAVI_G_LOGINKO = "G_LOGINKO";
    public static final String W_GENCHIAVI_USRCANC = "USRCANC";

    // Tabellati
    public static final String TAB1_UFFICIO_APPARTENENZA = "G_022";
    public static final String TAB1_CATEGORIE_UTENTE = "G_059";

    // Opzioni utenti
    public static final String OU_INSERIMENTO_NOTE = "ou59";
    public static final String OU_BLOCCA_UTENTE_MODIFICA_UFFICI_INTESTATARI = "ou214";
    public static final String OU_ABILITA_TUTTI_UFFICI_INTESTATARI = "ou238";
    public static final String OU_AMMINISTRATORE = "ou89";
    public static final String OU_GESTIONE_UTENTI_COMPLETA = "ou11";
    public static final String OU_GESTIONE_UTENTI_OU12 = "ou12";
    public static final String OU_GESTIONE_MODELLI_COMPLETA = "ou50";
    public static final String OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI = "ou51";

    // Controlli GDPR
    // Suppress 'PASSWORD' detected in this expression, review this potentially hard-coded password.
    @SuppressWarnings("java:S2068")
    public static final String OU_UTENTE_PASSWORD_SICURA = "ou39";

    // Regex
    public static final String REGEX_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@([\\w-]+\\.)+[a-zA-Z]{2,7}$";

    // Operazione attivazione/disattivazione
    public static final String OPERAZIONE_ATTIVAZIONE = "ATTIVAZIONE";
    public static final String OPERAZIONE_DISATTIVAZIONE = "DISATTIVAZIONE";

    // Richiesta assistenza
    public static final String RICHIESTA_ASSISTENZA_ABILITATA = "assistenza.modo";
    public static final String RICHIESTA_ASSISTENZA_OGGETTO = "assistenza.oggetto";
    public static final String RICHIESTA_ASSISTENZA_OGGETTO_SEPARATORE = ";";
    public static final String RICHIESTA_ASSISTENZA_OGGETTO_SINGOLO_SEPARATORE = "-";
    public static final String RICHIESTA_ASSISTENZA_ALLEGATO_FILE_SIZE = "assistenza.filesize";
    public static final String RICHIESTA_ASSISTENZA_EMAIL = "assistenza.mail";

    // w_usrtoken
    // Suppress 'PASSWORD' detected in this expression, review this potentially hard-coded password.
    @SuppressWarnings("java:S2068")
    public static final String W_USRTOKEN_PASSWORD_RECOVERY_TOKEN_TYPE = "reactivation_recover";

    // w_mail
    public static final String PROP_CONFIGURAZIONE_MAIL_STANDARD = "STD";
    public static final String PROP_CONFIGURAZIONE_MAIL_STANDARD_CONV = "CONVERSAZIONI";

    // Database
    // Tipo di database ORA=Oracle; MSQ=SQL Server; POS=PostgreSQL; DB2=IBM DB2
    public static final String DATABASE_ORACLE = "ORA";
    public static final String DATABASE_SQL_SERVER = "MSQ";
    public static final String DATABASE_POSTGRES = "POS";
    public static final String DATABASE_DB2 = "DB2";

    // usr_loa
    public static final int USR_LOA_INTERNAL_AUTHENTICATION_LOA = 2;
    public static final String USR_LOA_INTERNAL_AUTHENTICATION_TYPE = "CUSTOM";
}
