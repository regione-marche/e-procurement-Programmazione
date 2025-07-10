export class SdkGestioneReportConstants {

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


    public static get SA_INFO_SELECT(): string {
        return 'saInfo';
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get ABILITAZIONE_AMMINISTRATORE(): string {
        return 'ou89';
    }

    public static get RESPONSE_N(): string {
        return 'N';
    }

    public static get RESPONSE_Y(): string {
        return 'Y';
    }
    
    public static get CODIFICA_AUTOMATICA_DEFAULT_CODAPP(): string {
        return 'G_';
    }

    public static get TIPO_PARAMETRI_REPORTS_TABELLATI(): Array<string> {
        return [
            'tipoParametroReport'
        ];
    }

    public static get OU_GESTIONE_UTENTI_COMPLETA(): string {
        return 'ou11';
    }

    public static get OU_GESTIONE_UTENTI_OU12(): string {
        return 'ou12';
    }

    public static get PROVINCE(): string {
        return 'Province';
    }

    public static get ARCHIVIO_STAZIONI_APPALTANTI_TABELLATI(): Array<string> {
        return [
            'TipologiaStazioneAppaltante'
        ];
    }

    public static get COMUNI(): string {
        return 'Comuni';
    }

    public static get UFFINT_RISERVATA(): string {
        return 'RISERVATA';
    }

    public static get OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI(): string {
        return 'ou214';
    }

    public static get COUNT_REPORT_PREDEFINITI(): string {
        return 'COUNT_REPORT_PREDEFINITI';
    }

    public static get MODIFYING_OPERATIONS(): Array<string>{
        return [
            "INSERT",
            "UPDATE",
            "DELETE",
            "UPSERT",
            "CREATE TABLE",
            "ALTER TABLE",
            "ADD COLUMN",
            "DROP COLUMN",
            "MODIFY COLUMN",
            "RENAME COLUMN",
            "RENAME TO",
            "ADD CONSTRAINT",
            "DROP CONSTRAINT",
            "DROP TABLE",
            "TRUNCATE TABLE",
            "CREATE INDEX",
            "DROP INDEX",
            "RENAME TABLE"
        ];
    }
}