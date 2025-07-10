// @dynamic
export class Constants {

    public static get SA_INFO_SELECT(): string {
        return 'saInfo';
    }

    public static get LOGOUT_PATH(): string {
        return 'LOGOUT_PATH';
    }


    public static get SA_INFO_DISPATCHER(): string {
        return 'SAINFO';
    }

    public static get LISTA_STAZIONI_APPALTANTI_COUNT_DISPATCHER(): string {
        return 'STAZIONI_APPALTANTI_COUNT';
    }

    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get ABILITAZIONE_AMMINISTRATORE(): string {
        return 'ou89';
    }

    public static get AUTHENTICATION_TOKEN_INFO(): string {
        return 'AUTHENTICATION_TOKEN_INFO';
    }

    public static get AUTHENTICATION_APP_CODE(): string {
        return 'AUTHENTICATION_APP_CODE';
    }

    public static get APP_CODE(): string {
        return 'APP_CODE';
    }

    public static get SESSION_EXPIRED(): string {
        return 'SESSION_EXPIRED';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get INBOX_ORIGIN(): string {
        return 'inbox';
    }

    public static get OUTBOX_ORIGIN(): string {
        return 'outbox';
    }

    public static get LETTO(): number {
        return 1;
    }

    public static get NON_LETTO(): number {
        return 0;
    }

    public static get PROVINCE(): string {
        return 'Province';
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    // Store
    public static get SEARCH_FORM_ARCHIVIO_IMPRESE_DISPATCHER(): string {
        return 'searchFormAID';
    }

    public static get SEARCH_FORM_ARCHIVIO_IMPRESE_SELECT(): string {
        return 'searchFormAIS';
    }

    public static get SEARCH_FORM_ARCHIVIO_CDC_DISPATCHER(): string {
        return 'searchFormACDCD';
    }

    public static get SEARCH_FORM_ARCHIVIO_CDC_SELECT(): string {
        return 'searchFormACDCS';
    }

    public static get SEARCH_FORM_ARCHIVIO_TECNICI_DISPATCHER(): string {
        return 'searchFormATECNICID';
    }

    public static get SEARCH_FORM_ARCHIVIO_TECNICI_SELECT(): string {
        return 'searchFormATECNICIS';
    }

    public static get SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER(): string {
        return 'searchFormASAD';
    }

    public static get SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_SELECT(): string {
        return 'searchFormASAS';
    }

    public static get SEARCH_FORM_ARCHIVIO_UFFICI_DISPATCHER(): string {
        return 'searchFormAUFFICID';
    }

    public static get SEARCH_FORM_ARCHIVIO_UFFICI_SELECT(): string {
        return 'searchFormAUFFICIS';
    }

    public static get CDC_TABELLATI(): Array<string> {
        return [
            'TipologiaCC'
        ];
    }

    public static get IMPRESA_TABELLATI(): Array<string> {
        return [
            'Province',
            'Nazioni',
            'FormaGiuridica'
        ];
    }

    public static get COMUNI(): string {
        return 'Comuni';
    }

    public static get ARCHIVIO_STAZIONI_APPALTANTI_TABELLATI(): Array<string> {
        return [
            'TipologiaStazioneAppaltante'
        ];
    }

    public static get OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI(): string {
        return 'ou214';
    }

    public static get RICERCA_AVANZATA_ARCHIVIO_STAZIONI_APPALTANTI_TABELLATI(): Array<string> {
        return [
            'Province'
        ];
    }

    public static get OU_GESTIONE_UTENTI_COMPLETA(): string {
        return 'ou11';
    }

    public static get OU_GESTIONE_UTENTI_OU12(): string {
        return 'ou12';
    }

    public static get RESPONSE_N(): string {
        return 'N';
    }

    public static get RESPONSE_Y(): string {
        return 'Y';
    }

    public static get MANUALI_URL_STORAGE_CODE(): string {
        return 'MANUALI_URL';
    }

    public static get UFFINT_RISERVATA(): string {
        return 'RISERVATA';
    }

    public static get RICERCA_EVENTI_TABELLATI(): Array<string> {
        return [
            'LivelloEvento'
        ];
    }
}